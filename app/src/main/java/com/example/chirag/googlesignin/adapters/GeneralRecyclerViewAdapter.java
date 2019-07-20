package com.example.chirag.googlesignin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chirag.googlesignin.R;

import com.example.chirag.googlesignin.fragment.AulaFragment;
import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.ExemptionMechanism;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralRecyclerViewAdapter extends RecyclerView.Adapter<GeneralRecyclerViewAdapter.ViewHolder> {


    List<AulaModel> aulaModels;
    Context context;
    List<DisciplinaModel> mData;
    private List<String> list;

    public GeneralRecyclerViewAdapter(Context context, List<DisciplinaModel> mData) {
        this.aulaModels = aulaModels;
        this.context = context;
        this.mData = mData;
    }


    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        if (mData.size() >= i)
            return;

        holder.tvtitle.setText(mData.get(1).getNome());
        holder.tvOverview.setText(mData.get(1).getNome());

        Picasso.with(getContext()).load(mData.get(1).getNome()).into(holder.ivcalendario);
    }

    @Override
    public int getItemCount() {

        //return mData.size();
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivcalendario)
        ImageView ivcalendario;
        @BindView(R.id.tvtitle)
        TextView tvtitle;
        @BindView(R.id.tvOverview)
        TextView tvOverview;
        @BindView(R.id.cvcalendario)
        CardView cvcalendario;

        ViewHolder(View itemview) {
            super(itemview);

            ButterKnife.bind(this, itemview);
            itemview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            AulaModel aula = aulaModels.get(getAdapterPosition());
            Intent intent = new Intent(getContext(), AulaFragment.class);
            intent.putExtra("Aula", aula);
            getContext().startActivity(intent);

        }
    }
}
