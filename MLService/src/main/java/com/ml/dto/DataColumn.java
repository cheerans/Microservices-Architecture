package com.ml.dto;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DataColumn{
	
	private int colIndex;
	private String colName;
	private ValueTypeEnum type;
	
	
	public DataColumn(){
		

	}
	
	public DataColumn(int colIndex,String colName, ValueTypeEnum type){
		
		this.colIndex = colIndex;
		this.colName = colName;		
		this.type = type;
	}

	public int getColIndex() {
		return colIndex;
	}

	public String getColName() {
		return colName;
	}

	public ValueTypeEnum getType() {
		return type;
	}
	
    public String toString() {    	
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE, true, true);
    }
    
	public static final String YES = "YES";
	public static final String Y = "Y";
	public static final String NO = "NO";
	public static final String N = "N";	
	
	public enum ValueTypeEnum {
		
		INT_TYPE(0),
		DOUBLE_TYPE(1),
		STRING_TYPE(2),
		BOOL_TYPE(3),
		STRINGBOOL_TYPE(4),
		DATE_TYPE(5),
		TIME_TYPE(6);
		
		private int value;
		
		ValueTypeEnum(int val) {
			this.value = val;
		}

		public static Object convert(String value, ValueTypeEnum type){
			
			Object retVal = null;
			if(null != value){
			
				switch(type){			
				case INT_TYPE:{
					retVal = Integer.parseInt(value);
				}break;
				case DOUBLE_TYPE:{
					retVal = Double.parseDouble(value);
				}break;
				case STRING_TYPE:{
					retVal = value;
				}break;
				case BOOL_TYPE:{
					retVal = Boolean.parseBoolean(value);
				}break;
				case STRINGBOOL_TYPE:{					
					if(0 == YES.compareToIgnoreCase(value) || 0 == Y.compareToIgnoreCase(value)){
						retVal = new Boolean(true);
					}else if(0 == NO.compareToIgnoreCase(value) || 0 == N.compareToIgnoreCase(value)){
						retVal = new Boolean(false);
					}else{
						retVal = null;
					}
				}break;
				case DATE_TYPE:{
					try {
						retVal = DateFormat.getInstance().parse(value);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}break;
				case TIME_TYPE:{
					retVal = Timestamp.valueOf(value);
				}break;
			}				
			}
			return retVal;
		}
	}	    
}
