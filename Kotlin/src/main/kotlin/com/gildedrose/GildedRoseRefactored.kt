package com.gildedrose

import kotlin.math.min
import kotlin.math.max

class GildedRoseRefactored(override var items: Array<Item>) : GildedRose {

    companion object {
        const val AgedBrie = "Aged Brie"
        const val Sulfuras = "Sulfuras, Hand of Ragnaros"
        const val BackstagePasses = "Backstage passes to a TAFKAL80ETC concert"
        const val Conjured = "Conjured"
    }

    override fun updateQuality() {
        items.forEach { item -> updateQuality(item) }
    }

    private fun updateQuality(item: Item) {
        if (item.isSpecialItem()) {
            item.updateSpecialItem()
        } else {
            item.updateNormalItem()
        }
    }

    private fun Item.updateSpecialItem() {
        when (name) {
            AgedBrie -> updateAgedBrieItem()
            BackstagePasses -> updateBackstagePassesItem()
            Sulfuras -> updateSulfurasItem()
            else -> if (isConjuredItem()) updateConjureItem()
        }
    }

    private fun Item.updateNormalItem() {
        decrementSellIn()
        decrementQualityBy(1)
        forceQualityWithinLimits()
    }

    private fun Item.updateAgedBrieItem() {
        decrementSellIn()
        incrementQualityBy(1)
        forceQualityWithinLimits()
    }

    private fun Item.updateBackstagePassesItem() {
        decrementSellIn()
        when {
            isSellByDatePassed() -> quality = 0
            isSellByDateLessThan(5)  -> incrementQualityBy(3)
            isSellByDateLessThan(10) -> incrementQualityBy(2)
            else -> incrementQualityBy(1)
        }
        forceQualityWithinLimits()
    }

    private fun Item.updateConjureItem() {
        decrementSellIn()
        decrementQualityBy(2)
        forceQualityWithinLimits()
    }

    private fun Item.updateSulfurasItem() {
        nothingToUpdate()
    }

    private fun Item.nothingToUpdate() = Unit

    private fun Item.decrementSellIn() {
        sellIn -= 1
    }

    private fun Item.decrementQualityBy(delta: Int) {
        changeQuality(-delta)
    }

    private fun Item.incrementQualityBy(delta: Int) {
        changeQuality(+delta)
    }

    private fun Item.changeQuality(delta: Int) {
        quality += delta * getQualityChangeFactor()
    }

    private fun Item.forceQualityWithinLimits(min: Int = 0, max: Int = 50) {
        quality = max(min, min(max, quality))
    }

    private fun Item.getQualityChangeFactor(): Int = if (isSellByDatePassed()) 2 else 1

    private fun Item.isSellByDatePassed(): Boolean = isSellByDateLessThan(0)

    private fun Item.isSellByDateLessThan(days: Int): Boolean = sellIn < days

    private fun Item.isSpecialItem(): Boolean = name in setOf(AgedBrie, Sulfuras, BackstagePasses) || isConjuredItem()

    private fun Item.isConjuredItem(): Boolean = name.startsWith(Conjured)
}
