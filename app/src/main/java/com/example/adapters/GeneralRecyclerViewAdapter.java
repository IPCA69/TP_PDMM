package com.example.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tp_pdmm.Atividades.R;
import com.example.tp_pdmm.model.AulaModel;
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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendario, parent, false);
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

   public class ViewHolder extends RecyclerView.ViewHolder{
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
        }
    }
}
