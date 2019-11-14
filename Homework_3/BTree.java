package Homework_3;

public class BTree {
	public static class Node {
		public int[] key; //���� ��� �� key�迭
		public Node[] child; //���� ����� �ڽ� ���� �迭
		public Node parent; //���� ����� �θ� ���(1��)		
		public int m;
		public int count; //���� ��� �� key�� ����		
		public boolean leaf; //�ܸ� ������� �ƴ��� 
		
		public Node() {
			// default ������
		}
		
		public Node(int m, Node parent) {
			this.m = m;
			this.parent = parent;
			this.key = new int[m-1];
			this.child = new Node[m];
			this.leaf = true; //��� ���� ó������ leaf
			this.count = 0; //ó������ key�迭�� �ƹ��͵� �ȴ��
			
		}
		
		public int getValue(int index) {
			return key[index];
		}
		
		public Node getChildNode(int index) {
			return child[index];
		}
		
	}

}
