import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export class PassiveFaceLivenessFailure extends PassiveFaceLivenessResult {
    constructor(errorMessage) {
        super("FAILURE");
        this.errorMessage = errorMessage;
    }
}
//# sourceMappingURL=passive-face-liveness-failure.js.map