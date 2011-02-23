<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>

<!-- Creating widget(table) overlay-->
<div class="create_widget_overlay" style="display:none;">
<form action="mskDashBoard" class="create_widget_form">
<h2>Create widget</h2>
<label for="name">Widget name:</label>
<input type="text" name="widgetName" value="" id="name"/>

<div class="widget_type">
    <input type="radio" id="t_table" name="widget_type" checked="checked"/><label for="t_table">Table widget</label>
    <input type="radio" id="t_chart" name="widget_type"/><label for="t_chart">Chart widget</label>
    <input type="radio" id="t_threshold" name="widget_type"/><label for="t_threshold">Threshold widget</label>

    <div class="clear"></div>
</div>

<div class="t_table">

    <div class="t_table_prod_group">
        <h3>Producer group</h3>
        <ul>
            <ano:iterate name="decorators" id="decorator" type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" indexId="indexId">
                <ano:equal name="decorator" property="visibility" value="SHOW">
                    <li class="<ano:equal name="decorator" property="inGadget" value="true">checked</ano:equal><ano:equal name="indexId" value="0"> active</ano:equal>">
                        <div class="top_l"></div>
                        <div class="bot_l"></div>
                        <a href="#" class="uncheck">
                            <img src="<ano:write name="mskPathToImages" scope="application"/>close.png" alt="Uncheck" title="Uncheck"/>&nbsp;
                        </a>
                        <a href="#" class="<ano:write name="decorator" property="name"/>"><ano:write name="decorator" property="name"/></a>
                    </li>
                </ano:equal>
            </ano:iterate>
        </ul>
    </div>

    <div class="right_part">
        <div class="top_r"></div>
        <div class="bot_r"></div>
        <div class="t_table_val">
            <h3>Value</h3>
            <ano:iterate name="decorators" id="decorator" type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" indexId="indexId">
                <ano:equal name="decorator" property="visibility" value="SHOW">
                    <ul class="<ano:write name="decorator" property="name"/>_val" <ano:notEqual name="indexId" value="0">style="display:none;"</ano:notEqual>>
                        <li><input type="checkbox" id="c_all" /><label>Select all</label></li>
                        <ano:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption">
                            <li><input type="checkbox" <ano:equal name="caption" property="inGadget" value="true">checked="checked"</ano:equal> name="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="caption"/>"/><label><ano:write name="caption" property="caption"/></label></li>
                        </ano:iterate>
                    </ul>
                </ano:equal>
            </ano:iterate>
        </div>

        <div class="t_table_prod">
            <h3>Producer</h3>
            <ano:iterate name="decorators" id="decorator" indexId="indexId" type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean">
                <ano:equal name="decorator" property="visibility" value="SHOW">
                    <ul class="<ano:write name="decorator" property="name"/>_prod" <ano:notEqual name="indexId" value="0">style="display:none;"</ano:notEqual>>
                        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>

                        <ano:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean">
                            <li><input type="checkbox" <ano:equal name="producer" property="inGadget" value="true">checked="checked"</ano:equal> name="<ano:write name="producer" property="id"/>"/><label><ano:write name="producer" property="id"/></label></li>
                        </ano:iterate>
                    </ul>
                </ano:equal>
            </ano:iterate>
        </div>

        <div class="clear"></div>
    </div>
    <div class="clear"></div>
</div>

<div class="clear"></div>

<div class="flr">
    <input type="submit" value="Create"/><span>&nbsp;&nbsp;or&nbsp;&nbsp;</span><a
        onclick="closeLightbox(); return false;"
        href="#">Cancel</a>
</div>
</form>
</div>
<!-- Creating widget(table) overlay END-->