package Homework_1;

public class BinarySearchTree {
	/*
	 * insertBST node����
	 * deleteBST node����
	 * getNode ���ο� node����
	 * height node�� height return
	 * noNodes node�� ����
	 * maxNode Ʈ�� �ȿ��� ���� ū value
	 * minNode Ʈ�� �ȿ��� ���� ���� value
	 */
	
	public class Node {
		private int data;
		private Node left;
		private Node right;
		
		public Node(int data) {
			this.data = data;
		}
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		public Node getRight() {
			return right;
		}
		public void setRight(Node right) {
			this.right = right;
		}
	}
	
	public class getNode {
		
		public int data;
		public getNode left;
		public getNode right;
		
		public getNode(int input) {
			this.data = input;
			this.left = null;
			this.right = null;
		}
	}

}
