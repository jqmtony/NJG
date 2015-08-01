/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.AcctAccreditAcctsFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditAcctsInfo;
import com.kingdee.eas.fdc.basedata.AcctAccreditSchemeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 首创售前Demo output class name
 */
public class AcctAccreditAssignUI extends AbstractAcctAccreditAssignUI {
	private static final Logger logger = CoreUIObject.getLogger(AcctAccreditAssignUI.class);

	/**
	 * output class constructor
	 */
	public AcctAccreditAssignUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnAddAll_actionPerformed method
	 */
	protected void btnAddAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnAddAll_actionPerformed(e);
		assignedMap.clear();
		assignedMap.putAll(acctNumberMap);
		fillAssignTable();
	}

	/**
	 * output btnAdd_actionPerformed method
	 */
	protected void btnAdd_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnAdd_actionPerformed(e);
		IRow row = KDTableUtil.getSelectedRow(tblAcct);
		if (row != null && row.getUserObject() != null) {
			CostAccountInfo info = (CostAccountInfo) row.getUserObject();
			addCostAcctToAssignedMap(info.getId().toString());
			fillAssignTable();
		}
	}

	/**
	 * output btnDelete_actionPerformed method
	 */
	protected void btnDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnDelete_actionPerformed(e);
		IRow row = KDTableUtil.getSelectedRow(tblAssignAcct);
		if (row != null && row.getUserObject() != null) {
			CostAccountInfo info = (CostAccountInfo) row.getUserObject();
			deleteCostAcctToAssignedMap(info.getId().toString());
			fillAssignTable();
		}
	}

	/**
	 * output btnDeleteAll_actionPerformed method
	 */
	protected void btnDeleteAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnDeleteAll_actionPerformed(e);
		assignedMap.clear();
		fillAssignTable();
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.btnAdd.setText(null);
		Icon addIcon = EASResource.getIcon("imgTbtn_move_right");
		this.btnAdd.setIcon(addIcon);
		Icon addAllIcon = EASResource.getIcon("imgTbtn_moveall_right");
		this.btnAddAll.setIcon(addAllIcon);
		Icon deleteIcon = EASResource.getIcon("imgTbtn_move_left");
		this.btnDelete.setIcon(deleteIcon);
		Icon deleteAllIcon = EASResource.getIcon("imgTbtn_moveall_left");
		this.btnDeleteAll.setIcon(deleteAllIcon);
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		setButtonDefaultStyl(btnSave);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * if(tblAssignAcct.getRowCount()<=0){ return; }
		 */
		String schemeId = FDCHelper.getF7Id(prmtScheme);
		if (schemeId == null) {
			return;
		}
		AcctAccreditSchemeInfo scheme=new AcctAccreditSchemeInfo();
		scheme.setId(BOSUuid.read(schemeId));
		CoreBaseCollection col=new CoreBaseCollection();
		for (int i = 0; i < tblAssignAcct.getRowCount(); i++) {
			IRow row = tblAssignAcct.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
				if (acct == null || !acct.isIsLeaf()) {
					continue;
				}
				AcctAccreditAcctsInfo info = new AcctAccreditAcctsInfo();
				info.setCostAccount(acct);
				info.setScheme(scheme);
				col.add(info);
			}
		}
		if (col.size() <= 0) {
			AcctAccreditAcctsInfo info = new AcctAccreditAcctsInfo();
			info.setCostAccount(null);
			info.setScheme(scheme);
			col.add(info);
		}
		AcctAccreditAcctsFactory.getRemoteInstance().submit(col);
		FDCMsgBox.showInfo(this, "保存成功!");
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initCtrlistener();
		initTable();
		fillLeftTable();
		String schemeId=(String)getUIContext().get(UIContext.ID);
		if(schemeId!=null){
			AcctAccreditSchemeInfo scheme=AcctAccreditSchemeFactory.getRemoteInstance().getAcctAccreditSchemeInfo(new ObjectUuidPK(schemeId));
			prmtScheme.setValue(scheme);
		}
	}
	private void initTable(){
		tblAcct.checkParsed();
		tblAssignAcct.checkParsed();
    	tblAcct.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	tblAcct.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblAcct.getColumn("number").setRenderer(FDCRenderHelper.getLongNumberRender());
    	tblAssignAcct.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	tblAssignAcct.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblAssignAcct.getColumn("number").setRenderer(FDCRenderHelper.getLongNumberRender());
    	tblAssignAcct.getStyleAttributes().setLocked(true);
    	tblAcct.getStyleAttributes().setLocked(true);
	}
	
	public void loadFields() {
		if (prmtScheme.getValue() == null) {
			return;
		}

		String schemeId = FDCHelper.getF7Id(prmtScheme);
		assignedMap.clear();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fcostAccountId from T_FDC_AcctAccreditAccts where fschemeid=?");
		builder.addParam(schemeId);
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String acctId = rowSet.getString("fcostAccountId");
				addCostAcctToAssignedMap(acctId);
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		fillAssignTable();
	}
	private void fillAssignTable() {
	
		tblAssignAcct.removeRows();
		tblAssignAcct.getTreeColumn().setDepth(0);
		if (assignedMap.size() <= 0) {
			return;
		}
		int dep = 0;
		for (Iterator iter = assignedMap.keySet().iterator(); iter.hasNext();) {
			CostAccountInfo acct = (CostAccountInfo) assignedMap.get(iter.next());
			if (acct == null) {
				continue;
			}
			if (acct.getLevel() > dep) {
				dep = acct.getLevel();
			}
		}
		tblAssignAcct.getTreeColumn().setDepth(dep);
		for (Iterator iter = assignedMap.keySet().iterator(); iter.hasNext();) {
			CostAccountInfo acct = (CostAccountInfo) assignedMap.get(iter.next());
			if (acct == null) {
				continue;
			}
			IRow row = tblAssignAcct.addRow();
			row.setUserObject(acct);
			row.setTreeLevel(acct.getLevel() - 1);
			row.getCell("id").setValue(acct.getId().toString());
			row.getCell("number").setValue(acct.getLongNumber());
			row.getCell("name").setValue(acct.getName());
		}
	}

	private Map assignedMap = new TreeMap();
	private Map acctMap = new HashMap();
	private Map acctNumberMap = new HashMap();

	/**
	 * 填充左边分配科目表格
	 */
	public void fillLeftTable() {
		tblAcct.removeRows();
		acctMap.clear();
		acctNumberMap.clear();
		//取集团科目
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("fullOrgUnit.id", OrgConstants.DEF_CU_ID);
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		view.getSorter().add(new SorterItemInfo("longNumber"));
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("name");
		view.getSelector().add("level");
		view.getSelector().add("isLeaf");
		
		try {
			CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
			int dep = 0;
			for (int i = 0; i < accts.size(); i++) {
				CostAccountInfo costAcct = accts.get(i);
				if (costAcct != null && costAcct.getLevel() > dep) {
					dep = costAcct.getLevel();
				}
			}
			tblAcct.getTreeColumn().setDepth(dep);
			for (int i = 0; i < accts.size(); i++) {
				CostAccountInfo costAcct = accts.get(i);
				acctNumberMap.put(costAcct.getLongNumber(), costAcct);
				acctMap.put(costAcct.getId().toString(), costAcct);
				IRow row = tblAcct.addRow();
				row.setTreeLevel(costAcct.getLevel() - 1);
				row.setUserObject(costAcct);
				row.getCell("id").setValue(costAcct.getId().toString());
				row.getCell("number").setValue(costAcct.getLongNumber());
				row.getCell("name").setValue(costAcct.getName());
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	private void initCtrlistener() {
		prmtScheme.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7AcctAccreditSchemeQuery");
		prmtScheme.setEnabledMultiSelection(false);
		prmtScheme.setDisplayFormat("$name$");
		prmtScheme.setEditFormat("$number$");
		prmtScheme.setCommitFormat("$number$");
		prmtScheme.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				loadFields();
			}
		});
	}

	private void addCostAcctToAssignedMap(String acctId) {
		CostAccountInfo acct = (CostAccountInfo) acctMap.get(acctId);
		if (acct == null) {
			return;
		}
		/*
		 * // assignedMap.put(acct.getLongNumber(), acct); //添加科目上级 String
		 * []splits=acct.getLongNumber().split("!"); String last=""; for(int
		 * i=0;i<splits.length;i++){ if(i==0){ last=splits[i]; }else{
		 * last=last+"!"+splits[i]; } assignedMap.put(last,
		 * acctNumberMap.get(last)); }
		 */
		// 将它的下级及上级也加入
		for (Iterator iter = acctNumberMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (key.startsWith(acct.getLongNumber() + "!") || acct.getLongNumber().startsWith(key + "!")) {
				assignedMap.put(key, acctNumberMap.get(key));
			}
		}
		assignedMap.put(acct.getLongNumber(), acct);
	}

	private void deleteCostAcctToAssignedMap(String acctId) {
		CostAccountInfo acct = (CostAccountInfo) acctMap.get(acctId);
		if (acct == null) {
			return;
		}
		// 检查有没有同级的,如果有则只删除本科目及下级科目,或者删除所有科目
		String[] splits = acct.getLongNumber().split("!");
		String last = "";
		for (int i = 0; i < splits.length; i++) {
			if (i == 0) {
				last = splits[i];
			} else {
				last = last + "!" + splits[i];
			}
			assignedMap.remove(last);
		}
		// 删除它的下级
		if (!acct.isIsLeaf()) {
			Map tmp=new TreeMap();
			tmp.putAll(assignedMap);
			for (Iterator iter = tmp.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (key.startsWith(acct.getLongNumber() + "!")) {
					assignedMap.remove(key);
				}
			}
		}
		// 做一次同步,同步回来被删除但是还有下级的上级
		Map tmp=new TreeMap();
		tmp.putAll(assignedMap);
		for (Iterator iter = tmp.values().iterator(); iter.hasNext();) {
			String id = (((CostAccountInfo) iter.next()).getId()).toString();
			addCostAcctToAssignedMap(id);
		}
	}
	
	public static void showUI(IUIObject ui,String schemeId,String oprtState) throws UIException{
		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, schemeId);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(AcctAccreditAssignUI.class.getName(), uiContext, null, oprtState);
		uiWindow.show();
	}
}