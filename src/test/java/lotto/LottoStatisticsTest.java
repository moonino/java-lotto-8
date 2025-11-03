package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoStatisticsTest {

    private LottoStatistics stats;

    @BeforeEach
    void setUp() {
        // Given: 8000원(8장)을 샀고, 5등 1장, 꽝 7장이 나왔다고 가정
        PurchaseAmount purchaseAmount = new PurchaseAmount(8000);
        List<Rank> results = List.of(
                Rank.FIFTH, // 5,000원
                Rank.MISS,
                Rank.MISS,
                Rank.MISS,
                Rank.MISS,
                Rank.MISS,
                Rank.MISS,
                Rank.MISS
        );

        this.stats = new LottoStatistics(results, purchaseAmount);
    }

    @Test
    @DisplayName("각 등수별 당첨 개수를 정확히 집계한다.")
    void countRanks() {
        assertThat(stats.getCount(Rank.FIFTH)).isEqualTo(1);
        assertThat(stats.getCount(Rank.FOURTH)).isEqualTo(0);
        assertThat(stats.getCount(Rank.THIRD)).isEqualTo(0);
        assertThat(stats.getCount(Rank.SECOND)).isEqualTo(0);
        assertThat(stats.getCount(Rank.FIRST)).isEqualTo(0);
    }

    @Test
    @DisplayName("총 당첨금을 정확히 계산한다.")
    void calculateTotalPrizeMoney() {
        long totalPrize = stats.getTotalPrizeMoney();

        assertThat(totalPrize).isEqualTo(5_000L); // 5등 1장
    }

    @Test
    @DisplayName("총 수익률을 소수점 둘째 자리에서 반올림하여 계산한다.")
    void calculateProfitRate() {
        // README 예시: 8000원 투자, 5000원 당첨 -> "총 수익률은 62.5%입니다."
        double profitRate = stats.getProfitRate();

        assertThat(profitRate).isEqualTo(62.5);
    }
}