import { PassiveFaceLivenessSuccess } from './result/passive-face-liveness-success';
import { PassiveFaceLivenessFailure } from './result/passive-face-liveness-failure';
import { PassiveFaceLivenessClosed } from './result/passive-face-liveness-closed';

import { Plugins } from '@capacitor/core';
const { PassiveFaceLivenessPlugin } = Plugins;


export class PassiveFaceLiveness {
  private mobileToken: string;
  private personId: string;

  constructor(mobileToken: string, personId: string,) {
    this.mobileToken = mobileToken;
    this.personId = personId;
   }

  async start() {

    var param = JSON.stringify(this);

    const result = (await PassiveFaceLivenessPlugin.start({ builder: param })).results;

    if (result.success == null) {
      return new PassiveFaceLivenessClosed();
    } else if (result.success) {
      return new PassiveFaceLivenessSuccess(result.signedResponse)
    } else {
      return new PassiveFaceLivenessFailure(result.errorMessage)
    }

  }

}
