package net.java.dev.moskito.webcontrol.ui;

import net.anotheria.maf.MAFFilter;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.java.dev.moskito.webcontrol.repository.RepositoryUpdater;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MoskitoWebcontrolUIFilter extends MAFFilter {

	/**
	 * Path to images for html-layer image linking.
	 */
	private String pathToImages = "../img/";

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		String pathToImagesParameter = config.getInitParameter("pathToImages");
		if (pathToImagesParameter != null && pathToImagesParameter.length() > 0)
			pathToImages = pathToImagesParameter;
		config.getServletContext().setAttribute("mskPathToImages", pathToImages);

		RepositoryUpdater.init(null);
	

		// TODO delete later
		// DummyFillRepository.fillRepository();
		// DummyFillViewConfig.fillConfig();

	}

	@Override
	public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain chain) throws IOException, ServletException {
		System.out.println("doFilter");
		super.doFilter(sreq, sres, chain);

	}

	@Override
	public String getProducerId() {
		return "moskitoWC";
	}

	@Override
	public String getSubsystem() {
		return "monitoring";
	}

	@Override
	public String getCategory() {
		return "filter";
	}

	@Override
	protected List<ActionMappingsConfigurator> getConfigurators() {
		return Arrays.asList(new ActionMappingsConfigurator[] { new MoskitoWebcontrolMappingsConfigurator() });
	}

}
