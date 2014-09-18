package br.com.brunoscrokbrunoro.MediaCar.banco;

import android.database.sqlite.SQLiteDatabase;

import java.util.Locale;

public class Banco {

	public static final String TAG_LOG = "Banco";
	public static boolean aberto = false;
	public static String lock = "dblock";
	private BancoManager bancoManager;
	private SQLiteDatabase sqld;

	public Banco(BancoManager bancoManager) {
		this.bancoManager = bancoManager;
	}

	public void open() {
		synchronized (lock) {
			if (!(aberto)) {
				sqld = bancoManager.getWritableDatabase();
				sqld.setLocale(new Locale("pt_BR"));
				aberto = true;
			}
		}

	}

	public SQLiteDatabase get() {
		if (sqld != null && sqld.isOpen()) {
			return sqld;
		}
		return null;
	}

	public void close() {
		synchronized (lock) {
			if (aberto) {
				bancoManager.close();
				aberto = false;
			}
		}
	}

}