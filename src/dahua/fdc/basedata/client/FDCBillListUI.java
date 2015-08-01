/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.ITextIconDisplayStyle;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.util.FdcOrgUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class FDCBillListUI extends AbstractFDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCBillListUI.class);
    
	public static final String COL_PERIOD= "period";
	public static final String COL_PERIODNUMBER= "period.number";
	public static final String COL_DATE = "bookedDate";
	
	//���óɱ��½�
	protected boolean isIncorporation = false;
	//���ò���ɱ�һ�廯
	protected boolean isFinacial = false;
	
	protected FullOrgUnitInfo orgUnit = null;
	
	/** ������֯ */
	protected CompanyOrgUnitInfo company = null;
	
	private String titleMain = null;
	
    /**
     * output class constructor
     */
    public FDCBillListUI() throws Exception
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
//    	checkIsInWorkflow();
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.getClass().getName().equalsIgnoreCase("com.kingdee.eas.fdc.contract.client.contractBillListUI")) {
    		fdcDataImport();
    	}else{    	
    		super.actionImportData_actionPerformed(e);
    	}

    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAttachment_actionPerformed(e);
    	boolean isEdit=false;
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
        if(getBillStatePropertyName()!=null){
        	int rowIdx=getMainTable().getSelectManager().getActiveRowIndex();
        	ICell cell = getMainTable().getCell(rowIdx, getBillStatePropertyName());
        	Object obj=cell.getValue();
        	if(obj!=null&&
        			(obj.toString().equals(FDCBillStateEnum.SAVED.toString())
        			||obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
        			||obj.toString().equals(BillStatusEnum.SAVE.toString())
        			||obj.toString().equals(BillStatusEnum.SUBMIT.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITING.toString()))){
        		isEdit=true;
        	}else{
        		isEdit=false;
        	}
			
        }
        acm.showAttachmentListUIByBoID(boID, this,isEdit);
        
        /**
        * ����ʼ�����  ʵ�ִ�ҵ���ܴ��ڷ��գ��ʴ˱���������������  by Cassiel_peng 
        * ����������Ҫ����ϵͳ�����ľ���ֵ��ѡ��
        * ����ֵΪ�ǣ����ñʼ�����     Ϊ�񣺱���ϵͳԭ���߼�����
        *//*
       //����ID���ʵ���BOSType
        BOSObjectType bosType = BOSUuid.read(boID).getType();  // boID �� �븽�������� ҵ������ ID
        //����BOSType���ʵ����ýӿ�
        ICoreBase coreBase = (ICoreBase) BOSObjectFactory.createRemoteBOSObject(bosType, ICoreBase.class);
        //��øö���
        CoreBaseInfo objectInfo = coreBase.getValue(new ObjectUuidPK(boID));  
        if(objectInfo instanceof FDCBillInfo){
    	   boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
    	   if(!isUseWriteMark){//����ϵͳ��׼������������������
    	   }else{
    		   UIContext uiContext = new UIContext();
			   uiContext.put("billInfo",objectInfo);
			   uiContext.put("optState", oprtState);
	   		   if(isEdit){
	   				uiContext.put("optState", OprtState.VIEW);	
	   		   }
	   		  uiContext.put(UIContext.OWNER, this);
			  IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractContentUI.class.getName(), uiContext);
			  window.show();
    	   }
       }else{
    	   acm.showAttachmentListUIByBoID(boID, this,isEdit);
       }*/
    }
    
    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();

		checkBillState(getBillStateEnum(), "selectRightRowForCancel");
		
		checkBeforeCancel();
		
		int confirm = MsgBox.showConfirm2(this, "ȷ����ֹ? �ò������ɳ�����");
		if(confirm == MsgBox.OK) {
			cancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
			
			showOprtOKMsgAndRefresh();
		}
    }
    
    protected String[] getBillStateEnum(){
    	return new String[]{FDCBillStateEnum.AUDITTED_VALUE};
    }
    /**
     * ����
     * @param ids
     * @throws Exception
     */
    protected void cancel(List ids) throws Exception{}
    
    /**
     * ��Ч
     * @param ids
     * @throws Exception
     */
    protected void cancelCancel(List ids) throws Exception{}

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();

		checkBillState(new String[]{FDCBillStateEnum.CANCEL_VALUE}, "selectRightRowForCancelCancel");

		cancelCancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
	
		showOprtOKMsgAndRefresh();
    }
    
    /**
     * ����ǰ��ҵ����
     * @throws Exception
     */
    protected void checkBeforeCancel() throws Exception{
    	
    }
    
    
    /**
	 * 
	 * �������޸�ǰ��鵥�Ƿ��ڹ�������
	 * 
	 */
	protected void checkIsInWorkflow() throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		
		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			
//			��鵥���Ƿ��ڹ�������
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
		}
	}
	
    /**
	 * 
	 * ��������鵥��״̬
	 * 
	 * @param states
	 *            ״̬
	 * @param res
	 *            ��ʾ��Ϣ��Դ����
	 * @throws BOSException
	 * @author:liupd ����ʱ�䣺2006-7-27
	 *               <p>
	 */
	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

		if(idList==null){
			MsgBox.showWarning(this, "��ѡ����");
			abort();
			return ;
		}
		
		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			
//			��鵥���Ƿ��ڹ�������
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
//			if (!element.getString(getBillStatePropertyName()).equals(states)) {
			if (!stateSet.contains(element.getString(getBillStatePropertyName()))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
	
	  /**
     * 
     * ����������״̬�������ƣ������ṩȱʡʵ��
     * @return
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     */
    protected String getBillStatePropertyName() {
    	return "state";
    }
    
    /**
	 * 
	 * ��������ȡ��ǰ���ݵ�Table���������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-8-2 <p>
	 */
	protected KDTable getBillListTable() {
		return getMainTable();
	}
	
    protected SelectorItemCollection getStateSelectors() {
    	SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("state");
		
		return selectors;
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
       	checkSelected();
    	String fieldName = getQueryFieldNameBindingWf();
        String id = (String)getSelectedFDCFieldValues(fieldName).get(0);
        IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo processInstInfo = null;
        ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(id);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if (procInsts[i].getState().startsWith("open")) {
            	processInstInfo = procInsts[i];
            }
        }
        if (processInstInfo == null) {
        	//���û������ʱ���̣��ж��Ƿ��п���չ�ֵ�����ͼ��չ��
        	procInsts = service.getAllProcessInstancesByBizobjId(id);
        	if(procInsts== null || procInsts.length <=0)
        		MsgBox.showInfo(this ,EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_WFHasNotInstance"));
        	else if(procInsts.length ==1){
	        	showWorkflowDiagram(procInsts[0]);
        	}else{
        		UIContext uiContext = new UIContext(this);
                 uiContext.put("procInsts",procInsts);
                 String className = ProcessRunningListUI.class.getName();
                 IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className,uiContext);
                 uiWindow.show();
        	}
		} else {
			showWorkflowDiagram(processInstInfo);
        }
    }
    
	/**
	 * ��������ѡ����������ģʽ��
	 * @param fieldName
	 * @return
	 */
	public final ArrayList getSelectedFDCFieldValues(String fieldName) {
        ArrayList list = new ArrayList();
        int[] selectRows = KDTableUtil.getSelectedRows( getBillListTable());
        for (int i = 0; i < selectRows.length; i++) {
        	ICell cell =  getBillListTable().getRow(selectRows[i]).getCell(fieldName);
        	if (cell == null) {
        		MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
        		SysUtil.abort();
        	}
        	if (cell.getValue()!=null)
        	{
        		String id = cell.getValue().toString();
            	if (!list.contains(id))
            		list.add(id);
        	}

        }
        return list;
    }
    
    private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
	throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		String className = BasicWorkFlowMonitorPanel.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal())
				.create(className, uiContext);
		uiWindow.show();
	}

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

	protected String getEditUIName() {
		/* TODO �Զ����ɷ������ */
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		/* TODO �Զ����ɷ������ */
		return null;
	}
	
	/**
	 * �Ƿ���ҪԶ�̻�ȡ��ʼ���ݣ�����������
	 * @return
	 * @throws Exception
	 */
	protected  boolean isNeedfetchInitData() throws Exception{	
		return true;
	}
	
	protected  void fetchInitData() throws Exception{		

		if(!isNeedfetchInitData() ){
			return ;
		}
		
		try {
			//���óɱ�����һ�廯
//			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			//������֯������,�õ�ǰ��֯ȥ�ж�,��ǰ��֯Ϊ�ɱ�����,�ϼ�ʵ�������֯�˲�������ʱ,��ȡ�Ĳ����Ǵ����
			//HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			
			HashMap paramItem = (HashMap)ActionCache.get("FDCBillListUIHandler.paramItem");
			if(paramItem==null){
				CompanyOrgUnitInfo fiUnit = SysContext.getSysContext().getCurrentFIUnit();
				if (fiUnit != null && fiUnit.getId() != null) {
					isIncorporation = FDCUtils.IsInCorporation(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString());
				} else {
					logger.warn("CurrentOrgUnit is not FIUnit...");
					isIncorporation = FDCUtils.IsInCorporation(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
				}
			} else if (paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) != null) {
				isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
			}
			
		}catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
		
		Map initData = (Map)ActionCache.get("FDCBillListUIHandler.initData");
		if(initData==null){
			Map param = new HashMap();
			initData =  (Map)((IFDCBill)getBillInterface()).fetchInitData(param);	
		}		
		
		//��õ�ǰ��֯
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		
		company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		if(company==null){
			company =  SysContext.getSysContext().getCurrentFIUnit();
		}
		isFinacial = FDCUtils.IsFinacial(null, company.getId().toString());
	}
	
	public void onLoad() throws Exception {
		menuItemSwitchView.setEnabled(false);
		menuItemSwitchView.setVisible(false);

		// ��ȡ��ʼ����������
		fetchInitData();

		super.onLoad();
		if (isUseMainMenuAsTitle()) {
			FDCClientHelper.setUIMainMenuAsTitle(this);
		}
		// ��ʼ�����
		initTable();

		getMainTable().setColumnMoveable(true);

		// ����һ��hiddenSql������Ժ��ѯ�������ݡ�
		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		// �������������Զ���˵�����һ��
		refreshUITitle();

		// ���غ�
		afterOnLoad();
	}
	
	
	/**
	 * 
	 * ��������ʼ�����
	 * @author:liupd
	 * ����ʱ�䣺2006-8-3 <p>
	 */
	protected void initTable() {
		
		getMainTable().setColumnMoveable(true);
		
		//freezeMainTableColumn();
		if(getBillListTable()!=null){
			//freezeBillTableColumn();
			getBillListTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
			if(getBillListTable().getColumn("createTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createTime");
			}
			if(getBillListTable().getColumn("auditTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "auditTime");
			}
			if(getBillListTable().getColumn("createDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createDate");
			}
			if(getBillListTable().getColumn("createTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createTime");
			}
			if(getBillListTable().getColumn("signDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "signDate");
			}
			if(getBillListTable().getColumn("entrys.deductDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "entrys.deductDate");
			}
//			if(getBillListTable().getColumn("entrys.deductDate")!=null){
//				FDCHelper.formatTableDate(getBillListTable(), "entrys.deductDate");
//			}
			if(getBillListTable().getColumn("bookedDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "bookedDate");
			}
		}
		//getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if(getMainTable().getColumn("amount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "amount");
		}
		if(getMainTable().getColumn("originalAmount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		}
//		FDCHelper.formatTableDateTime(getMainTable(), "signDate");
		
		//
		if(!isIncorporation){
//			if(getMainTable().getColumn(COL_DATE)!=null){
//				getMainTable().getColumn(COL_DATE).getStyleAttributes().setHided(true);
//			}
			if(getMainTable().getColumn(COL_PERIOD)!=null){
				getMainTable().getColumn(COL_PERIOD).getStyleAttributes().setHided(true);
			}
			if(getMainTable().getColumn(COL_PERIODNUMBER)!=null){
				getMainTable().getColumn(COL_PERIODNUMBER).getStyleAttributes().setHided(true);
			}			
//			if(getBillListTable().getColumn(COL_DATE)!=null){
//				getBillListTable().getColumn(COL_DATE).getStyleAttributes().setHided(true);
//			}
			if(getBillListTable().getColumn(COL_PERIOD)!=null){
				getBillListTable().getColumn(COL_PERIOD).getStyleAttributes().setHided(true);
			}
			if(getBillListTable().getColumn(COL_PERIODNUMBER)!=null){
				getBillListTable().getColumn(COL_PERIODNUMBER).getStyleAttributes().setHided(true);
			}
		}
		
		//Table�������֮������ԭ�ҵľ���
		getMainTable().getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						FDCClientHelper.tblMainAfterDataFill(e,getMainTable(),FDCBillListUI.this);
					}
				}
		);	
	}
	
    protected void refreshUITitle() {
		// ���ݱ���titleMain
		if (getUIContext().get("MainMenuName") != null) {
			titleMain = (String) getUIContext().get("MainMenuName");
			this.setUITitle(titleMain);
		}

	}

	/**
	 * ���������غ�
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 */
	protected void afterOnLoad() throws Exception {
		// ���á����ù�����ť
		enableWorkButton();
	}
	
	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * ���������á����ù�����ť
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 */
	protected void enableWorkButton() {
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * 
	 * ��������ʾ�����ɹ�
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	
	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();
		btnCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
		btnCancelCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
	}
	
	protected void fdcDataImport() throws Exception{
/*        FDCDatataskCaller task = new FDCDatataskCaller();
        task.setParentComponent(this);
        final ArrayList dataImportParam = getFDCDataImportParam();
        if (dataImportParam != null)
        {
            task.invoke(dataImportParam, DatataskMode.ImpMode);
        }*/
	}
    protected ArrayList getFDCDataImportParam()
    {
      DatataskParameter param = new DatataskParameter();
      //        String solutionName = EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "accountTypeImportSolutionPath"); //�����ܷ���.���뷽��-��������.ȫ������.��ƿ�Ŀ�����Ŀ¼
//      String solutionName = "�����ܷ���.���뷽��-��������.ȫ������.��ƿ�Ŀ���";
      String solutionName = "eas.fdc.bill.contractBill";
      param.solutionName = solutionName;
//      param.alias = EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "accountType"); //��ƿ�Ŀ���
      param.alias="��ͬ";
      Hashtable dataTaskCtx = new Hashtable();
      
      param.setContextParam(dataTaskCtx);

      ArrayList paramList = new ArrayList();
      paramList.add(param);

      return paramList;
  
    }
    
    /**
     * �ò˵�����ΪUI��ҳǩ��,���������
     * @return Ĭ�Ϸ���true
     */
    protected boolean isUseMainMenuAsTitle(){
    	return true;
    }
    
	/**
	 * RPC���죬�κ�һ���¼�ֻ��һ��RPC
	 */
	
	public boolean isPrepareInit() {
		return true;
	}

	//���ò�ѯ��
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
		// modify by lihaiou,2014-09-12,��ӡSQL���������������⣬���Ҵ˴��ظ���ӡ������
//		String sql = null;
//		try {
//			if(logger.isDebugEnabled()){
//				sql = queryExecutor.getSQL();
//				String classSimpleName = FdcClassUtil.getSimpleName(this.getClass());
//	
//				logger.info("================================================================");
//				logger.info(classSimpleName + ".getQueryExecutor()��sql:" + sql);
//				logger.info(classSimpleName + ".getQueryExecutor()��viewInfo:" + viewInfo);
//				logger.info("================================================================");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return queryExecutor;
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
	/**
	 * ��������ȡ������Ŀ�������ƣ������ṩȱʡʵ��
	 * 
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	protected String getProjectPropertyName() {
		return "curProject";
	}

	/**
	 * ������������Ŀ�ɱ����Ĺ�����
	 * 
	 * @param orgUnitId
	 * @param authorizedOrgs
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	protected FilterInfo dealWithProjectCostCenterFilter(BOSUuid orgUnitId, Set authorizedOrgs) throws EASBizException, BOSException, SQLException {
		FilterInfo filterInfo = new FilterInfo();
		FilterItemCollection filterItems = filterInfo.getFilterItems();
		String maskString = null;
	
		// //////////////////////////////////////////////////////////////////////////
		
		FullOrgUnitInfo orgUnitInfo = null;
		String orgUnitLongNumber = null;
		if (orgUnit != null && orgUnitId.toString().equals(orgUnit.getId().toString())) {
			orgUnitInfo = orgUnit;
		} else {
			orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));
		}
		orgUnitLongNumber = orgUnitInfo.getLongNumber();
		
		// ȡ�õ�ǰ��¼�û�
		UserInfo userInfo = FdcOrgUtil.getCurrentUserInfo(null);
		// ȡ���й�����Ŀ�ĳɱ�����ID����
		Set hasProjectCostCenterIdSet = FdcOrgUtil.getHasProjectCostCenterIdSet(null, orgUnitInfo, userInfo);
		int hasProjectCostCenterIdSetSize = hasProjectCostCenterIdSet.size();
		int authorizedOrgsSize = authorizedOrgs.size();
	
		// //////////////////////////////////////////////////////////////////////////
		
		filterItems.add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));
		filterItems.add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
		
		// ��ȡ������Ŀ�������ƣ������ṩȱʡʵ��
		String projectPropertyName = getProjectPropertyName();
		if (FdcStringUtil.isBlank(projectPropertyName)) {
			// ������������Ŀ��ֱ��ʹ��orgUnit.id(��ǰ�ĳ���BUG:orgUnit.id��һ����curProject.fullOrgUnit.id��ͬ)
			// filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));
			String sql = FdcOrgUtil.getAuthorizedOrgsSql(orgUnitInfo, userInfo);
			filterItems.add(new FilterItemInfo("orgUnit.id", sql, CompareType.INNER));
			
			maskString = "#0 and #1 and #2";
	
			filterInfo.setMaskString(maskString);
	
			return filterInfo;
		}
	
		// //////////////////////////////////////////////////////////////////////////
		
		// û�й�����Ŀ
		if (0 == hasProjectCostCenterIdSetSize) {
			// ��չʾ�κ�����
			filterItems.add(new FilterItemInfo("orgUnit.id", "-1"));
			
			maskString = "#0 and #1 and #2";
		}
		// 1/3�����й�����Ŀ
		else if (hasProjectCostCenterIdSetSize < authorizedOrgsSize * 0.34) {
			filterItems.add(new FilterItemInfo(projectPropertyName + ".fullOrgUnit.id", hasProjectCostCenterIdSet, CompareType.INCLUDE));
			
			maskString = "#0 and #1 and #2";
		}
		// 2/3�����й�����Ŀ
		else if (hasProjectCostCenterIdSetSize < authorizedOrgsSize * 0.67) {
			// ������������Ŀ��ֱ��ʹ��orgUnit.id(��ǰ�ĳ���BUG:orgUnit.id��һ����curProject.fullOrgUnit.id��ͬ)
			// filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));
			String sql = FdcOrgUtil.getAuthorizedOrgsSql(orgUnitInfo, userInfo);
			filterItems.add(new FilterItemInfo("orgUnit.id", sql, CompareType.INNER));
			
			maskString = "#0 and #1 and #2";
		}
		// 2/3�����й�����Ŀ
		else if (hasProjectCostCenterIdSetSize < authorizedOrgsSize * 1) {
			Set hasNotProjectCostCenterIdSet = new LinkedHashSet(authorizedOrgs);
			hasNotProjectCostCenterIdSet.removeAll(hasProjectCostCenterIdSet);
			// �ų�û�й�����Ŀ����֯
			filterItems.add(new FilterItemInfo(projectPropertyName + "fullOrgUnit.id", hasNotProjectCostCenterIdSet, CompareType.NOTINCLUDE));
			
			maskString = "#0 and #1 and #2";
		}
		// ȫ�����й�����Ŀ
		else if (hasProjectCostCenterIdSetSize == authorizedOrgsSize) {
			// չʾȫ������
			// filterItems.add(new FilterItemInfo("curProject.FullOrgUnit.id", "-1"));
	
			maskString = "#0 and #1";
		}
		
		filterInfo.setMaskString(maskString);
	
		// //////////////////////////////////////////////////////////////////////////
		
		return filterInfo;
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
	// ��һ���µĽ���
	public void showUI(String uiClassName, String model, Map dataObjects, String oprtState) throws Exception {

		UIContext uiContext = new UIContext(this);
		prepareUIContext(uiContext);

		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(model).create(uiClassName, uiContext, dataObjects, oprtState);

		uiWindow.show();
	}

	protected void prepareUIContext(UIContext uiContext) {
		if (uiContext != null) {
			uiContext.put("titleMain", titleMain);
		}
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		if (uiContext != null) {
			uiContext.put("titleMain", titleMain);
		}
	}

	/**
	 * ������ȡ��ѡ����ID
	 * 
	 * @param table
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected List getSelectedIdValues(KDTable table) {
		ArrayList selectList = new ArrayList();

		java.util.List selectKeyIdFields = null;
		int mode = 0;
		int selectRows[] = KDTableUtil.getSelectedRows(table);
		java.util.List blockList = table.getSelectManager().getBlocks();

		if (blockList != null && blockList.size() == 1) {
			mode = ((IBlock) table.getSelectManager().getBlocks().get(0)).getMode();
		}
		if (mode == 8 && selectRows.length >= 100) {
			selectKeyIdFields = getQueryPkList();
		}

		return ListUiHelper.getSelectedIdValues(table, getKeyFieldName(), selectList, selectKeyIdFields);
	}

	/**
	 * �������Ƿ�֧��EAS�߼�ͳ��(EAS800�����Ĺ���)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

}