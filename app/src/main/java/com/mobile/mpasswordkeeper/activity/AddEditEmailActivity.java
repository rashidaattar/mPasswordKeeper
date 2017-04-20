package com.mobile.mpasswordkeeper.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.mobile.mpasswordkeeper.R;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditEmailActivity extends AppCompatActivity {

    @BindView(R.id.include)
    Toolbar mToolbar;

    @BindView(R.id.title)
    EditText title_edit;

    @BindView(R.id.emailid_layout)
    TextInputLayout email_id_layout;

    @BindView(R.id.email_pwd_layout)
    TextInputLayout email_pwd_layout;

    @BindView(R.id.email_id)
    EditText email_id;

    @BindView(R.id.pwd)
    EditText email_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_email);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Bank");
    }

    @OnClick(R.id.save_email)
    public void saveEmail(){
        boolean flag = true;
        if(!validateEmail(email_id.getText().toString())){
            email_id_layout.setErrorEnabled(true);
            email_id_layout.setError("Enter valid email ID");
            flag=false;
        }
        if(email_pwd.getText().toString()==null || email_pwd.getText().toString().length()<=0){
            email_pwd_layout.setErrorEnabled(true);
            email_pwd_layout.setError("Enter Password");
            flag = false;
        }

    }




    private boolean validateEmail(String email){
        Pattern valid_email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        return valid_email.matcher(email).find();
    }
}
