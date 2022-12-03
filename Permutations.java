import java.util.*;

public class Permutations {

    private int[] arr;

    Permutations(int[] array) {
        arr = array;
    }

    public int[] permute(int k) {
        for (int i = k; i < arr.length; i++) {
            swap(i, k);
            permute(k + 1);
            //swap(k, i);
        }
        if (k == arr.length- 1) {
            //System.out.println(Arrays.toString(arr));
        }
        return arr;
    }


    public void swap(int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


    public static void main(String[] args) {
        int[] r = new int[4];
        for(int i = 0; i < r.length; ++i){
            r[i] = i;
        }
        Permutations a = new Permutations(r);
        a.permute(0);
        System.out.println(Arrays.toString(a.permute(4)));
    }

}
