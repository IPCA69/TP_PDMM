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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralRecyclerViewAdapter extends RecyclerView.Adapter<GeneralRecyclerViewAdapter.ViewHolder> {


    List<AulaModel> aulaModels;
    Context context;

    public GeneralRecyclerViewAdapter(Context context, List<AulaModel> aulaModels){

        this.aulaModels = aulaModels;
        this.context = context;
    }

private Context getContext(){
        return context;
}
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int i) {

        AulaModel aulaModel = aulaModels.get(i);
        holder.tvtitle.setText(aulaModel.getSala());
        holder.tvOverview.setText(aulaModel.getSumario());

        Picasso.with(getContext()).load(aulaModel.getSala()).into(holder.ivcalendario);
    }

    @Override
    public int getItemCount() {
        return aulaModels.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivcalendario)
        ImageView ivcalendario;
        @BindView(R.id.tvtitle)
        TextView tvtitle;
        @BindView(R.id.tvOverview)
        TextView tvOverview;
        @BindView(R.id.cvcalendario)
        CardView cvcalendario;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

       @Override
       public void onClick(View view) {

           AulaModel aula = aulaModels.get(getAdapterPosition());
           Intent intent = new Intent(getContext(), AulaFragment.class);
           intent.putExtra("Aula",aula);
           getContext().startActivity(intent);

       }
   }
}
