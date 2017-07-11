package com.mobile.mpasswordkeeper.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.Utility;
import com.mobile.mpasswordkeeper.activity.AddEditEmailActivity;
import com.mobile.mpasswordkeeper.database.DaoSession;
import com.mobile.mpasswordkeeper.database.EmailDetails;
import com.mobile.mpasswordkeeper.database.EmailDetailsDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 836137 on 11-07-2017.
 */

public class EmailListAdapter extends RecyclerView.Adapter<EmailListAdapter.EmailViewHolder> {


    ArrayList<EmailDetails> emailDetailses = new ArrayList<>();
    Context mContext;
    ActionMode mActionMode;
    private EmailDetailsDao emailDetailsDao;

    public EmailListAdapter(ArrayList<EmailDetails> emailDetailses, Context mContext) {
        this.emailDetailses = emailDetailses;
        this.mContext = mContext;
    }

    @Override
    public EmailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_item, null);
        return new EmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmailViewHolder holder, final int position) {
        final EmailDetails emailDetails = emailDetailses.get(position);
        holder.emailID.setText(emailDetails.getEmailId());
        holder.pwd.setText(emailDetails.getPassword());
        if(emailDetails.getAlternateEmail()!=null && emailDetails.getAlternateEmail().length()>0){
            holder.alternateEmail.setText(emailDetails.getAlternateEmail());
        }
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
                                    Intent intent = new Intent(mContext, AddEditEmailActivity.class);
                                    intent.putExtra(Utility.EMAIL_ID,emailDetails.getId());
                                    mContext.startActivity(intent);
                                    actionMode.finish();
                                    return true;
                                case R.id.delete_button:
                                    deleteEmailDetails(emailDetails);
                                    emailDetailses.remove(position);
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
                    }, ActionMode.TYPE_FLOATING);
                }
                return false;
            }
        });

    }

    private void deleteEmailDetails(EmailDetails emailDetails) {
        DaoSession daoSession = ((PasswordKeeper) mContext.getApplicationContext()).getDaoSession();
        emailDetailsDao = daoSession.getEmailDetailsDao();
        emailDetailsDao.deleteByKey(emailDetails.getId());
    }

    @Override
    public int getItemCount() {
        return emailDetailses.size();
    }

    public class EmailViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.email_id)
        TextView emailID;

        @BindView(R.id.pwd)
        TextView pwd;

        @BindView(R.id.alternate_email_id)
        TextView alternateEmail;

        @BindView(R.id.card_view)
        CardView cardView;

        public EmailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
