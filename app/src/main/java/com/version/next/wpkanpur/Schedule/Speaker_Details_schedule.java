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
import android.content.SharedPreferences;
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
import java.util.List;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chintal-Pragma on 12/23/2016.
 */

public class Speaker_Details_schedule extends Activity
{
    TextView tv_speaker_detail_name,tv_speaker_detail_content,tv_session_title,tv_session_time,tv_speaker_detail_twitter,tv_speaker_detail;
    CircleImageView iv_speaker_detail_image;

    ImageLoader imageLoader;
    DisplayImageOptions defaultOptions;

    ImageView iv_back;
    TextView tv_days_togo;

    SharedPreferences sharedPreferences_speaker_data;
    SharedPreferences.Editor editor_speaker_data;
    String speaker_response = "", firstname = "" , lastname = "" , session_title = "" , session_time = ""  ,session_id="";;
    Gson gson;
    List<Object> list , list_sessions , list_sessionmeta;
    Map<String,Object> map_speaker,map_spaker_title ,map_content,map_avatar_urls;

    String speakerimage="",twitter_response="",shortname="",twitter_url="",speaker_desc="",speakername="",speakerid="";

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.speaker_detail);

        set_timer();

        tv_speaker_detail_name = (TextView)findViewById(R.id.tv_speaker_detail_name);
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
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(Speaker_Details_schedule.this));

        sharedPreferences_speaker_data = getSharedPreferences("Speaker_Data",MODE_PRIVATE);
        editor_speaker_data = sharedPreferences_speaker_data.edit();
        speaker_response = sharedPreferences_speaker_data.getString("speaker_response","");

        extras = getIntent().getExtras();

        if (!speaker_response.equals(""))
        {
            speakerid = extras.getString("speakerid");

            set_speaker_data(speaker_response);
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
    public void onBackPressed()
    {
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

    public void set_speaker_data(String speaker_response)
    {
        gson = new Gson();
        list = (List) gson.fromJson(speaker_response, List.class);

        for (int i = 0 ; i < list.size() ; i++)
        {

            map_speaker = (Map<String,Object>)list.get(i);

            String speakerid_compare = (String)map_speaker.get("id").toString();

            Log.d("map_speaker==>","map_speaker==>"+map_speaker);

            if (speakerid_compare.equals(speakerid))
            {
                map_spaker_title = (Map<String, Object>) map_speaker.get("title");

                for (int i1=0; i1<map_spaker_title.size(); i1++)
                {
                    Log.d("speaker_title->",""+map_spaker_title.get("rendered"));

                    tv_speaker_detail_name.setText("Name : "+ String.valueOf(map_spaker_title.get("rendered")));

                }

                map_content = (Map<String, Object>) map_speaker.get("content");

                for (int i1=0; i1<map_content.size(); i1++)
                {
                    Log.d("speaker_content->",""+map_content.get("rendered"));

                    if (Build.VERSION.SDK_INT >= 24)
                    {
                        tv_speaker_detail_content.setText(Html.fromHtml(String.valueOf(map_content.get("rendered")),Html.FROM_HTML_MODE_LEGACY));
                        tv_speaker_detail_content.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tv_speaker_detail_content.setText(Html.fromHtml(String.valueOf(map_content.get("rendered"))));
                        tv_speaker_detail_content.setVisibility(View.VISIBLE);
                    }

                }


                map_avatar_urls = (Map<String, Object>) map_speaker.get("avatar_urls");

                Log.d("avatar_urls=>","avatar_urls=>"+String.valueOf(map_avatar_urls.get("96")));

                imageLoader.displayImage(String.valueOf(map_avatar_urls.get("96")),iv_speaker_detail_image , defaultOptions);

            }
        }

    }

    public void get_twitter_data()
    {
        Log.d("speakerimage","speakerimage"+speakerimage);

        StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.twitter_url)+speakerimage.replace("https://secure.gravatar.com/avatar/","").replace("?s=96&d=mm&r=g","")+".json", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {
                twitter_response = s;


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
                //   Toast.makeText(Speaker_Details_schedule.this, "There is no response from the server.", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Speaker_Details_schedule.this);
        rQueue.add(request);
    }
}
