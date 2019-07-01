package com.codester.fitness.workout.Activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codester.fitness.workout.R;


public class ActivityCompose extends AppCompatActivity {


    EditText to_mail, from_mail;
    TextView version_id;
    String versionName;
    LinearLayout from_gmail, to_gmail;
    final static int requestcode = 4;
    String get_to_mail, get_from_mail;
    Button mail_send;

    private static String getEmailId(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            return "length is zero";
        }
        return account.name;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case requestcode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    String emailAddr = getEmailId(getApplicationContext());
                    ShowMessage(emailAddr);

                } else {
                    ShowMessage("Permission Denied");
                }
        }
    }

    public void ShowMessage(String email) {
        AlertDialog alertDialog = new AlertDialog.Builder(ActivityCompose.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(email);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        init();
        Context context = getApplicationContext();

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.GET_ACCOUNTS}, requestcode);
        } else {
            String possibleEmail = getEmailId(getApplicationContext());
            ShowMessage(possibleEmail);
            from_mail.setText(possibleEmail);

        }


        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version_id.setText(versionName);


        mail_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_from_mail = from_mail.getText().toString();
                get_to_mail = to_mail.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{get_to_mail});
                email.putExtra(Intent.EXTRA_SUBJECT, get_from_mail);
                email.putExtra(Intent.EXTRA_TEXT, getString(R.string.SHARE_APP_LINK)
                        + ActivityCompose.this.getPackageName());
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.compose));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        to_mail = (EditText) findViewById(R.id.To_mail);
        from_mail = (EditText) findViewById(R.id.From_mail);
        version_id = (TextView) findViewById(R.id.version_id);
        from_gmail = (LinearLayout) findViewById(R.id.from_gmail);
        to_gmail = (LinearLayout) findViewById(R.id.to_gmail);
        mail_send = (Button) findViewById(R.id.send);

    }


}
