import java.util.*;

public class Permutations {

    private int[] arr;

    Permutations(int[] array) {
        arr = array;
    }

    static List<Integer> permute(int[] arr, int k) {
        for (int i = k; i < arr.length; i++) {
            arr.swap(i, k);
            permute(arr, k + 1);
            arr.swap(k, i);
        }
        if (k == arr.length- 1) {
            System.out.println(Arrays.toString(arr));
        }
        return arr;
    }


    public void swap(int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


    public static void main(String[] args) {
        permute(, 0);

    }

}
}
