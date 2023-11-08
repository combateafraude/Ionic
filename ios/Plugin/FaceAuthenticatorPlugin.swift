import Foundation
import Capacitor
import FaceAuthenticator
/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceAuthenticatorPlugin)
public class FaceAuthenticatorPlugin: CAPPlugin {
    var faceAuth: FaceAuthSDK?
    var userViewController: UIViewController?
    var externalCall: CAPPluginCall? = nil
    
//    @objc func echo(_ call: CAPPluginCall) {
//        let value = call.getString("value") ?? ""
//        call.resolve([
//            "value": implementation.echo(value)
//        ])
//    }
    
    @objc func Configure(_ call: CAPPluginCall) {
        var builder = FaceAuthSDK.Builder()
        
        guard let mobileToken = call.getString("mobileToken") else {
            call.reject("mobileToken must be provided")
            return
        }
        let stageValue = call.getString("stage") ?? "prod"
        let personId = call.getString("personId") ?? ""
        
        switch stageValue {
        case "prod":
            builder.setStage(stage: .PROD)
        case "beta":
            builder.setStage(stage: .BETA)
        default:
            call.reject("Invalid stage value: \(stageValue)")
        }
        
        let filter = call.getString("filter") ?? ""
        
        switch filter {
        case "natural":
            builder.setFilter(filter: .natural)
        case "line-drawing":
            builder.setFilter(filter: .lineDrawing)
        default:
            call.reject("Invalid stage value: \(filter)")
        }
        
        builder.setCredentials(token: mobileToken, personId: personId)
        
        faceAuth = builder.build()
        faceAuth?.delegate = self
        
        call.resolve()
    }
    
    @objc func authenticate(_ call: CAPPluginCall) {
        externalCall = call
        call.keepAlive = true
        
        if let viewController = userViewController {
            faceAuth?.startFaceAuthSDK(viewController: viewController)
        }
    }
}

extension FaceAuthenticatorPlugin: FaceAuthenticator.FaceAuthSDKDelegate {
    public func didFinishSuccess(with faceAuthenticatorResult: FaceAuthenticator.FaceAuthenticatorResult) {
        var dict :[String : Any] = [:]
        var dictData: [String: Any] = [:]
        dict["type"] = "success"
        dictData["signedResponse"] = faceAuthenticatorResult.signedResponse
        dict["data"] = dictData
        
        externalCall?.resolve(dict)

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
