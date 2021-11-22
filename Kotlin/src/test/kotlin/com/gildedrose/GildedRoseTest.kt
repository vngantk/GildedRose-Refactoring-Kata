package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.max

internal class GildedRoseTest {

    @Test
    fun testByApproval() {
        val expectedOutput = outputTextFixtureAsString(days = 10, ::GildedRoseOriginal)
        val actualOutput = outputTextFixtureAsString(days = 10, ::GildedRoseRefactored)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun testUpdateConjuredItemWithSellByDatePassedBeforeQualityDroppedToZero() {
        val itemWithInitialEvenQualityValue = Item("Conjured Mana Cake", sellIn = 3, quality = 15)
        testUpdateConjuredItem(itemWithInitialEvenQualityValue, 5)
    }

    @Test
    fun testUpdateConjuredItemWithQualityDroppedToZeroBeforeSellByDatePassed() {
        val itemWithInitialEvenQualityValue = Item("Conjured Mana Cake", sellIn = 10, quality = 9)
        testUpdateConjuredItem(itemWithInitialEvenQualityValue, 15)
    }

    private fun testUpdateConjuredItem(item: Item, days: Int) {
        val gildedRose = GildedRoseRefactored(arrayOf(item))
        repeat(days) {
            val itemBeforeUpdate = Item(item.name, item.sellIn, item.quality)
            gildedRose.updateQuality()

            assertEquals(itemBeforeUpdate.name, item.name)
            assertEquals(itemBeforeUpdate.sellIn - 1, item.sellIn)

            if (itemBeforeUpdate.quality <= 0) {
                assertEquals(0, item.quality)
            } else if (item.isSellByDatePassed()) {
                assertEquals(max(0, itemBeforeUpdate.quality - 4), item.quality)
            } else {
                assertEquals(max(0, itemBeforeUpdate.quality - 2), item.quality)
            }
        }
    }

    private fun Item.isSellByDatePassed(): Boolean {
        return sellIn < 0
    }

}
