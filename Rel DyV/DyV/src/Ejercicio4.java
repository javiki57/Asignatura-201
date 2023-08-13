import java.util.Arrays;

public class Ejercicio4 {

    static int[] findMaxSubarray(int[] nums) {
        return findMaxSubarray(nums, 0, nums.length - 1);
    }

    static int[] findMaxSubarray(int[] nums, int left, int right) {
        if (left == right) {
            return new int[]{left, right, nums[left]};
        }

        int mid = left + (right - left) / 2;

        int[] leftMax = findMaxSubarray(nums, left, mid);
        int[] rightMax = findMaxSubarray(nums, mid + 1, right);
        int[] crossMax = findMaxCrossingSubarray(nums, left, mid, right);

        if (leftMax[2] >= rightMax[2] && leftMax[2] >= crossMax[2]) {
            return leftMax;
        } else if (rightMax[2] >= leftMax[2] && rightMax[2] >= crossMax[2]) {
            return rightMax;
        } else {
            return crossMax;
        }
    }

    static int[] findMaxCrossingSubarray(int[] nums, int left, int mid, int right) {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        int maxLeft = mid;

        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }

        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        int maxRight = mid + 1;

        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = i;
            }
        }

        return new int[]{maxLeft, maxRight, leftSum + rightSum};
    }

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4}; // Ejemplo de vector
        int[] maxSubarray = findMaxSubarray(nums);

        System.out.println("Subvector máximo: " + Arrays.toString(maxSubarray));
        System.out.println("Suma máxima: " + maxSubarray[2]);
    }
}
