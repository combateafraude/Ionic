package com.passive;

import android.app.Activity;
import android.content.Intent;

import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.combateafraude.passivefaceliveness.controller.server.utils.CaptureMode;
import com.combateafraude.passivefaceliveness.input.PassiveFaceLiveness;
import com.combateafraude.passivefaceliveness.input.PreviewSettings;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import com.combateafraude.passivefaceliveness.PassiveFaceLivenessActivity;
import com.combateafraude.passivefaceliveness.input.CaptureSettings;
import com.combateafraude.passivefaceliveness.input.SensorStabilitySettings;
import com.combateafraude.passivefaceliveness.input.SensorOrientationSettings;
import com.combateafraude.passivefaceliveness.output.PassiveFaceLivenessResult;
import com.combateafraude.passivefaceliveness.output.failure.SDKFailure;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.combateafraude.passivefaceliveness.input.MaskType;
import com.combateafraude.passivefaceliveness.input.VideoCapture;
import com.combateafraude.passivefaceliveness.input.ImageCapture;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CapacitorPlugin(requestCodes = { PassiveFaceLivenessPlugin.REQUEST_CODE })
public class PassiveFaceLivenessPlugin extends Plugin {
    protected static final int REQUEST_CODE = 1002;

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
        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }
        Map<String, Object> argumentsMap = jsonToMap(jsonObject);
        // Mobile token
        String mobileToken = (String) argumentsMap.get("mobileToken");

        PassiveFaceLiveness.Builder mPassiveFaceLivenessBuilder = new PassiveFaceLiveness.Builder(mobileToken);

        // People ID
        if(argumentsMap.get("peopleId") != null){
            String peopleId = (String) argumentsMap.get("peopleId");
            mPassiveFaceLivenessBuilder.setPersonId(peopleId);
        }

        // Person Name
        if(argumentsMap.get("personName") != null){
            String personName = (String) argumentsMap.get("personName");
            mPassiveFaceLivenessBuilder.setPersonName(personName);
        }

        // Person CPF
        if(argumentsMap.get("personCPF") != null){
            String personCPF = (String) argumentsMap.get("personCPF");
            mPassiveFaceLivenessBuilder.setPersonCPF(personCPF);
        }

        // Use Analytics
        Boolean useAnalytics = (Boolean) argumentsMap.get("useAnalytics");
        if (useAnalytics != null)
            mPassiveFaceLivenessBuilder.setAnalyticsSettings(useAnalytics);

        // Image URL expire time
        String expireTime = (String) argumentsMap.get("expireTime");
        if (expireTime != null) {
            mPassiveFaceLivenessBuilder.setGetImageUrlExpireTime(expireTime);
        }

        Boolean useOpenEyeValidation = (Boolean) argumentsMap.get("useOpenEyeValidation");
        if(useOpenEyeValidation != null){
            Double openEyesThreshold = (Double) argumentsMap.get("openEyesThreshold");
            mPassiveFaceLivenessBuilder.setEyesClosedSettings(useOpenEyeValidation, openEyesThreshold);
        }

        HashMap<String, Object> showPreview = (HashMap<String, Object>) argumentsMap.get("showPreview");
        if (showPreview != null) {
            String title = (String) showPreview.get("title");
            String subTitle = (String) showPreview.get("subTitle");
            String confirmLabel = (String) showPreview.get("confirmLabel");
            String retryLabel = (String) showPreview.get("retryLabel");
            boolean show = (boolean) showPreview.get("show");

            PreviewSettings previewSettings = new PreviewSettings(
                    true,
                    title,
                    subTitle,
                    confirmLabel,
                    retryLabel);

            mPassiveFaceLivenessBuilder.setPreviewSettings(previewSettings);
        }

        HashMap<String, Object> captureMode = (HashMap<String, Object>) argumentsMap.get("captureMode");
        if (captureMode != null) {
            // VideoCapture
            HashMap<String, Object> videoCapture = (HashMap<String, Object>) captureMode.get("videoCapture");
            if (videoCapture != null) {
                boolean use = (Boolean) videoCapture.get("use");

                if (use) {
                    if (videoCapture.get("time") != null) {
                        Integer time = (Integer) videoCapture.get("time");
                        mPassiveFaceLivenessBuilder.setCaptureSettings(new VideoCapture(time));
                    } else {
                        mPassiveFaceLivenessBuilder.setCaptureSettings(new VideoCapture());
                    }
                }
            }
            // ImageCapture
            HashMap<String, Object> imageCapture = (HashMap<String, Object>) captureMode.get("imageCapture");
            if (imageCapture != null) {
                boolean use = (Boolean) imageCapture.get("use");
                Integer afterPictureMillis = (Integer) imageCapture.get("afterPictureMillis");
                Integer beforePictureMillis = (Integer) imageCapture.get("beforePictureMillis");

                if (use) {
                    if (afterPictureMillis != null && beforePictureMillis != null) {
                        mPassiveFaceLivenessBuilder
                                .setCaptureSettings(new ImageCapture(afterPictureMillis, beforePictureMillis));
                    } else {
                        mPassiveFaceLivenessBuilder.setCaptureSettings(new ImageCapture());
                    }
                }
            }
        }

        // Android specific settings
        HashMap<String, Object> androidSettings = (HashMap<String, Object>) argumentsMap.get("androidSettings");
        if (androidSettings != null) {

            if (androidSettings.get("customization") != null) {
                // Layout customization
                HashMap<String, Object> customizationAndroid = (HashMap<String, Object>) androidSettings
                        .get("customization");
                if (customizationAndroid != null) {
                    Integer styleId = getResourceId((String) customizationAndroid.get("styleResIdName"), STYLE_RES);
                    if (styleId != null)
                        mPassiveFaceLivenessBuilder.setStyle(styleId);

                    Integer layoutId = getResourceId((String) customizationAndroid.get("layoutResIdName"), LAYOUT_RES);
                    Integer greenMaskId = getResourceId((String) customizationAndroid.get("greenMaskResIdName"),
                            DRAWABLE_RES);
                    Integer whiteMaskId = getResourceId((String) customizationAndroid.get("whiteMaskResIdName"),
                            DRAWABLE_RES);
                    Integer redMaskId = getResourceId((String) customizationAndroid.get("redMaskResIdName"),
                            DRAWABLE_RES);
                    mPassiveFaceLivenessBuilder.setLayout(layoutId);
                    mPassiveFaceLivenessBuilder.setMask(greenMaskId, whiteMaskId, redMaskId);

                    String maskType = (String) customizationAndroid.get("maskType");
                    switch (maskType) {
                        case "NONE":
                            mPassiveFaceLivenessBuilder.setMask(MaskType.NONE);
                            break;
                        default:
                            mPassiveFaceLivenessBuilder.setMask(MaskType.DEFAULT);
                            break;
                    }
                }

            }

            if (androidSettings.get("sensorSettings") != null) {

                // Sensor settings
                HashMap<String, Object> sensorSettings = (HashMap<String, Object>) androidSettings
                        .get("sensorSettings");
                if (sensorSettings != null) {
                    HashMap<String, Object> sensorStability = (HashMap<String, Object>) sensorSettings
                            .get("sensorStabilitySettings");
                    if (sensorStability != null) {
                        Integer stabilityStabledMillis = (Integer) sensorStability.get("stabilityStabledMillis");
                        Double stabilityThreshold = (Double) sensorStability.get("stabilityThreshold");
                        if (stabilityStabledMillis != null && stabilityThreshold != null) {
                            mPassiveFaceLivenessBuilder.setStabilitySensorSettings(
                                    new SensorStabilitySettings(stabilityStabledMillis, stabilityThreshold));
                        }
                    } else {
                        mPassiveFaceLivenessBuilder.setStabilitySensorSettings(null);
                    }

                    HashMap<String, Object> sensorOrientation = (HashMap<String, Object>) androidSettings
                            .get("sensorOrientationAndroid");
                    if (sensorOrientation != null) {
                        Integer messageResourceIdName = (Integer) sensorOrientation.get("messageResourceIdName");
                        Double stabilityThreshold = (Double) sensorOrientation.get("stabilityThreshold");
                        if (messageResourceIdName != null && stabilityThreshold != null) {
                            SensorOrientationSettings sensorOrientationSettings = new SensorOrientationSettings(
                                    stabilityThreshold);
                            mPassiveFaceLivenessBuilder.setOrientationSensorSettings(sensorOrientationSettings);
                        }
                    } else {
                        mPassiveFaceLivenessBuilder.setOrientationSensorSettings(null);
                    }

                }
            }

            if (androidSettings.get("showButtonTime") != null) {
                int showButtonTime = (int) androidSettings.get("showButtonTime");
                mPassiveFaceLivenessBuilder.setShowButtonTime(showButtonTime);
            }

            if (androidSettings.get("enableSwitchCameraButton") != null) {
                boolean enableSwitchCameraButton = (boolean) androidSettings.get("enableSwitchCameraButton");
                mPassiveFaceLivenessBuilder.enableSwitchCameraButton(enableSwitchCameraButton);
            }

            if (androidSettings.get("enableGoogleServices") != null) {
                boolean enableGoogleServices = (boolean) androidSettings.get("enableGoogleServices");
                mPassiveFaceLivenessBuilder.enableGoogleServices(enableGoogleServices);
            }

            if (androidSettings.get("useEmulator") != null) {
                boolean useEmulator = (boolean) androidSettings.get("useEmulator");
                mPassiveFaceLivenessBuilder.setUseEmulator(useEmulator);
            }

            if (androidSettings.get("useRoot") != null) {
                boolean useRoot = (boolean) androidSettings.get("useRoot");
                mPassiveFaceLivenessBuilder.setUseRoot(useRoot);
            }

            if (androidSettings.get("enableBrightnessIncrease") != null) {
                boolean enableBrightnessIncrease = (boolean) androidSettings.get("enableBrightnessIncrease");
                mPassiveFaceLivenessBuilder.enableBrightnessIncrease(enableBrightnessIncrease);
            }

            if(androidSettings.get("useDeveloperMode") != null){
                Boolean useDeveloperMode = (Boolean) androidSettings.get("useDeveloperMode");
                mPassiveFaceLivenessBuilder.setUseDeveloperMode(useDeveloperMode);
            }

            if(androidSettings.get("useAdb") != null){
                Boolean useAdb = (Boolean) androidSettings.get("useAdb");
                mPassiveFaceLivenessBuilder.setUseAdb(useAdb);
            }

            if(androidSettings.get("useDebug") != null){
                Boolean useDebug = (Boolean) androidSettings.get("useDebug");
                mPassiveFaceLivenessBuilder.setUseDebug(useDebug);
            }
        }

        // Sound settings
        Boolean enableSound = (Boolean) argumentsMap.get("enableSound");
        if (enableSound != null)
            mPassiveFaceLivenessBuilder.setAudioSettings(enableSound);

        Integer soundResId = getResourceId((String) argumentsMap.get("sound"), RAW_RES);
        if (soundResId != null)
            mPassiveFaceLivenessBuilder.setAudioSettings(soundResId);

        // CurrentStepDoneDelay
        Boolean showDelay = (Boolean) argumentsMap.get("showDelay");
        if (showDelay != null) {
            if (argumentsMap.get("delay") != null) {
                int delay = (int) argumentsMap.get("delay");
                mPassiveFaceLivenessBuilder.setCurrentStepDoneDelay(showDelay, delay);
            }
        }

        // Network settings
        Integer requestTimeout = (Integer) argumentsMap.get("requestTimeout");
        if (requestTimeout != null)
            mPassiveFaceLivenessBuilder.setNetworkSettings(requestTimeout);

        saveCall(call);

        Intent mIntent = new Intent(this.getContext(), PassiveFaceLivenessActivity.class);
        mIntent.putExtra(PassiveFaceLiveness.PARAMETER_NAME, mPassiveFaceLivenessBuilder.build());
        startActivityForResult(call, mIntent, "passiveFaceLivenessResult");
    }

    @ActivityCallback
    private void passiveFaceLivenessResult(PluginCall call, ActivityResult result) {
        int resultCode = result.getResultCode();

        // Get the previously saved call
        PluginCall savedCall = getSavedCall();

        if (savedCall == null) {
            return;
        }

        if (resultCode == Activity.RESULT_OK && result.getData() != null) {

            PassiveFaceLivenessResult mPassiveFaceLivenessResult = (PassiveFaceLivenessResult) result.getData()
                    .getSerializableExtra(PassiveFaceLivenessResult.PARAMETER_NAME);
            if (mPassiveFaceLivenessResult.wasSuccessful()) {
                call.resolve(getSucessResponseMap(mPassiveFaceLivenessResult));
            } else {
                call.resolve(getFailureResponseMap(mPassiveFaceLivenessResult.getSdkFailure()));
            }
        } else {
            call.resolve(getClosedResponseMap());
        }

    }

    private Integer getResourceId(@Nullable String resourceName, String resourceType) {
        if (resourceName == null || this.bridge.getActivity() == null)
            return null;
        int resId = this.bridge.getActivity().getResources().getIdentifier(resourceName, resourceType,
                this.bridge.getActivity().getPackageName());
        return resId == 0 ? null : resId;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    private JSObject getSucessResponseMap(PassiveFaceLivenessResult mPassiveFaceLivenessResult) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", Boolean.TRUE);
        responseMap.put("imagePath", mPassiveFaceLivenessResult.getImagePath());
        responseMap.put("imageUrl", mPassiveFaceLivenessResult.getImageUrl());
        responseMap.put("signedResponse", mPassiveFaceLivenessResult.getSignedResponse());
        responseMap.put("trackingId", mPassiveFaceLivenessResult.getTrackingId());
        responseMap.put("lensFacing", mPassiveFaceLivenessResult.getLensFacing());
        if(mPassiveFaceLivenessResult.getCapturePath() != null) responseMap.put("capturePath", mPassiveFaceLivenessResult.getCapturePath());
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
