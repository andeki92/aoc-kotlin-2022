package utils

fun <T : Any> List<T>.chunked(delimiter: (T) -> Boolean): List<List<T>> =
    mapIndexedNotNull { index, entry ->
        index.takeIf { index == 0 || delimiter(entry) || index == this.lastIndex }
    }.windowed(2).map { (from, to) ->
        this.slice(from..to).filterNot(delimiter)
    }

