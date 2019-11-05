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
	 * inorder ���� ��ȸ
	 */
	
	public static class Node {
		public int key;
		public Node left;
		public Node right;
		
		public Node(int key) { //������
			this.key= key;
		}
		public void getNode(int newKey) { //getNode ���ο� node����
			this.key = newKey;
			this.left = null;
			this.right = null;
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
		
	public static void inorderBST(Node T) {
		if(T != null) {
			inorderBST(T.getLeft());
			System.out.print(" " + T.getKey());
			inorderBST(T.getRight());
		}
		else {
			return;
		}
	}
		
	public static Node insertBST(Node T, int newKey) { //T�� �����ϴ� Ʈ��, newKey�� �����Ϸ��� ���ο� value
		Node p = T;
		Node q = null; //�θ� node�� �� ����. �ʱ�ȭ�� null��
		
		while(p != null) { //�����Ϸ��� Ű ���� ���� ��尡 �̹� �ִ��� �˻�
			if(newKey == p.getKey()) {
				System.out.println("�̹� �����ϴ� ���Դϴ�.");				
			}
			q = p; //q�� p�� �θ� ���� ����
			if(newKey < p.getKey()) {
				p = p.getLeft();
			}
			else {
				p = p.getRight();
			}
		}
				
		Node newNode = new Node(newKey);
		newNode.getNode(newKey);
		
		if(T == null) {
			return newNode;
		}
		else if (newKey < q.getKey()) {
			q.setLeft(newNode);			
		}			
		else {
			q.setRight(newNode);			
		}
		return T;		
	}
		
	public static int height(Node T) {
		if(T == null) {
	         return 0;
	    } 
		else {         
	         return 1+Math.max(height(T.getLeft()), height(T.getRight()));
	    }  
	}
	
	public static int noNodes(Node T) {
		Node p = T;
		int count = 0;
		if(p!= null) {
			count = 1 + noNodes(p.getLeft()) + noNodes(p.getRight());
		}	
		return count;
	}
	
	public static Node minNode(Node T) {
		Node p = T;
		while(p.getLeft() != null) {
			p = p.getLeft();
		}	
		return p;
	}
	
	public static Node maxNode(Node T) {
		Node p = T;
		while(p.getRight() != null) {
			p = p.getRight();
		}
		return p;
	}
	
	public static Node deleteBST(Node T, int deleteKey) {
		if(T == null) {
			System.out.println("������ ��尡 �����ϴ�.");
            return null;
		}
		Node q = null;
		Node p = T;

		while(p.getKey() != deleteKey){ 
			q = p; //q�� �θ� ���� ����
			if(p == null) {
				System.out.println("��带 ã�� �� �����ϴ�.");
                return T;
			}			
			if(p.getKey() < deleteKey) { //�����Ϸ��� ���Ұ� �� ũ��
				p = p.getRight(); //������ ����Ʈ���� �̵�
			}
			else { //���� �Ϸ��� ���Ұ� �� ������
				p = p.getLeft(); //���� ����Ʈ���� �̵�
			}
		} 
				
		//�����Ϸ��� ����� ������ 0�̸�
		if(p.getLeft() == null && p.getRight() == null) {
			if(q == null) { //������ ���� �ִµ� �θ��尡 ����. ��, �����Ϸ��� ��尡 ��Ʈ����̸�
				T = null;
				return T;
			}
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
				if(q == null) { //������ ���� �ִµ� �θ��尡 ����. ��, �����Ϸ��� ��尡 ��Ʈ����̸�
					T.setKey(p.getLeft().getKey());
					T.setLeft(p.getLeft().getLeft());
					T.setRight(p.getLeft().getRight());
					return T;
				}
				else {
					if(q.getLeft() == p) { //������ ��尡 �θ��� ���� ����̸�
						q.setLeft(p.getLeft());
					}
					else { //������ ��尡 �θ��� ������ ����̸�
						q.setRight(p.getLeft());
					}
				}
			}
			else { //������ ��尡 ������ �ڽ��� ���� �����鼭
				if(q == null) { //������ ���� �ִµ� �θ��尡 ����. ��, �����Ϸ��� ��尡 ��Ʈ����̸�
					T.setKey(p.getRight().getKey());
					T.setLeft(p.getRight().getLeft());
					T.setRight(p.getRight().getRight());
					return T;
				}
				else {
					if(q.getLeft() == p) { //������ ��尡 �θ��� ���� ����̸�
						q.setLeft(p.getRight());
					}
					else { //������ ��尡 �θ��� ������ ����̸�
						q.setRight(p.getRight());
					}
				}
			}
		}
		
		/*
		 * �����Ϸ��� ����� ������ 2�̸�
		 * 1. �ڽ� Ʈ���� ���� �� -> ū ������ Ʈ������ �ִ� or�ּڰ� ���� ���� ��ü
		 * 2. �ڽ� Ʈ���� ���� ������ -> �ڽ� Ʈ���� ��� ���� �� -> ū ������ Ʈ������ �ִ� or�ּڰ� ���� ���� ��ü
		 */
		else if(p.getLeft() != null && p.getRight() != null){ 
			
			int heightLeft = height(p.getLeft());
			int heightRight = height(p.getRight());
			
			int noNodesLeft = noNodes(p.getLeft());
			int noNodesRight = noNodes(p.getRight());
			
			String flag=" ";
			Node r = new Node(0);
			if(heightLeft > heightRight) { //���� ����Ʈ�� ���� > ������ ����Ʈ�� ����
				r = maxNode(p.getLeft()); //���� ����Ʈ������ ���� ū key���� ���
				flag = "LEFT";
			}
			else if(heightLeft < heightRight) { //���� ����Ʈ�� ���� < ������ ����Ʈ�� ����
				r = minNode(p.getRight()); //������ ����Ʈ������ ���� ���� key���� ���
				flag = "RIGHT";
			}
			else { //���� ����Ʈ�� ���� == ������ ����Ʈ�� ����
				if(noNodesLeft > noNodesRight) { //���� ����Ʈ������ ����� ���� > ������ ����Ʈ������ ����� ���� 
					r = maxNode(p.getLeft()); //���� ����Ʈ������ ���� ū key���� ���
					flag = "LEFT";//���� ����Ʈ������ ���� ū key���� ���
				}
				else { //���� ����Ʈ������ ����� ���� <= ������ ����Ʈ������ ����� ����
					r = minNode(p.getRight()); //������ ����Ʈ������ ���� ���� key���� ���
					flag = "RIGHT";
				}
			}
			
			p.setKey(r.getKey()); //������ ������ �ڸ��� r�ֱ�
			if(flag == "LEFT") { //flag�� left�̸� ���� ����Ʈ������ r����� 
				/*
				 * r�� p�� �ٷ� ���� �ڽ��ΰ��, ��ͷ� deleteBST�� ȣ�� �� root��尡 �Ǿ���� r�� �������ϴµ� r�� p�� �θ𿴴� q�� ������ �����ʾ� ������ �߻���
				 * ���� �̷��� ��� p�� ���ڷ� �Բ� �ѱ�� deleteBST�� ȣ��
				 */
				if(p.getLeft() == r || p.getRight() == r) {
					Node parentP = p;
					deleteBST(p.getLeft(), r.getKey(), parentP);
				}
				else {
					deleteBST(p.getLeft(), r.getKey());
				}
			}
			else { //flag�� right�̸� ������ ����Ʈ������ r����� 
				if(p.getLeft() == r || p.getRight() == r) {
					Node parentP = p;
					deleteBST(p.getRight(), r.getKey(), parentP);
				}
				else {
					deleteBST(p.getRight(), r.getKey());
				}	
			}			
		}
		return T;
	}
	
	public static Node deleteBST(Node T, int deleteKey, Node parent) {
		if(T == null) {
			System.out.println("������ ��尡 �����ϴ�.");
            return null;
		}
		Node q = parent;
		Node p = T;

		while(p.getKey() != deleteKey){ 
			q = p; //q�� �θ� ���� ����
			if(p == null) {
				System.out.println("��带 ã�� �� �����ϴ�.");
                return T;
			}
			
			if(p.getKey() < deleteKey) { //�����Ϸ��� ���Ұ� �� ũ��
				p = p.getRight(); //������ ����Ʈ���� �̵�
			}
			else { //���� �Ϸ��� ���Ұ� �� ������
				p = p.getLeft(); //���� ����Ʈ���� �̵�
			}
		}
		if(p == null) { //������ ��尡 ���� Ʈ���� ������ 
			return T;
		}
				
		//�����Ϸ��� ����� ������ 0�̸�
		if(p.getLeft() == null && p.getRight() == null) {
			if(q == null) { //������ ���� �ִµ� �θ��尡 ����. ��, �����Ϸ��� ��尡 ��Ʈ����̸�
				T = null;
				return T;
			}
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
				if(q == null) { //������ ���� �ִµ� �θ��尡 ����. ��, �����Ϸ��� ��尡 ��Ʈ����̸�
					T.setKey(p.getLeft().getKey());
					T.setLeft(p.getLeft().getLeft());
					T.setRight(p.getLeft().getRight());
					return T;
				}
				else {
					if(q.getLeft() == p) { //������ ��尡 �θ��� ���� ����̸�
						q.setLeft(p.getLeft());
					}
					else { //������ ��尡 �θ��� ������ ����̸�
						q.setRight(p.getLeft());
					}
				}
			}
			else { //������ ��尡 ������ �ڽ��� ���� �����鼭
				if(q == null) { //������ ���� �ִµ� �θ��尡 ����. ��, �����Ϸ��� ��尡 ��Ʈ����̸�
					T.setKey(p.getRight().getKey());
					T.setLeft(p.getRight().getLeft());
					T.setRight(p.getRight().getRight());
					return T;
				}
				else {
					if(q.getLeft() == p) { //������ ��尡 �θ��� ���� ����̸�
						q.setLeft(p.getRight());
					}
					else { //������ ��尡 �θ��� ������ ����̸�
						q.setRight(p.getRight());
					}
				}
			}
		}
		
		/*
		 * �����Ϸ��� ����� ������ 2�̸�
		 * 1. �ڽ� Ʈ���� ���� �� -> ū ������ Ʈ������ �ִ� or�ּڰ� ���� ���� ��ü
		 * 2. �ڽ� Ʈ���� ���� ������ -> �ڽ� Ʈ���� ��� ���� �� -> ū ������ Ʈ������ �ִ� or�ּڰ� ���� ���� ��ü
		 */
		else if(p.getLeft() != null && p.getRight() != null){ 
			
			int heightLeft = height(p.getLeft());
			int heightRight = height(p.getRight());
			
			int noNodesLeft = noNodes(p.getLeft());
			int noNodesRight = noNodes(p.getRight());
			
			String flag=" ";
			Node r = new Node(0);
			if(heightLeft > heightRight) { //���� ����Ʈ�� ���� > ������ ����Ʈ�� ����
				r = maxNode(p.getLeft()); //���� ����Ʈ������ ���� ū key���� ���
				flag = "LEFT";
			}
			else if(heightLeft < heightRight) { //���� ����Ʈ�� ���� < ������ ����Ʈ�� ����
				r = minNode(p.getRight()); //������ ����Ʈ������ ���� ���� key���� ���
				flag = "RIGHT";
			}
			else { //���� ����Ʈ�� ���� == ������ ����Ʈ�� ����
				if(noNodesLeft > noNodesRight) { //���� ����Ʈ������ ����� ���� > ������ ����Ʈ������ ����� ���� 
					r = maxNode(p.getLeft()); //���� ����Ʈ������ ���� ū key���� ���
					flag = "LEFT";//���� ����Ʈ������ ���� ū key���� ���
				}
				else { //���� ����Ʈ������ ����� ���� <= ������ ����Ʈ������ ����� ����
					r = minNode(p.getRight()); //������ ����Ʈ������ ���� ���� key���� ���
					flag = "RIGHT";
				}
			}
			
			p.setKey(r.getKey()); //������ ������ �ڸ��� r�ֱ�
			if(flag == "LEFT") { //flag�� left�̸� ���� ����Ʈ������ r����� 
				if(p.getLeft() == r || p.getRight() == r) {
					Node parentP = p;
					deleteBST(p.getLeft(), r.getKey(), parentP);
				}
				else {
					deleteBST(p.getLeft(), r.getKey());
				}
			}
			else { //flag�� right�̸� ������ ����Ʈ������ r����� 
				if(p.getLeft() == r || p.getRight() == r) {
					Node parentP = p;
					deleteBST(p.getRight(), r.getKey(), parentP);
				}
				else {
					deleteBST(p.getRight(), r.getKey());
				}
			}			
		}
		return T;
	}	
}

