package com.example.android.coffeecafe;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        //method for when the order button is clicked
    public void orderClicked(View view){
        //gets the users name
        EditText nameText = (EditText)findViewById(R.id.name_text_field);
        String name = nameText.getText().toString();

        //finds out if the user wants whippedCream topping
        CheckBox whippedCream = (CheckBox)findViewById(R.id.add_whipped_cream);
        boolean haswhippedCream = whippedCream.isChecked();

        //finds out if the user wants chocolate topping
        CheckBox chocolate = (CheckBox)findViewById(R.id.add_chocolate);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(haswhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(name, price, haswhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "coffee order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }

    }
    /*
    calculate the price of the order
    *@param addWhippedCream is to check if the user wants whippedCream or not
    * @param addChocolate is to check if the user wants chocolate or not
     *  */
    public int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream){
            //add $1 if the user wants whippedCream
            basePrice = basePrice + 1;
        }
        if(addChocolate){
            //add $2 if the user wants chocolate
            basePrice = basePrice + 2;
        }
        //calculate the total order price by multiplying by quantity
         return quantity * basePrice;
    }

    public void decrement(View view){
        if(quantity==1){
            //shows an error message as a toast when the user clicked the - button when quantity is 1
            Toast.makeText(this, "you can't order less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;

        }
        quantity = quantity -1;
        displayQuantity(quantity);
    }
      public void increment(View view){
        if(quantity==100){
            //shows an error mesage when user hits the + button when quantity is at 100
            Toast.makeText(this,"you can't order more than 100 coffees at a time",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
      }
        /*
        * @params name gets the name of the user
        * @param price shows the total order price
        * */
      private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String displayTotalSummary = "Name: " + name;
        displayTotalSummary += "\nAdd whipped Cream? " + addWhippedCream;
        displayTotalSummary += "\nAdd chocolate? " + addChocolate;
        displayTotalSummary += "\n Quantity: "+ quantity;
        displayTotalSummary += "\nTotal: $" + price;
        displayTotalSummary += "\nThank you";

            return displayTotalSummary;
      }


      //method to display the quantity on the screen
      private void displayQuantity(int numbers){
        TextView textView = (TextView)findViewById(R.id.tv_quantity);
        textView.setText("" + numbers);
      }

}
