import java.util.ArrayList;
import java.util.Arrays;

public class FileParser {

	private TagNode root;//

	public void createTree(ArrayList<String> lines) {

		// make parent TreeNode null
		TreeNode parentNode = null;
		String tagNodeText = new String();

		// make a for loop
		for (int ii = 0; ii < lines.size(); ii++) {
			TagNode node = new TagNode(null);

			// if it's an opening tag:
			if (lines.get(ii).startsWith("<") && !lines.get(ii).startsWith("</")) {

				// if it's an image, set the tag as an image
				if (lines.get(ii).contains("img")) {
					node = new TagNode("img");
					// if it contains an "=", it has an attribute
					if (lines.get(ii).contains("=")) {
						String[] textSplit = lines.get(ii).split("=");
						node.addAttribute("src", textSplit[1].substring(1, textSplit[1].lastIndexOf("\"")));
						if (lines.get(ii).contains("alt")) {
							textSplit = textSplit[2].split("\"");
							node.addAttribute("alt", textSplit[1]);
						}
					}
				} else {
					// create a new tagnode using substring of what's between < and >
					tagNodeText = lines.get(ii).substring(1, lines.get(ii).length() - 1);

					node = new TagNode(tagNodeText);
				}
				// if the parentNode is null, make the node the new parentNode
				if (this.getRoot() == null) {
					parentNode = node;
					// set it equal to root
					this.setRoot(node);
				} else {
					// otherwise add node as a child to paretNode node first
					// get this out of the way of images coming down
					parentNode.addChild(node);
					// set the parent to the parentNode (previous) node
					node.setParent(parentNode);
					// if the line doesn't close, set node as the new parentNode
					if (!lines.get(ii).contains("img")) {
						// check to make sure it isn't a text node
						if (lines.get(ii).contains("<")) {
							parentNode = node;
						}
					}
				}
				// If the line starts with a closing tag, go up one level by setting the current
				// equal to the current parent
			} else if (lines.get(ii).startsWith("</")) {

				parentNode = parentNode.getParent();
			} else {
				TextNode txtNode = new TextNode(lines.get(ii));
				parentNode.addChild(txtNode);
				txtNode.setParent(parentNode);
			}
		}
	}

	public TagNode getRoot() {
		return root;
	}

	public void setRoot(TagNode root) {
		this.root = root;
	}

	public void mineImages(ArrayList images, TreeNode node) {
		// check if it's a tagNode with instanceof
		if (node instanceof TagNode) {
			// make a new tagnode from the treenode
			// if tagnode has tag <IMG>

//			System.out.println("node tag: " + ((TagNode) node).getTag());
			if ((((TagNode) node).getTag()).contains("img")) {
				// images.add(node)
				images.add(node);
				// else
				// do nothing
			} else {
				// if node has children, loop through them use recursion to mine the children
				if (node.getChildren() != null) {

					for (int ii = 0; ii < node.getChildren().size(); ii++) {
						// call mineImages (images, node.getchildren.get(i))
						mineImages(images, node.getChildren().get(ii));
					}
				}
			}
		}
	}

	public String getKeywordsForImage(String filename) {
		String result = new String();

		result += getKeywordsForImageHelper(filename, this.getRoot());

	

		return result;

	}

	private String getKeywordsForImageHelper(String filename, TreeNode node) {
		String result = new String();
		if (node.getChildren() != null) {
			for (int ii = 0; ii < node.getChildren().size(); ii++) {
				
//				System.out.println("node: " + ((TagNode) node).getTag());
				
				if (node.getChildren().get(ii) instanceof TagNode) {
					
//						System.out.println("instanceof TagNode");
						
					if (((TagNode) node.getChildren().get(ii)).getTag().contains("img")) {
						
//						System.out.println("contains image");
						
						if (((TagNode) node.getChildren().get(ii)).getValue("src").contains(filename)) {
							
//							System.out.println("gets where it should");
							
							result += ((TagNode) node.getChildren().get(ii)).mineCloseText();
								if (((TagNode) node.getChildren().get(ii)).getValue("alt") != null) {
									result += " " + ((TagNode) node.getChildren().get(ii)).getValue("alt");
								}
						}
					} else {
						result+=""+getKeywordsForImageHelper(filename, node.getChildren().get(ii));
					}
				} else {
					result+=""+getKeywordsForImageHelper(filename, node.getChildren().get(ii));
				}
			}
		}

		return result;

	}

}
