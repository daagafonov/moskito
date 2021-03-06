package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

/**
 * Creates a thread dump and presents it to the users.
 */
public class ThreadsDumpAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] infos = mxBean.dumpAllThreads(true, true);
		req.setAttribute("infos", Arrays.asList(infos));
		req.setAttribute("infosCount", infos.length);

		return mapping.success();
	}

	
}
