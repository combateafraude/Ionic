import { DocumentDetectorResult } from './document-detector-result';
export class DocumentDetectorFailure extends DocumentDetectorResult {
    constructor(message, type) {
        super("FAILURE");
        this.message = message;
        this.type = type;
    }
}
//# sourceMappingURL=document-detector-failure.js.map