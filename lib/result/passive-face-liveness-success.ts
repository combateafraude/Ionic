import { PassiveFaceLivenessResult } from './passive-face-liveness-result';

export class PassiveFaceLivenessSuccess extends PassiveFaceLivenessResult {
  public static LENS_FACING_FRONT:number = 0;
  public static LENS_FACING_BACK:number = 1;

  private imageUrl:string;
  private isAlive:boolean;
  private userId:string;

  constructor(imageUrl: string, isAlive: boolean, userId: string){
    super("SUCCESS");
    this.imageUrl = imageUrl;
    this.isAlive = isAlive;
    this.userId = userId;
  }
}
