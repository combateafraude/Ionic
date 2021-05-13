import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export class PassiveFaceLivenessFailure extends PassiveFaceLivenessResult {
    constructor(message, type) {
        super("FAILURE");
        this.message = message;
        this.type = type;
    }
}
//# sourceMappingURL=passive-face-liveness-failure.js.map