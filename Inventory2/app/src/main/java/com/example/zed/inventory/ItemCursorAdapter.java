package com.example.zed.inventory;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zed.inventory.data.ItemContract.ItemEntry;

/**
 * Created by Zed on 2/27/2017.
 */

public class ItemCursorAdapter extends CursorAdapter {

    protected ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantTextView = (TextView) view.findViewById(R.id.quantity_edit);
        ImageView orderImageView = (ImageView) view.findViewById(R.id.order);
        final int position = cursor.getPosition();

        orderImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                cursor.moveToPosition(position);
                int itemColumnIndex = cursor.getColumnIndex(ItemEntry._ID);
                final long itemId = cursor.getLong(itemColumnIndex);
                Uri mCurrentItemUri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI, itemId);
                int quantColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);
                String itemQuant = cursor.getString(quantColumnIndex);
                int currentQuant = Integer.parseInt(itemQuant);

                if(currentQuant >0) {

                    currentQuant--;

                    ContentValues values = new ContentValues();
                    values.put(ItemEntry.COLUMN_ITEM_QUANTITY, currentQuant);

                    int tabUpdate = context.getContentResolver().update(mCurrentItemUri, values, null, null);

                } else {
                    Toast.makeText(context, "Out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });

        int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
        final int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
        final int quantColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);

        String itemName = cursor.getString(nameColumnIndex);
        String itemPrice = "$" + cursor.getString(priceColumnIndex);
        String itemQuant = cursor.getString(quantColumnIndex);

        if (TextUtils.isEmpty(itemPrice)) {
            itemPrice = context.getString(R.string.unknown_price);
        }
        nameTextView.setText(itemName);
        priceTextView.setText(itemPrice);
        quantTextView.setText(itemQuant);

    }
}