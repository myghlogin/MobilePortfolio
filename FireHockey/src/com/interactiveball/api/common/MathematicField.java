package com.interactiveball.api.common;

/**
 * 
 * @author Mike
 * Presents a base abstract class for mathematics field. 
 * Mathematics field is defined by field function which defines field value 
 * in any point of field and composite function which form new value 
 * in any point of field as composition value of this field with given value 
 * @param <T> is type which set definition interval. This type should define each 
 * point of field.
 * @param <V> is type which set value area. This value should depend on <T> type.
 */
public abstract class MathematicField<T, V>
{
	protected boolean sensitive;      // define if field will be moved under control of other fields. if false then field is static

	public MathematicField(boolean sensitive) 
	{
		this.sensitive = sensitive;
	}
	
	public boolean isSensitive() {
		return sensitive;
	}

	public void setSensitive(boolean sensitive) {
		this.sensitive = sensitive;
	}

	/**
	 * Field function
	 * @param param defines point of field of which it is needed to calculate 
	 * value of field 
	 * @return value of field in point defined by param.
	 */
	public abstract V fieldFunction(T param);   
	
	/**
	 * Composition function. Calculate composition value of field in point 
	 * defined by param with value defined by value 
	 * @param param defines point in which it is needed to execute composition 
	 * @param value defines another value with which it is needed to execute composition
	 * @return
	 */
	public abstract V composite(T param, V value);
}
