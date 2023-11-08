import Foundation
import Capacitor
import FaceAuthenticator
import FaceLiveness
/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceAuthenticatorPlugin)
public class FaceAuthenticatorPlugin: CAPPlugin {
    var userViewController: UIViewController?
    var stage: FaceLiveness.CAFStage?
    var filter: FaceLiveness.Filter?
    var mobileToken: String?
    
    @objc func Configure(_ call: CAPPluginCall) {        
        guard let mobileTokenValue = call.getString("mobileToken") else {
            call.reject("mobileToken must be provided")
            return
        }

        mobileToken = mobileTokenValue

        let stageValue = call.getString("stage") ?? "prod"

        switch stageValue {
        case "prod":
            stage = CAFStage.PROD
            break
        case "beta":
            stage = CAFStage.BETA
        default:
            call.reject("Invalid stage value: \(stageValue)")
            return
        }
        
        let filterValue = call.getString("filter") ?? ""
        
        switch filterValue {
        case "natural":
            filter = Filter.natural
        case "line-drawing":
            filter = Filter.lineDrawing
        default:
            call.reject("Invalid filter value: \(filter)")
            return
        }
        
        call.resolve()
    }
    
    @objc func authenticate(_ call: CAPPluginCall) {
        guard let personId = call.getString("personId") else {
            call.reject("personId must be provided")
            bridge?.releaseCall(call)
            return
        }
        
        if mobileToken == nil {
            call.reject("You must first configure the FaceAuthenticator")
            bridge?.releaseCall(call)
            return
        }

        var faceAuth = FaceAuthSDK.Builder()
            .setStage(stage: stage ?? .PROD)
            .setFilter(filter: filter ?? .lineDrawing)
        .setCredentials(token: mobileToken!, personId: personId)
        .build()
        
        faceAuth.delegate = self

        call.keepAlive = true
        call.callbackId
        
        if let viewController = userViewController {
            faceAuth.startFaceAuthSDK(viewController: viewController)
        }
    }
    
}

extension FaceAuthenticatorPlugin: FaceAuthSDKDelegate {
    public func didFinishSuccess(with faceAuthenticatorResult: FaceAuthenticator.FaceAuthenticatorResult) {
        var dict :[String : Any] = [:]
        var dictData: [String: Any] = [:]
        dict["type"] = "success"
        dictData["signedResponse"] = faceAuthenticatorResult.signedResponse
        dict["data"] = dictData
        
        var call = bridge?.savedCall(withID: <#T##String#>)
        
        call?.resolve(dict)
        bridge?.releaseCall(call!)
    }
    
    public func didFinishWithError(with faceAuthenticatorErrorResult: FaceAuthenticator.FaceAuthenticatorErrorResult) {
        var dict :[String : Any] = [:]
        let genericErrorMessage = "Error on authentication process"
        
        dict["error"] = "GenericError"
        dict["message"] = genericErrorMessage
        
        dict["message"] = faceAuthenticatorErrorResult.description
        
        if faceAuthenticatorErrorResult.errorType == .networkError {
            dict["error"] = "NetworkReason"
        }
        
        if faceAuthenticatorErrorResult.errorType == .serverError {
            dict["error"] = "ServerReason"
            dict["statusCode"] = faceAuthenticatorErrorResult.code
        }
        
        externalCall?.reject(faceAuthenticatorErrorResult.description ?? genericErrorMessage, nil, nil, dict)
    }
    
    public func didFinishWithCancell(with faceAuthenticatorResult: FaceAuthenticator.FaceAuthenticatorResult) {
        externalCall?.reject("Operation cancelled");
    }
    
    public func didFinishWithFail(with faceAuthenticatorResult: FaceAuthenticator.FaceAuthenticatorFailResult) {
        var dict :[String : Any] = [:]
        let genericFailMessage = "Fail in authentication process"
        
        dict["fail"] = "GenericFail"
        if faceAuthenticatorResult.errorType == .unknown {
            dict["fail"] = "unknown"
        }
        
        externalCall?.reject(genericFailMessage, nil, nil, dict)
        
        
    }
    
    public func openLoadingScreenStartSDK() {
        var dict :[String : Any] = [:]
        dict["type"] = "loading"
        externalCall?.resolve(dict)
    }
    
    public func closeLoadingScreenStartSDK() {
        var dict :[String : Any] = [:]
        dict["type"] = "loaded"
        externalCall?.resolve(dict)
    }
    
    public func openLoadingScreenValidation() {
        var dict :[String : Any] = [:]
        dict["type"] = "loading"
        externalCall?.resolve(dict)
    }
    
    public func closeLoadingScreenValidation() {
        var dict :[String : Any] = [:]
        dict["type"] = "loaded"
        externalCall?.resolve(dict)
    }
    
    
}
