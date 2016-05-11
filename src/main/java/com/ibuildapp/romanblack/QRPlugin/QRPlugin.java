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
package com.ibuildapp.romanblack.QRPlugin;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appbuilder.sdk.android.AppBuilderModuleMainAppCompat;
import com.appbuilder.sdk.android.DialogSharing;
import com.appbuilder.sdk.android.StartUpActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


@StartUpActivity(moduleName = "QRCodeReader")
public final class QRPlugin extends AppBuilderModuleMainAppCompat {

    private ScanFragment mainFragment;

    private static boolean inScaned = false;
    @Override
    public void create() {
        super.create();
        setContentView(R.layout.qr_code_layout_main);
        hideTopBar();

        mainFragment = (ScanFragment) getSupportFragmentManager().findFragmentById(R.id.qr_code_main_fragment);
        mainFragment.setListener(new IFragmentListener() {
            @Override
            public void onQrCodeRead(final String scannerString) {
                DialogSharing.Configuration configuration = new DialogSharing.Configuration.Builder()
                        .setOpenInBrowserClickListener(new DialogSharing.Item.OnClickListener() {
                            @Override
                            public void onClick() {
                                openInBrowser(scannerString);
                            }
                        })
                        .addCustomListener(R.string.qr_share_email, R.drawable.share_email, false, new DialogSharing.Item.OnClickListener() {
                            @Override
                            public void onClick() {
                               shareEmail(scannerString);
                            }
                        })
                        .addCustomListener(R.string.qr_share_message, R.drawable.share_message, false,new DialogSharing.Item.OnClickListener() {
                            @Override
                            public void onClick() {
                                shareSMS(scannerString);
                            }
                        })
                        .build();
                DialogSharing sharing = new DialogSharing(QRPlugin.this, scannerString,  configuration);
                sharing.setCanceledOnTouchOutside(false);
                sharing.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                sharing.show();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    private void openInBrowser(String scannerString) {
        finish();

        if (!scannerString.startsWith("http://") && !scannerString.startsWith("https://"))
            scannerString = "http://" + scannerString;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(scannerString));
        startActivity(i);
    }

    public void shareSMS(String text){
        finish();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
        intent.putExtra("sms_body", text);
        startActivity(intent);
    }

    public void shareEmail( String text){
        finish();
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(text));
        startActivity(emailIntent);
    }

    public  interface IFragmentListener {
        void onQrCodeRead(String scannerString);
        void onCancel();
    }

    public static class ScanFragment extends Fragment {
        private String toast;
        private IFragmentListener listener;

        public ScanFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            displayToast();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.qr_code_scan_fragment, container, false);
            scanFromFragment();
            return view;
        }

        public void scanFromFragment() {
            inScaned = true;
            IntentIntegrator.forSupportFragment(this).setOrientationLocked(false).initiateScan();
        }

        private void displayToast() {
            if(getActivity() != null && toast != null) {
                Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
                toast = null;
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            inScaned = false;
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    if (listener!= null)
                        listener.onCancel();
                } else {
                    listener.onQrCodeRead(result.getContents());
                }
            }
        }

        public void setListener(IFragmentListener listener) {
            this.listener = listener;
        }
    }
}
