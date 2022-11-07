import Foundation
import Capacitor
import FaceAuthenticator

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceAuthenticatorPlugin)
public class FaceAuthenticatorPlugin: CAPPlugin, FaceAuthenticatorControllerDelegate {
    
    var call: CAPPluginCall?
    
    @objc func start(_ call: CAPPluginCall) {
        self.call = call
        let value = call.getString("builder") ?? ""
        let data = value.data(using: .utf8)!
        
        do {
            if let arguments = try JSONSerialization.jsonObject(with: data, options : .allowFragments) as? [String:AnyObject]{
                
                let mobileToken = arguments["mobileToken"] as! String
                
                let faceAuthenticatorBuilder = FaceAuthenticatorSdk.Builder(mobileToken: mobileToken)
                                
                if let peopleId = arguments["peopleId"] as? String ?? nil {
                    _ = faceAuthenticatorBuilder.setPeopleId(peopleId)
                }
                
                if let useAnalytics = arguments["useAnalytics"] as? Bool ?? nil {
                    _ = faceAuthenticatorBuilder.setAnalyticsSettings(useAnalytics: useAnalytics)
                }
                
                if let hasSound = arguments["sound"] as? Bool ?? nil {
                    _ = faceAuthenticatorBuilder.enableSound(hasSound: hasSound)
                }
                
                if let requestTimeout = arguments["requestTimeout"] as? TimeInterval ?? nil {
                    _ = faceAuthenticatorBuilder.setNetworkSettings(requestTimeout: requestTimeout)
                }
                
                if let iosSettings = arguments["iosSettings"] as? [String: Any] ?? nil {
                    
                    if let customization = iosSettings["customization"] as? [String: Any] ?? nil {
                        
                        let layout = FaceAuthenticatorLayout()
                        
                        if let colorHex = customization["colorHex"] as? String ?? nil {
                            _ = faceAuthenticatorBuilder.setColorTheme(color: UIColor.init(hexString: colorHex))
                        }
                        
                        if let showStepLabel = customization["showStepLabel"] as? Bool ?? nil {
                            _ = faceAuthenticatorBuilder.showStepLabel(show: showStepLabel)
                        }
                        
                        if let showStatusLabel = customization["showStatusLabel"] as? Bool ?? nil {
                            _ = faceAuthenticatorBuilder.showStatusLabel(show: showStatusLabel)
                        }
                        
                        if let closeImageName = customization["closeImageName"] as? String ?? nil {
                            layout.closeImage = UIImage(named: closeImageName)
                        }
                        
                        var greenMask : UIImage?
                        if let greenMaskImageName = customization["greenMaskImageName"] as? String ?? nil {
                            greenMask = UIImage(named: greenMaskImageName)
                        }
                        
                        var whiteMask : UIImage?
                        if let whiteMaskImageName = customization["whiteMaskImageName"] as? String ?? nil {
                            whiteMask = UIImage(named: whiteMaskImageName)
                        }
                        
                        var redMask : UIImage?
                        if let redMaskImageName = customization["redMaskImageName"] as? String ?? nil {
                            redMask = UIImage(named: redMaskImageName)
                        }
                        
                        layout.changeMaskImages(
                            greenMask: greenMask,
                            whiteMask: whiteMask,
                            redMask: redMask)
                        
                        
                        _ = faceAuthenticatorBuilder.setLayout(layout: layout)
                    }
                    
                    if let sensorStability = iosSettings["sensorStability"] as? [String: Any] ?? nil {
                        if let sensorStability = sensorStability["sensorStability"] as? [String: Any] ?? nil {
                            let stabilityThreshold = sensorStability["stabilityThreshold"] as? Double ?? nil
                            _ = faceAuthenticatorBuilder.setStabilitySensorSettings(message: nil, stabilityThreshold: stabilityThreshold)
                        }
                    }
                    
                }
                
                if let captureMode = arguments["captureMode"] as? [String: Any] ?? nil {
                    if let videoCapture = captureMode["videoCapture"] as? [String: Any] ?? nil {
                        if let use = videoCapture["use"] as? Bool ?? nil {
                            if(use){
                                if let time = videoCapture["time"] as? TimeInterval ?? nil {
                                    _ = faceAuthenticatorBuilder.setVideoCaptureSettings(time: time)
                                }else{
                                    _ = faceAuthenticatorBuilder.setVideoCaptureSettings(time: 3)
                                }
                            }
                        }
                    }
                }

                if let useOpenEyeValidation = arguments["useOpenEyeValidation"] as? Bool ?? nil {
                    faceAuthenticatorBuilder.setEyesClosedSettings(threshold: arguments["openEyesThreshold"] as? Double ?? 0.5, isEnable: useOpenEyeValidation)
                }
                
                DispatchQueue.main.async {
                    let scannerVC = FaceAuthenticatorController(faceAuthenticator: faceAuthenticatorBuilder.build())
                    scannerVC.faceAuthenticatorDelegate = self
                    
                    self.bridge?.presentVC(scannerVC, animated: true, completion: nil)
                    
                }
                
            }else {
                print("bad json")
            }
        } catch let error as NSError {
            print(error)
        }
        
    }
    
    public func faceAuthenticatorController(_ faceAuthenticatorController: FaceAuthenticatorController, didFinishWithResults results: FaceAuthenticatorResult) {
        let response : NSMutableDictionary! = [:]
        
        response["success"] = NSNumber(value: true)
        response["isAuthenticated"] = results.authenticated
        response["signedResponse"] = results.signedResponse
        response["trackingId"] = results.trackingId
        response["lensFacing"] = results.lensFacing
        
        self.call?.resolve(["results": response])
    }
    
    public func faceAuthenticatorControllerDidCancel(_ faceAuthenticatorController: FaceAuthenticatorController) {
        let response : NSMutableDictionary! = [:]
        
        response["success"] = nil
        
        self.call?.resolve(["results": response])
    }
    
    public func faceAuthenticatorController(_ faceAuthenticatorController: FaceAuthenticatorController, didFailWithError error: FaceAuthenticatorFailure) {
        let response : NSMutableDictionary! = [:]
        
        response["success"] = NSNumber(value: false)
        response["message"] = error.message
        response["type"] = String(describing: type(of: error))
        
        self.call?.resolve(["results": response])
    }
    
    func saveImageToDocumentsDirectory(image: UIImage, withName: String) -> String? {
        if let data = image.jpegData(compressionQuality: 0.8) {
            let dirPath = getDocumentsDirectory()
            let filename = dirPath.appendingPathComponent(withName)
            do {
                try data.write(to: filename)
                print("Successfully saved image at path: \(filename)")
                return filename.path
            } catch {
                print("Error saving image: \(error)")
            }
        }
        return nil
    }
    
    func getDocumentsDirectory() -> URL {
        let paths = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask)
        return paths[0]
    }
}

extension UIColor {
    convenience init(hexString: String, alpha: CGFloat = 1.0) {
        let hexString: String = hexString.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines)
        let scanner = Scanner(string: hexString)
        if (hexString.hasPrefix("#")) {
            scanner.scanLocation = 1
        }
        var color: UInt32 = 0
        scanner.scanHexInt32(&color)
        let mask = 0x000000FF
        let r = Int(color >> 16) & mask
        let g = Int(color >> 8) & mask
        let b = Int(color) & mask
        let red   = CGFloat(r) / 255.0
        let green = CGFloat(g) / 255.0
        let blue  = CGFloat(b) / 255.0
        self.init(red:red, green:green, blue:blue, alpha:alpha)
    }
    func toHexString() -> String {
        var r:CGFloat = 0
        var g:CGFloat = 0
        var b:CGFloat = 0
        var a:CGFloat = 0
        getRed(&r, green: &g, blue: &b, alpha: &a)
        let rgb:Int = (Int)(r*255)<<16 | (Int)(g*255)<<8 | (Int)(b*255)<<0
        return String(format:"#%06x", rgb)
    }
}

