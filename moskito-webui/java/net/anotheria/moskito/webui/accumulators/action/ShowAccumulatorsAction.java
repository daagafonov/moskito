package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.accumulation.AccumulatedValue;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedValueAO;
import net.anotheria.moskito.webui.accumulators.bean.AccumulatedValuesBean;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.SortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
/**
 * Displays configured accumulators and the gathered data and graphs.
 * @author lrosenberg
 */
public class ShowAccumulatorsAction extends BaseAccumulatorsAction {

	/**
	 * Sort type for sorting. Actually unneeded, but having a variable saves ram.
	 */
	private static final SortType SORT_TYPE = new DummySortType();

	/**
	 * Graph data modes.
	 * @author lrosenberg
	 *
	 */
	static enum MODE{
		/**
		 * Combined mode means multiple graphs in one graph.
		 */
		combined, 
		/**
		 * Multiple graphs in one graph, normalized to 1-100% scale.
		 */
		normalized, 
		/**
		 * Multiple graphs on one page, one per accumulator.
		 */
		multiple;
		
		static MODE fromString(String value){
			if (value==null)
				return combined;
			for (MODE m : values()){
				if (m.name().equals(value)){
					return m;
				}
			}
			//combined is default
			return combined;
		}
	}
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		List<Accumulator> accumulators = AccumulatorRepository.getInstance().getAccumulators();

		MODE mode = MODE.fromString(req.getParameter("mode"));
		req.setAttribute(mode.name()+"_set", Boolean.TRUE);
		
		String chartType = req.getParameter("type");
		if (chartType==null || chartType.length()==0)
			chartType="LineChart";
		req.setAttribute("type", chartType);
		
		
		int normalizeBase = 100;
		try{
			normalizeBase = Integer.parseInt(req.getParameter("normalizeBase"));
		}catch(Exception ignored){}
		req.setAttribute("normalizeBase", normalizeBase);
		int maxValues = 200;
		try{
			maxValues = Integer.parseInt(req.getParameter("maxValues"));
		}catch(Exception ignored){;/*empty*/}
		req.setAttribute("maxValues", maxValues);
		
		req.setAttribute("accumulators", getAccumulatorAPI().getAccumulatorDefinitions());
		
		List<String> ids = new ArrayList<String>();
		
		Enumeration<?> names = req.getParameterNames();
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			if (name.startsWith("id_")){
				ids.add(name.substring("id_".length()));
				//set flag to activate checkbox
				req.setAttribute(name+"_set", Boolean.TRUE);
			}
		}
		
//		System.out.println("ids to show: "+ids);
		if (ids.size()>0){
		List<AccumulatedValueAO> dataBeans = new ArrayList<AccumulatedValueAO>();
		List<AccumulatedSingleGraphAO> singleGraphDataBeans = new ArrayList<AccumulatedSingleGraphAO>(ids.size());
			
			//prepare values
			HashMap<Long, AccumulatedValuesBean> values = new HashMap<Long, AccumulatedValuesBean>();
			List<String> accNames = new ArrayList<String>();
			
			for (String id : ids){
				Accumulator acc = AccumulatorRepository.getInstance().getById(id);
				AccumulatedSingleGraphAO singleGraphDataBean = new AccumulatedSingleGraphAO(acc.getName());
				singleGraphDataBeans.add(singleGraphDataBean);
				accNames.add(acc.getName());
				List<AccumulatedValue> accValues = acc.getValues();
				for (AccumulatedValue v : accValues){
					long timestamp = v.getTimestamp()/1000*1000;
					AccumulatedValuesBean bean = values.get(timestamp);
					if (bean==null){
						bean = new AccumulatedValuesBean(timestamp);
						values.put(timestamp, bean);
					}
					bean.setValue(acc.getName(), v.getValue());
					
					//for single graph data
					AccumulatedValueAO accValueForGraphData = new AccumulatedValueAO(NumberUtils.makeTimeString(timestamp));
					accValueForGraphData.addValue(v.getValue());
					singleGraphDataBean.add(accValueForGraphData);
				}
			}
			List<AccumulatedValuesBean> valuesList = StaticQuickSorter.sort(values.values(), SORT_TYPE);
			
			//now check if the data is complete
			//Stores last known values to allow filling in of missing values (combining 1m and 5m values)
			HashMap<String, String> lastValue = new HashMap<String, String>();

			//filling last (or first) values.
			for (String accName : accNames){
				//first put 'some' initial value.
				lastValue.put(accName, "0");
				//now search for first non-null value
				for(AccumulatedValuesBean accValueBean : valuesList){
					String aValue = accValueBean.getValue(accName);
					if (aValue!=null){
						lastValue.put(accName, aValue);
						break;
					}
				}
			}
			
			for(AccumulatedValuesBean accValueBean : valuesList){
				for (String accName : accNames){
					String value = accValueBean.getValue(accName);
					if (value==null){
						accValueBean.setValue(accName, lastValue.get(accName));
					}else{
						lastValue.put(accName, value);
					}
				}
			}
//			System.out.println("!2!");
//			for(AccumulatedValuesBean avbTemp : valuesList){
//				System.out.println("  "+avbTemp);
//			}

			if (mode==MODE.normalized){
				normalize(valuesList, accNames, normalizeBase);
			}
			
			//now create final data
			for(AccumulatedValuesBean avb : valuesList){
				AccumulatedValueAO bean = new AccumulatedValueAO(avb.getTime());
				for (String accName : accNames){
					bean.addValue(avb.getValue(accName));
				}
				dataBeans.add(bean);
			}
			
			if (dataBeans.size()>maxValues)
				dataBeans = dataBeans.subList(dataBeans.size()-maxValues, dataBeans.size());

			req.setAttribute("data", dataBeans);
			req.setAttribute("accNames", accNames);
			if (mode==MODE.multiple)
				req.setAttribute("singleGraphData", singleGraphDataBeans);
			
		}
		if (getForward(req).equalsIgnoreCase("csv")){
			res.setHeader("Content-Disposition", "attachment; filename=\"accumulators.csv\"");
		}
		return mapping.findCommand(getForward(req));
	}
	
	/*test visibility */ static void normalize(List<AccumulatedValuesBean> values, List<String> names, int limit){
		for (String name : names){
			//System.out.println("normalizing "+name);
			ArrayList<Float> valueCopy = new ArrayList<Float>(values.size());
			//step1 transform everything to float
			float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
			for (AccumulatedValuesBean v : values){
				float val = Float.parseFloat(v.getValue(name));
				if (val>max)
					max = val;
				if (val<min)
					min = val;
				valueCopy.add(val);
			}
			//System.out.println("1: "+valueCopy);
			float range = max - min;
			float multiplier = limit / range;
			//System.out.println("range "+range+", multiplier "+multiplier);
			
			//step2 recalculate
			for (int i=0; i<values.size(); i++){
				float newValue = (valueCopy.get(i)-min)*multiplier;
				//System.out.println(values.get(i).getValue(name)+" --> "+newValue);
				values.get(i).setValue(name, ""+newValue);
			}
		}
	}
	

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		String newQS = rebuildQueryStringWithoutParameter(req.getQueryString(), "ts", "pReloadInterval");
		if (newQS.length()>0)
			newQS+="&";
		newQS+="ts="+System.currentTimeMillis();
		return "mskAccumulators?"+ newQS;
	}

}
