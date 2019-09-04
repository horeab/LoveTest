package libgdx.game;

import libgdx.screen.AbstractScreenManager;
import libgdx.screens.ScreenTypeEnum;

public class ScreenManager extends AbstractScreenManager {

    @Override
    public void showMainScreen() {
        showScreen(ScreenTypeEnum.MAIN_MENU_SCREEN, null);
//        showScreen(ScreenTypeEnum.GAME_SCREEN, LettersCampaignLevelEnum.LEVEL_0_0);
//        showCampaignScreen();
    }

}
