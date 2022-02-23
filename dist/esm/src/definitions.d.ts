declare module '@capacitor/core' {
    interface PluginRegistry {
        FaceAuthenticatorPlugin: FaceAuthenticatorPlugin;
    }
}
export interface FaceAuthenticatorPlugin {
    start(options: {
        builder: string;
    }): Promise<{
        results: any;
    }>;
}
