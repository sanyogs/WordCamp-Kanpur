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

package com.version.next.wpkanpur.Schedule.Tab_View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.version.next.wpkanpur.Drawer.Drawer_main;
import com.version.next.wpkanpur.R;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day1;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day10;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day2;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day3;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day4;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day5;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day6;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day7;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day8;
import com.version.next.wpkanpur.Schedule.Fragment_Schedule_Day9;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;


public class Tabs_Schedule extends Fragment
{
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;

    String speaker_id,speaker_name;

    public static ArrayList<String> arrayList_date = new ArrayList<String>();
    public static ArrayList<String> arrayList_date_compare = new ArrayList<String>();
    public static ArrayList<String> arrayList_sc_id= new ArrayList<String>();
    public static ArrayList<String> arrayList_sc_title= new ArrayList<String>();
    public static ArrayList<String> arrayList_sc_date= new ArrayList<String>();
    public static ArrayList<String> arrayList_sc_speaker= new ArrayList<String>();
    public static ArrayList<String> arrayList_sc_speakerid= new ArrayList<String>();
    public static ArrayList<String> arrayList_sc_content= new ArrayList<String>();

    Gson gson;
    List<Object> list_schedule,list_speaker;
    Map<String,Object> map_schedule,map_schedule_title,map_schedule_content,map_speaker,map_speakermname,map_embedded
                        ,map_schedule_datetime;
    ProgressDialog pdia;

    int tab_set = 0;

    TextView tv_days_togo;
    View x;

    String day_1="",day_2="",day_3="",day_4="",day_5="",day_6="",day_7="",day_8="",day_9="",day_10="";
    TextView tv_no_schedule_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
         x =  inflater.inflate(R.layout.tab_layout_schedule,null);

            set_timer();

            tabLayout = (TabLayout) x.findViewById(R.id.tabs);
            viewPager = (ViewPager) x.findViewById(R.id.viewpager);
            tv_no_schedule_data = (TextView) x.findViewById(R.id.tv_no_schedule_data);

            pdia = new ProgressDialog(getActivity());
            pdia.setCanceledOnTouchOutside(false);
            pdia.setCancelable(false);
            pdia.setTitle("Please Wait...");
            pdia.show();

            StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.url)+"wp-json/wp/v2/sessions?per_page=100&_embed", new Response.Listener<String>()
            {
            @Override
            public void onResponse(String s)
            {
                try
                {
                    gson = new Gson();

                    list_schedule = (List) gson.fromJson(s, List.class);
                    Log.d("list_schedule->","list_schedule->"+s);

                    arrayList_date.clear();
                    arrayList_date_compare.clear();
                    arrayList_sc_id.clear();
                    arrayList_sc_title.clear();
                    arrayList_sc_date.clear();
                    arrayList_sc_speaker.clear();
                    arrayList_sc_content.clear();
                    arrayList_sc_speakerid.clear();


                    for (int i = 0 ; i < list_schedule.size() ; i++) {
                        map_schedule = (Map<String, Object>) list_schedule.get(i);

                        //schedule id
                        Log.d("sc_is->", "sc_is->" + String.valueOf((Double) map_schedule.get("id")));
                        arrayList_sc_id.add(String.valueOf((Double) map_schedule.get("id")));

                        //schedule title
                        map_schedule_title = (Map<String, Object>) map_schedule.get("title");

                        if (map_schedule_title != null) {
                            Log.d("titlesize", "" + map_schedule_title.size());
                            Log.d("schedule_title->", "" + map_schedule_title.get("rendered"));
                            arrayList_sc_title.add(String.valueOf(map_schedule_title.get("rendered")));

                        }


                        //schedule_content
                        map_schedule_content = (Map<String, Object>) map_schedule.get("content");
                        if (map_schedule_content != null) {
                            Log.d("sc_content->", "" + map_schedule_content.get("rendered"));
                            arrayList_sc_content.add(String.valueOf(map_schedule_content.get("rendered")));

                        } else {
                            //arrayList_sc_content.add("");
                        }


                        map_embedded = (Map<String, Object>) map_schedule.get("_embedded");

                        list_speaker = (List) map_embedded.get("speakers");

                        //Log.d("map_embedded", "map_embedded->" + map_embedded.size());
                        //Log.d("list_speaker", "list_speaker->" + list_speaker.size());

                        if (list_speaker != null)
                        {
                            for (int j = 0; j < list_speaker.size(); j++) {


                                map_speaker = (Map<String, Object>) list_speaker.get(j);

                                if (j == 0)
                                {
                                    Log.d("sc_speakerid->", "sc_speakerid->" + String.valueOf((Double) map_speaker.get("id")));

                                    if (map_speaker != null) {
                                        arrayList_sc_speakerid.add(String.valueOf((Double) map_speaker.get("id")));
                                    } else {
                                        // arrayList_sc_speakerid.add("");
                                    }

                                    map_speakermname = (Map<String, Object>) map_speaker.get("title");

                                    if (map_speakermname != null) {
                                        Log.d("sc_title->", "" + map_speakermname.get("rendered"));
                                        arrayList_sc_speaker.add(String.valueOf(map_speakermname.get("rendered")));
                                    } else {
                                        //   arrayList_sc_speaker.add("");
                                    }
                                }


                            }

                            //arrayList_sc_speakerid.add("notnull");
                           // arrayList_sc_speaker.add("notnull");

                        }
                        else
                        {
                            arrayList_sc_speakerid.add("");
                            arrayList_sc_speaker.add("");
                        }



                        map_schedule_datetime =   (Map<String, Object>) map_schedule.get("meta");


                        if (map_schedule_datetime != null)
                        {

                                Log.d("sc_datetime->",""+String.format("%.0f", map_schedule_datetime.get("_wcpt_session_time"))); //arrayList_sc_date

                                arrayList_sc_date.add(String.format("%.0f", map_schedule_datetime.get("_wcpt_session_time")));

                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                try
                                {

                                    Log.d("Unparseable","Unparseable"+getDate(Long.parseLong(String.format("%.0f", map_schedule_datetime.get("_wcpt_session_time")))));
                                    // Log.d("Unparseable","Unparseable"+sdf.parse((getFormattedDate(String.format("%.0f", map_schedule_test.get("value"))))));

                                    Date datetime  = sdf.parse((getDate(Long.parseLong(String.format("%.0f", map_schedule_datetime.get("_wcpt_session_time"))))));

                                    DateFormat sdf_date = new SimpleDateFormat("dd MMM yyyy");

                                    arrayList_date.add(sdf_date.format(datetime));
                                    arrayList_date_compare.add(sdf_date.format(datetime));

                                    Log.d("sdf_date","sdf_date"+datetime);
                                }
                                catch (ParseException e)
                                {
                                    e.printStackTrace();
                                }


                        }
                        else
                        {
                          /*  arrayList_sc_date.add("");
                            arrayList_date.add("");
                            arrayList_date_compare.add("");*/
                        }

                     /*   list_schedule_date_time = (List) gson.fromJson((String) map_schedule.get("post_meta").toString(), List.class);
                        DateFormat sdf_date = new SimpleDateFormat("dd MMM yyyy");

                        for (int j=0; j<list_schedule_date_time.size(); j++)
                        {
                            map_schedule_test = (Map<String,Object>)list_schedule_date_time.get(j);

                            if (map_schedule_test.get("key").equals("_wcpt_session_time"))
                            {
                                arrayList_sc_date.add(String.format("%.0f", map_schedule_test.get("value")));

                                String x = String.format("%.0f", map_schedule_test.get("value"));

                                System.out.printf(": ", x);
                                Log.d("map_schedule_test","map_schedule_test->"+x);
                               // Log.d("map_schedule_test","date->"+(getFormattedDate(String.format("%.0f", map_schedule_test.get("value")))));

                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                try
                                {

                                    Log.d("Unparseable","Unparseable"+getDate(Long.parseLong(String.format("%.0f", map_schedule_test.get("value")))));
                                   // Log.d("Unparseable","Unparseable"+sdf.parse((getFormattedDate(String.format("%.0f", map_schedule_test.get("value"))))));

                                    Date datetime  = sdf.parse((getDate(Long.parseLong(String.format("%.0f", map_schedule_test.get("value"))))));
                                    DateFormat sdf_date1 = new SimpleDateFormat("dd MMM yyyy");

                                    arrayList_date.add(sdf_date.format(datetime));
                                    arrayList_date_compare.add(sdf_date.format(datetime));

                                }
                                catch (ParseException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }*/
                    }

                    pdia.dismiss();

                    Set<String> hs = new HashSet<>();
                    hs.addAll(arrayList_date);
                    arrayList_date.clear();
                    arrayList_date.addAll(hs);


                    Collections.sort(arrayList_date, new Comparator<String>()
                    {
                        @Override
                        public int compare(String arg0, String arg1)
                        {
                            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                            int compareResult = 0;

                            try
                            {
                                Date arg0Date = format.parse(arg0);
                                Date arg1Date = format.parse(arg1);
                                compareResult = arg0Date.compareTo(arg1Date);
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                                compareResult = arg0.compareTo(arg1);
                            }
                            return compareResult;

                        }
                    });

                    int_items = arrayList_date.size();
                    for (int i = 0 ; i < arrayList_date.size() ; i++)
                    {
                        Log.d("arrayList_date","arrayList_date"+arrayList_date.get(i));


                        DateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy");
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM");

                        Date date = null;
                        try
                        {
                            if (i == 0)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_1= outputFormat.format(date);

                            }
                            else if ( i == 1)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_2= outputFormat.format(date);
                            }
                            else if ( i == 2)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_3= outputFormat.format(date);
                            }
                            else if ( i == 3)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_4= outputFormat.format(date);
                            }
                            else if ( i == 4)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_5= outputFormat.format(date);
                            }
                            else if ( i == 5)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_6 = outputFormat.format(date);
                            }
                            else if ( i == 6)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_7 = outputFormat.format(date);
                            }
                            else if ( i == 7)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_8 = outputFormat.format(date);
                            }
                            else if ( i == 8)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_9 = outputFormat.format(date);
                            }
                            else if ( i == 9)
                            {
                                date = inputFormat.parse(arrayList_date.get(i));
                                day_10 = outputFormat.format(date);
                            }

                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }

                    }


                    if (arrayList_sc_id.size() > 0)
                    {
                        viewPager.setVisibility(View.VISIBLE);
                        tv_no_schedule_data.setVisibility(View.GONE);
                    }
                    else
                    {
                        viewPager.setVisibility(View.GONE);
                        tv_no_schedule_data.setVisibility(View.VISIBLE);
                    }

                    viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

                    tabLayout.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    });

                    if (!Drawer_main.session_id.equals(""))
                    {

                        for (int i = 0 ; i < arrayList_sc_date.size() ; i++)
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            try
                            {
                                Date datetime  = sdf.parse(arrayList_sc_date.get(i));
                                DateFormat sdf_date = new SimpleDateFormat("dd MMM yyyy");

                                //arrayList_date.add(sdf_date.format(datetime));

                                for (int j = 0 ; j < arrayList_date.size(); j++)
                                {


                                    if (sdf_date.format(datetime).equals(arrayList_date.get(j)))
                                    {

                                        Double d = Double.parseDouble(arrayList_sc_id.get(i));
                                        if (Drawer_main.session_id.equals(String.valueOf(d.intValue())))
                                        {

                                            viewPager.setCurrentItem(j);
                                            Drawer_main.session_id = "";
                                        }
                                    }
                                }

                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                        }

                    }






                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

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

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new Fragment_Schedule_Day1();
                case 1 : return new Fragment_Schedule_Day2();
                case 2 : return new Fragment_Schedule_Day3();
                case 3 : return new Fragment_Schedule_Day4();
                case 4 : return new Fragment_Schedule_Day5();
                case 5 : return new Fragment_Schedule_Day6();
                case 6 : return new Fragment_Schedule_Day7();
                case 7 : return new Fragment_Schedule_Day8();
                case 8 : return new Fragment_Schedule_Day9();
                case 9 : return new Fragment_Schedule_Day10();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return day_1;
                case 1 :
                    return day_2;
                case 2 :
                    return day_3;
                case 3 :
                    return day_4;
                case 4:
                    return day_5;
                case 5:
                    return day_6;
                case 6:
                    return day_7;
                case 7:
                    return day_8;
                case 8:
                    return day_9;
                case 9:
                    return day_10;

            }
            return null;
        }
    }

    public void set_timer()
    {
        tv_days_togo = (TextView)x.findViewById(R.id.tv_days_togo);

        Calendar thatDay = Calendar.getInstance();

        thatDay.set(Calendar.DAY_OF_MONTH,Integer.parseInt(getString(R.string.timer_date)));
        thatDay.set(Calendar.MONTH,Integer.parseInt(getString(R.string.timer_month))); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, Integer.parseInt(getString(R.string.timer_year)));

        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() -  today.getTimeInMillis() ; //re
        long days = diff / (24 * 60 * 60 * 1000);

      //  tv_days_togo.setText(days +"days to go");
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

    /*public static String getFormattedDate(String str_date) {

        Log.d("str_date","str_date->"+str_date);

        long date = Long.parseLong(str_date);

        Date d = new Date((long) date * 1000);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }*/

    private String getDate(long timeStamp){
        DateFormat objFormatter = new SimpleDateFormat("dd-MM-yyyy");
        objFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        Calendar objCalendar =
                Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        objCalendar.setTimeInMillis(timeStamp*1000);//edit
        String result = objFormatter.format(objCalendar.getTime());
        objCalendar.clear();
        return result;
    }



}


