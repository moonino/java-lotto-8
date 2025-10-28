package lotto;

import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    // 6개의 로또 번호가 담긴 불변 리스트
    public List<Integer> getNumbers() {
        //  일단은 numbers를 그대로 반환합니다.)

        // 안전을 위해 불변 리스트로 반환
        return java.util.Collections.unmodifiableList(numbers);

        // TODO: 추가 기능 구현
    }
}
