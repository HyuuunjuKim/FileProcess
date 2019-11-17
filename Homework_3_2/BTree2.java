package Homework_3_2;

import java.util.*;


public class BTree2 {
	public static class Node {
		public ArrayList<Integer> key; //���� ��� �� key�迭
		public Node[] child; //���� ����� �ڽ� ���� �迭
		public Node parent; //���� ����� �θ� ���(1��)		
		public int m;
		public int count; //���� ��� �� key�� ����		
		public boolean isLeaf; //�ܸ� ������� �ƴ��� 
		
		public Node() {
			// default ������
		}
		
		public Node(int m, Node parent) {
			this.m = m;
			this.parent = parent;
			this.key = new ArrayList<Integer>();
			this.child = new Node[m];
			this.isLeaf = true; //��� ���� ó������ leaf
			this.count = 0; //ó������ key�迭�� �ƹ��͵� �ȴ��
			
		}
		public boolean isLeaf(int m) {
			this.isLeaf = true;
			for (int i = 0 ; i < m ; i++) {
				if(this.child[i] != null) {
					this.isLeaf = false;
					break;
					
				}
				
			}
			return this.isLeaf;
		}
		
//		public int getValue(int index) {
//			return key[index];
//		}
		
		public Node getChildNode(int index) {
			return child[index];
		}
		public Node reset(int m, Node parent) {
			Node newNode = new Node(m, parent);
			this.isLeaf = false;
			
			return newNode;
		}
	}
	
	public static void inorderBT2(Node T, int m) {

		if(T != null) {
			for (int i = 0 ; i < T.count ; i++) {
				inorderBT2(T.child[i], m);
				System.out.print(" " + T.key.get(i));	
			}
			inorderBT2(T.child[T.count], m);
		}
		else {
			return;	
		}
	}
	

	public Node search(Node root, int searchKey, Stack stack, Stack parent) {		
		int i=0;
		
		while(i < root.count && searchKey > root.key.get(i)) {
			i++;
		}
		
		
		if(i < root.count && searchKey == root.key.get(i)) {
			System.out.println("�̹� ���� �ϴ� key�Դϴ�.");
			return root; //searchKey�� root Node���� ã��
		}
		
		
		if(root.isLeaf(root.m) == true) {
			parent.push(root);
			stack.push(root);
			return null;
		}
//		else if (root.isLeaf(root.m) == false && root.count < root.m-1 && searchKey >root.key[0] ) {
//			parent.push(root);
//			stack.push(root);
//			return null;
//		}
		else {
			parent.push(root);
			stack.push(root);
			return search(root.getChildNode(i), searchKey, stack, parent);
		}
		
	}
	
	public Node insertBT2(Node T, int m, int newKey) {
		/*
		 * Node�� ����ִ� ���
		 * T�� newKey���� �� ����
		 */
		if(T == null) {
			T = new Node(m, null);
			T.key.add(0, newKey);
			T.count = 1;
			return T;
		}
		
		/*
		 * Node�� ������� ���� ���
		 * 1. T�� newKey�� ������ �ڸ��� ã��
		 */
		Stack<Node> stack = new Stack<>();
		Stack<Node> parent = new Stack<>();
		Node x = T;
		Node left = null;
		Node right = null;
		int middleKey;
		Vector split;
		
		Node NUllreturn = search(x, newKey, stack, parent); //stack�� �������� ���� push�ϸ鼭 �ܸ����� ������  null return;
		if (NUllreturn == null) {
			if(stack.size() == 1 && T.count < m-1) {
				T.key.add(T.count, newKey);
				for(int i = T.count ; i <m-1 ; i++) {
					T.child[i+1] = T.child[i];
				}
				T.child[T.count] = null;
				T.count++;
				Collections.sort(T.key);
				return T;
			}
			else {
				do{
					Node popNode = stack.pop();
					Node delete_top = null;
					Node insert = null;
					if(parent.empty() != true) {
						delete_top = parent.pop();
					}
					else {
						continue;
					}
					split = insertKey(popNode.count, popNode, newKey);
					
					
					if ((int)split.get(0) == -1) {
						popNode.key.add(popNode.count, newKey);
						popNode.count++;
						Collections.sort(popNode.key);						
						break;
					}
					else {
						newKey = (int)split.get(0);
						left = (Node)split.get(1);
						right = (Node)split.get(2);
						popNode.child[0] = left;
						popNode.child[1] = right;
						Node parent_pop = null;
						if(parent.empty() != true) {
							parent_pop = parent.pop();
							popNode.parent = parent_pop;
						}
						else {
							
							popNode.parent = null;
						}
	//					popNode.parent = parent.pop();
						popNode.isLeaf = false;
						
						if (popNode.parent ==  null) {
							Node newNode = new Node(m, null);
							newNode.key.add(0, newKey);
							newNode.child[0] = left;
							newNode.child[1] = right;
							
							newNode.count = 1;
							newNode.child[0].count = left.count;
							newNode.child[1].count = right.count;
							T = newNode;
							break;
						}
						
	//					else if(popNode.parent.count == m-1) {
	//						insertKey(popNode.count, popNode, newKey);
	//					}
						else if (popNode.parent.count <= m-1){
							insert = new Node(m, parent_pop);
	//						popNode.reset(m, parent_pop);
							insert.key.add(0, newKey);
							popNode.key = insert.key;
							popNode.count=1;
							popNode.child[0] = left;
							popNode.child[left.count] = right;
							popNode.child[0].count = left.count;
							popNode.child[left.count].count = right.count;
							popNode.isLeaf = false;
							break;
						}
						
						
					}					
				}while(!stack.isEmpty());
			
	//		if(stack.isEmpty()){//stack�� empty��� �� level�� �ϳ� �����Ѵٴ� ��
	//			T = new Node(m, null);
	//			T.key[0] = newKey;
	////			T.child[0] = 
	////			T.child[1] = 
	////			
	////			return T;
	//		}
			
			}
		}		
		return T;				
	}
	
	public Vector insertKey(int count, Node popNode, int newKey) {
		Node x = popNode;
		Vector split = new Vector(); //�߰� ��, ���� ���, ������ ��� ����
		Node splitLeft = new Node(popNode.m, popNode);
		Node splitRight = new Node(popNode.m, popNode);
		if(count != popNode.m -1) {
			//�����ϰ� ����?
//			
//			x.key[count] = newKey;
			split.add(-1);
			
		}
		else {
			//�߰� �� ã�Ƽ� �ѱ�� �ǳ�?
			
			Node tempNode = new Node(popNode.m+1,popNode.parent);
			for (int i = 0 ; i < x.count ; i++) {
				tempNode.key.add(i, x.key.get(i));
			}
			tempNode.key.add(tempNode.m-2, newKey);
			tempNode.count = tempNode.m-1;
			Collections.sort(tempNode.key);
			if(tempNode.key.size() % 2 != 0) { //tempNode�� newKey�� �����ؼ� key�� ������ Ȧ����
				newKey = tempNode.key.get(tempNode.m/2-1);
				for(int i = 0; i < tempNode.m/2 - 1 ; i++) {
					splitLeft.key.add(i, tempNode.key.get(i));
					splitRight.key.add(i, tempNode.key.get(i+tempNode.m/2));
					splitLeft.count++;
					splitRight.count++;
				}
//				tempNode.child[0] = splitLeft;
//				tempNode.child[1] = splitRight;
				split.add(newKey);
				split.add(splitLeft);
				split.add(splitRight);
			}
			else { //tempNode�� newKey�� �����ؼ� key�� ������ ¦����
				newKey = tempNode.key.get(tempNode.m/2);
				for(int i = 0; i < tempNode.m/2 ; i++) {
					splitLeft.key.add(i, tempNode.key.get(i));
					splitLeft.count++;
				}
					splitRight.key.add(tempNode.count-1-tempNode.m/2-1, tempNode.key.get(tempNode.count-1));
					splitRight.count++;
				
//				tempNode.child[0] = splitLeft;
//				tempNode.child[1] = splitRight;
				split.add(newKey);
				split.add(splitLeft);
				split.add(splitRight);
			}	
		
		}
		return split;
	}
	
	

}
