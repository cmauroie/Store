/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.mauricio.store.Models.ItemEntry;
import com.developer.mauricio.store.Provider.ProviderListener;
import com.developer.mauricio.store.Provider.ProviderStore;

import java.util.List;

/**
 * Created by Mauricio on 26/10/15.
 */

public class MainActivity extends AppCompatActivity implements ProviderListener {

    private ListView list;
    List<ItemEntry> listValues;
    private GridView gridView;
    private ProviderStore providerStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        providerStore = new ProviderStore(this, this);
        setLayoutByScreenSize();
        providerStore.getData();


    }

    private void setLayoutByScreenSize() {
        if(isTablet(this)){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setContentView(R.layout.activity_main);
            gridView = (GridView)findViewById(R.id.gridview);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    listValues.get(position);

                    Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                    intent.putExtra("title", listValues.get(position).getName());
                    intent.putExtra("image", listValues.get(position).getImage());
                    intent.putExtra("summary", listValues.get(position).getSummary());
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);

                }
            });

        }else{
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.activity_main);
            list = (ListView)findViewById(R.id.lisView);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    listValues.get(position);

                    Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                    intent.putExtra("title", listValues.get(position).getName());
                    intent.putExtra("image", listValues.get(position).getImage());
                    intent.putExtra("summary", listValues.get(position).getSummary());
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);


                }
            });
        }
    }

    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResponseEvent(List<ItemEntry> result) {
        if (result != null) {
            findViewById(R.id.linear_progress).setVisibility(View.GONE);
            listValues = result;
            if(isTablet(MainActivity.this)){
                gridView.setVisibility(View.VISIBLE);
                gridView.setAdapter(new AdapterListFeed(MainActivity.this, R.layout.grid_item, listValues));
            }else{
                list.setVisibility(View.VISIBLE);
                list.setAdapter(new AdapterListFeed(MainActivity.this, R.layout.listitem, listValues));
            }

        }

    }
}
