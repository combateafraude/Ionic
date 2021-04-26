import { Capture } from './capture';
import { DocumentDetectorResult } from './document-detector-result';
export declare class DocumentDetectorSuccess extends DocumentDetectorResult {
    private captures;
    private type;
    private trackingId;
    constructor(captures: Array<Capture>, type: string, trackingId: string);
}
