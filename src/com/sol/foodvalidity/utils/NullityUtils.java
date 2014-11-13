package com.sol.foodvalidity.utils;


public class NullityUtils {

	public static boolean isNull(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Object object) {
		if (object != null) {
			return true;
		}
		return false;
	}
	
}
