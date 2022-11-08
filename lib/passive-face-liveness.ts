import { PassiveFaceLivenessSuccess } from './result/passive-face-liveness-success';
import { PassiveFaceLivenessFailure } from './result/passive-face-liveness-failure';
import { PassiveFaceLivenessClosed } from './result/passive-face-liveness-closed';
import { IosSettings } from './ios/ios-settings';
import { ShowPreview } from './show-preview';
import { AndroidSettings } from "./android/android-settings";
import { CaptureMode } from './android/capture-mode';
import { PassiveFaceLivenessCustomizationAndroid } from './android/customization';
import { PassiveFaceLivenessCustomizationIos } from './ios/customization';
import { SensorStabilitySettingsIos } from './ios/sensor-stability-settings';

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
  private mobileToken: string;
  private peopleId: string;
  private personName: string;
  private personCpf: string;
  private useAnalytics: boolean;
  private sound?: string;
  private enableSound: boolean;
  private requestTimeout: number;
  private showPreview: ShowPreview;
  private androidSettings: AndroidSettings;
  private iosSettings: IosSettings;
  private showDelay: boolean;
  private delay: number;
  private captureMode: CaptureMode;
  private expireTime: string;
  private useOpenEyeValidation?: boolean;
  private openEyesThreshold?: number;

  constructor() { }

  public set setMobileToken(mobileToken: string) {
    this.mobileToken = mobileToken;
  }

  set setPeopleId(peopleId: string) {
    this.peopleId = peopleId;
  }

  set setPersonName(personName: string) {
    this.personName = personName;
  }

  set setPersonCpf(personCpf: string) {
    this.personCpf = personCpf;
  }

  set setUseAnalytics(useAnalytics: boolean) {
    this.useAnalytics = useAnalytics;
  }

  setAudioSettings(enable: boolean, soundResId?: string) {
    this.enableSound = enable;
    this.sound = soundResId;
  }

  set setRequestTimeout(requestTimeout: number) {
    this.requestTimeout = requestTimeout;
  }

  set setShowDelay(showDelay: boolean) {
    this.showDelay = showDelay;
  }

  set setDelay(delay: number) {
    this.delay = delay;
  }

  setCurrentStepDoneDelay(showDelay: boolean, delay: number): void {
    this.showDelay = showDelay;
    this.delay = delay;
  }

  set setShowPreview(showPreview: ShowPreview) {
    this.showPreview = showPreview;
  }

  set setAndroidSettings(androidSettings: AndroidSettings) {
    this.androidSettings = androidSettings;
  }

  setIosSettings(iosSettings: IosSettings) {
    this.iosSettings = iosSettings;
  }

  set setCaptureMode(captureMode: CaptureMode) {
    this.captureMode = captureMode;
  }

  set setGetImageUrlExpireTime(expireTime: string) {
    this.expireTime = expireTime;
  }

  setEyesClosedSettings(enable: boolean, threshold?: number): void {
    this.useOpenEyeValidation = enable;
    this.openEyesThreshold = threshold;
  }

  async start() {

    var param = JSON.stringify(this);

    const result = (await PassiveFaceLivenessPlugin.start({ builder: param })).results;

    if (result.success == null) {
      return new PassiveFaceLivenessClosed();
    } else if (result.success) {
      return new PassiveFaceLivenessSuccess(result.imagePath, result.imageUrl, result.signedResponse, result.trackingId, result.capturePath, result.lensFacing)
    } else {
      return new PassiveFaceLivenessFailure(result.message, result.type)
    }

  }

}
