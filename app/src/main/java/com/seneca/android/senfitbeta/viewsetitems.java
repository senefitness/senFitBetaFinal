package com.seneca.android.senfitbeta;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class viewsetitems extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Calender> list = new ArrayList<Calender>();
    private ArrayList<String> names = new ArrayList<String>();
    private DbHelper db;
    private itemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsetitems);

        db = new DbHelper(this);
        list = db.getSetE();
        if(list.isEmpty()){

        } else {
            lv = (ListView) findViewById(R.id.listView);
            adapter = new itemAdapter(viewsetitems.this,list);

            lv.setAdapter(adapter);
        }



    }




    public class itemAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Calender> cal;
        private int crush;

        //constructor to set up our instance variables
        public itemAdapter(Context c, ArrayList<Calender> cl){
            context = c;
            cal = cl;
        }


        @Override
        public int getCount() { // return number of elements
            return cal.size();
        }

        @Override
        public Object getItem(int position) { // return the obj at postion
            return cal.get(position);
        }

        @Override
        public long getItemId(int position) { //return id of given row
            return 0;
        }



        // take data from array and put it in customlayout.xml
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_items,null);

            final Calender calender = (Calender) getItem(position);
            String month_ = calender.getAllMonth(calender.getMonth()+1);
            crush = calender.getNum();

            TextView tv1 = (TextView) row.findViewById(R.id.textItems);
            tv1.setText(month_+"-"+calender.getDay());
            tv1.append("\n"+calender.getName());

            Button remover = (Button)row.findViewById(R.id.button);

            //ading the row.find... stop it from crashing
            remover.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    db.removeFromCal(crush);
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"Removed successfully",Toast.LENGTH_LONG).show();
                }
            });
            return row;
        }
    }
}