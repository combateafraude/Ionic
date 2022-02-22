package com.passive;

import android.app.Activity;
import android.content.Intent;

import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.combateafraude.faceauthenticator.input.FaceAuthenticator;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import com.combateafraude.faceauthenticator.FaceAuthenticatorActivity;
import com.combateafraude.faceauthenticator.input.SensorStabilitySettings;
import com.combateafraude.faceauthenticator.output.FaceAuthenticatorResult;
import com.combateafraude.faceauthenticator.output.failure.SDKFailure;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.combateafraude.faceauthenticator.input.VideoCapture;
import com.combateafraude.faceauthenticator.input.ImageCapture;
import com.passive.passivefacelivenessplugin.R;

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
        requestCodes={FaceAuthenticatorPlugin.REQUEST_CODE}
)
public class FaceAuthenticatorPlugin extends Plugin {
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

        FaceAuthenticator.Builder mFaceAuthenticatorBuilder = new FaceAuthenticator.Builder(mobileToken);

        // People ID
        String peopleId = (String) argumentsMap.get("peopleId");
        mFaceAuthenticatorBuilder.setPeopleId(peopleId);

        // Use Analytics
        Boolean useAnalytics = (Boolean) argumentsMap.get("useAnalytics");
        if (useAnalytics != null) mFaceAuthenticatorBuilder.setAnalyticsSettings(useAnalytics);

        HashMap<String, Object> captureMode = (HashMap<String, Object>) argumentsMap.get("captureMode");
        if (captureMode != null){
            //VideoCapture
            HashMap<String, Object> videoCapture = (HashMap<String, Object>) captureMode.get("videoCapture");
            if(videoCapture != null){
                boolean use = (Boolean) videoCapture.get("use");

                if(use){
                    if(videoCapture.get("time") != null){
                        Integer time = (Integer) videoCapture.get("time");
                        mFaceAuthenticatorBuilder.setCaptureSettings(new VideoCapture(time));
                    }else{
                        mFaceAuthenticatorBuilder.setCaptureSettings(new VideoCapture());
                    }
                }
            }
            //ImageCapture
            HashMap<String, Object> imageCapture = (HashMap<String, Object>) captureMode.get("imageCapture");
            if(imageCapture != null){
                boolean use = (Boolean) imageCapture.get("use");
                Integer afterPictureMillis = (Integer) imageCapture.get("afterPictureMillis");
                Integer beforePictureMillis = (Integer) imageCapture.get("beforePictureMillis");

                if(use){
                    if(afterPictureMillis != null && beforePictureMillis != null){
                        mFaceAuthenticatorBuilder.setCaptureSettings(new ImageCapture(afterPictureMillis, beforePictureMillis));
                    }else{
                        mFaceAuthenticatorBuilder.setCaptureSettings(new ImageCapture());
                    }
                }
            }
        }

        // Android specific settings
        HashMap<String, Object> androidSettings = (HashMap<String, Object>) argumentsMap.get("androidSettings");
        if (androidSettings != null) {

            if(androidSettings.get("customization") != null){
                // Layout customization
                HashMap<String, Object> customizationAndroid = (HashMap<String, Object>) androidSettings.get("customization");
                if (customizationAndroid != null) {
                    Integer styleId = getResourceId((String) customizationAndroid.get("styleResIdName"), STYLE_RES);
                    if (styleId != null) mFaceAuthenticatorBuilder.setStyle(styleId);

                    Integer layoutId = getResourceId((String) customizationAndroid.get("layoutResIdName"), LAYOUT_RES);
                    Integer greenMaskId = getResourceId((String) customizationAndroid.get("greenMaskResIdName"), DRAWABLE_RES);
                    Integer whiteMaskId = getResourceId((String) customizationAndroid.get("whiteMaskResIdName"), DRAWABLE_RES);
                    Integer redMaskId = getResourceId((String) customizationAndroid.get("redMaskResIdName"), DRAWABLE_RES);
                    mFaceAuthenticatorBuilder.setLayout(layoutId);
                    mFaceAuthenticatorBuilder.setMask(greenMaskId, whiteMaskId, redMaskId);
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
                            mFaceAuthenticatorBuilder.setStabilitySensorSettings(new SensorStabilitySettings(R.string.error_100_message,stabilityStabledMillis, stabilityThreshold));
                        }
                    } else {
                        mFaceAuthenticatorBuilder.setStabilitySensorSettings(null);
                    }
                }
            }

            if (androidSettings.get("enableSwitchCameraButton") != null){
                boolean enableSwitchCameraButton = (boolean) androidSettings.get("enableSwitchCameraButton");
                mFaceAuthenticatorBuilder.enableSwitchCameraButton(enableSwitchCameraButton);
            }

            if (androidSettings.get("enableGoogleServices") != null){
                boolean enableGoogleServices = (boolean) androidSettings.get("enableGoogleServices");
                mFaceAuthenticatorBuilder.enableGoogleServices(enableGoogleServices);
            }

            if (androidSettings.get("useEmulator") != null){
                boolean useEmulator = (boolean) androidSettings.get("useEmulator");
                mFaceAuthenticatorBuilder.setUseEmulator(useEmulator);
            }

            if (androidSettings.get("useRoot") != null){
                boolean useRoot = (boolean) androidSettings.get("useRoot");
                mFaceAuthenticatorBuilder.setUseRoot(useRoot);
            }

        }

        // Sound settings
        Boolean enableSound = (Boolean) argumentsMap.get("sound");
        if (enableSound != null) mFaceAuthenticatorBuilder.enableSound(enableSound);

        // Network settings
        Integer requestTimeout = (Integer) argumentsMap.get("requestTimeout");
        if (requestTimeout != null) mFaceAuthenticatorBuilder.setNetworkSettings(requestTimeout);

        saveCall(call);

        Intent mIntent = new Intent(this.getContext(), FaceAuthenticatorActivity.class);
        mIntent.putExtra(FaceAuthenticator.PARAMETER_NAME, mFaceAuthenticatorBuilder.build());
        startActivityForResult(call, mIntent, "faceAuthenticatorResult");
    }

    @ActivityCallback
    private void faceAuthenticatorResult(PluginCall call, ActivityResult result) {
        int resultCode = result.getResultCode();

        // Get the previously saved call
        PluginCall savedCall = getSavedCall();

        if (savedCall == null) {
            return;
        }

        if (resultCode == Activity.RESULT_OK && result.getData() != null) {

            FaceAuthenticatorResult mFaceAuthenticatorResult = (FaceAuthenticatorResult) result.getData().getSerializableExtra(FaceAuthenticatorResult.PARAMETER_NAME);
            if (mFaceAuthenticatorResult.wasSuccessful()) {
                call.resolve(getSucessResponseMap(mFaceAuthenticatorResult));
            } else {
                call.resolve(getFailureResponseMap(mFaceAuthenticatorResult.getSdkFailure()));
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


    private JSObject getSucessResponseMap(FaceAuthenticatorResult mFaceAuthenticatorResult) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", Boolean.TRUE);
        responseMap.put("isAuthenticated", mFaceAuthenticatorResult.isAuthenticated());
        responseMap.put("signedResponse", mFaceAuthenticatorResult.getSignedResponse());
        responseMap.put("trackingId", mFaceAuthenticatorResult.getTrackingId());
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
