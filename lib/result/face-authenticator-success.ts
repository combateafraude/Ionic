import { FaceAuthenticatorResult } from './face-authenticator-result';

export class FaceAuthenticatorSuccess extends FaceAuthenticatorResult {
  private isAuthenticated:boolean;
  private signedResponse:string;
  private trackingId:string;

  constructor(isAuthenticated: boolean, signedResponse: string, trackingId: string){
    super("SUCCESS");
    this.isAuthenticated = isAuthenticated;
    this.signedResponse = signedResponse;
    this.trackingId = trackingId;
  }
}
