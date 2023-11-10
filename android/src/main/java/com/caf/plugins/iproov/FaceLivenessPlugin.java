package com.caf.plugins.iproov;

import android.Manifest;
import android.util.Log;

import com.caf.facelivenessiproov.input.VerifyLivenessListener;
import com.caf.facelivenessiproov.input.iproov.Filter;
import com.caf.facelivenessiproov.output.FaceLivenessResult;
import com.caf.facelivenessiproov.output.failure.NetworkReason;
import com.caf.facelivenessiproov.output.failure.ServerReason;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

import com.caf.facelivenessiproov.input.FaceLiveness;
import com.caf.facelivenessiproov.output.failure.SDKFailure;

import com.caf.facelivenessiproov.input.CAFStage;

@CapacitorPlugin(
        name = "FaceLiveness",
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
public class FaceLivenessPlugin extends Plugin {
    private FaceLiveness faceLiveness;

    @PluginMethod()
    public void Configure(PluginCall call) {
        if (!call.hasOption("mobileToken")) {
            call.reject("mobileToken must be provided");
            return;
        }

        FaceLiveness.Builder builder = new FaceLiveness.Builder(call.getString("mobileToken"));

        /**
         * Configure the stage provided by the options.
         * If not set the default is PROD.
         */
        String stageValue = call.getString("stage", "prod");
        CAFStage stage;

        switch (stageValue) {
            case "beta":
                stage = CAFStage.BETA;
                break;
            case "prod":
                stage = CAFStage.PROD;
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

        this.faceLiveness = builder.build();

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

        if (this.faceLiveness == null) {
            call.reject("You must first configure the FaceAuthenticator");
            return;
        }

        String personId = call.getString("personId");

        call.setKeepAlive(true);
        this.faceLiveness.startSDK(this.bridge.getContext(), personId, new VerifyLivenessListener() {
            @Override
            public void onSuccess(FaceLivenessResult result) {
                //The sdk has finished with success, it doesn't mean that the user is approved. It means that everything
                // went through and worked.
                Log.d(FaceLivenessPlugin.class.getSimpleName(), "Authenticated with success");
                JSObject event = new JSObject();
                event.put("type", "success");
                event.put("data", new JSObject().put("signedResponse", result.getSignedResponse()));

                call.resolve(event);
                bridge.releaseCall(call);
            }

            @Override
            public void onError(FaceLivenessResult result) {
                //The sdk has finished with an error, the message will be return in the result, so you can see what went wrong.
                String genericErrorMessage = "Error on authentication process";
                Log.d(FaceLivenessPlugin.class.getSimpleName(), genericErrorMessage);

                JSObject ret = new JSObject();
                SDKFailure sdkFailure = result.getSdkFailure();

                ret.put("error", "GenericError");
                ret.put("message", result.getErrorMessage() != null || result.getErrorMessage().isEmpty()  ? result.getErrorMessage() : genericErrorMessage);

                if (sdkFailure == null) {
                    call.reject(genericErrorMessage, ret);
                    bridge.releaseCall(call);
                    return;
                }

                ret.put("message", sdkFailure.getMessage());

                if (sdkFailure instanceof NetworkReason){
                    ret.put("error", "NetworkReason");
                    Log.d(FaceLivenessPlugin.class.getSimpleName(), "Throwable: " + ((NetworkReason) sdkFailure).getThrowable());
                } else if (sdkFailure instanceof ServerReason){
                    ret.put("error", "ServerReason");
                    ret.put("statusCode", ((ServerReason) sdkFailure).getCode());
                    Log.d(FaceLivenessPlugin.class.getSimpleName(), "Status Code: " + ((ServerReason) sdkFailure).getCode());
                }

                call.reject(sdkFailure.getMessage(), ret);
                bridge.releaseCall(call);
            }

            @Override
            public void onCancel(FaceLivenessResult result) {
                //The sdk has been closed by the user.
                Log.d(FaceLivenessPlugin.class.getSimpleName(), "Operation was cancelled");
                call.reject("Operation cancelled");
                bridge.releaseCall(call);
            }

            @Override
            public void onLoading() {
                //The sdk has enter in a loading process, you can use this step to customize a loading in your application
                // that way your user know that the SDK is loading.
                Log.d(FaceLivenessPlugin.class.getSimpleName(), "Event: ON LOADING");
                call.resolve(new JSObject().put("type", "loading"));
            }

            @Override
            public void onLoaded() {
                //The sdk has finished the loading process, you can use this step to customize your application
                // that way your user know that the SDK is not loading anymore.
                Log.d(FaceLivenessPlugin.class.getSimpleName(), "Event: ON LOADED");
                call.resolve(new JSObject().put("type", "loaded"));
            }
        });
    }
}
