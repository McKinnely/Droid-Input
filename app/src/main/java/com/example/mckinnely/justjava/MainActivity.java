/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.example.mckinnely.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{
    //Global
    int quantity = 0;
    boolean whippedCream = false;
    boolean chocolateDip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */


    public void add(View view)
    {
        if(quantity > 100)
        {
            quantity--;
            Context context = getApplicationContext();
            CharSequence messageUser = "We can only serve 100.";
            int time = Toast.LENGTH_SHORT;

            Toast toastMessage = Toast.makeText(context,messageUser, time);
            toastMessage.show();

            return;
        }

        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */

    public void subtract(View view)
    {
        if(quantity < 1)
        {
            Context context = getApplicationContext();
            CharSequence messageUser = "You have to buy 1 or more coffees.";
            int time = Toast.LENGTH_SHORT;

            Toast toastMessage = Toast.makeText(context,messageUser, time);
            toastMessage.show();

            return;
        }

        quantity--;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view)
    {
        CheckBox whippedCream_CheckBox = (CheckBox) findViewById(R.id.whipped_Cream_Checkbox);
        CheckBox chocolateDip_CheckBox = (CheckBox) findViewById(R.id.chocolate_dip);
        boolean hasWhippedCream = whippedCream_CheckBox.isChecked();
        boolean hasChocolateDip = chocolateDip_CheckBox.isChecked();

        whippedCream = hasWhippedCream;
        chocolateDip = hasChocolateDip;

        int price;
        price =  calculatePrice();
        String priceMessage = createOrderSummary(price);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Java order for: " + getName());
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private  String getName()
    {
        String userName;
        TextView inputTextView = (TextView) findViewById(R.id.input_view);
        userName = inputTextView.getText().toString();
        userName = "Name: " + userName;
        return  userName;
    }

    /**
     * This method correctly calculates coffee.
     */
    private int calculatePrice()
    {
        int price = 2;
        price = price  * quantity;

        if(whippedCream)
        {
            price = price +  (quantity * 1);
        }

        if(chocolateDip)
        {
            price = price + (quantity * 2);
        }

        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given order summary on the screen.
     */

    private String createOrderSummary(int orderPrice)
    {
        String nameOnOrder =  getName();
        nameOnOrder = nameOnOrder + "\nAdded whipped cream?  " + whippedCream;
        nameOnOrder = nameOnOrder + "\nAdded chocolate dip?  " + chocolateDip;
        nameOnOrder = nameOnOrder + "\nQuantity: " + quantity;
        nameOnOrder = nameOnOrder + "\nTotal: $" + orderPrice;
        nameOnOrder = nameOnOrder + "\nThank you!";

        return nameOnOrder;
    }
}