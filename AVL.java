class Data { 
	int parent, height; 
	Data left, right; 
	Data(int n) {
		parent = n;
		height = 1; 
	}
} 

class AVL { 
	Data root;
	int calcheight(Data ntree) { 
		if (ntree == null) {
			return 0; 
		}

		return ntree.height; 
        }
        
	int maximum(int m, int n) { 
		return (m > n) ? m : n; 
	} 

	Data rightRotation(Data y) { 
		Data x = y.left; 
		Data n = x.right; 
		x.right = y; 
		y.left = n;
		y.height = maximum(calcheight(y.left), calcheight(y.right)) + 1; 
		x.height = maximum(calcheight(x.left), calcheight(x.right)) + 1;
		return x;
	}

	Data leftRotation(Data x) { 
		Data y = x.right; 
		Data lft = y.left; 
		y.left = x; 
		x.right = lft;
		x.height = maximum(calcheight(x.left), calcheight(x.right)) + 1; 
		y.height = maximum(calcheight(y.left), calcheight(y.right)) + 1; 
		return y; 
	} 

	int fetchBalance(Data N) { 
		if (N == null) {
			return 0;
		}

		return calcheight(N.left) - calcheight(N.right); 
	} 

	Data insertNode(Data node, int parent) {
		if (node == null) {
			return (new Data(parent)); 
                }
                
		if (parent < node.parent) {
			node.left = insertNode(node.left, parent); 
		} else if (parent > node.parent) {
			node.right = insertNode(node.right, parent); 
		} else  {
			return node; 
		}

		node.height = 1 + maximum(calcheight(node.left), calcheight(node.right)); 

		int balance = fetchBalance(node); 

		if (balance > 1 && parent < node.left.parent) {
			return rightRotation(node); 
		}
		
		if (balance < -1 && parent > node.right.parent)  {
			return leftRotation(node); 
		}
		
		if (balance > 1 && parent > node.left.parent) { 
			node.left = leftRotation(node.left); 
			return rightRotation(node); 
		} 

		if (balance < -1 && parent < node.right.parent) { 
			node.right = rightRotation(node.right); 
			return leftRotation(node); 
		} 

		return node; 
	} 

	void traverse(Data node) { 
		if (node != null) { 
			System.out.print(node.parent + " | ");
			traverse(node.left); 
			traverse(node.right); 
		} else {
                        System.out.print("\n");
                }
	} 

	public static void main(String[] args) { 
		AVL avltree = new AVL(); 
		avltree.root = avltree.insertNode(avltree.root, 10); 
		avltree.root = avltree.insertNode(avltree.root, 20); 
		avltree.root = avltree.insertNode(avltree.root, 15); 
		avltree.root = avltree.insertNode(avltree.root, 25); 
		avltree.root = avltree.insertNode(avltree.root, 30);
		avltree.root = avltree.insertNode(avltree.root, 16); 
		avltree.root = avltree.insertNode(avltree.root, 18);
		avltree.root = avltree.insertNode(avltree.root, 19);
		System.out.println("Ordered form of constructed avltree is: ");
		avltree.traverse(avltree.root);
	} 
} 
