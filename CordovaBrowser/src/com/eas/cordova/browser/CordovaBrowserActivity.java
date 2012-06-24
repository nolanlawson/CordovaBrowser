package com.eas.cordova.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.DroidGap;

public class CordovaBrowserActivity extends DroidGap {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String defaultUrl = "file:///android_asset/www/index.html";
		String url = getUrlFromIntent(getIntent(), defaultUrl);
		super.loadUrl(url);
	}
	
	public String getUrlFromIntent(Intent i, String defaultUrl){
		String webURI = i.getStringExtra(Intent.EXTRA_TEXT);
		if (webURI != null)
			return webURI;
		else 
			return defaultUrl;
	}
}