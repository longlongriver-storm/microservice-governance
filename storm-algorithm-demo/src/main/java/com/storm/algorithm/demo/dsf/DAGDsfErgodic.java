/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.algorithm.demo.dsf;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过DSF算法查找DAG中的闭环
 */
public class DAGDsfErgodic {

    /**
     * 限制node最大数
     */
    private static final int MAX_NODE_COUNT = 1000;

    /**
     * node集合
     */
    private final List<String> nodes = new ArrayList<String>();

    /**
     * 有向图的邻接矩阵
     */
    private final int[][] adjacencyMatrix = new int[MAX_NODE_COUNT][MAX_NODE_COUNT];

    /**
     * 添加节点
     *
     * @param nodeName
     * @return
     */
    private int addNode(String nodeName) {
        if (!nodes.contains(nodeName)) {
            if (nodes.size() >= MAX_NODE_COUNT) {
                System.out.println("nodes is over length:" + nodeName);
                return -1;
            }
            nodes.add(nodeName);
            return nodes.size() - 1;
        }
        return nodes.indexOf(nodeName);
    }

    /**
     * 添加线，初始化邻接矩阵
     *
     * @param startNode
     * @param endNode
     */
    public void addLine(String startNode, String endNode) {
        int startIndex = addNode(startNode);
        int endIndex = addNode(endNode);
        if (startIndex >= 0 && endIndex >= 0) {
            adjacencyMatrix[startIndex][endIndex] = 1;
        }
    }

    /**
     * 寻找闭环
     *
     * @return
     */
    public List<String> find() {
        // 从出发节点到当前节点的轨迹
        List<Integer> trace = new ArrayList();
        //返回值
        List<String> reslut = new ArrayList();
        if (adjacencyMatrix.length > 0) {
            findCycle(0, trace, reslut);
        }
        if (reslut.isEmpty()) {
            reslut.add("no cycle!");
        }
        return reslut;
    }

    /**
     * @Title findCycle
     * @Description dfs
     * @date 2018年5月17日
     * @param v
     * @param trace
     * @param reslut
     */
    private void findCycle(int v, List<Integer> trace, List<String> reslut) {
        int j;
        //添加闭环信息
        if ((j = trace.indexOf(v)) != -1) {
            StringBuilder sb = new StringBuilder();
            String startNode = nodes.get(trace.get(j));
            while (j < trace.size()) {
                sb.append(nodes.get(trace.get(j))).append("-");
                j++;
            }
            reslut.add("cycle:" + sb.toString() + startNode);
            return;
        }
        trace.add(v);
        for (int i = 0; i < nodes.size(); i++) {
            if (adjacencyMatrix[v][i] == 1) {
                findCycle(i, trace, reslut);
            }
        }
        trace.remove(trace.size() - 1);
    }
}
