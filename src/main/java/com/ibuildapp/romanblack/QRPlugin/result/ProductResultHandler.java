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

import com.google.zxing.Result;
import com.ibuildapp.romanblack.QRPlugin.R;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ProductParsedResult;

import android.app.Activity;
import android.view.View;

/**
 * Handles generic products which are not books.
 */
public final class ProductResultHandler extends ResultHandler {

    private static final int[] buttons = {
        R.string.button_product_search,
        R.string.button_web_search,
        R.string.button_custom_product_search
    };

    public ProductResultHandler(Activity activity, ParsedResult result, Result rawResult) {
        super(activity, result, rawResult);
        showGoogleShopperButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductParsedResult productResult = (ProductParsedResult) getResult();
                openGoogleShopper(productResult.getNormalizedProductID());
            }
        });
    }

    @Override
    public int getButtonCount() {
        return hasCustomProductSearch() ? buttons.length : buttons.length - 1;
    }

    @Override
    public int getButtonText(int index) {
        return buttons[index];
    }

    @Override
    public void handleButtonPress(int index) {
        ProductParsedResult productResult = (ProductParsedResult) getResult();
        switch (index) {
            case 0:
                openProductSearch(productResult.getNormalizedProductID());
                break;
            case 1:
                webSearch(productResult.getNormalizedProductID());
                break;
            case 2:
                openURL(fillInCustomSearchURL(productResult.getNormalizedProductID()));
                break;
        }
    }

    @Override
    public int getDisplayTitle() {
        return R.string.result_product;
    }
}
