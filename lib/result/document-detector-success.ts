import { Capture } from './capture';
import { DocumentDetectorResult } from './document-detector-result';

export class DocumentDetectorSuccess extends DocumentDetectorResult {
  static LENS_FACING_FRONT: number;
  static LENS_FACING_BACK: number;

  private captures: Array<Capture>;
  private type:string;
  private trackingId:string;

  constructor(captures: Array<Capture>, type: string, trackingId: string){
    super("SUCCESS");
    this.captures = captures;
    this.type = type;
    this.trackingId = trackingId;
  }
}
