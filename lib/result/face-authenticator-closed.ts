import { FaceAuthenticatorResult } from './face-authenticator-result';

export class FaceAuthenticatorClosed extends FaceAuthenticatorResult {
    constructor(){
        super("CLOSED");
    }
}