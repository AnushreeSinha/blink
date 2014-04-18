package com.blink.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class TreeMaker {

	Map<Long, TreeNode> index = new HashMap<Long, TreeNode> ();
	Gson gson = new Gson();
	public  String convertJSON (final List<TreeNode> nodes) {

		TreeNode rootNode = init(nodes);

		for(TreeNode node: nodes) {
			addToParent(node);
		}
		return convertNodeToJSON(rootNode) ;

	}

	private String convertNodeToJSON(TreeNode node)  {
		System.out.println(" Processing " + node );
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\nid:'");
		sb.append(node.getId()); 
		sb.append("', \ntext:'");
		sb.append(node.getText());
		sb.append("'");
		if( node.getChildren() != null) {
			sb.append(", \nchildren:[");

			for(TreeNode childNode: node.getChildren()) {
				sb.append(convertNodeToJSON(childNode));
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("\n]");
		}
		else {
			sb.append(",leaf: true");
		}
		sb.append("}");


		return sb.toString().replace("'", "\"");


	}

	private TreeNode init(List<TreeNode> nodes ) {
		TreeNode rootNode = null; 
		for( TreeNode treeNode : nodes) {
			index.put(treeNode.getId(),  treeNode);
			if(  treeNode.getParentNodeId() == 0 ) {
				rootNode = treeNode;	
			}	
		}

		return rootNode;
	}

	private void addToParent(TreeNode node) {
		if( node.getParentNodeId() ==0 )
			return;
		TreeNode parentNode = index.get(node.getParentNodeId());
		if( parentNode.getChildren() == null ) {
			parentNode.setChildren( new ArrayList<TreeNode>());
		}

		parentNode.getChildren().add(node);
	}


	public String  main() {
		List<TreeNode> treeNodes = new ArrayList<TreeNode> ();
		treeNodes.add(new TreeNode(1l,"a",0l, false, false));
		treeNodes.add(new TreeNode(2l,"b",1l ,false, false));
		treeNodes.add(new TreeNode(3l, "c", 2l,false, false));
		treeNodes.add(new TreeNode(4l, "d", 2l,false, false));
		treeNodes.add(new TreeNode(5l, "e", 4l,false, true));
		treeNodes.add(new TreeNode(6l, "f", 4l,false, true));

		return new TreeMaker().convertJSON(treeNodes);
	}
}


