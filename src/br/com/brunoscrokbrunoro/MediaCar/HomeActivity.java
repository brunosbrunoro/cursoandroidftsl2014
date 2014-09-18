package br.com.brunoscrokbrunoro.MediaCar;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private List<Media> listMedias = new ArrayList<Media>();
    private AdapterMedia adapter;
    private ListView lstMedias;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        LinearLayout lilNomeCarro = (LinearLayout) findViewById(R.id.lilNomeCarro);
        final TextView txtNomeCarro = (TextView) findViewById(R.id.txtNomeCarro);
        final EditText edtNomeCarro = (EditText) findViewById(R.id.edtNomeCarro);
        final EditText edtKm = (EditText) findViewById(R.id.edtKm);
        final EditText edtLitros = (EditText) findViewById(R.id.edtLitros);
        String nomeCarro = edtNomeCarro.getText().toString();

        final RadioGroup rgbCombustivel = (RadioGroup) findViewById(R.id.rgbCombustivel);
        final RadioButton rbtGasolina = (RadioButton) findViewById(R.id.rbtGasolina);
        final RadioButton rbtAlcool = (RadioButton) findViewById(R.id.rbtAlcool);
        final RadioButton rbtDiesel = (RadioButton) findViewById(R.id.rbtDiesel);

        lstMedias = (ListView) findViewById(R.id.lstMedias);
        adapter = new AdapterMedia(HomeActivity.this,listMedias);
        lstMedias.setAdapter(adapter);


        Button butCalcularMedia = (Button) findViewById(R.id.butCalcularMedia);
        butCalcularMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNullOrWhitespaceOrEmpty(edtNomeCarro.getText().toString())){
                    alertShow("Informe o nome do carro");
                    return;
                }
                if(isNullOrWhitespaceOrEmpty(edtKm.getText().toString())){
                    alertShow("Informe uma quilometragem");
                    return;
                }
                if(isNullOrWhitespaceOrEmpty(edtLitros.getText().toString())){
                    alertShow("Infome os litros abastecido");
                    return;
                }

                String combustivel = "";
                if (rgbCombustivel.getCheckedRadioButtonId() == rbtGasolina.getId()){
                    combustivel = "Gasolina";
                }else if (rgbCombustivel.getCheckedRadioButtonId() == rbtAlcool.getId()){
                    combustivel = "Alcool";
                }else if (rgbCombustivel.getCheckedRadioButtonId() == rbtDiesel.getId()){
                    combustivel = "Diesel";
                }else{
                    alertShow("Selecione um combustível");
                    return;
                }





                double media = Double.parseDouble(edtKm.getText().toString()) / Double.parseDouble(edtLitros.getText().toString());


                AlertDialog.Builder dialogs = new AlertDialog.Builder(HomeActivity.this);
                dialogs.setTitle("Media");
                dialogs.setMessage("No combustivel " + combustivel + " a média foi de " + media + " KM/l");
                dialogs.show();

                Media mediaCalculada = new Media();
                mediaCalculada.setNomeCarro(edtNomeCarro.getText().toString());
                mediaCalculada.setCombustivel(combustivel);
                mediaCalculada.setMedia(Double.toString(media));

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(HomeActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("MediaCar")
                                .setContentText("No combustivel " + combustivel + " a média foi de " + media + " KM/l");
                Intent resultIntent = new Intent(HomeActivity.this, HomeActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(HomeActivity.this);
                stackBuilder.addParentStack(HomeActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Random random = new Random(100);
                mNotificationManager.notify(random.nextInt(), mBuilder.build());

                mediaCalculada.insert(HomeActivity.this);

                edtNomeCarro.setText("");
                edtKm.setText("");
                edtLitros.setText("");

                adapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onStop(){
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activityhome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAtualizar: {
                listMedias.clear();
                listMedias = Media.select(HomeActivity.this);
                adapter = new AdapterMedia(HomeActivity.this,listMedias);
                lstMedias.setAdapter(adapter);
                return true;
            }
            default:
                return true;
        }

    }
    public void alertShow(String mensagem){
        AlertDialog.Builder dialogs = new AlertDialog.Builder(HomeActivity.this);
        dialogs.setTitle("Aviso");
        dialogs.setMessage(mensagem);
        dialogs.show();
    }

    public boolean isNullOrWhitespaceOrEmpty(String s) {
        return s == null || s.length() == 0 || isWhitespace(s);

    }
    private boolean isWhitespace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
