package com.ml.dto;

import java.util.List;

import com.ml.core.impl.DecisionPredicate;

public class DataHolder {
	
	static {
		_instance =  new DataHolder();
	}
	
	private static DataHolder _instance;	
	private List<DataRow> analyzedData;
	private List<DataColumn> columnsToBuildDecisionFor;
	private DecisionPredicate decisionToBeMadeColumn;
	private DecisionTreeNode tree = null;

	protected DataHolder() {		
		super();
	}
	
	public static DataHolder getInstance(){
		return _instance;
	}
	
	public List<DataRow> getAnalyzedData() {
		return analyzedData;
	}

	public void setAnalyzedData(List<DataRow> analyzedData) {
		this.analyzedData = analyzedData;
	}

	public List<DataColumn> getColumnsToBuildDecisionFor() {
		return columnsToBuildDecisionFor;
	}

	public void setColumnsToBuildDecisionFor(List<DataColumn> columnsToBuildDecisionFor) {
		this.columnsToBuildDecisionFor = columnsToBuildDecisionFor;
	}

	public DecisionPredicate getDecisionToBeMadeColumn() {
		return decisionToBeMadeColumn;
	}

	public void setDecisionToBeMadeColumn(DecisionPredicate decisionToBeMadeColumn) {
		this.decisionToBeMadeColumn = decisionToBeMadeColumn;
	}

	public DecisionTreeNode getTree() {
		return tree;
	}

	public void setTree(DecisionTreeNode tree) {
		this.tree = tree;
	}
}