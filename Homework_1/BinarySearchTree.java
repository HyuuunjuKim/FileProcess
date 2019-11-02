package Homework_1;

public class BinarySearchTree {
	/*
	 * insertBST node삽입
	 * deleteBST node삭제
	 * getNode 새로운 node설정
	 * height node의 height return
	 * noNodes node의 개수
	 * maxNode 트리 안에서 가장 큰 value
	 * minNode 트리 안에서 가장 작은 value
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
