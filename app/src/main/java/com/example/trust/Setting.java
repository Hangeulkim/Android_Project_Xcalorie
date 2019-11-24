package com.example.trust;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Setting extends AppCompatActivity {

    Button Bike;

    public void Click_Run_Bike(View view){
        int toggle=1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Bike=(Button)findViewById(R.id.Bike);

    }
}
