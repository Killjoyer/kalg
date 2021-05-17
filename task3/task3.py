
from collections import deque

with open("input.txt", "r") as f:

    n = int(f.readline())

    # {from : {to1: cost1, to2: cost2}}
    graph = {(i + 1): {} for i in range(n)}

    for fr in range(1, n+1):
        line = [int(i) for i in f.readline().split()]
        j = 0
        while line[j] != 0:
            to = line[j]
            cost = line[j+1]
            j += 2
            graph[fr][to] = cost

    departure = int(f.readline())
    destination = int(f.readline())

print("Graph:", *graph.items(), sep='\n')
print(f"From: {destination}; To: {departure}")

dijkstra = {(i + 1): (float('infinity'), -1) for i in range(n)}
dijkstra[1] = (0, -1)
visited = [False] * (n + 1)

while not visited[destination]:
    current = -1
    m = float('infinity')
    for k, v in dijkstra.items():
        if not visited[k]:
            if v[0] < m:
                current = k
                m = v[0]
    if current == -1:
        break
    if visited[current]:
        continue
    visited[current] = True
    for (to, cost) in graph[current].items():
        if dijkstra[to][0] > dijkstra[current][0] + cost:
            dijkstra[to] = (dijkstra[current][0] + cost, current)
print(*dijkstra.items(), sep='\n')

with open("output.txt", "w") as f:
    if not visited[destination]:
        f.write("N")
    else:
        f.write("Y\n")
        ans = [dijkstra[destination]]
        while ans[-1][1] != -1:
            ans.append(dijkstra[ans[-1][1]])
        f.write(
            " ".join(list(map(lambda x: str(x[1]), ans[::-1]))[1:] + [str(destination)]))
        f.write("\n")
        f.write(" ".join(list(map(lambda x: str(x[0]), ans[::-1]))))
