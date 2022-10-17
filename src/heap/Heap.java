package heap;

import hashtables.HNode;

import javax.swing.*;
import java.util.ArrayList;

public class Heap<V> implements IPriorityQueue<V> {
    private final ArrayList<HeapNode<V>> arr;

    public Heap() {

        arr = new ArrayList<>();

    }

    private void maxHeapify(int i) {
        int largest;

        int l = 2 * i;

        int r = 2 * i + 1;

        if (l < arr.size() && arr.get(l).getKey() > arr.get(i).getKey()) {
            largest = l;
        } else {
            largest = i;
        }

        if (r < arr.size() && arr.get(r).getKey() > arr.get(i).getKey()) {
            largest = r;
        }
        HeapNode maxTemp;
        HeapNode minTemp;
        if (largest != i) {
            maxTemp = arr.get(largest);
            minTemp = arr.get(i);

            arr.set(largest, minTemp);
            arr.set(i, maxTemp);
            maxHeapify(largest);
        }


    }

    @Override
    public V heapExtracMax() {
        if (arr.size() < 1) {
            System.out.println("Error, heap underflow");
        }
        V max = arr.get(0).getValue();
        arr.set(0, arr.get(arr.size() - 1));
        arr.remove(arr.size() - 1);
        maxHeapify(0);
        return max;
    }

    @Override
    public void heapIncreaseKey(V a, int key) {
        int index = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getValue() == a) {
                index = i;
            }
        }
        if (key < arr.get(index).getKey()) {
            System.out.println("Error: new key is smaller than current key");
        } else {
            arr.get(index).setKey(key);
        }

        while (index > 0 && arr.get(index / 2).getKey() < arr.get(index).getKey()) {

            HeapNode<V> temp1 = arr.get(index);
            HeapNode<V> temp2 = arr.get(index / 2);
            arr.set(index, temp2);
            arr.set(index / 2, temp1);
            index = index / 2;
        }

    }

    @Override
    public void HeapInsert(V a, int key) {
        int size = arr.size();

        if (size == 0) {
            arr.add(new HeapNode<>(key, a));
        } else {
            arr.add(new HeapNode<>(key, a));
            buildMaxHeap();
        }

    }

    @Override
    public void buildMaxHeap() {
        for (int i = arr.size()/2; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    @Override
    public V heapMaximum() {

        return arr.get(0).getValue();
    }

    public void delete(int goal){
        int inicio = 0;
        int fin = arr.size() - 1;
        while (inicio <= fin) {
            int mid = (inicio + fin) / 2;
            if (goal > arr.get(mid).getKey()) {
                inicio = mid + 1;
            } else if (goal < arr.get(mid).getKey()) {
                fin = mid - 1;
            } else {
                System.out.println("Valor buscado: " + arr.get(mid) + " en posición " + mid);
                return;
            }
        }
    }

    public String print(){
        String msg = "";
        for (int i = 0; i < arr.size(); i++) {
            msg += arr.get(i) + " ";
        }
        return msg;
    }

    public int length(){
        return arr.size();
    }

}
