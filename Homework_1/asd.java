package Homework_1;

public class asd {

public class BST {

   public Node getNode(int key) {
      Node node = new Node(key);
      return node;
   }
   
   //BST 검색 알고리즘.
   public boolean searchParentBST(Node root, int searchKey, ParentsChild<Node> _q, ParentsChild<Node> _p) {
      if(root == null) {
         return false;
      }
      Node p = _p.get();
      Node q = _q.get();
      p = root;
      q = null;
      
      do {
         if(searchKey == p.getKey()) {
            _p.set(p);
            _q.set(q);
            return true;
         }
         q = p;
         if(searchKey < p.getKey()) {
            p = p.getLeftNode();
         } else {
            p = p.getRightNode();
         }
      }while(p != null);
      
      _p.set(p);
      _q.set(q);
      return false;
   }
   
   //트리 중위순회(in-order) 알고리즘
   //reference : https://ko.wikipedia.org/wiki/%ED%8A%B8%EB%A6%AC_%EC%88%9C%ED%9A%8C
   public void inorderBST(Node node) {
      if(node == null) { 
         return; 
      } else {
         inorderBST(node.getLeftNode());
         System.out.print(node.getKey()+ " ");
         inorderBST(node.getRightNode());
      }
   }
   
   //트리의 높이를 구하는 알고리즘
   //refernece : https://mattlee.tistory.com/27
   public int height(Node root) {
      if(root == null) {
         return 0;
      } else {         
         return 1+Math.max(height(root.getLeftNode()), height(root.getRightNode()));
      }   
   }
   
   //최대 크기의 노드를 구하는 알고리즘
   public Node maxNode(Node root) {
      Node p = root;
      while(p.getRightNode() != null) {
         p = p.getRightNode();
      }
      return p;
   }
   
   //최소 크기의 노드를 구하는 알고리즘
   public Node minNode(Node root) {
      Node p = root;
      while(p.getLeftNode() != null) {
         p = p.getLeftNode();
      }
      return p;
   }
   
   //노드의 총 개수를 구하는 알고리즘 
   //refernece : http://nospblog.blogspot.com/2013/12/data-structure-counting-treenode.html
   public int noNodes(Node root) {
      Node p = root;
      int cnt = 0;
      if(p != null) {
         cnt = 1 + noNodes(p.getLeftNode()) + noNodes(p.getRightNode());
      }
      return cnt;
   }
   
      
   //BST 삽입 알고리즘
   //객체 인스턴스 생성 및 객체 상태변경 적용을 위해 Node객체를 return하였습니다.
   public Node insertBST(Node root, int newKey) {
      Node p, q;      //p:현재 노드. q:p의 부모노드
      p = root;
      
      if(root == null) {
         root = getNode(newKey);
         //System.out.println("루트에 값 삽입 성공.");
         return root;
      }
      
      do {
         if(newKey == p.getKey()) {
            //System.out.println("이미 해당 값을 가진 노드가 존재함.");
            return root;
         }
         q = p;
         if(newKey < p.getKey()) {
            p = p.getLeftNode();
         } else {
            p = p.getRightNode();
         }
      } while(p != null);
      
      Node newNode = getNode(newKey);
      
      if (newKey <q.getKey()) {
         q.setLeftNode(newNode);
         //System.out.println("왼쪽서브트리에 값 삽입 성공");
      } else {
         q.setRightNode(newNode);
         //System.out.println("오른서브트리에 값 삽입 성공");
      }
      return root;
   }

   
   //BST 삭제 알고리즘
   public Node deleteBST(Node root, int deleteKey) {
      //p와 q의 call by reference를 위해 제네릭형 클래스를 사용하였다.(다중 리턴이 불가하므로)
      ParentsChild<Node> _p = new ParentsChild<Node>();
      ParentsChild<Node> _q = new ParentsChild<Node>();
      searchParentBST(root, deleteKey, _q, _p);
      Node p = _p.get();
      Node q = _q.get();
      
      if(p == null) {
         //System.out.println("삭제할 원소가 존재하지 않음.");
         return root;
      }
   
      if(p.getLeftNode() == null && p.getRightNode() == null) {
         
         if(q == null) { //루트노드만 존재할 경우, q값이 null이며 서브트리가 존재하지 않아야 하므로 pdf상의 의사코드는 오류가 있어 수정하였다.
            root = null;
            //System.out.println("루트노드 삭제.");
            return root;
         }
         
         //삭제할 노드 p의 차수가 0인 경우 (leap node)
         //System.out.println("삭제할 노드의 차수가 0! -> 리프노드");
         if(q.getLeftNode() == p) {
            q.setLeftNode(null);
         } else {
            q.setRightNode(null);
         }
      } else if(p.getLeftNode() == null || p.getRightNode() == null) {
         //삭제할 노드 p의 차수가 1인 경우.
         //System.out.println("삭제할 노드의 차수가 1!");
         if(p.getLeftNode() != null) {
            if(q == null) {
               root.setNode(p.getLeftNode());
               //노드 값에 setNode를 하여 인스턴스 변수만을 변경하였다.
               return root;
            } else {
               if(q.getLeftNode() == p) {
                  q.setLeftNode(p.getLeftNode());
               } else {
                  q.setRightNode(p.getLeftNode());
               }
            }
         } else {
            if(q == null) {
               root.setNode(p.getRightNode());
               return root;
            } else {
               if(q.getLeftNode() == p) {
                  q.setLeftNode(p.getRightNode());
               } else {
                  q.setRightNode(p.getRightNode());
               }
            }
         }
      } else if(p.getLeftNode() != null && p.getRightNode() != null) {
         //삭제할 노드 p의 차수가 2인 경우
         boolean isLeft;
         Node recursiveNode; //재귀로 삭제할 노드를 담을 객체변수.

         //System.out.println("삭제할 노드의 차수가 2!");
         if(height(p.getLeftNode()) > height(p.getRightNode())) {
            recursiveNode = maxNode(p.getLeftNode());
            isLeft = true;
         } else if(height(p.getLeftNode()) < height(p.getRightNode())) {
            recursiveNode = minNode(p.getRightNode());
            isLeft = false;
         } else {
            if(noNodes(p.getLeftNode()) >= noNodes(p.getRightNode())) {
               recursiveNode = maxNode(p.getLeftNode());
               isLeft = true;
            } else {
               recursiveNode = minNode(p.getRightNode());
               isLeft = false;
            }
         } 
         //재귀로 삭제할 노드가 leaf노드인 경우 해당노드를 매개변수로 deleteBST를 호출하면, null값 처리가 되지 않는 오류가 있어,
         //트리 통째로 보내 부모노드에서 해당 자식노드를 가르키는 포인터를 null값으로 업데이트 해주는 방법을 사용하였다.
         // 이를 위해 p에 업데이트 해줄 key값을 임시 변수에 보관했다가 삭제가 끝나면 set해주었다.
         if(recursiveNode.getLeftNode()==null && recursiveNode.getRightNode()==null) {
            int changedValue = recursiveNode.getKey();
            deleteBST(root, changedValue);
            p.setKey(changedValue);
            
            return root;
         }
         
         p.setKey(recursiveNode.getKey());
         if(isLeft == true) {
            deleteBST(p.getLeftNode(), recursiveNode.getKey());
         } else {
            deleteBST(p.getRightNode(), recursiveNode.getKey());
         }
      }
      return root;
   }
}


}
