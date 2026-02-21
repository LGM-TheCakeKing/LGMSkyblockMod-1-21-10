package ml.thecakeking.lgmmods;

import ml.thecakeking.lgmmods.Model.ChatTrigger;
import ml.thecakeking.lgmmods.chattrigger.TriggerConfigHandler;
import ml.thecakeking.lgmmods.chattrigger.TriggerManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen
{
    protected ConfigScreen(Text title)  {
        super(Text.literal("Configuration"));
    }

    @Override
    protected void init() {
        // 1. Initialize the Scrollable List
        // (x, y, width, height, itemHeight)
        //this.list = new ChatTrigger("", "");
        //this.addSelectableChild(this.list);

        // Initialize the Input Fields for new chat triggers (Bottom of the screen)
        //this.inputPatternField = new TextFieldWidget(this.textRenderer, this.width / 4 - 1, this.height - 32, this.width / 4, 20, Text.literal("New Trigger"));
        //this.inputPatternField.setTooltip(Tooltip.of(Text.literal("Some text here something something Pattern")));
        //this.addSelectableChild(this.inputPatternField);

        //this.inputResponseField = new TextFieldWidget(this.textRenderer, this.width / 2 + 1 , this.height - 32, this.width / 4, 20, Text.literal("New Trigger"));
        //this.inputResponseField.setTooltip(Tooltip.of(Text.literal("Write the response/action here")));
        //this.addSelectableChild(this.inputResponseField);

        //ButtonWidget button = ButtonWidget.builder(Text.literal("Add"), addTriggerButton ->
          //      {
            //        if (!inputPatternField.getText().isEmpty() && !inputResponseField.getText().isEmpty())
              //      {
                //        ChatTrigger trigger = new ChatTrigger(inputPatternField.getText(), inputResponseField.getText());
                  //      TriggerManager.triggers.add(trigger);
                    //    LGMSkyblockMod.LOGGER.info("Adding trigger: inputField: " + inputPatternField.getText().toLowerCase() + " Action: " + inputResponseField.getText());
                      //  inputPatternField.setText("");
                        //inputResponseField.setText("");
                        //TriggerConfigHandler.save();
                    //}
                //})
                //.dimensions((int) (this.width * 0.75+4), this.height - 32, 48, 20)
                //.tooltip(Tooltip.of(Text.literal("Add trigger")))
                //.build();
        //this.addDrawableChild(button);
    }
}
