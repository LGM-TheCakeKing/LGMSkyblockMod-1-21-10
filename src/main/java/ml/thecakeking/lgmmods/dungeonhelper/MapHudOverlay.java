package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.MapRenderState;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;

public class MapHudOverlay implements HudRenderCallback { //TODO Not working well, cause visual glitch //TODO 2 When bow is in main hand slot 8 is turn into feathers, HOW!?, //TODO 3 scale the map, and remember it

    // Persistent state to avoid object allocation every frame
    private final MapRenderState mapRenderState = new MapRenderState();

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        // 1. Get the item in the main hand and Check if it is a Filled Map
        ItemStack stack = client.player.getInventory().getStack(8);
        if (!(stack.getItem() instanceof FilledMapItem)) return;

        MapIdComponent mapId = stack.get(DataComponentTypes.MAP_ID);
        if (mapId == null) return;

        MapState mapState = client.world.getMapState(mapId);
        if (mapState == null) return;

        renderMap(drawContext, client, mapId, mapState);
    }


    private void renderMap(DrawContext context, MinecraftClient client, MapIdComponent mapId, MapState mapState) {
        // 1. Update the state object with current map data
        // This is mandatory in 1.21.x
        client.getMapRenderer().update(mapId, mapState, this.mapRenderState);

        context.getMatrices().pushMatrix();

        // Position the map on your HUD
        context.getMatrices().translate(10.0f, 10.0f);
        context.getMatrices().scale(1, 1);

        var queue = client.gameRenderer.getEntityRenderCommandQueue();
        MatrixStack matrixStack = new MatrixStack();

        var matrix2d = context.getMatrices();

// 3. Apply the 2D transformation to the 3D stack
// Note: We map the 2D coordinates to a 4x4 identity
        matrixStack.peek().getPositionMatrix().set(
                matrix2d.m00, matrix2d.m01, 0.0F, matrix2d.m20,
                matrix2d.m10, matrix2d.m11, 0.0F, matrix2d.m21,
                0.0F,         0.0F,         1.0F, 0.0F,
                0.0F,         0.0F,         0.0F, 1.0F
        );

        // 2. Draw using the State-based method
        // In 1.21.10, the DrawContext can provide the VertexConsumerProvider directly
        // which handles the 'OrderedRenderCommandQueue' logic internally.
        client.getMapRenderer().draw(
                this.mapRenderState,
                matrixStack,
                queue,
                false, // hideIcons
                0xF000F0 // Light (Full Bright)
        );

        // 3. Force the draw context to flush its current commands
        context.drawMap(mapRenderState);

        context.getMatrices().popMatrix();
    }
}
