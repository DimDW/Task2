package DimDW;


import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyHashMap<K,V> {
    private Bucket[] buckets;

    public MyHashMap() {
        this.buckets = (Bucket[]) Array.newInstance(Bucket.class,16);
    }
    public MyHashMap(int buckets) {
        this.buckets = (Bucket[]) Array.newInstance(Bucket.class,buckets);
    }

    private int getHash(K key) {
        return Objects.hash(key)%buckets.length;
    }

    public int size() {
        int size = 0;
        for (Bucket bucket : buckets) {
            if (bucket == null) continue;
            size += bucket.getSize();
        }
        return size;
    }

    public V put(Object key, Object value){
        int hash = getHash((K) key);
        if (buckets[hash] == null){
            if (key == null && value == null) buckets[hash] = new Bucket(Object.class,Object.class);
            if (key == null && value != null) buckets[hash] = new Bucket(Object.class,value.getClass());
            if (key != null && value == null) buckets[hash] = new Bucket(key.getClass(),Object.class);
            if (key != null && value != null) buckets[hash] = new Bucket(key.getClass(),value.getClass());
        }
        return (V) buckets[hash].addElement(key,value);
    }

    public V remove(K key){
        int hash = getHash(key);
        if (buckets[hash] == null) throw new NoSuchElementException();
        return (V) buckets[hash].removeElement(key);
    }

    public V get(K key){
        int hash = getHash(key);
        if (buckets[hash] == null) throw new NoSuchElementException();
        return (V) buckets[hash].findElementByKey(key);
    }

    public boolean containsKey(K key){
        int hash = getHash(key);
        if (buckets[hash] == null) return false;
        return buckets[hash].containsKey(key);
    }

    public boolean containsValue(V value){
        for (Bucket bucket : buckets) {
            if (bucket != null && bucket.containsValue(value)) return true;
        }
        return false;
    }
}
