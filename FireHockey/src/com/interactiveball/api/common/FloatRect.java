package com.interactiveball.api.common;

public class FloatRect 
{
	public float left;
	public float top;
	public float right;
	public float bottom;
	
	public FloatRect() 
	{
		this.left = 0;
		this.top = 0;
		this.right = 0;
		this.bottom = 0;
	}
	
	public FloatRect(float left, float top, float right, float bottom) 
	{
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public float getLeft() {
		return left;
	}
	
	public void setLeft(float left) {
		this.left = left;
	}
	
	public float getTop() {
		return top;
	}
	
	public void setTop(float top) {
		this.top = top;
	}
	
	public float getRight() {
		return right;
	}
	
	public void setRight(float right) {
		this.right = right;
	}
	
	public float getBottom() {
		return bottom;
	}
	
	public void setBottom(float bottom) {
		this.bottom = bottom;
	}
	
	public float getWidth()
	{
		return Math.abs(right - left);
	}
	
	public float getHeight()
	{
		return Math.abs(bottom - top);
	}
}
