package leetcode34;

public class Solution  {

    public int[] searchRange(int[] nums, int target) {
        int l = search(nums, target);
        int r = search(nums, target + 1);
        return l == r ? new int[]{-1, -1} : new int[]{l, r - 1};
    }
    private int search(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] ins = new Solution().searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(ins);
    }
}
