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

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.zxing.Result;
import com.ibuildapp.romanblack.QRPlugin.R;

import java.util.ArrayList;

final class HistoryItemAdapter extends ArrayAdapter<HistoryItem> {

    private final Activity activity;

    HistoryItemAdapter(Activity activity) {
        super(activity, R.layout.romanblack_qr_history_list_item, new ArrayList<HistoryItem>());
        this.activity = activity;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LinearLayout layout;
        if (view instanceof LinearLayout) {
            layout = (LinearLayout) view;
        } else {
            LayoutInflater factory = LayoutInflater.from(activity);
            layout = (LinearLayout) factory.inflate(R.layout.romanblack_qr_history_list_item, viewGroup, false);
        }

        HistoryItem item = getItem(position);
        Result result = item.getResult();

        String title;
        String detail;
        if (result != null) {
            title = result.getText();
            detail = item.getDisplayAndDetails();
        } else {
            Resources resources = getContext().getResources();
            title = resources.getString(R.string.history_empty);
            detail = resources.getString(R.string.history_empty_detail);
        }

        ((TextView) layout.findViewById(R.id.romanblack_qr_history_title)).setText(title);
        ((TextView) layout.findViewById(R.id.romanblack_qr_history_detail)).setText(detail);

        return layout;
    }
}
