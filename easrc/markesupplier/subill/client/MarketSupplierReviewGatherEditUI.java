/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subill.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.core.fm.ClientVerifyHelper;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.hr.emp.client.EmployeeMultiF7PromptBox;
import com.kingdee.eas.port.markesupplier.subase.AppraiseTypeEnum;
import com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAreaFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateEntryCollection;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateEntryFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateEntryInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeFactory;
import com.kingdee.eas.port.markesupplier.subase.SupplierState;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonCollection;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryPersonFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketSupplierReviewGatherEditUI extends AbstractMarketSupplierReviewGatherEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierReviewGatherEditUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierReviewGatherEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	this.prmtEntry.setEnabledMultiSelection(true);
    	setEnable();
    	super.onLoad();
    	
    	initButton();
    	
    	setF7Filter();
    	
    	setRequiredState();
    }
    
    public void loadFields() {
    	detachListeners();
    	super.loadFields();
    	attachListeners();
    	try {
			setEntryScorePass();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    
    public void storeFields() {
    	if(this.prmtEntry.getValue() instanceof Object[])
    	{
    		StringBuffer sb = new StringBuffer();
    		this.txtsavePerson.setText(null);
    		Object[] obj = (Object[]) this.prmtEntry.getValue();
    		for (int i = 0; i < obj.length; i++) 
    		{
				if(!"".equals(sb.toString().trim()))
				{
					sb.append("#"+((PersonInfo)obj[i]).getId().toString());
				}
				else
				{
					sb.append(((PersonInfo)obj[i]).getId().toString());
				}
			}
    		this.txtsavePerson.setText(sb.toString());
    	}
    	super.storeFields();
    }
    
    private void setRequiredState() 
    {
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
    	this.contState.setVisible(false);
    	this.contsavePerson.setVisible(false);
    	this.contorgUnit.setVisible(false);
		this.txtNumber.setRequired(true);
		this.prmtSupplier.setRequired(true);
		this.prmtEvaluationType.setRequired(true);
		this.prmtTemplate.setRequired(true);
		this.prmtEntry.setRequired(true);
		this.pkBizDate.setRequired(true);
		this.kdtEntrys.getColumn("contract").setRequired(true);
		this.kdtEntrys.getColumn("contractAmount").getStyleAttributes().setLocked(false);
		this.kdtEntryPs.getColumn("guideName").getStyleAttributes().setLocked(true);
		this.kdtEntryPs.getColumn("guideType").getStyleAttributes().setLocked(true);
		this.kdtEntryPs.getColumn("fullNum").getStyleAttributes().setLocked(true);
		this.kdtEntryPs.getColumn("weight").getStyleAttributes().setLocked(true);
//		this.kdtEntryPs.getColumn("remark").getStyleAttributes().setLocked(true);
		for (int i = 0; i < kdtEntryPs.getRowCount(); i++) {
        	KDTableHelper.autoFitRowHeight(this.kdtEntryPs, i);
		}
	}

	private void setF7Filter() 
	{
		this.actionCopy.setVisible(false);
		this.prmtTemplate.setQueryInfo("com.kingdee.eas.port.markesupplier.subase.app.MarketSupplierAppraiseTemplateQuery");		
        this.prmtTemplate.setEditable(true);		
        this.prmtTemplate.setDisplayFormat("$name$");		
        this.prmtTemplate.setEditFormat("$number$");		
        this.prmtTemplate.setCommitFormat("$number$");		
        this.prmtTemplate.setRequired(false);
		
		EmployeeMultiF7PromptBox person = new EmployeeMultiF7PromptBox(this);
		person.setIsSingleSelect(false);
		person.showNoPositionPerson(false);
		person.setIsShowAllAdmin(true);
		prmtEntry.setSelector(person);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		Set org=new HashSet();
		if(editData.getOrgUnit()!=null){
			org.add(editData.getOrgUnit().getId().toString());
		}
		org.add(OrgConstants.DEF_CU_ID);
		filter.getFilterItems().add(new FilterItemInfo("state",Integer.valueOf(SupplierState.AUDIT_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id",org,CompareType.INCLUDE));
		view.setFilter(filter);
		this.prmtSupplier.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtEvaluationType.setEntityViewInfo(view);
		this.prmtTemplate.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
		if(this.prmtEvaluationType.getValue()!=null)
		{
			filter.getFilterItems().add(new FilterItemInfo("AccreditationType.id",((MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue()).getId()));
		}
		view.setFilter(filter);
		this.prmtTemplate.setEntityViewInfo(view);
	}

	private void initButton() 
	{
		MarketSupplierStockEditUI.initTableButton(kDContainer1, kdtEntrys_detailPanel);
		MarketSupplierStockEditUI.initTableButton(kDContainer2, kdtEntryPs_detailPanel);
		kdtEntryPs_detailPanel.getAddNewLineButton().setVisible(false);
		kdtEntryPs_detailPanel.getInsertLineButton().setVisible(false);
		kdtEntryPs_detailPanel.getRemoveLinesButton().setVisible(false);
		
		this.kDContainer1.getContentPane().add(this.kdtEntrys,BorderLayout.CENTER);
    	this.kDContainer2.getContentPane().add(this.kdtEntryPs,BorderLayout.CENTER);
    	
    	KDTextArea indexValue_TextField = new KDTextArea();
		indexValue_TextField.setAutoAdjustCaret(true);
		KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(indexValue_TextField);
		this.kdtEntryPs.getColumn("remark").getStyleAttributes().setWrapText(true);
		this.kdtEntryPs.getColumn("fullNum").getStyleAttributes().setWrapText(true);
		this.kdtEntryPs.getColumn("remark").setEditor(indexValue_CellEditor);
		this.kdtEntryPs.getColumn("remark").setRequired(true);
		
    	this.actionCreateTo.setVisible(false);//推式生成
		this.actionCreateFrom.setVisible(false);//拉式生成
		this.actionWorkFlowG.setVisible(false);//流程图
		this.actionTraceUp.setVisible(false);//上查
		this.actionTraceDown.setVisible(false);//下查 
		this.actionFirst.setVisible(false);//第一
		this.actionRemove.setVisible(false);//第一
		this.actionPre.setVisible(false);//前一
		this.actionNext.setVisible(false);//后一
		this.actionLast.setVisible(false);//最后一个
		this.actionAuditResult.setVisible(false);//审批结果查看
		this.actionMultiapprove.setVisible(false);//多级审批
		this.actionNextPerson.setVisible(false);//下一步处理人
		
		if(editData.getId()!=null)
		{
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(true);
		}
		else
		{
			this.actionSave.setEnabled(true);
			this.actionSubmit.setEnabled(true);
		}
	}

	private void setEnable() 
	{
		this.State.setEnabled(false);
		this.txtLinkJob.setEnabled(false);
	}

	protected void prmtEntry_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtEntry_dataChanged(e);
    }
    
    protected void prmtEvaluationType_dataChanged(DataChangeEvent e)
    		throws Exception {
    	super.prmtEvaluationType_dataChanged(e);
    	if(this.prmtSupplier.getValue()!=null&&this.prmtEvaluationType.getValue()!=null)
    	{
        }
    	else
    	{
			this.prmtTemplate.setValue(null);
		}
    	this.txtamount.setValue(this.kdtEntryPs.getFootManager()!=null?this.kdtEntryPs.getFootManager().getFootRow(0).getCell("score").getValue():BigDecimal.ZERO);
    }
    
    protected void prmtSupplier_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtSupplier_dataChanged(e);
    	 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged||this.prmtSupplier.getValue()==null)
         {
        	 return;
         }
         MarketSupplierStockInfo infos=(MarketSupplierStockInfo) this.prmtSupplier.getValue();
         if(infos!=null)
         {
        	 MarketSupplierStockInfo info = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(infos.getId()));
        	 if(info.getInviteType()!=null)
        	 {
        		 this.txtInviteType.setText(SupplierInvoiceTypeTreeFactory.getRemoteInstance().getSupplierInvoiceTypeTreeInfo(new ObjectUuidPK(info.getInviteType().getId())).getName());
        	 }
        	 else
        	 {
        		 this.txtInviteType.setText(null);
        	 }
        	 
        	 if(info.getSupplierSplAreaEntry()!=null)
        	 {
        		 this.txtSplArea.setText(MarketSplAreaFactory.getRemoteInstance().getMarketSplAreaInfo(new ObjectUuidPK(info.getSupplierSplAreaEntry().getId())).getName());
        	 }
        	 else
        	 {
        		 this.txtSplArea.setText(null);
        	 }
        	
        	 String entryOql = "select * where parent.id ='"+info.getId().toString()+"' and isDefault='1'";
        	 MarketSupplierStockEntryPersonCollection entryColl = MarketSupplierStockEntryPersonFactory.getRemoteInstance().getMarketSupplierStockEntryPersonCollection(entryOql);
 			 if(entryColl.size()>0)
 			 {
 				 this.txtLinkPerson.setText(entryColl.get(0).getPersonName());
 				 this.txtLinkPhone.setText(entryColl.get(0).getPhone());
 			 	 this.txtLinkJob.setText(entryColl.get(0).getPosition());
 			 }
 			 else
 			 {
 				 this.txtLinkPerson.setText(null);
 				 this.txtLinkPhone.setText(null);
 				 this.txtLinkJob.setText(null);
 			 }
         }
    }
    
    protected void prmtTemplate_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtTemplate_dataChanged(e);

		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        MarketSupplierAppraiseTemplateInfo info=(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue();
        boolean isShowWarn=false;
        boolean isUpdate=false;
        if(this.kdtEntryPs.getRowCount()>0){
        	isShowWarn=true;
        }
        if(isShowWarn){
        	if(MsgBox.showConfirm2(this, "评审模板改变会覆盖供应商评审数据，是否继续？")== MsgBox.YES){
        		isUpdate=true;
            }
        }else{
        	isUpdate=true;
        }
        if(isUpdate){
        	this.kdtEntryPs.removeRows();
        	if(info!=null){
//        		this.storeFields();
            	info=MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(new ObjectUuidPK(info.getId()));
        		MarketSupplierAppraiseTemplateEntryCollection guideEntry=info.getEntry();
        		for(int i=0;i<guideEntry.size();i++){
        			MarketSupplierAppraiseTemplateEntryInfo e1info = MarketSupplierAppraiseTemplateEntryFactory.getRemoteInstance()
        			.getMarketSupplierAppraiseTemplateEntryInfo(new ObjectUuidPK(((MarketSupplierAppraiseTemplateEntryInfo)guideEntry.get(i)).getId()));
        			IRow  irow = kdtEntryPs.addRow();
        			irow.getCell("guideType").setValue(e1info.getAccreditationwd());
        			if(e1info.getIndexName()!=null){
        				irow.getCell("guideName").setValue(MarketSplAuditIndexFactory.getRemoteInstance().getMarketSplAuditIndexInfo(new ObjectUuidPK(e1info.getIndexName().getId())));
        			}
        			irow.getCell("fullNum").setValue(e1info.getThreeStandard());
        			irow.getCell("weight").setValue(e1info.getQz());
        		}
        	}
        	getFootRow(this.kdtEntryPs,new String[]{"weight"});
        	setEntryScorePass();
        	BigDecimal score =getTotalScore(this.kdtEntryPs,"score","weight");
        	IRow footRow = null;
        	KDTFootManager footRowManager = kdtEntryPs.getFootManager();
        	if(footRowManager == null)
        	{
	        	String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
	        	footRowManager = new KDTFootManager(kdtEntryPs);
	        	footRowManager.addFootView();
	        	kdtEntryPs.setFootManager(footRowManager);
	        	footRow = footRowManager.addFootRow(0);
	        	footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
	        	kdtEntryPs.getIndexColumn().setWidthAdjustMode((short)1);
	        	kdtEntryPs.getIndexColumn().setWidth(30);
	        	footRowManager.addIndexText(0, total);
        	} else{
        		footRow = footRowManager.getFootRow(0);
        	}
        	footRow.getCell("score").setValue(score);
        }
	
    }
    
	public void setEntryScorePass() throws EASBizException, BOSException{
    	Map map = new HashMap();
        if(this.prmtTemplate.getValue()!=null){
			if(this.prmtTemplate.getValue()!=null){
				String tempID = ((MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue()).getId().toString();
				try {
					String oql = "select id where id='"+tempID+"' ";
					MarketSupplierAppraiseTemplateInfo  tempinfo =MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(oql);
					MarketSupplierAppraiseTemplateEntryCollection e1col = tempinfo.getEntry();
					for (int i = 0; i < e1col.size(); i++) {
						MarketSupplierAppraiseTemplateEntryInfo e1info = MarketSupplierAppraiseTemplateEntryFactory.getRemoteInstance()
						.getMarketSupplierAppraiseTemplateEntryInfo(new ObjectUuidPK(((MarketSupplierAppraiseTemplateEntryInfo)e1col.get(i)).getId()));
						map.put(e1info.getIndexName().getId().toString(), e1info.getId().toString());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		for(int i=0;i<this.kdtEntryPs.getRowCount();i++){
			MarketSplAuditIndexInfo entry=(MarketSplAuditIndexInfo) this.kdtEntryPs.getRow(i).getCell("guideName").getValue();
			MarketSupplierAppraiseTemplateEntryInfo  E1entry = null;
			String evalType = entry.getId().toString();
			try {
				if(map.get(evalType)!=null){
					E1entry = MarketSupplierAppraiseTemplateEntryFactory.getRemoteInstance()
					.getMarketSupplierAppraiseTemplateEntryInfo(new ObjectUuidPK((String)map.get(evalType)));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(E1entry!=null&&AppraiseTypeEnum.PASS.equals(E1entry.getScoreType())){
				this.kdtEntryPs.getRow(i).getCell("score").getStyleAttributes().setLocked(true);
				this.kdtEntryPs.getRow(i).getCell("isPass").getStyleAttributes().setLocked(false);
				this.kdtEntryPs.getRow(i).getCell("isPass").getStyleAttributes().setBackground(new Color(0xFCFBDF));
			}else{
				this.kdtEntryPs.getRow(i).getCell("isPass").getStyleAttributes().setLocked(true);
				this.kdtEntryPs.getRow(i).getCell("score").getStyleAttributes().setLocked(false);
				this.kdtEntryPs.getRow(i).getCell("score").getStyleAttributes().setBackground(new Color(0xFCFBDF));
			}
		}
		
		BigDecimal score =getTotalScore(this.kdtEntryPs,"score","weight");
    	IRow footRow = null;
    	KDTFootManager footRowManager = kdtEntryPs.getFootManager();
    	if(footRowManager == null)
    	{
        	String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
        	footRowManager = new KDTFootManager(kdtEntryPs);
        	footRowManager.addFootView();
        	kdtEntryPs.setFootManager(footRowManager);
        	footRow = footRowManager.addFootRow(0);
        	footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
        	kdtEntryPs.getIndexColumn().setWidthAdjustMode((short)1);
        	kdtEntryPs.getIndexColumn().setWidth(30);
        	footRowManager.addIndexText(0, total);
    	} else{
    		footRow = footRowManager.getFootRow(0);
    	}
    	footRow.getCell("score").setValue(score);
    	
    	if(UIRuleUtil.isNotNull(this.txtsavePerson.getText()))
    	{
    		String perosnId[] = this.txtsavePerson.getText().split("#");
    		PersonInfo person[] = new PersonInfo[perosnId.length];
    		IPerson IPerosn = PersonFactory.getRemoteInstance();
    		for (int i = 0; i < perosnId.length; i++) 
    		{
    			person[i] = IPerosn.getPersonInfo(new ObjectUuidPK(perosnId[i]));
			}
    		this.prmtEntry.setValue(person);
    	}
    	for (int i = 0; i < kdtEntryPs.getRowCount(); i++) {
        	KDTableHelper.autoFitRowHeight(this.kdtEntryPs, i);
		}
    }
	
	protected void attachListeners() {
		addDataChangeListener(this.prmtSupplier);
		addDataChangeListener(this.prmtEvaluationType);
		addDataChangeListener(this.prmtEntry);
		addDataChangeListener(this.prmtTemplate);
		addDataChangeListener(this.prmtEvaluationType);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtSupplier);
		removeDataChangeListener(this.prmtEvaluationType);
		removeDataChangeListener(this.prmtEntry);
		removeDataChangeListener(this.prmtTemplate);
		removeDataChangeListener(this.prmtEvaluationType);
	}
    
	//监听器
	protected Map listenersMap = new HashMap();
    
    protected void addDataChangeListener(JComponent com) {
    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
		
    }
        
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
	
	public static void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }

        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	
    /**
     * 给指定table数字列求和
     * 
     * @param table 表格
     * @param columnName 表格列名
     */
    public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null ){
        		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
            		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof String){
            		String value = (String)table.getRow(i).getCell(columnName).getValue();
            		if(value.indexOf("零")==-1 && value.indexOf("[]")==-1){
            			value = value.replaceAll(",", "");
                		sum = sum.add(new BigDecimal(value));
            		}
            	}
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
            		String value = table.getRow(i).getCell(columnName).getValue().toString();
            		sum = sum.add(new BigDecimal(value));
            	}
        	}
        }
        return sum;
    }
    
    public static BigDecimal getTotalScore(KDTable kdtEntry,String scoreName,String weightName){
		BigDecimal sum = new BigDecimal(0);
		for(int i=0;i<kdtEntry.getRowCount();i++){
			IRow row=kdtEntry.getRow(i);
			if(row.getCell(scoreName).getValue() instanceof BigDecimal){
				BigDecimal score=row.getCell(scoreName).getValue()!=null?(BigDecimal)row.getCell(scoreName).getValue():BigDecimal.ZERO;
				BigDecimal weight=row.getCell(weightName).getValue()!=null?(BigDecimal)row.getCell(weightName).getValue():BigDecimal.ZERO;
				if(score!=null&&weight!=null){
					sum=sum.add(score.multiply(weight).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		sum=sum.multiply(new BigDecimal(100).divide(new BigDecimal(5), 2, BigDecimal.ROUND_HALF_UP));
		return sum;
	}
    
    protected void kdtEntryPs_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtEntryPs_editStopped(e);

		if(e.getRowIndex()!=-1)
			KDTableHelper.autoFitRowHeight(this.kdtEntryPs, e.getRowIndex());
		if(this.kdtEntryPs.getColumnKey(e.getColIndex()).equals("score")){
			IRow footRow = null;
			KDTFootManager footRowManager = kdtEntryPs.getFootManager();
			if(footRowManager == null)
			{
				String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
				footRowManager = new KDTFootManager(kdtEntryPs);
				footRowManager.addFootView();
				kdtEntryPs.setFootManager(footRowManager);
				footRow = footRowManager.addFootRow(0);
				footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
				kdtEntryPs.getIndexColumn().setWidthAdjustMode((short)1);
				kdtEntryPs.getIndexColumn().setWidth(30);
				footRowManager.addIndexText(0, total);
			} else{
				footRow = footRowManager.getFootRow(0);
			}
			BigDecimal a =UIRuleUtil.getBigDecimal(this.kdtEntryPs.getCell(e.getRowIndex(), "score").getValue());
			if(a.compareTo(new BigDecimal("5"))==1||a.compareTo(BigDecimal.ZERO)==-1)
			{
				this.kdtEntryPs.getCell(e.getRowIndex(), "score").setValue(BigDecimal.ZERO);
				this.txtamount.setValue(BigDecimal.ZERO);
				footRow.getCell("score").setValue(BigDecimal.ZERO);
				SysUtil.abort();
			}
			BigDecimal score =getTotalScore(this.kdtEntryPs,"score","weight");
	    	if(score.compareTo(new BigDecimal("100")) >0){
	    		MsgBox.showWarning(this, "综合得分之和不能大于100！");
	    		this.kdtEntryPs.getRow(e.getRowIndex()).getCell("score").setValue(null);
	    	}
        	footRow.getCell("score").setValue(score.setScale(2, BigDecimal.ROUND_HALF_UP));
	    	this.txtamount.setValue(score.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
    }
    
    protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtEntrys_editStopped(e);
		DecimalFormat df = new DecimalFormat("##.00");
		if(this.kdtEntrys.getColumnKey(e.getColIndex()).equals("contractPrice")){
			double contractAmount = UIRuleUtil.getBigDecimalValue(this.kdtEntrys.getRow(e.getRowIndex()).getCell("contractAmount").getValue());
			double contractPrice = UIRuleUtil.getBigDecimalValue(this.kdtEntrys.getRow(e.getRowIndex()).getCell("contractPrice").getValue());
			this.kdtEntrys.getRow(e.getRowIndex()).getCell("Quantity").setValue(df.format(contractAmount/contractPrice));
		}else if(this.kdtEntrys.getColumnKey(e.getColIndex()).equals("Quantity")){
			double contractAmount = UIRuleUtil.getBigDecimalValue(this.kdtEntrys.getRow(e.getRowIndex()).getCell("contractAmount").getValue());
			double contractPrice = UIRuleUtil.getBigDecimalValue(this.kdtEntrys.getRow(e.getRowIndex()).getCell("Quantity").getValue());
			this.kdtEntrys.getRow(e.getRowIndex()).getCell("contractPrice").setValue(df.format(contractAmount/contractPrice));
		}else if(this.kdtEntrys.getColumnKey(e.getColIndex()).equals("contractAmount")){
			this.kdtEntrys.getRow(e.getRowIndex()).getCell("Quantity").setValue(null);
			this.kdtEntrys.getRow(e.getRowIndex()).getCell("contractPrice").setValue(null);
		}
	
    }
    
    void verifyInputForSubmit() throws EASBizException, BOSException
    {
    	verifyInputForSave();
    	Map map = new HashMap();
		if(this.prmtTemplate.getValue()!=null){
			if(this.prmtTemplate.getValue()!=null){
				String tempID = ((MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue()).getId().toString();
				try {
					String oql = "select id where id='"+tempID+"' ";
					MarketSupplierAppraiseTemplateInfo  tempinfo =MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(oql);
					MarketSupplierAppraiseTemplateEntryCollection e1col = tempinfo.getEntry();
					for (int i = 0; i < e1col.size(); i++) {
						MarketSupplierAppraiseTemplateEntryInfo e1info = MarketSupplierAppraiseTemplateEntryFactory.getRemoteInstance()
						.getMarketSupplierAppraiseTemplateEntryInfo(new ObjectUuidPK(((MarketSupplierAppraiseTemplateEntryInfo)e1col.get(i)).getId()));
						map.put(e1info.getIndexName().getId().toString(), e1info.getId().toString());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
    	for (int i = 0; i < this.kdtEntryPs.getRowCount(); i++) {
			IRow row=this.kdtEntryPs.getRow(i);
			MarketSplAuditIndexInfo entry=(MarketSplAuditIndexInfo) row.getCell("guideName").getValue();
			MarketSupplierAppraiseTemplateEntryInfo  E1entry = null;
			String evalType = entry.getId().toString();
			if(map.get(evalType)!=null){
				E1entry = MarketSupplierAppraiseTemplateEntryFactory.getRemoteInstance()
				.getMarketSupplierAppraiseTemplateEntryInfo(new ObjectUuidPK((String)map.get(evalType)));
			}
			if(UIRuleUtil.isNull(row.getCell("remark").getValue())){
				MsgBox.showWarning(this,"情况描述不能为空！");
				this.kdtEntryPs.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntryPs.getColumnIndex("remark"));
				SysUtil.abort();
			}
			if(E1entry!=null&&AppraiseTypeEnum.WEIGHT.equals(E1entry.getScoreType())){
				if(row.getCell("score").getValue()==null){
					MsgBox.showWarning(this,"供应商评审综合得分不能为空！");
					this.kdtEntryPs.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntryPs.getColumnIndex("score"));
					SysUtil.abort();
				}
			}else{
				if(row.getCell("isPass").getValue()==null){
					MsgBox.showWarning(this,"供应商评审合格与否不能为空！");
					this.kdtEntryPs.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntryPs.getColumnIndex("isPass"));
					SysUtil.abort();
				}
			}
		}
    }
    
    void verifyInputForSave()
    {
		ClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		ClientVerifyHelper.verifyEmpty(this, this.prmtSupplier);
		ClientVerifyHelper.verifyEmpty(this, this.prmtEvaluationType);
		ClientVerifyHelper.verifyEmpty(this, this.prmtTemplate);
		ClientVerifyHelper.verifyEmpty(this, this.prmtEntry);
		ClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
//		ClientVerifyHelper.verifyInput(this, this.kdtEntrys, "contract");
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	verifyInputForSave();
    	super.actionSave_actionPerformed(e);
    	if(editData.getId()!=null)
		{
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(true);
		}
		else
		{
			this.actionSave.setEnabled(true);
			this.actionSubmit.setEnabled(true);
		}
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	verifyInputForSubmit();
    	if(this.State.getSelectedItem().equals(SupplierState.audit))
    	{
    		MsgBox.showWarning("已审批，不能提交！");SysUtil.abort();
    	}
    	super.actionSubmit_actionPerformed(e);
    	if(editData.getId()!=null)
		{
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(true);
		}
		else
		{
			this.actionSave.setEnabled(true);
			this.actionSubmit.setEnabled(true);
		}
    	this.setOprtState("VIEW");
    	loadData();
    }
    
    public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
    	if(this.State.getSelectedItem().equals(SupplierState.audit))
    	{
    		MsgBox.showWarning("已审批，不能修改！");SysUtil.abort();
    	}
    	super.actionEdit_actionPerformed(arg0);
    	if(editData.getId()!=null)
		{
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(true);
		}
		else
		{
			this.actionSave.setEnabled(true);
			this.actionSubmit.setEnabled(true);
		}
    }
    
    public void actionRemove_actionPerformed(ActionEvent arg0) throws Exception {
    	if(this.State.getSelectedItem().equals(SupplierState.audit))
    	{
    		MsgBox.showWarning("已审批，不能删除！");SysUtil.abort();
    	}
    	super.actionRemove_actionPerformed(arg0);
    }
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherFactory.getRemoteInstance();
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
        com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo objectValue = new com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        if(getUIContext().get("org")!=null){
			try {
				PurchaseOrgUnitInfo orgUnitInfo = PurchaseOrgUnitFactory.getRemoteInstance().getPurchaseOrgUnitInfo(new ObjectUuidPK(((OrgStructureInfo) getUIContext().get("org")).getUnit().getId()));
				objectValue.setOrgUnit(orgUnitInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
        return objectValue;
    }

}