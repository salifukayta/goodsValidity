package com.sol.foodvalidity.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.sol.foodvalidity.model.Food;

public class FoodDao extends AbstractDaoBase {

	private static FoodDao instance;
	public static final String TABLE_NAME = "food";
	public static final String KEY = "id";
	public static final String FOOS_NAME = "name";
	public static final String FOOD_QUANTITY = "quantity";
	public static final String FOOD_DATE_VALIDITY = "dateValidity";
	public static final String FOOD_REMIND_BEFORE = "remindBefore";
	public static final String FOOD_PICTURE_URL = "pictureUrl";

	public static final String[] FOOD_COLUMNS = {KEY, FOOS_NAME, FOOD_QUANTITY,
		FOOD_DATE_VALIDITY, FOOD_REMIND_BEFORE, FOOD_PICTURE_URL};

	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + " (" + 
					KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					FOOS_NAME + " TEXT, " + 
					FOOD_QUANTITY + " TEXT, " + 
					FOOD_DATE_VALIDITY + " TEXT, " + 
					FOOD_REMIND_BEFORE + " TEXT," +
					FOOD_PICTURE_URL + " TEXT);";

	public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	private FoodDao(Context pContext) {
		super(pContext);
	}
	
	public static FoodDao getInstance(Context pContext) {
		if (instance == null) {
			 synchronized (FoodDao.class) {
				 if (instance == null) {
					 instance = new FoodDao(pContext);
				 }
			 }
		}
		return instance;
	}
	public static FoodDao getInstance() {
		if (instance == null) {
			synchronized (FoodDao.class) {
				if (instance == null) {
					Log.e("context not found", "call getInstance(Context pContext) before calling getInstance()");
				}
			}
		}
		return instance;
	}

	/**
	 * @param food to be added to base
	 */
	public long add(Food food) {
		ContentValues value = fillFood(food);
		return dataBase.insert(TABLE_NAME, null, value);
	}
	
	/** 
	 * Add good and close connection
	 * @param food to be added to base
	 */
	public long addOnly(Food food) {
		open();
		ContentValues value = fillFood(food);
		long rowId = dataBase.insert(TABLE_NAME, null, value);
		close();
		return rowId;
	}

	private ContentValues fillFood(Food food) {
		ContentValues value = new ContentValues();
		value.put(FOOS_NAME, food.getName());
		value.put(FOOD_QUANTITY, food.getQuantity());
		if (food.getDateValidity() != null) {
			value.put(FOOD_DATE_VALIDITY, food.getDateValidity().getTimeInMillis());
		}
		if (food.getRemindBefore() != null) {
			value.put(FOOD_REMIND_BEFORE, food.getRemindBefore().getTimeInMillis());
		}		
		value.put(FOOD_PICTURE_URL, food.getPictureUrl());
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
	public int deleteOnly(long id) {
		open();
		int nbRowAffedted = dataBase.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
		close();
		return nbRowAffedted;
	}

	/**
	 * @param food to be updated
	 */
	public void update(Food food) {
		ContentValues value = fillFood(food);
		dataBase.update(TABLE_NAME, value, KEY  + " = ?", 
				new String[] {String.valueOf(food.getId())});
	}
	
	/**
	 * @param food to be updated
	 */
	public void updateOnly(Food food) {
		open();
		ContentValues value = fillFood(food);
		dataBase.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(food.getId())});
		close();
	}

	public Food getById(long id) {
		Cursor cursor = dataBase.query(TABLE_NAME, FOOD_COLUMNS, KEY + "=?", 
				new String[] {String.valueOf(id)}, null, null, null);
		if (cursor instanceof Food) {
			return (Food) cursor;
		} 
		else {
			List<Food> food = getFoodListFromCursor(cursor);
			cursor.close();
			return food.get(0);
		}
//		Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
	}

	public Food getByIdOnly(long id) {
		open();
		Cursor cursor = dataBase.query(TABLE_NAME, FOOD_COLUMNS, KEY + "=?", 
				new String[] {String.valueOf(id)}, null, null, null);
		if (cursor instanceof Food) {
			close();
			return (Food) cursor;
		} 
		else {
			List<Food> food = getFoodListFromCursor(cursor);
			cursor.close();
			close();
			if (food != null) {
				return food.get(0);
			}
			else{
				return null;
			}
		}
//		Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
	}

	public List<Food> getAll() {
		Cursor cursor = dataBase.query(TABLE_NAME, FOOD_COLUMNS, null, 
				null, null, null, null);
		List<Food> foodList = getFoodListFromCursor(cursor);
		cursor.close();
		return foodList;
	}

	public List<Food> getAllOnly() {
		open();
		Cursor cursor = dataBase.query(TABLE_NAME, FOOD_COLUMNS, null, 
				null, null, null, null);
		List<Food> foodList = getFoodListFromCursor(cursor);
		cursor.close();
		close();
		return foodList;
	}

	protected List<Food> getFoodListFromCursor(Cursor cursor) {
		List<Food> foodList = new ArrayList<Food>();
		while (cursor.moveToNext()) {
			foodList.add(new Food(cursor.getLong(0), cursor.getString(1), cursor.getString(2), 
					getDateFormLong(cursor.getLong(3)), getDateFormLong(cursor.getLong(4)), cursor.getString(5)));
		}
		return foodList;
	}
	
}
