import { QualitySettings } from './quality-settings';
import { DetectionSettings } from './detection-settings';
import { CaptureMode } from './capture-mode';

export class CaptureStage {
  private durationMillis: number;
  private wantSensorCheck: boolean;
  private qualitySettings: QualitySettings;
  private detectionSettings: DetectionSettings;
  private captureMode: CaptureMode;

  constructor(durationMillis: number, wantSensorCheck: boolean, qualitySettings: QualitySettings,
    detectionSettings: DetectionSettings, captureMode: CaptureMode){
      this.durationMillis = durationMillis;
      this.wantSensorCheck = wantSensorCheck;
      this.qualitySettings = qualitySettings;
      this.detectionSettings = detectionSettings;
      this.captureMode = captureMode;
    }

 /* Map asMap() {
    Map<String, dynamic> map = new Map();

    map["durationMillis"] = durationMillis;
    map["wantSensorCheck"] = wantSensorCheck;
    map["qualitySettings"] = qualitySettings?.asMap();
    map["detectionSettings"] = detectionSettings?.asMap();
    map["captureMode"] = captureMode.toString().split(".")[1];

    return map;
  }*/
}
