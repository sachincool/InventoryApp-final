package luthra.harshit.inventoryapp_final.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import luthra.harshit.inventoryapp_final.DetailItem;
import luthra.harshit.inventoryapp_final.MainActivity;
import luthra.harshit.inventoryapp_final.R;

import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.COL_ITEM_NAME;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.COL_ITEM_PICTURE;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.COL_ITEM_PRICE;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.COL_ITEM_QUANTITY;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.TABLE_NAME;
import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry._ID;

/**
 * Created by crappy on 19/9/17.
 */

public class ItemCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;

    public ItemCursorAdapter(Context context, Cursor c, int flag) {
        super(context, c, flag);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return layoutInflater.inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView itemQuantityview = (TextView) view.findViewById(R.id.quantity);
        TextView itemPriceview = (TextView) view.findViewById(R.id.price);
        Button buyButton = (Button) view.findViewById(R.id.buy_button);
        LinearLayout linearView = (LinearLayout) view.findViewById(R.id.listItem);
        TextView itemNameview = (TextView) view.findViewById(R.id.name);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        final int ID = cursor.getInt(cursor.getColumnIndex(_ID));
        final String itemName = cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME));
        final int itemQuantity = cursor.getInt(cursor.getColumnIndex(COL_ITEM_QUANTITY));
        final int itemPrice = cursor.getInt(cursor.getColumnIndex(COL_ITEM_PRICE));
        final byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(COL_ITEM_PICTURE));

        itemNameview.setText(itemName.toUpperCase());
        itemQuantityview.setText("Quantity :- "+itemQuantity+" ");
        itemPriceview.setText("Price :- "+itemPrice+" ");

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = itemQuantity - 1;
                if (newQuantity < 0) {
                    newQuantity = 0;

                    Toast.makeText(context, "We've Run Out of items", Toast.LENGTH_SHORT).show();
                }
                ContentValues values = new ContentValues();
                values.put(COL_ITEM_QUANTITY, newQuantity);
                String selection = _ID + "=?";
                String[] selectionArgs = {String.valueOf(ID)};
                ItemQuery.getInstance(context).updateData(TABLE_NAME, values, selection, selectionArgs);
                MainActivity.onCursorRefresh();
            }
        });
        Glide.with(context).load(imageBlob).error(R.mipmap.ic_launcher).into(imageView);

        linearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", ID);
                bundle.putString("name", itemName);

                bundle.putInt("price", itemPrice);
                bundle.putInt("quantity", itemQuantity);
                bundle.putByteArray("image", imageBlob);
                Intent i = new Intent(context, DetailItem.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }
}
