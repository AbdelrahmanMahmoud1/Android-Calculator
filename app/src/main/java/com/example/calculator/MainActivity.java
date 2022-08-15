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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class MainActivity extends AppCompatActivity {
    CalculatorFrag Fragment;
    ActivityMainBinding binding;
    TextView textview;
    int flag;
    boolean zeroFlag = true;
    int operationFlag;
    int operationTrigger;
    int evalFlag;
    ScriptEngine engine;

    boolean isDot = false;
    boolean prevNum = false;
    boolean isOp = true;
    boolean isZeroDot = false;

    int numbersCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button btn_Converter = binding.btnConverter;


        engine = new ScriptEngineManager().getEngineByName("rhino");

        Fragment = new CalculatorFrag();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, Fragment);
        transaction.commit();



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

        if (  textview.length() < 32){
        if (dispText.isEmpty())
        {

            if(text.equals("*")){return;}
            else if(text.equals("-")){return;}
            else if(text.equals("/")){return;}
            else if(text.equals("+")){return;}
            else{
                if(text.equals("0")){textview.append(text);prevNum = true;isDot=false;numbersCount++;zeroFlag=false;}
                else{textview.append(text);prevNum = true;isDot=false;numbersCount++;}
            }
        }
        else if (text.equals("*") || text.equals("-") || text.equals("+") || text.equals("/")){
            if(operationTrigger == 0 && !isDot){
                textview.append(text);
                isOp = true;
                prevNum = false;
                operationTrigger = 1;
                numbersCount = 0;
                zeroFlag = true;
                isZeroDot = false;
            }else {
                return;
            }
        }
        else{
            if(numbersCount<11){
                if (zeroFlag && text.equals("0")){textview.append(text);zeroFlag=false;operationTrigger = 0;prevNum = true;isDot=false;numbersCount++;}
                else if (isZeroDot && text.equals("0")){
                    if(!textview.getText().toString().equals("0"))
                    textview.append(text);operationTrigger = 0;prevNum = true;isDot=false;numbersCount++;
                }
                else if(!text.equals("0")){textview.append(text);operationTrigger = 0;prevNum = true;isDot=false;numbersCount++;}
            }
            }

    }else{return;}}

    public void dotFunction (View view){
//        Button num = (Button) view;
//        String text = num.getText().toString();

        String dispText = textview.getText().toString();

        if(dispText.isEmpty()){return;}

        else if (prevNum && isOp){textview.append(".");prevNum=false;isOp = false;isDot=true;isZeroDot=true;}

        else{
            return;
        }

    }

    // operation check
//    public void opClicked(View view){
//        Button num = (Button) view;
//        String text = num.getText().toString();
//        Log.d("TAG", "opClicked: "+text + textview.getText().toString());
//
//
//        switch (text){
//
//            case "+": {if(!textview.getText().toString().isEmpty()) {setHintText(textview.getText().toString());textview.setText(""); return;}}
//            case "-": {if(!textview.getText().toString().isEmpty()) {operationFlag = 2; number1 = Float.parseFloat(textview.getText().toString()) ;setHintText(textview.getText().toString());textview.setText("");return;}}
//            case "/": {if(!textview.getText().toString().isEmpty()) {operationFlag = 3; number1 = Float.parseFloat(textview.getText().toString()) ;setHintText(textview.getText().toString());textview.setText("");return;}}
//            case "*": {if(!textview.getText().toString().isEmpty()) {operationFlag = 4; number1 = Float.parseFloat(textview.getText().toString()) ;setHintText(textview.getText().toString());textview.setText("");return;}}
//            case ".": {if(isDot) {return;} else{}}
//            default: return;
//
//
//        }
//    }



// evaluation function
    public void evaluateFunction(View view) throws ScriptException {
        String result = textview.getText().toString();

        if (result.isEmpty()) {return;} else {
            Double fResult = (double) engine.eval(result);
            if (fResult % 1 == 0) {
                Integer R = fResult.intValue();
                textview.setText(R.toString());
                isOp = true;
                prevNum = true;
                numbersCount = R.toString().length();
            } else {
                textview.setText(fResult.toString());
                isOp = false;
                numbersCount = fResult.toString().length();



            }
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
        isDot = false;
        prevNum = false;
        isOp = true;
        numbersCount = 0;
        isZeroDot = false;
    }

}



