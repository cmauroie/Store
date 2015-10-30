/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store.Provider;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.developer.mauricio.store.Models.ItemEntry;
import com.developer.mauricio.store.R;
import com.developer.mauricio.store.RequestManager.HttpManager;
import com.developer.mauricio.store.RequestManager.ResponseFormat;
import com.developer.mauricio.store.RequestManager.ServicesConsumer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Mauricio on 27/10/15.
 */
public class ProviderStore implements ServicesConsumer {
    private static final String URL = "/us/rss/topfreeapplications/limit=20/json";
    private static final String MAP = "entry";
    private static final String NAME = "im:name";
    private static final String IMAGE = "im:image";
    private static final String SUMMARY = "summary";
    private static final String LABEL = "label";
    private static String DEBUG_TAG = "Store";
    private ProviderListener listener;
    private Context context;
    private SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private static String CACHE_DATA = "cache_data";


    public ProviderStore(ProviderListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        editor=prefs.edit();
    }

    public void getData() {
        String dataCached = prefs.getString(CACHE_DATA,"");

        if(dataCached != null && dataCached.equals("")) {
            HttpManager connectionManager;

            connectionManager = new HttpManager();

            connectionManager.setConsumer(this)
                             .setmMethod(HttpManager.GET)
                             .excute(URL);
        } else {
            listener.onResponseEvent(string2Gson(dataCached));

        }

    }

    @Override
    public void processResponse(ResponseFormat responseFormat) {
        if(!responseFormat.isErrorLocal() && !responseFormat.isErrorServer()) {
            try {
                JSONObject json = new JSONObject(responseFormat.getData());
                JSONObject jsonObjectPr = json.getJSONObject("feed");
                JSONArray jsonArray = jsonObjectPr.getJSONArray(MAP);

                Log.i(DEBUG_TAG, "Array: " + jsonArray);
                Log.i(DEBUG_TAG, "length: "+jsonArray.length());
                List<ItemEntry> listValues = new ArrayList<>();

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject data = jsonArray.getJSONObject(i);
                    JSONArray imageArray = data.getJSONArray(IMAGE);
                    JSONObject title = data.getJSONObject(NAME);
                    JSONObject image = imageArray.getJSONObject(2);
                    JSONObject summary = data.getJSONObject(SUMMARY);
                    listValues.add(new ItemEntry(title.getString(LABEL),image.getString(LABEL),summary.getString(LABEL)));
                }
                editor.putString(CACHE_DATA, object2StringGson(listValues));
                editor.commit();
                listener.onResponseEvent(listValues);

            }catch (JSONException e){

            }
        }else{
            Toast.makeText(context, "Por favor revisa tu conexión a internet", Toast.LENGTH_LONG).show();
        }
    }

    private static String object2StringGson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);

    }

    private static List<ItemEntry> string2Gson (String data) {
        Gson gson = new Gson();
        Type token = new TypeToken<List<ItemEntry>>(){}.getType();

        return gson.fromJson(data, token);

    }
}
