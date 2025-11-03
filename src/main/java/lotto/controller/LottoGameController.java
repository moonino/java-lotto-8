package lotto.controller;

import lotto.Lotto;
import lotto.LottoMachine;
import lotto.LottoStatistics;
import lotto.PurchaseAmount;
import lotto.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

// 게임의 전체 흐름을 제어하는 컨트롤러
public class LottoGameController {

    // "하드 코딩 금지"
    private static final String ERROR_PREFIX = "[ERROR]";

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoMachine lottoMachine;

    public LottoGameController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.lottoMachine = new LottoMachine();
    }

    public void run() {
        PurchaseAmount purchaseAmount = getPurchaseAmountWithRetry();

        List<Lotto> lottos = buyLottos(purchaseAmount);

        WinningLotto winningLotto = getWinningLottoWithRetry(lottos);

        calculateAndPrintResults(lottos, winningLotto, purchaseAmount);
    }

    // 1. 구입 금액 입력 (재시도 로직 포함)
    private PurchaseAmount getPurchaseAmountWithRetry() {
        return retryUntilSuccess(() -> {
            String input = inputView.readPurchaseAmount();
            int amount = validateAndParseInt(input, "[ERROR] 구입 금액은 숫자여야 합니다.");
            return new PurchaseAmount(amount);
        });
    }

    // 2. 로또 구매 및 출력
    private List<Lotto> buyLottos(PurchaseAmount purchaseAmount) {
        List<Lotto> lottos = lottoMachine.generateLottos(purchaseAmount);

        outputView.printPurchaseCount(lottos.size());
        outputView.printLottos(lottos);

        return lottos;
    }

    // 3. 당첨 번호 입력 (재시도 로직 포함)
    private WinningLotto getWinningLottoWithRetry(List<Lotto> lottos) {
        Lotto winningMainLotto = retryUntilSuccess(() -> {
            String input = inputView.readWinningNumbers();
            List<Integer> numbers = parseWinningNumbers(input);
            return new Lotto(numbers);
        });

        return retryUntilSuccess(() -> {
            String input = inputView.readBonusNumber();
            int bonus = validateAndParseInt(input, "[ERROR] 보너스 번호는 숫자여야 합니다.");
            return new WinningLotto(winningMainLotto, bonus);
        });
    }

    // 4. 결과 계산 및 출력
    private void calculateAndPrintResults(List<Lotto> lottos, WinningLotto winningLotto, PurchaseAmount purchaseAmount) {
        LottoStatistics stats = new LottoStatistics(
                lottos.stream().map(winningLotto::match).toList(), // (채점)
                purchaseAmount // (원본 금액)
        );
        outputView.printResults(stats); // (통계 및 수익률 출력)
    }

    // "숫자가 아닌 경우" 예외 처리
    private int validateAndParseInt(String input, String errorMessage) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    private List<Integer> parseWinningNumbers(String input) {
        try {
            return List.of(input.split(",")).stream()
                    .map(String::trim)
                    .map(s -> validateAndParseInt(s, "[ERROR] 당첨 번호는 숫자여야 합니다."))
                    .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호 입력 형식이 올바르지 않습니다.");
        }
    }

    // "그 부분부터 입력을 다시 받는다" -> 핵심 재시도 로직
    private <T> T retryUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}