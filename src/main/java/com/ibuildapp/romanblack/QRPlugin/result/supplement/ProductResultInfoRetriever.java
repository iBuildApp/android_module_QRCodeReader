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
import android.text.Html;
import android.widget.TextView;
import com.ibuildapp.romanblack.QRPlugin.HttpHelper;
import com.ibuildapp.romanblack.QRPlugin.R;
import com.ibuildapp.romanblack.QRPlugin.history.HistoryManager;
import com.ibuildapp.romanblack.QRPlugin.LocaleManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ProductResultInfoRetriever extends SupplementalInfoRetriever {

    private static final Pattern PRODUCT_NAME_PRICE_PATTERN =
            Pattern.compile("owb63p\">([^<]+).+zdi3pb\">([^<]+)");
    private final String productID;
    private final String source;
    private final Context context;

    ProductResultInfoRetriever(TextView textView,
            String productID,
            Handler handler,
            HistoryManager historyManager,
            Context context) {
        super(textView, handler, historyManager);
        this.productID = productID;
        this.source = context.getString(R.string.msg_google_product);
        this.context = context;
    }

    @Override
    void retrieveSupplementalInfo() throws IOException, InterruptedException {

        String encodedProductID = URLEncoder.encode(productID, "UTF-8");
        String uri = "http://www.google." + LocaleManager.getProductSearchCountryTLD(context)
                + "/m/products?ie=utf8&oe=utf8&scoring=p&source=zxing&q=" + encodedProductID;
        String content = HttpHelper.downloadViaHttp(uri, HttpHelper.ContentType.HTML);

        Matcher matcher = PRODUCT_NAME_PRICE_PATTERN.matcher(content);
        if (matcher.find()) {
            append(productID,
                    source,
                    new String[]{unescapeHTML(matcher.group(1)), unescapeHTML(matcher.group(2))},
                    uri);
        }
    }

    private static String unescapeHTML(String raw) {
        return Html.fromHtml(raw).toString();
    }
}
