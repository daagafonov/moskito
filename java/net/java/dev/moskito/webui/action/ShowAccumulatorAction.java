package net.java.dev.moskito.webui.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.accumulation.AccumulatedValue;
import net.java.dev.moskito.core.accumulation.Accumulator;
import net.java.dev.moskito.core.accumulation.AccumulatorRepository;
import net.java.dev.moskito.webui.bean.AccumulatedSingleGraphDataBean;
import net.java.dev.moskito.webui.bean.AccumulatedValueBean;
import net.java.dev.moskito.webui.bean.AccumulatorInfoBean;
import net.java.dev.moskito.webui.bean.NaviItem;

/**
/**
 * Displays a single accumulator.
 * @author lrosenberg
 */
public class ShowAccumulatorAction extends BaseMoskitoUIAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String accumulatorId = req.getParameter("pId");
		Accumulator accumulator = AccumulatorRepository.getInstance().getAccumulatorById(accumulatorId);

		AccumulatorInfoBean accInfoBean = new AccumulatorInfoBean();
		accInfoBean.setName(accumulator.getName());
		accInfoBean.setPath(accumulator.getDefinition().describe());
		accInfoBean.setId(accumulator.getId());
		List<AccumulatedValue> values = accumulator.getValues();
		if (values!=null && values.size()>0){
			accInfoBean.setNumberOfValues(values.size());
			accInfoBean.setLastValueTimestamp(values.get(values.size()-1).getISO8601Timestamp());
		}else{
			accInfoBean.setNumberOfValues(0);
			accInfoBean.setLastValueTimestamp("none");
		}
		
		req.setAttribute("accumulatorInfo", accInfoBean);
		
		AccumulatedSingleGraphDataBean singleGraphDataBean = new AccumulatedSingleGraphDataBean(accumulator.getName());

		List<AccumulatedValue> accValues = accumulator.getValues();
		for (AccumulatedValue v : accValues){
			long timestamp = v.getTimestamp()/1000*1000;
			//for single graph data
			AccumulatedValueBean accValueForGraphData = new AccumulatedValueBean(NumberUtils.makeTimeString(timestamp));
			accValueForGraphData.addValue(v.getValue());
			accValueForGraphData.setIsoTimestamp(NumberUtils.makeISO8601TimestampString(v.getTimestamp()));
			singleGraphDataBean.add(accValueForGraphData);
		}
		
		req.setAttribute("accumulatorData", singleGraphDataBean);
	
		return mapping.findCommand(getForward(req));
	}
	

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskAccumulator?ts="+System.currentTimeMillis()+"&pId="+req.getParameter("pId");
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.ACCUMULATORS;
	}
}
