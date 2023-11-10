import { registerPlugin } from '@capacitor/core';

import type { FaceAuthenticatorPlugin, FaceLivenessPlugin } from './definitions';

const FaceAuthenticator = registerPlugin<FaceAuthenticatorPlugin>('FaceAuthenticator');
const FaceLiveness = registerPlugin<FaceLivenessPlugin>('FaceLiveness');


export * from './definitions';
export { FaceAuthenticator, FaceLiveness };

