package com.example.timesince;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class TimeSinceActivity extends AppCompatActivity {
    private TextView input1;
    private EditText input2;
    private EditText input3;
    private TextView tv_result;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, 1);

        input1 = (TextView) findViewById(R.id.et_input1);
        input2 = (EditText) findViewById(R.id.et_input2);
        input3 = (EditText) findViewById(R.id.et_input3);
        Button bt_calculate = (Button) findViewById(R.id.bt_calculate);

        tv_result = (TextView) findViewById(R.id.tv_result);

        setTodaysDate();

        bt_calculate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            makeCalculations();
            save_to_internal_storage(getApplicationContext());
            read_from_internal_storage();
        }

    });
}

    private void makeCalculations() {
        double n2 = Double.valueOf(input2.getText().toString());
        double n3 = Double.valueOf(input3.getText().toString());
        double result = n2-n3;
        tv_result.setText("The result is: " + result);
    }

    private long return_todays_milliseconds(){
        return System.currentTimeMillis();
    }

    private String convert_milli_to_date(long milli){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
        Date date = new Date(milli);
        return (formatter).format(date);
    }

    private void setTodaysDate() {
        input1.setText("Clean since: "+ convert_milli_to_date(return_todays_milliseconds()));
    }






//    private void resetDate(){
//        homeScoreBytes[0] = (byte) homeScore;
//        homeScoreBytes[1] = (byte) (homeScore >> 8);  //you can probably skip these two
//        homeScoreBytes[2] = (byte) (homeScore >> 16); //lines, because I've never seen a
//        //basketball score above 128, it's
//        //such a rare occurance.
//
//        writeFileOnInternalStorage();
//
//        FileOutputStream outputStream = getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
//        outputStream.write(homeScoreBytes);
//        outputStream.close();
//    }


    private void save_to_internal_storage(Context ctx){
        String data="my info to save";
        try {

            FileOutputStream fOut = openFileOutput("somefile.txt", ctx.MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void read_from_internal_storage(){
        try {
            FileInputStream fin = openFileInput("somefile.txt");
            int c;
            String temp="";

            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            tv_result.setText(temp);
            Toast.makeText(getBaseContext(), "file read", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
        }
    }

//    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
//        File dir = new File(mcoContext.getFilesDir(), "mydir");
//        if(!dir.exists()){
//            dir.mkdir();
//        }
//
//        try {
//            File gpxfile = new File(dir, sFileName);
//            FileWriter writer = new FileWriter(gpxfile);
//            writer.append(sBody);
//            writer.flush();
//            writer.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}