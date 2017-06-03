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

/**
 * Created by admin on 1/5/2016.
 */
public class All_Speaker
{

    public String speaker_id,speaker_name,speaker_image,speaker_desc,first_name,last_name,gravtar_pic,intro_text,session_title,session_meta,session_id;

    public All_Speaker(String speaker_id,String speaker_name,String speaker_image,String speaker_desc,String first_name , String last_name , String gravtar_pic, String intro_text,
                        String session_title ,String session_meta ,String session_id)
    {
        this.speaker_id = speaker_id;
        this.speaker_name = speaker_name;
        this.speaker_image = speaker_image;
        this.speaker_desc = speaker_desc;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gravtar_pic = gravtar_pic;
        this.intro_text = intro_text;
        this.session_title = session_title;
        this.session_meta = session_meta;
        this.session_id = session_id;
    }

    public String getSpeaker_id()
    {
        return speaker_id;
    }
    public String getSpeaker_name()
    {
        return speaker_name;
    }
    public String getSpeaker_image()
    {
        return speaker_image;
    }
    public String getSpeaker_desc()
    {
        return speaker_desc;
    }
    public String getFirst_name()
    {
        return first_name;
    }
    public String getLast_name()
    {
        return last_name;
    }
    public String getGravtar_pic()
    {
        return gravtar_pic;
    }
    public String getIntro_text()
    {
        return intro_text;
    }
    public String getSession_title()
    {
        return session_title;
    }
    public String getSession_meta()
    {
        return session_meta;
    }
    public String getSession_id()
    {
        return session_id;
    }


    public void setSpeaker_id(String speaker_id)
    {
        this.speaker_id = speaker_id;
    }
    public void setSpeaker_name(String speaker_name)
    {
        this.speaker_name = speaker_name;
    }
    public void setSpeaker_image(String speaker_image)
    {
        this.speaker_image = speaker_image;
    }
    public void setSpeaker_desc(String speaker_desc)
    {
        this.speaker_desc = speaker_desc;
    }
    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public void setGravtar_pic(String gravtar_pic)
    {
        this.gravtar_pic = gravtar_pic;
    }

    public void setIntro_text(String intro_text)
    {
        this.intro_text = intro_text;
    }

    public void setSession_title(String session_title)
    {
        this.session_title = session_title;
    }

    public void setSession_meta(String session_meta)
    {
        this.session_meta = session_meta;
    }
    public void setSession_id(String session_id)
    {
        this.session_id = session_id;
    }
}
