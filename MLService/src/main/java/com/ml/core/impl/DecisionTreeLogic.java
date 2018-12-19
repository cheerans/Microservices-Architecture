package com.ml.core.impl;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.ml.dto.DataColumn;
import com.ml.dto.DataHolder;
import com.ml.dto.DataRow;
import com.ml.dto.DecisionTreeNode;

public class DecisionTreeLogic {
	
    /** Logger. */
    private static Logger log = Logger.getLogger(DecisionTreeLogic.class);
	
    /**
     * When data is considered homogeneous and node becomes leaf
     *  and is labeled. If it is equal 1.0 then absolutely all
     * data must be of the same label that node would be considered a leaf.
     */
    private static double homogenityPercentage = 0.95;
    private static double decisionMajorityPercentage = 0.75;	
    public static final String COMMADELIMITER = ",";
    public static final String TABDELIMITER = "\t";
	
	public static void learn(List<DataRow> analyzedData, List<DataColumn> colsToLearnAbout, DecisionPredicate decisionToBeMadeForCol){
		
		DataHolder.getInstance().setColumnsToBuildDecisionFor(colsToLearnAbout);
		DataHolder.getInstance().setDecisionToBeMadeColumn(decisionToBeMadeForCol);
		DataHolder.getInstance().setAnalyzedData(analyzedData);
		if(null != DataHolder.getInstance().getAnalyzedData()){			
			DecisionTreeNode decisionTree = buildDecisionTree(DataHolder.getInstance().getAnalyzedData(),colsToLearnAbout,1);
			DataHolder.getInstance().setTree(decisionTree);
		}
	}
	
	private static final int MAX_DEPTH = 15;
	protected static DecisionTreeNode buildDecisionTree(List<DataRow> learningData, List<DataColumn> cols, int currentDepth) {
		
		Object homogeneousVal = null;
	    // if dataset already homogeneous enough (has label assigned) make this node a leaf
	    if ((homogeneousVal = DecisionTreeLogic.getPivotValueIfDataHomogeneous(learningData)) != null) {
	        return DecisionTreeNode.addLeafNode(homogeneousVal);
	    }
	     
	    boolean stoppingCriteriaReached = cols.isEmpty() || currentDepth >= MAX_DEPTH;
	    if (stoppingCriteriaReached) {
	        return DecisionTreeNode.addLeafNode(DecisionTreeLogic.getMajorityPivotValue(learningData));
	    }	 
	    
	    final DecisionTreeNode bestNode = DecisionTreeLogic.findBestDecisionColumnByAmountOfData(learningData, cols);
	    Map<Boolean, List<DataRow>> splitData = bestNode.getPredicate().split(learningData);
	    // remove best decision column
	    List<DataColumn> colsToBuildOn = cols.stream().filter(p -> !p.equals(bestNode.getFldDetails())).collect(Collectors.toList());

	    List<List<DataRow>> splitDataPositives = new LinkedList<List<DataRow>>();
	    splitDataPositives.add(splitData.get(Boolean.TRUE));
	    splitDataPositives.add(splitData.get(Boolean.FALSE));
	    for(List<DataRow> thisSplitPortion : splitDataPositives){
		    if(thisSplitPortion.isEmpty()) {
		    	bestNode.addDecision(DecisionTreeNode.addLeafNode((DecisionTreeLogic.getMajorityPivotValue(learningData))));
	        }else {
	        	bestNode.addDecision(buildDecisionTree(thisSplitPortion, colsToBuildOn, currentDepth + 1));
	        }
	    }
	    return bestNode;
	}
	
	public static double calculateimpurity(List<DataRow> data,DataColumn col){	

		Map<Boolean, List<DataRow>> splitData = DataHolder.getInstance().getDecisionToBeMadeColumn().split(data);
		int positive = splitData.get(true).size();
		int total = splitData.get(true).size() + splitData.get(false).size();
		double imp = ((double)positive/(double)total);
		return 2.0 * imp * (1 - imp);
	}
	
	public static DecisionTreeNode findBestDecisionColumnByAmountOfData(List<DataRow> data, List<DataColumn> colsRemainingToBuildFor) {
		 
		DecisionTreeNode retVal = null;
		double currentImpurity = 1;
		DataColumn bestDecision = null; 	 
		String medianValue = null;
		for (DataColumn col : colsRemainingToBuildFor) {	
	        double calculatedSplitImpurity = DecisionTreeLogic.calculateimpurity(data,col);
	        if (calculatedSplitImpurity < currentImpurity) {
	            currentImpurity = calculatedSplitImpurity;
	            bestDecision = col;
	        }
		}
		if(null != bestDecision){			 
			 retVal = getDecisionPivotValueForAColumn(data,bestDecision);
		}
	    return retVal;
	}
	
    public static Object makeDecision(DataRow row) {
        DecisionTreeNode node = DataHolder.getInstance().getTree();
        while (!node.isLeafNode()) { // go through tree until leaf is reached
            // only binary splits for now - has feature first child node(left branch), does not have feature second child node(right branch).
            if (node.getPredicate().canAccept(row)) {
                node = node.getChildren().get(0); 
            } else {
                node = node.getChildren().get(1);
            }
        }
        return node.getLeaf();
    }
	
	
    /**
     * Returns Label if data is homogeneous. 
     * The logic is to find a value where to pivot BASED on homogenityPercentage
     */
    protected static DecisionTreeNode getDecisionPivotValueForAColumn(List<DataRow> rowList, DataColumn col) {
    	
    	DecisionTreeNode retVal = null;
        rowList.sort(new ColumnComparator(col));
        int totalCount = rowList.size();
        int index = ((int)(totalCount * decisionMajorityPercentage));
        if(index > (totalCount-1)){
        	index = (totalCount-1);
        }
        retVal = new DecisionTreeNode(rowList.get(index).getColumn(col.getColIndex()),col);
        return retVal;
    }
    
    /**
     * Returns Label if data is homogeneous.
     */
    protected static Object getPivotValueIfDataHomogeneous(List<DataRow> data) {

    	Object retVal = null;
        Map<Object, Long> distinctValCount = data.parallelStream().collect(groupingBy(DataRow::getPivotColumnValue, counting()));
        long totalCount = data.size();
        for (Object value : distinctValCount.keySet()) {
            long nbrOfValues = distinctValCount.get(value);
            if (((double) nbrOfValues / (double) totalCount) >= homogenityPercentage) {
            	retVal = value;
            	break;
            }
        }
        return retVal;
    }
	
	public static Object getMajorityPivotValue(List<DataRow> data) {
		
        Object majValue =  data.parallelStream().collect(groupingBy(DataRow::getPivotColumnValue, counting())).entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        return majValue;
    }
	
	public static List<String> parseData(String line, String delimiter){
    	List<String> dataArr = new ArrayList<String>();
    	String[] arr = line.split(delimiter);
    	if(null != arr){
    		dataArr = Arrays.asList(arr);
    	}
    	return dataArr;
    }		
	
	public static List<DataRow> readFile(String fileName, Class clazz, List<DataColumn> colsToLearnAbout){
		
        InputStreamReader stream = new InputStreamReader(clazz.getResourceAsStream(fileName));
        ICsvListReader listReader = new CsvListReader(stream, CsvPreference.STANDARD_PREFERENCE);       
		List<DataRow> retVal = new ArrayList<DataRow>();
		
		try {
            // the header elements are used to map the values to the bean (names must match)
            final String[] header = listReader.getHeader(true);
		    List<String> read = null;
		    while ((read = listReader.read()) != null) {
		    	retVal.add((new DataRow.Builder()).setRowData(read).build(colsToLearnAbout));
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return retVal;
    }
	
	public static void readColumnIndexes(String fileName, Class loaderClazz, List<DataColumn> colsToLearnAbout){
		
		readColumnIndexes(fileName, loaderClazz, colsToLearnAbout,DataHolder.getInstance().getDecisionToBeMadeColumn());
	}
        
	public static void readColumnIndexes(String fileName, Class loaderClazz, List<DataColumn> colsToLearnAbout,DecisionPredicate pivotColPred){
		
        InputStreamReader stream = new InputStreamReader(loaderClazz.getResourceAsStream(fileName));
        ICsvListReader listReader = new CsvListReader(stream, CsvPreference.STANDARD_PREFERENCE);            
        try {
			final String[] header = listReader.getHeader(true);
			int colIndex = 0;
			int arrIndex = 0;
			if(null != header){
				for(String colName : header){
					for(DataColumn col : colsToLearnAbout){
						if(col.getColName().equals(colName)){
							colsToLearnAbout.set(	arrIndex, 
													new DataColumn(	colIndex,
																	col.getColName(),
																	col.getType()));							
							break;
						}else if(pivotColPred.getColDetails().getColName().equals(colName)){

							pivotColPred.setColDetails(new DataColumn(	pivotColPred.getColDetails().getColIndex(),
																		pivotColPred.getColDetails().getColName(),
																		pivotColPred.getColDetails().getType()));
							break;
						}	
						arrIndex++;						
					}	
					arrIndex = 0;
					colIndex ++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }	        	
}	