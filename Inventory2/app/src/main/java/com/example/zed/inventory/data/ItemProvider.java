package com.example.zed.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.zed.inventory.data.ItemContract.ItemEntry;


public class ItemProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = ItemProvider.class.getSimpleName();

    /** URI matcher code for the content URI*/
    private static final int ITEMS = 100;

    /** URI matcher code for the content URI for a single item */
    private static final int ITEM_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEMS, ITEMS);

        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEMS + "/#", ITEM_ID);
    }

    /** Database helper object */
    private ItemDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ItemDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:

                cursor = database.query(ItemEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ITEM_ID:

                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ItemEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    private Uri insertItem(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(ItemEntry.COLUMN_ITEM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Item requires a name");
        }

        Integer type = values.getAsInteger(ItemEntry.COLUMN_ITEM_TYPE);
        if (type == null || !ItemEntry.isValidType(type)) {
            throw new IllegalArgumentException("Item requires valid type");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer weight = values.getAsInteger(ItemEntry.COLUMN_ITEM_WEIGHT);
        if (weight != null && weight < 0) {
            throw new IllegalArgumentException("Item requires valid weight");
        }

        Integer price = values.getAsInteger(ItemEntry.COLUMN_ITEM_PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Item requires valid price");
        }

        Integer quantity = values.getAsInteger(ItemEntry.COLUMN_ITEM_QUANTITY);
        if (quantity != null && quantity < 1) {
            throw new IllegalArgumentException("Item requires valid quantity");
        }

        String supplierName = values.getAsString(ItemEntry.COLUMN_SUPPLIER_NAME);
        if (supplierName == null) {
            throw new IllegalArgumentException("Item requires a supplier name");
        }

        Integer supplierPhone = values.getAsInteger(ItemEntry.COLUMN_SUPPLIER_PHONE);
        if (supplierPhone != null && supplierPhone < 0) {
            throw new IllegalArgumentException("Item requires a supplier phone");
        }

        String supplierEmail = values.getAsString(ItemEntry.COLUMN_SUPPLIER_EMAIL);
        if (supplierEmail == null) {
            throw new IllegalArgumentException("Item requires a supplier email");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        long id = database.insert(ItemEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }


        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }



    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return updateItem(uri, contentValues, selection, selectionArgs);
            case ITEM_ID:
                // For the ITEM_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateItem(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateItem(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link ItemEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(ItemEntry.COLUMN_ITEM_NAME)) {
            String name = values.getAsString(ItemEntry.COLUMN_ITEM_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Item requires a name");
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_ITEM_TYPE)) {
            Integer type = values.getAsInteger(ItemEntry.COLUMN_ITEM_TYPE);
            if (type == null || !ItemEntry.isValidType(type)) {
                throw new IllegalArgumentException("Item requires valid type");
            }
        }

        // If the {@link ItemEntry#COLUMN_PET_WEIGHT} key is present,
        // check that the weight value is valid.
        if (values.containsKey(ItemEntry.COLUMN_ITEM_WEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer weight = values.getAsInteger(ItemEntry.COLUMN_ITEM_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Item requires valid weight");
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_ITEM_PRICE)) {
            Integer price = values.getAsInteger(ItemEntry.COLUMN_ITEM_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Item requires valid price");
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_ITEM_QUANTITY)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer quantity = values.getAsInteger(ItemEntry.COLUMN_ITEM_QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Image requires valid weight");
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_SUPPLIER_NAME)) {
            String supplierName = values.getAsString(ItemEntry.COLUMN_SUPPLIER_NAME);
            if (supplierName == null) {
                throw new IllegalArgumentException("Supplier requires a name");
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_SUPPLIER_PHONE)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer supplierPhone = values.getAsInteger(ItemEntry.COLUMN_SUPPLIER_PHONE);
            if (supplierPhone != null && supplierPhone < 0) {
                throw new IllegalArgumentException("Supplier requires valid phone number");
            }
        }

        if (values.containsKey(ItemEntry.COLUMN_SUPPLIER_EMAIL)) {
            String supplierEmail = values.getAsString(ItemEntry.COLUMN_SUPPLIER_EMAIL);
            if (supplierEmail == null) {
                throw new IllegalArgumentException("Supplier requires an email");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(ItemEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(ItemEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                // Delete a single row given by the ID in the URI
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(ItemEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return ItemEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return ItemEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
