package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

import static com.example.android.justjava.R.layout.activity_main;


public class MainActivity extends AppCompatActivity {
    int nOfCups = 1;
    int price;
    int priceOneCap = 4;
    int priceChocolate = 2;
    int priceWhippedCream = 1;
    int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

    }


    /**

     This method is called when the Plus button is clicked.
     */
    public void increment(View view) {
        if (nOfCups == 100) {
            Toast.makeText(getApplicationContext(), "You cannot buy more than 100 caps", Toast.LENGTH_LONG).show();
            return;

        }
        nOfCups = nOfCups + 1;
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

     This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        totalPrice = calculatePrice(nOfCups, priceOneCap);
        createOrderSummary(nOfCups,totalPrice);
        sendOrder();
        displayMessage(createOrderSummary(nOfCups,totalPrice));
        

    }

    public void sendOrder() {
        EditText nameEditText = findViewById(R.id.name_edit_text);
        String emailSubject = getString(R.string.email_subject, nameEditText);
        String emailMessage = createOrderSummary(nOfCups,totalPrice);
        String emailAddress = findViewById(R.id.email_edit_text).toString();
        Intent sendMail = new Intent(Intent.ACTION_SENDTO);
        sendMail.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        sendMail.putExtra(Intent.EXTRA_TEXT, emailMessage);
        sendMail.setData(Uri.parse(emailAddress));
        if (sendMail.resolveActivity(getPackageManager()) != null) {
            startActivity(sendMail);
        }
    }

        /**
     * Calculates the price of the order.
     *
     * @param nOfCups is the number of cups of coffee ordered
     */
    public int calculatePrice(int nOfCups, int priceOneCap) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_chekbox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_chekbox);
        if (whippedCreamCheckBox.isChecked()) {
            if (chocolateCheckBox.isChecked()) {
                price = nOfCups * (priceOneCap + priceWhippedCream + priceChocolate);
            }
            else price = nOfCups * (priceOneCap + priceWhippedCream);
        }
        else if (chocolateCheckBox.isChecked()) {
            price = nOfCups * (priceOneCap + priceChocolate);
        }
        else {
            price = nOfCups * priceOneCap;
        }
        return price;
    }

    /**

     This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number1) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(number1);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String priceMessage) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(priceMessage);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public String createOrderSummary(int nOfCups, int totalPrice) {

        EditText nameEditText = findViewById(R.id.name_edit_text);
        String priceMessage = getString(R.string.order_summary_name, nameEditText) + "\n" + getString(R.string.add_whipped_cream) + ((CheckBox)findViewById(R.id.whipped_cream_chekbox)).isChecked() + "\n"
         + getString(R.string.add_chocolate) + ((CheckBox)findViewById(R.id.chocolate_chekbox)).isChecked() + "\n"
         + getString(R.string.quantity) + nOfCups;
        priceMessage = priceMessage + "\n" + getString(R.string.total_summ) + totalPrice + "\n" + getString(R.string.thanks);
        return priceMessage;
    }


}
