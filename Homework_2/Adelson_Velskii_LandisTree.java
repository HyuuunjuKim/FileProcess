package Homework_2;
import java.util.*;

public class Adelson_Velskii_LandisTree {
	/*
	 * class Node�� BST���� ����� Node + balanceFactor
	 * 
	 * <checkBalance>
	 * 1. ���� �� ��尡 newKey
	 * 2. newKey�� �θ� ��带 �˻��ϸ鼭 bF�� ������ ���� ��带 p
	 * 3. p�� �θ� ��带 q
	 * 4. newKey�� p�� ����/������ �ڽ��� ����/������ ����Ʈ���� ���� �ʿ� ���� LL/RR/LR/RL/NO �Ǻ� 
	 * 
	 * <rotateTree>
	 * 1. checkBalance�� return�ϴ� ���鿡 ����
	 * 2. p�� rootNode�� �ؼ� ȸ��
	 * 
	 * <insertAVL>
	 * 1. insertBST
	 * 2. insertBST�� ���� checkBalance
	 * 3. rotationType ���
	 * 4. checkBalance�� �����ϴ� ���鿡 ���� rotateTree
	 */
	public static class Node {
		public int key;
		public Node left;
		public Node right;
		public int balanceFactor;
		
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
		// balanceFactor���� get,set method �߰�
		public int getBalanceFactor(Node T) {
			if(T.getLeft() != null && T.getRight() != null) {
				this.balanceFactor = height(T.getLeft()) - height(T.getRight());
			}
			else if(T.getLeft() == null && T.getRight() != null) {
				this.balanceFactor = -1 * height(T.getRight());
			}
			else if (T.getRight() == null && T.getLeft() != null ) {
				this.balanceFactor = height(T.getLeft());
			}
			else {
				this.balanceFactor = 0;
			}
			
			return balanceFactor;
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
	
	public static int height(Node T) {
		if(T == null) {
	         return 0;
	    } 
		else {         
	         return 1+Math.max(height(T.getLeft()), height(T.getRight()));
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
	
	
	public static Vector checkBalance(Node T, int newKey, String rotationType, Node p, Node q) {
		int[] keyArray= new int[height(T)]; //newKey�� ã�µ� �����İ��� Node�� key�� ���� �� �迭
		for (int i = 0 ; i < height(T) ; i++) {
			keyArray[i] = 0; //�迭 �ʱ�ȭ
		}
		int[] bFArray = new int[height(T)]; //newKey�� ã�µ� �����İ��� Node�� bF�� ���� �� ����
		for (int i = 0 ; i < height(T) ; i++) {
			bFArray[i] = 0; //�迭 �ʱ�ȭ
		}
		char[] directionArray = new char[height(T) -1]; //newKey�� ã�µ� �����İ��� edge�� ���� L or R ���� �� �迭
		for (int i = 0 ; i < height(T)-1 ; i++) {
			directionArray[i] = '0'; //�迭 �ʱ�ȭ
		}
		
		Vector forRotate = new Vector(); //ȸ���ϴµ� �ʿ��� ���� ���� �迭
		
		p = T;
		q = null; //�θ� node�� �� ����. �ʱ�ȭ�� null��
		
		/*
		 * newKey���� ã�ư��鼭 �����İ��� Key��� balanceFactor(node)�� ����
		 * newKey���� ã�ư��鼭 �����İ��� edge�� ���� ������ ����(L or R)
		 */
		int index = 0;
		int directIndex = 0;
		while(p != null) { 
			keyArray[index] = p.getKey();
			bFArray[index] = p.getBalanceFactor(p);
			if(newKey == p.getKey()) {
				break;
			}
			q = p; //q�� p�� �θ� ���� ����
			
			if(newKey < p.getKey()) {
				p = p.getLeft();
				directionArray[directIndex] = 'L';
				directIndex++;
			}
			else {
				p = p.getRight();
				directionArray[directIndex] = 'R';
				directIndex++;
			}
			index++;
		}
		
		rotationType = "NO"; //rotationType �ʱ�ȭ
		
		Node r1 = T; 
		Node r2 = T;
		/*
		 * bFArray�� �ڿ��� ���� �����ϸ� Ʈ���� �뷱���� �����κ��� �˻�
		 * ��, bF�� -2�� 2�� ������ �ش��ϴ� index�� key���� ���� Node�� T���� ã�� p�� ����(r1 �̿�)
		 * p�� �θ� ��带 ã�� q�� ����(r2 �̿�)
		 */
		for(int j= height(T)-1 ; j>=0 ; j--) {
			if(bFArray[j] == -2 || bFArray[j] == 2) {
				while( r1!= null) {
					if(keyArray[j] == r1.getKey()) {
						p = r1; 
						break;
					}
					if(keyArray[j] < r1.getKey()) {
						r1 = r1.getLeft();
					}
					else {
						r1 = r1.getRight();
					}
					
				}
				while (r2 != null) {
					if(j != 0) {
						if(keyArray[j-1] == r2.getKey()) {
							q = r2;
							break;
						}
						if(keyArray[j-1] < r2.getKey()) {
							r2 = r2.getLeft();
						}
						else {
							r2 = r2.getRight();
						}
					}
					else if(j == 0){ // ���� �뷱���κ��� ã�� ��� p�� root node�� ��� 
						q = null;
						break;
					}
					
				}
				
				/*
				 * �뷱���� ���� ������ newKey���� ���� ��θ� edge�� ���� ã��
				 * edge���� ����� L �Ǵ� R�� ���� rotationType ����(String type)
				 */
				rotationType = new String(directionArray, j, 2);
				/*
				 * rotateTree�� �ʿ��� ������ forRotate�� ����
				 * forRotate(0) <- rotationType
				 * forRotate(1) <- Node p
				 * forRotate(2) <- Node q
				 * 
				 */
				forRotate.add(rotationType);
				forRotate.add(p);
				forRotate.add(q);
				
				break;	
			}
			
			/*
			 * Ʈ���� ������ ������ ���� ���
			 * rotationType�� "NO"�� ����
			 */
			if(j==0) { 
				p = null;
				q = null;
				rotationType = "NO";
				forRotate.add(rotationType);
				forRotate.add(p);
				forRotate.add(q);
			}		
		}
		return forRotate;
	}
	
	
	public static Node rotateTree(Node T, String rotationType, Node pp, Node qq) {		
		Node p = pp;
		Node q = qq;
		Node child_p = null;
		
		//���� ����
		Node p1 = null;
		Node q1 = null;		
		
		if(rotationType == "NO") {  //rotationType�� "NO"�̸� ȸ������ �ʰ� �״�� T return
			return T;
		}
		
		//p�� root node�� ���(q�� null�� ���)
		Node newRoot = null; 
		if(q == null) {
			switch(rotationType) {
			case "LL" :
				child_p = p.getLeft();
		
				newRoot = p.getLeft();
				p.setLeft(child_p.getRight());
				child_p.setRight(p);
				
				T=newRoot;
		        return T;
			case "RR" :
				child_p = p.getRight();
				
				newRoot = p.getRight();
				p.setRight(child_p.getLeft());
				child_p.setLeft(p);
				
				T=newRoot;
		        return T;
			case "LR" :
				p1= p.getLeft();
				q1=p;
				//RR
				child_p = p1.getRight();
				
				q1.setLeft(child_p);
				p1.setRight(child_p.getLeft());
				child_p.setLeft(p1);
				
				p.setLeft(child_p);
				//LL
				child_p = p.getLeft();
				
				newRoot = p.getLeft();
				p.setLeft(child_p.getRight());
				newRoot.setRight(p);
				
				T=newRoot;
				return T;
			case "RL" :
				p1= p.getRight();
				q1=p;
				//LL
				child_p = p1.getLeft();
				
				q1.setRight(child_p);
				p1.setLeft(child_p.getRight());
				child_p.setRight(p1);
				
				p.setRight(child_p);
				//RR
				child_p = p.getRight();
				
				newRoot = p.getRight();
				p.setRight(child_p.getLeft());
				newRoot.setLeft(p);
				
				T=newRoot;
				return T;
			}
		}		
		//p�� q�� ������ �ڽ��� ���
		if(q.getRight() == p) {
			switch(rotationType) {
				case "LL" :
					child_p = p.getLeft();
					
					q.setRight(child_p);
					p.setLeft(child_p.getRight());
					child_p.setRight(p);
					
			        return T;
				case "RR" :
					child_p = p.getRight();
					
					q.setRight(child_p);
					p.setRight(child_p.getLeft());
					child_p.setLeft(p);
					
			        return T;
				case "LR" :
					
					p1= p.getLeft();
					q1=p;
					//RR
					child_p = p1.getRight();
					
					q1.setLeft(child_p);
					p1.setRight(child_p.getLeft());
					child_p.setLeft(p1);
					
					p.setLeft(child_p);
					//LL
					child_p = p.getLeft();
					
					q.setRight(child_p);
					p.setLeft(child_p.getRight());
					child_p.setRight(p);
					
					return T;
				case "RL" :
					p1= p.getRight();
					q1=p;
					//LL
					child_p = p1.getLeft();
					
					q1.setRight(child_p);
					p1.setLeft(child_p.getRight());
					child_p.setRight(p1);
										
					p.setRight(child_p);
					//RR
					child_p = p.getRight();
					
					q.setRight(child_p);
					p.setRight(child_p.getLeft());
					child_p.setLeft(p);
					
					return T;
			}
			
		}
		//p�� q�� ���� �ڽ��� ���
		else if(q.getLeft() == p) {
			switch(rotationType) {
			case "LL" :
				child_p = p.getLeft();
				
				q.setLeft(child_p);
				p.setLeft(child_p.getRight());
				child_p.setRight(p);
				
		        return T;
			case "RR" :
				child_p = p.getRight();
				
				q.setLeft(child_p);
				p.setRight(child_p.getLeft());
				child_p.setLeft(p);
		        return T;
			case "LR" :
				p1= p.getLeft();
				q1=p;
				//RR
				child_p = p1.getRight();
				
				q1.setLeft(child_p);
				p1.setRight(child_p.getLeft());
				child_p.setLeft(p1);
				
				p.setLeft(child_p);
				//LL
				child_p = p.getLeft();
				
				q.setLeft(child_p);
				p.setLeft(child_p.getRight());
				child_p.setRight(p);
				
				return T;
			case "RL" :
				p1= p.getRight();
				q1=p;
				//LL
				child_p = p1.getLeft();
				
				q1.setRight(child_p);
				p1.setLeft(child_p.getRight());
				child_p.setRight(p1);
									
				p.setRight(child_p);
				//RR
				child_p = p.getRight();
				
				q.setLeft(child_p);
				p.setRight(child_p.getLeft());
				child_p.setLeft(p);
				
				return T;
			}
		}	
		return T;		
	}

	
	public static Node insertAVL(Node T, int newKey) {
		T = insertBST(T, newKey);
		
		Vector forRotate;
		String rotationType = "";
		Node p = null;
		Node q = null;
		
		forRotate = checkBalance(T, newKey, rotationType, p, q);
		rotationType = (String)forRotate.get(0);
		p=(Node)forRotate.get(1);
		q=(Node)forRotate.get(2);
		
		System.out.printf(rotationType);
		
		Node finalTree = rotateTree(T, rotationType, p, q);
		
		return finalTree;
	}
}
