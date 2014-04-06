package com.eas.cordova.browser;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class Loader extends Plugin {

	/**
	 * Executes the request and returns PluginResult.
	 *
	 * @param action        The action to execute.
	 * @param args          JSONArry of arguments for the plugin.
	 * @param callbackId    The callback id used when calling back into JavaScript.
	 * @return              A PluginResult object with a status and message.
	 */
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		try {
			if (action.equals("load")) {
				String url = args.getString(0);
				this.webView.loadUrl(url);
				return new PluginResult(PluginResult.Status.OK);
			} else {
				return new PluginResult(PluginResult.Status.INVALID_ACTION);
			}
		} catch (JSONException e) {
			return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
		}
	}
}