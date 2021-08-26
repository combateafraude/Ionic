package com.passive;

import android.app.Activity;
import android.content.Intent;

import android.util.Log;

import com.combateafraude.passivefaceliveness.input.PassiveFaceLiveness;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import com.combateafraude.passivefaceliveness.PassiveFaceLivenessActivity;
import com.combateafraude.passivefaceliveness.input.CaptureSettings;
import com.combateafraude.passivefaceliveness.input.SensorStabilitySettings;
import com.combateafraude.passivefaceliveness.output.PassiveFaceLivenessResult;
import com.combateafraude.passivefaceliveness.output.failure.SDKFailure;
import com.combateafraude.passivefaceliveness.input.MessageSettings;

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
        requestCodes={PassiveFaceLivenessPlugin.REQUEST_CODE}
)
public class PassiveFaceLivenessPlugin extends Plugin {
    protected static final int REQUEST_CODE = 1001;

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
        // Mobile token
        String mobileToken = (String) argumentsMap.get("mobileToken");

        PassiveFaceLiveness.Builder mPassiveFaceLivenessBuilder = new PassiveFaceLiveness.Builder(mobileToken);

        // People ID
        String peopleId = (String) argumentsMap.get("peopleId");
        mPassiveFaceLivenessBuilder.setPersonId(peopleId);

        // Use Analytics
        Boolean useAnalytics = (Boolean) argumentsMap.get("useAnalytics");
        if (useAnalytics != null) mPassiveFaceLivenessBuilder.setAnalyticsSettings(useAnalytics);

        HashMap<String, Object> showPreview = (HashMap<String, Object>) argumentsMap.get("showPreview");
        if (showPreview != null) {
            Integer title = getResourceId((String) showPreview.get("title"), LAYOUT_RES);
            Integer subTitle = getResourceId((String) showPreview.get("subTitle"), LAYOUT_RES);
            Integer confirmLabel = getResourceId((String) showPreview.get("confirmLabel"), LAYOUT_RES);
            Integer retryLabel = getResourceId((String) showPreview.get("retryLabel"), LAYOUT_RES);
            boolean show = (boolean) showPreview.get("show");
            mPassiveFaceLivenessBuilder.showPreview(show, title, subTitle, confirmLabel, retryLabel);
        }

        HashMap<String, Object> messageSettingsParam = (HashMap<String, Object>) argumentsMap.get("messageSettings");
        if (messageSettingsParam != null) {
            Integer stepName = getResourceId((String) messageSettingsParam.get("stepName"), LAYOUT_RES);
            Integer faceNotFoundMessage = getResourceId((String) messageSettingsParam.get("faceNotFoundMessage"), LAYOUT_RES);
            Integer faceTooFarMessage = getResourceId((String) messageSettingsParam.get("faceTooFarMessage"), LAYOUT_RES);
            Integer faceTooCloseMessage = getResourceId((String) messageSettingsParam.get("faceTooCloseMessage"), LAYOUT_RES);
            Integer faceNotFittedMessage = getResourceId((String) messageSettingsParam.get("faceNotFittedMessage"), LAYOUT_RES);
            Integer multipleFaceDetectedMessage = getResourceId((String) messageSettingsParam.get("multipleFaceDetectedMessage"), LAYOUT_RES);
            Integer verifyingLivenessMessage = getResourceId((String) messageSettingsParam.get("verifyingLivenessMessage"), LAYOUT_RES);
            Integer holdItMessage = getResourceId((String) messageSettingsParam.get("holdItMessage"), LAYOUT_RES);
            Integer invalidFaceMessage = getResourceId((String) messageSettingsParam.get("invalidFaceMessage"), LAYOUT_RES);

            MessageSettings messageSettings = new MessageSettings();
            if (stepName != null)
                messageSettings.setStepName(stepName);
            if (faceNotFoundMessage != null)
                messageSettings.setFaceNotFoundMessage(faceNotFoundMessage);
            if (faceTooFarMessage != null)
                messageSettings.setFaceTooFarMessage(faceTooFarMessage);
            if (faceTooCloseMessage != null)
                messageSettings.setFaceTooCloseMessage(faceTooCloseMessage);
            if (faceNotFittedMessage != null)
                messageSettings.setFaceNotFittedMessage(faceNotFittedMessage);
            if (multipleFaceDetectedMessage != null)
                messageSettings.setMultipleFaceDetectedMessage(multipleFaceDetectedMessage);
            if (verifyingLivenessMessage != null)
                messageSettings.setVerifyingLivenessMessage(verifyingLivenessMessage);
            if (holdItMessage != null)
                messageSettings.setHoldItMessage(holdItMessage);
            if (invalidFaceMessage != null)
                messageSettings.setInvalidFaceMessage(invalidFaceMessage);

            mPassiveFaceLivenessBuilder.setMessageSettings(messageSettings);
        }


        // Android specific settings
        HashMap<String, Object> androidSettings = (HashMap<String, Object>) argumentsMap.get("androidSettings");
        if (androidSettings != null) {

            // Layout customization
            HashMap<String, Object> customizationAndroid = (HashMap<String, Object>) androidSettings.get("customization");
            if (customizationAndroid != null) {
                Integer styleId = getResourceId((String) customizationAndroid.get("styleResIdName"), STYLE_RES);
                if (styleId != null) mPassiveFaceLivenessBuilder.setStyle(styleId);

                Integer layoutId = getResourceId((String) customizationAndroid.get("layoutResIdName"), LAYOUT_RES);
                Integer greenMaskId = getResourceId((String) customizationAndroid.get("greenMaskResIdName"), DRAWABLE_RES);
                Integer whiteMaskId = getResourceId((String) customizationAndroid.get("whiteMaskResIdName"), DRAWABLE_RES);
                Integer redMaskId = getResourceId((String) customizationAndroid.get("redMaskResIdName"), DRAWABLE_RES);
                mPassiveFaceLivenessBuilder.setLayout(layoutId, greenMaskId, whiteMaskId, redMaskId);
            }

            // Sensor settings
            HashMap<String, Object> sensorSettings = (HashMap<String, Object>) androidSettings.get("sensorSettings");
            if (sensorSettings != null) {
                HashMap<String, Object> sensorStability = (HashMap<String, Object>) sensorSettings.get("sensorStabilitySettings");
                if (sensorStability != null) {
                    Integer sensorMessageId = getResourceId((String) sensorStability.get("messageResourceIdName"), STRING_RES);
                    Integer stabilityStabledMillis = (Integer) sensorStability.get("stabilityStabledMillis");
                    Double stabilityThreshold = (Double) sensorStability.get("stabilityThreshold");
                    if (sensorMessageId != null && stabilityStabledMillis != null && stabilityThreshold != null) {
                        mPassiveFaceLivenessBuilder.setStabilitySensorSettings(new SensorStabilitySettings(sensorMessageId, stabilityStabledMillis, stabilityThreshold));
                    }
                } else {
                    mPassiveFaceLivenessBuilder.setStabilitySensorSettings(null);
                }
            }

            // Capture settings
            HashMap<String, Object> captureSettings = (HashMap<String, Object>) androidSettings.get("captureSettings");
            if (captureSettings != null) {
                Integer beforePictureMillis = (Integer) captureSettings.get("beforePictureMillis");
                Integer afterPictureMillis = (Integer) captureSettings.get("afterPictureMillis");
                if (beforePictureMillis != null && afterPictureMillis != null) {
                    mPassiveFaceLivenessBuilder.setCaptureSettings(new CaptureSettings(beforePictureMillis, afterPictureMillis));
                }
            }

            if (androidSettings.get("showButtonTime") != null){
                int showButtonTime = (int) androidSettings.get("showButtonTime");
                mPassiveFaceLivenessBuilder.setShowButtonTime(showButtonTime);
            }
        }

        // Sound settings
        Boolean enableSound = (Boolean) argumentsMap.get("sound");
        if (enableSound != null) mPassiveFaceLivenessBuilder.enableSound(enableSound);

        //CurrentStepDoneDelay
        Boolean showDelay = (Boolean) argumentsMap.get("showDelay");
        if(showDelay != null){
            if(argumentsMap.get("delay") != null) {
                int delay = (int) argumentsMap.get("delay");
                mPassiveFaceLivenessBuilder.setCurrentStepDoneDelay(showDelay, delay);
            }
        }

        // Network settings
        Integer requestTimeout = (Integer) argumentsMap.get("requestTimeout");
        if (requestTimeout != null) mPassiveFaceLivenessBuilder.setNetworkSettings(requestTimeout);

        saveCall(call);

        Intent mIntent = new Intent(this.getContext(), PassiveFaceLivenessActivity.class);
        mIntent.putExtra(PassiveFaceLiveness.PARAMETER_NAME, mPassiveFaceLivenessBuilder.build());
        startActivityForResult(call, mIntent, REQUEST_CODE);
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
            PluginCall call = getSavedCall();
            if (resultCode == Activity.RESULT_OK && data != null) {
                
                PassiveFaceLivenessResult mPassiveFaceLivenessResult = (PassiveFaceLivenessResult) data.getSerializableExtra(PassiveFaceLivenessResult.PARAMETER_NAME);
                if (mPassiveFaceLivenessResult.wasSuccessful()) {
                        call.success(getSucessResponseMap(mPassiveFaceLivenessResult));
                } else {
                        call.success(getFailureResponseMap(mPassiveFaceLivenessResult.getSdkFailure()));
                }
            } else {
                    call.success(getClosedResponseMap());
            }
        }
    }

    private JSObject getSucessResponseMap(PassiveFaceLivenessResult mPassiveFaceLivenessResult) {
        Map<String,Object> responseMap =  new HashMap<String, Object>();
        responseMap.put("success", Boolean.TRUE);

        responseMap.put("imagePath", mPassiveFaceLivenessResult.getImagePath());
        responseMap.put("imageUrl", mPassiveFaceLivenessResult.getImageUrl());
        responseMap.put("signedResponse", mPassiveFaceLivenessResult.getSignedResponse());
        responseMap.put("trackingId", mPassiveFaceLivenessResult.getTrackingId());

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
