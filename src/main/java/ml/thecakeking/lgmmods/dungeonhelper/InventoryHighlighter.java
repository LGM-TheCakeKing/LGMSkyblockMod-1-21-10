package ml.thecakeking.lgmmods.dungeonhelper;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InventoryHighlighter
{
    private final static String COLORTERMINALTITLE = "select all the ";
    public static boolean colorTerminal;
    public static String lookForColor = "";
    private final static String ORDERTERMINALTITLE = "click in order!";
    public static boolean orderTerminal;
    public static Integer nextIndexOfOrder = 1;
    private final static String NAMETERMINALTITLE = "what starts with: ";
    public static boolean nameTerminal ;
    public static String lookForLetter = "";
    private final static String ONOFFTERMINALTITLE = "correct all the panes!";
    public static boolean onOffTerminal;
    private final static String PANESTERMINALTITLE = "change all to same color!";
    public static boolean panesTerminal;

    public static final Map<String, Set<Item>> COLOR_GROUPS = new HashMap<>();

    static {
        COLOR_GROUPS.put("white", Set.of(Items.BONE_MEAL, Items.WHITE_WOOL, Items.WHITE_TERRACOTTA, Items.WHITE_STAINED_GLASS));
        COLOR_GROUPS.put("black", Set.of(Items.INK_SAC, Items.BLACK_WOOL, Items.BLACK_TERRACOTTA, Items.BLACK_STAINED_GLASS));
        COLOR_GROUPS.put("red", Set.of(Items.RED_DYE, Items.RED_WOOL, Items.RED_TERRACOTTA,  Items.RED_STAINED_GLASS));
        COLOR_GROUPS.put("yellow", Set.of(Items.YELLOW_DYE, Items.YELLOW_WOOL, Items.YELLOW_TERRACOTTA,  Items.YELLOW_STAINED_GLASS));
        COLOR_GROUPS.put("blue", Set.of(Items.LAPIS_LAZULI, Items.BLUE_WOOL, Items.BLUE_TERRACOTTA,  Items.BLUE_STAINED_GLASS));
        COLOR_GROUPS.put("green", Set.of(Items.GREEN_DYE, Items.GREEN_WOOL, Items.GREEN_TERRACOTTA,   Items.GREEN_STAINED_GLASS));
        COLOR_GROUPS.put("lime", Set.of(Items.LIME_DYE, Items.LIME_WOOL, Items.LIME_TERRACOTTA,  Items.LIME_STAINED_GLASS));
        COLOR_GROUPS.put("light blue", Set.of(Items.LIGHT_BLUE_DYE, Items.LIGHT_BLUE_WOOL, Items.LIGHT_BLUE_TERRACOTTA,  Items.LIGHT_BLUE_STAINED_GLASS));
        COLOR_GROUPS.put("orange", Set.of(Items.ORANGE_DYE, Items.ORANGE_WOOL, Items.ORANGE_TERRACOTTA,  Items.ORANGE_STAINED_GLASS));
        COLOR_GROUPS.put("magenta", Set.of(Items.MAGENTA_DYE, Items.MAGENTA_WOOL, Items.MAGENTA_TERRACOTTA,  Items.MAGENTA_STAINED_GLASS));
        COLOR_GROUPS.put("brown", Set.of(Items.BROWN_DYE, Items.COCOA_BEANS, Items.BROWN_WOOL, Items.BROWN_TERRACOTTA,  Items.BROWN_STAINED_GLASS));
        COLOR_GROUPS.put("cyan", Set.of(Items.CYAN_DYE, Items.CYAN_WOOL, Items.CYAN_TERRACOTTA,  Items.CYAN_STAINED_GLASS));
        COLOR_GROUPS.put("gray", Set.of(Items.GRAY_DYE, Items.GRAY_WOOL, Items.GRAY_TERRACOTTA,  Items.GRAY_STAINED_GLASS));
        COLOR_GROUPS.put("pink", Set.of(Items.PINK_DYE, Items.PINK_WOOL, Items.PINK_TERRACOTTA,  Items.PINK_STAINED_GLASS));
        COLOR_GROUPS.put("purple", Set.of(Items.PURPLE_DYE, Items.PURPLE_WOOL, Items.PURPLE_TERRACOTTA,  Items.PURPLE_STAINED_GLASS));
        COLOR_GROUPS.put("silver", Set.of(Items.LIGHT_GRAY_DYE, Items.LIGHT_GRAY_WOOL, Items.LIGHT_GRAY_TERRACOTTA,  Items.LIGHT_GRAY_STAINED_GLASS));
    }

    public static void onScreenOpened(String title)
    {
        //first check if terminal helper is on, then check if we are in a f7?
        if(true)
        {
            colorTerminal=false; orderTerminal=false; nameTerminal=false; onOffTerminal=false; panesTerminal=false; nextIndexOfOrder=1;

            if(title.toLowerCase().startsWith(COLORTERMINALTITLE)) {
                colorTerminal = true;
                int titleSuffix = title.indexOf(" items!");
                lookForColor = title.toLowerCase().substring(COLORTERMINALTITLE.length(), titleSuffix);
            }

            if(title.toLowerCase().startsWith(NAMETERMINALTITLE)) {
                nameTerminal  = true;
                int indexForLetter = NAMETERMINALTITLE.length()+1;
                lookForLetter =  title.toLowerCase().substring(indexForLetter, indexForLetter+1);
            }

            if(title.toLowerCase().startsWith(ORDERTERMINALTITLE)) {
                orderTerminal = true;
            }
        }
    }
}
