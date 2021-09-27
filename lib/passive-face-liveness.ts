import { PassiveFaceLivenessSuccess } from './result/passive-face-liveness-success';
import { PassiveFaceLivenessFailure } from './result/passive-face-liveness-failure';
import { PassiveFaceLivenessClosed } from './result/passive-face-liveness-closed';
import { IosSettings } from './ios/ios-settings';
import  {ShowPreview}  from './show-preview';
import  {AndroidSettings}  from "./android/android-settings";
import { Plugins } from '@capacitor/core';
const { PassiveFaceLivenessPlugin } = Plugins;

export {ShowPreview} from './show-preview';

export {AndroidSettings} from "./android/android-settings" ;
export { CaptureAndroidSettings } from './android/capture-settings';
export { SensorSettingsAndroid } from './android/sensor-settings';
export { DocumentDetectorCustomizationAndroid } from './android/customization';

export { IosSettings } from './ios/ios-settings';
import { PassiveFaceLivenessCustomizationIos } from './ios/customization';
import { SensorStabilitySettingsIos } from './ios/sensor-stability-settings';

export class PassiveFaceLiveness {
  private mobileToken: string;
  private peopleId: string;
  private useAnalytics: boolean;
  private sound: boolean;
  private requestTimeout: number;
  private showPreview: ShowPreview;
  private androidSettings: AndroidSettings;
  private iosSettings: IosSettings;
  private showDelay: boolean;
  private delay: number;

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

  set setShowPreview(showPreview: ShowPreview){
    this.showPreview = showPreview;
  }

  set setAndroidSettings(androidSettings: AndroidSettings){
    this.androidSettings = androidSettings;
  }

  setIosSettings(iosSettings: IosSettings){
    this.iosSettings = iosSettings;
  }

  async start(){

    var param = JSON.stringify(this);

    const result = (await PassiveFaceLivenessPlugin.start({builder: param})).results;
  
    if(result.success == null){
      return new PassiveFaceLivenessClosed();
    }else if(result.success){
      return new PassiveFaceLivenessSuccess(result.imagePath, result.imageUrl, result.signedResponse, result.trackingId)
    }else{
      return new PassiveFaceLivenessFailure(result.message, result.type)
    }
    
  }

}
