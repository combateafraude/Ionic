var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { DocumentDetectorStep } from './document-detector-step';
import { Plugins } from '@capacitor/core';
export { DocumentDetectorStep };
export { DocumentType } from './document-type';
const { DocumentDetectorPlugin } = Plugins;
export class DocumentDetector {
    constructor() { }
    set setMobileToken(mobileToken) {
        this.mobileToken = mobileToken;
    }
    set setPeopleId(peopleId) {
        this.peopleId = peopleId;
    }
    set setUseAnalytics(useAnalytics) {
        this.useAnalytics = useAnalytics;
    }
    set setPopup(popup) {
        this.popup = popup;
    }
    set setSound(sound) {
        this.sound = sound;
    }
    set setRequestTimeout(requestTimeout) {
        this.requestTimeout = requestTimeout;
    }
    set setShowDelay(showDelay) {
        this.showDelay = showDelay;
    }
    set setDelay(delay) {
        this.delay = delay;
    }
    set setAutoDetection(autoDetection) {
        this.autoDetection = autoDetection;
    }
    set setDocumentDetectorSteps(documentDetectorSteps) {
        this.documentDetectorSteps = documentDetectorSteps;
    }
    setCurrentStepDoneDelay(showDelay, delay) {
        this.showDelay = showDelay;
        this.delay = delay;
    }
    set setShowPreview(showPreview) {
        this.showPreview = showPreview;
    }
    set setAndroidSettings(androidSettings) {
        this.androidSettings = androidSettings;
    }
    set setIosSettings(iosSettings) {
        this.iosSettings = iosSettings;
    }
    start() {
        return __awaiter(this, void 0, void 0, function* () {
            var builder = JSON.stringify(this);
            var result = yield DocumentDetectorPlugin.start({ builder });
            return result;
        });
    }
}
//# sourceMappingURL=document-detector.js.map