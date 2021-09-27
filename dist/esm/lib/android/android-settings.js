export class DocumentDetectorAndroidSettings {
    constructor(customization, sensorSettings, captureStages, enableSwitchCameraButton) {
        this.customization = customization;
        this.sensorSettings = sensorSettings;
        this.captureStages = captureStages;
        this.enableSwitchCameraButton = enableSwitchCameraButton;
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