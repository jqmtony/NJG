/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
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
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.BOSUIContext;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcWfUtil;
import com.kingdee.eas.fdc.basedata.util.FullOrgUnitHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.FrameWorkException;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * 描述:合同序时簿界面
 * 
 * @author liupd date:2006-7-25
 *         <p>
 * @version EAS5.1.3
 */
public class ContractBillListUI extends AbstractContractBillListUI {
	
	/**
	 * 本币金额 列
	 */
	private static final String AMOUNT = "amount";

	private static final Logger logger = CoreUIObject.getLogger(ContractBillListUI.class);

	private int viewType = 0;//合同编辑信息
	private Map contentMap = new HashMap();
	private Map attachMap = new HashMap();
	// 再次归档时更新合同编号
	private boolean isUpdateConNo = false;
	// 合同，变更审批后上传附件
    private boolean canUploadForAudited = false;
    private boolean isMultiProject = false;
	/**
	 * 最后审批人，最后审批时间
	 */
	Map auditMap = new HashMap();
	//房地产业务系统正文管理是否启用审批笔迹留痕功能
	boolean isUseWriteMark=false;
	//补充合同可以选择提交状态的主合同
	private boolean isSupply = false;
	
	public ContractBillListUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		this.btnAddContent.setEnabled(false);
		this.menuItemAddContent.setEnabled(false);
		this.actionAddContent.setVisible(false);
		this.btnViewContent.setEnabled(true);
		this.menuItemViewContent.setEnabled(true);
			
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			/*
			 * 如果按主合同编码，会产生树的级次，主合同为根级，其子合同为其子级
			 */
			public void afterDataFill(KDTDataRequestEvent e) {
				boolean isMainOrder = false; // 是否按“主合同编码”排序
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
						//如果有多级审批，如此编码就会导致最后审批人和最后审批时间数据部正确,BUG,by Cassiel_peng
//						if(row.getCell("auditor").getValue()==null){
							row.getCell("auditor").setValue(info.getCreator().getName());
							row.getCell("auditTime").setValue(info.getCreateTime());
//						}
					}
					if(isMainOrder){
						String number = "";
						if(row.getCell("mainContractNumber")!=null){
							if (row.getCell("mainContractNumber").getValue() != null) {
								number = row.getCell("mainContractNumber").getValue().toString();
							}
							if (!number.equals(preNumber)) {
								row.setTreeLevel(0);
							} else if (!"".equals(number)) {
								row.setTreeLevel(1);
								tblMain.getTreeColumn().setDepth(2);
								row.getCell("number").setValue("   " + row.getCell("number").getValue());
							}
							preNumber = number;
						}
					}
					
					Object number = row.getCell("number").getValue();
					Object mainNumber = row.getCell("mainContractNumber").getValue();
					// 补充合同显示
					if (number != null && mainNumber != null && number.toString().equals(mainNumber.toString())) {
						row.getCell("tabShowIsAmtWithoutCost").setValue(null);
					}
				}
			}
		});
		
		super.onLoad();
		actionStore.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_archive"));
		actionAntiStore.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_undistribute"));
		
		kDSplitPane2.add(contContrList, "bottom");
		kDSplitPane2.setDividerLocation(1.0);
		actionConMove.setEnabled(true);
		actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
		//隐藏关联合约框架
		actionProgram.setVisible(false);
	}
	
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		
//		放开，在虚体可以增删改
		actionAddNew.setEnabled(true);
		actionEdit.setEnabled(true);
		actionRemove.setEnabled(true);
		actionAddNew.setVisible(true);
		actionEdit.setVisible(true);
		actionRemove.setVisible(true);
		actionAttachment.setEnabled(true);
		actionAttachment.setVisible(true);
		menuEdit.setVisible(true);
		actionRespite.setVisible(true);
		actionCancelRespite.setVisible(true);
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
        super.refreshListForOrder();
    }

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);	
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex()!= -1) {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
			if((tblMain.getSelectManager().getBlocks().size() > 1) 
				||(e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() >0)){
				actionRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			}else{
				if(Boolean.TRUE.equals(row.getCell("isRespite").getValue())){
					actionRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}else{
					actionRespite.setEnabled(true);
					actionCancelRespite.setEnabled(false);
				}
			}
			if(row.getCell("state").getValue().equals("合计")) return;
			if (row.getCell("state") != null) {		
				if (FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo) row.getCell("state").getValue()).getValue()
						.toString())) {
					if((e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() ==0)&&
							(tblMain.getSelectManager().getBlocks().size()== 1)){
						actionCancelRespite.setEnabled(true);
						actionRespite.setEnabled(false);
					}
					
					if(!((Boolean)row.getCell("isArchived").getValue()).booleanValue()){
						actionStore.setEnabled(true);
						actionAntiStore.setEnabled(false);
					}else{
						actionAntiStore.setEnabled(true);
						actionStore.setEnabled(false);
					}
				}else{
					actionStore.setEnabled(false);
					actionAntiStore.setEnabled(false);
				}
			}
		}
		String selectedKeyValue = getSelectedKeyValue();
		if (null != selectedKeyValue) {
			SelectorItemCollection sic = new SelectorItemCollection();

			sic.add("id");
			sic.add("programmingContract");
			sic.add("state");

			ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(
					new ObjectUuidPK(selectedKeyValue), sic);
			if (contractBillInfo.getState().equals(FDCBillStateEnum.AUDITTED)
					|| contractBillInfo.getState().equals(FDCBillStateEnum.AUDITTING) || isRelateCon(contractBillInfo.getId().toString())) {
				this.btnProgram.setEnabled(false);
			} else {
				this.btnProgram.setEnabled(true);
			}
		}
		// modify end
	}

	private boolean isRelateCon(String conId) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select con.fid conId from t_con_contractbillentry entry");
		builder
				.appendSql(" inner join T_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY'  ");
		builder
				.appendSql(" inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid	 ");
		builder.appendSql("  where entry.FRowkey='am' and");
		builder.appendParam("  con.fid", conId);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			return true;
		}
		return false;
	}

	/**
	 * output actionStore_actionPerformed method
	 */
	public void actionStore_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		HashMap hm = new UIContext(this);
		hm.put("isUpdateConNo", Boolean.valueOf(isUpdateConNo));
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex()== -1) {
			//提示选中
		}else{
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
			
			if(row.getCell("id")!=null){
				hm.put("id", row.getCell("id").getValue().toString());
			}
			if(row.getCell("number")!=null){
				hm.put("number", row.getCell("number").getValue().toString());
			}
			/*
			 * 把当前选中的工程项目和合同类型传给EditUI
			 */
//			BOSUuid projId = ((CurProjectInfo) getProjSelectedTreeNode().getUserObject()).getId();
//			BOSUuid typeId = null;
//			if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
//
//				typeId = ((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject()).getId();
//			}
//			hm.put("projectId", projId);
//			hm.put("contractTypeId", typeId);
			
		}	
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.contract.client.ContractBillStoreUI", hm, null,null);
		uiWindow.show();
		actionRefresh_actionPerformed(e);	
	}
	//审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		isCanOperation();
//		获取用户选择的块
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			if(isRelatedTask()){
				for(int i = 0 ;i<idList.size();i++){
					String idKey = idList.get(i).toString();
					ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(idKey));
					ContractClientUtils.checkConRelatedTaskSubmit(contract);
				}
			}			
			
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
	//合同提交审批时是否必须与计划任务进行关联 2010-08-09
	private boolean isRelatedTask(){
		boolean isRealtedTask = false;
		String cuID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isRealtedTask = FDCUtils.getDefaultFDCParamByKey(null, cuID, FDCConstants.FDC_PARAM_RELATEDTASK);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return isRealtedTask;
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		isCanOperation();
		
		/* modified by zhaoqin for R140109-0016 on 2014/01/15 start */
		//FDCClientHelper.checkAuditor(getSelectedIdValues(), "t_con_ContractBill");
		FDCClientHelper.checkAuditor(getSelectedIdValues(), auditMap);
		/* modified by zhaoqin for R140109-0016 on 2014/01/15 end */
		
		
		//R110603-0148:如果存在变更签证申请，则不允许反审批
	    if (ContractUtil.hasChangeAuditBill(null, getSelectedIdValues())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasChangeAuditBill"));
			this.abort();
    	}
    	
    	//R110603-0148:如果存在变更签证确认，则不允许反审批
    	if (ContractUtil.hasContractChangeBill(null, getSelectedIdValues())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasContractChangeBill"));
			this.abort();
    	}
    	
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionUnAudit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		isCanOperation();
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}

		super.actionRemove_actionPerformed(e);
		// 维护框架合约是否引用字段
	}

	/**
	 * 打回状态的单据不让删除 - add by zhaoqin for R130812-0007 on 2013/08/21
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#getBillStateForRemove()
	 */
	protected String[] getBillStateForRemove() {
		// return new String[] { FDCBillStateEnum.BACK_VALUE, FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE };
		return new String[] { FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE };
	}
	
	/**
	 * 检查合同是否关联框架合约
	 * 
	 * @return
	 * @throws Exception
	 */
	private Set checkReaPre() throws Exception {
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		ContractBillInfo contractBillInfo = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		Set billId = new HashSet();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		builder.clear();
		builder.appendSql("select fprogrammingContract from T_CON_CONTRACTBILL where 1=1 and ");
		builder.appendParam("fid", selectedIdValues.toArray());
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getString("fprogrammingContract") != null) {
				billId.add(rowSet.getString("fprogrammingContract"));
			}
		}
		return billId;
	}

	/**
	 * 更新老框架合约是否被引用
	 * 
	 * @throws Exception
	 */
	private void updateOldProg() throws Exception {
		Set checkReaPre = checkReaPre();
		if (checkReaPre != null && checkReaPre.size() != 0) {
			for (Iterator it = checkReaPre.iterator(); it.hasNext();) {
				String temp = it.next().toString();
				int count = 0;// 关联合约数
				int linkInviteProject = isCitingByInviteProject(temp);// 本合约关联招标立项数
				int linkContractBill = isCitingByContractBill(temp);// 本合约关联合同数
				int linkContractWithoutText = isCitingByContractWithoutText(temp);// 本合约关联无文本合同招立项数
				count = linkInviteProject + linkContractBill + linkContractWithoutText;
				boolean isCiting = preVersionProg(temp);
				if (count <= 1 && !isCiting) {
					updateProgrammingContract(temp, 0);
				}
			}
		}
	}

	/**
	 * 更新新框架合约是否被引用
	 * 
	 * @throws Exception
	 */
	private void updateNewProg() throws Exception {
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		ContractBillInfo contractBillInfo = null;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		for (int i = 0; i < selectedIdValues.size(); i++) {
			contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(selectedIdValues.get(i).toString()), sic);
			if (contractBillInfo.getProgrammingContract() != null) {
				updateProgrammingContract(contractBillInfo.getProgrammingContract().getId().toString(), 1);
			}
		}
	}
	
	private void checkConRelatedTaskDelUnAudit() throws BOSException, SQLException{
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		
		if(selectedIdValues!=null&&selectedIdValues.size()==0){
			return;
		}
		
		Set contractIds = FDCHelper.getSetByList(selectedIdValues);
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendParam("select fid,count(fid) jishu from T_SCH_ContractAndTaskRel where FContractID",contractIds.toArray());
		builder.appendSql(" group by fid ");
		IRowSet rowSet = builder.executeQuery();
		Set ids = new HashSet();
		while(rowSet.next()){
			int count = rowSet.getInt("jishu");
			if(count >0){
				String id = rowSet.getString("fid");
				ids.add(id);
			}
		}
		boolean flag = false;
		if(ids.size()>0){
			builder.clear();
			builder.appendParam("select count(fid) jishu from T_SCH_ContractAndTaskRelEntry where FParentID  ",ids.toArray());
			IRowSet _rowSet = builder.executeQuery();
			while(_rowSet.next()){
				int _count = _rowSet.getInt("jishu");
				if(_count > 0){
					flag = true;
					break;
				}
			}
		}
		if(flag){
			FDCMsgBox.showInfo("合同已被任务关联，不能执行此操作。");
			SysUtil.abort();
		}
	}
	
	//反归档
    public void actionAntiStore_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	
    	checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");
    	
    	List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
    	
		IContractBill icb = ContractBillFactory.getRemoteInstance();
		boolean flag = false;
		flag = icb.contractBillAntiStore(selectedIdValues);
		if (flag) {				
			FDCClientUtils.showOprtOK(this);
			actionRefresh_actionPerformed(e);	
		}
    }

    /**
     * 支持定位功能，定位字段有：合同编号，合同名称，合同类型，签约时间，状态，签约乙方，合同原币金额，本位币金额
     * Modified by Owen_wen 2010-09-06
     */
	protected String[] getLocateNames()
    {
		return new String[] {IFWEntityStruct.dataBase_Number, IFWEntityStruct.dataBase_Name, "contractType.name", 
				"signDate", "state", "partB.name", "originalAmount", AMOUNT,};
    }
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		if(viewType==0){
			return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class.getName();
		}else{
			return getContractUIName() ;
		}
	}
	
	protected String getContractUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractFullInfoUI.class.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.contract.ContractBillFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
		objectValue.setCurProject((CurProjectInfo) getProjSelectedTreeNode().getUserObject());
		if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
			objectValue.setContractType((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject());
		}
		return objectValue;
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		
		return (ICoreBillBase) ContractBillFactory.getRemoteInstance();
	}

	protected void audit(List ids) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select bill.fcontractpropert from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on  bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on main.fid=entry.fcontent and main.fstate <> '4AUDITTED' ");
	    builder.appendSql("where ");
	    builder.appendParam("bill.fid", FDCHelper.list2Set(ids).toArray());
	    builder.appendSql(" and bill.fcontractpropert='SUPPLY' ");
	    builder.appendSql(" and entry.fdetail='对应主合同编码' ");
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()==1){
	    	rs.next();
	    	String prop = rs.getString("fcontractpropert");
	    	if("SUPPLY".equals(prop)){
	    		FDCMsgBox.showWarning(this,"您所选择的数据中存在主合同未审批的补充合同，不能进行此操作!");
		    	this.abort();
	    	}
	    }
	        
		ContractBillFactory.getRemoteInstance().audit(ids);
	}
	
	/**
	 * 如果为非单独计算补充合同，则不允许反审批
	 * 
	 * @param billId
	 */
	private int unLongContract(List ids) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(1) from t_con_contractbillentry entry");
		builder.appendSql(" inner join t_con_contractbill con on con.fid = entry.fparentid   ");
		builder.appendSql(" inner join t_con_contractbillentry entry2 on entry2.fparentid = entry.fparentid ");
		builder.appendSql("  where entry.frowkey = 'am' and");
		builder.appendParam(" entry2.fcontent", FDCHelper.list2Set(ids).toArray());
		try {
			RowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				return rowSet.getInt(1);
			}
		} catch (Exception e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}
		return 0;
	}

	protected void unAudit(List ids) throws Exception {
		// 如果为非单独计算补充合同，则不允许反审批
		int unLongContract = unLongContract(ids);
		if (unLongContract == 1) {
			FDCMsgBox.showWarning("合同已关联补充合同不能进行此操作");
			SysUtil.abort();
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select * from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on  main.fid=entry.fcontent ");
	    builder.appendSql("where");
	    builder.appendParam("main.fid", FDCHelper.list2Set(ids).toArray());
	    if(isSupply){
	    	builder.appendSql(" and bill.fstate='4AUDITTED'");
	    }
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()>0){
			FDCMsgBox.showWarning(this, "您所选择的数据存在被补充合同引用的合同，不能进行此操作!");
			this.abort();
		}
	    
	    builder.clear();
	    builder.appendSql("select count(fid) from t_con_contractbill where ");
	    builder.appendParam("FMainContractID", FDCHelper.list2Set(ids).toArray());
	    rs = builder.executeQuery();
	    if(rs!= null && rs.size() > 0 && rs.next() && rs.getInt(1) > 0){
	    	FDCMsgBox.showWarning(this, "您所选择的数据存在被战略子合同引用的合同，不能进行此操作!");
			this.abort();
	    }
		ContractBillFactory.getRemoteInstance().unAudit(ids);
	}

	protected KDTable getBillListTable() {

		return getMainTable();
	}

	protected void initTable() {
		super.initTable();
		FDCHelper.formatTableNumber(getMainTable(), AMOUNT);
		getMainTable().getColumn("exRate").getStyleAttributes().setHided(true);
		getMainTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
	}

	protected ArrayList getExportParam() {		
		DatataskParameter param = new DatataskParameter();
		param.solutionName = getSolutionName();
		param.alias = getDatataskAlias();
		param.datataskMode = DatataskMode.ExpMode;
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;		
	}
	
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		ArrayList para = getExportParam();
        if (para == null || para.size() <= 0) {
            throw new FrameWorkException(FrameWorkException.EXPORTDATAPARANULL);// "请设置正确的引出参数。"此处需要修改，请在EASBizException中定义此异常信息
        }

        Object tmp = para.get(0);
        if (tmp instanceof DatataskParameter)
        {
            DatataskParameter dp = (DatataskParameter) tmp;
            IMetaDataPK contractExportQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillForExportQuery");
            EntityViewInfo view = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
            view.setFilter(filter);
            dp.putContextParam("mainQueryPK", contractExportQueryPK);
            dp.putContextParam("mainQuery", view);
        }

        DatataskCaller dc = new DatataskCaller();
        dc.setParentComponent(this);
        dc.invoke(para, DatataskMode.ExpMode);
    }

	/**
	 * 
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext, java.awt.event.ActionEvent)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {

			/*
			 * 把当前选中的工程项目和合同类型传给EditUI
			 */
			BOSUuid projId = ((CurProjectInfo) getProjSelectedTreeNode().getUserObject()).getId();
			BOSUuid typeId = null;
			if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
				if(getTypeSelectedTreeNode().getUserObject() instanceof ContractTypeInfo){
					typeId = ((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject()).getId();
				}
			}
			uiContext.put("projectId", projId);
			uiContext.put("contractTypeId", typeId);
			
			if(projId!=null){
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("isWholeAgeStage");
					CurProjectInfo curInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projId),sic);
					if(curInfo.isIsWholeAgeStage()){
						FDCMsgBox.showWarning("你当前选择的是全期项目，请注意， 谢谢！");
					}
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}
		}
		uiContext.put("prjIds", getUIContext().get("prjIds"));
		
		//设置标识，只要不是从list界面打开的合同，新增删除复制修改都灰显
		uiContext.put("isFromList", Boolean.TRUE);
	}

	protected boolean isHasBillTable() {
		return false;
	}

	/**
	 * 
	 * 描述：检查是否有关联对象
	 * @author:liupd
	 * 创建时间：2006-8-26 <p>
	 */
	protected void checkRef(String id) throws Exception {
		ContractClientUtils.checkContractBillRef(this, id);
	}

	/**
	 * 工程附件
	 */
	public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception {
		//根据新需求在点击工程附件的时候即使没选中行值选中了工程也需要能添加附件 fengYJ 2008-12-16
		//		checkSelected();
		super.actionProjectAttachment_actionPerformed(e);

		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String contractId = this.getSelectedKeyValue();
		if(contractId!=null){
			ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
			CurProjectInfo curProject = contract.getCurProject();
			if (curProject == null) {
				return;
			}
			acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
			
		//update by david_yang 2011.03.29
		}else if(getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo){
			CurProjectInfo curProject = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
			if (curProject == null) {
				return;
			}
			acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
		}else{
			MsgBox.showWarning("请选择工程项目进行添加附件！");
		}
	}
	
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	isCanOperation();
		//    	super.actionAttachment_actionPerformed(e);
		checkSelected(tblMain);
		boolean isEdit = false;
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String boID = this.getSelectedKeyValue();
		if (boID == null) {
			return;
		}

		//////////////////////////////////////////////////////////////////////////
		// R130718-0394:时序簿界面，在工作流中，不允许编辑附件
		//////////////////////////////////////////////////////////////////////////

		boolean flag = FdcWfUtil.isBillInWorkflow(boID);
		if (flag) {
			ICoreBase coreBase = getBizInterface();
			if (null == coreBase) {
				return;
			}

			CoreBaseInfo editData = coreBase.getValue(new ObjectUuidPK(boID));
			AttachmentUIContextInfo info = new AttachmentUIContextInfo();
			info.setBoID(boID);
			MultiApproveUtil.showAttachmentManager(info, this, editData, String.valueOf("1"), false);

			return;
		}

		//////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////

		if (getBillStatePropertyName() != null) {
    		int rowIdx=tblMain.getSelectManager().getActiveRowIndex();
    		ICell cell =tblMain.getCell(rowIdx, getBillStatePropertyName());
    		Object obj=cell.getValue();
    		isEdit=ContractClientUtils.canUploadAttaForAudited(obj, canUploadForAudited);
    	}
    	//add by david_yang PT043562 2011.03.29
    	 if(isEdit){
         	//合同
 	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
 	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
 			if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.ContractBillListUI")){
 				String uiName = "com.kingdee.eas.fdc.contract.client.ContractBillEditUI";
 				boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
 	    				new ObjectUuidPK(userId),
 	    				new ObjectUuidPK(orgId),
 	    				new MetaDataPK(uiName),
 	    				new MetaDataPK("ActionAttamentCtrl") );
 				//如果未启用参数则有权限的用户才能进行附件维护,如果已经启用了参数则制单人等于当前用户且有权限才能进行 维护
 	        	if(hasFunctionPermission){
 	        		boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
 	        		if(creatorCtrl){
 	        			//制单人要等于当前用户才行
 	        			FDCSQLBuilder builder=new FDCSQLBuilder();
 	        			builder.appendSql("select 1 from T_Con_ContractBill where fid=? and fcreatorId=?");
 	        			builder.addParam(boID);
 	        			builder.addParam(userId);
 	        			if(!builder.isExist()){
 	        				isEdit=false;
 	        			}
 	        		}
 	        	}else{
 	        		isEdit=false;
 	        	}
 	        }
    	}
    	acm.showAttachmentListUIByBoID(boID,this,isEdit);
    	this.refreshList();
    }

	/**
	 * 查看正文
	 */
	public void actionViewContent_actionPerformed(ActionEvent arg0) throws Exception {
		isCanOperation();	
			this.checkSelected();
			ContractBillInfo billInfo=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
			/*if(isUseWriteMark&&billInfo!=null&&billInfo instanceof FDCBillInfo){//参数值为是，并且基类要是FDCBill.
				//序时簿界面的操作状态默认地为VIEW,但我们要能够使得在序时簿界面上(假设单据状态合适)能够添加、修改、删除正文就不能直接传递一个 getOprtState()状态 
				ForWriteMarkHelper.isUseWriteMarkForListUI(billInfo,OprtState.EDIT,this);
			}else{
				ContractClientUtils.viewContent(this, this.getSelectedKeyValue());
			}*/
			
			if(billInfo!=null&&billInfo instanceof FDCBillInfo){//参数值为是，并且基类要是FDCBill.
				//序时簿界面的操作状态默认地为VIEW,但我们要能够使得在序时簿界面上(假设单据状态合适)能够添加、修改、删除正文就不能直接传递一个 getOprtState()状态 
				ForWriteMarkHelper.isUseWriteMarkForListUI(billInfo,OprtState.EDIT,this);
			}
		}
	
	/**
	 * 合同执行信息
	 */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    	viewType=-1;
    	try {
			actionView_actionPerformed(e);
		} catch (Exception e1) {
			handUIExceptionAndAbort(e1);
		}finally{
			viewType=0;
		}    	
    }
    //允许审批后添加合同正文  by cassiel_peng
    private boolean isAddContentAfterAudited() {
		boolean returnVal=false;
		try {
			returnVal=FDCUtils.getDefaultFDCParamByKey(null, null,FDCConstants.FDC_PARAM_ADDCONTENTAUDITED);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return returnVal;
	}
	/**
	 * 添加正文
	 */
	public void actionAddContent_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String selectedKeyValue = this.getSelectedKeyValue();
		/*ContractBillInfo contract = new ContractBillInfo();
		String selectedKeyValue = this.getSelectedKeyValue();
		contract.setId(BOSUuid.read(selectedKeyValue));
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", selectedKeyValue);
		filter.appendFilterItem("state", FDCBillStateEnum.CANCEL_VALUE);
		if(ContractBillFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning(this, "合同已终止，不能添加正文！");
			SysUtil.abort();
		}*/
		//已审批的合同不能添加正文   by cassiel_peng 2009-11-06
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("state");
		ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(selectedKeyValue)),selector);
		if(contractBill!=null&&contractBill.getState()!=null&&FDCBillStateEnum.CANCEL.equals(contractBill.getState())){
			MsgBox.showWarning(this, "合同已终止，不能添加正文！");
			SysUtil.abort();
		}
		if(!isAddContentAfterAudited()){
			if(contractBill!=null&&contractBill.getState()!=null&&FDCBillStateEnum.AUDITTED.equals(contractBill.getState())){
				MsgBox.showWarning(this, "合同已审批，不能添加正文！");
				SysUtil.abort();
			}
		}
		
		ContractContentCollection coll = ContractContentFactory.getRemoteInstance().getContractContentCollection("select id,filetype where contract='" + contractBill.getId().toString() + "'");
		File file = this.chooseFileByDialog();
		if (file == null) {
			return;
		}
		
		String fullname = file.getName();
		String extName = StringUtil4File.getExtendedFileName(fullname);
		for(int i=0;i<coll.size();i++){
			if(fullname.equals(((ContractContentInfo)coll.get(i)).getFileType())){
				MsgBox.showWarning(fullname+" 已经存在，不允许添加！");
				return;
			}
		}
		if(!file.canRead()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}
		byte[] content = null;
		try {
			content = FileGetter.getBytesFromFile(file);
		} catch (IOException ex) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}
		
		ContractContentInfo contentInfo = new ContractContentInfo();
		contentInfo.setVersion(new BigDecimal("1.0"));
		contentInfo.setContract(contractBill);
		contentInfo.setFileType(fullname);
		contentInfo.setContentFile(content);
		ContractContentFactory.getRemoteInstance().addnew(contentInfo);
		super.actionAddContent_actionPerformed(e);
	}
	
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String selectedKeyValue = getSelectedKeyValue();
		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
			FDCMsgBox.showInfo("已在工作流处理中，请稍后再试!");
			SysUtil.abort();
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(selectedKeyValue), sic);
		ProgrammingContractInfo pc = (ProgrammingContractInfo) contractBillInfo.getProgrammingContract();
		if (pc != null) {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select con.fid from t_con_contractbill con ");
			builder.appendSql(" inner join T_INV_AcceptanceLetter accep on accep.fid = con.fsourcebillid");
			builder.appendSql(" inner join t_inv_inviteProject invite on invite.fid = accep.FInviteProjectID where ");
			builder.appendSql("  con.fprogrammingcontract = invite.fprogrammingcontractid and ");
			builder.appendParam("con.fid", contractBillInfo.getId().toString());
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.next()) {
				FDCMsgBox.showInfo("框架合约由招标通知书带入，不可修改");
				this.abort();
			}
		}
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("contractBillInfo", contractBillInfo);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,
				OprtState.EDIT);
		uiWindow.show();
		if (contractBillInfo.getProgrammingContract() != null) {

			Object object = uiWindow.getUIObject().getUIContext().get("cancel");
			if (object != null) {
				//
			} else {
				// ======================合约框架控制合同签定逻辑控制
				String param = null;
				SelectorItemCollection sict = new SelectorItemCollection();
				sict.add("*");
				sict.add("fullOrgUnit.*");
				// BOSUuid idcu = contractBillInfo.getCurProject().getId();
				// CurProjectInfo curProjectInfo =
				// CurProjectFactory.getRemoteInstance().getCurProjectInfo(new
				// ObjectUuidPK(idcu), sict);
				ObjectUuidPK pk = new ObjectUuidPK(contractBillInfo.getOrgUnit().getId());
				// if
				// (SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit
				// ()) {
				// ObjectUuidPK pk = new ObjectUuidPK(SysContext.getSysContext()
				// .getCurrentCostUnit().getId());
				param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC228_ISSTRICTCONTROL", pk);
				// }else{
				// ObjectUuidPK pk = new ObjectUuidPK(SysContext.getSysContext()
				// .getCurrentCostUnit().getId());
				// param = ContextHelperFactory.getRemoteInstance()
				// .getStringParam("FDC228_ISSTRICTCONTROL", pk);
				// }
				if (!com.kingdee.util.StringUtils.isEmpty(param)) {
					if (!contractBillInfo.getState().equals(FDCBillStateEnum.SAVED)) {
						int i = Integer.parseInt(param);
						switch (i) {
						case 0:
							// 严格控制时
							if (contractBillInfo.getProgrammingContract() != null) {
								if (contractBillInfo.getAmount()
										.compareTo(
												FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract()
														.getControlBalance()))) > 0) {
									FDCMsgBox.showWarning(this, "合同签约金额超过关联的合约控制余额，不允许提交");
									SysUtil.abort();
								}
							} else {
								FDCMsgBox.showWarning(this, "未关联框架合约，不允许提交");
								SysUtil.abort();
							}
							break;
						case 1:
							// 提示控制时
							if (contractBillInfo.getProgrammingContract() != null) {
								if (contractBillInfo.getAmount().compareTo(
										FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getControlBalance())) > 0) {
									if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否提交") == FDCMsgBox.CANCEL) {
										SysUtil.abort();
									}
								}
							} else {
								FDCMsgBox.showWarning(this, "未关联框架合约，不允许提交");
								SysUtil.abort();
							}
							break;
						case 2:
							// 不控制时
							if (contractBillInfo.getProgrammingContract() != null) {
								if (contractBillInfo.getAmount().compareTo(
										FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getControlBalance())) > 0) {
									if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否提交") == FDCMsgBox.CANCEL) {
										SysUtil.abort();
									}
								}
							}
							break;
						}
						// 更新old的框架合约
						updateOldProg();
						// 维护源ID用于动态规划
						if (contractBillInfo.getProgrammingContract() != null) {
							contractBillInfo.setSrcProID(contractBillInfo.getProgrammingContract().getId().toString());
						}
						SelectorItemCollection sicz = new SelectorItemCollection();
						sicz.add("programmingContract");
						sicz.add("srcProID");
						ContractBillFactory.getRemoteInstance().updatePartial(contractBillInfo, sicz);
						// 更新new的框架合约
						updateNewProg();
						FDCMsgBox.showInfo("关联框架合约成功");
					} else {

						// 更新old的框架合约
						updateOldProg();
						// 维护源ID用于动态规划
						if (contractBillInfo.getProgrammingContract() != null) {
							contractBillInfo.setSrcProID(contractBillInfo.getProgrammingContract().getId().toString());
						}
						ContractBillFactory.getRemoteInstance().save(contractBillInfo);
						// 更新new的框架合约
						updateNewProg();
						FDCMsgBox.showInfo("关联框架合约成功");
					}
				}
				}
			}
		}
	
	private File chooseFileByDialog() {
		File retFile = null;
		int retVal;
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);

		retVal = fc.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION)
			return retFile;
		retFile = fc.getSelectedFile();
		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > StringUtil4File.FILE_BYTES_LIMIT_SINGLE) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		}
		return retFile;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		/**
		 * 隐藏“引入”和“引出”菜单项，一方面这里功能有问题，另一方面数据迁移工具已有实现
		 * Added by Owen_wen 2012-3-1
		 */
		this.btnAddContent.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.btnProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		this.menuItemAddContent.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		this.menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		this.btnConMove.setIcon(FDCClientHelper.ICON_MOVE);
		this.menuItemConMove.setIcon(FDCClientHelper.ICON_MOVE);
		
		this.menuItemViewContract.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.btnViewContract.setIcon(EASResource.getIcon("imgTbtn_execute"));
		
		actionRespite.setVisible(true);
		actionRespite.setEnabled(true);
		actionCancelRespite.setVisible(true);
		actionCancelRespite.setEnabled(true);
	}

	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		return filter;
	}
	
	/**
	 * 
	 * 描述：新增合同前先检查合同类型是否被禁用，若已禁用，则给出提示，该合同类型下不能再新增合同。
	 * @Author：owen_wen
	 * @CreateTime：2013-2-19
	 */
	private void checkContractTypeIsEnableBeforeAddNew() {
		DefaultKingdeeTreeNode typeNode = getTypeSelectedTreeNode();
		if (typeNode.getUserObject() instanceof ContractTypeInfo) {
			ContractTypeInfo contractTypeInfo = (ContractTypeInfo) typeNode.getUserObject();
			if (!contractTypeInfo.isIsEnabled()) { // 未启用
				FDCMsgBox.showInfo(this, "选中的合同类型未启用，请选择其它合同类型。");
				this.abort();
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		//判断当前有没有打开新增的窗体
		IUIWindow win = FDCClientUtils.findUIWindow(this.getEditUIName(), this.getUIContext(), dataObjects, OprtState.ADDNEW);
		if (null != win) {
			//显示窗体
			win.show();
			//显示后试图关闭，如果新增的窗体有新增数据就会提示用户，如果没有就直接关掉打开了一个新的
			win.close();
		}
		    
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		checkContractTypeIsEnableBeforeAddNew();
		super.actionAddNew_actionPerformed(e);
	}

	
	/**
	 * 作废
	 * @param ids
	 * @throws Exception
	 */
	protected void cancel(List ids) throws Exception {
		IObjectPK[] pkArray = FDCHelper.idListToPKArray(ids);
		ContractBillFactory.getRemoteInstance().cancel(pkArray);
	}

	/**
	 * 生效
	 * @param ids
	 * @throws Exception
	 */
	protected void cancelCancel(List ids) throws Exception {
		IObjectPK[] pkArray = FDCHelper.idListToPKArray(ids);

		ContractBillFactory.getRemoteInstance().cancelCancel(pkArray);
	}
	
	public void actionConMove_actionPerformed(ActionEvent e) throws Exception {
		super.actionConMove_actionPerformed(e);
		
		checkSelected();
		checkBillState(new String[]{FDCBillStateEnum.AUDITTED_VALUE}, "selectRightRowForConMove");
		
		if(ContractMoveEditUI.showMe(getSelectedKeyValue(), this)) {
			refreshList();
		}
		
	}
	
    /**
     * 
     * 描述：修改前检查
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     * @throws Exception 
     */
    protected void checkBeforeEdit() throws Exception {
	    checkSelected();
		
		//CoreBillBaseInfo billInfo = getRemoteInterface().getCoreBillBaseInfo(new ObjectUuidPK(getSelectedKeyValue(getBillListTable())));
		String selectedKeyValue = getSelectedKeyValue();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(getBillStatePropertyName());
		selector.add("splitState");
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectStringPK(selectedKeyValue), selector);		
	    
	    String billState = contractBillInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if(billState.equals(states[i])) {
				pass = true;
			}
		}
		if(!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
		
		//该合同已经进行了拆分，不能进行修改
		if(contractBillInfo.getSplitState()!=null && !CostSplitStateEnum.NOSPLIT.equals(contractBillInfo.getSplitState())){
			MsgBox.showWarning(this, "该合同已经进行了拆分，不能进行修改");
			SysUtil.abort();
		}
    }
    
	protected void checkBeforeCancel() throws Exception {
		super.checkBeforeCancel();
		
		String selectedKeyValue = getSelectedKeyValue();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasSettled");
		selector.add("state");
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectStringPK(selectedKeyValue), selector);
		if(contractBillInfo.getState()==FDCBillStateEnum.SUBMITTED || contractBillInfo.getState()==FDCBillStateEnum.AUDITTING){
			//R101224-250提交状态的可以直接终止
		}else{
			//审批状态的只有结算了才可能终止
			if(contractBillInfo.getState()==FDCBillStateEnum.AUDITTED&&!contractBillInfo.isHasSettled()) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("hasNotSettled"));
				SysUtil.abort();
			}
		}
		
	}
	

	//增加：可以对提交、审批中的合同进行终止的状态
	protected String[] getBillStateEnum() {
    	return new String[]{FDCBillStateEnum.AUDITTED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE,FDCBillStateEnum.AUDITTING_VALUE};
	}
	

	// 关联合约查看功能
	public void actionHeYue_actionPerformed(ActionEvent e) throws Exception {
		BOSUIContext uiCtx = new BOSUIContext();

		IUIWindow uiWindow = null;
		uiCtx.put(UIContext.ID, getSelectedKeyValue());
		// com.kingdee.eas.fdc.contract.client.HeYueDanEditUI
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(HeYueDanEditUI.class.getName(), uiCtx, null, OprtState.VIEW);
		uiWindow.show();
		super.actionHeYue_actionPerformed(e);
	}

	/**
	 * 是否忽略CU过滤
	 * <p>
	 * 始终返回是，因为已经有自定义过滤器getTreeSelectFilter
	 * 
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#isIgnoreCUFilter()
	 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}

	//设置查询器
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
		// modify by lihaiou,2014-09-12,打印SQL存在严重性能问题，而且此处重复打印了三次
//		queryExecutor.option().isAutoIgnoreZero = false;
//
//		String sql = null;
//		try {
//			sql = queryExecutor.getSQL();
//
//			logger.info("================================================================");
//			logger.info("logger.info：ContractBillListUI.getQueryExecutor().sql:" + sql);
//			logger.info("logger.info：ContractBillListUI.getQueryExecutor().viewInfo:" + viewInfo);
//			logger.info("================================================================");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return queryExecutor;
	}

	protected IRow appendFootRow() {
		IRow footRow = super.appendFootRow();
		footRow.getCell(AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
		return footRow;
	}

	//在填充Table前处理数据
    public void onGetRowSet(IRowSet rowSet) {
    	super.onGetRowSet(rowSet);
		try {
			rowSet.beforeFirst();

			Set contractIds = new HashSet() ;
			while (rowSet.next()) {
				String id  = rowSet.getString("id");
				contractIds.add(id);					
			}
			Map retValue = ContractBillFactory.getRemoteInstance().getOtherInfo(contractIds);
	    	contentMap = (Map)retValue.get("contentMap");
	    	attachMap = (Map) retValue.get("attachMap");
	    	auditMap = (Map) retValue.get("auditMap");
		}catch(Exception e){
			handUIExceptionAndAbort(e);
		}finally{
			try {
				rowSet.beforeFirst();
			} catch (SQLException e) {
				handUIExceptionAndAbort(e);
			}
		}
/*    	在getOtherInfo内进行统一处理 by sxhong 2009-05-07 15:24:37
    	contentMap = (Map) ActionCache.get("ContractBillListUIHandler.contentMap");
    	attachMap = (Map) ActionCache.get("ContractBillListUIHandler.attachMap");
    	auditMap = (Map) ActionCache.get("ContractBillListUIHandler.auditMap");
    	
    	if(contentMap==null || attachMap==null){
    		contentMap = new HashMap();
    		attachMap = new HashMap();
    		
			try {
				rowSet.beforeFirst();
	
				Set contractIds = new HashSet() ;
				while (rowSet.next()) {
					String id  = rowSet.getString("id");
					contractIds.add(id);					
				}
				
				auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(contractIds);
				
				if(contractIds.size()>0){
					*//**
					 * 填充正文列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
					 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
					 * 如果该合同有正文附件就进行标记，否则不标记
					 *//*
					EntityViewInfo viewContent = new EntityViewInfo();
					FilterInfo filterContent = new FilterInfo();
					viewContent.getSelector().clear();
					viewContent.getSelector().add("contract.id");
					filterContent.getFilterItems().add(new FilterItemInfo("contract.id", contractIds, CompareType.INCLUDE));
					viewContent.setFilter(filterContent);
					ContractContentCollection colContent = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewContent);				
					for(int j = 0; j < colContent.size(); ++j){
						contentMap.put(colContent.get(j).getContract().getId().toString(),Boolean.TRUE);
					}						
					
					*//**
					 * 填充附件列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
					 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
					 * 如果该合同有业务相关附件就进行标记，否则不标记
					 *//*
					EntityViewInfo viewAttachment = new EntityViewInfo();
					FilterInfo filterAttachment = new FilterInfo();
					viewContent.getSelector().clear();
					viewAttachment.getSelector().add("boID");
					filterAttachment.getFilterItems().add(new FilterItemInfo("boID", contractIds, CompareType.INCLUDE));
					viewAttachment.setFilter(filterAttachment);
					BoAttchAssoCollection colAttachment = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(viewAttachment);
						
					for(int j = 0; j < colAttachment.size(); ++j){
						attachMap.put(colAttachment.get(j).getBoID().toString(),Boolean.TRUE);
					}			
					//具体填充在afterDataFill
				}
				rowSet.beforeFirst();
			} catch (Exception e) {
				handUIException(e);
			}
    	}*/
    	
    }
    
	protected void fetchInitData() throws Exception {
		
		super.fetchInitData();
		// 成本中心级参数
		String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		//modify by lihaiou, 2014-09-12,修改性能问题
		//Map paramMap = FDCUtils.getDefaultFDCParam(null, orgId);
		IObjectPK comPK = new ObjectUuidPK(orgId);
		HashMap hmParamIn = new HashMap();
		//集团参数：再次归档时更新合同编号
        hmParamIn.put(FDCConstants.FDC_PARAM_UPDATECONTRACTNO, null);
        //审批状态的单据可以上传附件
        hmParamIn.put(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL,comPK);
        //房地产业务系统附件管理启用笔迹留痕管理
        hmParamIn.put(FDCConstants.FDC_PARAM_WRITEMARK, null);
        
	    hmParamIn.put(FDCConstants.FDC_PARAM_SELECTSUPPLY, comPK);
	    //跨 项目拆分的合同在各项目下单独变更、奖励、扣款及付款
        hmParamIn.put(FDCConstants.FDC_PARAM_MULTIPROJECT, null);
        
        Map paramMap = FDCUtils.getParamHashMapBatch(null, hmParamIn);
		if(paramMap.get(FDCConstants.FDC_PARAM_UPDATECONTRACTNO)!=null){
			//与集团一样值
			isUpdateConNo = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPDATECONTRACTNO).toString()).booleanValue();
		}
		if(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL)!=null){
			canUploadForAudited = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL).toString()).booleanValue();
		}
		if(paramMap.get(FDCConstants.FDC_PARAM_SELECTSUPPLY)!=null){
			isSupply = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_SELECTSUPPLY).toString()).booleanValue();
		}
		if(paramMap.get(FDCConstants.FDC_PARAM_WRITEMARK)!=null){
			isUseWriteMark = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_WRITEMARK).toString()).booleanValue();
		}
		if(paramMap.get(FDCConstants.FDC_PARAM_MULTIPROJECT)!=null){
			isMultiProject = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_MULTIPROJECT).toString()).booleanValue();
		}
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}
	
	
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
    	return false;
    }
	
	public boolean isPrepareActionSubmit() {
    	return true;
    }
	
	public boolean isPrepareActionSave() {
    	return true;
    }
		
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception{
		DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        if (getImportParam() != null) {
			task.invoke(getImportParam(), DatataskMode.ImpMode, true, true);
		}
		actionRefresh_actionPerformed(e);		
	}
	
	protected ArrayList getImportParam() {			
		DatataskParameter param = new DatataskParameter();
		Hashtable hs = new Hashtable();
		param.setContextParam(hs);
        param.solutionName = getSolutionName();      
        param.alias = getDatataskAlias();
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }
	protected String getSolutionName(){
		return "eas.fdc.contract.ContractBillImport";
    }
    
    protected String getDatataskAlias(){
    	return "合同";
    } 

	/**
	 * 找出招标立项所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByInviteProject(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_INV_InviteProject ");
		buildSQL.appendSql("where FProgrammingContractId = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 找出合同所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByContractBill(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_CON_ContractBill ");
		buildSQL.appendSql("where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 找出无文本合同所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByContractWithoutText(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_CON_ContractWithoutText ");
		buildSQL.appendSql("where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 更新规划合约"是否被引用"字段
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(String proContId, int isCiting) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
		try {
			buildSQL.executeUpdate();
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	private boolean preVersionProg(String progId) throws BOSException, SQLException {
		boolean isCityingProg = false;
		int tempIsCiting = 0;
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql(" select t1.FIsCiting isCiting from t_con_programmingContract t1 where t1.fid = (");
		buildSQL.appendSql(" select t2.FSrcId from t_con_programmingContract t2 where t2.fid = '" + progId + "')");
		IRowSet rowSet = buildSQL.executeQuery();
		while (rowSet.next()) {
			tempIsCiting = rowSet.getInt("isCiting");
		}
		if (tempIsCiting > 0) {
			isCityingProg = true;
		}
		return isCityingProg;
	}
	
	protected FilterInfo getTreeSelectFilter(Object projectNode, Object typeNode, boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		Set prjIds = null;
		/*
		 * 工程项目树
		 */
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			boolean isCompanyUint = false;
			FullOrgUnitInfo selectedOrg = new FullOrgUnitInfo();
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {

				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getId();
					selectedOrg = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				} else {
					selectedOrg = (FullOrgUnitInfo) projTreeNodeInfo;
					id = ((FullOrgUnitInfo) projTreeNodeInfo).getId();
					isCompanyUint = selectedOrg.isIsCompanyOrgUnit();
				}

				// 处理项目成本中心过滤器
				FilterInfo filterInfo = dealWithProjectCostCenterFilter(id, authorizedOrgs);
				
				FilterInfo f2 = new FilterInfo();
				//TODO sql待优化
				
				if (isMultiProject&& (!isCompanyUint)) {
					prjIds = FDCClientUtils.getProjIdsOfCostOrg(selectedOrg, true);
					//Set idSet = FDCClientUtils.getSQLIdSet(FDCClientUtils.getSQLIdSet(prjIds));
					Set idSet = FDCClientUtils.getSQLIdSet(prjIds);
					if(idSet==null || idSet.size()==0) {//added by ken_liu 避免为空时sql报错
						idSet.add("null");
					}
					
					String filterSplitSql = "select distinct(fcontractbillid) from T_con_contractCostSplit head " + " inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid "
							+ " inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " + " (" + idSet
							+ ") and fstate<>'9INVALID'";
					String filternoCostSplitSql = "select (fcontractbillid) from T_CON_ConNoCostSplit head " + " inner join T_CON_ConNoCostSplitEntry entry on head.fid=entry.fparentid "
					+ "  where entry.FCURPROJECTID in " + " (" + idSet
					+ ") and fstate<>'9INVALID'";
					f2.getFilterItems().add(new FilterItemInfo("id", filterSplitSql, CompareType.INNER));
					f2.getFilterItems().add(new FilterItemInfo("id", filternoCostSplitSql, CompareType.INNER));
					f2.setMaskString("(#0 or #1 )");
					if (filter != null) {
						filter.mergeFilter(f2, "or");
					}
				}else if(isMultiProject && (isCompanyUint)){
					// 财务组织以上的组织 by hpw 2011.110.19
					Set comIds = FullOrgUnitHelper.getChildrenCompanyOrgUnitInfos(selectedOrg.getLongNumber());
					prjIds = ProjectHelper.getCompanysPrjs(comIds);
					Set idSet = FDCClientUtils.getSQLIdSet(prjIds);
					if(idSet==null || idSet.size()==0) {///added by ken_liu	避免为空时sql报错
						idSet.add("null");
					}
					
					String filterSplitSql = "select distinct(fcontractbillid) from T_con_contractCostSplit head " + " inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid "
							+ " inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " + " (" + idSet
							+ ") and fstate<>'9INVALID'";
					String filternoCostSplitSql = "select fcontractbillid from T_CON_ConNoCostSplit head " + " inner join T_CON_ConNoCostSplitEntry entry on head.fid=entry.fparentid "
					+ "  where entry.FCURPROJECTID in " + " (" + idSet
					+ ") and fstate<>'9INVALID'";
					f2.getFilterItems().add(new FilterItemInfo("id", filterSplitSql, CompareType.INNER));
					f2.getFilterItems().add(new FilterItemInfo("id", filternoCostSplitSql, CompareType.INNER));
					f2.setMaskString("(#0 or #1 )");
					if (filter != null) {
						filter.mergeFilter(f2, "and");
					}
					
				}
				if (filter != null) {
					filter.mergeFilter(filterInfo, "and");
				}
				
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				prjIds = FDCClientUtils.genProjectIdSet(id);
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("curProject.id", prjIds, CompareType.INCLUDE));
				if (FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MULTIPROJECT)) {
					Set sqlSet = FDCClientUtils.getSQLIdSet(prjIds);
					String filterSplitSql = "select distinct(fcontractbillid) from T_con_contractCostSplit head " + " inner join T_Con_contractCostSplitEntry entry on head.fid=entry.fparentid "
							+ " inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid where acct.fcurProject in " + " (" + sqlSet + ") and fstate<>'9INVALID'";
					String filterNoCostSplitSql = "select distinct(fcontractbillid) from T_CON_ConNoCostSplit head " + " inner join T_CON_ConNoCostSplitEntry entry on head.fid=entry.fparentid "
					+ "  where entry.FCURPROJECTID in " + " (" + sqlSet + ") and fstate<>'9INVALID'";
					f.getFilterItems().add(new FilterItemInfo("id", filterSplitSql, CompareType.INNER));
					f.getFilterItems().add(new FilterItemInfo("id", filterNoCostSplitSql, CompareType.INNER));
					f.setMaskString("(#0 or #1 or #2)");

				}
				if (filter != null) {
					filter.mergeFilter(f, "and");
				}
			}
		}
		//用于处理跨项目
		getUIContext().put("prjIds", prjIds);
		
		FilterInfo typefilter = new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();
		/*
		 * 合同类型树
		 */
		if (typeNode != null && typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo) typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet, CompareType.INCLUDE));
		} else if (containConWithoutTxt && typeNode != null && typeNode.equals("allContract")) {
			// 如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}

		if (filter != null && typefilter != null) {
			filter.mergeFilter(typefilter, "and");
		}

		setIsAmtWithoutCostFilter(typefilter);

		return filter;
	}

	protected void setIsAmtWithoutCostFilter(FilterInfo filter) throws BOSException {
		// 合同都显示
		// super.setIsAmtWithoutCostFilter(filter);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		isCanOperation();
		super.actionEdit_actionPerformed(e);
	}
	/**
	 * 对款项目拆分合同：工具栏及菜单栏上修改、删除、审批、反审批、添加正文、附件管理按钮点击时，提示：其他项目拆分过来的合同，不允许此操作！
	 */
	private void isCanOperation() {
		checkSelected();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		DefaultKingdeeTreeNode projectNode = getProjSelectedTreeNode();
		Object projectInfo = null;
		if(projectNode!=null){
			projectInfo = projectNode.getUserObject();
		}
		CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectInfo;
		String id = null;
		if (projTreeNodeInfo instanceof CurProjectInfo && projectNode.isLeaf()) {
			id = projTreeNodeInfo.getId().toString();
			IRow row = tblMain.getRow(rowIndex);
			if(row==null){
				return;
			}
			String proId = row.getCell("curProject.id").getValue().toString();
			if(proId != null){
				if(!proId.equals(id)){
					FDCMsgBox.showWarning(this,"其他项目拆分过来的合同，不允许此操作！");
					this.abort();
				}
			}
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("signDate"));
		sic.add(new SelectorItemInfo("costProperty"));
		sic.add(new SelectorItemInfo("contractPropert"));
		sic.add(new SelectorItemInfo("landDeveloper.name"));
		sic.add(new SelectorItemInfo("contractType.name"));
		sic.add(new SelectorItemInfo("partB.name"));
		sic.add(new SelectorItemInfo("partC.name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("contractSourceId.name"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("exRate"));
		sic.add(new SelectorItemInfo("curProject.id"));
		//sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("isArchived"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("originalAmount"));
		//sic.add(new SelectorItemInfo("currency.id"));
		//sic.add(new SelectorItemInfo("currency.precision"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("mainContractNumber"));
		sic.add(new SelectorItemInfo("attachment"));
		sic.add(new SelectorItemInfo("content"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("isRespite"));
		sic.add(new SelectorItemInfo("respDept.name"));
		sic.add(new SelectorItemInfo("ceremonyb"));
		sic.add(new SelectorItemInfo("ceremonybb"));
		sic.add(new SelectorItemInfo("tabShowIsAmtWithoutCost"));
		return sic;
	}
	
	 /**
	* 是否需要远程获取初始数据：包括参数等
	* @return
	* @throws Exception
	*/
	protected boolean isNeedfetchInitData() throws Exception {
		return false;
		
	}
	/**
	 * ContractBillListUIDecimal的0值按照null来处理，供业务覆盖，返回true则在查询数据时BigDecimal字段的值为0时返回null
	 * 
	 * @return
	 * @author jie_zhao2
	 * @createDate 2015-4-3
	 */
	public boolean isAutoIgnoreZero() {
		return false;
	}
}
