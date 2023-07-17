import Foundation
import Capacitor
import PassiveFaceLiveness

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(PassiveFaceLivenessPlugin)
public class PassiveFaceLivenessPlugin: CAPPlugin, PassiveFaceLivenessControllerDelegate {

    
    
    var call: CAPPluginCall?
    
    @objc func start(_ call: CAPPluginCall) {
        self.call = call
        let value = call.getString("builder") ?? ""
        let data = value.data(using: .utf8)!
        
        do {
            if let arguments = try JSONSerialization.jsonObject(with: data, options : .allowFragments) as? [String:AnyObject]{
                
                let mobileToken = arguments["mobileToken"] as! String
                
                let peopleId = arguments["personId"] as! String
        
                var faceLiveness = FaceLivenessSDK.Build()
                    .setCredentials(mobileToken: mobileToken, personId: peopleId)
                    .build()
                
                faceLiveness.delegate = self
                
                let controller = UIApplication.shared.keyWindow!.rootViewController!
                
                faceLiveness.startSDK(viewController: controller)
              
                
            } else {
                    print("bad json")
            }
                
        } catch let error as NSError {
            print(error)
        }
    }
    
    extension SwiftPassiveFaceLivenessPlugin: FaceLivenessDelegate {
        public func didFinishLiveness(with faceLivenesResult: FaceLivenessIproov.FaceLivenessResult) {

            let response : NSMutableDictionary! = [:]

            if faceLivenesResult.errorMessage != nil {
                response["success"] = NSNumber(value: false)
                response["errorMessage"] = faceLivenesResult.errorMessage
            } else {
                response["success"] = NSNumber(value: true)
                response["signedResponse"] = faceLivenesResult.signedResponse
            }

            self.call?.resolve(["results": response])
        }
    }

    public func startLoadingScreen() {
        print("StartLoadScreen")
    }
    
}