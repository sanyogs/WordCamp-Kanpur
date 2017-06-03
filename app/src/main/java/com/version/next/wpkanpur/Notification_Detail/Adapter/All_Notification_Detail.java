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

/**
 * Created by admin on 1/5/2016.
 */
public class All_Notification_Detail
{

    String notification_id,notification_title,notification_message,notification_time;

    public All_Notification_Detail(String notification_id, String notification_title, String notification_message, String notification_time )
    {
        this.notification_id = notification_id;
        this.notification_title = notification_title;
        this.notification_message = notification_message;
        this.notification_time = notification_time;

    }

    public String getNotification_id()
    {
        return notification_id;
    }
    public String getNotification_title()
    {
        return notification_title;
    }
    public String getNotification_message()
    {
        return notification_message;
    }
    public String getNotification_time()
    {
        return notification_time;
    }



    public void setNotification_id(String notification_id)
    {
        this.notification_id = notification_id;
    }
    public void setNotification_title(String notification_title)
    {
        this.notification_title = notification_title;
    }

    public void setNotification_message(String notification_message) {
        this.notification_message = notification_message;
    }

    public void setNotification_time(String notification_time)
    {
        this.notification_time = notification_time;
    }

}
