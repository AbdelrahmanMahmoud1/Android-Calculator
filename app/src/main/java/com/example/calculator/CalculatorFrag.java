package com.example.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.databinding.FragmentCalculatorBinding;

public class CalculatorFrag extends Fragment  {

    TextView textView;
    private FragmentCalculatorBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        return view;
    }

public void ab (View v){

}





}