package lotto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class LottoTest {
    @Test
    fun throwExceptionWhenNumberCountIsMoreThanSix() {
        assertThrows<IllegalArgumentException> {
            Lotto(listOf(1, 2, 3, 4, 5, 6, 7))
        }
    }

    @Test
    fun throwExceptionWhenNumberCountIsLessThanFive() {
        assertThrows<IllegalArgumentException> { Lotto(listOf(1, 2, 3, 4, 5)) }
    }

    @Test
    fun throwExceptionWhenNumbersAreNotInValidRange() {
        assertThrows<IllegalArgumentException> { Lotto(listOf(0, 1, 2, 3, 4, 5)) }
        assertThrows<IllegalArgumentException> { Lotto(listOf(1, 2, 3, 4, 5, 46)) }
    }

    @Test
    fun throwExceptionWhenNumbersAreNotUnique() {
        assertThrows<IllegalArgumentException> {
            Lotto(listOf(1, 2, 3, 4, 5, 5))
        }
    }

    @Test
    fun throwExceptionWhenPriceAreNotValid() {
        assertThrows<IllegalArgumentException> {
            validateLottoPriceString("abcd")
        }
    }

    @Test
    fun throwExceptionWhenPriceIsNegative() {
        assertThrows<IllegalArgumentException> {
            validateLottoPrice(-1000)
        }
    }

    @Test
    fun throwExceptionWhenPriceIsNotMultipleOf1000() {
        assertThrows<IllegalArgumentException> { validateLottoPrice(1001) }
    }

    @Test
    fun throwExceptionWhenNumbersAreNotValid() {
        assertThrows<IllegalArgumentException> { parseNormalWinningNumbers("1,2,3,4,5,a") }
        assertThrows<IllegalArgumentException> { parseBonusNumber("a") }
    }

    @Test
    fun throwExceptionWhenWinningNumbersAreNotCountOfFive() {
        assertThrows<IllegalArgumentException> { validateWinningNumber(listOf(1, 2, 3, 4, 5) to 6) }
        assertThrows<IllegalArgumentException> {
            validateWinningNumber(listOf(1, 2, 3, 4, 5, 6, 7) to 8)
        }
    }

    @Test
    fun throwExceptionWhenWinningNumbersAreNotValidRange() {
        assertThrows<IllegalArgumentException> { validateWinningNumber(listOf(-1, 1, 2, 3, 4, 5) to 6) }
        assertThrows<IllegalArgumentException> { validateWinningNumber(listOf(47, 1, 2, 3, 4, 5) to 6) }
        assertThrows<IllegalArgumentException> { validateWinningNumber(listOf(6, 1, 2, 3, 4, 5) to -1) }
        assertThrows<IllegalArgumentException> { validateWinningNumber(listOf(6, 1, 2, 3, 4, 5) to 47) }
    }

    @Test
    fun throwExceptionWhenWinningNumbersAreNotUnique() {
        assertThrows<IllegalArgumentException> { validateWinningNumber(listOf(1, 2, 3, 4, 5, 5) to 6) }
        assertThrows<IllegalArgumentException> { validateWinningNumber(listOf(1, 2, 3, 4, 5, 6) to 6) }
    }

    @Test
    fun checkForLottoRank() {
        assertEquals(gameRank(Lotto(listOf(1, 2, 3, 4, 5, 6)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.FIRST)
        assertEquals(gameRank(Lotto(listOf(1, 2, 3, 4, 5, 7)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.SECOND)
        assertEquals(gameRank(Lotto(listOf(1, 2, 3, 4, 5, 8)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.THIRD)
        assertEquals(gameRank(Lotto(listOf(1, 2, 3, 4, 5, 8)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.THIRD)
        assertEquals(gameRank(Lotto(listOf(1, 2, 3, 4, 8, 7)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.FOURTH)
        assertEquals(gameRank(Lotto(listOf(1, 2, 3, 4, 8, 9)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.FOURTH)
        assertEquals(gameRank(Lotto(listOf(1, 2, 3, 8, 9, 7)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.FIFTH)
        assertEquals(gameRank(Lotto(listOf(1, 2, 3, 8, 9, 10)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.FIFTH)
        assertEquals(gameRank(Lotto(listOf(1, 2, 7, 9, 10, 11)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.NONE)
        assertEquals(gameRank(Lotto(listOf(1, 2, 8, 9, 10, 11)), listOf(1, 2, 3, 4, 5, 6) to 7), LottoRank.NONE)
    }
}