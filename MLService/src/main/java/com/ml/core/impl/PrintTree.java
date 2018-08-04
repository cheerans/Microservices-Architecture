package com.ml.core.impl;

import com.ml.dto.DecisionTreeNode;

public class PrintTree {

    public static void printTree(DecisionTreeNode root) {
        printSubtree(root);
    }

    public static void printSubtree(DecisionTreeNode node) {
        if (!node.getChildren().isEmpty() && node.getChildren().get(0) != null) {
            printTree(node.getChildren().get(0), true, "");
        }
        printNodeValue(node);
        if (node.getChildren().size() > 1 && node.getChildren().get(1) != null) {
            printTree(node.getChildren().get(1), false, "");
        }
    }

    private static void printNodeValue(DecisionTreeNode node) {
        if (node.isLeafNode()) {
            System.out.print(node.getLeaf());
        } else {
            System.out.print(node.getValue().getDecisionVal());
        }
        System.out.println();
    }

    private static void printTree(DecisionTreeNode node, boolean isRight, String indent) {
    	
        if (!node.getChildren().isEmpty() && node.getChildren().get(0) != null) {
            printTree(node.getChildren().get(0), true, indent + (isRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        printNodeValue(node);
        if (node.getChildren().size() > 1 && node.getChildren().get(1) != null) {
            printTree(node.getChildren().get(1), false, indent + (isRight ? " |      " : "        "));
        }
    }
}
