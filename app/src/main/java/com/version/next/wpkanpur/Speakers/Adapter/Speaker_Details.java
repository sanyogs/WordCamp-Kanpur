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

package com.version.next.wpkanpur.Speakers.Adapter;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.version.next.wpkanpur.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chintal-Pragma on 12/23/2016.
 */

public class Speaker_Details extends Activity
{
    TextView tv_speaker_detail_name,tv_speaker_detail_content,tv_session_title,tv_session_time,tv_speaker_detail_twitter,tv_speaker_detail;
    CircleImageView iv_speaker_detail_image;

    Bundle extras;
    String speaker_name,speaker_image,speaker_desc,intro_text;

    ImageLoader imageLoader;
    DisplayImageOptions defaultOptions;

    ImageView iv_back;
    TextView tv_days_togo;

    //twitter
    Gson gson;
    String twitter_response="",shortname="",twitter_url="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.speaker_detail);

        set_timer();

        tv_speaker_detail_name = (TextView)findViewById(R.id.tv_speaker_detail_name);
        //tv_speaker_firstname = (TextView)findViewById(R.id.tv_speaker_firstname);
       // tv_speaker_lastname = (TextView)findViewById(R.id.tv_speaker_lastname);
        tv_session_title = (TextView)findViewById(R.id.tv_session_title);
        tv_session_time = (TextView)findViewById(R.id.tv_session_time);
        tv_speaker_detail_content = (TextView)findViewById(R.id.tv_speaker_detail_content);
        tv_speaker_detail_twitter = (TextView)findViewById(R.id.tv_speaker_detail_twitter);
        tv_speaker_detail = (TextView)findViewById(R.id.tv_speaker_detail);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_speaker_detail_image = (CircleImageView)findViewById(R.id.iv_speaker_detail_image);

        defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader = ImageLoader.getInstance();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(Speaker_Details.this));

        extras = getIntent().getExtras();
       speaker_name = extras.getString("speaker_name","");
        speaker_image = extras.getString("speaker_image","");
        speaker_desc = extras.getString("speaker_desc","");
        intro_text = extras.getString("intro_text","");


        if (!speaker_name.equals(""))
        {
            tv_speaker_detail_name.setText("Name : "+speaker_name);
            tv_speaker_detail_name.setVisibility(View.VISIBLE);
        }
        else
        {
            tv_speaker_detail_name.setVisibility(View.GONE);
        }




        imageLoader.displayImage(speaker_image, iv_speaker_detail_image, defaultOptions);

        Log.d("speaker_desc","speaker_desc"+speaker_desc);

        if (!speaker_desc.equals(""))
        {
            if (Build.VERSION.SDK_INT >= 24)
            {
                tv_speaker_detail_content.setText(Html.fromHtml(speaker_desc,Html.FROM_HTML_MODE_LEGACY));
                tv_speaker_detail_content.setVisibility(View.VISIBLE);
            }
            else
            {
                tv_speaker_detail_content.setText(Html.fromHtml(speaker_desc));
                tv_speaker_detail_content.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            tv_speaker_detail_content.setVisibility(View.GONE);

        }


        iv_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });

        get_twitter_data();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    public void set_timer()
    {
        tv_days_togo = (TextView)findViewById(R.id.tv_days_togo);

        Calendar thatDay = Calendar.getInstance();

        thatDay.set(Calendar.DAY_OF_MONTH,Integer.parseInt(getString(R.string.timer_date)));
        thatDay.set(Calendar.MONTH,Integer.parseInt(getString(R.string.timer_month))); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, Integer.parseInt(getString(R.string.timer_year)));

        Calendar today = Calendar.getInstance();

        Log.d("today","today"+today.getTimeInMillis());

        long diff = thatDay.getTimeInMillis() -  today.getTimeInMillis() ; //re
        long days = diff / (24 * 60 * 60 * 1000);


        String date_to_live = getString(R.string.no_of_days);
        date_to_live = "-"+date_to_live;

        int date_to_live_val = Integer.parseInt(date_to_live) + 1;
        Log.d("date_to_live","date_to_live"+date_to_live_val);

        if (days > 0)
        {
            Log.d("days>0","days"+days);
            tv_days_togo.setText(days +"days to go");
        }
        else if (days ==0 || days >= date_to_live_val)
        {
            Log.d("days=0","days"+days);
            tv_days_togo.setText("Wordcamp is live");
        }
        else
        {
            Log.d("days<0","days"+days);
            tv_days_togo.setVisibility(View.GONE);
        }

    }


    public void get_twitter_data()
    {
        StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.twitter_url)+speaker_image.replace("https://secure.gravatar.com/avatar/","").replace("?s=96&d=mm&r=g","")+".json", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {
                twitter_response = s;



                Log.d("twitter_response","twitter_response"+twitter_response);
                try
                {
                    JSONObject object = new JSONObject(twitter_response);
                    JSONArray array_entry = object.getJSONArray("entry");

                    if (array_entry.length()>0)
                    {
                      for (int i = 0 ; i < array_entry.length() ; i++)
                      {

                          if (array_entry.getJSONObject(i).has("accounts"))
                          {
                              JSONArray array_accounts = array_entry.getJSONObject(i).getJSONArray("accounts");

                              for (int j = 0 ; j <array_accounts.length() ; j++ )
                              {
                                  shortname = array_accounts.getJSONObject(j).getString("shortname");

                                  if (shortname.equals("twitter"))
                                  {
                                      twitter_url =  array_accounts.getJSONObject(j).getString("url");
                                      Log.d("twitter_url->","twitter_url->"+twitter_url);

                                      if (!twitter_url.equals(""))
                                      {
                                          tv_speaker_detail_twitter.setText("Twitter : "+twitter_url);
                                          tv_speaker_detail_twitter.setVisibility(View.VISIBLE);
                                          tv_speaker_detail.setVisibility(View.VISIBLE);
                                      }
                                      else
                                      {
                                          tv_speaker_detail_twitter.setVisibility(View.GONE);
                                          tv_speaker_detail.setVisibility(View.GONE);
                                      }

                                  }

                              }
                          }
                          else
                          {
                              tv_speaker_detail_twitter.setVisibility(View.GONE);
                              tv_speaker_detail.setVisibility(View.GONE);
                          }

                      }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
               // Toast.makeText(Speaker_Details.this, "There is no response from the server.", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Speaker_Details.this);
        rQueue.add(request);
    }

}
