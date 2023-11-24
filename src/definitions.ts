type DictionaryValues<Type> = Type[keyof Type];

export const CAFStage = Object.freeze({
  PROD: 'prod',
  BETA: 'beta'
} as const);

export const Filter = Object.freeze({
  NATURAL: 'natural',
  LINE_DRAWING: 'line-drawing' 
} as const);

export const Time = Object.freeze({
  THREE_HOURS: 'three-hours',
  THIRTY_DAYS: 'thirty-days'
} as const);

export type CAFStageValues =  DictionaryValues<typeof CAFStage>;
export type FilterValues =  DictionaryValues<typeof Filter>;
export type TimeValues =  DictionaryValues<typeof Time>;

/**
 * Simple Load events that hints when there is a loding process happening.
 * 
 * The events following this interface are emmited from the onLoading and onLoaded
 * native callback methods.
 */
export interface LoadEvent {
  type: 'loaded' | 'loading';
}

/**
 * Success event emmited by the onSuccess native callback method.
 */
export interface SuccessEvent {
  type: 'success';
  data: SuccessData;
}

export interface SuccessData {
  /**
   * JWT containing the information related to the FaceAuthenticator/FaceLiveness.
   */
  signedResponse: string;
}

export interface FaceAuthenticatorPlugin {
  /**
   * Allows to cofigure the FaceAuthenticatorPlugin.
   * 
   * You only need to call it once, but you must call it before other methods in this class.
   * 
   * @throws {Error} Throws an error if the required options are not provided or if invalid values are passed.
   */
  Configure(options: ConfigureOptions): Promise<void>;

  /**
   * Allows to authenticate a person.
   * 
   * @param options Options to enable the authentication method to happen.
   * @param callback Allows you to specify a callback and receive the events or any error
   * that happens during the authentication process.
   * 
   * @returns {string} The CallbackID. For more information refer to: https://capacitorjs.com/docs/plugins/method-types#callback
   */
  authenticate(options: AuthenticateOptions, callback: GenericCallback): Promise<string>;
}

export interface FaceLivenessPlugin {
  /**
   * Allows to cofigure the FaceLivenessPlugin.
   * 
   * You only need to call it once, but you must call it before other methods in this class.
   * 
   * @throws {Error} Throws an error if the required options are not provided or if invalid values are passed.
   */
  Configure(options: ConfigureOptions): Promise<void>;

  /**
   * Start the FaceLiveness SDK.
   * 
   * @param options Options to start the SDK.
   * @param callback Allows you to specify a callback and receive the events or any error
   * that happens.
   * 
   * @returns {string} The CallbackID. For more information refer to: https://capacitorjs.com/docs/plugins/method-types#callback
   */
  startSDK(options: LivenessStartOptions, callback: GenericCallback): Promise<string>;
}

/**
 * Callback that allow handling of events that happens during the authentication/liveness process.
 * The error argument will be set when the onCancel and onError native callbacks execute. 
 */
export type GenericCallback = (events: LoadEvent | SuccessEvent | null, err?: any) => void;

export interface ConfigureOptions {
  /**
   * Token to configure the SDK with.
   */
  mobileToken: string;

  /**
   * Stage that is related to the token.
   * 
   * @default 'prod'
   */
  stage?: CAFStageValues;

  /**
   * Filter to be used.
   * 
   * @default 'line-drawing'
   */
  filter?: FilterValues;

  /**
   * Used to enable a default loading progressbar during loading events. You can set
   * your customized loading screen instead, using the {@link LoadEvent}.
   * 
   * @default false
   */
  useCustomLoadingScreen?: boolean;

  /**
   * Android Only
   * 
   * Used to enable screenshots during the SDK scan. Disabled by default for security reasons.
   * 
   * @default false
   */
  enableScreenshots?: boolean;

   /**
     * Used to configure an image URL expiration time.
     *
     * @default 'null'
     */

    imageUrlExpirationTime?: TimeValues;
}

export interface AuthenticateOptions {
  /**
   * Identification of the person (E.g.: CPF)
   */
  personId: string;
}

export interface LivenessStartOptions {
  /**
   * Identification of the person (E.g.: CPF)
   */
  personId: string;
}