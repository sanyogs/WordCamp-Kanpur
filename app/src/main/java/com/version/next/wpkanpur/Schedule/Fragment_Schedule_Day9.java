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


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.version.next.wpkanpur.R;
import com.version.next.wpkanpur.Schedule.Adapter.Adapter_Schedule;
import com.version.next.wpkanpur.Schedule.Adapter.All_Schedule;
import com.version.next.wpkanpur.Schedule.Tab_View.Tabs_Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Chintal-Pragma on 12/22/2016.
 */

public class Fragment_Schedule_Day9 extends Fragment
{
    ListView ll_schedule;
    ProgressDialog pdia;

    ArrayList<All_Schedule> arrayList_all_schedule = new ArrayList<All_Schedule>();
    Adapter_Schedule adapter_schedule;

    ArrayList<String> this_arrayList_sc_date = new ArrayList<String>();
    ArrayList<String> arrayList_visibility = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.schedule,container,false);

        ll_schedule = (ListView)rootview.findViewById(R.id.ll_schedule);

        pdia = new ProgressDialog(getActivity());
        pdia.setCanceledOnTouchOutside(false);
        pdia.setCancelable(false);
        pdia.setTitle("Please Wait...");
        pdia.show();

        adapter_schedule = new Adapter_Schedule(getActivity(),arrayList_all_schedule);
        ll_schedule.setAdapter(adapter_schedule);

        arrayList_all_schedule.clear();
        arrayList_visibility.clear();
        this_arrayList_sc_date.clear();

        for (int i = 0 ; i < Tabs_Schedule.arrayList_sc_date.size() ; i++) {

            if (this_arrayList_sc_date.size() > 0)
            {

                if (this_arrayList_sc_date.contains(Tabs_Schedule.arrayList_sc_date.get(i)))
                {
                    arrayList_visibility.add("no");
                }
                else
                {
                    this_arrayList_sc_date.add(Tabs_Schedule.arrayList_sc_date.get(i));
                    arrayList_visibility.add("yes");
                }
            }
            else
            {
                this_arrayList_sc_date.add(Tabs_Schedule.arrayList_sc_date.get(i));
                arrayList_visibility.add("yes");
            }
        }

        for (int i = 0 ; i < Tabs_Schedule.arrayList_sc_id.size() ; i++)
        {
            if (Tabs_Schedule.arrayList_date.get(8).equals(Tabs_Schedule.arrayList_date_compare.get(i)))
            {
                        arrayList_all_schedule.add(new All_Schedule(
                        Tabs_Schedule.arrayList_sc_id.get(i),
                        Tabs_Schedule.arrayList_sc_title.get(i),
                        Tabs_Schedule.arrayList_sc_date.get(i),
                        Tabs_Schedule.arrayList_sc_speaker.get(i),
                        "no",
                        Tabs_Schedule.arrayList_sc_content.get(i),
                        arrayList_visibility.get(i),
                        Tabs_Schedule.arrayList_sc_speakerid.get(i)));
            }
        }

        Collections.sort(arrayList_all_schedule, new Comparator<All_Schedule>()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(All_Schedule o1, All_Schedule o2)
            {
                return Long.compare(Long.parseLong(o1.getSchedule_date()),
                        Long.parseLong(o2.getSchedule_date()));
            }
        });

        pdia.dismiss();
        adapter_schedule.notifyDataSetChanged();

        return rootview;
    }

    public  String getDateCurrentTimeZone(long timestamp)
    {
        try
        {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));

            SimpleDateFormat sdf_date = new SimpleDateFormat("dd");

            Date currenTimeZone = (Date) calendar.getTime();

            return sdf_date.format(currenTimeZone);

        }
        catch (Exception e)
        {

        }
        return "";
    }

}
