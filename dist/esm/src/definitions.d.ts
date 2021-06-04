declare module '@capacitor/core' {
    interface PluginRegistry {
        PassiveFaceLivenessPlugin: PassiveFaceLivenessPlugin;
    }
}
export interface PassiveFaceLivenessPlugin {
    start(options: {
        builder: string;
    }): Promise<{
        results: any;
    }>;
}
