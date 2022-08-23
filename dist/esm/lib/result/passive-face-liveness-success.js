import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    constructor(imagePath, imageUrl, signedResponse, trackingId, capturePath, lensFacing) {
        super("SUCCESS");
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.signedResponse = signedResponse;
        this.trackingId = trackingId;
        this.capturePath = capturePath;
        this.lensFacing = lensFacing;
    }
}
PassiveFaceLivenessSuccess.LENS_FACING_FRONT = 0;
PassiveFaceLivenessSuccess.LENS_FACING_BACK = 1;
//# sourceMappingURL=passive-face-liveness-success.js.map