import java.io.File

typealias Matrix<T> = List<List<T>>

fun main() {
    val input = File("task2/in.txt").readLines().iterator()
    val output = File("task2/out.txt").printWriter()
    val n = input.next().toInt()
    val matrix: Matrix<Int> = (0 until n).map { input.next().split(' ').map { it.toInt() } }
    val used = (0 until n).map { false }.toMutableList()
    var componentsAmount = 0
    val components = mutableListOf<HashSet<Int>>()
    (0 until n).forEach { v ->
        v.takeIf { !used[v] }?.also { componentsAmount++ }?.also {
            val newComponent = hashSetOf(v)
            matrix.dfs(v, used, newComponent)
            components.add(newComponent)
        }
    }
    output.use { out ->
        out.println(componentsAmount)
        components.forEach { component -> component.forEach { out.print("${it + 1} ") }.also { out.println(0) } }
    }
}

fun Matrix<Int>.dfs(vert: Int, used: MutableList<Boolean>, componentStorage: HashSet<Int>) {
    this.indices.also { used[vert] = true }.forEach { i ->
        i.takeIf { this[vert][i] == 1 && !used[i] }?.also { componentStorage.add(i) }
            ?.also { dfs(i, used, componentStorage) }
    }
}