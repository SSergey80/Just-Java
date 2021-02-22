package com.example.android.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.android.justjava.R.layout.activity_main;


public class MainActivity extends AppCompatActivity {
    int nOfCups = 1;
    int price;
    final int priceOneCap = 5;
    final int priceChocolate = 2;
    final int priceWhippedCream = 1;
    int totalPrice;
    CheckBox whippedCreamCheckBox;
    CheckBox chocolateCheckBox;
    EditText nameEditText;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;
    String name;
    String orderSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        whippedCreamCheckBox = findViewById(R.id.whipped_cream_chekbox);
        chocolateCheckBox = findViewById(R.id.chocolate_chekbox);
        nameEditText = findViewById(R.id.name_edit_text);
    }

    /**
     This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        name = nameEditText.getText().toString();
        hasWhippedCream = whippedCreamCheckBox.isChecked();
        hasChocolate = chocolateCheckBox.isChecked();
        totalPrice = calculatePrice();
        orderSummary = (createOrderSummary(totalPrice));
//        sendOrder();
        displayMessage(orderSummary);
    }

    /**
     * Calculates the price of the order.
     */
    public int calculatePrice() {

        if (hasWhippedCream) {
            if (hasChocolate) {
                price = nOfCups * (priceOneCap + priceWhippedCream + priceChocolate);
            }
            else price = nOfCups * (priceOneCap + priceWhippedCream);
        }
        else if (hasChocolate) {
            price = nOfCups * (priceOneCap + priceChocolate);
        }
        else {
            price = nOfCups * priceOneCap;
        }
        return price;
    }

    public String createOrderSummary(int totalPrice) {
        String priceMessage = getString(R.string.order_summary_name) + name + "\n";
        priceMessage += getString(R.string.add_whipped_cream) + hasWhippedCream + "\n";
        priceMessage += getString(R.string.add_chocolate) + hasChocolate + "\n";
        priceMessage += getString(R.string.quantity) + ": " + nOfCups;
        priceMessage += "\n" + getString(R.string.total_summ) + totalPrice + "\n" + getString(R.string.thanks);
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String orderSummary) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(orderSummary);
    }

    /**
     This method is called when the Plus button is clicked.
     */
    public void increment(View view) {
        if (nOfCups < 10) {
            nOfCups++;
        }
        else if (nOfCups == 10) {
            Toast.makeText(getApplicationContext(), "You cannot buy more than 100 caps", Toast.LENGTH_SHORT).show();
         }
        displayQuantity(nOfCups);
    }

    /**
     This method is called when the Minus button is clicked.
     */
    public void decrement(View view) {
        if (nOfCups == 0) {
            Toast.makeText(getApplicationContext(), "Number of cups must be more 0", Toast.LENGTH_LONG).show();
            return;
        }
        nOfCups = nOfCups - 1;
        displayQuantity(nOfCups);
    }

    /**
     This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number1) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(number1);
    }

//    public void sendOrder() {
//        String emailSubject = getString(R.string.email_subject);
//        String emailMessage = createOrderSummary(totalPrice);
//        String emailAddress = findViewById(R.id.email_edit_text).toString();
//        Intent sendMail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
//        sendMail.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
//        sendMail.putExtra(Intent.EXTRA_TEXT, emailMessage);
//        sendMail.setData(Uri.parse(emailAddress));
//        if (sendMail.resolveActivity(getPackageManager()) != null) {
//            startActivity(sendMail);
//        }
//    }

}
