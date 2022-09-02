import { DocumentDetectorCustomizationIos } from './customization';
import { SensorSettingsIos } from './sensor_settings';
export declare class DocumentDetectorIosSettings {
    private detectionThreshold;
    private verifyQuality;
    private qualityThreshold;
    private customization;
    private sensorSettings;
    private enableManualCapture;
    private timeEnableManualCapture;
    private resolution;
    private compressQuality;
    constructor(detectionThreshold: number, verifyQuality: boolean, qualityThreshold: number, customization: DocumentDetectorCustomizationIos, sensorSettings: SensorSettingsIos, enableManualCapture: boolean, timeEnableManualCapture: number, resolution: string, compressQuality: number);
}
