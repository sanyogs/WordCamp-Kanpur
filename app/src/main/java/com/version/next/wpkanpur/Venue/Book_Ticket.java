/**
 * WordCamp Kanpur
 *
 * @package    WordCamp Kanpur
 * @author     Sanyog Shelar <sanyog@hotmail.com>
 * @copyright  Copyright (c) WHMCS Limited 2005-2013
 * @license    WordCamp is released under the GPL
 * @version    1.3
 * @link       https://2017.kanpur.wordcamp.org
 */

package com.version.next.wpkanpur.Venue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.version.next.wpkanpur.R;

/**
 * Created by Chintal-Pragma on 1/11/2017.
 */

public class Book_Ticket extends AppCompatActivity
{

    WebView webview;
    ProgressDialog pdia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book_ticket);

        pdia = new ProgressDialog(this);
        pdia.setCanceledOnTouchOutside(false);
        pdia.setCancelable(false);
        pdia.setMessage("Please Wait...");
        pdia.show();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setAppCacheEnabled(false);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(getString(R.string.url)+getString(R.string.open_buyticket));

        webview.setWebViewClient(new WebViewClient()
        {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
                catch (Exception e)
                {
                    // Google Play app is not installed, you may want to open the app store link
                    Toast.makeText(getApplicationContext(),"Google Play Store is not installed, Please install and try again!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return false;
                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                pdia.dismiss();

            }

            @Override
            public void onPageCommitVisible(WebView view, String url)
            {
                super.onPageCommitVisible(view, url);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
