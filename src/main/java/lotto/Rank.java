package lotto; //


 //로또 당첨 등수를 나타내는 Enum.

public enum Rank {

    // Enum 상수 정의 (일치 개수, 당첨금)
    FIRST(6, 2_000_000_000L),
    SECOND(5, 30_000_000L), // 5개 + 보너스
    THIRD(5, 1_500_000L),  // 5개 (보너스X)
    FOURTH(4, 50_000L),
    FIFTH(3, 5_000L),
    MISS(0, 0L); // 꽝 (0, 1, 2개 일치)

    private final int matchCount;
    private final long prizeMoney;

    /**
     * Rank Enum 생성자
     * @param matchCount    해당 등수의 기준이 되는 일치 개수 (MISS는 0으로 통일)
     * @param prizeMoney    해당 등수의 당첨금
     */
    Rank(int matchCount, long prizeMoney) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
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
}