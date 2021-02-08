import generators.MessageGenerator
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import systems.CommunicationSystem
import systems.Recipient

fun main() = runBlocking {
    val communicationSystem = CommunicationSystem(10, MessageGenerator())
    val recipient = Recipient(communicationSystem)

    communicationSystem.start()
    recipient.start()

    delay(10_800)

    communicationSystem.shutDown()
    recipient.shutdown()
}