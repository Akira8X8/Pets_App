package com.example.zed.inventory;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zed.inventory.data.ItemContract.ItemEntry;


public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int EXISTING_ITEM_LOADER = 0;

    /** Content URI for the existing item (null if it's a new item) */
    private Uri mCurrentitemUri;


    /** EditText field to enter the item's name */
    private EditText mNameEditText;

    private EditText mPriceEditText;

    private Spinner mTypeSpinner;

    /** EditText field to enter the item's weight */
    private EditText mWeightEditText;

    private EditText mQuantityEditText;

    private EditText mSupplierNameEditText;

    private EditText mSupplierPhoneEditText;

    private EditText mSupplierEmailEditText;

    private int mType = ItemEntry.TYPE_UNKNOWN;

    /** Boolean flag that keeps track of whether the item has been edited (true) or not (false) */
    public boolean mitemHasChanged = false;

    private ImageView orderMore;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mitemHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mitemHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new item or editing an existing one.
        Intent intent = getIntent();
        mCurrentitemUri = intent.getData();

        ImageButton decrease;

        ImageButton increase;


        // If the intent DOES NOT contain a item content URI, then we know that we are
        // creating a new item.
        if (mCurrentitemUri == null) {
            // This is a new item, so change the app bar to say "Add a item"
            setTitle(getString(R.string.editor_activity_title_new_item));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a item that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing item, so change app bar to say "Edit item"
            setTitle(getString(R.string.editor_activity_title_edit_item));

            // Initialize a loader to read the item data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_item_name);
        mPriceEditText = (EditText) findViewById(R.id.edit_item_price);
        mTypeSpinner = (Spinner) findViewById(R.id.spinner_type);
        mWeightEditText = (EditText) findViewById(R.id.edit_item_weight);
        mQuantityEditText = (EditText) findViewById(R.id.quantity_edit);
        increase = (ImageButton) findViewById(R.id.increase_quantity);
        decrease = (ImageButton) findViewById(R.id.decrease_quantity);
        mSupplierNameEditText = (EditText) findViewById(R.id.supplier_name);
        mSupplierPhoneEditText = (EditText) findViewById(R.id.supplier_phone);
        mSupplierEmailEditText = (EditText) findViewById(R.id.supplier_email);
        orderMore = (ImageView) findViewById(R.id.order_more);


        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mNameEditText.setOnTouchListener(mTouchListener);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mWeightEditText.setOnTouchListener(mTouchListener);
        mTypeSpinner.setOnTouchListener(mTouchListener);
        mQuantityEditText.setOnTouchListener(mTouchListener);


        setupSpinner();

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantPlus();
                mitemHasChanged = true;
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantMinus();
                mitemHasChanged = true;
            }
        });

        orderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                orderConfirm();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void setupSpinner() {
        ArrayAdapter typeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_type_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mTypeSpinner.setAdapter(typeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_factory))) {
                        mType = ItemEntry.TYPE_FACTORY;
                    } else if (selection.equals(getString(R.string.type_sale))) {
                        mType = ItemEntry.TYPE_SALE;
                    } else {
                        mType = ItemEntry.TYPE_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = ItemEntry.TYPE_UNKNOWN;
            }
        });
    }

    private void quantPlus() {
        String currentQuantStr = mQuantityEditText.getText().toString();
        int currentQuant;
        if (currentQuantStr.isEmpty()) {
            currentQuant = 0;
        } else {
            currentQuant = Integer.parseInt(currentQuantStr);
        }
        mQuantityEditText.setText(String.valueOf(currentQuant + 1));
    }

    public void quantMinus() {
        String currentQuantStr = mQuantityEditText.getText().toString();
        int currentQuant;
        if (currentQuantStr.isEmpty()) {
            return;
        } else if (currentQuantStr.equals("0")) {
            return;
        } else {
            currentQuant = Integer.parseInt(currentQuantStr);
            mQuantityEditText.setText(String.valueOf(currentQuant - 1));
        }
    }

    // add a global variable imageUri
    Uri imageUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Get user input from editor and save item into database.
     */
    private void saveitem() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String weightString = mWeightEditText.getText().toString().trim();
        String quantString = mQuantityEditText.getText().toString().trim();
        String supName = mSupplierNameEditText.getText().toString().trim();
        String supPhone = mSupplierPhoneEditText.getText().toString().trim();
        String supEmail = mSupplierEmailEditText.getText().toString().trim();
        // Check if this is supposed to be a new item
        // and check if all the fields in the editor are blank
        if (mCurrentitemUri == null &&
                (
                        TextUtils.isEmpty(nameString) ||
                        TextUtils.isEmpty(priceString) ||
                        TextUtils.isEmpty(weightString) ||
                        mType == ItemEntry.TYPE_UNKNOWN ||
                        TextUtils.isEmpty((quantString)) ||
                        TextUtils.isEmpty((supName)) ||
                        TextUtils.isEmpty((supPhone)) ||
                        TextUtils.isEmpty((supEmail))
                )
                )
        {
            // you could also add a toast here
            Toast.makeText(this, "Please fill all the required information", Toast.LENGTH_SHORT).show();

            // Since no fields were modified, we can return early without creating a new item.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and item attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_NAME, nameString);
        values.put(ItemEntry.COLUMN_ITEM_PRICE, priceString);
        values.put(ItemEntry.COLUMN_ITEM_TYPE, mType);
        // If the weight is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int weight = 0;
        if (!TextUtils.isEmpty(weightString)) {
            weight = Integer.parseInt(weightString);
        }
        values.put(ItemEntry.COLUMN_ITEM_WEIGHT, weight);

        int quant = 1;
        if (!TextUtils.isEmpty(quantString)) {
            quant = Integer.parseInt(quantString);
        }
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, quant);

        values.put(ItemEntry.COLUMN_SUPPLIER_NAME, supName);

        values.put(ItemEntry.COLUMN_SUPPLIER_PHONE, supPhone);

        values.put(ItemEntry.COLUMN_SUPPLIER_EMAIL, supEmail);

        // Determine if this is a new or existing item by checking if mCurrentitemUri is null or not
        if (mCurrentitemUri == null) {
            // This is a NEW item, so insert a new item into the provider,
            // returning the content URI for the new item.
            Uri newUri = getContentResolver().insert(ItemEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING item, so update the item with content URI: mCurrentitemUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentitemUri will already identify the correct row in the database that
            // we want to modify.
            Log.v("EditorActivity", ">>>>>> MCurrentitemUri: " + mCurrentitemUri.toString());
            int rowsAffected = getContentResolver().update(mCurrentitemUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new item, hide the "Delete" menu item.
        if (mCurrentitemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save item to database
                saveitem();

                finish();

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the item hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mitemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the item hasn't changed, continue with handling back button press
        if (!mitemHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all item attributes, define a projection that contains
        // all columns from the item table
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_ITEM_PRICE,
                ItemEntry.COLUMN_ITEM_TYPE,
                ItemEntry.COLUMN_ITEM_WEIGHT,
                ItemEntry.COLUMN_ITEM_QUANTITY,
                ItemEntry.COLUMN_SUPPLIER_NAME,
                ItemEntry.COLUMN_SUPPLIER_PHONE,
                ItemEntry.COLUMN_SUPPLIER_EMAIL};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentitemUri,         // Query the content URI for the current item
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of item attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
            int typeColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_TYPE);
            int weightColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_WEIGHT);
            int quantColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);
            int supNameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_SUPPLIER_NAME);
            int supPhoneColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_SUPPLIER_PHONE);
            int supEmailColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_SUPPLIER_EMAIL);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String price = cursor.getString(priceColumnIndex);
            int type = cursor.getInt(typeColumnIndex);
            int weight = cursor.getInt(weightColumnIndex);
            int quant = cursor.getInt(quantColumnIndex);
            String supName = cursor.getString(supNameColumnIndex);
            int supPhone = cursor.getInt(supPhoneColumnIndex);
            String supEmail = cursor.getString(supEmailColumnIndex);

            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mPriceEditText.setText(price);
            mWeightEditText.setText(Integer.toString(weight));
            mQuantityEditText.setText(Integer.toString(quant));
            mSupplierNameEditText.setText(supName);
            mSupplierPhoneEditText.setText(Integer.toString(supPhone));
            mSupplierEmailEditText.setText(supEmail);


            // Type is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Factory, 2 is Sale).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (type) {
                case ItemEntry.TYPE_FACTORY:
                    mTypeSpinner.setSelection(1);
                    break;
                case ItemEntry.TYPE_SALE:
                    mTypeSpinner.setSelection(2);
                    break;
                default:
                    mTypeSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mNameEditText.setText("");
        mPriceEditText.setText("");
        mWeightEditText.setText("");
        mTypeSpinner.setSelection(0);
        mQuantityEditText.setText("");
        mSupplierNameEditText.setText("");
        mSupplierPhoneEditText.setText("");
        mSupplierEmailEditText.setText("");
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Prompt the user to confirm that they want to delete this item.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the item.
                deleteitem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the item in the database.
     */
    private void deleteitem() {
        // Only perform the delete if this is an existing item.
        if (mCurrentitemUri != null) {
            // Call the ContentResolver to delete the item at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentitemUri
            // content URI already identifies the item that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentitemUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

    public void openImageSelector() {
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }

        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
    }



    private void orderConfirm() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.order_more);
        builder.setPositiveButton(R.string.phone, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Ordery by phone
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mSupplierPhoneEditText.getText().toString().trim()));
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.email, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Order by email
                Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:" + mSupplierEmailEditText.getText().toString().trim()));
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "New order");
                String bodyMessage = "Please send more " +
                        mNameEditText.getText().toString().trim();
                intent.putExtra(android.content.Intent.EXTRA_TEXT, bodyMessage);
                startActivity(intent);
            }
        });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}