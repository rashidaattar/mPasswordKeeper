package com.mobile.mpasswordkeeper.fragment;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.mpasswordkeeper.PasswordKeeper;
import com.mobile.mpasswordkeeper.R;
import com.mobile.mpasswordkeeper.Utility;
import com.mobile.mpasswordkeeper.database.BankDetails;
import com.mobile.mpasswordkeeper.database.BankDetailsDao;
import com.mobile.mpasswordkeeper.database.CardDetails;
import com.mobile.mpasswordkeeper.database.CardTypeConverter;
import com.mobile.mpasswordkeeper.database.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BankDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BankDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private Long bankId;

    private OnFragmentInteractionListener mListener;

    private BankDetailsDao bankDetailsDao;


    @BindView(R.id.toolbar_bank)
    Toolbar bankTitle;

    @BindView(R.id.account_no)
    TextView accountNo;

    @BindView(R.id.bank_name)
    TextView bankName;

    @BindView(R.id.brach_name)
    TextView branchName;

    @BindView(R.id.ifsc_name)
    TextView ifscName;

    @BindView(R.id.stub1)
    ViewStubCompat viewStubCompat1;

    @BindView(R.id.stub2)
    ViewStubCompat viewStubCompat2;

    @BindView(R.id.stub3)
    ViewStubCompat viewStubCompat3;

    LinearLayout onlineLayout;
    LinearLayout ccLayout;
    LinearLayout dcLayout;

    private Unbinder unbinder;

    private BankDetails bankDetails;

    public BankDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BankDetailFragment.
     */

    public static BankDetailFragment newInstance(long param1) {
        BankDetailFragment fragment = new BankDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bankId = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View view = inflater.inflate(R.layout.fragment_bank_detail, container, false);
        unbinder = ButterKnife.bind(this,view);
        DaoSession daoSession = ((PasswordKeeper) getActivity().getApplication()).getDaoSession();
        bankDetailsDao = daoSession.getBankDetailsDao();
        QueryBuilder<BankDetails> bankDetailsQueryBuilder = bankDetailsDao.queryBuilder()
                .where(BankDetailsDao.Properties.Id.eq(bankId));
        bankDetails = bankDetailsQueryBuilder.unique();
        if(bankDetails.getTitle()!=null && bankDetails.getTitle().length()>0){
            bankTitle.setTitle(bankDetails.getTitle());
        }
        else{
            bankTitle.setTitle("NO TITLE");
        }
        accountNo.setText(bankDetails.getAccountNo());
        bankName.setText(bankDetails.getBankName());
        ifscName.setText("IFSC : "+bankDetails.getIfsc());
        branchName.setText(bankDetails.getBankBranch());
        if(bankDetails.getOnlineUsername()!=null && bankDetails.getOnlineUsername().length()>0){

            onlineLayout = (LinearLayout) Utility.inflateViewtoStub(viewStubCompat1,R.layout.online_detail);
            showOnlineDetails(bankDetails.getOnlineUsername(),bankDetails.getOnlinePassword());
        }
        if(bankDetails.getCards()!=null && bankDetails.getCards().size()>0) {
            for (CardDetails cardDetails : bankDetails.getCards()) {
                if (viewStubCompat1.getLayoutResource() == 0) {
                    switch (cardDetails.getCardType()) {
                        case DEBIT:
                            dcLayout = (LinearLayout) Utility.inflateViewtoStub(viewStubCompat1, R.layout.cc_dc_detail);
                            break;
                        case CREDIT:
                            ccLayout = (LinearLayout) Utility.inflateViewtoStub(viewStubCompat1, R.layout.cc_dc_detail);
                            break;
                    }
                } else if (viewStubCompat2.getLayoutResource() == 0) {
                    switch (cardDetails.getCardType()) {
                        case DEBIT:
                            dcLayout = (LinearLayout) Utility.inflateViewtoStub(viewStubCompat2, R.layout.cc_dc_detail);
                            break;
                        case CREDIT:
                            ccLayout = (LinearLayout) Utility.inflateViewtoStub(viewStubCompat2, R.layout.cc_dc_detail);
                            break;
                    }
                } else if (viewStubCompat3.getLayoutResource() == 0) {
                    switch (cardDetails.getCardType()) {
                        case DEBIT:
                            dcLayout = (LinearLayout) Utility.inflateViewtoStub(viewStubCompat3, R.layout.cc_dc_detail);
                            break;
                        case CREDIT:
                            ccLayout = (LinearLayout) Utility.inflateViewtoStub(viewStubCompat3, R.layout.cc_dc_detail);
                            break;
                    }
                }
                showCCDCDetails(cardDetails);
            }
        }

        return view;
    }

    private void showCCDCDetails(CardDetails cardDetails) {
        TextView cardType;
        TextView cardNo = null;
        TextView cardExpiry = null;
        TextView cvv = null;
        TextView pin = null;
        if(cardDetails.getCardType().equals(CardDetails.CardType.CREDIT) && ccLayout!=null) {
            cardType = (TextView) ccLayout.findViewById(R.id.card_type);
            cardNo = (TextView) ccLayout.findViewById(R.id.card_no);
            cardExpiry = (TextView) ccLayout.findViewById(R.id.expiry_date);
            cvv = (TextView) ccLayout.findViewById(R.id.cvv);
            pin = (TextView) ccLayout.findViewById(R.id.card_pin);
            cardType.setText("Credit Card");
        }
        else if(cardDetails.getCardType().equals(CardDetails.CardType.DEBIT) && dcLayout!=null){
            cardType = (TextView) dcLayout.findViewById(R.id.card_type);
            cardNo = (TextView) dcLayout.findViewById(R.id.card_no);
            cardExpiry = (TextView) dcLayout.findViewById(R.id.expiry_date);
            cvv = (TextView) dcLayout.findViewById(R.id.cvv);
            pin = (TextView) dcLayout.findViewById(R.id.card_pin);
            cardType.setText("Debit Card");
        }
        cardNo.setText(String.valueOf(cardDetails.getCardNumber()));
        cardExpiry.setText(new SimpleDateFormat("MM/yy").format(cardDetails.getExpiryDate()));
        cvv.setText("CVV : "+String.valueOf(cardDetails.getCVV()));
        pin.setText("PIN : "+String.valueOf(cardDetails.getCardPin()));

    }

    private void showOnlineDetails(String username,String pwd) {
            TextView usernameText = (TextView) onlineLayout.findViewById(R.id.username);
            TextView pwdText = (TextView) onlineLayout.findViewById(R.id.pwd);
            usernameText.setText(username);
            pwdText.setText(pwd);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        unbinder.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }


}
