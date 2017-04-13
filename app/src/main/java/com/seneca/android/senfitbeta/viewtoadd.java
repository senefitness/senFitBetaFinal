package com.seneca.android.senfitbeta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class viewtoadd extends AppCompatActivity {

    private TextView t1;
    private ImageView imageView;
    private Button button;
    private String s1;
    private int s3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtoadd);

        Bundle bud = getIntent().getExtras();
        s1 = bud.getString("_name");
        String s2 = bud.getString("_des");
        s3 = bud.getInt("_id");
        String s4 = bud.getString("_liA");
        String s5 = bud.getString("n_OG");
        String s6 = bud.getString("cdate");
        String s7 = bud.getString("cat5");
        int pos = bud.getInt("imageId");




        imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(pos);
        button = (Button)findViewById(R.id.btnAdd);

        t1 = (TextView)findViewById(R.id.textViewArt);
        t1.setPaintFlags(t1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //t1.setTextColor(Color.BLUE);
        t1.setText("NAME: "+s1);  //name
        t1.append("\nORIGINAL NAME: "+s5);//original name
        t1.append("\nID: "+s3); //id
        t1.append("\nAUTHOR: "+s4); //author
        t1.append("\nCREATION DATE: "+s6); //creation date
        t1.append("\nCATEGORY: "+s7); //category
        t1.append("\n\nDESCRIPTION: "+s2); //descriptionOn

        ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),calendat_.class);
                intent.putExtra("calExerName",s1);
                intent.putExtra("calExerId",s3);
                startActivity(intent);
            }
        });


    }
}
