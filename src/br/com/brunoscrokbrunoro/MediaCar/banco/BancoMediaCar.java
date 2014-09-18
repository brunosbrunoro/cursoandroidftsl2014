package br.com.brunoscrokbrunoro.MediaCar.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.brunoscrokbrunoro.MediaCar.R;


public class BancoMediaCar extends BancoManager {
	public static final String NAME = "mediacarbd";
	public static final String TAG_LOG = "BancoMediaCar";
	public static final int VERSAO = 2;

	public BancoMediaCar(Context context) {
		super(context, NAME, VERSAO);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase bd) {
		try {
			byFile(R.raw.create, bd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase bd, int versaoAtual, int versaoNova) {
		try {
		} catch (Exception e) {
		}
	}
}