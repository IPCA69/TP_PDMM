package com.example.chirag.googlesignin.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

public class FragmentGenerico extends Fragment {
    public Context context;
    public static final String TAG = "SignIn";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
