package com.mobile.mpasswordkeeper.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.database.BankDetails;
import com.mobile.mpasswordkeeper.database.BankDetailsDao;
import com.mobile.mpasswordkeeper.database.CardDetails;
import com.mobile.mpasswordkeeper.database.CardDetailsDao;
import com.mobile.mpasswordkeeper.database.CardTypeConverter;
import com.mobile.mpasswordkeeper.database.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditBankActivity extends AppCompatActivity {

    @BindView(R.id.include)
    Toolbar mToolbar;

    @BindView(R.id.stub1)
    ViewStubCompat viewStubCompat1;

    @BindView(R.id.stub2)
    ViewStubCompat viewStubCompat2;

    @BindView(R.id.stub3)
    ViewStubCompat viewStubCompat3;

    @BindView(R.id.title)
    EditText title_text;

    @BindView(R.id.bankName)
    EditText bank_name;

    @BindView(R.id.branchName)
    EditText branch_name;

    @BindView(R.id.bankAcNo)
    EditText account_number;

    @BindView(R.id.ifscNo)
    EditText ifsc_no;

    @BindView(R.id.name_layout)
    TextInputLayout bank_name_layout;

    @BindView(R.id.branch_layout)
    TextInputLayout branch_layout;

    @BindView(R.id.ac_layout)
    TextInputLayout account_layout;

    LinearLayout onlineTransaction;
    LinearLayout debitCard;
    LinearLayout creditcard;

    EditText cc_number_text;
    EditText cc_expiry_date;
    EditText cc_cvv;
    EditText cc_pin;
    EditText cc_name;

    EditText dc_number_text;
    EditText dc_expiry_date;
    EditText dc_cvv;
    EditText dc_pin;
    EditText dc_name;

    EditText online_username;
    EditText online_pwd;

    TextInputLayout cc_number_layout;
    TextInputLayout cc_expiry_layout;
    TextInputLayout cc_cvv_layout;
    TextInputLayout cc_pin_layout;
    TextInputLayout cc_name_layout;

    TextInputLayout dc_number_layout;
    TextInputLayout dc_expiry_layout;
    TextInputLayout dc_cvv_layout;
    TextInputLayout dc_pin_layout;
    TextInputLayout dc_name_layout;

    TextInputLayout online_username_layout;
    TextInputLayout online_pwd_layout;
    BankDetailsDao bankDetailsDao;
    CardDetailsDao cardDetailsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_bank);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Bank");
        DaoSession daoSession = ((PasswordKeeper) getApplication()).getDaoSession();
        bankDetailsDao = daoSession.getBankDetailsDao();
        cardDetailsDao = daoSession.getCardDetailsDao();
        QueryBuilder.LOG_SQL =true;
        QueryBuilder.LOG_VALUES = true;
    }

    public View inflateViewtoStub(ViewStubCompat viewStubCompat, int layout){
        viewStubCompat.setLayoutResource(layout);
        View view = viewStubCompat.inflate();
        viewStubCompat.setVisibility(View.VISIBLE);
        return view;
    }

    @OnClick({R.id.debit_card_button,R.id.credit_card_button,R.id.net_banking_button})
    public void addStubs(View view){
        if(viewStubCompat1.getLayoutResource()==0){
            switch (view.getId()){
                case R.id.debit_card_button:
                    debitCard=(LinearLayout) inflateViewtoStub(viewStubCompat1, R.layout.add_dc_details);
                    break;
                case R.id.credit_card_button:
                    creditcard=(LinearLayout) inflateViewtoStub(viewStubCompat1, R.layout.add_cc_details);
                    break;
                case R.id.net_banking_button:
                    onlineTransaction=(LinearLayout) inflateViewtoStub(viewStubCompat1, R.layout.add_online_transaction);
                    break;
            }
        }
        else if(viewStubCompat2.getLayoutResource()==0){
            switch (view.getId()){
                case R.id.debit_card_button:
                    debitCard=(LinearLayout) inflateViewtoStub(viewStubCompat2, R.layout.add_dc_details);
                    break;
                case R.id.credit_card_button:
                    creditcard=(LinearLayout) inflateViewtoStub(viewStubCompat2, R.layout.add_cc_details);
                    break;
                case R.id.net_banking_button:
                    onlineTransaction=(LinearLayout) inflateViewtoStub(viewStubCompat2, R.layout.add_online_transaction);
                    break;
            }
        }
        else if(viewStubCompat3.getLayoutResource()==0){
            switch (view.getId()){
                case R.id.debit_card_button:
                    debitCard=(LinearLayout) inflateViewtoStub(viewStubCompat3, R.layout.add_dc_details);
                    break;
                case R.id.credit_card_button:
                    creditcard=(LinearLayout) inflateViewtoStub(viewStubCompat3, R.layout.add_cc_details);
                    break;
                case R.id.net_banking_button:
                    onlineTransaction=(LinearLayout)inflateViewtoStub(viewStubCompat3, R.layout.add_online_transaction);
                    break;
            }
        }
    }

    @OnClick(R.id.save_bank_details)
    public void saveDetails(View view){

        initializeViews();
        if(validateData()){
            insertUpdateData();
        }

    }

    private void insertUpdateData() {
        BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountNo(account_number.getText().toString());
        bankDetails.setBankBranch(branch_name.getText().toString());
        bankDetails.setBankName(bank_name.getText().toString());
        if(ifsc_no.getText().toString()!=null && ifsc_no.getText().toString().length()>0){
            bankDetails.setIfsc(ifsc_no.getText().toString());
        }
        if(onlineTransaction!=null){
            bankDetails.setOnlinePassword(online_pwd.getText().toString());
            bankDetails.setOnlineUsername(online_username.getText().toString());
        }
        long bankId = bankDetailsDao.insert(bankDetails);
        Log.d("addbank","added bank details :"+bankId);
        Toast.makeText(this,"added bank  details :"+bankId,Toast.LENGTH_SHORT).show();

        if(bankId>0){
            if(creditcard!=null){
                CardDetails cardDetails = new CardDetails();
                cardDetails.setCardType(CardDetails.CardType.CREDIT);
                cardDetails.setBankId(bankId);
                cardDetails.setCardName(cc_name.getText().toString());
                cardDetails.setCardNumber(Long.valueOf(cc_number_text.getText().toString()));
                cardDetails.setCardPin(Integer.valueOf(cc_pin.getText().toString()));
                cardDetails.setCVV(Integer.valueOf(cc_cvv.getText().toString()));
                try {
                    cardDetails.setExpiryDate(new SimpleDateFormat("MM/yy").parse(cc_expiry_date.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long ccId = cardDetailsDao.insert(cardDetails);
                Log.d("addbank","added credit  details :"+ccId);
                Toast.makeText(this,"added credit  details :"+ccId,Toast.LENGTH_SHORT).show();
            }
            if(debitCard!=null){
                CardDetails cardDetails = new CardDetails();
                cardDetails.setCardType(CardDetails.CardType.DEBIT);
                cardDetails.setBankId(bankId);
                cardDetails.setCardName(dc_name.getText().toString());
                cardDetails.setCardNumber(Long.valueOf(dc_number_text.getText().toString()));
                cardDetails.setCardPin(Integer.valueOf(dc_pin.getText().toString()));
                cardDetails.setCVV(Integer.valueOf(dc_cvv.getText().toString()));
                try {
                    cardDetails.setExpiryDate(new SimpleDateFormat("MM/yy").parse(dc_expiry_date.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long dcId = cardDetailsDao.insert(cardDetails);
                Log.d("addbank","added debit details :"+dcId);
                Toast.makeText(this,"added debit  details :"+dcId,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateData() {
        boolean isValid = true;
        if(bank_name==null || bank_name.getText().toString().length()<=0){
            isValid = false;
            bank_name_layout.setErrorEnabled(true);
            bank_name_layout.setError("Bank name cannot be empty");
        }
        if(branch_name==null || branch_name.getText().toString().length()<=0){
            isValid = false;
            branch_layout.setErrorEnabled(true);
            branch_layout.setError("Branch name cannot be empty");
        }
        if(account_number==null || account_number.getText().toString().length()<=0){
            isValid = false;
            account_layout.setErrorEnabled(true);
            account_layout.setError("Bank account cannot be empty");
        }
        if(onlineTransaction!=null){
            if(online_pwd.getText().toString()==null || online_pwd.getText().toString().length()<=0){
                isValid = false;
                online_pwd_layout.setErrorEnabled(true);
                online_pwd_layout.setError("Password cannot be empty");
            }
            if(online_username.getText().toString()==null || online_username.getText().toString().length()<=0){
                isValid = false;
                online_username_layout.setErrorEnabled(true);
                online_username_layout.setError("Username cannot be empty");
            }
        }
        if(creditcard!=null){
            if(cc_pin.getText().toString()==null || cc_pin.getText().toString().length()<=0){
                isValid = false;
                cc_pin_layout.setErrorEnabled(true);
                cc_pin_layout.setError("Pin cannot be empty;");
            }
            if(cc_expiry_date.getText().toString()==null || cc_expiry_date.getText().toString().length()<=0){
                isValid = false;
                cc_expiry_layout.setErrorEnabled(true);
                cc_expiry_layout.setError("Expiry date cannot be empty;");
            }
            if(cc_cvv.getText().toString()==null || cc_cvv.getText().toString().length()<=0){
                isValid = false;
                cc_cvv_layout.setErrorEnabled(true);
                cc_cvv_layout.setError("CVV cannot be empty;");
            }
            if(cc_number_text.getText().toString()==null || cc_number_text.getText().toString().length()<=0){
                isValid = false;
                cc_number_layout.setErrorEnabled(true);
                cc_number_layout.setError("CC number cannot be empty;");
            }
            if(cc_name.getText().toString()==null || cc_name.getText().toString().length()<=0){
                isValid = false;
                cc_name_layout.setErrorEnabled(true);
                cc_name_layout.setError("Pin cannot be empty;");
            }
        }
        if(debitCard!=null){
            if(dc_pin.getText().toString()==null || dc_pin.getText().toString().length()<=0){
                isValid = false;
                dc_pin_layout.setErrorEnabled(true);
                dc_pin_layout.setError("Pin cannot be empty;");
            }
            if(dc_expiry_date.getText().toString()==null || dc_expiry_date.getText().toString().length()<=0){
                isValid = false;
                dc_expiry_layout.setErrorEnabled(true);
                dc_expiry_layout.setError("Expiry date cannot be empty;");
            }
            if(dc_cvv.getText().toString()==null || dc_cvv.getText().toString().length()<=0){
                isValid = false;
                dc_cvv_layout.setErrorEnabled(true);
                dc_cvv_layout.setError("CVV cannot be empty;");
            }
            if(dc_number_text.getText().toString()==null || dc_number_text.getText().toString().length()<=0){
                isValid = false;
                dc_number_layout.setErrorEnabled(true);
                dc_number_layout.setError("DC number cannot be empty;");
            }
            if(dc_name.getText().toString()==null || dc_name.getText().toString().length()<=0){
                isValid = false;
                dc_name_layout.setErrorEnabled(true);
                dc_name_layout.setError("Pin cannot be empty;");
            }
        }

        return isValid;
    }

    private void initializeViews(){
        if(onlineTransaction!=null){
            online_pwd_layout = (TextInputLayout) onlineTransaction.findViewById(R.id.password_layout);
            online_pwd = (EditText) onlineTransaction.findViewById(R.id.password);
            online_username_layout = (TextInputLayout) onlineTransaction.findViewById(R.id.user_name_layout);
            online_username = (EditText) onlineTransaction.findViewById(R.id.userName);
        }
        if(creditcard!=null){
            cc_number_layout = (TextInputLayout) creditcard.findViewById(R.id.cc_number_layout);
            cc_number_text = (EditText) creditcard.findViewById(R.id.cc_number);
            cc_cvv_layout = (TextInputLayout) creditcard.findViewById(R.id.cvv_layout);
            cc_cvv = (EditText) creditcard.findViewById(R.id.cvv_text);
            cc_expiry_layout = (TextInputLayout) creditcard.findViewById(R.id.cc_expiry_layout);
            cc_expiry_date = (EditText) creditcard.findViewById(R.id.cc_expiry_date);
            cc_pin_layout = (TextInputLayout) creditcard.findViewById(R.id.cc_pin_layout);
            cc_pin = (EditText) creditcard.findViewById(R.id.cc_pin);
            cc_name_layout = (TextInputLayout) creditcard.findViewById(R.id.cc_name_layout);
            cc_name = (EditText) creditcard.findViewById(R.id.cc_name);
        }
        if(debitCard!=null){
            dc_number_layout = (TextInputLayout) debitCard.findViewById(R.id.dc_number_layout);
            dc_number_text = (EditText) debitCard.findViewById(R.id.dc_number);
            dc_cvv_layout = (TextInputLayout) debitCard.findViewById(R.id.cvv_layout);
            dc_cvv = (EditText) debitCard.findViewById(R.id.cvv_text);
            dc_expiry_layout = (TextInputLayout) debitCard.findViewById(R.id.dc_expiry_layout);
            dc_expiry_date = (EditText) debitCard.findViewById(R.id.dc_expiry_date);
            dc_pin_layout = (TextInputLayout) debitCard.findViewById(R.id.dc_pin_layout);
            dc_pin = (EditText) debitCard.findViewById(R.id.dc_pin);
            dc_name = (EditText) debitCard.findViewById(R.id.dc_name);
            dc_name_layout = (TextInputLayout) debitCard.findViewById(R.id.dc_name_layout);
        }

    }
}
