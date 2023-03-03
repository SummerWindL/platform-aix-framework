package com.platform.aix.common;

/**
 * @author Advance
 * @date 2022年12月09日 11:36
 * @since V1.0.0
 */
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class ForestTree {
    // 树节点类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 森林
    private List<TreeNode> forest;

    public ForestTree() {
        forest = new ArrayList<>();
    }

    // 向森林中添加树
    public void addTree(TreeNode root) {
        forest.add(root);
    }

    // 判断森林是否为空
    public boolean isEmpty() {
        return forest.isEmpty();
    }

    // 获取森林中树的数量
    public int size() {
        return forest.size();
    }

    // 获取森林中指定位置的树
    public TreeNode getTree(int index) {
        return forest.get(index);
    }

    public static void main(String[] args) {
        // 创建一棵树
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        // 创建森林树对象
        ForestTree forest = new ForestTree();

        // 向森林中添加树
        forest.addTree(root);

        // 获取森林中树的数量
        int size = forest.size();
        System.out.println(size);

        // 获取森林中指定位置的树
        TreeNode tree = forest.getTree(0);
        System.out.println(tree.val);
    }
}