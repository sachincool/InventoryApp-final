package luthra.harshit.inventoryapp_final.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static luthra.harshit.inventoryapp_final.data.ItemContract.Db_name;

/**
 * Created by crappy on 18/9/17.
 */

public class ItemHelper extends SQLiteOpenHelper {
    ItemHelper(Context context) {
        super(context, Db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME + " ("
                + ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemContract.ItemEntry.COL_ITEM_NAME + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COL_ITEM_PRICE + " INTEGER NOT NULL , "
                + ItemContract.ItemEntry.COL_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ItemContract.ItemEntry.COL_ITEM_PICTURE + " BLOB NOT NULL   );";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
