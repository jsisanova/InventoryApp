package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract.BookEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the book name */
    private EditText mNameEditText;

    /** EditText field to enter the author name */
    private EditText mAuthorEditText;

    /** EditText field to enter the book price */
    private EditText mPriceEditText;

    /** EditText field to enter the book quantity */
    private EditText mQuantityEditText;

    /** EditText field to enter the book supplier name */
    private Spinner mSupplierNameSpinner;

    /** EditText field to enter the book supplier phone */
    private EditText mSupplierPhoneEditText;

    /**
     * Supplier name of the book. The possible valid values are in the BookContract.java file:
     * {@link BookEntry#SUPPLIER_NAME_BAKER_TAYLOR}, {@link BookEntry#SUPPLIER_NAME_TAN_BOOKS}, or
     * {@link BookEntry#SUPPLIER_NAME_CASEMATE}.
     */
    private int mSupplierName = BookEntry.SUPPLIER_NAME_BAKER_TAYLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new book or editing an existing one.
        Intent intent = getIntent();
        Uri currentBookUri = intent.getData();

        // If the intent DOES NOT contain a book content URI, then we know that we are
        // creating a new book.
        if (currentBookUri == null) {
            // This is a new book, so change the app bar (label) to say "Add a Book"
            setTitle(getString(R.string.editor_activity_title_new_book));
        } else {
            // Otherwise this is an existing book, so change app bar to say "Edit Book"
            setTitle(getString(R.string.editor_activity_title_edit_book));
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_book_name);
        mAuthorEditText = (EditText) findViewById(R.id.edit_book_author);
        mPriceEditText = (EditText) findViewById(R.id.edit_book_price);
        mQuantityEditText = (EditText) findViewById(R.id.edit_book_quantity);
        mSupplierNameSpinner = (Spinner) findViewById(R.id.spinner_supplier_name);
        mSupplierPhoneEditText = (EditText) findViewById(R.id.edit_supplier_phone);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter supplierNameSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_supplier_name_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        supplierNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSupplierNameSpinner.setAdapter(supplierNameSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mSupplierNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplier_name_tan_books))) {
                        mSupplierName = BookEntry.SUPPLIER_NAME_TAN_BOOKS; // TAN Books
                    } else if (selection.equals(getString(R.string.supplier_name_casemate))) {
                        mSupplierName = BookEntry.SUPPLIER_NAME_CASEMATE; // Casemate
                    } else {
                        mSupplierName = BookEntry.SUPPLIER_NAME_BAKER_TAYLOR; // Baker & Taylor
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSupplierName = BookEntry.SUPPLIER_NAME_BAKER_TAYLOR; // Baker & Taylor
            }
        });
    }

    /**
     * Get user input from editor and save new book into database.
     */
    private void insertBook() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String authorString = mAuthorEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supplierPhoneString = mSupplierPhoneEditText.getText().toString().trim();
        // Convert string into integer
//        int price = Integer.parseInt(priceString);
//        int quantity = Integer.parseInt(quantityString);

        int price;
        int quantity;
        try {
            price = Integer.parseInt(priceString);
            // If we try to parse to an integer value when the priceString value is empty, which means "" (No user input)
            // This throws an NumberFormatException because we are trying to convert an empty string to an integer.
        } catch (NumberFormatException e) {
            price = 0; // Set the price to 0, because this is the default value in the database
        }

         try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e){
            quantity = 0; // Set the quantity to 0, because this is the default value in the database
        }

        // Create a ContentValues object where column names are the keys,
        // and book attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, nameString);
        values.put(BookEntry.COLUMN_BOOK_AUTHOR, authorString);
        values.put(BookEntry.COLUMN_BOOK_PRICE, price);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, mSupplierName);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, supplierPhoneString);

        // Insert a new book into the provider, returning the content URI for the new book.
        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_book_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_insert_book_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save book to database
                insertBook();
                // Exit  editor activity and return automatically to catalog activity
                finish();
                // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}