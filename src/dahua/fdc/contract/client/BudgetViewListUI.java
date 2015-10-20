/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * Ԥ��鿴���ܽ���<br>
 * ���������뵥�������Ԥ��鿴����<br>
 * ��ز���ͨ��UIContext���ݣ���������Ӧ�ĵ���<br>
 * Author: ��˩��
 * Date  ��2010/09/20
 * 
 */
public class BudgetViewListUI extends AbstractBudgetViewListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BudgetViewListUI.class);

	
	// ���������
	private static final String[] TABLE_COLS = new String[] { "curProject",
			/* modified by zhaoqin for R131218-0367 on 2013/12/25 */
			"curDept", "planMonth", "localBudget", "actPaied", "paid", "floatFund",
			"bgBalance", "planType" };
	
	
	public BudgetViewListUI() throws Exception {
		super();
		initTable();
	}

	
	public void onLoad() throws Exception{
		super.onLoad();
		
		Map uiContext = getUIContext();
		Map dataMap = dealData(uiContext);
		fillTableData(dataMap);
	}
	
	// ���ñ������
	private void initTable(){
		this.tblMain.checkParsed();
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getStyleAttributes().setLocked(true);
		
		KDBizPromptBox prmtPayPlan = new KDBizPromptBox();
		prmtPayPlan.setName("prmtPayPlan");
		prmtPayPlan.setDisplayFormat("$name$");
		prmtPayPlan.setEditFormat("$number$");
		prmtPayPlan.setCommitFormat("$number$;$name$");
		
		ObjectValueRender objRendar = new ObjectValueRender();
		objRendar.setFormat(new BizDataFormat("$name$"));
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(prmtPayPlan);
		
		this.tblMain.getColumn("curProject").setEditor(editor);
		this.tblMain.getColumn("curProject").setRenderer(objRendar);
	}
	



	// ������ݵ�����,��Ӧ������һ������
	public void fillTableData(Map dataMap){
		this.tblMain.checkParsed();
		Set keys = dataMap.keySet();
		String key = "";
		IRow row = tblMain.addRow();
		for(Iterator it = keys.iterator();it.hasNext();){
			key = it.next().toString();
			row.getCell(key).setValue(dataMap.get(key));
		}
	}
	
	// ���ݲ�����������
	public Map dealData(Map uiContext)throws Exception{
		Map dataMap = new HashMap();
		if(uiContext == null){
			return dataMap;
		}
		// ��ȡ����ֵ
		String curProject = (String)uiContext.get("curProject");
		String curDept = (String)uiContext.get("curDept");
		String planMonth =(String)uiContext.get("planMonth");
		BigDecimal localBudget = (BigDecimal)uiContext.get("localBudget");
		BigDecimal actPaied = (BigDecimal)uiContext.get("actPaied");
		BigDecimal floatFund = (BigDecimal)uiContext.get("floatFund");
		BigDecimal bgBalance = (BigDecimal)uiContext.get("bgBalance");
		
		/* modified by zhaoqin for R131218-0367 on 2013/12/25 start */
		BigDecimal paid = (BigDecimal)uiContext.get("paid");
		dataMap.put("paid", paid);
		/* modified by zhaoqin for R131218-0367 on 2013/12/25 end */
		
		dataMap.put("curProject", curProject);
		dataMap.put("curDept",curDept);
		
		dataMap.put("planMonth", planMonth);
		dataMap.put("localBudget", localBudget);
		dataMap.put("actPaied", actPaied);
		dataMap.put("floatFund", floatFund);
		dataMap.put("bgBalance", bgBalance);
		
		return dataMap;
	}

}