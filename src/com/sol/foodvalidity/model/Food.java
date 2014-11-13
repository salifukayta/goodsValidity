package com.sol.foodvalidity.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Food implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String quantity;
	// end validity date
	private Calendar dateValidity;
	// to be reminded in "remindBefore" before "dateValidity"
	private Calendar remindBefore;
	private String imageUrl;

	public Food() {
		super();
	}

	public Food(String name, String quantity, Calendar dateValidity, Calendar remindBefore) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.dateValidity = dateValidity;
		this.remindBefore = remindBefore;
	}

	public Food(Long id, String name, String quantity, Calendar dateValidity, Calendar remindBefore) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.dateValidity = dateValidity;
		this.remindBefore = remindBefore;
	}

	public Food(Long id, String name, String quantity, Calendar dateValidity, Calendar remindBefore, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.dateValidity = dateValidity;
		this.remindBefore = remindBefore;
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Calendar getDateValidity() {
		return dateValidity;
	}

	public void setDateValidity(Calendar dateValidity) {
		this.dateValidity = dateValidity;
	}

	public Calendar getRemindBefore() {
		return remindBefore;
	}

	public void setRemindBefore(Calendar remindBefore) {
		this.remindBefore = remindBefore;
		setTimeOnSevenAm();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	private void setTimeOnSevenAm() {
		// remaind on 7h00
		this.remindBefore.set(Calendar.HOUR_OF_DAY, 7);
		this.remindBefore.set(Calendar.MINUTE, 0);
		this.remindBefore.set(Calendar.SECOND, 0);
		this.remindBefore.set(Calendar.AM_PM,Calendar.AM);
	}

	@Override
	public String toString() {
		DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG);
		try {
			return "Goods [id=" + id + ", name=" + name + ", quantity=" + quantity
					+ ", dateValidity=" + dateFormat.format((dateValidity!=null?dateValidity.getTime():""))
					+ ", remindBefore=" + dateFormat.format((remindBefore!=null?remindBefore.getTime():"")) + "]";			
		}
		catch(IllegalArgumentException e) {
			return "Goods [id=" + id + ", name=" + name + ", quantity=" + quantity + "]";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Food other = (Food) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	
}
