package com.wac.my_restuarant.Card

import org.springframework.stereotype.Service

@Service
class CardService(private val cardRepository: CardRepository) {

    fun findAll(): Iterable<Card> = cardRepository.findAll()

    fun findById(id: Long): Card = cardRepository.findById(id).orElseThrow()

    fun save(card: Card): Card = cardRepository.save(card)

    fun deleteById(id: Long) = cardRepository.deleteById(id)

    fun edit (id: Long, card: Card): Card {
        val cardToEdit = findById(id)
        cardToEdit.name = card.name
        return save(cardToEdit)
    }
}