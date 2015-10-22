/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.programming.PTECostCollection;
import com.kingdee.eas.fdc.contract.programming.PTECostInfo;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByMonthInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fdc.finance.PayPlanTemplateByMonthCollection;
import com.kingdee.eas.fdc.finance.PayPlanTemplateByMonthInfo;
import com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleCollection;
import com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanTemplateInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ProgrammingExportUI extends AbstractProgrammingExportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingExportUI.class);
    private ProgrammingInfo info = null;
    
    /**
     * output class constructor
     */
    public ProgrammingExportUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	Object pro = getUIContext().get("Programming");
    	if(pro != null){
    		info = (ProgrammingInfo)pro;
    	}
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionConfirm_actionPerformed
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    	String name = txtName.getText();
    	if(name == null || name.toString().trim().length() <= 0){
    		throw new ProgrammingException(ProgrammingException.NAMENOTNULL);
    	}
    	if(name.trim().length() > 80){
    		throw new EASBizException(new NumericExceptionSubItem("1","合约框架名称超长！"));
    	}
    	
    	FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name, CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (ProgrammingTemplateFactory.getRemoteInstance().exists(filter)) {
			throw new ProgrammingException(ProgrammingException.CHECKDUPLICATED, new Object[] { name });
		}
    	
    	ProgrammingTemplateInfo template = new ProgrammingTemplateInfo();
    	template.setName(name);
    	template.setIsEnabled(true);
		template.setNumber(getDateString());
		ProgrammingTemplateEntireCollection proTempEntries = template.getEntires();
		ProgrammingContractCollection proEntries = info.getEntries();
		addProgrammingTemp(proTempEntries, proEntries);
		ProgrammingTemplateFactory.getRemoteInstance().save(template);
		MsgBox.showInfo("导出模板成功！");
		this.disposeUIWindow();
    }

	/**
	 * 描述：添加付款计划模板
	 * @param tInfo
	 * @param proTempInfo
	 * @throws BOSException 
	 */
	private void addPayPlanTemplate(ProgrammingTemplateEntireInfo ptInfo, ProgrammingContractInfo proInfo) throws BOSException {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programming.id", proInfo.getId().toString()));
		evi.setFilter(filter);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("BySchedule.*");
//		sic.add("BySchedule.costAccount.number");
//		sic.add("BySchedule.costAccount.name");
//		sic.add("BySchedule.costAccount.longNumber");
		sic.add("BySchedule.paymentType.number");
		sic.add("BySchedule.paymentType.name");
//		sic.add("ByMonth.*");
//		sic.add("ByMonth.costAccount.number");
//		sic.add("ByMonth.costAccount.name");
//		sic.add("ByMonth.costAccount.longNumber");
		evi.setSelector(sic);
		PayPlanNewCollection tColl = PayPlanNewFactory.getRemoteInstance().getPayPlanNewCollection(evi);
		if (tColl != null && tColl.size() > 0) {
			PayPlanNewInfo tInfo = tColl.get(0);
			PayPlanTemplateInfo pInfo = new PayPlanTemplateInfo();
			pInfo.putAll(tInfo);
			pInfo.setId(null);
//			pInfo.put("ByMonth", new PayPlanTemplateByMonthCollection());
			pInfo.put("BySchedule", new PayPlanTemplateByScheduleCollection());

			for (int i = 0; i < tInfo.getBySchedule().size(); i++) {
				PayPlanNewByScheduleInfo tsInfo = tInfo.getBySchedule().get(i);
//				CostAccountInfo pteCostAccount = tsInfo.getCostAccount(); // 模板中成本科目
//				if (pteCostAccount != null) {
//					CostAccountInfo costAccount = getOrgCostAccountInfo(pteCostAccount);
//					if (costAccount != null) {
//						tsInfo.setCostAccount(costAccount);
//					}
//				}
				PayPlanTemplateByScheduleInfo psInfo = new PayPlanTemplateByScheduleInfo();
				psInfo.putAll(tsInfo);
				psInfo.setId(null);
				pInfo.getBySchedule().add(psInfo);
			}
			ptInfo.put("PayPlan", pInfo);
		}
	}

	/**
	 * 添加框架合约模板
	 * @param proTempEntries
	 * @param proEntries
	 * @throws BOSException 
	 */
	private void addProgrammingTemp(ProgrammingTemplateEntireCollection proTempEntries, ProgrammingContractCollection proEntries)
			throws BOSException {
		ProgrammingContractInfo proInfo = null;
		ProgrammingTemplateEntireInfo proTempInfo = null;
		for(int i = 0 ; i < proEntries.size() ; i++){
			proTempInfo = new ProgrammingTemplateEntireInfo();
			proTempInfo.setId(BOSUuid.create(proTempInfo.getBOSType()));
			proInfo = proEntries.get(i);
			if(proInfo.getParent() != null){
				ProgrammingContractInfo headProInfo = proInfo.getParent();
				boolean isHas = false;
				for(int j = 0 ; j < proTempEntries.size() ; j++){
					ProgrammingTemplateEntireInfo tInfo = proTempEntries.get(j);
					if(headProInfo.getLongNumber().equals(tInfo.getLongNumber())){
						proTempInfo.setHead(tInfo);
						isHas = true;
					}
				}
				if(!isHas){
					ProgrammingTemplateEntireInfo parentProTempInfo = addProgrammingTemplateEntireInfo(new ProgrammingTemplateEntireInfo() , headProInfo);
					parentProTempInfo.setId(BOSUuid.create(parentProTempInfo.getBOSType()));
					proTempInfo.setHead(parentProTempInfo);
					proTempEntries.add(parentProTempInfo);
					proTempEntries.add(addProgrammingTemplateEntireInfo(proTempInfo , proInfo));
				}else{
					proTempEntries.add(addProgrammingTemplateEntireInfo(proTempInfo , proInfo));
				}
			}else{
				proTempEntries.add(addProgrammingTemplateEntireInfo(proTempInfo , proInfo));
			}
		}
	}
    
	private ProgrammingTemplateEntireInfo addProgrammingTemplateEntireInfo(ProgrammingTemplateEntireInfo proTempInfo,
			ProgrammingContractInfo proInfo) throws BOSException {
		// modify yxl 20150811
		proTempInfo.setContractRange(proInfo.getContractRange());
		proTempInfo.setHyType(proInfo.getHyType());
		proTempInfo.setLevel(proInfo.getLevel());
		proTempInfo.setSortNumber(proInfo.getSortNumber());
		proTempInfo.setLongNumber(proInfo.getLongNumber());
		proTempInfo.setNumber(proInfo.getNumber());
		proTempInfo.setDisplayName(proInfo.getDisplayName());
		proTempInfo.setName(proInfo.getName().trim());
		proTempInfo.setScope(proInfo.getWorkContent());
		proTempInfo.setProblem(proInfo.getSupMaterial());
		getAttachmentForCopy(proTempInfo , proInfo);
		proTempInfo.setAttachment(proInfo.getAttachment());
		proTempInfo.setDescription(proInfo.getDescription());
		proTempInfo.setContractContUI(proInfo.getContractContUI());
		proTempInfo.setAttachWork(proInfo.getAttachWork());
		proTempInfo.setAttContract(proInfo.getAttContract());
		PTECostCollection pteCostCol = proTempInfo.getPteCost();
		ProgrammingContracCostCollection  proCostCol = proInfo.getCostEntries();
		addCostEntries(proTempInfo, pteCostCol, proCostCol);
		
		
		//添加付款规划模板  modify by yxl 20150915 
//		PTEEnonomyCollection pteEnonomyCol = proTempInfo.getPteEnonomy();
//		ProgrammingContractEconomyCollection economyInfoCol = proInfo.getEconomyEntries();
//		addEnonomyEntries(proTempInfo, pteEnonomyCol, economyInfoCol);
		addPayPlanTemplate(proTempInfo, proInfo);
		return proTempInfo;
	}

	/**
	 * 添加经济条款页签数据
	 * @param proTempInfo
	 * @param pteEnonomyCol
	 * @param economyInfoCol
	 */
	private void addEnonomyEntries(ProgrammingTemplateEntireInfo proTempInfo, PTEEnonomyCollection pteEnonomyCol,
			ProgrammingContractEconomyCollection economyInfoCol) {
		for(int i = 0 ; i < economyInfoCol.size() ; i++){
			ProgrammingContractEconomyInfo economyInfo = economyInfoCol.get(i);
			PTEEnonomyInfo pteEconomyInfo = new PTEEnonomyInfo();
			pteEconomyInfo.setParent(proTempInfo);
			pteEconomyInfo.setPaymentType(economyInfo.getPaymentType());
			pteEconomyInfo.setScale(economyInfo.getScale());
			pteEconomyInfo.setCondition(economyInfo.getCondition());
			pteEconomyInfo.setDescription(economyInfo.getDescription());
			pteEnonomyCol.add(pteEconomyInfo);
		}
	}

	/**
	 * 添加成本构成页签数据
	 * @param proTempInfo
	 * @param pteCostCol
	 * @param proCostCol
	 */
	private void addCostEntries(ProgrammingTemplateEntireInfo proTempInfo, PTECostCollection pteCostCol, ProgrammingContracCostCollection proCostCol) {
		Map map = new HashMap();
		ProgrammingContracCostCollection proConCostCol = (ProgrammingContracCostCollection)proCostCol.clone();
		for(int i = 0 ; i < proConCostCol.size() ; i++){
			ProgrammingContracCostInfo costInfo = proConCostCol.get(i);
			CostAccountInfo oldCost = null;
			try {
				oldCost = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(costInfo.getCostAccount().getId().toString()));
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
			if(oldCost == null)
				continue;
			CostAccountInfo cost = getOrgCostAccountInfo(oldCost);
			if(cost != null){
				PTECostInfo pteCostInfo = createPTECost(proTempInfo, costInfo, cost);
				pteCostCol.add(pteCostInfo);
			}else{
				CostAccountInfo costAccountInfo = getOrgCostAccountInfo(oldCost, null);
				if (map.isEmpty()) {
					map.put(costAccountInfo.getId().toString(), costInfo);
				} else {
					if (map.containsKey(costAccountInfo.getId().toString())) {
						ProgrammingContracCostInfo proCon = (ProgrammingContracCostInfo) map.get(costAccountInfo.getId().toString());
						proCon.setContractAssign(proCon.getContractAssign().add(costInfo.getContractAssign()));// 本合约分配
						proCon.setGoalCost(proCon.getGoalCost().add(costInfo.getGoalCost()));// 目标成本
						proCon.setAssigning(proCon.getAssigning().add(costInfo.getAssigning()));// 待分配
					} else {
						map.put(costAccountInfo.getId().toString(), costInfo);
					}
				}
			}
		}
		
		if (!map.isEmpty()) {
			Set set = map.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				PTECostInfo pteCostInfo = new PTECostInfo();
				pteCostInfo.setParent(proTempInfo);
				String id = it.next().toString();
				try {
					CostAccountInfo orgCost = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(id));
					ProgrammingContracCostInfo proConCostInfo = (ProgrammingContracCostInfo)map.get(id);
					pteCostCol.add(createPTECost(proTempInfo, proConCostInfo, orgCost));
				} catch (EASBizException e) {
					logger.error(e);
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					logger.error(e);
					handUIExceptionAndAbort(e);
				}
			}
		}
	}

	private PTECostInfo createPTECost(ProgrammingTemplateEntireInfo proTempInfo, ProgrammingContracCostInfo costInfo, CostAccountInfo cost) {
		PTECostInfo pteCostInfo = new PTECostInfo();
		pteCostInfo.setCostAccount(cost);
		pteCostInfo.setParent(proTempInfo);
		pteCostInfo.setDescription(costInfo.getDescription());
		BigDecimal contractAssign = costInfo.getContractAssign();// 本合约分配
		BigDecimal goalCost = costInfo.getGoalCost();// 目标成本
		BigDecimal assigning = costInfo.getAssigning();//待分配
		if(goalCost != null && goalCost.compareTo(FDCHelper.ZERO) > 0){
			if (contractAssign != null) {
				BigDecimal percent = contractAssign.divide(goalCost, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				if(percent.intValue() <= 0)
					pteCostInfo.setContractScale(FDCHelper.ZERO);
				else
					pteCostInfo.setContractScale(percent);
			}
			if(goalCost != null){
				BigDecimal percent = assigning.divide(goalCost, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				if(percent.intValue() <= 0)
					pteCostInfo.setAssignScale(FDCHelper.ZERO);
				else
					pteCostInfo.setAssignScale(percent);
			}
		}
		return pteCostInfo;
	}
	
	private CostAccountInfo getOrgCostAccountInfo(CostAccountInfo info , CostAccountInfo orgCost){
		if(orgCost == null){
			if(info.getParent() != null){
				CostAccountInfo costInfo = null;
				try {
					costInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(info.getParent().getId()));
					orgCost = getOrgCostAccountInfo(costInfo);
				} catch (EASBizException e) {
					logger.error(e);
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					logger.error(e);
					handUIExceptionAndAbort(e);
				}
				if(orgCost == null)
					return getOrgCostAccountInfo(costInfo , null);
			}
		}
		return orgCost;
	}
	
	private CostAccountInfo getOrgCostAccountInfo(CostAccountInfo cost){
		String longNumber = cost.getLongNumber();
		StringBuffer oql = new StringBuffer();
		
		oql.append("select id, number, name, longNumber where longnumber = '").append(longNumber).append("'");
		oql.append(" and curProject is null");
		oql.append(" and fullOrgUnit = '").append(OrgConstants.DEF_CU_ID).append("'");
		try {
			return CostAccountFactory.getRemoteInstance().getCostAccountInfo(oql.toString());
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return null;
	}

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	this.disposeUIWindow();
    }
    
    private final String attIDColumn = "FATTACHMENTID";
    /**
     * 复制附件
     */
    private void getAttachmentForCopy(ProgrammingTemplateEntireInfo proTempInfo, ProgrammingContractInfo proInfo){
    	FDCSQLBuilder sql = new FDCSQLBuilder("select * from T_BAS_BoAttchAsso where FBOID = '"+proInfo.getId()+"'");
    	BoAttchAssoInfo info = null;
    	try {
			IRowSet rs = sql.executeQuery();
			while(rs.next()){
				info = new BoAttchAssoInfo();
				String attID = rs.getString(attIDColumn);
				if (!FDCHelper.isEmpty(attID))
					info.setAttachment(AttachmentFactory.getRemoteInstance().getAttachmentInfo(new ObjectUuidPK(attID)));
				info.setBoID(proTempInfo.getId().toString());
				info.setAssoBusObjType(proTempInfo.getBOSType().toString());
				info.setAssoType("系统已有附件");
				BoAttchAssoFactory.getRemoteInstance().addnew(info);
			}
		} catch (BOSException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		} catch (EASBizException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}
    	
    }
    
    private String getDateString(){
    	Calendar cal = Calendar.getInstance();
    	Timestamp ts   =  new Timestamp(cal.getTimeInMillis());
    	Date bizDate = new Date(ts.getTime());
    	return bizDate.toString();
    }
}