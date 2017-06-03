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

import android.view.View;
import android.widget.TextView;

import com.version.next.wpkanpur.R;

/**
 * Created by Pragma on 1/21/2016.
 */
public class ViewHolder_Scan_info {

    TextView tv_scan_contact_name,tv_scan_contact_phone,tv_scan_contact_email,tv_scan_contact_twitter;

    public ViewHolder_Scan_info(View v)
    {
        this.tv_scan_contact_name = (TextView) v.findViewById(R.id.tv_scan_contact_name);
        this.tv_scan_contact_phone = (TextView) v.findViewById(R.id.tv_scan_contact_phone);
        this.tv_scan_contact_email = (TextView) v.findViewById(R.id.tv_scan_contact_email);
        this.tv_scan_contact_twitter = (TextView) v.findViewById(R.id.tv_scan_contact_twitter);

    }
}
