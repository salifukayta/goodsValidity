package com.sol.foodvalidity.comparator;

import com.sol.foodvalidity.model.Food;

/**
 * By food name comparator
 * 
 * @author salifukayta
 */
public class ByNameComparator extends DefaultFoodComparator {

	@Override
	public int compare(Food food1, Food food2) {
		return (super.compare(food1, food2)!=0?super.compare(food1, food2):
			food1.getName().compareToIgnoreCase(food2.getName()));
	}

}
