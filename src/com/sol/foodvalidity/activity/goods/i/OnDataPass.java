package com.sol.foodvalidity.activity.goods.i;



/**
 * 
 * @author salifukayta
 *
 * @param <E>
 * @param <T>
 */
public interface OnDataPass<E, T> {
	
	/**
	 * 
	 * @param data
	 * @param typeData
	 */
	void onDataPass(E data, T typeData);
	
	/**
	 * 
	 * @return
	 */
	E getClicked();
}
