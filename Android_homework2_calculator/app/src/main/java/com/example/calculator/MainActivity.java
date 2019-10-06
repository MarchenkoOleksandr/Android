package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView history, curNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        history = findViewById(R.id.history);
        curNumber = findViewById(R.id.curNumber);
    }

    public void onClearAllClick(View view) {
        history.setText(null);
        curNumber.setText(null);
    }

    public void onClearClick(View view) {
        curNumber.setText(null);
    }

    public void onDeleteClick(View view) {
        if (!curNumber.getText().toString().isEmpty()) {
            curNumber.setText(curNumber.getText().subSequence(0, curNumber.getText().length() - 1));
        }
    }

    public void onPointClick(View view) {
        if (!curNumber.getText().toString().isEmpty() && !curNumber.getText().toString().contains(".")) {
            curNumber.setText(curNumber.getText() + ".");
        }
    }

    public void onNumberClick(View view) {
        Button button = (Button)view;

        if (curNumber.getText().length() == 1 && curNumber.getText().toString().startsWith("0")) {
            curNumber.setText(button.getText().toString() != "0" ? button.getText().toString() : "0");
        } else {
            curNumber.setText(curNumber.getText() + button.getText().toString());
        }
    }

    public void onSignClick(View view) {
        Button button = (Button)view;

        if (!curNumber.getText().toString().isEmpty()) {
            history.setText(history.getText().toString() + curNumber.getText().toString() + " " + button.getText().toString() + " ");
        } else if (!history.getText().toString().isEmpty()) {
            history.setText(history.getText().toString().substring(0, history.getText().length() - 2) + " " + button.getText().toString() + " ");
        }
        curNumber.setText(null);
    }

    public void onEqualSignClick(View view) {
        if (curNumber.getText().length() > 0) {
            history.setText(history.getText().toString() + curNumber.getText().toString());
        } else {
            return;
        }

        curNumber.setText(Counting());
        history.setText(null);
    }

    // подсчёт введённого выражения, в том числе из многих параметров
    private String Counting() {
        String expression = history.getText().toString().replace(" ", "");

        // если первое число отрицательное - добавляем 0 для предотвращения ошибки парсинга строки
        if (expression.startsWith("-"))
            expression = "0" + expression;

        // создаём и заполняем массив арифметических знаков
        ArrayList<String> symbols = new ArrayList<>();

        for (char s: expression.toCharArray()) {
            if (s == '+' || s == '-' || s == '*' || s == '/')
                symbols.add(String.valueOf(s));
        }

        // создаём и заполняем массив для хранения чисел
        ArrayList<String> strNumbers = new ArrayList<>(Arrays.asList(expression.split("[+\\-\\*\\//]")));
        ArrayList<Double> numbers = new ArrayList<>();

        // парсим массив строк в массив чисел
        try {
            for (String item: strNumbers) {
                numbers.add(Double.parseDouble(item));
            }

            // в первую очередь умножаем и делим
            for (int i = 0; i < symbols.size(); ) {
                switch (symbols.get(i)) {
                    case "*":
                        numbers.set(i, numbers.get(i) * numbers.get(i + 1));
                        numbers.remove(i + 1);
                        symbols.remove(i);
                        break;
                    case "/":
                        numbers.set(i, numbers.get(i) / numbers.get(i + 1));
                        numbers.remove(i + 1);
                        symbols.remove(i);
                        break;
                    default:
                        i++;
                        break;
                }
            }

            // если нужно ещё прибавить или отнять - делаем это
            if (symbols.contains("+") || symbols.contains("-")) {
                Double result = numbers.get(0);

                for (int i = 0; i < symbols.size(); i++) {
                    switch (symbols.get(i)) {
                        case "+":
                            result += numbers.get(i + 1);
                            break;
                        case "-":
                            result -= numbers.get(i + 1);
                            break;
                    }
                }
                return deleteEndZero(result);
            } else {
                return deleteEndZero(numbers.get(0));
            }
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
        }

        return "";
    }

    private String deleteEndZero(Double number) {
        String result = number.toString();
        return result.endsWith(".0") ? result.substring(0, result.length() - 2) : result;
    }
}
