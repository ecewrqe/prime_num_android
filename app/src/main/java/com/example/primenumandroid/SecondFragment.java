package com.example.primenumandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.primenumclasses.PrimeNum;
import com.example.primenumclasses.NumSmallError;

import java.util.ArrayList;
import java.util.List;


public class SecondFragment extends Fragment {
    View rootView;
    EditText judge_edit;
    Button judge;
    TextView judgeMsg;
    Button toPrimeTable;

    boolean happy;
    boolean prime;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.e("fffddd", "ff");
        rootView = inflater.inflate(R.layout.fragment_second, container, false);
        // 値を与える
        judge_edit = (EditText)rootView.findViewById(R.id.judge_edit);
        judge = (Button)rootView.findViewById(R.id.judge);
        judgeMsg = (TextView)rootView.findViewById(R.id.judgeMsg);
        toPrimeTable = (Button)rootView.findViewById(R.id.toPrimeTable);
        judge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int prime_num = Integer.parseInt(judge_edit.getText().toString());

                    List<Integer> divitor = new ArrayList<Integer>(1);
                    divitor.add(0);
                    boolean result = PrimeNum.is_prime_func(prime_num, divitor);
                    Log.e("out", divitor.toString());
                    boolean happy = PrimeNum.is_happy_func(prime_num);
                    if(result){
                        Log.e("out", "素数");
                        judgeMsg.setText("素数");
                        if(happy){
                            judgeMsg.setText("素数且つハッピー素数");
                        }
                    }else{
                        judgeMsg.setText(("素数ではない、少なくとも"+divitor.get(0)+"に割れる"));
                        if(happy){
                            judgeMsg.setText(("素数ではない、少なくとも"+divitor.get(0)+"に割れる、ハッピー数"));
                        }
                    }
                    judgeMsg.setTextColor(Color.BLACK);
                }catch (NumberFormatException ex){
                    judgeMsg.setText("数字ではない");
                    judgeMsg.setTextColor(Color.RED);
                }

            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toPrimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dddd", "dddd");
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}