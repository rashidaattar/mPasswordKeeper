package com.mobile.mpasswordkeeper.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.Utility;
import com.mobile.mpasswordkeeper.database.OtherDetails;
import com.mobile.mpasswordkeeper.database.OtherDetailsDao;

import org.greenrobot.greendao.query.QueryBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditOtherActivity extends AppCompatActivity {

    @BindView(R.id.title_text)
    EditText titleText;

    @BindView(R.id.username_text)
    EditText usernameText;

    @BindView(R.id.pwd)
    EditText pwdText;

    @BindView(R.id.additional_info_text)
    EditText additionalInfoText;

    @BindView(R.id.title_layout)
    TextInputLayout titleLayout;

    @BindView(R.id.user_name_layout)
    TextInputLayout usernameLayout;

    @BindView(R.id.pwd_layout)
    TextInputLayout pwdLayout;


    OtherDetailsDao otherDetailsDao;

    long otherId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_other);
        ButterKnife.bind(this);
        otherDetailsDao = ((PasswordKeeper) getApplication()).getDaoSession().getOtherDetailsDao();
        if(getIntent().hasExtra(Utility.OTHER_ID)){
            populateData();
        }
    }

    private void populateData() {

        QueryBuilder<OtherDetails> otherDetailsQueryBuilder = otherDetailsDao.queryBuilder()
                .where(OtherDetailsDao.Properties.Id.eq(getIntent().getLongExtra(Utility.OTHER_ID,-1)));
        OtherDetails otherDetails = otherDetailsQueryBuilder.unique();
        otherId = otherDetails.getId();
        populateViews(otherDetails);
    }

    private void populateViews(OtherDetails otherDetails) {

        titleText.setText(otherDetails.getTitle());
        pwdText.setText(otherDetails.getPassword());
        usernameText.setText(otherDetails.getUserName());
        additionalInfoText.setText(otherDetails.getAdditionalInfo());
        otherDetails.setId(getIntent().getLongExtra(Utility.OTHER_ID,-1));
    }

    @OnClick(R.id.save_other)
    public void saveOther(View view){
        if(validateData()){
            OtherDetails otherDetails = new OtherDetails();
            otherDetails.setUserName(usernameText.getText().toString().trim());
            otherDetails.setPassword(pwdText.getText().toString().trim());
            if(titleText.getText().toString().trim().length()>0){
                otherDetails.setTitle(titleText.getText().toString().trim());
            }
            if(additionalInfoText.getText().toString().trim().length()>0){
                otherDetails.setAdditionalInfo(additionalInfoText.getText().toString().trim());
            }

            if(otherId!=-1){
                otherDetailsDao.update(otherDetails);
            }
            else{
                otherId = otherDetailsDao.insert(otherDetails);
            }
            Toast.makeText(this,"email details saved :"+ otherId,Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    private boolean validateData() {
        boolean flag = true;

        if(usernameText.getText().toString().length()<=0){
            usernameLayout.setError("Please Enter Username");
            usernameLayout.setErrorEnabled(true);
            flag = false;
        }

        if(pwdText.getText().toString().length()<=0){
            pwdLayout.setError("Please Enter Password");
            pwdLayout.setErrorEnabled(true);
            flag = false;
        }

        return flag;
    }
}
