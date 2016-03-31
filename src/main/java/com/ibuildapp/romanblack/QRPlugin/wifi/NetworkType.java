/****************************************************************************
*                                                                           *
*  Copyright (C) 2014-2015 iBuildApp, Inc. ( http://ibuildapp.com )         *
*                                                                           *
*  This file is part of iBuildApp.                                          *
*                                                                           *
*  This Source Code Form is subject to the terms of the iBuildApp License.  *
*  You can obtain one at http://ibuildapp.com/license/                      *
*                                                                           *
****************************************************************************/
package com.ibuildapp.romanblack.QRPlugin.wifi;

enum NetworkType {

    WEP,
    WPA,
    NO_PASSWORD;

    static NetworkType forIntentValue(String networkTypeString) {
        if (networkTypeString == null) {
            return NO_PASSWORD;
        }
        if ("WPA".equals(networkTypeString)) {
            return WPA;
        }
        if ("WEP".equals(networkTypeString)) {
            return WEP;
        }
        if ("nopass".equals(networkTypeString)) {
            return NO_PASSWORD;
        }
        throw new IllegalArgumentException(networkTypeString);
    }
}
