package hashtables;


import Exceptions.NoValueFoundException;

import java.sql.SQLOutput;

public class ChainHashTable<K, V> implements IHashTable<K, V>{

    public static final int ARR_SIZE = 10;
   // private HNode<K, V> nodes;
    private HNode<K, V>[] arr;

    public ChainHashTable() {

        arr = new HNode[ARR_SIZE];
    }

    @Override //COMPROBAR
    public void insert(K key, V value){

        int k= key.hashCode();
        int j = Math.abs(k%ARR_SIZE);

        HNode<K,V> nodes = new HNode<>(key,value);

        if(arr[j]==null){
            arr[j] = nodes;
        } else {
            addLast(nodes, j);
        }

    }

     private void addLast(HNode<K, V> node, int j){

        HNode<K, V> temporal = arr[j];
        temporal.setPrevious(node);
        node.setNext(temporal);
        arr[j] = node;

    }

    @Override
    public V search(K key){
        int k= key.hashCode();
        int j = Math.abs(k%ARR_SIZE);

        HNode<K, V> object = arr[j];

        while(object!=null){
            if(object.getKey().equals(key)){
                return object.getValue();
            }else {
                object = object.getNext();
            }
        }

        return null;
    }

    @Override
    public void delete(K key) throws NoValueFoundException{
        int k= key.hashCode();
        int j = Math.abs(k%ARR_SIZE);
        HNode<K, V> object = arr[j];
        boolean flag = false;
        int error = 0;

        if(arr[j]!=null && arr[j].getKey().equals(key)){
            //COMPROBAR
            arr[j].setPrevious(null);
            arr[j] = object.getNext();
            error = 1;
        } else {
            while(object!=null && !flag) {
                if (object.getKey().equals(key)) {
                    object.getPrevious().setNext(object.getNext());
                    error = 1;
                    if (object.getNext() != null) {
                        object.getNext().setPrevious(object.getPrevious());
                    }
                    flag = true;
                } else {
                    object = object.getNext();
                }
            }
        }

        if(error == 0){
            throw new NoValueFoundException();
        }

    }

}
