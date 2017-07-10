package com.mobile.mpasswordkeeper.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.Utility;
import com.mobile.mpasswordkeeper.activity.AddEditBankActivity;
import com.mobile.mpasswordkeeper.activity.AddEditEmailActivity;
import com.mobile.mpasswordkeeper.database.BankDetails;
import com.mobile.mpasswordkeeper.database.BankDetailsDao;
import com.mobile.mpasswordkeeper.database.CardDetails;
import com.mobile.mpasswordkeeper.database.CardDetailsDao;
import com.mobile.mpasswordkeeper.database.DaoSession;
import com.mobile.mpasswordkeeper.fragment.BankDetailFragment;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.mode;

/**
 * Created by 836137 on 22-05-2017.
 */

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankViewHolder> {

    ArrayList<BankDetails> mBanksList = new ArrayList<>();
    ArrayList<CardDetails> mCardDetailsList ;
    Context mContext;
    private ActionMode mActionMode;
    private BankDetailsDao bankDetailsDao;
    private  CardDetailsDao cardDetailsDao;

    public BankListAdapter(List<BankDetails> bankDetails, Context context){
        mBanksList.addAll(bankDetails);
        mContext = context;
    }

    @Override
    public BankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_item, null);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BankViewHolder holder, final int position) {

        Log.d("check","came here");
        final BankDetails bankDetails = mBanksList.get(position);
        mCardDetailsList = new ArrayList<>();
        mCardDetailsList.addAll(bankDetails.getCards());

        if(bankDetails.getTitle()!=null){
            holder.bankTitle.setText(bankDetails.getTitle());
        }
        holder.accountNo.setText("AC No : "+bankDetails.getAccountNo());
        holder.bankName.setText(bankDetails.getBankName());

        if(mCardDetailsList.size()>0){
            for(CardDetails cardDetails: mCardDetailsList){
                if(cardDetails.getCardType().equals(CardDetails.CardType.DEBIT)){
                    holder.dcNo.setText("DC : "+cardDetails.getCardNumber());
                    holder.dcNo.setVisibility(View.VISIBLE);
                }
                else if(cardDetails.getCardType().equals(CardDetails.CardType.CREDIT)){
                    holder.ccNo.setText("CC : "+cardDetails.getCardNumber());
                    holder.ccNo.setVisibility(View.VISIBLE);
                }
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mActionMode!=null){
                    mActionMode.finish();
                }
                else{
                    BankDetailFragment bankDetailFragment = BankDetailFragment.newInstance(bankDetails.
                            getId());
                    if(mContext instanceof AppCompatActivity){
                        ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragment_container,bankDetailFragment,"test")
                                .addToBackStack("fragment").commit();
                    }
                }

            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    mActionMode = view.startActionMode(new android.view.ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
                            MenuInflater inflater = actionMode.getMenuInflater();
                            inflater.inflate(R.menu.edit_delete_context_menu, menu);
                        //    actionMode.getMenu().getItem(1).setVisible(false);
                            view.setBackgroundColor(mContext.getColor(R.color.cardview_dark_background));
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.edit_button:
                                    Intent intent = new Intent(mContext, AddEditBankActivity.class);
                                    intent.putExtra(Utility.BANK_ID,bankDetails.getId());
                                    mContext.startActivity(intent);
                                    actionMode.finish();
                                    return true;
                                case R.id.delete_button:
                                   deleteBankDetails(bankDetails);
                                    mBanksList.remove(position);
                                    notifyDataSetChanged();
                                    actionMode.finish();
                                    return true;
                                default:
                                    return false;
                            }
                        }

                        @Override
                        public void onDestroyActionMode(android.view.ActionMode actionMode) {

                            if(mActionMode!=null){
                                mActionMode=null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    view.setBackgroundColor(mContext.getColor(R.color.bgColor));
                                }
                            }

                        }
                    },ActionMode.TYPE_FLOATING);
                }

                return false;
            }
        });
    }

    private void deleteBankDetails(BankDetails bankDetails) {
        DaoSession daoSession = ((PasswordKeeper) mContext.getApplicationContext()).getDaoSession();
        bankDetailsDao = daoSession.getBankDetailsDao();
        bankDetailsDao.deleteByKey(bankDetails.getId());
        if(bankDetails.getCards()!=null && bankDetails.getCards().size()>0){
            for(CardDetails cardDetails : bankDetails.getCards()){
                cardDetailsDao = daoSession.getCardDetailsDao();
                cardDetailsDao.deleteByKey(cardDetails.getId());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mBanksList.size();
    }

   /* @Override
    public long getItemId(int position) {
        return position;
    }*/

    public class BankViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.bank_title)
        TextView bankTitle;

        @BindView(R.id.bank_name)
        TextView bankName;

        @BindView(R.id.account_no)
        TextView accountNo;

        @BindView(R.id.cc_text)
        TextView ccNo;

        @BindView(R.id.dc_text)
        TextView dcNo;

        @BindView(R.id.card_view)
        CardView cardView;


        public BankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
