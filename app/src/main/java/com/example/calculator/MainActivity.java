package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculator.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    CalculatorFrag Fragment;
    ActivityMainBinding binding;
    TextView textview;
    int flag;
    int operationFlag;
    int evalFlag;

    Float number1 = null;
    Float number2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button btn_Converter = binding.btnConverter;




        flag = 0;

//        btn_Converter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (flag == 0){
//                    flag = 1;
//                    btn_Converter.setText("Calculator");
//                    Fragment = new ConverterFrag();
//                    FragmentManager fm = getSupportFragmentManager();
//                    FragmentTransaction transaction = fm.beginTransaction();
//                    transaction.replace(R.id.fragmentContainerView, Fragment);
//                    transaction.commit();
//
//                }else{
//                    flag = 0;
//                    btn_Converter.setText("CONVERTER");
//                    Fragment = new CalculatorFrag();
//                    FragmentManager fm = getSupportFragmentManager();
//                    FragmentTransaction transaction = fm.beginTransaction();
//                    transaction.replace(R.id.fragmentContainerView, Fragment);
//                    transaction.commit();
//                }
//
//
//
//            }
//        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        textview = (TextView) binding.fragmentContainerView.findViewById(R.id.textView2);
    }

    //number clicked
    public void clickedNum(View view){
        Button num = (Button) view;
        String text = num.getText().toString();
        String dispText = textview.getText().toString();
        Log.d("text", "clickedNum: "+ text);
        if (dispText.isEmpty())
        {
            if(text.equals("0")){return;}
            else{
                textview.append(text);}
        }
        else{textview.append(text);}

    }

    // operation check
    public void opClicked(View view){
        Button num = (Button) view;
        String text = num.getText().toString();
        Log.d("TAG", "opClicked: "+text + textview.getText().toString());


        switch (text){

            case "+": {if(!textview.getText().toString().isEmpty()) {operationFlag = 1; number1 = Float.parseFloat(textview.getText().toString()) ;setHintText(textview.getText().toString());textview.setText(""); return;}}
            case "-": {if(!textview.getText().toString().isEmpty()) {operationFlag = 2; number1 = Float.parseFloat(textview.getText().toString()) ;setHintText(textview.getText().toString());textview.setText("");return;}}
            case "/": {if(!textview.getText().toString().isEmpty()) {operationFlag = 3; number1 = Float.parseFloat(textview.getText().toString()) ;setHintText(textview.getText().toString());textview.setText("");return;}}
            case "*": {if(!textview.getText().toString().isEmpty()) {operationFlag = 4; number1 = Float.parseFloat(textview.getText().toString()) ;setHintText(textview.getText().toString());textview.setText("");return;}}
            default: return;


        }
    }



// evaluation function
    public void evaluateFunction(View view){
        if(textview.getText().toString() != null) number2 = Float.parseFloat(textview.getText().toString()) ;

        if(operationFlag == 1){
            if(number1 != null && number2!= null){
            String result = String.valueOf(number1 + number2);
            textview.setText(result);
            setHintText("");}
        }
        if(operationFlag == 2){
            if(number1 != null && number2!= null){
                String result = String.valueOf(number1 - number2);
                textview.setText(result);
                setHintText("");}
        }
        if(operationFlag == 3){
            if(number1 != null && number2!= null){
                if(number2!=0){
                String result = String.valueOf(number1 / number2);
                textview.setText(result);
                setHintText("");}else{
                    Toast.makeText(this, "Error divide by zero", Toast.LENGTH_SHORT).show();
                    clear(textview);
                }}
        }
        if(operationFlag == 4){
            if(number1 != null && number2!= null){
                String result = String.valueOf(number1 * number2);
                textview.setText(result);
                setHintText("");}
        }

    }

    //set the hint text
    public void setHintText(String t){
       TextView text = binding.fragmentContainerView.findViewById(R.id.textView3);
       text.setText(t);
    }

    //clear function
    public void clear(View view){
        textview.setText("");
        setHintText("");
        number1 = null;
        number2 = null;
    }

}



