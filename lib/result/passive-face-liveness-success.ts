import { PassiveFaceLivenessResult } from './passive-face-liveness-result';

export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
  private signedResponse:string;

  constructor(signedResponse: string){
    super("SUCCESS");
    this.signedResponse = signedResponse;
  }
}
