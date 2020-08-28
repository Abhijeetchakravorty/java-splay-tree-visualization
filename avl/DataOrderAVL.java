import java.util.*;
import java.io.*;

class DataOrderAVL {
    
    private int calcheight(Node N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }

    private Node insertData(Node node, int value) {
        if (node == null) {
            return(new Node(value));
        }

        if (value < node.value) {
                node.left  = insertData(node.left, value);
        } else {
                node.right = insertData(node.right, value);
        }
            
        node.height = Math.max(calcheight(node.left), calcheight(node.right)) + 1;

        int balance = getTreeBalance(node);        

        // Left Left Rotation 
        if (balance > 1 && value < node.left.value) {
            return rotateRight(node);
        }
            

        // Right Right Rotation
        if (balance < -1 && value > node.right.value) {
            return rotateLeft(node);
        }

        // Left Right Rotation
        if (balance > 1 && value > node.left.value) {
            node.left =  rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left Rotation
        if (balance < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node data = x.right;
        x.right = y;
        y.left = data;
        y.height = Math.max(calcheight(y.left), calcheight(y.right))+1;
        x.height = Math.max(calcheight(x.left), calcheight(x.right))+1;
        return x; // return new root 
    }

    
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node data = y.left;
        // Perform rotation
        y.left = x;
        x.right = data;
        //  Update heights
        x.height = Math.max(calcheight(x.left), calcheight(x.right))+1;
        y.height = Math.max(calcheight(y.left), calcheight(y.right))+1;
        // Return new root
        return y;
    }

    public class Node {
        private Node left, right, parent;
        private int height = 1;
        private int value;

        private Node (int val) {
            this.value = val;
        }
    }

    private int getTreeBalance(Node N) {
        if (N == null) {
            return 0;
        }
        return calcheight(N.left) - calcheight(N.right);
    }

    public void traversePreOrder(Node root) {
        if (root != null) {
            traversePreOrder(root.left);
            System.out.printf("%d ", root.value);
            traversePreOrder(root.right);
        }
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node deleteNodeData(Node root, int value) {
        if (root == null) {
            return root;
        }
            
        if ( value < root.value ) {
            root.left = deleteNodeData(root.left, value);
        } else if( value > root.value ) {
            root.right = deleteNodeData(root.right, value);
        } else {
            if( (root.left == null) || (root.right == null) ) {
                Node temp;
                if (root.left != null) {
                    temp = root.left;
                } else {
                    temp = root.right;
                }
            
                if(temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
                temp = null;
            } else {
                Node temp = minValueNode(root.right);
                root.value = temp.value;
                root.right = deleteNodeData(root.right, temp.value);
            }
        }

        if (root == null) {
            return root;
        }
        
        root.height = Math.max(calcheight(root.left), calcheight(root.right)) + 1;
        int balance = getTreeBalance(root);
        
        if (balance > 1 && getTreeBalance(root.left) >= 0) {
            return rotateRight(root);
        }

        if (balance > 1 && getTreeBalance(root.left) < 0) {
            root.left =  rotateLeft(root.left);
            return rotateRight(root);
        }

        if (balance < -1 && getTreeBalance(root.right) <= 0) {
            return rotateLeft(root);
        }
        
        if (balance < -1 && getTreeBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    public void print(Node root) {

        if(root == null) {
            System.out.println("(XXXXXX)");
            return;
        }

        int height = root.height,
            width = (int)Math.pow(2, height-1);

        
        List<Node> current = new ArrayList<Node>(1),
            next = new ArrayList<Node>(2);
        current.add(root);

        final int maxHalfLength = 4;
        int elements = 1;

        StringBuilder sb = new StringBuilder(maxHalfLength*width);
        for(int i = 0; i < maxHalfLength*width; i++) {
            sb.append(' ');
        }
            
        String textBuffer;

        // Iterating through height levels.
        for(int i = 0; i < height; i++) {

            sb.setLength(maxHalfLength * ((int)Math.pow(2, height-1-i) - 1));

            // Creating spacer space indicator.
            textBuffer = sb.toString();

            // Print tree node elements
            for(Node n : current) {

                System.out.print(textBuffer);

                if(n == null) {
                    System.out.print("        ");
                    next.add(null);
                    next.add(null);
                } else {

                    System.out.printf("(%6d)", n.value);
                    next.add(n.left);
                    next.add(n.right);

                }

                System.out.print(textBuffer);

            }

            System.out.println();
            // Print tree node extensions for next level.
            if(i < height - 1) {

                for(Node n : current) {

                    System.out.print(textBuffer);

                    if(n == null)
                        System.out.print("        ");
                    else
                        System.out.printf("%s      %s",
                                n.left == null ? " " : "/", n.right == null ? " " : "\\");

                    System.out.print(textBuffer);

                }

                System.out.println();

            }

            elements *= 2;
            current = next;
            next = new ArrayList<Node>(elements);
        }
    }

    public static void main(String args[]) {
        DataOrderAVL t = new DataOrderAVL();
        Node root = null;
        while (true) {
            System.out.println("(1) Insert");
            System.out.println("(2) Delete");

            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String s = bufferRead.readLine();

                if (Integer.parseInt(s) == 1) {
                    System.out.print("Value to be inserted: ");
                    root = t.insertData(root, Integer.parseInt(bufferRead.readLine()));
                }
                else if (Integer.parseInt(s) == 2) {
                    System.out.print("Value to be deleted: ");
                    root = t.deleteNodeData(root, Integer.parseInt(bufferRead.readLine()));
                }
                else {
                    System.out.println("Invalid choice, try again!");
                    continue;
                }

                t.print(root);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}