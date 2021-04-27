import { DocumentDetectorResult } from './document-detector-result';

export class DocumentDetectorClosed extends DocumentDetectorResult {
    constructor(){
        super("CLOSED");
    }
}