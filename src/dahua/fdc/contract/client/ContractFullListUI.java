/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.finance.client.PaymentBillListUI;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)			ContractFullListUI			
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		��ͬ��ѯ����
 *		
 * @author		����		<p>
 * @createDate	2011-8-22	<p>	 
 * @version		EAS7.0		
 * @see					
 */
public class ContractFullListUI extends AbstractContractFullListUI {
	/** */
	private static final long serialVersionUID = 1L;

	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	private static final String CONTRACT_PROPERT = "�����ͬ";
	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	/** ���� */
	private Map contentMap = new HashMap();
	
	/** ����*/
	private Map attachMap = new HashMap();
	
	//���������� ����ʱ��
	private Map auditMap = new HashMap();
	
	//У��Ȩ����
	private String currentUserInfoId="";//�û�Id
	private String currentOrgUnitId="";//��֯Id

	/**
	 * Ĭ�Ϲ�����
	 */
	public ContractFullListUI() throws Exception {
		super();
	}

	protected String getEditUIName() {
		return ContractFullInfoUI.class.getName();
	}
	
	/**
	 * �ر�editui�� ���Զ�ˢ����ʱ�� modify by zhiqiao_yang at 2010-09-28
	 */
	protected void refresh(ActionEvent e) throws Exception {		
		if (e != null && e.getSource() != null && !e.getSource().equals(this.btnRefresh) ) {			
			return;
		}
//		super.refresh(e);
		refreshTblMain(this.getMainQuery());
	}

	/**
	 * description		ִ�в�ѯ
	 * @author			���� <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.framework.client.ListUI#execQuery()
	 */
	protected void execQuery() {
		super.execQuery();
		auditMap.clear();
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"amount","originalAmount"});
//		FDCClientHelper.initTable(tblMain);
		/**
		 * �����Ż���������map��ȡֵ��Map com.kingdee.eas.fdc.contract.app.ContractBillControllerBean._getOtherInfo��һ����ȡ��,����RPC
		 * by Cassiel_peng 2009-11-13
		 */
		/*Set contractIds = new HashSet() ;
		for(int i = 0; i < this.tblMain.getRowCount(); ++i)
		{
			IRow row = this.tblMain.getRow(i);
			String contractId = row.getCell("id").getValue().toString();
			contractIds.add(contractId);
		}
		
		*//**
		 * ���������,���б��л�ȡ��ͬ��ID�����е�ID���Ǻ�ͬ�����ID��
		 * Ȼ��ͨ�����ù�����Ϣ��ʹ��Զ�̵��ã��鿴��ͬ���ĸ����������Ƿ��к�ͬ��ID.
		 * ����ú�ͬ�����ĸ����ͽ��б�ǣ����򲻱��
		 *//*
		EntityViewInfo viewContent = new EntityViewInfo();
		FilterInfo filterContent = new FilterInfo();
		viewContent.getSelector().clear();
		viewContent.getSelector().add("contract.id");
		filterContent.getFilterItems().add(new FilterItemInfo("contract.id", contractIds, CompareType.INCLUDE));
		viewContent.setFilter(filterContent);
		try {
			ContractContentCollection colContent = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewContent);
			
			for(int j = 0; j < colContent.size(); ++j){
				contentMap.put(colContent.get(j).getContract().getId().toString(),Boolean.TRUE);
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		*//**
		 * ����Ф쭱��ڱ������ĵ�ʱ����Ҫ�������ز�ϵͳ����ҵ�񵥾ݶ����ñ�û��ֱ��ʹ��֮ǰϵͳ���е�contract����ֶ�Ϊ���ĵ�ҵ�񵥾ݸ�������¼���
		 * �ֶ�parent�����Ա����ʱ�����ݲ�ͬ��֮��鿴�޸ĵ�ʱ����ܾͻ�����ܶ����⡣����ID��ͬ�����������������û��߽��ò���"���ز�ҵ��ϵͳ���Ĺ������������ʼ����۹���"
		 * ����ģ�Ҳ����˵���û���ֶ����û����ǽ��ò����Ļ����ǵ�ID���Ǵ��ڲ�ͬ�������⣬����ֱ�ӵ�Ӱ������ں�ͬ¼����ʱ���ͺ�ͬ��ѯ��ʱ���ϵ�"����"һ�е���Ϣ��ʾ��׼ȷ
		 * ����Ŀǰ����������ز�ϵͳ��ֻ�к�ͬģ�����˸ù��ܣ�����FParent��ʵ���Ǻ�ͬ��ID(FContractId)���ǽ���������ֻ��FParentΪ��������һ�鿩�������� by Cassiel_peng 2009-9-6
		 *//*
		EntityViewInfo _viewContent1 = new EntityViewInfo();
		FilterInfo _filterContent1 = new FilterInfo();
		_viewContent1.getSelector().clear();
		_viewContent1.getSelector().add("parent");
		_filterContent1.getFilterItems().add(new FilterItemInfo("parent", contractIds, CompareType.INCLUDE));
		_viewContent1.setFilter(_filterContent1);
		try {
			ContractContentCollection colContent = ContractContentFactory.getRemoteInstance().getContractContentCollection(_viewContent1);
			
			for(int j = 0; j < colContent.size(); ++j){
				contentMap.put(colContent.get(j).getParent(),Boolean.TRUE);
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		//��ȡ���������� ����ʱ����Ϣ
		try {
			auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(contractIds);
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
		}
		*//**
		 * ��丽����,���б��л�ȡ��ͬ��ID�����е�ID���Ǻ�ͬ�����ID��
		 * Ȼ��ͨ�����ù�����Ϣ��ʹ��Զ�̵��ã��鿴��ͬ���ĸ����������Ƿ��к�ͬ��ID.
		 * ����ú�ͬ��ҵ����ظ����ͽ��б�ǣ����򲻱��
		 *//*
		EntityViewInfo viewAttachment = new EntityViewInfo();
		FilterInfo filterAttachment = new FilterInfo();
		_viewContent1.getSelector().clear();
		viewAttachment.getSelector().add("boID");
		filterAttachment.getFilterItems().add(new FilterItemInfo("boID", contractIds, CompareType.INCLUDE));
		viewAttachment.setFilter(filterAttachment);
		try {
			BoAttchAssoCollection colAttachment = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(viewAttachment);
			
			for(int j = 0; j < colAttachment.size(); ++j)
				attachMap.put(colAttachment.get(j).getBoID().toString(),Boolean.TRUE);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		for(int k = 0; k < this.tblMain.getRowCount(); k++)
		{
			IRow row = this.tblMain.getRow(k);
			//��ȡÿһ��row��keyֵ
			String key = row.getCell("id").getValue().toString();
			//������������˺�����ʱ��
			if(auditMap.containsKey(key)){
			MultiApproveInfo info = (MultiApproveInfo)auditMap.get(key);
			row.getCell("wfAuditName").setValue(info.getCreator().getName());
			row.getCell("wfAuditTime").setValue(info.getCreateTime());
			}
			if(attachMap.containsKey(row.getCell("id").getValue()))
				row.getCell("attachment").setValue(Boolean.TRUE);
			else
				row.getCell("attachment").setValue(Boolean.FALSE);
			
			if(contentMap.containsKey(row.getCell("id").getValue()))
				row.getCell("content").setValue(Boolean.TRUE);
			else
				row.getCell("content").setValue(Boolean.FALSE);
		}*/
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractBillFactory.getRemoteInstance();
	}


	/**
	 * description		��ʼ����ѯ����
	 * @author			���� <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.framework.client.ListUI#initCommonQueryDialog()
	 */
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());		
		return commonQueryDialog;
	}
	
	protected boolean isShowAttachmentAction() {
		
		return true;
	}
	
	/**
	 * description		���ز�ѯ���
	 * @author			����<p>	
	 * @createDate		2011-8-23<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		return this.filterUI;
	}

	boolean hasCurrency = false;
	
	/**
	 * 
	 * ��ʼ��Ĭ�Ϲ�������
	 * 
	 * @return ��������ˣ������˳�ʼ�����������뷵��true;Ĭ�Ϸ���false;
	 */
	protected boolean initDefaultFilter() {
		return !FDCMsgBox.isHidedBox();
	}
	
	public void onLoad() throws Exception {
		currentUserInfoId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
		currentOrgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				boolean isMainOrder = false;
				SorterItemCollection sortItems = mainQuery.getSorter();
				if(sortItems!=null && sortItems.size()>0){
		        	for(Iterator it=sortItems.iterator();it.hasNext();){
		        		SorterItemInfo sortItem = (SorterItemInfo)it.next();
		        		if(sortItem.getPropertyName().equals("mainContractNumber")){
		        			isMainOrder = true;
		        			break;
		        		}
		        	}
		        }
				if(attachMap==null){
					attachMap=new HashMap();
				}
				if(auditMap==null){
					auditMap=new HashMap();
				}
				if(contentMap==null){
					contentMap=new HashMap();
				}
				String preNumber = null;
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblMain.getRow(i);
					String idkey = row.getCell("id").getValue().toString();
					if(contentMap.containsKey(idkey)){
						row.getCell("content").setValue(Boolean.TRUE);
					}
					else{
						row.getCell("content").setValue(Boolean.FALSE);
					}
					if(attachMap.containsKey(idkey)){
						row.getCell("attachment").setValue(Boolean.TRUE);
					}
					else{
						row.getCell("attachment").setValue(Boolean.FALSE);
					}
					if(auditMap.containsKey(idkey)){
						MultiApproveInfo info = (MultiApproveInfo)auditMap.get(idkey);
						//����ж༶��������˱���ͻᵼ����������˺��������ʱ�����ݲ���ȷ,BUG,by Cassiel_peng
//						if(row.getCell("wfAuditName").getValue()==null){
							row.getCell("wfAuditName").setValue(info.getCreator().getName());
						//�����벻֪���Ƕ��֮ǰ��λ����д���ˣ����Ƶ�ʱ�丳�����Ƶ�����һ�У������Ƶ��¼�ûֵ���Ƶ���ȴ�����ڣ�����ѽ��by cassiel_peng
//							row.getCell("wfAuditName").setValue(info.getCreateTime());
							row.getCell("wfAuditTime").setValue(info.getCreateTime());
//						}
					}
					if(isMainOrder){
//							 node = new CellTreeNode();
						String number = "";
						if(row.getCell("mainContractNumber")!=null){
							if (row.getCell("mainContractNumber").getValue() != null) {
								number = row.getCell("mainContractNumber").getValue().toString();
							}
							if (!number.equals(preNumber)) {
								row.setTreeLevel(0);
							} else  if (!"".equals(number)){//
								row.setTreeLevel(1);
								tblMain.getTreeColumn().setDepth(2);
								row.getCell("number").setValue("   " + row.getCell("number").getValue());
							}
							preNumber = number;
							
							//tblMain.getColumn("mainContractNumber").getStyleAttributes().setHided(true);
						}
					}
				}
			}
		});
		
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();
		FDCClientHelper.initTableListener(tblMain,this);
		
		FDCHelper.formatTableNumber(tblMain, new String[]{"amount", "originalAmount", "exRate","conPaid"});
		FDCHelper.formatTableDateTime(tblMain,"wfAuditTime");
		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		super.onLoad();
		afterOnLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//ֻ����ѡ����
		//��ʾͼ��
		initWorkButton();
		initTableStyle();
		
		refreshTblMain(this.getMainQuery());
		
	}

	private void afterOnLoad() {
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionAudit.setEnabled(false);
		this.actionAntiAudit.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionAntiAudit.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		FDCClientHelper.initTable(tblMain);
		
		//��ʾ��������ť�͸�������˵�
//		this.actionAttachment.setVisible(true);
//		this.actionAttachment.setEnabled(true);
		
		this.actionViewContent.setVisible(true);
		this.actionViewContent.setEnabled(true);
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
    protected boolean isIgnoreCUFilter() {
        return true;
    }
    
	public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception {
//		super.actionAntiAudit_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		checkSelected();
//
//		checkBillState(getStateForAudit(), "selectRightRowForAudit");
//
//		audit(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
//
//		super.actionAudit_actionPerformed(e);
//		
//		showOprtOKMsgAndRefresh();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	return;
    }
	
	protected OrgType getMainBizOrgType() {
		return OrgType.CostCenter;
	}
	
	/**
     * �����Ƿ���ʾ�ϼ���
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }
    
    /**
	 * �鿴����   by Cassiel_peng 
	 */
	public void actionViewContent_actionPerformed(ActionEvent arg0) throws Exception {
		this.checkSelected();
		//����ID���ʵ���BOSType
        BOSObjectType bosType = BOSUuid.read(getSelectedKeyValue()).getType(); 
        //����BOSType���ʵ����ýӿ�
        ICoreBase coreBase = (ICoreBase) BOSObjectFactory.createRemoteBOSObject(bosType, ICoreBase.class);
        //��øö���
        CoreBaseInfo objectInfo = coreBase.getValue(new ObjectUuidPK(getSelectedKeyValue()));  
       
    	//���ز�ҵ��ϵͳ���Ĺ����Ƿ����������ʼ����۹���
		boolean isUseWriteMark = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_WRITEMARK);

		//����ֵΪ�ǣ����һ���Ҫ��FDCBill.
        if(isUseWriteMark&&objectInfo!=null&&objectInfo instanceof FDCBillInfo){
        	ForWriteMarkHelper.isUseWriteMarkForListUI((FDCBillInfo)objectInfo,OprtState.VIEW,this);
        }else{
        	ContractClientUtils.viewContent(this, this.getSelectedKeyValue());
        }
	}
	
	/**
	 * ��ͬ���:��Ҫ������ͬ�����ʱ����������ְ�ť���е��߼���"���۲��"��Ϊ��������������ͻ�ʹ�ùʲ��ڿ���֮��
	 *  by Cassiel_peng  2009-11-17
	 */
	public void actionContractSplit_actionPerformed(ActionEvent e)
			throws Exception {
		
		ContractFullListUtils.verifyAudited(tblMain);//�����У��
		
		Object contractId=this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		String UI="com.kingdee.eas.fdc.contract.client.ContractCostSplitListUI";
		String edit_action_permission="ActionCostSplit";
		String view_action_permission="ActionView";
		
		boolean hasEditPermission=ContractFullListUtils.verifyPermission(UI,edit_action_permission,currentUserInfoId,currentOrgUnitId);//У���Ƿ����޸�Ȩ��
		boolean hasViewPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);//У���Ƿ��в鿴Ȩ��
		
		if(ContractFullListUtils.isCostSplit(this.tblMain)){//�ɱ����ͬ
			EntityViewInfo view=new EntityViewInfo();
			view.getSelector().add("id");
			view.getSelector().add("state");
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill",contractId==null?"":contractId.toString()));
			filter.getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));//���ϵĹ��˳�ȥ
			view.setFilter(filter);
			ContractCostSplitCollection conCostSplitColl=ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection(view);
			if(conCostSplitColl!=null&&conCostSplitColl.size()==0){//δ���
				if(hasEditPermission){//��ά��Ȩ�ޣ������û�ά��
//					boolean canSplit=ContractFullListUtils.verifyCanSplit(this.tblMain,true);//У���Ƿ��ܹ����(���ں�ͬ��δ��֣����Բ����ڲ���Ƿ�����֮˵�����Ǹ���)
//					if(canSplit){//����Ѿ�������
						UIContext costSplitUIContext = new UIContext(this);
						costSplitUIContext.put("costBillID",contractId==null?"":contractId.toString());
						IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"ADDNEW");
						costSplitUIWindow.show();
//					}else{//���û�б�����
//						
//					}
				}else{
					FDCMsgBox.showWarning(this,"�ú�ͬ��δ��֣���������û��ά��Ȩ�ޣ�������ֹ��");
					SysUtil.abort();
				}
			}else if(conCostSplitColl!=null&&conCostSplitColl.size()!=0){//�Ѳ��
				ContractCostSplitInfo conCostSplitInfo=(ContractCostSplitInfo)conCostSplitColl.get(0);
				if(conCostSplitInfo.getId()!=null&&conCostSplitInfo.getState()!=null){
					if(conCostSplitInfo.getState().equals(FDCBillStateEnum.AUDITTED)){//������
						if(hasViewPermission){						//�Բ鿴��ʽ��
							UIContext costSplitUIContext = new UIContext(this);
							costSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
							IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"VIEW");
							costSplitUIWindow.show();
						}else{
							FDCMsgBox.showWarning(this,"�ú�ͬ��Ӧ�ĺ�ͬ����Ѿ�������������û�в鿴Ȩ�ޣ�������ֹ��");
							SysUtil.abort();
						}
					}else{//δ����
						if(hasEditPermission){//��ά��Ȩ�ޣ������û�ά��
							boolean canSplit=	ContractFullListUtils.verifyCanSplit(this.tblMain,true);//У���Ƿ��ܹ����
							if(canSplit){//���δ������
								UIContext costSplitUIContext = new UIContext(this);
								costSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
								IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"EDIT");
								costSplitUIWindow.show();
							}else{//����ѱ����ã�������Ƿ��в鿴Ȩ��
								if(hasViewPermission){
									UIContext costSplitUIContext = new UIContext(this);
									costSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
									IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"VIEW");
									costSplitUIWindow.show();
								}else{
									FDCMsgBox.showWarning("�ú�ͬ��Ӧ�ĺ�ͬ����Ѿ������ã��������޸Ĳ�ֵ��ݣ������鿴������������û�в鿴Ȩ�ޣ�������ֹ��");
									SysUtil.abort();
								}
							}
						}else{//��ά��Ȩ�ޣ���У���Ƿ��в鿴Ȩ��
							if(!hasViewPermission){
								FDCMsgBox.showWarning(this,"�ú�ͬ��Ӧ�ĺ�ͬ�����δ��������������û��ά�������ǲ鿴��ͬ��ֵ�Ȩ�ޣ�������ֹ��");
								SysUtil.abort();
							}else{//�в鿴Ȩ�ޣ������û��鿴
								UIContext costSplitUIContext = new UIContext(this);
								costSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
								IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"VIEW");
								costSplitUIWindow.show();
							}
						}
					}
				}
			}
		}else{//�ǳɱ����ͬ
			EntityViewInfo view=new EntityViewInfo();
			view.getSelector().add("id");
			view.getSelector().add("state");
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill",contractId==null?"":contractId.toString()));
			filter.getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));//���ϵĹ��˳�ȥ
			view.setFilter(filter);
			ConNoCostSplitCollection conNoCostSplitColl=ConNoCostSplitFactory.getRemoteInstance().getConNoCostSplitCollection(view);
			if(conNoCostSplitColl!=null&&conNoCostSplitColl.size()==0){//δ���
				if(hasEditPermission){//��ά��Ȩ�ޣ������û�ά��
					UIContext nonCostSplitUIContext = new UIContext(this);
					nonCostSplitUIContext.put("costBillID",contractId==null?"":contractId.toString());
					IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"ADDNEW");
					nonCostSplitUIWindow.show();
				}else{
					FDCMsgBox.showWarning(this,"�ú�ͬ��δ��֣���������û��ά��Ȩ�ޣ�������ֹ��");
					SysUtil.abort();
				}
			}else if(conNoCostSplitColl!=null&&conNoCostSplitColl.size()!=0){//�Ѳ��
				ConNoCostSplitInfo conCostSplitInfo=(ConNoCostSplitInfo)conNoCostSplitColl.get(0);
				if(conCostSplitInfo.getId()!=null&&conCostSplitInfo.getState()!=null){
					if(conCostSplitInfo.getState().equals(FDCBillStateEnum.AUDITTED)){//������
						if(hasViewPermission){						//�Բ鿴��ʽ��
							UIContext nonCostSplitUIContext = new UIContext(this);
							nonCostSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
							IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"VIEW");
							nonCostSplitUIWindow.show();
						}else{
							FDCMsgBox.showWarning(this,"�ú�ͬ��Ӧ�ĺ�ͬ����Ѿ�������������û�в鿴Ȩ�ޣ�������ֹ��");
							SysUtil.abort();
						}
					}else{//δ����
						if(hasEditPermission){//��ά��Ȩ�ޣ������û�ά��
							
							boolean canSplit=ContractFullListUtils.verifyCanSplit(this.tblMain,false);//У���Ƿ��ܹ����
							if(canSplit){//���δ������
								UIContext nonCostSplitUIContext = new UIContext(this);
								nonCostSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
								IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"EDIT");
								nonCostSplitUIWindow.show();
							}else{//����ѱ����ã���У���Ƿ��в鿴Ȩ��
								if(hasViewPermission){
									UIContext nonCostSplitUIContext = new UIContext(this);
									nonCostSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
									IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"VIEW");
									nonCostSplitUIWindow.show();
								}else{
									FDCMsgBox.showWarning("�ú�ͬ��Ӧ�ĺ�ͬ����Ѿ������ã��������޸Ĳ�ֵ��ݣ������鿴������������û�в鿴Ȩ�ޣ�������ֹ��");
									SysUtil.abort();
								}
							}
						}else{//��ά��Ȩ�ޣ���У���Ƿ��в鿴Ȩ��
							if(!hasViewPermission){
								FDCMsgBox.showWarning(this,"�ú�ͬ��Ӧ�ĺ�ͬ�����δ��������������û��ά�������ǲ鿴��ͬ��ֵ�Ȩ�ޣ�������ֹ��");
								SysUtil.abort();
							}else{//�в鿴Ȩ�ޣ������û��鿴
								UIContext nonCostSplitUIContext = new UIContext(this);
								nonCostSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
								IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"VIEW");
								nonCostSplitUIWindow.show();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * �������ǩ֤����     by Cassiel_peng  2009-11-17
	 */
	public void actionAddChangeAudit_actionPerformed(ActionEvent e)
			throws Exception {
		ContractFullListUtils.verifyHasSettled(tblMain);
		Object contractId=this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		String UI="com.kingdee.eas.fdc.contract.client.ChangeAuditListUI";
		String view_action_permission="ActionAddNew";
		boolean hasAddPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);
		if(hasAddPermission){
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",contractId));
			SelectorItemCollection sic = new SelectorItemCollection();
			view.setSelector(sic);
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("name"));
	        sic.add(new SelectorItemInfo("partB.number"));
	        sic.add(new SelectorItemInfo("partB.name"));
	        sic.add(new SelectorItemInfo("curProject.id"));
			sic.add(new SelectorItemInfo("curProject.name"));
			sic.add(new SelectorItemInfo("curProject.number"));
			sic.add(new SelectorItemInfo("curProject.cu"));
			
			sic.add(new SelectorItemInfo("currency.number"));
			sic.add(new SelectorItemInfo("currency.name"));		
			sic.add(new SelectorItemInfo("currency.precision"));	
			sic.add(new SelectorItemInfo("isCoseSplit"));	
			sic.add(new SelectorItemInfo("hasSettled"));	
			sic.add(new SelectorItemInfo("currency.precision"));
			ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId.toString())), sic);
			UIContext changeAuditUIContext = new UIContext(this);
			changeAuditUIContext.put("contract", info);
			changeAuditUIContext.put("contractBillId", contractId==null?"":contractId.toString());
			changeAuditUIContext.put("projectId", info.getCurProject().getId());
			IUIWindow  changeAuditUIWindow= UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ChangeAuditEditUI.class.getName(), changeAuditUIContext, null,"ADDNEW");
			changeAuditUIWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"��û������Ȩ�ޣ�������ֹ��");
			SysUtil.abort();
		}
	}
	/**
	 * �������㵥       by Cassiel_peng  2009-11-17
	 */
	public void actionAddContractSettlement_actionPerformed(ActionEvent e)
	throws Exception {
		ContractFullListUtils.verifyAudited(tblMain);
		ContractFullListUtils.verifyHasSettled(tblMain);
		
		Object contractId=this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		String UI="com.kingdee.eas.fdc.contract.client.ContractSettlementBillListUI";
		String view_action_permission="ActionAddNew";
		boolean hasAddPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);
		if(hasAddPermission){
			UIContext conSettleUIContext = new UIContext(this);
			conSettleUIContext.put("contractBillId", contractId==null?"":contractId.toString());
			IUIWindow  conSettleUIWindow= UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractSettlementBillEditUI.class.getName(), conSettleUIContext, null,"ADDNEW");
			conSettleUIWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"��û������Ȩ�ޣ�������ֹ��");
			SysUtil.abort();
		}
	}

	/**
	 * �����������뵥     by Cassiel_peng  2009-11-17
	 */
	public void actionAddPayRequest_actionPerformed(ActionEvent e)
			throws Exception {
		ContractFullListUtils.verifyAudited(tblMain);
		
		Object contractId=this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		String UI="com.kingdee.eas.fdc.contract.client.PayRequestBillListUI";
		String view_action_permission="ActionAddNew";
		boolean hasAddPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);
		if(hasAddPermission){
			UIContext changeAuditUIContext = new UIContext(this);
			changeAuditUIContext.put("contractBillId", contractId==null?"":contractId.toString());
			IUIWindow  changeAuditUIWindow= UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PayRequestBillEditUI.class.getName(), changeAuditUIContext, null,"ADDNEW");
			changeAuditUIWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"��û������Ȩ�ޣ�������ֹ��");
			SysUtil.abort();
		}
	}
	/**
	 * �����ʱ��   by Cassiel_peng  2009-11-17
	 */
	public void actionPaymentListUI_actionPerformed(ActionEvent e)
			throws Exception {
		if(tblMain.getRowCount()==0){
			FDCMsgBox.showWarning("����ѡ��һ�е��ݣ�");
			SysUtil.abort();
		};
		String UI="com.kingdee.eas.fdc.finance.client.PaymentBillListUI";
		String view_action_permission="ActionView";
		boolean hasViewPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);
		if(hasViewPermission){
			UIContext paymentListUIContext = new UIContext(this);
			IUIWindow paymentListUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PaymentBillListUI.class.getName(), paymentListUIContext, null,"EDIT");
			paymentListUIWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"��û�в鿴Ȩ�ޣ�������ֹ��");
			SysUtil.abort();
		}
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.btnContractSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnAddChangeAudit.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.btnAddContractSettlement.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.btnAddPayRequest.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.btnPaymentListUI.setIcon(EASResource.getIcon("imgTbtn_listfile"));
		this.menuItemView.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemViewContent.setAction(btnViewContent.getAction());
		this.menuItemContractSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.menuItemAddChangeAudit.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.menuItemAddConSettlement.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.menuItemAddPayRequest.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.menuItemPaymentListUI.setIcon(EASResource.getIcon("imgTbtn_listfile"));
		this.btnAuditResult.setEnabled(true);
        this.btnAuditResult.setVisible(true);
	}
	protected void updateButtonStatus() {
//		super.updateButtonStatus();
	}
	
	public void refreshListForOrder() throws Exception
    {
        SorterItemCollection sortItems = this.mainQuery.getSorter();
        if(sortItems!=null && sortItems.size()>0){
        	for(Iterator it=sortItems.iterator();it.hasNext();){
        		SorterItemInfo sortItem = (SorterItemInfo)it.next();
        		if(sortItem.getPropertyName().equals("mainContractNumber")){
        			SorterItemInfo cloneSort = (SorterItemInfo)sortItem.clone();
        			SorterItemInfo newSort = new SorterItemInfo();
        			newSort.setPropertyName("contractPropert");
        			newSort.setSortType(SortType.ASCEND);
        			this.mainQuery.getSorter().clear();
        			this.mainQuery.getSorter().add(cloneSort);
        			this.mainQuery.getSorter().add(newSort);
        			break;
        		}
        	}
        }
        refreshTblMain(mainQuery);
        
        /**
         * ��tblMain����������˺���������ʱ�丳ֵ��by neo
         */
        auditMap.clear();
        Set contractIds = new HashSet() ;
		for(int i = 0; i < this.tblMain.getRowCount(); ++i)
		{
			IRow row = this.tblMain.getRow(i);
			String contractId = row.getCell("id").getValue().toString();
			contractIds.add(contractId);
		}
		try {
			auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(contractIds);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
		}
        for(int k = 0; k < this.tblMain.getRowCount(); k++)
		{
			IRow row = this.tblMain.getRow(k);
			//��ȡÿһ��row��keyֵ
			String key = row.getCell("id").getValue().toString();
			//������������˺�����ʱ��
			if(auditMap.containsKey(key)){
				MultiApproveInfo info = (MultiApproveInfo)auditMap.get(key);
				row.getCell("wfAuditName").setValue(info.getCreator().getName());
				row.getCell("wfAuditTime").setValue(info.getCreateTime());
			}
		}
    }

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	boolean isEdit=false;
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
       
        acm.showAttachmentListUIByBoID(boID, this,isEdit); // boID �� �븽�������� ҵ������ ID
    }
	
	/**
	 * ���ò�ѯ��
	 * @author owen_wen 2010-8-25
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
		
		queryExecutor.option().isAutoIgnoreZero = false;
		
		return queryExecutor;
	}
	
	/**
	 * ��ʼ�������ʽ
	 * @author owen_wen 2010-8-25
	 */
	private void initTableStyle(){
		FDCHelper.formatTableNumber(getMainTable(), "amount");
		FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		FDCHelper.formatTableDate(getMainTable(), "wfAuditTime");
	}
	
	/**
	 * RPC���죬�κ�һ���¼�ֻ��һ��RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }

	/**
	 * description		���غ�ͬ�ķ�¼����
	 * @author			����<p>	
	 * @createDate		2011-8-17<p>
	 * @param			billInfo ��ͬ����
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private ContractBillEntryCollection getContractBillEntryCols(ContractBillInfo billInfo){
		ContractBillEntryCollection cols = null;
		if (null != billInfo && null != billInfo.getEntrys()) {
			cols = billInfo.getEntrys();
		}
		
		return cols;
	}
	
	/**
	 * description		�жϲ����ͬ�Ƿ� ��������
	 * @author			����<p>	
	 * @createDate		2011-8-17<p>
	 * @param			cols ��¼����
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private boolean isLonelyCal(ContractBillEntryCollection cols){
		boolean isLonelyCal = false;
		if (null != cols) {
			ContractBillEntryInfo entryInfo = null;
			for (int i = 0; i < cols.size(); i++) {
				entryInfo = cols.get(i);
				if (null != entryInfo.getDetail() && entryInfo.getDetail().toString().equals("�Ƿ񵥶�����")) {
					String content = entryInfo.getContent();
					if (content.equals("��")) {
						isLonelyCal = true;
					}else {
						isLonelyCal = false;
					}
				}
			}
		}
		
		return isLonelyCal;
	}
	
	/**
	 * description		��������ͬ�����Ĳ����ͬID����
	 * @author			����<p>	
	 * @createDate		2011-8-17<p>
	 * @param			contractBillId ����ͬID
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @throws BOSException 
	 * @throws SQLException 
	 * @see							
	 */
	private HashSet getSupplyContract(String contractBillId) throws BOSException, SQLException{
		HashSet suppplySet = new HashSet();
		
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select bill.fid id,bill.fcontractpropert ,entry.* from t_con_contractbill bill left outer join t_con_contractbillentry entry on bill.fid=entry.fparentid ");
		buildSQL.appendSql("where bill.fcontractpropert = 'SUPPLY' and entry.fcontent = '"+ contractBillId + "'");
		IRowSet rowSet = buildSQL.executeQuery();
		while (rowSet.next()) {
			String id = rowSet.getString("id");
			suppplySet.add(id);
		}
		return suppplySet;
	}
	
	/**
	 * modify haiou_li,2013.12.10
	 * @description:���غ�ͬ�б��˴�����Ӱ�����ܣ��ڵ�һ���к�ͬ���в�ѯ�������������ļ�¼��
	 *              ���Ǻ�����ȥѭ���鲹���ͬ����ȫû�б�Ҫ
	 * @param view
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException
	 */
	private void refreshTblMain(EntityViewInfo view) throws BOSException, SQLException, EASBizException{
		tblMain.removeRows(false);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE); //ʵģʽ
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//ֻ����ѡ����

		EntityViewInfo viewInfo = view;
		viewInfo.getSelector().clear();
		viewInfo.setSelector(getSelectors());
		viewInfo.getSelector();
		ContractBillCollection billColls = ContractBillFactory.getRemoteInstance().getContractBillCollection(viewInfo);
		if (null == billColls || billColls.size() < 0 ) {
			return;
		}
		
		Map addConMap = new HashMap();
		Set contractIds = new HashSet();
		for (int i = 0; i < billColls.size(); i++) {
			ContractBillInfo billInfo = billColls.get(i);
			String contractBillId = billInfo.getId().toString();
			//����ͬ����
			String mainConNumber = billInfo.getMainContractNumber();
			List addConList = null;
			if(StringUtils.isNotBlank(mainConNumber) ){
				addConList = (List)addConMap.get(mainConNumber);
				if(addConList == null){
					addConList = new ArrayList();
					addConMap.put(mainConNumber, addConList);
				}
				addConList.add(billInfo);
			}
			
			
			contractIds.add(contractBillId);
		}
		
		//������¼�Ѿ������еĺ�ͬID����ֹ�ظ����벹���ͬ
		Set hasInsertCon = new HashSet();
		/****���첹���ͬ****/
		for (int i = 0; i < billColls.size(); i++) { //���¼��غ�ͬ�б���ʾ����ͬ�벹���ͬ�Ĺ�ϵ
			ContractBillInfo billInfo = billColls.get(i);
			String contractBillId = billInfo.getId().toString();
			hasInsertCon.add(contractBillId);
			if (null != billInfo.getContractPropert() && ! billInfo.getContractPropert().toString().equals("�����ͬ")) {
				//
				String mainConNumber = billInfo.getNumber();
				List addConList = (List)addConMap.get(mainConNumber);
				IRow row = tblMain.addRow();
				int level = row.getTreeLevel();
				int dep = tblMain.getTreeColumn().getDepth();
				if (dep < level + 1) {
					tblMain.getTreeColumn().setDepth(level + 1);
				}
				row.setUserObject(billInfo);
				fillSupplyRow(row,billInfo);
				//���������ͬ
				if(addConList != null && addConList.size() > 0){
					for(int j = 0; j < addConList.size(); j ++){
						ContractBillInfo supplyBillInfo = (ContractBillInfo)addConList.get(j);
						IRow newRow = tblMain.addRow();
						newRow.setUserObject(supplyBillInfo);
						newRow.setTreeLevel(level + 1);
						fillSupplyRow(newRow, supplyBillInfo);
						if (null != newRow.getCell("isLonelyCal")) {
							newRow.getCell("isLonelyCal").setValue(Boolean.valueOf(isLonelyCal(getContractBillEntryCols(supplyBillInfo))));
						}
						hasInsertCon.add(supplyBillInfo.getId().toString());
					}
				}
				/*if (null != supplySet && supplySet.size() > 0) { //����в����ͬ
					EntityViewInfo viewInfo2 = new EntityViewInfo();
					viewInfo2.getSelector().clear();
					viewInfo2.setSelector(getSelectors());
					FilterInfo filter = new FilterInfo(); 
					filter.getFilterItems().add(new FilterItemInfo("id",supplySet,CompareType.INCLUDE));
					
					if (isHasFilter("respDept.id", view.getFilter())) {
						//����˹������β������� add by jian_cao
						filter.getFilterItems().add(
								new FilterItemInfo("respDept.id", billInfo.getRespDept().getId().toString(), CompareType.EQUALS));
					}
					if (isHasFilter("contractType.id", view.getFilter())) {
						//����˺�ͬ���͹��� add by jian_cao BT718525
						filter.getFilterItems().add(
								new FilterItemInfo("contractType.id", billInfo.getContractType().getId().toString(), CompareType.EQUALS));
					}
					if (isHasFilter("partB.id", view.getFilter())) {
						//��Ӧ�̣��ҷ������� add by jian_cao BT718524
						filter.getFilterItems().add(new FilterItemInfo("partB.id", billInfo.getPartB().getId(), CompareType.EQUALS));
					}
					
					
					viewInfo2.setFilter(filter);
					ContractBillCollection supCols = ContractBillFactory.getRemoteInstance().getContractBillCollection(viewInfo2);
					IRow newRow = null; //�����ͬ��
					ContractBillInfo supplyBillInfo = null;
					for (int j = 0; j < supCols.size(); j++) {
						supplyBillInfo = supCols.get(j);
						newRow = tblMain.addRow();
						newRow.setUserObject(supplyBillInfo);
						newRow.setTreeLevel(level + 1);
						fillSupplyRow(newRow, supplyBillInfo);
						if (null != newRow.getCell("isLonelyCal")) {
							newRow.getCell("isLonelyCal").setValue(Boolean.valueOf(isLonelyCal(getContractBillEntryCols(supplyBillInfo))));
						}
					}
				}*/
			}else { //����ǲ����ͬ
				if (isAddSupplyRow(contractIds,billInfo)) {
					IRow newRow = tblMain.addRow();
					newRow.setUserObject(billInfo);
					fillSupplyRow(newRow, billInfo);
					if (null != newRow.getCell("isLonelyCal")) {
						newRow.getCell("isLonelyCal").setValue(Boolean.valueOf(isLonelyCal(getContractBillEntryCols(billInfo))));
					}
				}
			}
			
		}
		
		Map retValue = ContractBillFactory.getRemoteInstance().getOtherInfo(contractIds);
    	contentMap = (Map)retValue.get("contentMap");
    	attachMap = (Map) retValue.get("attachMap");
    	auditMap = (Map) retValue.get("auditMap");

		addTableListener();
	}


	/**
	 * ����������ͬ��ѯFilter�Ƿ����ָ����Filter
	 * @param name
	 * @param filterInfo
	 * @return
	 * @Author��jian_cao
	 * @CreateTime��2012-10-26
	 */
	private boolean isHasFilter(String name, FilterInfo filterInfo) {
		if (filterInfo == null)
			return false;
		FilterItemCollection filterItems = filterInfo.getFilterItems();
		if (filterItems != null && filterItems.size() > 0) {
			for (int i = 0, size = filterItems.size(); i < size; i++) {
				if (name.equals(filterItems.get(i).getPropertyName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * description		�����Ƿ񵥶����Ӳ����ͬ�У�true��Ҫ����
	 * @author			����<p>	
	 * @createDate		2011-8-25<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private boolean isAddSupplyRow(Set idsSet,ContractBillInfo billInfo){
		boolean isAddSupplyRow = false;
		ContractBillEntryCollection cols = getContractBillEntryCols(billInfo);
		if (null != cols && cols.size() > 0) {
			ContractBillEntryInfo entryInfo = null;
			for (int i = 0; i < cols.size(); i++) {
				entryInfo = cols.get(i);
				if (null != entryInfo.getDetail() && entryInfo.getDetail().toString().equals("��Ӧ����ͬ����")) {
					String content = entryInfo.getContent();
					if (! idsSet.contains(content)) {
						isAddSupplyRow = true;
					}
				}
			}
		}
		return isAddSupplyRow;
	}

	/**
	 * description		��䲹���ͬ������
	 * @author			����<p>	
	 * @createDate		2011-8-18<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private void fillSupplyRow(IRow row ,ContractBillInfo info){
		//��ͬID
		if (null != row.getCell("id") && null != info.getId()) {
			row.getCell("id").setValue(info.getId().toString());
		}
		
		//����ʱ��
		if (null != row.getCell("bookedDate") && null != info.getBookedDate()) {
			row.getCell("bookedDate").setValue(info.getBookedDate());
		}
		//�ڼ�
		if (null != row.getCell("period") && null != info.getPeriod()) {
			row.getCell("period").setValue(BigDecimal.valueOf(info.getPeriod().getNumber()));
		}

		//��ǰ��Ŀ
		if (null != row.getCell("project") && null != info.getCurProject() && null != info.getCurProject().getName()) {
			row.getCell("project").setValue(info.getCurProject().getName());
		}

		//��ͬ����
		if (null != row.getCell("contractType.name") && null != info.getContractType()) {
			row.getCell("contractType.name").setValue(info.getContractType().getName());
		}

		//��ͬ����
		if (null != row.getCell("number") && null != info.getNumber()) {
			row.getCell("number").setValue(info.getNumber());
		}

		//��ͬ����
		if (null != row.getCell("contractName") && null != info.getName()) {
			row.getCell("contractName").setValue(info.getName());
		}
		
		//��ͬ�ۼƸ���
		if (null != row.getCell("conPaid") && null != info.getPrjPriceInConPaid()) {
			row.getCell("conPaid").setValue(info.getPrjPriceInConPaid());
		}

		//״̬
		if (null != row.getCell("state") && null != info.getState()) {
			row.getCell("state").setValue(info.getState());
		}
		
		//�ѽ���
		if (null != row.getCell("hasSettle")) {
			row.getCell("hasSettle").setValue(Boolean.valueOf(info.isHasSettled()));
		}

		//��ͬ����
		if (null != row.getCell("contractPropert") && null != info.getContractPropert()) {
			row.getCell("contractPropert").setValue(info.getContractPropert());
		}

		//����ͬ����
		if (null != row.getCell("mainContractNumber")) {
			if (null != info.getMainContractNumber()) {
				row.getCell("mainContractNumber").setValue(info.getMainContractNumber());
			}else {
				row.getCell("mainContractNumber").setValue(info.getNumber());
			}
			
		}
		
		//�ұ�
		if (null != row.getCell("currency") && null != info.getCurrency() && null != info.getCurrency().getName()) {
			row.getCell("currency").setValue(info.getCurrency().getName());
		}

		//ԭ�ҽ��
		if (null != row.getCell("originalAmount") && null != info.getOriginalAmount()) {
			row.getCell("originalAmount").setValue(info.getOriginalAmount());
		}

		//˰��
		if (null != row.getCell("exRate") && null != info.getExRate()) {
			row.getCell("exRate").setValue(info.getExRate());
		}

		//���ҽ��
		if (null != row.getCell("amount") && null != info.getAmount()) {
			row.getCell("amount").setValue(info.getAmount());
		}

		//�ҷ�
		if (null != row.getCell("partB.name") && null != info.getPartB() && null != info.getPartB().getName()) {
			row.getCell("partB.name").setValue(info.getPartB().getName());
		}

		//�γɷ�ʽ
		if (null != row.getCell("contractSource") && null != info.getContractSourceId()) {
			row.getCell("contractSource").setValue(info.getContractSourceId().getName());
		}

		//ǩԼ����
		if (null != row.getCell("signDate") && null != info.getSignDate()) {
			row.getCell("signDate").setValue(info.getSignDate());
		}

		//���β���
		if (null != row.getCell("respDept") && null != info.getRespDept() && null != info.getRespDept().getName()) {
			row.getCell("respDept").setValue(info.getRespDept().getName());
		}

		//ǩԼ�׷�
		if (null != row.getCell("landDeveloper.name") && null != info.getLandDeveloper() && null != info.getLandDeveloper().getName()) {
			row.getCell("landDeveloper.name").setValue(info.getLandDeveloper().getName());
		}

		//����
		if (null != row.getCell("partC.name") && null != info.getPartC() && null != info.getPartC().getName()) {
			row.getCell("partC.name").setValue(info.getPartC().getName());
		}

		//�������
		if (null != row.getCell("costProperty") && null != info.getCostProperty()) {
			row.getCell("costProperty").setValue(info.getCostProperty());
		}

		//�鵵
		if (null != row.getCell("isArchived")) {
			row.getCell("isArchived").setValue(Boolean.valueOf(info.isIsArchived()));
		}

		if (null != row.getCell("currency.id") && null != info.getCurrency() && null != info.getCurrency().getId()) {
			row.getCell("currency.id").setValue(info.getCurrency().getId());
		}

		if (null != row.getCell("currency.precision") && null != info.getCurrency()) {
			row.getCell("currency.precision").setValue(BigDecimal.valueOf(info.getCurrency().getPrecision()));
		}
		
		if (info.getAuditor() != null) {
			row.getCell("wfAuditName").setValue(info.getAuditor().getName());
			row.getCell("wfAuditTime").setValue(info.getAuditTime());
		}

		if (info.getCreator()  != null ) {
			row.getCell("createTime").setValue(info.getCreateTime());
			row.getCell("creator.name").setValue(info.getCreator().getName());
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();		
        sic.add(new SelectorItemInfo("entrys.detail"));
		sic.add(new SelectorItemInfo("entrys.content"));
		return sic;
	}
	
	private void addTableListener(){
		boolean isMainOrder = false;
		SorterItemCollection sortItems = mainQuery.getSorter();
		if(sortItems!=null && sortItems.size()>0){
        	for(Iterator it=sortItems.iterator();it.hasNext();){
        		SorterItemInfo sortItem = (SorterItemInfo)it.next();
        		if(sortItem.getPropertyName().equals("mainContractNumber")){
        			isMainOrder = true;
        			break;
        		}
        	}
        }
		
		if(attachMap==null){
			attachMap=new HashMap();
		}
		if(auditMap==null){
			auditMap=new HashMap();
		}
		if(contentMap==null){
			contentMap=new HashMap();
		}

		for (int i = 0; i < this.tblMain.getRowCount(); i++) { //��丽�������ģ�������Ϣ
			IRow row = tblMain.getRow(i);
			String idkey = row.getCell("id").getValue().toString();
			if(contentMap.containsKey(idkey)){
				row.getCell("content").setValue(Boolean.TRUE);
			}
			else{
				row.getCell("content").setValue(Boolean.FALSE);
			}
			if(attachMap.containsKey(idkey)){
				row.getCell("attachment").setValue(Boolean.TRUE);
			}
			else{
				row.getCell("attachment").setValue(Boolean.FALSE);
			}
			if(auditMap.containsKey(idkey)){
				MultiApproveInfo info = (MultiApproveInfo)auditMap.get(idkey);
				//����ж༶��������˱���ͻᵼ����������˺��������ʱ�����ݲ���ȷ,BUG,by Cassiel_peng
					row.getCell("wfAuditName").setValue(info.getCreator().getName());
				//�����벻֪���Ƕ��֮ǰ��λ����д���ˣ����Ƶ�ʱ�丳�����Ƶ�����һ�У������Ƶ��¼�ûֵ���Ƶ���ȴ�����ڣ�����ѽ��by cassiel_peng
					row.getCell("wfAuditTime").setValue(info.getCreateTime());
			}
		}

	}

	/**
	 * @see com.kingdee.eas.framework.client.BillListUI#tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent)
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		
		boolean isEnabled = true;
		
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index != -1) {
			if (CONTRACT_PROPERT.equals(this.tblMain.getCell(index, "contractPropert").getValue().toString().trim())) {
				Object isLonelyCal = this.tblMain.getCell(index, "isLonelyCal").getValue();
				//������Ƕ�������
				if (isLonelyCal == Boolean.FALSE) {
					isEnabled = false;
				}
			}
		}
		this.actionAddPayRequest.setEnabled(isEnabled);
		this.actionAddChangeAudit.setEnabled(isEnabled);
		this.actionAddContractSettlement.setEnabled(isEnabled);
		
		super.tblMain_tableSelectChanged(e);
	}
	
	
}