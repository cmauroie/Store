/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.mauricio.store.Models.ItemEntry;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;


/**
 * Created by Mauricio on 27/10/15.
 */
public class AdapterListFeed extends ArrayAdapter<ItemEntry> {
    private Context context = null;
    private int resource = 0;
    private List<ItemEntry> objects = null;

    public AdapterListFeed(Context context, int resource, List<ItemEntry> objects) {

        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemEntry item = objects.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRoot = inflater.inflate(resource, parent, false);

        ImageView imageView = (ImageView)viewRoot.findViewById(R.id.imagePost);
        TextView textView = (TextView)viewRoot.findViewById(R.id.textoTitulo);

        Picasso.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        textView.setText(item.getName());

        return viewRoot;
    }
}
