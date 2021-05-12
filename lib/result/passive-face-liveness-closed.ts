import { PassiveFaceLivenessResult } from './passive-face-liveness-result';

export class PassiveFaceLivenessClosed extends PassiveFaceLivenessResult {
    constructor(){
        super("CLOSED");
    }
}