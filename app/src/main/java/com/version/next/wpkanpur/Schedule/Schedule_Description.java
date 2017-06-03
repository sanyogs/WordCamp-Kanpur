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

package com.version.next.wpkanpur.Schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.version.next.wpkanpur.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Chintal-Pragma on 5/29/2017.
 */

public class Schedule_Description extends Activity
{
    TextView tv_title,tv_schedule_description,tv_time,tv_speakername;
    ImageView iv_back;

    Bundle extras;
    String content,title,time,speaker,speakerid;
    LinearLayout ll_speaker_schedule;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.schedule_description);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_schedule_description = (TextView) findViewById(R.id.tv_schedule_description);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_speakername = (TextView) findViewById(R.id.tv_speakername);

        ll_speaker_schedule = (LinearLayout) findViewById(R.id.ll_speaker_schedule);

        extras = getIntent().getExtras();
        content = extras.getString("content");
        title = extras.getString("title");
        time = extras.getString("time");
        speaker = extras.getString("speaker");
        speakerid = extras.getString("speakerid");

        iv_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                 onBackPressed();
            }
        });

        if (!content.equals(""))
        {
            tv_schedule_description.setText(Html.fromHtml(content));
        }

        if (!title.equals(""))
        {
            tv_title.setText(title);
        }

        if (!time.equals(""))
        {
            tv_time.setText(getDate(Long.parseLong(time)));
        }

        if (!speaker.equals(""))
        {
            tv_speakername.setText(speaker);
        }

        ll_speaker_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!speakerid.equals(""))
                {
                    Log.d("speakerid","speakerid"+speakerid);

                    Intent i = new Intent(Schedule_Description.this,Speaker_Details_schedule.class);
                    i.putExtra("speakerid",speakerid);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    private String getDate(long timeStamp){
        DateFormat objFormatter = new SimpleDateFormat("MMM dd,yyyy - hh:mm aa");
        objFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        Calendar objCalendar =
                Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        objCalendar.setTimeInMillis(timeStamp*1000);//edit
        String result = objFormatter.format(objCalendar.getTime());
        objCalendar.clear();
        return result;
    }
}
