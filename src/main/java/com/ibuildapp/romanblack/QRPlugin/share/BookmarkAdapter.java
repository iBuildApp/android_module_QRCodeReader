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
package com.ibuildapp.romanblack.QRPlugin.share;

import com.ibuildapp.romanblack.QRPlugin.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A custom adapter designed to fetch bookmarks from a cursor. Before Honeycomb
 * we used SimpleCursorAdapter, but it assumes the existence of an _id column,
 * and the bookmark schema was rewritten for HC without one. This caused the app
 * to crash, hence this new class, which is forwards and backwards compatible.
 */
final class BookmarkAdapter extends BaseAdapter {

    private final Context context;
    private final Cursor cursor;

    BookmarkAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int index) {
        // Not used, so no point in retrieving it.
        return null;
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        LinearLayout layout;
        if (view instanceof LinearLayout) {
            layout = (LinearLayout) view;
        } else {
            LayoutInflater factory = LayoutInflater.from(context);
            layout = (LinearLayout) factory.inflate(R.layout.romanblack_qr_bookmark_picker_list_item, viewGroup, false);
        }

        cursor.moveToPosition(index);
        String title = cursor.getString(BookmarkPickerActivity.TITLE_COLUMN);
        ((TextView) layout.findViewById(R.id.romanblack_qr_bookmark_title)).setText(title);
        String url = cursor.getString(BookmarkPickerActivity.URL_COLUMN);
        ((TextView) layout.findViewById(R.id.romanblack_qr_bookmark_url)).setText(url);
        return layout;
    }
}
