/**
 * output package name
 */
package com.kingdee.eas.port.pm.contract.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextClientUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.port.pm.contract.ContractWithoutTextFactory;
import com.kingdee.eas.port.pm.contract.ContractWithoutTextInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * ����:���ı���ͬ�б����
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.1.3
 */
public class ContractWithoutTextListUI extends
AbstractContractWithoutTextListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractWithoutTextListUI.class);

	/**
	 * output class constructor
	 */
	public ContractWithoutTextListUI() throws Exception {
		super();
	}

	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractWithoutTextFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractWithoutTextInfo objectValue = new com.kingdee.eas.fdc.contract.ContractWithoutTextInfo();
		return objectValue;
	}
	/**
	 * 
	 * �����������ͬ����
	 * 
	 * @author:liupd ����ʱ�䣺2006-7-25
	 *               <p>
	 */
	protected void freezeTableColumn() {
		//FDCHelper.formatTableNumber(getMainTable(), "amount");
	
		// ��ͬ����
		int name_col_index = getMainTable().getColumn("billName")
				.getColumnIndex();
		getMainTable().getViewManager().setFreezeView(-1, name_col_index+1);
	}
	
	
	
	 /**
	 * 
	 * ����������Զ�̽ӿڣ��������ʵ�֣�
	 * @return
	 * @throws BOSException
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected ICoreBase getRemoteInterface() throws BOSException {
		return ContractWithoutTextFactory.getRemoteInstance();
	}
	
	/**
	 * 
	 * ���������ͨ�����������ʵ�֣����÷������˴���˱�־�ķ������ɣ�
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected void audit(List ids) throws Exception {
		ContractWithoutTextFactory.getRemoteInstance().audit(ids);
	}

	/**
	 * 
	 * ����������ˣ��������ʵ�֣����÷������˴���˱�־�ķ������ɣ�
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected void unAudit(List ids) throws Exception {
		ContractWithoutTextFactory.getRemoteInstance().unAudit(ids);
	}
	
	/**
     * 
     * ����������Ƿ��й�������
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     */
    protected void checkRef(String id) {
    	ContractWithoutTextClientUtils.checkRef(this, id);
    }
    
    protected void updateButtonStatus() {
    
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
    	FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
    	super.actionAddNew_actionPerformed(e);
    }
    
//	����ʱ�������ݻ���
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		��ȡ�û�ѡ��Ŀ�
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");

			super.actionAudit_actionPerformed(e);
	
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
	}
	
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }
    
    /**
     * ��������ť"�鿴���"��Action������
     * @author zhiyuan_tang 2010/08/04
     */
    public void actionViewPayment_actionPerformed(ActionEvent e)
    		throws Exception {
    	
    	//����Ƿ�ѡ����
    	super.checkSelected();
    	
    	//�ж��Ƿ��й����ĸ��
    	boolean hasPaymentBill = false;
    	String paymentID = null;
    	//ȡ��ѡ�еĺ�ͬID
    	String contractBillID = getSelectedKeyValue();
    	
    	//�ж���û�иú�ͬ��Ӧ�ĸ��
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("ContractBillId", contractBillID));
		view.setFilter(filter);
		view.getSelector().add("id");
		
		PaymentBillCollection col = com.kingdee.eas.fi.cas.PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
		if (col != null && col.size() > 0) {
			hasPaymentBill = true;
			
			//��ȡ���ID
			for (int i = 0; i < col.size(); i++) {
				paymentID = col.get(i).getId().toString();
			}
		} else {
			hasPaymentBill = false;
		}
    	
    	
    	if (hasPaymentBill) {
    		//�����ı���ͬ���ж�Ӧ���ɵĸ��ʱ���Բ鿴״̬�򿪸��
    		showPayment(paymentID);
    		
    	} else {
    		//�����ı���ͬ��û�ж�Ӧ���ɵĸ��ʱ����ʾ�û��������ı���ͬû�ж�Ӧ�ĸ��
    		FDCMsgBox.showWarning(EASResource.getString(
    						"com.kingdee.eas.fdc.contract.client.ContractResource",
    						"contractWithoutTextHasNOPayment"));
    		abort();
    	}
    }
    
    /**
     * ��������ָ������ı༭����
     * @param billID:���ID
     * @throws Exception
     * @author zhiyuan_tang 2010/08/03
     */
    private void showPayment(String billID) throws Exception {
		Map uiContext = new HashMap();
		uiContext.put(UIContext.OWNER, this);
		uiContext.put(UIContext.ID, billID);
		FilterInfo myFilter = new FilterInfo();
		myFilter.getFilterItems().add(new FilterItemInfo("id", billID));
		uiContext.put("MyFilter", myFilter); // ע�⣬�����billID��44λ����BOSUuid��������String
		// IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.MODEL) ;
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow window = uiFactory.create(
				"com.kingdee.eas.fdc.finance.client.PaymentBillEditUI",
				uiContext, null, OprtState.VIEW);
		window.show();
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		//Add by zhiyuan_tang 2010/08/03 
		//��Ӳ鿴�����ť,ToolBar��MenuBar�϶�Ҫ���,ͼ��Ϊ�²�ͼ��
		btnViewPayment.setVisible(true);
		btnViewPayment.setEnabled(true);
		btnViewPayment.setIcon(EASResource.getIcon("imgTbtn_downview"));
		menuItemViewPayment.setVisible(true);
		menuItemViewPayment.setEnabled(true);
		menuItemViewPayment.setIcon(EASResource.getIcon("imgTbtn_downview"));
		
		this.btnProgram.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		this.btnUnProgram.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		
		tblMain.getColumn("useDeptment").getStyleAttributes().setHided(true);
		
		FDCClientUtils.fmtFootNumber(tblMain, new String[]{"originalAmount","amount"});
		
		if(this.getUIContext().get("filter")!=null){
			this.toolBar.setVisible(false);
			this.pnlLeftTree.setVisible(false);
			kDSplitPane1.add(this.pnlLeftTree, "left");
			mainQuery=new EntityViewInfo();
			mainQuery.setFilter((FilterInfo) this.getUIContext().get("filter"));
		}
		this.actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
	}
	protected void treeSelectChange() throws Exception {
		if(this.getUIContext().get("filter")!=null){
			return;
		}
		super.treeSelectChange();
	}
	public void buildProjectTree() throws Exception {
		if(this.getUIContext().get("filter")!=null){
			return;
		}
		super.buildProjectTree();
	}
	/**
	 * RPC���죬�κ�һ���¼�ֻ��һ��RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
    	return true;
    }
	
	public boolean isPrepareActionSave() {
    	return true;
    }
		
	/** ��ӿ��ٶ�λ���ܣ���λ�ֶ��У����ݱ�ţ��������ơ���ͬ���͡��տλ��ǩԼ���ڣ�
	 * @author owen_wen 2010-09-06
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "billName","contractType", "receiveUnit.name", "signDate", };
	}
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		if(idList.size()>1){
			FDCMsgBox.showInfo("��ѡ��һ����¼���в�����");
			SysUtil.abort();
		}
		String selectedKeyValue = getSelectedKeyValue();
		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
			FDCMsgBox.showInfo("���ڹ����������У����Ժ�����!");
			SysUtil.abort();
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		sic.add("curProject.isWholeAgeStage");
		ContractWithoutTextInfo contractBillInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(selectedKeyValue), sic);
		boolean isRealToProgramming =FDCUtils.getDefaultFDCParamByKey(null,contractBillInfo.getOrgUnit().getId().toString(), FDCConstants.FDC_PARAM_OLDCONTRACT_PROGRAM);
		if(isRealToProgramming){
			if(!contractBillInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
				FDCMsgBox.showInfo("��ͬδ���������ܽ��к�Լ�滮������");
				SysUtil.abort();
			}
		}else{
			FDCMsgBox.showInfo("�������ͬ������Լ�滮����δ���ã����ܽ��к�Լ�滮������");
			SysUtil.abort();
		}
		ProgrammingContractInfo pc = (ProgrammingContractInfo) contractBillInfo.getProgrammingContract();
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("contractWithoutTextInfo", contractBillInfo);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,OprtState.EDIT);
		uiWindow.show();
		if (contractBillInfo.getProgrammingContract() != null) {
			Object object = uiWindow.getUIObject().getUIContext().get("cancel");
			if (object != null) {
				return;
			} else {
				String param = null;
				SelectorItemCollection sict = new SelectorItemCollection();
				sict.add("*");
				sict.add("fullOrgUnit.*");
				ObjectUuidPK pk = new ObjectUuidPK(contractBillInfo.getOrgUnit().getId());
				param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC228_ISSTRICTCONTROL", pk);
				if (!com.kingdee.util.StringUtils.isEmpty(param)) {
					if(pc!=null&&pc.getId().toString().equals(contractBillInfo.getProgrammingContract().getId().toString())){
						return;
					}
					int i = Integer.parseInt(param);
					switch (i) {
					case 0:
						// �ϸ����ʱ
						if (contractBillInfo.getProgrammingContract() != null) {
							if (contractBillInfo.getAmount().compareTo(FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance()))) > 0) {
								FDCMsgBox.showWarning(this, "��ͬǩԼ���������ĺ�Լ������");
								SysUtil.abort();
							}
						} else {
							FDCMsgBox.showWarning(this, "δ������ܺ�Լ��");
							SysUtil.abort();
						}
						break;
					case 1:
						// ��ʾ����ʱ
						if (contractBillInfo.getProgrammingContract() != null) {
							if (contractBillInfo.getAmount().compareTo(
									FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance())) > 0) {
								if (FDCMsgBox.showConfirm2(this, "��ͬǩԼ���������ĺ�Լ��������ȷ���Ƿ������") == FDCMsgBox.CANCEL) {
									SysUtil.abort();
								}
							}
						} else {
							FDCMsgBox.showWarning(this, "δ������ܺ�Լ��");
							SysUtil.abort();
						}
						break;
					case 2:
						// ������ʱ
						if (contractBillInfo.getProgrammingContract() != null) {
							if (contractBillInfo.getAmount().compareTo(
									FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance())) > 0) {
								if (FDCMsgBox.showConfirm2(this, "��ͬǩԼ���������ĺ�Լ��������ȷ���Ƿ������") == FDCMsgBox.CANCEL) {
									SysUtil.abort();
								}
							}
						}
						break;
					}
					ContractWithoutTextFactory.getRemoteInstance().synReUpdateProgramming(contractBillInfo.getId().toString(), pc);
					SelectorItemCollection sicz = new SelectorItemCollection();
					sicz.add("programmingContract");
					ContractWithoutTextFactory.getRemoteInstance().updatePartial(contractBillInfo, sicz);
					FDCMsgBox.showInfo("������ܺ�Լ�ɹ���");
					this.refresh(null);
				} 
			}
		}
	}
	public void actionUnProgram_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		if(idList.size()>1){
			FDCMsgBox.showInfo("��ѡ��һ����¼���в�����");
			SysUtil.abort();
		}
		String selectedKeyValue = getSelectedKeyValue();
		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
			FDCMsgBox.showWarning(this,"���ڹ����������У����Ժ����ԣ�");
			SysUtil.abort();
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("programmingContract.*");
		ContractWithoutTextInfo contractBillInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(selectedKeyValue), sic);
		if(contractBillInfo.getProgrammingContract()==null){
			FDCMsgBox.showWarning(this,"��ѡ���������Լ�滮��¼����ȡ��������");
			SysUtil.abort();
		}
		ContractWithoutTextFactory.getRemoteInstance().synReUpdateProgramming(contractBillInfo.getId().toString(), contractBillInfo.getProgrammingContract());
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("programmingContract");
		contractBillInfo.setProgrammingContract(null);
		ContractWithoutTextFactory.getRemoteInstance().updatePartial(contractBillInfo, sel);
		FDCMsgBox.showInfo(this,"�����ɹ���");
		this.refresh(null);
	}
	protected boolean isFootVisible(){
		return true;
	}
	protected void checkBeforeRemove() throws Exception {
		checkSelected();

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		String[] states = getBillStateForEditOrRemove();

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if (billState.equals(states[i])) {
					pass = true;
				}
			}
			if (!pass) {
				MsgBox.showWarning(this, ContractClientUtils
						.getRes("cantRemove"));
				SysUtil.abort();
			}
		}
	}
}