 package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covid19.api.Apiutilities;
import com.example.covid19.api.CountryData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryACTIVITY extends AppCompatActivity {
    private RecyclerView countries;
    private List<CountryData> list;
    private ProgressDialog pd;
    private EditText searchbar;
    private CountryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_a_c_t_i_v_i_t_y);
        countries = findViewById(R.id.countries);
        searchbar = findViewById(R.id.searchbar);
        list = new ArrayList<>();
        countries.setLayoutManager(new LinearLayoutManager(this));
        countries.setHasFixedSize(true);
         adapter =  new CountryAdapter(this,list);
        countries.setAdapter(adapter);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        Apiutilities.getApiinterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(CountryACTIVITY.this," "+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

    }

    private void filter(String text) {
        List<CountryData> filterList = new ArrayList<>();
        for (CountryData items: list){
            if (items.getCountry().toLowerCase().contains(text.toLowerCase())){
                filterList.add(items);
            }
        }

        adapter.fFilterList(filterList);

    }
}