package Homework_1;

public class BinarySearchTree {
	/*
	 * insertBST node삽입
	 * deleteBST node삭제
	 * getNode 새로운 node설정
	 * height node의 height return
	 * noNodes node의 개수
	 * maxNode 트리 안에서 가장 큰 value
	 * minNode 트리 안에서 가장 작은 value
	 * inorder 중위 순회
	 */
	
	public static class Node {
		public int key;
		public Node left;
		public Node right;
		
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
			System.out.println("삭제할 노드가 없습니다.");
            return null;
		}
		Node q = null;
		Node p = T;

		while(p.getKey() != deleteKey){ 
			q = p; //q를 부모 노드로 설정
			if(p == null) {
				System.out.println("노드를 찾을 수 없습니다.");
                return T;
			}			
			if(p.getKey() < deleteKey) { //삭제하려는 원소가 더 크면
				p = p.getRight(); //오른쪽 서브트리로 이동
			}
			else { //삭제 하려는 원소가 더 작으면
				p = p.getLeft(); //왼쪽 서브트리로 이동
			}
		} 
				
		//삭제하려는 노드의 차수가 0이면
		if(p.getLeft() == null && p.getRight() == null) {
			if(q == null) { //삭제할 노드는 있는데 부모노드가 없음. 즉, 삭제하려는 노드가 루트노드이면
				T = null;
				return T;
			}
			if(q.getLeft() == p) { //삭제할 노드가 부모의 왼쪽 노드이면
				q.setLeft(null);
			}
			else { //삭제할 노드가 부모의 오른쪽 노드이면
				q.setRight(null);
			}
		}
		
		//삭제하려는 노드의 차수가 1이면
		else if(p.getLeft() == null || p.getRight() == null) {		
			if(p.getLeft() != null) { //삭제할 노드가 왼쪽 자식을 갖고 있으면서
				if(q == null) { //삭제할 노드는 있는데 부모노드가 없음. 즉, 삭제하려는 노드가 루트노드이면
					T.setKey(p.getLeft().getKey());
					T.setLeft(p.getLeft().getLeft());
					T.setRight(p.getLeft().getRight());
					return T;
				}
				else {
					if(q.getLeft() == p) { //삭제할 노드가 부모의 왼쪽 노드이면
						q.setLeft(p.getLeft());
					}
					else { //삭제할 노드가 부모의 오른쪽 노드이면
						q.setRight(p.getLeft());
					}
				}
			}
			else { //삭제할 노드가 오른쪽 자식을 갖고 있으면서
				if(q == null) { //삭제할 노드는 있는데 부모노드가 없음. 즉, 삭제하려는 노드가 루트노드이면
					T.setKey(p.getRight().getKey());
					T.setLeft(p.getRight().getLeft());
					T.setRight(p.getRight().getRight());
					return T;
				}
				else {
					if(q.getLeft() == p) { //삭제할 노드가 부모의 왼쪽 노드이면
						q.setLeft(p.getRight());
					}
					else { //삭제할 노드가 부모의 오른쪽 노드이면
						q.setRight(p.getRight());
					}
				}
			}
		}
		
		/*
		 * 삭제하려는 노드의 차수가 2이면
		 * 1. 자식 트리의 높이 비교 -> 큰 높이의 트리에서 최대 or최솟값 가지 노드로 대체
		 * 2. 자식 트리의 높이 같으면 -> 자식 트리의 노드 개수 비교 -> 큰 높이의 트리에서 최대 or최솟값 가지 노드로 대체
		 */
		else if(p.getLeft() != null && p.getRight() != null){ 
			
			int heightLeft = height(p.getLeft());
			int heightRight = height(p.getRight());
			
			int noNodesLeft = noNodes(p.getLeft());
			int noNodesRight = noNodes(p.getRight());
			
			String flag=" ";
			Node r = new Node(0);
			if(heightLeft > heightRight) { //왼쪽 서브트리 높이 > 오른쪽 서브트리 높이
				r = maxNode(p.getLeft()); //왼쪽 서브트리에서 가장 큰 key가진 노드
				flag = "LEFT";
			}
			else if(heightLeft < heightRight) { //왼쪽 서브트리 높이 < 오른쪽 서브트리 높이
				r = minNode(p.getRight()); //오른쪽 서브트리에서 가장 작은 key가진 노드
				flag = "RIGHT";
			}
			else { //왼쪽 서브트리 높이 == 오른쪽 서브트리 높이
				if(noNodesLeft > noNodesRight) { //왼쪽 서브트리에서 노드의 개수 > 오른쪽 서브트리에서 노드의 개수 
					r = maxNode(p.getLeft()); //왼쪽 서브트리에서 가장 큰 key가진 노드
					flag = "LEFT";//왼쪽 서브트리에서 가장 큰 key가진 노드
				}
				else { //왼쪽 서브트리에서 노드의 개수 <= 오른쪽 서브트리에서 노드의 개수
					r = minNode(p.getRight()); //오른쪽 서브트리에서 가장 작은 key가진 노드
					flag = "RIGHT";
				}
			}
			
			p.setKey(r.getKey()); //삭제할 원소의 자리에 r넣기
			if(flag == "LEFT") { //flag가 left이면 왼쪽 서브트리에서 r지우기 
				/*
				 * r이 p의 바로 직계 자식인경우, 재귀로 deleteBST를 호출 시 root노드가 되어버린 r을 지워야하는데 r이 p의 부모였던 q와 연결이 되지않아 오류가 발생함
				 * 따라서 이러한 경우 p를 인자로 함께 넘기는 deleteBST를 호출
				 */
				if(p.getLeft() == r || p.getRight() == r) {
					Node parentP = p;
					deleteBST(p.getLeft(), r.getKey(), parentP);
				}
				else {
					deleteBST(p.getLeft(), r.getKey());
				}
			}
			else { //flag가 right이면 오른쪽 서브트리에서 r지우기 
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
			System.out.println("삭제할 노드가 없습니다.");
            return null;
		}
		Node q = parent;
		Node p = T;

		while(p.getKey() != deleteKey){ 
			q = p; //q를 부모 노드로 설정
			if(p == null) {
				System.out.println("노드를 찾을 수 없습니다.");
                return T;
			}
			
			if(p.getKey() < deleteKey) { //삭제하려는 원소가 더 크면
				p = p.getRight(); //오른쪽 서브트리로 이동
			}
			else { //삭제 하려는 원소가 더 작으면
				p = p.getLeft(); //왼쪽 서브트리로 이동
			}
		}
		if(p == null) { //삭제할 노드가 기존 트리에 없으면 
			return T;
		}
				
		//삭제하려는 노드의 차수가 0이면
		if(p.getLeft() == null && p.getRight() == null) {
			if(q == null) { //삭제할 노드는 있는데 부모노드가 없음. 즉, 삭제하려는 노드가 루트노드이면
				T = null;
				return T;
			}
			if(q.getLeft() == p) { //삭제할 노드가 부모의 왼쪽 노드이면
				q.setLeft(null);
			}
			else { //삭제할 노드가 부모의 오른쪽 노드이면
				q.setRight(null);
			}
		}
		
		//삭제하려는 노드의 차수가 1이면
		else if(p.getLeft() == null || p.getRight() == null) {
			
			if(p.getLeft() != null) { //삭제할 노드가 왼쪽 자식을 갖고 있으면서
				if(q == null) { //삭제할 노드는 있는데 부모노드가 없음. 즉, 삭제하려는 노드가 루트노드이면
					T.setKey(p.getLeft().getKey());
					T.setLeft(p.getLeft().getLeft());
					T.setRight(p.getLeft().getRight());
					return T;
				}
				else {
					if(q.getLeft() == p) { //삭제할 노드가 부모의 왼쪽 노드이면
						q.setLeft(p.getLeft());
					}
					else { //삭제할 노드가 부모의 오른쪽 노드이면
						q.setRight(p.getLeft());
					}
				}
			}
			else { //삭제할 노드가 오른쪽 자식을 갖고 있으면서
				if(q == null) { //삭제할 노드는 있는데 부모노드가 없음. 즉, 삭제하려는 노드가 루트노드이면
					T.setKey(p.getRight().getKey());
					T.setLeft(p.getRight().getLeft());
					T.setRight(p.getRight().getRight());
					return T;
				}
				else {
					if(q.getLeft() == p) { //삭제할 노드가 부모의 왼쪽 노드이면
						q.setLeft(p.getRight());
					}
					else { //삭제할 노드가 부모의 오른쪽 노드이면
						q.setRight(p.getRight());
					}
				}
			}
		}
		
		/*
		 * 삭제하려는 노드의 차수가 2이면
		 * 1. 자식 트리의 높이 비교 -> 큰 높이의 트리에서 최대 or최솟값 가지 노드로 대체
		 * 2. 자식 트리의 높이 같으면 -> 자식 트리의 노드 개수 비교 -> 큰 높이의 트리에서 최대 or최솟값 가지 노드로 대체
		 */
		else if(p.getLeft() != null && p.getRight() != null){ 
			
			int heightLeft = height(p.getLeft());
			int heightRight = height(p.getRight());
			
			int noNodesLeft = noNodes(p.getLeft());
			int noNodesRight = noNodes(p.getRight());
			
			String flag=" ";
			Node r = new Node(0);
			if(heightLeft > heightRight) { //왼쪽 서브트리 높이 > 오른쪽 서브트리 높이
				r = maxNode(p.getLeft()); //왼쪽 서브트리에서 가장 큰 key가진 노드
				flag = "LEFT";
			}
			else if(heightLeft < heightRight) { //왼쪽 서브트리 높이 < 오른쪽 서브트리 높이
				r = minNode(p.getRight()); //오른쪽 서브트리에서 가장 작은 key가진 노드
				flag = "RIGHT";
			}
			else { //왼쪽 서브트리 높이 == 오른쪽 서브트리 높이
				if(noNodesLeft > noNodesRight) { //왼쪽 서브트리에서 노드의 개수 > 오른쪽 서브트리에서 노드의 개수 
					r = maxNode(p.getLeft()); //왼쪽 서브트리에서 가장 큰 key가진 노드
					flag = "LEFT";//왼쪽 서브트리에서 가장 큰 key가진 노드
				}
				else { //왼쪽 서브트리에서 노드의 개수 <= 오른쪽 서브트리에서 노드의 개수
					r = minNode(p.getRight()); //오른쪽 서브트리에서 가장 작은 key가진 노드
					flag = "RIGHT";
				}
			}
			
			p.setKey(r.getKey()); //삭제할 원소의 자리에 r넣기
			if(flag == "LEFT") { //flag가 left이면 왼쪽 서브트리에서 r지우기 
				if(p.getLeft() == r || p.getRight() == r) {
					Node parentP = p;
					deleteBST(p.getLeft(), r.getKey(), parentP);
				}
				else {
					deleteBST(p.getLeft(), r.getKey());
				}
			}
			else { //flag가 right이면 오른쪽 서브트리에서 r지우기 
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

