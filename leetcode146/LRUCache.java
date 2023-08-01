package leetcode146;

import java.util.HashMap;
import java.util.Map;

/**
* 使用java的LinkedHashMap实现
* */
/*class LRUCache extends LinkedHashMap<Integer, Integer> {

    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    // public void put(int key, int value) {
    //
    // }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}*/

/*
* 哈希表 + 双向链表
算法

LRU 缓存机制可以通过哈希表辅以双向链表实现，我们用一个哈希表和一个双向链表维护所有在缓存中的键值对。

双向链表按照被使用的顺序存储了这些键值对，靠近头部的键值对是最近使用的，而靠近尾部的键值对是最久未使用的。

哈希表即为普通的哈希映射（HashMap），通过缓存数据的键映射到其在双向链表中的位置。

这样以来，我们首先使用哈希表进行定位，找出缓存项在双向链表中的位置，随后将其移动到双向链表的头部，即可在 O(1)O(1)O(1) 的时间内完成 get 或者 put 操作。具体的方法如下：

对于 get 操作，首先判断 key 是否存在：

如果 key 不存在，则返回 −1-1−1；

如果 key 存在，则 key 对应的节点是最近被使用的节点。通过哈希表定位到该节点在双向链表中的位置，并将其移动到双向链表的头部，最后返回该节点的值。

对于 put 操作，首先判断 key 是否存在：

如果 key 不存在，使用 key 和 value 创建一个新的节点，在双向链表的头部添加该节点，并将 key 和该节点添加进哈希表中。然后判断双向链表的节点数是否超出容量，如果超出容量，则删除双向链表的尾部节点，并删除哈希表中对应的项；

如果 key 存在，则与 get 操作类似，先通过哈希表定位，再将对应的节点的值更新为 value，并将该节点移到双向链表的头部。

上述各项操作中，访问哈希表的时间复杂度为 O(1)O(1)O(1)，在双向链表的头部添加节点、在双向链表的尾部删除节点的复杂度也为 O(1)O(1)O(1)。而将一个节点移到双向链表的头部，可以分成「删除该节点」和「在双向链表的头部添加节点」两步操作，都可以在 O(1)O(1)O(1) 时间内完成。

* */

class LRUCache {
   class DLinedNode {
       int key;
       int value;
       DLinedNode prev;
       DLinedNode next;
       public DLinedNode() {}
       public DLinedNode(int key, int value) {
           this.key = key;
           this.value = value;
       }
   }

   private Map<Integer, DLinedNode> cache = new HashMap<>();
   private int size;
   private int capacity;
   private DLinedNode head, tail;

   public LRUCache(int capacity) {
       this.size = 0;
       this.capacity = capacity;
       head = new DLinedNode();
       tail = new DLinedNode();
       head.next = tail;
       tail.prev = head;
   }

   public int get(int key) {
       DLinedNode node = cache.get(key);
       if (node == null) {
           return -1;
       }
       moveToHead(node);
       return node.value;
   }

   public void put(int key, int value) {
       DLinedNode node = cache.get(key);
       if (node == null) {
           DLinedNode newNode = new DLinedNode(key, value);
           cache.put(key, newNode);
           addToHead(newNode);
           ++size;
           if (size > capacity) {
               DLinedNode tail = removeTail();
               cache.remove(tail.key);
               --size;
           }
       } else {
           node.value = value;
           moveToHead(node);
       }
   }

   private void moveToHead(DLinedNode node) {
       removeNode(node);
       addToHead(node);
   }

   private void removeNode(DLinedNode node) {
       node.prev.next = node.next;
       node.next.prev = node.prev;
   }

   private void addToHead(DLinedNode node) {
       node.next = head.next;
       node.prev = head;
       head.next.prev = node;
       head.next = node;
   }

   private DLinedNode removeTail() {
       DLinedNode res = tail.prev;
       removeNode(res);
       return res;
   }

   public static void main(String[] args) {
       LRUCache cache = new LRUCache(3);
       cache.put(1, 1);
       cache.put(2, 2);
       cache.put(3, 3);
       cache.put(4, 4);
       cache.get(4);
       cache.get(3);
   }
}
