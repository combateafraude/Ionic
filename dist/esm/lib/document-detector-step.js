export class DocumentDetectorStep {
    constructor(documentType, android, ios) {
        if (android != null) {
            this.android = android;
        }
        if (ios != null) {
            this.ios = ios;
        }
        this.documentType = documentType;
    }
}
//# sourceMappingURL=document-detector-step.js.map