package solutions

import models.InputProvider

private sealed interface Packet : Comparable<Packet> {
    class Raw(val value: Int) : Packet {
        override fun compareTo(other: Packet): Int = when (other) {
            is Raw -> value compareTo other.value
            is Group -> Group(this) compareTo other
        }

        override fun toString(): String = value.toString()
    }

    class Group(vararg val packets: Packet) : Packet {
        override fun compareTo(other: Packet): Int = when (other) {
            is Raw -> this compareTo Group(other)
            is Group -> packets.zip(other.packets, Packet::compareTo).firstOrNull { it != 0 }
                ?: packets.size.compareTo(other.packets.size)
        }

        override fun toString(): String = "[${packets.joinToString(",")}]"
    }
}

context (InputProvider)
class Day13 : Day(13, 2022, "Distress Signal") {

    private val packets = input.filter(String::isNotBlank).map(::parsePacket)

    override fun part1(): Int = packets.chunked(2)
        .mapIndexed { index, (packet, controlPacket) -> if (packet < controlPacket) index + 1 else 0 }
        .sum()

    override fun part2(): Int {
        val dividerPackageOne = Packet.Group(Packet.Group(Packet.Raw(2)))
        val dividerPackageTwo = Packet.Group(Packet.Group(Packet.Raw(6)))

        val dividedPackets = (packets + dividerPackageOne + dividerPackageTwo).sorted()

        return (1 + dividedPackets.indexOf(dividerPackageOne)) * (1 + dividedPackets.indexOf(dividerPackageTwo))
    }
}

private val regex = """(,)|(?=\[)|(?=])|(?<=\[)|(?<=])""".toRegex()

private fun parsePacket(line: String): Packet = buildList<MutableList<Packet>> {
    add(mutableListOf())
    line.split(regex).filter(String::isNotBlank).forEach { t ->
        when (t) {
            "[" -> add(mutableListOf())
            "]" -> removeLast().also { last().add(Packet.Group(*it.toTypedArray())) }
            else -> last().add(Packet.Raw(t.toInt()))
        }
    }
}[0][0]

fun main() {
    test<Day13>(13, 140)
    solve<Day13>()
}