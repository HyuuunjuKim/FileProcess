package Homework_4;

import Homework_4.BTree.Node;


public class BT {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] array ={40, 11, 77, 33, 20, 90, 99, 70, 88, 80, 66, 10, 22, 30, 44, 55, 50, 60, 100, 28, 18, 9, 5, 17, 6, 3, 1, 4, 2, 7, 8, 73, 12, 13, 14, 16, 15, 25, 24, 28, 45, 49, 42, 43, 41, 47, 48, 46, 63, 68, 61, 62, 64, 69, 67, 65, 54, 59, 58, 51, 53, 57, 52, 56, 83, 81, 82, 84, 75, 89};

//		//m이 3인 경우
		BTree BT = new BTree();
		Node T = null;	
		
		for (int i = 0; i < array.length ; i++) {
			T = BT.insertBT(T, 3, array[i]); BT.inorderBT(T, 3); System.out.println();
		}
		int[] del_array= {66, 10, 22, 30, 44, 55, 50, 60, 100, 28, 18, 9, 5, 17, 6, 3, 1, 4, 2, 7, 8, 73, 12, 13, 14, 16, 15, 25, 24, 40, 11, 77, 33, 20, 90, 99, 70, 88, 80, 45, 49, 42, 43, 41, 47, 48, 46, 63, 68, 53, 57, 52, 56, 83, 81, 82, 84, 75, 89, 61, 62, 64, 69, 67, 65, 54, 59, 58, 51};


		for (int i = 0 ; i < del_array.length ; i++) {
			T = BT.deleteBT(T, 3, del_array[i]); BT.inorderBT(T, 3); System.out.println();
		}

		//m이 4인 경우
//		BTree BT2 = new BTree();
//		Node T2 = null;
//		
//		for (int i = 0; i < array.length ; i++) {
//			T2 = BT2.insertBT(T2, 4, array[i]); BT2.inorderBT(T2, 4); System.out.println();
//		}
		
//		int[] del_array= {66, 10, 22, 30, 44, 55, 50, 60, 100, 28, 18, 9, 5, 17, 6, 3, 1, 4, 2, 7, 8, 73, 12, 13, 14, 16, 15, 25, 24, 40, 11, 77, 33, 20, 90, 99, 70, 88, 80, 45, 49, 42, 43, 41, 47, 48, 46, 63, 68, 53, 57, 52, 56, 83, 81, 82, 84, 75, 89, 61, 62, 64, 69, 67, 65, 54, 59, 58, 51};
//
//
//		for (int i = 0 ; i < del_array.length ; i++) {
//			T2 = BT2.deleteBT(T2, 4, del_array[i]); BT2.inorderBT(T2, 4); System.out.println();
//		}

	}

}
