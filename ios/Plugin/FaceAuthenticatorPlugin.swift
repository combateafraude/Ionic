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
    var stage: FaceLiveness.CAFStage?
    var filter: FaceLiveness.Filter?
    var faceAuth: FaceAuthSDK?
    var builder: FaceAuthSDK.Builder?
    
    @objc func Configure(_ call: CAPPluginCall) { 
        print("chamou a configure")
        guard let mobileToken = call.getString("mobileToken") else {
            call.reject("mobileToken must be provided")
            return
        }
        print("pegou o token: \(mobileToken)")
        builder = FaceAuthSDK.Builder(mobileToken: mobileToken)

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
        builder?.setLoading(withLoading: true)

        print("pegou o filtro: \(filter)")
        
        call.resolve()
    }
    
    @objc func authenticate(_ call: CAPPluginCall) {
        guard let personId = call.getString("personId") else {
            call.reject("personId must be provided")
            bridge?.releaseCall(call)
            return
        }
        print("setou o personID: \(personId)")
        builder?.setPersonId(personId: personId)
    
        faceAuth = builder?.build()
        
        
        if faceAuth == nil {
            call.reject("You must first configure the FaceAuthenticator")
            bridge?.releaseCall(call)
            return
        }
        
        call.keepAlive = true
        
        let controller = UIApplication.shared.keyWindow!.rootViewController!
        
        faceAuth?.startFaceAuthSDK(viewController: controller) { faceAuthResult, status in
            switch status {
            case .sucess:
                var dict :[String : Any] = [:]
                var dictData: [String: Any] = [:]
                dict["type"] = "success"
                dictData["signedResponse"] = faceAuthResult.signedResponse
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
                self.bridge?.releaseCall(call)
            case .fail:
                var dict :[String : Any] = [:]
                let genericFailMessage = "Fail in authentication process"
                
                dict["fail"] = "GenericFail"
                dict["signedResponse"] = faceAuthResult.signedResponse
                if faceAuthResult.failType == FaceLiveness.FailType.unknown {
                    dict["fail"] = "unknown"
                }
                
                call.reject(genericFailMessage, nil, nil, dict)
                self.bridge?.releaseCall(call)
            case .cancelled:
                call.reject("Operation cancelled")
                self.bridge?.releaseCall(call)
            @unknown default:
                call.reject("Fatal error")
                self.bridge?.releaseCall(call)
            }
        }
    }
}
extension FaceAuthenticatorPlugin: FaceAuthSDKDelegate {
    public func openLoadingScreenAuthSDK() {
        print("DELEGATE DISPARADO - ")
    }
    
    public func closeLoadingScreenAuthSDK() {
        print("DELEGATE DISPARADO - ")
    }
    
    public func openLoadingScreenAuth() {
        print("DELEGATE DISPARADO - ")
    }
    
    public func closeLoadingScreenAuth() {
        print("DELEGATE DISPARADO - ")
    }
    
}
