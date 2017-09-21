package luthra.harshit.inventoryapp_final;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import luthra.harshit.inventoryapp_final.data.ItemContract;
import luthra.harshit.inventoryapp_final.data.ItemQuery;

import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.TABLE_NAME;

/**
 * Created by crappy on 19/9/17.
 */

public class AddItem extends AppCompatActivity {
    ImageView imageView;
    byte[] imageBlob = null;
    Button addItem;
    int mitemprice;
    String mitemname;

    int mitemquantity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        imageView = (ImageView) findViewById(R.id.item_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, 1);
            }
        });
        addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(image);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBlob = stream.toByteArray();
            }
        }
    }

    public void saveItem() {
        EditText itemName = (EditText) findViewById(R.id.name_edit);
        EditText itemPrice = (EditText) findViewById(R.id.price_edit);

        EditText itemQuantity = (EditText) findViewById(R.id.quantity_edit);


        if (itemName.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Name cant be Null", Toast.LENGTH_SHORT).show();
            return;
        } else mitemname = itemName.getText().toString();

        if (itemPrice.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please Enter the price", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mitemprice = Integer.parseInt(itemPrice.getText().toString());
        }

        if (imageBlob == null) {
            Toast.makeText(getApplicationContext(), "Item Image required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (itemQuantity.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Quantity Required", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mitemquantity = Integer.parseInt(itemQuantity.getText().toString());
        }


        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COL_ITEM_NAME, mitemname);
        values.put(ItemContract.ItemEntry.COL_ITEM_PRICE, mitemprice);
        values.put(ItemContract.ItemEntry.COL_ITEM_QUANTITY, mitemquantity);

        values.put(ItemContract.ItemEntry.COL_ITEM_PICTURE, imageBlob);
        ItemQuery.getInstance(getBaseContext()).insertIntoTable(TABLE_NAME, values);
        MainActivity.onCursorRefresh();


        finish();


    }

}
