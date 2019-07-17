package com.example.chirag.googlesignin.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.chirag.googlesignin.fragment.AulaFragment;
import com.example.chirag.googlesignin.fragment.CalendarioFragment;
import com.example.chirag.googlesignin.fragment.CursoFragment;
import com.example.chirag.googlesignin.fragment.DisciplinaFragment;
import com.example.chirag.googlesignin.fragment.EventoFragment;
import com.example.chirag.googlesignin.fragment.TipoDeAulaFragment;
import com.example.chirag.googlesignin.fragment.TurmasFragment;

import java.time.Year;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Integer Year = 0;

    public ViewPagerAdapter(FragmentManager fm, int year) {
        super(fm);
        this.Year = year;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("Year", this.Year);

        switch (position) {
            case 0:
                AulaFragment aulaFragment = new AulaFragment();
                position = position + 1;
                aulaFragment.setArguments(bundle);
                return aulaFragment;
            case 1:
                CalendarioFragment calendarioFragment = new CalendarioFragment();
                position = position + 1;
                calendarioFragment.setArguments(bundle);
                return calendarioFragment;
            case 2:
                CursoFragment cursoFragment = new CursoFragment();
                position = position + 1;
                cursoFragment.setArguments(bundle);
                return cursoFragment;
            case 3:
                DisciplinaFragment disciplinaFragment = new DisciplinaFragment();
                position = position + 1;
                disciplinaFragment.setArguments(bundle);
                return disciplinaFragment;
            case 4:
                EventoFragment eventoFragment = new EventoFragment();
                position = position + 1;
                eventoFragment.setArguments(bundle);
                return eventoFragment;
            case 5:
                TurmasFragment turmasFragment = new TurmasFragment();
                position = position + 1;
                turmasFragment.setArguments(bundle);
                return turmasFragment;
            case 6:
                TipoDeAulaFragment tipoDeAulaFragment = new TipoDeAulaFragment();
                position = position + 1;
                tipoDeAulaFragment.setArguments(bundle);
                return tipoDeAulaFragment;
        }


        return null;
    }

    @Override
    public int getCount() {
        return 7;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String nome = null;

        switch (position) {
            case 0:
                nome = "Aula";
                return nome;
            case 1:
                nome = "Cal";
                return nome;
            case 2:
                nome = "Curso";
                return nome;
            case 3:
                nome = "Disc";
                return nome;
            case 4:
                nome = "Evento";
                return nome;
            case 5:
                nome = "Turma";
                return nome;
            case 6:
                nome = "TipoAula";
                return nome;
        }
        return null;
    }
}
