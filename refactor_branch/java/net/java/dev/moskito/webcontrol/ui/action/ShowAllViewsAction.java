package net.java.dev.moskito.webcontrol.ui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

public class ShowAllViewsAction extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception {

//		List<String> viewnames = ConfigurationRepository.INSTANCE.getViewNames();
//		List<ViewTable> views = new ArrayList<ViewTable>(viewnames.size());

//		for (String viewName : viewnames) {
//			views.add(prepareView(viewName));
//		}

//		req.setAttribute("views", views);

		return mapping.findForward("success");
	}

}
