import { PassiveFaceLivenessResult } from './passive-face-liveness-result';
export declare class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
    static LENS_FACING_FRONT: number;
    static LENS_FACING_BACK: number;
    private imageUrl;
    private isAlive;
    private userId;
    constructor(imageUrl: string, isAlive: boolean, userId: string);
}
