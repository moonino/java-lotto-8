package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @DisplayName("일치 개수와 보너스 여부에 따라 정확한 등수를 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "6, false, FIRST",  // 6개 일치 -> 1등
            "5, true, SECOND",  // 5개 일치 + 보너스 -> 2등
            "5, false, THIRD",  // 5개 일치 -> 3등
            "4, false, FOURTH", // 4개 일치 -> 4등
            "3, false, FIFTH",  // 3개 일치 -> 5등
            "2, false, MISS",   // 2개 일치 -> 꽝
            "1, true, MISS",    // 1개 일치 + 보너스 (꽝)
            "0, false, MISS"    // 0개 일치 (꽝)
    })
    void findRankByMatchCountAndBonus(int matchCount, boolean bonusMatch, Rank expectedRank) {
        // when
        // (RED: Rank Enum도, find 메서드도 아직 없음)
        Rank result = Rank.find(matchCount, bonusMatch);

        // then
        assertThat(result).isEqualTo(expectedRank);
    }
}