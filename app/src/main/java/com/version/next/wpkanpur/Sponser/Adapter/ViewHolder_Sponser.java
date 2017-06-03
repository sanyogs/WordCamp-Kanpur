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

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.version.next.wpkanpur.R;

/**
 * Created by Pragma on 1/21/2016.
 */
public class ViewHolder_Sponser {

   ImageView iv_custom_sponser;
    TextView tv_sponsor_title,tv_spnsor_category;
    LinearLayout ll_custom_speaker;

    public ViewHolder_Sponser(View v)
    {
        this.iv_custom_sponser = (ImageView) v.findViewById(R.id.iv_custom_sponser);
        this.tv_sponsor_title = (TextView) v.findViewById(R.id.tv_sponsor_title);
        this.tv_spnsor_category = (TextView) v.findViewById(R.id.tv_spnsor_category);
        this.ll_custom_speaker = (LinearLayout) v.findViewById(R.id.ll_custom_speaker);

    }
}
