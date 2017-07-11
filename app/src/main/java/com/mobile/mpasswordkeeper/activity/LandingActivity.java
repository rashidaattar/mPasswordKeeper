package com.mobile.mpasswordkeeper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
}
