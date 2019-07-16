package com.example.chirag.googlesignin.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.chirag.googlesignin.R;
import com.example.chirag.googlesignin.adapters.GeneralRecyclerViewAdapter;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.RealmList;
import io.realm.RealmObject;

public class RecView extends FragmentGenerico {


    @BindView(R.id.rvid)
    RecyclerView rvid;
    Unbinder unbinder;

    List<AulaModel> aulaModels;
    List<DisciplinaModel> listadisciplinas;
    String[] strings = {"1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7"};

    public RecView() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rec_view, container, false);
        unbinder = ButterKnife.bind(this, view);

        GeneralRecyclerViewAdapter mAdapter= new GeneralRecyclerViewAdapter(getContext(),listadisciplinas);

        rvid.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvid.setAdapter(mAdapter);


        Log.d(TAG, "onCreate: View Initialization done");
        return view;

//        RecyclerView rv = new RecyclerView(getContext());
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv.setAdapter(new RecView.SimpleRVAdapter(strings));
//        return rv;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listadisciplinas = new ArrayList<>();
        // PORQUE NAO DÀ !!!!!!!!!!!!!!!!!! ???????????????????????
        // listadisciplinas.btSave(new DisciplinaModel(2,"sdf","dfh",2019,"fh",2));
    }

    /**
     * A Simple Adapter for the RecyclerView
     */
    public class SimpleRVAdapter extends RecyclerView.Adapter<RecView2.SimpleViewHolder> {
        private String[] dataSource;
        public SimpleRVAdapter(String[] dataArgs){
            dataSource = dataArgs;
        }

        @Override
        public RecView2.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new TextView(parent.getContext());
            RecView2.SimpleViewHolder viewHolder = new RecView2.SimpleViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecView2.SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource[position]);
        }

        @Override
        public int getItemCount() {
            return dataSource.length;
        }
    }


    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }


    @Override
    public RealmObject CastRealmObjectToEntity(RealmObject obj) {
        return null;
    }

    @Override
    public boolean Validate() {
        return false;
    }

    @Override
    public void EntityToDOM() {

    }

    @Override
    public void CleanView() {

    }

    @Override
    public void SetEnable(boolean value) {

    }

    @Override
    public Button getBtDelete() {
        return null;
    }

    @Override
    public Button getBtSave() {
        return null;
    }

    @Override
    public Button getBtNew() {
        return null;
    }

    @Override
    public Button getBtEdit() {
        return null;
    }

    @Override
    public Button getBtImport() {
        return null;
    }

    @Override
    public String getFragmentDesc() {
        return null;
    }
}

