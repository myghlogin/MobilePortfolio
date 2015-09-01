package com.interactiveball.api.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FieldComposer<T, V> 
{
	private List<MathematicField<T, V>> fields;
	private List<MathematicField<T, V>> uncompositeFields;
	private V resultValue = null;
	
	public FieldComposer()
	{
		fields = new LinkedList<MathematicField<T, V>>();
		uncompositeFields = new LinkedList<MathematicField<T, V>>();
	}
	
	public void add(MathematicField<T, V> field)
	{
		fields.add(field);
	}
	
	public void remove(MathematicField<T, V> field)
	{
		fields.remove(field);
	}	
	
	public void addUncompositeField(MathematicField<T, V> field)
	{
		uncompositeFields.add(field);
	}

	public void removeUncompositeField(MathematicField<T, V> field)
	{
		uncompositeFields.remove(field);
	}

	public void clearUncompositeFields()
	{
		uncompositeFields.clear();
	}
	
	public V composite(T params)
	{
		Iterator<MathematicField<T, V>> iterator = fields.iterator();
		MathematicField<T, V> mathField; 
		
		resultValue = null;
	
		while(iterator.hasNext())
		{
			mathField = iterator.next();
			
			if(!uncompositeFields.contains(mathField))
			{
				resultValue = mathField.fieldFunction(params);
				break;
			}
		}
		
		while(iterator.hasNext())
		{
			mathField = iterator.next();
			
			if(!uncompositeFields.contains(mathField))
				resultValue = mathField.composite(params, resultValue);	
		}
		
		return resultValue;		
	}
	
}
