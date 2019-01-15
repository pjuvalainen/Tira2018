# Tira2018
Data structures 2018 coursework - graph data structure in Java

Tdata.txt contains example data for graph.

Coursework assignment:
1. Create a graph by reading through the file that contains the points on 2D plane. For each point create a node that contains the point. In addition, for each node find another node that contains a point that is closest to the node in question. (Nearest neighbor). This procedure yields two kind of subgraphs. Subgraphs containing two nodes, and sub graphs containing three nodes. (Connected components)
2. For each node of the graph add link to the second closest neighbor
3. Write file BFS.txt, that contains coordinates of the graph nodes in Breadth First order
4. Write file DFS.txt, that contains coordinates of the graph nodes in Depth First order
5. Write file Degrees.txt, that contains the in- and out degrees of the nodes in Depth First order
6. Remove a given node from the graph and write remaining graph to the file DIM.txt in Breadth First order
7. Add more nearest neighbors to the graph nodes, until the graph becomes fully connected. This means that from a random node there exists a path to all other nodes of the graph and back to the starting node. Write your graphs in the file COMP.txt in Breadth First order
8. Create a minimum spanning tree to your graph and write it in the file MSP.txt using Breadth First order
9. In the first task the result was a set of subgraphs of size 2 and 3. What could be the role of 3-node subgraphs in the original graph if we consider the relations of the points?
10. Change a bit the way how you calculate the distances of the points and create a graph that contains the points on the outskirts of the point cloud. See Fig 1. You can determine the definition for outlier by yourself. Write your graph into file OUTLIERS.txt in Breadth First order
