
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author maaz
 */
public class DLL {
    
    Node root;
    Node tail;
    void insert(File data){
        
        Node n = new Node(data);
        if(root==null){
            root = n;
            tail = root;
//            root.next = n;
//            root.prev = n;
        }
        
        else {
            Node temp = root;
            while(temp.next != null){
                temp = temp.next;
            }
//            n.next = root;
            n.prev = temp;
            temp.next = n;
            tail = n;
        }
    }
    
    File retrieve(Node root) {
        
        if (root == null)
        {
            System.out.println("Empty");
            return null;
        }
        
        else {
        return root.data;
        }
        
    }
    
    Node search(String name){
        Node temp = root;
        while(temp!=null){
            if(temp.data.getName().equals(name)){
                return temp;
            }
            temp = temp.next;
        }
        return temp;
    }
    
    boolean isEmpty() {
        return (root == null);
    }
    
    void remove() {
        
        tail = tail.prev;
        tail.next = null;
        
    }
    
    void clear() {
    
        root = null;
        
        tail = null;
        
    }
    
    void print(){
        Node temp = root;
        while(temp!=null){
            System.out.println(temp.data.getName());
            temp = temp.next;
        }
    }
}

