# Introduction
* algorithms-core: 基本的算法和数据结构的实现。
* algorithms-assignment: coursera上算法一、算法二的所有大作业解法，供大家参考，但请不要复制我的代码提交。
* algorithms-application: 算法应用，依赖algorithms-core。

# 并查集
### 目标
* 求解连通性问题

### 实现
* 每个点指向父节点，指向自身的节点为根节点，根节点的值代表的是该connected component的编号
* union时根据connected component的size来做平衡
* 寻找根节点时做路径压缩(very brilliant idea)

### 应用
* percolation
* Kruskal algorithm

# 栈和队列
### 链表
* 栈：单链表，head指针指向栈顶 
* 队列：单链表，head指针指向队尾，tail指针指向队首
* 双端队列（deque）：双向链表

### resizing array
* 插入数据前，如果存储空间已满，倍增
* 移除数据后，如果存储空间利用率等于1/4，倍减
* 栈：数组栈
* 队列：实际申请的数组为capacity + 1, 注意：1.队列大小的计算， 2.resize之后需要更新head，tail索引。
* 随机化队列：resizing array，类似于数组栈，需要移除的元素跟栈顶元素交换，然后移除栈顶元素即可。

# 排序
## 插入排序
* 平均复杂度为~1/4 N^2
* 排序partially ordered的数据集的复杂度为O(N)
* 标准库的实现在数据长度<=7时采用了插入排序

## 希尔排序
* knuth's increment sequence: 3x + 1
* 本质上是多次插入排序算法，只不过间隔为x
* 算法复杂度为O(N^3/2)

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
