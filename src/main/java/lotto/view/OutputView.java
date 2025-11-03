package lotto.view;

import lotto.Lotto;
import lotto.LottoStatistics;
import lotto.Rank;

import java.util.List;

// 게임의 모든 출력을 담당하는 View
public class OutputView {

    private static final String PURCHASE_COUNT_MESSAGE = "%d개를 구매했습니다.";
    private static final String STATISTICS_HEADER = "\n당첨 통계\n---";
    private static final String PROFIT_RATE_MESSAGE = "총 수익률은 %.1f%%입니다.";
    private static final String LOTTO_NUMBER_FORMAT = "[%s]";
    private static final String DELIMITER = ", ";

    public void printPurchaseCount(int count) {
        System.out.printf((PURCHASE_COUNT_MESSAGE) + "%n", count);
    }

    public void printLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            printSingleLotto(lotto);
        }
    }

    private void printSingleLotto(Lotto lotto) {
        List<String> numbersAsStrings = lotto.getNumbers().stream()
                .map(String::valueOf)
                .toList();

        String joinedNumbers = String.join(DELIMITER, numbersAsStrings);
        System.out.printf((LOTTO_NUMBER_FORMAT) + "%n", joinedNumbers);
    }

    // "당첨 통계"와 "수익률" 출력을 한 번에 처리
    public void printResults(LottoStatistics stats) {
        System.out.println(STATISTICS_HEADER);

        System.out.printf("%s - %d개%n", Rank.FIFTH.getMessage(), stats.getCount(Rank.FIFTH));
        System.out.printf("%s - %d개%n", Rank.FOURTH.getMessage(), stats.getCount(Rank.FOURTH));
        System.out.printf("%s - %d개%n", Rank.THIRD.getMessage(), stats.getCount(Rank.THIRD));
        System.out.printf("%s - %d개%n", Rank.SECOND.getMessage(), stats.getCount(Rank.SECOND));
        System.out.printf("%s - %d개%n", Rank.FIRST.getMessage(), stats.getCount(Rank.FIRST));

        System.out.printf((PROFIT_RATE_MESSAGE) + "%n", stats.getProfitRate());
    }
}