
/** Source code example for "A Practical Introduction to Data
    Structures and Algorithm Analysis, 3rd Edition (Java)" 
    by Clifford A. Shaffer
    Copyright 2008-2011 by Clifford A. Shaffer
*/

import java.lang.Comparable;
import bridges.connect.Bridges;

/** Binary Search Tree implementation for Dictionary ADT */
class BST<Key extends Comparable<? super Key>, E>
         implements Dictionary<Key, E> {
  private AVLTreeElement<Key, E> root; // Root of the BST
  int nodecount;             // Number of nodes in the BST

  private Bridges<Key, E> bridges_obj;
  private Boolean rot_flag;

  /** Constructor */
  BST() { root = null; nodecount = 0;}

  public AVLTreeElement<Key, E> getTreeRoot() {
   return root;
  }
  public void setBridgesObject(Bridges<Key, E> b) {
  bridges_obj = b;
  }

  public Bridges<Key, E> getBridgesObject() {
   return bridges_obj;
  }
  /** Reinitialize tree */
  public void clear() { root = null; nodecount = 0; }

  /** Insert a record into the tree.
      @param k Key value of the record.
      @param e The record to insert. */
  public void insert(Key k, E e) {
    root = inserthelp(root, k, e);
System.out.println("Rotation Flag(Key:" + k + "):" + rot_flag);
    nodecount++;

// bridges_obj.setDataStructure(root);
// bridges_obj.visualize();
  }

  /** Remove a record from the tree.
      @param k Key value of record to remove.
      @return The record removed, or null if there is none. */
  public E remove(Key k) {
    E temp = findhelp(root, k);   // First find it
    if (temp != null) {
      root = removehelp(root, k); // Now remove it
      nodecount--;
    }
    return temp;
  }
private E findhelp(AVLTreeElement<Key,E> rt, Key k) {
  if (rt == null) return null;
  if (rt.getKey().compareTo(k) > 0)
    return findhelp(rt.getLeft(), k);
  else if (rt.getKey().compareTo(k) == 0) return rt.getValue();
  else return findhelp(rt.getRight(), k);
}

// This method balances the tree
private AVLTreeElement<Key,E> inserthelp(AVLTreeElement<Key,E> rt, Key k, E e) {
  if (rt == null) {
      AVLTreeElement<Key, E> new_node = new AVLTreeElement<Key, E>(k, e);
      new_node.setLabel(String.valueOf(0) + "," + String.valueOf(0));     
      return new_node;
   }
   else if (rt.getKey().compareTo(k) > 0)
   {
      rt.setLeft(inserthelp(rt.getLeft(), k, e));

   }
   else 
   {
      rt.setRight(inserthelp(rt.getRight(), k, e)); 
   }  
   int balance = height(rt.getLeft())-height(rt.getRight());
   rt.setValue(e);
   
   if(balance > 1)
   {
      if(height(rt.getLeft().getLeft()) >= height(rt.getLeft().getRight())){
         return rightRotation(rt);        
   }
      
      else{
         //rotates left then right
         rt.setLeft(leftRotation(rt.getLeft()));
         return rightRotation(rt);
      }
   }
   else if (balance < -1){
      if(height(rt.getRight().getRight()) >= height(rt.getRight().getLeft())){
        return leftRotation(rt);
      }
      else {
         // rotates right then left
         rt.setRight(rightRotation(rt.getRight()));
         return leftRotation(rt);
      }
   }
  
  else; 
  rt.setHeight(height(rt));
  return rt;
}
/** Remove a node with key value k
    @return The tree with the node removed */
private AVLTreeElement<Key,E> removehelp(AVLTreeElement<Key,E> rt, Key k) {
  if (rt == null) return null;
  if (rt.getKey().compareTo(k) > 0)
    rt.setLeft(removehelp(rt.getLeft(), k));
  else if (rt.getKey().compareTo(k) < 0)
    rt.setRight(removehelp(rt.getRight(), k));
  else { // Found it
    if (rt.getLeft() == null)
      return rt.getRight();
    else if (rt.getRight() == null)
      return rt.getLeft();
    else { // Two children
      AVLTreeElement<Key,E> temp = getmin(rt.getRight());
      rt.setValue(temp.getValue());
      rt.setLabel(String.valueOf(temp.getKey()));
      rt.setKey(temp.getKey());
      rt.setRight(deletemin(rt.getRight()));
    }
  }
  return rt;
}

  //this method rotates the nodes left
  private AVLTreeElement<Key, E> leftRotation (AVLTreeElement<Key, E> n){
      AVLTreeElement<Key, E> r = n.getRight();
      n.setRight(r.getLeft());
      r.setLeft(n);
      n.setHeight (1 + Math.max(height(n.getLeft()), height(n.getRight())));
      r.setHeight(1 + Math.max(height(n.getLeft()), height(n.getLeft())));
      return r;
  }
  
  //This method rotates the nodes right
  private AVLTreeElement<Key, E> rightRotation (AVLTreeElement<Key, E> n){
      AVLTreeElement<Key, E> r = n.getLeft();
      n.setLeft(r.getRight());
      r.setRight(n);
      n.setHeight (1 + Math.max(height(n.getLeft()), height(n.getRight())));
      r.setHeight(1 + Math.max(height(n.getLeft()), height(n.getLeft())));
      return r;
  }
  
 // this method finds the height of te node  
  public int height(AVLTreeElement<Key,E> rt){
    if(rt == null){
       return -1;
    }   
    else{
      return (1 + Math.max(height(rt.getLeft()), height(rt.getRight())));
    }
  }     

private AVLTreeElement<Key,E> getmin(AVLTreeElement<Key,E> rt) {
  if (rt.getLeft() == null)
    return rt;
 else return getmin(rt.getLeft());
}
private AVLTreeElement<Key,E> deletemin(AVLTreeElement<Key,E> rt) {
  if (rt.getLeft() == null)
    return rt.getRight();
  else {
    rt.setLeft(deletemin(rt.getLeft()));
    return rt;
  }
}
  private void printhelp(AVLTreeElement<Key,E> rt) {
    if (rt == null) return;
   printVisit(rt.getValue(), rt.getKey());
    printhelp(rt.getLeft());
    printhelp(rt.getRight());
  }

  private StringBuffer out;

  public String toString() {
    out = new StringBuffer(100);
    printhelp(root);
    return out.toString();
  }
  private void printVisit(E it, Key k) {
    out.append("[" + k + "," + it + "]");
  }


  /** Remove and return the root node from the dictionary.
      @return The record removed, null if tree is empty. */
  public E removeAny() {
    if (root != null) {
      E temp = root.getValue();
      root = removehelp(root, root.getKey());
      nodecount--;
      return temp;
   }
    else return null;
  }

  /** @return Record with key value k, null if none exist.
      @param k The key value to find. */
  public E find(Key k) { return findhelp(root, k); }

  /** @return The number of records in the dictionary. */
  public int size() { return nodecount; }
  
  //This method finds the height of the BST using recursion
//This method finds the height of the BST using recursion

}