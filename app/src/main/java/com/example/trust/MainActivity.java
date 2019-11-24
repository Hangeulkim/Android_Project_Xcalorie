package com.example.trust;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity {

    LinearLayout Select_More_Layout;
    RelativeLayout Select_Start_Layout;
    LinearLayout Start_First_Layout;

    Button Start_More;
    Button Start_Start;

    public void Click_Select_Log(View view){

    }

    public void Click_Select_Setting(View view){

    }

    public void Click_Select_Makers(View view){

    }

    public void Click_More_Quit(View view){
        Select_More_Layout.setVisibility(View.INVISIBLE);
        Start_First_Layout.setVisibility(View.VISIBLE);
    }


    public void Click_Start_More(View view){
        Start_First_Layout.setVisibility(View.INVISIBLE);
        Select_More_Layout.setVisibility(View.VISIBLE);
    }

    public void Click_Start_Start(View view){
        Select_Start_Layout.setVisibility(View.VISIBLE);
        Start_Start.setVisibility(View.GONE);
    }

    public void Click_Start_Timer(View view){

    }

    public void Click_Start_Select(View view){

    }

    public void Click_Start_Fast(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Select_More_Layout=(LinearLayout)findViewById(R.id.Select_More);
        Select_Start_Layout=(RelativeLayout)findViewById(R.id.Select_Start);
        Start_First_Layout=(LinearLayout)findViewById(R.id.Start_First);

        Start_More=(Button)findViewById(R.id.Start_More);
        Start_Start=(Button)findViewById(R.id.Start_Start);


    }
}
