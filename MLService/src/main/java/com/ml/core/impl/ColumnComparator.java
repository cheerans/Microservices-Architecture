package com.ml.core.impl;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;

import com.ml.dto.DataColumn;
import com.ml.dto.DataColumn.ValueTypeEnum;
import com.ml.dto.DataRow;

public class ColumnComparator implements Comparator{

	private DataColumn col = null;
	
	ColumnComparator(DataColumn col){
		this.col = col;
	}
	
	public int compare(Object r1,Object r2){
		
		int retVal = 0;
		Object ob1 = ((DataRow)r1).getColumn(col.getColIndex());
		Object ob2 = ((DataRow)r2).getColumn(col.getColIndex());
		ValueTypeEnum type = col.getType();
		if(	(null == ob1) && 
			(null != ob2)){
			retVal = -1;
		}else if(	(null == ob2) && 
					(null != ob1)){
			retVal = 1;
		}else{
		
			switch(type){			
			case INT_TYPE:{
				retVal = ((Integer)ob1 - (Integer) ob2);
			}break;
			case DOUBLE_TYPE:{
				retVal = (int)((Double)ob1 - (Double)ob2);
			}break;
			case STRING_TYPE:{
				retVal = ((String)ob1).compareTo((String)ob2);
			}break;
			case BOOL_TYPE:{
				retVal = Boolean.compare((Boolean)ob1, (Boolean) ob2);
			}break;
			case STRINGBOOL_TYPE:{
				Boolean v1,v2;
				v1 = (false == ((null == r1) || (0 != ((String)r1).compareToIgnoreCase(DataColumn.N)) || (0 != ((String)r1).compareToIgnoreCase(DataColumn.NO))));
				v2 = (false == ((null == r2) || (0 != ((String)r2).compareToIgnoreCase(DataColumn.Y)) || (0 != ((String)r2).compareToIgnoreCase(DataColumn.Y))));
				retVal = Boolean.compare(v1, v2);
			}
			break;
			case DATE_TYPE:{
				retVal = ((Date)ob1).compareTo((Date)ob2);
			}
			break;
			case TIME_TYPE:{
				retVal = ((Timestamp)ob1).compareTo((Timestamp)ob2);
			}
			break;
		}			
		}
		return retVal;
	}
}
