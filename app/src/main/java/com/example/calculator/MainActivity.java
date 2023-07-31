package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private double firstVar = 0, secondVar = 0;
    private boolean isOperationClick;
    private String operation;
    private Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        Next = findViewById(R.id.btn_next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                String text =textView.getText().toString();
                intent.putExtra("key1", text);
                startActivity(intent);
            }
        });
    }

    public void onNumberClick(View view) {
        String number = ((Button) view).getText().toString();
        setTv_Result(number);
    }

    public void setTv_Result(String number) {
        if (textView.getText().toString().equals("0") || isOperationClick) {
            textView.setText(number);
        } else {
            textView.append(number);
        }
        isOperationClick = false;
    }

    public void onOperationClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_clear) {
            textView.setText("0");
            firstVar = 0.0;
            secondVar = 0.0;
        } else if (viewId == R.id.btn_dot) {
            if (!textView.getText().toString().contains(".")) {
                textView.append(".");
            }
        } else if (viewId == R.id.btn_percent) {
            firstVar = Double.parseDouble(textView.getText().toString());
            Double result = firstVar / 100;
            textView.setText(new DecimalFormat("##.#######").format(result));
        } else if (viewId == R.id.btn_multiplay) {
            firstVar = Double.parseDouble(textView.getText().toString());
            firstVar *= -1;
            textView.setText(new DecimalFormat("##.#######").format(firstVar));
        } else if (viewId == R.id.btn_plus) {
            performOperation("+");
        } else if (viewId == R.id.btn_minus) {
            performOperation("-");
        } else if (viewId == R.id.btn_multiplication) {
            performOperation("X");
        } else if (viewId == R.id.btn_slash) {
            performOperation("/");
        } else if (viewId == R.id.btn_equals) {
            calculateResult();
        }

        if (view.getId() == R.id.btn_equals) {
            findViewById(R.id.btn_next).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.btn_next).setVisibility(View.GONE);
        }
    }


    private void performOperation(String op) {
        firstVar = Double.parseDouble(textView.getText().toString());
        operation = op;
        isOperationClick = true;
    }

    private void calculateResult() {
        if (!isOperationClick) {
            secondVar = Double.parseDouble(textView.getText().toString());
            double result = 0;
            switch (operation) {
                case "+":
                    result = firstVar + secondVar;
                    break;
                case "-":
                    result = firstVar - secondVar;
                    break;
                case "X":
                    result = firstVar * secondVar;
                    break;
                case "/":
                    result = firstVar / secondVar;
                    break;
            }
            textView.setText(new DecimalFormat("##.#######").format(result));
        }
    }
}
