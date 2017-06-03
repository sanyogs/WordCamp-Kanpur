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

package com.version.next.wpkanpur.Sponser;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.version.next.wpkanpur.R;
import com.version.next.wpkanpur.Sponser.Adapter.Adapter_Sponser;
import com.version.next.wpkanpur.Sponser.Adapter.All_Sponser;

import java.util.ArrayList;

/**
 * Created by Chintal-Pragma on 12/22/2016.
 */

public class Fragment_Sponsers_Other extends Fragment
{


    ListView ll_sponsor;
    ProgressDialog pdia;

    ArrayList<All_Sponser> arrayList_all_sponsor = new ArrayList<All_Sponser>();
    Adapter_Sponser adapter_sponsor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.sponsers,container,false);

        ll_sponsor = (ListView)rootview.findViewById(R.id.ll_sponsor);

        pdia = new ProgressDialog(getActivity());
        pdia.setCanceledOnTouchOutside(false);
        pdia.setCancelable(false);
        pdia.setTitle("Please Wait...");
        pdia.show();

        adapter_sponsor = new Adapter_Sponser(getActivity(),arrayList_all_sponsor);
        ll_sponsor.setAdapter(adapter_sponsor);

        arrayList_all_sponsor.clear();

        for (int i = 0; i < Tabs_Sponser.arrayList_sponsor_type.size() ; i++)
        {
            if (!Tabs_Sponser.arrayList_sponsor_type.get(i).equalsIgnoreCase("Gold") && !Tabs_Sponser.arrayList_sponsor_type.get(i).equalsIgnoreCase("Silver") &&
                    !Tabs_Sponser.arrayList_sponsor_type.get(i).equalsIgnoreCase("Bronze"))
            {

                Log.d("inother->","inother->"+Tabs_Sponser.arrayList_sponsor_type.get(i));

                arrayList_all_sponsor.add(new All_Sponser(Tabs_Sponser.arrayList_sponsor_id.get(i),
                        Tabs_Sponser.arrayList_sponsor_image.get(i),
                        Tabs_Sponser.arrayList_sponsor_title.get(i),"Other",Tabs_Sponser.arrayList_sponsor_type.get(i)
                        ,Tabs_Sponser.arrayList_sponsor_website.get(i)));
            }
        }

        pdia.dismiss();
        adapter_sponsor.notifyDataSetChanged();

        return rootview;
    }
}
