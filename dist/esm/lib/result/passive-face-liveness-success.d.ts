import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export declare class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    static LENS_FACING_FRONT: number;
    static LENS_FACING_BACK: number;
    private imagePath;
    private imageUrl;
    private signedResponse;
    private trackingId;
    private capturePath;
    private lensFacing;
    constructor(imagePath: string, imageUrl: string, signedResponse: string, trackingId: string, capturePath: string, lensFacing: number);
}
