var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { PassiveFaceLivenessSuccess } from './result/passive-face-liveness-success';
import { PassiveFaceLivenessFailure } from './result/passive-face-liveness-failure';
import { PassiveFaceLivenessClosed } from './result/passive-face-liveness-closed';
import { Plugins } from '@capacitor/core';
const { PassiveFaceLivenessPlugin } = Plugins;
export { ShowPreview } from './show-preview';
export { AndroidSettings } from "./android/android-settings";
export { VideoCapture } from './android/video-capture';
export { ImageCapture } from './android/image-capture';
export { CaptureMode } from './android/capture-mode';
export { SensorSettingsAndroid } from './android/sensor-settings';
export { PassiveFaceLivenessCustomizationAndroid } from './android/customization';
export { IosSettings } from './ios/ios-settings';
export { IosResolution } from './ios/ios-resolution';
export { PassiveFaceLivenessCustomizationIos } from './ios/customization';
export { SensorStabilitySettingsIos } from './ios/sensor-stability-settings';
export class PassiveFaceLiveness {
    constructor() { }
    set setMobileToken(mobileToken) {
        this.mobileToken = mobileToken;
    }
    set setPeopleId(peopleId) {
        this.peopleId = peopleId;
    }
    set setPersonName(personName) {
        this.personName = personName;
    }
    set setPersonCpf(personCpf) {
        this.personCpf = personCpf;
    }
    set setUseAnalytics(useAnalytics) {
        this.useAnalytics = useAnalytics;
    }
    setAudioSettings(enable, soundResId) {
        this.enableSound = enable;
        this.sound = soundResId;
    }
    set setRequestTimeout(requestTimeout) {
        this.requestTimeout = requestTimeout;
    }
    set setShowDelay(showDelay) {
        this.showDelay = showDelay;
    }
    set setDelay(delay) {
        this.delay = delay;
    }
    setCurrentStepDoneDelay(showDelay, delay) {
        this.showDelay = showDelay;
        this.delay = delay;
    }
    set setShowPreview(showPreview) {
        this.showPreview = showPreview;
    }
    set setAndroidSettings(androidSettings) {
        this.androidSettings = androidSettings;
    }
    setIosSettings(iosSettings) {
        this.iosSettings = iosSettings;
    }
    set setCaptureMode(captureMode) {
        this.captureMode = captureMode;
    }
    set setGetImageUrlExpireTime(expireTime) {
        this.expireTime = expireTime;
    }
    setEyesClosedSettings(enable, threshold) {
        this.useOpenEyeValidation = enable;
        this.openEyesThreshold = threshold;
    }
    start() {
        return __awaiter(this, void 0, void 0, function* () {
            var param = JSON.stringify(this);
            const result = (yield PassiveFaceLivenessPlugin.start({ builder: param })).results;
            if (result.success == null) {
                return new PassiveFaceLivenessClosed();
            }
            else if (result.success) {
                return new PassiveFaceLivenessSuccess(result.imagePath, result.imageUrl, result.signedResponse, result.trackingId, result.capturePath, result.lensFacing);
            }
            else {
                return new PassiveFaceLivenessFailure(result.message, result.type);
            }
        });
    }
}
//# sourceMappingURL=passive-face-liveness.js.map