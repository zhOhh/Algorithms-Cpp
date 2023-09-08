package main.com.zh.datastructure.priorityqueue;

import main.com.zh.datastructure.queue.Queue;

/**
 * 基于<b>大顶堆</b>实现
 *
 * @param <E> 队列中元素类型 必须实现Priority接口
 */
public class PriorityQueue3<E extends Priotity> implements Queue<E> {

    Priotity[] array;
    int size;

    public PriorityQueue3(int capacity) {
        array = new Priotity[capacity];
    }

    /*
    1.入堆新元素 加到数组末尾 （索引位置child）
    2.不断比较新加元素与它父节点（parent）优先级（上浮）
       - 如果父节点优先级低，则向下移动，并找到下一个parent
       - 自知父结点优先级更高或 child==0 为止
     */
    @Override
    public boolean offer(E offered) {
        if (isFull()) {
            return false;
        }
        int child = size++;
        int parent = (child - 1) / 2;
        while (child > 0 && offered.priority() > array[parent].priority()) {
            array[child] = array[parent];
            child = parent;
            parent = (child - 1) / 2;
        }
        array[child] = offered;
        return true;

    }

    /*
    1.交换堆底和尾部元素，让尾部元素出队
    2.下潜
       - 从堆顶开始，将父元素与两个孩子较大者交换
       - 直到父元素大于两孩子，或没有孩子为止
     */
    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        swap(0, size - 1);
        size--;
        Priotity e = array[size];
        array[size] = null;
        down(0);
        return (E) e;
    }

    private void down(int parent) {
        int left = 2 * parent + 1;
        int right = left + 1;
        int max = parent;
        if (left < size && array[left].priority() > array[max].priority()) {
            max = left;
        }
        if (right < size && array[right].priority() > array[max].priority()) {
            max = right;
        }
        if (max != parent) {
            swap(max, parent);
            down(max);
        }
    }

    private void swap(int i, int j) {
        Priotity t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return (E) array[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }
}
