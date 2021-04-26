declare module '@capacitor/core' {
  interface PluginRegistry {
    DocumentDetectorPlugin: DocumentDetectorPlugin;
  }
}

export interface DocumentDetectorPlugin {
  start(options: { builder: string }): Promise<{results: any}>; 
}
