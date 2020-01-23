package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;


public class MainActivity extends AppCompatActivity
{
    int coffees=2;
    int price =0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // decrement the quantity of coffees
    public void decrement(View view)
    {
        if(coffees>1)
        {
            coffees--;
            displayQuantity(coffees);
        }
        else
            Toast.makeText(this,"you cannot order 0 coffes",Toast.LENGTH_SHORT).show();
    }

    private int calculatePrice(boolean whippedCream, boolean chocolate)
    {
        price=5;

        if(whippedCream)
            price+=1;
        if (chocolate)
            price+=2;

        price=price*coffees;

        return price;
    }




    private String createOrderSummary(String name, boolean addWhippedCream, boolean addChocolate )
    {

        String summaryMessage;
        summaryMessage=getString(R.string.order_summary_name,name)+"\n";
        summaryMessage+=getString(R.string.order_summary_whipped_cream,addWhippedCream)+"\n";
        summaryMessage+=getString(R.string.order_summary_chocolate,addChocolate)+"\n";
        summaryMessage+=getString(R.string.order_summary_quantity)+coffees+"\n";// esta es la mejor forma??
        summaryMessage+=getString(R.string.thank_you);
        return summaryMessage;
    }

    // obtain the total expense of coffees
    public void submitOrder(View view)
    {
        boolean checkedWhippedCream;
        boolean checkedChocolate;

        CheckBox checkBoxWhippedCream = findViewById(R.id.checkbox_whippedCream);
        checkedWhippedCream = checkBoxWhippedCream.isChecked();

        CheckBox checkBoxChocolate = findViewById(R.id.checkbox_chocolate);
        checkedChocolate = checkBoxChocolate.isChecked();

        EditText editTextName = findViewById(R. id.editText_name);
        String name = editTextName.getText().toString();

        price=calculatePrice(checkedWhippedCream, checkedChocolate);

        String [] addresses={"jafet_dz99@hotmail.com","jafet.dz.jd@gmail.com"};
        String subject ="Just Java order for"+name;
        String priceMessage = createOrderSummary(name ,checkedWhippedCream, checkedChocolate);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);// list of addresses to send the mail to
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);// subject of the mail
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage); // the body of the mail

        // if someone can handle the intent send it otherwise do not send it
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // decrement the quantity of coffees
    public void increment(View view)
    {
        if(coffees<100)
        {
            coffees++;
            displayQuantity(coffees);
        }
        else
            Toast.makeText(this,"you cannot order more than 100 coffees",Toast.LENGTH_SHORT).show();

    }


    // display on the screen the change in the quantity of coffees
    private void displayQuantity(int number)
    {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }



}
