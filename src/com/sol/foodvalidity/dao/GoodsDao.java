package com.sol.foodvalidity.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.sol.foodvalidity.model.Food;

public class GoodsDao extends AbstractDaoBase {

	private static GoodsDao instance;
	public static final String TABLE_NAME = "goods";
	public static final String KEY = "id";
	public static final String GOODS_NAME = "name";
	public static final String GOODS_QUANTITY = "quantity";
	public static final String GOODS_DATE_VALIDITY = "dateValidity";
	public static final String GOODS_REMIND_BEFORE = "remindBefore";

	public static final String[] GOODS_COLUMNS = {KEY, GOODS_NAME, GOODS_QUANTITY,
		GOODS_DATE_VALIDITY, GOODS_REMIND_BEFORE};

	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + " (" + 
					KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					GOODS_NAME + " TEXT, " + 
					GOODS_QUANTITY + " TEXT, " + 
					GOODS_DATE_VALIDITY + " TEXT, " + 
					GOODS_REMIND_BEFORE + " TEXT);";

	public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	private GoodsDao(Context pContext) {
		super(pContext);
	}
	
	public static GoodsDao getInstance(Context pContext) {
		if (instance == null) {
			 synchronized (GoodsDao.class) {
				 if (instance == null) {
					 instance = new GoodsDao(pContext);
				 }
			 }
		}
		return instance;
	}
	public static GoodsDao getInstance() {
		if (instance == null) {
			synchronized (GoodsDao.class) {
				if (instance == null) {
					Log.e("context not found", "call getInstance(Context pContext) before calling getInstance()");
				}
			}
		}
		return instance;
	}

	/**
	 * @param goods to be added to base
	 */
	public long add(Food goods) {
		ContentValues value = fillGoods(goods);
		return dataBase.insert(TABLE_NAME, null, value);
	}
	
	/** 
	 * Add good and close connection
	 * @param goods to be added to base
	 */
	public long addOnly(Food goods) {
		open();
		ContentValues value = fillGoods(goods);
		long rowId = dataBase.insert(TABLE_NAME, null, value);
		close();
		return rowId;
	}

	private ContentValues fillGoods(Food goods) {
		ContentValues value = new ContentValues();
		value.put(GOODS_NAME, goods.getName());
		value.put(GOODS_QUANTITY, goods.getQuantity());
		if (goods.getDateValidity() != null) {
			value.put(GOODS_DATE_VALIDITY, goods.getDateValidity().getTimeInMillis());
		}
		if (goods.getRemindBefore() != null) {
			value.put(GOODS_REMIND_BEFORE, goods.getRemindBefore().getTimeInMillis());
		}		
		return value;
	}

	/**
	 * @param id food to be deleted
	 */
	public void delete(long id) {
		dataBase.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
	}
	
	/**
	 * @param id food to be deleted
	 */
	public void deleteOnly(long id) {
		open();
		dataBase.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
		close();
	}

	/**
	 * @param goods to be updated
	 */
	public void update(Food goods) {
		ContentValues value = fillGoods(goods);
		dataBase.update(TABLE_NAME, value, KEY  + " = ?", 
				new String[] {String.valueOf(goods.getId())});
	}
	
	/**
	 * @param goods to be updated
	 */
	public void updateOnly(Food goods) {
		open();
		ContentValues value = fillGoods(goods);
		dataBase.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(goods.getId())});
		close();
	}

	public Food getById(long id) {
		Cursor cursor = dataBase.query(TABLE_NAME, GOODS_COLUMNS, KEY + "=?", 
				new String[] {String.valueOf(id)}, null, null, null);
		if (cursor instanceof Food) {
			return (Food) cursor;
		} 
		else {
			List<Food> goods = getGoodsListFromCursor(cursor);
			cursor.close();
			return goods.get(0);
		}
//		Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
	}

	public Food getByIdOnly(long id) {
		open();
		Cursor cursor = dataBase.query(TABLE_NAME, GOODS_COLUMNS, KEY + "=?", 
				new String[] {String.valueOf(id)}, null, null, null);
		if (cursor instanceof Food) {
			close();
			return (Food) cursor;
		} 
		else {
			List<Food> goods = getGoodsListFromCursor(cursor);
			cursor.close();
			close();
			if (goods != null) {
				return goods.get(0);
			}
			else{
				return null;
			}
		}
//		Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
	}

	public List<Food> getAll() {
		Cursor cursor = dataBase.query(TABLE_NAME, GOODS_COLUMNS, null, 
				null, null, null, null);
		List<Food> goodsList = getGoodsListFromCursor(cursor);
		cursor.close();
		return goodsList;
	}

	public List<Food> getAllOnly() {
		open();
		Cursor cursor = dataBase.query(TABLE_NAME, GOODS_COLUMNS, null, 
				null, null, null, null);
		List<Food> goodsList = getGoodsListFromCursor(cursor);
		cursor.close();
		close();
		return goodsList;
	}

	protected List<Food> getGoodsListFromCursor(Cursor cursor) {
		List<Food> goodsList = new ArrayList<Food>();
		while (cursor.moveToNext()) {
			goodsList.add(new Food(cursor.getLong(0), cursor.getString(1), cursor.getString(2), 
					getDateFormLong(cursor.getLong(3)), getDateFormLong(cursor.getLong(4))));
		}
		return goodsList;
	}
	
}
