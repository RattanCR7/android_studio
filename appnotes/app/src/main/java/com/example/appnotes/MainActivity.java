package com.example.appnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SharedPreferences sharedPreferences;
    static ArrayAdapter arrayAdapter;
    static ArrayList<String> notes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences= getApplicationContext().getSharedPreferences("com.example.appnotes", Context.MODE_PRIVATE);
        listView=findViewById(R.id.noteslist);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes.add("Example note");
        }else{
            notes=new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),secondActivity.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int itemtodelete=position;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_input_delete)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(itemtodelete);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.addnotes:
                Intent intent=new Intent(getApplicationContext(),secondActivity.class);
                startActivity(intent);
//                createNotes("notes....");
                return true;
            default:
                return false;
        }
    }
}
