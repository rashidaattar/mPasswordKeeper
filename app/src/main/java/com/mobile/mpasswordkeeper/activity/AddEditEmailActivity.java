package com.mobile.mpasswordkeeper.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.Utility;
import com.mobile.mpasswordkeeper.database.DaoSession;
import com.mobile.mpasswordkeeper.database.EmailDetails;
import com.mobile.mpasswordkeeper.database.EmailDetailsDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditEmailActivity extends AppCompatActivity {

    @BindView(R.id.include)
    Toolbar mToolbar;

    @BindView(R.id.alternate_email_id)
    EditText alternate_email;

    @BindView(R.id.emailid_layout)
    TextInputLayout email_id_layout;

    @BindView(R.id.email_pwd_layout)
    TextInputLayout email_pwd_layout;

    @BindView(R.id.email_id)
    EditText email_id;

    @BindView(R.id.pwd)
    EditText email_pwd;

    @BindView(R.id.alternate_emailid_layout)
    TextInputLayout alternate_email_layout;

    private EmailDetailsDao emailDetailsDao;
    long emailID =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_email);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Email");
        DaoSession daoSession = ((PasswordKeeper) getApplication()).getDaoSession();
        emailDetailsDao = daoSession.getEmailDetailsDao();
        if(getIntent().hasExtra(Utility.EMAIL_ID)){
            populateViews();
        }
    }

    private void populateViews() {
        QueryBuilder<EmailDetails> emailDetailsQueryBuilder = emailDetailsDao.queryBuilder()
                .where(EmailDetailsDao.Properties.Id.eq(getIntent().getLongExtra(Utility.EMAIL_ID,-1)));
        EmailDetails emailDetails = emailDetailsQueryBuilder.unique();
        emailID = emailDetails.getId();
        populateData(emailDetails);
    }

    private void populateData(EmailDetails emailDetails) {

        email_id.setText(emailDetails.getEmailId());
        email_pwd.setText(emailDetails.getPassword());
        if(emailDetails.getAlternateEmail()!=null && emailDetails.getAlternateEmail().length()>0){
            alternate_email.setText(emailDetails.getAlternateEmail());
        }
    }

    @OnClick(R.id.save_email)
    public void saveEmail(){
        boolean flag = true;
        if(!validateEmail(email_id.getText().toString())){
            email_id_layout.setErrorEnabled(true);
            email_id_layout.setError("Enter valid email ID");
            flag=false;
        }
        if( email_pwd.getText().toString().length()<=0){
            email_pwd_layout.setErrorEnabled(true);
            email_pwd_layout.setError("Enter Password");
            flag = false;
        }
        if(alternate_email.getText().toString().length()>0 && !validateEmail(alternate_email.getText().toString())){
            alternate_email_layout.setErrorEnabled(true);
            alternate_email_layout.setError("Enter valid email ID");
            flag = false;
        }
        if(flag){
            saveData();
        }

    }

    private void saveData() {

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setEmailId(email_id.getText().toString().trim());
        emailDetails.setPassword(email_pwd.getText().toString().trim());
        if(alternate_email.getText().toString()!=null && alternate_email.getText().toString().length()>0){
            emailDetails.setAlternateEmail(alternate_email.getText().toString().trim());
        }
        if(emailID>-1){
            emailDetails.setId(getIntent().getLongExtra(Utility.EMAIL_ID,-1));
            emailDetailsDao.update(emailDetails);
        }
        else{
            emailID= emailDetailsDao.insert(emailDetails);
        }

        Toast.makeText(this,"email details saved :"+ emailID,Toast.LENGTH_SHORT).show();
        finish();
    }


    private boolean validateEmail(String email){
        Pattern valid_email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        return valid_email.matcher(email).find();
    }
}
