package com.githubuiviewer.tools

enum class Emoji(val emojiCode: String, val githubReaction: String) {
    LIKE("\uD83D\uDC4D", "+1"),
    DISLIKE("\uD83D\uDC4E", "-1"),
    LAUGH("\uD83D\uDE04", "laugh"),
    CONFUSED("\uD83D\uDE15", "confused"),
    HEART("❤", "heart"),
    HOORAY("\uD83C\uDF89", "hooray"),
    ROCKET("\uD83D\uDE80", "rocket"),
    EYES("\uD83D\uDC40", "eyes")
}