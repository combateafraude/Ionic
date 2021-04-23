import { DocumentType } from './document-type';
import { DocumentDetectorStepCustomizationAndroid } from './document-detector-step-customization-android';
export declare class DocumentDetectorStep {
    private documentType;
    private android;
    constructor(documentType: DocumentType, android: DocumentDetectorStepCustomizationAndroid);
    asMap(): Map<string, any>;
}
