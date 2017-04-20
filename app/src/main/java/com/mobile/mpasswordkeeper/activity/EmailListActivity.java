package com.mobile.mpasswordkeeper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mobile.mpasswordkeeper.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Email Details");
    }

    @OnClick(R.id.add_email)
    public void addEmail(){
        Intent intent = new Intent(this,AddEditEmailActivity.class);
        startActivity(intent);
    }
}
