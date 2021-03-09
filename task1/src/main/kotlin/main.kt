import java.io.File

internal typealias Labyrinth = MutableList<List<Int>>

fun main(args: Array<String>) {
    val inputFile = File("task1/in.txt").readLines().iterator()
    val outputFile = File("task1/out.txt").printWriter()
    val lines = inputFile.next().toInt()
    val rows = inputFile.next().toInt()
    val labyrinth: Labyrinth = mutableListOf()
    for (i in 0 until lines)
        labyrinth.add(readIntegerList(inputFile))
    val startPosition = readIntegerList(inputFile).map { it - 1 }
    val endPosition = readIntegerList(inputFile).map { it - 1 }

    val result = labyrinth.bfs(startPosition, endPosition)
    if (result == null) {
        outputFile.use { it.println("N") }
    } else {
        outputFile.use { out ->
            out.println("Y")
            result.reversed().map { out.println(it) }
        }
    }
}

fun Labyrinth.bfs(startPosition: List<Int>, endPosition: List<Int>): Path? {
    var result: Path? = null
    val queue = ArrayDeque<Path>()
    queue.addLast(Path(null, startPosition))
    while (!queue.isEmpty()) {
        val currentPath = queue.removeFirst()
        val (curX, curY) = currentPath.value
        if (curX == endPosition[0] && curY == endPosition[1]) {
            result = currentPath
            break
        }
        this.getNeighbours(curX, curY).forEach { queue.addLast(currentPath.addNextStep(it)) }
    }
    return result
}

fun readIntegerList(file: Iterator<String>): List<Int> = file.next().split(' ').map { it.toInt() }

fun Labyrinth.isInBounds(x: Int, y: Int): Boolean = x < this.size && y < this[0].size && x >= 0 && y >= 0

fun Labyrinth.getNeighbours(x: Int, y: Int): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    Pair(x, y).apply {
        checkNeighbour(0, -1, this@getNeighbours, result)
        checkNeighbour(0, 1, this@getNeighbours, result)
        checkNeighbour(-1, 0, this@getNeighbours, result)
        checkNeighbour(1, 0, this@getNeighbours, result)
    }
    return result
}

private fun Pair<Int, Int>.checkNeighbour(
    dx: Int,
    dy: Int,
    labyrinth: Labyrinth,
    result: MutableList<List<Int>>
) {
    if (labyrinth.isInBounds(this.first + dx, this.second + dy) && labyrinth[this.first + dx][this.second + dy] != 1) {
        result.add(listOf(this.first + dx, this.second + dy))
    }
}

class Path(private val previous: Path?, val value: List<Int>) : Iterable<Path> {
    fun addNextStep(child: List<Int>): Path = Path(this, child)

    override fun iterator(): Iterator<Path> = generateSequence(this) { it.previous }.iterator()

    override fun toString(): String = "${value[0] + 1} ${value[1] + 1}"
}