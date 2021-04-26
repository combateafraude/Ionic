import { DocumentDetectorResult } from './document-detector-result';
export declare class DocumentDetectorFailure extends DocumentDetectorResult {
    private message;
    private type;
    constructor(message: string, type: string);
}
