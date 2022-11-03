package pini.mattia.coachtimer

import pini.mattia.coachtimer.model.player.Picture
import pini.mattia.coachtimer.model.player.Player

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
