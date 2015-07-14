# Introduction
* algs: 主项目
* algs-inf: 子项目,stanford大学robot-sedgewick教授的algs4课程中用到的算法和标准库，我将其引入为maven module并添加package管理，方便学习使用。
* algs-learn: 子项目, 依赖algs-inf, 个人学习算法时的代码.

# 图算法

## 无向图
* DFS: 深度优先搜索
* BFS: 宽度优先搜索, 解决最短路径问题
* CycleDetect: 检测无向图中的环
* Bipartite: 将顶点分为两个集合A,B。如果对于图中的每一条边,该边的其中一点在集合A，另外一点一定在集合B，则该图就是Bipartite图。
* ConnectedComponent:求解极大连通子图

## 有向图
* Mark-Sweep垃圾收集算法: 运行dfs标记可用对象，其它对象就是垃圾对象了.
* 多源最短路径问题: 运行dfs, 初始化时将多个源添加到queue即可.
* 求解强连通子图:
    1. 对逆有向图(reverse digraph G')运行dfs得到reverse postOrder
    2. 按照由1得到的顺序在有向图(digraph G)上运行dfs就得到顶点所在的强连通子图

## 有向无环图
* 拓扑排序
* 有向无环图的检测: v->w 当调用dfs(v)时, w被标记且尚未返回(即还在调用栈中), 这样就可以得出w->v, 所以存在环.

## 带权重的无向图
* 最小生成树
    1. Kruskal algorithm
    2. prim algorithm - lazy version
    3. prim algorithm - eager version

## 带权重的有向图
* 单源最短路径问题
    1. Dijkstra algorithm(positive weights),本质上dijkstra、prim、dfs、bfs都是基于求解生成树的算法，差异在于下一个点的选取上
    2. Topological-based spt algorithm(acyclic), line performance, application: seam-carving, parallel job schedule
    3. Bellman-Ford algorithm(no negative cycle) -> 求解最长路径问题


# 查找

## 优先级队列
* 基于索引的小顶堆: Associate an index between 0 and N - 1 with each key in a priority queue.
    1. Client can insert and delete-the-minimum.
    2. Client can change the key by specifying the index.
    3. application for eager version prim algorithm and dijkstra shortest path algorithm.
