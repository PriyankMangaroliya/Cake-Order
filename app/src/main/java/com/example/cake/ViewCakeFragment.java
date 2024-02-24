package com.example.cake;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ViewCakeFragment extends Fragment {

    EditText etCakeName, etCategoryName, etPrice, etCakeDescription;
    Button btnSaveCake;
    TextView cakeNameTextView, categoryNameTextView, priceTextView, cakeDescTextView;
    CardView card;

    public ViewCakeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_cake, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        etCakeName = getActivity().findViewById(R.id.etCakeName);
        etCategoryName = getActivity().findViewById(R.id.etCategoryName);
        etPrice = getActivity().findViewById(R.id.etPrice);
        etCakeDescription = getActivity().findViewById(R.id.etCakeDescription);
        btnSaveCake = getActivity().findViewById(R.id.btnSaveCake);

        cakeNameTextView = getActivity().findViewById(R.id.cakeNameTextView);
        categoryNameTextView = getActivity().findViewById(R.id.categoryNameTextView);
        priceTextView = getActivity().findViewById(R.id.priceTextView);
        cakeDescTextView = getActivity().findViewById(R.id.cakeDescTextView);

        btnSaveCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cakeName = etCakeName.getText().toString();
                String categoryName = etCategoryName.getText().toString();
                String price = etPrice.getText().toString();
                String cakedec = etCakeDescription.getText().toString();

                cakeNameTextView.setText(cakeName);
                categoryNameTextView.setText(categoryName);
                priceTextView.setText(price);
                cakeDescTextView.setText(cakedec);
            }
        });
    }
}