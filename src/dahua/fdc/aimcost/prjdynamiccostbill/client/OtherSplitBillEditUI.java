/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitNewEntryCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitNewEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptProdUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptProjUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.programming.client.CostAccountPromptBox;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.client.PaymentSplitListUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillEditUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillListUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadSplitListUI;
import com.kingdee.eas.fdc.finance.utils.TableHelper;
import com.kingdee.eas.fm.ecore.app.bean.commercialdraft.ContractInformation;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;

/**
 * output class name
 */
public class OtherSplitBillEditUI extends AbstractOtherSplitBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(OtherSplitBillEditUI.class);
    private int groupIndex=0;
    private IUIWindow acctUI=null;
    private Map parentMap = new HashMap();
    private List oldCostAccountLongNumber = new ArrayList();
	private HashMap entrysMap = new HashMap(); 
	private OtherSplitNewEntryCollection entrys = null;
	 protected FDCCostSplitForSL fdcCostSplit=new FDCCostSplitForSL(null);
    /**
     * output class constructor
     */
    public OtherSplitBillEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        setDisplay();
        TableHelper.getFootRow(kdtEntrys, new String[]{"rate", "amount"});
    }

    public void onLoad() throws Exception {
    	this.contstate.setEnabled(false);
    	this.contcurProject.setEnabled(false);
    	this.contauditTime.setEnabled(false);
    	this.contBizDate.setEnabled(false);
    	this.contDescription.setVisible(false);
    	this.contcontractNumber.setVisible(false);
    	
    	this.txtamount.setRequired(true);
    	this.prmtcontract.setRequired(true);
    	this.btnRemove.setVisible(false);
    	this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
    	this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
    	super.onLoad();
    	if(this.editData.getBizDate()!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.editData.getBizDate());
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			
			this.spYear.setValue(year);
			this.spMonth.setValue(month);
		}
    	this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
    	chkMenuItemSubmitAndAddNew.setSelected(false);
    	menuSubmitOption.setEnabled(false);
    	CurProjectInfo project = this.editData.getCurProject();
    	if(project != null) {
    		EntityViewInfo evi = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		evi.setFilter(filter);
    		filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
    		this.prmtcontract.setEntityViewInfo(evi);
    	}
    	
    	CostAccountPromptBox selector = new CostAccountPromptBox(this);
    	KDBizPromptBox prmtAccount = (KDBizPromptBox) this.kdtEntrys.getColumn("costAccount").getEditor().getComponent();
    	prmtAccount.setDisplayFormat("$name$");
    	prmtAccount.setEditFormat("$longNumber$");
    	prmtAccount.setCommitFormat("$longNumber$");
    	prmtAccount.setSelector(selector);
    	
    	EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (project != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error", CompareType.EQUALS));
		}
		entityView.setFilter(filter);
		prmtAccount.setEntityViewInfo(entityView);
		
		KDWorkButton addNewLineButton = this.kdtEntrys_detailPanel.getAddNewLineButton();
		KDWorkButton insertLineButton = this.kdtEntrys_detailPanel.getInsertLineButton();
		KDWorkButton removeLineButton = this.kdtEntrys_detailPanel.getRemoveLinesButton();
		addNewLineButton.removeActionListener(addNewLineButton.getActionListeners()[0]);
		insertLineButton.removeActionListener(insertLineButton.getActionListeners()[0]);
		removeLineButton.removeActionListener(removeLineButton.getActionListeners()[0]);
		
		addNewLineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAddLine(e);
			}
		});
		insertLineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAddLine(e);
			}
		});
		removeLineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = kdtEntrys.getSelectManager().getActiveRowIndex();
				if(index == -1) {
					MsgBox.showWarning("û��ѡ�з�¼,�޷�ɾ��!");
					SysUtil.abort();
				}
				kdtEntrys.removeRow(index);
				TableHelper.getFootRow(kdtEntrys, new String[]{"rate", "amount"});
			}
		});
		kdtEntrys.getStyleAttributes().setHided(true);
		kdtEntrys_detailPanel.setVisible(false);
    }
    /**
     * У��
     */
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	super.verifyInput(actionevent);
    	if(txtNumber.getText() == null || txtNumber.getText().trim().equals("")) {
    		MsgBox.showWarning("���ݱ��Ϊ��!���޸�!");
    		SysUtil.abort();
    	}
    	if(txtamount.getBigDecimalValue() == null) {
    		MsgBox.showWarning("�������Ϊ��!���޸�!");
    		SysUtil.abort();
    	}
    	if(txtcomment.getText() == null || txtcomment.getText().trim().equals("")) {
    		MsgBox.showWarning("��עΪ��!���޸�!");
    		SysUtil.abort();
    	}
    	if(prmtcontract.getValue() == null) {
    		MsgBox.showWarning("��ͬΪ��!���޸�!");
    		SysUtil.abort();
    	}
    	if(prmtcurProject.getValue() == null) {
    		MsgBox.showWarning("������ĿΪ��!���޸�!");
    		SysUtil.abort();
    	}
    	if(kdtSplitEntry.getRowCount() == 0) {
    		MsgBox.showWarning("��¼����Ϊ��!���޸�!");
    		SysUtil.abort();
    	}
    	if(UIRuleUtil.sum(kdtSplitEntry, "splitScale")!=100){
    		MsgBox.showWarning("��ֱ���������100������ִ�д˲�����");
    		SysUtil.abort();
    	}
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
     * ���
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(editData.getId() == null) {
    		MsgBox.showWarning("���ȱ��浥��");
    		SysUtil.abort();
    	}
    	if(!editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
    		MsgBox.showWarning("���ύ״̬�����޷����");
    		SysUtil.abort();
    	}
    	super.actionAudit_actionPerformed(e);
    	syncDataFromDB();
    }
    /**
     * �����
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(editData.getId() == null) {
    		MsgBox.showWarning("���ȱ��浥��");
    		SysUtil.abort();
    	}
    	if(!editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
    		MsgBox.showWarning("�����״̬�����޷������");
    		SysUtil.abort();
    	}
    	super.actionUnAudit_actionPerformed(e);
    	syncDataFromDB();
    }
    protected void initWorkButton() {
    	super.initWorkButton();
		this.actionAcctSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		this.actionSplitProd.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_citetree"));
		this.actionRemoveLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		
		KDWorkButton btnAcctSelect = (KDWorkButton)this.kDContainer1.add(actionAcctSelect);
		btnAcctSelect.setText("�ɱ���Ŀ");
		KDWorkButton btnSplitProd = (KDWorkButton)this.kDContainer1.add(actionSplitProd);
		btnSplitProd.setText("��Ʒ���");
		KDWorkButton btnRemoveLine = (KDWorkButton)this.kDContainer1.add(actionRemoveLine);
		btnRemoveLine.setText("ɾ����¼");
    }
    /**
     * �ɱ���Ŀ���
     */
    public void actionAcctSelect_actionPerformed(ActionEvent e)
    		throws Exception {
    	CostAccountCollection accts=null;
    	if(editData.getContract() == null) {
    		MsgBox.showWarning("��ͬΪ��!���޸�!");
    		SysUtil.abort();
    	}
    	if(editData.getCurProject() == null) {
    		MsgBox.showWarning("������ĿΪ��!���޸�!");
    		SysUtil.abort();
    	}
    	if(txtamount.getBigDecimalValue() == null) {
    		MsgBox.showWarning("�������Ϊ��!���޸�!");
    		SysUtil.abort();
    	}
		if(acctUI==null){
			Map map = getUIContext();
			//��UIContext�л�õ�ǰID
			String costBillId = editData.getContract().getId().toString();
			String contractBillId = editData.getContract().getId().toString();
			CurProjectInfo curProject = editData.getCurProject();
			/* modified by zhaoqin for R130927-0088 on 2013/12/23 end */			
			
			//��ñ���ͬ������ڹ�����Ϣ������UIContext��������ѡ���Ŀ
			UIContext uiContext = new UIContext(this); 
			uiContext.put("curProject",curProject);
			if (contractBillId != null) {
				uiContext.put("contractBillId", contractBillId);
			}
			/************* ���Ϻ�ͬ���²�� *************/
//			uiContext.put("txtCostBillNumber", txtCostBillNumber.getText());
			/************* ���Ϻ�ͬ���²�� *************/
			// ����ģʽ���������븶���ֲ���ʾ�ɲ��ѡ��
//			if (isFinacial()) {
//				uiContext.put("isFinacial", Boolean.TRUE);
//			}
//			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)map.get("node");
			//���Ϊ��������������ֱ�Ӵ�CURRENT.VO�л��CurProjectInfo
			if(costBillId==null){
				if(map.get("CURRENT.VO") instanceof ConChangeSplitInfo){
					ConChangeSplitInfo info = (ConChangeSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				if(map.get("CURRENT.VO") instanceof ContractCostSplitInfo){
					ContractCostSplitInfo info = (ContractCostSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				if(map.get("CURRENT.VO") instanceof PaymentSplitInfo){
					PaymentSplitInfo info = (PaymentSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				
			}
//			//��uiContext�л����FDCSplitListUI�б���Ľڵ���Ϣ
//			if(node!=null && (node.getUserObject() instanceof OrgStructureInfo)){
//				OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
//				uiContext.put("curProject",info);
//			}
//			if(node!=null && node.getUserObject() instanceof CurProjectInfo){
//				CurProjectInfo info = (CurProjectInfo)node.getUserObject();
//				uiContext.put("curProject",info);
//			}
			
			
//			uiContext.put("curProject",editData.getCurProject());
//			uiContext.put("isMeasureSplit", isMeasureContract()?Boolean.TRUE:null);
			acctUI=UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
					com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI.class.getName(),	uiContext, null , null);       
		}else{
			((CostSplitAcctUI) acctUI.getUIObject()).actionNoneSelect_actionPerformed(null);
		}
		acctUI.show();
		IUIWindow uiWin=acctUI;
		
		if (((CostSplitAcctUI) uiWin.getUIObject()).isOk()) {	
			accts=((CostSplitAcctUI) uiWin.getUIObject()).getData();
			parentMap = ((CostSplitAcctUI) uiWin.getUIObject()).getParentIdMap();
		}else{
			return;
		}
		

		CostAccountInfo acct=null;
		
		OtherSplitNewEntryInfo entry=null;
		IRow row=null;
		boolean isExist=false;
		// �ڲ���һ�廯����ģʽ�����˲��� ɾ������ϸ��Ŀ
//		removeParentCostAccount(accts);
		
		for(Iterator iter=accts.iterator(); iter.hasNext();){
			acct = (CostAccountInfo)iter.next();
			
			//�жϿ�Ŀ�Ƿ����
			isExist=false;
			for(int i=0; i<kdtSplitEntry.getRowCount(); i++){			
				entry=(OtherSplitNewEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
								
				//����ѡ����������ַ������Ѵ��ڵĿ�Ŀ		jelon 12/6/06
				//if(entry.getCostAccount().getId().equals(acct.getId())){
				if(entry.getLevel()==0 && entry.getCostAccount().getId().equals(acct.getId())){
					isExist=true;
					break;
				}
			}
			if(!isExist){
				
				//entry=new FDCSplitBillEntryInfo();
				entry=(OtherSplitNewEntryInfo)createNewDetailData(kdtSplitEntry);
				entry.setCostAccount(acct);  
				entry.setLevel(0);
				entry.setIsLeaf(true);		//Jelon 	Dec 11, 2006
				entry.setAmount(FDCHelper.ZERO);
				
				//������	jelon 12/27/2006
				groupIndex++;
				entry.setIndex(groupIndex);
				
				row=addEntry(entry);
				setDisplay(row.getRowIndex());

			}				
		}
		setMenuSplitState();
		// ��ֱ�ӽ��ı���ɫ���óɰ�ɫ
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName())
				|| className.equals(PaymentSplitListUI.class.getName())) {
			for (int k = kdtSplitEntry.getRowCount() - 1; k > 0; k--) {
				if (kdtSplitEntry.getRow(k) != null && kdtSplitEntry.getRow(k).getCell("directAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directAmt").getStyleAttributes().setBackground(
						new Color(0xffffff));
				if (kdtSplitEntry.getRow(k) != null
						&& kdtSplitEntry.getRow(k).getCell("directPayedAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directPayedAmt").getStyleAttributes().setBackground(
						new Color(0xffffff));
			}
		} else if (className.equals(WorkLoadConfirmBillListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			for (int k = kdtSplitEntry.getRowCount() - 1; k > 0; k--) {
				if (kdtSplitEntry.getRow(k) != null && kdtSplitEntry.getRow(k).getCell("directAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directAmt").getStyleAttributes().setBackground(new Color(0xffffff));
			}
		}
//		setOneEntryAmt(txtadjustAmount.getBigDecimalValue());
    }
    public void setMenuSplitState() {
		// �µĳɱ���Ŀ���뼯��
		List newCostAccountLongNumber = new ArrayList();
		// �ж��Ƿ񹤳�����ֺ͸����ִ�
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName()) || className.equals(PaymentSplitListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillListUI.class.getName()) || className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			// �������γɱ������µĳɱ���Ŀ����
			String longNumber = null;
			PaymentSplitEntryInfo info = null;
			for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
				info = (PaymentSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
				// �жϵ�ǰ�Ƿ�ɱ���Ŀ
				if (info.getCostAccount() instanceof CostAccountInfo) {
					newCostAccountLongNumber.add(info.getCostAccount().getLongNumber());
				}
			}
			// �жϾɳɱ���Ŀ������³ɱ���Ŀ�����Ƿ�һ��
			if (!oldCostAccountLongNumber.containsAll(newCostAccountLongNumber)) {
				this.getUIContext().put("isCanEnable", Boolean.FALSE);
				PaymentSplitEntryInfo tmpInfo = null;
				// �����µı�������ж��Ƿ�ȫ��������ϸ��Ŀ
				for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
					tmpInfo = (PaymentSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
					// �жϵ�ǰ���Ƿ�ɱ���Ŀ
					if (tmpInfo.getCostAccount() instanceof CostAccountInfo) {
						// �ж��Ƿ�����ϸ�ɱ���Ŀ��������Ǿ����ð�ť�ɱ༭״̬����������ֺ͸�����
						if (!tmpInfo.getCostAccount().isIsLeaf()) {
							this.getUIContext().put("isCanEnable", Boolean.FALSE);
							return;
						} 
					}
				}
			}
		}
	}
    protected IRow addEntry(IObjectValue detailData)
    {
        IRow row = kdtSplitEntry.addRow();
        ((OtherSplitNewEntryInfo)detailData).setSeq(row.getRowIndex()+1);
        loadLineFields(kdtSplitEntry, row, detailData);
        afterAddLine(kdtSplitEntry, detailData);
        
        return row;
    }
    private Map initDirectMap=new HashMap();
    private void setDisplay(int rowIndex){
    	initDirectMap.clear();
    	setOneTreeDisplay(rowIndex);
    	initDirectAssign();
    	
    	setDisplay();
    }
    /**
	 * ���������÷�̯��׼��ȫ����
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    protected void setDisplay(){
    	OtherSplitNewEntryInfo entry=null;
    	IRow row=null;
    	initDirectMap.clear();
		for(int i=0; i<kdtSplitEntry.getRowCount(); i++){	
			row=kdtSplitEntry.getRow(i);
			entry = (OtherSplitNewEntryInfo)row.getUserObject();
			if(entry.getLevel()==0){
				setOneTreeDisplay(i);
				//�����ͬ��ֱ���ʱ�����ͷ�Ѳ��
				calcAmount(i);
			}
		}
		initDirectAssign();
    }
    protected void calcAmount(int rowIndex){
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		BigDecimal amount = FDCHelper.ZERO;
		
		OtherSplitNewEntryInfo entry = null;
		
		//�������ܽ��
		for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
			/*if (kdtSplitEntry.getRow(i).getCell(COLAMOUNT).getValue()!=null){
				amount = amount.add(new BigDecimal(kdtSplitEntry.getRow(i).getCell(COLAMOUNT).getValue().toString()));
			}*/
			entry = (OtherSplitNewEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
			
			if (entry.getLevel() == 0) {
				amount = entry.getAmount();
				if (amount != null) {
					amountTotal = amountTotal.add(amount);
				}
			}
		}
		amountTotal = amountTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
//		if (txtSplitedAmount.getBigDecimalValue() != null
//				&& amountTotal.compareTo(FDCHelper.toBigDecimal(txtSplitedAmount.getBigDecimalValue())
//						.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) {
//			try {
//				txtSplitedAmount_dataChanged(null);
//			} catch (Exception e) {
//				handUIExceptionAndAbort(e);
//			}
//		} else {
//			txtSplitedAmount.setValue(amountTotal);
//		}    	
    } 
    private void  initDirectAssign(){
    	if(initDirectMap==null||initDirectMap.size()==0){
    		return;
    	}
    	
    	Map projProdMap=new HashMap();
		//��Ʒ����		
		EntityViewInfo view = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",new HashSet(initDirectMap.values()),CompareType.INCLUDE));
    	//filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("IsAccObj", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("productType.*"); 		//ʹ�á�*���������б��е����ݺͷ�¼�е�����ƥ��
    	view.getSorter().add(new SorterItemInfo("productType.number"));
		try {   	    	    
			CurProjProductEntriesCollection c=CurProjProductEntriesFactory.getRemoteInstance().getCurProjProductEntriesCollection(view);
			for(int i=0;i<c.size();i++){
				String prjId=c.get(i).getCurProject().getId().toString();
				CurProjProductEntriesCollection temp=(CurProjProductEntriesCollection)projProdMap.get(prjId);
				if(temp==null){
					temp=new CurProjProductEntriesCollection();
				}
				temp.add(c.get(i));
				projProdMap.put(prjId, temp);
			}
		}catch(BOSException e){
			handUIExceptionAndAbort(e);
		}
    	for(Iterator iter=initDirectMap.keySet().iterator();iter.hasNext();	){
    		Integer idx=(Integer)iter.next();
    		String prjId=(String)initDirectMap.get(idx);
    		if(idx==null){
    			continue;
    		}
    		IRow row=kdtSplitEntry.getRow(idx.intValue());
    		CurProjProductEntriesCollection coll=(CurProjProductEntriesCollection)projProdMap.get(prjId);
			if(coll==null){
				coll=new CurProjProductEntriesCollection();
			}
			ProductTypeCollection collProd=new ProductTypeCollection();
			//����
			ProductTypeInfo prod=new ProductTypeInfo();
			prod.setName(null);
			//prod.setName("��");
	        collProd.insertObject(-1,prod);		
	        
	        //��ǰ��Ŀȫ����Ʒ
			for (Iterator iter2 = coll.iterator(); iter2.hasNext();)
			{
				prod = ((CurProjProductEntriesInfo)iter2.next()).getProductType();	        
				if(prod!=null){
					collProd.add(prod);
				}
	        }

			KDComboBox cbo = new KDComboBox();    	    	    	
	        cbo.addItems(collProd.toArray());			
	        row.getCell("product").setEditor(new KDTDefaultCellEditor(cbo));  
    	}
    }
    /**
     * ��Ʒ���
     */
    public void actionSplitProd_actionPerformed(ActionEvent e) throws Exception {
    	splitCost(CostSplitTypeEnum.PRODSPLIT);
    }
    private void splitCost(CostSplitTypeEnum costSplitType) throws Exception {

		//----------------------------------------------------------------------------------------
		//ѡ����

        if ((kdtSplitEntry.getSelectManager().size() == 0)
                || isTableColumnSelected(kdtSplitEntry))
        {
            FDCMsgBox.showInfo(this, "û��ѡ�з�¼���޷����ò�ַ�����");
            return;
        }
		
		
		int topIdx=-1;		
		int[] selectRows = KDTableUtil.getSelectedRows(kdtSplitEntry);        
        if(selectRows.length >0){
        	topIdx = selectRows[0];
        }
        if(!(topIdx>=0)){
        	return;
        }        	        
        

		//----------------------------------------------------------------------------------------
        //��ֶ���
        IRow topRow=kdtSplitEntry.getRow(topIdx);         
		//FDCSplitBillEntryInfo selectEntry=editData.getEntrys().get(selectIdx);
        OtherSplitNewEntryInfo topEntry=(OtherSplitNewEntryInfo)topRow.getUserObject();
        
        

		int topLevel=topEntry.getLevel();			
		BOSUuid topId=topEntry.getId();		
		CostAccountInfo topAcct=topEntry.getCostAccount();			
		if(topAcct==null){
			return;
		}
		String topAcctNo=topEntry.getCostAccount().getLongNumber();
		        
        //�������
		CostSplitTypeEnum splitType=topEntry.getSplitType();
		
		
		boolean isTrue=true;	
		
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){			//��Ʒ���	
			if(!isProdSplitEnabled(topEntry)){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷����ò�Ʒ��̯������");			
				FDCMsgBox.showWarning(this,"��ѡ��¼�����ϲ�Ʒ�������,��ѡ����ϸ��¼���в���");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.PROJSPLIT)){	//�Զ����	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT)){					
					isTrue=false;					
				}
			}
			
			if ((!parentMap.containsKey(topEntry.getCostAccount().getId().toString()) || topAcct.isIsLeaf()) && topAcct.getCurProject().isIsLeaf()) {
				isTrue=false;
			}
			
			if(topEntry.getLevel()!=0){
				isTrue=false;
			}
			
			if(!isTrue){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷������Զ���ַ�����");		
				FDCMsgBox.showWarning(this, "��ѡ��¼�������Զ��������,��ѡ��һ������ϸ��¼���в���");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){	//ĩ�����	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				//if(topEntry.getSplitType().equals(CostSplitType.PROJSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
					//isTrue=false;
										
					//����ǰ���Զ����ת����ĩ�����	jelon 12/6/06
					if(topEntry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) && topEntry.getLevel()==0){						
						if (!FDCMsgBox.isYes(FDCMsgBox
				                .showConfirm2(this,FDCSplitClientHelper.getRes("sure")))){
							return;
						}							
					}else{
						isTrue=false;
					}
				}
			}
			
			
			if(topEntry.getLevel()!=0){
				isTrue=false;
			}
			
			if(topAcct.isIsLeaf() && topAcct.getCurProject().isIsLeaf()){
				isTrue=false;
			}
			if ((!parentMap.containsKey(topEntry.getCostAccount().getId().toString()) || topAcct.isIsLeaf()) && topAcct.getCurProject().isIsLeaf()) {
				isTrue = false;
			}
			if(!isTrue){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷�����ĩ����ַ�����");		
				FDCMsgBox.showWarning(this, "��ѡ��¼������ĩ���������,��ѡ��һ������ϸ��¼���в���");
				return;
			}
		}
				
		//topEntry.setSplitType(costSplitType);
        
        int level=0;

		
		//----------------------------------------------------------------------------------------
		//׼������
        OtherSplitNewEntryCollection entrys=new OtherSplitNewEntryCollection();
		entrys.add(topEntry);
				
		OtherSplitNewEntryInfo entry=null;
		for(int i=topIdx+1; i<kdtSplitEntry.getRowCount(); i++){
			entry=(OtherSplitNewEntryInfo)kdtSplitEntry.getRow(i).getUserObject();		
			
			if(entry.getLevel()>topLevel){
				entrys.add(entry);
			}else{
				break;
			}
		}
				

		//----------------------------------------------------------------------------------------
		//�������UI
		
		FDCSplitBillEntryCollection arfterOldEntrys = new FDCSplitBillEntryCollection();
		for (int i = 0; i < entrys.size(); i++) {
			FDCSplitBillEntryInfo oldNewEntryInfo = new FDCSplitBillEntryInfo();
			oldNewEntryInfo.putAll(entrys.get(i));
			arfterOldEntrys.add(oldNewEntryInfo);
		}
		
		UIContext uiContext = new UIContext(this); 
		//uiContext.put("costSplit", editData.getEntrys());		
		uiContext.put("costSplit", arfterOldEntrys);			
		uiContext.put("splitType", costSplitType);		
		uiContext.put("entryClass", getSplitBillEntryClassName());		
		uiContext.put("parentMap", parentMap);
		String apptUiName;
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
			apptUiName=CostSplitApptProdUI.class.getName();
		}else{
			apptUiName=CostSplitApptProjUI.class.getName();
		}
		
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				apptUiName,	uiContext, null ,STATUS_ADDNEW );       
		uiWin.show();	
			
		if (!((CostSplitApptUI) uiWin.getUIObject()).isOk()) {
			return;
		}

		//����ֵ
		entrys=new OtherSplitNewEntryCollection();
		FDCSplitBillEntryCollection oldEntrys =(FDCSplitBillEntryCollection) ((CostSplitApptUI) uiWin.getUIObject()).getData() ;
		for (int i = 0; i < oldEntrys.size(); i++) {
			OtherSplitNewEntryInfo newInfo = new OtherSplitNewEntryInfo();
			newInfo.putAll(oldEntrys.get(i));
			entrys.add(newInfo);
		}
//		entrys =oldEntrys.c;

		//		for (int i = 0; i < entrys.size(); i++) {
		//			if (entrys.get(i).getLevel() > 1) {
		//				entrys.get(i).setLevel(1);
		//			}
		//		}

		//----------------------------------------------------------------------------------------
		//ɾ��ԭ���Ĳ��
		int index=0;
		for(int i=topIdx+1; i<kdtSplitEntry.getRowCount(); i++){
			entry=(OtherSplitNewEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()>topLevel){
				index=i;
			}else{
				break;
			}			
		}
		for(int i=index; i>topIdx ; i--){
			entry=(OtherSplitNewEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()==topLevel){
				break;
			}
			else
			{
				removeEntry(i);
			}
		}
				
				
		
		//----------------------------------------------------------------------------------------
		
		//�ɱ���Ŀ
		CostAccountInfo acct=null;
		acct=entrys.get(0).getCostAccount();
					
		//�������
		splitType=costSplitType;	//CostSplitType.BOTUPSPLIT;
				
		//��̯����
		ApportionTypeInfo apportionType;
		apportionType = entrys.get(0).getApportionType();  
		
		//���ӿ�Ŀ
		boolean isAddlAcct=entrys.get(0).isIsAddlAccount();
		
		topEntry.setSplitType(splitType);
		topEntry.setApportionType(apportionType);	
		topEntry.setIsLeaf(false);			
		topEntry.setProduct(null);
		
		topRow.getCell("standard").setValue(splitType.toString());
		topRow.getCell("product").setValue("");
		topRow.getCell("product").getStyleAttributes().setLocked(true);
		
		//���ԡ�begin
		if(apportionType!=null){
			topRow.getCell("apportionType.name").setValue(apportionType.getName());
		}
		topRow.getCell("splitType").setValue(splitType);
		//���ԡ�end
		
		IRow row;				
		
		//��Ʒ��֣�ɾ��ȫ�������
		if(entrys.size()==1){	
			topEntry.setIsLeaf(true);
			
			if(topEntry.getLevel()==0){
				topEntry.setSplitType(CostSplitTypeEnum.MANUALSPLIT);
				topEntry.setApportionType(null);
				
				topRow.getCell("splitType").setValue(CostSplitTypeEnum.MANUALSPLIT);
				//topRow.getCell("apportionType").setValue(new ApportionTypeInfo());
				

				//topRow.getCell("standard").setValue("");
				setDisplay(topIdx);
				
			}else{
				for(int i=topIdx-1; i>=0; i--){
					row=kdtSplitEntry.getRow(i);
					entry=(OtherSplitNewEntryInfo) row.getUserObject();
					
					if(entry.getLevel()==topEntry.getLevel()-1){
						topEntry.setSplitType(entry.getSplitType());
						topEntry.setApportionType(null);

						topRow.getCell("splitType").setValue(entry.getSplitType());
						//topRow.getCell("apportionType").setValue(new ApportionTypeInfo());
						
						setDisplay(i);
						
						break;
					}
				}
				
			}
			return;
		}
		//�����µĲ����
		int idxCurr=topIdx;
		
		for(int i=1; i<entrys.size(); i++){
			entry=entrys.get(i);				

			//������	jelon 12/27/2006
			entry.setIndex(topEntry.getIndex());
						
			//entry.setSplitType(splitType);
			if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
				//��Ŀ����а����Ĳ�Ʒ���
			}else{
				entry.setSplitType(splitType);
			}
			
			
			if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
				//��Ŀ����а����Ĳ�Ʒ���
				entry.setIsAddlAccount(isAddlAcct);
			}
									
			idxCurr++;
			row=insertEntry(idxCurr,entry);			
			
			row.getCell("costAccount.curProject.name").setValue(entry.getCostAccount().getCurProject().getName());
			row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());	
		}
		
		//----------------------------------------------------------------------------------------
		
		//���������	
		//calcApportionData(topIdx,costSplitType);	//ʹ���½ӿڡ�jelon 12/26/2006
		totApptValue(topIdx);

		//��̯�ɱ�
		//calcApportionAmount(topIdx,costSplitType);	//ʹ���½ӿڡ�jelon 12/26/2006
		apptAmount(topIdx);
			

		//������ʾ
		index=topIdx;
		
		//��Ʒ��֣��Ӳ�����ĸ��ڵ㿪ʼ����
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
			row=kdtSplitEntry.getRow(topIdx);
			entry=(OtherSplitNewEntryInfo)row.getUserObject();
			if(entry.getLevel()!=0){
				for(int i=topIdx-1; i>=0; i--){
					row=kdtSplitEntry.getRow(i);
					entry=(OtherSplitNewEntryInfo)row.getUserObject();
					if(entry.getLevel()==0){
						index=i;
						break;
					}
				}
			}
		}
		setDisplay(index);		
	}
    protected void kdtSplitEntry_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtSplitEntry_editStopped(e);
		final IRow row = kdtSplitEntry.getRow(e.getRowIndex());
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("amount")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount=FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				OtherSplitNewEntryInfo entry;
				entry = (OtherSplitNewEntryInfo)row.getUserObject();
				String key = getEntrysMapKey(entry);
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...������˵��
				if (entrysMap.get(key) != null) {//modified by ken_liu...������˵��
					// modified by zhaoqin on 2013/11/09 start, ¼����ʱ��NullPointException
					// entry = (FDCSplitBillEntryInfo) entrysMap.get(String.valueOf(entry.getSeq()));
					entry = (OtherSplitNewEntryInfo) entrysMap.get(key);
					// modified by zhaoqin on 2013/11/09 end
				}
				//amount=new BigDecimal(kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue().toString());
				Object cellVal=row.getCell("amount").getValue();
				if(cellVal!=null){
					amount=new BigDecimal(cellVal.toString());
				}
				entry.setAmount(amount);
				//��ֱ���
				if(entry.getLevel()==0){
					if(FDCHelper.toBigDecimal(txtamount.getBigDecimalValue()).compareTo(FDCHelper.ZERO)!=0){
						splitScale=FDCHelper.divide(FDCHelper.multiply(amount, FDCHelper.ONE_HUNDRED), txtamount.getBigDecimalValue(),10,BigDecimal.ROUND_HALF_UP);
					}
					entry.setSplitScale(splitScale);
					row.getCell("splitScale").setValue(splitScale);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				//��̯
				/*CostSplitType splitType=entry.getSplitType();
				calcApportionAmount(e.getRowIndex(),splitType);*/				
				apptAmount(e.getRowIndex());	
				
				
				//����
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}else if(entry.isIsLeaf() && isAddlAcctLeaf(entry)){
				}
			}
		}
		
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("splitScale")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount = FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				OtherSplitNewEntryInfo entry;
				entry = (OtherSplitNewEntryInfo)row.getUserObject();
				
				// modified by zhaoqin on 2013/11/09, Ӧ����ͳһ�ķ���
				// String key = entry.getCostAccount().getId().toString() + String.valueOf(entry.getSeq());
				String key = getEntrysMapKey(entry);
								
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...������˵��
				if (entrysMap.get(key) != null) {//modified by ken_liu...������˵��
					entry = (OtherSplitNewEntryInfo) entrysMap.get(key);
				}
				Object cellVal=row.getCell("splitScale").getValue();
				if(cellVal!=null){
					splitScale=FDCHelper.toBigDecimal(cellVal);
				}
				if(entry.getLevel()==0){
					entry.setSplitScale(splitScale);
					amount = FDCHelper.divide(FDCHelper.multiply(txtamount.getBigDecimalValue(), splitScale),FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP);
					entry.setAmount(amount);
					row.getCell("amount").setValue(amount);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				
				apptAmount(e.getRowIndex());				
				//����
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}
			}
		}
		
		//���ӿ�Ŀ����
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("amount")){
			BigDecimal value=UIRuleUtil.getBigDecimal(e.getValue());
			BigDecimal oldValue=e.getOldValue()==null?FDCHelper.ZERO:UIRuleUtil.getBigDecimal(e.getOldValue());
			BigDecimal changeAmt=value.subtract(oldValue);
			if (changeAmt.compareTo(FDCHelper.ZERO)!=0){
				OtherSplitNewEntryInfo entry=(OtherSplitNewEntryInfo)row.getUserObject();
				if(entry.isIsLeaf()&&entry.isIsAddlAccount()){
					totAddlAcct(entry.getCostAccount().getCurProject(), entry.getCostAccount(), changeAmt, e.getRowIndex());
					entry.setApportionValue(value);
					row.getCell("apportionValue").setValue(value);
				}
			}
		}
		//���Ӳ�Ʒ
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("product")){
			OtherSplitNewEntryInfo entry = (OtherSplitNewEntryInfo)row.getUserObject();
			Object product = row.getCell("product").getValue();
			//if(product!=null&&product instanceof ProductTypeInfo && ((ProductTypeInfo)product).getId()!=null ){
			if(product!=null&& product.toString()!=null ){
				entry.setProduct((ProductTypeInfo)product);
			}else{
				entry.setProduct(null);
			}
		}
		//������������ĩ����ֵ���ɱ���Ŀʱ����������¼�룬���½�����
		if(editData.getBoolean("isMeasureSplit")){
			handleMeasureCalc(e, row);
		}
	}
    protected void handleMeasureCalc(KDTEditEvent e, final IRow row) {
		//���ۻ���
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("workLoad")
			||e.getColIndex()==kdtSplitEntry.getColumnIndex("price")){
			FDCSplitBillEntryInfo entry= (FDCSplitBillEntryInfo)row.getUserObject();
			BigDecimal oldAmt=entry.getAmount();
			BigDecimal amount = FDCHelper.multiply(row.getCell("workLoad").getValue(), row.getCell("price").getValue());
			row.getCell("amount").setValue(amount);
			entry.setWorkLoad((BigDecimal)row.getCell("workLoad").getValue());
			entry.setPrice((BigDecimal)row.getCell("price").getValue());
			entry.setAmount(amount);
			try{
				kdtSplitEntry_editStopped(new KDTEditEvent(e.getSource(), oldAmt, amount, 
					row.getRowIndex(), row.getCell("amount").getColumnIndex(),false,1));
			}catch (Exception e1) {
				logger.error(e1.getMessage(),e1);
				handUIExceptionAndAbort(e1);
			}
			calcAmount(0);
		}

		setMeasureCtrl(row);
	}
    protected void totAddlAcct(CurProjectInfo prj,CostAccountInfo acct,BigDecimal amount,int end) {
		IRow row=null;
    	CurProjectInfo curPrj=null;
		CostAccountInfo curAcct=null;
		BigDecimal sum=null; 
    	for (int i = end-1; i >=0 ; i--) {
			row = getDetailTable().getRow(i);
			OtherSplitNewEntryInfo entry=(OtherSplitNewEntryInfo)(row.getUserObject());
			if(entry.getLevel()==0){
				break;
			}
			if(!entry.isIsAddlAccount()){
				continue;
			}
			
			curAcct=entry.getCostAccount();
			curPrj=entry.getCostAccount().getCurProject();
			//�����ϼ�������Ŀ����ͬ��Ŀ,ע:�ó��������ж�
			if(prj.getParent()!=null&&prj.getParent().getId().equals(curPrj.getId())
					&&acct.getLongNumber().equals(curAcct.getLongNumber())){
				if(entry.getAmount()==null){
					sum=FDCHelper.ZERO;
				}else{
					sum=amount.add(entry.getAmount());
				}
				entry.setAmount(sum);
				row.getCell("amount").setValue(sum);
			}
//			������ͬ������Ŀ���ϼ���Ŀ,���ݹ鴦��
			if(prj.getId().equals(curPrj.getId())
					&&acct.getParent()!=null
					&&acct.getParent().getId().equals(curAcct.getId())){
				if(entry.getAmount()==null){
					sum=FDCHelper.ZERO;
				}else{
					sum=amount.add(entry.getAmount());
				}
				entry.setAmount(sum);
				row.getCell("amount").setValue(sum);
				
				totAddlAcct(curPrj,curAcct,amount,i);
			}
		}

	}
    protected boolean isAddlAcctLeaf(OtherSplitNewEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsAddlAccount() 
    			&& entry.getCostAccount()!=null && entry.getCostAccount().isIsLeaf()
    			&& entry.getCostAccount().getCurProject()!=null && entry.getCostAccount().getCurProject().isIsLeaf()
    			&& !isProdSplitLeaf(entry)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    protected String getSplitBillEntryClassName(){
		return ConChangeSplitEntryInfo.class.getName();
	}
    protected void apptAmount(int rowIndex){
    	OtherSplitNewEntryInfo topEntry = (OtherSplitNewEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();
		fdcCostSplit.apptAmount(getEntrys(),topEntry);
		
		int level=topEntry.getLevel();
		IRow row=null;
		boolean isMeasureContract=isMeasureContract();
		
		Object value = kdtSplitEntry.getCell(rowIndex, "amount").getValue();
		//�ѷ�̯�ܽ��
		BigDecimal totalAmt = FDCHelper.ZERO;
		for(int i=rowIndex+1; i<kdtSplitEntry.getRowCount(); i++){				
			row=kdtSplitEntry.getRow(i);
			OtherSplitNewEntryInfo entry=(OtherSplitNewEntryInfo)row.getUserObject();
			
			if(entry.getLevel()>level){
				BigDecimal amount=entry.getAmount();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("amount").setValue(amount);
				if (entry.getLevel() == level + 1) {
					totalAmt = FDCHelper.add(totalAmt, amount);
				}
				if (i == kdtSplitEntry.getRowCount() - 1 && FDCHelper.compareTo(value, totalAmt) != 0) {
					row.getCell("amount").setValue(
							FDCHelper.add(row.getCell("amount").getValue(), FDCHelper.subtract(value, totalAmt)));
				}
				
				if(isMeasureContract&&isProdSplitLeaf(entry)){
					entry.setPrice(topEntry.getPrice());
					row.getCell("price").setValue(topEntry.getPrice());	
					row.getCell("workLoad").setValue(FDCHelper.divide(entry.getAmount(), entry.getPrice()));
				}
			}else{
				break;
			}
		}
    }
    protected OtherSplitNewEntryCollection getEntrys(){
		entrysMap.clear();
		entrys = new OtherSplitNewEntryCollection();
		for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
			OtherSplitNewEntryInfo info = (OtherSplitNewEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
			// entrysMap.put(String.valueOf(info.getSeq()), info);
			String key = getEntrysMapKey(info);
			entrysMap.put(key, info);
			entrys.add(info);
		}
    	
    	return entrys;
	}
    protected String getEntrysMapKey(OtherSplitNewEntryInfo entryInfo) {
		String key = entryInfo.getSeq() + "";

		return key;
	}
    private void totApptValue(int rowIndex){
    	OtherSplitNewEntryInfo entry = (OtherSplitNewEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();

    	//�޸ĵ��ýӿ�	jelon 12/26/2006
		/*CostSplitType splitType=entry.getSplitType();
		calcApportionData(rowIndex,splitType);*/
		

		//fdcCostSplit.totApptValue((IObjectCollection)editData.get("entrys"),entry);
		fdcCostSplit.totApptValue(getEntrys(),entry);
						
		int level=entry.getLevel();
		IRow row=null;
		BigDecimal amount=null;
		
		for(int i=rowIndex; i<kdtSplitEntry.getRowCount(); i++){				
			row=kdtSplitEntry.getRow(i);
			entry=(OtherSplitNewEntryInfo )row.getUserObject();
			
			if(entry.getLevel()>level
					|| (entry.getLevel()==level && i==rowIndex)){
				amount=entry.getApportionValueTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("apportionValueTotal").setValue(amount);   	 

				amount=entry.getDirectAmountTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("directAmountTotal").setValue(amount); 

				amount=entry.getOtherRatioTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("otherRatioTotal").setValue(amount);
				
			}else{
				break;
			}
		}
    }
    protected IRow insertEntry(int rowIndex, IObjectValue detailData)
    {
        IRow row = null;
        
        row = kdtSplitEntry.addRow(rowIndex);

        loadLineFields(kdtSplitEntry, row, detailData);
        
        return row;
    }
    /**
     * 
     */
    protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	BigDecimal total = this.txtamount.getBigDecimalValue();
    	BigDecimal totalRate = BigDecimal.ZERO;
    	IRow row = kdtEntrys.getRow(e.getRowIndex());
    	if(kdtEntrys.getColumnKey(e.getColIndex()).equals("rate")) {
    		BigDecimal rate = (BigDecimal) row.getCell("rate").getValue();
    		if(rate != null && rate.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
    			MsgBox.showWarning("��ֱ�������100�������!");
    			row.getCell("rate").setValue(null);
    			row.getCell("amount").setValue(null);
    			SysUtil.abort();
    		}
    		row.getCell("amount").setValue(rate == null ? BigDecimal.ZERO : rate.multiply(total).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP));
    	}
    	if(kdtEntrys.getColumnKey(e.getColIndex()).equals("amount")) {
    		BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
    		if(amount != null && amount.compareTo(total) > 0) {
    			MsgBox.showWarning("��ֽ������ܵ����������!");
    			row.getCell("rate").setValue(null);
    			row.getCell("amount").setValue(null);
    			SysUtil.abort();
    		}
    		row.getCell("rate").setValue(amount == null ? BigDecimal.ZERO : amount.divide(total, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
    	}
    	this.txtamount.setValue(total);
    	TableHelper.getFootRow(kdtEntrys, new String[]{"rate", "amount"});
    }
    
    private void setOneTreeDisplay(int rowIndex){
    	OtherSplitNewEntryInfo topEntry = (OtherSplitNewEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();    	
    	int topLevel=topEntry.getLevel();		
        CostAccountInfo topAcct=topEntry.getCostAccount();

        OtherSplitNewEntryInfo entry=null;
        IRow row=null;
        ICell cell=null;
        String display=null;
        int level=0;
        
        CostAccountInfo acct=null;
        CurProjectInfo proj=null;
        
        

    	NodeClickListener nodeClickListener = new NodeClickListener(){
    		public void doClick(CellTreeNode source, ICell cell, int type)	{
    			//��Ŀչ������Ʒչ��Ӧ�ý������� by sxhong 2009/02/05
    			if(source!=null&&!source.isCollapse()&&source.isHasChildren()){
    				//�����¼����е�+���-
    				int level=source.getTreeLevel();
    				for(int i=cell.getRowIndex()+1;i<kdtSplitEntry.getRowCount();i++){
    					if(cell.getColumnIndex()==kdtSplitEntry.getColumnIndex("costAccount.curProject.name")){
    						ICell cell2 = kdtSplitEntry.getCell(i, "costAccount.curProject.name");
    						if(cell2.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell2.getValue();
    							if(node.getTreeLevel()<=level){
    								return;
    							}
    							node.setCollapse(false);
    						}
    						ICell cell3 = kdtSplitEntry.getCell(i, "costAccount.name");
    						if(cell3.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell3.getValue();
    							node.setCollapse(false);
    						}
    					}else if(cell.getColumnIndex()==kdtSplitEntry.getColumnIndex("costAccount.name")){
    						ICell cell3 = kdtSplitEntry.getCell(i, "costAccount.name");
    						if(cell3.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell3.getValue();
    							if(node.getTreeLevel()<=level){
    								return;
    							}
    							node.setCollapse(false);
    						}
    					}
    				}
    			}
     		}
    	};
        
        for(int i=rowIndex; i<kdtSplitEntry.getRowCount(); i++){
        	row=kdtSplitEntry.getRow(i);   
			entry = (OtherSplitNewEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			
			row.getCell("directAmount").setValue(entry.getDirectAmount());
			
			level=entry.getLevel();
			
			acct=entry.getCostAccount();
			if(acct==null){
				proj=null;
			}else{
				proj=acct.getCurProject();
			}
				
			if(level>=topLevel){
				if(level==topLevel && i!=rowIndex){
					//��һ��������
					break;	
				}
				
				//���롢����
				if(entry.getCostAccount().getCurProject()!=null){
					//����
					row.getCell("costAccount.curProject.number").setValue(
							entry.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
					row.getCell("costAccount.number").setValue(
							entry.getCostAccount().getLongNumber().replace('!','.'));

					//����
					if(level==0){
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getDisplayName().replace('_','\\'));
						
					}else if(entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT && entry.isIsLeaf()){
						//��Ʒ�����ϸ
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						row.getCell("costAccount.name").setValue("");
						
					}else if(entry.isIsAddlAccount()){
						//���ӿ�Ŀ��ֱ�ӷ���
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						//row.getCell("costAccount.number").setValue(entry.getCostAccount().getLongNumber());
						row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());
						
					}else{
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getName());
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getName());	
					}		
					
					
					//��������
					if(level>=topLevel){
						CellTreeNode node = new CellTreeNode();
						node.addClickListener(nodeClickListener);			
						cell=row.getCell("costAccount.curProject.name");			
						// �ڵ��ֵ
						node.setValue(cell.getValue());
						// �Ƿ����ӽڵ�
						//if(entry.getCostAccount().getLongNumber().equals(topAcct.getLongNumber()) && !entry.getCostAccount().getCurProject().isIsLeaf()){
						/*if(entry.getCostAccount().getLongNumber().replace('!','.').equals(topAcct.getLongNumber().replace('!','.')) 
								&& !entry.getCostAccount().getCurProject().isIsLeaf()
								&& !isProdSplitLeaf(entry)){*/
						/*
						 * ���������Ƿ��к��ӽڵ� by 29 // if (!entry.isIsLeaf() // &&
						 * !entry.getCostAccount().getCurProject().isIsLeaf() //
						 * && //
						 * entry.getCostAccount().getLongNumber().replace('!',
						 * // '.').equals( //
						 * topAcct.getLongNumber().replace('!', '.'))) { //
						 * node.setHasChildren(true); // } else { // //
						 * node.setHasChildren(false); // }
						 */
						
						//node.setHasChildren(!entry.isIsAddlAccount());
						// �ڵ��������
						node.setTreeLevel(entry.getLevel());
						cell.getStyleAttributes().setLocked(false);
						cell.setValue(node);
						
						if(level!=topLevel){
							node = new CellTreeNode();
							node.addClickListener(nodeClickListener);			
							cell=row.getCell("costAccount.name");			
							// �ڵ��ֵ
							node.setValue(cell.getValue());
							/*
							 * ���������Ƿ��к��ӽڵ� by 29// �Ƿ����ӽڵ� //
							 * node.setHasChildren(!entry.getCostAccount(). //
							 * isIsLeaf() // || (!entry.isIsLeaf() && //
							 * entry.getCostAccount().isIsLeaf() && //
							 * entry.getSplitType()!=null && //
							 * entry.getSplitType() //
							 * .equals(CostSplitTypeEnum.PRODSPLIT)) );
							 */
							// �ڵ��������
							//node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel());	
							if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
								node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel()+1);	
							}else{
								node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel());	
							}
											
							cell.getStyleAttributes().setLocked(false);
							cell.setValue(node);							
						}
						//end
					}
				}
								
								
				//��ɫ
				if(level==0){
					row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
					row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));					
					row.getCell("splitScale").getStyleAttributes().setBackground(new Color(0xFFFFFF));
				}else{
					if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT) && entry.getProduct()==null){
						row.getStyleAttributes().setBackground(new Color(0xF5F5E6));
					}else{
						row.getStyleAttributes().setBackground(new Color(0xE8E8E3));
					}					
					row.getCell("amount").getStyleAttributes().setLocked(true);
					//�ǿ�Ŀ�в��ܱ༭ by hpw 2010-06-25
					row.getCell("splitScale").getStyleAttributes().setLocked(true);
					
					//���ӿ�Ŀ��������¼���
					/*
					if(entry.isIsAddlAccount() && entry.getCostAccount().isIsLeaf() && entry.getCostAccount().getCurProject().isIsLeaf()){
						if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitType.PRODSPLIT)){						
						}else{
							row.getCell("amount").getStyleAttributes().setLocked(false);
							row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));									
						}					
					}*/
					if(entry.isIsAddlAccount() 
							&& proj!=null && proj.isIsLeaf()
							&& acct!=null && acct.isIsLeaf()
							&& !isProdSplitLeaf(entry)){
						row.getCell("amount").getStyleAttributes().setLocked(false);
						row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));								
					}
				}					
				if(isMeasureContract()){
//					row.getCell("price").getStyleAttributes().setBackground(new Color(0xFFFFFF));
//					row.getCell("workLoad").getStyleAttributes().setBackground(new Color(0xFFFFFF));
					setMeasureCtrl(row);
				}
				//ֱ�ӹ���
				initDirectAssign(row);		
				
			}else{
				break;
			}
			
        }
        for(int i=rowIndex;i<kdtSplitEntry.getRowCount();i++){
        	row=kdtSplitEntry.getRow(i);
			entry = (OtherSplitNewEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()==0&&i!=rowIndex){
				break;
			}
			IRow rowNext = kdtSplitEntry.getRow(i + 1);
			// ȡ�¼��зǿ������Ҫ�ж�
			if (rowNext == null) {
				continue;
			}
			if (!entry.isIsLeaf() && rowNext.getStyleAttributes().isHided()) {
				Object obj = row.getCell("costAccount.name").getValue();
				CellTreeNode node=null;
				if(obj instanceof CellTreeNode){
					node=(CellTreeNode)obj;
					node.setCollapse(true);
				}
				
			}
        }
        //������׼
        setStandard(rowIndex);
    }
    private void setStandard(int index){
    	OtherSplitNewEntryInfo curEntry = (OtherSplitNewEntryInfo)kdtSplitEntry.getRow(index).getUserObject();    
     	
     	int level=curEntry.getLevel();	
     	
     	//1. ��ָ��ݽڵ㣬ʹ�ò��������Ϊ������׼
 		if(level==0){
 			//Jelon Dec 13, 2006			
 			/*if(curEntry.getSplitType()!=null && curEntry.getSplitType()!=CostSplitType.MANUALSPLIT){
 				kdtSplitEntry.getRow(index).getCell("standard").setValue(curEntry.getSplitType());			
 			}*/
 			if(curEntry.getSplitType()==null || curEntry.getSplitType()==CostSplitTypeEnum.MANUALSPLIT){
 				kdtSplitEntry.getRow(index).getCell("standard").setValue("");	
 			}else{
 				kdtSplitEntry.getRow(index).getCell("standard").setValue(curEntry.getSplitType().toString());			
 			}
 		}
     	
 		//2. ������ֽ�㣬ʹ�ø����ķ�̯������Ϊ������׼
     	String apptType=null;
     	if(curEntry.getApportionType()!=null){
     		apptType=curEntry.getApportionType().getName();
     	}
     	OtherSplitNewEntryInfo entry=null;
 		IRow row=null;
 		
 		for(int i=index+1; i<kdtSplitEntry.getRowCount(); i++){
 			row=kdtSplitEntry.getRow(i);
 			entry = (OtherSplitNewEntryInfo)row.getUserObject();
 			
 						
 			if(entry.getLevel()==level+1){	
 				if(entry.isIsAddlAccount()){
 					if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
 						row.getCell("standard").setValue(apptType);
 					}else{
 						row.getCell("standard").setValue("ֱ�ӷ���");
 					}
 				}else{
 					row.getCell("standard").setValue(apptType);
 				}
 				
 				if(!entry.isIsLeaf()){
 					setStandard(i);
 				}
 			}
 			else if(entry.getLevel()<=level){
 				break;
 			}		
 			
 		}	   					
 	}
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection selectors = super.getSelectors();
    	selectors.add("splitEntry.costAccount.curProject.isLeaf");
    	selectors.add("splitEntry.costAccount.curProject.longNumber");
    	selectors.add("splitEntry.costAccount.curProject.number");
    	selectors.add("splitEntry.costAccount.curProject.name");
    	selectors.add("splitEntry.costAccount.curProject.displayName");
    	selectors.add("splitEntry.costAccount.name");
    	selectors.add("splitEntry.costAccount.longNumber");
    	selectors.add("splitEntry.costAccount.displayName");
    	return selectors;
    }
    public void initDirectAssign(IRow row){
    	OtherSplitNewEntryInfo entry;
		entry = (OtherSplitNewEntryInfo)row.getUserObject();
		
		
		boolean isTrue=false;
		isTrue=isProdSplitEnabled(entry);
    	
		if(!isTrue || !entry.isIsLeaf()){
			row.getCell("product").getStyleAttributes().setLocked(true);
			return;
		}else{
			row.getCell("product").getStyleAttributes().setBackground(new Color(0xFFFFFF));
			initDirectMap.put(new Integer(row.getRowIndex()), entry.getCostAccount().getCurProject().getId().toString());
		}
    }
    private boolean isProdSplitEnabled(OtherSplitNewEntryInfo entry){		
		boolean isTrue=false;
		if (entry != null && entry.getCostAccount() != null && entry.getCostAccount().getCurProject() != null && entry.getCostAccount().getCurProject().isIsLeaf()) {
			
			if(entry.getSplitType()==null || entry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				isTrue=true;
			}else if(entry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) || entry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
				isTrue=true;
	    	}else if(entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
	    		if(!entry.isIsLeaf()){
	    			isTrue=true;
	    		}
	    	}			
		}
		
		return isTrue;
	}
    protected void setMeasureCtrl(final IRow row) {
		Color cantEditColor=new Color(0xF5F5E6);
		row.getCell("price").getStyleAttributes().setBackground(new Color(0xFFFFFF));
		row.getCell("workLoad").getStyleAttributes().setBackground(new Color(0xFFFFFF));
		BigDecimal amount=FDCHelper.toBigDecimal(row.getCell("amount").getValue());
		BigDecimal price=FDCHelper.toBigDecimal(row.getCell("price").getValue());
		BigDecimal workLoad=FDCHelper.toBigDecimal(row.getCell("workLoad").getValue());
		if(price.signum()!=0||workLoad.signum()!=0){
			row.getCell("amount").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("amount").getStyleAttributes().setLocked(true);
		}else if (amount.signum()!=0){
			row.getCell("price").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("workLoad").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("price").getStyleAttributes().setLocked(true);
			row.getCell("workLoad").getStyleAttributes().setLocked(true);
		}else{
			row.getCell("amount").getStyleAttributes().setLocked(false);
			row.getCell("price").getStyleAttributes().setLocked(false);
			row.getCell("workLoad").getStyleAttributes().setLocked(false);
			row.getCell("amount").getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("price").getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("workLoad").getStyleAttributes().setBackground(Color.WHITE);
		}
		if(row.getUserObject() instanceof OtherSplitNewEntryInfo){
			OtherSplitNewEntryInfo entry=(OtherSplitNewEntryInfo)row.getUserObject();
			if(isProdSplitLeaf(entry)){
				row.getCell("price").setValue(entry.getPrice());	
				row.getCell("workLoad").setValue(FDCHelper.divide(entry.getAmount(), entry.getPrice()));
				row.getCell("price").getStyleAttributes().setBackground(cantEditColor);
				row.getCell("workLoad").getStyleAttributes().setBackground(cantEditColor);
				row.getCell("price").getStyleAttributes().setLocked(true);
				row.getCell("workLoad").getStyleAttributes().setLocked(true);
			}
		}
	}
    protected boolean isMeasureContract(){
		return false;
	}
    protected boolean isProdSplitLeaf(OtherSplitNewEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * ������
     * @param e
     */
    protected void actionAddLine(ActionEvent e) {
    	BigDecimal adjAmount = txtamount.getBigDecimalValue();
    	if(adjAmount == null) {
    		MsgBox.showWarning("������д�������!");
    		SysUtil.abort();
    	} else if(adjAmount.compareTo(BigDecimal.ZERO) == 0) {
    		MsgBox.showWarning("��������Ϊ0,���޸�!");
    		SysUtil.abort();
    	}
    	kdtEntrys.addRow();
	}
    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
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
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        syncDataFromDB();
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
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
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!(editData.getState().equals(FDCBillStateEnum.SAVED) || 
    			editData.getState().equals(FDCBillStateEnum.SUBMITTED))) {
    		MsgBox.showWarning("�Ǳ�������ύ�����޷��޸�!");
    		SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!(editData.getState().equals(FDCBillStateEnum.SAVED) || 
    			editData.getState().equals(FDCBillStateEnum.SUBMITTED))) {
    		MsgBox.showWarning("�Ǳ�������ύ�����޷�ɾ��!");
    		SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionRemoveLine_actionPerformed(e);
    	if(!actionRemoveLine.isEnabled()||!actionRemoveLine.isVisible()) return;
        //if ((kdtSplitEntry.getSelectManager().size() == 0) || isTableColumnSelected(kdtSplitEntry))
    	if ((kdtSplitEntry.getSelectManager().size() == 0))
        {
            FDCMsgBox.showInfo(this, EASResource
                    .getString(FrameWorkClientUtils.strResource
                            + "Msg_NoneEntry"));

            //FDCMsgBox.showInfo(this,"û��ѡ�з�¼���޷�ɾ����");
            return;
        }

        //[begin]����ɾ����¼����ʾ����
        if(confirmRemove())
        {
            int top = kdtSplitEntry.getSelectManager().get().getBeginRow();
            int bottom = kdtSplitEntry.getSelectManager().get().getEndRow();
            
            int idx=0;
            int idx1,idx2;
            
            boolean isTrue=false;
            OtherSplitNewEntryInfo entry=null;
            
            for(int i =bottom ;i>=top ;i--)
            {
            	idx=i;
            	
            	idx1=idx;
            	idx2=idx;
            	
            	//�������һ��
            	isTrue=false;
            	for(int j=i+1; j<kdtSplitEntry.getRowCount(); j++){
            		entry = (OtherSplitNewEntryInfo)kdtSplitEntry.getRow(j).getUserObject();
            		if(entry.getLevel()==0){
            			idx2=j-1;
            			isTrue=true;
            			break;
            		}
            	}
            	if(!isTrue){
            		idx2=kdtSplitEntry.getRowCount()-1;
            	}
            	if(idx2<idx){
            		idx2=idx;
            	}
            	
            	//�����һ����ǰɾ����ֱ��Level=0
            	for(int j=idx2; j>=0; j--){
            		idx1=j;
            		
            		entry = (OtherSplitNewEntryInfo)kdtSplitEntry.getRow(j).getUserObject();
            		if(entry.getLevel()==0){
            			removeEntry(j);
            			break;
            		}else{
            			removeEntry(j);
            		}
            	}
            	
            	//i=idx1-1;
            	i=idx1;
            }            
        }

        
        if(kdtSplitEntry.getRowCount()>0){
        	calcAmount(0);
        }else{
//        	txtSplitedAmount.setValue(FDCHelper.ZERO);    
        }        	
        

		//������		jelon 12/28/2006
		int idx=0;
		OtherSplitNewEntryInfo entry=null;
		for(int i=0; i<kdtSplitEntry.getRowCount(); i++){	
			entry=(OtherSplitNewEntryInfo)kdtSplitEntry.getRow(i).getUserObject();			
			if(entry.getLevel()==0){
				if(entry.getIndex()>idx){
					idx=entry.getIndex();
				}
			}
		}
		groupIndex=idx;
    }

    protected void removeEntry(int idxRow)
    {    	
        IObjectValue detailData = (IObjectValue) kdtSplitEntry.getRow(idxRow).getUserObject();
        kdtSplitEntry.removeRow(idxRow);
        
        IObjectCollection collection = (IObjectCollection) kdtSplitEntry.getUserObject();
        if (collection == null)
        {
        	return;
        }
        else
        {
            if( detailData != null ) {
                collection.removeObject(detailData);
            }
        }
    }
    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
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
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
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
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
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
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return new OtherSplitNewEntryInfo();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
    	ContractBillInfo contractInfo = (ContractBillInfo) getUIContext().get("Contract");
    	Date bizDate = (Date) getUIContext().get("BizDate");
    	CurProjectInfo project = (CurProjectInfo) getUIContext().get("project");
    	
        com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillInfo objectValue = new com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        if(bizDate == null)
        	bizDate = Calendar.getInstance().getTime();
        objectValue.setBizDate(bizDate);
        objectValue.setContract(contractInfo);
        objectValue.setCurProject(project);
        objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        return objectValue;
    }

}