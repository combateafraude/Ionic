import { PassiveFaceLivenessSuccess } from './result/passive-face-liveness-success';
import { PassiveFaceLivenessFailure } from './result/passive-face-liveness-failure';
import { PassiveFaceLivenessClosed } from './result/passive-face-liveness-closed';
export declare class PassiveFaceLiveness {
    private mobileToken;
    private personId;
    constructor(mobileToken: string, personId: string);
    start(): Promise<PassiveFaceLivenessSuccess | PassiveFaceLivenessFailure | PassiveFaceLivenessClosed>;
}
