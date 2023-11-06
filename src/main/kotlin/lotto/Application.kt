package lotto

import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms.pickUniqueNumbersInRange

fun pickLottoNumbers(): List<Int> {
    return pickUniqueNumbersInRange(
        LottoConstraints.NUMBER_START, LottoConstraints.NUMBER_END, LottoConstraints.NUMBER_COUNT
    )
}

fun isValidInteger(digits: String): Boolean {
    return digits.all { it.isDigit() }
}

fun isPriceMultipleOf1000(value: Int): Boolean {
    return value % 1000 == 0
}

fun isPriceValid(digits: String): Boolean {
    if (!isValidInteger(digits)) {
        return false
    }
    val value = digits.toInt()
    return isPriceMultipleOf1000(value)
}

fun lottoPayment(): Int {
    println("구입금액을 입력해주세요.")
    var digits = Console.readLine()
    while (!isPriceValid(digits)) {
        println("다시 입력해주세요.")
        digits = Console.readLine()
    }
    return digits.toInt()
}

fun makeLottoTicket(): Lotto {
    return Lotto(pickLottoNumbers())
}

fun lottoGame(price: Int): List<Lotto> {
    val lottoGameTicketCount = price / 1000
    println("${lottoGameTicketCount}개를 구매했습니다.")
    val tickets: MutableList<Lotto> = mutableListOf()
    repeat(lottoGameTicketCount) {
        val ticket = makeLottoTicket()
        println(ticket)
        tickets.add(ticket)
    }
    return tickets
}

fun hasSixNumbers(numbers: List<Int>): Boolean {
    return numbers.size == 6
}

fun isInValidRange(numbers: List<Int>): Boolean {
    return numbers.all { (1 <= it) and (it <= 45) }
}

fun isUnique(numbers: List<Int>): Boolean {
    val uniqueNumbers: HashSet<Int> = hashSetOf()
    return numbers.all { uniqueNumbers.add(it) }
}

fun isUniqueWithBonusNumber(numbers: List<Int>, bonusNumber: Int): Boolean {
    val uniqueNumbers: HashSet<Int> = hashSetOf(bonusNumber)
    return numbers.all { uniqueNumbers.add(it) }
}

fun parseWinningNumber(): Pair<List<Int>, Int> {
    val numbers = Console.readLine().split(",").map { it.toInt() }
    val bonusNumber = Console.readLine().toInt()
    return numbers to bonusNumber
}

fun validateWinningNumber(winningNumber: Pair<List<Int>, Int>) {
    val (numbers, bonusNumber) = winningNumber
    require(hasSixNumbers(numbers))
    require(isUnique(numbers))
    require((1 <= bonusNumber) and (bonusNumber <= 45))
    require(isUniqueWithBonusNumber(numbers, bonusNumber))
}

fun normalNumberMatch(lottoNumber: Lotto, normalNumber: List<Int>): Int {
    val a = hashSetOf(lottoNumber)
    return (hashSetOf(lottoNumber).intersect(hashSetOf(normalNumber))).size
}

fun bonusNumberMatch(lottoNumber: Lotto, bonusNumber: Int): Boolean {
    return hashSetOf(lottoNumber).contains(bonusNumber)
}

fun gameRank(lottoNumber: Lotto, winningNumber: Pair<List<Int>, Int>): Int {
    val (normalNumber, bonusNumber) = winningNumber
    val numberMatch = normalNumberMatch(lottoNumber, normalNumber)
    val isBonusNumberMatch = bonusNumberMatch(lottoNumber, bonusNumber)
    return when {
        numberMatch == 6 -> 1
        (numberMatch == 5) and isBonusNumberMatch -> 2
        numberMatch == 5 -> 3
        numberMatch == 4 -> 4
        numberMatch == 3 -> 5
        else -> -1
    }
}

fun moneyRewardCalculate(rank: Int): Int {
    return when (rank) {
        1 -> 2_000_000_000
        2 -> 30_000_000
        3 -> 1_500_000
        4 -> 50_000
        5 -> 5_000
        else -> 0
    }
}

fun rewardRateCalculate(reward: Int, price: Int): Double {
    return reward.toDouble() * 100 / price.toDouble()
}

fun main() {
    println(lottoPayment())
}
