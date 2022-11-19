import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
inline fun <T, R> switch(value: T, @BuilderInference closure: Case.Builder<T, R>.() -> Case.Default<T, R>): R {
    val (cases, default) = Case.Builder<T, R>().let { it._cases to closure(it) }
    return cases.firstNotNullOfOrNull { with(it) { value() } } ?: with(default) { value() }
}

fun interface Case<T, R> {

    operator fun T.invoke(): R?

    class Builder<T, R>(
        val _cases: MutableList<Case<T, R>> = mutableListOf()
    ) {
        inline fun case(crossinline condition: T.() -> Boolean, crossinline closure: T.() -> R) {
            _cases.add(Case { if(condition()) closure() else null })
        }

        inline fun default(crossinline closure: T.() -> R) = Default<T, R> { closure() }.also { _cases.add(it) }
    }

    fun interface Default<T, R> : Case<T, R> {
        override operator fun T.invoke(): R
    }

}