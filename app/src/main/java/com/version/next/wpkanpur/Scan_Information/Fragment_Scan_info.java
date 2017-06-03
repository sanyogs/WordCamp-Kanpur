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

package com.version.next.wpkanpur.Scan_Information;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.version.next.wpkanpur.DatabaseHelper;
import com.version.next.wpkanpur.R;
import com.version.next.wpkanpur.Scan_Information.Adapter.Adapter_Scan_info;
import com.version.next.wpkanpur.Scan_Information.Adapter.All_Scan_info;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Chintal-Pragma on 1/23/2017.
 */

public class Fragment_Scan_info extends Fragment
{
    ListView lv_scan_contact;
    DatabaseHelper db;

    ArrayList<All_Scan_info> arrayList_all_scaninfo = new ArrayList<All_Scan_info>();
    Adapter_Scan_info adapter_scan_info;
    String scan_id,scan_name,scan_phone,scan_email,scan_twitter;
    int id = 0;

    TextView tv_days_togo;
    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
                rootview = inflater.inflate(R.layout.scan_info,container,false);
                set_timer();

                db = new DatabaseHelper(getActivity());
                lv_scan_contact = (ListView)rootview.findViewById(R.id.lv_scan_contact);

                adapter_scan_info = new Adapter_Scan_info(getActivity(),arrayList_all_scaninfo);
                lv_scan_contact.setAdapter(adapter_scan_info);

                arrayList_all_scaninfo.clear();

                Cursor cursor_get_sacn_contact = db.Get_ScanContact();

                if (cursor_get_sacn_contact.getCount() != 0)
                {
                                while (cursor_get_sacn_contact.moveToNext())
                                {
                                    Log.d("name","name" + cursor_get_sacn_contact.getString(1));
                                    Log.d("phone","phone" + cursor_get_sacn_contact.getString(2));
                                    Log.d("email","email" + cursor_get_sacn_contact.getString(3));
                                    Log.d("twitter","twitter" + cursor_get_sacn_contact.getString(4));
                                    Log.d("id","id" + scan_id);

                                    scan_id = String.valueOf(id);
                                    id ++;

                                    scan_name = cursor_get_sacn_contact.getString(1);
                                    scan_phone = cursor_get_sacn_contact.getString(2);
                                    scan_email = cursor_get_sacn_contact.getString(3);
                                    scan_twitter = cursor_get_sacn_contact.getString(4);

                                    arrayList_all_scaninfo.add(new All_Scan_info(scan_id,scan_name,scan_phone,scan_email,scan_twitter));
                                }
                                    adapter_scan_info.notifyDataSetChanged();
                }
                else
                {
                    arrayList_all_scaninfo.add(new All_Scan_info("","no_record_found","","",""));
                }

                return  rootview;
    }

    public void set_timer()
    {
        tv_days_togo = (TextView)rootview.findViewById(R.id.tv_days_togo);

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
