package com.kingdee.eas.fdc.contract.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.CtrlUIEnv;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.permission.BasePMException;
import com.kingdee.eas.base.uiframe.EASLoginException;
import com.kingdee.eas.base.uiframe.client.LoginHelper;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.client.DynamicProductTableFill;
import com.kingdee.util.UuidException;

public class ComTestUI extends JFrame {

	public static void login(String name, String pwd, String dataSource) {
		try {
			LoginHelper.initMetaDataLoader();
			LoginHelper.login(name, pwd, "eas", dataSource, new Locale("L2"));
		} catch (BasePMException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (EASLoginException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CtrlUIEnv.setKingdeeLAF();
		ComTestUI.login("hh","","fdc1221");
		ComTestUI comUI = new ComTestUI(false);
		comUI.test();
	}

	private KDTable table = DynamicProductTableFill.getTable("txSMdQEPEADgAAfLwKgOofnl6Ss=");

	public ComTestUI(boolean isNeedLogin) {
		if (isNeedLogin) {
			login("hh1", "", "eas511_0612_sql");
		}
		setSize(500, 500);
		setLocation(200, 100);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(table, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	void setFormatXml() {
		String formatXml = "<?xml version='1.0' encoding='UTF-8'?><DocRoot xmlns:c='http://www.kingdee.com/Common' xmlns:f='http://www.kingdee.com/Form' xmlns:t='http://www.kingdee.com/Table' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.kingdee.com/KDF KDFSchema.xsd' version='0.0'><Styles><c:Style id='sTable'><c:Font fontName='����' size='14' /><c:Borders><c:Border position='top' color='java.awt.Color[r=162,g=162,b=162]' lineStyle='3' /><c:Border position='bottom' /><c:Border position='left' /><c:Border position='right' /></c:Borders><c:Interior color='java.awt.Color[r=255,g=255,b=255]' /><c:Alignment horizontal='Left' /><c:Protection locked='true' /></c:Style><c:Style id='sCol0'><c:Font fontName='����' size='14' /><c:Borders><c:Border position='top' color='java.awt.Color[r=162,g=162,b=162]' lineStyle='3' /><c:Border position='bottom' /><c:Border position='left' /><c:Border position='right' /></c:Borders><c:Interior color='java.awt.Color[r=255,g=255,b=255]' /><c:Alignment horizontal='Left' /><c:Protection locked='true' /></c:Style><c:Style id='sCol1'><c:Font fontName='����' size='14' /><c:Borders><c:Border position='top' color='java.awt.Color[r=162,g=162,b=162]' lineStyle='3' /><c:Border position='bottom' /><c:Border position='left' /><c:Border position='right' /></c:Borders><c:Interior color='java.awt.Color[r=255,g=255,b=255]' /><c:Alignment horizontal='Left' /><c:Protection hidded='true' /></c:Style><c:Style id='sCol2'><c:Font fontName='����' size='14' /><c:Borders><c:Border position='top' color='java.awt.Color[r=162,g=162,b=162]' lineStyle='3' /><c:Border position='bottom' /><c:Border position='left' /><c:Border position='right' /></c:Borders><c:Interior color='java.awt.Color[r=255,g=255,b=255]' /><c:Alignment horizontal='Left' /><c:Protection locked='true' hidded='true' /></c:Style></Styles><Table id='KDTable'><t:Sheet name='sheet1'><t:Table t:selectMode='15' t:mergeMode='0' t:dataRequestMode='0' t:pageRowCount='100' styleID='sTable'><t:ColumnGroup><t:Column t:key='column1' t:width='100' t:mergeable='true' t:resizeable='true' t:moveable='true' t:group='false' styleID='sCol0' /><t:Column t:key='column2' t:width='100' t:mergeable='true' t:resizeable='true' t:moveable='true' t:group='false' styleID='sCol1' /><t:Column t:key='column3' t:width='100' t:mergeable='true' t:resizeable='true' t:moveable='true' t:group='false' styleID='sCol2' /><t:Column t:key='column4' t:width='100' t:mergeable='true' t:resizeable='true' t:moveable='true' t:group='false' /></t:ColumnGroup><t:Head><t:Row t:name='header1' t:height='19' t:mergeable='true' t:resizeable='true'><t:Cell>cell1</t:Cell><t:Cell>cell2</t:Cell><t:Cell>cell3</t:Cell><t:Cell>cell4</t:Cell></t:Row></t:Head></t:Table></t:Sheet></Table></DocRoot>";
		table.setFormatXml(formatXml);
	}
	ChartType[] supports = {ChartType.CT_PIE, // ��ͼ
			ChartType.CT_PIEEXPLODED, // �����ͱ�ͼ
			ChartType.CT_PIE3D, // ��ά��ͼ
			ChartType.CT_COLUMNCLUSTERED, // ��״����ͼ
			ChartType.CT_COLUMNSTACKED, // �ѻ�����ͼ
			ChartType.CT_COLUMNCLUSTERED3D, // ��ά��״����ͼ
			ChartType.CT_COLUMNSTACKED3D, // ��ά�ѻ�����ͼ
			ChartType.CT_BARCLUSTERED, // ��״����ͼ
			ChartType.CT_BARSTACKED, // �ѻ�����ͼ
			ChartType.CT_BARCLUSTERED3D, // ��ά��״����ͼ
			ChartType.CT_BARSTACKED3D, // ��ά�ѻ�����ͼ
			ChartType.CT_LINE, // ����ͼ
			ChartType.CT_LINESTACKED, // �ѻ�����ͼ
			ChartType.CT_LINEMARKERS, // ���ݵ�����ͼ
			ChartType.CT_LINEMARKERSSTACKED, // �ѻ����ݵ�����ͼ
			ChartType.CT_XYSCATTER, // ɢ��ͼ
			ChartType.CT_XYSCATTERLINES, // ����ɢ��ͼ
			ChartType.CT_XYSCATTERLINESNOMARKERS, // �����ݵ�����ɢ��ͼ
			ChartType.CT_AREA, // ���ͼ
			ChartType.CT_AREASTACKED, // �ѻ����ͼ
			ChartType.CT_GANTT // ����ͼ
	};
	public void test() throws EASBizException, BOSException, UuidException,
			SQLException {
//		DynamicProductTableFill.getTable("bPGKcAEPEADgBPgCwKgTFPnl6Ss=");
		this.getContentPane().add(this.table,BorderLayout.CENTER);
		show();
	}

	public static void showUI(String uiClassName, boolean isNeedLogin,
			Map uiContext) {

		if (isNeedLogin) {
			login("gcgly", "", "eas511_0612_sql");
		}
		// ����UI������ʾ
		IUIObject uiObject = null;
		try {
			uiObject = UIFactoryHelper.initUIObject(uiClassName, uiContext,
					null, "ADDNEW");
		} catch (UIException e) {
			e.printStackTrace();
			System.exit(0);
		}
		UIModelDialog dlg = new UIModelDialog(uiObject, (Frame) null);
		JPanel contentPane = (JPanel) dlg.getContentPane();
		int toolbarHeight = 30;
		if (uiObject.getUIToolBar() == null
				|| UIFactoryHelper.getVisibleComponentCount(uiObject
						.getUIToolBar()) == 0) {
			toolbarHeight = 0;
		}
		// Dimension contentPaneSize = java.awt.Toolkit.getDefaultToolkit()
		// .getScreenSize();
		Dimension contentPaneSize = new Dimension(800, 600);
		contentPane.setPreferredSize(contentPaneSize);
		dlg.show();
		System.exit(0);
	}

}
