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
package com.ibuildapp.romanblack.QRPlugin.result;

import android.view.View;
import android.widget.Button;

/**
 * Handles the result of barcode decoding in the context of the Android
 * platform, by dispatching the proper intents to open other activities like
 * GMail, Maps, etc.
 */
public final class ResultButtonListener implements Button.OnClickListener {

    private final ResultHandler resultHandler;
    private final int index;

    public ResultButtonListener(ResultHandler resultHandler, int index) {
        this.resultHandler = resultHandler;
        this.index = index;
    }

    @Override
    public void onClick(View view) {
        resultHandler.handleButtonPress(index);
    }
}
