package com.sol.foodvalidity.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractDaoBase {

	protected final static int VERSION = 2;

	protected final static String NOM = "foodValidityBase.db";

	protected SQLiteDatabase dataBase = null;
	protected DatabaseHandler baseHandler = null;

	public AbstractDaoBase(Context pContext) {
		this.baseHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
	}

	public SQLiteDatabase open() {
		// Pas besoin de fermer la derniére base puisque getWritableDatabase s'en charge
		dataBase = baseHandler.getWritableDatabase();
		return dataBase;
	}

	public void close() {
		dataBase.close();
	}

	public SQLiteDatabase getDb() {
		return dataBase;
	}
	
	protected Calendar getDateFormLong(Long dateMillis) {
		Calendar date = new GregorianCalendar(); 
		date.setTimeInMillis(dateMillis);
		return date;
	}
}
