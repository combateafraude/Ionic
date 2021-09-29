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
                
                var passiveFaceLivenessBuilder = PassiveFaceLiveness.Builder(mobileToken: mobileToken)
                
                if let peopleId = arguments["peopleId"] as? String ?? nil {
                    passiveFaceLivenessBuilder.setPersonId(personId: peopleId)
                }
                
                if let useAnalytics = arguments["useAnalytics"] as? Bool ?? nil {
                    passiveFaceLivenessBuilder.setAnalyticsSettings(useAnalytics: useAnalytics)
                }
                
                if let hasSound = arguments["sound"] as? Bool ?? nil {
                    passiveFaceLivenessBuilder.enableSound(enableSound: hasSound)
                }
                
                if let requestTimeout = arguments["requestTimeout"] as? TimeInterval ?? nil {
                    passiveFaceLivenessBuilder.setNetworkSettings(requestTimeout: requestTimeout)
                }
                
                if let showPreview = arguments["showPreview"] as? [String: Any] ?? nil {
                    let show = showPreview["show"] as? Bool ?? false
                    let title = showPreview["title"] as? String ?? nil
                    let subtitle = showPreview["subTitle"] as? String ?? nil
                    let confirmLabel = showPreview["confirmLabel"] as? String ?? nil
                    let retryLabel = showPreview["retryLabel"] as? String ?? nil
                    passiveFaceLivenessBuilder.showPreview(show, title: title, subtitle: subtitle, confirmLabel: confirmLabel, retryLabel: retryLabel)
                }
                
                if let messageSettingsParam = arguments["messageSettings"] as? [String: Any] ?? nil {
                    let stepName = messageSettingsParam["stepName"] as? String ?? nil
                    let faceNotFoundMessage = messageSettingsParam["faceNotFoundMessage"] as? String ?? nil
                    let faceTooFarMessage = messageSettingsParam["faceTooFarMessage"] as? String ?? nil
                    let faceNotFittedMessage = messageSettingsParam["faceNotFittedMessage"] as? String ?? nil
                    let multipleFaceDetectedMessage = messageSettingsParam["multipleFaceDetectedMessage"] as? String ?? nil
                    let holdItMessage = messageSettingsParam["holdItMessage"] as? String ?? nil
                    let invalidFaceMessage = messageSettingsParam["invalidFaceMessage"] as? String ?? nil
                    
                    let messageSettings = MessageSettings()
                    if(stepName != nil){ messageSettings.stepName = stepName}
                    if(faceNotFoundMessage != nil){ messageSettings.faceNotFoundMessage = faceNotFoundMessage}
                    if(faceTooFarMessage != nil){ messageSettings.faceTooFarMessage = faceTooFarMessage}
                    if(faceNotFittedMessage != nil){ messageSettings.faceNotFittedMessage = faceNotFittedMessage}
                    if(multipleFaceDetectedMessage != nil){ messageSettings.multipleFaceDetectedMessage = multipleFaceDetectedMessage}
                    if(holdItMessage != nil){ messageSettings.holdItMessage = holdItMessage}
                    if(invalidFaceMessage != nil){ messageSettings.invalidFaceMessage = invalidFaceMessage}
                    
                    passiveFaceLivenessBuilder.setMessageSettings(messageSettings)
                }
                
                if let iosSettings = arguments["iosSettings"] as? [String: Any] ?? nil {
                    
                    if let customization = iosSettings["customization"] as? [String: Any] ?? nil {
                        
                        let layout = PassiveFaceLivenessLayout()
                        
                        if let colorHex = customization["colorHex"] as? String ?? nil {
                            passiveFaceLivenessBuilder.setColorTheme(color: UIColor.init(hexString: colorHex))
                        }
                        
                        if let showStepLabel = customization["showStepLabel"] as? Bool ?? nil {
                            passiveFaceLivenessBuilder.showStepLabel(show: showStepLabel)
                        }
                        
                        if let showStatusLabel = customization["showStatusLabel"] as? Bool ?? nil {
                            passiveFaceLivenessBuilder.showStatusLabel(show: showStatusLabel)
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
                        
                        
                        passiveFaceLivenessBuilder.setLayout(layout: layout)
                    }
                    
                    if let beforePictureMillis = iosSettings["beforePictureMillis"] as? TimeInterval ?? nil {
                        passiveFaceLivenessBuilder.setCaptureSettings(beforePictureInterval: beforePictureMillis)
                    }
                    
                    
                    if let sensorStability = iosSettings["sensorStability"] as? [String: Any] ?? nil {
                        if let sensorStability = sensorStability["sensorStability"] as? [String: Any] ?? nil {
                            let message = sensorStability["message"] as? String ?? nil
                            let stabilityThreshold = sensorStability["stabilityThreshold"] as? Double ?? nil
                            passiveFaceLivenessBuilder.setStabilitySensorSettings(message: message, stabilityThreshold: stabilityThreshold)
                        }
                    }
                    
                }
                
                passiveFaceLivenessBuilder.enableMultiLanguage(enable: false)
                
                DispatchQueue.main.async {
                    let scannerVC = PassiveFaceLivenessController(passiveFaceLiveness: passiveFaceLivenessBuilder.build())
                    scannerVC.passiveFaceLivenessDelegate = self
                    
                    self.bridge?.viewController.present(scannerVC, animated: true, completion: nil)
                    
                }
                
            }else {
                print("bad json")
            }
        } catch let error as NSError {
            print(error)
        }
        
    }
    
    public func passiveFaceLivenessController(_ passiveFacelivenessController: PassiveFaceLivenessController, didFinishWithResults results: PassiveFaceLivenessResult) {
        let response : NSMutableDictionary! = [:]
        
        let imagePath = saveImageToDocumentsDirectory(image: results.image, withName: "selfie.jpg")
        response["success"] = NSNumber(value: true)
        response["imagePath"] = imagePath
        response["imageUrl"] = results.imageUrl
        response["signedResponse"] = results.signedResponse
        response["trackingId"] = results.trackingId
        
        self.call?.success(["results": response])
    }
    
    public func passiveFaceLivenessControllerDidCancel(_ passiveFacelivenessController: PassiveFaceLivenessController) {
        let response : NSMutableDictionary! = [:]
        
        response["success"] = nil
        
        self.call?.success(["results": response])
    }
    
    public func passiveFaceLivenessController(_ passiveFacelivenessController: PassiveFaceLivenessController, didFailWithError error: PassiveFaceLivenessFailure) {
        let response : NSMutableDictionary! = [:]
        
        response["success"] = NSNumber(value: false)
        response["message"] = error.message
        response["type"] = String(describing: type(of: error))
        
        self.call?.success(["results": response])
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

