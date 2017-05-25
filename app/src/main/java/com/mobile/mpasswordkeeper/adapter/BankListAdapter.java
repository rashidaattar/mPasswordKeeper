package com.mobile.mpasswordkeeper.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.database.BankDetails;
import com.mobile.mpasswordkeeper.database.CardDetails;
import com.mobile.mpasswordkeeper.database.CardDetailsDao;
import com.mobile.mpasswordkeeper.fragment.BankDetailFragment;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 836137 on 22-05-2017.
 */

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankViewHolder> {

    ArrayList<BankDetails> mBanksList = new ArrayList<>();
    ArrayList<CardDetails> mCardDetailsList ;
    Context mContext;

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
    public void onBindViewHolder(final BankViewHolder holder, int position) {

        Log.d("check","came here");
        mCardDetailsList = new ArrayList<>();
        mCardDetailsList.addAll(mBanksList.get(position).getCards());

        if(mBanksList.get(position).getTitle()!=null){
            holder.bankTitle.setText(mBanksList.get(position).getTitle());
        }
        holder.accountNo.setText("AC No : "+mBanksList.get(position).getAccountNo());
        holder.bankName.setText(mBanksList.get(position).getBankName());

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
                BankDetailFragment bankDetailFragment = BankDetailFragment.newInstance("test","test");
                if(mContext instanceof AppCompatActivity){
                    ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container,bankDetailFragment,"test").addToBackStack("fragment").commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBanksList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

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
