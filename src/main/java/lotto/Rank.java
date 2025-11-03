package lotto; //


 //로또 당첨 등수를 나타내는 Enum.

import java.text.NumberFormat;
import java.util.Locale;

public enum Rank {

    FIRST(6, 2_000_000_000L, false),
    SECOND(5, 30_000_000L, true),
    THIRD(5, 1_500_000L, false),
    FOURTH(4, 50_000L, false),
    FIFTH(3, 5_000L, false),
    MISS(0, 0L, false);

    private final int matchCount;
    private final long prizeMoney;
    private final String message;

    // Rank Enum 생성자
    Rank(int matchCount, long prizeMoney, boolean needsBonus) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
        this.message = createMessage(matchCount, prizeMoney, needsBonus);
    }

    // 출력 메소드

    private String createMessage(int count, long prize, boolean needsBonus) {
        if (this == MISS) {
            return "";
        }

        String prizeFormatted = NumberFormat.getInstance(Locale.KOREA).format(prize);

        // --- (5) this == SECOND 대신 needsBonus로 비교! ---
        if (needsBonus) {
            return String.format("%d개 일치, 보너스 볼 일치 (%s원)", count, prizeFormatted);
        }
        return String.format("%d개 일치 (%s원)", count, prizeFormatted);
    }

    /**
     * 일치 개수와 보너스 일치 여부를 기반으로 당첨 등수(Rank)를 반환합니다.
     * @return 해당하는 Rank (FIRST ~ MISS)
     */
    public static Rank find(int matchCount, boolean bonusMatch) {
        // 1등: 6개 일치
        if (matchCount == FIRST.matchCount) { // 6
            return FIRST;
        }

        // 2등: 5개 일치 + 보너스 일치
        if (matchCount == SECOND.matchCount && bonusMatch) { // 5
            return SECOND;
        }

        // 3등: 5개 일치 (보너스 X)
        if (matchCount == THIRD.matchCount) { // 5
            return THIRD;
        }

        // 4등: 4개 일치
        if (matchCount == FOURTH.matchCount) { // 4
            return FOURTH;
        }

        // 5등: 3개 일치
        if (matchCount == FIFTH.matchCount) { // 3
            return FIFTH;
        }

        // 꽝
        return MISS;
    }

    //이 등수의 당첨금을 반환합니다.

    public long getPrizeMoney() {
        return prizeMoney;
    }
    public String getMessage() {
        return message;
    }
}