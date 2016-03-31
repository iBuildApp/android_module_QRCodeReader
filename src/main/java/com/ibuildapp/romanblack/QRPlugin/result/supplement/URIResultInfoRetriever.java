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
package com.ibuildapp.romanblack.QRPlugin.result.supplement;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;
import com.ibuildapp.romanblack.QRPlugin.HttpHelper;
import com.ibuildapp.romanblack.QRPlugin.history.HistoryManager;
import com.ibuildapp.romanblack.QRPlugin.R;
import com.google.zxing.client.result.URIParsedResult;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

final class URIResultInfoRetriever extends SupplementalInfoRetriever {

    private static final int MAX_REDIRECTS = 5;
    private final URIParsedResult result;
    private final String redirectString;

    URIResultInfoRetriever(TextView textView,
            URIParsedResult result,
            Handler handler,
            HistoryManager historyManager,
            Context context) {
        super(textView, handler, historyManager);
        redirectString = context.getString(R.string.msg_redirect);
        this.result = result;
    }

    @Override
    void retrieveSupplementalInfo() throws IOException, InterruptedException {
        URI oldURI;
        try {
            oldURI = new URI(result.getURI());
        } catch (URISyntaxException e) {
            return;
        }
        URI newURI = HttpHelper.unredirect(oldURI);
        int count = 0;
        while (count++ < MAX_REDIRECTS && !oldURI.equals(newURI)) {
            append(result.getDisplayResult(),
                    null,
                    new String[]{redirectString + " : " + newURI},
                    newURI.toString());
            oldURI = newURI;
            newURI = HttpHelper.unredirect(newURI);
        }
    }
}
