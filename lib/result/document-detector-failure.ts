import { DocumentDetectorResult } from './document-detector-result';

export class DocumentDetectorFailure extends DocumentDetectorResult {
  private message: string;
  private type: string;

  constructor(message: string, type: string){
      super("FAILURE");
      this.message = message;
      this.type = type;
  }
}