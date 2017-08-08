package com.mobile.mpasswordkeeper.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.Utility;
import com.mobile.mpasswordkeeper.database.BankDetails;
import com.mobile.mpasswordkeeper.database.BankDetailsDao;
import com.mobile.mpasswordkeeper.database.CardDetails;
import com.mobile.mpasswordkeeper.database.CardDetailsDao;
import com.mobile.mpasswordkeeper.database.EmailDetailsDao;
import com.mobile.mpasswordkeeper.database.OtherDetailsDao;

import org.apache.poi.hpsf.Util;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFromFileActivity extends AppCompatActivity {

    public static final int READ_FOR_BANK = 1;
    public static final int READ_FOR_EMAIL = 2;
    public static final int READ_FOR_OTHER = 3;
    private BankDetailsDao bankDetailsDao;
    private CardDetailsDao cardDetailsDao;
    private EmailDetailsDao emailDetailsDao;
    private OtherDetailsDao otherDetailsDao;

    @BindView(R.id.file_spinner)
    Spinner fileSpinner;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_file);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Import File");
        mContext = this;
        bankDetailsDao = ((PasswordKeeper) getApplication()).getDaoSession().getBankDetailsDao();
        cardDetailsDao = ((PasswordKeeper) getApplication()).getDaoSession().getCardDetailsDao();
        emailDetailsDao = ((PasswordKeeper) getApplication()).getDaoSession().getEmailDetailsDao();
        otherDetailsDao = ((PasswordKeeper) getApplication()).getDaoSession().getOtherDetailsDao();
        fileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                if(i==0){
                //    Toast.makeText(mContext,"Select a valid option!",Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(mContext);
                    }
                    builder.setTitle("Import .xlsx")
                            .setMessage("Please make sure to import file exactly as displayed in tempalate")
                            .setPositiveButton("Import", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    addFromExcel(i);
                                }
                            })
                            .setNeutralButton("Download Tempalate", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addFromExcel(int pos){
        if((ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE))== PackageManager.PERMISSION_GRANTED){
            performFileSearch(pos);
        }
        else{
            switch(pos){
                case READ_FOR_BANK:
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_FOR_BANK);
                    break;
                case READ_FOR_EMAIL:
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_FOR_EMAIL);
                    break;
                case READ_FOR_OTHER:
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_FOR_OTHER);
                    break;
            }


        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            performFileSearch(requestCode);
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && (requestCode == READ_FOR_BANK
                || requestCode == READ_FOR_OTHER || requestCode == READ_FOR_EMAIL)){

            if (data != null) {
                Uri uri = data.getData();
                System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
                System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
                System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
                switch(requestCode){
                    case READ_FOR_BANK:
                        Utility.readFileforBank(uri,bankDetailsDao,cardDetailsDao,this);
                        break;
                    case READ_FOR_EMAIL :
                        Utility.readFileforEmail(uri,emailDetailsDao,this);
                        break;
                    case READ_FOR_OTHER :
                        Utility.readExcelforOther(uri,otherDetailsDao,this);
                        break;
                }

            }
        }
    }

    public void performFileSearch(int id) {


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
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        switch(id){
            case READ_FOR_BANK :
                startActivityForResult(intent, READ_FOR_BANK);
                break;
            case READ_FOR_EMAIL:
                startActivityForResult(intent, READ_FOR_EMAIL);
                break;
            case READ_FOR_OTHER:
                startActivityForResult(intent, READ_FOR_OTHER);
                break;
        }


    }


}
