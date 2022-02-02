package com.passive;

import android.app.Activity;
import android.content.Intent;

import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.combateafraude.passivefaceliveness.input.PassiveFaceLiveness;
import com.combateafraude.passivefaceliveness.input.PreviewSettings;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import com.combateafraude.passivefaceliveness.PassiveFaceLivenessActivity;
import com.combateafraude.passivefaceliveness.input.CaptureSettings;
import com.combateafraude.passivefaceliveness.input.SensorStabilitySettings;
import com.combateafraude.passivefaceliveness.output.PassiveFaceLivenessResult;
import com.combateafraude.passivefaceliveness.output.failure.SDKFailure;
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

@CapacitorPlugin(
        requestCodes={PassiveFaceLivenessPlugin.REQUEST_CODE}
)
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
                    retryLabel
            );

            mPassiveFaceLivenessBuilder.setPreviewSettings(previewSettings);
        }

        // Android specific settings
        HashMap<String, Object> androidSettings = (HashMap<String, Object>) argumentsMap.get("androidSettings");
        if (androidSettings != null) {

            if(androidSettings.get("customization") != null){
                // Layout customization
                HashMap<String, Object> customizationAndroid = (HashMap<String, Object>) androidSettings.get("customization");
                if (customizationAndroid != null) {
                    Integer styleId = getResourceId((String) customizationAndroid.get("styleResIdName"), STYLE_RES);
                    if (styleId != null) mPassiveFaceLivenessBuilder.setStyle(styleId);

                    Integer layoutId = getResourceId((String) customizationAndroid.get("layoutResIdName"), LAYOUT_RES);
                    Integer greenMaskId = getResourceId((String) customizationAndroid.get("greenMaskResIdName"), DRAWABLE_RES);
                    Integer whiteMaskId = getResourceId((String) customizationAndroid.get("whiteMaskResIdName"), DRAWABLE_RES);
                    Integer redMaskId = getResourceId((String) customizationAndroid.get("redMaskResIdName"), DRAWABLE_RES);
                    mPassiveFaceLivenessBuilder.setLayout(layoutId);
                    mPassiveFaceLivenessBuilder.setMask(greenMaskId, whiteMaskId, redMaskId);
                }

            }

            if(androidSettings.get("sensorSettings") != null){

                // Sensor settings
                HashMap<String, Object> sensorSettings = (HashMap<String, Object>) androidSettings.get("sensorSettings");
                if (sensorSettings != null) {
                    HashMap<String, Object> sensorStability = (HashMap<String, Object>) sensorSettings.get("sensorStabilitySettings");
                    if (sensorStability != null) {
                        Integer stabilityStabledMillis = (Integer) sensorStability.get("stabilityStabledMillis");
                        Double stabilityThreshold = (Double) sensorStability.get("stabilityThreshold");
                        if (stabilityStabledMillis != null && stabilityThreshold != null) {
                            mPassiveFaceLivenessBuilder.setStabilitySensorSettings(new SensorStabilitySettings(stabilityStabledMillis, stabilityThreshold));
                        }
                    } else {
                        mPassiveFaceLivenessBuilder.setStabilitySensorSettings(null);
                    }
                }
            }

            if (androidSettings.get("captureSettings") != null){
                // Capture settings
                HashMap<String, Object> captureSettings = (HashMap<String, Object>) androidSettings.get("captureSettings");
                if (captureSettings != null) {
                    Integer beforePictureMillis = (Integer) captureSettings.get("beforePictureMillis");
                    Integer afterPictureMillis = (Integer) captureSettings.get("afterPictureMillis");
                    if (beforePictureMillis != null && afterPictureMillis != null) {
                        mPassiveFaceLivenessBuilder.setCaptureSettings(new CaptureSettings(beforePictureMillis, afterPictureMillis));
                    }
                }
            }

            if (androidSettings.get("showButtonTime") != null){
                int showButtonTime = (int) androidSettings.get("showButtonTime");
                mPassiveFaceLivenessBuilder.setShowButtonTime(showButtonTime);
            }


            if (androidSettings.get("enableSwitchCameraButton") != null){
                boolean enableSwitchCameraButton = (boolean) androidSettings.get("enableSwitchCameraButton");
                mPassiveFaceLivenessBuilder.enableSwitchCameraButton(enableSwitchCameraButton);
            }

            if (androidSettings.get("enableGoogleServices") != null){
                boolean enableGoogleServices = (boolean) androidSettings.get("enableGoogleServices");
                mPassiveFaceLivenessBuilder.enableGoogleServices(enableGoogleServices);
            }

            if (androidSettings.get("useEmulator") != null){
                boolean useEmulator = (boolean) androidSettings.get("useEmulator");
                mPassiveFaceLivenessBuilder.setUseEmulator(useEmulator);
            }

            if (androidSettings.get("useRoot") != null){
                boolean useRoot = (boolean) androidSettings.get("useRoot");
                mPassiveFaceLivenessBuilder.setUseRoot(useRoot);
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

            PassiveFaceLivenessResult mPassiveFaceLivenessResult = (PassiveFaceLivenessResult) result.getData().getSerializableExtra(PassiveFaceLivenessResult.PARAMETER_NAME);
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


    private JSObject getSucessResponseMap(PassiveFaceLivenessResult mPassiveFaceLivenessResult) {
        HashMap<String, Object> responseMap = new HashMap<>();
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
