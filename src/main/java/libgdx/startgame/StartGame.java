package libgdx.startgame;

import libgdx.constants.GameIdEnum;
import libgdx.constants.Language;
import libgdx.implementations.skelgame.SkelGame;
import libgdx.utils.startgame.test.DefaultAppInfoService;
import libgdx.utils.startgame.test.DefaultBillingService;
import libgdx.utils.startgame.test.DefaultFacebookService;

public class StartGame {

    public static void main(String[] args) {
        SkelGame game = new SkelGame(
                new DefaultFacebookService(),
                new DefaultBillingService(),
                new DefaultAppInfoService() {
                    @Override
                    public String getGameIdPrefix() {
                        return GameIdEnum.lovetest.name();
                    }

                    @Override
                    public float gameScreenTopMargin() {
                        return super.gameScreenTopMargin();
                    }

                    @Override
                    public String getAppName() {
                        return "Crossword Garden";
                    }

                    @Override
                    public String getLanguage() {
                        return Language.pl.name();
                    }
                });
        libgdx.utils.startgame.StartGame.main(game, args);
    }
}
