package tech.alvarez.numbers.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import tech.alvarez.numbers.R;
import tech.alvarez.numbers.utils.Constants;
import tech.alvarez.numbers.utils.Util;

public class CreditsActivity extends AppCompatActivity {

    private TextView versionTextView;

    private String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        versionTextView = (TextView) findViewById(R.id.versionTextView);

        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            versionName = info.versionName;

            versionTextView.setText(versionName);

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(Constants.TAG, e.getMessage());
        }
    }

    public void goCreditsChannel(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(Util.getURLChannel(Constants.YOUTUBE_CREDITS_CHANNEL_ID)));
        startActivity(intent);
    }

    public void sendMessage(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.EMAIL_CREDITS});
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " " + versionName);
        i.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(i);
    }
}
