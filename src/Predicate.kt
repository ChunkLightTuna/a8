data class Predicate(val name: String, val args: List<String>) {
    private constructor(predicate: Predicate) : this(predicate.name, predicate.args)
    constructor(string: String) : this(gen(string))

    companion object {
        fun gen(string: String): Predicate {
            return if (string.isBlank()) {
                Predicate("", listOf())
            } else {
                val split = string.split("(")
                val name = split[0].filterNot(Char::isWhitespace)
                val args = split[1].split(",")

                Predicate(name, args)
            }
        }

        fun genL(string: String): List<Predicate> {
            return if (string.isBlank()) {
                listOf()
            } else {
                string.filterNot(Char::isWhitespace)
                        .split(")")
                        .dropLast(1)
                        .map(::Predicate)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (!(other is Predicate && name == other.name)) {
            return false
        }

        //inefficient, but they're small so w/e
        args.forEachIndexed { i, s ->
            if (s != other.args[i]) {
                return false
            }
        }

        return true
    }


    override fun hashCode(): Int {
        var k = 0
        args.forEachIndexed { i, s -> k += (i + 1) * s.hashCode() }

        return name.hashCode() + k
    }
}