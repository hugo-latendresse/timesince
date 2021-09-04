package com.example.timesince;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.lang.String.valueOf;

public class TimeSinceActivity extends AppCompatActivity {
    private TextView input1;
    private EditText input2;
    private EditText input3;
    private TextView tv_result;
    private long millis_to_display;
    private String MILLIS_FILENAME = "millis_storage.txt";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);

        input1 = (TextView) findViewById(R.id.et_input1);
        input2 = (EditText) findViewById(R.id.et_input2);
        input3 = (EditText) findViewById(R.id.et_input3);
        Button bt_calculate = (Button) findViewById(R.id.bt_calculate);

        tv_result = (TextView) findViewById(R.id.tv_result);

        display_millis();





        bt_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!isFinishing()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TimeSinceActivity.this);
                            builder.setTitle("Your Alert");
                            builder.setMessage("Your Message");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    save_current_to_internal_storage(getApplicationContext());
                                    display_millis();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            builder.show();
                        }
                    }
                });




            }

        });
    }


    private long return_todays_milliseconds() {
        return System.currentTimeMillis();
    }

    private String convert_milli_to_date(long milli) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
        Date date = new Date(milli);
        return (formatter).format(date);
    }


    private void save_current_to_internal_storage(Context ctx) {
        String data = valueOf(return_todays_milliseconds());
        try {

            FileOutputStream fOut = openFileOutput(MILLIS_FILENAME, ctx.MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void set_millis_to_internal_storage() {
        try {
            FileInputStream fin = openFileInput(MILLIS_FILENAME);
            int c;
            String temp = "";

            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            millis_to_display = Long.parseLong(temp);
        } catch (Exception e) {
            millis_to_display = 0;
        }
    }

    private void display_millis() {
        set_millis_to_internal_storage();
        input1.setText("Clean since " + convert_milli_to_date(millis_to_display));
        tv_result.setText(convert_milli_to_date(millis_to_display));
        Toast.makeText(getBaseContext(), "date displayed", Toast.LENGTH_SHORT).show();
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