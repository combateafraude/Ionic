# [FaceAuthenticatorPlugin](https://docs.caf.io/sdks/ionic/getting-started) - Ionic Plugin

Plugin que chama os SDKs nativos em [Android](https://docs.caf.io/sdks/android/getting-started/faceauthenticator) e [iOS](https://docs.caf.io/sdks/ios/getting-started/newfaceauthenticator).

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

  rootProject.allprojects {
    repositories {
        maven { url "https://repo.combateafraude.com/android/release" }
        maven { url 'https://raw.githubusercontent.com/iProov/android/master/maven/' }
    }
  }
}
```

Importe o pacote e chame o método add() dentro da inicialização em `android/app/src/main/java/io/ionic/starter/MainActivity.java`:

```java

import com.authenticator.FaceAuthenticatorPlugin;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    registerPlugin(FaceAuthenticatorPlugin.class);
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

Adicione o plugin no seu arquivo `ROOT_PROJECT/package.json`:

```json
"dependencies": {
    "new-face-authenticator-plugin": "https://github.com/combateafraude/Ionic/archive/refs/tags/new-face-authenticator-plugin-v1.0.0.tar.gz"
}
```

Após, execute:
1. npm install
2. ionic capacitor build < platform > [ options ]

## Importando

```typescript

import { FaceAuthenticator } from 'new-face-authenticator-plugin';

```

## Utilizando 
```typescript

    const faceLiveness = new FaceAuthenticator(this.mobileToken, '038871233334');

    const response = await faceLiveness.start();

      this.results = [];

      if(response.result == 'SUCCESS'){
        this.results.push('result: SUCCESS');
        this.results.push('isMatch: '+response.isMatch);
        this.results.push('isAlive: '+response.isAlive);
        this.results.push('userId: '+response.userId);

      }else if(response.result == 'FAILURE'){
        this.results.push('result: FAILURE');
        this.results.push('errorMessage: '+response.errorMessage);
      }else{
        this.results.push('result: CANCEL');
      }

```

### Coletando o resultado

O objeto de retorno do FaceAuthenticator terá o atributo `result` que contém uma string `SUCCESS`, `FAILURE` ou `CLOSED`. O retorno terá o padrão PassiveFaceLivenessSuccess, PassiveFaceLivenessFailure e PassiveFaceLivenessClosed, respectivamente, para cada um dos casos.

#### FaceAuthenticatorSuccess

| Campo |
| --------- |
| `String imageUrl`<br><br> URL da imagem armazenada temporariamente nos servidores da CAF.|
| `bool isAlive`<br><br> Retorno do Liveness, use este retorno para validar se o usuário foi aprovado ou não pelos serviços da caf. |
| `String userId`<br><br> Identificador interno do usuário. |

#### FaceAuthenticatorFailure

| Campo |
| --------- |
| `String errorMessage`<br><br>Mensagem explicando o motivo da falha do SDK.|

#### FaceAuthenticatorClosed
Objeto vazio indicando fechamento da tela de captura pelo usuário.
