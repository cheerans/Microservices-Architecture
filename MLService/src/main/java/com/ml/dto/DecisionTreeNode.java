package com.ml.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ml.core.impl.DecisionPredicate;

public class DecisionTreeNode {
	
	private DecisionPredicate value;
	
	private List<DecisionTreeNode> children = new ArrayList<DecisionTreeNode>();
	
	public DecisionTreeNode(Object value, DataColumn fldDetails) {
		
		super();
		this.value = new DecisionPredicate(value,fldDetails); 
	}

	public Object getDecisionVal() {
		return value.getDecisionVal();
	}	

	public DecisionPredicate getPredicate() {
		return value;
	}

	public DataColumn getFldDetails() {
		return value.getColDetails();
	}
	
	public void addDecision(DecisionTreeNode node){
		this.children.add(node);
	}
	
	public Object getLeaf(){
		return value.getDecisionVal();
	}	
	
	public static DecisionTreeNode addLeafNode(Object val){
		return new DecisionTreeNode(val, null);
	}
	
	public boolean isLeafNode(){
		return (this.value.getColDetails() == null) && (this.value.getDecisionVal() != null);
	}

    public DecisionPredicate getValue() {
		return value;
	}

	public List<DecisionTreeNode> getChildren() {
		return children;
	}

	public String toString() {    	
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE, true, true);
    }
}
