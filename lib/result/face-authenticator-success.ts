import { FaceAuthenticatorResult } from './face-authenticator-result';

export class FaceAuthenticatorSuccess extends FaceAuthenticatorResult {


  private isMatch: boolean;
  private isAlive: boolean;
  private userId: string;


  constructor(isMatch: boolean, isAlive: boolean, userId: string) {
    super("SUCCESS");
    this.isMatch = isMatch;
    this.isAlive = isAlive;
    this.userId = userId;
  }
}
