package com.mobile.mpasswordkeeper.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.adapter.BankListAdapter;
import com.mobile.mpasswordkeeper.database.BankDetails;
import com.mobile.mpasswordkeeper.database.BankDetailsDao;
import com.mobile.mpasswordkeeper.database.CardDetails;
import com.mobile.mpasswordkeeper.database.CardDetailsDao;
import com.mobile.mpasswordkeeper.database.DaoSession;
import com.mobile.mpasswordkeeper.fragment.BankDetailFragment;

import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankListActivity extends AppCompatActivity implements BankDetailFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.banks_list)
    RecyclerView bankListView;

    List<BankDetails> banksList ;
    ArrayList<CardDetails> cardsList;

    BankListAdapter bankListAdapter;
    BankDetailsDao bankDetailsDao;
   // CardDetailsDao cardDetailsDao;
  //  HashMap<BankDetails,ArrayList<CardDetails>> bankDetailsCardDetailsHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Bank Details");
        DaoSession daoSession = ((PasswordKeeper) getApplication()).getDaoSession();
        bankDetailsDao = daoSession.getBankDetailsDao();

       // cardDetailsDao = daoSession.getCardDetailsDao();

    }

    private void getBanksList() {
        QueryBuilder<BankDetails> bankDetailsQueryBuilder = bankDetailsDao.queryBuilder();
      //  bankDetailsQueryBuilder.join(CardDetails.class, CardDetailsDao.Properties.BankId);
        banksList = new ArrayList<>();
        banksList.addAll(bankDetailsQueryBuilder.list());
    }

    @OnClick(R.id.add_bank)
    public void addBank(){
        Intent intent = new Intent(this,AddEditBankActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBanksList();
        bankListAdapter = new BankListAdapter(banksList,this);
        bankListView.setAdapter(bankListAdapter);
        bankListView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        bankListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            finish();
        }
        else{
            getSupportFragmentManager().popBackStack();
        }
    }
}
