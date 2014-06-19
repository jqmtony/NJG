/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;


import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.calc.CalculatorDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.port.pm.contract.ContractBillInfo;
import com.kingdee.eas.port.pm.utils.ContractClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class FDCBillEditUI extends AbstractFDCBillEditUI {
	private final static String CANTAUDIT = "cantAudit";

	private static final String CANTEDIT = "cantEdit";

	private static final String CANTREMOVE = "cantRemove";

	private final static String CANTUNAUDIT = "cantUnAudit";

	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";

	private final static int PRECSION = 2;

	//是否保存操作
	public boolean isSaveAction = false;

	private static final Logger logger = CoreUIObject.getLogger(FDCBillEditUI.class);

	//财务组织
	protected CompanyOrgUnitInfo company = null;
	
	//当前组织
	protected FullOrgUnitInfo orgUnitInfo = null;
	
	//本位币
	protected CurrencyInfo baseCurrency = null;
	//合同单据
	protected ContractBillInfo contractBill = null;
	
	//当前工程项目
	protected ProjectInfo curProject = null;

    // 界面标题：主标题，新增，修改，查看，凭证索引
    protected String titleMain, titleNew, titleModify, titleView, billIndex = "";

	//启用成本成本月结
	protected boolean isIncorporation = false;
	
	//登记日期
	protected Date bookedDate = null;	
	//当前期间
	protected PeriodInfo curPeriod = null ;
	//成本还是财务
	protected boolean isCost = true;
	//是否已经冻结
	protected boolean isFreeze = false;
	//可录入期间
	protected PeriodInfo canBookedPeriod = null ;
	
	//当前服务器日日期
	protected Date serverDate = null;	
	
	/**审批与反审批是否同一个用户*/
	protected boolean isSameUserForUnAudit = false;
	
	/**
	 * output class constructor
	 */
	public FDCBillEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {
	    //titleMain = getUITitle();
	    titleNew = getStr("titleNew");
	    titleModify = getStr("titleModify");
	    titleView = getStr("titleView");
	}

    public String getStr(String name) {
        return getStr(FDCConstants.VoucherResource, name);
    }

    public String getStr(String resFile,String name) {
        return EASResource.getString(resFile, name);
    }

    protected void refreshUITitle() {
    	if(titleMain==null){
    		return;
    	}

        String state = getOprtState();
        if (OprtState.ADDNEW.equals(state)) {
            billIndex = "";
            this.setUITitle(titleMain + " - " + titleNew);
        } else {
            if (OprtState.EDIT.equals(state)) {
                this.setUITitle(titleMain + " - " + titleModify + billIndex);
            } else {
                boolean isFromMsgCenter = false;
                Map uiContext = getUIContext();
                if (uiContext != null) {
                    isFromMsgCenter = Boolean.TRUE.equals(uiContext.get("isFromWorkflow"));
                }
                if (!isFromMsgCenter) {
                    this.setUITitle(titleMain + " - " + titleView + billIndex);
                }
            }
        }
    }
    
    //控件数据变化统一事件,使用该功能统一控件事件,以免在loadFields时，不停触发控件事件
	public class ControlDateChangeListener implements DataChangeListener {
		private String shortCut = null; 
		public ControlDateChangeListener(String shortCut){
			this.shortCut = shortCut;
		}
        public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
            try {
            	controlDate_dataChanged(e,this);
            } catch (Exception exc) {
                handUIException(exc);
            } finally {
            }
        }
		public String getShortCut() {
			return shortCut;
		}
    };
    
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	//业务EditUI重载，实现控件数据变化事件
    }
    

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			FDCMsgBox.showInfo("单据已被修改，请先提交。");
			this.abort();
		}
		
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
		String id = getSelectBOID();
		if (id != null) {
			getFDCBillInterface().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
	}

	/**
	 * 同步数据库数据到界面,用于审批/反审批后显示审批人,审批日期
	 * @throws Exception
	 */
	protected void syncDataFromDB() throws Exception {
		//由传递过来的ID获取值对象
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(e);
		setSaveActionStatus();
		setNumberTextEnabled();
		
		//启用财务成本一体化
		enablePeriodComponent(this,isIncorporation);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTREMOVE);
		checkRef(getSelectBOID());
		super.actionRemove_actionPerformed(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(getFDCBillInfo().getState() == null)
		getFDCBillInfo().setState(FDCBillStateEnum.SAVED);
		setSaveAction(true);
		super.actionSave_actionPerformed(e);

		handleOldData();


	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(false);

		if(!checkCanSubmit()){
			MsgBox.showWarning(this,"单据状态已经在审核中或者已审核，不能再提交");
			SysUtil.abort();
		}

		super.actionSubmit_actionPerformed(e);

		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
			handleCodingRule();
		}
		handleOldData();
	}

	protected boolean checkCanSubmit() throws Exception {
		
//		if(isIncorporation && ((FDCBillInfo)editData).getPeriod()==null){
//			MsgBox.showWarning(this,"启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
//			SysUtil.abort();
//		}
		
		if(editData.getId()==null){
			return true;
		}
		//R110506-0417：工作流人工节点在提交流程后报错. By zhiyuan_tang 2010-05-12
//		//检查是否在工作流中
//		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		
		return ((IFDCBill)getBillInterface()).checkCanSubmit(editData.getId().toString());
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        } 
        boolean isEdit = false;
        if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
        {
            isEdit = true;
        }
        
        if(isEdit){
        	//合同
	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
	        String creatorId=this.editData.getCreator()!=null?this.editData.getCreator().getId().toString():null;
			if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.ContractBillEditUI")){
				String uiName = "com.kingdee.eas.fdc.contract.client.ContractBillEditUI";
				boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
	    				new ObjectUuidPK(userId),
	    				new ObjectUuidPK(orgId),
	    				new MetaDataPK(uiName),
	    				new MetaDataPK("ActionAttamentCtrl") );
				//如果未启用参数则有权限的用户才能进行附件维护,如果已经启用了参数则制单人等于当前用户且有权限才能进行 维护
	        	if(hasFunctionPermission){
	        		//creatorId ==null; 为新增,不做控制
	        		if(creatorId!=null){
//	        			boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
//	        			if(creatorCtrl&&!creatorId.equals(userId)){
//	        				//制单人要等于当前用户才行
//	        				isEdit=false;
//	        			}
	        		}
	        	}else{
	        		isEdit=false;
	        	}
	        }
	        //付款申请单
	        if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI")){
	        		String uiName = "com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI";
					boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
		    				new ObjectUuidPK(userId),
		    				new ObjectUuidPK(orgId),
		    				new MetaDataPK(uiName),
		    				new MetaDataPK("ActionAttamentCtrl") );
					//如果未启用参数则有权限的用户才能进行附件维护,如果已经启用了参数则制单人等于当前用户且有权限才能进行 维护
		        	if(hasFunctionPermission){
		        		if(creatorId!=null){
//		        			boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
//		        			if(creatorCtrl&&!creatorId.equals(userId)){
//		        				//制单人要等于当前用户才行
//		        				isEdit=false;
//		        			}
		        		}
		        	}else{
		        		isEdit=false;
		        	}
		        }
	    }
        acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID 是 与附件关联的 业务对象的 ID
       /* *//**
         * by ling_peng 2009-7-1      关于是否启用“笔记留痕”
         * 与肖飙彪交流，发现将房地产所有业务系统的“附件管理”都启用“笔记留痕”存在风险，已知风险：此“笔记留痕”功能只支持能够向上转型为FDCBillInfo的单据，
         * 但是房地产业务系统的一部分单据基类并非FDCBillInfo。所以只针对继承了FDCBillInfo的单据。
         * 由于存在风险，此业务功能暂时保留
         *//*
      if(this.editData instanceof FDCBillInfo){
    	   boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
           //保持系统标准“附件管理”方案不变
           if(!isUseWriteMark){
           }else{//启用“笔记留痕”
           	if(this.editData != null && this.editData.getId() != null){
       			UIContext uiContext = new UIContext();
       			uiContext.put("billInfo", this.editData);
       			//因为需要根据状态来判断显示出 ContractContentUI.ui 视图之后菜单栏里的菜单是否可用，
       			//故要将界面操作状态(oprtState)作为参数传递给 ContractContentUI.java 
       			uiContext.put("optState", oprtState);
       			//按照上面逻辑判断，如果没有权限维护附件的话我们就将OprtState的值设置为只能查看
       			if(!isEdit){
       				uiContext.put("optState", OprtState.VIEW);	
       			}
       			uiContext.put(UIContext.OWNER, this);
       			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractContentUI.class.getName(), uiContext);
       			window.show();
       		}
           }
       }else{
    	   acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID 是 与附件关联的 业务对象的 ID
       }*/
	}
	/**
	 *
	 * 描述：获得FDCBill远程接口
	 * @return
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-10-16 <p>
	 */
	private IFDCBill getFDCBillInterface() throws Exception {
		return (IFDCBill)getBizInterface();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		checkRef(getSelectBOID());
		String id = getSelectBOID();
		if (id != null) {
			getFDCBillInterface().unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
	}


	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception{
		
//		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null,
//				FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);
		
		if(isSameUserForUnAudit && editData.getAuditor() != null){
			
			if(!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())){
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = getFDCBillInfo() != null
				&& getFDCBillInfo().getState() != null
				&& getFDCBillInfo().getState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		 if(getOprtState().equals(STATUS_EDIT)) {
			 String warn = null;
			 if(state.equals(FDCBillStateEnum.AUDITTED)) {
				 warn = CANTUNAUDITEDITSTATE;
			 }
			 else {
				 warn = CANTAUDITEDITSTATE;
			 }
			 MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			 SysUtil.abort();
		 }
	}
//将edit替换为传进来的参数warning xiaoao_liu
	protected void checkBeforeEditOrRemove(String warning) {
		FDCBillStateEnum state = getFDCBillInfo().getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}

	/**
	 *
	 * 描述：根据当前单据的状态决定执行保存还是提交操作
	 *
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.EditUI#checkBeforeWindowClosing()
	 */
	public boolean checkBeforeWindowClosing() {
		if (hasWorkThreadRunning()) {
			return false;
		}

		if(getTableForPrintSetting()!=null){
			this.savePrintSetting(this.getTableForPrintSetting());
		}

		boolean b = true;

		if (!b) {
			return b;
		} else {

			// 校验storeFields()是否抛出异常
			// Exception err = verifyStoreFields();

			// storeFields()抛出异常或者editdata有改变，询问是否保存退出
			if (isModify()) {
				// editdata有改变
				int result = MsgBox.showConfirm3(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Confirm_Save_Exit"));

				if (result == KDOptionPane.YES_OPTION) {

					try {
						if (getFDCBillInfo().getState() == null
								|| getFDCBillInfo().getState() == FDCBillStateEnum.SAVED) {
							actionSave.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// 会出空指针错，因为beforeAction()会使用ActionEvent。
							ActionEvent event = new ActionEvent(btnSave,
									ActionEvent.ACTION_PERFORMED, btnSave
											.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// 会出空指针错，因为beforeAction()会使用ActionEvent。
							ActionEvent event = new ActionEvent(btnSubmit,
									ActionEvent.ACTION_PERFORMED, btnSubmit
											.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
							// actionSubmit_actionPerformed(event);
						}
						// return true;
					} catch (Exception exc) {
						// handUIException(exc);
						return false;
					}

				} else if (result == KDOptionPane.NO_OPTION) {
					// stopTempSave();
					return true;
				} else {
					return false;
				}
			} else {
				// stopTempSave();
				return true;
			}

		}

	}

	protected IObjectValue createNewData() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		/* TODO 自动生成方法存根 */
		return null;
	}

	/**
	 * 编辑界面，禁用打印和打印预览功能
	 * @Modified By Owen_wen 2010-12-06 将private改为protected，
	 * 子类可空方法重写，开启打印和打印预览功能
	 */
	protected void disablePrintFunc() {
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
	}

	/* 不合理接口,将其改成抽象方法子类必须实现
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getBizInterface()
	 */
	protected abstract ICoreBase getBizInterface() throws Exception;
	
	/*
	 *  不合理接口,将其改成抽象方法子类必须实现,如果没有分录table,返回null
	 * (non-Javadoc)
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getDetailTable()
	 */
	protected abstract KDTable getDetailTable();

	private FDCBillInfo getFDCBillInfo() {
		return (FDCBillInfo) editData;
	}

	//虚体可以新增之后,虚体就可以修改等操作.所以将此方法放开,以便控制
	protected void handleActionStatusByCurOrg() {
		if(true){
			//取消了虚体不允许操作的规定 by sxhong
			return;
		}
		// 如果是虚体成本中心，则不能增、删、改
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
			actionAttachment.setEnabled(false);
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionCopy.setEnabled(false);
			actionCopy.setVisible(false);
		}
	}

	/**
	 *
	 * 描述：避免在新增单据（未作修改）直接关闭时，出现保存提示
	 * 放在后台线程执行以优化
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	protected void handleOldData() {
		if(!(getOprtState()==STATUS_FINDVIEW||getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}

	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}

	
	//监听器
	protected Map listenersMap = new HashMap();
    protected abstract void attachListeners();
    protected abstract void detachListeners();
    
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
    
	public void loadFields() {
		//注销监听器
		detachListeners();
		
		super.loadFields();

		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		
		//最后加上监听器
		attachListeners();
		//2009-1-21 在loadFields加入设置审批、反审批按钮状态的方法，在通过上一、下一功能切换单据时，正确显示审批、反审批按钮。
		setAuditButtonStatus(this.getOprtState());
	}

	protected void setSaveActionStatus() {
		if (getFDCBillInfo().getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}

	//子类可以重载
	protected  void fetchInitParam() throws Exception{

		if(company==null){
			return ;
		}
		//启用成本财务一体化
		Map paramItem = (Map)ActionCache.get("FDCBillEditUIHandler.comParamItem");
		if(paramItem==null){
			paramItem = FDCUtils.getDefaultFDCParam(null,company.getId().toString());
		}
		if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
			isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
		}	
	}

	//子类可以重载
	protected  void fetchInitData() throws Exception{
		//合同Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if(contractBillId==null){
			Object object  = getUIContext().get("ID");
			if(object instanceof String){
				contractBillId = (String)object;
			}else if(object!=null){
				contractBillId = object.toString();
			}
		}
		//工程项目Id
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		
		Map param = new HashMap();
		param.put("contractBillId",contractBillId);
		if(projectId!=null){
			param.put("projectId",projectId.toString());
		}
		
		//RPC从response获取
		Map initData = (Map)ActionCache.get("FDCBillEditUIHandler.initData");
		if(initData==null){
			initData = ((IFDCBill)getBizInterface()).fetchInitData(param);
		}

		//本位币
		baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		//本位币
		company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		if(company==null){
			company =  SysContext.getSysContext().getCurrentFIUnit();
		}
		//
		orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if(orgUnitInfo==null){
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		
		//合同单据
		contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
		
		//当前日期
		bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
		if(bookedDate==null){
//			bookedDate = new Date(FDCDateHelper.getServerTimeStamp().getTime());
		}		
		//当前期间
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
		//是否已经冻结
		if(initData.get(FDCConstants.FDC_INIT_ISFREEZE)!=null){
			isFreeze = ((Boolean)initData.get(FDCConstants.FDC_INIT_ISFREEZE)).booleanValue();
		}
		//可录入期间
		if(isFreeze){
			canBookedPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_BOOKEDPERIOD);
		}else{
			canBookedPeriod = curPeriod;
		}		

		//工程项目
		curProject = (ProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
			
		//当前服务器日期
		serverDate = (Date)initData.get("serverDate");
		if(serverDate==null){
			serverDate = bookedDate;
		}
			 
	}

	public void onLoad() throws Exception {

		//获取初始化界面数据
		fetchInitData();
		
		//获取参数
//		fetchInitParam();
		
		super.onLoad();
		if(isUseMainMenuAsTitle()){
//			FDCClientHelper.setUIMainMenuAsTitle(this);
		}
		handleCodingRule();

//		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		btnSubmit.setToolTipText(FDCClientUtils.getRes("submit"));

		disablePrintFunc();
		updateButtonStatus();

		// 设置所有金额控件KDFormattedTextField，BasicNumberTextField的最大最小值，精度，右对齐
//		FDCHelper.setComponentPrecision(this.getComponents(), FDCBillEditUI.PRECSION);

		//
		setNumberTextEnabled();

		//启用财务成本一体化
		enablePeriodComponent(this,isIncorporation);

//		handleOldData();
		AmusementUI.addHideMenuItem(this, this.menuBiz);
		
		setAuditButtonStatus(this.getOprtState());
	}

	/**
	 * 获取期间
	 * @param e				DataChange事件
	 * @param pkbookedDate	需要获取期间的日期控件
	 * @param cbPeriod		期间F7控件
	 * @param projectId		工程项目ID
	 * @param isCost		是否是取成本期间
	 * @throws Exception
	 */
	public void fetchPeriod(DataChangeEvent e,KDDatePicker pkbookedDate,KDBizPromptBox cbPeriod,String projectId, boolean isCost) throws Exception {
		
		if((!getOprtState().equals(OprtState.ADDNEW))&&(!getOprtState().equals(OprtState.EDIT))){
			return ;
		}
    	if(bookedDate==null){
    		return ;
    	}
    	
		Date bookedDate  = (Date)pkbookedDate.getValue();
		PeriodInfo period = null;
		PeriodInfo oldPeriod = null;
		if(isIncorporation){    	
	        if (canBookedPeriod!=null && bookedDate.before(canBookedPeriod.getBeginDate())) {
	        	FDCMsgBox.showConfirm2(this,"所选日期不能在工程当前期间之前");
	        	//R110114-028：只在oldValue在工程当前期间之后的时候才将值恢复成oldValue，否则会出现死循环 
	        	if(((Date)e.getOldValue()).before(canBookedPeriod.getBeginDate())){
	        		pkbookedDate.setValue(canBookedPeriod.getBeginDate());
	        	} else {
	        		pkbookedDate.setValue(e.getOldValue());
	        	}
	        	this.abort();
	        }
	        
	        if(isCost){
//	        	period = FDCUtils.fetchCostPeriod(projectId,bookedDate);
	        }else{
//	        	period = FDCUtils.fetchFinacialPeriod(projectId,bookedDate);
	        }
	        
	        if (period!=null && bookedDate.before(period.getBeginDate())) {
	        	FDCMsgBox.showConfirm2(this,"所选日期不能在工程当前期间之前");
	        	
	        	if(((Date)e.getOldValue()).before(period.getBeginDate())){
	        		pkbookedDate.setValue(period.getBeginDate());
	        	}else{
		        	pkbookedDate.setValue(e.getOldValue());
	        	}
	        	return ;
	        	//this.abort();
	        }
    	}else{
    		period = PeriodUtils.getPeriodInfo(null ,bookedDate ,new ObjectUuidPK(company.getId().toString()));
    		oldPeriod = PeriodUtils.getPeriodInfo(null , (Date)e.getOldValue() ,new ObjectUuidPK(company.getId().toString()));
    		if(period==null){
    			FDCMsgBox.showConfirm2(this,"没有对应的期间。请先在基础资料维护期间");
	        	//R110114-028：只在oldValue有对应的期间的时候才将值恢复成oldValue，否则会出现死循环 
	        	if (oldPeriod != null) {
	        		pkbookedDate.setValue(e.getOldValue());
	        	}
	        	this.abort();
    		}
    	}
    	
    	cbPeriod.setValue(period);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);

		handleCodingRule();
	}

	protected void enablePeriodComponent(Container aa,boolean isIncorporation){
		Component[] cc = aa.getComponents();

		Component c;
		for (int i = 0, n = cc.length; i < n; i++) {
			c = cc[i];

			if (c instanceof Container) {
				enablePeriodComponent((Container)c,isIncorporation);
			}

			if (c instanceof KDDatePicker) {
				//业务日期客户可以自行修改
//				KDDatePicker obj = (KDDatePicker)c;
//				if("pkbookedDate".equals(obj.getName())){
//					obj.setEnabled(isIncorporation);
//				}
			}else if (c instanceof KDBizPromptBox) {
				KDBizPromptBox obj = (KDBizPromptBox)c;
				if("cbPeriod".equals(obj.getName())){
					obj.setEnabled(false);
				}
			}
		}
	}

	/**
	 * 处理编码规则
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		/*
		 * 2008-09-27编码规则的取值取错了，应当取FDCBillInfo中关联的ID
		 */
		String currentOrgId = "";//FDCClientHelper.getCurrentOrgId();
		if(editData instanceof FDCBillInfo && ((FDCBillInfo)editData).getOrgUnit() != null){
			currentOrgId = ((FDCBillInfo)editData).getOrgUnit().getId().toString();
		}else{		
			//-- 销售组织下获取成本中心为空的处理 zhicheng_jin 090319 --
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if(org == null){
				org = SysContext.getSysContext().getCurrentOrgUnit();
			}
			currentOrgId = org.getId().toString();

		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27如果不是新增状态，直接返回
		 * 然后分别判断成本中心和当前组织是否存在编码规则
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）
				isExist = false;
			}
		}
				
		if( isExist ){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}

	//重写EditUI的获取编码
    //通过编码规则获取编码。子类可用。
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              当前用户所属组织不存在时，缺省实现是用集团的
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // 启用了断号支持功能,同时启用了用户选择断号功能
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?是因为使用用户选择功能也是get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    // 没有启用断号支持功能，此时获取了编码规则产生的编码
                	String costCenterId = null;
            		if(editData instanceof FDCBillInfo && ((FDCBillInfo)editData).getOrgUnit() != null){
            			costCenterId = ((FDCBillInfo)editData).getOrgUnit().getId().toString();
            		}else{		
//            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();售楼组织成本中心为空 
            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
            		}
            		
                    number =  prepareNumberForAddnew(iCodingRuleManager,(FDCBillInfo)editData, orgId,costCenterId, null); 
                    	//iCodingRuleManager.getNumber(caller, orgId);
                }

                // 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //如果启用了用户可修改
                    setNumberTextEnabled();
                }
                return;
            }

            /*
            if (orgId != null && orgId.trim().length() > 0 && iCodingRuleManager.isExist(caller, orgId)) {
                String number = "";

                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) //此处的orgId
                                                                           // 与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                {
                    //启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                    number = iCodingRuleManager.readNumber(caller, orgId);
                } else {
                    //没有启用断号支持功能，此时获取了编码规则产生的编码
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                //把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                prepareNumber(caller, number);

                return;
            } else {
                //当前用户所属组织不存在时，缺省实现是用集团的
                String companyId = getNextCompanyId();

                if (companyId != null && companyId.trim().length() > 0 && iCodingRuleManager.isExist(caller, companyId)) {
                    String number = "";

                    if (iCodingRuleManager.isUseIntermitNumber(caller, companyId)) //此处的orgId
                                                                                   // 与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                    {
                        //启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                        number = iCodingRuleManager.readNumber(caller, companyId);
                    } else {
                        //没有启用断号支持功能，此时获取了编码规则产生的编码
                        number = iCodingRuleManager.getNumber(caller, companyId);
                    }

                    //把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                    prepareNumber(caller, number);

                    return;
                }
            }
            */
        } catch (Exception err) {
            //获取编码规则出错，现可以手工输入编码！
            handleCodingRuleError(err);

            //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
            setNumberTextEnabled();
        }

        //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
        setNumberTextEnabled();
    }
    
	
	//编码规则中启用了“新增显示”,必须检验是否已经重复
	protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,FDCBillInfo info,String orgId,String costerId,String bindingProperty)throws Exception{

		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//如果编码重复重新取编码
			if(bindingProperty!=null){
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			}else{
				number = iCodingRuleManager.getNumber(editData, orgId);
			}
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", number));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(),CompareType.NOTEQUALS));		
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (((IFDCBill)getBizInterface()).exists(filter) && i<1000);
		
		return number;
	}
	
	/**
	 * getNumberByCodingRule只提供了获取编码的功能，没有提供设置到控件的功能，实现此方法将根据编码规则的"是否新增显示"属性设置编码到控件
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		getNumberCtrl().setText(number);

	}

	protected void setNumberTextEnabled() {

		if(getNumberCtrl() != null) {
			OrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentOrgUnit();
			if(currentCostUnit == null){
				currentCostUnit = SysContext.getSysContext().getCurrentSaleUnit();
			}
			
			if(currentCostUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(getFDCBillInfo(), currentCostUnit.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}	
	}

	/**
	 *
	 * 描述：返回编码控件（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	abstract protected KDTextField getNumberCtrl();

	/**
	 * 子类重载此方法，负责清空一些不能重复的域的值
	 */
	protected void setFieldsNull(AbstractObjectValue newData) {
		FDCBillInfo info = (FDCBillInfo) newData;
		info.setNumber(null);
		info.setName(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setCreateTime(null);
		info.setCreator(null);
		info.setLastUpdateTime(null);
		info.setLastUpdateUser(null);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
	}

	protected void updateButtonStatus() {

		super.updateButtonStatus();
		actionUnAudit.setEnabled(true);
		handleActionStatusByCurOrg();
	}
	/**
	 *  因为基础框架新加了 菜单 复制分录 但是又没有效果 所以这里处理掉 
	 *  by jian_wen 2010.1.7
	 */
	public void initUIMenuBarLayout() {
		
		super.initUIMenuBarLayout();
		this.menuTable1.removeAll();
		this.menuTable1.add(menuItemAddLine);
		this.menuTable1.add(menuItemInsertLine);
		this.menuTable1.add(menuItemRemoveLine);
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		//保存不进行任何校验
		if(isSaveAction()) {
			verifyInputForSave();
			return;
		}

		verifyInputForSubmint();
		
		super.verifyInput(e);

	}

	/**
	 * 保存的时候需要校验的内容,
	 *  一般是必须录入的,如果不录入会出现异常的字段,如工程项目,如果不录入,则单据无法在序时簿显示
	 *
	 */
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}

	}
	
	protected void verifyInputForSubmint() throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
	}

	protected void initWorkButton() {

		super.initWorkButton();

		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);

		btnCalculator.setIcon(FDCClientHelper.ICON_CALCULATOR);
		menuItemCalculator.setIcon(FDCClientHelper.ICON_CALCULATOR);

		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
		menuItemAudit.setText(menuItemAudit.getText().replaceAll("\\(A\\)", "")+"(A)");
		menuItemAudit.setMnemonic('A');

		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setText(menuItemUnAudit.getText().replaceAll("\\(U\\)", "")+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setMnemonic('U');
	}

	public boolean isSaveAction() {
		return isSaveAction;
	}

	public void setSaveAction(boolean isSaveAction) {
		this.isSaveAction = isSaveAction;
	}

	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		//super.actionCalculator_actionPerformed(e);
/*		//这种处理方式会与表格的树型目录冲突,故去掉这个易用功能 sxhong
		if(getCalcValue() == null) return;

		boolean hasSet = setValueForFocusComp(getComponents(), getCalcValue());

		if(!hasSet && getDetailTable() != null) {
			int c = getDetailTable().getSelectManager().getActiveColumnIndex();
	    	int r = getDetailTable().getSelectManager().getActiveRowIndex();

	    	ICell cell = getDetailTable().getCell(r, c);
	    	if(cell!=null&&!cell.getStyleAttributes().isLocked()){
				cell.setValue(getCalcValue());
			}
		}
*/

    	CalculatorDialog calc = new CalculatorDialog(this);
        calc.showDialog(2, true);
	}

	/**
	 * 为当前焦点控件设置value
	 * @param components
	 * @param value
	 * @return
	 */
	private boolean setValueForFocusComp(Component[] components, BigDecimal value) {
		boolean found = false;
		if(found) return found;
		for (int i = 0; i < components.length; i++) {
			Component comp = components[i];
			if(comp instanceof KDLabelContainer) {
				JComponent boundEditor = ((KDLabelContainer)components[i]).getBoundEditor();
				if(boundEditor == null) continue;

				if(boundEditor instanceof KDFormattedTextField) {
					if(boundEditor.isFocusOwner()) {	//不知道为啥不是焦点
						((KDFormattedTextField)boundEditor).setValue(value);
						found = true;
					}
				}
			}
			else if(comp instanceof KDTabbedPane || comp instanceof KDPanel) {
				setValueForFocusComp(((JComponent)comp).getComponents(), value);
			}
		}

		return found;
	}

	/**
     *
     * 描述：检查是否有关联对象
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected void checkRef(String id) throws Exception {

    }

    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionCopy_actionPerformed(e);
    	handleCodingRule();
    }

    public void setOprtState(String oprtType) {
    	// TODO Auto-generated method stub
    	super.setOprtState(oprtType);

//    	FDCClientHelper.setCtrlEnabledByState(this.getComponents(), oprtType);

    	setAuditButtonStatus(oprtType);

    	refreshUITitle();
    }
    
    protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		FDCBillInfo bill = (FDCBillInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
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
	public RequestContext prepareActionSubmit(IItemAction itemAction)
	throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);
		
		return request;
	}
	
    /**
     * EditUI提供的初始化handler参数方法
     */
    protected void PrepareHandlerParam(RequestContext request)
    {
        super.PrepareHandlerParam(request);
        
        prepareInitDataHandlerParam(request);
    }
    
    protected void prepareInitDataHandlerParam(RequestContext request)
    {
		//合同Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if(contractBillId==null){
			Object object  = getUIContext().get("ID");
			if(object instanceof String){
				contractBillId = (String)object;
			}else if(object!=null){
				contractBillId = object.toString();
			}
		}
		request.put("FDCBillEditUIHandler.contractBillId",contractBillId);		
		
		//工程项目Id
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		request.put("FDCBillEditUIHandler.projectId",projectId);

		//合同类型ID
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		request.put("FDCBillEditUIHandler.typeId",typeId);
    }
    
}