package luthra.harshit.inventoryapp_final.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.TABLE_NAME;

/**
 * Created by crappy on 16/9/17.
 */

public class ItemContract {
    public static final String CONTENT_AUTHORITY = "luthra.harshit.inventoryapp_final";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String Db_name = "Item.db";
    public static final String PATH_ITEM = TABLE_NAME;

    public ItemContract() {
    }

    public static abstract class ItemEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEM);
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;

        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "item";
        public static final String COL_ITEM_NAME = "name";
        public static final String COL_ITEM_QUANTITY = "quantity";
        public static final String COL_ITEM_PRICE = "price";
        public static final String COL_ITEM_PICTURE = "picture";
    }


}
