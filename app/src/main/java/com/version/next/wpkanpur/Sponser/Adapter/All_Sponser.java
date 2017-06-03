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

/**
 * Created by admin on 1/5/2016.
 */
public class All_Sponser
{

    String sponser_id,sponser_image,sponsor_title,sponsor_type,sponsor_cat_name,website_sponsor;

    public All_Sponser(String sponser_id, String sponser_image ,String sponsor_title,String sponsor_type,String sponsor_cat_name,String website_sponsor)
    {
        this.sponser_id = sponser_id;
        this.sponser_image = sponser_image;
        this.sponsor_title = sponsor_title;
        this.sponsor_type = sponsor_type;
        this.sponsor_cat_name = sponsor_cat_name;
        this.website_sponsor = website_sponsor;

    }

    public String getSponser_id()
    {
        return sponser_id;
    }
    public String getSponser_image()
    {
        return sponser_image;
    }
    public String getSponsor_title()
    {
        return sponsor_title;
    }

    public String getSponsor_type()
    {
        return sponsor_type;
    }

    public String getSponsor_cat_name()
    {
        return  sponsor_cat_name;
    }
    public String getWebsite_sponsor()
    {
        return  website_sponsor;
    }

    public void setSponser_id(String sponser_id){this.sponser_id = sponser_id;}
    public void setSponsor_image(String sponser_image){this.sponser_image = sponser_image;}
    public void setSponsor_title(String sponsor_title){this.sponsor_title = sponsor_title;}

}
