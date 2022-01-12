export class DocumentDetectorAndroidSettings {
    constructor(customization, sensorSettings, captureStages, enableSwitchCameraButton, compressQuality, resolution, enableGoogleServices, useRoot, useEmulator) {
        this.customization = customization;
        this.sensorSettings = sensorSettings;
        this.captureStages = captureStages;
        this.enableSwitchCameraButton = enableSwitchCameraButton;
        this.compressQuality = compressQuality;
        this.resolution = resolution;
        this.enableGoogleServices = enableGoogleServices;
        this.useRoot = useRoot;
        this.useEmulator = useEmulator;
    }
}
/*Map asMap() {
    Map<String, dynamic> map = new Map();

    map["customization"] = customization?.asMap();
    map["sensorSettings"] = sensorSettings?.asMap();

    if (captureStages != null) {
      List<Map<String, dynamic>> stagesMap = [];
      for (var stage in captureStages!) {
        stagesMap.add(stage.asMap() as Map<String, dynamic>);
      }
      map["captureStages"] = stagesMap;
    }
    return map;
  }*/
//# sourceMappingURL=android-settings.js.map