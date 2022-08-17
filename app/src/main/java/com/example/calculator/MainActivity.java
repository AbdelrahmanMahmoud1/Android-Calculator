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


    boolean sign = true;
    boolean isDot = false;
    boolean prevNum = false;
    boolean isOp = true;
    boolean isZeroDot = false;
    boolean firstNumberZero = false;
    boolean numberAfDot = true;
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
        binding.fragmentContainerView.findViewById(R.id.sign).setOnClickListener(v -> {
            if (sign){
                textview.append("-");
                sign = false;
            }else{
                String dispText = textview.getText().toString();

                String text = dispText.substring(0,dispText.length() - 1);
                textview.setText(text);
                sign = true;
            }
        });
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
            else if(text.equals("%")){return;}
            else{
                if(text.equals("0")){textview.append(text);prevNum = true;isDot=false;numbersCount++;zeroFlag=false;firstNumberZero=true;}
                else{textview.append(text);prevNum = true;isDot=false;numbersCount++;zeroFlag=true;}
            }
        }
        else if (text.equals("*") || text.equals("-") || text.equals("+") || text.equals("/") || text.equals("%")){
            if(operationTrigger == 0 && numberAfDot){
                textview.append(text);
                isOp = true;
                prevNum = false;
                operationTrigger = 1;
                numbersCount = 0;
                zeroFlag = false;
                isZeroDot = false;
                firstNumberZero = false;
            }else {
                Log.d("TAG", "clickedNum: ");
                return;
            }
        }
        else{
            if(numbersCount<11){
//                if (zeroFlag && text.equals("0")){textview.append(text);zeroFlag=false;operationTrigger = 0;prevNum = true;isDot=false;numbersCount++;}
//
//                else if (isZeroDot && text.equals("0")){
//                    if(!textview.getText().toString().equals("0"))
//                    textview.append(text);operationTrigger = 0;prevNum = true;isDot=false;numbersCount++;
//                }

                 if(!text.equals("0"))
                 {
                     if (dispText.equals("0") && firstNumberZero){
                         textview.setText(text);operationTrigger = 0;prevNum = true;isDot=false;numbersCount++;zeroFlag=true;firstNumberZero=false;sign = true;numberAfDot=true;}
                     else if (firstNumberZero && !isZeroDot){
                         text = dispText.substring(0,dispText.length() - 1) + text;
                         textview.setText(text);operationTrigger = 0;prevNum = true;isDot=false;numbersCount++;zeroFlag=true;firstNumberZero=false;sign = true;numberAfDot=true;
                     }
                     else{textview.append(text);operationTrigger = 0;prevNum = true;isDot=false;numbersCount++;zeroFlag=true;firstNumberZero=false;sign = true;numberAfDot=true;}
                 }
                 else {
                     if(operationTrigger == 1){textview.append(text);prevNum = true;firstNumberZero=true;operationTrigger=0;numbersCount++;sign = true;numberAfDot=true;}
                     else if(isZeroDot){textview.append(text);prevNum = true;firstNumberZero=false;operationTrigger=0;numbersCount++;sign = true;numberAfDot=true;}
                     else if (zeroFlag){textview.append(text);prevNum = true;firstNumberZero=false;operationTrigger=0;numbersCount++;sign = true;numberAfDot=true;}


                 }
            }
            }

    }else{return;}}

    public void dotFunction (View view){
//        Button num = (Button) view;
//        String text = num.getText().toString();

        String dispText = textview.getText().toString();

        if(dispText.isEmpty()){return;}

        else if (prevNum && isOp){textview.append(".");prevNum=false;isOp = false;isDot=true;isZeroDot=true;numberAfDot=false;}

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
        String value = engine.eval(result).toString();
        if (result.isEmpty()) {return;} else {
            if (value.equals("Infinity") || value.equals("NaN")){
                Log.d("TAG", "evaluateFunction: "+engine.eval(result) + engine.eval(result));
                Toast.makeText(this, "Invalid Format", Toast.LENGTH_SHORT).show();
                textview.setText("");
                return;
            }
            Double fResult = (double) engine.eval(result);
            if (fResult % 1 == 0) {
                Integer R = fResult.intValue();
                textview.setText(R.toString());
                isOp = true;
                prevNum = true;
                numbersCount = R.toString().length();
                sign = true;
            } else {
                textview.setText(fResult.toString());
                isOp = false;
                numbersCount = fResult.toString().length();

                sign = true;

            }
            if (fResult == 0){firstNumberZero = true;}
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
        prevNum = false;
        isOp = true;
        isZeroDot = false;
        sign = true;
        isDot = false;
        firstNumberZero = false;
        numberAfDot = true;
        numbersCount = 0;
        operationTrigger=0;
    }

    public void delete (View view){
        String dispText = textview.getText().toString();
        if(!textview.getText().toString().isEmpty()){
            String text = dispText.substring(0,dispText.length() - 1);
            textview.setText(text);
        }
    }
}



