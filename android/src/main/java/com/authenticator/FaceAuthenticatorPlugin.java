package com.authenticator;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import input.FaceAuthenticator;
import input.VerifyAuthenticationListener;
import output.FaceAuthenticatorResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CapacitorPlugin()
public class FaceAuthenticatorPlugin extends Plugin {

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
        

        String mobileToken = (String) argumentsMap.get("mobileToken");
        String personId = (String) argumentsMap.get("personId");


        FaceAuthenticator faceAuthenticator = new FaceAuthenticator.Builder(mobileToken)
                .build();

        faceAuthenticator.authenticate(this.getActivity().getApplicationContext(), personId, new VerifyAuthenticationListener() {
            @Override
            public void onSuccess(FaceAuthenticatorResult faceAuthenticatorResult) {
                getSucessResponseMap(faceAuthenticatorResult);
            }

            @Override
            public void onError(FaceAuthenticatorResult faceAuthenticatorResult) {
                getFailureResponseMap(faceAuthenticatorResult);
            }

            @Override
            public void onCancel(FaceAuthenticatorResult faceAuthenticatorResult) {
                getClosedResponseMap();
            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded() {

            }
        });

    

        

        saveCall(call);
    }

    @ActivityCallback
    private void faceAuthenticatorResult(PluginCall call, ActivityResult result) {
        int resultCode = result.getResultCode();

        // Get the previously saved call
        PluginCall savedCall = getSavedCall();

        if (savedCall == null) {
            return;
        }
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


    private JSObject getSucessResponseMap(FaceAuthenticatorResult faceAuthenticatorResult) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", Boolean.TRUE);
        responseMap.put("isMatch", faceAuthenticatorResult.isMatch());
        responseMap.put("isAlive", faceAuthenticatorResult.isAlive());
        responseMap.put("userId", faceAuthenticatorResult.getUserId());
        JSONObject jsonObject = new JSONObject(responseMap);
        JSObject result = new JSObject();
        result.put("results", jsonObject);
        return result;
    }

    private JSObject getFailureResponseMap(FaceAuthenticatorResult faceAuthenticatorResult) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", Boolean.FALSE);
        responseMap.put("errorMessage", faceAuthenticatorResult.getErrorMessage());

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