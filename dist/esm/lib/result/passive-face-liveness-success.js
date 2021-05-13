import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    constructor(imagePath, imageUrl, signedResponse, trackingId) {
        super("SUCCESS");
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.signedResponse = signedResponse;
        this.trackingId = trackingId;
    }
}
//# sourceMappingURL=passive-face-liveness-success.js.map