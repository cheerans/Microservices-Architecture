package com.ml.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DataRow {
		
	private List<Object> columns = null;
	
	public DataRow() {		
		super();
	}	
	
	public DataRow(List<Object> columns) {		
		super();
		this.columns = columns;
	}
	
	public List<Object> getColumns() {
		return columns;
	}

	public Object getColumn(int iIndex) {
		return columns.get(iIndex);
	}
	
	@JsonIgnore
	public Object getPivotColumnValue(){
		
		Object ret = null;
		try{
			if(null != DataHolder.getInstance().getDecisionToBeMadeColumn()){
				ret = columns.get(DataHolder.getInstance().getDecisionToBeMadeColumn().getColDetails().getColIndex());
			}			
		}catch(Exception e){
			ret = null;
		}
		return ret;
	}
	
	public static class Builder{
		
		private List<String> rowData;
		
		public Builder() {
			super();
		}

		public Builder setRowData(List<String> data){
			this.rowData = data;
			return this;
		}
				
		public DataRow build(){	
			
			return build(DataHolder.getInstance().getColumnsToBuildDecisionFor());
		}
		
		public DataRow build(List<DataColumn> colsToBuildDecisionFor){
			
			List<Object> columns = new ArrayList<Object>(rowData.size());
			columns.addAll(rowData);	
			for(DataColumn col : colsToBuildDecisionFor){
				columns.set(col.getColIndex(), 
							DataColumn.ValueTypeEnum.convert(	rowData.get(col.getColIndex()), 
																col.getType()));
			}
			DataRow data = new DataRow(columns);
			return data;
		}		
	}
	
    public String toString() {    	
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE, true, true);
    }
}