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

package com.version.next.wpkanpur.Schedule.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.version.next.wpkanpur.R;
import com.version.next.wpkanpur.Schedule.Schedule_Description;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Pragma on 1/21/2016.
 */
public class Adapter_Schedule extends BaseAdapter
{

    public static LayoutInflater inflater;

    Activity activity1;
    ArrayList<All_Schedule> arr;
    boolean isFilter = false;
    public ArrayList<All_Schedule> all_speakers_details;
    ArrayList<All_Schedule> data = new ArrayList<All_Schedule>();
    ViewHolder_Schedule holder;
    All_Schedule all_speaker;


    public Adapter_Schedule(Activity activity, ArrayList<All_Schedule> object)
    {
            this.activity1 = activity;
            arr = object;
            all_speakers_details = object;
            this.data = object;
            inflater = (LayoutInflater)activity1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            notifyDataSetChanged();
      }

    @Override
    public int getCount()
    {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    All_Schedule getAppointmentDetail(int position)
    {
        return ((All_Schedule) data.get(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View vi=convertView;

        try{

                  if (null == vi)
                    {

                                vi = inflater.inflate(R.layout.custom_schedule_detail, null);
                                holder = new ViewHolder_Schedule(vi);
                                try
                                {
                                    all_speaker = getAppointmentDetail(position);
                                }
                                catch(IndexOutOfBoundsException e)
                                {
                                    e.printStackTrace();
                                }

                    }

                        final All_Schedule appointment_list_detail;
                        appointment_list_detail = data.get(position);

                        if (null != appointment_list_detail)
                        {
                            vi = inflater.inflate(R.layout.custom_schedule_detail, null);
                            holder = new ViewHolder_Schedule(vi);
                            holder.tv_custom_schedule_speaker.setVisibility(View.GONE);
                            holder.tv_custom_schedule_desc.setText(data.get(position).schedule_name);
                            holder.tv_custom_schedule_speakername.setText(data.get(position).speaker_name);
                            holder.tv_custom_schedule_time.setText(getDate(Long.parseLong(data.get(position).schedule_date)));
                            holder.tv_time_multiple_Session.setText(getDate(Long.parseLong(data.get(position).schedule_date)));

                            if (data.get(position).getVisibility().equalsIgnoreCase("yes"))
                            {
                                holder.tv_time_multiple_Session.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                holder.tv_time_multiple_Session.setVisibility(View.GONE);
                            }

                            holder.id_ll_sc_detail.setTag(position);
                            holder.id_ll_sc_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {

                                    int pos = (Integer)view.getTag();

                                    if (!data.get(pos).getContent().equals(""))
                                    {
                                        Log.d("content->","content->"+data.get(pos).getContent());

                                        Intent i = new Intent(activity1, Schedule_Description.class);
                                        i.putExtra("content",data.get(pos).getContent());
                                        i.putExtra("title",data.get(pos).getSchedule_name());
                                        i.putExtra("time",data.get(pos).getSchedule_date());
                                        i.putExtra("speaker",data.get(pos).getSpeaker_name());
                                        i.putExtra("speakerid",data.get(pos).getSpeakerid());
                                        activity1.startActivity(i);
                                    }

                                }
                            });
                        }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return vi;

    }


    public  String getDateCurrentTimeZone(String date)
    {
        try{
                    //Calendar calendar = Calendar.getInstance();
                 /*   TimeZone tz = TimeZone.getDefault();
                    calendar.setTimeInMillis(timestamp * 1000);
                    calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));*/

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-ddThh:mm:ss");
                    Date currenTimeZone = sdf.parse(date);

                    DateFormat dateFormat = new SimpleDateFormat("dd");
                    Log.d("currenTimeZone","currenTimeZone"+dateFormat.format(currenTimeZone));

                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-ddThh:mm:ss");
                    SimpleDateFormat sdf_date = new SimpleDateFormat("dd");
                    SimpleDateFormat sdf_month = new SimpleDateFormat("MMM");
                    SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm:ss");
                    SimpleDateFormat sdf_dayofweek = new SimpleDateFormat("EEE");


                    holder.tv_custom_schedule_date.setText(sdf_date.format(currenTimeZone));
                    holder.tv_custom_schedule_month.setText(sdf_month.format(currenTimeZone));
                    holder.tv_custom_schedule_time.setText(sdf_time.format(currenTimeZone));
                    holder.tv_custom_schedule_day.setText(sdf_dayofweek.format(currenTimeZone));

                    return sdf.format(currenTimeZone);
        }
        catch (Exception e)
        {

        }
        return "";
    }

   /* public static String getFormattedDate(String str_date) {

        Log.d("str_date","str_date->"+str_date);

        long date = Long.parseLong(str_date);

        Date d = new Date((long) date * 1000);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }*/

    /*public static String getFormattedDate(int date) {
        Date d = new Date((long) date * 1000);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }*/

  /*  public static String getFormattedTime(String str_date) {

        long date = Long.parseLong(str_date);

        Date d = new Date((long) date * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int minutes = 5 * (Math.round(calendar.get(Calendar.MINUTE) / 5));
        if (minutes == 60) {
            minutes = 0;
            calendar.set(Calendar.HOUR, (calendar.get(Calendar.HOUR) + 1));
        }
        calendar.set(Calendar.MINUTE, minutes);
        d = calendar.getTime();
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }*/

    private String getDate(long timeStamp)
    {
        DateFormat objFormatter = new SimpleDateFormat("hh:mm aa");
        objFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        Calendar objCalendar =
                Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        objCalendar.setTimeInMillis(timeStamp*1000);//edit
        String result = objFormatter.format(objCalendar.getTime());
        objCalendar.clear();
        return result;
    }
}
