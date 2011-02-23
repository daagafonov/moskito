package net.java.dev.moskito.webui;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import net.anotheria.util.TimeUnit;
import net.java.dev.moskito.webui.bean.WidgetType;
import org.configureme.parser.ConfigurationParserException;
import org.configureme.parser.ParsedAttribute;
import org.configureme.parser.json.JsonParser;
import org.json.*;

import java.sql.Array;
import java.util.*;

/**
 * @author dsilenko
 */
public class Test {

	public static void main(String args[]) throws JSONException {

		final List<Caption> captions = new ArrayList<Caption>();
		captions.add(new Caption("name1", true));
		captions.add(new Caption("name2", true));
		captions.add(new Caption("name3", true));

		modifyCollection(captions);

		for (Caption caption : captions)
			System.out.println("old: " + caption.getName());


	}

	private static void modifyCollection(List<Caption> captions){
		List<Caption> newCaptions = new ArrayList<Caption>(captions.size());
		//Collections.copy(newCaptions, captions);

		for (Caption caption : captions)
			newCaptions.add(caption);

		for (Caption caption : newCaptions)
			System.out.println("new: " + caption.getName());

	}


	public static class DashWidget{
		private String groupName;
		private List<Caption> captions;

		public DashWidget(String groupName, List<Caption> captions) {
			this.groupName = groupName;
			this.captions = captions;
		}

		public String getGroupName() {
			return groupName;
		}

		public List<Caption> getCaptions() {
			return captions;
		}

	}

	public static class Caption {
		private String name;
		private boolean inGadget;

		public Caption(String name, boolean inGadget) {
			this.name = name;
			this.inGadget = inGadget;
		}

		public String getName() {
			return name;
		}

		public boolean isInGadget() {
			return inGadget;
		}

		public void setName(String aName){
			this.name = aName;
		}
	}

}
