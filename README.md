# [FaceAuthenticatorPlugin](https://docs.combateafraude.com/docs/mobile/introduction/home/#faceauthenticator-autentica%C3%A7%C3%A3o-facial) - Ionic Plugin

Plugin que chama os SDKs nativos em [Android](https://docs.combateafraude.com/docs/mobile/android/face-authenticator/) e [iOS](https://docs.combateafraude.com/docs/mobile/ios/face-authenticator/). Caso tenha alguma dúvida, envie um email para o nosso [Head of Mobile](mailto:daniel.seitenfus@combateafraude.com)

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
    "face-authenticator-plugin": "https://github.com/combateafraude/Ionic/archive/refs/tags/face-authenticator-v2.6.0.tar.gz"
}
```

Após, execute:
1. npm install
2. ionic capacitor build < platform > [ options ]

## Importando

```typescript

import { FaceAuthenticator } from 'face-authenticator-plugin';

```

## Utilizando 
```typescript

    let faceAuthenticator = await new FaceAuthenticator();

    faceAuthenticator.setMobileToken = '<mobile token>'
    faceAuthenticator.setPeopleId = '<cpf>';

    //Habilitar captura por vídeo
    let captureMode = new CaptureMode ({videoCapture: new VideoCapture ({use: true, time: 3})});
    faceAuthenticator.setCaptureMode = captureMode;

    const response = await faceAuthenticator.start();

    if(response.result == "SUCCESS"){
      // Sucesso. Confira response.isAuthenticated e response.signedResponse
    }else if(response.result == "FAILURE"){
      // Falha. Confira reponse.type e response.message
    }else{
      // Usuário fechou a tela.
    }

```


### Customizações gerais

| FaceAuthenticator |
| --------- |
| `.setPeopleId(String peopleId)`<br><br>CPF do usuário que está utilizando o plugin à ser usado para detecção de fraudes via analytics |
| `.setAnalyticsSettings(bool useAnalytics)`<br><br>Habilita/desabilita a coleta de dados para maximização da informação antifraude. O padrão é `true` |
| `.enableSound(bool enable)`<br><br>Habilita/desabilita os sons. O padrão é `true` |
| `.setNetworkSettings(int requestTimeout)`<br><br>Altera as configurações de rede padrão. O padrão é `60` segundos |
| `.setAndroidSettings(AndroidSettings androidSettings)`<br><br>Customizações somente aplicadas em Android |
| `.setIosSettings(IosSettings iosSettings)`<br><br>Customizações somente aplicadas em iOS |
| `.setCaptureMode(CaptureMode captureMode)`<br><br> Define as configurações de captura |

| CaptureMode |
| --------- |
| `videoCapture: VideoCapture(use: boolean, time: number) `<br><br>Configura a captura por vídeo |
| `imageCapture: ImageCapture(use: boolean, beforePictureMillis: number, afterPictureMillis: number)`<br><br>Configura a captura por foto |

##### Exemplo de uso
```typescript
    let imageCapture = new ImageCapture({use: true});
    let captureMode = new CaptureMode({imageCapture: imageCapture});
    faceAuthenticator.setCaptureMode = captureMode;
```


### Coletando o resultado

O objeto de retorno do FaceAuthenticator terá o atributo `result` que contém uma string `SUCCESS`, `FAILURE` ou `CLOSED`. O retorno terá o padrão PassiveFaceLivenessSuccess, PassiveFaceLivenessFailure e PassiveFaceLivenessClosed, respectivamente, para cada um dos casos.

#### FaceAuthenticatorSuccess

| Campo |
| --------- |
| `Boolean isAuthenticated`<br><br>Endereço completo da imagem no dispositivo |
| `String signedResponse`<br><br>Resposta assinada do servidor da CAF que confirmou que a selfie capturada possui um rosto verdadeiro (não é foto de foto ou vídeo). Utilize esse parâmetro caso queira uma camada extra de segurança verificando se a assinatura da resposta não está quebrada, provocada por uma interceptação da requisição. Se estiver quebrada, há um grande indício de interceptação da requisição |
| `String trackingId`<br><br>Identificador dessa execução em nossos servidores. Se possível, salve este campo e mande-o junto para nossa API. Assim, teremos mais dados de como o usuário se comportou durante a execução | Será nulo se o usuário configurar useAnalytics = false ou as chamadas de analytics não funcionarem |

#### FaceAuthenticatorFailure

| Campo |
| --------- |
| `String message`<br><br>Mensagem amigável explicando o motivo da falha do SDK |
| `String type`<br><br>Tipo de falha que encerrou o SDK |

Os tipos de falha existentes são:
- `InvalidTokenReason`: quando o token informado é inválido. Não deve ocorrer em um ambiente de produção;
- `PermissionReason`: quando alguma permissão obrigatória não foi concedida pelo usuário. Só ocorrerá em um ambiente de produção se o seu app não solicitar ao seu usuário ou o mesmo desabilitar manualmente antes de iniciar;
- `NetworkReason`: falha de conexão com o servidor. Ocorrerá em produção se o dispositivo do usuário estiver sem internet;
- `ServerReason`: falha em alguma requisição com nossos servidores. Ocorrerá em produção somente no caso de uma falha nossa;
- `SecurityReason`: quando o dispositivo não é seguro para executar o SDK. Se esta falha ocorrer, avise-nos;
- `StorageReason`: quando o dispositivo não possui espaço suficiente para a captura de alguma foto. Pode ocorrer em produção;
- `LibraryReason`: quando alguma falha interna impossibilitou a execução do SDK. Pode ocorrer devico à erros de configuração do projeto, não deve ocorrer em produção;


#### FaceAuthenticatorClosed
Objeto vazio indicando fechamento da tela de captura pelo usuário.
