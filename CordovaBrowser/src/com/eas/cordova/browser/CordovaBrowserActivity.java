package com.eas.cordova.browser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.DroidGap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.eas.cordova.browser.sqlite.R;

public class CordovaBrowserActivity extends DroidGap {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String defaultUrl = "file:///android_asset/www/index.html";
		String url = getUrlFromIntent(getIntent(), defaultUrl);
		super.loadUrl(url);
		super.appView.clearCache(false);

	}


	public String getUrlFromIntent(Intent i, String defaultUrl){
		String webURI = i.getStringExtra(Intent.EXTRA_TEXT);
		if (webURI != null)
			return webURI;
		else 
			return defaultUrl;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.home){
			super.loadUrl("file:///android_asset/www/index.html");
		}
		else if (item.getItemId() == R.id.refresh){
			super.appView.clearCache(false);
			super.appView.reload();
		}
		return true;
	}

	private boolean mightBeError(String message){
		return message.contains("Error");
	}

	private void toastConsoleMessage(String message, int lineNumber, String sourceID){
		if (message != null && sourceID != null &&
				!message.contains("JSCallback Error: Request failed with status 0") &&
				sourceID.length() != 0){
			Toast toast = Toast.makeText(this,
					sourceID + ": Line " + lineNumber + " : " + message, 
					Toast.LENGTH_SHORT);
			if (mightBeError(message)){
				toast.setDuration(Toast.LENGTH_LONG);
				TextView msg = (TextView) toast.getView().findViewById(android.R.id.message);
				String html = msg.getText().toString().replace(
							"Error", 
							"<font color='#EE0000'>Error</font>");
				msg.setText(Html.fromHtml(html));
			}
			toast.show();
		}
	}

	@Override
	public void init() {
		CordovaWebView webView = new CordovaWebView(this);
		CordovaWebViewClient webViewClient;
		webViewClient = new CordovaWebViewClient(this, webView){{
		    
		    
		    
		}

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("CordovaSqliteBrowser", "url started: "+ url);
            if (!url.startsWith("http")) {
                Log.d("CordovaSqliteBrowser", "skipping url " + url);
                return;
            }
            Log.d("CordovaSqliteBrowser", "loading cordova/sqlite plugin");
            /*try {
                String cordova = Utils.slurp(getAssets().open("www/libs/cordova.min.js"), 0x1000);
                String sqlite = Utils.slurp(getAssets().open("www/js/SQLitePlugin.min.js"), 0x1000);
                view.loadUrl("javascript:" + cordova);
                view.loadUrl("javascript:" + sqlite);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            view.loadUrl("javascript:console.log('do we have cordova ' + !!window.cordova);");
            view.loadUrl("javascript:console.log('do we have sqlite ' + !!window.sqlitePlugin);");
            view.loadUrl("javascript:console.log('do we have pouchdb ' + !!window.PouchDB);");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);   
            Log.d("CordovaSqliteBrowser", "url ended: "+ url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            return super.shouldOverrideUrlLoading(view, url);
        }
        
        
		};
		this.init(webView, webViewClient, new CordovaChromeClient(this, webView){
			@SuppressLint("NewApi")
            @Override
			public boolean onConsoleMessage(ConsoleMessage consoleMessage){
				Log.d("CordovaSqliteBrowser", consoleMessage.message());
				return super.onConsoleMessage(consoleMessage);
			}

            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                super.onConsoleMessage(message, lineNumber, sourceID);
                Log.d("CordovaSqliteBrowser", message);
            }
			
		});
	}
	

	
	
}