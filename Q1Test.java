import java.util.ArrayList;
import java.util.List;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     static class Node {
          int value;
          Node left, right;

          Node(int value) {
               this.value = value;
          }
     }

     public static String verify(String traversal1, String traversal2, String traversal3){
          int[][] t = { parse(traversal1), parse(traversal2), parse(traversal3) };
          int[][] perms = { {0, 1, 2}, {0, 2, 1}, {1, 0, 2}, {1, 2, 0}, {2, 0, 1}, {2, 1, 0} };
          String[] names = new String[3];

          // perm = {index of inorder, index of preorder, index of postorder}
          for (int[] perm : perms) {
               int[] in = t[perm[0]], pre = t[perm[1]], post = t[perm[2]];
               if (!isIncreasing(in)) {
                    continue;
               }

               // a BST is uniquely rebuilt by inserting its preorder sequence
               Node root = null;
               for (int v : pre) {
                    root = insert(root, v);
               }

               List<Integer> builtIn = new ArrayList<>();
               List<Integer> builtPost = new ArrayList<>();
               inorder(root, builtIn);
               postorder(root, builtPost);

               if (matches(builtIn, in) && matches(builtPost, post)) {
                    names[perm[0]] = "Inorder";
                    names[perm[1]] = "Preorder";
                    names[perm[2]] = "Postorder";
                    return "Traversal 1 - " + names[0] + ", Traversal 2 - " + names[1] + ", Traversal 3 - " + names[2];
               }
          }
          return "Invalid traversals";
     }

     private static int[] parse(String traversal) {
          String[] parts = traversal.split("-");
          int[] values = new int[parts.length];
          for (int i = 0; i < parts.length; i++) {
               values[i] = Integer.parseInt(parts[i].trim());
          }
          return values;
     }

     private static boolean isIncreasing(int[] values) {
          for (int i = 1; i < values.length; i++) {
               if (values[i] <= values[i - 1]) {
                    return false;
               }
          }
          return true;
     }

     private static Node insert(Node node, int value) {
          if (node == null) {
               return new Node(value);
          }
          if (value < node.value) {
               node.left = insert(node.left, value);
          } else {
               node.right = insert(node.right, value);
          }
          return node;
     }

     private static void inorder(Node node, List<Integer> out) {
          if (node == null) {
               return;
          }
          inorder(node.left, out);
          out.add(node.value);
          inorder(node.right, out);
     }

     private static void postorder(Node node, List<Integer> out) {
          if (node == null) {
               return;
          }
          postorder(node.left, out);
          postorder(node.right, out);
          out.add(node.value);
     }

     private static boolean matches(List<Integer> built, int[] expected) {
          if (built.size() != expected.length) {
               return false;
          }
          for (int i = 0; i < expected.length; i++) {
               if (built.get(i) != expected[i]) {
                    return false;
               }
          }
          return true;
     }
}