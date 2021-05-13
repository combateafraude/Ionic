import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export declare class PassiveFaceLivenessFailure extends PassiveFaceLivenessResult {
    private message;
    private type;
    constructor(message: string, type: string);
}
