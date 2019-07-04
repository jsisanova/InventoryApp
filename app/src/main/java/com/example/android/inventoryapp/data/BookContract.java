package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Inventory app.
 */
public final class BookContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {}

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

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
    }
}