package com.example.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

        binding.MS.setOnClickListener(v -> {
           String s = binding.textView2.getText().toString();
            Log.d("TAG", "onCreateView: "+s);
        });

        binding.btn1.setOnClickListener(v -> {
            ((MainActivity)getActivity()).clickedNum(binding.btn1);
        });

        return view;
    }

    }







