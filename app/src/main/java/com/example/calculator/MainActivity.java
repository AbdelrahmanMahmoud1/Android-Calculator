package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
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

//calculator App Abdelrahman Mahmoud 18P6605

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TextView textview;


    ScriptEngine engine;

    boolean dotAfterEval = false; // condition to prevent setting dot to decimal number after evaluation
    boolean zeroFlag = true; // condition that give permession to add zeros
    boolean operationTrigger = false; // condition to check if there is operation or not
    boolean sign = true; // condtion to prevent adding two signs after each other
    boolean isDot = false; // condition to check if there is dot or not
    boolean prevNum = false; // condition to check if there is previous number entered or not
    boolean isOp = true; // condition to check if opertaion entered to make the app enter new dot
    boolean isZeroDot = false; // condition to check if there is dot to add zeros as the user want bc there is no leading zero
    boolean firstNumberZero = false; // condition to check if the first number is zero to replace it if user entered number
    boolean numberAfDot = true; // condition to check if there is number after dot to prevent two dots error
    int numbersCount = 0;// count of characters in number

    String ms = ""; // memory variable
    boolean isMs =true; // check if can save item in the memroy or not to prevent errors


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        engine = new ScriptEngineManager().getEngineByName("rhino");


        Intent intent = new Intent(this, ConverterActivity.class);

        // function to change the activity
        binding.btnConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        // MS memory function that save  item in the memory

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
        // MR memory function that recall item from the memory

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
        // M+ memory function that add to item in the memory

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

        // M- memory function that subtract from item in the memory
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
        //this function set the sign on the screen to make number negative or positive
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


        }




    //number clicked
    //this function used to  type on the screen for user
    // it handle most of the corner cases to prevent it
    //like adding two operation after each other
    //leading zero error
    //override the zero if it leads a number
    //etc..
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


    // this function used to set dot on the screen after checking on different condition
    // to prevent various errors and corner cases that could exist
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
//first we check that the characters entered are valid and not empty
        if (result.isEmpty()) {return;} else {
           char x = result.charAt(result.length()-1);
            if (x == '+' || x == '*' || x == '-' || x == '%' || x == '/' ){
                Toast.makeText(this, "Invalid Format", Toast.LENGTH_SHORT).show();
                return;
            }
            // if the entered number is valid then we evaluate the number by the script engine which
            //provide us with eval funtion
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
                double scale = Math.pow(10, 4);
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
    //set the hint text to see the previous value
    public void setHintText(String t){
       TextView text = binding.textView3;
       text.setText(t);
    }

    //clear function and reset all condition to the default value
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

    // this function delete the last character in the textview and change the program state
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




}



