package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseAmountTest {

    @DisplayName("구입 금액이 1,000원으로 나누어 떨어지지 않으면 예외가 발생한다.")
    @Test
    void createAmountByInvalidUnit() {

        int inputAmount = 1500; // 실패할 것이라 예상되는 값

        assertThatThrownBy(() -> new PurchaseAmount(inputAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
    }

    @DisplayName("구입 금액이 0원 이하면 예외가 발생한다.")
    @Test
    void createAmountByZeroOrLess() {

        int inputAmount = 0; // 실패할 것이라 예상되는 값

        assertThatThrownBy(() -> new PurchaseAmount(inputAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 0보다 커야 합니다.");
    }

    @DisplayName("구입 금액으로 구매할 로또 개수를 계산한다.")
    @Test
    void calculateTicketCount() {

        PurchaseAmount purchaseAmount = new PurchaseAmount(8000); // 8,000원

        int ticketCount = purchaseAmount.calculateTicketCount(); // "몇 장 살 수 있어?"

        assertThat(ticketCount).isEqualTo(8); // "8장이요."
    }
}
