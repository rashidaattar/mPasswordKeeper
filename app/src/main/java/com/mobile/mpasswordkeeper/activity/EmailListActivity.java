package com.mobile.mpasswordkeeper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.adapter.EmailListAdapter;
import com.mobile.mpasswordkeeper.database.BankDetails;
import com.mobile.mpasswordkeeper.database.DaoSession;
import com.mobile.mpasswordkeeper.database.EmailDetails;
import com.mobile.mpasswordkeeper.database.EmailDetailsDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.emails_list)
    RecyclerView emailList;

    EmailListAdapter emailListAdapter;
    ArrayList<EmailDetails> emailDetailsList;
    EmailDetailsDao emailDetailsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Email Details");
        DaoSession daoSession = ((PasswordKeeper) getApplication()).getDaoSession();
        emailDetailsDao = daoSession.getEmailDetailsDao();
    }

    @OnClick(R.id.add_email)
    public void addEmail(){
        Intent intent = new Intent(this,AddEditEmailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBanksList();
        emailListAdapter = new EmailListAdapter(emailDetailsList,this);
        emailList.setAdapter(emailListAdapter);
        emailList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

    }
    private void getBanksList() {
        QueryBuilder<EmailDetails> bankDetailsQueryBuilder = emailDetailsDao.queryBuilder();
        //  bankDetailsQueryBuilder.join(CardDetails.class, CardDetailsDao.Properties.BankId);
        emailDetailsList = new ArrayList<>();
        emailDetailsList.addAll(bankDetailsQueryBuilder.list());
    }
    
}
