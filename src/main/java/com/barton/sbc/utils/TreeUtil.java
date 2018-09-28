package com.barton.sbc.utils;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import java.util.*;

public class TreeUtil {
    /**
     * 将无序的结点集合，创建成一棵树。
     * 创建过程中使用了树的广度优先遍历，并且在考察无序集合的元素时，
     * 将其逐个插入到广度优先遍历结果集中，最后得到的结果集即是广度优先
     * 遍历的结果，也是从根元素(结果集中第一个元素)串联好的树形结构。
     *
     * @param root 根元素
     * @param list 无序的、不含根元素的集合
     * @return 包含子类的树形结构的根元素
     */
    public static <T extends InheritedNode> T getTree(T root, List<T> list){
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        LinkedList<T> linkedList = new LinkedList<T>();
        list.forEach(item -> linkedList.add(item));
        return getTree(root,linkedList);
    }



    /**
     * 将无序的结点集合，创建成一棵树。
     * 创建过程中使用了树的广度优先遍历，并且在考察无序集合的元素时，
     * 将其逐个插入到广度优先遍历结果集中，最后得到的结果集即是广度优先
     * 遍历的结果，也是从根元素(结果集中第一个元素)串联好的树形结构。
     *
     * @param root 根元素
     * @param list 无序的、不含根元素的集合
     * @return 包含子类的树形结构的根元素
     */
    public static <T extends InheritedNode> T getTree(T root, LinkedList<T> list) {
        // 模拟树的广度遍历结果的集合  
        LinkedList<T> traversalList = new LinkedList<T>();
        traversalList.push(root);
        // 原始集合不为空，则继续迭代，将其中的元素加入到树的广度遍历结果集合中  
        while (list.size() != 0) {
            // 迭代原始集合中的元素  
            Iterator<T> iterAll = list.iterator();
            while (iterAll.hasNext()) {
                T ndInAll = iterAll.next();
                // 迭代树的广度遍历结果集合中的元素  
                Iterator<T> iterTrav = traversalList.iterator();
                int indInTrav = 0;// 记录迭代当前元素的位置  
                boolean mate = false;// 标识是否找到父子类匹配关系  
                while (iterTrav.hasNext()) {
                    T ndInTrav = iterTrav.next();
                    // 如果存在父子类关系，则在在树的广度遍历结果集合中添加该元素，并父类中加入子元素  
                    if (!mate) {
                        if (ndInAll.isChildFrom(ndInTrav)) {
                            // 如果存在父子类关系，则在父类中加入子元素，并设置标识  
                            ndInTrav.addChildNode(ndInAll);
                            mate = true;
                        }
                    } else {
                        // 在找到iterInAll元素的父类之后，继续迭代，找到它的兄弟结点的位置  
                        if (ndInAll.isBrother(ndInTrav)) {
                            break;
                        }
                    }
                    indInTrav++; // 执行++之后为迭代当前元素的位置  
                }
                if (mate) {
                    // 如果找到iterInAll元素的父类，则在它的兄弟结点之前插入该元素  
                    traversalList.add(indInTrav, ndInAll);
                    // 移除已经匹配的元素  
                    iterAll.remove();
                }
            }
        }
        // 最后将所有元素已经放到了树的广度遍历结果集合中，并且元素之间建立好了子父关系，即只取根就可得到所有元素  
        T root2 = traversalList.getFirst();
        return root2;
    }

    /**
     * 通过树的深度优先遍历获取树的遍历集合
     *
     * @param root 树的根元素
     * @return 深度优先遍历方式的遍历集合
     */
    public static <T extends InheritedNode> List<T> createDepthFirstTraversalList(T root) {
        List<T> depthFirstTraversalList = new ArrayList<T>();
        // 深度优先遍历使用的栈结构  
        Deque<T> stack = new ArrayDeque<T>();
        stack.addFirst(root);
        T node = null;
        while ((node = stack.pollFirst()) != null) {
            List<T> sub = node.getChildNodes();
            if (sub != null && !sub.isEmpty()) {
                for (int i = 0; i < sub.size(); i++) {
                    stack.addFirst(sub.get(i));
                }
            }
            depthFirstTraversalList.add(node);
        }
        return depthFirstTraversalList;
    }

    /**
     * 通过树的广度优先遍历获取树的遍历集合
     *
     * @param root 树的根元素
     * @return 深度优先遍历方式的遍历集合
     */
    public static <T extends InheritedNode> List<T> createBreadthFirstTraversalList(T root) {
        List<T> depthFirstTraversalList = new ArrayList<T>();
        // 广度优先遍历使用的队列结构  
        Deque<T> stack = new ArrayDeque<T>();
        stack.addLast(root);
        T node = null;
        while ((node = stack.pollFirst()) != null) {
            List<T> sub = node.getChildNodes();
            if (sub != null && !sub.isEmpty()) {
                for (int i = 0; i < sub.size(); i++) {
                    stack.addLast(sub.get(i));
                }
            }
            depthFirstTraversalList.add(node);
        }
        return depthFirstTraversalList;
    }

    /**
     * 打印树形结构，打印部分可以根据业务需求进行修改
     *
     * @param root 树的根元素
     */
    public static <T extends InheritedNode> void printTreeByDepthFirstTraversal(T root) {
        List<T> depthFirstTraversalList = createDepthFirstTraversalList(root);
        // 记录每个元素的深度  
        int[] deepList = new int[depthFirstTraversalList.size()];
        System.out.printf("%-5s", root);
        int deep = 1; // 考察的当前元素的深度  
        deepList[0] = 1;
        for (int i = 1; i < depthFirstTraversalList.size(); i++) {
            if (depthFirstTraversalList.get(i).isChildFrom(depthFirstTraversalList.get(i - 1))) {
                // 如果判断成立，则深度加1  
                deep++;
                deepList[i] = deep;
                // 如果上一个元素是当前元素的父亲，则打印  
                System.out.printf("%-5s", depthFirstTraversalList.get(i));
            } else {
                // 如果上一个元素不是当前元素的父亲，则回溯迭代找到当前元素的父亲，换行进行打印  
                System.out.println();
                for (int j = i - 2; j >= 0; j--) {
                    if (depthFirstTraversalList.get(i).isChildFrom(depthFirstTraversalList.get(j))) {
                        deep = deepList[j] + 1;
                        deepList[i] = deep;
                        // 当前元素之前用空进行打印，在此利用了元素的深度  
                        for (int k = 0; k < deep - 1; k++) {
                            System.out.printf("%-5s", "");
                        }
                        System.out.printf("%-5s", depthFirstTraversalList.get(i));
                        break;
                    }
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedList<SimpleNode> list = new LinkedList<SimpleNode>();
        list.add(new SimpleNode("B2", "B"));
        list.add(new SimpleNode("D", "A"));
        list.add(new SimpleNode("C2", "C"));
        list.add(new SimpleNode("C12", "C1"));
        list.add(new SimpleNode("D11", "D1"));
        list.add(new SimpleNode("B1", "B"));
        list.add(new SimpleNode("B11", "B1"));
        list.add(new SimpleNode("B12", "B1"));
        list.add(new SimpleNode("C11", "C1"));
        list.add(new SimpleNode("B22", "B2"));
        list.add(new SimpleNode("C1", "C"));
        list.add(new SimpleNode("B", "A"));
        list.add(new SimpleNode("D1", "D"));
        list.add(new SimpleNode("C", "A"));

        SimpleNode root = new SimpleNode("A", null);
        root = TreeUtil.getTree(root, list);

        TreeUtil.printTreeByDepthFirstTraversal(root);

    }

}
