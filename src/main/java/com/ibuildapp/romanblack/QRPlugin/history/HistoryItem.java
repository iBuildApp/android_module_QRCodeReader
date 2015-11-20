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
package com.ibuildapp.romanblack.QRPlugin.history;

import com.google.zxing.Result;

public final class HistoryItem {

    private final Result result;
    private final String display;
    private final String details;

    HistoryItem(Result result, String display, String details) {
        this.result = result;
        this.display = display;
        this.details = details;
    }

    public Result getResult() {
        return result;
    }

    public String getDisplayAndDetails() {
        StringBuilder displayResult = new StringBuilder();
        if (display == null || display.length() == 0) {
            displayResult.append(result.getText());
        } else {
            displayResult.append(display);
        }
        if (details != null && details.length() > 0) {
            displayResult.append(" : ").append(details);
        }
        return displayResult.toString();
    }
}
