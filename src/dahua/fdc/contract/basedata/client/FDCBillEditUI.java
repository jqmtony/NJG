/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;


import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.ITextLengthLimit;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.ColumnInfo;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.util.MetaDataLoader;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleEntryCollection;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.calc.CalculatorDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMapUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectValueUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractChangeBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
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

	//�Ƿ񱣴����
	public boolean isSaveAction = false;

	private static final Logger logger = CoreUIObject.getLogger(FDCBillEditUI.class);

	//������֯
	protected CompanyOrgUnitInfo company = null;
	
	//��ǰ��֯
	protected FullOrgUnitInfo orgUnitInfo = null;
	
	//��λ��
	protected CurrencyInfo baseCurrency = null;
	//��ͬ����
	protected ContractBillInfo contractBill = null;
	
	//��ǰ������Ŀ
	protected CurProjectInfo curProject = null;

    // ������⣺�����⣬�������޸ģ��鿴��ƾ֤����
    protected String titleMain, titleNew, titleModify, titleView, billIndex = "";

	//���óɱ��ɱ��½�
	protected boolean isIncorporation = false;
	
	//�Ǽ�����
	protected Date bookedDate = null;	
	//��ǰ�ڼ�
	protected PeriodInfo curPeriod = null ;
	//�ɱ����ǲ���
	protected boolean isCost = true;
	//�Ƿ��Ѿ�����
	protected boolean isFreeze = false;
	//��¼���ڼ�
	protected PeriodInfo canBookedPeriod = null ;
	
	// ��ǰ������������
	protected Date serverDate = null;

	/** �����뷴�����Ƿ�ͬһ���û� */
	protected boolean isSameUserForUnAudit = false;

	/**
	 * ʵ�����Info
	 */
	protected EntityObjectInfo entityObjectInfo = null;

	/**
	 * ��������У����������������Map
	 */
	protected Map valueAttributeMap = new LinkedHashMap();

	/**
	 * �Ƿ��Ǳ������Ըı�(������������������ԣ�������)
	 */
	private boolean codePropertyChanged = false;

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
    
    //�ؼ����ݱ仯ͳһ�¼�,ʹ�øù���ͳһ�ؼ��¼�,������loadFieldsʱ����ͣ�����ؼ��¼�
	public class ControlDateChangeListener implements DataChangeListener {
		private String shortCut = null; 
		public ControlDateChangeListener(String shortCut){
			this.shortCut = shortCut;
		}
        public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
            try {
            	controlDate_dataChanged(e,this);
            } catch (Exception exc) {
                handUIExceptionAndAbort(exc);
            } 
        }
		public String getShortCut() {
			return shortCut;
		}
    }
    
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	//ҵ��EditUI���أ�ʵ�ֿؼ����ݱ仯�¼�
    }
    

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			FDCMsgBox.showInfo("�����ѱ��޸ģ������ύ��");
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
	 * ͬ�����ݿ����ݵ�����,��������/����������ʾ������,��������
	 * @throws Exception
	 */
	protected void syncDataFromDB() throws Exception {
		//�ɴ��ݹ�����ID��ȡֵ����
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
		
		//���ò���ɱ�һ�廯
		enablePeriodComponent(this,isIncorporation);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTREMOVE);
		
		/* modified by zhaoqin for R131111-0034 on 2013/12/2 */
		// ��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, getFDCBillInfo().getId().toString(), "���ڹ����������У�����ִ�д˲�����");
		
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
			MsgBox.showWarning(this,"����״̬�Ѿ�������л�������ˣ��������ύ");
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
//			MsgBox.showWarning(this,"���óɱ��½��ڼ䲻��Ϊ�գ����ڻ�������ά���ڼ������ѡ��ҵ������");
//			SysUtil.abort();
//		}
		
		if(editData.getId()==null){
			return true;
		}
		//R110506-0417���������˹��ڵ����ύ���̺󱨴�. By zhiyuan_tang 2010-05-12
//		//����Ƿ��ڹ�������
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
            //added by ken...�ڲ���FDC216����Ϊ���񡱵�����£�����Ƶĵ���Ӧ���ܱ༭����
            FDCBillStateEnum state = null;
            //������������Ŀǰ�ܴ˲������Ƶĵ���Ϊ����ͬ�����ָ�����so..
            if(this instanceof ContractBillEditUI || this instanceof ContractChangeBillEditUI) {
            	FDCBillInfo billInfo = (FDCBillInfo) this.editData;
            	state = billInfo.getState();
            }
            
            if(state !=null ) {
            	boolean allowEditWhenAudited = FDCUtils.getBooleanValue4FDCParamByKey(null,
    					SysContext.getSysContext().getCurrentOrgUnit().getId()
    							.toString(), "FDC216_UPLOADAUDITEDBILL");
            	if(!allowEditWhenAudited) {
            		isEdit=ContractClientUtils.canUploadAttaForAudited(state, allowEditWhenAudited);//����listui���߼�..
            	}
            }
            //added by ken... end;
        }
        
        if(isEdit){
        	//��ͬ
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
				//���δ���ò�������Ȩ�޵��û����ܽ��и���ά��,����Ѿ������˲������Ƶ��˵��ڵ�ǰ�û�����Ȩ�޲��ܽ��� ά��
	        	if(hasFunctionPermission){
	        		//creatorId ==null; Ϊ����,��������
	        		if(creatorId!=null){
	        			boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
	        			if(creatorCtrl&&!creatorId.equals(userId)){
	        				//�Ƶ���Ҫ���ڵ�ǰ�û�����
	        				isEdit=false;
	        			}
	        		}
	        	}else{
	        		isEdit=false;
	        	}
	        }
	        //�������뵥
	        if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI")){
	        		String uiName = "com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI";
					boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
		    				new ObjectUuidPK(userId),
		    				new ObjectUuidPK(orgId),
		    				new MetaDataPK(uiName),
		    				new MetaDataPK("ActionAttamentCtrl") );
					//���δ���ò�������Ȩ�޵��û����ܽ��и���ά��,����Ѿ������˲������Ƶ��˵��ڵ�ǰ�û�����Ȩ�޲��ܽ��� ά��
		        	if(hasFunctionPermission){
		        		if(creatorId!=null){
		        			boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
		        			if(creatorCtrl&&!creatorId.equals(userId)){
		        				//�Ƶ���Ҫ���ڵ�ǰ�û�����
		        				isEdit=false;
		        			}
		        		}
		        	}else{
		        		isEdit=false;
		        	}
		        }
	    }
        acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID �� �븽�������� ҵ������ ID
       /* *//**
         * by ling_peng 2009-7-1      �����Ƿ����á��ʼ����ۡ�
         * ��Ф쭱뽻�������ֽ����ز�����ҵ��ϵͳ�ġ��������������á��ʼ����ۡ����ڷ��գ���֪���գ��ˡ��ʼ����ۡ�����ֻ֧���ܹ�����ת��ΪFDCBillInfo�ĵ��ݣ�
         * ���Ƿ��ز�ҵ��ϵͳ��һ���ֵ��ݻ��ಢ��FDCBillInfo������ֻ��Լ̳���FDCBillInfo�ĵ��ݡ�
         * ���ڴ��ڷ��գ���ҵ������ʱ����
         *//*
      if(this.editData instanceof FDCBillInfo){
    	   boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
           //����ϵͳ��׼������������������
           if(!isUseWriteMark){
           }else{//���á��ʼ����ۡ�
           	if(this.editData != null && this.editData.getId() != null){
       			UIContext uiContext = new UIContext();
       			uiContext.put("billInfo", this.editData);
       			//��Ϊ��Ҫ����״̬���ж���ʾ�� ContractContentUI.ui ��ͼ֮��˵�����Ĳ˵��Ƿ���ã�
       			//��Ҫ���������״̬(oprtState)��Ϊ�������ݸ� ContractContentUI.java 
       			uiContext.put("optState", oprtState);
       			//���������߼��жϣ����û��Ȩ��ά�������Ļ����Ǿͽ�OprtState��ֵ����Ϊֻ�ܲ鿴
       			if(!isEdit){
       				uiContext.put("optState", OprtState.VIEW);	
       			}
       			uiContext.put(UIContext.OWNER, this);
       			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractContentUI.class.getName(), uiContext);
       			window.show();
       		}
           }
       }else{
    	   acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID �� �븽�������� ҵ������ ID
       }*/
	}
	/**
	 *
	 * ���������FDCBillԶ�̽ӿ�
	 * @return
	 * @throws Exception
	 * @author:liupd
	 * ����ʱ�䣺2006-10-16 <p>
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
		
		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null,
				FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);
		
		if(isSameUserForUnAudit && editData.getAuditor() != null){
			
			if(!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())){
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		//��鵥���Ƿ��ڹ�������
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
//��edit�滻Ϊ�������Ĳ���warning xiaoao_liu
	protected void checkBeforeEditOrRemove(String warning) throws BOSException {
		FDCBillStateEnum state = getFDCBillInfo().getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}

	/**
	 *
	 * ���������ݵ�ǰ���ݵ�״̬����ִ�б��滹���ύ����
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

			// У��storeFields()�Ƿ��׳��쳣
			// Exception err = verifyStoreFields();

			// storeFields()�׳��쳣����editdata�иı䣬ѯ���Ƿ񱣴��˳�
			if (isModify()) {
				// editdata�иı�
				int result = MsgBox.showConfirm3(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Confirm_Save_Exit"));

				if (result == KDOptionPane.YES_OPTION) {
					if (getFDCBillInfo().getState() == null || getFDCBillInfo().getState() == FDCBillStateEnum.SAVED) {
						actionSave.setDaemonRun(false);
						// actionSubmit.actionPerformed(null);
						// by jakcy 2005-1-6
						// �����ָ�����ΪbeforeAction()��ʹ��ActionEvent��
						ActionEvent event = new ActionEvent(btnSave, ActionEvent.ACTION_PERFORMED, btnSave.getActionCommand());
						actionSave.actionPerformed(event);
						return !actionSave.isInvokeFailed();
					} else {
						actionSubmit.setDaemonRun(false);
						// actionSubmit.actionPerformed(null);
						// by jakcy 2005-1-6
						// �����ָ�����ΪbeforeAction()��ʹ��ActionEvent��
						ActionEvent event = new ActionEvent(btnSubmit, ActionEvent.ACTION_PERFORMED, btnSubmit.getActionCommand());
						actionSubmit.actionPerformed(event);
						return !actionSubmit.isInvokeFailed();
						// actionSubmit_actionPerformed(event);
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
		return null;
	}

	protected void createNewData(FDCBillInfo newData) {

		// ////////////////////////////////////////////////////////////////

		newData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		newData.setCreateTime(new Timestamp(serverDate.getTime()));
		newData.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
		newData.setLastUpdateTime(new Timestamp(serverDate.getTime()));

		// ////////////////////////////////////////////////////////////////

	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	/**
	 * �༭���棬���ô�ӡ�ʹ�ӡԤ������
	 * @Modified By Owen_wen 2010-12-06 ��private��Ϊprotected��
	 * ����ɿշ�����д��������ӡ�ʹ�ӡԤ������
	 */
	protected void disablePrintFunc() {
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
	}

	/* ������ӿ�,����ĳɳ��󷽷��������ʵ��
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getBizInterface()
	 */
	protected abstract ICoreBase getBizInterface() throws Exception;
	
	/*
	 *  ������ӿ�,����ĳɳ��󷽷��������ʵ��,���û�з�¼table,����null
	 * (non-Javadoc)
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getDetailTable()
	 */
	protected abstract KDTable getDetailTable();

	/**
	 * ������ȡ�÷��ز����ݶ���
	 * 
	 * @return
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	protected FDCBillInfo getFDCBillInfo() {
		return (FDCBillInfo) editData;
	}

	/**
	 * ������ȡ�õ�ǰ��֯����
	 * 
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	protected OrgUnitInfo getOrgUnitInfo() {
		FDCBillInfo billInfo = getFDCBillInfo();

		return getOrgUnitInfo(billInfo);
	}

	/**
	 * ������ȡ�õ�ǰ��֯����
	 * 
	 * @param billInfo
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	protected OrgUnitInfo getOrgUnitInfo(FDCBillInfo billInfo) {
		if (null == billInfo) {
			return null;
		}

		OrgUnitInfo orgUnitInfo = billInfo.getOrgUnit();
		if (null == orgUnitInfo) {
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
		}
		if (null == orgUnitInfo) {
			orgUnitInfo = SysContext.getSysContext().getCurrentSaleUnit();
		}

		return orgUnitInfo;
	}

	//�����������֮��,����Ϳ����޸ĵȲ���.���Խ��˷����ſ�,�Ա����
	protected void handleActionStatusByCurOrg() {
		if(true){
			//ȡ�������岻��������Ĺ涨 by sxhong
			return;
		}
		// ���������ɱ����ģ���������ɾ����
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
	 * �������������������ݣ�δ���޸ģ�ֱ�ӹر�ʱ�����ֱ�����ʾ
	 * ���ں�̨�߳�ִ�����Ż�
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
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

	
	//������
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
		//ע��������
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
		
		//�����ϼ�����
		attachListeners();
		//2009-1-21 ��loadFields����������������������ť״̬�ķ�������ͨ����һ����һ�����л�����ʱ����ȷ��ʾ��������������ť��
		setAuditButtonStatus(this.getOprtState());
	}

	protected void setSaveActionStatus() {
		if (getFDCBillInfo().getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}

	//�����������
	protected void fetchInitParam() throws Exception {

		if(company==null){
			return ;
		}
		//���óɱ�����һ�廯
		Map paramItem = (Map)ActionCache.get("FDCBillEditUIHandler.comParamItem");
		if(paramItem==null){
			isIncorporation = FDCUtils.getBooleanValue4FDCParamByKey(null, company.getId().toString(), FDCConstants.FDC_PARAM_INCORPORATION);
		}else if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
			isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
		}	
		
		// return paramItem;
	}

	//�����������
	protected void fetchInitData() throws Exception {
		//��ͬId
		String contractBillId = (String) getUIContext().get("contractBillId");
		if(contractBillId==null){
			Object object  = getUIContext().get("ID");
			if(object instanceof String){
				contractBillId = (String)object;
			}else if(object!=null){
				contractBillId = object.toString();
			}
		}
		//������ĿId
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		
		Map param = new HashMap();
		param.put("contractBillId",contractBillId);
		if(projectId!=null){
			param.put("projectId",projectId.toString());
		}
		
		//RPC��response��ȡ
		Map initData = (Map)ActionCache.get("FDCBillEditUIHandler.initData");
		if(initData==null){
			initData = ((IFDCBill)getBizInterface()).fetchInitData(param);
		}

		//��λ��
		baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		//��λ��
		company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		if(company==null){
			company =  SysContext.getSysContext().getCurrentFIUnit();
		}
		//
		orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if(orgUnitInfo==null){
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		
		//��ͬ����
		contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
		
		//��ǰ����
		bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
		if(bookedDate==null){
			bookedDate = new Date(FDCDateHelper.getServerTimeStamp().getTime());
		}		
		//��ǰ�ڼ�
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
		//�Ƿ��Ѿ�����
		if(initData.get(FDCConstants.FDC_INIT_ISFREEZE)!=null){
			isFreeze = ((Boolean)initData.get(FDCConstants.FDC_INIT_ISFREEZE)).booleanValue();
		}
		//��¼���ڼ�
		if(isFreeze){
			canBookedPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_BOOKEDPERIOD);
		}else{
			canBookedPeriod = curPeriod;
		}		

		//������Ŀ
		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
			
		//��ǰ����������
		serverDate = (Date)initData.get("serverDate");
		if(serverDate==null){
			serverDate = bookedDate;
		}
		
		// return initData;
			 
	}

	public void onLoad() throws Exception {

		// //////////////////////////////////////////////////////////////////////////

		// ��ȡ��ʼ����������
		fetchInitData();

		// ��ȡ����
		fetchInitParam();

		super.onLoad();

		// ��ͷ����
		initHeadStyle();

		// ��������
		initTableStyle();

		// ���ÿؼ���ť״̬
		setButtonStatus();

		// //////////////////////////////////////////////////////////////////////////

		if (isUseMainMenuAsTitle()) {
			FDCClientHelper.setUIMainMenuAsTitle(this);
		}

		handleCodingRule();

		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		btnSubmit.setToolTipText(FDCClientUtils.getRes("submit"));

		disablePrintFunc();
		updateButtonStatus();

		// �������н��ؼ�KDFormattedTextField��BasicNumberTextField�������Сֵ�����ȣ��Ҷ���
		FDCHelper.setComponentPrecision(this.getComponents(), FDCBillEditUI.PRECSION);

		setNumberTextEnabled();

		// ���ò���ɱ�һ�廯
		enablePeriodComponent(this, isIncorporation);

		// handleOldData();
		AmusementUI.addHideMenuItem(this, this.menuBiz);

		setAuditButtonStatus(this.getOprtState());

		// ���ñ༭������
		resetEditorLength(true);

		// //////////////////////////////////////////////////////////////////////////

		// ���غ�
		afterOnLoad();

		// //////////////////////////////////////////////////////////////////////////
	}

	/**
	 * ��������ͷ����
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 */
	protected void initHeadStyle() throws Exception {
	}

	/**
	 * ��������������
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 */
	protected void initTableStyle() throws Exception {
	}

	/**
	 * ���������ð�ť״̬
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 */
	protected void setButtonStatus() {
	}

	/**
	 * ���������غ�
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 */
	protected void afterOnLoad() throws Exception {
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��ȡ�ڼ�
	 * @param e				DataChange�¼�
	 * @param pkbookedDate	��Ҫ��ȡ�ڼ�����ڿؼ�
	 * @param cbPeriod		�ڼ�F7�ؼ�
	 * @param projectId		������ĿID
	 * @param isCost		�Ƿ���ȡ�ɱ��ڼ�
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
	        	FDCMsgBox.showConfirm2(this,"��ѡ���ڲ����ڹ��̵�ǰ�ڼ�֮ǰ");
	        	//R110114-028��ֻ��oldValue�ڹ��̵�ǰ�ڼ�֮���ʱ��Ž�ֵ�ָ���oldValue������������ѭ�� 
	        	if(((Date)e.getOldValue()).before(canBookedPeriod.getBeginDate())){
	        		pkbookedDate.setValue(canBookedPeriod.getBeginDate());
	        	} else {
	        		pkbookedDate.setValue(e.getOldValue());
	        	}
	        	this.abort();
	        }
	        
	        if(isCost){
	        	period = FDCUtils.fetchCostPeriod(projectId,bookedDate);
	        }else{
	        	period = FDCUtils.fetchFinacialPeriod(projectId,bookedDate);
	        }
	        
	        if (period!=null && bookedDate.before(period.getBeginDate())) {
	        	FDCMsgBox.showConfirm2(this,"��ѡ���ڲ����ڹ��̵�ǰ�ڼ�֮ǰ");
	        	
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
    			/* modified by zhaoqin for BT867689 on 2015/01/19 */
    			//FDCMsgBox.showConfirm2(this,"û�ж�Ӧ���ڼ䡣�����ڻ�������ά���ڼ�");
    			FDCMsgBox.showConfirm2(this,"û�ж�Ӧ���ڼ�,���ȵ�ϵͳƽ̨-ϵͳ����-ϵͳ״̬����������");
    			
	        	//R110114-028��ֻ��oldValue�ж�Ӧ���ڼ��ʱ��Ž�ֵ�ָ���oldValue������������ѭ�� 
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
				//ҵ�����ڿͻ����������޸�
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
	 * ����������
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		/*
		 * 2008-09-27��������ȡֵȡ���ˣ�Ӧ��ȡFDCBillInfo�й�����ID
		 */
		String currentOrgId = "";//FDCClientHelper.getCurrentOrgId();
		if(editData instanceof FDCBillInfo && ((FDCBillInfo)editData).getOrgUnit() != null){
			currentOrgId = ((FDCBillInfo)editData).getOrgUnit().getId().toString();
		}else{		
			//-- ������֯�»�ȡ�ɱ�����Ϊ�յĴ��� zhicheng_jin 090319 --
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if(org == null){
				org = SysContext.getSysContext().getCurrentOrgUnit();
			}
			currentOrgId = org.getId().toString();

		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27�����������״̬��ֱ�ӷ���
		 * Ȼ��ֱ��жϳɱ����ĺ͵�ǰ��֯�Ƿ���ڱ������
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��
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
						currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
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

	//��дEditUI�Ļ�ȡ����
    //ͨ����������ȡ���롣������á�
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
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
                            // ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?����Ϊʹ���û�ѡ����Ҳ��get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    // û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                	String costCenterId = null;
            		if(editData instanceof FDCBillInfo && ((FDCBillInfo)editData).getOrgUnit() != null){
            			costCenterId = ((FDCBillInfo)editData).getOrgUnit().getId().toString();
            		}else{		
//            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();��¥��֯�ɱ�����Ϊ�� 
            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
            		}
            		
                    number =  prepareNumberForAddnew(iCodingRuleManager,(FDCBillInfo)editData, orgId,costCenterId, null); 
                    	//iCodingRuleManager.getNumber(caller, orgId);
                }

                // ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //����������û����޸�
                    setNumberTextEnabled();
                }
                return;
            }

            /*
            if (orgId != null && orgId.trim().length() > 0 && iCodingRuleManager.isExist(caller, orgId)) {
                String number = "";

                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) //�˴���orgId
                                                                           // �벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                {
                    //�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                    number = iCodingRuleManager.readNumber(caller, orgId);
                } else {
                    //û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                //��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);

                return;
            } else {
                //��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                String companyId = getNextCompanyId();

                if (companyId != null && companyId.trim().length() > 0 && iCodingRuleManager.isExist(caller, companyId)) {
                    String number = "";

                    if (iCodingRuleManager.isUseIntermitNumber(caller, companyId)) //�˴���orgId
                                                                                   // �벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                    {
                        //�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.readNumber(caller, companyId);
                    } else {
                        //û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                        number = iCodingRuleManager.getNumber(caller, companyId);
                    }

                    //��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                    prepareNumber(caller, number);

                    return;
                }
            }
            */
        } catch (Exception err) {
        	// @AbortException
        	logger.error(err.getMessage(), err);
            //��ȡ�����������ֿ����ֹ�������룡
            handleCodingRuleError(err);

            //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
            setNumberTextEnabled();
        }

        //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
        setNumberTextEnabled();
    }
    
	
	//��������������ˡ�������ʾ��,��������Ƿ��Ѿ��ظ�
	protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,FDCBillInfo info,String orgId,String costerId,String bindingProperty)throws Exception{

		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//��������ظ�����ȡ����
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
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"�������ñ��뵽�ؼ�
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		getNumberCtrl().setText(number);

	}

	/**
	 * ���������ñ���ؼ��Ƿ�ɱ༭
	 * 
	 * @author skyiter_wang
	 * @createDate 2013-11-22
	 * @see com.kingdee.eas.framework.client.EditUI#setNumberTextEnabled()
	 */
	protected void setNumberTextEnabled() {
		KDTextField numberCtrl = getNumberCtrl();

		if (numberCtrl != null) {
			FDCBillInfo billInfo = getFDCBillInfo();
			if (null == billInfo) {
				numberCtrl.setEnabled(true);
				numberCtrl.setEditable(true);

				return;
			}

			OrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentOrgUnit();
			if (currentCostUnit == null) {
				currentCostUnit = SysContext.getSysContext().getCurrentSaleUnit();
			}
			if (null == currentCostUnit) {
				numberCtrl.setEnabled(true);
				numberCtrl.setEditable(true);

				return;
			}

			String orgId = currentCostUnit.getId().toString();
			String bindingPropertyName = getBindingProperty();
			Object bindingPropertyValue = null;
			boolean isAllowModify = true;

			if (FdcStringUtil.isNotBlank(bindingPropertyName)) {
				bindingPropertyValue = FdcObjectValueUtil.get(billInfo, bindingPropertyName);

				if (null != bindingPropertyValue) {
					isAllowModify = FDCClientUtils.isAllowModifyNumber(billInfo, orgId, bindingPropertyName);
				} else {
					isAllowModify = true;
				}
			} else {
				isAllowModify = FDCClientUtils.isAllowModifyNumber(billInfo, orgId);
			}

			numberCtrl.setEnabled(isAllowModify);
			numberCtrl.setEditable(isAllowModify);
		}
	}

	/**
	 *
	 * ���������ر���ؼ����������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
	 */
	abstract protected KDTextField getNumberCtrl();
	
	/**
	 * ������ȡ������
	 * <p>
	 * �����������а�������������ø�ֵ
	 * 
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2013-11-28
	 */
	protected String getBindingProperty() {
		return null;
	}

	/**
	 * �������ش˷������������һЩ�����ظ������ֵ
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
	 *  ��Ϊ��������¼��� �˵� ���Ʒ�¼ ������û��Ч�� �������ﴦ��� 
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
		//���治�����κ�У��
		if(isSaveAction()) {
			verifyInputForSave();
			return;
		}

		verifyInputForSubmint();
		
		super.verifyInput(e);

	}

	/**
	 * �����ʱ����ҪУ�������,
	 *  һ���Ǳ���¼���,�����¼�������쳣���ֶ�,�繤����Ŀ,�����¼��,�򵥾��޷�����ʱ����ʾ
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
/*		//���ִ���ʽ�����������Ŀ¼��ͻ,��ȥ��������ù��� sxhong
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
	 * Ϊ��ǰ����ؼ�����value
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
					if(boundEditor.isFocusOwner()) {	//��֪��Ϊɶ���ǽ���
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
     * ����������Ƿ��й�������
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
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
     * �ò˵�����ΪUI��ҳǩ��,���������
     * @return Ĭ�Ϸ���true
     */
    protected boolean isUseMainMenuAsTitle(){
    	return true;
    }
    
	/**
	 * RPC���죬�κ�һ���¼�ֻ��һ��RPC
	 */
	public RequestContext prepareActionSubmit(IItemAction itemAction)
	throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);
		
		return request;
	}
	
    /**
     * EditUI�ṩ�ĳ�ʼ��handler��������
     */
    protected void PrepareHandlerParam(RequestContext request)
    {
        super.PrepareHandlerParam(request);
        
        prepareInitDataHandlerParam(request);
    }
    
    protected void prepareInitDataHandlerParam(RequestContext request)
    {
		//��ͬId
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
		
		//������ĿId
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		request.put("FDCBillEditUIHandler.projectId",projectId);

		//��ͬ����ID
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		request.put("FDCBillEditUIHandler.typeId",typeId);
    }

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * �������Ƿ����������
	 * 
	 * @param propertyName
	 * @return
	 * @author skyiter_wang
	 * @createDate 2013-12-2
	 */
	protected boolean isInCode(String propertyName) {
		boolean flag = false;
		
		// ȡ��ʵ�����Info
		EntityObjectInfo entityObjectInfo = getEntityObjectInfo();

		if (null != entityObjectInfo) {
			PropertyInfo propertyInfo = entityObjectInfo.getPropertyByNameRuntime(propertyName);

			// Ԫ����û�ж���isInCode��չ���Ի�
			if (null == propertyInfo) {
				flag = false;
			} else {
				// �Ƿ����������
				String extendedPropertyValue = propertyInfo.getExtendedProperty("isInCode");
				flag = FdcObjectUtil.isEquals("true", extendedPropertyValue);
			}
		}

		// ���Ԫ����û�ж���isInCode��չ���Ի�isInCode��Ϊtrue����ôֱ�ӷ���
		if (!flag) {
			return flag;
		}

		Map valueAttributeMap = getValueAttributeMap();
		if (FdcMapUtil.isNotEmpty(valueAttributeMap)) {
			Set valueAttributeSet = valueAttributeMap.keySet();
			for (Iterator iterator = valueAttributeSet.iterator(); iterator.hasNext();) {
				String valueAttributeName = (String) iterator.next();

				// ֧�ּ�������
				if (valueAttributeName.startsWith(propertyName)) {
					return true;
				}
			}

			flag = false;
		} else {
			flag = false;
		}

		return flag;
	}

	/**
	 * ������ȡ�ó�ʼ��ʵ�����Info
	 * 
	 * @return
	 * @author skyiter_wang
	 * @createDate 2013-12-2
	 */
	protected EntityObjectInfo getEntityObjectInfo() {
		// ��ʼ��ʵ�����Info
		initEntityObjectInfo();

		return this.entityObjectInfo;
	}

	/**
	 * ������ȡ�ó�ʼ��ʵ�����Info
	 * 
	 * @return
	 * @author skyiter_wang
	 * @createDate 2013-12-2
	 */
	protected Map getValueAttributeMap() {
		return this.valueAttributeMap;
	}

	/**
	 * ��������ʼ��ʵ�����Info
	 * 
	 * @return
	 * @author skyiter_wang
	 * @createDate 2013-12-2
	 */
	protected void initEntityObjectInfo() {
		try {
			if (null == entityObjectInfo) {
				entityObjectInfo = MetaDataLoader.getEntity(null, getBizType());
			}
		} catch (Exception e) {
			/* TODO �Զ����� catch �� */
			e.printStackTrace();

			logger.error(e);
		}

	}

	/**
	 * ��������ȡ����������У�������������Map
	 * 
	 * @param orgId
	 * @param propertyName
	 * @return
	 * @author skyiter_wang
	 * @createDate 2013-12-2
	 */
	protected void fetchValueAttributeMap(String orgId, String bindingProperty) {
		try {
			// ȡ�ñ�������¼
			CodingRuleEntryCollection codingRuleEntryCollection = FdcCodingRuleUtil.getCodingRuleEntryCollection(null,
					this.editData, orgId, getBindingProperty());

			// ���������������
			valueAttributeMap = FdcObjectCollectionUtil.parsePropertyMap(codingRuleEntryCollection, "valueAttribute");
		} catch (Exception e) {
			/* TODO �Զ����� catch �� */
			e.printStackTrace();

			logger.error(e);
		}
	}

	public boolean isCodePropertyChanged() {
		return codePropertyChanged;
	}

	public void setCodePropertyChanged(boolean codePropertyChanged) {
		this.codePropertyChanged = codePropertyChanged;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
	/**
	 * ���ñ༭������
	 * <p>
	 * ������Ը�����Ҫ����isReset����ѡ���Ե�����
	 * 
	 * @param isReset
	 *            �Ƿ�����
	 * @throws Exception
	 */
	protected void resetEditorLength(boolean isReset) throws Exception {
		// ��ʱ������
		if (true) {
			// return;
		}

		// �鿴״̬������
		if (!isReset || STATUS_VIEW.equals(getOprtState())) {
			return;
		}

		// ȡ��ҵ��ӿ�
		ICoreBase coreBase = null;
		coreBase = getBizInterface();
		if (null == coreBase) {
			return;
		}

		// ����BOS����Ķ�������
		BOSObjectType bosType = coreBase.getType();
		// ����bosTypeȡ�ö�Ӧ��ʵ��
		EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(null, bosType);

		// ȡ��ʵ��Ķ�Ӧ�����ݿ��
		DataTableInfo dataTableInfo = entityObjectInfo.getTable();
		if (null == dataTableInfo) {
			return;
		}
		IMetaDataPK metaDataPK = new MetaDataPK(dataTableInfo.getFullName());
		dataTableInfo = MetaDataLoader.getDataTable(null, metaDataPK);
		if (null == dataTableInfo) {
			return;
		}

		// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)��ʹ���˻�����ԣ���Studio�в�Ӧʹ��
		PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();
		PropertyInfo propertyInfo = null;
		String propertyName = null;
		ColumnInfo columnInfo = null;
		String columnName = null;
		int columnLength = 0;
		Component component = null;
		ITextLengthLimit txtField = null;
		int cmpMaxLength = 0;

		// ȡ�ñ�ͷ������List
		// List headPropertyNameList = getHeadPropertyNameList();
		// if (FdcCollectionUtil.isEmpty(headPropertyNameList)) {
		// return;
		// }

		for (Iterator iterator = propertyCollection.iterator(); iterator.hasNext();) {
			propertyInfo = (PropertyInfo) iterator.next();
			propertyName = propertyInfo.getName();

			// if (!headPropertyNameList.contains(propertyName)) {
			// continue;
			// }

			// ����������ȡ�ð󶨵Ŀؼ�
			component = this.dataBinder.getComponetByField(propertyName);

			// �ı��༭��
			if (component instanceof KDTextField || component instanceof KDTextArea) {
				txtField = (ITextLengthLimit) component;
				columnInfo = propertyInfo.getMappingField();

				if (null != columnInfo) {
					columnName = columnInfo.getName();

					// ����
					if ("number".equals(propertyName)) {
						columnLength = FDCConstants.MAX_LENGTH_TXT_NUMBER;
					}
					// ����
					else if ("name".equals(propertyName)) {
						columnLength = FDCConstants.MAX_LENGTH_TXT_NAME;
					}
					// �ο���Ϣ
					// else if ("description".equals(columnName)) {
					// columnLength = FDCConstants.MAX_LENGTH_TXT;
					// }
					else {
						columnInfo = dataTableInfo.getColumnByName(columnName);
						if (null != columnInfo) {
							columnLength = columnInfo.getLength();
						}
					}
				} else {
					columnLength = -1;
				}

				// �����󳤶�
				cmpMaxLength = txtField.getMaxLength();
				if (0 < columnLength) {
					// û��������󳤶�
					if (0 >= cmpMaxLength) {
						txtField.setMaxLength(columnLength);
					}
					// �Զ��峤�ȳ������ݿ����
					else if (columnLength < cmpMaxLength) {
						txtField.setMaxLength(columnLength);
					}
				}
			}
		}
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	

}