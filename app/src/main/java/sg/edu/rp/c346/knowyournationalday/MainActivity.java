package sg.edu.rp.c346.knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayList.add("Singapore National Dat is on 9 Aug");
        arrayList.add("Singapore is 53 years old");
        arrayList.add("Theme is 'we are Singapore'");
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout passPhrase =
                (LinearLayout) inflater.inflate(R.layout.access_code, null);
        final EditText etPassphrase = (EditText) passPhrase
                .findViewById(R.id.editText);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean login = prefs.getBoolean("login", false);
        if (login == false) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please login")
                    .setView(passPhrase)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            String code = etPassphrase.getText().toString().trim();
                            if (!code.equals("730840")) {
                                finish();
                            } else {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                SharedPreferences.Editor preEdit = prefs.edit();
                                preEdit.putBoolean("login", true);
                            }
                        }
                    }).setNegativeButton("NO ACCESS CODE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.sendToFriends) {

            String [] list = new String[] { "EMAIL", "SMS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Which is your freest weekday?")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent email = new Intent(Intent.ACTION_SEND);
// Put essentials like email address, subject & body text
                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"jason_lim@rp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "Test Email from C347");
                                email.putExtra(Intent.EXTRA_TEXT,
                                        arrayList.get(0)+arrayList.get(1)+arrayList.get(2));
// This MIME type indicates email
                                email.setType("message/rfc822");
// createChooser shows user a list of app that can handle
// this MIME type, which is, email
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));
                            }else {
                                int number = 84397820;
                                String context = "this is the context";
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+number));
                                intent.putExtra("sms_body", context);
                                startActivityForResult(intent, 456);
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.quiz) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout quiz =
                    (LinearLayout) inflater.inflate(R.layout.quiz, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please login")
                    .setView(quiz)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            boolean q1 = false;
                            boolean q2 = false;
                            boolean q3 = false;
                            final RadioButton rb1 = quiz.findViewById(R.id.radioButton2);
                            if(rb1.isChecked()){q1 = true;}
                            final RadioButton rb2 = quiz.findViewById(R.id.radioButton3);
                            if(rb2.isChecked()){q2 = true;}
                            final RadioButton rb3 = quiz.findViewById(R.id.radioButton5);
                            if(rb3.isChecked()){q3 = true;}
                            if(q1 == true && q2 == true && q3 == true){
                                Toast.makeText(getBaseContext(),"You win!",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getBaseContext(),"You lose!",Toast.LENGTH_LONG).show();

                            }

                        }
                    }).setNegativeButton("DONT KNOW LAH", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (item.getItemId() == R.id.quit){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    .setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).setNegativeButton("NOT REALLY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

}
