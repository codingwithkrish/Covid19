package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19.api.Apiutilities;
import com.example.covid19.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private  TextView totalconfirmedcases,todayconfirm,totalactive,totalrecoverd,
            todayrecovered,totaldeaths,todaydeaths,totaltests,todaytests,updatedd,name;
    private List<CountryData> list;
    private Button call,sms;
    private PieChart mPieChart;
    String country = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        if (getIntent().getStringExtra("country")!=null){
            country = getIntent().getStringExtra("country");
        }
        init();
        name.setText(country);
        Apiutilities.getApiinterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                list.addAll(response.body());
                for (int i  = 0;i<list.size();i++){
                    if (list.get(i).getCountry().equals(country)){
                        int Confirm =Integer.parseInt(list.get(i).getCases());
                        int Active =Integer.parseInt(list.get(i).getActive());
                        int Recovered =Integer.parseInt(list.get(i).getRecovered());
                        int Death =Integer.parseInt(list.get(i).getDeaths());

                        totalactive.setText(NumberFormat.getInstance().format(Active));
                        totalrecoverd.setText(NumberFormat.getInstance().format(Recovered));
                        totaldeaths.setText(NumberFormat.getInstance().format(Death));
                        totalconfirmedcases.setText(NumberFormat.getInstance().format(Confirm));
                        //totaltests.setText(NumberFormat.getInstance().format(Confirm));

                        todaydeaths.setText("*"+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                        todayconfirm.setText("*"+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                        todayrecovered.setText("*"+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                        todaytests.setText(" "+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                        setText(list.get(i).getUpdated());

                        mPieChart.addPieSlice(new PieModel("Confirm", Confirm, Color.parseColor("#FFFF00")));
                        mPieChart.addPieSlice(new PieModel("Recovered", Recovered, Color.parseColor("#69F0AE")));
                        mPieChart.addPieSlice(new PieModel("Active", Active, Color.parseColor("#536DFE")));
                        mPieChart.addPieSlice(new PieModel("Deaths", Death, Color.parseColor("#FF5252")));
                        mPieChart.startAnimation();






                    }
                }
            }


            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error"+t.getMessage(),Toast.LENGTH_LONG
                );
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CountryACTIVITY.class);
                startActivity(intent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri uri = Uri.parse("tel:"+"1123978046");
                intent.setData(uri);
                startActivity(intent);
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("vnd.android-dir/mms-sms");
                Uri uri = Uri.parse("sms:"+"1123978046");
                intent.setData(uri);
                startActivity(intent);
            }
        });

    }

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("MMM dd,yyy");
        long millisecond = Long.parseLong(updated);
        Calendar calendar =Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        updatedd.setText("Updated at "+format.format(calendar.getTime()));
    }


    public  void init(){
        totalconfirmedcases = findViewById(R.id.totalconfirmedcases);
        todayconfirm = findViewById(R.id.todayconfirm);
        totalactive = findViewById(R.id.totalactive);
        totalrecoverd = findViewById(R.id.totalrecoverd);
        todayrecovered = findViewById(R.id.todayrecovered);
        totaldeaths = findViewById(R.id.totaldeaths);
        todaydeaths = findViewById(R.id.todaydeaths);
        //totaltests = findViewById(R.id.totaltests);
        call = findViewById(R.id.call);
        sms = findViewById(R.id.sms);
        todaytests = findViewById(R.id.todaytests);
         mPieChart =  findViewById(R.id.piechart);
        updatedd =  findViewById(R.id.updated);
        name =  findViewById(R.id.name);



    }
}