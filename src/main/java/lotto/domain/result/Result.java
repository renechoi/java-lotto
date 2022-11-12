package lotto.domain.result;

import lotto.domain.player.PurchaseAmount;
import lotto.ui.ConsoleOutput;

import java.util.List;

import static lotto.domain.result.MatchCalculator.*;

public class Result {

	public Result(List<Integer> winningNumber, int bonusNumber){
		MatchCalculator.findMatch(winningNumber, bonusNumber);
	}

	public void calculateLottoRank(){
		ConsoleOutput.PrintResultMessage(fiveMatchAndBonusMatch, countingMatches);
	}

	public void calculateTotalProfit(PurchaseAmount purchaseAmount){
		TotalProfit.countPrize();
		ConsoleOutput.PrintProfitMessage(TotalProfit.computeProfit(purchaseAmount.toMoney()));
	}
}
