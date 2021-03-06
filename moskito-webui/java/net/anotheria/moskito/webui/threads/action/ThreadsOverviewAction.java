package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.threads.bean.ThreadsInfoBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * This action renders the overview page of the threads section of the webui.
 */
public class ThreadsOverviewAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadsInfoBean infoBean = new ThreadsInfoBean();

		
		infoBean.setThreadCount(mxBean.getThreadCount());
		infoBean.setDaemonThreadCount(mxBean.getDaemonThreadCount());
		infoBean.setPeakThreadCount(mxBean.getPeakThreadCount());
		infoBean.setTotalStarted(mxBean.getTotalStartedThreadCount());
		
		infoBean.setCurrentThreadCpuTimeSupported(mxBean.isCurrentThreadCpuTimeSupported());
		infoBean.setThreadContentionMonitoringEnabled(mxBean.isThreadContentionMonitoringEnabled());
		infoBean.setThreadContentionMonitoringSupported(mxBean.isThreadContentionMonitoringSupported()); 
		infoBean.setThreadCpuTimeEnabled(mxBean.isThreadCpuTimeEnabled()); 
		infoBean.setThreadCpuTimeSupported(mxBean.isThreadCpuTimeSupported());

		req.setAttribute("info", infoBean);
		
		return mapping.success();
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}


}
