package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button Calculate;
    Button Reset;
    TextView Date;
    TextView histBMI;
    TextView Result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etHeight=findViewById(R.id.Height);
        etWeight=findViewById(R.id.Weight);
        Calculate=findViewById(R.id.buttonCal);
        Reset=findViewById(R.id.buttonRST);
        Date=findViewById(R.id.date);
        histBMI=findViewById(R.id.lastBMI);
        Result=findViewById(R.id.textViewResult);


        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float w= Float.parseFloat(etWeight.getText().toString());
                Float h=Float.parseFloat(etHeight.getText().toString());
                Float cal = w/(h*h);
                histBMI.setText(cal+"");
                if (cal<18.5){
                    Result.setText("You are underweight");
                }else if(cal>=18.5 && cal <25){
                    Result.setText("Your BMI is normal");
                }else if (cal>24.9 && cal<30){
                    Result.setText("You are overweight");
                }else{
                    Result.setText("You are obese");
                }


                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                Date.setText(datetime);

            }
        });
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText("");
                etWeight.setText("");
                histBMI.setText("");
                Date.setText("");
                Result.setText("");
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        String bmi=histBMI.getText().toString();
        String date=Date.getText().toString();
        String res=Result.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit= prefs.edit();
        prefEdit.putString("bmi",bmi);
        prefEdit.putString("date",date);
        prefEdit.putString("res",res);
        prefEdit.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String bmi=prefs.getString("bmi","");
        String date=prefs.getString("date","");
        String result=prefs.getString("res","");
        histBMI.setText(bmi);
        Date.setText(date);
        Result.setText(result);
    }
}
