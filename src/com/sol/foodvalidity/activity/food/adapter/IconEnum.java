package com.sol.foodvalidity.activity.food.adapter;

import com.sol.foodvalidity.R;

public enum IconEnum {
	
	WARNING (R.drawable.warning),
	DANGER (R.drawable.danger);
	
	private int theEnum;

	IconEnum(int theEnum) {
		this.theEnum = theEnum;
	}
	
	public int getImageSource() {
		return theEnum;
	}

}
