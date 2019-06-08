package com.example.chirag.googlesignin.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.chirag.googlesignin.fragment.AulaFragment;
import com.example.chirag.googlesignin.fragment.CalendarioFragment;
import com.example.chirag.googlesignin.fragment.CursoFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("message","Fragment"+position);

        switch(position) {
            case 0:
                AulaFragment aulaFragment =new AulaFragment();
                position = position +1;
                aulaFragment.setArguments(bundle);
                return aulaFragment;
            case 1:
                CalendarioFragment calendarioFragment =new CalendarioFragment();
                position = position +1;
                calendarioFragment.setArguments(bundle);
                return calendarioFragment;
            default:
                CursoFragment cursoFragment =new CursoFragment();
                position = position +1;
                cursoFragment.setArguments(bundle);
                return cursoFragment;
        }



    }

    @Override
    public int getCount() {
        return 3;
    }
}
