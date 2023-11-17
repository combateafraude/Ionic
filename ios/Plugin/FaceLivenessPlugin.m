//
//  FaceLivenessPlugin.m
//  Plugin
//
//  Created by Gabriel Caldeira Martins on 17/11/23.
//  Copyright © 2023 Max Lynch. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

CAP_PLUGIN(FaceLivenessPlugin, "FaceLiveness",
           CAP_PLUGIN_METHOD(Configure, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startSDK, CAPPluginReturnCallback);
)
