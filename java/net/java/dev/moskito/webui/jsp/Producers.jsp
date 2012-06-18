<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Moskito Producers <msk:write name="pageTitle" /></title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>

<jsp:include page="Menu.jsp" flush="false" />

<!--
 Data for charts -->
 <script>
<msk:iterate type="net.java.dev.moskito.webui.bean.GraphDataBean" id="graph" name="graphDatas">	
	var <msk:write name="graph" property="jsVariableName"/>Caption = "<msk:write name="graph" property="caption"/>";
	var <msk:write name="graph" property="jsVariableName"/>Array = <msk:write name="graph" property="jsArrayValue"/>;
</msk:iterate> 

 </script>
<!-- -->


<div class="main">
<msk:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" 	id="decorator" name="decorators">	
<div class="clear"><!-- --></div>
<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">
	
	<msk:define id="sortType" type="net.java.dev.moskito.webui.bean.ProducerBeanSortType" name="<%=decorator.getSortTypeName()%>"/>
	<msk:define id="visibility" type="net.java.dev.moskito.webui.bean.ProducerVisibility" name="decorator" property="visibility"/>

	<msk:equal name="visibility" value="SHOW">
		<h2 class="titel_open">
		<a href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="producerVisibilityParameterName"/>=<msk:write name="visibility" property="opposite"/>">
			<msk:write name="decorator" property="name" />
		</a>
		</h2>
	</msk:equal>
	<msk:equal name="visibility" value="HIDE">
		<h2 class="title_collapsed">
			<a class="hidden" href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="producerVisibilityParameterName"/>=<msk:write name="visibility" property="opposite" />">
		<msk:write name="decorator" property="name" />
		</a>
		</h2>
	</msk:equal>
	<a target="_blank" class="help" href="mskShowExplanations#<msk:write name="decorator" property="name"/>">Help</a>&nbsp;
				
				
	
	<div class="clear"><!-- --></div>
	<msk:equal name="visibility" value="SHOW">
		<div class="table_itseft">
			<div class="top">
				<div class="left"><!-- --></div>
				<div class="right"><!-- --></div>
			</div>
			<div class="in">			
		<table cellpadding="0" cellspacing="0" class="fll" id="<msk:write name="decorator" property="name"/>_table">
		  <thead>
			<tr class="stat_header">
				<th>
					<msk:equal name="sortType" property="sortBy" value="1000">
						<msk:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by <msk:write name="caption" property="shortExplanationLowered"/>"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1000&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=DESC">Producer Id </a>
						</msk:equal>
						<msk:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <msk:write name="caption" property="shortExplanationLowered"/>"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1000&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC">Producer Id </a>
						</msk:equal>
					</msk:equal>   
					<msk:notEqual name="sortType" property="sortBy" value="1000">
						<a 	class="" title="ascending sort by <msk:write name="caption" property="shortExplanationLowered"/>"
							href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1000&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC">Producer Id </a>
					</msk:notEqual>
				</th>
				<th>
					<msk:equal name="sortType" property="sortBy" value="1001">
						<msk:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by category"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1001&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=DESC">Category</a>
						</msk:equal>
						<msk:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by category"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1001&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC">Category</a>
						</msk:equal>
					</msk:equal>   
					<msk:notEqual name="sortType" property="sortBy" value="1001">
						<a 	class="" title="ascending sort by category"
							href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1001&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC">Category</a>
					</msk:notEqual>
				</th>
				<th>
					<msk:equal name="sortType" property="sortBy" value="1002">
						<msk:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by subsystem"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1002&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=DESC">Subsystem</a>
						</msk:equal>
						<msk:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by subsystem"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1002&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC">Subsystem</a>
						</msk:equal>
					</msk:equal>   
					<msk:notEqual name="sortType" property="sortBy" value="1002">
						<a 	class="" title="ascending sort by subsystem"
							href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1002&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC">Subsystem</a>
					</msk:notEqual>
				</th>
			</tr>	
		</thead>
		<tbody>	
			<msk:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean" indexId="index">
			 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
				<td>
					<a href="mskShowProducer?pProducerId=<msk:write name="producer" property="id"/>"
					   title="Show details for this producer">
					   <msk:write name="producer" property="id" />
					 </a>
				</td>
				<td>
					<a	href="mskShowProducersByCategory?pCategory=<msk:write name="producer" property="category"/>"
						title="Show all producers in category:	<msk:write name="producer" property="category"/>">
						<msk:write name="producer" property="category" />
					</a>
				</td>
				<td>
					<a	href="mskShowProducersBySubsystem?pSubsystem=<msk:write name="producer" property="subsystem"/>"
						title="Show all producers in subsystem: <msk:write name="producer" property="subsystem"/>">
						<msk:write	name="producer" property="subsystem" />
					</a>
				</td>
			</tr>
			</msk:iterate>	
		</tbody>			
	</table>
		
	<div class="table_right">
		<table cellpadding="0" cellspacing="0">
		 <thead>
		  <tr>		  
			<msk:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">				
			 <th title="<msk:write name="caption" property="shortExplanation"/>">

					<!-- variable for this graph is <msk:write name="decorator" property="name"/>_<msk:write name="caption" property="jsVariableName"/> -->
				 	<input type="hidden" value="<msk:write name="decorator" property="name"/>_<msk:write name="caption" property="jsVariableName"/>"/>
					<msk:equal name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<msk:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by <msk:write name="caption" property="shortExplanationLowered"/>"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><msk:write name="caption" property="caption"/></a><a href="#"
																								 onClick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</msk:equal>
						<msk:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <msk:write name="caption" property="shortExplanationLowered"/>"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><msk:write name="caption" property="caption"/></a><a href="#"
																								 onClick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</msk:equal>
					</msk:equal>   
					<msk:notEqual name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<a 	class="" title="ascending sort by <msk:write name="caption" property="shortExplanationLowered"/>"
							href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><msk:write name="caption" property="caption"/></a><a href="#"
																								 onClick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
					</msk:notEqual>
			 </th>
			</msk:iterate>			
			
			<th>class
					<msk:equal name="sortType" property="sortBy" value="1003">
						<msk:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by producer class"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1002&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"></a>
						</msk:equal>
						<msk:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by producer class"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1003&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"></a>
						</msk:equal>
					</msk:equal>   
					<msk:notEqual name="sortType" property="sortBy" value="1003">
						<a 	class="" title="ascending sort by producer class"
							href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1003&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"></a>
					</msk:notEqual>
			</th>
		 </tr>		
	   </thead>
	  <tbody>
	   <msk:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean" indexId="index">
		 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
			<msk:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean">
				<td onmouseover="Tip('<msk:write name="producer" property="id"/>.<msk:write name="value" property="name"/>&lt;br/&gt;&lt;b&gt;&lt;span align=center&gt;<msk:write name="value" property="value"/>&lt;/span&gt;&lt;/b&gt;', TEXTALIGN, 'center')" onmouseout="UnTip()">
					<msk:write name="value" property="value" />
				</td>
			</msk:iterate>
			<td class="al_left" onmouseover="Tip('<msk:write name="producer" property="fullClassName"/>&lt;br/&gt;&lt;b&gt;&lt;span align=center&gt;&lt;/span&gt;&lt;/b&gt;', TEXTALIGN, 'center')" onmouseout="UnTip()">
				<msk:write name="producer" property="className" />
			</td>
		</tr>	
		</msk:iterate>	
	 </tbody>
	 </table>
    </div>

	
    <div class="clear"><!-- --></div>
    </div>
	<div class="bot">
		<div class="left"><!-- --></div>
		<div class="right"><!-- --></div>
	</div>
   </div>
		</msk:equal>

   </div>

		<div class="bot"><div><!-- --></div></div>
	</div>
	</msk:iterate>
	<div class="generated">Generated at <msk:write name="timestampAsDate"/>&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <msk:write name="timestamp"/>&nbsp;&nbsp;|&nbsp;&nbsp;Interval updated at: <msk:write name="currentIntervalUpdateTimestamp"/>&nbsp;&nbsp;|&nbsp;&nbsp; Interval age: <msk:write name="currentIntervalUpdateAge"/></div>
<div class="lightbox" style="display:none;">
	<div class="black_bg"><!-- --></div>
	<div class="box">
		<div class="box_top">
			<div><!-- --></div>
			<span><!-- --></span>
			<a class="close_box"><!-- --></a>

			<div class="clear"><!-- --></div>
		</div>
		<div class="box_in">
			<div class="right">
				<div class="text_here">
					<div id="chartcontainer"></div>
					<a href="#" class="pie_chart"></a> <!-- changes to bar_chart -->
					<a href="#" style="display:none;" class="bar_chart"></a>
				</div>
			</div>
		</div>
		<div class="box_bot">
			<div><!-- --></div>
			<span><!-- --></span>
		</div>
	</div>
</div>
<script type="text/javascript">
	google.load('visualization', '1', {packages: ['piechart']});
	google.load('visualization', '1', {packages: ['columnchart']});
	function lightbox(link) {
		$('.lightbox').show();
		var el = $('.lightbox');
		$('.pie_chart').show();
		$('.bar_chart').hide();
		$('.lightbox .box').css('width', 'auto');
		$('.lightbox .box').width($('.lightbox .box_in').width());

		var wid = el.find('.box').width();
		var box = el.find('.box');
		var hig = el.find('.box').height();
		box.css('left', '50%');
		box.css('margin-left', -wid / 2);
		box.css('top', '50%');
		box.css('margin-top', -hig / 2);
		box.css('position', 'fixed');
		return false;
	}
	
	//var datas = new Array;
	var cap, mas, data;
	$('.chart').click(function() {
		cap = eval($(this).parent().find('input').val()+'Caption');
		mas = eval($(this).parent().find('input').val()+'Array');
		data = new google.visualization.DataTable();
        data.addColumn('string', 'Producer');
        data.addColumn('number', 'val');
		data.addRows(mas);
		new google.visualization.PieChart(
          document.getElementById('chartcontainer')).
            draw(data, {is3D:true, width: 1200, height:600, title: cap, legendFontSize: 12, legend:'label'});
		return false;
	});

	$('.pie_chart').live('click', function() {
		new google.visualization.ColumnChart(
          document.getElementById('chartcontainer')).
            draw(data, {is3D:true, width: 1200, height:600, title: cap, legendFontSize: 12, legend:'label'});
		$('.pie_chart').hide();
		$('.bar_chart').show();
		return false;
	});

	$('.bar_chart').live('click', function() {
		new google.visualization.PieChart(
          document.getElementById('chartcontainer')).
            draw(data, {is3D:true, width: 1200, height:600, title: cap, legendFontSize: 12, legend:'label'});
		$('.pie_chart').show();
		$('.bar_chart').hide();
		return false;
	});
</script>
<jsp:include page="Footer.jsp" flush="false" />
</div>	
</body>
</html>

