package com.mobile.mpasswordkeeper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;

import com.mobile.mpasswordkeeper.R;

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

    View onlineTransaction;
    View debitCard;
    View creditcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_bank);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Bank");
    }

    @OnClick(R.id.net_banking_button)
    public void showNetBanking(View view){
        //netBanking_layout.setVisibility(View.VISIBLE);
        if(viewStubCompat1.getLayoutResource()==0){
            onlineTransaction=inflateViewtoStub(viewStubCompat1, R.layout.add_online_transaction);
        }
        else if(viewStubCompat2.getLayoutResource()==0){
            onlineTransaction=inflateViewtoStub(viewStubCompat2, R.layout.add_online_transaction);
        }
        else if(viewStubCompat3.getLayoutResource()==0){
            onlineTransaction=inflateViewtoStub(viewStubCompat3, R.layout.add_online_transaction);
        }

    }

    @OnClick(R.id.debit_card_button)
    public void showDebitCard(){

        if(viewStubCompat1.getLayoutResource()==0){
            debitCard=inflateViewtoStub(viewStubCompat1, R.layout.add_dc_details);
        }
        else if(viewStubCompat2.getLayoutResource()==0){
            debitCard=inflateViewtoStub(viewStubCompat2, R.layout.add_dc_details);
        }
        else if(viewStubCompat3.getLayoutResource()==0){
            debitCard=inflateViewtoStub(viewStubCompat3, R.layout.add_dc_details);
        }
    }

    @OnClick(R.id.credit_card_button)
    public void showCreditCard(){
        if(viewStubCompat1.getLayoutResource()==0){
            creditcard=inflateViewtoStub(viewStubCompat1, R.layout.add_cc_details);
        }
        else if(viewStubCompat2.getLayoutResource()==0){
            creditcard=inflateViewtoStub(viewStubCompat2, R.layout.add_cc_details);
        }
        else if(viewStubCompat3.getLayoutResource()==0){
            creditcard=inflateViewtoStub(viewStubCompat3, R.layout.add_cc_details);
        }
    }

    public View inflateViewtoStub(ViewStubCompat viewStubCompat, int layout){
        viewStubCompat.setLayoutResource(layout);
        View view = viewStubCompat.inflate();
        viewStubCompat.setVisibility(View.VISIBLE);
        return view;
    }
}
