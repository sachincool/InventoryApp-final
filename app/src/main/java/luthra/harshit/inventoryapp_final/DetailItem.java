package luthra.harshit.inventoryapp_final;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import luthra.harshit.inventoryapp_final.data.ItemQuery;

import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.COL_ITEM_NAME;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.COL_ITEM_PRICE;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.COL_ITEM_QUANTITY;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.TABLE_NAME;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry._ID;

/**
 * Created by crappy on 19/9/17.
 */

public class DetailItem extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView quantity;
    ImageView imageview;

    ImageView inc_image;
    ImageView dec_image;
    Button order_item;
    Button delete;
    int mID;
    String mitemName;

    int mitemQuantity, mitemPrice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);
        imageview = (ImageView) findViewById(R.id.item_image);
        delete = (Button) findViewById(R.id.Delete_completely);
        order_item = (Button) findViewById(R.id.order_more);
        name = (TextView) findViewById(R.id.name_edit);
        inc_image = (ImageView) findViewById(R.id.remove_angle);
        dec_image = (ImageView) findViewById(R.id.add_angle);
        price = (TextView) findViewById(R.id.price_edit);
        quantity = (TextView) findViewById(R.id.quantity_edit);
        Intent i = getIntent();
        if (i != null) {
            Bundle bundle = i.getExtras();
            mitemQuantity = bundle.getInt("quantity");
            mID = bundle.getInt("id");
            mitemName = bundle.getString("name");
            name.setText(mitemName);
            mitemPrice = bundle.getInt("price");
            price.setText(String.valueOf(mitemPrice));

            quantity.setText(String.valueOf(mitemQuantity));
            Glide.with(this)
                    .load(bundle.getByteArray("image"))
                    .error(R.mipmap.ic_launcher)
                    .into(imageview);

        }
        inc_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mitemQuantity++;
                quantity.setText(String.valueOf(mitemQuantity));

            }
        });
        dec_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mitemQuantity--;
                if (mitemQuantity < 0) {
                    mitemQuantity = 0;
                    Toast.makeText(DetailItem.this, "We've Run Out of items", Toast.LENGTH_SHORT).show();
                } else {
                    quantity.setText(String.valueOf(mitemQuantity));

                }
            }
        });

        order_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = "More order of Item";
                String message = "Item Name: " + name.getText() +
                        "\nItem Price: " + price.getText() +
                        "\nQuantity To be ordered: 10";
                String[] emails = {"love.coolsachin@gmail.com"};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, emails);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }


    public void deleteItem() {
        new AlertDialog.Builder(DetailItem.this)
                .setTitle("Warning!")
                .setMessage("Are you Sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selection = _ID + "=?";
                        String[] selectionArgs = {String.valueOf(mID)};
                        ItemQuery.getInstance(DetailItem.this).deleteEntry(TABLE_NAME, selection, selectionArgs);
                        Toast.makeText(DetailItem.this, "Deleted", Toast.LENGTH_SHORT).show();
                        MainActivity.onCursorRefresh();
                        finish();
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
        MainActivity.onCursorRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tick_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                ContentValues values = new ContentValues();
                values.put(COL_ITEM_NAME, name.getText().toString());
                values.put(COL_ITEM_PRICE, Integer.parseInt(price.getText().toString()));
                values.put(COL_ITEM_QUANTITY, Integer.parseInt(quantity.getText().toString()));


                String selection = _ID + "=?";
                String[] selectionArgs = {String.valueOf(mID)};
                ItemQuery.getInstance(DetailItem.this).updateData(TABLE_NAME, values, selection, selectionArgs);
                MainActivity.onCursorRefresh();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
