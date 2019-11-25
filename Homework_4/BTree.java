package Homework_4;

import java.util.*;


public class BTree {
	public static class Node {
		public ArrayList<Integer> key; //현재 노드 안 key배열리스트
		public Node[] child; //현재 노드의 자식 노드들 배열
		public Node parent; //현재 노드의 부모 노드(1개)		
		public int m;
		public int count; //현재 노드 안 key의 개수		
		public boolean isLeaf; //단말 노드인지 아닌지 
		
				
		public Node(int m, Node parent) {
			this.m = m;
			this.parent = parent;
			this.key = new ArrayList<Integer>();
			this.child = new Node[m];
			this.isLeaf = true; //모든 노드는 처음에는 leaf
			this.count = 0; //처음에는 key배열에 아무것도 안담김
			
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
			System.out.println("이미 존재 하는 key입니다.");
			return root; //searchKey가 이미 존재하면 root를 리턴
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
		 * Node가 비어있는 경우
		 * T에 newKey삽입 후 종료
		 */
		if(T == null) {
			T = new Node(m, null);
			T.key.add(0, newKey);
			T.count = 1;
			return T;
		}
		
		/*
		 * Node가 비어있지 않은 경우
		 * 1. T에 newKey를 삽입할 자리를 찾기
		 */
		Stack<Node> stack = new Stack<>(); //newKey의 자리를 찾아가며 거처가는 노드들을 담을 스택
		
		Node x = T;
		Node left = null;
		Node right = null;
		int middleKey;
		Vector split;
		
		/*
		 * 새로운 노드를 삽입하는 경우면 null리턴
		 * 이미 존재하는 노드를 삽입하는 경우면 T리턴
		 */
		Node NULLreturn = search(x, newKey, stack); 
		if (NULLreturn == null) {
			//삽입 초반의 경우 
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
					
					Node popNode = stack.pop(); //newKey를 삽입시킬 위치
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
					
					//pop한 노드가 꽉 차지 않아 현재 추가 가능 상태
					if (split.get(0).equals("현재추가가능")) {
						popNode.key.add(popNode.count, newKey);
						popNode.count++;
						Collections.sort(popNode.key);						
						break;
					}
					/*
					 * pop한 노드가 꽉 차 있어, 현재 추가 불가능 상태
					 * insertKey를 통해 return받은 값들을 통해 split시도
					 */
					else {
						middleKey = (int)split.get(0); //중간 값
						left = (Node)split.get(1); //왼쪽 자식이 될 노드
						right = (Node)split.get(2); //오른쪽 자식이 될 노드
						
						//popNode는 더 이상 단말노드가 아님
						popNode.isLeaf = false;
						
						//root Node를 split하는 경우
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
							//pop한 노드에 inserKey를 통해 받은 중간값만 삽입
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
		Vector split = new Vector(); //중간 값, 왼쪽 노드, 오른쪽 노드 리턴 예정
		Node splitLeft = new Node(popNode.m, popNode);
		Node splitRight = new Node(popNode.m, popNode);
		
		//현재 pop한 노드에 추가적으로 key 삽입 가능 상태
		if(count != popNode.m - 1) {
			split.add("현재추가가능");
		}
		//현재 pop한 노드가 꽉 차있어, split한 노드와 중간 키를 return하여 넘겨줘야하는 상황
		else {
			Node tempNode = new Node(popNode.m+1, popNode.parent);
			for (int i = 0 ; i < x.count ; i++) {
				tempNode.key.add(i, x.key.get(i));
			}
			tempNode.key.add(tempNode.m-2, newKey);
			tempNode.count = tempNode.m-1;
			Collections.sort(tempNode.key);
			
			
			if(popNode.isLeaf == true) {
				if(tempNode.key.size() % 2 != 0) { //tempNode에 newKey를 포함해서 key의 개수가 홀수개
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
				else { //tempNode에 newKey를 포함해서 key의 개수가 짝수개
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
				//tempNode에서 newKey가 몇번쨰 인덱스인지
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
							
				if(tempNode.key.size() % 2 != 0) { //tempNode에 newKey를 포함해서 key의 개수가 홀수개
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
		 * 1. deleteKey가 일단 트리에 있는지 조사
		 * 2. deleteKey가 tree 안에 존재하면, 내부노드 or 단말노드 중 어디에 존재하는지 조사
		 * 3. 단말노드이면 그대로 return
		 * 4. 내부노드이면 후행키까지 찾아가서 후행키와 deleteKey를 찾은후 바꿔서 return
		 */
		int i=0;
		Node root = root2;
		while(i < root.count && searchKey > root.key.get(i)) {
			i++;
		}
		
		if(i < root.count && searchKey == root.key.get(i)) {
//			return null; //delete할 searchKey를 찾음
		}
		else {
			stack2.push(root);
			stack.push(root);
			return searchDeleteKey(root.getChildNode(i), searchKey, stack, stack2);
		}
		
		Node internal = null;	
		int temp = 0;
		if(!root.isLeaf(root.m)) { //deleteKey가 내부 노드에서 발견됨
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
		else { //deleteKey가 단말노드에서 발견
			stack.push(root);
			stack2.push(root);
			root2 = root;
			return root2;
		}
		
	}
	
	public Node deleteBT(Node T, int m, int oldKey) {
		Stack<Node> stack = new Stack<>(); //deleteKey의 자리를 찾아가며 거처가는 노드들을 담을 스택
		Stack<Node> stack2 = new Stack<>(); //거처가는 노드들의 부모노드를 담을 스택
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
			
			if(deleteInfo.get(0).equals("바로삭제가능")) {
				//바로 삭제
				popNode.key.remove(deleteKeyIndex);
				popNode.count--;
				break;
			}
			else if(deleteInfo.get(0).equals("getLeft")) {
				//parent가
				LeftSibling = (Node) deleteInfo.get(1);
				if(popNode.isLeaf == true) {
					popNode.key.remove(deleteKeyIndex);
					//지우는 자리에 부모의 key대신 넣고
					popNode.key.add(deleteKeyIndex, popNode.parent.key.get(popNodeIndex-1));
					//부모 자리에 왼쪽 자식의 가장큰거 넣고
					popNode.parent.key.remove(popNodeIndex-1);
					popNode.parent.key.add(popNodeIndex-1, LeftSibling.key.get(LeftSibling.count-1));
					//왼쪽 자식에서 가장 큰거 지우고
					LeftSibling.key.remove(LeftSibling.count-1);
					//왼쪽 자식의 count --1
					LeftSibling.count--;
					break;
				}
				else { //popNode가 자식이 있음
					popNode.key.remove(deleteKeyIndex);
					//지우는 자리에 부모의 key대신 넣고
					popNode.key.add(deleteKeyIndex, popNode.parent.key.get(popNodeIndex-1));
					//부모 자리에 왼쪽 자식의 가장큰거 넣고
					popNode.parent.key.remove(popNodeIndex-1);
					popNode.parent.key.add(popNodeIndex-1, LeftSibling.key.get(LeftSibling.count-1));
					LeftSibling.key.remove(LeftSibling.count-1);
					
					//왼쪽 자식의 count --1
					LeftSibling.count--;
					popNode.child[0] = LeftSibling.getChildNode(LeftSibling.count+1);
					for(int i = LeftSibling.count+1 ; i < LeftSibling.m ; i++) {
						LeftSibling.child[i] = null;
					}
					break;
					
				}
				
				
				
			}
			else if(deleteInfo.get(0).equals("getRight")) {
				//parent가
				RightSibling = (Node) deleteInfo.get(1);
				if(popNode.isLeaf == true) {
					popNode.key.remove(deleteKeyIndex);
					//지우는 자리에 부모의 key대신 넣고
					popNode.key.add(deleteKeyIndex, popNode.parent.key.get(popNodeIndex));
					//부모 자리에 오른쪽 자식의 가장 작은거 넣고
					popNode.parent.key.remove(popNodeIndex);
					popNode.parent.key.add(popNodeIndex, RightSibling.key.get(0));
					//왼쪽 자식에서 가장 큰거 지우고
					RightSibling.key.remove(0);
					//왼쪽 자식의 count --1
					RightSibling.count--;
					break;
				}
				else {
					popNode.key.remove(deleteKeyIndex);
					//지우는 자리에 부모의 key대신 넣고
					popNode.key.add(deleteKeyIndex, popNode.parent.key.get(popNodeIndex));
					//부모 자리에 오른쪽 자식의 가장 작은거 넣고
					popNode.parent.key.remove(popNodeIndex);
					popNode.parent.key.add(popNodeIndex, RightSibling.key.get(0));
					//오른쪽 자식에서 가장 작은거 지우고
					RightSibling.key.remove(0);
					//오른쪽 자식의 count --1
					RightSibling.count--;
					//일단 m=4일때만
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
		 * popNode에서 oldKey를 지워야함
		 * oldKey를 지울 때 
		 * 해당 popNode에서 지워도
		 * 1. underflow안 발생 -> 그냥 지움
		 * 2. underflow발생 -> 형제노드에서 가져옴
		 * 3. 형제노드에서 못가져옴 -> 합병해야함
		 */
		Node x = popNode;
		Node parent = popNode.parent;
		Vector deleteInfo = new Vector();
		
		if(popNode.count > Math.ceil(popNode.m/2) - 1) {
			deleteInfo.add("바로삭제가능");
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
			//형제 노드 가져오기 가능 -> 재분배 가능
			if(parent.getChildNode(parent.count) == popNode) { //무조건 왼쪽 형제노드 가져올 떄
				if(parent.getChildNode(parent.count-1).count > Math.ceil(popNode.m/2) - 1) { //재분배 가능
					deleteInfo.add("getLeft");
					deleteInfo.add(parent.getChildNode(parent.count-1)); //왼쪽 형제
				}
				else { //합병해야함
					deleteInfo.add("mergeLeft");
					deleteInfo.add(parent.getChildNode(parent.count-1)); //왼쪽 형제
				}

			}
			else if(parent.getChildNode(0) == popNode){ //무조건 오른쪽 형제노드 가져올때
				if(parent.getChildNode(1).count > Math.ceil(popNode.m/2) - 1) { //재분배 가능
					deleteInfo.add("getRight");
					deleteInfo.add(parent.getChildNode(1)); //오른쪽 형제
				}
				else { //합병해야함
					deleteInfo.add("mergeRight");
					deleteInfo.add(parent.getChildNode(1)); //오른쪽 형제
				}
			}
			else { //양쪽 다있으면 key의개수 많은거에서 가져오기
				int i = 0;
				
				for(i = 0 ; i <= parent.count ; i++) {
					if(parent.getChildNode(i) == popNode) {
						break;
					}
				}
				
				if(parent.getChildNode(i-1).count >= parent.getChildNode(i+1).count) {
					//개수 비교했는데 왼쪽 형제가 더 많으면
					if(parent.getChildNode(i-1).count > Math.ceil(popNode.m/2) - 1) { //재분배 가능
						deleteInfo.add("getLeft");
						deleteInfo.add(parent.getChildNode(i-1)); //왼쪽 형제
					}
					else { //합병해야함
						deleteInfo.add("mergeLeft");
						deleteInfo.add(parent.getChildNode(i-1)); //왼쪽 형제
					}
				}
				else { //개수 비교했는데 오쪽 형제가 더 많으면
					if(parent.getChildNode(i+1).count > Math.ceil(popNode.m/2) - 1) { //재분배 가능
						deleteInfo.add("getRight");
						deleteInfo.add(parent.getChildNode(i+1)); //오른쪽 형제
					}
					else { //합병해야함
						deleteInfo.add("mergeRight");
						deleteInfo.add(parent.getChildNode(i+1)); //오른쪽 형제
					}
				}
				
			}
		}
		return deleteInfo;
	}

}

