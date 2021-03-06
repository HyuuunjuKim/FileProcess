package Homework_3;

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
	

	public Node search(Node root, int searchKey, Stack stack, Stack parent) {		
		int i=0;
		
		while(i < root.count && searchKey > root.key.get(i)) {
			i++;
		}
		
		if(i < root.count && searchKey == root.key.get(i)) {
			System.out.println("이미 존재 하는 key입니다.");
			return root; //searchKey가 이미 존재하면 root를 리턴
		}
		
		
		if(root.isLeaf(root.m) == true) {
			parent.push(root);
			stack.push(root);
			return null;
		}
		else {
			parent.push(root);
			stack.push(root);
			return search(root.getChildNode(i), searchKey, stack, parent);
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
		Stack<Node> parent = new Stack<>(); //거처가는 노드들의 부모노드를 담을 스택
		
		Node x = T;
		Node left = null;
		Node right = null;
		int middleKey;
		Vector split;
		
		/*
		 * 새로운 노드를 삽입하는 경우면 null리턴
		 * 이미 존재하는 노드를 삽입하는 경우면 T리턴
		 */
		Node NUllreturn = search(x, newKey, stack, parent); 
		if (NUllreturn == null) {
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
					/*
					 * 현재는 stack와 parent 스택이 똑같은 상태
					 * 하지만 처음에 parent 스택에서 top노드를 꺼낸 후,
					 * stack와 parent 스택을 계속 해서 동시에 pop하면
					 * parent 스택에서 pop되는 노드는 stack의 부모 노드
					 */
					Node delete_top = null;
					if(parent.empty() != true) {
						delete_top = parent.pop();
					}
					else {
						continue;
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
						newKey = (int)split.get(0); //중간 값
						left = (Node)split.get(1); //왼쪽 자식이 될 노드
						right = (Node)split.get(2); //오른쪽 자식이 될 노드
						popNode.child[0] = left; 
						popNode.child[1] = right;
						
						Node parent_pop = null;
						//pop 한 노드의 parent노드 pop하여 부모노드로 설정
						if(parent.empty() != true) {
							parent_pop = parent.pop();
							popNode.parent = parent_pop;
						}
						else {
							popNode.parent = null;
						}
						//popNode는 더 이상 단말노드가 아님
						popNode.isLeaf = false;
						
						//root Node를 split하는 경우
						if (popNode.parent ==  null) {
							Node newNode = new Node(m, null);
							newNode.key.add(0, newKey);
							newNode.count = 1;
							
							newNode.child[0] = left;
							newNode.child[1] = right;
							
							newNode.child[0].count = left.count;
							newNode.child[1].count = right.count;
							
							T = newNode;
							break;
						}
						else if (popNode.parent.count <= m-1){
							//pop한 노드의 현재 위치에 들어갈 노드
							insert = new Node(m, parent_pop); 
							insert.key.add(0, newKey);
							//pop한 노드에 inserKey를 통해 받은 중간값만 삽입
							popNode.key = insert.key;
							popNode.count=1;
							
							popNode.child[0] = left;
							popNode.child[left.count] = right;
							
							popNode.child[0].count = left.count;
							popNode.child[left.count].count = right.count;
							
							break;
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
			Node tempNode = new Node(popNode.m+1,popNode.parent);
			for (int i = 0 ; i < x.count ; i++) {
				tempNode.key.add(i, x.key.get(i));
			}
			tempNode.key.add(tempNode.m-2, newKey);
			tempNode.count = tempNode.m-1;
			Collections.sort(tempNode.key);
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
				for (int j = tempNode.m/2 + 1 ; j < tempNode.m - 1 ; j++)
					splitRight.key.add(j-tempNode.m/2-1, tempNode.key.get(j));
					splitRight.count++;
				split.add(newKey);
				split.add(splitLeft);
				split.add(splitRight);
			}	
		
		}
		return split;
	}

}
