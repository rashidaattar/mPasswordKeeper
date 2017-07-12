package com.mobile.mpasswordkeeper.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mobile.mpasswordkeeper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    public void goToBank(View view) {
        Intent intent = new Intent(this,BankListActivity.class);
        startActivity(intent);
    }

    public void goToEmail(View view) {

        Intent intent = new Intent(this,EmailListActivity.class);
        startActivity(intent);
    }

    public void goToOther(View view) {

        Intent intent = new Intent(this,OtherListActivity.class);
        startActivity(intent);
    }

    public void goToImport(View view) {

        performFileSearch();
    }
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("application/vnd.ms-excel"); //MIME for Excel

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("here", "Uri: " + uri.toString());
               // showImage(uri);
            }
        }
    }
}
