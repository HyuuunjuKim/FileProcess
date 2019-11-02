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
	 * inorder
	 */
	
	public static class Node {
		private int key;
		private Node left;
		private Node right;
		
		
		public Node(int key) { //������
			this.key= key;
		}
		public int getKey() {
			return key;
		}
		public void setKey(int key) {
			this.key = key;
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
	
	//���ο� node ����
	public static class getNode {
		
		private int key;
		private Node left;
		private Node right;
		
//		public getNode(int input) { 
//			this.key = input;
//			this.left = null;
//			this.right = null;
//		}
	}
	
	public static void inorderBST(Node n) {
		if(n != null) {
			inorderBST(n.getLeft());
			System.out.print(" " + n.getKey());
			inorderBST(n.getRight());
		}
	}
	public static void insertBST(Node T, int newKey) { //T�� �����ϴ� Ʈ��, newKey�� �����Ϸ��� ���ο� value
		Node p = T;
		Node q = null; //�θ� node�� �� ����. �ʱ�ȭ�� null��
		while(p != null) { //�����Ϸ��� Ű ���� ���� ��尡 �̹� �ִ��� �˻�
			if(newKey == p.getKey()) {
				System.out.println("�̹� �����ϴ� ���Դϴ�.");
				return;
			}
			q = p; //q�� p�� �θ� ���� ����
			if(newKey < p.getKey()) {
				p = p.getLeft();
			}
			else {
				p = p.getRight();
			}
		}
		
//		getNode newNode = new getNode();
//		newNode.key = newKey;
//		newNode.right = null;
//		newNode.left = null;
		
		Node newNode = new Node(newKey);
		
		if(T == null) {
//			T.key = newNode.key;
//			T.setRight(newNode.right);
//			T.setLeft(newNode.left);
			T = newNode;
			
		}
		else if (newKey < q.getKey()) {
//			Node qa = new Node();
			q.setLeft(newNode);
		}
			
		else {
			q.setRight(newNode);
		}
		
	}
	
	public static int height(Node root) {
		int leftHeight = 0;
		int rightHeight = 0;
			
		if(root.left == null && root.right == null) {
			return 0;
		}
		else if(root.left != null) {
			leftHeight = height(root.left);
		}
		else if(root.right != null) {
			rightHeight = height(root.right);
		}
			
		//leftHeight�� rightHeight �� ū�ſ� rootnode�����ؼ� 1���ϱ�
		return (leftHeight <= rightHeight ? rightHeight : leftHeight) + 1;
			
	}
	
	public static int noNodes(Node node) {
		int count = 0;
		while(node != null) {
			count = 1 + noNodes(node.getLeft()) + noNodes(node.getRight());
		}
		
		return count;
	}
	
	public static Node minNode(Node root) {
		Node p = root;
		while(p.getLeft() != null) {
			p = p.getLeft();
		}
		
		return p;
	}
	
	public static Node maxNode(Node root) {
		Node p = root;
		while(p.getRight() != null) {
			p = p.getRight();
		}
		
		return p;
	}
	
	public static void deleteBST(Node T, int deleteKey) {
		Node node = T; //���� Ʈ��
		Node p = null; //������ ���Ҹ� ���� ���
		Node q = null; //������ ���Ҹ� �ٹ� ����� �θ� ���
		Node r = null;
		
		while(node != null) { 
			if(node.getKey() == deleteKey) { //���� ��忡�� ���� ���ã����
				p = node; //p�� ã�� ��� ��� 
				break;
			}
			q = node; //q�� �θ� ���� ����
			if(node.getKey() < deleteKey) { //�����Ϸ��� ���Ұ� �� ũ��
				node = node.getRight(); //������ ����Ʈ���� �̵�
			}
			else { //���� �Ϸ��� ���Ұ� �� ������
				node = node.getLeft(); //���� ����Ʈ���� �̵�
			}
		}
		
		if(p == null) { //������ ��尡 ���� Ʈ���� ������ 
			return;
		}
		if(q == null) { //������ ���� �ִµ� �θ��尡 ����. ��, �����Ϸ��� ��尡 ��Ʈ����̸�
			T = null;
			return;
		}
		
		//�����Ϸ��� ����� ������ 0�̸�
		if(p.getLeft() == null && p.getRight() == null) {
			if(q.getLeft() == p) { //������ ��尡 �θ��� ���� ����̸�
				q.setLeft(null);
			}
			else { //������ ��尡 �θ��� ������ ����̸�
				q.setRight(null);
			}
		}
		
		//�����Ϸ��� ����� ������ 1�̸�
		else if(p.getLeft() == null || p.getRight() == null) {
			if(p.getLeft() != null) { //������ ��尡 ���� �ڽ��� ���� �����鼭
				if(q.getLeft() == p) { //������ ��尡 �θ��� ���� ����̸�
					q.setLeft(p.getLeft());
				}
				else { //������ ��尡 �θ��� ������ ����̸�
					q.setRight(p.getLeft());
				}
			}
			else { //������ ��尡 ������ �ڽ��� ���� �����鼭
				if(q.getLeft() == p) { //������ ��尡 �θ��� ���� ����̸�
					q.setLeft(p.getRight());
				}
				else { //������ ��尡 �θ��� ������ ����̸�
					q.setRight(p.getRight());
				}
			}
		}
		
		/*
		 * �����Ϸ��� ����� ������ 2�̸�
		 * 1. �ڽ� Ʈ���� ���� �� -> ū ������ Ʈ������ �ִ� or�ּڰ� ���� ���� ��ü
		 * 2. �ڽ� Ʈ���� ���� ������ -> �ڽ� Ʈ���� ��� ���� �� -> ū ������ Ʈ������ �ִ� or�ּڰ� ���� ���� ��ü
		 */
		else { 
			int heightLeft = height(p.getLeft());
			int heightRight = height(p.getRight());
			
			int noNodesLeft = noNodes(p.getLeft());
			int noNodesRight = noNodes(p.getRight());
			
			String flag;
			
			if(heightLeft > heightRight) { //���� ����Ʈ�� ���� > ������ ����Ʈ�� ����
				Node maxNode = maxNode(p.getLeft());
				r = maxNode; //���� ����Ʈ������ ���� ū key���� ���
				flag = "LEFT";
				
			}
			else if(heightLeft < heightRight) { //���� ����Ʈ�� ���� < ������ ����Ʈ�� ����
				Node minNode = minNode(p.getRight());
				r = minNode; //������ ����Ʈ������ ���� ���� key���� ���
				flag = "RIGHT";
			}
			else { //���� ����Ʈ�� ���� == ������ ����Ʈ�� ����
				if(noNodesLeft > noNodesRight) { //���� ����Ʈ������ ����� ���� > ������ ����Ʈ������ ����� ���� 
					Node maxNode = maxNode(p.getLeft());
					r = maxNode; //���� ����Ʈ������ ���� ū key���� ���
					flag = "LEFT";//���� ����Ʈ������ ���� ū key���� ���
				}
				else { //���� ����Ʈ������ ����� ���� <= ������ ����Ʈ������ ����� ����
					Node minNode = minNode(p.getRight());
					r = minNode; //������ ����Ʈ������ ���� ���� key���� ���
					flag = "RIGHT";
				}
			}
			
			p.setKey(r.getKey()); //������ ������ �ڸ��� r�ֱ�
			if(flag == "LEFT") { //flag�� left�̸� ���� ����Ʈ������ r����� 
				deleteBST(p.getLeft(), r.getKey());
			}
			else { //flag�� right�̸� ������ ����Ʈ������ r����� 
				deleteBST(p.getRight(), r.getKey());
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node T = new Node(0);
		
		insertBST(T, 40); inorderBST(T); System.out.println();
		insertBST(T, 11); inorderBST(T); System.out.println();
		insertBST(T, 77); inorderBST(T); System.out.println();
		insertBST(T, 33); inorderBST(T); System.out.println();
		insertBST(T, 20); inorderBST(T); System.out.println();
		insertBST(T, 90); inorderBST(T); System.out.println();
		insertBST(T, 99); inorderBST(T); System.out.println();
		insertBST(T, 70); inorderBST(T); System.out.println();
		insertBST(T, 88); inorderBST(T); System.out.println();
		insertBST(T, 80); inorderBST(T); System.out.println();
		insertBST(T, 66); inorderBST(T); System.out.println();
		insertBST(T, 10); inorderBST(T); System.out.println();
		insertBST(T, 22); inorderBST(T); System.out.println();
		insertBST(T, 30); inorderBST(T); System.out.println();
		insertBST(T, 44); inorderBST(T); System.out.println();
		insertBST(T, 55); inorderBST(T); System.out.println();
		insertBST(T, 50); inorderBST(T); System.out.println();
		insertBST(T, 60); inorderBST(T); System.out.println();
		insertBST(T, 100); inorderBST(T); System.out.println();
		deleteBST(T, 100); inorderBST(T); System.out.println();
		deleteBST(T, 70); inorderBST(T); System.out.println();
		
	

	}

}
