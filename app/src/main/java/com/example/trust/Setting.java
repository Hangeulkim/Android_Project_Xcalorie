package com.example.trust;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Setting extends AppCompatActivity {

    ImageButton Bike;

    public void Click_Run_Bike(View view){
        int toggle=1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Bike=(ImageButton)findViewById(R.id.Bike);

    }
}
