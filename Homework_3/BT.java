package Homework_3;

import Homework_3.BTree.Node;

public class BT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree BT = new BTree();
		
		Node T = null;
		
		T = BT.insertBT(T, 3, 40); BT.inorderBT(T, 3); System.out.println();
		T = BT.insertBT(T, 3, 11); BT.inorderBT(T, 3); System.out.println();
		T = BT.insertBT(T, 3, 77); BT.inorderBT(T, 3); System.out.println();
		T = BT.insertBT(T, 3, 33); BT.inorderBT(T, 3); System.out.println();
		T = BT.insertBT(T, 3, 20); BT.inorderBT(T, 3); System.out.println();
		T = BT.insertBT(T, 3, 90); BT.inorderBT(T, 3); System.out.println();
		T = BT.insertBT(T, 3, 99); BT.inorderBT(T, 3); System.out.println();

	}

}
