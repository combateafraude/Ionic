import { FaceAuthenticatorResult } from './face-authenticator-result';

export class FaceAuthenticatorFailure extends FaceAuthenticatorResult {
  private message: string;
  private type: string;

  constructor(message: string, type: string){
      super("FAILURE");
      this.message = message;
      this.type = type;
  }
}