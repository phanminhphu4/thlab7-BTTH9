package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.BindException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler db;
    Button btn_add, btn_remove, bnt_cancel;
    EditText namedit_tx;
    ListView listView;
    ArrayList nameList;
    ArrayAdapter adapter;
    ArrayList idList;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         db = new DatabaseHandler(this, "usedb.sqlite",null,1);
        namedit_tx=findViewById(R.id.editTextTextPersonName) ;
         btn_add = findViewById(R.id.button5);
        btn_remove = findViewById(R.id.button4);
        bnt_cancel= findViewById(R.id.button3);
        listView = findViewById(R.id.list);
        idList = new ArrayList();
        nameList = new ArrayList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);
         getNameList();
        listView.setAdapter(adapter);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addUser(new User(namedit_tx.getText().toString()));
                 getNameList();
                 namedit_tx.setText(null);
                adapter.notifyDataSetChanged();
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              db.deleteUser((int) idList.get(index));
              getNameList();
              adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Succesful", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }
        });

    /**    // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addUser(new User(1, "????? Anh B??n"));
        db.addUser(new User(2, "Ho??ng Qu???c C?????ng"));
        db.addUser(new User(3, "Ph???m Minh D??ng"));
        db.addUser(new User(4, "Ch??u Ho??ng Duy"));



        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<User> contacts = db.getAllUser();

        for (User cn : contacts) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getName() ;
            // Writing Contacts to log
            Log.d("Name: ", log);
        }**/
    }
    private ArrayList getNameList(){
        nameList.clear();
        idList.clear();
        for (Iterator iterator =db.getAllUser().iterator(); iterator.hasNext();){
            User user = (User) iterator.next();
            nameList.add(user.getName());
            idList.add(user.getId());

        }
        return nameList;
    }
}