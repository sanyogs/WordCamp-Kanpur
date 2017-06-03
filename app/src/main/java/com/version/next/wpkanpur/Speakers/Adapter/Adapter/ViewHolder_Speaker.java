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

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.version.next.wpkanpur.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pragma on 1/21/2016.
 */
public class ViewHolder_Speaker {

    LinearLayout ll_custom_speaker;
    CircleImageView iv_custom_speaker;
    TextView tv_custom_speaker_name;

    public ViewHolder_Speaker(View v)
    {
        this.ll_custom_speaker = (LinearLayout) v.findViewById(R.id.ll_custom_speaker);
        this.iv_custom_speaker = (CircleImageView) v.findViewById(R.id.iv_custom_speaker);
        this.tv_custom_speaker_name = (TextView) v.findViewById(R.id.tv_custom_speaker_name);

    }
}
