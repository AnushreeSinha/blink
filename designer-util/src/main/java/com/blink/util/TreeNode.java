package com.blink.util;

import java.util.List;

public class TreeNode {

	private Long id; 

	private String text;
	private  boolean expanded;
	private  boolean leaf;

	private Long parentNodeId; 

	private  transient List<TreeNode> children;



	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(  "TreeNode [id=" + id + ", text=" + text + "]\n");
		sb.append("\t\t\t\t\t\t");
		if(children == null) 
			return sb.toString();
		for(TreeNode treeNode: children) {
			sb.append(treeNode.getId());
			sb.append(":");
		}
		return sb.toString();

	}

	public TreeNode(Long id, String text, Long parentNodeId, boolean expanded, boolean leaf) {
		super();
		this.id = id;
		this.parentNodeId = parentNodeId;
		this.text = text;
		this.expanded = expanded;
		this.leaf = leaf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNode(Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public void setParentNodeId(Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}


}