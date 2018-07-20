package com.example.zed.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Zed on 2/12/2017.
 */

final public class HabitContract {

    public static abstract class HabitEntry implements BaseColumns{

        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_FREQUENCY = "frequency";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_WHEN = "timing";

        public static final int FREQUENCY_RARE = 0;
        public static final int FREQUENCY_NORMAL = 1;
        public static final int FREQUENCY_OFTEN = 2;

        public static final int WHEN_MORNING = 0;
        public static final int WHEN_AFTERNOON = 1;
        public static final int WHEN_NIGHT = 2;
    }

    private HabitContract(){}
}
