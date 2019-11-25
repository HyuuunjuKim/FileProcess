package Homework_4;

import java.util.*;


public class BTree {
	public static class Node {
		public ArrayList<Integer> key; //���� ��� �� key�迭����Ʈ
		public Node[] child; //���� ����� �ڽ� ���� �迭
		public Node parent; //���� ����� �θ� ���(1��)		
		public int m;
		public int count; //���� ��� �� key�� ����		
		public boolean isLeaf; //�ܸ� ������� �ƴ��� 
		
				
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
		
		public Node getChildNode(int index) {
			return child[index];
		}
		
	}
	
	
	public static void inorderBT(Node T, int m) {
		if(T != null) {
			for (int i = 0 ; i < T.count ; i++) {
				inorderBT(T.child[i], m);
				System.out.print(" " + T.key.get(i));	
			}
			inorderBT(T.child[T.count], m);
		}
		else {
			return;	
		}
	}
	

	public Node search(Node root, int searchKey, Stack stack) {		
		int i=0;
		
		while(i < root.count && searchKey > root.key.get(i)) {
			i++;
		}
		
		if(i < root.count && searchKey == root.key.get(i)) {
			System.out.println("�̹� ���� �ϴ� key�Դϴ�.");
			return root; //searchKey�� �̹� �����ϸ� root�� ����
		}
		
		
		if(root.isLeaf(root.m) == true) {
			stack.push(root);
			return null;
		}
		else {
			stack.push(root);
			return search(root.getChildNode(i), searchKey, stack);
		}
		
	}
	
	
	public Node insertBT(Node T, int m, int newKey) {
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
		Stack<Node> stack = new Stack<>(); //newKey�� �ڸ��� ã�ư��� ��ó���� ������ ���� ����
		
		Node x = T;
		Node left = null;
		Node right = null;
		int middleKey;
		Vector split;
		
		/*
		 * ���ο� ��带 �����ϴ� ���� null����
		 * �̹� �����ϴ� ��带 �����ϴ� ���� T����
		 */
		Node NULLreturn = search(x, newKey, stack); 
		if (NULLreturn == null) {
			//���� �ʹ��� ��� 
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
					
					Node popNode = stack.pop(); //newKey�� ���Խ�ų ��ġ
					Node insert = null;
					if(stack.size() >= 1) {
						popNode.parent = stack.peek();
					}
					else {
						popNode.parent = null;
					}
					
					
					int popNodeIndex = 0 ;
					if(popNode.parent != null ) {
						for (popNodeIndex = 0 ; popNodeIndex <= popNode.parent.count+1 ; popNodeIndex++) {
							if(popNode.parent.getChildNode(popNodeIndex) == popNode) {
								break;
							}
							else {
								continue;
							}
							
						}
					}
					
					split = insertKey(popNode.count, popNode, newKey);
					
					//pop�� ��尡 �� ���� �ʾ� ���� �߰� ���� ����
					if (split.get(0).equals("�����߰�����")) {
						popNode.key.add(popNode.count, newKey);
						popNode.count++;
						Collections.sort(popNode.key);						
						break;
					}
					/*
					 * pop�� ��尡 �� �� �־�, ���� �߰� �Ұ��� ����
					 * insertKey�� ���� return���� ������ ���� split�õ�
					 */
					else {
						middleKey = (int)split.get(0); //�߰� ��
						left = (Node)split.get(1); //���� �ڽ��� �� ���
						right = (Node)split.get(2); //������ �ڽ��� �� ���
						
						//popNode�� �� �̻� �ܸ���尡 �ƴ�
						popNode.isLeaf = false;
						
						//root Node�� split�ϴ� ���
						if (popNode.parent ==  null) {
							Node newNode = new Node(m, null);
							newNode.key.add(0, middleKey);
							newNode.count = 1;
							
							newNode.child[0] = left;
							newNode.child[1] = right;
							
							newNode.child[0].count = left.count;
							newNode.child[1].count = right.count;
							
							T = newNode;
							break;
						}
						else if (popNode.parent.count < m-1){
							//pop�� ��忡 inserKey�� ���� ���� �߰����� ����
							popNode.parent.key.add(popNodeIndex, middleKey);
							Collections.sort(popNode.parent.key);
							popNode.parent.count++;
							for(int i = popNode.parent.count-1 ; i >= popNodeIndex+1 ; i--) {
								popNode.parent.child[i+1] = popNode.parent.child[i];
							}
							
							popNode.parent.child[popNodeIndex] = left;
							popNode.parent.child[popNodeIndex].count = left.count;
							
							popNode.parent.child[popNodeIndex+1] = right;
							popNode.parent.child[popNodeIndex+1].count = 1;
							
							break;
						}
						else if(popNode.parent.count == m - 1) {
							Node newNode = new Node(m, null);
							newNode.key.add(0, middleKey);
							newNode.count = 1;
							
							newNode.child[0] = left;
							newNode.child[1] = right;
							
							newNode.child[0].count = left.count;
							newNode.child[1].count = right.count;
							
							popNode.parent.child[popNodeIndex] = newNode;
							
							newKey = middleKey;
							continue;
							
						}
						
					}
					
				}while(!stack.isEmpty());
		
			}
		}	
		
		return T;				
	}
	
	public Vector insertKey(int count, Node popNode, int newKey) {
		Node x = popNode;
		Vector split = new Vector(); //�߰� ��, ���� ���, ������ ��� ���� ����
		Node splitLeft = new Node(popNode.m, popNode);
		Node splitRight = new Node(popNode.m, popNode);
		
		//���� pop�� ��忡 �߰������� key ���� ���� ����
		if(count != popNode.m - 1) {
			split.add("�����߰�����");
		}
		//���� pop�� ��尡 �� ���־�, split�� ���� �߰� Ű�� return�Ͽ� �Ѱ�����ϴ� ��Ȳ
		else {
			Node tempNode = new Node(popNode.m+1, popNode.parent);
			for (int i = 0 ; i < x.count ; i++) {
				tempNode.key.add(i, x.key.get(i));
			}
			tempNode.key.add(tempNode.m-2, newKey);
			tempNode.count = tempNode.m-1;
			Collections.sort(tempNode.key);
			
			
			if(popNode.isLeaf == true) {
				if(tempNode.key.size() % 2 != 0) { //tempNode�� newKey�� �����ؼ� key�� ������ Ȧ����
					newKey = tempNode.key.get(tempNode.m/2-1);
					for(int i = 0; i < tempNode.m/2 - 1 ; i++) {
						splitLeft.key.add(i, tempNode.key.get(i));
						splitRight.key.add(i, tempNode.key.get(i+tempNode.m/2));
						splitLeft.count++;
						splitRight.count++;
					}
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
					for (int j = tempNode.m/2 + 1 ; j < tempNode.m - 1 ; j++) {
						splitRight.key.add(j-tempNode.m/2-1, tempNode.key.get(j));
						splitRight.count++;
					}
					split.add(newKey);
					split.add(splitLeft);
					split.add(splitRight);
				}	
			}
			else {
				//tempNode���� newKey�� ����� �ε�������
				int newKeyIndex = 0;
				for(newKeyIndex = 0 ; newKeyIndex < tempNode.count ; newKeyIndex++) {
					if(tempNode.key.get(newKeyIndex) == newKey) {
						break;
					}
					else {
						continue;
					}
				}
				tempNode.child[newKeyIndex] = popNode.child[newKeyIndex].child[0];
				tempNode.child[newKeyIndex+1] = popNode.child[newKeyIndex].child[1];
				
				int a = 0;
				while (a < newKeyIndex) {
					tempNode.child[a] = popNode.child[a];
					a++;
				}
				a = a+1 ;
				while (a > newKeyIndex && a < tempNode.m - 1) {
					tempNode.child[a+1] = popNode.child[a];
					a++;
				}
							
				if(tempNode.key.size() % 2 != 0) { //tempNode�� newKey�� �����ؼ� key�� ������ Ȧ����
					newKey = tempNode.key.get(tempNode.m/2 - 1);
					for(int i = 0; i < tempNode.m/2 -1 ; i++) {
						splitLeft.key.add(i, tempNode.key.get(i));
						splitLeft.count++;
						splitLeft.child[i] = tempNode.child[i];
					}
					splitLeft.child[tempNode.m/2 -1] = tempNode.child[tempNode.m/2 -1];
					
					for (int j = tempNode.m/2 ; j < tempNode.m - 1 ; j++) {
						splitRight.key.add(j-tempNode.m/2, tempNode.key.get(j));
						splitRight.count++;
						splitRight.child[j-tempNode.m/2] = tempNode.child[j];
					}
					splitRight.child[splitRight.count] = tempNode.child[tempNode.m - 1];
					
					
					split.add(newKey);
					split.add(splitLeft);
					split.add(splitRight);
				}
				else {
					newKey = tempNode.key.get(tempNode.m/2);
					for(int i = 0; i < tempNode.m/2 ; i++) {
						splitLeft.key.add(i, tempNode.key.get(i));
						splitLeft.count++;
						splitLeft.child[i] = tempNode.child[i];
					}
					splitLeft.child[tempNode.m/2] = tempNode.child[tempNode.m/2];
					
					for (int j = tempNode.m/2 + 1 ; j < tempNode.m - 1 ; j++) {
						splitRight.key.add(j-tempNode.m/2-1, tempNode.key.get(j));
						splitRight.count++;
						splitRight.child[j-tempNode.m/2-1] = tempNode.child[j];
					}
					splitRight.child[splitRight.count] = tempNode.child[tempNode.m - 1];
					
					
					split.add(newKey);
					split.add(splitLeft);
					split.add(splitRight);
				}
			}
			
		
		}
		return split;
	}
	
	public Node searchDeleteKey(Node root2, int searchKey, Stack stack, Stack stack2) {
		/*
		 * 1. deleteKey�� �ϴ� Ʈ���� �ִ��� ����
		 * 2. deleteKey�� tree �ȿ� �����ϸ�, ���γ�� or �ܸ���� �� ��� �����ϴ��� ����
		 * 3. �ܸ�����̸� �״�� return
		 * 4. ���γ���̸� ����Ű���� ã�ư��� ����Ű�� deleteKey�� ã���� �ٲ㼭 return
		 */
		int i=0;
		Node root = root2;
		while(i < root.count && searchKey > root.key.get(i)) {
			i++;
		}
		
		if(i < root.count && searchKey == root.key.get(i)) {
//			return null; //delete�� searchKey�� ã��
		}
		else {
			stack2.push(root);
			stack.push(root);
			return searchDeleteKey(root.getChildNode(i), searchKey, stack, stack2);
		}
		
		Node internal = null;	
		int temp = 0;
		if(!root.isLeaf(root.m)) { //deleteKey�� ���� ��忡�� �߰ߵ�
			internal = root;
			stack.push(root);
//			stack2.push(root);
			root = root.getChildNode(i+1);
			do {
				stack.push(root);
//				stack2.push(root);
				root = root.getChildNode(0);
			}while(root != null);
			
			if(root == null) {
				root = (Node) stack.peek();
				temp = internal.key.get(i);
				
				internal.key.remove(i);
				internal.key.add(i, root.key.get(0));
				root.key.remove(0);
				root.key.add(0, temp);
				root2 = root;
			}
			
			return root2;
		}
		else { //deleteKey�� �ܸ���忡�� �߰�
			stack.push(root);
			stack2.push(root);
			root2 = root;
			return root2;
		}
		
	}
	
	public Node deleteBT(Node T, int m, int oldKey) {
		Stack<Node> stack = new Stack<>(); //deleteKey�� �ڸ��� ã�ư��� ��ó���� ������ ���� ����
		Stack<Node> stack2 = new Stack<>(); //��ó���� ������ �θ��带 ���� ����
		Vector deleteInfo;
		
		Node x = T;
		
		Node switchTree = searchDeleteKey(x, oldKey, stack, stack2);
		
		
		do {
			Node popNode = stack.pop();
			if(stack.size() >= 1) {
				popNode.parent = stack.peek();
			}
			else {
				popNode.parent = null;
			}
			Node LeftSibling = null;
			Node RightSibling = null;
			
			
			int deleteKeyIndex = 0;
			for (deleteKeyIndex = 0 ; deleteKeyIndex <= popNode.count ; deleteKeyIndex++) {
				if(popNode.key.get(deleteKeyIndex) == oldKey) {
					break;
				}
			}
			
			if(popNode.parent == null) {
				popNode.key.remove(deleteKeyIndex);
				popNode.count--;
				break;
			}
			
			int popNodeIndex = 0;
			for (popNodeIndex = 0 ; popNodeIndex <= popNode.parent.count ; popNodeIndex++) {
				if(popNode.parent.getChildNode(popNodeIndex) == popNode) {
					break;
				}
				else {
					continue;
				}
			}
			
			deleteInfo = deleteKey(m, popNode, oldKey);
			
			if(deleteInfo.get(0).equals("�ٷλ�������")) {
				//�ٷ� ����
				popNode.key.remove(deleteKeyIndex);
				popNode.count--;
				break;
			}
			else if(deleteInfo.get(0).equals("getLeft")) {
				//parent��
				LeftSibling = (Node) deleteInfo.get(1);
				if(popNode.isLeaf == true) {
					popNode.key.remove(deleteKeyIndex);
					//����� �ڸ��� �θ��� key��� �ְ�
					popNode.key.add(deleteKeyIndex, popNode.parent.key.get(popNodeIndex-1));
					//�θ� �ڸ��� ���� �ڽ��� ����ū�� �ְ�
					popNode.parent.key.remove(popNodeIndex-1);
					popNode.parent.key.add(popNodeIndex-1, LeftSibling.key.get(LeftSibling.count-1));
					//���� �ڽĿ��� ���� ū�� �����
					LeftSibling.key.remove(LeftSibling.count-1);
					//���� �ڽ��� count --1
					LeftSibling.count--;
					break;
				}
				else { //popNode�� �ڽ��� ����
					popNode.key.remove(deleteKeyIndex);
					//����� �ڸ��� �θ��� key��� �ְ�
					popNode.key.add(deleteKeyIndex, popNode.parent.key.get(popNodeIndex-1));
					//�θ� �ڸ��� ���� �ڽ��� ����ū�� �ְ�
					popNode.parent.key.remove(popNodeIndex-1);
					popNode.parent.key.add(popNodeIndex-1, LeftSibling.key.get(LeftSibling.count-1));
					LeftSibling.key.remove(LeftSibling.count-1);
					
					//���� �ڽ��� count --1
					LeftSibling.count--;
					popNode.child[0] = LeftSibling.getChildNode(LeftSibling.count+1);
					for(int i = LeftSibling.count+1 ; i < LeftSibling.m ; i++) {
						LeftSibling.child[i] = null;
					}
					break;
					
				}
				
				
				
			}
			else if(deleteInfo.get(0).equals("getRight")) {
				//parent��
				RightSibling = (Node) deleteInfo.get(1);
				if(popNode.isLeaf == true) {
					popNode.key.remove(deleteKeyIndex);
					//����� �ڸ��� �θ��� key��� �ְ�
					popNode.key.add(deleteKeyIndex, popNode.parent.key.get(popNodeIndex));
					//�θ� �ڸ��� ������ �ڽ��� ���� ������ �ְ�
					popNode.parent.key.remove(popNodeIndex);
					popNode.parent.key.add(popNodeIndex, RightSibling.key.get(0));
					//���� �ڽĿ��� ���� ū�� �����
					RightSibling.key.remove(0);
					//���� �ڽ��� count --1
					RightSibling.count--;
					break;
				}
				else {
					popNode.key.remove(deleteKeyIndex);
					//����� �ڸ��� �θ��� key��� �ְ�
					popNode.key.add(deleteKeyIndex, popNode.parent.key.get(popNodeIndex));
					//�θ� �ڸ��� ������ �ڽ��� ���� ������ �ְ�
					popNode.parent.key.remove(popNodeIndex);
					popNode.parent.key.add(popNodeIndex, RightSibling.key.get(0));
					//������ �ڽĿ��� ���� ������ �����
					RightSibling.key.remove(0);
					//������ �ڽ��� count --1
					RightSibling.count--;
					//�ϴ� m=4�϶���
					popNode.child[0] = popNode.child[1];
					popNode.child[1] = RightSibling.getChildNode(0);
					for(int i = 0 ; i <= RightSibling.count ; i++) {
						RightSibling.child[i] = RightSibling.child[i+1];
					}
					break;
				}
				
			}
			else if(deleteInfo.get(0).equals("mergeLeft")) {
				popNode.key.remove(deleteKeyIndex);
				
				LeftSibling = (Node) deleteInfo.get(1);
				for(int i = 0 ; i < LeftSibling.count; i++) {
					popNode.key.add(i, LeftSibling.key.get(i));
					popNode.count++;
				}
				if(popNode.isLeaf != true) {
					popNode.child[popNode.count] = popNode.child[1];
					for(int i = 0 ; i < popNode.count ; i++) {
						popNode.child[i] = LeftSibling.child[i];
					}
				}
				LeftSibling.key.clear();
				
				popNode.key.add(LeftSibling.count, popNode.parent.key.get(popNodeIndex-1));
				
				if(popNode.parent.count  > Math.ceil(popNode.m/2) - 1) {
					
					popNode.parent.key.remove(popNodeIndex-1);
					popNode.parent.count--;
					
					for(int i = popNodeIndex-1 ; i <= popNode.parent.count ; i++) {
						popNode.parent.child[i] = popNode.parent.child[i+1];
					}
					popNode.parent.child[popNode.parent.count+1] = null;
					break;
				}
				else {
					if(popNode.parent == T) {
						T = T.getChildNode(1);
						break;
					}
					oldKey = popNode.parent.key.get(popNodeIndex-1);
					popNode.parent.isLeaf = false;
					continue;
				}
			}
			else if(deleteInfo.get(0).equals("mergeRight")) {
				popNode.key.remove(deleteKeyIndex);
				
				RightSibling = (Node) deleteInfo.get(1);
				popNode.key.add(popNode.count-1, popNode.parent.key.get(popNodeIndex));
				for(int i = deleteKeyIndex ; i < deleteKeyIndex + RightSibling.count ; i++) {
					popNode.key.add(i+1, RightSibling.key.get(i-deleteKeyIndex));
					popNode.count++;
				}
				if(popNode.isLeaf != true) {
					popNode.child[0] = popNode.child[1];
					for(int i = 0 ; i <= RightSibling.count ; i++) {
						popNode.child[i+1] = RightSibling.child[i];
					}
				}
				RightSibling.key.clear();
				if(popNode.parent.count  > Math.ceil(popNode.m/2) - 1) {
					for(int i = popNodeIndex+2 ; i < popNode.parent.count+1 ; i++) {
						popNode.parent.child[i-1] = popNode.parent.child[i];
					}
					popNode.parent.child[popNode.parent.count]=null;
					popNode.parent.key.remove(popNodeIndex);
					popNode.parent.count--;
					break;
				}
				else {
					if(popNode.parent == T) {
						T = T.getChildNode(1);
						break;
					}
					popNode.parent.child[popNodeIndex+1] = popNode.parent.child[popNodeIndex];
					popNode.parent.child[popNodeIndex] =  null;
					oldKey = popNode.parent.key.get(popNodeIndex);
					popNode.parent.isLeaf = false;
					continue;
				}
				
			}
		}while(!stack.isEmpty());
			
		return T;

	}
	
	public Vector deleteKey(int count, Node popNode, int oldKey) {
		/*
		 * popNode���� oldKey�� ��������
		 * oldKey�� ���� �� 
		 * �ش� popNode���� ������
		 * 1. underflow�� �߻� -> �׳� ����
		 * 2. underflow�߻� -> ������忡�� ������
		 * 3. ������忡�� �������� -> �պ��ؾ���
		 */
		Node x = popNode;
		Node parent = popNode.parent;
		Vector deleteInfo = new Vector();
		
		if(popNode.count > Math.ceil(popNode.m/2) - 1) {
			deleteInfo.add("�ٷλ�������");
		}
		else if(popNode.count <= Math.ceil(popNode.m/2) - 1) {
			int popNodeIndex = 0;
			for (popNodeIndex = 0 ; popNodeIndex <= popNode.parent.count ; popNodeIndex++) {
				if(popNode.parent.getChildNode(popNodeIndex) == popNode) {
					break;
				}
				else {
					continue;
				}
			}
			//���� ��� �������� ���� -> ��й� ����
			if(parent.getChildNode(parent.count) == popNode) { //������ ���� ������� ������ ��
				if(parent.getChildNode(parent.count-1).count > Math.ceil(popNode.m/2) - 1) { //��й� ����
					deleteInfo.add("getLeft");
					deleteInfo.add(parent.getChildNode(parent.count-1)); //���� ����
				}
				else { //�պ��ؾ���
					deleteInfo.add("mergeLeft");
					deleteInfo.add(parent.getChildNode(parent.count-1)); //���� ����
				}

			}
			else if(parent.getChildNode(0) == popNode){ //������ ������ ������� �����ö�
				if(parent.getChildNode(1).count > Math.ceil(popNode.m/2) - 1) { //��й� ����
					deleteInfo.add("getRight");
					deleteInfo.add(parent.getChildNode(1)); //������ ����
				}
				else { //�պ��ؾ���
					deleteInfo.add("mergeRight");
					deleteInfo.add(parent.getChildNode(1)); //������ ����
				}
			}
			else { //���� �������� key�ǰ��� �����ſ��� ��������
				int i = 0;
				
				for(i = 0 ; i <= parent.count ; i++) {
					if(parent.getChildNode(i) == popNode) {
						break;
					}
				}
				
				if(parent.getChildNode(i-1).count >= parent.getChildNode(i+1).count) {
					//���� ���ߴµ� ���� ������ �� ������
					if(parent.getChildNode(i-1).count > Math.ceil(popNode.m/2) - 1) { //��й� ����
						deleteInfo.add("getLeft");
						deleteInfo.add(parent.getChildNode(i-1)); //���� ����
					}
					else { //�պ��ؾ���
						deleteInfo.add("mergeLeft");
						deleteInfo.add(parent.getChildNode(i-1)); //���� ����
					}
				}
				else { //���� ���ߴµ� ���� ������ �� ������
					if(parent.getChildNode(i+1).count > Math.ceil(popNode.m/2) - 1) { //��й� ����
						deleteInfo.add("getRight");
						deleteInfo.add(parent.getChildNode(i+1)); //������ ����
					}
					else { //�պ��ؾ���
						deleteInfo.add("mergeRight");
						deleteInfo.add(parent.getChildNode(i+1)); //������ ����
					}
				}
				
			}
		}
		return deleteInfo;
	}

}

