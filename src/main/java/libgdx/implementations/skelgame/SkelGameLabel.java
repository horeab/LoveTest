package libgdx.implementations.skelgame;

import libgdx.game.Game;
import libgdx.resources.gamelabel.GameLabelUtils;
import libgdx.resources.gamelabel.SpecificPropertiesUtils;

public enum SkelGameLabel implements libgdx.resources.gamelabel.GameLabel {

    comp_0,
    comp_1,
    comp_2,
    comp_3,
    comp_4,
    comp_5,
    comp_6,
    comp_7,
    comp_8,
    comp_9,

    lbl_your_name,
    lbl_partner_name,
    lbl_write_name,

    lbl_calculate,
    lbl_new_test;

    @Override
    public String getText(Object... params) {
        String language = Game.getInstance().getAppInfoService().getLanguage();
        return GameLabelUtils.getText(getKey(), language, GameLabelUtils.getLabelRes(language).getPath(), params);
    }

    @Override
    public String getKey() {
        return name().toLowerCase();
    }
}
