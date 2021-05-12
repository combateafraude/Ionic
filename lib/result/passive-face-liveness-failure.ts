import { PassiveFaceLivenessResult } from './passive-face-liveness-result';

export class PassiveFaceLivenessFailure extends PassiveFaceLivenessResult {
  private message: string;
  private type: string;

  constructor(message: string, type: string){
      super("FAILURE");
      this.message = message;
      this.type = type;
  }
}