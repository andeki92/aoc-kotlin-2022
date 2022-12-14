package solutions

import models.InputProvider

private class Directory(
    val name: String,
    val parent: Directory?,
    val children: MutableSet<Directory> = mutableSetOf(),
) {
    private var fileSize = 0L

    val totalSize: Long get() = fileSize + children.sumOf { it.totalSize }

    fun addSubdirectory(dir: Directory): Directory = this.also { children.add(dir) }
    fun addFile(size: Long): Directory = this.also { fileSize += size }

    fun listAllSubDirs(): Set<Directory> = children + children.flatMap { it.listAllSubDirs() }
}


context (InputProvider)
class Day07 : Day(7, 2022, "No Space Left On Device") {

    private val root = Directory("/", null).apply {
        input.fold(this) { directory, command: String ->
            when {
                command == "$ cd /" -> directory
                command == "$ ls" -> directory
                command == "$ cd .." -> directory.parent!!
                command.startsWith("$ cd") -> {
                    directory.children.first { it.name == command.substringAfter("cd ") }
                }

                command.startsWith("dir") -> {
                    val dir = Directory(command.substringAfter("dir "), directory)
                    directory.addSubdirectory(dir)
                }

                else -> {
                    val fileSize = command.substringBefore(" ").toLong()
                    directory.addFile(fileSize)
                }
            }
        }
    }

    override fun part1(): Long = root.listAllSubDirs().filter { it.totalSize <= 100_000 }.sumOf { it.totalSize }

    override fun part2(): Long {
        val missingSpace = 30_000_000L - (70_000_000L - root.totalSize)
        return root.listAllSubDirs().filter { it.totalSize >= missingSpace }.minOf { it.totalSize }
    }
}


fun main() {
    test<Day07>(95437, 24_933_642)
    solve<Day07>()
}