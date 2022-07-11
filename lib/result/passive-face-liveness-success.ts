import { PassiveFaceLivenessResult } from './passive-face-liveness-result';

export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
  private imagePath:string;
  private imageUrl:string;
  private signedResponse:string;
  private trackingId:string;
  private capturePath:string;

  constructor(imagePath: string, imageUrl: string, signedResponse: string, trackingId: string, capturePath:string){
    super("SUCCESS");
    this.imagePath = imagePath;
    this.imageUrl = imageUrl;
    this.signedResponse = signedResponse;
    this.trackingId = trackingId;
    this.capturePath = capturePath;
  }
}
