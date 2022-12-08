package utils

import models.InputContext
import solutions.Day
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

context (InputContext)
inline fun <reified T : Day> create(): T {
    return T::class.constructors.first { it.parameters.isEmpty() }.call(this@InputContext)
}

context(InputContext)
fun <T : Day> create(clazz: KClass<T>): T {
    return clazz.primaryConstructor!!.call(this@InputContext)
}
