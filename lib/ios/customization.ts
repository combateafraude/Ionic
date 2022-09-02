export class PassiveFaceLivenessCustomizationIos {
  private colorHex?: string;
  private greenMaskImageName?: string;
  private whiteMaskImageName?: string;
  private redMaskImageName?: string;
  private closeImageName?: string;
  private showStepLabel?: boolean;
  private showStatusLabel?: boolean;
  private buttonSize?: number;
  private buttonContentMode?: string;

  constructor(
    options: {
      colorHex?: string,
      greenMaskImageName?: string,
      whiteMaskImageNam?: string,
      redMaskImageName?: string,
      closeImageName?: string,
      showStepLabel?: boolean,
      showStatusLabel?: boolean,
      buttonSize?: number,
      buttonContentMode?: string
    }) {
    this.colorHex = options?.colorHex;
    this.greenMaskImageName = options?.greenMaskImageName;
    this.whiteMaskImageName = options?.whiteMaskImageNam;
    this.redMaskImageName = options?.redMaskImageName;
    this.closeImageName = options?.closeImageName;
    this.showStepLabel = options?.showStepLabel;
    this.showStatusLabel = options?.showStatusLabel;
    this.buttonSize = options?.buttonSize;
    this.buttonContentMode = options?.buttonContentMode;
  }
}