declare module '@capacitor/core' {
    interface PluginRegistry {
        IonicPlugin: IonicPluginPlugin;
    }
}
export interface IonicPluginPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    getContacts(filter: string): Promise<{
        results: any[];
    }>;
    start(options: {
        value: string;
    }): Promise<{
        results: any[];
    }>;
}
