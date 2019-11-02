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
	 * inorder
	 */
	
	public static class Node {
		private int key;
		private Node left;
		private Node right;
		
		
		public Node(int key) { //생성자
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
	
	//새로운 node 생성
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
	public static void insertBST(Node T, int newKey) { //T는 현존하는 트리, newKey는 삽입하려는 새로운 value
		Node p = T;
		Node q = null; //부모 node가 될 예정. 초기화는 null로
		while(p != null) { //삽입하려는 키 값을 가진 노드가 이미 있는지 검사
			if(newKey == p.getKey()) {
				System.out.println("이미 존재하는 값입니다.");
				return;
			}
			q = p; //q는 p의 부모 노드로 지시
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
			
		//leftHeight와 rightHeight 중 큰거에 rootnode까지해서 1더하기
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
		Node node = T; //기존 트리
		Node p = null; //삭제할 원소를 담은 노드
		Node q = null; //삭제할 원소를 다믄 노드의 부모 노드
		Node r = null;
		
		while(node != null) { 
			if(node.getKey() == deleteKey) { //기존 노드에서 삭제 노드찾으면
				p = node; //p에 찾은 노드 담기 
				break;
			}
			q = node; //q를 부모 노드로 설정
			if(node.getKey() < deleteKey) { //삭제하려는 원소가 더 크면
				node = node.getRight(); //오른쪽 서브트리로 이동
			}
			else { //삭제 하려는 원소가 더 작으면
				node = node.getLeft(); //왼쪽 서브트리로 이동
			}
		}
		
		if(p == null) { //삭제할 노드가 기존 트리에 없으면 
			return;
		}
		if(q == null) { //삭제할 노드는 있는데 부모노드가 없음. 즉, 삭제하려는 노드가 루트노드이면
			T = null;
			return;
		}
		
		//삭제하려는 노드의 차수가 0이면
		if(p.getLeft() == null && p.getRight() == null) {
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
				if(q.getLeft() == p) { //삭제할 노드가 부모의 왼쪽 노드이면
					q.setLeft(p.getLeft());
				}
				else { //삭제할 노드가 부모의 오른쪽 노드이면
					q.setRight(p.getLeft());
				}
			}
			else { //삭제할 노드가 오른쪽 자식을 갖고 있으면서
				if(q.getLeft() == p) { //삭제할 노드가 부모의 왼쪽 노드이면
					q.setLeft(p.getRight());
				}
				else { //삭제할 노드가 부모의 오른쪽 노드이면
					q.setRight(p.getRight());
				}
			}
		}
		
		/*
		 * 삭제하려는 노드의 차수가 2이면
		 * 1. 자식 트리의 높이 비교 -> 큰 높이의 트리에서 최대 or최솟값 가지 노드로 대체
		 * 2. 자식 트리의 높이 같으면 -> 자식 트리의 노드 개수 비교 -> 큰 높이의 트리에서 최대 or최솟값 가지 노드로 대체
		 */
		else { 
			int heightLeft = height(p.getLeft());
			int heightRight = height(p.getRight());
			
			int noNodesLeft = noNodes(p.getLeft());
			int noNodesRight = noNodes(p.getRight());
			
			String flag;
			
			if(heightLeft > heightRight) { //왼쪽 서브트리 높이 > 오른쪽 서브트리 높이
				Node maxNode = maxNode(p.getLeft());
				r = maxNode; //왼쪽 서브트리에서 가장 큰 key가진 노드
				flag = "LEFT";
				
			}
			else if(heightLeft < heightRight) { //왼쪽 서브트리 높이 < 오른쪽 서브트리 높이
				Node minNode = minNode(p.getRight());
				r = minNode; //오른쪽 서브트리에서 가장 작은 key가진 노드
				flag = "RIGHT";
			}
			else { //왼쪽 서브트리 높이 == 오른쪽 서브트리 높이
				if(noNodesLeft > noNodesRight) { //왼쪽 서브트리에서 노드의 개수 > 오른쪽 서브트리에서 노드의 개수 
					Node maxNode = maxNode(p.getLeft());
					r = maxNode; //왼쪽 서브트리에서 가장 큰 key가진 노드
					flag = "LEFT";//왼쪽 서브트리에서 가장 큰 key가진 노드
				}
				else { //왼쪽 서브트리에서 노드의 개수 <= 오른쪽 서브트리에서 노드의 개수
					Node minNode = minNode(p.getRight());
					r = minNode; //오른쪽 서브트리에서 가장 작은 key가진 노드
					flag = "RIGHT";
				}
			}
			
			p.setKey(r.getKey()); //삭제할 원소의 자리에 r넣기
			if(flag == "LEFT") { //flag가 left이면 왼쪽 서브트리에서 r지우기 
				deleteBST(p.getLeft(), r.getKey());
			}
			else { //flag가 right이면 오른쪽 서브트리에서 r지우기 
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
