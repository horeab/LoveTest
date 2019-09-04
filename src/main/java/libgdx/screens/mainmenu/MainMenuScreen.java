package libgdx.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import libgdx.controls.MyTextField;
import libgdx.controls.animations.WordAnimation;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.textfield.MyTextFieldBuilder;
import libgdx.game.ScreenManager;
import libgdx.implementations.skelgame.SkelGameLabel;
import libgdx.resources.FontManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.screen.AbstractScreen;
import libgdx.screen.PercentCounter;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.Utils;
import libgdx.utils.model.FontColor;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class MainMenuScreen extends AbstractScreen<ScreenManager> {

    @Override
    public void buildStage() {
        createAllTable();
    }

    private void createAllTable() {
        TextField.TextFieldFilter textFieldFilter = new TextField.TextFieldFilter() {
            public boolean acceptChar(TextField textField, char c) {
                String s = String.valueOf(c);
                return StringUtils.isAlpha(s) || s.equals(" ");
            }
        };
        float height = ScreenDimensionsManager.getScreenHeightValue(6);
        float width = ScreenDimensionsManager.getScreenWidthValue(60);
        MyTextField myNameTextField = new MyTextFieldBuilder().setHeight(height).setWidth(width).setTextFieldFilter(textFieldFilter).build();
        MyTextField partnerNameTextField = new MyTextFieldBuilder().setHeight(height).setWidth(width).setTextFieldFilter(textFieldFilter).build();

        Table infoContainer = new Table();

        final Table allTable = new Table();
        allTable.setFillParent(true);

        infoContainer.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText(StringUtils.capitalize(SkelGameLabel.lbl_your_name.getText())).build())).width(ScreenDimensionsManager.getScreenWidthValue(50)).row();
        float pad = MainDimen.vertical_general_margin.getDimen() * 2;
        infoContainer.add(myNameTextField).padBottom(pad).row();
        infoContainer.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText(StringUtils.capitalize(SkelGameLabel.lbl_partner_name.getText())).build())).width(ScreenDimensionsManager.getScreenWidthValue(50)).row();
        infoContainer.add(partnerNameTextField).padBottom(pad).row();

        final MyButton calculateBtn = new ButtonBuilder().setText(StringUtils.capitalize(SkelGameLabel.lbl_calculate.getText())).build();
        final MyButton newTestBtn = new ButtonBuilder().setText(StringUtils.capitalize(SkelGameLabel.lbl_new_test.getText())).build();
        newTestBtn.setVisible(false);
        MyWrappedLabel compLabel = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText(" ").build());
        compLabel.setVisible(false);
        newTestBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                allTable.addAction(Actions.sequence(Actions.fadeOut(0.6f), Utils.createRunnableAction(new Runnable() {
                    @Override
                    public void run() {
                        createAllTable();
                    }
                })));
            }
        });

        float duration = 0.3f;
        final PercentCounter percentCounter = new PercentCounter(getAbstractScreen()) {
            @Override
            public void executeAfterCountDownCounter() {
                Utils.fadeInActor(newTestBtn, duration * 5);
                Utils.fadeInActor(compLabel, duration);
            }
        };
        percentCounter.getDisplayLabel().setVisible(false);

        calculateBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (myNameTextField.getText().length() < 2 || partnerNameTextField.getText().length() < 2) {
                    new WordAnimation().animateShowFadeOut(StringUtils.capitalize(SkelGameLabel.lbl_write_name.getText()), FontColor.RED);
                } else {
                    myNameTextField.getTextField().setDisabled(true);
                    partnerNameTextField.getTextField().setDisabled(true);
                    float matchPercent = getMatchPercent(myNameTextField, partnerNameTextField);
                    compLabel.setText(getCompText(matchPercent));
                    compLabel.setFontScale(FontManager.getBigFontDim());
                    calculateBtn.addAction(Actions.fadeOut(duration));
                    Utils.fadeInActor(percentCounter.getDisplayLabel(), duration);
                    percentCounter.start(matchPercent);
                }
            }
        });
        infoContainer.add(calculateBtn).padBottom(pad).row();
        infoContainer.add();

        allTable.add(infoContainer).height(ScreenDimensionsManager.getScreenHeightValue(40)).row();
        allTable.add(compLabel).height(ScreenDimensionsManager.getScreenHeightValue(15)).row();
        allTable.add(percentCounter.getDisplayLabel()).height(ScreenDimensionsManager.getScreenHeightValue(15)).row();
        allTable.add(newTestBtn).height(ScreenDimensionsManager.getScreenHeightValue(15));

        addActor(allTable);
    }

    private float getMatchPercent(MyTextField myNameTextField, MyTextField partnerNameTextField) {
        float percent = (float) StringUtils.getJaroWinklerDistance(myNameTextField.getText(), partnerNameTextField.getText()) * 100;
        return percent + new Random().nextInt(30);
    }

    private String getCompText(float percent) {
        String text;
        if (percent < 10) {
            text = SkelGameLabel.comp_0.getText();
        } else if (percent < 20) {
            text = SkelGameLabel.comp_1.getText();
        } else if (percent < 30) {
            text = SkelGameLabel.comp_2.getText();
        } else if (percent < 40) {
            text = SkelGameLabel.comp_3.getText();
        } else if (percent < 50) {
            text = SkelGameLabel.comp_4.getText();
        } else if (percent < 60) {
            text = SkelGameLabel.comp_5.getText();
        } else if (percent < 70) {
            text = SkelGameLabel.comp_6.getText();
        } else if (percent < 80) {
            text = SkelGameLabel.comp_7.getText();
        } else if (percent < 90) {
            text = SkelGameLabel.comp_8.getText();
        } else {
            text = SkelGameLabel.comp_9.getText();
        }
        return StringUtils.capitalize(text);
    }

    @Override
    public void onBackKeyPress() {
        Gdx.app.exit();
    }
}
