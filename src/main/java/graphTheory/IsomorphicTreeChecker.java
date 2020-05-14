package graphTheory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IsomorphicTreeChecker {
    /**
     * @author Kaushal Kumar
     * @version 1.0
     * @since 8 may 2020
     */
    static class TreeNode {
        int degree;
        int id;
        List<TreeNode> children;

        TreeNode(int id) {
            this.id = id;
            degree = 0;
            children = new ArrayList<TreeNode>();
        }

        /**
         * @param child adds it to list of children
         */
        void add(TreeNode child) {
            degree++;
            children.add(child);
        }

        /**
         *
         * @param parent ensures that we don't visit the nodes already visited
         * @return all leafNodes in subtree rooted at currentNode
         */
        List<TreeNode> getLeafeNodes(TreeNode parent) {
            List<TreeNode> leafeNodes = new ArrayList<TreeNode>();
            if (this.degree == 1) leafeNodes.add(this);
            for (TreeNode child: children) {
                if (child != parent) {
                    List<TreeNode> leafNodesReachableFromChild = child.getLeafeNodes(this);
                    leafeNodes.addAll(leafNodesReachableFromChild);
                }
            }
            return leafeNodes;
        }

        /**
         *
         * @param parent ensures that we don't visit the nodes already visited
         * @return size of the subtree rooted at currentNode
         */
        int calculateSize(TreeNode parent) {
            int sz = 1;
            for (TreeNode child: children) {
                if (child != parent) {
                    sz += child.calculateSize(this);
                }
            }
            return sz;
        }

        /**
         * @return the tree centres in the tree there might be 2 tree centers in tree
        */
        List<TreeNode> getCenter() {
            List<TreeNode> leafNodes = getLeafeNodes(this);
            int cnt = leafNodes.size();
            int n = calculateSize(this);
            while (cnt < n) {
                List<TreeNode> newLeafNodes = new ArrayList<TreeNode>();
                for (TreeNode leaf: leafNodes) {
                    leaf.degree--;
                    for (TreeNode adjacent: leaf.children) {
                        if (adjacent == leaf) continue;
                        adjacent.degree--;
                        if (adjacent.degree == 1) newLeafNodes.add(adjacent);
                    }
                }
                leafNodes = newLeafNodes;
                cnt += newLeafNodes.size();
                break;
            }
            return leafNodes;
        }

        /**
         * @param parent ensures that we don't visit the nodes already visited
         * @return serialized version of tree root at current node
         */
        String serialize(TreeNode parent) {
            StringBuilder serializedTree = new StringBuilder();
            List<String> serializedChilds = new ArrayList<String>();
            for (TreeNode child: children) {
                if (child != parent) {
                    serializedChilds.add(child.serialize(this));
                }
            }
            Collections.sort(serializedChilds);
            serializedTree.append("(");
            for (String serializedChild: serializedChilds) {
                serializedTree.append(serializedChild);
            }
            serializedTree.append(")");
            return serializedTree.toString();
        }

        @Override
        public String toString() {
            return id+"";
        }
    }

    public static void main(String[] args) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        List<TreeNode> nodes1 = new ArrayList<TreeNode>();
        for (int i=0; i<6; i++) {
            nodes.add(new TreeNode(i));
            nodes1.add(new TreeNode(i));
        }
        nodes.get(0).add(nodes.get(1));
        nodes.get(1).add(nodes.get(0));

        nodes.get(1).add(nodes.get(2));
        nodes.get(2).add(nodes.get(1));

        nodes.get(1).add(nodes.get(3));
        nodes.get(3).add(nodes.get(1));

        nodes.get(3).add(nodes.get(4));
        nodes.get(4).add(nodes.get(3));

        nodes.get(3).add(nodes.get(5));
        nodes.get(5).add(nodes.get(3));

        //sec graph
        nodes1.get(2).add(nodes1.get(1));
        nodes1.get(1).add(nodes1.get(2));

        nodes1.get(1).add(nodes1.get(3));
        nodes1.get(3).add(nodes1.get(1));

        nodes1.get(1).add(nodes1.get(0));
        nodes1.get(0).add(nodes1.get(1));

        nodes1.get(0).add(nodes1.get(4));
        nodes1.get(4).add(nodes1.get(0));

        nodes1.get(0).add(nodes1.get(5));
        nodes1.get(5).add(nodes1.get(0));

        TreeNode centre = nodes.get(0).getCenter().get(0);
        String serialized = centre.serialize(centre);
        for (TreeNode node: nodes1.get(0).getCenter()) {
            String serialized1 = node.serialize(node);
            if (serialized.equals(serialized1)) {
                System.out.println("Trees are isomorphic");
                return ;
            }
        }
        System.out.println("Trees are not isomorphic");
    }
}
