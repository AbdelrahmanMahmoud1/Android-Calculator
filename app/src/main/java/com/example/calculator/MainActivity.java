package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculator.databinding.ActivityMainBinding;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class MainActivity extends AppCompatActivity {
    CalculatorFrag Fragment;
    ConverterFrag  Fragment2;
    ActivityMainBinding binding;
    TextView textview;
    int flag;

    ScriptEngine engine;

    boolean dotAfterEval = false;
    boolean zeroFlag = true;
    boolean operationTrigger = false;
    boolean sign = true;
    boolean isDot = false;
    boolean prevNum = false;
    boolean isOp = true;
    boolean isZeroDot = false;
    boolean firstNumberZero = false;
    boolean numberAfDot = true;
    int numbersCount = 0;

    String ms = "";
    boolean isMs =true;
    String mr;
    boolean isMr =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        engine = new ScriptEngineManager().getEngineByName("rhino");


        Intent intent = new Intent(this, ConverterActivity.class);

        binding.btnConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        binding.MS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( binding.textView2.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isMs && binding.textView2.getText().toString().charAt(
                        binding.textView2.getText().toString().length() -1 )!= '.')

                {ms = binding.textView2.getText().toString();clear(new View (getApplication()  ));
                    Toast.makeText(MainActivity.this, "Number saved= "+ ms, Toast.LENGTH_SHORT).show();

                }else{Toast.makeText(MainActivity.this, "Invalid formate save only numbers", Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", "onClick: "+ms);
            }
        });

        binding.MR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.textView2.getText().toString().isEmpty()){
                    if (ms.isEmpty()){
                        Toast.makeText(MainActivity.this, "Nothing saved", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    binding.textView2.setText(ms);
                }else{
                    Toast.makeText(MainActivity.this, "Please clear the screen first", Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", "onClick: "+ms);
            }
        });
        binding.MC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ms = "";
                clear(new View (getApplication()  ));
                Toast.makeText(MainActivity.this, "Memory Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        binding.M1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.textView2.getText().toString().isEmpty() && isMs){
                    if (ms.isEmpty()){
                        Toast.makeText(MainActivity.this, "Nothing saved to add on it", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Double M1 = new Double(binding.textView2.getText().toString());
                    Double savedValue = new Double(ms);
                    savedValue = savedValue + M1;
                    ms = savedValue.toString();

                    if (M1 % 1 == 0) {
                        Integer R = savedValue.intValue();
                        ms = R.toString();
                        clear(new View (getApplication()  ));

                        Toast.makeText(MainActivity.this, "Value saved = "+ms, Toast.LENGTH_SHORT).show();

                    } else {
                        double scale = Math.pow(10, 3);
                        Double f = Math.round(savedValue * scale) / scale;
                        ms = f.toString();
                        clear(new View (getApplication()  ));

                        Toast.makeText(MainActivity.this, "Value saved = "+ms, Toast.LENGTH_SHORT).show();


                    }}else{
                    Toast.makeText(MainActivity.this, "Please Enter number first", Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", "onClick: "+ms);
            }
        });

        binding.M2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.textView2.getText().toString().isEmpty() && isMs){
                    if (ms.isEmpty()){
                        Toast.makeText(MainActivity.this, "Nothing saved to add on it", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Double M1 = new Double(binding.textView2.getText().toString());
                    Double savedValue = new Double(ms);
                    savedValue = savedValue - M1;
                    ms = savedValue.toString();

                    if (M1 % 1 == 0) {
                        Integer R = savedValue.intValue();
                        ms = R.toString();
                        clear(new View (getApplication()  ));

                        Toast.makeText(MainActivity.this, "Value saved = "+ms, Toast.LENGTH_SHORT).show();

                    } else {
                        double scale = Math.pow(10, 3);
                        Double f = Math.round(savedValue * scale) / scale;
                        ms = f.toString();
                        clear(new View (getApplication()  ));

                        Toast.makeText(MainActivity.this, "Value saved = "+ms, Toast.LENGTH_SHORT).show();


                    }}else{
                    Toast.makeText(MainActivity.this, "Please Enter number first", Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG", "onClick: "+ms);
            }
        });

    }




    @Override
    protected void onStart() {
        super.onStart();
        textview = (TextView) binding.textView2;
        binding.sign.setOnClickListener(v -> {
            if (!textview.getText().toString().isEmpty()){

                if(textview.getText().toString().charAt(textview.getText().length() - 1) == '.' ){return;}
                else if (textview.getText().toString().charAt(textview.getText().length() - 1) == '-' &&
                operationTrigger){return;}
            }
            if (sign){
                textview.append("-");
                isMs =false;
                sign = false;
                zeroFlag = true;
                isOp= true;
            }else{
                String dispText = textview.getText().toString();

                String text = dispText.substring(0,dispText.length() - 1);
                textview.setText(text);
                sign = true;
                isOp= false;
                isMs =false;
            }
        });
//        Button btn_Converter = binding.fragmentContainerView.findViewById(R.id.btn_Converter);
//
//
//            btn_Converter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    btn_Converter.setText("Calculator");
//                    Fragment2 = new ConverterFrag();
//                    FragmentManager fm = getSupportFragmentManager();
//                    FragmentTransaction transaction = fm.beginTransaction();
//                    transaction.replace(R.id.fragmentContainerView, Fragment2);
//                    transaction.addToBackStack("name");
//                    transaction.commit();
//
//
//
//                }
//            });


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
                if(text.equals("0")){textview.append(text);prevNum = true;
                    isMs =false;
                    isDot=false;numbersCount++;zeroFlag=false;firstNumberZero=true;}
                else{textview.append(text);prevNum = true;isDot=false;numbersCount++;zeroFlag=true;}
            }
        }
        else if (text.equals("*") || text.equals("-") || text.equals("+") || text.equals("/") || text.equals("%")){
            if(!operationTrigger && numberAfDot && sign){
                textview.append(text);
                isOp = true;
                prevNum = false;
                operationTrigger = true;
                numbersCount = 0;
                zeroFlag = false;
                isZeroDot = false;
                firstNumberZero = false;
                isDot = false;
                dotAfterEval = false;
                isMs =false;
            }else {
                Log.d("TAG", "clickedNum: ");
                return;
            }
        }
        else{
            if(numbersCount<11){

                 if(!text.equals("0"))
                 {
                     if (dispText.equals("0") && firstNumberZero){
                         textview.setText(text);operationTrigger = false;prevNum = true;numbersCount++;zeroFlag=true;firstNumberZero=false;sign = true;numberAfDot=true;}
                     else if (firstNumberZero && !isZeroDot){
                         text = dispText.substring(0,dispText.length() - 1) + text;
                         textview.setText(text);
                         operationTrigger = false;
                         prevNum = true;
                         numbersCount++;
                         zeroFlag=true;
                         firstNumberZero=false;
                         sign = true;numberAfDot=true;
                         dotAfterEval = false;
                     }
                     else{textview.append(text); operationTrigger = false;prevNum = true;numbersCount++;zeroFlag=true;firstNumberZero=false;sign = true;numberAfDot=true;}
                 }
                 else {
                     if(operationTrigger){textview.append(text);prevNum = true;firstNumberZero=true;operationTrigger=false;numbersCount++;sign = true;numberAfDot=true;}
                     else if(isZeroDot){textview.append(text);prevNum = true;firstNumberZero=false;operationTrigger=false;numbersCount++;sign = true;numberAfDot=true;}
                     else if (zeroFlag){
                         if (!sign){textview.append(text);prevNum = true;zeroFlag=false;firstNumberZero=false;operationTrigger=false;numbersCount++;sign = true;numberAfDot=true;
                         }
                         else{
                         textview.append(text)
                         ;prevNum = true;firstNumberZero=false;operationTrigger=false;numbersCount++;sign = true;numberAfDot=true;
                     }}


                 }

            }
            }

    }else{return;}}

    public void dotFunction (View view){


        String dispText = textview.getText().toString();

        if(dispText.isEmpty()){return;}
        if (dotAfterEval){Log.d("TAG", "dotFunction: "+"0");
            return;
        }
        else if (prevNum && isOp){
            textview.append(".");prevNum=false;isOp = false;isDot=true;isZeroDot=true;numberAfDot=false;dotAfterEval=false;
            Log.d("TAG", "dotFunction: "+"1");
            return;
        }
        else if (sign){
            if(isDot || operationTrigger){
                Log.d("TAG", "dotFunction: ");
                return;
            }
           String text = textview.getText().toString();
           if(text.charAt(text.length() - 1) != '-'){
               textview.append(".");prevNum=false;isOp = false;isDot=true;isZeroDot=true;numberAfDot=false;dotAfterEval=false;
               sign = false;
               Log.d("TAG", "dotFunction: "+"2");
           }

        }
        else{
            return;
        }

    }




// evaluation function
    public void evaluateFunction(View view) throws ScriptException {
        String result = textview.getText().toString();

        if (result.isEmpty()) {return;} else {
           char x = result.charAt(result.length()-1);
            if (x == '+' || x == '*' || x == '-' || x == '%' || x == '/' ){
                Toast.makeText(this, "Invalid Format", Toast.LENGTH_SHORT).show();
                return;
            }
            String value = engine.eval(result).toString();
            Log.d("TAG", "evaluateFunction: "+ value);
            if (value.equals("Infinity") || value.equals("NaN") || value.equals(".")){
                Log.d("TAG", "evaluateFunction: "+engine.eval(result) + engine.eval(result));
                Toast.makeText(this, "Invalid Format", Toast.LENGTH_SHORT).show();
                textview.setText("");

                clear(new View (getApplication()  ));
                return;
            }
            Double fResult = (double) engine.eval(result);
            if (fResult % 1 == 0) {
                Integer R = fResult.intValue();
                textview.setText(R.toString());
                setHintText(R.toString());
                isOp = true;
                prevNum = true;
                numbersCount = R.toString().length();
                sign = true;
                isMs = true;

            } else {
                double scale = Math.pow(10, 3);
                Double f = Math.round(fResult * scale) / scale;
                textview.setText(f.toString());
                isOp = false;
                numbersCount = f.toString().length();
                setHintText(f.toString());
                sign = true;
                dotAfterEval = true;
                isMs = true;

            }
            if (fResult == 0){firstNumberZero = true;}
        }

    }
    //set the hint text
    public void setHintText(String t){
       TextView text = binding.textView3;
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
        operationTrigger=false;
        zeroFlag = true;
        dotAfterEval = false;
        isMs =true;

    }

    public void delete (View view){
        String dispText = textview.getText().toString();
        if(!textview.getText().toString().isEmpty()){
            String text = dispText.substring(0,dispText.length() - 1);
            textview.setText(text);
            prevNum = true;
            isOp = true;
            isZeroDot = false;
            sign = true;
            isDot = false;
            firstNumberZero = false;
            numberAfDot = true;
            numbersCount = numbersCount -1;
            operationTrigger=false;
            zeroFlag = true;
            dotAfterEval = false;
            isMs =true;
        }

    }

//    public void ss(View v) {
//
//
//
//        Fragment Fragment2 = new ConverterFrag();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(R.id.fragmentContainerView, Fragment2);
//        transaction.commit();
//
//
//
//    }


}



