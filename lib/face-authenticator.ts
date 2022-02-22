import { FaceAuthenticatorSuccess } from './result/face-authenticator-success';
import { FaceAuthenticatorFailure } from './result/face-authenticator-failure';
import { FaceAuthenticatorClosed } from './result/face-authenticator-closed';
import { IosSettings } from './ios/ios-settings';
import  {AndroidSettings}  from "./android/android-settings";
import { Plugins } from '@capacitor/core';
const { FaceAuthenticatorPlugin } = Plugins;
import { CaptureMode } from './android/capture-mode';

export {AndroidSettings} from "./android/android-settings" ;
export { VideoCapture } from './android/video-capture';
export { ImageCapture } from './android/image-capture';
export {CaptureMode} from './android/capture-mode';
export { SensorSettingsAndroid } from './android/sensor-settings';
export { IosSettings } from './ios/ios-settings';

export class FaceAuthenticator {
  private mobileToken: string;
  private peopleId: string;
  private useAnalytics: boolean;
  private sound: boolean;
  private requestTimeout: number;
  private androidSettings: AndroidSettings;
  private iosSettings: IosSettings;
  private showDelay: boolean;
  private delay: number;
  private captureMode: CaptureMode;

  constructor() {}

  public set setMobileToken(mobileToken : string){
    this.mobileToken = mobileToken;
  }

  set setPeopleId(peopleId:string){
    this.peopleId = peopleId;
  }

  set setUseAnalytics(useAnalytics:boolean){
    this.useAnalytics = useAnalytics;
  }

  set setSound(sound:boolean){
    this.sound = sound;
  }

  set setRequestTimeout(requestTimeout:number){
    this.requestTimeout = requestTimeout;
  }

  set setShowDelay(showDelay:boolean){
    this.showDelay = showDelay;
  }

  set setDelay(delay:number){
    this.delay = delay;
  }

  setCurrentStepDoneDelay(showDelay:boolean, delay: number): void{
    this.showDelay = showDelay;
    this.delay = delay;
  }

  set setAndroidSettings(androidSettings: AndroidSettings){
    this.androidSettings = androidSettings;
  }

  setIosSettings(iosSettings: IosSettings){
    this.iosSettings = iosSettings;
  }

  set setCaptureMode(captureMode:CaptureMode){
    this.captureMode = captureMode;
  }

  async start(){

    var param = JSON.stringify(this);

    const result = (await FaceAuthenticatorPlugin.start({builder: param})).results;
  
    if(result.success == null){
      return new FaceAuthenticatorClosed();
    }else if(result.success){
      return new FaceAuthenticatorSuccess(result.isAuthenticated, result.signedResponse, result.trackingId)
    }else{
      return new FaceAuthenticatorFailure(result.message, result.type)
    }
    
  }

}
