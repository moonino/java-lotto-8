package lotto; // (사용자님의 패키지 구조)

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;

class LottoMachineTest extends NsTest { // NsTest를 상속받아 랜덤 고정 기능을 사용합니다.

    @DisplayName("구매 금액만큼 로또를 발행한다.")
    @Test
    void generateLottosByPurchaseAmount() {
        // given
        PurchaseAmount purchaseAmount = new PurchaseAmount(3000); // 3장 구매
        LottoMachine lottoMachine = new LottoMachine(); // (RED: 아직 없는 클래스)

        // when & then
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    // when: 로또 발행 요청
                    List<Lotto> lottos = lottoMachine.generateLottos(purchaseAmount); // (RED: 아직 없는 메서드)

                    // then: 3장이 생성되었는지,
                    //       그리고 우리가 '고정한' 랜덤 값이 정확히 들어갔는지 확인
                    assertThat(lottos).hasSize(3);

                    assertThat(lottos.get(0).getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
                    assertThat(lottos.get(1).getNumbers()).containsExactly(7, 8, 9, 10, 11, 12);
                    assertThat(lottos.get(2).getNumbers()).containsExactly(13, 14, 15, 16, 17, 18);
                },
                // --- 'Randoms.pickUniqueNumbersInRange()'가 호출될 때마다 반환할 값들을 순서대로 지정 ---
                List.of(1, 2, 3, 4, 5, 6),   // 1번째 호출 시
                List.of(7, 8, 9, 10, 11, 12), // 2번째 호출 시
                List.of(13, 14, 15, 16, 17, 18) // 3번째 호출 시
        );
    }

    @Override
    protected void runMain() {

    }
}