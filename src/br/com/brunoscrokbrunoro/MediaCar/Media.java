package br.com.brunoscrokbrunoro.MediaCar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import br.com.brunoscrokbrunoro.MediaCar.banco.Banco;
import br.com.brunoscrokbrunoro.MediaCar.banco.BancoMediaCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bruno Scrok Brunoro
 * Date: 16/09/14
 * Time: 21:58
 */
public class Media {
    private String nomeCarro;
    private String combustivel;
    private String media;

    public String getNomeCarro() {
        return nomeCarro;
    }

    public void setNomeCarro(String nomeCarro) {
        this.nomeCarro = nomeCarro;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }


    public void loadFromCursor(Cursor cursor) {
        this.setNomeCarro(cursor.getString(0));
        this.setMedia(cursor.getString(1));
        this.setCombustivel(cursor.getString(2));
    }
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("nomeCarro", this.getNomeCarro());
        values.put("media",this.getMedia());
        values.put("combustivel",this.getCombustivel());
        return values;
    }

    public static String getTableName() {
        return "TAB_Media";
    }

    public void insert(Context context) {
        Banco banco = new Banco(new BancoMediaCar(context));
        try {
            ContentValues cv = getContentValues();
            banco.open();
            banco.get().insert(getTableName(), null, cv);
            banco.close();
        } catch (SQLException e) {
            banco.close();
            e.printStackTrace();
        } catch (Exception e) {
            banco.close();
            e.printStackTrace();
        }
    }

    public void update(Context context) {
        Banco banco = new Banco(new BancoMediaCar(context));
        try {
            ContentValues cv = getContentValues();
            banco.open();
            banco.get().update(getTableName(), cv, "nomeCarro = ? and media = ? and combustivel = ?", new String[]{nomeCarro, media, combustivel});
            banco.close();
        } catch (SQLException e) {
            banco.close();
            e.printStackTrace();
        } catch (Exception e) {
            banco.close();
            e.printStackTrace();
        }
    }

    public void deletar(Context context) {
        Banco banco = new Banco(new BancoMediaCar(context));
        try {
            banco.open();
            banco.get().delete(getTableName(), "nomeCarro = ? and media = ? and combustivel = ?", new String[] {nomeCarro,media,combustivel});
            banco.close();
        } catch (SQLException e) {
            banco.close();
            e.printStackTrace();
        } catch (Exception e) {
            banco.close();
            e.printStackTrace();
        }
    }

    public static List<Media> select(Context context) {
        List<Media> select = new ArrayList<Media>();
        Banco banco = new Banco(new BancoMediaCar(context));
        try {
            banco.open();
            Cursor cursor = banco.get().rawQuery("select * from "+getTableName(), new String[]{});
            while (cursor.moveToNext()) {
                Media media = new Media();
                media.loadFromCursor(cursor);
                select.add(media);
            }
            cursor.close();
            banco.close();
        } catch (SQLException e) {
            banco.close();
            e.printStackTrace();
        } catch (Exception e) {
            banco.close();
            e.printStackTrace();
        }
        return select;
    }

}
