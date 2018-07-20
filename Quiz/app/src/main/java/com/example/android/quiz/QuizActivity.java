package com.example.android.quiz;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends Activity {

    int score = 0;
    int point = 10;
    int answeredRight = 0;
    String getbFrank = "";
    String correct = "Correct! Next Question!";
    String wrong =  "Try again!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


    }

    /**
     * displays the score on the screen.
     */
    private void display(int number) {
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("" + number);
    }


    //increases the score when clicked
    public void correctTF(View view) {
        display(score = score + point);
        answeredRight ++;
        Toast.makeText(this, correct, Toast.LENGTH_SHORT).show();
        //disable correct button after clicking
        Button correct = (Button) findViewById(R.id.trueButton);
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                ;
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.A:
                if (checked)

                    Toast.makeText(this, wrong, Toast.LENGTH_SHORT).show();
                    break;
            case R.id.B:
                if (checked)
                    display(score = score + point);
                answeredRight ++;
                //disable radio buttons
                RadioButton barona = (RadioButton) findViewById(R.id.A);
                barona.setEnabled(false);
                RadioButton baronb = (RadioButton) findViewById(R.id.B);
                baronb.setEnabled(false);
                RadioButton baronc = (RadioButton) findViewById(R.id.C);
                baronc.setEnabled(false);
                RadioButton barond = (RadioButton) findViewById(R.id.D);
                barond.setEnabled(false);
                Toast.makeText(this, correct, Toast.LENGTH_SHORT).show();

                    break;
            case R.id.C:
                if (checked)
                Toast.makeText(this, wrong, Toast.LENGTH_SHORT).show();
                    break;
            case R.id.D:
                if (checked)
                Toast.makeText(this, wrong, Toast.LENGTH_SHORT).show();
                    break;
        }



    }

    //Shows incorrect when clicked
    public void incorrect(View view) {
        Toast.makeText(this, wrong, Toast.LENGTH_SHORT).show();
    }

//make string of checked countries & answer countries

    String choiceList = "";

//see if italy is checked
    public void itChecked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.it:
                if (checked) {
                    choiceList = choiceList + " it";
                    if (!checked){
                        choiceList = choiceList.replace(" it", "");
                    }
                }
                else
                break;
        }
    }

    public void cyChecked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.cy:
                if (checked) {
                    choiceList = choiceList + " cy";
                    if (!checked){
                        choiceList = choiceList.replace(" cy", "");
                    }
                }
                else
                    break;
        }
    }

    public void ltChecked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.lt:
                if (checked) {
                    choiceList = choiceList + " lt";
                    if (!checked){
                        choiceList = choiceList.replace(" lt", "");
                    }
                }
                else
                    break;
        }
    }

    public void lxChecked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.lx:
                if (checked) {
                    choiceList = choiceList + " lx";
                    if (!checked){
                        choiceList = choiceList.replace(" lx", "");
                    }
                }
                else
                    break;
        }
    }


    public void blChecked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.bl:
                if (checked) {
                    choiceList = choiceList + " bl";
                    if (!checked){
                        choiceList = choiceList.replace(" bl", "");
                    }
                }
                else
                    break;
        }
    }


    public void icChecked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.ic:
                if (checked) {
                    choiceList = choiceList + " ic";
                    if (!checked){
                        choiceList = choiceList.replace(" ic", "");
                    }
                }
                else
                    break;
        }
    }


    public void swChecked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.sw:
                if (checked) {
                    choiceList = choiceList + " sw";
                    if (!checked){
                        choiceList = choiceList.replace(" sw", "");
                    }
                }
                else
                    break;
        }
    }

    public void check(View view) {
        if  (choiceList.contains("it") && choiceList.contains("cy") && choiceList.contains("lt") && choiceList.contains("lx") ){
            display(score = score + point);
            answeredRight ++;
            Toast.makeText(this, correct, Toast.LENGTH_SHORT).show();
            Button submitEU = (Button) findViewById(R.id.submitEU);
            submitEU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setEnabled(false);
                    ;
                }
            });
        }
        else {

            Toast.makeText(this, wrong, Toast.LENGTH_SHORT).show();
        }


    }

    public void benCheck(View view) {
        EditText dec = (EditText) findViewById(R.id.decAnswer);
        getbFrank = dec.getText().toString();
        if (getbFrank.toLowerCase().contains("ben franklin") || (getbFrank.toLowerCase().contains("franklin")) || (getbFrank.toLowerCase().contains("benjamin franklin"))) {
            display(score = score + point);
            answeredRight ++;
            Toast.makeText(this, "Correct! Great job!", Toast.LENGTH_SHORT).show();
            Button submitDec = (Button) findViewById(R.id.submitDec);
            submitDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setEnabled(false);
                    ;
                }
            });
        }
        else {
            getbFrank = "";
            Toast.makeText(this, wrong, Toast.LENGTH_SHORT).show();
        }
    }

    public void skip(View view) {
            Toast.makeText(this, "Finished! Your score is " + score, Toast.LENGTH_SHORT).show();

        }



}
