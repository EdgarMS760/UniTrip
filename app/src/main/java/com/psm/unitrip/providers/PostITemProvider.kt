package com.psm.unitrip.providers

import com.psm.unitrip.R
import com.psm.unitrip.classes.PostItem

class PostITemProvider {
    companion object {
        val PostList = listOf<PostItem>(
            PostItem(
                listOf(R.drawable.background, R.drawable.champions),
                "@gigakai",
                "Av. Camino del Valle 2247...",
                "Voy a Cerralvo",
                "$500.00",
                "Salida desde Pedro de Alba...",
                "27/01/2024 11:30:10 PM"
            ),
            PostItem(
                listOf(R.drawable.background),
                "@EdgarMs760",
                "Av. pablo livas 587",
                "raid a la uni",
                "$20",
                "Salida desde el cine pablo livas",
                "24/10/2024 11:30:10 PM"
            ),
            PostItem(
                listOf(R.drawable.background),
                "@user1",
                "Calle Falsa 123...",
                "Voy a Monterrey",
                "$300.00",
                "Salida desde Plaza Central...",
                "15/02/2024 08:00:00 AM"
            ),
            PostItem(
                listOf(R.drawable.background),
                "@user2",
                "Av. Siempre Viva 742...",
                "Voy a Guadalupe",
                "$450.00",
                "Salida desde Estadio...",
                "20/02/2024 02:30:00 PM"
            ),
            PostItem(
                listOf(R.drawable.background),
                "@user3",
                "Calle de la Amargura 666...",
                "Voy a San Pedro",
                "$600.00",
                "Salida desde Centro Comercial...",
                "05/03/2024 05:45:00 PM"
            ),
            PostItem(
                listOf(R.drawable.background),
                "@user4",
                "Boulevard de los Sueños Rotos...",
                "Voy a Apodaca",
                "$350.00",
                "Salida desde Terminal Norte...",
                "10/03/2024 09:15:00 AM"
            ),
            PostItem(
                listOf(R.drawable.background),
                "@user5",
                "Avenida de la Paz 101...",
                "Voy a Escobedo",
                "$400.00",
                "Salida desde Parque Fundidora...",
                "25/03/2024 07:00:00 PM"
            )

        )
    }
}