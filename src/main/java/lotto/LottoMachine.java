package lotto; // (사용자님의 패키지 구조)

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoMachine {

    private static final int LOTTO_NUMBER_MIN = 1;
    private static final int LOTTO_NUMBER_MAX = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

    /**
     * 구매 금액에 해당하는 만큼 로또를 자동으로 발행합니다.
     * @return 발행된 로또 목록 (List<Lotto>)
     */
    public List<Lotto> generateLottos(PurchaseAmount purchaseAmount) {

        int count = purchaseAmount.calculateTicketCount();

        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            lottos.add(generateSingleLotto());
        }

        return lottos;
    }

    /**
     * 로또 1장을 발행합니다.
     * @return 오름차순 정렬된 로또 1장
     */
    private Lotto generateSingleLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                LOTTO_NUMBER_MIN,
                LOTTO_NUMBER_MAX,
                LOTTO_NUMBER_COUNT
        );

        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);

        return new Lotto(sortedNumbers);
    }
}