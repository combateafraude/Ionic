import { FaceAuthenticatorResult } from './face-authenticator-result';

export class FaceAuthenticatorFailure extends FaceAuthenticatorResult {
  private errorMessage: string;

  constructor(errorMessage: string){
      super("FAILURE");
      this.errorMessage = errorMessage;
  }
}