package com.mobile.mpasswordkeeper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.adapter.OtherListAdapter;
import com.mobile.mpasswordkeeper.database.EmailDetails;
import com.mobile.mpasswordkeeper.database.OtherDetails;
import com.mobile.mpasswordkeeper.database.OtherDetailsDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.others_list)
    RecyclerView othersList;

    OtherDetailsDao otherDetailsDao;

    ArrayList<OtherDetails> otherDetailsArrayList;

    OtherListAdapter otherListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_list);
        ButterKnife.bind(this);
        otherDetailsDao = ((PasswordKeeper)getApplication()).getDaoSession().getOtherDetailsDao();
    }


    @OnClick(R.id.add_others)
    public void addOthers(){
        Intent intent = new Intent(this,AddEditOtherActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOthersList();
        otherListAdapter = new OtherListAdapter(otherDetailsArrayList,this);
        othersList.setAdapter(otherListAdapter);
        othersList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        otherListAdapter.notifyDataSetChanged();
    }

    private void getOthersList() {

        QueryBuilder<OtherDetails> otherDetailsQueryBuilder = otherDetailsDao.queryBuilder();
        //  otherDetailsQueryBuilder.join(CardDetails.class, CardDetailsDao.Properties.BankId);
        otherDetailsArrayList = new ArrayList<>();
        otherDetailsArrayList.addAll(otherDetailsQueryBuilder.list());
    }
}
