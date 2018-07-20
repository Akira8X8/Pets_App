package com.example.zed.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Zed on 2/27/2017.
 */

//An empty private constructor makes sure that the class is not going to be initialized.
public class ItemContract {
    private ItemContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.zed.inventory";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     */
    public static final String PATH_ITEMS = "items";

    /**
     * Inner class that defines constant values for the database table.
     */
    public static final class ItemEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public final static String TABLE_NAME = "items";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_NAME ="name";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_TYPE = "type";

        /**
         * The only possible values are {@link #TYPE_UNKNOWN}, {@link #TYPE_FACTORY},
         * or {@link #TYPE_SALE}.
         *
         * Type: INTEGER
         */

        public static final int TYPE_UNKNOWN = 0;
        public static final int TYPE_FACTORY = 1;
        public static final int TYPE_SALE = 2;

        public final static String COLUMN_ITEM_PRICE = "price";
        /**
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_WEIGHT = "weight";

        public final static String COLUMN_ITEM_QUANTITY = "quantity";

        public final static String COLUMN_SUPPLIER_NAME = "supplierName";

        public final static String COLUMN_SUPPLIER_PHONE = "supplierPhone";

        public final static String COLUMN_SUPPLIER_EMAIL = "supplierEmail";
        /**
         * Returns whether or not the given type is {@link #TYPE_UNKNOWN}, {@link #TYPE_FACTORY},
         * or {@link #TYPE_SALE}.
         */
        public static boolean isValidType(int type) {
            if (type == TYPE_UNKNOWN || type == TYPE_FACTORY || type == TYPE_SALE) {
                return true;
            }
            return false;
        }
    }
}
