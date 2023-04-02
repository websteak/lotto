package org.example.domain.result;

import org.example.domain.lotto.Lotto;
import org.example.domain.lotto.LottoList;
import org.example.domain.lotto.LottoNumber;
import org.example.domain.winning_number.BonusBallNumber;
import org.example.domain.winning_number.WinningNumbers;
import org.example.util.FilteringNumberOfRankUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Result {
    private Map<BigDecimal, BigDecimal> rankList;
    
    
    public Result(LottoList lottoList, Lotto winningNumbers, LottoNumber bonusBallNumber) {
        this.rankList = createResult(lottoList, winningNumbers, bonusBallNumber);
    }
    
    private Map<BigDecimal, BigDecimal> createResult(LottoList lottoList, Lotto winningNumbers, LottoNumber bonusBallNumber) {
        List<Lotto> lottos = lottoList.getLottoList();
        List<Integer> winning = winningNumbers.getLottoNumbers();
        int bonusBall = bonusBallNumber.getNumber();
        List<Integer> resultList = new ArrayList<>();
        
        for (Lotto lotto : lottos) {
            List<Integer> resultNumbers = new ArrayList<>();
            
            resultNumbers.addAll(lotto.getLottoNumbers());
            resultNumbers.addAll(winning);
            
            int numberOfDuplicate = findDuplicateNumber(resultNumbers).size();
            int rank = checkedRank(numberOfDuplicate, bonusBall, lotto.getLottoNumbers());
    
            resultList.add(rank);
        }
    
        Map<BigDecimal, BigDecimal> rankList = FilteringNumberOfRankUtil.FilteringRank(resultList);
        
        return rankList;
    }
    
    private List<Integer> findDuplicateNumber(List<Integer> resultNumbers) {
        Set<Integer> duplicateNumber = new HashSet<>();
    
        return resultNumbers.stream()
            .filter(number -> !duplicateNumber.add(number))
            .collect(Collectors.toList());
    }
    
    private int checkedRank(int numberOfDuplicate, int bonusBallNumber, List<Integer> lotto) {
        if (numberOfDuplicate == 5 && lotto.contains(bonusBallNumber)) {
            return 7;
        }
    
        return numberOfDuplicate;
    }
    
    public Map<BigDecimal, BigDecimal> getResult() {
        return rankList;
    }
}
