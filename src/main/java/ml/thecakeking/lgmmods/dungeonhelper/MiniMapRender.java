package ml.thecakeking.lgmmods.dungeonhelper;

import ml.thecakeking.lgmmods.LGMSkyblockMod;
import ml.thecakeking.lgmmods.Model.WorldAreasNames;
import ml.thecakeking.lgmmods.WorldTransferrerHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.MapRenderState;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import org.joml.Matrix3x2fStack;


public class MiniMapRender
{
    public static float mapScale = 1.5f;
    private static MapRenderState mapRenderState = new MapRenderState();
    private static MapState mapState;
    private static MapIdComponent mapId;
    public static void RenderMiniMapHud(DrawContext context, RenderTickCounter renderTickCounter)
    {
        if (!WorldTransferrerHandler.GetCurrentWorld().equals(WorldAreasNames.Dungeon)) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        ItemStack stack = client.player.getInventory().getStack(8);
        if ((stack.getItem() instanceof FilledMapItem))
        {
            mapId = stack.get(DataComponentTypes.MAP_ID);
            if (mapId == null) return;

            mapState = client.world.getMapState(mapId);
        }

        if (mapId == null || mapState == null) return;

        renderMiniMap(context, client, mapId, mapState);
    }

    private static void renderMiniMap(DrawContext context, MinecraftClient client, MapIdComponent mapId, MapState mapState)
    {
        mapState.getDecorations().forEach(x -> {
            //LGMSkyblockMod.LOGGER.info("Rendering MiniMap for map decorations: " + x.getAssetId());
        });
        mapRenderState.decorations.forEach(x -> {
            //LGMSkyblockMod.LOGGER.info("[MiniMapRender] mapRenderState decorations: " + x.toString());
        });
        client.getMapRenderer().update(mapId, mapState, mapRenderState);

        context.getMatrices().pushMatrix();

        context.getMatrices().translate(10.0f, 10.0f);
        context.getMatrices().scale(mapScale, mapScale);

        MatrixStack matrixStack = new MatrixStack();
        Matrix3x2fStack matrix2d = context.getMatrices();

        matrixStack.peek().getPositionMatrix().set(
                matrix2d.m00, matrix2d.m01, 0.0F, matrix2d.m20,
                matrix2d.m10, matrix2d.m11, 0.0F, matrix2d.m21,
                0.0F,0.0F,1.0F,0.0F,
                0.0F,0.0F,0.0F,1.0F
        );

        OrderedRenderCommandQueueImpl queue = client.gameRenderer.getEntityRenderCommandQueue();

        client.getMapRenderer().draw(mapRenderState, matrixStack, queue, false, 0xF000F0);

        context.drawMap(mapRenderState);

        context.getMatrices().popMatrix();
    }

    public static void reset() {
        mapRenderState = new  MapRenderState();
        mapState = null;
        mapId = null;
    }
}
