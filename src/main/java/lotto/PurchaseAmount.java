package lotto;

public class PurchaseAmount {

    private static final int LOTTO_PRICE_UNIT = 1_000;
    private static final int MIN_PURCHASE_AMOUNT = 0;
    private static final String ERROR_INVALID_UNIT = "[ERROR] 구입 금액은 1,000원 단위여야 합니다.";
    private static final String ERROR_LESS_THAN_MIN = "[ERROR] 구입 금액은 0보다 커야 합니다.";


    private final int amount;

    public PurchaseAmount(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(int amount) {

        if (amount <= MIN_PURCHASE_AMOUNT) {
            throw new IllegalArgumentException(ERROR_LESS_THAN_MIN);
        }
        if (amount % LOTTO_PRICE_UNIT != 0) {
            throw new IllegalArgumentException(ERROR_INVALID_UNIT);
        }
    }

}
