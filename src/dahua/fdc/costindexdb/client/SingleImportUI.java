/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryFactory;
import com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SingleImportUI extends AbstractSingleImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SingleImportUI.class);
    
    /**
     * output class constructor
     */
    public SingleImportUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionConfirm.setEnabled(true);
		actionExit.setEnabled(true);
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				executeImport();
			}
		}
    }

    /**
     * output actionConfirm_actionPerformed
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		executeImport();
    }
    
    private void executeImport() throws Exception {
    	if (MsgBox.OK != MsgBox.showConfirm2(this, "从模板导入将覆盖当前数据，继续么？")) {
			return;
		}
    	KDTable table = (KDTable)getUIContext().get("kdtable");
    	SinglePointTempEntryCollection btcoll = null;
    	btcoll=SinglePointTempEntryFactory.getRemoteInstance().getSinglePointTempEntryCollection("select pointName,baseUnit.id,baseUnit.name,baseUnit.number,costAcount.id,costAcount.name,costAcount.number,costAcount.longNumber,costAcount.isLeaf,isCombo where parent.id='"+getSelectedKeyValue()+"' order by seq");
    	AimCostInfo aimCostInfo = getLastAimCostByCurProject((String)getUIContext().get("curProject"));
    	Map<String,BigDecimal> goalCostMap = new HashMap<String,BigDecimal>();
    	if(aimCostInfo != null){
    		FDCSQLBuilder builder = new FDCSQLBuilder();
    		builder.appendSql("select costAccount.flongnumber,sum(costEntry.FCostAmount) from T_AIM_CostEntry costEntry ");
    		builder.appendSql("left join T_FDC_CostAccount costAccount on costEntry.FCostAccountID = costAccount.FID ");
    		builder.appendSql("left join T_FDC_CurProject project on costAccount.FCurProject = project.FID where ");
//    		builder.appendSql("where costAccount.fid = ? and ");
    		builder.appendSql("costEntry.FHeadID ='"+aimCostInfo.getId().toString()+"' group by costAccount.flongnumber");
    		IRowSet rowSet = builder.executeQuery();
    		while(rowSet.next()){
    			goalCostMap.put(rowSet.getString(1),rowSet.getBigDecimal(2));
    		}
    	}
    	IRow row = null;
    	SinglePointTempEntryInfo beinfo = null;
    	table.removeRows();
    	String costLongNumber = null;
    	CostAccountInfo costInfo = null;
    	for (int i = 0; i < btcoll.size(); i++) {
    		beinfo = btcoll.get(i);
    		row = table.addRow();
    		row.getCell("pointName").setValue(beinfo.getPointName());
    		row.getCell("baseUnit").setValue(beinfo.getBaseUnit());
    		costInfo = beinfo.getCostAcount();
    		row.getCell("costAccount").setValue(costInfo);
    		if("目标成本".equals(beinfo.getPointName())){
    			costLongNumber = costInfo.getLongNumber();
    			if(goalCostMap.containsKey(costLongNumber))
    				row.getCell("pointValue").setValue(goalCostMap.get(costLongNumber));
    			else if(!costInfo.isIsLeaf()){
    				row.getCell("pointValue").setValue(getUpLevelValue(costLongNumber,goalCostMap));
    			}
    		}
    		row.getCell("isModel").setValue(Boolean.FALSE);
    		row.getCell("beizhu").setValue("temp");
    		row.getCell("isCombo").setValue(beinfo.isIsCombo());
    		row.getCell("isCombo").getStyleAttributes().setLocked(true);
		}
    	MsgBox.showInfo(this, "导入成功");
		disposeUIWindow();
    }
    
    //根据科目的长编码，汇总本级总金额
    private BigDecimal getUpLevelValue(String costLongNumber, Map<String,BigDecimal> goalCostMap){
    	BigDecimal temp = BigDecimal.ZERO;
		String key = null;
		for(Iterator<String> it=goalCostMap.keySet().iterator(); it.hasNext();) {
			key = it.next();
			if(key.startsWith(costLongNumber))
				temp = temp.add(goalCostMap.get(key));
		}
		goalCostMap.put(costLongNumber,temp);
		return temp;
    }

    /**
     * output actionExit_actionPerformed
     */
    public void actionExit_actionPerformed(ActionEvent e) throws Exception
    {
    	this.disposeUIWindow();
    }
    
    /**
	 * 获取当前工程项目最新版本的目标成本
	 * @param curProject
	 * @throws BOSException
	 * @author zhaoqin
	 * @date 2013/10/22
	 */
	private AimCostInfo getLastAimCostByCurProject(String curProjectId) throws BOSException {
		if(null == curProjectId )
			return null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("versionNumber");
		selector.add("versionName");
		selector.add("orgOrProId");
		selector.add("state");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", curProjectId));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE));
		view.setFilter(filter);
		view.setSelector(selector);
		AimCostCollection coll = AimCostFactory.getRemoteInstance().getAimCostCollection(view);
		if(coll.size() > 0)
			return coll.get(0);
		return null;
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

}