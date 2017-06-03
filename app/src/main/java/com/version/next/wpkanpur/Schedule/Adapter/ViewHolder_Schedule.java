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

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.version.next.wpkanpur.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pragma on 1/21/2016.
 */
public class ViewHolder_Schedule {

    LinearLayout ll_custom_speaker,id_ll_sc_detail,ll_datetime;
    CircleImageView iv_custom_speaker;
    TextView tv_custom_schedule_month,tv_custom_schedule_date,tv_custom_schedule_day,tv_custom_schedule_time,tv_custom_schedule_desc,
            tv_custom_schedule_speaker,tv_custom_schedule_speakername,tv_time_multiple_Session;
    RelativeLayout rl_schedulerow_main;

    public ViewHolder_Schedule(View v)
    {
        this.ll_custom_speaker = (LinearLayout) v.findViewById(R.id.ll_custom_speaker);
        this.id_ll_sc_detail = (LinearLayout) v.findViewById(R.id.id_ll_sc_detail);
        this.ll_datetime = (LinearLayout) v.findViewById(R.id.ll_datetime);

        this.iv_custom_speaker = (CircleImageView) v.findViewById(R.id.iv_custom_speaker);

        this.tv_custom_schedule_month = (TextView) v.findViewById(R.id.tv_custom_schedule_month);
        this.tv_custom_schedule_date = (TextView) v.findViewById(R.id.tv_custom_schedule_date);
        this.tv_custom_schedule_day = (TextView) v.findViewById(R.id.tv_custom_schedule_day);
        this.tv_custom_schedule_time = (TextView) v.findViewById(R.id.tv_custom_schedule_time);
        this.tv_custom_schedule_desc = (TextView) v.findViewById(R.id.tv_custom_schedule_desc);
        this.tv_custom_schedule_speaker = (TextView) v.findViewById(R.id.tv_custom_schedule_speaker);
        this.tv_custom_schedule_speakername = (TextView) v.findViewById(R.id.tv_custom_schedule_speakername);
        this.tv_time_multiple_Session = (TextView) v.findViewById(R.id.tv_time_multiple_Session);

        this.rl_schedulerow_main = (RelativeLayout) v.findViewById(R.id.rl_schedulerow_main);


    }
}
