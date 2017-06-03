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

package com.version.next.wpkanpur.Notification_Detail.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.version.next.wpkanpur.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Pragma on 1/21/2016.
 */
public class Adapter_Notification_Detail extends BaseAdapter {


    public static LayoutInflater inflater;

    Activity activity1;
    ArrayList<All_Notification_Detail> arr;
    boolean isFilter = false;
    public ArrayList<All_Notification_Detail> all_notification_details;
    ArrayList<All_Notification_Detail> data = new ArrayList<All_Notification_Detail>();
    ViewHolder_Notification_Detail holder;
    All_Notification_Detail all_notinfo_detail;

    ImageLoader imageLoader;
    DisplayImageOptions defaultOptions;

    public Adapter_Notification_Detail(Activity activity, ArrayList<All_Notification_Detail> object)
    {
            this.activity1 = activity;
            arr = object;
            all_notification_details = object;
            this.data = object;
            inflater = (LayoutInflater)activity1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

            imageLoader = ImageLoader.getInstance();
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity1));

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

    All_Notification_Detail getAppointmentDetail(int position)
    {
        return ((All_Notification_Detail) data.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;

        try
        {
                if (null == vi)
                {
                        vi = inflater.inflate(R.layout.custom_notification_detail, null);

                        holder = new ViewHolder_Notification_Detail(vi);

                        try
                        {
                            all_notinfo_detail = getAppointmentDetail(position);
                        }
                        catch(IndexOutOfBoundsException e)
                        {
                            e.printStackTrace();
                        }
                }

                final All_Notification_Detail appointment_list_detail;

                appointment_list_detail = data.get(position);


                if (null != appointment_list_detail)
                {

                            vi = inflater.inflate(R.layout.custom_notification_detail, null);

                            holder = new ViewHolder_Notification_Detail(vi);

                            if (data.get(position).getNotification_title().equals("no_record_found"))
                            {
                                holder.tv_notification_title.setText("No data Available");
                                holder.tv_notification_message.setVisibility(View.GONE);
                                holder.tv_notification_time.setVisibility(View.GONE);

                            }
                            else {
                                if (!data.get(position).getNotification_title().equals("")) {
                                    holder.tv_notification_title.setVisibility(View.VISIBLE);
                                    holder.tv_notification_title.setText(data.get(position).getNotification_title());
                                } else {
                                    holder.tv_notification_title.setVisibility(View.GONE);
                                }

                                if (!data.get(position).getNotification_time().equals(""))
                                {
                                    holder.tv_notification_time.setVisibility(View.VISIBLE);
                                    holder.tv_notification_time.setText(getDate(Long.parseLong(data.get(position).getNotification_time())));
                                }
                                else {
                                    holder.tv_notification_time.setVisibility(View.GONE);
                                }

                                if (!data.get(position).getNotification_message().equals(""))
                                {
                                    holder.tv_notification_message.setVisibility(View.VISIBLE);
                                    holder.tv_notification_message.setText(data.get(position).getNotification_message());
                                }
                                else
                                {
                                    holder.tv_notification_message.setVisibility(View.GONE);
                                }



                            }

                            /*
                            holder.tv_custom_speaker_name.setText(data.get(position).speaker_name);
                            imageLoader.displayImage(data.get(position).getSpeaker_image(), holder.iv_custom_speaker, defaultOptions);

                            holder.ll_custom_speaker.setTag(position);
                            holder.ll_custom_speaker.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                        int pos = (Integer)view.getTag();

                                        Intent i = new Intent(activity1, Speaker_Details.class);
                                        i.putExtra("speaker_name",data.get(pos).speaker_name);
                                        i.putExtra("speaker_image",data.get(pos).speaker_image);
                                        i.putExtra("speaker_desc",data.get(pos).speaker_desc);
                                        activity1.startActivity(i);
                                }
                            });*/
                }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return vi;

    }

    public static String getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm: aa");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
