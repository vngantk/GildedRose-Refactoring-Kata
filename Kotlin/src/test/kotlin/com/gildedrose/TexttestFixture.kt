package com.gildedrose

import java.io.ByteArrayOutputStream
import java.io.PrintStream

fun main(args: Array<String>) {
    System.out.outputTextFixture(days = if (args.isNotEmpty()) args[0].toInt() + 1 else 8, ::GildedRoseOriginal)
}

fun PrintStream.outputTextFixture(days: Int, factory: (Array<Item>) -> GildedRose ) {
    println("OMGHAI!")

    val items = arrayOf(Item("+5 Dexterity Vest", 10, 20), //
            Item("Aged Brie", 2, 0), //
            Item("Elixir of the Mongoose", 5, 7), //
            Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            Item("Sulfuras, Hand of Ragnaros", -1, 80),
            Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            // Item("Conjured Mana Cake", 3, 6)
    )

    val app = factory(items)

    for (i in 0 until days) {
        println("-------- day $i --------")
        println("name, sellIn, quality")
        for (item in items) {
            println(item)
        }
        println()
        app.updateQuality()
    }
}

fun outputTextFixtureAsString(days: Int, factory: (Array<Item>) -> GildedRose): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val printStream = PrintStream(byteArrayOutputStream, true, "utf-8")
    printStream.outputTextFixture(days, factory)
    printStream.close()
    return byteArrayOutputStream.toString("utf-8")
}
