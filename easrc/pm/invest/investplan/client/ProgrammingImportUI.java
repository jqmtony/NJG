/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.investplan.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.port.pm.base.InvestYearFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryEconomyEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryEconomyEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteCostCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteCostInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteEnonomyCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteEnonomyInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateFactory;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
 
/**
 * output class name
 */
public class ProgrammingImportUI extends AbstractProgrammingImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingImportUI.class);
    
	public ProgrammingImportUI() throws Exception {
		super();
	}
	
    public void onLoad() throws Exception {
    	super.onLoad();
    	menuBar.setVisible(false);
		toolBar.setVisible(false);
		actionConfirm.setEnabled(true);
		actionExit.setEnabled(true);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
    	tblMain.getColumn("lastUpdateTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
    }
    
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
		EntityViewInfo view = this.getMainQuery();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled" , new Integer(1) , CompareType.EQUALS));
		view.setFilter(filter);
	}
	
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				executeImport();
			}
		}
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		executeImport();
	}
	
	private void executeImport() throws Exception {
		if (MsgBox.OK != MsgBox.showConfirm2(this, "��ģ�嵼�뽫���ǵ�ǰ���ݣ�����ô��")) {
			return;
		}
		Map uiContext = getUIContext();
		ProgrammingInfo programming = (ProgrammingInfo) uiContext.get("editData"); // ������ĺ�Լ�滮
		DataChangeListener dataChangeListener = (DataChangeListener) uiContext.get("dataChangeListener");
		//����ģ���ʱ��Ĭ����û��Ŀ��ɱ���
		ProgrammingEditUI editUI = (ProgrammingEditUI)uiContext.get("Owner");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent", getSelectedKeyValue()));
		
		EntityViewInfo evi = new EntityViewInfo();
		evi.setSelector(getTemplateEntrySelector());
		evi.setFilter(filter);
		
		ProgrammingTemplateEntireCollection templateEntireCollection = 
		ProgrammingTemplateEntireFactory.getRemoteInstance().getProgrammingTemplateEntireCollection(evi);
		
		templateImport(programming, templateEntireCollection, null);
		MsgBox.showInfo(this, "����ɹ�");
		disposeUIWindow();
	}
	
	private void templateImport(ProgrammingInfo programming, ProgrammingTemplateEntireCollection 
			templateEntryCollection, ProjectInfo project) 
	throws EASBizException, BOSException {
		ProgrammingEntryCollection entries = programming.getEntries();
		entries.clear();
		
		InvestPlanBaseInfo planInfo = (InvestPlanBaseInfo)getUIContext().get("planInfo");
		
		for (int i = 0, size = templateEntryCollection.size(); i < size; i++) {
			ProgrammingTemplateEntireInfo templateEntry = templateEntryCollection.get(i);
			ProgrammingEntryInfo programmingEntry = new ProgrammingEntryInfo();
			initEntryAttribute(templateEntry, programmingEntry);
			copyAttachment(templateEntry, programmingEntry);
			entryImport(programmingEntry, templateEntry, project);
			initEntryOtherAmout(programmingEntry);
			entries.add(programmingEntry);
			if(planInfo!=null)
			{
				ProgrammingEntryCostEntryInfo newCostEntryInfo = new ProgrammingEntryCostEntryInfo();
				newCostEntryInfo.setId(BOSUuid.create(newCostEntryInfo.getBOSType()));
				newCostEntryInfo.setProject(planInfo.getProjectName());
				newCostEntryInfo.setContract(programmingEntry);
				newCostEntryInfo.setNumber(planInfo.getNumber());
				newCostEntryInfo.setInvestYear(InvestYearFactory.getRemoteInstance().getInvestYearInfo(new ObjectUuidPK(planInfo.getYearIdt())));
				newCostEntryInfo.setGoalCost(BigDecimal.ZERO);//Ͷ���ܶ�
				newCostEntryInfo.setAssigned(BigDecimal.ZERO);// �ۼ�Ͷ�ʣ��������꣩
				newCostEntryInfo.setContractAssign(BigDecimal.ZERO);//�����Ͷ�ʽ��
				newCostEntryInfo.setAssigning(BigDecimal.ZERO);//Ͷ�����
				newCostEntryInfo.setProportion(BigDecimal.ZERO);//Ͷ�ʱ���
				programmingEntry.getCostEntries().add(newCostEntryInfo);
				
			}
		}
		
		setEntryParent(entries);
	}
	
	/**
	 * ��ʼ������ĺ�Լ���������
	 */
	private void initEntryOtherAmout(ProgrammingEntryInfo info) {
		info.setControlBalance(FDCHelper.ZERO); // �������
		info.setSignUpAmount(FDCHelper.ZERO);	// ǩԼ���
		info.setChangeAmount(FDCHelper.ZERO);	// ������
		info.setSettleAmount(FDCHelper.ZERO);	// ������
	}

	private void initEntryAttribute(ProgrammingTemplateEntireInfo templateEntry,
			ProgrammingEntryInfo programmingEntry) {
		programmingEntry.setId(BOSUuid.create(programmingEntry.getBOSType()));
		programmingEntry.setLevel(templateEntry.getLevel());
		programmingEntry.setLongNumber(templateEntry.getLongNumber());
		programmingEntry.setNumber(templateEntry.getNumber());
		programmingEntry.setDisplayName(templateEntry.getDisplayName());
		programmingEntry.setName(templateEntry.getName().trim());
		programmingEntry.setWorkContent(templateEntry.getScope());
		programmingEntry.setSupMaterial(templateEntry.getProblem());
		programmingEntry.setSortNumber(templateEntry.getSortNumber());
		programmingEntry.setPrice(BigDecimal.ZERO);//����
		programmingEntry.setQuantities(BigDecimal.ZERO);//����
		programmingEntry.setCumulativeInvest(BigDecimal.ZERO);//�ۼ�Ͷ��
		programmingEntry.setInvestAmount(BigDecimal.ZERO);//����Ͷ��
		programmingEntry.setBalance(BigDecimal.ZERO);//Ͷ�����
		if(templateEntry.getAttachment() != null){
			programmingEntry.setAttachment("���и���");
		}
		programmingEntry.setDescription(templateEntry.getDescription());
		programmingEntry.setContractType(templateEntry.getContractType());
	}

	// ���÷�¼���Ӽ���ϵ
	private void setEntryParent(ProgrammingEntryCollection entries) {
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingEntryInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			for (int j = 0; j < size; j++) {
				ProgrammingEntryInfo entryParent = entries.get(j);
				String longNumberParent = entryParent.getLongNumber();
				if (longNumber.startsWith(longNumberParent)) {
					int count = longNumber.split("\\.").length;
					int countParent = longNumberParent.split("\\.").length;
					if (count - countParent == 1) {
						entry.setParent(entryParent);
						break;
					}
				}
			}
		}
	}
	
	/**
     * ���Ƹ���
     */
    private void copyAttachment(ProgrammingTemplateEntireInfo proTempInfo, ProgrammingEntryInfo proInfo){
    	FDCSQLBuilder sql = new FDCSQLBuilder("select * from T_BAS_BoAttchAsso where FBOID = '"+proTempInfo.getId()+"'");
    	BoAttchAssoInfo info = null;
    	try {
			IRowSet rs = sql.executeQuery();
			while(rs.next()){
				info = new BoAttchAssoInfo();
				String attID = rs.getString("FATTACHMENTID");
				if(!StringUtils.isEmpty(attID))
					info.setAttachment(AttachmentFactory.getRemoteInstance().getAttachmentInfo(new ObjectUuidPK(attID)));
				info.setBoID(proInfo.getId().toString());
				info.setAssoBusObjType(proInfo.getBOSType().toString());
				info.setAssoType("ϵͳ���и���");
				BoAttchAssoFactory.getRemoteInstance().addnew(info);
			}
		} catch (BOSException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		} catch (EASBizException e) {
			logger.error(e);
		}
    	
    }
	
	private void entryImport(ProgrammingEntryInfo programmingEntry, 
			ProgrammingTemplateEntireInfo templateEntry, ProjectInfo project) 
	throws EASBizException, BOSException {
		BigDecimal planAmountTotal = FDCHelper.ZERO; // ���гɱ����󱾺�Լ����ɹ滮���
		// ģ���гɱ�����
		ProgrammingTemplateEntirePteCostCollection pteCostCollection = templateEntry.getPteCost();
		// ��Լ�гɱ�����
		ProgrammingEntryCostEntryCollection costEntries = programmingEntry.getCostEntries();
		for (int i = 0, size = pteCostCollection.size(); i < size; i++) {
			ProgrammingTemplateEntirePteCostInfo pteCostInfo = pteCostCollection.get(i);
			// ģ�嵼�������ĳɱ�����
//			ProgrammingEntryCostEntryCollection programmingCollection = costEntryImport(pteCostInfo, project, aimCost);
			
			// �����ģ���еĳɱ���Ŀ���ظ�
			// ֱ����ӵ���¼��
//			for (int j = 0, sizeJ = programmingCollection.size(); j < sizeJ; j++) {
//				ProgrammingEntryCostEntryInfo info = programmingCollection.get(j);
//				planAmountTotal = planAmountTotal.add(info.getContractAssign());
//				costEntries.add(programmingCollection.get(j));
//			}
		}
		
		programmingEntry.setAmount(planAmountTotal);
		programmingEntry.setControlAmount(FDCHelper.ZERO);
		// ģ���о�������
		ProgrammingTemplateEntirePteEnonomyCollection pteEnonomyCollection = templateEntry.getPteEnonomy();
		enonomyImport(programmingEntry, pteEnonomyCollection);
	}
	
	/**
	 * ���뾭������
	 * @param programmingCollection
	 * @param pteEnonomyCollection
	 */
	private void enonomyImport(ProgrammingEntryInfo programmingEntry, 
			ProgrammingTemplateEntirePteEnonomyCollection pteEnonomyCollection) {
		ProgrammingEntryEconomyEntryCollection programmingEconomyEntries = programmingEntry.getEconomyEntries();
		programmingEconomyEntries.clear();
		for (int i = 0, size = pteEnonomyCollection.size(); i < size; i ++) {
			ProgrammingEntryEconomyEntryInfo economy = new ProgrammingEntryEconomyEntryInfo();
			ProgrammingTemplateEntirePteEnonomyInfo pteEnonomy = pteEnonomyCollection.get(i);
			economy.setScale(pteEnonomy.getScale()); // ������� 
			economy.setCondition(pteEnonomy.getCondition()); // ��������
			economy.setPaymentType(pteEnonomy.getPaymentType()); // ��������
			economy.setDescription(pteEnonomy.getDescription()); // ��ע
			economy.setAmount(pteEnonomy.getScale().multiply(programmingEntry.getAmount())); // ������
			economy.setAmount(FDCHelper.divide(economy.getAmount(), FDCHelper.ONE_HUNDRED)); // ������
			programmingEconomyEntries.add(economy);
		}
	}
	
	/**
	 * ����ɱ�
	 * @param pteCostInfo
	 * @param project
	 * @param aimCost
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
//	private ProgrammingEntryCostEntryCollection costEntryImport(ProgrammingTemplateEntirePteCostInfo pteCostInfo, 
//			ProjectInfo project) 
//	throws BOSException, EASBizException {
//		ProgrammingEntryCostEntryCollection retCollection = new ProgrammingEntryCostEntryCollection();
//		CostAccountInfo pteCostAccount = pteCostInfo.getCostAccount(); // ģ���гɱ���Ŀ
//		CostAccountInfo costAccount = changePteCost2CurProject(pteCostAccount, project);
//		if (costAccount == null) {
//			return retCollection;
//		}
//		
//		CostAccountCollection proCostCollection = getProCostAccount(costAccount);
//		if (aimCost != null) {
//			// ָ����Ŀ��ɱ�
//			BigDecimal aimCostValue = ProgrammingEntryUtil.getGoalCostBy_costAcc_aimCost(
//					costAccount, aimCost);
//			for (int i = 0, size = proCostCollection.size(); i <size; i++) {
//				ProgrammingEntryCostEntryInfo info = new ProgrammingEntryCostEntryInfo();
//				info.setCostAccount(costAccount);
//				info.setGoalCost(aimCostValue); // Ŀ��ɱ�
//				
//				BigDecimal contractScale = pteCostInfo.getContractScale() == null ? FDCHelper.ZERO :
//					pteCostInfo.getContractScale();
//				contractScale = FDCHelper.divide(contractScale, FDCHelper.ONE_HUNDRED);
//				info.setContractAssign(aimCostValue.multiply(contractScale)); // ����Լ����
//				
//				BigDecimal assignScale = pteCostInfo.getAssignScale() == null ? FDCHelper.ZERO :
//					pteCostInfo.getAssignScale();
//				assignScale = FDCHelper.divide(assignScale, FDCHelper.ONE_HUNDRED);
//				BigDecimal assigning = aimCostValue.multiply(assignScale);
//				info.setAssigning(assigning); // ������
//				info.setAssigned(aimCostValue.subtract(assigning)); // �ѷ���
//				info.setDescription(pteCostAccount.getDescription()); // ��ע
//				retCollection.add(info);
//			}
//		} else {
//			for (int i = 0, size = proCostCollection.size(); i <size; i++) {
//				ProgrammingEntryCostEntryInfo info = new ProgrammingEntryCostEntryInfo();
//				info.setCostAccount(costAccount);
//				info.setGoalCost(FDCHelper.ZERO); // Ŀ��ɱ�
//				info.setAssigned(FDCHelper.ZERO); // �ѷ���
//				info.setAssigning(FDCHelper.ZERO); // ������
//				info.setContractAssign(FDCHelper.ZERO); // ����Լ����
//				info.setDescription(pteCostAccount.getDescription()); // ��ע
//				retCollection.add(info);
//			}
//		}
//		return retCollection;
//	}
	
	/**
	 * ת��ģ���е���֯�ɱ���ĿΪ ������Ŀ�µĳɱ���Ŀ
	 * @param pteCostAccount
	 * @param project
	 * @return
	 */
//	private CostAccountInfo changePteCost2CurProject(CostAccountInfo pteCostAccount, ProjectInfo project) {
//		String longNumber = pteCostAccount.getLongNumber();
//		String projectId = project.getId().toString();
//		StringBuffer oql = new StringBuffer();
//		
//		oql.append("select id, number, name, longNumber, curProject.id, curProject.number, curProject.name where longnumber = '").append(longNumber).append("'");
//		oql.append(" and curProject = '").append(projectId).append("'");
//		oql.append(" and fullOrgUnit is null");
//		try {
//			return CostAccountFactory.getRemoteInstance().getCostAccountInfo(oql.toString());
//		} catch (Exception e) {
//		}
//		return null;
//	}
	
	/**
	 * ���ģ���е�ĩ���ɱ���Ŀ����Ŀ�б�Ϊ�����ɱ���Ŀ
	 * ���ڿ�ܺ�Լ�ĳɱ����ɹ�ϵ���Զ��Ѹø�����Ŀ�滻Ϊ��Ӧ������ĩ���ɱ���Ŀ
	 * @param pteCostAccount ģ��ɱ���Ŀ
	 * @return CostAccountCollection
	 * @throws BOSException
	 */
//	private CostAccountCollection getProCostAccount(CostAccountInfo pteCostAccount) throws BOSException {
//		CostAccountCollection costCollection = new CostAccountCollection();
//		String longNumber = pteCostAccount.getLongNumber();
//		
//		StringBuffer oql = new StringBuffer();
//		oql.append("select id, number, name, longNumber, curProject.id, curProject.number, curProject.name where longNumber like '").append(longNumber).append("!%'");
//		oql.append(" and fullOrgUnit is null");
//		costCollection = CostAccountFactory.getRemoteInstance().getCostAccountCollection(oql.toString());
//		if (costCollection.isEmpty()) {
//			costCollection.add(pteCostAccount);
//		}
//		return costCollection;
//	}
	
	private SelectorItemCollection getTemplateEntrySelector() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("pteCost.costAccount");
		sic.add("pteCost.costAccount.id");
		sic.add("pteCost.costAccount.number");
		sic.add("pteCost.costAccount.name");
		sic.add("pteCost.costAccount.longNumber");
		sic.add("pteCost.assignScale");
		sic.add("pteCost.contractScale");
		sic.add("pteEnonomy.scale");
		sic.add("pteEnonomy.condition");
		sic.add("pteEnonomy.description");
		sic.add("pteEnonomy.parent");
		sic.add("pteEnonomy.paymentType");
		sic.add("pteEnonomy.paymentType.name");
		sic.add("pteEnonomy.paymentType.number");
		sic.add("name");
		sic.add("number");
		sic.add("description");
		sic.add("id");
		sic.add("level");
		sic.add("longNumber");
		sic.add("attachment");
		sic.add("displayName");
		sic.add("sortNumber");
		sic.add("contractType.*");
		return sic;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProgrammingTemplateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.programming.client.ProgrammingTemplateEditUI.class.getName();
	}
	
	public void actionExit_actionPerformed(ActionEvent e) throws Exception {
		this.disposeUIWindow();
	}
}