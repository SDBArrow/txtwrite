package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "example.txt";
    final String[] lunch = {"魯肉飯", "控肉飯", "雞排飯", "炸醬麵", "水餃"};
    EditText mEditText;
    Spinner mspinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToast("ActivityonCreate");
        setContentView(R.layout.activity_main);
        mspinner = findViewById(R.id.spinner);
        mEditText = findViewById(R.id.edit_text);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);spinner.setAdapter(lunchList);
    }
    public void save(View v) {
        String text = mEditText.getText().toString();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);//Context.MODE_PRIVATE：為默認操作模式，代表該文件是私有數據，只能被應用本身訪問，在該模式下，寫入的內容會覆蓋原文件的內容，如果想把新寫入的內容追加到原文件中。可以使用Context.MODE_APPEND
            int spinnerget = mspinner.getSelectedItemPosition();
            String iuiu = lunch[spinnerget];
            fos.write(iuiu.getBytes());

            mEditText.getText().clear(); //清除test文字
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();  //在Android源碼中的NotificationManagerService.java這個類中定義了兩個靜態變量，分別對應Toast.LENGTH_LONG（3.5秒）和Toast.LENGTH_SHORT（2秒）的值
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            String test = sb.toString();
            String[] tempor = test.split(",");
            mEditText.setText(tempor[0]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void opengoogle(View v) {
        Uri uri = Uri.parse("https://www.google.com");
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}