package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
}