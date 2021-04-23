export class SensorSettingsAndroid {
    constructor(sensorLuminositySettings, sensorOrientationSettings, sensorStabilitySettings) {
        this.sensorLuminositySettings = sensorLuminositySettings;
        this.sensorOrientationSettings = sensorOrientationSettings;
        this.sensorStabilitySettings = sensorStabilitySettings;
    }
}
/*Map asMap() {
  Map<String, dynamic> map = new Map();

  map["sensorLuminositySettings"] = sensorLuminositySettings?.asMap();
  map["sensorOrientationSettings"] = sensorOrientationSettings?.asMap();
  map["sensorStabilitySettings"] = sensorStabilitySettings?.asMap();

  return map;
}
}*/
//# sourceMappingURL=sensor-settings.js.map