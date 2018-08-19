import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class TagNode extends TreeNode {

	private String tag;//
	private Map<String,String> attributes = new HashMap<String, String>();//
	
	public TagNode(String tag) {
		super(new ArrayList<TreeNode>());
		this.tag = tag;
		
		
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}
	
	public String getValue(String name) {
		return this.attributes.get(name);
		
	}
	
	public String mineCloseText() {
		
		java.util.List<TreeNode> children = this.getChildren();
		String textNodeText = new String();
		for ( TreeNode node : children) {
			if ( node instanceof TextNode) {
				
				textNodeText += ((TextNode) node).getText();
			}
		}
		if (super.getParent() != null) {
			for (TreeNode node : super.getParent().getChildren()) {
				if (node instanceof TextNode) {
					textNodeText += " " + ((TextNode) node).getText();
				}
			}
		}
		textNodeText = textNodeText.trim();
		System.out.println("mined " + textNodeText);
		return textNodeText;
		
	}
}
