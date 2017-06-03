package com.version.next.wpkanpur.Drawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.version.next.wpkanpur.ConnectionDetector;
import com.version.next.wpkanpur.Notification_Detail.Fragment_Notification_Detail;
import com.version.next.wpkanpur.QR_Contact.Contact_scan;
import com.version.next.wpkanpur.R;
import com.version.next.wpkanpur.Scan_Information.Fragment_Scan_info;
import com.version.next.wpkanpur.Schedule.Tab_View.Tabs_Schedule;
import com.version.next.wpkanpur.Speakers.Adapter.Fragment_Speakers;
import com.version.next.wpkanpur.Sponser.Tabs_Sponser;
import com.version.next.wpkanpur.Venue.Book_Ticket;
import com.version.next.wpkanpur.Venue.Fragment_Venue;

/**
 * Created by chintal-pragma
 */

public class Drawer_main extends AppCompatActivity
{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar toolbar;
    LinearLayout ll_scan_contact,ll_buy_ticket;
    public static ImageView imageView_actionbar_left_reload;

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ConnectionDetector cd;

    Bundle extras;
    public static String session_id = "";
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_final);

        cd  = new ConnectionDetector(Drawer_main.this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.mNavigationView) ;

        imageView_actionbar_left_reload = (ImageView) findViewById(R.id.imageView_actionbar_left_reload) ;

        View header = LayoutInflater.from(this).inflate(R.layout.drawer_header, null);
        mNavigationView.addHeaderView(header);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        extras = getIntent().getExtras();

        if(extras.getString("intent_type","").equals("speaker"))
        {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView,new Fragment_Speakers()).commit();
        }
        else if (extras.getString("intent_type","").equals("schedule"))
        {
            session_id = extras.getString("session_id","");
            Log.d("session_id","session_id->>>"+session_id);
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView,new Tabs_Schedule()).commit();
        }
        else if (extras.getString("intent_type","").equals("notification"))
        {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView,new Fragment_Notification_Detail()).commit();
        }
        else
        {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView,new Fragment_Speakers()).commit();
        }

        imageView_actionbar_left_reload.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Drawer_main.this,R.color.colorPrimary));
        }

        ll_scan_contact = (LinearLayout) header.findViewById(R.id.ll_scan_contact);

        ll_scan_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Drawer_main.this, Contact_scan.class);
                startActivity(i);
            }
        });


        ll_buy_ticket = (LinearLayout) mNavigationView.findViewById(R.id.ll_buy_ticket);
        ll_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.open_buyticket)));
                startActivity(browserIntent);
*/

                if (cd.isConnectingToInternet())
                {
                    Intent i = new Intent(Drawer_main.this, Book_Ticket.class);
                    startActivity(i);
                }
                else
                {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Drawer_main.this);
                    builder1.setTitle("Kindly Check your Internet Connection");
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

                Log.d("btn_buy_ticket","btn_buy_ticket->");
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                if (menuItem.getItemId() == R.id.nav_item_speakers)
                {
                    imageView_actionbar_left_reload.setVisibility(View.VISIBLE);

                    mFragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new Fragment_Speakers()).commit();

                }
                else if (menuItem.getItemId() == R.id.nav_item_schedule)
                {
                    imageView_actionbar_left_reload.setVisibility(View.GONE);

                    if (cd.isConnectingToInternet())
                    {
                        mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.containerView,new Tabs_Schedule()).commit();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Drawer_main.this);
                        builder1.setTitle("Kindly Check your Internet Connection");
                        builder1.setCancelable(true);
                        builder1.setNeutralButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }

                }
                else if (menuItem.getItemId() == R.id.nav_item_sponsers)
                {
                    imageView_actionbar_left_reload.setVisibility(View.GONE);

                    if (cd.isConnectingToInternet())
                    {
                        mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView,new Tabs_Sponser()).commit();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Drawer_main.this);
                        builder1.setTitle("Kindly Check your Internet Connection");
                        builder1.setCancelable(true);
                        builder1.setNeutralButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }

                }
                else  if (menuItem.getItemId() == R.id.nav_item_venue)
                {
                    imageView_actionbar_left_reload.setVisibility(View.GONE);

                    if (cd.isConnectingToInternet())
                    {
                        mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView,new Fragment_Venue()).commit();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Drawer_main.this);
                        builder1.setTitle("Kindly Check your Internet Connection");
                        builder1.setCancelable(true);
                        builder1.setNeutralButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }

                }
                else  if (menuItem.getItemId() == R.id.nav_item_scan_info)
                {
                    imageView_actionbar_left_reload.setVisibility(View.GONE);

                    if (cd.isConnectingToInternet())
                    {
                        mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView,new Fragment_Scan_info()).commit();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Drawer_main.this);
                        builder1.setTitle("Kindly Check your Internet Connection");
                        builder1.setCancelable(true);
                        builder1.setNeutralButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }

                }
                else  if (menuItem.getItemId() == R.id.nav_item_notification_detail)
                {
                    imageView_actionbar_left_reload.setVisibility(View.GONE);

                    if (cd.isConnectingToInternet())
                    {
                        mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView,new Fragment_Notification_Detail()).commit();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Drawer_main.this);
                        builder1.setTitle("Kindly Check your Internet Connection");
                        builder1.setCancelable(true);
                        builder1.setNeutralButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }

                }

                return false;
            }

        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            finish();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }
}
