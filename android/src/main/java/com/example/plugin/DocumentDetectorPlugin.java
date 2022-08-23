package com.example.plugin;


import android.app.Activity;
import android.content.Intent;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.combateafraude.documentdetector.DocumentDetectorActivity;
import com.combateafraude.documentdetector.input.CaptureMode;
import com.combateafraude.documentdetector.input.CaptureStage;
import com.combateafraude.documentdetector.input.DetectionSettings;
import com.combateafraude.documentdetector.input.Document;
import com.combateafraude.documentdetector.input.DocumentDetector;
import com.combateafraude.documentdetector.input.DocumentDetectorStep;
import com.combateafraude.documentdetector.input.FileFormat;
import com.combateafraude.documentdetector.input.PreviewSettings;
import com.combateafraude.documentdetector.input.QualitySettings;
import com.combateafraude.documentdetector.input.Resolution;
import com.combateafraude.documentdetector.input.SensorLuminositySettings;
import com.combateafraude.documentdetector.input.SensorOrientationSettings;
import com.combateafraude.documentdetector.input.SensorStabilitySettings;
import com.combateafraude.documentdetector.input.UploadSettings;
import com.combateafraude.documentdetector.output.Capture;
import com.combateafraude.documentdetector.output.DocumentDetectorResult;
import com.combateafraude.documentdetector.output.failure.SDKFailure;
import com.combateafraude.documentdetector.input.MaskType;


import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CapacitorPlugin()
public class DocumentDetectorPlugin extends Plugin {

    private static final String DRAWABLE_RES = "drawable";
    private static final String STYLE_RES = "style";
    private static final String STRING_RES = "string";
    private static final String RAW_RES = "raw";
    private static final String LAYOUT_RES = "layout";

    @PluginMethod()
    public void start(PluginCall call) throws JSONException {
        saveCall(call);

        String value = call.getString("builder");

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
        mDocumentDetectorBuilder.setPersonId(peopleId);

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
            String title = (String) showPreview.get("title");
            String subTitle = (String) showPreview.get("subTitle");
            String confirmLabel = (String) showPreview.get("confirmLabel");
            String retryLabel = (String) showPreview.get("retryLabel");
            boolean show = (boolean) showPreview.get("show");

            PreviewSettings previewSettings = new PreviewSettings(
                    show,
                    title,
                    subTitle,
                    confirmLabel,
                    retryLabel
            );

            mDocumentDetectorBuilder.setPreviewSettings(previewSettings);
        }


        HashMap<String, Object> uploadSettingsParam = (HashMap<String, Object>) argumentsMap.get("uploadSettings");
        if(uploadSettingsParam != null){
            UploadSettings uploadSettings = new UploadSettings();

            Integer activityLayout = getResourceId((String) uploadSettingsParam.get("activityLayout"), LAYOUT_RES);
            Integer popUpLayout = getResourceId((String) uploadSettingsParam.get("popUpLayout"), LAYOUT_RES);
            Boolean compress = (Boolean) uploadSettingsParam.get("compress");
            ArrayList<String> fileFormatsParam = (ArrayList<String>) uploadSettingsParam.get("fileFormats");
            Integer maxFileSize = (Integer) uploadSettingsParam.get("maxFileSize");
            String intent = (String) uploadSettingsParam.get("intent");

            if(compress != null){
                uploadSettings.setCompress(compress);
            }    
            if(intent != null){
                uploadSettings.setIntent(intent);
            }
            if(maxFileSize != null){
                uploadSettings.setMaxFileSize(maxFileSize);
            }
            if(popUpLayout != null){
                uploadSettings.setPopUpLayout(popUpLayout);
            }
            if(activityLayout != null){
                uploadSettings.setActivityLayout(activityLayout);
            }
            if (fileFormatsParam != null){
                FileFormat[] fileFormats = new FileFormat[fileFormatsParam.size()];
                for (int i = 0; i < fileFormats.length; i++ ) {
                    FileFormat fileFormat = FileFormat.valueOf(fileFormatsParam.get(i));
                    if (fileFormat != null) fileFormats[i] = fileFormat;
                }
                uploadSettings.setFileFormats(fileFormats);
            }
        
            
            mDocumentDetectorBuilder.setUploadSettings(uploadSettings);
        }


        // Android specific settings
        HashMap<String, Object> androidSettings = (HashMap<String, Object>) argumentsMap.get("androidSettings");
        if (androidSettings != null) {

            Object stages = androidSettings.get("captureStages");
            if (stages != null) {

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
                            if (threshold == null)
                                threshold = QualitySettings.RECOMMENDED_THRESHOLD;
                            qualitySettings = new QualitySettings(threshold);
                        }

                        DetectionSettings detectionSettings = null;
                        HashMap<String, Object> detectionSettingsParam = (HashMap<String, Object>) stage.get("detectionSettings");
                        if (detectionSettingsParam != null) {
                            Double threshold = (Double) detectionSettingsParam.get("threshold");
                            if (threshold == null)
                                threshold = DetectionSettings.RECOMMENDED_THRESHOLD;
                            Integer consecutiveFrames = (Integer) detectionSettingsParam.get("consecutiveFrames");
                            if (consecutiveFrames == null) consecutiveFrames = 5;
                            detectionSettings = new DetectionSettings(threshold, consecutiveFrames);
                        }
                        CaptureMode captureMode = CaptureMode.valueOf((String) stage.get("captureMode"));

                        captureStages[i] = new CaptureStage(durationMillis, wantSensorCheck, qualitySettings, detectionSettings, captureMode);
                    }
                    mDocumentDetectorBuilder.setCaptureStages(captureStages);
                }
            }

           if(androidSettings.get("enableSwitchCameraButton") != null){
                boolean enableSwitchCameraButton = (boolean) androidSettings.get("enableSwitchCameraButton");
                mDocumentDetectorBuilder.enableSwitchCameraButton(enableSwitchCameraButton);
            }

            if(androidSettings.get("compressQuality") != null){
                int compressQuality = (int) androidSettings.get("compressQuality");
                mDocumentDetectorBuilder.setCompressSettings(compressQuality);
            }

            if (androidSettings.get("resolution") != null){
                String resolution = (String) androidSettings.get("resolution");
                mDocumentDetectorBuilder.setResolutionSettings(Resolution.valueOf(resolution));
            }

            if (androidSettings.get("useEmulator") != null){
                Boolean useEmulator = (Boolean) androidSettings.get("useEmulator");
                mDocumentDetectorBuilder.setUseEmulator(useEmulator);
            }

           if(androidSettings.get("useRoot") != null){
                Boolean useRoot = (Boolean) androidSettings.get("useRoot");
                mDocumentDetectorBuilder.setUseRoot(useRoot);
            }

            if(androidSettings.get("useDeveloperMode") != null){
                Boolean useDeveloperMode = (Boolean) androidSettings.get("useDeveloperMode");
                mDocumentDetectorBuilder.setUseDeveloperMode(useDeveloperMode);
            }

            if(androidSettings.get("useAdb") != null){
                Boolean useAdb = (Boolean) androidSettings.get("useAdb");
                mDocumentDetectorBuilder.setUseAdb(useAdb);
            }
    
            if(androidSettings.get("useDebug") != null){
                Boolean useDebug = (Boolean) androidSettings.get("useDebug");
                mDocumentDetectorBuilder.setUseDebug(useDebug);
            }

            if(androidSettings.get("useGoogleServices") != null){
                Boolean useGoogleServices = (Boolean) androidSettings.get("useGoogleServices");
                mDocumentDetectorBuilder.enableGoogleServices(useGoogleServices);
            }

            // Layout customization
            if (androidSettings.get("customization") != null){
                HashMap<String, Object> customizationAndroid = (HashMap<String, Object>) androidSettings.get("customization");
                Integer styleId = getResourceId((String) customizationAndroid.get("styleResIdName"), STYLE_RES);
                if (styleId != null) mDocumentDetectorBuilder.setStyle(styleId);

                Integer layoutId = getResourceId((String) customizationAndroid.get("layoutResIdName"), LAYOUT_RES);
                Integer greenMaskId = getResourceId((String) customizationAndroid.get("greenMaskResIdName"), DRAWABLE_RES);
                Integer whiteMaskId = getResourceId((String) customizationAndroid.get("whiteMaskResIdName"), DRAWABLE_RES);
                Integer redMaskId = getResourceId((String) customizationAndroid.get("redMaskResIdName"), DRAWABLE_RES);
                mDocumentDetectorBuilder.setLayout(layoutId);
                mDocumentDetectorBuilder.setMask(greenMaskId, whiteMaskId, redMaskId);

                String maskType = (String) customizationAndroid.get("maskType");
                switch (maskType) {
                    case "DETAILED":
                        mDocumentDetectorBuilder.setMask(MaskType.DETAILED);
                        break;
                    case "NONE":
                        mDocumentDetectorBuilder.setMask(MaskType.NONE);
                        break;
                    default:
                        mDocumentDetectorBuilder.setMask(MaskType.DEFAULT);
                        break;
                }

            }


            // Sensor settings
            if (androidSettings.get("sensorSettings") != null){
                HashMap<String, Object> sensorSettings = (HashMap<String, Object>) androidSettings.get("sensorSettings");
                HashMap<String, Object> sensorLuminosity = (HashMap<String, Object>) sensorSettings.get("sensorLuminositySettings");
                if (sensorLuminosity != null) {
                    Integer luminosityThreshold = (Integer) sensorLuminosity.get("luminosityThreshold");
                    if (luminosityThreshold != null) {
                        mDocumentDetectorBuilder.setLuminositySensorSettings(new SensorLuminositySettings(luminosityThreshold));
                    }
                } else {
                    mDocumentDetectorBuilder.setLuminositySensorSettings(null);
                }

                HashMap<String, Object> sensorOrientation = (HashMap<String, Object>) sensorSettings.get("sensorOrientationSettings");
                if (sensorOrientation != null) {
                    Double orientationThreshold = (Double) sensorOrientation.get("orientationThreshold");
                    if (orientationThreshold != null) {
                        mDocumentDetectorBuilder.setOrientationSensorSettings(new SensorOrientationSettings(orientationThreshold));
                    }
                } else {
                    mDocumentDetectorBuilder.setOrientationSensorSettings(null);
                }

                HashMap<String, Object> sensorStability = (HashMap<String, Object>) sensorSettings.get("sensorStabilitySettings");
                if (sensorStability != null) {
                    Integer stabilityStabledMillis = (Integer) sensorStability.get("stabilityStabledMillis");
                    Double stabilityThreshold = (Double) sensorStability.get("stabilityThreshold");
                    if (stabilityStabledMillis != null && stabilityThreshold != null) {
                        mDocumentDetectorBuilder.setStabilitySensorSettings(new SensorStabilitySettings(stabilityStabledMillis, stabilityThreshold));
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
        if (enableSound != null)
            mDocumentDetectorBuilder.setAudioSettings(enableSound);

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
        startActivityForResult(call, mIntent, "documentDetectorResult");
    }

    @ActivityCallback
    private void documentDetectorResult(PluginCall call, ActivityResult result) {

        if (call == null) {
            return;
        }

        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {

            DocumentDetectorResult mDocumentDetectorResult = (DocumentDetectorResult) result.getData().getSerializableExtra(DocumentDetectorResult.PARAMETER_NAME);
            if (mDocumentDetectorResult.wasSuccessful()) {
                call.resolve(getSucessResponseMap(mDocumentDetectorResult));
            } else {
                call.resolve(getFailureResponseMap(mDocumentDetectorResult.getSdkFailure()));
            }
        } else {
            call.resolve(getClosedResponseMap());
        }
    }


    private Integer getResourceId(@Nullable String resourceName, String resourceType) {
        if (resourceName == null || this.bridge.getActivity() == null) return null;
        int resId = this.bridge.getActivity().getResources().getIdentifier(resourceName, resourceType, this.bridge.getActivity().getPackageName());
        return resId == 0 ? null : resId;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();

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


    }

    private JSObject getSucessResponseMap(DocumentDetectorResult mDocumentDetectorResult) {
        Map<String,Object> responseMap =  new HashMap<String, Object>();
        responseMap.put("success", Boolean.TRUE);

        ArrayList<HashMap<String, String>> captures = new ArrayList<>();
        for (Capture capture : mDocumentDetectorResult.getCaptures()) {
            HashMap<String, String> captureResponse = new HashMap<>();
            captureResponse.put("imagePath", capture.getImagePath());
            captureResponse.put("imageUrl", capture.getImageUrl());
            captureResponse.put("label", capture.getLabel());
            captureResponse.put("lensFacing", Integer.toString(capture.getLensFacing()));
            if(capture.getQuality() != null)
                captureResponse.put("quality", capture.getQuality().toString());
            captures.add(captureResponse);
        }
        responseMap.put("captures", captures);
        responseMap.put("type", mDocumentDetectorResult.getType());
        responseMap.put("trackingId", mDocumentDetectorResult.getTrackingId());

        JSONObject jsonObject = new JSONObject(responseMap);
        JSObject result = new JSObject();
        result.put("results", jsonObject);
        return result;
    }

    private JSObject getFailureResponseMap(SDKFailure sdkFailure) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", Boolean.FALSE);
        responseMap.put("message", sdkFailure.getMessage());
        responseMap.put("type", sdkFailure.getClass().getSimpleName());

        JSONObject jsonObject = new JSONObject(responseMap);
        JSObject result = new JSObject();
        result.put("results", jsonObject);
        return result;
    }

    private JSObject getClosedResponseMap() {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", null);
        JSONObject jsonObject = new JSONObject(responseMap);
        JSObject result = new JSObject();
        result.put("results", jsonObject);
        return result;
    }

}