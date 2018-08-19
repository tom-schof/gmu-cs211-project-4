import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;

public abstract class TreeNode  {

	static int count; //
	private String id; //
	private List<TreeNode> children = new ArrayList<TreeNode>(); //
	private TreeNode parent; //

	public TreeNode(List<TreeNode> children) {
		count++;
		this.id = Integer.toString(count);
		this.children = children;
		if (children != null) {
			for (int ii = 0; ii < children.size(); ii++) {
				children.get(ii).setParent(this);
			}
		}
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		TreeNode.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void addChild(TreeNode child) {
		children.add(child);
		child.setParent(this);
	}


	
	
	

		
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
