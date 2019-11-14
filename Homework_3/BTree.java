package Homework_3;

public class BTree {
	public static class Node {
		public int[] key; //현재 노드 안 key배열
		public Node[] child; //현재 노드의 자식 노드들 배열
		public Node parent; //현재 노드의 부모 노드(1개)		
		public int m;
		public int count; //현재 노드 안 key의 개수		
		public boolean leaf; //단말 노드인지 아닌지 
		
		public Node() {
			// default 생성자
		}
		
		public Node(int m, Node parent) {
			this.m = m;
			this.parent = parent;
			this.key = new int[m-1];
			this.child = new Node[m];
			this.leaf = true; //모든 노드는 처음에는 leaf
			this.count = 0; //처음에는 key배열에 아무것도 안담김
			
		}
		
		public int getValue(int index) {
			return key[index];
		}
		
		public Node getChildNode(int index) {
			return child[index];
		}
		
	}

}
