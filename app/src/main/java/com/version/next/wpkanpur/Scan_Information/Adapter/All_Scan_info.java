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

/**
 * Created by admin on 1/5/2016.
 */
public class All_Scan_info
{

    String scan_id,scan_name,scan_phone,scan_email,scan_twitter;

    public All_Scan_info(String scan_id, String scan_name, String scan_phone, String scan_email , String scan_twitter)
    {
        this.scan_id = scan_id;
        this.scan_name = scan_name;
        this.scan_phone = scan_phone;
        this.scan_email = scan_email;
        this.scan_twitter = scan_twitter;
    }

    public String getScan_id()
    {
        return scan_id;
    }
    public String getScan_name()
    {
        return scan_name;
    }
    public String getScan_phone()
    {
        return scan_phone;
    }
    public String getScan_email()
    {
        return scan_email;
    }

       public String getScan_twitter()
    {
        return scan_twitter;
    }

    public void setScan_id(String scan_id)
    {
        this.scan_id = scan_id;
    }
    public void setScan_name(String scan_name)
    {
        this.scan_name = scan_name;
    }
    public void setScan_phone(String scan_phone)
    {
        this.scan_phone = scan_phone;
    }
    public void setScan_email(String scan_email)
    {
        this.scan_email = scan_email;
    }
    public void setScan_twitter(String scan_twitter)
    {
        this.scan_twitter = scan_twitter;
    }
}
