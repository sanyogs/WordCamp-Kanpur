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

package com.version.next.wpkanpur.Sponser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
public class Adapter_Sponser extends BaseAdapter
{

    public static LayoutInflater inflater;

    Activity activity1;
    ArrayList<All_Sponser> arr;
    boolean isFilter = false;
    public ArrayList<All_Sponser> all_sponser_details;
    ArrayList<All_Sponser> data = new ArrayList<All_Sponser>();
    ViewHolder_Sponser holder;
    All_Sponser all_sponser;

    ImageLoader imageLoader;
    DisplayImageOptions defaultOptions;


    public Adapter_Sponser(Activity activity, ArrayList<All_Sponser> object)
    {
            this.activity1 = activity;
            arr = object;
            all_sponser_details = object;
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

    All_Sponser getAppointmentDetail(int position)
    {
        return ((All_Sponser) data.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;

        try{

                        if (null == vi)
                        {

                                    vi = inflater.inflate(R.layout.custom_sponser_detail, null);
                                    holder = new ViewHolder_Sponser(vi);
                                    try
                                    {
                                        all_sponser = getAppointmentDetail(position);
                                    }
                                    catch(IndexOutOfBoundsException e)
                                    {
                                        e.printStackTrace();
                                    }

                        }

                        final All_Sponser appointment_list_detail;
                        appointment_list_detail = data.get(position);

                        if (null != appointment_list_detail)
                        {
                            vi = inflater.inflate(R.layout.custom_sponser_detail, null);

                            holder = new ViewHolder_Sponser(vi);
                           imageLoader.displayImage(data.get(position).getSponser_image(), holder.iv_custom_sponser, defaultOptions);

                            Log.d("getSponser_image","getSponser_image"+data.get(position).getSponser_image());
                            holder.tv_sponsor_title.setText(data.get(position).getSponsor_title());

                            if (data.get(position).getSponsor_type().equalsIgnoreCase("other"))
                            {
                                holder.tv_spnsor_category.setVisibility(View.VISIBLE);
                                holder.tv_spnsor_category.setText(data.get(position).getSponsor_cat_name());
                            }
                            else
                            {
                                holder.tv_spnsor_category.setVisibility(View.GONE);
                            }

                            holder.ll_custom_speaker.setTag(position);
                            holder.ll_custom_speaker.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    int pos = (Integer)view.getTag();

                                    Uri uri = Uri.parse(data.get(pos).getWebsite_sponsor()); // missing 'http://' will cause crashed
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    activity1.startActivity(intent);
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
