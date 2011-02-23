package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean;

public class ShowUseCasesAction extends BaseMoskitoUIAction{

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) {
		
		//prepare RecordedUseCases
		List<ExistingRunningUseCase> recorded = getUseCaseRecorder().getRecordedUseCases();
		List<RecordedUseCaseListItemBean> beans = new ArrayList<RecordedUseCaseListItemBean>(recorded.size());
		for (int i=0; i<recorded.size(); i++){
			ExistingRunningUseCase useCase = recorded.get(i);
			RecordedUseCaseListItemBean b = new RecordedUseCaseListItemBean();
			b.setName(useCase.getName());
			b.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));
			beans.add(b);
		}
		
		req.setAttribute("recorded", beans);
		if (beans.size()>0)
			req.setAttribute("recordedAvailableFlag", Boolean.TRUE);
		return mapping.findForward("success");
	}
	
	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.USECASES;
	}


}
