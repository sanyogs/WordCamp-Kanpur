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

package com.version.next.wpkanpur.Speakers.Adapter.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.version.next.wpkanpur.R;
import com.version.next.wpkanpur.Speakers.Adapter.Speaker_Details;

import java.util.ArrayList;

/**
 * Created by Pragma on 1/21/2016.
 */
public class Adapter_Speaker extends BaseAdapter {


    public static LayoutInflater inflater;

    Activity activity1;
    ArrayList<All_Speaker> arr;
    boolean isFilter = false;
    public ArrayList<All_Speaker> all_speakers_details;
    ArrayList<All_Speaker> data = new ArrayList<All_Speaker>();
    ViewHolder_Speaker holder;
    All_Speaker all_speaker;

    ImageLoader imageLoader;
    DisplayImageOptions defaultOptions;


    public Adapter_Speaker(Activity activity, ArrayList<All_Speaker> object)
    {
        this.activity1 = activity;
        arr = object;
        all_speakers_details = object;
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

    All_Speaker getAppointmentDetail(int position) {
        return ((All_Speaker) data.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;

        try{
            if (null == vi)
            {
                vi = inflater.inflate(R.layout.custom_speakerdetail, null);

                holder = new ViewHolder_Speaker(vi);

                try
                {
                    all_speaker = getAppointmentDetail(position);
                }
                catch(IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
            }

            final All_Speaker appointment_list_detail;

            appointment_list_detail = data.get(position);


            if (null != appointment_list_detail)
            {

                vi = inflater.inflate(R.layout.custom_speakerdetail, null);

                holder = new ViewHolder_Speaker(vi);


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
                                    i.putExtra("speaker_fname",data.get(pos).first_name);
                                    i.putExtra("speaker_lname",data.get(pos).last_name);
                                    i.putExtra("intro_text",data.get(pos).intro_text);
                                    i.putExtra("session_title",data.get(pos).session_title);
                                    i.putExtra("session_meta",data.get(pos).session_meta);
                                    i.putExtra("session_id",data.get(pos).session_id);
                                    activity1.startActivity(i);
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

}
