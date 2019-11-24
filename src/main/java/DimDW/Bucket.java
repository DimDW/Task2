package DimDW;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;

class Bucket<K,V>
{
    private K[] keys;
    private V[] values;
    private int size;

    K[] getKeys() {
        return Arrays.copyOf(keys,size);
    }
    V[] getValues() {
        return Arrays.copyOf(values,size);
    }
    int getSize() {
        return size;
    }

    Bucket(Class<K> keyClass, Class<V> valueClass) {
        this.keys = (K[]) Array.newInstance(keyClass, 1);
        this.values = (V[]) Array.newInstance(valueClass, 1);
        this.size = 0;
    }

    V addElement(K key, V value){
        boolean isAdding = true;
        V result = null;
        while (size > keys.length-1) increaseArraySize();
        for (int i = 0; i < size; i++) {
            if ((key == null && keys[i] == null) || (keys[i] != null && keys[i].equals(key))) {
                isAdding = false;
                result = values[i];
                values[i] = value;
            }
        }
        if (isAdding) {
            keys[size] = key;
            values[size] = value;
            size++;
        }
        return result;
    }

    V removeElement(K key) {
        while (size < keys.length * 2 / 3) decreaseArraySize();
        for (int i = 0; i < size; i++) {
            if ((key == null && keys[i] == null) || (keys[i] != null && keys[i].equals(key))) {
                V result = values[i];
                for (int j = i; j < keys.length - 1; j++) {
                    keys[j] = keys[j + 1];
                    values[j] = values[j + 1];
                }
                size--;
                return result;
            }
        }
        throw new NoSuchElementException();
    }

    V findElementByKey(K key){
        for (int i = 0; i < size; i++) {
            if ((key == null && keys[i] == null) || (keys[i] != null && keys[i].equals(key))) {
                return values[i];
            }
        }
        return null;
    }

    boolean containsValue(V value) {
        for (int i = 0; i < size; i++) {
            if ((value == null && values[i] == null) || (values[i] != null && values[i].equals(value))){
                return true;
            }
        }
        return false;
    }

    boolean containsKey(K key) {
        for (int i = 0; i < size; i++) {
            if ((key == null && keys[i] == null) || (keys[i] != null && keys[i].equals(key))) {
                return true;
            }
        }
        return false;
    }

    private void increaseArraySize() {
        int newSize = size*3/2+1;
        keys = Arrays.copyOf(keys,newSize);
        values = Arrays.copyOf(values,newSize);
    }

    private void decreaseArraySize() {
        int newSize = size*2/3;
        keys = Arrays.copyOf(keys,newSize);
        values = Arrays.copyOf(values,newSize);
    }
}