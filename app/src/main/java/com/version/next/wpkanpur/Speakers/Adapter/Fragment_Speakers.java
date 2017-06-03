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

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.version.next.wpkanpur.ConnectionDetector;
import com.version.next.wpkanpur.Drawer.Drawer_main;
import com.version.next.wpkanpur.R;
import com.version.next.wpkanpur.Speakers.Adapter.Adapter.Adapter_Speaker;
import com.version.next.wpkanpur.Speakers.Adapter.Adapter.All_Speaker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Chintal-Pragma on 12/22/2016.
 */

public class Fragment_Speakers extends Fragment
{

    GridView gv_speakers;
    ProgressDialog pdia;

    Gson gson;
    List<Object> list , list_sessions , list_sessionmeta;
    Map<String,Object> map_speaker,map_spaker_title,map_content,map_avatar_urls;

    ArrayList<All_Speaker> arrayList_all_speaker = new ArrayList<All_Speaker>();
    Adapter_Speaker adapter_speaker;

    ConnectionDetector cd;

    SharedPreferences sharedPreferences_speaker_data;
    SharedPreferences.Editor editor_speaker_data;

    String speaker_response = "" ;

    TextView tv_days_togo;
    View rootview;

    TextView tv_no_speaker_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences_speaker_data = getActivity().getSharedPreferences("Speaker_Data",getActivity().MODE_PRIVATE);
        editor_speaker_data = sharedPreferences_speaker_data.edit();

        cd  = new ConnectionDetector(getActivity());

         rootview = inflater.inflate(R.layout.speaker,container,false);

        gv_speakers = (GridView)rootview.findViewById(R.id.gv_speakers);
        tv_no_speaker_data = (TextView) rootview.findViewById(R.id.tv_no_speaker_data);

        set_timer();

        adapter_speaker = new Adapter_Speaker(getActivity(),arrayList_all_speaker);
        gv_speakers.setAdapter(adapter_speaker);

        //new spaker_list().execute();

        pdia = new ProgressDialog(getActivity());
        pdia.setCanceledOnTouchOutside(false);
        pdia.setCancelable(false);
        pdia.setTitle("Please Wait...");
        pdia.show();

        speaker_response = sharedPreferences_speaker_data.getString("speaker_response","");

        if (!speaker_response.equals(""))
        {
            set_speaker_data(speaker_response);
        }
        else
        {
            if (cd.isConnectingToInternet())
            {
                get_speaker_data();
            }
            else
            {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setTitle("Kindly Check your Internet Connection");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    dialog.cancel();
                                    getActivity().finish();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
            }
        }

        if (Drawer_main.imageView_actionbar_left_reload != null)
        {
            Drawer_main.imageView_actionbar_left_reload.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {

                    pdia = new ProgressDialog(getActivity());
                    pdia.setCanceledOnTouchOutside(false);
                    pdia.setCancelable(false);
                    pdia.setTitle("Please Wait...");
                    pdia.show();

                    get_speaker_data();
                }
            });
        }

        return rootview;
    }

    public void set_speaker_data(String speaker_response)
    {
        gson = new Gson();
        list = (List) gson.fromJson(speaker_response, List.class);

        arrayList_all_speaker.clear();


        Log.d("list=>","list=>"+list.size());

        for (int i = 0 ; i < list.size() ; i++)
        {

            map_speaker = (Map<String,Object>)list.get(i);

            Log.d("id_speaker->",""+(String)map_speaker.get("id").toString());


            map_spaker_title = (Map<String, Object>) map_speaker.get("title");

            for (int i1=0; i1<map_spaker_title.size(); i1++)
            {
                Log.d("speaker_title->",""+map_spaker_title.get("rendered"));
            }

            map_content = (Map<String, Object>) map_speaker.get("content");

            for (int i1=0; i1<map_content.size(); i1++)
            {
                Log.d("speaker_content->",""+map_content.get("rendered"));
            }

            map_avatar_urls = (Map<String, Object>) map_speaker.get("avatar_urls");


            Log.d("map_avatar_urls","map_avatar_urls"+String.valueOf(map_avatar_urls.get("96")));

            arrayList_all_speaker.add(new All_Speaker((String)map_speaker.get("id").toString(),
                    (String)map_spaker_title.get("rendered"),
                    String.valueOf(map_avatar_urls.get("96")),//avtar
                    (String)map_content.get("rendered"),//content
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""));


            if (arrayList_all_speaker.size() >0)
            {
                gv_speakers.setVisibility(View.VISIBLE);
                tv_no_speaker_data.setVisibility(View.GONE);
            }
            else
            {
                gv_speakers.setVisibility(View.GONE);
                tv_no_speaker_data.setVisibility(View.VISIBLE);
            }

          /*
            for (int j=0; j<list_sessions.size(); j++) {

            }



         */
        }

        pdia.dismiss();

        Collections.sort(arrayList_all_speaker, new Comparator<All_Speaker>() {
            @Override
            public int compare(All_Speaker s1, All_Speaker s2) {
                return s1.speaker_name.compareToIgnoreCase(s2.speaker_name);
            }
        });

        adapter_speaker.notifyDataSetChanged();
    }

    public void get_speaker_data()
    {
        StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.url)+"wp-json/wp/v2/speakers?per_page=100&_embed", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {
                speaker_response = s;

                editor_speaker_data.putString("speaker_response",speaker_response);
                editor_speaker_data.commit();

                set_speaker_data(speaker_response);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                Toast.makeText(getActivity(), "There is no response from the server.", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
    }

    public void set_timer()
    {
        tv_days_togo = (TextView)rootview.findViewById(R.id.tv_days_togo);

        Calendar thatDay = Calendar.getInstance();

        thatDay.set(Calendar.DAY_OF_MONTH,Integer.parseInt(getString(R.string.timer_date)));
        thatDay.set(Calendar.MONTH,Integer.parseInt(getString(R.string.timer_month)));
        thatDay.set(Calendar.YEAR, Integer.parseInt(getString(R.string.timer_year)));

        Calendar today = Calendar.getInstance();

        Log.d("today","today"+today);
        Log.d("thatDay","thatDay"+thatDay);

        long diff = thatDay.getTimeInMillis() -  today.getTimeInMillis() ; //re
        long days = diff / (24 * 60 * 60 * 1000);

        //tv_days_togo.setText(days +"days to go");

        String date_to_live = getString(R.string.no_of_days);
        date_to_live = "-"+date_to_live;

        int date_to_live_val = Integer.parseInt(date_to_live) + 1;
        Log.d("date_to_live","date_to_live"+date_to_live_val);

        if (days > 0)
        {
            Log.d("days>0","days"+days);
            tv_days_togo.setText(days +"days to go");
        }
        else if (days ==0 || days >=  date_to_live_val)
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
}
