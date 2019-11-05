package Homework_1;

public class asd {

public class BST {

   public Node getNode(int key) {
      Node node = new Node(key);
      return node;
   }
   
   //BST �˻� �˰���.
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
   
   //Ʈ�� ������ȸ(in-order) �˰���
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
   
   //Ʈ���� ���̸� ���ϴ� �˰���
   //refernece : https://mattlee.tistory.com/27
   public int height(Node root) {
      if(root == null) {
         return 0;
      } else {         
         return 1+Math.max(height(root.getLeftNode()), height(root.getRightNode()));
      }   
   }
   
   //�ִ� ũ���� ��带 ���ϴ� �˰���
   public Node maxNode(Node root) {
      Node p = root;
      while(p.getRightNode() != null) {
         p = p.getRightNode();
      }
      return p;
   }
   
   //�ּ� ũ���� ��带 ���ϴ� �˰���
   public Node minNode(Node root) {
      Node p = root;
      while(p.getLeftNode() != null) {
         p = p.getLeftNode();
      }
      return p;
   }
   
   //����� �� ������ ���ϴ� �˰��� 
   //refernece : http://nospblog.blogspot.com/2013/12/data-structure-counting-treenode.html
   public int noNodes(Node root) {
      Node p = root;
      int cnt = 0;
      if(p != null) {
         cnt = 1 + noNodes(p.getLeftNode()) + noNodes(p.getRightNode());
      }
      return cnt;
   }
   
      
   //BST ���� �˰���
   //��ü �ν��Ͻ� ���� �� ��ü ���º��� ������ ���� Node��ü�� return�Ͽ����ϴ�.
   public Node insertBST(Node root, int newKey) {
      Node p, q;      //p:���� ���. q:p�� �θ���
      p = root;
      
      if(root == null) {
         root = getNode(newKey);
         //System.out.println("��Ʈ�� �� ���� ����.");
         return root;
      }
      
      do {
         if(newKey == p.getKey()) {
            //System.out.println("�̹� �ش� ���� ���� ��尡 ������.");
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
         //System.out.println("���ʼ���Ʈ���� �� ���� ����");
      } else {
         q.setRightNode(newNode);
         //System.out.println("��������Ʈ���� �� ���� ����");
      }
      return root;
   }

   
   //BST ���� �˰���
   public Node deleteBST(Node root, int deleteKey) {
      //p�� q�� call by reference�� ���� ���׸��� Ŭ������ ����Ͽ���.(���� ������ �Ұ��ϹǷ�)
      ParentsChild<Node> _p = new ParentsChild<Node>();
      ParentsChild<Node> _q = new ParentsChild<Node>();
      searchParentBST(root, deleteKey, _q, _p);
      Node p = _p.get();
      Node q = _q.get();
      
      if(p == null) {
         //System.out.println("������ ���Ұ� �������� ����.");
         return root;
      }
   
      if(p.getLeftNode() == null && p.getRightNode() == null) {
         
         if(q == null) { //��Ʈ��常 ������ ���, q���� null�̸� ����Ʈ���� �������� �ʾƾ� �ϹǷ� pdf���� �ǻ��ڵ�� ������ �־� �����Ͽ���.
            root = null;
            //System.out.println("��Ʈ��� ����.");
            return root;
         }
         
         //������ ��� p�� ������ 0�� ��� (leap node)
         //System.out.println("������ ����� ������ 0! -> �������");
         if(q.getLeftNode() == p) {
            q.setLeftNode(null);
         } else {
            q.setRightNode(null);
         }
      } else if(p.getLeftNode() == null || p.getRightNode() == null) {
         //������ ��� p�� ������ 1�� ���.
         //System.out.println("������ ����� ������ 1!");
         if(p.getLeftNode() != null) {
            if(q == null) {
               root.setNode(p.getLeftNode());
               //��� ���� setNode�� �Ͽ� �ν��Ͻ� �������� �����Ͽ���.
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
         //������ ��� p�� ������ 2�� ���
         boolean isLeft;
         Node recursiveNode; //��ͷ� ������ ��带 ���� ��ü����.

         //System.out.println("������ ����� ������ 2!");
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
         //��ͷ� ������ ��尡 leaf����� ��� �ش��带 �Ű������� deleteBST�� ȣ���ϸ�, null�� ó���� ���� �ʴ� ������ �־�,
         //Ʈ�� ��°�� ���� �θ��忡�� �ش� �ڽĳ�带 ����Ű�� �����͸� null������ ������Ʈ ���ִ� ����� ����Ͽ���.
         // �̸� ���� p�� ������Ʈ ���� key���� �ӽ� ������ �����ߴٰ� ������ ������ set���־���.
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
