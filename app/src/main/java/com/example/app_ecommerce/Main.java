package com.example.app_ecommerce;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.app_ecommerce.adapter.productAdapter;
import com.example.app_ecommerce.model.Products;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main extends AppCompatActivity {
    Animation top, buttom;
    RecyclerView productItemrecycler;
    productAdapter productAdapter;
    Database db = new Database(this);
    ConstraintLayout conOrder;
    TextView phone, watch, lab, ord;
    int cord = 0;
    public static EditText searchtxt;
    public static TextView locationtxt, totalprice;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        buttom = AnimationUtils.loadAnimation(this, R.anim.butom_animation);

        ConstraintLayout tabChange = (ConstraintLayout) findViewById(R.id.tabchange);
        tabChange.setAnimation(top);
        ConstraintLayout conSearch = (ConstraintLayout) findViewById(R.id.constsearch);
        conSearch.setVisibility(View.VISIBLE);
        conSearch.setAnimation(top);

        String Email = "";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Email = preferences.getString("Name", "");
        TextView prof = (TextView) findViewById(R.id.textSir);
        prof.setText("Welcome " + Email + "");
        prof.setAnimation(top);

        locationtxt = (TextView) findViewById(R.id.textUserLocation);
        totalprice = (TextView) findViewById(R.id.textTotalPrice);
        searchtxt = (EditText) findViewById(R.id.editTextSearch);
        conOrder = (ConstraintLayout) findViewById(R.id.confirmorder);
        conOrder.setVisibility(View.INVISIBLE);
        phone = (TextView) findViewById(R.id.txtPhone);
        watch = (TextView) findViewById(R.id.txtWatch);
        lab = (TextView) findViewById(R.id.txtLabs);

        RecyclerView ProdRecycle = (RecyclerView) findViewById(R.id.product_recycler);
        DataSet();
        phone_data();
        ProdRecycle.setAnimation(buttom);

        phone.setTextColor(R.color.teal_700);
        lab.setTextColor(R.color.black);
        watch.setTextColor(R.color.black);

        phone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                ProdRecycle.removeAllViews();
                phone_data();
                phone.setTextColor(R.color.teal_700);
                lab.setTextColor(R.color.black);
                watch.setTextColor(R.color.black);
            }
        });
        lab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                ProdRecycle.removeAllViews();
                lab_data();
                lab.setTextColor(R.color.teal_700);
                watch.setTextColor(R.color.black);
                phone.setTextColor(R.color.black);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                ProdRecycle.removeAllViews();
                watch_data();
                watch.setTextColor(R.color.teal_700);
                lab.setTextColor(R.color.black);
                phone.setTextColor(R.color.black);
            }
        });

    }

    private void setproductItemrecycle(List<Products> productsList) {
        productItemrecycler = findViewById(R.id.product_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productItemrecycler.setLayoutManager(layoutManager);
        productAdapter = new productAdapter(this, productsList);
        productItemrecycler.setAdapter(productAdapter);
    }

    public void DataSet() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!preferences.getBoolean("r", false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("r", true);
            editor.apply();
            db.add_category("Phone", 1);
            db.add_category("Lab", 2);
            db.add_category("Watch", 3);

            db.add_Item("iPhone 13 Pro Max", "iPhone 13 Pro Max with FaceTime - 128GB, 6GB RAM, 4G LTE, Sierra Blue, Single SIM & E-SIM", "Apple", 28999, 1, R.drawable.phone1);
            db.add_Item("Xiaomi Mi 11 Ultra", "Xiaomi Mi 11 Ultra Dual Sim, 12GB Ram, 256GB, Ceramic White", "Xiaomi", 18998, 1, R.drawable.phone2);
            db.add_Item("Xiaomi POCO X3 Pro", "Xiaomi POCO X3 Pro Dual SIM Mobile - 6.67 Inches, 256 GB, 8 GB RAM, 4G LTE - Metal Bronze ", "Xiaomi", 5475, 1, R.drawable.phone3);
            db.add_Item("OPPO Reno 4", "OPPO Reno 4 Dual SIM - 128GB 8GB RAM 4G LTE - Metallic Black - Mo Salah Edition", "OPPO", 6999, 1, R.drawable.phone4);

            db.add_Item("HP Pavilion 15", "HP Pavilion 15-ec1010nia Gaming Laptop - Ryzen 7 4800H 8-Cores, 16 GB RAM , NVIDIA GeForce GTX 1650 Ti, 15.6 FHD", "HP", 20199, 2, R.drawable.lab1);
            db.add_Item("Lenovo-Legion 5", "Lenovo-Legion 5 - Intel Core I7-10750H,  16GB RAM , NVIDIA GeForce GTX1660TI 6GB - 15.6", "Lenovo", 22495, 2, R.drawable.lab2);
            db.add_Item("MSI GS65 Stealth 9SF", "MSI GS65 Stealth 9SF Gaming Laptop - Intel Core i7, 15.6 Inch FHD, 16 GB RAM, Nvidia Ge-Force RTX 2070", "MSI", 55550, 2, R.drawable.lab3);

            db.add_Item("HUAWEI Band 6", "HUAWEI Band 6 Fitness Tracker Smartwatch, 1.47’’ AMOLED Color Screen,Heart Rate Monitoring,5ATM Waterproof,Pink", "HUAWEI", 937, 3, R.drawable.watch1);
            db.add_Item("Samsung Galaxy Watch4", "Samsung Galaxy Watch4 Classic 46mm Bluetooth Smartwatch, Silver", "Samsung", 5814, 3, R.drawable.watch2);
            db.add_Item("Xiaomi Mi Lite Waterproof", "Xiaomi Mi Lite Waterproof Smart Watch with Built-in GPS and Heart Rate Monitoring - Black ", "Xiaomi", 949, 3, R.drawable.watch3);
        }
    }


    public void phone_data() {

        RecyclerView ProdRecycle = (RecyclerView) findViewById(R.id.product_recycler);
        ProdRecycle.setVisibility(View.VISIBLE);
        ConstraintLayout conOrder = (ConstraintLayout) findViewById(R.id.confirmorder);
        conOrder.setVisibility(View.INVISIBLE);
        List<Products> productsList = new ArrayList<>();
        Cursor cursor = db.getAllItems();
        do {
            if (cursor.getCount() <= 0 || cursor == null) break;
            String name, Description, Brand;
            Integer url, item_id, price, catid;
            name = cursor.getString(cursor.getColumnIndex("Name"));
            Description = cursor.getString(cursor.getColumnIndex("Description"));
            Brand = cursor.getString(cursor.getColumnIndex("Brand"));
            price = cursor.getInt(cursor.getColumnIndex("Price"));
            catid = cursor.getInt(cursor.getColumnIndex("Cat_ID"));
            item_id = cursor.getInt(cursor.getColumnIndex("ItemID"));
            url = cursor.getInt(cursor.getColumnIndex("url"));
            if (catid == 1)
                productsList.add(new Products(item_id, name, Description, price, Brand, url));
        } while (cursor.moveToNext());
        db.close();
        setproductItemrecycle(productsList);
        if (cord > 0)
            ord.setTextColor(Color.BLACK);

    }

    public void lab_data() {

        RecyclerView ProdRecycle = (RecyclerView) findViewById(R.id.product_recycler);
        ProdRecycle.setVisibility(View.VISIBLE);
        ConstraintLayout conOrder = (ConstraintLayout) findViewById(R.id.confirmorder);
        conOrder.setVisibility(View.INVISIBLE);
        List<Products> productsList = new ArrayList<>();
        Cursor cursor = db.getAllItems();
        do {
            if (cursor.getCount() <= 0 || cursor == null) break;
            String name, Description, Brand;
            Integer url, item_id, price, catid;
            name = cursor.getString(cursor.getColumnIndex("Name"));
            Description = cursor.getString(cursor.getColumnIndex("Description"));
            Brand = cursor.getString(cursor.getColumnIndex("Brand"));
            price = cursor.getInt(cursor.getColumnIndex("Price"));
            catid = cursor.getInt(cursor.getColumnIndex("Cat_ID"));
            item_id = cursor.getInt(cursor.getColumnIndex("ItemID"));
            url = cursor.getInt(cursor.getColumnIndex("url"));
            if (catid == 2)
                productsList.add(new Products(item_id, name, Description, price, Brand, url));
        } while (cursor.moveToNext());
        db.close();
        setproductItemrecycle(productsList);
        if (cord > 0)
            ord.setTextColor(Color.BLACK);

    }

    public void watch_data() {

        RecyclerView ProdRecycle = (RecyclerView) findViewById(R.id.product_recycler);
        ProdRecycle.setVisibility(View.VISIBLE);
        ConstraintLayout conOrder = (ConstraintLayout) findViewById(R.id.confirmorder);
        conOrder.setVisibility(View.INVISIBLE);
        List<Products> productsList = new ArrayList<>();
        Cursor cursor = db.getAllItems();
        do {
            if (cursor.getCount() <= 0 || cursor == null) break;
            String name, Description, Brand;
            Integer url, item_id, price, catid;
            name = cursor.getString(cursor.getColumnIndex("Name"));
            Description = cursor.getString(cursor.getColumnIndex("Description"));
            Brand = cursor.getString(cursor.getColumnIndex("Brand"));
            price = cursor.getInt(cursor.getColumnIndex("Price"));
            catid = cursor.getInt(cursor.getColumnIndex("Cat_ID"));
            item_id = cursor.getInt(cursor.getColumnIndex("ItemID"));
            url = cursor.getInt(cursor.getColumnIndex("url"));
            if (catid == 3)
                productsList.add(new Products(item_id, name, Description, price, Brand, url));
        } while (cursor.moveToNext());
        db.close();
        setproductItemrecycle(productsList);
        if (cord > 0)
            ord.setTextColor(Color.BLACK);

    }

    int c=0;
    public void tabchange(View view) {
        c++;
        ConstraintLayout con2=(ConstraintLayout)findViewById(R.id.tabchange);
        if(c%2!=0){
            con2.setVisibility(View.VISIBLE);
            con2.setAnimation(top);
        }
        else {
            con2.setVisibility(View.INVISIBLE);
            con2.setAnimation(buttom);
        }
    }

    public void Logout(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        Intent intent = new Intent(getApplicationContext(), signup_login.class);
        startActivity(intent);
        finish();
    }


    public void Search(View view) {
        EditText txt = (EditText) findViewById(R.id.editTextSearch);
        if (txt.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Name of Item", Toast.LENGTH_SHORT).show();
            return;
        }
        RecyclerView ProdRecycle = (RecyclerView) findViewById(R.id.product_recycler);
        Cursor cursor = db.getAllItems();
        List<Products> productsList = new ArrayList<>();
        int c = 0;
        do {
            if (cursor.getCount() <= 0 || cursor == null) break;
            String name, Description, Brand;
            Integer url, item_id, price;
            name = cursor.getString(cursor.getColumnIndex("Name"));
            Description = cursor.getString(cursor.getColumnIndex("Description"));
            Brand = cursor.getString(cursor.getColumnIndex("Brand"));
            price = cursor.getInt(cursor.getColumnIndex("Price"));
            item_id = cursor.getInt(cursor.getColumnIndex("ItemID"));
            url = cursor.getInt(cursor.getColumnIndex("url"));
            if (name.contains(txt.getText().toString())) {
                c++;
                productsList.add(new Products(item_id, name, Description, price, Brand, url));
            }
        } while (cursor.moveToNext());
        db.close();
        if (c == 0) {
            Toast.makeText(this, "Item Not Found", Toast.LENGTH_SHORT).show();
            return;
        }
        ProdRecycle.removeAllViews();
        setproductItemrecycle(productsList);
        txt.setText("");
    }

    public void Speach(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak something to Search");
        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EditText editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        if (requestCode == 1) {
            if (resultCode == -1 && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editTextSearch.setText(result.get(0));
            }
        }
    }

    public void BarCode(View view) {
        startActivity(new Intent(getApplicationContext(), scanner_view.class));

    }

    public void Plus(View view) {
        TextView textNumItem = (TextView) findViewById(R.id.textNumItem);
        Integer num = Integer.parseInt(textNumItem.getText().toString());
        num++;
        textNumItem.setText(String.valueOf(num));
    }

    public void Mins(View view) {
        TextView textNumItem = (TextView) findViewById(R.id.textNumItem);
        Integer num = Integer.parseInt(textNumItem.getText().toString());
        if (num > 0)
            num--;
        textNumItem.setText(String.valueOf(num));
    }

    public void DropDown(View view) {
        AutoCompleteTextView textItemId = (AutoCompleteTextView) findViewById(R.id.textItemId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Items);
        textItemId.setAdapter(adapter);
        textItemId.showDropDown();

    }

    private static final String[] Items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @SuppressLint("ResourceAsColor")
    public void Order(View view) {
        RecyclerView ProdRecycle = (RecyclerView) findViewById(R.id.product_recycler);
        ProdRecycle.setVisibility(View.INVISIBLE);
        ProdRecycle.setAnimation(buttom);
        ord = (TextView) findViewById(R.id.txtOrder);
        int bool = 1;
        int c = ord.getCurrentTextColor();

        if (c == Color.BLACK)
            bool = 0;

        if (bool == 0) {
            conOrder.setEnabled(true);
            ProdRecycle.setVisibility(View.INVISIBLE);

            conOrder.setVisibility(View.VISIBLE);
            ord.setTextColor(Color.RED);
        }

        cord++;
        phone.setTextColor(R.color.black);
        lab.setTextColor(R.color.black);
        watch.setTextColor(R.color.black);


    }

    public void Location(View view) {
        int id = view.getId();
        if (id == R.id.imageUserLocation) {
            Intent i = new Intent(Main.this, Map.class);
            startActivity(i);
        }
    }

    private Integer payPrice = 0;

    public void add_itemPrice(View view) {
        AutoCompleteTextView textItemId = (AutoCompleteTextView) findViewById(R.id.textItemId);
        TextView number_item = (TextView) findViewById(R.id.textNumItem);

        Integer num_item = Integer.parseInt(number_item.getText().toString());
        if (textItemId.getText().toString().equals("")) {
            Toast.makeText(this, "Please Choose Item's Id", Toast.LENGTH_SHORT).show();
        } else {

            if (num_item <= 0) {
                Toast.makeText(this, "Number of Items must be > 0", Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor;
                cursor = db.getAllItems();
                do {
                    if (cursor.equals(null) || cursor.getCount() <= 0) break;
                    ;
                    if (cursor.getInt(cursor.getColumnIndex("ItemID")) == Integer.parseInt(textItemId.getText().toString())) {
                        Integer ItemPrice = cursor.getInt(cursor.getColumnIndex("Price"));
                        Integer pRice = num_item * ItemPrice;
                        payPrice = payPrice + pRice;

                        totalprice.setText(String.valueOf(payPrice));
                        textItemId.setText("");
                        number_item.setText("0");
                        break;
                    }
                } while (cursor.moveToNext());
            }

        }
    }

    public void Payment(View view) {
        if (locationtxt.getText().toString().equals("")) {
            Toast.makeText(this, "Please Enter Your Location", Toast.LENGTH_LONG).show();
        } else if (totalprice.getText().toString().equals("Total Price")) {
            Toast.makeText(this, "Please Select Item in Your Cart ", Toast.LENGTH_LONG).show();
        } else {
            Integer id = null;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            id = preferences.getInt("id", 0);
            db.add_Cart(payPrice, id, locationtxt.getText().toString());
            TextView totalprice = (TextView) findViewById(R.id.textTotalPrice);
            AutoCompleteTextView textItemId = (AutoCompleteTextView) findViewById(R.id.textItemId);
            TextView number_item = (TextView) findViewById(R.id.textNumItem);
            totalprice.setText("Total Price");
            number_item.setText("0");
            textItemId.setText("");
            Toast.makeText(this, "Success Order", Toast.LENGTH_SHORT).show();
            Order(view);
        }
    }
}


