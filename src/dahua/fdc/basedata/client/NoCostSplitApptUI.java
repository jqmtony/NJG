/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class NoCostSplitApptUI extends AbstractNoCostSplitApptUI
{
    private static final Logger logger = CoreUIObject.getLogger(NoCostSplitApptUI.class);
    //private ContractCostSplitInfo costSplit = null;
    protected FDCNoCostSplitBillEntryCollection costSplit = null;
    protected String costSplitAcctNo = null;
    protected String entryClassName = null;

    protected CostSplitTypeEnum costSplitType = null;
    
    protected ApportionTypeCollection apportionTypeCollection = null;	

	
    protected boolean isOk = false;
    
    protected final int COLISSELECTED = 1;
    protected final int COLAPPORTOBJ = 2;
    
    //动态加载行号和列号的起始值
    protected int rowBase=1;
    protected int colBase=3;
    
    //分摊类型选择行
    protected int idxRowApptSel=0;
    
    //分摊对象和分摊类型的ID
    protected String apptObjIds[];
    protected String apptTypeIds[];
    
    public NoCostSplitApptUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        super.kdtEntrys_editStopped(e);
    }

    /**
     * output kdtEntrys_editValueChanged method
     */
    protected void kdtEntrys_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        super.kdtEntrys_editValueChanged(e);
    }

    /**
     * output kdtEntrys_tableClicked method
     */
    protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.kdtEntrys_tableClicked(e);
    }

    /**
     * output btnCancel2_actionPerformed method
     */
    protected void btnCancel2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnCancel2_actionPerformed(e);
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnConfirm_actionPerformed(e);
    }

    /**
     * output actionSelectAll_actionPerformed
     */
    public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSelectAll_actionPerformed(e);
		
		//String id=null;
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			//id=kdtEntrys.getCell(i,"id").getValue().toString();
			if(kdtEntrys.getCell(i,"id").getValue()!=null){
				kdtEntrys.getCell(i,"isSelected").setValue(Boolean.TRUE);					
			}		
		}
    }

    /**
     * output actionSelectNone_actionPerformed
     */
    public void actionSelectNone_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSelectNone_actionPerformed(e);
		
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			kdtEntrys.getCell(i,"isSelected").setValue(Boolean.FALSE);			
		}
    }

    /**
     * output actionSplit_actionPerformed
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSplit_actionPerformed(e);
		
		/*//检查是否选择分摊对象
    	boolean isSelected=false;		
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
    		if(kdtEntrys.getCell(i,"isSelected").getValue().equals(Boolean.TRUE)){
    			isSelected=true;
    			break;
    		}
		}
		if(!isSelected){
			return;
		}*/
		
    	
    	confirm();
    }

	protected IObjectValue createNewData() {
		// TODO 自动生成方法存根
		//return null;

        costSplit = (FDCNoCostSplitBillEntryCollection)getUIContext().get("costSplit");
		costSplitType=(CostSplitTypeEnum)getUIContext().get("splitType");
		entryClassName=(String)getUIContext().get("entryClass");
		
		costSplitAcctNo=costSplit.get(0).getAccountView().getNumber();
		
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		//return null;


		//return com.kingdee.eas.fdc.contract.ContractCostSplitFactory.getRemoteInstance();
		return com.kingdee.eas.fdc.finance.PaymentSplitFactory.getRemoteInstance();
	}

	

	protected void initTable() throws Exception{
		
	}
	


	/* （非 Javadoc）
	 * @see com.kingdee.eas.framework.client.EditUI#onShow()
	 */
	public void onShow() throws Exception {
		//清空表格设置
    	String className=this.getClass().getName();
    	String sql = "delete t_fw_usercustomconfig where fuiclassname='"+className+"'";
		logger.info("sql:"+sql);
		try {
			FDCSQLFacadeFactory.getRemoteInstance().executeUpdate(sql, null);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		}
		super.onShow();
		
		initTable();	 
		
		loadPreSplit();
    	kdtEntrys.getColumn("directAmount").setEditor(FDCSplitClientHelper.getTotalCellNumberEdit());
    	
		kdtEntrys.setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e)
			{
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					for (int i = 0; i < kdtEntrys.getSelectManager().size(); i++)
					{
						KDTSelectBlock block = kdtEntrys.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
								int directAmount_index=kdtEntrys.getColumnIndex("directAmount");
								if(colIndex!=directAmount_index) {
									e.setCancel(true);
									continue;
								}
								try
								{
									kdtEntrys.getCell(rowIndex, colIndex).setValue(FDCHelper.ZERO);
									kdtEntrys_editStopped(new KDTEditEvent(e.getSource(), null, FDCHelper.ZERO, 
											rowIndex, colIndex,false,1));
								} catch (Exception e1) {
									logger.error(e1.getMessage(), e1);
									handUIExceptionAndAbort(e1);
								}
							}
//							e.setCancel(true);
						}
					}

				}
				
			}
		});
		
	}
	


	/* （非 Javadoc）
	 * @see com.kingdee.eas.framework.client.EditUI#loadData()
	 */
	protected void loadData() throws Exception {
		// TODO 自动生成方法存根
		super.loadData();

		btnSplit.setEnabled(true);
		btnSplit.setIcon(EASResource.getIcon("imgTbtn_collect"));
		btnSelectAll.setEnabled(true);
		btnSelectAll.setIcon(EASResource.getIcon("imgTbtn_selectall"));
		btnSelectNone.setEnabled(true);
		btnSelectNone.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
	}
	
	
    
    protected void confirm() throws Exception {
		//checkSelected();
    	
    	//getData();
    	setConfirm(true);
	}

    public FDCNoCostSplitBillEntryCollection getData() throws Exception {    	
		disposeUIWindow();
		
		return null;
	}

    public boolean isOk() {
    	return isOk;
    }

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;

		disposeUIWindow();
	}
	

	
	protected void loadPreSplit(){
		
	}
	
	protected FDCNoCostSplitBillEntryInfo createNewEntryData(){
		//String clsName="com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo";
		String clsName=entryClassName;
		
		FDCNoCostSplitBillEntryInfo entry=null;
		
		Class c = null;
		
		try {
			c = Class.forName(clsName);
		} catch (ClassNotFoundException e) {
			handUIExceptionAndAbort(e);
		}
		
		try {
			entry = (FDCNoCostSplitBillEntryInfo) c.newInstance();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);		
		}
		
		return entry;
	}
}
