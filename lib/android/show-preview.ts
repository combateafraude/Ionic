export class ShowPreview {
    private title: string;
    private subTitle: string;
    private confirmLabel: string;
    private retryLabel: string;
    private show: boolean;
  
    constructor(show: boolean,
        title: string, subTitle: string, confirmLabel: string, retryLabel: string){
          this.title = title;
          this.subTitle = subTitle;
          this.confirmLabel = confirmLabel;
          this.retryLabel = retryLabel;
          this.show = show;
        }
  }
  