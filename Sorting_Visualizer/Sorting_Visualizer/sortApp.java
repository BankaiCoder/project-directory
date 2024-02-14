import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class sortApp {

    private static final int SCREEN_WIDTH = 910;
    private static final int SCREEN_HEIGHT = 750;

    private static final int arrSize = 130;
    private static final int rectSize = 7;

    private static int[] arr = new int[arrSize];
    private static int[] Barr = new int[arrSize];

    private static JFrame frame;
    private static SortingPanel sortingPanel;

    private static boolean complete = false;

    private static boolean init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Sorting Visualizer");
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        sortingPanel = new SortingPanel();
        frame.add(sortingPanel);

        frame.setVisible(true);
        return true;
    }

    private static void close() {
        frame.dispose();
    }

    private static void visualize(int x, int y, int z) {
        sortingPanel.setHighlight(x, y, z);
        sortingPanel.repaint();
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void visualize() {
        sortingPanel.repaint();
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void inplaceHeapSort(int[] input, int n) {
        for (int i = 1; i < n; i++) {
            int childIndex = i;
            int parentIndex = (childIndex - 1) / 2;

            while (childIndex > 0) {
                if (input[childIndex] > input[parentIndex]) {
                    int temp = input[parentIndex];
                    input[parentIndex] = input[childIndex];
                    input[childIndex] = temp;
                } else {
                    break;
                }

                visualize(parentIndex, childIndex, -1);

                childIndex = parentIndex;
                parentIndex = (childIndex - 1) / 2;
            }
        }

        for (int heapLast = n - 1; heapLast >= 0; heapLast--) {
            int temp = input[0];
            input[0] = input[heapLast];
            input[heapLast] = temp;

            int parentIndex = 0;
            int leftChildIndex = 2 * parentIndex + 1;
            int rightChildIndex = 2 * parentIndex + 2;

            while (leftChildIndex < heapLast) {
                int maxIndex = parentIndex;

                if (input[leftChildIndex] > input[maxIndex]) {
                    maxIndex = leftChildIndex;
                }
                if (rightChildIndex < heapLast && input[rightChildIndex] > input[maxIndex]) {
                    maxIndex = rightChildIndex;
                }
                if (maxIndex == parentIndex) {
                    break;
                }

                temp = input[parentIndex];
                input[parentIndex] = input[maxIndex];
                input[maxIndex] = temp;

                visualize(maxIndex, parentIndex, heapLast);

                parentIndex = maxIndex;
                leftChildIndex = 2 * parentIndex + 1;
                rightChildIndex = 2 * parentIndex + 2;
            }
        }
    }

    private static int partitionArray(int[] a, int si, int ei) {
        int count_small = 0;

        for (int i = (si + 1); i <= ei; i++) {
            if (a[i] <= a[si]) {
                count_small++;
            }
        }
        int c = si + count_small;
        int temp = a[c];
        a[c] = a[si];
        a[si] = temp;
        visualize(c, si, -1);

        int i = si, j = ei;

        while (i < c && j > c) {
            if (a[i] <= a[c]) {
                i++;
            } else if (a[j] > a[c]) {
                j--;
            } else {
                int temp_1 = a[j];
                a[j] = a[i];
                a[i] = temp_1;

                visualize(i, j, -1);

                i++;
                j--;
            }
        }
        return c;
    }

    private static void quickSort(int[] a, int si, int ei) {
        if (si >= ei) {
            return;
        }

        int c = partitionArray(a, si, ei);
        quickSort(a, si, c - 1);
        quickSort(a, c + 1, ei);
    }

    private static void merge2SortedArrays(int[] a, int si, int ei) {
        int sizeOutput = (ei - si) + 1;
        int[] output = new int[sizeOutput];

        int mid = (si + ei) / 2;
        int i = si, j = mid + 1, k = 0;
        while (i <= mid && j <= ei) {
            if (a[i] <= a[j]) {
                output[k] = a[i];
                visualize(i, j, -1);
                i++;
                k++;
            } else {
                output[k] = a[j];
                visualize(i, j, -1);
                j++;
                k++;
            }
        }
        while (i <= mid) {
            output[k] = a[i];
            visualize(-1, i, -1);
            i++;
            k++;
        }
        while (j <= ei) {
            output[k] = a[j];
            visualize(-1, j, -1);
            j++;
            k++;
        }
        int x = 0;
        for (int l = si; l <= ei; l++) {
            a[l] = output[x];
            visualize(l, -1, -1);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
        }
    }

    private static void mergeSort(int[] a, int si, int ei) {
        if (si >= ei) {
            return;
        }
        int mid = (si + ei) / 2;

        mergeSort(a, si, mid);
        mergeSort(a, mid + 1, ei);

        merge2SortedArrays(a, si, ei);
    }

    private static void bubbleSort() {
        for (int i = 0; i < arrSize - 1; i++) {
            for (int j = 0; j < arrSize - 1 - i; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    visualize(j + 1, j, arrSize - i);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void insertionSort() {
        for (int i = 1; i < arrSize; i++) {
            int j = i - 1;
            int temp = arr[i];
            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;

                visualize(i, j + 1, -1);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            arr[j + 1] = temp;
        }
    }

    private static void selectionSort() {
        int minIndex;
        for (int i = 0; i < arrSize - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < arrSize; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                    visualize(i, minIndex, -1);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    private static void loadArr() {
        System.arraycopy(Barr, 0, arr, 0, arrSize);
    }

    private static void randomizeAndSaveArray() {
        Random rand = new Random();
        for (int i = 0; i < arrSize; i++) {
            int random = rand.nextInt(SCREEN_HEIGHT);
            Barr[i] = random;
        }
    }

    private static void execute() {
        if (!init()) {
            System.out.println("Initialization Failed.");
        } else {
            randomizeAndSaveArray();
            loadArr();

            boolean quit = false;
            while (!quit) {
                if (controls()) {
                    sortingPanel.setComplete(false);
                    randomizeAndSaveArray();
                    loadArr();
                    sortingPanel.setComplete(true);
                    selectionSort(); // Default sorting algorithm, you can change it to any other.
                } else {
                    quit = true;
                    System.out.println("Exiting Sorting Visualizer.");
                }
            }
            close();
        }
    }

    private static boolean controls() {
        System.out.println("WARNING: Giving repetitive commands may cause latency and the visualizer may behave unexpectedly. Please give a new command only after the current command's execution is done.\n\n"
                + "Available Controls inside Sorting Visualizer:\n"
                + "    Use 0 to Generate a different randomized list.\n"
                + "    Use 1 to start Selection Sort Algorithm.\n"
                + "    Use 2 to start Insertion Sort Algorithm.\n"
                + "    Use 3 to start Bubble Sort Algorithm.\n"
                + "    Use 4 to start Merge Sort Algorithm.\n"
                + "    Use 5 to start Quick Sort Algorithm.\n"
                + "    Use 6 to start Heap Sort Algorithm.\n"
                + "    Use q to exit out of Sorting Visualizer\n\n"
                + "PRESS ENTER TO START SORTING VISUALIZER...\n\n"
                + "Or type -1 and press ENTER to quit the program.");

        String s = JOptionPane.showInputDialog(null, "");
        if ("-1".equals(s)) {
            return false;
        }
        return true;
    }

    private static void intro() {
        System.out.println("==============================Sorting Visualizer==============================\n\n"
                + "Visualization of different sorting algorithms in Java with Swing. A sorting algorithm is an algorithm that puts the elements of a list in a certain order. While there are a large number of sorting algorithms, in practical implementations a few algorithms predominate.\n"
                + "In this implementation of sorting visualizer, we'll be looking at some of these sorting algorithms and visually comprehend their working.\n"
                + "The sorting algorithms covered here are Selection Sort, Insertion Sort, Bubble Sort, Merge Sort, Quick Sort, and Heap Sort.\n"
                + "The list size is fixed to 130 elements. You can randomize the list and select any type of sorting algorithm to call on the list from the given options. Here, all sorting algorithms will sort the elements in ascending order. The sorting time being visualized for an algorithm is not exactly the same as their actual time complexities. The relatively faster algorithms like Merge Sort, etc. have been delayed so that they could be properly visualized.\n\n"
                + "Press ENTER to show controls...");

        String s = JOptionPane.showInputDialog(null, "");
        if ("\n".equals(s)) {
            return;
        }
    }

    public static void main(String[] args) {
        intro();

        while (true) {
            System.out.println('\n');
            if (controls()) {
                execute();
            } else {
                System.out.println("Exiting Program.");
                break;
            }
        }
    }

    private static class SortingPanel extends JPanel {

        private int highlightX = -1;
        private int highlightY = -1;
        private int highlightZ = -1;
        private boolean complete = false;

        public void setHighlight(int x, int y, int z) {
            this.highlightX = x;
            this.highlightY = y;
            this.highlightZ = z;
        }

        public void setComplete(boolean complete) {
            this.complete = complete;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int j = 0;
            for (int i = 0; i <= SCREEN_WIDTH - rectSize; i += rectSize) {
                int rectHeight = arr[j];

                if (complete) {
                    g.setColor(new Color(100, 180, 100));
                    g.drawRect(i, 0, rectSize, rectHeight);
                } else if (j == highlightX || j == highlightZ) {
                    g.setColor(new Color(100, 180, 100));
                    g.fillRect(i, 0, rectSize, rectHeight);
                } else if (j == highlightY) {
                    g.setColor(new Color(165, 105, 189));
                    g.fillRect(i, 0, rectSize, rectHeight);
                } else {
                    g.setColor(new Color(170, 183, 184));
                    g.drawRect(i, 0, rectSize, rectHeight);
                }

                j++;
            }
        }
    }
}

