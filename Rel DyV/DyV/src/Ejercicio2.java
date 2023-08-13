public class Ejercicio2 {

    static double findMedianSortedArrays(int[] A, int[] B) {
        int totalLength = A.length + B.length;
        if (totalLength % 2 == 1) {
            return findKthElement(A, B, totalLength / 2 + 1);
        } else {
            int first = findKthElement(A, B, totalLength / 2);
            int second = findKthElement(A, B, totalLength / 2 + 1);
            return (first + second) / 2.0;
        }
    }

    static int findKthElement(int[] A, int[] B, int k) {
        int m = A.length;
        int n = B.length;
        int indexA = 0;
        int indexB = 0;

        while (true) {
            if (indexA == m) {
                return B[indexB + k - 1];
            }
            if (indexB == n) {
                return A[indexA + k - 1];
            }
            if (k == 1) {
                return Math.min(A[indexA], B[indexB]);
            }

            int halfK = k / 2;
            int newIndexA = Math.min(indexA + halfK, m) - 1;
            int newIndexB = Math.min(indexB + halfK, n) - 1;
            int pivotA = A[newIndexA];
            int pivotB = B[newIndexB];

            if (pivotA <= pivotB) {
                k -= (newIndexA - indexA + 1);
                indexA = newIndexA + 1;
            } else {
                k -= (newIndexB - indexB + 1);
                indexB = newIndexB + 1;
            }
        }
    }

    public static void main(String[] args) {
        int[] A = {1, 3, 5};
        int[] B = {2, 4, 6};

        double median = findMedianSortedArrays(A, B);
        System.out.println("Mediana: " + median);
    }
}
