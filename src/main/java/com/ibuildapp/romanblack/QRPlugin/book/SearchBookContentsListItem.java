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

import com.ibuildapp.romanblack.QRPlugin.R;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * A list item which displays the page number and snippet of this search result.
 */
public final class SearchBookContentsListItem extends LinearLayout {

    private TextView pageNumberView;
    private TextView snippetView;

    SearchBookContentsListItem(Context context) {
        super(context);
    }

    public SearchBookContentsListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        pageNumberView = (TextView) findViewById(R.id.romanblack_qr_page_number_view);
        snippetView = (TextView) findViewById(R.id.romanblack_qr_snippet_view);
    }

    public void set(SearchBookContentsResult result) {
        pageNumberView.setText(result.getPageNumber());
        String snippet = result.getSnippet();
        if (snippet.length() > 0) {
            if (result.getValidSnippet()) {
                String lowerQuery = SearchBookContentsResult.getQuery().toLowerCase(Locale.getDefault());
                String lowerSnippet = snippet.toLowerCase(Locale.getDefault());
                Spannable styledSnippet = new SpannableString(snippet);
                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                int queryLength = lowerQuery.length();
                int offset = 0;
                while (true) {
                    int pos = lowerSnippet.indexOf(lowerQuery, offset);
                    if (pos < 0) {
                        break;
                    }
                    styledSnippet.setSpan(boldSpan, pos, pos + queryLength, 0);
                    offset = pos + queryLength;
                }
                snippetView.setText(styledSnippet);
            } else {
                // This may be an error message, so don't try to bold the query terms within it
                snippetView.setText(snippet);
            }
        } else {
            snippetView.setText("");
        }
    }
}
