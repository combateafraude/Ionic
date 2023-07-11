package com.passive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.caf.facelivenessiproov.input.CAFStage;
import com.caf.facelivenessiproov.input.FaceLiveness;
import com.caf.facelivenessiproov.input.VerifyLivenessListener;
import com.caf.facelivenessiproov.output.FaceLivenessResult;
import com.getcapacitor.JSObject;
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

@CapacitorPlugin(requestCodes = { PassiveFaceLivenessPlugin.REQUEST_CODE })
public class PassiveFaceLivenessPlugin extends Plugin {
    protected static final int REQUEST_CODE = 1002;

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

        //PersonID
        String personId = (String) argumentsMap.get("personId");

        //Stage
        CAFStage cafStage = CAFStage.PROD;
        String flutterStage = (String) argumentsMap.get("stage");
        if (flutterStage != null) {
            cafStage = CAFStage.valueOf(flutterStage);
        }

        FaceLiveness faceLiveness = new FaceLiveness.Builder(mobileToken)
                .setStage(cafStage)
                .build();


        faceLiveness.startSDK(this.getActivity().getApplicationContext(), personId, new VerifyLivenessListener() {
            @Override
            public void onSuccess(FaceLivenessResult result) {
                getSucessResponseMap(result);
            }

            @Override
            public void onError(FaceLivenessResult result) {
                getFailureResponseMap(result);
            }

            @Override
            public void onCancel(FaceLivenessResult result) {
                getClosedResponseMap();
            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded() {

            }
        });

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

    private JSObject getSucessResponseMap(FaceLivenessResult faceLivenessResult) {
        HashMap<String, Object> responseMap = new HashMap<>();

        responseMap.put("success", Boolean.TRUE);
        responseMap.put("signedResponse", faceLivenessResult.getSignedResponse());

        JSONObject jsonObject = new JSONObject(responseMap);
        JSObject result = new JSObject();
        result.put("results", jsonObject);
        return result;
    }

    private JSObject getFailureResponseMap(FaceLivenessResult faceLivenessResult) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", Boolean.FALSE);
        responseMap.put("errorMessage", faceLivenessResult.getErrorMessage());

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
