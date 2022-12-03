import javax.print.attribute.IntegerSyntax;
import java.util.*;

public class Permutations {

	private int[] original;
	private Queue<Integer>[] queues;
	private ArrayList<Integer> currentPermutation;

	public static void main(String[] args) {
		Permutations perm = new Permutations(new int[]{0,1,2});
		for (int i = 0; i < 6; i++) {
			System.out.println(Arrays.toString(perm.getCurrentPermutation()));
		}
	}

	public Permutations(int[] arr) {
		original = arr;
		currentPermutation = new ArrayList<>(arr.length);
		queues = new Queue[arr.length];
		for (int i = 0; i < queues.length; i++) {
			queues[i] = new Queue<Integer>();
		}
		queues[0] = setQueue();
		currentPermutation.add(queues[0].dequeue());
		for (int i = 1; i < queues.length; i++) {
			queues[i] = setQueue(queues[i-1]);
			currentPermutation.add(queues[i].dequeue());
		}
	}

	public Integer[] getCurrentPermutation() {
		int current = original.length-1;
		while (current >= 0 && queues[current].isEmpty()) {
			current--;
		}
		if (current == -1) return null;
		ArrayList<Integer> temp = currentPermutation;
		currentPermutation = new ArrayList<>();
		currentPermutation.add( queues[current].dequeue() );
		current++;
		for (int i = current; i < queues.length; i++) {
			queues[i] = setQueue(queues[i-1]);
			currentPermutation.add( queues[i].dequeue() );
		}
		return currentPermutation.toArray(new Integer[0]);
	}

	private Queue<Integer> setQueue() {
		Queue<Integer> queue = new Queue<>();
		for (int i = original.length-1; i >= 0; i--) {
			queue.enqueue(original[i]);
		}
		return queue;
	}

	private Queue<Integer> setQueue(Queue<Integer> copy) {
		Queue<Integer> queue = new Queue<>();
		int counter = 0;
		while (counter < copy.size()) {
			Integer temp = copy.dequeue();
			copy.enqueue(temp);
			queue.enqueue(temp);
			counter++;
		}
		return queue;
	}

}
