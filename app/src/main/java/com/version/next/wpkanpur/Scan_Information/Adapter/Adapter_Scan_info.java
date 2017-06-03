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

package com.version.next.wpkanpur.Scan_Information.Adapter;

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

import java.util.ArrayList;

/**
 * Created by Pragma on 1/21/2016.
 */
public class Adapter_Scan_info extends BaseAdapter {


    public static LayoutInflater inflater;

    Activity activity1;
    ArrayList<All_Scan_info> arr;
    boolean isFilter = false;
    public ArrayList<All_Scan_info> all_scaninfo_details;
    ArrayList<All_Scan_info> data = new ArrayList<All_Scan_info>();
    ViewHolder_Scan_info holder;
    All_Scan_info all_scan_info;

    ImageLoader imageLoader;
    DisplayImageOptions defaultOptions;


    public Adapter_Scan_info(Activity activity, ArrayList<All_Scan_info> object)
    {
        this.activity1 = activity;
        arr = object;
        all_scaninfo_details = object;
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
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    All_Scan_info getAppointmentDetail(int position) {
        return ((All_Scan_info) data.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;

        try{
            if (null == vi)
            {
                vi = inflater.inflate(R.layout.custom_scan_info, null);

                holder = new ViewHolder_Scan_info(vi);

                try
                {
                    all_scan_info = getAppointmentDetail(position);
                }
                catch(IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
            }

            final All_Scan_info appointment_list_detail;

            appointment_list_detail = data.get(position);


            if (null != appointment_list_detail)
            {

                vi = inflater.inflate(R.layout.custom_scan_info, null);

                holder = new ViewHolder_Scan_info(vi);

                if (data.get(position).getScan_name().equals("no_record_found"))
                {
                    holder.tv_scan_contact_name.setText("No data Available");
                    holder.tv_scan_contact_email.setVisibility(View.GONE);
                    holder.tv_scan_contact_phone.setVisibility(View.GONE);
                    holder.tv_scan_contact_twitter.setVisibility(View.GONE);
                }
                else
                {
                    holder.tv_scan_contact_name.setText(data.get(position).getScan_name());
                    holder.tv_scan_contact_email.setText(data.get(position).getScan_email());
                    holder.tv_scan_contact_phone.setText(data.get(position).getScan_phone());
                    holder.tv_scan_contact_twitter.setText(data.get(position).getScan_twitter());
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

}
