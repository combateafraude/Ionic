import { registerPlugin } from '@capacitor/core';

import type { FaceAuthenticatorPlugin } from './definitions';

const FaceAuthenticator = registerPlugin<FaceAuthenticatorPlugin>('FaceAuthenticator');

export * from './definitions';
export { FaceAuthenticator };

