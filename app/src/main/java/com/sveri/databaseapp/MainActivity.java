package com.sveri.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.sveri.databaseapp.database.MyDatabase;

public class MainActivity extends AppCompatActivity {

    //declaration
    EditText etName, etAddr, etSal;
    Button btnInsert;
    ListView lvEmp;
    MyDatabase mdb = new MyDatabase(this);
    Cursor cursor;
    SimpleCursorAdapter sca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
        eventHandler();
        showData();
    }

    protected void initialise(){

        etName = findViewById(R.id.editTextName);
        etAddr = findViewById(R.id.editTextAddress);
        etSal = findViewById(R.id.editTextSalary);
        btnInsert = findViewById(R.id.buttonInsert);
        lvEmp = findViewById(R.id.listEmployees);
    }

    protected void eventHandler(){
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stName = etName.getText().toString().trim();
                String stAddr = etAddr.getText().toString().trim();
                Integer intSal = Integer.parseInt(etSal.getText().toString().trim());

                //field validation
                if (stAddr.isEmpty()){
                    etAddr.setError("Address cannot be empty");
                }else if (stName.isEmpty()){
                    etName.setError("Name cannot be empty");
                } else if (intSal == null || intSal.equals("")) {
                    etSal.setError("Salary cannot be empty");
                }else {

                    ContentValues cv = new ContentValues();
                    cv.put("name", stName);
                    cv.put("address",stAddr);
                    cv.put("salary",intSal);

                    //lets insert the data
                    mdb.insertEmp(cv);
                    Toast.makeText(MainActivity.this, "Data inserted Successfully",
                            Toast.LENGTH_SHORT).show();
                    //lets clear the fields for new entry
                    etName.setText(null);
                    etAddr.setText(null);
                    etSal.setText(null);

                    //lets requery for new entry
                    cursor.requery();
                }
            }
        });
    }

    public void showData(){
        cursor = mdb.getData();
        String[] fromDb = {"name","address","salary"};
        int[] toTextView = {R.id.textViewName, R.id.textViewAddress, R.id.textViewSalary};
        sca = new SimpleCursorAdapter(this,R.layout.row,cursor,fromDb,toTextView,1);
        lvEmp.setAdapter(sca);
        sca.notifyDataSetChanged();
        cursor.requery();
    }
}