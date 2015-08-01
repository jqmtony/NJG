package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDChart;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.finance.client.util.ChartUtil;

/**
 * FinaceͼUI
 * @author liangliang_ye
 */
public class FinaceMapUI extends AbstractFinaceMapUI {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(FinaceMapUI.class);
    
	public FinaceMapUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
    	super.onLoad();
		this.menuBar.setVisible(false);
		Map map = this.getUIContext();
		String title = (String) map.get("title");
		String[] seriesKeys = (String[]) map.get("seriesKeys");
		String[] groupNames = (String[]) map.get("groupNames");
		double[][] values = (double[][]) map.get("values");
		Integer type = (Integer) map.get("type");
		String windTitle = (String) map.get("windTitle");
		this.setUITitle(windTitle);
		KDChart chart = ChartUtil.buildChart(title, seriesKeys, groupNames, values, type.intValue());
		plnMap.add(chart, BorderLayout.CENTER);
    }
}