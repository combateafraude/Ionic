import { DocumentDetectorCustomizationIos } from './ios/customization';
import { DocumentType } from './document-type';
import { DocumentDetectorStepCustomizationAndroid } from './document-detector-step-customization-android';
export declare class DocumentDetectorStep {
    private documentType;
    private android;
    private ios;
    constructor(documentType: DocumentType, android: DocumentDetectorStepCustomizationAndroid, ios: DocumentDetectorCustomizationIos);
}
