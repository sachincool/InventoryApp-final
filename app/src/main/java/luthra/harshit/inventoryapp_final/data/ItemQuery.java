package luthra.harshit.inventoryapp_final.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ItemQuery {

    private static ItemQuery INSTANCE;
    private ItemHelper ItemHelper;
    private SQLiteDatabase db;

    private ItemQuery(Context context) {
        ItemHelper = new ItemHelper(context);
    }

    public static ItemQuery getInstance(Context context) {
        if (context == null) {
            return null;
        }

        if (INSTANCE == null) {
            INSTANCE = new ItemQuery(context);
        }
        return INSTANCE;
    }

    public void insertIntoTable(String tableName, ContentValues values) {

        db = ItemHelper.getWritableDatabase();
        db.insert(tableName, null, values);

    }

    public Cursor readFromTable(String tableName, String[] projections) {

        db = ItemHelper.getReadableDatabase();
        return db.query(tableName, projections, null, null, null, null, null);

    }


    public void deleteEntry(String tableName, String selection, String[] selectionArgs) {

        db = ItemHelper.getReadableDatabase();
        db.delete(tableName, selection, selectionArgs);

    }

    public void updateData(String tableName, ContentValues values, String selection, String[] selectionArgs) {

        db = ItemHelper.getWritableDatabase();
        db.update(tableName, values, selection, selectionArgs);

    }

}
