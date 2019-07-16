package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.android.inventoryapp.data.BookContract.BookEntry;

/**
 * Displays list of books that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /** Tag for the log messages */
    private static final String LOG_TAG = CatalogActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    // When activity starts again, after user clicks Save in editor activity, the list will refresh with new book in the database
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the books database.
     */
    private void displayDatabaseInfo() {

        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_AUTHOR,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPPLIER_NAME,
                BookEntry.COLUMN_BOOK_SUPPLIER_PHONE
        };

        // Perform a query on the provider using the ContentResolver.
        // Use the {@link BookEntry#CONTENT_URI} to access the book data.
        //
        // Call ContentResolver query() method, which will call BookProvider query() method
        // and receive a Cursor result.
        Cursor cursor = getContentResolver().query(
                BookEntry.CONTENT_URI,           // The content URI of the books table
                projection,                      // The columns to return for each row
                null,                   // Selection criteria
                null,                // Selection criteria
                null);                 // The sort order for the returned rows

        // Find the ListView which will be populated with the book data
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Setup an Adapter to create a list item for each row of book data in the Cursor.
        BookCursorAdapter adapter = new BookCursorAdapter(this, cursor);

        // Attach the adapter to the ListView.
        bookListView.setAdapter(adapter);
    }

    /**
     * Helper method to insert hardcoded book data into the database. For debugging purposes only.
     */
    private void insertBook() {

        // Create a ContentValues object where column names are the keys,
        // and Stephen King book attributes are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, "Bag of Bones");
        values.put(BookEntry.COLUMN_BOOK_AUTHOR, "Stephen King");
        values.put(BookEntry.COLUMN_BOOK_PRICE, 19);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, 1);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, BookEntry.SUPPLIER_NAME_BAKER_TAYLOR);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, "1234567890");

        // Insert a new row for Stephen King's book into the provider using the ContentResolver.
        // Use the {@link BookEntry#CONTENT_URI} to indicate that we want to insert
        // into the books database table.
        // Receive the new content URI that will allow us to access book's data in the future.
        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertBook();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}