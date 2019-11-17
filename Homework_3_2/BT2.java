package Homework_3_2;

import Homework_3_2.BTree2.Node;

public class BT2 {	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree2 BT2 = new BTree2();
		
		Node T = null;
		int[] array = {40, 11, 77, 33, 20, 90, 99, 70, 88, 80, 66, 10, 22, 30, 44, 55, 50, 60, 100, 28, 18, 9, 5, 17, 6, 3, 1, 4, 2, 7, 8, 73, 12, 13, 14, 16, 15, 25, 24, 28, 45, 49, 42, 43, 41, 47, 48, 46, 63, 68, 61, 62, 64, 69, 67, 65, 54, 59, 58, 51, 53, 57, 52, 56, 83, 81, 82, 84, 75, 89};
		for (int i = 0; i < array.length ; i++) {
			T = BT2.insertBT2(T, 3, array[i]); BT2.inorderBT2(T, 3); System.out.println();
		}
		

	}

}

