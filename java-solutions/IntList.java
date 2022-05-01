public class IntList {
    private int capacity = 1;
    private int curSize = 0;
    private int[] data = new int[capacity];

    public void add(int value) {
        if (curSize == capacity) {
            capacity *= 2;
            int[] dataExtended = new int[capacity];
            System.arraycopy(data, 0, dataExtended, 0, capacity / 2);
            data = dataExtended;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < curSize - 1; i++) {
            sb.append(data[i]);
            sb.append(" ");
        }
        if (curSize > 0) {
            sb.append(data[curSize - 1]);
        }
        return sb.toString();
    }
}
