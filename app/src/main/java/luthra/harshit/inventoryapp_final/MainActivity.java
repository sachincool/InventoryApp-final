package luthra.harshit.inventoryapp_final;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import luthra.harshit.inventoryapp_final.data.ItemCursorAdapter;
import luthra.harshit.inventoryapp_final.data.ItemQuery;

import static luthra.harshit.inventoryapp_final.data.ItemContract.ItemEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    private static ItemCursorAdapter adapter;
    private static Context context;
    ListView listView;
    Cursor cursor;

    public static void onCursorRefresh() {
        Cursor cursor = ItemQuery.getInstance(context).readFromTable(TABLE_NAME, null);
        adapter.swapCursor(cursor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        listView = (ListView) findViewById(R.id.listview);
        RelativeLayout emptyView = (RelativeLayout) findViewById(R.id.emptyView);
        listView.setEmptyView(emptyView);
        cursor = ItemQuery.getInstance(this).readFromTable(TABLE_NAME, null);
        if (cursor != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    adapter = new ItemCursorAdapter(context, cursor, 0);
                    listView.setAdapter(adapter);
                }
            });
        } else
            emptyView.setVisibility(View.VISIBLE);

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddItem.class);
                startActivity(i);
            }
        });
    }
}
