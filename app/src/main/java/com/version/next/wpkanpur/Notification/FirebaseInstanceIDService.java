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

package com.version.next.wpkanpur.Notification;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    String userid = "";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String token="";

    @Override
    public void onTokenRefresh()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        token = FirebaseInstanceId.getInstance().getToken();
        Log.d("token","token->"+token);

        if (token.equalsIgnoreCase(preferences.getString("registration_id", "")))
        {

        }
        else
        {
            saveTokenToPrefs(token);
        }

        /*registerToken(token);*/
    }

    private void saveTokenToPrefs(String _token)
    {
        // Save to SharedPreferences
        editor.putString("registration_id", _token);
        editor.putString("fcm_token_update_status", "no");
        editor.apply();
    }
}
