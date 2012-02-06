package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.journey.Journey;
import net.java.dev.moskito.core.journey.JourneyManager;
import net.java.dev.moskito.core.journey.JourneyManagerFactory;
import net.java.dev.moskito.core.journey.NoSuchJourneyException;
import net.java.dev.moskito.webui.bean.JourneyListItemBean;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.TracedCallListItemBean;

/**
 * The actions displays a whole monitoring session.
 * @author lrosenberg.
 *
 */
public class ShowJourneyAction extends BaseMoskitoUIAction{
	
	private JourneyManager journeyManager;
	
	public ShowJourneyAction(){
		journeyManager = JourneyManagerFactory.getJourneyManager();
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res){

		
		String journeyName = req.getParameter("pJourneyName");
		
		Journey journey = null;
		try{
			journey = journeyManager.getJourney(journeyName);
		}catch(NoSuchJourneyException e){
			throw new IllegalArgumentException("Journey with name "+journeyName+" not found.");
		}

		
		JourneyListItemBean bean = new JourneyListItemBean();
			
		bean.setName(journey.getName());
		bean.setActive(journey.isActive());
		bean.setCreated(NumberUtils.makeISO8601TimestampString(journey.getCreatedTimestamp()));
		bean.setLastActivity(NumberUtils.makeISO8601TimestampString(journey.getLastActivityTimestamp()));
		bean.setNumberOfCalls(journey.getTracedCalls().size());
		req.setAttribute("journey", bean);

		List<CurrentlyTracedCall> recorded = journey.getTracedCalls();
		List<TracedCallListItemBean> beans = new ArrayList<TracedCallListItemBean>(recorded.size());
		for (int i=0; i<recorded.size(); i++){
			CurrentlyTracedCall useCase = recorded.get(i);
			TracedCallListItemBean b = new TracedCallListItemBean();
			b.setName(useCase.getName());
			b.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));
			beans.add(b);
		}
		
		req.setAttribute("recorded", beans);
		return mapping.success();
	}
	
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.JOURNEYS;
	}

	
}