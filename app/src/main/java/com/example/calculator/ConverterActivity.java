package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.calculator.databinding.ActivityConverterBinding;
import com.example.calculator.databinding.ActivityMainBinding;

public class ConverterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String value1;
    String value2;

    Double result;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter;
    ActivityConverterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        binding = ActivityConverterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = new Intent(this, MainActivity.class);

        binding.btnConverter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Spinner spinner = (Spinner) binding.spinner;
        Spinner spinner2 = (Spinner) binding.spinner3;
        adapter = ArrayAdapter.createFromResource(this,
                R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);


        // this function check for the entered values in the spinner then convert to the another unit
        // only if the two units are compatable for convertion
        binding.Convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.editTextNumberDecimal.getText().toString().isEmpty()){
                    Toast.makeText(ConverterActivity.this, "Please select the Value", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value1.isEmpty() || value2.isEmpty()){
                    Toast.makeText(ConverterActivity.this, "Please select the unit", Toast.LENGTH_SHORT).show();
                }

                else if( value1.equals(value2)){
                    binding.textView4.setText(binding.editTextNumberDecimal.getText().toString());
                    return;
                }
                else{
                    result =new Double(binding.editTextNumberDecimal.getText().toString());

                    if (value1.equals("Km") || value1.equals("M") || value1.equals("Cm")){

                        if (value2.equals("Km") || value2.equals("M") || value2.equals("Cm")){

                            switch (value1){
                                case "Km": if(value2.equals("M")){result = result * 1000;}else{result = result *100000;}
                                case "M" : if(value2.equals("Km")){result = result / 1000;}else{result = result *100;}
                                case "Cm": if(value2.equals("Km")){result = result / 100000;}else{result = result /100;}
                            }
                            binding.textView4.setText(result.toString());
                        }
                        }
                    else if (value1.equals("C") || value1.equals("F")){
                            if (value2.equals("C") || value2.equals("F")){
                                switch (value1){
                                    case "C":  if(value2.equals("F")){result = (result * 9/5) + 32;binding.textView4.setText(result+"F");}
                                    case "F":  if(value2.equals("C")){

                                        result = (result-32.0);
                                        result = result * 5/9;
                                        Log.d("TAG", "onClick: "+result);
                                        binding.textView4.setText(result+"C");}

                                }
                            }

                    }else if (value1.equals("Kmh") || value1.equals("Ms")){
                        if (value2.equals("Kmh") || value2.equals("Ms")){
                            switch (value1){
                                case "Kmh":  if(value2.equals("Ms")){
                                    result = (result/3.6);
                                    double scale = Math.pow(10, 3);
                                    Double f = Math.round(result * scale) / scale;
                                    binding.textView4.setText(f+"m/s");}
                                case "Ms":  if(value2.equals("Kmh")){
                                    result = (result*3.6);
                                    double scale = Math.pow(10, 3);
                                    Double f = Math.round(result * scale) / scale;
                                    binding.textView4.setText(f+"Km/h");}

                            }
                        }
                    }
            }
        }});}





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // this function check which spinner has been selected and get its value
        // value1 is for spinner 1 value
        // value2 is for spinner 2 value
        if (parent.getAdapter() == adapter){value1 = parent.getSelectedItem().toString();}
        if (parent.getAdapter() == adapter2){value2 = parent.getSelectedItem().toString();}
        Log.d("TAG", "onClick: "+value1+value2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}