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
package com.ibuildapp.romanblack.QRPlugin.book;

import android.os.Handler;
import android.os.Message;
import com.ibuildapp.romanblack.QRPlugin.Constants;
import android.util.Log;
import com.ibuildapp.romanblack.QRPlugin.HttpHelper;
import com.ibuildapp.romanblack.QRPlugin.LocaleManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

final class NetworkWorker implements Runnable {

    private static final String TAG = NetworkWorker.class.getSimpleName();
    private final String isbn;
    private final String query;
    private final Handler handler;

    NetworkWorker(String isbn, String query, Handler handler) {
        this.isbn = isbn;
        this.query = query;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            // These return a JSON result which describes if and where the query was found. This API may
            // break or disappear at any time in the future. Since this is an API call rather than a
            // website, we don't use LocaleManager to change the TLD.
            String uri;
            if (LocaleManager.isBookSearchUrl(isbn)) {
                int equals = isbn.indexOf('=');
                String volumeId = isbn.substring(equals + 1);
                uri = "http://www.google.com/books?id=" + volumeId + "&jscmd=SearchWithinVolume2&q=" + query;
            } else {
                uri = "http://www.google.com/books?vid=isbn" + isbn + "&jscmd=SearchWithinVolume2&q=" + query;
            }

            try {
                String content = HttpHelper.downloadViaHttp(uri, HttpHelper.ContentType.JSON);
                JSONObject json = new JSONObject(content);
                Message message = Message.obtain(handler, Constants.SEARCH_BOOK_CONTENTS_SUCCEEDED);
                message.obj = json;
                message.sendToTarget();
            } catch (IOException ioe) {
                Message message = Message.obtain(handler, Constants.SEARCH_BOOK_CONTENTS_FAILED);
                message.sendToTarget();
            }
        } catch (JSONException je) {
            Log.w(TAG, "Error accessing book search", je);
            Message message = Message.obtain(handler, Constants.SEARCH_BOOK_CONTENTS_FAILED);
            message.sendToTarget();
        }
    }
}
