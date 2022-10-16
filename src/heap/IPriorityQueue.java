package heap;

public interface IPriorityQueue<V> {

    public V heapExtracMax();

    public void heapIncreaseKey(V i, int key);

    public void HeapInsert(V a, int key);

    public void buildMaxHeap();

    public V heapMaximum();


}
