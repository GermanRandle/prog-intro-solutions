public class MyList {
    private int capacity = 2;
    private int curSize = 0;
    private int[] data = new int[capacity];

    public void add(int value) {
        if (curSize == capacity) {
            int[] dataExtended = new int[capacity * 3 / 2];
            System.arraycopy(data, 0, dataExtended, 0, capacity);
            data = dataExtended;
            capacity = capacity * 3 / 2;
        }
        data[curSize] = value;
        curSize++;
    }

    public int get(int pos) {
        return data[pos];
    }

    public int size() {
        return curSize;
    }
}
