# PassiveFaceLiveness

Plugin que chama os SDKs nativos em [Android](https://docs.combateafraude.com/docs/mobile/android/passive-face-liveness/) e [iOS](https://docs.combateafraude.com/docs/mobile/ios/passive-face-liveness/). Caso tenha alguma dúvida, envie um email para o nosso [Head of Mobile](mailto:daniel.seitenfus@combateafraude.com)

Atualmente, os documentos suportados são RG, CNH, RNE e CRLV. Caso tenha alguma sugestão de outro documento, contate-nos!

# Políticas de privacidade e termos e condições de uso

Ao utilizar nosso plugin, certifique-se que você concorda com nossas [Políticas de privacidade](https://www.combateafraude.com/politicas/politicas-de-privacidade) e nossos [Termos e condições de uso](https://www.combateafraude.com/politicas/termos-e-condicoes-de-uso).

## Configurações

### Android

No arquivo `ROOT_PROJECT/android/app/build.gradle`, adicione:

``` gradle
android {

    ...

    dataBinding.enabled = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
```

Importe o pacote e chame o método add() dentro da inicialização em `android/app/src/main/java/io/ionic/starter/MainActivity.java`:

```java

import com.passive.PassiveFaceLivenessPlugin;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    registerPlugin(PassiveFaceLivenessPlugin.class);
  }
}

```

### iOS

No arquivo `ROOT_PROJECT/ios/App/Podfile`, adicione no final do arquivo:

``` swift
source 'https://github.com/combateafraude/iOS.git'
source 'https://cdn.cocoapods.org/' # ou 'https://github.com/CocoaPods/Specs' se o CDN estiver fora do ar
```

Por último, adicione a permissão de câmera no arquivo `ROOT_PROJECT/ios/App/Runner/Info.plist`:

```
<key>NSCameraUsageDescription</key>
<string>To read the documents</string>
```


### Ionic

In the file `ROOT_PROJECT/package.json` add:

```json
"dependencies": {
    "new-passive-face-liveness-plugin": "https://github.com/combateafraude/Ionic/archive/refs/tags/new-passive-face-liveness-v1.2.0.tar.gz"
}
```

Than, run:
1. npm install
2. ionic capacitor build < platform > [ options ]

## Importando

```typescript

import {PassiveFaceLiveness} from 'new-passive-face-liveness-plugin';

```

## Utilizando 
```typescript

    const faceLiveness = new PassiveFaceLiveness({mobileToken}, {personId});

    const response = await faceLiveness.start();

    if(response.result == "SUCCESS"){
      // Sucesso
    }else if(response.result == "FAILURE"){
      // Falha. Confira reponse.type e response.message
    }else{
      // Usuário fechou a tela
    }

```

### Coletando o resultado

O objeto de retorno do DocumentDetector terá o atributo `result` que contém uma string `SUCCESS`, `FAILURE` ou `CLOSED`. O retorno terá o padrão PassiveFaceLivenessSuccess, PassiveFaceLivenessFailure e PassiveFaceLivenessClosed, respectivamente, para cada um dos casos.

#### PassiveFaceLivenessSuccess

| Return |
| --------- |
| `String imageUrl`<br><br>The url from the image that was captured. |
| `Boolean isAlive`<br><br>Liveness return, use this to check if your user it's aproved by the liveness. |
| `String userId`<br><br>Return the id of the user that used the SDK.|

#### PassiveFaceLivenessFailure

| Return |
| --------- |
| `String errorMessage`<br><br>In case of error in the request, return the error.|

#### PassiveFaceLivenessClosed
Objeto vazio indicando fechamento da tela de captura pelo usuário.
