package generators

import entities.Message
import java.util.*
import kotlin.streams.asSequence

class MessageGenerator {

    private val letters: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private val generateRandomId: Int
        get() = (111111..999999).random()

    private val generateRandomMessageLen: Long
        get() = (1..100).random().toLong()

    private val generateRandomMessage: String
        get() = Random().ints(generateRandomMessageLen, 0, letters.length)
            .asSequence()
            .map(letters::get)
            .joinToString("")

    fun generate(): Message = Message(
        id = generateRandomId,
        createdAt = Date().time,
        text = generateRandomMessage
    )
}