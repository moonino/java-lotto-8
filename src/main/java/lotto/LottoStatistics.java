package lotto;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 당첨 결과(Rank) 목록과 원본 구매 금액을 기반으로
 * 당첨 통계 및 수익률을 계산하는 객체입니다.
 */
public class LottoStatistics {

    private final Map<Rank, Long> rankCounts;
    private final long totalPrizeMoney;
    private final int purchaseAmount;

    /**
     * 당첨 통계 객체를 생성합니다.
     * 생성 시점에 모든 집계와 계산을 완료합니다.
     */
    public LottoStatistics(List<Rank> results, PurchaseAmount purchaseAmount) {
        // 1. 통계 맵을 0으로 초기화
        this.rankCounts = initializeRankCounts();

        // 2. 결과 집계 (MISS는 집계에서 제외)
        processResults(results);

        // 3. 총 당첨금 계산 (한 번만 계산)
        this.totalPrizeMoney = calculateTotalPrizeMoney();

        // 4. 원본 구매 금액 저장
        this.purchaseAmount = purchaseAmount.getAmount();
    }

    //  0개인 등수도 맵에 0L로 존재해야 함.
    private Map<Rank, Long> initializeRankCounts() {
        Map<Rank, Long> counts = new EnumMap<>(Rank.class);

        // Rank.MISS는 통계에 포함하지 않으므로 제외
        for (Rank rank : Rank.values()) {
            if (rank != Rank.MISS) {
                counts.put(rank, 0L);
            }
        }
        return counts;
    }

    // 1. 집계
    private void processResults(List<Rank> results) {
        for (Rank rank : results) {
            if (rank != Rank.MISS) {
                rankCounts.put(rank, rankCounts.get(rank) + 1);
            }
        }
    }

    // 2. 총 당첨금
    private long calculateTotalPrizeMoney() {
        long total = 0L;
        // "else 금지" -> for문 사용
        for (Map.Entry<Rank, Long> entry : rankCounts.entrySet()) {
            long prize = entry.getKey().getPrizeMoney();
            long count = entry.getValue();
            total += prize * count;
        }
        return total;
    }


    // 각 등수별 당첨 개수를 반환합니다.

    public long getCount(Rank rank) {
        // MISS가 들어올 경우 0을 반환
        return rankCounts.getOrDefault(rank, 0L);
    }

    // 총 당첨금을 반환합니다.

    public long getTotalPrizeMoney() {
        return totalPrizeMoney;
    }

    // 총 수익률을 계산합니다 (소수점 둘째 자리에서 반올림).

    public double getProfitRate() {

        // 수익률 = (총 당첨금 / 구매 금액) * 100
        if (purchaseAmount == 0) {
            return 0.0; // 0으로 나누기 방지
        }

        double rate = ((double) totalPrizeMoney / purchaseAmount) * 100.0;

        // 소수점 둘째 자리에서 반올림
        return Math.round(rate * 10.0) / 10.0;
    }
}