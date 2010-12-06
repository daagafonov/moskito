package net.java.dev.moskito.webcontrol.ui;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;

public class MoskitoWebcontrolMappingsConfigurator implements ActionMappingsConfigurator {

	public void configureActionMappings() {
		ActionMappings.addMapping("mwcShowAll", "net.java.dev.moskito.webcontrol.ui.action.ShowAllViewsAction", new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/allViews.jsp"));
		ActionMappings.addMapping("mwcShowView", "net.java.dev.moskito.webcontrol.ui.action.ShowViewAction", new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/showView.jsp"));
		ActionMappings.addMapping("mwcCSS", "net.java.dev.moskito.webcontrol.ui.action.CssAction", new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/CSS.jsp"));

		ActionMappings.addMapping(
				"viewConfig", 
				"net.java.dev.moskito.webcontrol.ui.action.ViewConfigAction", 
				new ActionForward("success", "/net/java/dev/moskito/webcontrol/ui/jsp/viewConfig.jsp")
		);
	}

}
