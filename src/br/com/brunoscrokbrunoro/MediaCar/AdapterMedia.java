package br.com.brunoscrokbrunoro.MediaCar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bruno Scrok Brunoro
 * Date: 16/09/14
 * Time: 22:03
 */
public class AdapterMedia extends ArrayAdapter<Media> {

    private LayoutInflater inflater;

    public AdapterMedia(Context context, List<Media> objects) {
        super(context,R.layout.adapter_media, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final Media media = getItem(position);
            convertView = inflater.inflate(R.layout.adapter_media, parent, false);
            final TextView txtNomeCarro = (TextView) convertView.findViewById(R.id.txtNomeCarro);
            final TextView txtMedia = (TextView) convertView.findViewById(R.id.txtMedia);
            final TextView txtCombustivel = (TextView) convertView.findViewById(R.id.txtCombustivel);

            txtCombustivel.setText(media.getCombustivel());
            txtMedia.setText(media.getMedia()+"Km/l");
            txtNomeCarro.setText(media.getNomeCarro());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;

    }
}
