package com.sol.foodvalidity.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sol.foodvalidity.model.User;

public class UserDao extends AbstractDaoBase {

	public static final String TABLE_NAME = "user";
	public static final String KEY = "id";
	public static final String USER_NAME = "name";
	public static final String USER_PASSWORD = "password";
	public static final String USER_EMAIL = "email";
	public static final String USER_ADRESS = "adress";

	public static final String[] USER_COLUMNS = {KEY, USER_NAME, USER_PASSWORD,
		USER_EMAIL, USER_ADRESS};

	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + " (" + 
					KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					USER_NAME + " TEXT, " + 
					USER_PASSWORD + " TEXT, " + 
					USER_EMAIL + " TEXT, " + 
					USER_ADRESS + " TEXT);";

	public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	public UserDao(Context pContext) {
		super(pContext);
	}

	/**
	 * @param user
	 */
	public long add(User user) {
		ContentValues value = fillUser(user);
		return dataBase.insert(TABLE_NAME, null, value);
	}

	/**
	 * @param user
	 */
	public long addOnly(User user) {
		open();
		ContentValues value = fillUser(user);
		long insertResult = dataBase.insert(TABLE_NAME, null, value);
		close();
		return insertResult;
	}

	private ContentValues fillUser(User user) {
		ContentValues value = new ContentValues();
		value.put(USER_NAME, user.getName());
		value.put(USER_PASSWORD, user.getPassword());
		value.put(USER_EMAIL, user.getEmail());
		value.put(USER_ADRESS, user.getAdress());
		return value;
	}

	/**
	 * @param id
	 *            l'identifiant du m�tier � supprimer
	 */
	public void delete(long id) {
		dataBase.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
	}

	/**
	 * @param m
	 *            le m�tier modifi�
	 */
	public void update(User user) {
		ContentValues value = fillUser(user);
		dataBase.update(TABLE_NAME, value, KEY  + " = ?", 
				new String[] {String.valueOf(user.getId())});
	}

	/**
	 * @param id
	 *            l'identifiant du m�tier � r�cup�rer
	 */
	public User getById(long id) {
		return (User) dataBase.query(TABLE_NAME, USER_COLUMNS, KEY + "= ?", 
				new String[] {String.valueOf(id)}, null, null, null);
//		Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
	}

	public List<User> getAll() {
		Cursor cursor = dataBase.query(TABLE_NAME, USER_COLUMNS, null, 
				null, null, null, null);
		List<User> users = new ArrayList<User>();
		while (cursor.moveToNext()) {
			Date date = new Date();
			users.add(new User(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), 
					cursor.getString(4), date));
		}
		cursor.close();
		return users;
	}

}
