import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    constructor(imageUrl, isAlive, userId) {
        super("SUCCESS");
        this.imageUrl = imageUrl;
        this.isAlive = isAlive;
        this.userId = userId;
    }
}
PassiveFaceLivenessSuccess.LENS_FACING_FRONT = 0;
PassiveFaceLivenessSuccess.LENS_FACING_BACK = 1;
//# sourceMappingURL=passive-face-liveness-success.js.map