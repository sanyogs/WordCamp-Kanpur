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

package com.version.next.wpkanpur.Sponser;

    import android.app.ProgressDialog;
    import android.content.DialogInterface;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.design.widget.TabLayout;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentPagerAdapter;
    import android.support.v4.view.ViewPager;
    import android.support.v7.app.AlertDialog;
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
    import com.version.next.wpkanpur.ConnectionDetector;
    import com.version.next.wpkanpur.Drawer.Drawer_main;
    import com.version.next.wpkanpur.R;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.IllegalFormatCodePointException;
    import java.util.List;
    import java.util.Map;


    public class Tabs_Sponser extends Fragment {


        public static TabLayout tabLayout;
        public static ViewPager viewPager;
        public static int int_items = 4 ;
        ProgressDialog pdia;


        TextView tv_days_togo;
        View x;

        Gson gson;
        List<Object> list_sponsor,list_featuredmedia,list_term,list_name;
        Map<String,Object> map_sponsor,map_sponsor_title,map_sponsor_mediadetails,map_media_details,map_media,map_sizes,map_full,map_sponsor_type,map_meta_website;

       public static ArrayList<String> arrayList_sponsor_id = new ArrayList<String>();
       public static ArrayList<String> arrayList_sponsor_title = new ArrayList<String>();
       public static ArrayList<String> arrayList_sponsor_type = new ArrayList<String>();
       public static ArrayList<String> arrayList_sponsor_image = new ArrayList<String>();
       public static ArrayList<String> arrayList_sponsor_website = new ArrayList<String>();

        SharedPreferences sharedPreferences_sponsor_data;
        SharedPreferences.Editor editor_sponsor_data;
        String sponsor_response="";

        ConnectionDetector cd;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             x =  inflater.inflate(R.layout.tab_layout_sponser,null);
            tabLayout = (TabLayout) x.findViewById(R.id.tabs);
            viewPager = (ViewPager) x.findViewById(R.id.viewpager);

            cd  = new ConnectionDetector(getActivity());

            set_timer();


            sharedPreferences_sponsor_data = getActivity().getSharedPreferences("Sponsor_Data",getActivity().MODE_PRIVATE);
            editor_sponsor_data = sharedPreferences_sponsor_data.edit();


            pdia = new ProgressDialog(getActivity());
            pdia.setCanceledOnTouchOutside(false);
            pdia.setCancelable(false);
            pdia.setTitle("Please Wait...");
            pdia.show();

            sponsor_response = sharedPreferences_sponsor_data.getString("sponsor_response","");

            //wp-json/posts?type=wcb_sponsor&filter[posts_per_page]=-1
            if (!sponsor_response.equals(""))
            {
                set_sponsor_data(sponsor_response);
            }
            else
            {
                if (cd.isConnectingToInternet())
                {
                    get_sponsor_data();
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

                        get_sponsor_data();
                    }
                });
            }



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
                switch (position)
                {
                    case 0 : return new Fragment_Sponsers_Gold();
                    case 1 : return new Fragment_Sponsers_Silver();
                    case 2 : return new Fragment_Sponsers_Bronze();
                    case 3 : return new Fragment_Sponsers_Other();

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
                        return "Gold";
                    case 1 :
                        return "Silver";
                    case 2 :
                        return "Bronze";
                    case 3 :
                        return "Other";
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


               // tv_days_togo.setText(days +"days to go");
                String date_to_live = getString(R.string.no_of_days);
                date_to_live = "-"+date_to_live;

                int date_to_live_val = Integer.parseInt(date_to_live) + 1;
                Log.d("date_to_live","date_to_live"+date_to_live_val);
                Log.d("days","days"+days);

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



        public void get_sponsor_data()
        {
            StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.url)+"wp-json/wp/v2/sponsors?per_page=100&_embed", new Response.Listener<String>()
            {
                @Override
                public void onResponse(String s)
                {
                    sponsor_response = s;

                    editor_sponsor_data.putString("sponsor_response",sponsor_response);
                    editor_sponsor_data.commit();

                    set_sponsor_data(sponsor_response);


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

            public void set_sponsor_data(String sponsor_response)
            {
                gson = new Gson();
                list_sponsor = (List) gson.fromJson(sponsor_response, List.class);

                arrayList_sponsor_id.clear();
                arrayList_sponsor_title.clear();
                arrayList_sponsor_type.clear();
                arrayList_sponsor_image.clear();
                arrayList_sponsor_website.clear();


                Log.d("response_sponser","response_sponser->"+list_sponsor.size());


                for (int i = 0 ; i < list_sponsor.size() ; i++)
                {
                    map_sponsor = (Map<String, Object>) list_sponsor.get(i);

                    Log.d("sponsor_id->",""+String.valueOf((Double)map_sponsor.get("id")));
                    arrayList_sponsor_id.add(String.valueOf((Double)map_sponsor.get("id")));

                    map_sponsor_title = (Map<String, Object>) map_sponsor.get("title");


                    if (map_sponsor_title !=null)
                    {
                        Log.d("sponsor_title->",""+map_sponsor_title.get("rendered"));
                        arrayList_sponsor_title.add(String.valueOf(map_sponsor_title.get("rendered")));
                    }
                    else
                    {
                        arrayList_sponsor_title.add("");
                    }

                    map_sponsor_mediadetails = (Map<String, Object>) map_sponsor.get("_embedded");
                    list_featuredmedia = (List)map_sponsor_mediadetails.get("wp:featuredmedia");

                    if (list_featuredmedia !=null)
                    {
                        Log.d("list_featuredmedia","list_featuredmedia"+list_featuredmedia.size());

                        for (int i1=0; i1<list_featuredmedia.size(); i1++)
                        {
                            if (i1 == 0)
                            {
                                map_media = (Map<String, Object>) list_featuredmedia.get(i1);

                                map_media_details = (Map<String, Object>) map_media.get("media_details");
                                if (map_media_details != null)
                                {


                                    Log.d("map_media_details", "map_media_details->" + map_media_details);
                                    map_sizes = (Map<String, Object>) map_media_details.get("sizes");

                                    if (map_sizes != null)
                                    {
                                        map_full = (Map<String, Object>) map_sizes.get("full");
                                        Log.d("source_url_test=>","source_url=>"+ String.valueOf(map_full.get("source_url")));
                                        arrayList_sponsor_image.add(String.valueOf(map_full.get("source_url")));
                                    }
                                    else
                                    {
                                        arrayList_sponsor_image.add("");
                                    }
                                }

                            }
                        }
                    }
                    else
                    {
                        arrayList_sponsor_image.add("");
                    }



                    Log.d("source_url=>","source_url=>"+arrayList_sponsor_id.size());

                    list_term = (List)map_sponsor_mediadetails.get("wp:term");
                    if (list_term !=null)
                    {
                        for (int i1 = 0 ; i1< list_term.size() ; i1++)
                        {
                            list_name = (List)list_term.get(0);

                            Log.d ("list_name_test=>","list_name_test=>"+list_name);
                            Log.d ("list_name_test_size","list_name_test_size=>"+list_name.size());

                            for (int i2=0; i2<list_name.size(); i2++)
                            {
                                if (i2 ==0)
                                {
                                    map_sponsor_type = (Map<String, Object>) list_name.get(i2);

                                    //map_sponsor_name = (Map<String, Object>) map_sponsor_type.get("name");

                                    Log.d ("map_sponsor_name","map_sponsor_name=>"+map_sponsor_type.get("name"));
                                    arrayList_sponsor_type.add(String.valueOf(map_sponsor_type.get("name")));
                                }
                                else
                                {arrayList_sponsor_type.add("");

                                }

                            }

                        }
                    }
                    else
                    {
                        arrayList_sponsor_type.add("");
                    }




                    map_meta_website = (Map<String, Object>) map_sponsor.get("meta");

                    if (map_meta_website != null)
                    {
                        arrayList_sponsor_website.add(String.valueOf(map_meta_website.get("_wcpt_sponsor_website")));
                    }
                    else
                    {
                        arrayList_sponsor_website.add("");
                    }

                }
                try
                {
                    viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

                    tabLayout.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    });


                }
                catch (Exception e){e.printStackTrace();}

                pdia.dismiss();
            }
        }

