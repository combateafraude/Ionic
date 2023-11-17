//
//  FaceLivenessPlugin.swift
//  Plugin
//
//  Created by Gabriel Caldeira Martins on 17/11/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor
import FaceAuthenticator
import FaceLiveness
/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceLivenessPlugin)
public class FaceLivenessPlugin: CAPPlugin {
    var stage: FaceLiveness.CAFStage?
    var filter: FaceLiveness.Filter?
    var faceLiveness: FaceLivenessSDK?
    var builder: FaceLivenessSDK.Build?
    
    @objc func Configure(_ call: CAPPluginCall) {
        print("chamou a configure")
        guard let mobileToken = call.getString("mobileToken") else {
            call.reject("mobileToken must be provided")
            return
        }
        print("pegou o token: \(mobileToken)")
        builder = FaceLivenessSDK.Build(mobileToken: mobileToken)

        let stageValue = call.getString("stage", "prod")
        print("pegou o stage\(stageValue)")
        switch stageValue {
        case "prod":
            stage = CAFStage.PROD
            break
        case "beta":
            stage = CAFStage.DEV
        default:
            call.reject("Invalid stage value: \(stageValue)")
            return
        }
        builder?.setStage(stage: stage ?? .PROD)
        print("setou o stage: \(stage))")
        let filterValue = call.getString("filter", "line-drawing")
        print("pegou o filtro: \(filterValue))")
        switch filterValue {
        case "natural":
            filter = Filter.natural
        case "line-drawing":
            filter = Filter.lineDrawing
        default:
            call.reject("Invalid filter value: \(String(describing: filter))")
            return
        }
        
        builder?.setFilter(filter: filter ?? Filter.lineDrawing)
        builder?.setLoadingScreen(withLoading: true)

        print("pegou o filtro: \(filter)")
        
        call.resolve()
    }
    
    @objc func startSDK(_ call: CAPPluginCall) {
        guard let personId = call.getString("personId") else {
            call.reject("personId must be provided")
            bridge?.releaseCall(call)
            return
        }
        print("setou o personID: \(personId)")
        builder?.setPersonId(personId: personId)
    
        faceLiveness = builder?.build()
        
        
        if faceLiveness == nil {
            call.reject("You must first configure the FaceLiveness")
            bridge?.releaseCall(call)
            return
        }
        
        call.keepAlive = true
        
        let controller = UIApplication.shared.keyWindow!.rootViewController!
        
        faceLiveness?.startSDK(viewController: controller) {faceLivenessResult, status in
            switch status {
                
            case .sucess:
                var dict :[String : Any] = [:]
                var dictData: [String: Any] = [:]
                dict["type"] = "success"
                dictData["signedResponse"] = faceLivenessResult.signedResponse
                dict["data"] = dictData
                
                call.resolve(dict)
                self.bridge?.releaseCall(call)
            case .fail:
                var dict :[String : Any] = [:]
                let genericFailMessage = "Fail in authentication process"
                
                dict["fail"] = "GenericFail"
                dict["signedResponse"] = faceLivenessResult.signedResponse
                if faceLivenessResult.failType == FaceLiveness.FailType.unknown {
                    dict["fail"] = "unknown"
                }
                
                call.reject(genericFailMessage, nil, nil, dict)
                self.bridge?.releaseCall(call)
            case .cancelled:
                call.reject("Operation cancelled")
                self.bridge?.releaseCall(call)
            case .error:
                var dict :[String : Any] = [:]
                let genericErrorMessage = "Error on authentication process"
                
                dict["error"] = "GenericError"
                dict["message"] = genericErrorMessage
                
                dict["message"] = faceLivenessResult.errorMessage
                
                if faceLivenessResult.errorType == .networkError {
                    dict["error"] = "NetworkReason"
                }
                
                if faceLivenessResult.errorType == .serverError {
                    dict["error"] = "ServerReason"
                    dict["statusCode"] = faceLivenessResult.code
                }
                
                call.reject(faceLivenessResult.errorMessage ?? genericErrorMessage, nil, nil, dict)
                self.bridge?.releaseCall(call)
            @unknown default:
                call.reject("Fatal error")
                self.bridge?.releaseCall(call)
            }
        }
    }
}
extension FaceLivenessPlugin: FaceLivenessDelegate {
    public func openLoadingScreenStartSDK() {
        print("Delegate disparado")
    }
    
    public func closeLoadingScreenStartSDK() {
        print("Delegate disparado")
    }
    
    public func openLoadingScreenValidation() {
        print("Delegate disparado")
    }
    
    public func closeLoadingScreenValidation() {
        print("Delegate disparado")
    }
}
