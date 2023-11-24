# @caf/capacitor-faces

## Install

```bash
npm install @caf/capacitor-faces
npx cap sync
```

## API

<docgen-index>

* [`Configure(...)`](#configure)
* [`authenticate(...)`](#authenticate)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### Configure(...)

```typescript
Configure(options: ConfigureOptions) => Promise<void>
```

Allows to cofigure the FaceAuthenticatorPlugin.

You only need to call it once, but you must call it before other methods in this class.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#configureoptions">ConfigureOptions</a></code> |

--------------------


### authenticate(...)

```typescript
authenticate(options: AuthenticateOptions, callback: GenericCallback) => Promise<string>
```

Allows to authenticate a person.

| Param          | Type                                                                | Description                                                                                                          |
| -------------- | ------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| **`options`**  | <code><a href="#authenticateoptions">AuthenticateOptions</a></code> | Options to enable the authentication method to happen.                                                               |
| **`callback`** | <code><a href="#genericcallback">GenericCallback</a></code>         | Allows you to specify a callback and receive the events or any error that happens during the authentication process. |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### Interfaces


#### ConfigureOptions

| Prop                         | Type                                                      | Description                                                                                                                                                                   | Default                     |
| ---------------------------- | --------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`mobileToken`**            | <code>string</code>                                       | Token to configure the SDK with.                                                                                                                                              |                             |
| **`stage`**                  | <code><a href="#cafstagevalues">CAFStageValues</a></code> | Stage that is related to the token.                                                                                                                                           | <code>'prod'</code>         |
| **`filter`**                 | <code><a href="#filtervalues">FilterValues</a></code>     | Filter to be used.                                                                                                                                                            | <code>'line-drawing'</code> |
| **`useCustomLoadingScreen`** | <code>boolean</code>                                      | Used to enable a default loading progressbar during loading events. You can set your customized loading screen instead, using the {@link <a href="#loadevent">LoadEvent</a>}. | <code>false</code>          |
| **`enableScreenshots`**      | <code>boolean</code>                                      | Android Only Used to enable screenshots during the SDK scan. Disabled by default for security reasons.                                                                        | <code>false</code>          |
| **`imageUrlExpirationTime`** | <code><a href="#timevalues">TimeValues</a></code>         | Used to configure an image URL expiration time.                                                                                                                               | <code>'null'</code>         |


#### AuthenticateOptions

| Prop           | Type                | Description                              |
| -------------- | ------------------- | ---------------------------------------- |
| **`personId`** | <code>string</code> | Identification of the person (E.g.: CPF) |


#### LoadEvent

Simple Load events that hints when there is a loding process happening.

The events following this interface are emmited from the onLoading and onLoaded
native callback methods.

| Prop       | Type                               |
| ---------- | ---------------------------------- |
| **`type`** | <code>'loaded' \| 'loading'</code> |


#### SuccessEvent

Success event emmited by the onSuccess native callback method.

| Prop       | Type                                                |
| ---------- | --------------------------------------------------- |
| **`type`** | <code>'success'</code>                              |
| **`data`** | <code><a href="#successdata">SuccessData</a></code> |


#### SuccessData

| Prop                 | Type                | Description                                                                   |
| -------------------- | ------------------- | ----------------------------------------------------------------------------- |
| **`signedResponse`** | <code>string</code> | JWT containing the information related to the FaceAuthenticator/FaceLiveness. |


### Type Aliases


#### CAFStageValues

<code><a href="#dictionaryvalues">DictionaryValues</a>&lt;typeof CAFStage&gt;</code>


#### DictionaryValues

<code>Type[keyof Type]</code>


#### FilterValues

<code><a href="#dictionaryvalues">DictionaryValues</a>&lt;typeof Filter&gt;</code>


#### TimeValues

<code><a href="#dictionaryvalues">DictionaryValues</a>&lt;typeof Time&gt;</code>


#### GenericCallback

Callback that allow handling of events that happens during the authentication/liveness process.
The error argument will be set when the onCancel and onError native callbacks execute.

<code>(events: <a href="#loadevent">LoadEvent</a> | <a href="#successevent">SuccessEvent</a> | null, err?: any): void</code>

</docgen-api>
