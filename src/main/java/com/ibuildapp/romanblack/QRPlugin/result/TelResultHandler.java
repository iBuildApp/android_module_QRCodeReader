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

import com.ibuildapp.romanblack.QRPlugin.R;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.TelParsedResult;

import android.app.Activity;
import android.telephony.PhoneNumberUtils;

/**
 * Offers relevant actions for telephone numbers.
 */
public final class TelResultHandler extends ResultHandler {

    private static final int[] buttons = {
        R.string.button_dial,
        R.string.button_add_contact
    };

    public TelResultHandler(Activity activity, ParsedResult result) {
        super(activity, result);
    }

    @Override
    public int getButtonCount() {
        return buttons.length;
    }

    @Override
    public int getButtonText(int index) {
        return buttons[index];
    }

    @Override
    public void handleButtonPress(int index) {
        TelParsedResult telResult = (TelParsedResult) getResult();
        switch (index) {
            case 0:
                dialPhoneFromUri(telResult.getTelURI());
                break;
            case 1:
                String[] numbers = new String[1];
                numbers[0] = telResult.getNumber();
                addPhoneOnlyContact(numbers, null);
                break;
        }
    }

    // Overriden so we can take advantage of Android's phone number hyphenation routines.
    @Override
    public CharSequence getDisplayContents() {
        String contents = getResult().getDisplayResult();
        contents = contents.replace("\r", "");
        return PhoneNumberUtils.formatNumber(contents);
    }

    @Override
    public int getDisplayTitle() {
        return R.string.result_tel;
    }
}
