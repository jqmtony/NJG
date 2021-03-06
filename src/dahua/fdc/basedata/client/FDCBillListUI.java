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
	
	//启用成本月结
	protected boolean isIncorporation = false;
	//启用财务成本一体化
	protected boolean isFinacial = false;
	
	protected FullOrgUnitInfo orgUnit = null;
	
	/** 财务组织 */
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
        * 处理笔记留痕  实现此业务功能存在风险，故此保留！！！！！！  by Cassiel_peng 
        * 附件管理方案要根据系统参数的具体值来选择：
        * 参数值为是：启用笔记留痕     为否：保持系统原有逻辑不变
        *//*
       //根据ID获得实体的BOSType
        BOSObjectType bosType = BOSUuid.read(boID).getType();  // boID 是 与附件关联的 业务对象的 ID
        //根据BOSType获得实体调用接口
        ICoreBase coreBase = (ICoreBase) BOSObjectFactory.createRemoteBOSObject(bosType, ICoreBase.class);
        //获得该对象
        CoreBaseInfo objectInfo = coreBase.getValue(new ObjectUuidPK(boID));  
        if(objectInfo instanceof FDCBillInfo){
    	   boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
    	   if(!isUseWriteMark){//保持系统标准“附件管理”方案不变
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
		
		int confirm = MsgBox.showConfirm2(this, "确认终止? 该操作不可撤销！");
		if(confirm == MsgBox.OK) {
			cancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
			
			showOprtOKMsgAndRefresh();
		}
    }
    
    protected String[] getBillStateEnum(){
    	return new String[]{FDCBillStateEnum.AUDITTED_VALUE};
    }
    /**
     * 作废
     * @param ids
     * @throws Exception
     */
    protected void cancel(List ids) throws Exception{}
    
    /**
     * 生效
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
     * 作废前的业务检查
     * @throws Exception
     */
    protected void checkBeforeCancel() throws Exception{
    	
    }
    
    
    /**
	 * 
	 * 描述：修改前检查单是否在工作流中
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
			
//			检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
		}
	}
	
    /**
	 * 
	 * 描述：检查单据状态
	 * 
	 * @param states
	 *            状态
	 * @param res
	 *            提示信息资源名称
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

		if(idList==null){
			MsgBox.showWarning(this, "请选中行");
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
			
//			检查单据是否在工作流中
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
     * 描述：单据状态属性名称，基类提供缺省实现
     * @return
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected String getBillStatePropertyName() {
    	return "state";
    }
    
    /**
	 * 
	 * 描述：获取当前单据的Table（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-2 <p>
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
        	//如果没有运行时流程，判断是否有可以展现的流程图并展现
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
	 * 所见即所选，不处理虚模式。
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
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		/* TODO 自动生成方法存根 */
		return null;
	}
	
	/**
	 * 是否需要远程获取初始数据：包括参数等
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
			//启用成本财务一体化
//			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			//财务组织级参数,用当前组织去判断,当前组织为成本中心,上级实体财务组织此参数禁用时,获取的参数是错误的
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
		
		//获得当前组织
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

		// 获取初始化界面数据
		fetchInitData();

		super.onLoad();
		if (isUseMainMenuAsTitle()) {
			FDCClientHelper.setUIMainMenuAsTitle(this);
		}
		// 初始化表格
		initTable();

		getMainTable().setColumnMoveable(true);

		// 增加一个hiddenSql项，方便以后查询跟踪数据。
		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		// 单据名称需与自定义菜单名称一致
		refreshUITitle();

		// 加载后
		afterOnLoad();
	}
	
	
	/**
	 * 
	 * 描述：初始化表格
	 * @author:liupd
	 * 创建时间：2006-8-3 <p>
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
		
		//Table填充数据之后，设置原币的精度
		getMainTable().getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						FDCClientHelper.tblMainAfterDataFill(e,getMainTable(),FDCBillListUI.this);
					}
				}
		);	
	}
	
    protected void refreshUITitle() {
		// 单据标题titleMain
		if (getUIContext().get("MainMenuName") != null) {
			titleMain = (String) getUIContext().get("MainMenuName");
			this.setUITitle(titleMain);
		}

	}

	/**
	 * 描述：加载后
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 */
	protected void afterOnLoad() throws Exception {
		// 启用、禁用工作按钮
		enableWorkButton();
	}
	
	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * 描述：启用、禁用工作按钮
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
	 * 描述：提示操作成功
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
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
      //        String solutionName = EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "accountTypeImportSolutionPath"); //引入总方案.引入方案-基础资料.全局资料.会计科目类别子目录
//      String solutionName = "引入总方案.引入方案-基础资料.全局资料.会计科目类别";
      String solutionName = "eas.fdc.bill.contractBill";
      param.solutionName = solutionName;
//      param.alias = EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "accountType"); //会计科目类别
      param.alias="合同";
      Hashtable dataTaskCtx = new Hashtable();
      
      param.setContextParam(dataTaskCtx);

      ArrayList paramList = new ArrayList();
      paramList.add(param);

      return paramList;
  
    }
    
    /**
     * 用菜单名做为UI的页签名,子类可重载
     * @return 默认返回true
     */
    protected boolean isUseMainMenuAsTitle(){
    	return true;
    }
    
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
		return true;
	}

	//设置查询器
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
		// modify by lihaiou,2014-09-12,打印SQL存在严重性能问题，而且此处重复打印了三次
//		String sql = null;
//		try {
//			if(logger.isDebugEnabled()){
//				sql = queryExecutor.getSQL();
//				String classSimpleName = FdcClassUtil.getSimpleName(this.getClass());
//	
//				logger.info("================================================================");
//				logger.info(classSimpleName + ".getQueryExecutor()，sql:" + sql);
//				logger.info(classSimpleName + ".getQueryExecutor()，viewInfo:" + viewInfo);
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
	 * 描述：获取工程项目属性名称，基类提供缺省实现
	 * 
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	protected String getProjectPropertyName() {
		return "curProject";
	}

	/**
	 * 描述：处理项目成本中心过滤器
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
		
		// 取得当前登录用户
		UserInfo userInfo = FdcOrgUtil.getCurrentUserInfo(null);
		// 取得有工程项目的成本中心ID集合
		Set hasProjectCostCenterIdSet = FdcOrgUtil.getHasProjectCostCenterIdSet(null, orgUnitInfo, userInfo);
		int hasProjectCostCenterIdSetSize = hasProjectCostCenterIdSet.size();
		int authorizedOrgsSize = authorizedOrgs.size();
	
		// //////////////////////////////////////////////////////////////////////////
		
		filterItems.add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));
		filterItems.add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
		
		// 获取工程项目属性名称，基类提供缺省实现
		String projectPropertyName = getProjectPropertyName();
		if (FdcStringUtil.isBlank(projectPropertyName)) {
			// 不关联工程项目，直接使用orgUnit.id(以前的程序BUG:orgUnit.id不一定和curProject.fullOrgUnit.id相同)
			// filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));
			String sql = FdcOrgUtil.getAuthorizedOrgsSql(orgUnitInfo, userInfo);
			filterItems.add(new FilterItemInfo("orgUnit.id", sql, CompareType.INNER));
			
			maskString = "#0 and #1 and #2";
	
			filterInfo.setMaskString(maskString);
	
			return filterInfo;
		}
	
		// //////////////////////////////////////////////////////////////////////////
		
		// 没有工程项目
		if (0 == hasProjectCostCenterIdSetSize) {
			// 不展示任何数据
			filterItems.add(new FilterItemInfo("orgUnit.id", "-1"));
			
			maskString = "#0 and #1 and #2";
		}
		// 1/3以下有工程项目
		else if (hasProjectCostCenterIdSetSize < authorizedOrgsSize * 0.34) {
			filterItems.add(new FilterItemInfo(projectPropertyName + ".fullOrgUnit.id", hasProjectCostCenterIdSet, CompareType.INCLUDE));
			
			maskString = "#0 and #1 and #2";
		}
		// 2/3以下有工程项目
		else if (hasProjectCostCenterIdSetSize < authorizedOrgsSize * 0.67) {
			// 不关联工程项目，直接使用orgUnit.id(以前的程序BUG:orgUnit.id不一定和curProject.fullOrgUnit.id相同)
			// filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));
			String sql = FdcOrgUtil.getAuthorizedOrgsSql(orgUnitInfo, userInfo);
			filterItems.add(new FilterItemInfo("orgUnit.id", sql, CompareType.INNER));
			
			maskString = "#0 and #1 and #2";
		}
		// 2/3以上有工程项目
		else if (hasProjectCostCenterIdSetSize < authorizedOrgsSize * 1) {
			Set hasNotProjectCostCenterIdSet = new LinkedHashSet(authorizedOrgs);
			hasNotProjectCostCenterIdSet.removeAll(hasProjectCostCenterIdSet);
			// 排除没有工程项目的组织
			filterItems.add(new FilterItemInfo(projectPropertyName + "fullOrgUnit.id", hasNotProjectCostCenterIdSet, CompareType.NOTINCLUDE));
			
			maskString = "#0 and #1 and #2";
		}
		// 全部都有工程项目
		else if (hasProjectCostCenterIdSetSize == authorizedOrgsSize) {
			// 展示全部数据
			// filterItems.add(new FilterItemInfo("curProject.FullOrgUnit.id", "-1"));
	
			maskString = "#0 and #1";
		}
		
		filterInfo.setMaskString(maskString);
	
		// //////////////////////////////////////////////////////////////////////////
		
		return filterInfo;
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
	// 打开一个新的界面
	public void showUI(String uiClassName, String model, Map dataObjects, String oprtState) throws Exception {

		UIContext uiContext = new UIContext(this);
		prepareUIContext(uiContext);

		// 创建UI对象并显示
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
	 * 描述：取得选中行ID
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
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
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