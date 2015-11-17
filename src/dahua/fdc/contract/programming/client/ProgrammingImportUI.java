/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcEntityViewUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMapUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
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
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewInfo;
import com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanTemplateCollection;
import com.kingdee.eas.fdc.finance.PayPlanTemplateFactory;
import com.kingdee.eas.fdc.finance.PayPlanTemplateInfo;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��Լ���ģ�� ����
 */
public class ProgrammingImportUI extends AbstractProgrammingImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingImportUI.class);
    //����Ǵ󻪼���ģ�壬��Ҫ��ģ��ID�����Լ�滮�У������ٺ�Լ�滮�ж����ߵĳɱ����ɽ��жԱȣ��������ͬ���Լ�����еĺ�Լ���ͺ�ɫ������ʾ
    private boolean isGroupTemp = false;
    
	public ProgrammingImportUI() throws Exception {
		super();
	}
	
	private ProgrammingInfo programming;
	private AimCostInfo aimCost;
	private CurProjectInfo project;
	private String programmingTemplateId; // ��Լ���ģ���ID

	public ProgrammingImportUI(ProgrammingInfo programming, AimCostInfo aimCost) throws Exception {
		super();
		this.programming = programming;
		this.aimCost = aimCost;
		this.project = programming.getProject();
		this.programmingTemplateId = (String) programming.get("programmingTemplateId");
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
    	
    	tblMain.getColumn("isEnabled").getStyleAttributes().setHided(true);
    	tblMain.getColumn("description").getStyleAttributes().setHided(true);
    	
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
		CurProjectInfo project = (CurProjectInfo) uiContext.get("project"); // ��ܺ�Լ�Ĺ����Ĺ�����Ŀ
		if (programming == null || project == null) {
			return;
		}
		AimCostInfo aimCost = (AimCostInfo) uiContext.get("aimCost"); // Ŀ��ɱ�
		if (aimCost == null) {
			aimCost = ProgrammingContractUtil.getLastVersionAimCostInfo(project.getId().toString());
		}
		programming.setAimCost(aimCost);

		String selectedKeyValue = getSelectedKeyValue();
		String tempName = (String)tblMain.getCell(KDTableUtil.getSelectedRow(tblMain),"name").getValue();
		if("�󻪼���ģ��".equals(tempName))
			isGroupTemp = true;
		executeImportByTemplate(programming, aimCost, project, selectedKeyValue);
		MsgBox.showInfo(this, "����ɹ�");
		disposeUIWindow();
	}

	private void executeImportByTemplate(ProgrammingInfo programming, AimCostInfo aimCost, CurProjectInfo project, String sltKeyID) throws BOSException, EASBizException {
		if(project==null){
			project = programming.getProject();
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent", sltKeyID));
		
		EntityViewInfo evi = new EntityViewInfo();
		evi.setSelector(getTemplateEntrySelector());
		evi.setFilter(filter);
		
		ProgrammingTemplateEntireCollection templateEntireCollection = 
			ProgrammingTemplateEntireFactory.getRemoteInstance().getProgrammingTemplateEntireCollection(evi);
		
		templateImport(programming, templateEntireCollection, project, aimCost);
	}
	
	private void templateImport(ProgrammingInfo programming, ProgrammingTemplateEntireCollection templateEntryCollection,
			CurProjectInfo project, AimCostInfo aimCost) throws EASBizException, BOSException {
		//ȡ�ú�Լ��ܸ�������Map(��������)
		Map boIdBoAttchAssoMap = getBoIdBoAttchAssoMap(templateEntryCollection);
		Map longNumberCostAccountMap = getLongNumberCostAccountMap(project);
		Map idCostAccountAmtMap = ProgrammingContractUtil.getGoalCostByAimCost(aimCost);
		Map returnMap = getPayPlanTempMap(project,templateEntryCollection);
		Map payPlanTempMap = (Map) returnMap.get("payPlanTem");
//		Map tempTaskMap = (Map) returnMap.get("tempTaskMap");
		
		ProgrammingContractCollection entries = programming.getEntries();
		entries.clear();
		ProgrammingTemplateEntireInfo templateEntry = null;
		ProgrammingContractInfo programmingEntry = null;
		for (int i = 0, size = templateEntryCollection.size(); i < size; i++) {
			templateEntry = templateEntryCollection.get(i);
			programmingEntry = new ProgrammingContractInfo();
			initEntryAttribute(templateEntry, programmingEntry);
			// copyAttachment(templateEntry, programmingEntry);
			//��ʼ������
			initAttachment(programmingEntry, templateEntry, boIdBoAttchAssoMap);
			entryImport(programmingEntry, templateEntry, project, aimCost, longNumberCostAccountMap, idCostAccountAmtMap);
			initEntryOtherAmout(programmingEntry);
			entries.add(programmingEntry);
			programmingEntry.setBalance(programmingEntry.getAmount());
			programmingEntry.setContractContUI(templateEntry.getContractContUI());
			programmingEntry.setAttachWork(templateEntry.getAttachWork());
			programmingEntry.setAttContract(templateEntry.getAttContract());
			programmingEntry.setWorkContent(templateEntry.getScope());	// modified by zhaoqin on 2013/11/08
			if(isGroupTemp){
				programmingEntry.setSimpleName(templateEntry.getId().toString());
			}
			// modify by yxl 20150914 ��ʱ�����븶��滮ҳǩ
			payPlanImport(programming, templateEntry, programmingEntry, project,payPlanTempMap,null);
		}

		//������Ӹ�������
		batchAddBoAttchAsso(boIdBoAttchAssoMap);

		setEntryParent(entries);
		programming.put("programmingTemplateId", getSelectedKeyValue() == null ? programmingTemplateId : getSelectedKeyValue());
	}
	
	
	
	private Map getPayPlanTempMap(CurProjectInfo project, ProgrammingTemplateEntireCollection templateEntryCollection) throws BOSException {
		
		Map returnMap = new HashMap();
		Set tempSet = new HashSet();
//		Set tempTask = new HashSet();
//		Map tempTaskMap = new HashMap();
		Map payPlanTem = new HashMap();
		for(int i=0;i<templateEntryCollection.size();i++){
			tempSet.add(templateEntryCollection.get(i).getId().toString());
		}
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programmingTemplate.id", tempSet,CompareType.INCLUDE));
		evi.setFilter(filter);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("BySchedule.*");
		sic.add("BySchedule.paymentType.number");
		sic.add("BySchedule.paymentType.name");
		evi.setSelector(sic);

		PayPlanTemplateCollection tColl = PayPlanTemplateFactory.getRemoteInstance().getPayPlanTemplateCollection(evi);
		for(int i=0;i<tColl.size();i++){
			PayPlanTemplateInfo payPlanTemplateInfo = tColl.get(i);
			payPlanTem.put(payPlanTemplateInfo.getProgrammingTemplate().getId().toString(), payPlanTemplateInfo);
			
//			for(int j=0;j<payPlanTemplateInfo.getBySchedule().size();j++){
//				RESchTemplateTaskInfo templateTask = payPlanTemplateInfo.getBySchedule().get(j).getTemplateTask();
//				if(templateTask!=null){
//					tempTask.add(templateTask.getId().toString());
//				}
//			}
		}
		returnMap.put("payPlanTem", payPlanTem);
		
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo f = new FilterInfo();
//		f.getFilterItems().add(new FilterItemInfo("schTemplateTask", tempTask,CompareType.INCLUDE));
//		f.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
//		f.getFilterItems().add(new FilterItemInfo("schedule.project.id", project.getId().toString()));
//		view.setFilter(f);
		// yxl 20150811
//		FDCScheduleTaskCollection col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
//		for(int i=0;i<col.size();i++){
//			if(col.get(i).getSchTemplateTask()!=null){
//				tempTaskMap.put(col.get(i).getSchTemplateTask().getId().toString(), col.get(i));
//			}
//		}
		
//		returnMap.put("tempTaskMap", tempTaskMap);
		
		return returnMap;
	}
	
	/**
	 * ����������滮����
	 * @param programming
	 * @param programmingEntry
	 * @param tempTaskMap 
	 * @throws BOSException 
	 */
	private void payPlanImport(ProgrammingInfo programming, ProgrammingTemplateEntireInfo templateEntry,
			ProgrammingContractInfo programmingEntry, CurProjectInfo project,Map payPlanTem, Map tempTaskMap) throws BOSException {

			PayPlanTemplateInfo tInfo = (PayPlanTemplateInfo) payPlanTem.get(templateEntry.getId().toString());
			if(tInfo==null){
				return;
			}

			PayPlanNewInfo pInfo = new PayPlanNewInfo();

			pInfo.putAll(tInfo);
			pInfo.setId(null);

//			pInfo.put("ByMonth", new PayPlanNewByMonthCollection());
			pInfo.put("BySchedule", new PayPlanNewByScheduleCollection());

			for (int i = 0; i < tInfo.getBySchedule().size(); i++) {
				PayPlanTemplateByScheduleInfo tsInfo = tInfo.getBySchedule().get(i);
				PayPlanNewByScheduleInfo psInfo = new PayPlanNewByScheduleInfo();
				psInfo.putAll(tsInfo);
				psInfo.setId(null);
				psInfo.setSrcID(tsInfo.getId());
				
//				RESchTemplateTaskInfo templateTask = tsInfo.getTemplateTask();
//				if (templateTask != null) {
////					EntityViewInfo view = new EntityViewInfo();
////					FilterInfo f = new FilterInfo();
////					f.getFilterItems().add(new FilterItemInfo("schTemplateTask", templateTask.getId().toString()));
////					f.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
////					f.getFilterItems().add(new FilterItemInfo("schedule.project.id", project.getId().toString()));
////					view.setFilter(f);
//					FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) tempTaskMap.get(templateTask.getId().toString());
//					if(info!=null){
//						Date beginDate = null;
//						Date endDate = null;
//						psInfo.put("Task", new PayPlanNewByScheduleTaskCollection());
//						PayPlanNewByScheduleTaskInfo item = new PayPlanNewByScheduleTaskInfo();
//						FDCScheduleTaskInfo taskInfo = info;
//						item.setTask(taskInfo);
//						psInfo.getTask().add(item);
//						
//						beginDate = taskInfo.getStart();
//						endDate = taskInfo.getEnd();
//						
//						
//						psInfo.setBeginDate(beginDate);
//						psInfo.setEndDate(endDate);
//					}
//				}
				pInfo.getBySchedule().add(psInfo);
			}

			programmingEntry.put("PayPlan", pInfo);


	}
	
	/**
	 * ��ʼ������ĺ�Լ���������
	 */
	private void initEntryOtherAmout(ProgrammingContractInfo info) {
		info.setControlBalance(FDCHelper.ZERO); // �������
		info.setSignUpAmount(FDCHelper.ZERO);	// ǩԼ���
		info.setChangeAmount(FDCHelper.ZERO);	// ������
		info.setSettleAmount(FDCHelper.ZERO);	// ������
	}

	private void initEntryAttribute(ProgrammingTemplateEntireInfo templateEntry,
			ProgrammingContractInfo programmingEntry) {
		//modified by yxl 20150811 ��ģ���еĺ�Լ���ͺͺ�ͬ��Χ�����Լ�滮
		programmingEntry.setContractRange(templateEntry.getContractRange());
		programmingEntry.setHyType(templateEntry.getHyType());
		if(templateEntry.getHyType() != null){
			programmingEntry.setPriceWay(templateEntry.getHyType().getPriceWay());
			programmingEntry.setSendPage(templateEntry.getHyType().getSendContWay());
		}
		programmingEntry.setId(BOSUuid.create(programmingEntry.getBOSType()));
		programmingEntry.setLevel(templateEntry.getLevel());
		programmingEntry.setLongNumber(templateEntry.getLongNumber());
		programmingEntry.setNumber(templateEntry.getNumber());
		programmingEntry.setDisplayName(templateEntry.getDisplayName());
		programmingEntry.setName(templateEntry.getName().trim());
		programmingEntry.setWorkContent(templateEntry.getScope());
		programmingEntry.setSupMaterial(templateEntry.getProblem());
		programmingEntry.setSortNumber(templateEntry.getSortNumber());
		if(templateEntry.getAttachment() != null){
			programmingEntry.setAttachment("���и���");
		}
		programmingEntry.setDescription(templateEntry.getDescription());
		programmingEntry.put("templateInfo", templateEntry);
	}

	// ���÷�¼���Ӽ���ϵ
	private void setEntryParent(ProgrammingContractCollection entries) {
		List parents=new ArrayList();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			for (int j = 0; j < size; j++) {
				ProgrammingContractInfo entryParent = entries.get(j);
				String longNumberParent = entryParent.getLongNumber();
				if (longNumber.startsWith(longNumberParent)) {
					int count = longNumber.split("\\.").length;
					int countParent = longNumberParent.split("\\.").length;
					if (count - countParent == 1) {
						entry.setParent(entryParent);
						parents.add(entryParent);
						break;
					}
				}
			}
		}
		for(int i=0;i<parents.size();i++){
			ProgrammingContractInfo parent=(ProgrammingContractInfo)parents.get(i);
			parent.setAmount(FDCHelper.ZERO);
			parent.setBalance(FDCHelper.ZERO);
		}
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			for (int j = 0; j < size; j++) {
				ProgrammingContractInfo entryParent = entries.get(j);
				String longNumberParent = entryParent.getLongNumber();
				if (longNumber.startsWith(longNumberParent)) {
					int count = longNumber.split("\\.").length;
					int countParent = longNumberParent.split("\\.").length;
					if (count - countParent == 1) {
						entryParent.setAmount(entryParent.getAmount().add(entry.getAmount()));
						entryParent.setBalance(entryParent.getAmount());
						break;
					}
				}
			}
		}
	}

	/**
	 * ���Ƹ���
	 */
	private void copyAttachment(ProgrammingTemplateEntireInfo proTempInfo, ProgrammingContractInfo proInfo) {
		FDCSQLBuilder sql = new FDCSQLBuilder("select * from T_BAS_BoAttchAsso where FBOID = '" + proTempInfo.getId() + "'");
		BoAttchAssoInfo info = null;
		try {
			IRowSet rs = sql.executeQuery();
			while (rs.next()) {
				info = new BoAttchAssoInfo();
				String attID = rs.getString("FATTACHMENTID");
				if (!StringUtils.isEmpty(attID))
					info.setAttachment(AttachmentFactory.getRemoteInstance().getAttachmentInfo(new ObjectUuidPK(attID)));
				info.setBoID(proInfo.getId().toString());
				info.setAssoBusObjType(proInfo.getBOSType().toString());
				info.setAssoType("ϵͳ���и���");
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

	/**
	 * ������ȡ�ú�Լ��ܸ�������Map
	 * 
	 * @param templateEntryCollection ��Լ��ܷ�¼����
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-9-21
	 */
	private Map getBoIdBoAttchAssoMap(ProgrammingTemplateEntireCollection templateEntryCollection) {
		Map boIdBoAttchAssoMap = new LinkedHashMap();

		if (FdcObjectCollectionUtil.isEmpty(templateEntryCollection)) {
			return boIdBoAttchAssoMap;
		}

		List templateEntryStrIdList = FdcObjectCollectionUtil.parseStringIdList(templateEntryCollection);
		Set templateEntryStrIdSet = new LinkedHashSet(templateEntryStrIdList);

		EntityViewInfo entityView = new EntityViewInfo();

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("boID");
		selector.add("attachment.id");
		selector.add("attachment.simpleName");
		selector.add("attachment.name");
		entityView.setSelector(selector);

		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterCols = filter.getFilterItems();
		filterCols.add(new FilterItemInfo("boID", templateEntryStrIdSet, CompareType.INCLUDE));
		entityView.setFilter(filter);

		logger.info("ProgrammingImportUI.getBoAttchAssoMap(),entityView:" + entityView);
		logger.info("ProgrammingImportUI.getBoAttchAssoMap(),filter:" + filter);

		BoAttchAssoCollection boAttchAssoCollection = null;
		try {
			boAttchAssoCollection = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(entityView);
		} catch (BOSException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}

		if (FdcObjectCollectionUtil.isNotEmpty(boAttchAssoCollection)) {
			boIdBoAttchAssoMap = FdcObjectCollectionUtil.parsePropertyMap(boAttchAssoCollection, "boID");
		}

		return boIdBoAttchAssoMap;
	}

	/**
	 * ��������ʼ������
	 * 
	 * @param proInfo ��Լ���
	 * @param proTempInfo ��Լ��ܷ�¼
	 * @param boIdBoAttchAssoMap ��Լ��ܸ�������Map
	 * @Author��skyiter_wang
	 * @CreateTime��2013-9-21
	 */
	private void initAttachment(ProgrammingContractInfo proInfo, ProgrammingTemplateEntireInfo proTempInfo, Map boIdBoAttchAssoMap) {
		String proTempInfoId = proTempInfo.getId().toString();
		List boAttchAssoList = (List) boIdBoAttchAssoMap.get(proTempInfoId);

		if (FdcCollectionUtil.isNotEmpty(boAttchAssoList)) {
			String proContInfoId = proInfo.getId().toString();
			String assoBusObjType = proInfo.getBOSType().toString();

			BoAttchAssoInfo boAttchAssoInfo = null;
			for (int i = 0, size = boAttchAssoList.size(); i < size; i++) {
				boAttchAssoInfo = (BoAttchAssoInfo) boAttchAssoList.get(i);

				boAttchAssoInfo.setId(null);
				boAttchAssoInfo.setBoID(proContInfoId);
				boAttchAssoInfo.setAssoBusObjType(assoBusObjType);
				boAttchAssoInfo.setAssoType("ϵͳ���и���");
			}
		}
	}

	/**
	 * ������������Ӹ�������
	 * @Author��skyiter_wang
	 * @CreateTime��2013-9-21
	 */
	private void batchAddBoAttchAsso(Map boIdBoAttchAssoMap) {
		if (FdcMapUtil.isEmpty(boIdBoAttchAssoMap)) {
			return;
		}
		List allBoAttchAssoList = new ArrayList();

		Set idSet = boIdBoAttchAssoMap.keySet();
		String boID = null;
		List boAttchAssoList = null;
		for (Iterator iterator = idSet.iterator(); iterator.hasNext();) {
			boID = (String) iterator.next();
			boAttchAssoList = (List) boIdBoAttchAssoMap.get(boID);
			allBoAttchAssoList.addAll(boAttchAssoList);
		}

		if (FdcCollectionUtil.isEmpty(allBoAttchAssoList)) {
			return;
		}
		//������Ӹ�������
		try {
			CoreBaseCollection objectCollection = new CoreBaseCollection();
			FdcObjectCollectionUtil.addToObjectCollection(allBoAttchAssoList, objectCollection);

			BoAttchAssoFactory.getRemoteInstance().addnew(objectCollection);
		} catch (BOSException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		} catch (EASBizException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}
	}
	
	private void entryImport(ProgrammingContractInfo programmingEntry, ProgrammingTemplateEntireInfo templateEntry, CurProjectInfo project,
			AimCostInfo aimCost, Map longNumberCostAccountMap, Map idCostAccountAmtMap) throws EASBizException, BOSException {
		BigDecimal planAmountTotal = FDCHelper.ZERO; // ���гɱ����󱾺�Լ����ɹ滮���
		// ģ���гɱ�����
		PTECostCollection pteCostCollection = templateEntry.getPteCost();
		// ��Լ�гɱ�����
		ProgrammingContracCostCollection costEntries = programmingEntry.getCostEntries();
		for (int i = 0, size = pteCostCollection.size(); i < size; i++) {
			PTECostInfo pteCostInfo = pteCostCollection.get(i);
			// ģ�嵼�������ĳɱ�����
			ProgrammingContracCostCollection programmingCollection = costEntryImport(pteCostInfo, project, aimCost,
					longNumberCostAccountMap, idCostAccountAmtMap);

			// �����ģ���еĳɱ���Ŀ���ظ�
			// ֱ����ӵ���¼��
			for (int j = 0, sizeJ = programmingCollection.size(); j < sizeJ; j++) {
				ProgrammingContracCostInfo info = programmingCollection.get(j);
				planAmountTotal = planAmountTotal.add(info.getContractAssign());
				costEntries.add(programmingCollection.get(j));
			}
		}

		programmingEntry.setAmount(planAmountTotal);
		programmingEntry.setReservedChangeRate(FDCHelper.ZERO);
		programmingEntry.setControlAmount(planAmountTotal);
//		programmingEntry.setControlAmount(FDCHelper.ZERO);
		// ģ���о�������
//		PTEEnonomyCollection pteEnonomyCollection = templateEntry.getPteEnonomy();
//		enonomyImport(programmingEntry, pteEnonomyCollection);
	}
	
	/**
	 * ���뾭������
	 * @param programmingCollection
	 * @param pteEnonomyCollection
	 */
	private void enonomyImport(ProgrammingContractInfo programmingEntry, 
			PTEEnonomyCollection pteEnonomyCollection) {
		ProgrammingContractEconomyCollection programmingEconomyEntries = programmingEntry.getEconomyEntries();
		programmingEconomyEntries.clear();
		for (int i = 0, size = pteEnonomyCollection.size(); i < size; i ++) {
			ProgrammingContractEconomyInfo economy = new ProgrammingContractEconomyInfo();
			PTEEnonomyInfo pteEnonomy = pteEnonomyCollection.get(i);
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
	 * @param longNumberCostAccountMap
	 * @param idCostAccountAmtMap
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private ProgrammingContracCostCollection costEntryImport(PTECostInfo pteCostInfo, CurProjectInfo project, AimCostInfo aimCost,
			Map longNumberCostAccountMap, Map idCostAccountAmtMap) throws BOSException, EASBizException {
		ProgrammingContracCostCollection retCollection = new ProgrammingContracCostCollection();
		CostAccountInfo pteCostAccount = pteCostInfo.getCostAccount(); // ģ���гɱ���Ŀ
		CostAccountInfo costAccount = changePteCost2CurProject(pteCostAccount, project, longNumberCostAccountMap);
		if (costAccount == null) {
			return retCollection;
		}

		CostAccountCollection proCostCollection = getProCostAccount(costAccount);
		if (aimCost != null) {
			// ָ����Ŀ��ɱ�
			BigDecimal aimCostValue = ProgrammingContractUtil.getGoalCostByCostAcc(costAccount, idCostAccountAmtMap);
			for (int i = 0, size = proCostCollection.size(); i < size; i++) {
				ProgrammingContracCostInfo info = new ProgrammingContracCostInfo();
				info.setCostAccount(costAccount);
				info.setGoalCost(aimCostValue); // Ŀ��ɱ�
				
				// modified by zhaoqin on 2013/10/24, start
				/*
				BigDecimal contractScale = pteCostInfo.getContractScale() == null ? FDCHelper.ZERO : pteCostInfo.getContractScale();
				BigDecimal assignScale = pteCostInfo.getAssignScale() == null ? FDCHelper.ZERO : pteCostInfo.getAssignScale();
				assignScale = assignScale.subtract(contractScale);	// ԭ��������� - ԭ����Լ����, modified by zhaoqin on 2013/10/22
				contractScale = FDCHelper.ONE_HUNDRED.subtract(contractScale);	// 100 - �ѷ���ı���, modified by zhaoqin on 2013/10/22
				info.setContractScale(contractScale); //����Լ����
				contractScale = FDCHelper.divide(contractScale, FDCHelper.ONE_HUNDRED);
				info.setContractAssign(aimCostValue.multiply(contractScale)); // ����Լ����
				assignScale = FDCHelper.divide(assignScale, FDCHelper.ONE_HUNDRED);
				BigDecimal assigning = aimCostValue.multiply(assignScale);
				info.setAssigning(assigning); // ������
				info.setAssigned(aimCostValue.subtract(assigning)); // �ѷ���
				info.setDescription(pteCostAccount.getDescription()); // ��ע
				 */
				BigDecimal allAssignedScale = FDCHelper.ZERO;
				BigDecimal assigning;
				BigDecimal assignScale;
				if(FDCHelper.compareTo(aimCostValue, FDCHelper.ZERO) == 0) {
					assigning = FDCHelper.ZERO;
					assignScale = FDCHelper.ONE_HUNDRED;
				} else {
					allAssignedScale = getAllContractAssignScale(costAccount, project);; 	// �ѷ������
					assignScale = FDCHelper.ONE_HUNDRED.subtract(allAssignedScale);
					
					/* modified by zhaoqin for R131122-0233 on 2013/12/11 */
					if(assignScale.compareTo(FDCHelper.ZERO) < 0)
						assignScale = FDCHelper.ZERO;
					
					assigning = aimCostValue.multiply(assignScale).divide(FDCHelper.ONE_HUNDRED, 8, 4);	// ��ǰ������
				}
				
				BigDecimal contractScale = pteCostInfo.getContractScale() == null ? FDCHelper.ZERO : pteCostInfo.getContractScale();
				if(assignScale.compareTo(contractScale) < 0) {
					info.setContractScale(assignScale); //����Լ����
					info.setContractAssign(assigning); // ����Լ����
					info.setAssigning(assigning); // ������
				} else {
					info.setContractScale(contractScale); //����Լ����
					info.setContractAssign(aimCostValue.multiply(contractScale).divide(FDCHelper.ONE_HUNDRED, 8, 4)); // ����Լ����
					info.setAssigning(assigning); // ������
				}
				info.setAssigned(aimCostValue.multiply(allAssignedScale.add(info.getContractScale()))); // �ѷ���
				info.setDescription(pteCostAccount.getDescription()); // ��ע
				// �����Լ�滮����ʱĿ��ɱ�����汾����
				info.setAimCostId(aimCost.getId().toString());
				// modified by zhaoqin on 2013/10/24, end
				
				retCollection.add(info);
			}
		} else {
			for (int i = 0, size = proCostCollection.size(); i < size; i++) {
				ProgrammingContracCostInfo info = new ProgrammingContracCostInfo();
				info.setCostAccount(costAccount);
				info.setGoalCost(FDCHelper.ZERO); // Ŀ��ɱ�
				info.setAssigned(FDCHelper.ZERO); // �ѷ���
				info.setAssigning(FDCHelper.ZERO); // ������
				info.setContractAssign(FDCHelper.ZERO); // ����Լ����
				
				//info.setContractScale(FDCHelper.ZERO); // ����Լ����
				info.setContractScale(pteCostInfo.getContractScale()); // ����Լ����
				
				info.setDescription(pteCostAccount.getDescription()); // ��ע
				retCollection.add(info);
			}
		}
		return retCollection;
	}

	/**
	 * ת��ģ���е���֯�ɱ���ĿΪ ������Ŀ�µĳɱ���Ŀ
	 * @param pteCostAccount
	 * @param project
	 * @param longNumberCostAccountMap
	 * @return
	 */
	private CostAccountInfo changePteCost2CurProject(CostAccountInfo pteCostAccount, CurProjectInfo project, Map longNumberCostAccountMap) {
		String longNumber = pteCostAccount.getLongNumber();

		return (CostAccountInfo) longNumberCostAccountMap.get(longNumber);
	}
	
	/**
	 * ���ģ���е�ĩ���ɱ���Ŀ����Ŀ�б�Ϊ�����ɱ���Ŀ
	 * ���ڿ�ܺ�Լ�ĳɱ����ɹ�ϵ���Զ��Ѹø�����Ŀ�滻Ϊ��Ӧ������ĩ���ɱ���Ŀ
	 * @param pteCostAccount ģ��ɱ���Ŀ
	 * @return CostAccountCollection
	 * @throws BOSException
	 */
	private CostAccountCollection getProCostAccount(CostAccountInfo pteCostAccount) throws BOSException {
		CostAccountCollection costCollection = new CostAccountCollection();
		String longNumber = pteCostAccount.getLongNumber();
		
		StringBuffer oql = new StringBuffer();
//		oql.append("select id, number, name, longNumber, curProject.id, curProject.number, curProject.name where longNumber like '").append(longNumber).append("!%'");
//		oql.append(" and fullOrgUnit is null");
		oql.append("select id, number, name, longNumber, curProject.id, curProject.number, curProject.name where curProject.id='").append(
				pteCostAccount.getCurProject().getId()).append("' and longNumber like '").append(longNumber).append("!%'");
		oql.append(" and fullOrgUnit is null");
		costCollection = CostAccountFactory.getRemoteInstance().getCostAccountCollection(oql.toString());
		if (costCollection.isEmpty()) {
			costCollection.add(pteCostAccount);
		}
		return costCollection;
	}
	
	/**
	 * ȡ�ù�����Ŀ�µĳɱ���ĿMap(Key��ų����룬Value��Ŷ���)
	 * 
	 * @param project
	 * @return
	 */
	private Map getLongNumberCostAccountMap(CurProjectInfo project) throws BOSException {
		Map costAccountMap = new LinkedHashMap();

		String projectId = project.getId().toString();

		String[] selectors = { "id", "number", "name", "longNumber", "curProject.id", "curProject.number", "curProject.name" };
		EntityViewInfo view = FdcEntityViewUtil.getView(selectors);

		Map items = new HashMap();
		items.put("curProject.id", projectId);
		items.put("fullOrgUnit", null);
		FilterInfo filter = FdcEntityViewUtil.getFilter(items);
		view.setFilter(filter);

		CostAccountCollection costAccountCollection = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		if (FdcObjectCollectionUtil.isNotEmpty(costAccountCollection)) {
			//���ݳ����룬�������󼯺ϵ�Ψһ������ӳ�� 
			costAccountMap = FdcObjectCollectionUtil.parseUniquePropertyMap(costAccountCollection, "longNumber");
		}

		return costAccountMap;
	}
	
	private SelectorItemCollection getTemplateEntrySelector() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("pteCost.costAccount");
		sic.add("pteCost.costAccount.id");
		sic.add("pteCost.costAccount.number");
		sic.add("pteCost.costAccount.name");
		sic.add("pteCost.costAccount.longNumber");
		sic.add("pteCost.assignScale");
		sic.add("pteCost.contractScale");
//		sic.add("pteEnonomy.scale");
//		sic.add("pteEnonomy.condition");
//		sic.add("pteEnonomy.description");
//		sic.add("pteEnonomy.parent");
//		sic.add("pteEnonomy.paymentType");
//		sic.add("pteEnonomy.paymentType.name");
//		sic.add("pteEnonomy.paymentType.number");
		sic.add("name");
		sic.add("number");
		sic.add("description");
		sic.add("id");
		sic.add("level");
		sic.add("longNumber");
		sic.add("attachment");
		sic.add("displayName");
		sic.add("sortNumber");
		sic.add("scope");	// modified by zhaoqin on 2013/11/08
		sic.add("hyType.id");
		sic.add("hyType.hyType");
		sic.add("hyType.sendContWay");
		sic.add("hyType.priceWay");
		sic.add("contractRange");
		sic.add("contractContUI");
		sic.add("attachWork");
		sic.add("attContract");
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
	
	public void computeAim() {
		try {
			executeImportByTemplate(this.programming, this.aimCost, this.project, this.programmingTemplateId);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}
	
	
	/**
	 * ȡ�óɱ���Ŀ��"����Լ����"����֮��
	 * @author zhaoqin
	 * @date 2013/10/25
	 */
	private BigDecimal getAllContractAssignScale(CostAccountInfo caInfo, CurProjectInfo project) throws BOSException {
		String projectId = project.getId().toString();
		String costAccountId = caInfo.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		
		/* modified by zhaoqin for R131212-0339 on 2013/12/16 */
		//builder.appendSql("select sum(cost.fcontractAssign) as assigned,sum(cost.FContractScale) as assignedScale ");
		builder.appendSql("select cost.fcontractAssign as contractAssign,cost.FContractScale as ContractScale ,cost.FGoalCost as GoalCost ");
		
		builder.appendSql("from T_CON_ProgrammingContracCost as cost ");
		builder.appendSql("join T_CON_ProgrammingContract as con on con.fid= cost.FContractID ");
		builder.appendSql("join T_CON_Programming as pro on pro.fid = con.FProgrammingID ");
		builder.appendSql("join (select max(pro.fversion) as fver,pro.fprojectid as proid");
		//builder.appendSql(" from T_CON_ProgrammingContracCost as cost");
		//builder.appendSql(" join T_CON_ProgrammingContract as con on con.fid= cost.FContractID");
		//builder.appendSql(" join T_CON_Programming as pro on pro.fid = con.FProgrammingID");
		builder.appendSql(" from T_CON_Programming as pro ");
		builder.appendSql(" where pro.fprojectid<>'").appendSql(projectId).appendSql("' ");	// ��������ǰ������Ŀ
		//builder.appendSql(" and cost.fcostAccountid='").appendSql(costAccountId).appendSql("' ");
		builder.appendSql(" group by pro.fprojectid");
		builder.appendSql(") t on t.fver=pro.fversion and t.proid = pro.fprojectid ");
		builder.appendSql("where cost.fcostAccountid='").appendSql(costAccountId).appendSql("'");

		//Map results = new HashMap();
		//BigDecimal allContractAssign = FDCHelper.ZERO;
		BigDecimal allContractScale = FDCHelper.ZERO;
		IRowSet rowSet = builder.executeQuery();
		try {
			/* modified by zhaoqin for R131212-0339 on 2013/12/16 start */
			/*if(rowSet.next()) {
				//allContractAssign = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
				allContractScale = FDCHelper.toBigDecimal(rowSet.getBigDecimal("assignedScale"));
			}*/
			while(rowSet.next()) {
				BigDecimal contractAssign = rowSet.getBigDecimal("contractAssign");
				BigDecimal goalCost = rowSet.getBigDecimal("GoalCost");
				allContractScale = allContractScale.add(FDCHelper.multiply(FDCHelper.ONE_HUNDRED, 
						FDCHelper.divide(contractAssign, goalCost, 10, BigDecimal.ROUND_HALF_UP)));
			}
			/* modified by zhaoqin for R131212-0339 on 2013/12/16 end */
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		//results.put("allContractAssign", allContractAssign);
		//results.put("allContractScale", allContractScale);

		return (null == allContractScale ? FDCHelper.ZERO : allContractScale);
	}

}