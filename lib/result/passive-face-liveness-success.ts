import { PassiveFaceLivenessResult } from './passive-face-liveness-result';

export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
  public static LENS_FACING_FRONT:number = 0;
  public static LENS_FACING_BACK:number = 1;

  private imagePath:string;
  private imageUrl:string;
  private signedResponse:string;
  private trackingId:string;
  private capturePath:string;
  private lensFacing: number;

  constructor(imagePath: string, imageUrl: string, signedResponse: string, trackingId: string, capturePath:string, lensFacing:number){
    super("SUCCESS");
    this.imagePath = imagePath;
    this.imageUrl = imageUrl;
    this.signedResponse = signedResponse;
    this.trackingId = trackingId;
    this.capturePath = capturePath;
    this.lensFacing = lensFacing;
  }
}
