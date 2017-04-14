package com.mobile.mpasswordkeeper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.mpasswordkeeper.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditBankActivity extends AppCompatActivity {

    @BindView(R.id.include)
    Toolbar mToolbar;


    @BindView(R.id.debit_card_details)
    LinearLayout debit_layout;

    @BindView(R.id.credit_card_details)
    LinearLayout credit_layout;

    @BindView(R.id.stub1)
    ViewStubCompat viewStubCompat;

    View onlineTransaction;

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
        viewStubCompat.setLayoutResource(R.layout.online_transaction);
        onlineTransaction = viewStubCompat.inflate();
        viewStubCompat.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.debit_card_button)
    public void showDebitCard(){
        debit_layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.credit_card_button)
    public void showCreditCard(){
        credit_layout.setVisibility(View.VISIBLE);
    }
}
