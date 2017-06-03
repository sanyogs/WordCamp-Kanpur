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

package com.version.next.wpkanpur.QR_Contact;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.zxing.Result;
import com.version.next.wpkanpur.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Email;
import ezvcard.property.Note;
import ezvcard.property.RawProperty;
import ezvcard.property.Telephone;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by Chintal-Pragma on 12/31/2016.
 */

public class Contact_scan extends Activity implements ZXingScannerView.ResultHandler
{
    private ZXingScannerView mScannerView;
    String arr_contactdetails[];
    String name,Phone,Email,Twitter,Notes;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        db = new DatabaseHelper(this);
        if (weHavePermissionToReadContacts())
        {
            mScannerView.setAutoFocus(true);
            mScannerView.startCamera();
        }
        else
        {
            requestReadContactsPermissionFirst();
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if (weHavePermissionToReadContacts())
        {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera(0);
        }
        else
        {
            requestReadContactsPermissionFirst();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (weHavePermissionToReadContacts())
        {
            mScannerView.stopCamera();
        }
        else
        {
            requestReadContactsPermissionFirst();
        }
    }


    @Override
    public void handleResult(Result rawResult)
    {

        Log.d("rawResult->","rawResult->"+rawResult.getText().toString());
        VCard vcard = Ezvcard.parse(rawResult.getText().toString()).first();
        String fullName = vcard.getFormattedName().getValue();
        List<Note> list_note = vcard.getNotes();
        List<Telephone> list_phone = vcard.getTelephoneNumbers();
        List<Email> list_email = vcard.getEmails();
        List<RawProperty> list_twitter = vcard.getExtendedProperties();


         arr_contactdetails = rawResult.getText().toString().split("\n");

        if (arr_contactdetails.length == 9)
        {

                    if (weHavePermissionTowriteContacts())
                    {
                                    Log.v("arr_contactdetails","arr_contactdetails"+arr_contactdetails.length);
                                    Log.v("name>","name->"+arr_contactdetails[3].substring(arr_contactdetails[3].indexOf(":")+1));
                                    Log.v("twittwer->","twittwer->"+arr_contactdetails[4].substring(arr_contactdetails[4].indexOf(":")+1));
                                    Log.v("EMAIL->","EMAIL->"+arr_contactdetails[6].substring(arr_contactdetails[6].indexOf(":")+1));
                                    //Log.v("type->","type->"+arr_contactdetails[7].substring(arr_contactdetails[7].indexOf("=")+1,arr_contactdetails[7].indexOf(",")));
                                    Log.v("phone->","phone->"+arr_contactdetails[7].substring(arr_contactdetails[7].indexOf(":")+1));

                                         /*   Log.d("fullName=>","fullName=>"+fullName);
                                            Log.d("phone=>","phone=>"+list_phone.get(0).getText());
                                            Log.d("email=>","email=>"+list_email.get(0).getValue());
                                            Log.d("twittwer=>","twittwer=>"+list_twitter.size());
                                            Log.d("twittwer=>","twittwer=>"+list_twitter.get(0).getValue());
                                            Log.d("note=>","note=>"+list_note.get(0).getValue());
*/

                                           /*  name = arr_contactdetails[3].substring(arr_contactdetails[3].indexOf(":")+1);
                                             Phone = arr_contactdetails[7].substring(arr_contactdetails[7].indexOf(":")+1);
                                             Email = arr_contactdetails[6].substring(arr_contactdetails[6].indexOf(":")+1);
                                             Twitter = arr_contactdetails[4].substring(arr_contactdetails[4].indexOf(":")+1);*/

                                            if (fullName.equals(""))
                                            {
                                                name = "";
                                            }
                                            else
                                            {
                                                name = fullName;
                                            }

                                            if (list_phone.size() <= 0)
                                            {
                                                Phone = "";
                                            }
                                            else
                                            {
                                                Phone = list_phone.get(0).getText();
                                            }

                                            if (list_email.size() <=0)
                                            {
                                                Email = "";
                                            }
                                            else
                                            {
                                                Email = list_email.get(0).getValue();
                                            }

                                            if (list_twitter.size() <= 0 )
                                            {
                                                Twitter = "";
                                            }
                                            else
                                            {
                                                Twitter = list_twitter.get(0).getValue();
                                            }


                                            if (list_note.size() <=0)
                                            {
                                                Notes = "";
                                            }
                                            else
                                            {
                                                Notes = list_note.get(0).getValue();
                                            }

                                            if (db.Insert_ScanContact(name,Phone,Email,Twitter,"") == 1l)
                                            {
                                                //update
                                               // Toast.makeText(getApplicationContext(),"Already added",Toast.LENGTH_SHORT).show();
                                               // mScannerView.resumeCameraPreview(Contact_scan.this);
                                            }
                                            else
                                            {
                                                //insert
                                                db.Insert_ScanContact(name,Phone,Email,Twitter,"");
                                            }

                                            Intent intent = new Intent(Intent.ACTION_INSERT);
                                            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

                                            intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                                            intent.putExtra(ContactsContract.Intents.Insert.PHONE, Phone);
                                            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, Email);
                                            intent.putExtra(ContactsContract.Intents.Insert.NOTES, Notes);
                                            intent.putExtra(ContactsContract.Intents.Insert.IM_HANDLE, Twitter);
                                            //intent.putExtra(ContactsContract.Intents.Insert.IM_PROTOCOL,ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM);
                                            intent.putExtra("Twitter",ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM);
                                            intent.putExtra(ContactsContract.CommonDataKinds.Im.LABEL,"Twitter");
                                            intent.putExtra(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL,"Twitter");

                                            int PICK_CONTACT = 100;
                                            startActivityForResult(intent, PICK_CONTACT);

                                            /*Intent intent = new Intent(
                                                    ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
                                                    Uri.parse("tel:" + phoneNumber));
                                            intent.putExtra(ContactsContract.Intents.EXTRA_FORCE_CREATE, true);
                                            startActivity(intent);*/

                    }
                    else
                    {
                        requestwriteContactsPermissionFirst();
                    }
        }
        else
        {
            mScannerView.resumeCameraPreview(this);
        }
                    /*
                    BEGIN:VCARD
                    VERSION:3.0
                    N:Sonar;Ratnesh
                    FN:Ratnesh Sonar
                    X-TWITTER:https://twitter.com/ratneshsonar
                    URL:http://ratnesh.me
                    EMAIL;TYPE=PREF,INTERNET:ratneshsonar2011@gmail.com
                    TEL;TYPE=HOME,CELL:8976792190
                    END:VCARD*/

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == 100)
            {
                Toast.makeText(Contact_scan.this,"Succesfully Added",Toast.LENGTH_SHORT).show();
                mScannerView.resumeCameraPreview(this);

            }

        }
    }

    private boolean weHavePermissionToReadContacts()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadContactsPermissionFirst()
    {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
            {
                requestForResultContactPermission();
            }
            else
            {
                requestForResultContactPermission();
            }
    }

    private void requestForResultContactPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 123);
    }


    //writecontact
    private boolean weHavePermissionTowriteContacts()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestwriteContactsPermissionFirst()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS))
        {
            requestForResultwriteContactPermission();
        }
        else
        {
            requestForResultwriteContactPermission();
        }
    }

    private void requestForResultwriteContactPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 124);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123&& grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            if (!weHavePermissionTowriteContacts())
            {
                requestwriteContactsPermissionFirst();
            }


        }
        else if (requestCode == 124&& grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {


            mScannerView.setResultHandler(this);
            mScannerView.startCamera(0);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    void add_contact( String displayname, String homenumber, String homeemail,String twitter)
    {
                //http://androidsurya.blogspot.in/2011/12/android-adding-contacts.html

                String DisplayName = displayname;
                String MobileNumber = homenumber;
                String homeemailID = homeemail;
                String twitter_id = twitter;

                ArrayList<ContentProviderOperation> contentProviderOperation = new
                        ArrayList<ContentProviderOperation>();

                contentProviderOperation.add(ContentProviderOperation
                        .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());

                // ------------------------------------------------------ Names
                if (DisplayName != null) {
                    contentProviderOperation.add(ContentProviderOperation
                            .newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(
                                    ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(
                                    ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(
                                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                    DisplayName).build());
                }

                // ------------------------------------------------------ Mobile Number
                if (MobileNumber != null) {
                    contentProviderOperation.add(ContentProviderOperation
                            .newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(
                                    ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(
                                    ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    MobileNumber)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());
                }

                // ------------------------------------------------------ homeEmail
                if (homeemailID != null)
                {
                    contentProviderOperation.add(ContentProviderOperation
                            .newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(
                                    ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(
                                    ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Email.DATA,
                                    homeemailID)
                            .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                                    ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                            .build());
                }

                //----------------------------------------------twitter----------------------

                if (twitter_id != null)
                {

                  /*  contentProviderOperation.add(ContentProviderOperation
                            .newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(
                                    ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(
                                    ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Im.DATA,
                                    twitter_id)
                            .withValue(ContactsContract.CommonDataKinds.Im.TYPE,
                                    ContactsContract.CommonDataKinds.Im.TYPE_CUSTOM)
                            .withValue(ContactsContract.CommonDataKinds.Im.LABEL,
                                    "Twitter")
                            .withValue(ContactsContract.CommonDataKinds.Im.PROTOCOL,
                                    ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM)
                            .build());

                    */

                            contentProviderOperation.add(ContentProviderOperation
                            .newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(
                                    ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(
                                    ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Im.DATA,
                                    twitter_id)
                            .withValue(ContactsContract.CommonDataKinds.Im.TYPE,
                                    ContactsContract.CommonDataKinds.Im.TYPE_CUSTOM)
                            .withValue(ContactsContract.CommonDataKinds.Im.LABEL,
                                    "Twitter")
                            .withValue(ContactsContract.CommonDataKinds.Im.PROTOCOL,
                                    ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM)

                            .withValue(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL,
                                    "Twitter")
                            .build());

                    }
                    // Asking the Contact provider to create a new contact
                    try
                    {
                            getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProviderOperation);
                            Toast.makeText(Contact_scan.this,"Succesfully Added",Toast.LENGTH_SHORT).show();
                            mScannerView.resumeCameraPreview(this);
                    }
                    catch (Exception e)
                    {
                            e.printStackTrace();
                            Toast.makeText(Contact_scan.this, "Exception: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
    }

}
