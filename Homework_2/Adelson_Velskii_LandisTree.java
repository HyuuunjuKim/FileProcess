package Homework_2;
import java.util.*;

public class Adelson_Velskii_LandisTree {
	/*
	 * class Node는 BST에서 사용한 Node + balanceFactor
	 * 
	 * <checkBalance>
	 * 1. 새로 들어간 노드가 newKey
	 * 2. newKey의 부모 노드를 검색하면서 bF의 균형이 깨진 노드를 p
	 * 3. p의 부모 노드를 q
	 * 4. newKey가 p의 왼쪽/오른쪽 자식의 왼쪽/오른쪽 서브트리에 삽입 됨에 따라 LL/RR/LR/RL/NO 판별 
	 * 
	 * <rotateTree>
	 * 1. checkBalance가 return하는 값들에 따라서
	 * 2. p를 rootNode로 해서 회전
	 * 
	 * <insertAVL>
	 * 1. insertBST
	 * 2. insertBST에 의한 checkBalance
	 * 3. rotationType 출력
	 * 4. checkBalance가 리턴하는 값들에 의해 rotateTree
	 */
	public static class Node {
		public int key;
		public Node left;
		public Node right;
		public int balanceFactor;
		
		public Node(int key) { //생성자
			this.key= key;
		}
		public void getNode(int newKey) { //getNode 새로운 node설정
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
		// balanceFactor관련 get,set method 추가
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
	
	
	public static Node insertBST(Node T, int newKey) { //T는 현존하는 트리, newKey는 삽입하려는 새로운 value
		Node p = T;
		Node q = null; //부모 node가 될 예정. 초기화는 null로
		
		while(p != null) { //삽입하려는 키 값을 가진 노드가 이미 있는지 검사
			if(newKey == p.getKey()) {
				System.out.println("이미 존재하는 값입니다.");				
			}
			q = p; //q는 p의 부모 노드로 지시
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
		int[] keyArray= new int[height(T)]; //newKey를 찾는데 지나쳐가는 Node의 key값 저장 할 배열
		for (int i = 0 ; i < height(T) ; i++) {
			keyArray[i] = 0; //배열 초기화
		}
		int[] bFArray = new int[height(T)]; //newKey를 찾는데 지나쳐가는 Node의 bF값 저장 할 베열
		for (int i = 0 ; i < height(T) ; i++) {
			bFArray[i] = 0; //배열 초기화
		}
		char[] directionArray = new char[height(T) -1]; //newKey를 찾는데 지나쳐가는 edge로 인해 L or R 저장 할 배열
		for (int i = 0 ; i < height(T)-1 ; i++) {
			directionArray[i] = '0'; //배열 초기화
		}
		
		Vector forRotate = new Vector(); //회전하는데 필요한 값들 담을 배열
		
		p = T;
		q = null; //부모 node가 될 예정. 초기화는 null로
		
		/*
		 * newKey까지 찾아가면서 지나쳐가는 Key들과 balanceFactor(node)을 저장
		 * newKey까지 찾아가면서 지나쳐가는 edge로 인한 방향을 저장(L or R)
		 */
		int index = 0;
		int directIndex = 0;
		while(p != null) { 
			keyArray[index] = p.getKey();
			bFArray[index] = p.getBalanceFactor(p);
			if(newKey == p.getKey()) {
				break;
			}
			q = p; //q는 p의 부모 노드로 지시
			
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
		
		rotationType = "NO"; //rotationType 초기화
		
		Node r1 = T; 
		Node r2 = T;
		/*
		 * bFArray를 뒤에서 부터 조사하며 트리의 밸런스가 깨진부분을 검사
		 * 즉, bF가 -2나 2가 나오면 해당하는 index의 key값을 가진 Node를 T에서 찾아 p에 저장(r1 이용)
		 * p의 부모 노드를 찾아 q에 저장(r2 이용)
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
					else if(j == 0){ // 깨진 밸런스로부터 찾은 노드 p가 root node인 경우 
						q = null;
						break;
					}
					
				}
				
				/*
				 * 밸런스가 깨진 노드부터 newKey까지 가는 경로를 edge를 통해 찾고
				 * edge마다 저장된 L 또는 R을 통해 rotationType 결정(String type)
				 */
				rotationType = new String(directionArray, j, 2);
				/*
				 * rotateTree에 필요한 값들을 forRotate에 저장
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
			 * 트리의 균형이 깨지지 않은 경우
			 * rotationType에 "NO"를 저장
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
		
		//복사 예정
		Node p1 = null;
		Node q1 = null;		
		
		if(rotationType == "NO") {  //rotationType이 "NO"이면 회전하지 않고 그대로 T return
			return T;
		}
		
		//p가 root node인 경우(q가 null인 경우)
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
		//p가 q의 오른쪽 자식인 경우
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
		//p가 q의 왼쪽 자식인 경우
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
