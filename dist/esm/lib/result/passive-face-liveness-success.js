import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    constructor(signedResponse) {
        super("SUCCESS");
        this.signedResponse = signedResponse;
    }
}
//# sourceMappingURL=passive-face-liveness-success.js.map