# [PassiveFaceLiveness](https://docs.combateafraude.com/docs/mobile/introduction/home/#passivefaceliveness) - Ionic Plugin

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

Adicione o plugin no seu arquivo `ROOT_PROJECT/package.json`:

```json
"dependencies": {
    "passive-face-liveness-plugin": "https://github.com/combateafraude/Ionic/archive/refs/tags/passive-face-liveness-v6.1.0.tar.gz"
}
```

Após, execute:
1. npm install
2. ionic capacitor build < platform > [ options ]

## Importando

```typescript

import {PassiveFaceLiveness} from 'passive-face-liveness-plugin';

```

## Utilizando 
```typescript

    let passiveFaceLivenessor = new PassiveFaceLiveness();
    passiveFaceLivenessor.setMobileToken = '<your mobile token>';

    const response = await passiveFaceLivenessor.start();

    if(response.result == "SUCCESS"){
      // Sucesso
    }else if(response.result == "FAILURE"){
      // Falha. Confira reponse.type e response.message
    }else{
      // Usuário fechou a tela
    }

```


### Customizações gerais

| PassiveFaceLiveness |
| --------- |
| `.setPeopleId(String peopleId)`<br><br>CPF do usuário que está utilizando o plugin à ser usado para detecção de fraudes via analytics |
| `.setAnalyticsSettings(bool useAnalytics)`<br><br>Habilita/desabilita a coleta de dados para maximização da informação antifraude. O padrão é `true` |
| `.enableSound(bool enable)`<br><br>Habilita/desabilita os sons. O padrão é `true` |
| `.setNetworkSettings(int requestTimeout)`<br><br>Altera as configurações de rede padrão. O padrão é `60` segundos |
| `.setShowPreview(ShowPreview showPreview)`<br><br> Preview para verificação de qualidade da foto |
| `.setAndroidSettings(AndroidSettings androidSettings)`<br><br>Customizações somente aplicadas em Android |
| `.setIosSettings(IosSettings iosSettings)`<br><br>Customizações somente aplicadas em iOS |
| `.setCaptureMode(CaptureMode captureMode )`<br><br> Define as configurações de captura |
| `.setEyesClosedSettings(enable: boolean, threshold?:number )`<br><br> Permite customizar as configurações de validação de olhos abertos do SDK. O método espera como parâmetro `enable` para habilitar ou não a validação, e `threshold`, valor entre 0.0 e 1.0 |


| CaptureMode |
| --------- |
| `videoCapture: VideoCapture(use: boolean, time: number) `<br><br>Configura a captura por vídeo |
| `imageCapture: ImageCapture(use: boolean, beforePictureMillis: number, afterPictureMillis: number)`<br><br>Configura a captura por foto |

##### Exemplo de uso
```typescript
    let imageCapture = new ImageCapture({use: true});
    let captureMode = new CaptureMode({imageCapture: imageCapture});
    passiveFaceLiveness.setCaptureMode = captureMode;
```

| ShowPreview |
| --------- |
| `bool show`<br><br>Habilita/Desabilita preview |
| `String title`<br><br>Título |
| `String subTitle`<br><br>Subtítulo |
| `String confirmLabel`<br><br>Texto do botão de confirmação |
| `String retryLabel`<br><br>Texto do botão de capturar novamente |

#### Android

| AndroidSettings constructor |
| --------- |
| `PassiveFaceLivenessCustomizationAndroid customization`<br><br>Customização do layout em Android da activity |
| `CaptureSettings captureSettings`<br><br>Configuraçōes de tempos de estabilização para a captura da selfie |
| `SensorSettingsAndroid sensorSettings`<br><br>Customização das configurações dos sensores de captura |
| `int showButtonTime`<br><br>Altera o tempo para a exibição do botão de captura manual. O padrão é `20000` milisegundos |
| `bool useEmulator`<br><br>Permite habilitar/desabilitar o uso de dispositivos emulados no SDK, recomendamos desabilitar o uso dos emuladores por questões de segurança. O padrão é `false` |
| `bool useRoot`<br><br>Permite habilitar/desabilitar o uso de dispositivos com root no SDK, recomendamos desabilitar o uso desses dispositivos por questões de segurança. O padrão é `false` |
| `bool useDeveloperMode`<br><br>Permite habilitar/desabilitar o uso de dispositivos com o modo de desenvolvedor Android ativado. Recomendamos desabilitar o uso desses dispositivos por questões de segurança. O padrão é `False` |
| `bool useAdb`<br><br>Permite habilitar/desabilitar o uso do modo de depuração Android Debug Bridge (ADB). Recomendamos desabilitar o uso desses dispositivos por questões de segurança. O padrão é `False` |
| `bool useDebug`<br><br>Permite o uso do app em modo debug quando true. Não é recomendado ativar esta opção, utilize apenas para fins de testes.	 O padrão é `False` |
| `bool enableBrightnessIncrease`<br><br>Habilita/desabilita o incremento de brilho do dispositivo do dispositivo na abertura do SDK |

##### Exemplo de uso
```typescript
   let passiveFaceLiveness = new PassiveFaceLiveness();

   passiveFaceLiveness.setAndroidSettings = new AndroidSettings({useEmulator: false});
```


| PassiveFaceLivenessCustomizationAndroid constructor |
| --------- |
| `String styleResIdName`<br><br>Nome do style resource que define as cores do SDK. Por exemplo, caso deseje mudar as cores do SDK, crie um style em `ROOT_PROJECT/android/app/src/main/res/values/styles.xml` com o nome `R.style.my_custom_style` seguindo o [template](https://gist.github.com/kikogassen/4b57db7139034ea2e85ea798eb88d248) e parametrize "my_custom_style" |
| `String layoutResIdName`<br><br>Nome do layout resource que substituirá o layout padrão do SDK. Por exemplo, caso deseje mudar o layout do SDK, crie um layout em `ROOT_PROJECT/android/app/src/main/res/layout/my_custom_layout.xml` seguindo o [template](https://gist.github.com/dbseitenfus/b4f4cb601ba854b0c041625ed75af0b4) e parametrize "my_custom_layout" |
| `String greenMaskResIdName`<br><br>Nome do drawable resource à substituir a máscara verde padrão. **Caso for usar este parâmetro, use uma máscara com a mesma área de corte, é importante para o algoritmo de detecção**. Por exemplo, salve a imagem da máscara em `ROOT_PROJECT/android/app/src/main/res/drawable/my_custom_green_mask.png` e parametrize "my_custom_green_mask" |
| `String redMaskResIdName`<br><br>Nome do drawable resource à substituir a máscara vermelha padrão. **Caso for usar este parâmetro, use uma máscara com a mesma área de corte, é importante para o algoritmo de detecção**. Por exemplo, salve a imagem da máscara em `ROOT_PROJECT/android/app/src/main/res/drawable/my_custom_red_mask.png` e parametrize "my_custom_red_mask" |
| `String whiteMaskResIdName`<br><br>Nome do drawable resource à substituir a máscara branca padrão. **Caso for usar este parâmetro, use uma máscara com a mesma área de corte, é importante para o algoritmo de detecção**. Por exemplo, salve a imagem da máscara em `ROOT_PROJECT/android/app/src/main/res/drawable/my_custom_white_mask.png` e parametrize "my_custom_white_mask" |
| `MaskType maskType`<br><br>Define o tipo de máscara utilizada nas capturas. Existem dois tipos: MaskType.DEFAULT, com o padrão pontilhado e MaskType.NONE, que remove completamente a máscara. O padrão é `MaskType.DEFAULT` |

| CaptureSettings constructor |
| --------- |
| `int beforePictureMillis`<br><br>Duração em milissegundos entre a primeira detecção do rosto e a efetiva captura da foto |
| `int afterPictureMillis`<br><br>Duração em milissegundos entre a captura da foto e o envio para o servidor para o mantimento do rosto e dos sensores válidos |


| SensorSettingsAndroid constructor |
| --------- |
| `SensorStabilitySettingsAndroid sensorStabilitySettings`<br><br>Configurações do sensor de orientação à ser aplicado em todos os passos do SDK |

| SensorStabilitySettingsAndroid constructor |
| --------- |
| `String messageResourceIdName`<br><br>Nome do string resource à ser mostrado quando o celular não estiver estável. A mensagem padrão é "Mantenha o celular parado". Por exemplo, caso deseje mostrar a String "Teste", crie uma String em `ROOT_PROJECT/android/app/src/main/res/values/strings.xml` com o nome `R.string.my_custom_stability_string` e valor "Teste" e parametrize "my_custom_stability_string" |
| `int stabilityStabledMillis`<br><br>Quantos milissegundos o celular deve se manter no limiar correto para ser considerado estável. O padrão é `2000` ms |
| `double stabilityThreshold`<br><br>Limiar inferior entre estável/instável, em variação de m/s² entre as últimas duas coletas do sensor. O padrão é `0.5` m/s² |

#### iOS

| IosSettings constructor |
| --------- |
| `PassiveFaceLivenessCustomizationIos customization`<br><br>Customização visual do SDK |
| `int beforePictureMillis`<br><br>Duração em milissegundos entre a primeira detecção do rosto e a efetiva captura da foto |
| `SensorStabilitySettingsIos sensorStability`<br><br>Configurações do sensor de estabilidade à ser aplicado no SDK |

| PassiveFaceLivenessCustomizationIos constructor |
| --------- |
| `String colorHex`<br><br>Cor tema do SDK. Por exemplo, caso deseje usar a cor preta, utilize "#000000" |
| `String greenMaskImageName`<br><br>Nome da imagem à substituir a máscara verde padrão. Lembre de adicionar a imagem em `Assets Catalog Document` no seu projeto do XCode |
| `String whiteMaskImageName`<br><br>Nome da imagem à substituir a máscara branca padrão. Lembre de adicionar a imagem em `Assets Catalog Document` no seu projeto do XCode |
| `String redMaskImageName`<br><br>Nome da imagem à substituir a máscara vermelha padrão. Lembre de adicionar a imagem em `Assets Catalog Document` no seu projeto do XCode |
| `String closeImageName`<br><br>Nome da imagem à substituir o botão de fechar o SDK. Lembre de adicionar a imagem em `Assets Catalog Document` no seu projeto do XCode |
| `bool showStepLabel`<br><br>Flag que indica se deseja mostrar o label do passo atual |
| `bool showStatusLabel`<br><br>Flag que indica se deseja mostrar o label do status atual |

| SensorStabilitySettingsAndroid constructor |
| --------- |
| `String message`<br><br>String à ser mostrada quando o celular não estiver estável |
| `double stabilityThreshold`<br><br>Limiar inferior entre estável/instável, em variação de m/s² entre as últimas duas coletas do sensor. O padrão é `0.3` m/s² |

### Coletando o resultado

O objeto de retorno do DocumentDetector terá o atributo `result` que contém uma string `SUCCESS`, `FAILURE` ou `CLOSED`. O retorno terá o padrão PassiveFaceLivenessSuccess, PassiveFaceLivenessFailure e PassiveFaceLivenessClosed, respectivamente, para cada um dos casos.

#### PassiveFaceLivenessSuccess

| Campo |
| --------- |
| `String imagePath`<br><br>Endereço completo da imagem no dispositivo |
| `String imageUrl`<br><br>URL da imagem armazenada temporariamente nos servidores da CAF |
| `String signedResponse`<br><br>Resposta assinada do servidor da CAF que confirmou que a selfie capturada possui um rosto verdadeiro (não é foto de foto ou vídeo). Utilize esse parâmetro caso queira uma camada extra de segurança verificando se a assinatura da resposta não está quebrada, provocada por uma interceptação da requisição. Se estiver quebrada, há um grande indício de interceptação da requisição |
| `String trackingId`<br><br>Identificador dessa execução em nossos servidores. Se possível, salve este campo e mande-o junto para nossa API. Assim, teremos mais dados de como o usuário se comportou durante a execução | Será nulo se o usuário configurar useAnalytics = false ou as chamadas de analytics não funcionarem |

#### PassiveFaceLivenessFailure

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


#### PassiveFaceLivenessClosed
Objeto vazio indicando fechamento da tela de captura pelo usuário.
