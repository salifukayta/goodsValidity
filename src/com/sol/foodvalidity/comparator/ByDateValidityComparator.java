package com.sol.foodvalidity.comparator;

import com.sol.foodvalidity.model.Food;

/**
 * By food date validity comparator
 * 
 * @author salifukayta
 */
public class ByDateValidityComparator extends DefaultFoodComparator {

	@Override
	public int compare(Food food1, Food food2) {
		return (super.compare(food1, food2)!=0?super.compare(food1, food2):
			food1.getDateValidity().compareTo(food2.getDateValidity()));
	}

}
