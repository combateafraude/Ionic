package com.caf.plugins.iproov;

import android.Manifest;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

import input.CafStage;
import input.FaceAuthenticator;
import input.VerifyAuthenticationListener;
import input.iproov.Filter;
import output.FaceAuthenticatorResult;
import output.failure.SDKFailure;

@CapacitorPlugin(
        name = "FaceAuthenticator",
        permissions = {
                @Permission(
                        alias = "camera",
                        strings = { Manifest.permission.CAMERA }
                ),
                @Permission(
                        alias = "internet",
                        strings = { Manifest.permission.INTERNET }
                )
        }
)
public class FaceAuthenticatorPlugin extends Plugin {
    private FaceAuthenticator faceAuthenticator;

    @PluginMethod()
    public void Configure(PluginCall call) {
        if (!call.hasOption("mobileToken")) {
            call.reject("mobileToken must be provided");
            return;
        }

        FaceAuthenticator.Builder builder = new FaceAuthenticator.Builder(call.getString("mobileToken"));

        /**
         * Configure the stage provided by the options.
         * If not set the default is PROD.
         */
        String stageValue = call.getString("stage", "prod");
        CafStage stage;

        switch (stageValue) {
            case "beta":
                stage = CafStage.BETA;
                break;
            case "prod":
                stage = CafStage.PROD;
                break;
            default:
                call.reject("Invalid stage value: " + stageValue);
                return;
        }

        Log.i(this.getClass().getSimpleName(), "Configured stage: " + stage);
        builder.setStage(stage);

        /**
         * Configure the filter provided by the options
         */
        if (call.hasOption("filter")) {
             String filterValue = call.getString("filter");
            Filter filter;

            switch (filterValue) {
                case "natural":
                    filter = Filter.NATURAL;
                    break;
                case "line-drawing":
                    filter = Filter.LINE_DRAWING;
                    break;
                default:
                    call.reject("Invalid filter value: " + filterValue);
                    return;
            }

            builder.setFilter(filter);
        }

        /**
         * Configure whether to use a custom loading screen
         */
        if (call.hasOption("useCustomLoadingScreen")) {
            Boolean useCustomLoadingScreen = call.getBoolean("useCustomLoadingScreen");
            builder.setLoadingScreen(useCustomLoadingScreen);
        }

        /**
         * Configure whether to enable screenshots
         */
        if (call.hasOption("enableScreenshots")) {
            Boolean enableScreenshots = call.getBoolean("enableScreenshots");
            builder.setEnableScreenshots(enableScreenshots);
        }

        this.faceAuthenticator = builder.build();

        Log.d(this.getClass().getSimpleName(), "FaceAuthenticator configured successfully");

        call.resolve();
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void authenticate(PluginCall call) {
        Log.d(this.getClass().getSimpleName(), "Starting authentication");
        if (!call.hasOption("personId")) {
            call.reject("personId attribute must be provided");
            return;
        }

        if (this.faceAuthenticator == null) {
            call.reject("You must first configure the FaceAuthenticator");
            return;
        }

        String personId = call.getString("personId");

        call.setKeepAlive(true);
        this.faceAuthenticator.authenticate(this.bridge.getContext(), personId, new VerifyAuthenticationListener() {
            @Override
            public void onSuccess(FaceAuthenticatorResult result) {
                //The sdk has finished with success, it doesn't mean that the user is approved. It means that everything
                // went through and worked.
                Log.d(FaceAuthenticatorPlugin.class.getSimpleName(), "Authenticated with success");
                JSObject event = new JSObject();
                event.put("type", "success");
                event.put("data", new JSObject().put("signedResponse", result.getSignedResponse()));

                call.resolve(event);
                bridge.releaseCall(call);
            }

            @Override
            public void onError(FaceAuthenticatorResult result) {
                //The sdk has finished with an error, the message will be return in the result, so you can see what went wrong.
                String genericErrorMessage = "Error on authentication process";
                Log.d(FaceAuthenticatorPlugin.class.getSimpleName(), genericErrorMessage);

                JSObject ret = new JSObject();
                SDKFailure sdkFailure = result.getSdkFailure();

                ret.put("error", "GenericError");
                ret.put("message", result.getErrorMessage() != null ? result.getErrorMessage() : genericErrorMessage);

                if (sdkFailure == null) {
                    call.reject(genericErrorMessage, ret);
                    bridge.releaseCall(call);
                    return;
                }

                ret.put("message", sdkFailure.getMessage());

                if (sdkFailure instanceof output.failure.NetworkReason){
                    ret.put("error", "NetworkReason");
                    Log.d(FaceAuthenticatorPlugin.class.getSimpleName(), "Throwable: " + ((output.failure.NetworkReason) sdkFailure).getThrowable());
                } else if (sdkFailure instanceof output.failure.ServerReason){
                    ret.put("error", "ServerReason");
                    ret.put("statusCode", ((output.failure.ServerReason) sdkFailure).getCode());
                    Log.d(FaceAuthenticatorPlugin.class.getSimpleName(), "Status Code: " + ((output.failure.ServerReason) sdkFailure).getCode());
                }

                call.reject(sdkFailure.getMessage(), ret);
                bridge.releaseCall(call);
            }

            @Override
            public void onCancel(FaceAuthenticatorResult result) {
                //The sdk has been closed by the user.
                Log.d(FaceAuthenticatorPlugin.class.getSimpleName(), "Operation was cancelled");
                call.reject("Operation cancelled");
                bridge.releaseCall(call);
            }

            @Override
            public void onLoading() {
                //The sdk has enter in a loading process, you can use this step to customize a loading in your application
                // that way your user know that the SDK is loading.
                Log.d(FaceAuthenticatorPlugin.class.getSimpleName(), "Event: ON LOADING");
                call.resolve(new JSObject().put("type", "loading"));
            }

            @Override
            public void onLoaded() {
                //The sdk has finished the loading process, you can use this step to customize your application
                // that way your user know that the SDK is not loading anymore.
                Log.d(FaceAuthenticatorPlugin.class.getSimpleName(), "Event: ON LOADED");
                call.resolve(new JSObject().put("type", "loaded"));
            }
        });
    }
}
