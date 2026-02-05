package ml.thecakeking.lgmmods;

import net.minecraft.client.gui.screen.ingame.HandledScreen;

import java.util.HashMap;
import java.util.Map;

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
