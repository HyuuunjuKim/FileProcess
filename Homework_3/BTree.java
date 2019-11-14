package Homework_3;

import java.util.*;

import Homework_2.Adelson_Velskii_LandisTree.Node;

public class BTree {
	public static class Node {
		public int[] key; //현재 노드 안 key배열
		public Node[] child; //현재 노드의 자식 노드들 배열
		public Node parent; //현재 노드의 부모 노드(1개)		
		public int m;
		public int count; //현재 노드 안 key의 개수		
		public boolean isLeaf; //단말 노드인지 아닌지 
		
		public Node() {
			// default 생성자
		}
		
		public Node(int m, Node parent) {
			this.m = m;
			this.parent = parent;
			this.key = new int[m-1];
			this.child = new Node[m];
			this.isLeaf = true; //모든 노드는 처음에는 leaf
			this.count = 0; //처음에는 key배열에 아무것도 안담김
			
		}
		
		public int getValue(int index) {
			return key[index];
		}
		
		public Node getChildNode(int index) {
			return child[index];
		}
		public Node reset(int m, Node parent) {
			Node newNode = new Node(m, parent);
			this.isLeaf = false;
			
			return newNode;
		}
	}
	
	public static void inorderBT(Node T, int m) {
		if(T != null) {
			for (int i = 0 ; i < T.count ; i++) {
				inorderBT(T.child[i], m);
				System.out.print(" " + T.key[i]);	
			}
			inorderBT(T.child[T.count], m);
		}
		else {
			return;	
		}
	}
	
//	public static int size;
//	Node root;
//	
//	//BTree생성자
//	public BTree(int m) {
//		this.size = m;
//		root = new Node(m, null);
//	}
	
	public Node search(Node root, int searchKey, Stack stack) {		
		int i=0;
		
		while(i < root.count && searchKey > root.key[i]) {
			i++;
		}
		
		
		if(i < root.count && searchKey == root.key[i]) {
			System.out.println("이미 존재 하는 key입니다.");
			return root; //searchKey를 root Node에서 찾음
		}
		
		
		if(root.isLeaf) {
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
			T.key[0] = newKey;
			T.count = 1;
			return T;
		}
		
		/*
		 * Node가 비어있지 않은 경우
		 * 1. T에 newKey를 삽입할 자리를 찾기
		 */
		Stack<Node> stack = new Stack<>();
		Node x = T;
		Node left = null;
		Node right = null;
		int middleKey;
		Vector split;
		
		search(x, newKey, stack); //stack에 지나가는 노드들 push하면서 단말까지 갔을때  null return;
		if(stack.size() == 1 && T.count < m-1) {
			T.key[T.count] = newKey;
			T.count++;
			Arrays.sort(T.key);
			return T;
		}
		else {
			do{
				Node popNode = stack.pop();
				split = insertKey(popNode.count, popNode, newKey);
				
				
				if ((int)split.get(0) == -1) {
					popNode.key[popNode.count] = newKey;
					break;
				}
				else {
					newKey = (int)split.get(0);
					left = (Node)split.get(1);
					right = (Node)split.get(2);
					popNode.child[0] = left;
					popNode.child[1] = right;
					popNode.isLeaf = false;
					
					if (popNode.parent ==  null) {
						Node newNode = new Node(m, null);
						newNode.key[0] = newKey;
						newNode.child[0] = left;
						newNode.child[1] = right;
						
						newNode.count = 1;
						newNode.child[0].count = 1;
						newNode.child[1].count = 1;
						T = newNode;
						break;
					}
					
					else if(popNode.parent.m == m) {
						insertKey(popNode.count, popNode, newKey);
					}
					else if (popNode.parent.m != m){
						popNode.reset(m, popNode.parent);
						popNode.key[0] = newKey;
						popNode.child[0] = left;
						popNode.child[1] = right;
						popNode.isLeaf = false;
					}
					
					
				}					
			}while(!stack.isEmpty());
		
//		if(stack.isEmpty()){//stack이 empty라는 건 level이 하나 증가한다는 것
//			T = new Node(m, null);
//			T.key[0] = newKey;
////			T.child[0] = 
////			T.child[1] = 
////			
////			return T;
//		}
		
		}
		return T;
				
	}
	
	public Vector insertKey(int count, Node popNode, int newKey) {
		Node x = popNode;
		Vector split = new Vector(); //중간 값, 왼쪽 노드, 오른쪽 노드 리턴
		Node splitLeft = new Node(popNode.m, popNode);
		Node splitRight = new Node(popNode.m, popNode);
		if(count != popNode.m -1) {
			//삽입하고 종료?
//			
//			x.key[count] = newKey;
			split.add(-1);
			
		}
		else {
			//중간 값 찾아서 넘기면 되나?
			
			Node tempNode = new Node(popNode.m+1,popNode.parent);
			for (int i = 0 ; i < x.count ; i++) {
				tempNode.key[i] = x.key[i];
			}
			tempNode.key[tempNode.m-2] = newKey;
			tempNode.count = tempNode.m-1;
			Arrays.sort(tempNode.key);
			if(tempNode.key.length % 2 != 0) { //tempNode에 newKey를 포함해서 key의 개수가 홀수개
				newKey = tempNode.key[tempNode.m/2-1];
				for(int i = 0; i < tempNode.m/2 - 1 ; i++) {
					splitLeft.key[i] = tempNode.key[i];
					splitRight.key[i] = tempNode.key[i+tempNode.m/2];
					count++;
				}
//				tempNode.child[0] = splitLeft;
//				tempNode.child[1] = splitRight;
				split.add(newKey);
				split.add(splitLeft);
				split.add(splitRight);
			}
			else { //tempNode에 newKey를 포함해서 key의 개수가 짝수개
				newKey = tempNode.key[tempNode.m/2];
				for(int i = 0; i < tempNode.m/2 - 1 ; i++) {
					splitLeft.key[i] = tempNode.key[i];
				}
				for(int j = tempNode.m/2+1; j < tempNode.count ; j++) {
					splitRight.key[j-tempNode.m/2-1] = tempNode.key[j];
				}
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
