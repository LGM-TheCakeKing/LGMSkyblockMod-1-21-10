package ml.thecakeking.lgmmods.chattrigger;


import ml.thecakeking.lgmmods.LGMSkyblockMod;
import ml.thecakeking.lgmmods.Model.ChatTrigger;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TriggerConfigScreen extends Screen {


    private List<ChatTrigger> list;
    private TextFieldWidget inputPatternField;
    private TextFieldWidget inputResponseField;

    public TriggerConfigScreen() {
        super(Text.literal("Trigger Configuration"));
    }

    @Override
    protected void init() {
        // 1. Initialize the Scrollable List
        // (x, y, width, height, itemHeight)
        //this.list = new ChatTrigger("", "");
        //this.addSelectableChild(this.list);

        // Initialize the Input Fields for new chat triggers (Bottom of the screen)
        this.inputPatternField = new TextFieldWidget(this.textRenderer, this.width / 4 - 1, this.height - 32, this.width / 4, 20, Text.literal("New Trigger"));
        this.inputPatternField.setTooltip(Tooltip.of(Text.literal("Some text here something something Pattern")));
        this.addSelectableChild(this.inputPatternField);

        this.inputResponseField = new TextFieldWidget(this.textRenderer, this.width / 2 + 1 , this.height - 32, this.width / 4, 20, Text.literal("New Trigger"));
        this.inputResponseField.setTooltip(Tooltip.of(Text.literal("Write the response/action here")));
        this.addSelectableChild(this.inputResponseField);

        ButtonWidget button = ButtonWidget.builder(Text.literal("Add"), addTriggerButton ->
                {
                    if (!inputPatternField.getText().isEmpty() && !inputResponseField.getText().isEmpty())
                    {
                        ChatTrigger trigger = new ChatTrigger(inputPatternField.getText(), inputResponseField.getText());
                        TriggerManager.triggers.add(trigger);
                        LGMSkyblockMod.LOGGER.info("Adding trigger: inputField: " + inputPatternField.getText().toLowerCase() + " Action: " + inputResponseField.getText());
                        inputPatternField.setText("");
                        inputResponseField.setText("");
                        TriggerConfigHandler.save();
                    }
                })
                .dimensions((int) (this.width * 0.75+4), this.height - 32, 48, 20)
                .tooltip(Tooltip.of(Text.literal("Add trigger")))
                .build();
        this.addDrawableChild(button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.inputPatternField.render(context, mouseX, mouseY, delta);
        this.inputResponseField.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);
    }
}
