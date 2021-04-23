// import { WebPlugin } from '@capacitor/core';
// import { IonicPluginPlugin } from './definitions';

export * from '../lib/document-detector';

// import { DocumentDetector as sdk } from '../lib/document-detector';

// export { DocumentDetector as default } from '../lib/document-detector';

// if (window) {
//     (<any>window).DocumentDetector = sdk;
// }

// import { DocumentDetector } from '../lib/document-detector';

// export class IonicPluginWeb extends WebPlugin implements IonicPluginPlugin {
//   constructor() {
//     super({
//       name: 'IonicPlugin',
//       platforms: ['web']
//     });
//   }

//   async echo(options: { value: string }): Promise<{ value: string }> {
//     console.log('ECHO', options);
//     return options;
//   }

//   async getContacts(filter: string): Promise<{ results: any[] }> {
//     console.log('filter: ', filter);
//     return {
//       results: [{
//         firstName: 'Dummy',
//         lastName: 'Entry',
//         telephone: '123456'
//       }]
//     };
//   }
//   async start(options: { value: string }): Promise<{ results: any[] }> {
//     console.log('filter: ', options);
//     return {
//       results: [{
//         firstName: 'Dummy',
//         lastName: 'Entry',
//         telephone: '123456'
//       }]
//     };
//   }
// }

// const DocumentDetector = new sdk();
// export { DocumentDetector };
// import { registerWebPlugin } from '@capacitor/core';
// registerWebPlugin(DocumentDetector);

// if (window) {
//   (<any>window).IonicPluginWeb = IonicPluginWeb;
// }
