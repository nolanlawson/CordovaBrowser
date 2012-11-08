package com.eas.cordova.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import org.apache.cordova.DroidGap;

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
}