package heap;

public class HeapNode <V> {

    private int key;
    private V value;


    public HeapNode(int key, V value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int compare(int o){

        if(o == this.getKey()){
            return 0;
        }else if(0> this.getKey()){
            return 1;
        }else{
            return -1;
        }

    }
}
