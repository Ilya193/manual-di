package ru.ikom.ui

interface BaseView<Model: Any> {

    var viewRenderer: ViewRenderer<Model>?

    fun render(model: Model) {
        viewRenderer?.render(model)
    }

    fun release() { viewRenderer = null }
}

fun <Model : Any> diff(block: DiffBuilder<Model>.() -> Unit): ViewRenderer<Model> {
    val builder = object : DiffBuilder<Model>(), ViewRenderer<Model> {
        override fun render(model: Model) {
            binders.forEach { it.render(model) }
        }
    }

    builder.block()
    return builder
}

interface ViewRenderer<Model: Any> {
    fun render(model: Model)
}

open class DiffBuilder<Model : Any> {
    val binders = mutableListOf<ViewRenderer<Model>>()

    inline fun <T> diff(
        crossinline get: (Model) -> T,
        crossinline compare: (old: T, new: T) -> Boolean = { a, b -> a == b },
        crossinline set: (T) -> Unit
    ) {
        binders += object : ViewRenderer<Model> {
            private var oldValue: T? = null

            override fun render(model: Model) {
                val newValue = get(model)
                val oldValue = oldValue
                this.oldValue = newValue

                if ((oldValue == null) || !compare(oldValue, newValue)) {
                    set(newValue)
                }
            }
        }
    }

    inline fun <T> all(
        crossinline get: (Model) -> T,
        crossinline compare: (old: T, new: T) -> Boolean = { a, b -> a == b },
        crossinline set: (Model) -> Unit
    ) {
        binders += object : ViewRenderer<Model> {
            private var oldValue: T? = null

            override fun render(model: Model) {
                val newValue = get(model)
                val oldValue = oldValue
                this.oldValue = newValue

                if ((oldValue == null) || !compare(oldValue, newValue)) {
                    set(model)
                }
            }
        }
    }
}