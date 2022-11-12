package lotto.domain;

import lotto.Calculation;
import lotto.LottoIssuer;
import lotto.domain.player.Player;
import lotto.domain.player.PurchaseAmount;

import java.util.stream.Stream;

public class Game {

	public void run() {
		Player player = new Player();
		long purchaseAmount = new PurchaseAmount(player.receivePurchaseAmount()).toConvert();
		int purchaseCount = new PurchaseCounter().countLotto(purchaseAmount);

		LottoIssuer lottoIssuer = new LottoIssuer(purchaseCount);
		Stream<Integer> lottoNumbers = player.receiveLottoNumber();
		player.receiveBonusNumber(lottoNumbers);

		Calculation.findMatch();
		Calculation.calculateResult();
		Calculation.calculateProfit(purchaseAmount);
	}
}
