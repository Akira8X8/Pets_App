package com.example.android.pets.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Zed on 1/31/2017.
 */

public final class PetContract {

    private PetContract(){}

    public static final String CONTENT_AUTHORITY
            = "com.example.android.pets";
    public static final Uri BASE_CONTENT_URI
            = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PETS
            = "pets";

    public static abstract class PetEntry implements BaseColumns{

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE
                = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/"
                + CONTENT_AUTHORITY
                + "/"
                + PATH_PETS;

        public static final String CONTENT_ITEM_TYPE
                    = ContentResolver.CURSOR_DIR_BASE_TYPE
                    + "/"
                    + CONTENT_AUTHORITY
                    + "/"
                    + PATH_PETS;


        public static final String TABLE_NAME
                = "pets";
        public static final String _ID
                = BaseColumns._ID;
        public static final String COLUMN_NAME
                = "name";
        public static final String COLUMN_BREED
                = "breed";
        public static final String COLUMN_WEIGHT
                = "weight";
        public static final String COLUMN_GENDER
                = "gender";

        public static final Uri CONTENT_URI
                = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        //Possible gender values

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static boolean isValidGender(int gender)
        {
            if
                    (
                    gender == GENDER_UNKNOWN
                            ||
                            gender == GENDER_MALE
                            ||
                            gender == GENDER_FEMALE
                    )
            {
                return true;
            }
            return false;
        }
    }
}
