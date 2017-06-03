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

import android.view.View;
import android.widget.TextView;

import com.version.next.wpkanpur.R;

/**
 * Created by Pragma on 1/21/2016.
 */
public class ViewHolder_Notification_Detail {

    TextView tv_notification_title,tv_notification_message,tv_notification_time;

    public ViewHolder_Notification_Detail(View v)
    {
        this.tv_notification_title = (TextView) v.findViewById(R.id.tv_notification_title);
        this.tv_notification_message = (TextView) v.findViewById(R.id.tv_notification_message);
        this.tv_notification_time = (TextView) v.findViewById(R.id.tv_notification_time);

    }
}
