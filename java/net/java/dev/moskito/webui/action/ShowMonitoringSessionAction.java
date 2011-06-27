package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.session.IMonitoringSessionManager;
import net.java.dev.moskito.core.usecase.session.MonitoringSession;
import net.java.dev.moskito.core.usecase.session.MonitoringSessionManagerFactory;
import net.java.dev.moskito.core.usecase.session.NoSuchMonitoringSessionException;
import net.java.dev.moskito.webui.bean.MonitoringSessionListItemBean;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean;

/**
 * The actions displays a whole monitoring session.
 * @author lrosenberg.
 *
 */
public class ShowMonitoringSessionAction extends BaseMoskitoUIAction{
	
	private IMonitoringSessionManager sessionManager;
	
	public ShowMonitoringSessionAction(){
		sessionManager = MonitoringSessionManagerFactory.getMonitoringSessionManager();
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res){

		
		String sessionName = req.getParameter("pSessionName");
		
		MonitoringSession msession = null;
		try{
			msession = sessionManager.getSession(sessionName);
		}catch(NoSuchMonitoringSessionException e){
			throw new IllegalArgumentException("Session with name "+sessionName+" not found.");
		}

		
		MonitoringSessionListItemBean bean = new MonitoringSessionListItemBean();
			
		bean.setName(msession.getName());
		bean.setActive(msession.isActive());
		bean.setCreated(NumberUtils.makeISO8601TimestampString(msession.getCreatedTimestamp()));
		bean.setLastActivity(NumberUtils.makeISO8601TimestampString(msession.getLastActivityTimestamp()));
		bean.setNumberOfCalls(msession.getUseCases().size());
		req.setAttribute("msession", bean);

		List<ExistingRunningUseCase> recorded = msession.getUseCases();
		List<RecordedUseCaseListItemBean> beans = new ArrayList<RecordedUseCaseListItemBean>(recorded.size());
		for (int i=0; i<recorded.size(); i++){
			ExistingRunningUseCase useCase = recorded.get(i);
			RecordedUseCaseListItemBean b = new RecordedUseCaseListItemBean();
			b.setName(useCase.getName());
			b.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));
			beans.add(b);
		}
		
		req.setAttribute("recorded", beans);
		return mapping.success();
	}
	
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.SESSIONS;
	}

	
}