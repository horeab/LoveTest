package libgdx.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import libgdx.controls.MyTextField;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.textfield.MyTextFieldBuilder;
import libgdx.game.ScreenManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.screen.AbstractScreen;
import libgdx.screen.PercentCounter;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.Utils;
import org.apache.commons.lang3.StringUtils;

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

        infoContainer.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText("Your name").build())).width(ScreenDimensionsManager.getScreenWidthValue(50)).row();
        float pad = MainDimen.vertical_general_margin.getDimen() * 2;
        infoContainer.add(myNameTextField).padBottom(pad).row();
        infoContainer.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText("Partner name").build())).width(ScreenDimensionsManager.getScreenWidthValue(50)).row();
        infoContainer.add(partnerNameTextField).padBottom(pad).row();

        final MyButton calculateBtn = new ButtonBuilder().setText("Calculate").build();
        final MyButton newTestBtn = new ButtonBuilder().setText("New test").build();
        newTestBtn.setVisible(false);
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
                Utils.fadeInActor(newTestBtn, duration);
            }
        };
        percentCounter.getDisplayLabel().setVisible(false);

        calculateBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                calculateBtn.addAction(Actions.fadeOut(duration));
                Utils.fadeInActor(percentCounter.getDisplayLabel(), duration);
                percentCounter.start((float) StringUtils.getJaroWinklerDistance(myNameTextField.getText(), partnerNameTextField.getText()) * 100);
            }
        });
        infoContainer.add(calculateBtn).padBottom(pad).row();
        infoContainer.add();

        allTable.add(infoContainer).height(ScreenDimensionsManager.getScreenHeightValue(40)).row();
        allTable.add(percentCounter.getDisplayLabel()).height(ScreenDimensionsManager.getScreenHeightValue(15)).row();
        allTable.add(newTestBtn).height(ScreenDimensionsManager.getScreenHeightValue(15));

        addActor(allTable);
    }

    @Override
    public void onBackKeyPress() {
        Gdx.app.exit();
    }
}
