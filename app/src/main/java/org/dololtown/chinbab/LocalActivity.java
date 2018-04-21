package org.dololtown.chinbab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class LocalActivity extends AppCompatActivity {
    UserSessionManage ums;
    private static final int RC_USAGE = 1111;
    private static final String ISFIRST = "isFirst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        GoogleSignInAccount account = getIntent().getParcelableExtra("signInAccount");
        Button signOutButton = findViewById(R.id.sign_out_button);
        ums = new UserSessionManage(getApplicationContext());

        TextView textview = findViewById(R.id.email);
        textview.setText(account.getEmail());

        viewUsageForBeginningUser();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ums.mGoogleSignInClient.signOut();
                finish();
            }
        });
    }

    void viewUsageForBeginningUser() {
        SharedPreferences pref = getSharedPreferences(ISFIRST, Activity.MODE_PRIVATE);
        boolean first = pref.getBoolean(ISFIRST, true);
        if (first) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(ISFIRST, false);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), UsageActivity.class);
            startActivityForResult(intent, RC_USAGE);
        }
    }
}