package utils

import models.InputProvider
import solutions.Day
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

context (InputProvider)
inline fun <reified T : Day> create(): T {
    return T::class.primaryConstructor!!.call(this@InputProvider)
}

context(InputProvider)
fun <T : Day> create(clazz: KClass<T>): T {
    return clazz.primaryConstructor!!.call(this@InputProvider)
}
