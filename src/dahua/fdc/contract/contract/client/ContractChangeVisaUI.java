/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractChangeVisaUI extends AbstractContractChangeVisaUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractChangeVisaUI.class);
    private boolean isOk = false;
    /**
     * output class constructor
     */
    public ContractChangeVisaUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	txtImpleCondition.setMaxLength(80);
    	txtDisThisTime.setMaxLength(80);
    	disableAutoAddLine(getDetailTable());
		disableAutoAddLineDownArrow(getDetailTable());
    }

    public void loadFields()
    {
    	super.loadFields();
    }
    
	protected void btnCan_actionPerformed(ActionEvent e) throws Exception {
		setConfirm(false);
    	disposeUIWindow();
		super.btnCan_actionPerformed(e);
	}

	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeConfirm();
		storeFields();
		
		ContractChangeBillCollection cols = new  ContractChangeBillCollection();
		cols.add(editData);
		
		if (getUIContext().get("ID") != null) {
    		String id = getUIContext().get("ID").toString();
			ContractChangeBillFactory.getRemoteInstance().visa(new IObjectPK[]{new ObjectUuidPK(id)},cols);
    	}
		
		setConfirm(true);
		super.btnConfirm_actionPerformed(e);
		actionCancel_actionPerformed(e);
	}
    
	public void setConfirm(boolean isOk) {
		this.isOk = isOk;
		disposeUIWindow();
	}
	
	private void checkBeforeConfirm() throws Exception{
		FDCClientVerifyHelper.verifyRequire(this);
		for(int i= 0;i<getDetailTable().getRowCount();i++){
			if(getDetailTable().getCell(i, "isAllExe").getValue().equals(Boolean.FALSE)&&
					getDetailTable().getCell(i, "isPartExe").getValue().equals(Boolean.FALSE)&&
					getDetailTable().getCell(i, "isNoExe").getValue().equals(Boolean.FALSE)){
				MsgBox.showError(this,ChangeAuditUtil.getRes("noCondition"));
				SysUtil.abort();
			}
		}
//		if(radioCondition.getValue()==-1){
//			MsgBox.showError(this,"执行情况不能为空！");
//			SysUtil.abort();
//		}
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return new ContractChangeEntryInfo();
	}

	protected KDTable getDetailTable() {
		return kdtEntrys;
	}

	protected IObjectValue createNewData() {
		return new ContractChangeBillInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractChangeBillFactory.getRemoteInstance();
	}
	
	public boolean isModify()
	{
		return false;
	}
	
	public void onShow() throws Exception
    {
        super.onShow();
        getDetailTable().getColumn(1).getStyleAttributes().setLocked(true);
        for(int i= 0;i<getDetailTable().getRowCount();i++){
        	final int j = i;
	        final KDCheckBox cb = new KDCheckBox();
	        cb.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if(cb.isSelected()){
						getDetailTable().getCell(j,"isPartExe").setValue(Boolean.FALSE);
						getDetailTable().getCell(j,"isNoExe").setValue(Boolean.FALSE);
					}
				}
	        });
	        getDetailTable().getCell(i,"isAllExe").setEditor(new KDTDefaultCellEditor(cb));
	        final KDCheckBox cc = new KDCheckBox();
	        cc.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if(cc.isSelected()){
						getDetailTable().getCell(j,"isAllExe").setValue(Boolean.FALSE);
						getDetailTable().getCell(j,"isNoExe").setValue(Boolean.FALSE);
					}
				}
	        });
	        getDetailTable().getCell(i,"isPartExe").setEditor(new KDTDefaultCellEditor(cc));
	        final KDCheckBox cd = new KDCheckBox();
	        cd.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if(cd.isSelected()){
						getDetailTable().getCell(j,"isPartExe").setValue(Boolean.FALSE);
						getDetailTable().getCell(j,"isAllExe").setValue(Boolean.FALSE);
					}
				}
	        });
	        getDetailTable().getCell(i,"isNoExe").setEditor(new KDTDefaultCellEditor(cd));
	        KDTextField txt = new KDTextField();
	        txt.setMaxLength(80);
	        getDetailTable().getCell(i, "discription").setEditor(new KDTDefaultCellEditor(txt));
	    }
    }
	
	public void initLayout() {
		this.initUIContentLayout();
	}
	
	/**
	 * 必须要取出number属性，如果不加这行，editData的number为空，
	 * 当启用了编码规则，保存时会生成新的编码替换之前的编码
	 * Added by Owen_wen 2010-09-29
	 */
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sic = super.getSelectors();
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("CU.id"));
        
        // 如果不加这行，editData的number为空，当启用了编码规则，保存时会生成新的编码替换之前的编码
        sic.add(new SelectorItemInfo("number")); 
    	return sic;
    }
    
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
    	ContractChangeBillFactory.getRemoteInstance().confirmExecute(editData.getId());
    }
}