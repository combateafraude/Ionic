import { FaceAuthenticatorSuccess } from './result/face-authenticator-success';
import { FaceAuthenticatorFailure } from './result/face-authenticator-failure';
import { FaceAuthenticatorClosed } from './result/face-authenticator-closed';
import { Plugins } from '@capacitor/core';
const { FaceAuthenticatorPlugin } = Plugins;

export class FaceAuthenticator {
  private mobileToken: string;
  private personId: string;


  constructor(mobileToken: string, personId: string) { 
    this.mobileToken = mobileToken;
    this.personId = personId;
  }

  async start() {

    var param = JSON.stringify(this);

    const result = (await FaceAuthenticatorPlugin.start({ builder: param })).results;

    if (result.success == null) {
      return new FaceAuthenticatorClosed();
    } else if (result.success) {
      return new FaceAuthenticatorSuccess(result.isMatch, result.isAlive, result.userId, )
    } else {
      return new FaceAuthenticatorFailure(result.errorMessage)
    }

  }

}
