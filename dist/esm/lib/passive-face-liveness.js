var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { PassiveFaceLivenessSuccess } from './result/passive-face-liveness-success';
import { PassiveFaceLivenessFailure } from './result/passive-face-liveness-failure';
import { PassiveFaceLivenessClosed } from './result/passive-face-liveness-closed';
import { Plugins } from '@capacitor/core';
const { PassiveFaceLivenessPlugin } = Plugins;
export class PassiveFaceLiveness {
    constructor(mobileToken, personId) {
        this.mobileToken = mobileToken;
        this.personId = personId;
    }
    start() {
        return __awaiter(this, void 0, void 0, function* () {
            var param = JSON.stringify(this);
            const result = (yield PassiveFaceLivenessPlugin.start({ builder: param })).results;
            if (result.success == null) {
                return new PassiveFaceLivenessClosed();
            }
            else if (result.success) {
                return new PassiveFaceLivenessSuccess(result.signedResponse);
            }
            else {
                return new PassiveFaceLivenessFailure(result.errorMessage);
            }
        });
    }
}
//# sourceMappingURL=passive-face-liveness.js.map