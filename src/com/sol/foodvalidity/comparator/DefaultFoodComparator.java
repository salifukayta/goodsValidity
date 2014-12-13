package com.sol.foodvalidity.comparator;

import java.util.Comparator;

import com.sol.foodvalidity.model.Food;

/**
 * Abstract Default food comparator
 * 
 * @author salifukayta
 */
abstract class DefaultFoodComparator implements Comparator<Food>{

	@Override
	public int compare(Food food1, Food food2) {
		if (food1 == null) {
			return 1;
		}
		if (food2 == null) {
			return -1; 
		}
		return 0;
	}

}
