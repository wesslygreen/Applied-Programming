import java.io.File

// Data class to store card information
data class Card(val question: String, val answer: String)

class Deck(val name: String) {
    val cards = mutableListOf<Card>()

    fun addCard(question: String, answer: String) {
        cards.add(Card(question, answer))
    }

    // shows all the cards
    fun reviewCards() {
        if (cards.isEmpty()) {
            println("This deck has no cards yet.")
            return
        }
        println("\n--- Reviewing deck: $name ---")
        for ((index, card) in cards.withIndex()) {
            println("${index + 1}. Q: ${card.question}")
            println("   A: ${card.answer}")
        }
    }

    // allows the user to take a quiz
    fun quiz() {
        if (cards.isEmpty()) {
            println("This deck has no cards to quiz.")
            return
        }

        var correct = 0
        var incorrect = 0

        println("\n--- Quiz: $name ---")
        for (card in cards) {
            println("Question: ${card.question}")
            print("Your answer: ")
            val userAnswer = readLine()?.trim() ?: ""

            if (userAnswer.equals(card.answer, ignoreCase = true)) {
                println("Correct!\n")
                correct++
            } else {
                println("Incorrect. Answer: ${card.answer}\n")
                incorrect++
            }
        }

        // Track how many the user got right and wrong
        println("Quiz complete!")
        println("Correct: $correct")
        println("Incorrect: $incorrect")
    }

    // allows the user to save the deck
    fun saveToFile() {
        val file = File("${name}.txt")
        file.printWriter().use { out ->
            for (card in cards) {
                // Use || to separate question and answer in the file
                out.println("${card.question}||${card.answer}")
            }
        }
        println("Deck saved to ${file.name}")
    }

    companion object {
        fun loadFromFile(name: String): Deck {
            val deck = Deck(name)
            val file = File("${name}.txt")

            // If no saved deck, create a new one
            if (!file.exists()) {
                println("No saved deck found. A new deck will be created.")
                return deck
            }

            file.forEachLine { line ->
                val parts = line.split("||")
                if (parts.size == 2) {
                    deck.addCard(parts[0], parts[1])
                }
            }

            println("Deck loaded from ${file.name}")
            return deck
        }
    }
}

// Main function
fun main() {
    println("=== Flashcard App ===")
    print("Enter deck name: ")
    val deckName = readLine()?.trim().orEmpty()

    if (deckName.isBlank()) {
        println("Deck name cannot be empty.")
        return
    }

    val deck = Deck.loadFromFile(deckName)

    //main menu loop that allows the user to choose an option
    while (true) {
        println(
            """
            
            Choose an option:
            1. Add card
            2. Review cards
            3. Quiz yourself
            4. Save deck
            5. Exit
            """.trimIndent()
        )

        print("Option: ")
        when (readLine()?.trim()) {
            "1" -> {
                print("Enter question: ")
                val question = readLine()?.trim().orEmpty()
                print("Enter answer: ")
                val answer = readLine()?.trim().orEmpty()

                if (question.isNotBlank() && answer.isNotBlank()) {
                    deck.addCard(question, answer)
                    println("Card added.")
                } else {
                    println("Question and answer cannot be empty.")
                }
            }

            "2" -> deck.reviewCards()
            "3" -> deck.quiz()
            "4" -> deck.saveToFile()
            "5" -> {
                print("Save before exiting? (y/n): ")
                if (readLine()?.trim()?.lowercase() == "y") {
                    deck.saveToFile()
                }
                println("Goodbye!")
                break
            }

            else -> println("Invalid option.")
        }
    }
}

