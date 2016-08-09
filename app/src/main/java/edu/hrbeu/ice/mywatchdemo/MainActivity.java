package edu.hrbeu.ice.mywatchdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
int count=0;
    private MyWatch wat;
    private MySmallWatch msw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btn);
        wat = (MyWatch)findViewById(R.id.wat);
        msw = (MySmallWatch) findViewById(R.id.msw);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count+=10;
                wat.setSpeed(count);
                msw.setVal(count);
            }
        });
    }
}
