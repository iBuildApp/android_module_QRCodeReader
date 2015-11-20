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
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ParsedResult;

import android.app.Activity;

/**
 * Handles email addresses.
 */
public final class EmailAddressResultHandler extends ResultHandler {

    private static final int[] buttons = {
        R.string.button_email,
        R.string.button_add_contact
    };

    public EmailAddressResultHandler(Activity activity, ParsedResult result) {
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
        EmailAddressParsedResult emailResult = (EmailAddressParsedResult) getResult();
        switch (index) {
            case 0:
                sendEmailFromUri(emailResult.getMailtoURI(),
                        emailResult.getEmailAddress(),
                        emailResult.getSubject(),
                        emailResult.getBody());
                break;
            case 1:
                String[] addresses = new String[1];
                addresses[0] = emailResult.getEmailAddress();
                addEmailOnlyContact(addresses, null);
                break;
        }
    }

    @Override
    public int getDisplayTitle() {
        return R.string.result_email_address;
    }
}
