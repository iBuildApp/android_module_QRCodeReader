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

/**
 * The underlying data for a SBC result.
 */
final class SearchBookContentsResult {

    private static String query;
    private final String pageId;
    private final String pageNumber;
    private final String snippet;
    private final boolean validSnippet;

    SearchBookContentsResult(String pageId,
            String pageNumber,
            String snippet,
            boolean validSnippet) {
        this.pageId = pageId;
        this.pageNumber = pageNumber;
        this.snippet = snippet;
        this.validSnippet = validSnippet;
    }

    public static void setQuery(String query) {
        SearchBookContentsResult.query = query;
    }

    public String getPageId() {
        return pageId;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public String getSnippet() {
        return snippet;
    }

    public boolean getValidSnippet() {
        return validSnippet;
    }

    public static String getQuery() {
        return query;
    }
}
