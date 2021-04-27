import { Capture } from './capture';
import { DocumentDetectorResult } from './document-detector-result';

export class DocumentDetectorSuccess extends DocumentDetectorResult {
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
