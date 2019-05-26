package com.example.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

public class FragmentGenerico extends Fragment {
    public Context context;
    public static final String TAG = "MainActivity";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
