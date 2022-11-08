package pini.mattia.coachtimer

import pini.mattia.coachtimer.model.player.Picture
import pini.mattia.coachtimer.model.player.Player
import pini.mattia.coachtimer.model.trainingsession.Lap
import pini.mattia.coachtimer.model.trainingsession.TrainingSession
import java.util.Date

val fakePlayers = listOf(
    Player(
        "Mr",
        "Sir",
        "Punce",
        picture = Picture(
            "https://randomuser.me/api/portraits/men/42.jpg",
            "https://randomuser.me/api/portraits/med/men/42.jpg",
            "https://randomuser.me/api/portraits/thumb/men/42.jpg"
        )
    ),
    Player(
        "Mr",
        "John",
        "Doe",
        picture = Picture(
            "https://randomuser.me/api/portraits/men/91.jpg",
            "https://randomuser.me/api/portraits/med/men/91.jpg",
            "https://randomuser.me/api/portraits/thumb/men/91.jpg"
        )
    ),
    Player(
        "Miss",
        "Kappa",
        "U'rina",
        picture = Picture(
            "https://randomuser.me/api/portraits/men/91.jpg",
            "https://randomuser.me/api/portraits/med/men/91.jpg",
            "https://randomuser.me/api/portraits/thumb/men/91.jpg"
        )
    )
)

val fakeTrainingSession = listOf(
    TrainingSession(
        Date().time,
        45000,
        30,
        Player("Mr", "Sir", "Pounce", Picture("fake", "fake", "fake")),
        listOf(
            Lap(5000),
            Lap(5500),
            Lap(6000),
            Lap(6500),
            Lap(7000)
        )
    ),
    TrainingSession(
        Date().time,
        45000,
        30,
        Player("Miss", "Bri", "Anza", Picture("fake", "fake", "fake")),
        listOf(
            Lap(5000),
            Lap(5500),
            Lap(6000),
            Lap(6500),
            Lap(7000)
        )
    ),
    TrainingSession(
        Date().time,
        45000,
        30,
        Player("Mister", "Bruce", "Chetta", Picture("fake", "fake", "fake")),
        listOf(
            Lap(5000),
            Lap(5500),
            Lap(6000),
            Lap(6500),
            Lap(7000)
        )
    )
)
