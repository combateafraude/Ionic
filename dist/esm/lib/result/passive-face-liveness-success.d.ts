import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export declare class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    private signedResponse;
    constructor(signedResponse: string);
}
