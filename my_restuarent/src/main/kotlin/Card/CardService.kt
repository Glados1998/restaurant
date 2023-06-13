package Card

import org.springframework.stereotype.Service

@Service
class CardService(private val cardRepository: CardRepository) {

    fun findAll(): Iterable<Card> = cardRepository.findAll()

    fun findById(id: Long): Card = cardRepository.findById(id).orElseThrow()

    fun save(card: Card): Card = cardRepository.save(card)

    fun deleteById(id: Long) = cardRepository.deleteById(id)

    fun edit (id: Long, card: Card): Card {
        val adminToEdit = findById(id)
        adminToEdit.name = card.name
        adminToEdit.password = card.password
        return save(adminToEdit)
    }
}