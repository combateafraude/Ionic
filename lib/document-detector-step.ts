import { DocumentDetectorCustomizationIos } from './ios/customization';
import { DocumentType } from './document-type';
import { DocumentDetectorStepCustomizationAndroid } from './document-detector-step-customization-android';

export class DocumentDetectorStep {
  private documentType: DocumentType;
  private android: DocumentDetectorStepCustomizationAndroid;
  private ios: DocumentDetectorCustomizationIos
  
  constructor(documentType: DocumentType, android: DocumentDetectorStepCustomizationAndroid, ios: DocumentDetectorCustomizationIos){
    if(android != null){
      this.android = android;
    }
    if(ios != null){
      this.ios = ios;
    }
    this.documentType = documentType;
  }

}

