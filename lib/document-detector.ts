import { ShowPreview } from './android/show-preview';
import { DocumentDetectorStep } from './document-detector-step';
import { DocumentDetectorIosSettings } from './ios/ios-settings'
import { DocumentDetectorAndroidSettings } from './android/android-settings';
import { Plugins } from '@capacitor/core';

export { DocumentDetectorStep };
export { DocumentType } from './document-type';

const { DocumentDetectorIonic } = Plugins;

export class DocumentDetector {
  private mobileToken: string;
  private peopleId: string;
  private useAnalytics: boolean;
  private documentDetectorSteps: Array<DocumentDetectorStep>;
  private popup: boolean;
  private sound: boolean;
  private requestTimeout: number;
  private showPreview: ShowPreview;
  private androidSettings: DocumentDetectorAndroidSettings;
  private iosSettings: DocumentDetectorIosSettings;
  private showDelay: boolean;
  private delay: number;
  private autoDetection: boolean;

  constructor() {}

  public set setMobileToken(mobileToken: string) {
    this.mobileToken = mobileToken;
  }

  set setPeopleId(peopleId: string) {
    this.peopleId = peopleId;
  }

  set setUseAnalytics(useAnalytics: boolean) {
    this.useAnalytics = useAnalytics;
  }

  set setPopup(popup: boolean) {
    this.popup = popup;
  }

  set setSound(sound: boolean) {
    this.sound = sound;
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

  set setAutoDetection(autoDetection: boolean) {
    this.autoDetection = autoDetection;
  }

  set setDocumentDetectorSteps(
    documentDetectorSteps: Array<DocumentDetectorStep>,
  ) {
    this.documentDetectorSteps = documentDetectorSteps;
  }

  setCurrentStepDoneDelay(showDelay: boolean, delay: number): void {
    this.showDelay = showDelay;
    this.delay = delay;
  }

  set setShowPreview(showPreview: ShowPreview) {
    this.showPreview = showPreview;
  }

  set setAndroidSettings(androidSettings: DocumentDetectorAndroidSettings) {
    this.androidSettings = androidSettings;
  }

  set setIosSettings(iosSettings: DocumentDetectorIosSettings) {
    this.iosSettings = iosSettings;
  }

  async start() {
    var builder = JSON.stringify(this);

    var result = await DocumentDetectorIonic.start({ builder });

    return result.results;
  }
}
