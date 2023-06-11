package com.anubhavps.satrank.adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anubhavps.satrank.R;
import com.anubhavps.satrank.interfaces.iAdapterViewPressed;
import com.anubhavps.satrank.models.Result;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;


public class ViewAllScoreAdapters extends RecyclerView.Adapter<ViewAllScoreAdapters.ScoreViewHolder> {

    private final iAdapterViewPressed adapterViewPressed;
    private final List<Result> resultList;
    private final Context context;

    private int selectedPosition = 0;
    private View rootView;

    public ViewAllScoreAdapters(List<Result> resultList, Context context, iAdapterViewPressed adapterViewPressed) {
        this.resultList = resultList;
        this.context = context;
        this.adapterViewPressed = adapterViewPressed;
    }


    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_display_score, parent, false);
        return new ScoreViewHolder(rootView);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Result result = resultList.get(position);
        holder.nameTxt.setText(result.getName());
        holder.scoreTxt.setText(String.valueOf(result.getScore()));

        if (result.isPassed()) {
            holder.passed.setBackgroundColor(context.getResources().getColor(R.color.green));
        } else {
            holder.passed.setBackgroundColor(context.getResources().getColor(R.color.red));
        }

        holder.cardView.setOnClickListener(v -> adapterViewPressed.onViewPressed(result));


    }


    @Override
    public int getItemCount() {
        return resultList.size();
    }


    static class ScoreViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;
        MaterialTextView nameTxt, scoreTxt;
        LinearLayout passed;


        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cellDisplayScoreCardView);
            passed = itemView.findViewById(R.id.cellDisplayScorePassed);
            nameTxt = itemView.findViewById(R.id.cellDisplayScoreNameTxt);
            scoreTxt = itemView.findViewById(R.id.cellDisplayScoreTxt);
        }
    }
}
