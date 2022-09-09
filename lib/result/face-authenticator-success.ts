import { FaceAuthenticatorResult } from './face-authenticator-result';

export class FaceAuthenticatorSuccess extends FaceAuthenticatorResult {
  public static LENS_FACING_FRONT: number = 0;
  public static LENS_FACING_BACK: number = 1;

  private isAuthenticated: boolean;
  private signedResponse: string;
  private trackingId: string;
  private lensFacing: number;

  constructor(isAuthenticated: boolean, signedResponse: string, trackingId: string, lensFacing: number) {
    super("SUCCESS");
    this.isAuthenticated = isAuthenticated;
    this.signedResponse = signedResponse;
    this.trackingId = trackingId;
    this.lensFacing = lensFacing;
  }
}
