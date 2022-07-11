import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export declare class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    private imagePath;
    private imageUrl;
    private signedResponse;
    private trackingId;
    private capturePath;
    constructor(imagePath: string, imageUrl: string, signedResponse: string, trackingId: string, capturePath: string);
}
