import { QualitySettings } from './quality-settings';
import { DetectionSettings } from './detection-settings';
import { CaptureMode } from './capture-mode';
export declare class CaptureStage {
    private durationMillis;
    private wantSensorCheck;
    private qualitySettings;
    private detectionSettings;
    private captureMode;
    constructor(durationMillis: number, wantSensorCheck: boolean, qualitySettings: QualitySettings, detectionSettings: DetectionSettings, captureMode: CaptureMode);
}
