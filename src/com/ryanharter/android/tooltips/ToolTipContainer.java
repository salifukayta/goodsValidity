package com.ryanharter.android.tooltips;

import android.widget.ImageView;

import com.sol.foodvalidity.activity.food.adapter.IconEnum;

public class ToolTipContainer {
	
	private ImageView iconState;
	private ToolTipLayout tipContainer;
	private Boolean isVisible;
	private IconEnum iconEnum;

	public ToolTipContainer(IconEnum iconEnum, ImageView iconState, ToolTipLayout tipContainer) {
		super();
		this.iconEnum = iconEnum;
		this.iconState = iconState;
		this.tipContainer = tipContainer;
		this.isVisible = false;
	}

	public ImageView getIconState() {
		return iconState;
	}

	public void setIconState(ImageView iconState) {
		this.iconState = iconState;
	}

	public ToolTipLayout getTipContainer() {
		return tipContainer;
	}

	public void setTipContainer(ToolTipLayout tipContainer) {
		this.tipContainer = tipContainer;
	}

	public Boolean isVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void reverseVisibility() {
		this.isVisible = !this.isVisible;
	}

	public IconEnum getIconEnum() {
		return iconEnum;
	}

	public void setIconEnum(IconEnum iconEnum) {
		this.iconEnum = iconEnum;
	}

}
