/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.formula.builder.bosmeta.BOSType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.port.pm.base.CostTypeInfo;
import com.kingdee.eas.port.pm.base.CostTypeTreeFactory;

/**
 * output class name
 */
public class CostTempEditUI extends AbstractCostTempEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostTempEditUI.class);
    
    /**
     * output class constructor
     */
    public CostTempEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void onLoad() throws Exception {
    	super.onLoad();
    	contDescription.setVisible(false);
		txtDescription.setVisible(false);
		contBizStatus.setVisible(false);
		comboBizStatus.setVisible(false);
		contAuditor.setVisible(false);
		prmtAuditor.setVisible(false);
		contAuditTime.setVisible(false);
		pkAuditTime.setVisible(false);
//		this.kdtE1.getColumn("costType").getStyleAttributes().setLocked(false);
//		final KDBizPromptBox kdtE1_costType_PromptBox = new KDBizPromptBox();
//        kdtE1_costType_PromptBox.setQueryInfo("com.kingdee.eas.port.pm.invest.app.costTypeTreeQuery");
//        kdtE1_costType_PromptBox.setVisible(true);
//        kdtE1_costType_PromptBox.setEditable(true);
//        kdtE1_costType_PromptBox.setDisplayFormat("$E1.costType.name$");
//        kdtE1_costType_PromptBox.setEditFormat("$E1.costType.name$");
//        kdtE1_costType_PromptBox.setCommitFormat("$E1.costType.name$");
//        kdtE1_costType_PromptBox.setCommitFormat("$E1.costType.name$");
//        KDTDefaultCellEditor kdtEntry_costType_CellEditor = new KDTDefaultCellEditor(kdtE1_costType_PromptBox);
//        this.kdtE1.getColumn("costType").setEditor(kdtEntry_costType_CellEditor);
//        ObjectValueRender kdtEntry_costType_OVR = new ObjectValueRender();
//        kdtEntry_costType_OVR.setFormat(new BizDataFormat("$E1.costType.name$"));
//        this.kdtE1.getColumn("costType").setRenderer(kdtEntry_costType_OVR);
    }
    public void storeFields()
    {
        super.storeFields();
    }
    public void kdtE1_Changed(int rowIndex, int colIndex) throws Exception {
    	super.kdtE1_Changed(rowIndex, colIndex);
    	if(rowIndex==-1)
    	{
    		return;
    	}
    	if(this.kdtE1.getColumn(colIndex).getKey().equals("costNames")){
    		if(UIRuleUtil.isNotNull(this.kdtE1.getCell(rowIndex, colIndex).getValue())){
    			CostTypeInfo costTypeInfo = (CostTypeInfo)this.kdtE1.getCell(rowIndex, "costNames").getValue();
    			for (int i = 0; i < costTypeInfo.size(); i++) {
    				this.kdtE1.getCell(rowIndex, "costType").setValue(CostTypeTreeFactory.getRemoteInstance().getCostTypeTreeInfo(new ObjectUuidPK(costTypeInfo.getTreeid().getId())));	
				}
    		}
    	}
    }
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.CostTempFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }
  
    /**
     * output createNewData method
     */
    
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.CostTempInfo objectValue = new com.kingdee.eas.port.pm.invest.CostTempInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setBizDate(new Date());
        return objectValue;
    }
	protected void attachListeners() {
		
	}
	protected void detachListeners() {
		
	}
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

}