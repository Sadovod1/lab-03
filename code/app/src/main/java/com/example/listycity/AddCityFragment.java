package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener{
        void addCity(City city);
        void editCity(City city);
    }
    City myCity;

    public AddCityFragment(){

    }
    public AddCityFragment(City city){
        this.myCity = city;
    }
    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        }
        else{
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if(myCity == null){
            return builder.setView(view).setTitle("Add a city").setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", (dialog, which) -> { String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        listener.addCity(new City(cityName, provinceName));}).create();
        }
        else{
            editCityName.setText(myCity.getName());
            editProvinceName.setText(myCity.getProvince());
            return builder.setView(view).setTitle("Edit City").setPositiveButton("Save", (dialog, which) -> {
                myCity.setName(editCityName.getText().toString());
                myCity.setProvince(editProvinceName.getText().toString());
                listener.editCity(myCity);
            }).create();
        }
    }
}
