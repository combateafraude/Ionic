export class DocumentDetectorStep {
    constructor(documentType, android) {
        if (android != null) {
            this.android = android;
        }
        this.documentType = documentType;
    }
    asMap() {
        let documentDetectorStepMap = new Map();
        documentDetectorStepMap.set("documentType", this.documentType);
        return documentDetectorStepMap;
    }
}
//# sourceMappingURL=document-detector-step.js.map