import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

class Node {
	String value;
	int count;
	Node left;
	Node right;
	Node listforward;
	Node listback;

	public Node(String value, int count) {
		this.left = null;
		this.right = null;
		this.listback = null;
		this.listforward = null;
		this.value = value;
		this.count = count;
	}
}

public class huffmancode {

	static Node mynode;
	static Node root;
	static String output = "";

	public static void maketree(Node root, Node last) {
		// System.out.println(last.listback.value + last.listback.count);
		mynode = new Node(last.listback.value + last.value, last.listback.count + last.count);
		mynode.left = last.listback;
		mynode.right = last;
		last.listback.listback.listforward = mynode;
		mynode.listback = last.listback.listback;
		last = mynode;
		last.listforward = null;
		Node current = root;
		System.out.println("Linked list value");
		while (current.listforward != null) {
			current = current.listforward;
		}
		// System.out.println(current.value);

		if (root.listforward == last)// beginningg
		{
			mynode = new Node(root.value + last.value, root.count + last.count);
			mynode.left = root;
			mynode.right = last;
			mynode.listforward = null;
			mynode.listback = null;
			System.out.println(mynode.value);

		} else {
			maketree(root, last);
		}

	}

	public static void mycompress(ArrayList<String> tags) throws IOException {

		ArrayList<String> mychars = new ArrayList<String>();
		for (int i = 0; i < tags.size(); i++) {
			if (!(mychars.contains(tags.get(i)))) {
				mychars.add(tags.get(i));
			}
		}

		int[] countOfcharacters = new int[mychars.size()];
		for (int i = 0; i < countOfcharacters.length; i++) {
			countOfcharacters[i] = 0; // initialize to 0
		}

		for (int i = 0; i < mychars.size(); i++) {
			String checkiffoud = mychars.get(i);
			for (int j = 0; j < tags.size(); j++) {
				if (checkiffoud.equals(tags.get(j))) {
					countOfcharacters[i]++;
				}

			}

		}
		System.out.println("HERE IS THE CHAR AND ITS COUNT");
		for (int i = 0; i < countOfcharacters.length; i++) {
			System.out.println(mychars.get(i) + " " + countOfcharacters[i]);
		}

		// sorting count and strings
		for (int i = 0; i < countOfcharacters.length - 1; i++) {
			for (int j = 0; j < countOfcharacters.length - 1; j++) {
				if (countOfcharacters[j] < countOfcharacters[j + 1]) {
					int temp = countOfcharacters[j];
					countOfcharacters[j] = countOfcharacters[j + 1];
					countOfcharacters[j + 1] = temp;

					String tempforchar = mychars.get(j);
					mychars.set(j, mychars.get(j + 1));
					mychars.set(j + 1, tempforchar);

				}

			}
		}

		System.out.println("SORTED");
		for (int k = 0; k < countOfcharacters.length; k++) {
			System.out.println(mychars.get(k) + " " + countOfcharacters[k]);
		}

		Node root = null;
		Node current = null;
		Node last = null;

		for (int i = 0; i < countOfcharacters.length; i++) {
			Node mynode = new Node(mychars.get(i), countOfcharacters[i]);

			if (root == null) {
				root = mynode;
				last = mynode;
			} else {
				current = root;
				while (current.listforward != null) {
					current = current.listforward;
				}
				current.listforward = mynode;
				current.listforward.listback = current;
				last = mynode;
			}

		}

		maketree(root, last);
		FileWriter myff = new FileWriter("codes.txt");
		BufferedWriter mywrite = new BufferedWriter(myff);
		String check;
		String code="";
		Vector<Integer> codes = new Vector<>();
		for (int i = 0; i<tags.size(); i++) {
			current= mynode;
			check = tags.get(i);

			while (true) {
				if (current.left.value.equals(check)) {
					//System.out.println("left -------" + current.left.value + "++++" +check);			
					code+="0";
					System.out.println( check);
					mywrite.write( check+  " ");
					break; 
				}
				else {
					code+="1";
					
				}
				if (current.right != null ) {
					if (current.right.value.equals( mychars.get(countOfcharacters.length-1))) {
						//System.out.println("right -------" + current.right.value + "++++" +check);		
						System.out.println(check);
						mywrite.write(check + " ");
						break;					
					}
					current=current.right;	
					}
					else {
						
					break;
				}			
			}

			mywrite.write(code);
			mywrite.write(" ");
			System.out.println(code);
			code="";
		}
		
		mywrite.close();

	}


}
