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

package com.version.next.wpkanpur.Notification_Detail;

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
import com.version.next.wpkanpur.Notification_Detail.Adapter.Adapter_Notification_Detail;
import com.version.next.wpkanpur.Notification_Detail.Adapter.All_Notification_Detail;
import com.version.next.wpkanpur.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Chintal-Pragma on 2/8/2017.
 */

public class Fragment_Notification_Detail extends Fragment
{
    ListView lv_notification;
    DatabaseHelper db;

    ArrayList<All_Notification_Detail> arrayList_notification_detail = new ArrayList<All_Notification_Detail>();
    Adapter_Notification_Detail adapter_notification_detail;
    View rootview;
    TextView tv_days_togo;
    String noti_id;
    int id = 0;
    String not_time,noti_message,noti_title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

             rootview = inflater.inflate(R.layout.notification_detail,container,false);
             set_timer();

            lv_notification = (ListView)rootview.findViewById(R.id.lv_notification);
            db = new DatabaseHelper(getActivity());

        adapter_notification_detail = new Adapter_Notification_Detail(getActivity(),arrayList_notification_detail);
        lv_notification.setAdapter(adapter_notification_detail);

        arrayList_notification_detail.clear();

        Cursor cursor_get_noti_details = db.Get_Notification();

        if (cursor_get_noti_details.getCount() != 0)
        {
            while (cursor_get_noti_details.moveToNext())
            {
                Log.d("time","time->" + cursor_get_noti_details.getString(1));
                Log.d("msg","msg->" + cursor_get_noti_details.getString(2));
                Log.d("title","title->" + cursor_get_noti_details.getString(3));
                Log.d("id","id->" + noti_id);

                noti_id = String.valueOf(id);
                id ++;

                not_time = cursor_get_noti_details.getString(1);
                noti_message = cursor_get_noti_details.getString(2);
                noti_title = cursor_get_noti_details.getString(3);

                arrayList_notification_detail.add(new All_Notification_Detail(noti_id,noti_title,noti_message,not_time));
            }
            adapter_notification_detail.notifyDataSetChanged();
        }
        else
        {
            arrayList_notification_detail.add(new All_Notification_Detail("","no_record_found","",""));
        }

        return rootview;
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
