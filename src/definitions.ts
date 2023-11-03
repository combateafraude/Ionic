type DictionaryValues<Type> = Type[keyof Type];

export const CAFStage = Object.freeze({
  PROD: 'prod',
  BETA: 'beta'
} as const);

export const Filter = Object.freeze({
  NATURAL: 'natural',
  LINE_DRAWING: 'line-drawing' 
} as const);


export type CAFStageValues =  DictionaryValues<typeof CAFStage>;
export type FilterValues =  DictionaryValues<typeof Filter>;

export interface LoadEvent {
  type: 'loaded' | 'loading';
}

export interface SuccessEvent {
  type: 'success';
  data: AuthenticateSuccessData;
}

export interface AuthenticateSuccessData {
  signedResponse: string;
} 

export interface FaceAuthenticatorPlugin {
  Configure(options: ConfigureOptions): Promise<void>;
  authenticate(options: AuthenticateOptions, callback: AuthenticateCallback): Promise<string>;
}

export type AuthenticateCallback = (events: LoadEvent | SuccessEvent | null, err?: any) => void;

export interface ConfigureOptions {
  mobileToken: string;
  stage?: CAFStageValues;
  filter?: FilterValues;
  useCustomLoadingScreen?: boolean;
}

export interface AuthenticateOptions {
  personId: string;
}