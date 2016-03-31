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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import com.ibuildapp.romanblack.QRPlugin.R;

/**
 * Manufactures list items which represent SBC results.
 */
final class SearchBookContentsAdapter extends ArrayAdapter<SearchBookContentsResult> {

    SearchBookContentsAdapter(Context context, List<SearchBookContentsResult> items) {
        super(context, R.layout.romanblack_qr_search_book_contents_list_item, 0, items);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        SearchBookContentsListItem listItem;

        if (view == null) {
            LayoutInflater factory = LayoutInflater.from(getContext());
            listItem = (SearchBookContentsListItem) factory.inflate(
                    R.layout.romanblack_qr_search_book_contents_list_item, viewGroup, false);
        } else {
            if (view instanceof SearchBookContentsListItem) {
                listItem = (SearchBookContentsListItem) view;
            } else {
                return view;
            }
        }

        SearchBookContentsResult result = getItem(position);
        listItem.set(result);
        return listItem;
    }
}
