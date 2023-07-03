import { PassiveFaceLivenessResult } from './passive-face-liveness-result';

export class PassiveFaceLivenessFailure extends PassiveFaceLivenessResult {
  private errorMessage: string;

  constructor(errorMessage: string){
      super("FAILURE");
      this.errorMessage = errorMessage;
  }
}