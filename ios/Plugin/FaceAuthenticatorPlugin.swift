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
    var faceAuth: FaceAuthSDK?
    var builder: FaceAuthSDK.Builder?
    
    @objc func Configure(_ call: CAPPluginCall) {        
        guard let mobileToken = call.getString("mobileToken") else {
            call.reject("mobileToken must be provided")
            return
        }
        builder = FaceAuthSDK.Builder(mobileToken: mobileToken)

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
        builder?.setStage(stage: stage ?? .PROD)
        
        let filterValue = call.getString("filter") ?? ""
        
        switch filterValue {
        case "natural":
            filter = Filter.natural
        case "line-drawing":
            filter = Filter.lineDrawing
        default:
            call.reject("Invalid filter value: \(String(describing: filter))")
            return
        }
        
        builder?.setFilter(filter: filter ?? .lineDrawing)
        builder?.setLoading(withLoading: true)
        
        faceAuth?.delegate = self
        call.resolve()
    }
    
    @objc func authenticate(_ call: CAPPluginCall) {
        guard let personId = call.getString("personId") else {
            call.reject("personId must be provided")
            bridge?.releaseCall(call)
            return
        }
        builder?.setPersonId(personId: personId)
        faceAuth = builder?.build()
        
        if faceAuth == nil {
            call.reject("You must first configure the FaceAuthenticator")
            bridge?.releaseCall(call)
            return
        }
        
        call.keepAlive = true
        
        call.callbackId
        
        if let viewController = userViewController {
            faceAuth?.startFaceAuthSDK(viewController: viewController) { faceAuthResult, status in
                switch status {
                case .sucess:
                    var dict :[String : Any] = [:]
                    var dictData: [String: Any] = [:]
                    dict["type"] = "success"
                    dictData["signedResponse"] = faceAuthResult
                    dict["data"] = dictData
                    
                    call.resolve(dict)
                    self.bridge?.releaseCall(call)
                case .error:
                    var dict :[String : Any] = [:]
                    let genericErrorMessage = "Error on authentication process"
                    
                    dict["error"] = "GenericError"
                    dict["message"] = genericErrorMessage
                    
                    dict["message"] = faceAuthResult.errorMessage
                    
                    if faceAuthResult.errorType == .networkError {
                        dict["error"] = "NetworkReason"
                    }
                    
                    if faceAuthResult.errorType == .serverError {
                        dict["error"] = "ServerReason"
                        dict["statusCode"] = faceAuthResult.code
                    }
                    
                    call.reject(faceAuthResult.errorMessage ?? genericErrorMessage, nil, nil, dict)
                case .fail:
                    var dict :[String : Any] = [:]
                    let genericFailMessage = "Fail in authentication process"
                    
                    dict["fail"] = "GenericFail"
                    if faceAuthResult.failType == FaceLiveness.FailType.unknown {
                        dict["fail"] = "unknown"
                    }
                    
                    call.reject(genericFailMessage, nil, nil, dict)
                case .cancelled:
                    call.reject("Operation cancelled")
                @unknown default:
                    call.reject("Fatal error")
                }
            }
        }
    }
}

extension FaceAuthenticatorPlugin: FaceAuthSDKDelegate {
    public func openLoadingScreenStartSDK() {
//        var dict :[String : Any] = [:]
//        dict["type"] = "loading"
//        call?.resolve(dict)
    }
    
    public func closeLoadingScreenStartSDK() {
//        var dict :[String : Any] = [:]
//        dict["type"] = "loaded"
//        externalCall?.resolve(dict)
    }
    
    public func openLoadingScreenValidation() {
//        var dict :[String : Any] = [:]
//        dict["type"] = "loading"
//        externalCall?.resolve(dict)
    }
    
    public func closeLoadingScreenValidation() {
//        var dict :[String : Any] = [:]
//        dict["type"] = "loaded"
//        externalCall?.resolve(dict)
    }
    
    
}
