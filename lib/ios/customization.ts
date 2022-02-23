export class FaceAuthenticatorCustomizationIos {
    private colorHex: string;
    private greenMaskImageName: string;
    private whiteMaskImageName: string;
    private redMaskImageName: string;
    private closeImageName: string;
    private showStepLabel: boolean;
    private showStatusLabel: boolean;
    constructor(colorHex: string, greenMaskImageName: string, whiteMaskImageNam: string, redMaskImageName: string,
         closeImageName: string, showStepLabel: boolean, showStatusLabel: boolean){
             this.colorHex = colorHex;
             this.greenMaskImageName = greenMaskImageName;
             this.whiteMaskImageName = whiteMaskImageNam;
             this.redMaskImageName = redMaskImageName;
             this.closeImageName = closeImageName;
             this.showStepLabel = showStepLabel;
             this.showStatusLabel = showStatusLabel;
    }
  }