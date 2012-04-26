package net.java.dev.moskito.webui;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.java.dev.moskito.webui.action.AnalyzeJourneyAction;
import net.java.dev.moskito.webui.action.ForceIntervalUpdateAction;
import net.java.dev.moskito.webui.action.GetChartDataAction;
import net.java.dev.moskito.webui.action.GetChartMetaDataAction;
import net.java.dev.moskito.webui.action.InspectProducerAction;
import net.java.dev.moskito.webui.action.ShowAccumulatorsAction;
import net.java.dev.moskito.webui.action.ShowAllProducersAction;
import net.java.dev.moskito.webui.action.ShowChartsAction;
import net.java.dev.moskito.webui.action.ShowDashboardAction;
import net.java.dev.moskito.webui.action.ShowExplanationsAction;
import net.java.dev.moskito.webui.action.ShowJourneyAction;
import net.java.dev.moskito.webui.action.ShowJourneyCallAction;
import net.java.dev.moskito.webui.action.ShowJourneysAction;
import net.java.dev.moskito.webui.action.ShowProducerAction;
import net.java.dev.moskito.webui.action.ShowProducersForCategoryAction;
import net.java.dev.moskito.webui.action.ShowProducersForSubsystemAction;
import net.java.dev.moskito.webui.action.ShowRecordedUseCaseAction;
import net.java.dev.moskito.webui.action.ShowThresholdsAction;
import net.java.dev.moskito.webui.action.ShowUseCasesAction;

/**
 * Mappings configurator for MoSKito project for the AnoMaf framework.
 * @author lrosenberg.
 *
 */
public class MoskitoMappingsConfigurator implements ActionMappingsConfigurator{
	
	@Override public void configureActionMappings(ActionMappings mappings){
		mappings.addForward("mskCSS", "/net/java/dev/moskito/webui/jsp/CSS.jsp");

		mappings.addMapping("mskDashBoard", ShowDashboardAction.class,
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Dashboard.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducersJSON.jsp")
		);

		mappings.addAlias("mskDashBoard.csv", "mskDashBoard");
		mappings.addAlias("mskDashBoard.xml", "mskDashBoard");
		mappings.addAlias("mskDashBoard.json", "mskDashBoard");

		mappings.addMapping("mskShowAllProducers", ShowAllProducersAction.class,
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducersJSON.jsp")
		);

		mappings.addAlias("mskShowAllProducers.csv", "mskShowAllProducers");
		mappings.addAlias("mskShowAllProducers.xml", "mskShowAllProducers");
		mappings.addAlias("mskShowAllProducers.json", "mskShowAllProducers");
 
		mappings.addMapping("mskShowProducersByCategory", ShowProducersForCategoryAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducersJSON.jsp")
		);
		
		mappings.addAlias("mskShowProducersByCategory.csv", "mskShowProducersByCategory");
		mappings.addAlias("mskShowProducersByCategory.xml", "mskShowProducersByCategory");
		mappings.addAlias("mskShowProducersByCategory.json", "mskShowProducersByCategory");

		mappings.addMapping("mskShowProducersBySubsystem", ShowProducersForSubsystemAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
		 		new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducersJSON.jsp")
		);
		
		mappings.addAlias("mskShowProducersBySubsystem.csv", "mskShowProducersBySubsystem");
		mappings.addAlias("mskShowProducersBySubsystem.xml", "mskShowProducersBySubsystem");
		mappings.addAlias("mskShowProducersBySubsystem.json", "mskShowProducersBySubsystem");

		mappings.addMapping("mskShowProducer", ShowProducerAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducerXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducerCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducerJSON.jsp")
		);
		
		mappings.addAlias("mskShowProducer.csv", "mskShowProducer");
		mappings.addAlias("mskShowProducer.xml", "mskShowProducer");
		mappings.addAlias("mskShowProducer.json", "mskShowProducer");

		mappings.addMapping("mskInspectProducer", InspectProducerAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/InspectProducer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/InspectProducerXML.jsp")
		);
		
		mappings.addMapping("mskShowExplanations", ShowExplanationsAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Explanations.jsp")
		);

		mappings.addMapping("mskShowUseCases", ShowUseCasesAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/UseCases.jsp")
		);
		mappings.addMapping("mskShowRecordedUseCase", ShowRecordedUseCaseAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/RecordedUseCase.jsp")
		);
		mappings.addMapping("mskShowJourneys", ShowJourneysAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Journeys.jsp")
		);
		mappings.addMapping("mskShowJourney", ShowJourneyAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Journey.jsp")
		);
		mappings.addMapping("mskShowJourneyCall", ShowJourneyCallAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/JourneyCall.jsp")
		);

		mappings.addMapping("getChartData", GetChartDataAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/ChartData.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ChartDataXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ChartDataCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ChartDataJSON.jsp")

		);
		
		mappings.addMapping("getChartMetaData", GetChartMetaDataAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/ChartMetaData.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ChartMetaDataXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ChartMetaDataCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ChartMetaDataJSON.jsp")

		);
		
		mappings.addMapping("mskShowCharts", ShowChartsAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Charts.jsp")
		);
		
		mappings.addMapping("mskForceIntervalUpdate", ForceIntervalUpdateAction.class, (ActionForward[])null);

		mappings.addMapping("mskThresholds", ShowThresholdsAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Thresholds.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ThresholdsXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ThresholdsCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ThresholdsJSON.jsp")
		);
		mappings.addAlias("mskThresholds.csv", "mskThresholds");
		mappings.addAlias("mskThresholds.xml", "mskThresholds");
		mappings.addAlias("mskThresholds.json", "mskThresholds");
		

		mappings.addMapping("mskAccumulators", ShowAccumulatorsAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Accumulators.jsp")
		);
		
		
		//analyze journey
		mappings.addMapping("mskAnalyzeJourney", AnalyzeJourneyAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/AnalyzeJourney.jsp")
		);
		
	}

}
