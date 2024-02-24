package com.example.cake;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CakeUpdateFragment extends Fragment {

    private ViewCake.CakeDetails cakeDetails;
    private DatabaseHelper dbHelper;
    private EditText etCakeName, etCakeCategory, etCakePrice, etCakeDescription;

    public static CakeUpdateFragment newInstance(ViewCake.CakeDetails cakeDetails) {
        CakeUpdateFragment fragment = new CakeUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable("cakeDetails", cakeDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cakeDetails = getArguments().getParcelable("cakeDetails");
        }
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cake_details, container, false);

        etCakeName = view.findViewById(R.id.CakeName);
        etCakeCategory = view.findViewById(R.id.CakeCategory);
        etCakePrice = view.findViewById(R.id.CakePrice);
        etCakeDescription = view.findViewById(R.id.CakeDescription);

        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        etCakeName.setText(cakeDetails.getCakeName());
        etCakeCategory.setText(cakeDetails.getCakeCategory());
        etCakePrice.setText(String.valueOf(cakeDetails.getCakePrice()));
        etCakeDescription.setText(cakeDetails.getCakeDescription());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCake();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCake();
            }
        });

        return view;
    }

    private void updateCake() {
        String updatedCakeName = etCakeName.getText().toString();
        String updatedCakeCategory = etCakeCategory.getText().toString();
        double updatedCakePrice = Double.parseDouble(etCakePrice.getText().toString());
        String updatedCakeDescription = etCakeDescription.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Cake_Name", updatedCakeName);
        values.put("Category_Id", updatedCakeCategory);
        values.put("Cake_Price", updatedCakePrice);
        values.put("Cake_Description", updatedCakeDescription);

        long cakeId = cakeDetails.getId();
        db.update("Cake", values, "Cake_Id=?", new String[]{String.valueOf(cakeId)});
        db.close();

        Toast.makeText(getActivity(), "Cake Update Successfully", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
        // Optionally, update the cakeDetails object with the new values (if needed)
        cakeDetails = new ViewCake.CakeDetails(cakeId, updatedCakeName, updatedCakeCategory, updatedCakePrice, updatedCakeDescription);

    }

    private void deleteCake() {
        long cakeId = cakeDetails.getId();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Cake", "Cake_Id=?", new String[]{String.valueOf(cakeId)});
        db.close();

        // Notify the parent activity or fragment about the deletion (if needed)
        if (getActivity() instanceof OnCakeDeletedListener) {
            ((OnCakeDeletedListener) getActivity()).onCakeDeleted(cakeId);
        }

        Toast.makeText(getActivity(), "Cake Delete Successfully", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public interface OnCakeDeletedListener {
        void onCakeDeleted(long cakeId);
    }
}
