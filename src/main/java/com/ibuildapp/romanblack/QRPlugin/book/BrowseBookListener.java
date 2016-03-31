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

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import com.ibuildapp.romanblack.QRPlugin.LocaleManager;

import java.util.List;

final class BrowseBookListener implements AdapterView.OnItemClickListener {

    private final SearchBookContentsActivity activity;
    private final List<SearchBookContentsResult> items;

    BrowseBookListener(SearchBookContentsActivity activity, List<SearchBookContentsResult> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        if (position < 1) {
            // Clicked header, ignore it
            return;
        }
        String pageId = items.get(position - 1).getPageId();
        String query = SearchBookContentsResult.getQuery();
        if (LocaleManager.isBookSearchUrl(activity.getISBN()) && pageId.length() > 0) {
            String uri = activity.getISBN();
            int equals = uri.indexOf('=');
            String volumeId = uri.substring(equals + 1);
            String readBookURI = "http://books.google."
                    + LocaleManager.getBookSearchCountryTLD(activity)
                    + "/books?id=" + volumeId + "&pg=" + pageId + "&vq=" + query;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(readBookURI));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            activity.startActivity(intent);
        }
    }
}
