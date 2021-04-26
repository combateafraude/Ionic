import { Capture } from './capture';
import 'package:document_detector/result/capture.dart';
import 'package:document_detector/result/document_detector_result.dart';

class DocumentDetectorSuccess extends DocumentDetectorResult {
  private captures: Array<Capture>;
  private type:string;
  private trackingId:string;

  constructor(captures: Array<Capture>, type: string, trackingId: string){
    super();
    this.captures = captures;
    this.type = type;
    this.trackingId = trackingId;
  }
}
