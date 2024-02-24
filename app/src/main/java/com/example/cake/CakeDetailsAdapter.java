package com.example.cake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CakeDetailsAdapter extends RecyclerView.Adapter<CakeDetailsAdapter.CakeViewHolder> {

    private Context context;
    private List<ViewCake.CakeDetails> cakeDetailsList;
    private FragmentManager fragmentManager;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ViewCake.CakeDetails cakeDetails);
    }

    public CakeDetailsAdapter(Context context, List<ViewCake.CakeDetails> cakeDetailsList, FragmentManager fragmentManager, OnItemClickListener itemClickListener) {
        this.context = context;
        this.cakeDetailsList = cakeDetailsList;
        this.fragmentManager = fragmentManager;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake, parent, false);
        return new CakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CakeViewHolder holder, int position) {
        ViewCake.CakeDetails cakeDetails = cakeDetailsList.get(position);

        holder.tvCakeName.setText(cakeDetails.getCakeName());
        holder.tvCakeCategory.setText(cakeDetails.getCakeCategory());
        holder.tvCakePrice.setText("" + cakeDetails.getCakePrice());
        holder.tvCakeDescription.setText(cakeDetails.getCakeDescription());

        // Set a click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(cakeDetails);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cakeDetailsList.size();
    }

    public class CakeViewHolder extends RecyclerView.ViewHolder {
        TextView tvCakeName, tvCakeCategory, tvCakePrice, tvCakeDescription;

        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCakeName = itemView.findViewById(R.id.cakeNameTextView);
            tvCakeCategory = itemView.findViewById(R.id.categoryNameTextView);
            tvCakePrice = itemView.findViewById(R.id.priceTextView);
            tvCakeDescription = itemView.findViewById(R.id.cakeDescTextView);
        }
    }
}
