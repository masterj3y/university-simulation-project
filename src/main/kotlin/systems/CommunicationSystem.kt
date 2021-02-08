package systems

import Constants
import entities.Message
import generators.MessageGenerator
import kotlinx.coroutines.*
import java.util.*

class CommunicationSystem(val numberOfLines: Int, private val messageGenerator: MessageGenerator) {

    private val coroutineScope = CoroutineScope(Dispatchers.Default + CoroutineName("CommunicationSystem"))

    private val lines: Array<MutableList<Message>> = Array(numberOfLines) { mutableListOf() }
    private val linesWaitingTime: Array<Long> = Array(numberOfLines) { 0 }

    private val randomLineNumber: Int
        get() = (0..9).random()

    private val nextMessageGenerationDelay: Long
        get() = (4..7).random().toLong() * Constants.timeSpeed

    fun start() {
        coroutineScope.launch {
            while (isActive) {
                val lineNumber = randomLineNumber
                val message = messageGenerator.generate()
                lines[lineNumber].add(message)
                delay(nextMessageGenerationDelay)
            }
        }
    }

    fun getMessage(lineNumber: Int): Message? {
        if (lineNumber > numberOfLines || lineNumber < 0)
            throw IllegalStateException("there is no line that numbered by $lineNumber, available lines: from 0 too $numberOfLines")

        val line = lines[lineNumber]

        val message = line.firstOrNull()

        message?.let {
            val messageWaitingTime = Date().time - message.createdAt
            linesWaitingTime[lineNumber] = linesWaitingTime[lineNumber] + messageWaitingTime
            line.remove(it)
        }

        return message
    }

    fun shutDown() {
        coroutineScope.cancel()
        val waitingTimes = linesWaitingTime.joinToString(", ") { "${it/3600f}m" }
        println("\nlines waiting time: $waitingTimes")
    }
}