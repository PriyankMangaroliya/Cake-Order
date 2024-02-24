package com.example.cake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CakeCategoryAdapter extends RecyclerView.Adapter<CakeCategoryAdapter.ViewHolder> {
    private List<CakeCategory> cakeCategories;

    public CakeCategoryAdapter(List<CakeCategory> cakeCategories) {
        this.cakeCategories = cakeCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CakeCategory cakeCategory = cakeCategories.get(position);

        holder.categoryNameTextView.setText(cakeCategory.getCategoryName());
        holder.categoryDescriptionTextView.setText(cakeCategory.getDescription());
    }

    @Override
    public int getItemCount() {
        return cakeCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTextView, categoryDescriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            categoryDescriptionTextView = itemView.findViewById(R.id.categoryDescriptionTextView);
        }
    }
}
