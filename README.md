# teste-plugin

teste

## Install

```bash
npm install teste-plugin
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

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#configureoptions">ConfigureOptions</a></code> |

--------------------


### authenticate(...)

```typescript
authenticate(options: AuthenticateOptions, callback: AuthenticateCallback) => Promise<string>
```

| Param          | Type                                                                  |
| -------------- | --------------------------------------------------------------------- |
| **`options`**  | <code><a href="#authenticateoptions">AuthenticateOptions</a></code>   |
| **`callback`** | <code><a href="#authenticatecallback">AuthenticateCallback</a></code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### Interfaces


#### ConfigureOptions

| Prop                         | Type                                                      |
| ---------------------------- | --------------------------------------------------------- |
| **`mobileToken`**            | <code>string</code>                                       |
| **`stage`**                  | <code><a href="#cafstagevalues">CAFStageValues</a></code> |
| **`filter`**                 | <code><a href="#filtervalues">FilterValues</a></code>     |
| **`useCustomLoadingScreen`** | <code>boolean</code>                                      |


#### AuthenticateOptions

| Prop           | Type                |
| -------------- | ------------------- |
| **`personId`** | <code>string</code> |


#### LoadEvent

| Prop       | Type                               |
| ---------- | ---------------------------------- |
| **`type`** | <code>'loaded' \| 'loading'</code> |


#### SuccessEvent

| Prop       | Type                                                                        |
| ---------- | --------------------------------------------------------------------------- |
| **`type`** | <code>'success'</code>                                                      |
| **`data`** | <code><a href="#authenticatesuccessdata">AuthenticateSuccessData</a></code> |


#### AuthenticateSuccessData

| Prop                 | Type                |
| -------------------- | ------------------- |
| **`signedResponse`** | <code>string</code> |


### Type Aliases


#### CAFStageValues

<code><a href="#dictionaryvalues">DictionaryValues</a>&lt;typeof CAFStage&gt;</code>


#### DictionaryValues

<code>Type[keyof Type]</code>


#### FilterValues

<code><a href="#dictionaryvalues">DictionaryValues</a>&lt;typeof Filter&gt;</code>


#### AuthenticateCallback

<code>(events: <a href="#loadevent">LoadEvent</a> | <a href="#successevent">SuccessEvent</a> | null, err?: any): void</code>

</docgen-api>
