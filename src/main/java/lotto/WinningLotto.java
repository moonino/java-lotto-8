package lotto;

import java.util.List;

/**
 * 1등 당첨 번호(6개)와 보너스 번호(1개)를 가지는
 * '당첨 로또' 객체입니다.
 */
public class WinningLotto {

    private static final int LOTTO_NUMBER_MIN = 1;
    private static final int LOTTO_NUMBER_MAX = 45;

    private final Lotto mainLotto;     // 당첨 번호 6개
    private final int bonusNumber; // 보너스 번호 1개

    //당첨 로또를 생성합니다.

    public WinningLotto(Lotto mainLotto, int bonusNumber) {
        validate(mainLotto, bonusNumber); // "함수 분리" 요구 사항
        this.mainLotto = mainLotto;
        this.bonusNumber = bonusNumber;
    }

    //보너스 번호의 유효성을 검증합니다.

    private void validate(Lotto mainLotto, int bonusNumber) {

        // 1. 보너스 번호 범위 검증
        if (bonusNumber < LOTTO_NUMBER_MIN || bonusNumber > LOTTO_NUMBER_MAX) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }

        // 2. 보너스 번호 중복 검증
        List<Integer> mainNumbers = mainLotto.getNumbers();
        if (mainNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    // 사용자의 로또와 당첨 번호를 비교하여 당첨 등수(Rank)를 반환합니다.

    public Rank match(Lotto userLotto) {

        // 1. 6개 당첨 번호 중 몇 개나 일치하는지 계산
        int matchCount = calculateMatchCount(userLotto);

        // 2. 보너스 번호가 일치하는지 계산
        boolean bonusMatch = checkBonusMatch(userLotto);

        // 3. Rank Enum에게 최종 등수 판별을 위임
        return Rank.find(matchCount, bonusMatch);
    }

    // 사용자의 로또가 6개의 당첨 번호와 몇 개 일치하는지 계산합니다.

    private int calculateMatchCount(Lotto userLotto) {
        List<Integer> userNumbers = userLotto.getNumbers();
        List<Integer> mainNumbers = mainLotto.getNumbers();

        // (indent 2)
        return (int) userNumbers.stream()
                .filter(mainNumbers::contains)
                .count();
    }

    // 사용자의 로또가 보너스 번호를 포함하는지 확인합니다.
    private boolean checkBonusMatch(Lotto userLotto) {
        return userLotto.getNumbers().contains(this.bonusNumber);
    }
}

