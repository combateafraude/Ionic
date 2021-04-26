package com.example.plugin;


import android.app.Activity;
import android.content.Intent;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.combateafraude.documentdetector.DocumentDetectorActivity;
import com.combateafraude.documentdetector.input.CaptureMode;
import com.combateafraude.documentdetector.input.CaptureStage;
import com.combateafraude.documentdetector.input.DetectionSettings;
import com.combateafraude.documentdetector.input.Document;
import com.combateafraude.documentdetector.input.DocumentDetector;
import com.combateafraude.documentdetector.input.DocumentDetectorStep;
import com.combateafraude.documentdetector.input.QualitySettings;
import com.combateafraude.documentdetector.input.SensorLuminositySettings;
import com.combateafraude.documentdetector.input.SensorOrientationSettings;
import com.combateafraude.documentdetector.input.SensorStabilitySettings;
import com.combateafraude.documentdetector.output.Capture;
import com.combateafraude.documentdetector.output.DocumentDetectorResult;
import com.combateafraude.documentdetector.output.failure.SDKFailure;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@NativePlugin(
        requestCodes={IonicPlugin.REQUEST_CODE}
)
public class DocumentDetectorPlugin extends Plugin {
    protected static final int REQUEST_CODE = 1001;

    private static final String DRAWABLE_RES = "drawable";
    private static final String STYLE_RES = "style";
    private static final String STRING_RES = "string";
    private static final String RAW_RES = "raw";
    private static final String LAYOUT_RES = "layout";

    @PluginMethod()
    public void start(PluginCall call) throws JSONException {
        saveCall(call);

        String value = call.getString("value");
        //System.out.println("Parametro:" + value);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(value);
       }catch (JSONException err){
            Log.d("Error", err.toString());
       }
        Map<String, Object> argumentsMap = jsonToMap(jsonObject);
        DocumentDetector.Builder mDocumentDetectorBuilder = new DocumentDetector.Builder((String) argumentsMap.get("mobileToken"));

        // People ID
        String peopleId = (String) argumentsMap.get("peopleId");
        mDocumentDetectorBuilder.setPeopleId(peopleId);

        // Use Analytics
        Boolean useAnalytics = (Boolean) argumentsMap.get("useAnalytics");
        if (useAnalytics != null) mDocumentDetectorBuilder.setAnalyticsSettings(useAnalytics);

        ArrayList<HashMap<String, Object>> paramSteps = (ArrayList<HashMap<String, Object>>) argumentsMap.get("documentDetectorSteps");
        DocumentDetectorStep[] documentDetectorSteps = new DocumentDetectorStep[paramSteps.size()];
        for (int i = 0; i < paramSteps.size(); i++) {
            HashMap<String, Object> step = paramSteps.get(i);

            Document document = Document.valueOf((String) step.get("documentType"));
            documentDetectorSteps[i] = new DocumentDetectorStep(document);

            HashMap<String, Object> stepCustomization = (HashMap<String, Object>) step.get("android");
            if (stepCustomization != null) {
                Integer stepLabelId = getResourceId((String) stepCustomization.get("stepLabelStringResName"), STRING_RES);
                Integer illustrationId = getResourceId((String) stepCustomization.get("illustrationDrawableResName"), DRAWABLE_RES);
                Integer audioId = getResourceId((String) stepCustomization.get("audioRawResName"), RAW_RES);

                DocumentDetectorStep detectorStep = new DocumentDetectorStep(document);

                if(stepLabelId != null) detectorStep.setStepLabel(stepLabelId);
                if(illustrationId != null) detectorStep.setIllustration(illustrationId);
                if(audioId != null) detectorStep.setStepAudio(audioId);

                documentDetectorSteps[i] = detectorStep;
            } else {
                documentDetectorSteps[i] = new DocumentDetectorStep(document);
            }
        }
        mDocumentDetectorBuilder.setDocumentSteps(documentDetectorSteps);

        // Preview
        HashMap<String, Object> showPreview = (HashMap<String, Object>) argumentsMap.get("showPreview");
        if (showPreview != null) {
            boolean show = (boolean) showPreview.get("show");
            String title = (String) showPreview.get("title");
            String subTitle = (String) showPreview.get("subTitle");
            String confirmLabel = (String) showPreview.get("confirmLabel");
            String retryLabel = (String) showPreview.get("retryLabel");
            mDocumentDetectorBuilder.showPreview(show, title, subTitle, confirmLabel, retryLabel);
        }


        // Android specific settings
        HashMap<String, Object> androidSettings = (HashMap<String, Object>) argumentsMap.get("androidSettings");
        if (androidSettings != null) {

            // Capture stages
            ArrayList<HashMap<String, Object>> paramStages = (ArrayList<HashMap<String, Object>>) androidSettings.get("captureStages");
            if (paramStages != null) {
                CaptureStage[] captureStages = new CaptureStage[paramStages.size()];
                for (int i = 0; i < paramStages.size(); i++) {
                    HashMap<String, Object> stage = paramStages.get(i);

                    Long durationMillis = ((Number) stage.get("durationMillis")).longValue();

                    Boolean wantSensorCheck = (Boolean) stage.get("wantSensorCheck");
                    if (wantSensorCheck == null) wantSensorCheck = false;

                    QualitySettings qualitySettings = null;
                    HashMap<String, Object> qualitySettingsParam = (HashMap<String, Object>) stage.get("qualitySettings");
                    if (qualitySettingsParam != null) {
                        Double threshold = (Double) qualitySettingsParam.get("threshold");
                        if (threshold == null) threshold = QualitySettings.RECOMMENDED_THRESHOLD;
                        qualitySettings = new QualitySettings(threshold);
                    }

                    DetectionSettings detectionSettings = null;
                    HashMap<String, Object> detectionSettingsParam = (HashMap<String, Object>) stage.get("detectionSettings");
                    if (detectionSettingsParam != null) {
                        Double threshold = (Double) detectionSettingsParam.get("threshold");
                        if (threshold == null) threshold = DetectionSettings.RECOMMENDED_THRESHOLD;
                        Integer consecutiveFrames = (Integer) detectionSettingsParam.get("consecutiveFrames");
                        if (consecutiveFrames == null) consecutiveFrames = 5;
                        detectionSettings = new DetectionSettings(threshold, consecutiveFrames);
                    }
                    CaptureMode captureMode = CaptureMode.valueOf((String) stage.get("captureMode"));

                    captureStages[i] = new CaptureStage(durationMillis, wantSensorCheck, qualitySettings, detectionSettings, captureMode);
                }
                mDocumentDetectorBuilder.setCaptureStages(captureStages);
            }

            // Layout customization
            HashMap<String, Object> customizationAndroid = (HashMap<String, Object>) androidSettings.get("customization");
            if (customizationAndroid != null) {
                Integer styleId = getResourceId((String) customizationAndroid.get("styleResIdName"), STYLE_RES);
                if (styleId != null) mDocumentDetectorBuilder.setStyle(styleId);

                Integer layoutId = getResourceId((String) customizationAndroid.get("layoutResIdName"), LAYOUT_RES);
                Integer greenMaskId = getResourceId((String) customizationAndroid.get("greenMaskResIdName"), DRAWABLE_RES);
                Integer whiteMaskId = getResourceId((String) customizationAndroid.get("whiteMaskResIdName"), DRAWABLE_RES);
                Integer redMaskId = getResourceId((String) customizationAndroid.get("redMaskResIdName"), DRAWABLE_RES);
                mDocumentDetectorBuilder.setLayout(layoutId, greenMaskId, whiteMaskId, redMaskId);
            }


            // Sensor settings
            HashMap<String, Object> sensorSettings = (HashMap<String, Object>) androidSettings.get("sensorSettings");
            if (sensorSettings != null) {
                HashMap<String, Object> sensorLuminosity = (HashMap<String, Object>) sensorSettings.get("sensorLuminositySettings");
                if (sensorLuminosity != null) {
                    Integer sensorMessageId = getResourceId((String) sensorLuminosity.get("messageResourceIdName"), STRING_RES);
                    Integer luminosityThreshold = (Integer) sensorLuminosity.get("luminosityThreshold");
                    if (sensorMessageId != null && luminosityThreshold != null) {
                        mDocumentDetectorBuilder.setLuminositySensorSettings(new SensorLuminositySettings(sensorMessageId, luminosityThreshold));
                    }
                } else {
                    mDocumentDetectorBuilder.setLuminositySensorSettings(null);
                }

                HashMap<String, Object> sensorOrientation = (HashMap<String, Object>) sensorSettings.get("sensorOrientationSettings");
                if (sensorOrientation != null) {
                    Integer sensorMessageId = getResourceId((String) sensorOrientation.get("messageResourceIdName"), STRING_RES);
                    Double orientationThreshold = (Double) sensorOrientation.get("orientationThreshold");
                    if (sensorMessageId != null && orientationThreshold != null) {
                        mDocumentDetectorBuilder.setOrientationSensorSettings(new SensorOrientationSettings(sensorMessageId, orientationThreshold));
                    }
                } else {
                    mDocumentDetectorBuilder.setOrientationSensorSettings(null);
                }

                HashMap<String, Object> sensorStability = (HashMap<String, Object>) sensorSettings.get("sensorStabilitySettings");
                if (sensorStability != null) {
                    Integer sensorMessageId = getResourceId((String) sensorStability.get("messageResourceIdName"), STRING_RES);
                    Integer stabilityStabledMillis = (Integer) sensorStability.get("stabilityStabledMillis");
                    Double stabilityThreshold = (Double) sensorStability.get("stabilityThreshold");
                    if (sensorMessageId != null && stabilityStabledMillis != null && stabilityThreshold != null) {
                        mDocumentDetectorBuilder.setStabilitySensorSettings(new SensorStabilitySettings(sensorMessageId, stabilityStabledMillis, stabilityThreshold));
                    }
                } else {
                    mDocumentDetectorBuilder.setStabilitySensorSettings(null);
                }
            }
        }

        // Popup settings
        Boolean showPopup = (Boolean) argumentsMap.get("popup");
        if (showPopup != null) mDocumentDetectorBuilder.setPopupSettings(showPopup);

        // Sound settings
        Boolean enableSound = (Boolean) argumentsMap.get("sound");
        if (enableSound != null) mDocumentDetectorBuilder.enableSound(enableSound);

        // Network settings
        Integer requestTimeout = (Integer) argumentsMap.get("requestTimeout");
        if (requestTimeout != null) mDocumentDetectorBuilder.setNetworkSettings(requestTimeout);

        //AutoDetection
        Boolean autoDetectionEnable = (Boolean) argumentsMap.get("autoDetection");
        if(autoDetectionEnable != null) mDocumentDetectorBuilder.setAutoDetection(autoDetectionEnable);

        //CurrentStepDoneDelay
        Boolean showDelay = (Boolean) argumentsMap.get("showDelay");
        if(showDelay != null){
            if(argumentsMap.get("delay") != null) {
                int delay = (int) argumentsMap.get("delay");
                mDocumentDetectorBuilder.setCurrentStepDoneDelay(showDelay, delay);
            }
        }
        saveCall(call);

        Intent mIntent = new Intent(this.getContext(), DocumentDetectorActivity.class);
        mIntent.putExtra(DocumentDetector.PARAMETER_NAME, mDocumentDetectorBuilder.build());
        startActivityForResult(call, mIntent,REQUEST_CODE);
    }


    private Integer getResourceId(@Nullable String resourceName, String resourceType) {
        if (resourceName == null || this.bridge.getActivity() == null) return null;
        int resId = this.bridge.getActivity().getResources().getIdentifier(resourceName, resourceType, this.bridge.getActivity().getPackageName());
        return resId == 0 ? null : resId;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);

    // Get the previously saved call
        PluginCall savedCall = getSavedCall();

        if (savedCall == null) {
            return;
        }

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                PluginCall call = getSavedCall();
                DocumentDetectorResult mDocumentDetectorResult = (DocumentDetectorResult) data.getSerializableExtra(DocumentDetectorResult.PARAMETER_NAME);
                if (mDocumentDetectorResult.wasSuccessful()) {
                        call.success(getSucessResponseMap(mDocumentDetectorResult));
                } else {
                        //call.success(mDocumentDetectorResult.getSdkFailure());
                }
            } else {
                    //call.success(getClosedResponseMap());
            }
        }
    }

    private JSObject getSucessResponseMap(DocumentDetectorResult mDocumentDetectorResult) {
        ArrayList<Map> responseList = new ArrayList<>();
        Map<String,Object> responseMap =  new HashMap<String, Object>();
        responseMap.put("success", Boolean.TRUE.toString());

        ArrayList<HashMap<String, String>> captures = new ArrayList<>();
        for (Capture capture : mDocumentDetectorResult.getCaptures()) {
            HashMap<String, String> captureResponse = new HashMap<>();
            captureResponse.put("imagePath", capture.getImagePath());
            captureResponse.put("imageUrl", capture.getImageUrl());
            captureResponse.put("label", capture.getLabel());
            captureResponse.put("quality", capture.getQuality().toString());
            captures.add(captureResponse);
        }
        responseMap.put("captures", captures);
        responseMap.put("type", mDocumentDetectorResult.getType());
        responseMap.put("trackingId", mDocumentDetectorResult.getTrackingId());
        responseList.add(responseMap);

        JSONArray jsonArray = new JSONArray(responseList);
        JSObject result = new JSObject();
        result.put("results", jsonArray);
        return result;
    }

    private HashMap<String, Object> getFailureResponseMap(SDKFailure sdkFailure) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", Boolean.FALSE);
        responseMap.put("message", sdkFailure.getMessage());
        responseMap.put("type", sdkFailure.getClass().getSimpleName());
        return responseMap;
    }

    private HashMap<String, Object> getClosedResponseMap() {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", null);
        return responseMap;
    }

}