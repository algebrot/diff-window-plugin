import com.intellij.openapi.wm.ToolWindow;
import lombok.extern.java.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
public class DiffWindow {
    private ToolWindow toolWindow;
    private Map<String, DiffTab> tabsMap;

    public DiffWindow(ToolWindow toolWindow, List<DiffTab> tabsList) {
        this.toolWindow = toolWindow;
        this.initializeTabsMap(tabsList);
    }

    private void initializeTabsMap(List<DiffTab> tabsList) {
        this.tabsMap = new HashMap<>();
        tabsList.forEach(t -> {
            tabsMap.put(t.getTitle(), t);
            addTabContentToWindow(t);
        });
    }

    private void addTabContentToWindow(DiffTab tab) {
        if (tab != null && tab.getContent() != null) {
            this.toolWindow.getContentManager().addContent(tab.getContent());
        } else {
            log.info("Tab or content was null");
        }
    }

    public void updateTab(String title, String diffOld, String diffNew) {
        DiffTab diffTab = this.tabsMap.get(title);
        if (diffTab != null) {
            diffTab.updateTab(diffOld, diffNew);
            addTabContentToWindow(diffTab);
        } else {
            log.info(String.format("No tab found with title \"%s\".", title));
        }
    }
}
