package com.example.timesince;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSinceActivity extends AppCompatActivity {
    private TextView input1;
    private EditText input2;
    private EditText input3;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = (TextView) findViewById(R.id.et_input1);
        input2 = (EditText) findViewById(R.id.et_input2);
        input3 = (EditText) findViewById(R.id.et_input3);
    Button bt_calculate = (Button) findViewById(R.id.bt_calculate);

    tv_result = (TextView) findViewById(R.id.tv_result);

        bt_calculate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            makeCalculations();
        }
    });
}

    private void makeCalculations() {
        double n2 = Double.valueOf(input2.getText().toString());
        double n3 = Double.valueOf(input3.getText().toString());

        // Do your calculation here.
        // I'm assuming you have inserted the result on a variable called 'result'. Like: double result
        double result = n2-n3;
        tv_result.setText("The result is: " + result);
    }



    private void setTodaysDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String out;
        out = "Produced" + (formatter).format(date);
        input1.setText("Clean since: "+ out);
//        System.out.println(formatter.format(date));
    }

}