package com.andreabaccega.formedittextvalidator;

import android.util.Log;
import android.widget.EditText;

/**
 * The or validator checks if passed validators is returning true.<br/>
 * Note: the message that will be shown is the one passed to the Constructor
 * 
 * @author Andrea B.
 * 
 */
public class OrValidator extends MultiValidator {

	public OrValidator(String message, Validator... validators) {
		super(message, validators);
	}

	@Override
	public boolean isValid(EditText et) {
		// TODO: What if we've no validators ?
		Log.d("OrValidator class", "isValid method, validators.size:"+validators.size());
		for (Validator v : validators) {
			if (v.isValid(et)) {
				return true; // Remember :) We're acting like an || operator.
			}
		}
		return false;
	}

}
