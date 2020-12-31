package com.troy.safer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Contactt extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactt);




        button = (Button) findViewById(R.id.send);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(button);
                Toast.makeText(Contactt.this, "choose", Toast.LENGTH_LONG).show();//display the text of button1
            }
        });
    }
    public void back(View view) {
        Intent intent = new Intent (Contactt.this, MainActivity.class);
        startActivity(intent);
    }
    public void sendEmail(View button){
        final EditText formName=(EditText) findViewById(R.id.formName);
        String clientName=formName.getText().toString();
        final EditText formEmail=(EditText) findViewById(R.id.formEmail);
        String clientEmail=formEmail.getText().toString();
        final EditText formDetails=(EditText)  findViewById(R.id.formDetails);
        String clientDetails=formDetails.getText().toString();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL,new String[]{"koracorner1@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "New Private Service Request");
        i.putExtra(Intent.EXTRA_TEXT, "TODO: compose message body");
        try {
            startActivity(Intent.createChooser(i, "Send email with...?"));
        } catch (android.content.ActivityNotFoundException exception) {
            Toast.makeText(Contactt.this, "No email clients installed on device!", Toast.LENGTH_LONG).show();
        }




    }
}
