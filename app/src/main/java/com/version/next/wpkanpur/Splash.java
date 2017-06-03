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

package com.version.next.wpkanpur.wpkanpur;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.version.next.wpkanpur.Drawer.Drawer_main;
import com.crashlytics.android.Crashlytics;

import java.util.Random;

import io.fabric.sdk.android.Fabric;

/**
 * Created by shashi on 19/10/16.
 */

public class Splash extends Activity {

    public static int time=2000;
    RelativeLayout rl_splash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        FirebaseInstanceId.getInstance();
        Log.d("firebase token","firebase token->"+FirebaseInstanceId.getInstance().getToken());

        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();

        setContentView(R.layout.splash);
        rl_splash = (RelativeLayout)findViewById(R.id.rl_splash);

        Random rn = new Random();
         int random_num = rn.nextInt(3 - 1 + 1) + 1;

        Log.d("random_num","random_num"+random_num);
        if (random_num == 1)
        {
            rl_splash.setBackgroundResource(R.mipmap.bg1);
        }
        else if (random_num == 2)
        {
            rl_splash.setBackgroundResource(R.mipmap.bg2);
        }
        else if (random_num == 3)
        {
            rl_splash.setBackgroundResource(R.mipmap.bg3);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
                Intent intent = new Intent(Splash.this,Drawer_main.class);
                intent.putExtra("intent_type","speaker");
                startActivity(intent);

            }
        }, time);

    }
}
