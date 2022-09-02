export class SensorStabilitySettingsIos {
    private message?: string;
    private stabilityThreshold?: number;

    constructor(
        options: {
            message: string, stabilityThreshold: number
        }) {
        this.message = options?.message;
        this.stabilityThreshold = options?.stabilityThreshold;
    }
}
