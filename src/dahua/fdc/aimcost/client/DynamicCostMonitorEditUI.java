/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import static com.kingdee.eas.fdc.basedata.FDCNumberHelper.divide;
import static com.kingdee.eas.fdc.basedata.FDCNumberHelper.multiply;
import static com.kingdee.eas.fdc.basedata.FDCNumberHelper.subtract;
import static com.kingdee.eas.fdc.basedata.FDCNumberHelper.toBigDecimal;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.DynamicCostMonitorCAEntriesInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMonitorCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostMonitorContractEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMonitorFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostMonitorInfo;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractFullInfoUI;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��̬�ɱ���� �༭����
 * @author owen_wen 
 */
public class DynamicCostMonitorEditUI extends AbstractDynamicCostMonitorEditUI
{
	private static final String ProgrammeBalance = "�滮���";
	/**
	 * ��ͬID
	 */
	private static final String CON_ID = "conId";
	private static final Logger logger = CoreUIObject.getLogger(DynamicCostMonitorEditUI.class);
	
	private ImageIcon yellow_ball;
	private ImageIcon red_ball;
	private ImageIcon green_ball;
	
	private ProgrammingInfo pi;
	

	// ��ȡ��Ȩ�޵���֯
	protected Set authorizedOrgs = null;
    
    public DynamicCostMonitorEditUI() throws Exception
    {
        super();
    }
  
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return DynamicCostMonitorFactory.getRemoteInstance();
    }

    protected IObjectValue createNewDetailData(KDTable table)
    {		
        return null;
    }

    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        DynamicCostMonitorInfo dcmInfo = new DynamicCostMonitorInfo();
		dcmInfo.setCreator((com.kingdee.eas.base.permission.UserInfo) (SysContext.getSysContext().getCurrentUser()));
		dcmInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		dcmInfo.setState(FDCBillStateEnum.SAVED);
		dcmInfo.setCurProject(getSelectedCurProjectInfo());
		if (getSelectedCurProjectInfo() != null) {
			try {
				dcmInfo.setProgramming(getLatestProgrammingWithPrjId(getSelectedCurProjectInfo().getId().toString()));
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}
		return dcmInfo;
    }
    
    @Override
	public void onLoad() throws Exception {
		super.onLoad();
		initAmontUnitToolBar();
		initCurProjectTree();
		
		//����delete������Ϊdelete�󲻻ᴥ��editStop�¼�
		tblContractEntries.getActionMap().remove(KDTAction.DELETE);
		tblCostAccountEntries.getActionMap().remove(KDTAction.DELETE);
	}
    
    /**
	 * ��ʼ���������еġ���λ����ť
	 */
    private void initAmontUnitToolBar() {
		for (Iterator it = AmountUnitEnum.getEnumList().iterator(); it.hasNext();) {
			AmountUnitEnum unitEnum = (AmountUnitEnum) it.next();
			KDMenuItem menuItem = new KDMenuItem(unitEnum.getAlias());
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					KDMenuItem source = (KDMenuItem) e.getSource();
					for (int i = 0; i < btnSwitchUnit.getAssistButtonCount(); i++) {
						KDMenuItem assitButton = (KDMenuItem) btnSwitchUnit.getAssitButton(i);
						if (assitButton.getText().equals(source.getText())) {
							assitButton.setIcon(EASResource.getIcon("imgTbtn_affirm"));
						} else {
							assitButton.setIcon(null);
						}
					}					
				}
			});
			btnSwitchUnit.addAssistMenuItem(menuItem);
		}
		//Ĭ��ѡ�е�һ����λ��Ԫ
		KDMenuItem assitButtonYuan = (KDMenuItem) btnSwitchUnit.getAssitButton(0);
		assitButtonYuan.setIcon(EASResource.getIcon("imgTbtn_affirm"));
	}
    
    /**
	* ��ʼ��������Ŀ��
	*/
    private void initCurProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();		
		projectTreeBuilder.build(this, projectTree, actionOnLoad);
		projectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		projectTree.expandAllNodes(true, (TreeNode) ((TreeModel) projectTree.getModel()).getRoot());
		
		authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}
		}
	}
    
    @Override
	protected void initWorkButton() {
		super.initWorkButton();
		initImageIcon();
		initStatusIcon();
	}
    
    @Override
	protected void projectTree_valueChanged(TreeSelectionEvent e) throws Exception {
		CurProjectInfo prjInfo = getSelectedCurProjectInfo();
		
		if (prjInfo == null) {
			return;
		}
		
		//0. Ԥ�жϣ�ѡ�е���Ŀ����û������Լ�滮����û�й滮������ʾ�����ڵ��л�ԭ���ڵ���
    	if (!checkIsExistProgramming(prjInfo.getId().toString())) {
			FDCMsgBox.showWarning(this, "ѡ�еĹ�����Ŀ��û������Լ�滮���ݲ�������̬�ɱ����ҵ��");
			projectTree.setSelectionPath(e.getOldLeadSelectionPath()); //TODO ���ٴδ���һ��value_change�¼������Ľ�
			this.abort();
		}
    	
    	//1. ���ж�ѡ�еĹ�����Ŀ����û��������̬��أ�����ȡ���µ�һ����ʾ
    	DynamicCostMonitorCollection colls = getLatestDynamicCostMonitorWithPrjId(prjInfo.getId().toString());
		if (colls.size() == 1) {
			setOprtState(OprtState.VIEW);
			setDataObject(colls.getObject(0));
			loadFields();
			tblContractEntries.getIOManager().load(new ByteArrayInputStream(editData.getConEntryTblContent()));
			tblCostAccountEntries.getIOManager().load(new ByteArrayInputStream(editData.getCaEntryTblContent()));			
		} else {
		//2. ��ѡ�еĹ�����Ŀ��û�У�������һ�����û��༭
			createNewDataForEdit(prjInfo);
		}

		calcTotalAmountForTblCostAccount();
	}
    
    /**
	 * tblCostAccountEntries��������ϻ��ܽ��
	 */
	private void calcTotalAmountForTblCostAccount() {
		FDCTableHelper.calcTotalAmount(tblCostAccountEntries, 0, new String[] { "aimCostAmt", "willHappendCost", "dynamicCost", "diff" }, "caId",
				"caParentId");
	}

	/**
	 * ����һ������̬�ɱ���ء����༭ʹ��
	 * @param prjInfo ������Ŀ
	 */
	private void createNewDataForEdit(CurProjectInfo prjInfo) throws EASBizException, BOSException, SQLException {
		setDataObject(createNewData());
		editData.setCurProject(prjInfo);
		setOprtState(OprtState.ADDNEW);
		unLockUI();
		loadFields();
		ProgrammingInfo pi = getLatestProgrammingWithPrjId(prjInfo.getId().toString());
		editData.setProgramming(pi);
		fillPCDataToTblContractEntries(pi);
		fillCostAccountToTableWhenAddNew();
		fillDataToTblContractEntriesWhenAddNew(pi);
		allocateAmountToCostAccountWhenAddNew();
	}
    
    
    @Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		CurProjectInfo prjInfo = getSelectedCurProjectInfo();
		if (prjInfo == null) {
			FDCMsgBox.showConfirm2(this, "��ѡ�й�����Ŀ�ڵ���������");
			return;
		}
		super.actionAddNew_actionPerformed(e);
		createNewDataForEdit(prjInfo);
		calcTotalAmountForTblCostAccount();
	}
    
    private DynamicCostMonitorCollection getLatestDynamicCostMonitorWithPrjId(String prjId) throws BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", prjId));
		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		view.setTopCount(1);
		SorterItemInfo sorter = new SorterItemInfo("createTime");
		sorter.setSortType(SortType.DESCEND);
		view.getSorter().add(sorter);
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.number");
		sic.add("curProject.name");
		sic.add("programming.entries.*");
		sic.add("contractEntries.*");
		sic.add("contractEntries.contractbill.*");
		sic.add("contractEntries.contractbill.programmingContract.id");
		sic.add("costAccountEntries.*");
		view.setSelector(sic);
		
		return DynamicCostMonitorFactory.getRemoteInstance().getDynamicCostMonitorCollection(view);
	}
    
    /**
	 *  ����Լ����еĺ�Լ�滮������䵽��̬�ɱ���ط�¼����
	 * @param pi ��Լ���
	 */
	private void fillPCDataToTblContractEntries(ProgrammingInfo pi) throws BOSException {
		tblContractEntries.removeRows(false);
		int maxLevel = 0;
		for (int i = 0; i < pi.getEntries().size(); i++) {
			IRow row = tblContractEntries.addRow();
			row.setUserObject(pi.getEntries().get(i));
			row.getCell("programmingContractName").setValue(pi.getEntries().get(i).getName());
			row.getCell("pgAmount").setValue(pi.getEntries().get(i).getAmount());
			row.getCell("reservedRateOfChange").setValue(pi.getEntries().get(i).getReservedChangeRate()); 
			row.getCell("controlAmt").setValue(pi.getEntries().get(i).getControlAmount());
			row.getCell("pcId").setValue(pi.getEntries().get(i).getId()); 
			row.getCell("state").setValue(yellow_ball);
			row.setTreeLevel(pi.getEntries().get(i).getLevel() - 1);
			row.setUserObject(pi.getEntries().get(i));
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(FDCColorConstants.lockColor);
			maxLevel = Math.max(maxLevel, pi.getEntries().get(i).getLevel());
		}
		tblContractEntries.getTreeColumn().setDepth(maxLevel + 1);//��Ϊ���滹Ҫ�Ӻ�ͬ�к͹滮����У����Լ��λ�Ҫ��1
	}

	/**
	 * ����pi�����ĺ�ͬ��䵽��̬�ɱ���ط�¼����, �����ɱ����ʱʹ��
	 * @param pi �ù�����Ŀ�µĺ�Լ���
	 */
	private void fillDataToTblContractEntriesWhenAddNew(ProgrammingInfo pi) throws BOSException, EASBizException {
		ContractBillCollection cbColls = getLatestContractBillCollsForAddNew(pi);
		
		// ȡ��ͬ������
		Map contractChangeAmounts = new HashMap();
		if (cbColls.size() > 0) {
			String[] contractIds = new String[cbColls.size()];
			for (int i = 0; i < cbColls.size(); i++) {
				contractIds[i] = cbColls.get(i).getId().toString();
			}
			contractChangeAmounts = ContractFacadeFactory.getRemoteInstance().getChangeAmt(contractIds);
		}
		
		// ����ͬ����,��������������ݣ�addRowʱ��������������
		for (int i = tblContractEntries.getRowCount() - 1; i >= 0; i--) {
			ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) tblContractEntries.getRow(i).getUserObject();
			
			BigDecimal totalEstimatedSettledAmt = FDCNumberHelper.ZERO; // �ú�Լ�滮�µ��ۼơ�Ԥ�ƽ����
			int hasInsertRowCount = 0;//ͳ�Ʊ���Լ�滮�²����˶����к�ͬ
			
			for (int j = 0; j < cbColls.size(); j++) {
				if (!pcInfo.getId().equals(cbColls.get(j).getProgrammingContract().getId())) {
					continue;
				}
				IRow newRow = tblContractEntries.addRow(i + hasInsertRowCount + 1);
				hasInsertRowCount++;
				newRow.setTreeLevel(pcInfo.getLevel());
				newRow.getCell("conNumber").setValue(cbColls.get(j).getNumber());
				newRow.getCell("conNumber").getStyleAttributes().setFontColor(Color.BLUE);
				newRow.getCell("conName").setValue(cbColls.get(j).getName());
				newRow.getCell("conName").getStyleAttributes().setFontColor(Color.BLUE);
				newRow.getCell("conAmount").setValue(cbColls.get(j).getAmount());
				newRow.getCell("conChangeAmt").setValue(contractChangeAmounts.get(cbColls.get(j).getId().toString())); 
				newRow.getCell("isSettled").setValue(cbColls.get(j).isHasSettled());
				// �ѽ���ĺ�ͬ����Ԥ�ƴ����������ɱ༭,�ѽ���ĺ�ͬ��������д��Ԥ�ƴ���������
				newRow.getCell("estimatedHappenAmt").getStyleAttributes().setLocked(cbColls.get(j).isHasSettled());
				newRow.getCell("estimatedHappenAmt").getStyleAttributes()
						.setBackground(cbColls.get(j).isHasSettled() ? FDCColorConstants.cantEditColor : null);
				newRow.getCell("estimatedHappenAmt").setEditor(FDCTableHelper.getCellNumberEditor());
				
				newRow.getCell(CON_ID).setValue(cbColls.get(j).getId());
				// �ټ��㲢�������е�ֵ��E=B+C+D
				if (!cbColls.get(j).isHasSettled()) {//δ����
					newRow.getCell("estimatedSettledAmt").setValue(
							FDCNumberHelper.add(FDCNumberHelper
									.add(newRow.getCell("conAmount").getValue(), newRow.getCell("conChangeAmt").getValue()), newRow.getCell(
									"estimatedHappenAmt").getValue()));
				} else {//�ѽ���
					newRow.getCell("estimatedSettledAmt").setValue(cbColls.get(j).getSettleAmt());
				}
				
				totalEstimatedSettledAmt = FDCNumberHelper.add(totalEstimatedSettledAmt, newRow.getCell("estimatedSettledAmt").getValue());
				DynamicCostMonitorContractEntryInfo ceInfo = new DynamicCostMonitorContractEntryInfo();
				ceInfo.setParent(editData);
				editData.getContractEntries().add(ceInfo);
				ceInfo.setContractbill(cbColls.get(j));
			}
			
			// �����������������ӡ��滮���� 
			if (pcInfo.isIsLeaf() // ��ϸ�У����й滮���
					&& pcInfo.getAmount().subtract(totalEstimatedSettledAmt).compareTo(FDCNumberHelper.ZERO) > 0) {
				IRow newRow = tblContractEntries.addRow(i + hasInsertRowCount + 1); // ���ӡ�δǩ����ͬ����
				newRow.setTreeLevel(pcInfo.getLevel());
				newRow.getCell("conNumber").setValue(ProgrammeBalance);
				newRow.getCell("conAmount").setValue(pcInfo.getAmount().subtract(totalEstimatedSettledAmt));
				newRow.getCell("estimatedHappenAmt").setValue(pcInfo.getAmount().subtract(totalEstimatedSettledAmt));
				newRow.getCell("estimatedSettledAmt").setValue(pcInfo.getAmount().subtract(totalEstimatedSettledAmt));
			}
			
			// ���ϻ��ܺϼ��� start��Ŀǰֻ������ϸ��Լ�滮 
			BigDecimal[] sumAmts = new BigDecimal[2]; //0 ��ͬ��1 �����2Ԥ�ƴ�������3Ԥ�ƽ�����
			for (int j = 0; j < cbColls.size(); j++) {
				if (pcInfo.getId().equals(cbColls.get(j).getProgrammingContract().getId())) {
					sumAmts[0] = FDCNumberHelper.add(sumAmts[0], cbColls.get(j).getAmount());
					sumAmts[1] = FDCNumberHelper.add(sumAmts[1], contractChangeAmounts.get(cbColls.get(j).getId().toString()));
				}
			}
			tblContractEntries.getRow(i).getCell("conAmount").setValue(sumAmts[0]);
			tblContractEntries.getRow(i).getCell("conChangeAmt").setValue(sumAmts[1]);
			if (pcInfo.getAmount().subtract(totalEstimatedSettledAmt).compareTo(FDCNumberHelper.ZERO) > 0) {
				// �С��滮���У�Ԥ�ƴ���������=�滮���
				tblContractEntries.getRow(i).getCell("estimatedHappenAmt").setValue(pcInfo.getAmount().subtract(totalEstimatedSettledAmt));
				// �С��滮���У�Ԥ�ƽ��������=��Լ�滮���
				tblContractEntries.getRow(i).getCell("estimatedSettledAmt").setValue(pcInfo.getAmount());
			} else {
				tblContractEntries.getRow(i).getCell("estimatedSettledAmt").setValue(totalEstimatedSettledAmt);		
			}
			tblContractEntries.getRow(i).getCell("targetDeviationRate").setValue(FDCNumberHelper.ZERO);
			// ���ϻ��ܺϼ��� end
			
			updateStateColumn(tblContractEntries.getRow(i));
		}
	}

	/**
	 * ����ʱ�����ɱ���Ŀ��䵽�����
	 * @author owen_wen 2013-9-17
	 * @throws SQLException 
	 */
	private void fillCostAccountToTableWhenAddNew() throws BOSException, EASBizException, SQLException {
		//���Ŀ��ɱ�����̬�ɱ����ѷ�����δ����������
		Map aimCostMap = getAcctAimCostData(getSelectedCurProjectInfo().getId().toString());
		HappenDataGetter happenGetter = new HappenDataGetter(getSelectedCurProjectInfo().getId().toString(), false, true, false);
		CostAccountCollection costAccounts = getCostAccountsByPrjId(getSelectedCurProjectInfo().getId().toString());
		tblCostAccountEntries.removeRows(false);
		int maxLevel = 0;
		for (int i = 0; i < costAccounts.size(); i++) {
			IRow newRow = tblCostAccountEntries.addRow();
			newRow.getCell("caNumber").setValue(costAccounts.get(i).getLongNumber().replace("!", "."));
			newRow.getCell("caName").setValue(costAccounts.get(i).getName());
			newRow.getCell("caId").setValue(costAccounts.get(i).getId());
			if (costAccounts.get(i).getParent() != null) {
				newRow.getCell("caParentId").setValue(costAccounts.get(i).getParent().getId());
			}
			newRow.getCell("aimCostAmt").setValue(aimCostMap.get(costAccounts.get(i).getId().toString()));
			
			fillHappendedConCost(happenGetter, costAccounts, i, newRow);
			
			HappenDataInfo hdInfo = (HappenDataInfo) happenGetter.noTextSplitMap.get(costAccounts.get(i).getId().toString());
			newRow.getCell("happendedNoConCost").setValue(hdInfo != null ? hdInfo.getAmount() : null);
			
			//�����¼�뵥��ͷ�Ĺ�ϵ
			DynamicCostMonitorCAEntriesInfo entryInfo = new DynamicCostMonitorCAEntriesInfo();
			entryInfo.setCostAccount(costAccounts.get(i));
			entryInfo.setParent(editData);
			editData.getCostAccountEntries().add(entryInfo);
			
			newRow.setTreeLevel(costAccounts.get(i).getLevel() - 1);
			maxLevel = Math.max(maxLevel, costAccounts.get(i).getLevel());
			
			if (!costAccounts.get(i).isIsLeaf()) { // ����ϸ��ĿӦ�ò��ܱ༭
				newRow.getStyleAttributes().setLocked(true);
				newRow.getStyleAttributes().setBackground(FDCColorConstants.lockColor);
			}
			newRow.getCell("willHappendCost").setEditor(FDCTableHelper.getCellNumberEditor());
		}
		
		tblCostAccountEntries.getTreeColumn().setDepth(maxLevel);
	}

	/**
	 * ��䡰�ѷ���_��ͬ�ɱ���������
	 * @param happenGetter
	 * @param costAccounts
	 * @param i
	 * @param newRow
	 */
	private void fillHappendedConCost(HappenDataGetter happenGetter, CostAccountCollection costAccounts, int i, IRow newRow) {
		HappenDataInfo happenDataInfo = (HappenDataInfo) happenGetter.conSplitMap.get(costAccounts.get(i).getId().toString() + "has" + 0);
		BigDecimal conSplitAmount = null;
		if (happenDataInfo != null) {
			conSplitAmount = happenDataInfo.getAmount();
		}
		happenDataInfo = (HappenDataInfo) happenGetter.changeSplitMap.get(costAccounts.get(i).getId().toString() + 0);
		BigDecimal changeAmount = null;
		if (happenDataInfo != null) {
			changeAmount = happenDataInfo.getAmount();
		}
		
		BigDecimal settleAmount = null;
		happenDataInfo = (HappenDataInfo) happenGetter.settleSplitMap.get(costAccounts.get(i).getId().toString());
		if (happenDataInfo != null) {
			settleAmount = happenDataInfo.getAmount();
		}
		newRow.getCell("happendedConCost").setValue(FDCNumberHelper.add(FDCNumberHelper.add(conSplitAmount, changeAmount), settleAmount));
	}
	
	/**
	 * ����pi�����ĺ�ͬ��䵽��̬�ɱ���ط�¼����, �Ѿ����ڳɱ����ʱʹ��
	 */
	private void fillData4TblContractEntriesWhenExist() throws EASBizException, BOSException {
		// ȡ��ͬ������
		Map contractChangeAmounts = new HashMap();
		if (editData.getContractEntries().size() > 0) {
			String[] contractIds = new String[editData.getContractEntries().size()];
			for (int i = 0; i < editData.getContractEntries().size(); i++) {
				contractIds[i] = editData.getContractEntries().get(i).getContractbill().getId().toString();
			}
			contractChangeAmounts = ContractFacadeFactory.getRemoteInstance().getChangeAmt(contractIds);
		}
		
		// ����ͬ����,��������������ݣ�addRowʱ��������������
		for (int i = tblContractEntries.getRowCount() - 1; i >= 0; i--) {
			ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) tblContractEntries.getRow(i).getUserObject();
			for (int j = 0; j < editData.getContractEntries().size()
					&& pcInfo.getId().equals(editData.getContractEntries().get(j).getContractbill().getProgrammingContract().getId()); j++) {
				IRow newRow = tblContractEntries.addRow();
				newRow.setUserObject(editData.getContractEntries().get(j));
				newRow.getCell("conNumber").setValue(editData.getContractEntries().get(j).getContractbill().getNumber());
				newRow.getCell("conName").setValue(editData.getContractEntries().get(j).getContractbill().getName());
				newRow.getCell("conAmount").setValue(editData.getContractEntries().get(j).getContractbill().getAmount());
				newRow.getCell("conChangeAmt").setValue(
						contractChangeAmounts.get(editData.getContractEntries().get(j).getContractbill().getId().toString())); 
				newRow.getCell("isSettled").setValue(editData.getContractEntries().get(j).getContractbill().isHasSettled());
				
				newRow.getCell("estimatedHappenAmt").setValue(editData.getContractEntries().get(j).getEnxpectedToHappenAmt());
				newRow.getCell("reason").setValue(editData.getContractEntries().get(j).getReason());
			}
		}
	}
	
    /**
	 * ����ѡ�еĹ�����Ŀ
	 * @return ������Ŀinfo
	 */
    private CurProjectInfo getSelectedCurProjectInfo() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) projectTree.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CurProjectInfo) {
			return (CurProjectInfo) node.getUserObject();
		} else {//ѡ �зǹ�����Ŀ��㣨����֯�ڵ㣩, ����null������ҵ������������
			return null;
		}
	}

	/**
	 * ���ù�����Ŀ���Ƿ��к�Լ���
	 * @param prjId ������Ŀ��ID
	 */
	private boolean checkIsExistProgramming(String prjId) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", prjId));
		filter.getFilterItems().add(new FilterItemInfo("isLatest", 1));
		return ProgrammingFactory.getRemoteInstance().exists(filter);
	}

	/**
	 * ������Ŀ��ID��ȡ���µĺ�Լ���
	 * <br><b>ע�⣺</b>�ȵ���checkIsExistProgramming(String prjId)�ٵ��ô˷������ܱ�֤ȡ��Ψһ��¼
	 * @param prjId ������Ŀ��ID
	 * @return ProgrammingInfo ��Լ���
	 */
	private ProgrammingInfo getLatestProgrammingWithPrjId(String prjId) throws EASBizException, BOSException {
		if (pi != null && pi.getProject().getId().toString().equals(prjId))
			return pi;
		
		refatorProgrammingContractIsLeafField(prjId); 
		pi = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(
				"where project.id = '" + prjId + "' and isLatest = 1 order by entries.longNumber");
		return pi;
	}

	/**
	 * ���������Լ�滮T_CON_ProgrammingContract�ϵ�isLeaf�ֶ�
	 * @throws BOSException
	 */
	private void refatorProgrammingContractIsLeafField(String prjId) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("update T_CON_ProgrammingContract set FIsLeaf = 1 "); //Ҷ��
		builder.appendSql("where not exists (select 1 from T_CON_ProgrammingContract t where t.fparentid = T_CON_ProgrammingContract.fid) ");
		builder.executeUpdate();

		builder.clear();
		builder.appendSql("update T_CON_ProgrammingContract set FIsLeaf = 0 "); //��Ҷ��
		builder.appendSql("where exists (select 1 from T_CON_ProgrammingContract t where t.fparentid = T_CON_ProgrammingContract.fid) ");
		builder.executeUpdate();
	}

	/**
	 * ������̬�ɱ����ʱ��ʵʱ��ȡ��������Լ��ܵ����к�ͬ����
	 * @param pi ��Լ���
	 * @return ��ͬ����
	 * @throws BOSException 
	 */
	private ContractBillCollection getLatestContractBillCollsForAddNew(ProgrammingInfo pi) throws BOSException {
		String programmingId = pi.getId().toString();
		
		// �Ƕ�������Ĳ����ͬ��Ӧ��ʾ�ڶ�̬�ɱ���ر��� ���Ϲ���������mainContractNumber is null
		// mainContractNumber is null -- �ǲ����ͬ��������ͬ
		// mainContractNumber is not null and FIsAmtWithoutCost =1  -- �����ͬ�����������
		// ContractBillCollection cols = ContractBillFactory
		// .getRemoteInstance()
		// .getContractBillCollection(
		// " where programmingContract.programming.id = '"
		// + programmingId
		// + "' and (mainContractNumber is null or (mainContractNumber is not null and IsAmtWithoutCost =1))");

		// R141009-0042��R140926-0157: 1�������ͬ���Ƕ������㣬������̬�ɱ� 2�������ͬ���������㣬����̬�ɱ� by skyiter_wang 2014-12-18

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("*");
		selector.add("programmingContract.id");
		selector.add("programmingContract.programming.id");

		String sql = " SELECT DISTINCT bill.FID FROM T_CON_ContractBillEntry entry "
				+ " INNER JOIN T_CON_ContractBill bill ON bill.FID = entry.FParentID "
				+ " INNER JOIN T_CON_ProgrammingContract proCon ON proCon.FID = bill.FProgrammingContract "
				+ " WHERE proCon.FProgrammingID = '" + programmingId + "' " + " AND (bill.FMainContractNumber IS NULL "
				+ " OR (bill.FMainContractNumber IS NOT NULL AND entry.FDetail = '�Ƿ񵥶�����' AND entry.FContent = '��')) ";

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("id", sql, CompareType.INNER));
		filterItems.add(new FilterItemInfo("programmingContract.programming.id", programmingId));

		ContractBillCollection cols = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);

		return cols;
	}

	/**
	 * ����ͬ�Ƿ��Ѳ�֣���δ��֣�������ʾҪ�Ȳ��
	 * @param conId ��ͬID
	 */
	private boolean isContractBillIsSplited(String conId) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill", conId));
		return ContractCostSplitFactory.getRemoteInstance().exists(filter);
	}

	/**
	 * ��ͬ��¼�����޸�
	 */
	protected void tblContractEntries_editStopped(KDTEditEvent e) throws Exception {
		// ��Ԥ�ƴ�������  �����ı�
		if (e.getColIndex() == tblContractEntries.getColumn("estimatedHappenAmt").getColumnIndex()) {
			IRow row = tblContractEntries.getRow(e.getRowIndex());
			
			if (row.getCell("conId").getValue() != null && !isContractBillIsSplited(row.getCell("conId").getValue().toString())) {
				FDCMsgBox.showConfirm2(this, "��ͬδ��֣����Ƚ��в�֡�");
				tblContractEntries.getRow(e.getRowIndex()).getCell("estimatedHappenAmt").setValue(e.getOldValue());
				this.abort();
			}
			
			for (int i = 0; i < editData.getContractEntries().size(); i++) {
				if (editData.getContractEntries().get(i).getContractbill().getId().equals(row.getCell("conId").getValue())) {
					editData.getContractEntries().get(i).setEnxpectedToHappenAmt(toBigDecimal(e.getValue()));
				}
			}
			
			// ����������Ԫ��Ҫ�Զ����
			BigDecimal diffAmount = subtract(e.getValue(), e.getOldValue());//������
			row.getCell("estimatedSettledAmt").setValue(FDCNumberHelper.add(row.getCell("estimatedSettledAmt").getValue(), diffAmount));
			// �����ҡ��滮�������㡰��ͬ����Ԫ��
			for (int i = e.getRowIndex() + 1; i < tblContractEntries.getRowCount(); i++) {
				if (tblContractEntries.getRow(i).getCell("conNumber").getValue() == null) {//�ﵽ��һ����Լ�滮��
					break;
				}
				if (ProgrammeBalance.equals(tblContractEntries.getRow(i).getCell("conNumber").getValue())) { // �ҵ�����Լ�滮�µġ��滮����
					tblContractEntries.getRow(i).getCell("conAmount").setValue(
							subtract(tblContractEntries.getRow(i).getCell("conAmount").getValue(), diffAmount));
					break;
				}
			}
			// �����Һ�Լ�滮�У����㡰Ԥ�ƴ��������͡�Ԥ�ƽ����
			for (int i = e.getRowIndex() - 1; i >= 0; i--) {
				if (tblContractEntries.getRow(i).getCell("conNumber").getValue() == null) {//�ҵ�����Լ�滮��
					tblContractEntries.getRow(i).getCell("estimatedHappenAmt").setValue(
							FDCNumberHelper.add(tblContractEntries.getRow(i).getCell("estimatedHappenAmt").getValue(), diffAmount));
					tblContractEntries.getRow(i).getCell("estimatedSettledAmt").setValue(
							FDCNumberHelper.add(tblContractEntries.getRow(i).getCell("estimatedSettledAmt").getValue(), diffAmount));
					tblContractEntries.getRow(i).getCell("targetDeviationRate").setValue( //Ŀ��ƫ����
							multiply(divide(subtract(tblContractEntries.getRow(i).getCell(
									"estimatedSettledAmt").getValue(), tblContractEntries.getRow(i).getCell("pgAmount").getValue()),
									tblContractEntries.getRow(i).getCell("estimatedSettledAmt").getValue()), FDCNumberHelper.ONE_HUNDRED)
									+ "%");
					updateStateColumn(tblContractEntries.getRow(i));
					break;
				}
			}
			
			allocateAmountToCostAccount(subtract(e.getValue(), e.getOldValue()), e.getRowIndex());
			calcTotalAmountForTblCostAccount();
		} else if (e.getColIndex() == tblContractEntries.getColumn("reason").getColumnIndex()) {//ԭ�����
			IRow row = tblContractEntries.getRow(e.getRowIndex());
			for (int i = 0; i < editData.getContractEntries().size(); i++) {
				if (editData.getContractEntries().get(i).getContractbill().getId().toString().equals(row.getCell("conId").getValue())) {
					editData.getContractEntries().get(i).setReason((String) e.getValue());
				}
			}
		}
	}

	/**
	 * ������̬�ɱ���ء�ҳǩ�еĽ����䵽���ɱ���Ŀ��ҳǩ��ȥ��ֻҪ��Ԥ�ƴ���������0ʱ��Ҫ���� <p>
	 * �������
	 * <li> "�滮���"�У�����Լ�滮����ϸ�ɱ���Ŀ�еı������䵽�ɱ���Ŀ��
	 * <li> ��ͬ�У�����ͬ��ֱ������������䵽�������ɱ���Ŀ��
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void allocateAmountToCostAccount(BigDecimal diffAmount, int rowIndex) throws EASBizException, BOSException {
		Map allocateAmountMap = getAllocatedAmountMap(diffAmount, rowIndex);
		//���³ɱ���Ŀҳǩ�е�����
		for (int i = 0; i < tblCostAccountEntries.getRowCount(); i++) {
			IRow currentRow = tblCostAccountEntries.getRow(i);
			String costAccountId = currentRow.getCell("caId").getValue().toString();
			if (allocateAmountMap.get(costAccountId) != null) {
				BigDecimal oldWillHappendCost = toBigDecimal(currentRow.getCell("willHappendCost").getValue());
				currentRow.getCell("willHappendCost").setValue(FDCNumberHelper.add(oldWillHappendCost, allocateAmountMap.get(costAccountId)));
			}
			calculateRelationColumnValue(currentRow);
		}
	}

	/**
	 * ����ɱ���Ŀҳǩ������е�ֵ
	 * @param currentRow
	 */
	private void calculateRelationColumnValue(IRow currentRow) {
		//  ��̬�ɱ� e = b+c+d
		BigDecimal dynamicCost = FDCNumberHelper.add(FDCNumberHelper.add(currentRow.getCell("happendedConCost").getValue(), currentRow
				.getCell("happendedNoConCost").getValue()), currentRow.getCell("willHappendCost").getValue());
		currentRow.getCell("dynamicCost").setValue(dynamicCost);
		// ���� = e-a
		currentRow.getCell("diff").setValue(subtract(dynamicCost, currentRow.getCell("aimCostAmt").getValue()));
	}
	
	/**
	 * ����ʱ�������滮�����ϵġ�Ԥ�ƴ����������䵽�ɱ���Ŀҳǩ��
	 */
	private void allocateAmountToCostAccountWhenAddNew() throws EASBizException, BOSException {
		for (int i = 0; i < tblContractEntries.getRowCount(); i++) {
			IRow row = tblContractEntries.getRow(i);
			if (row.getCell("conNumber").getValue() != null && ProgrammeBalance.equals(row.getCell("conNumber").getValue().toString())) {
				BigDecimal diffAmount = toBigDecimal(row.getCell("estimatedHappenAmt").getValue());
				allocateAmountToCostAccount(diffAmount, i);
			}
		}
	}

	/**
	 * �õ������䵽�ɱ���Ŀҳǩ�ϵĽ��Map
	 * @param diffAmount ����ֵ
	 * @param rowIndex ��һ��
	 */
	private Map getAllocatedAmountMap(BigDecimal diffAmount, int rowIndex) throws EASBizException, BOSException {
		IRow row = tblContractEntries.getRow(rowIndex);
		Map allocateAmountMap = new HashMap(); // <costAccountId, diffAmount>

		if (row.getCell("conNumber").getValue() != null) {
			if (ProgrammeBalance.equals(row.getCell("conNumber").getValue().toString())) {//�滮�����
				String pcId = "";
				for (int i = rowIndex - 1; i >= 0; i--) {
					if (tblContractEntries.getRow(i).getCell("pcId").getValue() != null) { // �ҵ���Ӧ�ĺ�Լ�滮��
						pcId = tblContractEntries.getRow(i).getCell("pcId").getValue().toString();
						break;
					}
				}
				allocatePCAmountToCostAccount(allocateAmountMap, diffAmount, pcId);
			} else { //��ͬ��
				String conId = row.getCell("conId").getValue().toString();
				allocateConAmountToCostAccount(allocateAmountMap, diffAmount, conId);
			}
		}
		return allocateAmountMap;
	}

	/**
	 * �����ͬ���ϵ�Ԥ�ƴ������ɱ����ɱ���Ŀҳǩ��
	 * @param conId ��ͬID
	 * @throws BOSException 
	 */
	private void allocateConAmountToCostAccount(Map allocateAmountMap, BigDecimal diffAmount, String conId) throws BOSException {
		if (diffAmount == null) {
			return;
		}
		ContractCostSplitEntryCollection entryColls =
		ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection("where parent.contractBill.id = '" + conId + "'");
		for (int i = 0; i < entryColls.size(); i++) {
			String costAccountId = entryColls.get(i).getCostAccount().getId().toString();
			if (entryColls.get(i).getSplitScale() != null) {
				allocateAmountMap.put(costAccountId, diffAmount.multiply(entryColls.get(i).getSplitScale()).divide(FDCNumberHelper.ONE_HUNDRED));
			}
		}
	}

	/**
	 * �����Լ�滮���ɱ���Ŀҳǩ�ϣ�ֻ��Ҫ����ֵ���¹�ȥ���������
	 * @param diffEstimatedHappenAmt �ı�Ľ���ֵ
	 */
	private void allocatePCAmountToCostAccount(Map allocateAmountMap, BigDecimal diffEstimatedHappenAmt, String pcId)
			throws EASBizException, BOSException {
		ProgrammingInfo pi = getLatestProgrammingWithPrjId(getSelectedCurProjectInfo().getId().toString());
		for (int i = 0; i < pi.getEntries().size(); i++) {
			ProgrammingContractInfo pc = pi.getEntries().get(i);
			if (pc.getId().toString().equals(pcId)) {
				pc.getAmount();
				for (int j = 0; j < pc.getCostEntries().size(); j++) {
					String costAccountId = pc.getCostEntries().get(j).getCostAccount().getId().toString();
					//�ȳ��ٳ˿��ܻ��о��ȶ�ʧ�����⣬����Ҫָ��һ���ϴ�ľ���scale������Ϊ�����Ͳ��������⣬scale����Ϊ4����
					BigDecimal thisAmount = diffEstimatedHappenAmt.multiply(pc.getCostEntries().get(j).getContractAssign()).divide(pc.getAmount(), 4,
							BigDecimal.ROUND_HALF_UP);
					allocateAmountMap.put(costAccountId, thisAmount);
				}
			}
		}
	}
	
	private void updateStateColumn(IRow row) {
		if (FDCNumberHelper.compareTo(row.getCell("pgAmount").getValue(), row.getCell("estimatedSettledAmt").getValue()) == 0) {
			row.getCell("state").setValue(yellow_ball);
		} else if (FDCNumberHelper.compareTo(row.getCell("pgAmount").getValue(), row.getCell("estimatedSettledAmt").getValue()) < 0) {
			row.getCell("state").setValue(red_ball);
		} else {
			row.getCell("state").setValue(green_ball);
		}
	}
	
	protected void tblCostAccountEntries_editStopped(KDTEditEvent e) throws Exception {
		IRow row = tblCostAccountEntries.getRow(e.getRowIndex());
		for(int i = 0; i<editData.getCostAccountEntries().size();i++){
			if (editData.getCostAccountEntries().get(i).getCostAccount().getId().equals(row.getCell("caId").getValue())) {
				editData.getCostAccountEntries().get(i).setEnxpectedToHappenAmt(
						toBigDecimal(e.getValue()));
				break;
			}
		}
		
		calculateRelationColumnValue(row);
		calcTotalAmountForTblCostAccount();
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getCurProject() == null) {
			FDCMsgBox.showInfo(this, "����ѡ��һ����ϸ������Ŀ��");
			abort();
		}
		
		loadTableContentToEditData();
		DynamicCostMonitorFactory.getRemoteInstance().save(editData);
	}

	/**
	 * ����������ݷŵ�editData��
	 */
	private void loadTableContentToEditData() {
		ByteArrayOutputStream outCA = new ByteArrayOutputStream();
		tblCostAccountEntries.getIOManager().save(outCA);
		editData.setCaEntryTblContent(outCA.toByteArray());
		
		ByteArrayOutputStream outCon = new ByteArrayOutputStream();
		tblContractEntries.getIOManager().save(outCon);
		editData.setConEntryTblContent(outCon.toByteArray());
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		loadTableContentToEditData();
		editData.setState(FDCBillStateEnum.SUBMITTED);
		super.actionSubmit_actionPerformed(e);
	}
	
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("curProject.id");
		sic.add("curProject.name");
		sic.add("curProject.number");
		
		sic.add("createTime");
		sic.add("state");
		
		sic.add("contractEntries.enxpectedToHappenAmt");
		sic.add("contractEntries.reason");
		sic.add("contractEntries.id");
		sic.add("contractEntries.contractbill.id");
		
		sic.add("costAccountEntries.id");
		sic.add("costAccountEntries.parent");
		sic.add("costAccountEntries.enxpectedToHappenAmt");
		sic.add("costAccountEntries.costAccount.id");
		sic.add("costAccountEntries.costAccount.number");
		sic.add("costAccountEntries.costAccount.name");
		sic.add("costAccountEntries.costAccount.longNumber");
		return sic;
	}
	
	private CostAccountCollection getCostAccountsByPrjId(String prjId) throws BOSException {
		return CostAccountFactory.getRemoteInstance().getCostAccountCollection(
				"where isEnabled =1 and curProject.id = '" + prjId + "' order by longNumber");
	}

	/**
	 * ˫������ͬ���ơ��򡰺�ͬ���롱Ҫ�򿪺�ִͬ����Ϣ
	 */
	protected void tblContractEntries_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2
				&& (e.getColIndex() == tblContractEntries.getColumnIndex("conNumber") || e.getColIndex() == tblContractEntries
						.getColumnIndex("conName"))) {
			IRow row = tblContractEntries.getRow(e.getRowIndex());
			if (row.getCell("conId").getValue() != null) {
				ContractFullInfoUI.showDialogWindows(this, row.getCell("conId").getValue().toString());
			}
		}
	}
	
	private void initImageIcon() {
		this.yellow_ball = new ImageIcon(getClass().getClassLoader().getResource("com/kingdee/eas/fdc/aimcost/image/ball_circle_yellow.png"));
		this.red_ball = new ImageIcon(getClass().getClassLoader().getResource("com/kingdee/eas/fdc/aimcost/image/ball_circle_red.png"));
		this.green_ball = new ImageIcon(getClass().getClassLoader().getResource("com/kingdee/eas/fdc/aimcost/image/ball_circle_green.png"));
	}
	
	/**
	 * ��ʼ��״̬�ļ���ͼ����ʾ
	 */
	private void initStatusIcon() {
		ball_red.setIcon(red_ball); // "��Ŀ��ɱ�"
		ball_yellow.setIcon(yellow_ball); // "����Ŀ��ɱ�"
		ball_green.setIcon(green_ball); // "С��Ŀ��ɱ�"
	}
	
	/**
	 * ����Ҫ��������ܡ�
	 * ��д�˷�����Ĭ�Ϸ�����tblContractEntries��tblContractEntries����onShow()�б��Զ�����������
	 */
	protected KDTable getDetailTable() {
		return null;		
	}

	/**
	 * ���ɱ���Ŀ��ȡĿ��ɱ�����, �ο�FDCCostRptFacadeControllerBean.getAcctAimCostData(Context ctx, String objectId)
	 * @param projectid ������ĿID
	 * @return 
	 */
	private Map getAcctAimCostData(String projectid) throws EASBizException, BOSException, SQLException {
		Map aimCostMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AIMCOSTAUDIT)) {
			builder.appendSql("select entry.fcostaccountid as acctid,sum(entry.fcostamount) as amount from T_AIM_AimCost head "
					+ "	inner join T_AIM_CostEntry entry on entry.fheadid=head.fid "
					+ "	where head.fisLastVersion=1 and  head.ForgOrProId=? " + "	and head.fstate=?" + "	group by entry.fcostaccountid ");
			builder.addParam(projectid);
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		} else {
			builder.appendSql("select entry.fcostaccountid as acctid,sum(entry.fcostamount) as amount from T_AIM_AimCost head "
					+ "	inner join T_AIM_CostEntry entry on entry.fheadid=head.fid "
					+ "	where head.fisLastVersion=1 and  head.ForgOrProId=? " + "	group by entry.fcostaccountid ");
			builder.addParam(projectid);
		}

		final IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			aimCostMap.put(rowSet.getString("acctid"), rowSet.getBigDecimal("amount"));
		}
		return aimCostMap;
	}
}