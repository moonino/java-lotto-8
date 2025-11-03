package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class WinningLottoTest {

    private Lotto mainNumbers; // 당첨 번호 (6개)

    @BeforeEach
    void setUp() {
        this.mainNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
    }

    @DisplayName("보너스 번호가 당첨 번호 6개와 중복되면 예외가 발생한다.")
    @Test
    void createWinningLottoWithDuplicateBonus() {
        // given
        int bonusNumber = 6;

        // when & then
        assertThatThrownBy(() -> new WinningLotto(mainNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void createWinningLottoWithInvalidBonusRange() {
        // given
        int bonusNumber = 46;

        // when & then
        assertThatThrownBy(() -> new WinningLotto(mainNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다. (0의 경우)")
    @Test
    void createWinningLottoWithInvalidBonusRangeZero() {

        int bonusNumber = 0;

        assertThatThrownBy(() -> new WinningLotto(mainNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
    }
    @DisplayName("로또 매칭 (등수 결정) 테스트")
    @Nested
    class MatchTest {

        private WinningLotto winningLotto;

        @BeforeEach
        void setUp() {
            // "정답지" 로또: 1, 2, 3, 4, 5, 6 (보너스: 7)
            Lotto mainLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
            this.winningLotto = new WinningLotto(mainLotto, 7);
        }

        @Test
        @DisplayName("1등: 6개 번호 모두 일치")
        void matchFirstPlace() {
            Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

            Rank rank = winningLotto.match(userLotto);

            assertThat(rank).isEqualTo(Rank.FIRST);
        }

        @Test
        @DisplayName("2등: 5개 번호 + 보너스 번호 일치")
        void matchSecondPlace() {
            Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 7)); // 5개 + 보너스 7
            Rank rank = winningLotto.match(userLotto);
            assertThat(rank).isEqualTo(Rank.SECOND);
        }

        @Test
        @DisplayName("3등: 5개 번호 일치 (보너스 불일치)")
        void matchThirdPlace() {
            Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 8)); // 5개 + 꽝 보너스 8
            Rank rank = winningLotto.match(userLotto);
            assertThat(rank).isEqualTo(Rank.THIRD);
        }

        @Test
        @DisplayName("4등: 4개 번호 일치")
        void matchFourthPlace() {
            Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 8, 9)); // 4개
            Rank rank = winningLotto.match(userLotto);
            assertThat(rank).isEqualTo(Rank.FOURTH);
        }

        @Test
        @DisplayName("5등: 3개 번호 일치")
        void matchFifthPlace() {
            Lotto userLotto = new Lotto(List.of(1, 2, 3, 8, 9, 10)); // 3개
            Rank rank = winningLotto.match(userLotto);
            assertThat(rank).isEqualTo(Rank.FIFTH);
        }

        @Test
        @DisplayName("꽝(MISS): 2개 번호 일치")
        void matchMiss() {
            Lotto userLotto = new Lotto(List.of(1, 2, 8, 9, 10, 11)); // 2개
            Rank rank = winningLotto.match(userLotto);
            assertThat(rank).isEqualTo(Rank.MISS);
        }
    }
}