import { DocumentDetectorResult } from './document-detector-result';
export class DocumentDetectorSuccess extends DocumentDetectorResult {
    constructor(captures, type, trackingId) {
        super("SUCCESS");
        this.captures = captures;
        this.type = type;
        this.trackingId = trackingId;
    }
}
//# sourceMappingURL=document-detector-success.js.map