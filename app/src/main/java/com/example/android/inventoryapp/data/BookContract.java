package com.example.android.inventoryapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Inventory app.
 */
public final class BookContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     * Used to help identify the Content Provider which we’d setup in the AndroidManifest tag.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.books";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     * To make this a usable URI, we use the parse method which takes in a URI string and returns a Uri.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * These constants store the path for each of the tables which will be appended to the base content URI.
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.books/books/ is a valid path for
     * looking at book data. content://com.example.android.books/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_BOOKS = "books";

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

        /** The content URI to access the book data in the provider
         * Inside each of the Entry classes in the contract, we create a full URI for the class
         * as a constant called CONTENT_URI. The Uri.withAppendedPath() method appends the BASE_CONTENT_URI
         * (which contains the scheme and the content authority) to the PATH segment.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        // Name of database table for books
        public final static String TABLE_NAME = "books";

        // Unique ID number for the book (only for use in the database table).
        // Type: INTEGER
        public final static String _ID = BaseColumns._ID;

        // Name of the book.
        // Type: TEXT
        public final static String COLUMN_BOOK_NAME ="name";

        // Author of the book.
        // Type: TEXT
        public final static String COLUMN_BOOK_AUTHOR ="author";

        //Price of the book.
        // Type: INTEGER
        public final static String COLUMN_BOOK_PRICE = "price";

        // Quantity of the book.
        // Type: INTEGER
        public final static String COLUMN_BOOK_QUANTITY = "quantity";

        // Supplier name of the book.
        // The only possible values are {@link #SUPPLIER_NAME_BAKER_TAYLOR}, {@link #SUPPLIER_NAME_TAN_BOOKS},
        // or {@link #SUPPLIER_NAME_CASEMATE}.
        // Type: TEXT
        public final static String COLUMN_BOOK_SUPPLIER_NAME = "supplier_name";

        // Supplier phone of the book.
        // Type: TEXT
        public final static String COLUMN_BOOK_SUPPLIER_PHONE = "supplier_phone";

        // Possible values for the supplier name of the book.
        public static final int SUPPLIER_NAME_BAKER_TAYLOR = 0;
        public static final int SUPPLIER_NAME_TAN_BOOKS = 1;
        public static final int SUPPLIER_NAME_CASEMATE = 2;

        /**
         * Returns whether or not the given supplier is {@link #SUPPLIER_NAME_BAKER_TAYLOR}, {@link #SUPPLIER_NAME_TAN_BOOKS},
         * or {@link #SUPPLIER_NAME_CASEMATE}.
         */
        public static boolean isValidSupplier (int supplier) {
            if (supplier == SUPPLIER_NAME_BAKER_TAYLOR || supplier == SUPPLIER_NAME_TAN_BOOKS || supplier == SUPPLIER_NAME_CASEMATE) {
                return true;
            }
            return false;
        }
    }
}