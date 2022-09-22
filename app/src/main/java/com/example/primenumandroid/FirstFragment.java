package com.example.primenumandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.FormatException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.primenumclasses.NumSmallError;
import com.example.primenumclasses.PrimeCheckedError;
import com.example.primenumclasses.PrimeNum;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    View rootView;

    EditText min_num_edit;
    EditText max_num_edit;
    CheckBox prime_check;
    CheckBox happy_check;
    Button toJudgePage;
    Button tableOkButton;
    TextView tableMsg;
    TextView table_title;
    TableLayout prime_table;

    boolean prime;
    boolean happy;

    private InputMethodManager mInputMethodManager;
    private LinearLayout mLayout;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        rootView = inflater.inflate(R.layout.fragment_first, container, false);
        mInputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mLayout = (LinearLayout)rootView.findViewById(R.id.fragment_first_layout);
        Log.e("ddd", mLayout.toString());
        // on touch
        rootView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mInputMethodManager.hideSoftInputFromWindow(mLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                mLayout.requestFocus();
                return false;
            }
        });
        // 値を与える
        min_num_edit = (EditText)rootView.findViewById(R.id.min_num_edit);
        max_num_edit = (EditText)rootView.findViewById(R.id.max_num_edit);
        prime_check = (CheckBox)rootView.findViewById(R.id.prime_check);
        happy_check = (CheckBox)rootView.findViewById(R.id.happy_check);
        toJudgePage = (Button)rootView.findViewById(R.id.toJudgePage);
        tableOkButton = (Button)rootView.findViewById(R.id.tableOkButton);
        tableMsg = (TextView)rootView.findViewById(R.id.tableMsg);
        table_title = (TextView)rootView.findViewById(R.id.table_title);
        prime_table = (TableLayout)rootView.findViewById(R.id.prime_table);
        prime = true;
        happy = false;
        prime_check.setChecked(prime);
        happy_check.setChecked(happy);


        // Inflate the layout for this fragment
        return rootView;
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 素数判定表へ遷移
        this.toJudgePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dddd", "dddd");
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        // checkbox
        this.prime_check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                prime = !prime;
                prime_check.setChecked(prime);
            }
        });

        this.happy_check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                happy = !happy;
                happy_check.setChecked(happy);
            }
        });

        // 素数二次元表生成
        tableOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int min_num = Integer.parseInt(min_num_edit.getText().toString());
                    int max_num = Integer.parseInt(max_num_edit.getText().toString());
                    if(min_num > max_num){
                        throw new NumSmallError();
                    }

                    if(!prime && !happy){
                        throw new PrimeCheckedError();
                    }
                    tableMsg.setText("");
                    tableMsg.setTextColor(Color.BLACK);
                    // 素数配列獲得
                    List<Integer> primenum_list = new ArrayList<Integer>(1);

                    if(prime && happy){
                        table_title.setText("ハッピー素数表");
                    }else if (prime){
                        table_title.setText("素数表");
                    }
                    else if (happy){
                        table_title.setText("ハッピー表");
                    }
                    make_table(primenum_list, max_num, min_num);
                }
                catch(NumberFormatException ex) {
                    tableMsg.setText("数字ではない");
                    tableMsg.setTextColor(Color.RED);
                }catch (NumSmallError nse){
                    tableMsg.setText("受けた最小数は最大数より大きい");
                    tableMsg.setTextColor(Color.RED);
                }
                catch (PrimeCheckedError pce){
                    tableMsg.setText("ハッピーと素数必ず一個をチェックを付く");
                    tableMsg.setTextColor(Color.RED);
                }


            }
        });
    }
    public void make_table(List<Integer>primenum_list, int max_num, int min_num){
        prime_table.removeAllViews();  // layout

        String row[] = new String[]{"head",
                "0",
                "1", "2",
                "3", "4",
                "5", "6",
                "7", "8",
                "9"
        };

        addRow(row);
        primenum_list = PrimeNum.get_primenum_list(max_num, min_num, happy, prime);


        Integer head = 0;
        Integer count_all = 0;

        while(count_all < primenum_list.size()){
            row[0] = ""; row[1] = ""; row[2] = ""; row[3] = ""; row[4] = "";
            row[5] = ""; row[6] = ""; row[7] = ""; row[8] = ""; row[9] = "";
            row[10] = "";
            if(head < (int)(Math.floor((double)min_num / 10.0))){
                head = (int)(Math.floor((double)min_num / 10.0));
            }
            row[0] = head.toString();
            while(count_all < primenum_list.size()){
                Integer prime_item = primenum_list.get(count_all);
                int next_head = (int)(Math.floor((double)prime_item / 10.0));
                if(next_head == head){
                    int count = prime_item % 10;
                    row[count + 1] = prime_item.toString();
                    Log.e("===", row[count + 1]);
                }
                else{
                    head = next_head;
                    break;
                }
                count_all++;

            }



            addRow(row);
            // add to row

        }


    }
    public void addRow(String[] row){
        TableRow prime_table_row = new TableRow(getContext());
//        prime_table_row.setBackgroundResource(R.drawable.table_border);
        TextView tv;
        for(String item: row){
            tv = new TextView(getContext());
            tv.setText(item);
            tv.setWidth(200);
            prime_table_row.addView(tv);
        }
        prime_table.addView(prime_table_row);
    }

}
