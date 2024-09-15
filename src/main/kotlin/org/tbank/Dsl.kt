package org.tbank

fun readme(init: Readme.() -> Unit): Readme {
    val readme = Readme()
    readme.init()
    return readme
}

class Readme {
    private val content = mutableListOf<Any>()

    fun header(level: Int, text: () -> String) {
        content.add(Header(level, text()))
    }

    fun text(init: Text.() -> Unit) {
        val text = Text()
        text.init()
        content.add(text)
    }

    fun print() {
        println(content.joinToString("\n"))
    }
}

class Header(private val level: Int, private val text: String) {
    override fun toString(): String {
        return "${"#".repeat(level)} $text"
    }
}

class Text {
    private val content = mutableListOf<String>()

    fun bold(text: String): String {
        return "**$text**"
    }

    fun link(link: String, text: String): String {
        return "[$text]($link)"
    }

    fun underlined(text: String): String {
        return "<ins>$text</ins>"
    }

    fun code(language: ProgrammingLanguage, code: () -> String): String {
        return "```(${language.textName})\n${code()}\n```"
    }

    operator fun String.unaryPlus() {
        content.add(this)
    }

    override fun toString(): String {
        return content.joinToString("\n")
    }
}

enum class ProgrammingLanguage(val textName: String) {
    KOTLIN("Kotlin"),
    JAVA("Java"),
    CPP("C++")
}
