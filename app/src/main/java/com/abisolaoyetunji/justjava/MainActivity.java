package com.abisolaoyetunji.justjava;

import java.text.NumberFormat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.abisolaoyetunji.justjava.R.id.name_field;

public class MainActivity extends AppCompatActivity {
 int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /** THIS method is called when increment button is clicked
     * *
     * @param view
     */
    public void increment(View view) {
        if(quantity==100)
        {

        Toast.makeText(this, "Alaye, you can't have more than 100 Coffes" ,Toast.LENGTH_SHORT).show();
         // exist this method early because there is nothing to do
            return;


        }
        quantity = quantity + 1;
        display(quantity);

    }
    /** THIS method is called when decrement button is clicked
     * *
     * @param view
     */
    public void decrement(View view) {
        if(quantity==1){
        Toast.makeText(this, "Alaye, you can't have more than 1 Coffes" ,Toast.LENGTH_SHORT).show();
            // exist this method early because there is nothing to do
       return;
        }quantity = quantity -1 ;

        display(quantity);}


    /** THIS mthd is callled when order butn is clicked
     * *
     * @param view
     */

    public void submitOrder(View view){
        EditText nameField =(EditText) findViewById(name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("Main Activity", "Has whipped cream:" + hasWhippedCream);

       // Check If chocolate toppings is needed
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//Only email apps should should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name );
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }

    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice =  5;
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate){basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    //addWhippedCream is whether or not the user wants whipped cream toppings//
@SuppressLint("StringFormatMatches")
private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolateCream){
        String priceMessage = getString(R.string.order_summary_name,name);
    priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
    priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolateCream);
    priceMessage +=  "\n"  + getString(R.string.order_summary_quantity, quantity);
    priceMessage += "\n" + getString(R.string.order_summary_price,
            NumberFormat.getCurrencyInstance().format(price));
    priceMessage += "\n" + getString(R.string.Thank_you);
        return priceMessage;
    }



}