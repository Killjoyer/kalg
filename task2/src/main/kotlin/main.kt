import java.io.File

typealias Matrix<T> = List<List<T>>

fun main() {
    val input = File("in.txt").readLines().iterator()
    val output = File("out.txt").printWriter()
    val n = input.next().toInt()
    val matrix: Matrix<Int> = (0 until n).map { input.next().split(' ').map { it.toInt() } }
    val used = (0 until n).map { false }.toMutableList()
    var componentsAmount = 0
    val components = mutableListOf<HashSet<Int>>()
    (0 until n).forEach { v ->
        v.takeIf { !used[v] }?.also { componentsAmount++ }?.also { components.add(matrix.dfs(v, used, hashSetOf(v))) }
    }
    output.use { out ->
        out.println(componentsAmount)
        components.forEach { component -> component.forEach { out.print("${it + 1} ") }.also { out.println(0) } }
    }
}

fun Matrix<Int>.dfs(vert: Int, used: MutableList<Boolean>, componentStorage: HashSet<Int>): HashSet<Int> {
    this.indices.also { used[vert] = true }.forEach { i ->
        i.takeIf { this[vert][it] == 1 && !used[it] }?.also { componentStorage.add(it) }
            ?.also { return dfs(i, used, componentStorage) }
    }.also { return componentStorage }
}