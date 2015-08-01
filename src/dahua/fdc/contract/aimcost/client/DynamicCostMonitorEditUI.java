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
 * 动态成本监控 编辑界面
 * @author owen_wen 
 */
public class DynamicCostMonitorEditUI extends AbstractDynamicCostMonitorEditUI
{
	private static final String ProgrammeBalance = "规划余额";
	/**
	 * 合同ID
	 */
	private static final String CON_ID = "conId";
	private static final Logger logger = CoreUIObject.getLogger(DynamicCostMonitorEditUI.class);
	
	private ImageIcon yellow_ball;
	private ImageIcon red_ball;
	private ImageIcon green_ball;
	
	private ProgrammingInfo pi;
	

	// 获取有权限的组织
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
		
		//禁用delete键，因为delete后不会触发editStop事件
		tblContractEntries.getActionMap().remove(KDTAction.DELETE);
		tblCostAccountEntries.getActionMap().remove(KDTAction.DELETE);
	}
    
    /**
	 * 初始化工具栏中的“单位”按钮
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
		//默认选中第一个单位：元
		KDMenuItem assitButtonYuan = (KDMenuItem) btnSwitchUnit.getAssitButton(0);
		assitButtonYuan.setIcon(EASResource.getIcon("imgTbtn_affirm"));
	}
    
    /**
	* 初始化工程项目树
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
		
		//0. 预判断，选中的项目下有没有做合约规划，若没有规划给个提示，树节点切回原来节点上
    	if (!checkIsExistProgramming(prjInfo.getId().toString())) {
			FDCMsgBox.showWarning(this, "选中的工程项目还没有做合约规划，暂不能做动态成本监控业务。");
			projectTree.setSelectionPath(e.getOldLeadSelectionPath()); //TODO 会再次触发一次value_change事件，待改进
			this.abort();
		}
    	
    	//1. 先判断选中的工程项目下有没有做过动态监控，若有取最新的一条显示
    	DynamicCostMonitorCollection colls = getLatestDynamicCostMonitorWithPrjId(prjInfo.getId().toString());
		if (colls.size() == 1) {
			setOprtState(OprtState.VIEW);
			setDataObject(colls.getObject(0));
			loadFields();
			tblContractEntries.getIOManager().load(new ByteArrayInputStream(editData.getConEntryTblContent()));
			tblCostAccountEntries.getIOManager().load(new ByteArrayInputStream(editData.getCaEntryTblContent()));			
		} else {
		//2. 若选中的工程项目下没有，则新增一条供用户编辑
			createNewDataForEdit(prjInfo);
		}

		calcTotalAmountForTblCostAccount();
	}
    
    /**
	 * tblCostAccountEntries表格逐级往上汇总金额
	 */
	private void calcTotalAmountForTblCostAccount() {
		FDCTableHelper.calcTotalAmount(tblCostAccountEntries, 0, new String[] { "aimCostAmt", "willHappendCost", "dynamicCost", "diff" }, "caId",
				"caParentId");
	}

	/**
	 * 新增一条“动态成本监控”供编辑使用
	 * @param prjInfo 工程项目
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
			FDCMsgBox.showConfirm2(this, "请选中工程项目节点再新增。");
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
	 *  将合约框架中的合约规划数据填充到动态成本监控分录表中
	 * @param pi 合约框架
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
		tblContractEntries.getTreeColumn().setDepth(maxLevel + 1);//因为后面还要加合同行和规划余额行，所以级次还要加1
	}

	/**
	 * 将与pi关联的合同填充到动态成本监控分录表中, 新增成本监控时使用
	 * @param pi 该工程项目下的合约框架
	 */
	private void fillDataToTblContractEntriesWhenAddNew(ProgrammingInfo pi) throws BOSException, EASBizException {
		ContractBillCollection cbColls = getLatestContractBillCollsForAddNew(pi);
		
		// 取合同变更金额
		Map contractChangeAmounts = new HashMap();
		if (cbColls.size() > 0) {
			String[] contractIds = new String[cbColls.size()];
			for (int i = 0; i < cbColls.size(); i++) {
				contractIds[i] = cbColls.get(i).getId().toString();
			}
			contractChangeAmounts = ContractFacadeFactory.getRemoteInstance().getChangeAmt(contractIds);
		}
		
		// 填充合同数据,从下往上填充数据，addRow时方便行索引处理
		for (int i = tblContractEntries.getRowCount() - 1; i >= 0; i--) {
			ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) tblContractEntries.getRow(i).getUserObject();
			
			BigDecimal totalEstimatedSettledAmt = FDCNumberHelper.ZERO; // 该合约规划下的累计“预计结算金额”
			int hasInsertRowCount = 0;//统计本合约规划下插入了多少行合同
			
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
				// 已结算的合同，“预计待发生金额”不可编辑,已结算的合同不允许填写“预计待发生金额”。
				newRow.getCell("estimatedHappenAmt").getStyleAttributes().setLocked(cbColls.get(j).isHasSettled());
				newRow.getCell("estimatedHappenAmt").getStyleAttributes()
						.setBackground(cbColls.get(j).isHasSettled() ? FDCColorConstants.cantEditColor : null);
				newRow.getCell("estimatedHappenAmt").setEditor(FDCTableHelper.getCellNumberEditor());
				
				newRow.getCell(CON_ID).setValue(cbColls.get(j).getId());
				// 再计算并填充相关列的值，E=B+C+D
				if (!cbColls.get(j).isHasSettled()) {//未结算
					newRow.getCell("estimatedSettledAmt").setValue(
							FDCNumberHelper.add(FDCNumberHelper
									.add(newRow.getCell("conAmount").getValue(), newRow.getCell("conChangeAmt").getValue()), newRow.getCell(
									"estimatedHappenAmt").getValue()));
				} else {//已结算
					newRow.getCell("estimatedSettledAmt").setValue(cbColls.get(j).getSettleAmt());
				}
				
				totalEstimatedSettledAmt = FDCNumberHelper.add(totalEstimatedSettledAmt, newRow.getCell("estimatedSettledAmt").getValue());
				DynamicCostMonitorContractEntryInfo ceInfo = new DynamicCostMonitorContractEntryInfo();
				ceInfo.setParent(editData);
				editData.getContractEntries().add(ceInfo);
				ceInfo.setContractbill(cbColls.get(j));
			}
			
			// 如果满足条件，则添加“规划余额”行 
			if (pcInfo.isIsLeaf() // 明细行，才有规划余额
					&& pcInfo.getAmount().subtract(totalEstimatedSettledAmt).compareTo(FDCNumberHelper.ZERO) > 0) {
				IRow newRow = tblContractEntries.addRow(i + hasInsertRowCount + 1); // 增加“未签定合同”行
				newRow.setTreeLevel(pcInfo.getLevel());
				newRow.getCell("conNumber").setValue(ProgrammeBalance);
				newRow.getCell("conAmount").setValue(pcInfo.getAmount().subtract(totalEstimatedSettledAmt));
				newRow.getCell("estimatedHappenAmt").setValue(pcInfo.getAmount().subtract(totalEstimatedSettledAmt));
				newRow.getCell("estimatedSettledAmt").setValue(pcInfo.getAmount().subtract(totalEstimatedSettledAmt));
			}
			
			// 往上汇总合计列 start：目前只汇总明细合约规划 
			BigDecimal[] sumAmts = new BigDecimal[2]; //0 合同金额，1 变更金额，2预计待发生金额，3预计结算金额
			for (int j = 0; j < cbColls.size(); j++) {
				if (pcInfo.getId().equals(cbColls.get(j).getProgrammingContract().getId())) {
					sumAmts[0] = FDCNumberHelper.add(sumAmts[0], cbColls.get(j).getAmount());
					sumAmts[1] = FDCNumberHelper.add(sumAmts[1], contractChangeAmounts.get(cbColls.get(j).getId().toString()));
				}
			}
			tblContractEntries.getRow(i).getCell("conAmount").setValue(sumAmts[0]);
			tblContractEntries.getRow(i).getCell("conChangeAmt").setValue(sumAmts[1]);
			if (pcInfo.getAmount().subtract(totalEstimatedSettledAmt).compareTo(FDCNumberHelper.ZERO) > 0) {
				// 有“规划余额”行，预计待发生汇总=规划余额
				tblContractEntries.getRow(i).getCell("estimatedHappenAmt").setValue(pcInfo.getAmount().subtract(totalEstimatedSettledAmt));
				// 有“规划余额”行，预计结算金额汇总=合约规划金额
				tblContractEntries.getRow(i).getCell("estimatedSettledAmt").setValue(pcInfo.getAmount());
			} else {
				tblContractEntries.getRow(i).getCell("estimatedSettledAmt").setValue(totalEstimatedSettledAmt);		
			}
			tblContractEntries.getRow(i).getCell("targetDeviationRate").setValue(FDCNumberHelper.ZERO);
			// 往上汇总合计列 end
			
			updateStateColumn(tblContractEntries.getRow(i));
		}
	}

	/**
	 * 新增时，将成本科目填充到表格中
	 * @author owen_wen 2013-9-17
	 * @throws SQLException 
	 */
	private void fillCostAccountToTableWhenAddNew() throws BOSException, EASBizException, SQLException {
		//获得目标成本、动态成本、已发生、未发生等数据
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
			
			//处理分录与单据头的关系
			DynamicCostMonitorCAEntriesInfo entryInfo = new DynamicCostMonitorCAEntriesInfo();
			entryInfo.setCostAccount(costAccounts.get(i));
			entryInfo.setParent(editData);
			editData.getCostAccountEntries().add(entryInfo);
			
			newRow.setTreeLevel(costAccounts.get(i).getLevel() - 1);
			maxLevel = Math.max(maxLevel, costAccounts.get(i).getLevel());
			
			if (!costAccounts.get(i).isIsLeaf()) { // 非明细科目应该不能编辑
				newRow.getStyleAttributes().setLocked(true);
				newRow.getStyleAttributes().setBackground(FDCColorConstants.lockColor);
			}
			newRow.getCell("willHappendCost").setEditor(FDCTableHelper.getCellNumberEditor());
		}
		
		tblCostAccountEntries.getTreeColumn().setDepth(maxLevel);
	}

	/**
	 * 填充“已发生_合同成本”列数据
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
	 * 将与pi关联的合同填充到动态成本监控分录表中, 已经存在成本监控时使用
	 */
	private void fillData4TblContractEntriesWhenExist() throws EASBizException, BOSException {
		// 取合同变更金额
		Map contractChangeAmounts = new HashMap();
		if (editData.getContractEntries().size() > 0) {
			String[] contractIds = new String[editData.getContractEntries().size()];
			for (int i = 0; i < editData.getContractEntries().size(); i++) {
				contractIds[i] = editData.getContractEntries().get(i).getContractbill().getId().toString();
			}
			contractChangeAmounts = ContractFacadeFactory.getRemoteInstance().getChangeAmt(contractIds);
		}
		
		// 填充合同数据,从下往上填充数据，addRow时方便行索引处理
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
	 * 返回选中的工程项目
	 * @return 工程项目info
	 */
    private CurProjectInfo getSelectedCurProjectInfo() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) projectTree.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CurProjectInfo) {
			return (CurProjectInfo) node.getUserObject();
		} else {//选 中非工程项目结点（即组织节点）, 返回null，后续业务来继续处理。
			return null;
		}
	}

	/**
	 * 检查该工程项目下是否有合约框架
	 * @param prjId 工程项目的ID
	 */
	private boolean checkIsExistProgramming(String prjId) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", prjId));
		filter.getFilterItems().add(new FilterItemInfo("isLatest", 1));
		return ProgrammingFactory.getRemoteInstance().exists(filter);
	}

	/**
	 * 根据项目的ID获取最新的合约框架
	 * <br><b>注意：</b>先调用checkIsExistProgramming(String prjId)再调用此方法才能保证取到唯一记录
	 * @param prjId 工程项目的ID
	 * @return ProgrammingInfo 合约框架
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
	 * 重新整理合约规划T_CON_ProgrammingContract上的isLeaf字段
	 * @throws BOSException
	 */
	private void refatorProgrammingContractIsLeafField(String prjId) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("update T_CON_ProgrammingContract set FIsLeaf = 1 "); //叶子
		builder.appendSql("where not exists (select 1 from T_CON_ProgrammingContract t where t.fparentid = T_CON_ProgrammingContract.fid) ");
		builder.executeUpdate();

		builder.clear();
		builder.appendSql("update T_CON_ProgrammingContract set FIsLeaf = 0 "); //非叶子
		builder.appendSql("where exists (select 1 from T_CON_ProgrammingContract t where t.fparentid = T_CON_ProgrammingContract.fid) ");
		builder.executeUpdate();
	}

	/**
	 * 新增动态成本监控时，实时获取关联到合约框架的所有合同单据
	 * @param pi 合约框架
	 * @return 合同单据
	 * @throws BOSException 
	 */
	private ContractBillCollection getLatestContractBillCollsForAddNew(ProgrammingInfo pi) throws BOSException {
		String programmingId = pi.getId().toString();
		
		// 非独立核算的补充合同不应显示在动态成本监控表里 加上过滤条件：mainContractNumber is null
		// mainContractNumber is null -- 非补充合同，即主合同
		// mainContractNumber is not null and FIsAmtWithoutCost =1  -- 补充合同，独立核算的
		// ContractBillCollection cols = ContractBillFactory
		// .getRemoteInstance()
		// .getContractBillCollection(
		// " where programmingContract.programming.id = '"
		// + programmingId
		// + "' and (mainContractNumber is null or (mainContractNumber is not null and IsAmtWithoutCost =1))");

		// R141009-0042，R140926-0157: 1、补充合同，非独立核算，不进动态成本 2、补充合同，独立核算，进动态成本 by skyiter_wang 2014-12-18

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
				+ " OR (bill.FMainContractNumber IS NOT NULL AND entry.FDetail = '是否单独计算' AND entry.FContent = '是')) ";

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("id", sql, CompareType.INNER));
		filterItems.add(new FilterItemInfo("programmingContract.programming.id", programmingId));

		ContractBillCollection cols = ContractBillFactory.getRemoteInstance().getContractBillCollection(view);

		return cols;
	}

	/**
	 * 检查合同是否已拆分，若未拆分，给出提示要先拆分
	 * @param conId 合同ID
	 */
	private boolean isContractBillIsSplited(String conId) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill", conId));
		return ContractCostSplitFactory.getRemoteInstance().exists(filter);
	}

	/**
	 * 合同分录表发生修改
	 */
	protected void tblContractEntries_editStopped(KDTEditEvent e) throws Exception {
		// “预计待发生金额”  发生改变
		if (e.getColIndex() == tblContractEntries.getColumn("estimatedHappenAmt").getColumnIndex()) {
			IRow row = tblContractEntries.getRow(e.getRowIndex());
			
			if (row.getCell("conId").getValue() != null && !isContractBillIsSplited(row.getCell("conId").getValue().toString())) {
				FDCMsgBox.showConfirm2(this, "合同未拆分，请先进行拆分。");
				tblContractEntries.getRow(e.getRowIndex()).getCell("estimatedHappenAmt").setValue(e.getOldValue());
				this.abort();
			}
			
			for (int i = 0; i < editData.getContractEntries().size(); i++) {
				if (editData.getContractEntries().get(i).getContractbill().getId().equals(row.getCell("conId").getValue())) {
					editData.getContractEntries().get(i).setEnxpectedToHappenAmt(toBigDecimal(e.getValue()));
				}
			}
			
			// 其它联动单元格要自动填充
			BigDecimal diffAmount = subtract(e.getValue(), e.getOldValue());//差异金额
			row.getCell("estimatedSettledAmt").setValue(FDCNumberHelper.add(row.getCell("estimatedSettledAmt").getValue(), diffAmount));
			// 往下找“规划余额”，计算“合同金额”单元格
			for (int i = e.getRowIndex() + 1; i < tblContractEntries.getRowCount(); i++) {
				if (tblContractEntries.getRow(i).getCell("conNumber").getValue() == null) {//达到下一个合约规划行
					break;
				}
				if (ProgrammeBalance.equals(tblContractEntries.getRow(i).getCell("conNumber").getValue())) { // 找到本合约规划下的“规划余额”行
					tblContractEntries.getRow(i).getCell("conAmount").setValue(
							subtract(tblContractEntries.getRow(i).getCell("conAmount").getValue(), diffAmount));
					break;
				}
			}
			// 往上找合约规划行，计算“预计待发生金额”和“预计结算金额”
			for (int i = e.getRowIndex() - 1; i >= 0; i--) {
				if (tblContractEntries.getRow(i).getCell("conNumber").getValue() == null) {//找到本合约规划行
					tblContractEntries.getRow(i).getCell("estimatedHappenAmt").setValue(
							FDCNumberHelper.add(tblContractEntries.getRow(i).getCell("estimatedHappenAmt").getValue(), diffAmount));
					tblContractEntries.getRow(i).getCell("estimatedSettledAmt").setValue(
							FDCNumberHelper.add(tblContractEntries.getRow(i).getCell("estimatedSettledAmt").getValue(), diffAmount));
					tblContractEntries.getRow(i).getCell("targetDeviationRate").setValue( //目标偏差率
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
		} else if (e.getColIndex() == tblContractEntries.getColumn("reason").getColumnIndex()) {//原因分析
			IRow row = tblContractEntries.getRow(e.getRowIndex());
			for (int i = 0; i < editData.getContractEntries().size(); i++) {
				if (editData.getContractEntries().get(i).getContractbill().getId().toString().equals(row.getCell("conId").getValue())) {
					editData.getContractEntries().get(i).setReason((String) e.getValue());
				}
			}
		}
	}

	/**
	 * 将“动态成本监控”页签中的金额分配到“成本科目”页签中去，只要“预计待发生金额”非0时就要分配 <p>
	 * 分配规则：
	 * <li> "规划余额"行，按合约规划的详细成本科目中的比例分配到成本科目上
	 * <li> 合同行，按合同拆分比例，将金额分配到待发生成本科目上
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void allocateAmountToCostAccount(BigDecimal diffAmount, int rowIndex) throws EASBizException, BOSException {
		Map allocateAmountMap = getAllocatedAmountMap(diffAmount, rowIndex);
		//更新成本科目页签中的数据
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
	 * 计算成本科目页签上相关列的值
	 * @param currentRow
	 */
	private void calculateRelationColumnValue(IRow currentRow) {
		//  动态成本 e = b+c+d
		BigDecimal dynamicCost = FDCNumberHelper.add(FDCNumberHelper.add(currentRow.getCell("happendedConCost").getValue(), currentRow
				.getCell("happendedNoConCost").getValue()), currentRow.getCell("willHappendCost").getValue());
		currentRow.getCell("dynamicCost").setValue(dynamicCost);
		// 差异 = e-a
		currentRow.getCell("diff").setValue(subtract(dynamicCost, currentRow.getCell("aimCostAmt").getValue()));
	}
	
	/**
	 * 新增时，将“规划余额”行上的“预计待发生”分配到成本科目页签上
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
	 * 得到待分配到成本科目页签上的金额Map
	 * @param diffAmount 金额差值
	 * @param rowIndex 哪一行
	 */
	private Map getAllocatedAmountMap(BigDecimal diffAmount, int rowIndex) throws EASBizException, BOSException {
		IRow row = tblContractEntries.getRow(rowIndex);
		Map allocateAmountMap = new HashMap(); // <costAccountId, diffAmount>

		if (row.getCell("conNumber").getValue() != null) {
			if (ProgrammeBalance.equals(row.getCell("conNumber").getValue().toString())) {//规划余额行
				String pcId = "";
				for (int i = rowIndex - 1; i >= 0; i--) {
					if (tblContractEntries.getRow(i).getCell("pcId").getValue() != null) { // 找到对应的合约规划行
						pcId = tblContractEntries.getRow(i).getCell("pcId").getValue().toString();
						break;
					}
				}
				allocatePCAmountToCostAccount(allocateAmountMap, diffAmount, pcId);
			} else { //合同行
				String conId = row.getCell("conId").getValue().toString();
				allocateConAmountToCostAccount(allocateAmountMap, diffAmount, conId);
			}
		}
		return allocateAmountMap;
	}

	/**
	 * 分配合同行上的预计待发生成本金额到成本科目页签上
	 * @param conId 合同ID
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
	 * 分配合约规划金额到成本科目页签上，只需要将差值更新过去，这样最简单
	 * @param diffEstimatedHappenAmt 改变的金额差值
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
					//先除再乘可能会有精度丢失的问题，必须要指定一个较大的精度scale，但改为最后除就不会有问题，scale给定为4即可
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
			FDCMsgBox.showInfo(this, "请先选中一个明细工程项目。");
			abort();
		}
		
		loadTableContentToEditData();
		DynamicCostMonitorFactory.getRemoteInstance().save(editData);
	}

	/**
	 * 将表格中数据放到editData中
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
	 * 双击“合同名称”或“合同编码”要打开合同执行信息
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
	 * 初始化状态的几个图标显示
	 */
	private void initStatusIcon() {
		ball_red.setIcon(red_ball); // "超目标成本"
		ball_yellow.setIcon(yellow_ball); // "等于目标成本"
		ball_green.setIcon(green_ball); // "小于目标成本"
	}
	
	/**
	 * 不需要表格排序功能。
	 * 重写此方法，默认返回是tblContractEntries，tblContractEntries会在onShow()中被自动置上排序功能
	 */
	protected KDTable getDetailTable() {
		return null;		
	}

	/**
	 * 按成本科目获取目标成本数据, 参考FDCCostRptFacadeControllerBean.getAcctAimCostData(Context ctx, String objectId)
	 * @param projectid 工程项目ID
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