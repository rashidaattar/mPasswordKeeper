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
import com.mobile.mpasswordkeeper.activity.AddEditOtherActivity;
import com.mobile.mpasswordkeeper.database.DaoSession;
import com.mobile.mpasswordkeeper.database.OtherDetails;
import com.mobile.mpasswordkeeper.database.OtherDetailsDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 836137 on 11-07-2017.
 */

public class OtherListAdapter  extends RecyclerView.Adapter<OtherListAdapter.OtherViewHolder> {

    ArrayList<OtherDetails> otherDetailses;
    Context mContext;
    ActionMode mActionMode;
    private OtherDetailsDao otherDetailsDao;

    public OtherListAdapter(ArrayList<OtherDetails> otherDetailses, Context mContext) {
        this.otherDetailses = otherDetailses;
        this.mContext = mContext;
    }

    @Override
    public OtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_item, null);
        return new OtherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OtherViewHolder holder, final int position) {

        final OtherDetails otherDetails = otherDetailses.get(position);
        holder.username.setText(otherDetails.getUserName());
        holder.pwd.setText(otherDetails.getPassword());
        holder.additionalInfo.setText(otherDetails.getAdditionalInfo());
        holder.title.setText(otherDetails.getTitle());
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
                                    Intent intent = new Intent(mContext, AddEditOtherActivity.class);
                                    intent.putExtra(Utility.OTHER_ID,otherDetails.getId());
                                    mContext.startActivity(intent);
                                    actionMode.finish();
                                    return true;
                                case R.id.delete_button:
                                    deleteOtherDetails(otherDetails);
                                    otherDetailses.remove(position);
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
                return true;
            }
        });

    }

    private void deleteOtherDetails(OtherDetails otherDetails) {
        DaoSession daoSession = ((PasswordKeeper) mContext.getApplicationContext()).getDaoSession();
        otherDetailsDao = daoSession.getOtherDetailsDao();
        otherDetailsDao.deleteByKey(otherDetails.getId());
    }

    @Override
    public int getItemCount() {
        return otherDetailses.size();
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.username)
        TextView username;

        @BindView(R.id.pwd)
        TextView pwd;

        @BindView(R.id.additional_info)
        TextView additionalInfo;

        @BindView(R.id.card_view)
        CardView cardView;

        public OtherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
