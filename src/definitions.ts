declare module '@capacitor/core' {
  interface PluginRegistry {
    IonicPlugin: IonicPluginPlugin;
  }
}

export interface IonicPluginPlugin {
  start(options: { builder: string }): Promise<{results: any}>; 
}
