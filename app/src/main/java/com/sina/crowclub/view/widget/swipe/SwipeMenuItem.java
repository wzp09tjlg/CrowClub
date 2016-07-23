package com.sina.crowclub.view.widget.swipe;


import android.content.Context;
import android.graphics.drawable.Drawable;

public class SwipeMenuItem {

	public static final int TYPE_HORIZONTAL = 0;
	public static final int TYPE_VERTICAL = 1;

	private int id;
	private Context mContext;
	private String title;
	private Drawable icon;
	private Drawable iconBackGround;
	private Drawable background;
	private int titleColor;
	private int titleSize;
	private int width;
	private int padding;
    private int type;

	public SwipeMenuItem(Context context) {
		mContext = context;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTitleColor() {
		return titleColor;
	}

	public int getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(int titleSize) {
		this.titleSize = titleSize;
	}

	public void setTitleColor(int titleColor) {
		this.titleColor = titleColor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(int resId) {
		setTitle(mContext.getString(resId));
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public Drawable getIconBackGround() {
		return iconBackGround;
	}

	public void setIconBackGround(Drawable iconBackGround) {
		this.iconBackGround = iconBackGround;
	}

	public void setIconBackGround(int resId) {
		this.iconBackGround = mContext.getResources().getDrawable(resId);
	}

	public void setIcon(int resId) {
		this.icon = mContext.getResources().getDrawable(resId);
	}

	public Drawable getBackground() {
		return background;
	}

	public void setBackground(Drawable background) {
		this.background = background;
	}

	public void setBackground(int resId) {
		this.background = mContext.getResources().getDrawable(resId);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public SwipeMenuItem(int padding) {
		this.padding = padding;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
