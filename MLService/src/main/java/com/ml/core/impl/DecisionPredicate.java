package com.ml.core.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ml.dto.DataColumn;
import com.ml.dto.DataRow;
import com.ml.dto.DataColumn.ValueTypeEnum;

public class DecisionPredicate implements Predicate<Object>{
	
	private DataColumn colDetails;	
	private Object decisionVal;
	
	public DecisionPredicate(Object decisionVal,DataColumn colDetails) {
		super();
		this.colDetails = colDetails;
		this.decisionVal = decisionVal;
	}

    public DataColumn getColDetails() {
		return colDetails;
	}

	public void setColDetails(DataColumn colDetails) {
		this.colDetails = colDetails;
	}

	public Object getDecisionVal() {
		return decisionVal;
	}

	public void setDecisionVal(Object decisionVal) {
		this.decisionVal = decisionVal;
	}
	
    public Map<Boolean, List<DataRow>> split(List<DataRow> data) {    	
        Map<Boolean, List<DataRow>> split = data.parallelStream().collect(Collectors.partitioningBy(datarow -> canAccept(datarow)));
        return split;
    }
	
	public boolean canAccept(DataRow row){		
		 Object val = row.getColumn(this.colDetails.getColIndex());
		 return val != null ? test(val) : false;
	}

	public boolean test(Object value) {
		boolean retVal = false;
		if(null != value){			
			if(colDetails.getType().equals(ValueTypeEnum.INT_TYPE)){
				if((Integer)(value) >= (Integer)decisionVal){
					retVal = true;
				}
			}else if(colDetails.getType().equals(ValueTypeEnum.DOUBLE_TYPE)){
				if((Double)(value) >= (Double)decisionVal){
					retVal = true;
				}
			}else if(colDetails.getType().equals(ValueTypeEnum.BOOL_TYPE)){			
				if((Boolean)(value) == (Boolean)decisionVal){
					retVal = true;
				}
			}else if(colDetails.getType().equals(ValueTypeEnum.STRING_TYPE)){							
				if(((String)(value)).equals((String)decisionVal)){
					retVal = true;
				}
			}else if(colDetails.getType().equals(ValueTypeEnum.STRINGBOOL_TYPE)){							
				if(((String)(value)).equals((String)decisionVal)){
					retVal = true;
				}
			}else if(colDetails.getType().equals(ValueTypeEnum.DATE_TYPE)){							
				if(((Date)(value)).equals((Date)decisionVal)){
					retVal = true;
				}
			}else if(colDetails.getType().equals(ValueTypeEnum.TIME_TYPE)){							
				if(((Timestamp)(value)).equals((Timestamp)decisionVal)){
					retVal = true;
				}
			}
		}
		return retVal;
	}
	
    public String toString() {    	
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE, true, true);
    }
}