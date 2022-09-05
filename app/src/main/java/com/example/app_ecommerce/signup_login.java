package com.example.app_ecommerce;

import android.annotation.SuppressLint;
            import android.app.AlertDialog;
            import android.app.DatePickerDialog;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.os.Bundle;
            import android.preference.PreferenceManager;
import android.view.View;
            import android.view.animation.Animation;
            import android.view.animation.AnimationUtils;
            import android.widget.DatePicker;
            import android.widget.EditText;
            import android.widget.ImageView;
            import android.widget.Switch;
            import android.widget.TextView;
            import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
            import androidx.constraintlayout.widget.ConstraintLayout;
            import androidx.fragment.app.DialogFragment;
            import androidx.viewpager.widget.ViewPager;
            import com.example.app_ecommerce.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup_login<DatabaseRef> extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    Database db = new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


   public boolean emailValidator(String email)
   {
       Pattern pattern;
       Matcher matcher;
       final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
      /*final String EMAIL_PATTERN = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
               + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
               + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
               + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
               + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
               + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";*/
       pattern = Pattern.compile(EMAIL_PATTERN);
       matcher = pattern.matcher(email);
       return matcher.matches();
   }
    public void SignGo(View view) {
        EditText e_mail=(EditText)findViewById(R.id.signemail);
        String email = e_mail.getText().toString().trim();
        EditText pass1 =(EditText)findViewById(R.id.signpassword);
        EditText pass2 =(EditText)findViewById(R.id.signconfirmpassword);
        EditText date  =(EditText)findViewById(R.id.signBdate);
        boolean check=emailValidator(email);
        if( !check)
        {
            Toast.makeText(getApplicationContext() , "Email Invalid" , Toast.LENGTH_LONG).show();

        }
        if(e_mail.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext() , "Required Field" , Toast.LENGTH_LONG).show();
            }
        else if(pass1.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext() , "Required Field" , Toast.LENGTH_LONG).show();
            }
        else if(pass2.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext() , "Required Field" , Toast.LENGTH_LONG).show();
            }
        else if(date.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext() , "Required Field" , Toast.LENGTH_LONG).show();
            }
        else {
            String check1=pass1.getText().toString(),check2=pass2.getText().toString();
            if(!check1.equals(check2)){
                Toast.makeText(getApplicationContext() , "Passwords doesn't match", Toast.LENGTH_LONG).show();

                 pass2.setText("");
             }
             else {
                 String e=e_mail.getText().toString(),p=pass1.getText().toString(),d=date.getText().toString();
                 boolean bool=false;
                 Cursor cursor;
                 cursor=db.getAllUser();
                 Integer x=cursor.getCount();
                 do{
                     if(x<=0||cursor==null)break;
                     String na=cursor.getString(cursor.getColumnIndex("Name"));
                     if(na.equals(e)){
                         bool=true;
                         break;
                     }
                 }while (cursor.moveToNext());
                 if(bool) {
                     e_mail.setText("");
                     Toast.makeText(signup_login.this, "Email is found before", Toast.LENGTH_SHORT).show();
                 }
                 else {

                     db.add_User(e,p,d);
                     Toast.makeText(signup_login.this, "Sign up success", Toast.LENGTH_SHORT).show();
                     e_mail.setText("");
                     pass1.setText("");
                     pass2.setText("");
                     date.setText("");
                     Intent intent = new Intent(signup_login.this, signup_login.class);
                     startActivity(intent);
                     finish();
                 }
             }
            }
            }
    public void LogGo(View view) {
        EditText logmail=(EditText)findViewById(R.id.loginemail);
        EditText logpass=(EditText)findViewById(R.id.loginpassword);

        if(logmail.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext() , "Required Field" , Toast.LENGTH_LONG).show();

            }
        else if(logpass.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext() , "Required Field" , Toast.LENGTH_LONG).show();
            }
        else{
            Switch re=(Switch)findViewById(R.id.rememberme);
            boolean bool=false;
            Cursor cursor;
            Integer id = null;
            String Name_user=logmail.getText().toString();
            cursor=db.getAllUser();
                do{
                    if(cursor.getCount()<=0||cursor==null)break;
                    String na=cursor.getString(cursor.getColumnIndex("Name")),pa=cursor.getString(cursor.getColumnIndex("Password"));
                   String logm=logmail.getText().toString(),logb=logpass.getText().toString();
                    if(na.equals(logm)&&pa.equals(logb)){
                        bool=true;
                        id=cursor.getInt(cursor.getColumnIndex("userid"));
                        break;
                    }
                }while (cursor.moveToNext());
                if(bool){
                    Toast.makeText(signup_login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    if(re.isChecked()){
                        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putBoolean("remember",true);
                        editor.putInt("id",id);
                        editor.putString("Name",Name_user);
                        editor.apply();
                    }
                    else {
                        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putBoolean("remember",false);
                        editor.putInt("id",id);
                        editor.putString("Name",Name_user);
                        editor.apply();
                    }
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);
                    finish();
                logmail.setText("");

                }
                else {
                Toast.makeText(signup_login.this, "Password or Email not Valid", Toast.LENGTH_SHORT).show();
                     }
                logpass.setText("");
            }
    }
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentdatestring = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        EditText signBdate=(EditText)findViewById(R.id.signBdate);
        signBdate.setText(currentdatestring);
    }

    public void Calender(View view) {
        DialogFragment datapicker=new data();
        datapicker.show(getSupportFragmentManager(),"data picker");
    }



    @SuppressLint("ResourceAsColor")
    public void Forget(View view) {
        EditText resetmail=new EditText(view.getContext()),resetpass=new EditText(view.getContext()),resetdate=new EditText(view.getContext());
        resetmail.setHint("E-mail");
        resetmail.setHintTextColor(R.color.black);
        AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Do you wanna Reset Password ?");
        passwordResetDialog.setMessage("Enter Your Email To reset password ");
        passwordResetDialog.setView(resetmail);
        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor cursor;
                cursor=db.getAllUser();
                boolean bool=false;
                    do{
                        if(cursor.getCount()<=0||cursor==null)break;
                        String name=cursor.getString(cursor.getColumnIndex("Name"));
                        if(name.equals(resetmail.getText().toString())){
                            resetdate.setText(cursor.getString(cursor.getColumnIndex("birthdate")));
                            bool=true;
                            break;
                        }
                         }while (cursor.moveToNext());
                    if(bool){

                        resetpass.setHint("New Password");
                        resetpass.setHintTextColor(R.color.black);
                        AlertDialog.Builder passwordResetDialog1=new AlertDialog.Builder(view.getContext());
                        passwordResetDialog1.setTitle("Reset Password");
                        passwordResetDialog1.setMessage("Enter Your New password");
                        passwordResetDialog1.setView(resetpass);

                        passwordResetDialog1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               db.delete_User(resetmail.getText().toString());
                               db.add_User(resetmail.getText().toString(),resetpass.getText().toString(),resetdate.getText().toString());
                                Toast.makeText(signup_login.this, "Rest Password Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                        passwordResetDialog1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        passwordResetDialog1.create().show();
                        }
                    else {
                        Toast.makeText(signup_login.this, "Email Not Valid", Toast.LENGTH_SHORT).show();
                         }
            }

            });
            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            passwordResetDialog.create().show();
            }

            }