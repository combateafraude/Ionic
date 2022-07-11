import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    constructor(imagePath, imageUrl, signedResponse, trackingId, capturePath) {
        super("SUCCESS");
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.signedResponse = signedResponse;
        this.trackingId = trackingId;
        this.capturePath = capturePath;
    }
}
//# sourceMappingURL=passive-face-liveness-success.js.map