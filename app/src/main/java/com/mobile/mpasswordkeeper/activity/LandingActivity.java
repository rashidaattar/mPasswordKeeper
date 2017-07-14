package com.mobile.mpasswordkeeper.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mobile.mpasswordkeeper.R;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Permission;
import java.util.Iterator;

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


        Intent intent = new Intent(this,AddFromFileActivity.class);
        startActivity(intent);

    }

}

