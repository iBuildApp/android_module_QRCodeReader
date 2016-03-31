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

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Loads a list of packages installed on the device asynchronously.
 */
final class LoadPackagesAsyncTask extends AsyncTask<List<String[]>, Void, List<String[]>> {

    private static final String[] PKG_PREFIX_WHITELIST = {
        "com.google.android.apps.",};
    private static final String[] PKG_PREFIX_BLACKLIST = {
        "com.android.",
        "android",
        "com.google.android.",
        "com.htc",};
    private final AppPickerActivity activity;

    LoadPackagesAsyncTask(AppPickerActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<String[]> doInBackground(List<String[]>... objects) {
        List<String[]> labelsPackages = objects[0];
        PackageManager packageManager = activity.getPackageManager();
        List<ApplicationInfo> appInfos = packageManager.getInstalledApplications(0);
        for (ApplicationInfo appInfo : appInfos) {
            CharSequence label = appInfo.loadLabel(packageManager);
            if (label != null) {
                String packageName = appInfo.packageName;
                if (!isHidden(packageName)) {
                    labelsPackages.add(new String[]{label.toString(), packageName});
                }
            }
        }
        Collections.sort(labelsPackages, new ByFirstStringComparator());
        return labelsPackages;
    }

    private static boolean isHidden(String packageName) {
        if (packageName == null) {
            return true;
        }
        for (String prefix : PKG_PREFIX_WHITELIST) {
            if (packageName.startsWith(prefix)) {
                return false;
            }
        }
        for (String prefix : PKG_PREFIX_BLACKLIST) {
            if (packageName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected synchronized void onPostExecute(List<String[]> results) {
        List<String> labels = new ArrayList<String>(results.size());
        for (String[] result : results) {
            labels.add(result[0]);
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, labels);
        activity.setListAdapter(listAdapter);
    }

    private static class ByFirstStringComparator implements Comparator<String[]>, Serializable {

        @Override
        public int compare(String[] o1, String[] o2) {
            return o1[0].compareTo(o2[0]);
        }
    }
}
