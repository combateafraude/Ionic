var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
// import { SensorSettingsAndroid } from './android/sensor-settings';
// import { stringify } from '@angular/compiler/src/util';
import { DocumentDetectorStep } from './document-detector-step';
//import {DocumentDetectorIosSettings}
import { Plugins } from '@capacitor/core';
// import { JsonPipe } from '@angular/common';
// import { WebPlugin } from '@capacitor/core';
export { DocumentDetectorStep };
export { DocumentType } from './document-type';
const { IonicPlugin } = Plugins;
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
    start() {
        return __awaiter(this, void 0, void 0, function* () {
            let steps = new Array();
            console.log('start step 1');
            //var param = JSON.stringify({ mobileToken: this._mobileToken, DocumentDetectorSteps: this._documentDetectorSteps});
            var param = JSON.stringify(this);
            console.log('start step 2');
            console.log('param: ', param);
            const contacts = (yield IonicPlugin.start({ value: param })).results;
            console.log('contacts', contacts);
            /*params["showPreview"] = showPreview?.asMap();
            params["androidSettings"] = androidSettings?.asMap();
            params["iosSettings"] = iosSettings?.asMap();*/
        });
    }
}
//# sourceMappingURL=document-detector.js.map