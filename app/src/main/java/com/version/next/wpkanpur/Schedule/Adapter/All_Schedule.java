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

/**
 * Created by admin on 1/5/2016.
 */
public class All_Schedule
{

    String schedule_id,schedule_name,schedule_date,speaker_name,attnd_status,content,visibility,speakerid;

    public All_Schedule(String schedule_id, String schedule_name, String schedule_date, String speaker_name,String attnd_status,String content,String visibility,String speakerid)
    {
        this.schedule_id = schedule_id;
        this.schedule_name = schedule_name;
        this.schedule_date = schedule_date;
        this.speaker_name = speaker_name;
        this.attnd_status = attnd_status;
        this.content = content;
        this.visibility = visibility;
        this.speakerid = speakerid;
    }

    public String getSchedule_id()
    {
        return schedule_id;
    }
    public String getSchedule_name()
    {
        return schedule_name;
    }
    public String getSchedule_date()
    {
        return schedule_date;
    }

     public String getSpeaker_name()
    {
        return speaker_name;
    }

    public String getAttnd_status()
    {
        return attnd_status;
    }
    public String getContent()
    {
        return content;
    }
    public String getVisibility()
    {
        return visibility;
    }
    public String getSpeakerid()
    {
        return speakerid;
    }

    public void setSchedule_id(String schedule_id){this.schedule_id = schedule_id;}
    public void setSchedule_name(String schedule_name)
    {
        this.schedule_name = schedule_name;
    }
    public void setSchedule_date(String schedule_date)
    {
        this.schedule_date = schedule_date;
    }
    public void setSpeaker_name(String speaker_name){this.speaker_name = speaker_name;}
    public void setAttnd_status(String attnd_status)
    {
        this.attnd_status = attnd_status;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public void setVisibility(String visibility)
    {
        this.visibility = visibility;
    }
    public void setSpeakerid(String speakerid)
    {
        this.speakerid = speakerid;
    }
}
