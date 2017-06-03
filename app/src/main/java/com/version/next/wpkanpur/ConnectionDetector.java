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

package com.version.next.wpkanpur.wpkanpur;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector
{
    private Context _context;

    public ConnectionDetector(Context context)
    {
        this._context = context;
    }

    public boolean isConnectingToInternet()
    {
          ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();

                  if (info != null)

                      for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                          return true;

          }
          return false;
    }
}
