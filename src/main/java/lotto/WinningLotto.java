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

    /**
     * 당첨 로또를 생성합니다.
     */
    public WinningLotto(Lotto mainLotto, int bonusNumber) {
        validate(mainLotto, bonusNumber); // "함수 분리" 요구 사항
        this.mainLotto = mainLotto;
        this.bonusNumber = bonusNumber;
    }

    /**
     * 보너스 번호의 유효성을 검증합니다.
     */
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

}