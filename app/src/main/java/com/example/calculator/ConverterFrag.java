package com.example.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.calculator.databinding.FragmentConverterBinding;


public class ConverterFrag extends Fragment {


    private FragmentConverterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentConverterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


//        binding.btnConverter2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment Fragment = new CalculatorFrag();
//                FragmentManager fm = getParentFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.fragmentContainerView, Fragment);
//                transaction.commit();
//            }
//        });


        return view;
    }


}