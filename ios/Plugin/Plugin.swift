import Foundation
import Capacitor
import FaceAuthenticator
/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceAuthenticatorPlugin)
public class FaceAuthenticatorPlugin: CAPPlugin {
    
    var call: CAPPluginCall?
    
    @objc func start(_ call: CAPPluginCall) {
        self.call = call
        let value = call.getString("builder") ?? ""
        let data = value.data(using: .utf8)!
        
        do {
            if let arguments = try JSONSerialization.jsonObject(with: data, options : .allowFragments) as? [String:AnyObject]{
                
                let mobileToken = arguments["mobileToken"] as! String
                                
                let peopleId = arguments["personId"] as! String
                
                var faceAuth: FaceAuthSDK!
                faceAuth = FaceAuthSDK.Builder()
                    .setCredentials(token: mobileToken, personId: peopleId)
                    .build()
                
                faceAuth.delegate = self
                
                let controller = UIApplication.shared.keyWindow!.rootViewController!
                
                faceAuth.startFaceAuthSDK(viewController: controller)
        
                
            }else {
                print("bad json")
            }
        } catch let error as NSError {
            print(error)
        }
        
    }

    public func startLoadingScreen() {
        print("StartLoadScreen")
    }
    
}

extension FaceAuthenticatorPlugin: FaceAuthSDKDelegate {
    public func didFinishFaceAuth(with faceAuthenticatorResult: FaceAuthenticatorResult) {        
        let response : NSMutableDictionary! = [:]
        
        
        if faceAuthenticatorResult.errorMessage != nil {
            response["success"] = NSNumber(value: false)
            response["errorMessage"] = faceAuthenticatorResult.errorMessage
        } else {
            response["success"] = NSNumber(value: true)
            response["isAlive"] = faceAuthenticatorResult.isAlive
            response["isMatch"] = faceAuthenticatorResult.isMatch
            response["userId"] = faceAuthenticatorResult.userId
        }

        self.call?.resolve(["results": response])
    }
}

