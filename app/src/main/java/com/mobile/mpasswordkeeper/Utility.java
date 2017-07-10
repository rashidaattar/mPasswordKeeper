package com.mobile.mpasswordkeeper;

import android.support.v7.widget.ViewStubCompat;
import android.view.View;

/**
 * Created by 836137 on 10-07-2017.
 */

public class Utility {

    public static final String BANK_ID = "bankId";

    public static View inflateViewtoStub(ViewStubCompat viewStubCompat, int layout){
        viewStubCompat.setLayoutResource(layout);
        View view = viewStubCompat.inflate();
        viewStubCompat.setVisibility(View.VISIBLE);
        return view;
    }
}
