package com.sol.foodvalidity.activity.goods.model;

import com.sol.foodvalidity.activity.R;

public class ModelDatePicker {

	Integer idCurrentViewPickDate;
	Integer idOtherViewPickDate;
	Integer errorMsg;
	Integer btnDateId;
	Integer viewMsg;
		
	public ModelDatePicker() {
		super();
		this.idCurrentViewPickDate = 0;
		this.idOtherViewPickDate = 0;
		this.errorMsg = 0;
		this.btnDateId = 0;
		this.viewMsg = 0;
	}

	public ModelDatePicker(Integer idCurrentViewPickDate, Integer idOtherViewPickDate, Integer errorMsg, 
			Integer btnDateId, Integer viewMsg) {
		super();
		this.idCurrentViewPickDate = idCurrentViewPickDate;
		this.idOtherViewPickDate = idOtherViewPickDate;
		this.errorMsg = errorMsg;
		this.btnDateId = btnDateId;
		this.viewMsg = viewMsg;
	}

	public Integer getIdCurrentViewPickDate() {
		return idCurrentViewPickDate;
	}

	public void setIdCurrentViewPickDate(Integer idCurrentViewPickDate) {
		this.idCurrentViewPickDate = idCurrentViewPickDate;
	}

	public Integer getIdOtherViewPickDate() {
		return idOtherViewPickDate;
	}

	public void setIdOtherViewPickDate(Integer idOtherViewPickDate) {
		this.idOtherViewPickDate = idOtherViewPickDate;
	}

	public Integer getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(Integer errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getBtnDateId() {
		return btnDateId;
	}

	public void setBtnDateId(Integer btnDateId) {
		this.btnDateId = btnDateId;
	}

	public Integer getViewMsg() {
		return viewMsg;
	}

	public void setViewMsg(Integer viewMsg) {
		this.viewMsg = viewMsg;
	}
	
}
