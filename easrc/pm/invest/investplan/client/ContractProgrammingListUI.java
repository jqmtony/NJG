/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.investplan.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractWithProgramFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractProgrammingListUI extends
		AbstractContractProgrammingListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractProgrammingListUI.class);

	/**
	 * output class constructor
	 */
	public ContractProgrammingListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	protected void audit(List ids) throws Exception {
		ContractProgrammingFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		ContractProgrammingFactory.getRemoteInstance().unAudit(ids);
	}

	protected ICoreBase getRemoteInterface() throws BOSException {

		return ContractProgrammingFactory.getRemoteInstance();
	}

	protected String getEditUIName() {

		return ContractProgrammingEditUI.class.getName();
	}

	public void onLoad() throws Exception {

		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("当前组织不是成本中心，不能进入！");
			SysUtil.abort();
		}
		super.onLoad();
		updateButtonStatus();
//		setColor();
	}
	
	protected void setSortForQuery(SorterItemInfo sortItem,
			SorterItemInfo oldSortItem) throws Exception {
		super.setSortForQuery(sortItem, oldSortItem);
		execQuery();
	}
	
	protected void execQuery(){
		super.execQuery();
		try {
			setColor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 当因为修订了 科目的目标成本 导致 已经规划的科目 规划金额超过了目标成本 这时 整行显示红色
	 * @throws Exception 
	 * 
	 */
	private void setColor() throws Exception{
		for(int i=0;i<tblMain.getRowCount();i++){
			IRow row=tblMain.getRow(i);
			if(row.getCell("isFinal").getValue()!=null){
				//构建服务器jdk版本低不支持Boolean.parseBoolean
				//boolean isFinal=Boolean.parseBoolean(row.getCell("isFinal").getValue().toString());
				boolean isLastVersion=Boolean.valueOf(row.getCell("isLastVersion").getValue().toString()).booleanValue();
				if(isLastVersion){
					//取合约规划 中科目累计规划金额
					FDCSQLBuilder builder=new FDCSQLBuilder();
					builder.appendSql(" select sum(entry.FProgrammingMoney) money from T_CON_ContractProgrammingEntry entry ");
					builder.appendSql(" inner join T_CON_ContractProgramming parent on entry.FParentID=parent.fid  ");
					builder.appendSql(" where entry.fprjLongNumber=? and entry.FCostAccountID=? and parent.FisLastVersion=1 ");
					if(row.getCell("entrys.prjLongNumber").getValue()==null 
							||row.getCell("costAccount.id").getValue()==null){
						return;
					}
					builder.addParam(row.getCell("entrys.prjLongNumber").getValue().toString());
					builder.addParam(row.getCell("costAccount.id").getValue().toString());
					BigDecimal programmingMoneys=FDCHelper.ZERO;
					IRowSet rs=builder.executeQuery();
					if(rs.next()){
						if(rs.getBigDecimal("money")!=null){
							programmingMoneys=programmingMoneys.add(rs.getBigDecimal("money"));
						}
					}
					builder.clear();
					//取合约规划中年科目最新目标成本
					builder.appendSql(" select sum(entry.FCostAmount) costAmount from T_AIM_CostEntry as entry ");
					builder.appendSql(" inner join T_AIM_AimCost a on entry.fheadid=a.fid ");
					builder.appendSql(" where entry.FCostAccountID=? and a.FIsLastVersion=1 ");
					builder.addParam(row.getCell("costAccount.id").getValue().toString());
					BigDecimal costAmount=FDCHelper.ZERO;
					IRowSet r=builder.executeQuery();
					if(r.next()){
						if(r.getBigDecimal("costAmount")!=null){
							costAmount=costAmount.add(r.getBigDecimal("costAmount"));
						}
					}
					//当因为修订了 科目的目标成本 导致 已经规划的科目 规划金额超过了目标成本 这时 整行显示红色
					if(costAmount.compareTo(programmingMoneys)<0){
						row.getStyleAttributes().setBackground(new Color(255,0,255));
					}
				}
			}
		}
	}

	// 重写此基类方法 放出按钮
	protected void updateButtonStatus() {

		super.updateButtonStatus();
		this.actionAddNew.setVisible(true);
		this.actionEdit.setVisible(true);
		this.actionRemove.setVisible(true);
		this.actionAddNew.setEnabled(true);
		this.actionEdit.setEnabled(true);
		this.actionRemove.setEnabled(true);
		this.menuEdit.setVisible(true);
		this.actionAuditResult.setVisible(false);
		this.actionQuery.setVisible(true);
		this.actionAbout.setEnabled(true);
		this.actionQuery.setEnabled(true);

		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.actionEmend.setEnabled(true);
	}

	/**
	 * 检查当前选择工程项目是否有目标成本
	 * 
	 * @throws EASBizException
	 */
	private void checkProjectInAimCost() throws BOSException, EASBizException {
//		DefaultKingdeeTreeNode node = getProjSelectedTreeNode();
//		// 保留这个判断可提高部分性能
//		if (node.getTextColor() == Color.RED) {
//			MsgBox.showError("该工程项目还没有对应目标成本，不能新增合约规划！");
//			abort();
//		}
//		if (node.getUserObject() instanceof ProjectInfo) {
//			ProjectInfo info = (ProjectInfo) node.getUserObject();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("orgOrProId", info.getId().toString(),
//							CompareType.EQUALS));
//			if (!AimCostFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showError("该工程项目还没有对应目标成本，不能新增合约规划！");
//				abort();
//			}
//		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkProjectInAimCost();
		super.actionAddNew_actionPerformed(e);
	}

	protected boolean confirmRemove() {
		if (MsgBox.isOk(MsgBox.showConfirm2New(this,
				"删除合约规划会删除掉该合约规划下所有科目,请确认是否删除?"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 这里检查 合约规划是否关联了 合同 任务等 如果关联了则给出提示然后中断修改、删除操作。
	 */
	private void checkEditOrRemove() throws Exception {
		checkSelected();
		String id=this.getSelectedKeyValue();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programming",id));
		if(ContractWithProgramFactory.getRemoteInstance().exists(filter)){
			MsgBox.showError("合约规划已被合同引用！");
			abort();
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkEditOrRemove();
		super.actionRemove_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkEditOrRemove();
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * 合约规划修订
	 */
	public void actionEmend_actionPerformed(ActionEvent e) throws Exception {

		checkSelected();
		ContractProgrammingInfo info = ContractProgrammingFactory
				.getRemoteInstance().getContractProgrammingInfo(
						new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
		if (!info.isIsFinal()||!info.isIsLastVersion()) {
			MsgBox.showError("只能修订最新且最大版本合约规划！");
			abort();
		}
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql(" select FContractBillID from T_CON_ContractWithProgram where FProgrammingID=? ");
		builder.addParam(this.getSelectedKeyValue());
		if(builder.isExist()){
			MsgBox.showError("合约规划已被合同引用不能修订！");
			abort();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("isEmend", "yes");
		
		ContractProgrammingInfo cpInfo = new ContractProgrammingInfo();
		cpInfo.setName(info.getName());
		cpInfo.setNumber(info.getNumber());
		cpInfo.setCreateTime(new Timestamp(new Date().getTime()));
		//每次修订时版本号 取最大版本号加0.1
		builder.clear();
		builder.appendSql("select max(fedition) edition from t_con_contractprogramming where fnumber=?");
		builder.addParam(info.getNumber());
		IRowSet rs=builder.executeQuery();
		BigDecimal maxEdition=FDCHelper.ZERO;
		if(rs.next()){
			maxEdition=maxEdition.add(rs.getBigDecimal("edition"));
		}
		builder.clear();		
		cpInfo.setEdition(maxEdition.add(new BigDecimal("0.1")));
		
		cpInfo.setIsImagePay(info.isIsImagePay());
		cpInfo.setProgrammingMoney(info.getProgrammingMoney());
		cpInfo.setDescription(info.getDescription());
		cpInfo.setIsFinal(false);
		cpInfo.setIsLastVersion(true);
		
		uiContext.put(UIContext.INIT_DATAOBJECT, cpInfo);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
		IUIWindow curDialog = uiFactory.create(ContractProgrammingEditUI.class
				.getName(), uiContext, null, OprtState.ADDNEW);
		curDialog.show();
		
	}

	/**
	 * 审批
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		super.actionAudit_actionPerformed(e);
	}

	/**
	 * 反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkEditOrRemove();
		ContractProgrammingInfo info = ContractProgrammingFactory
			.getRemoteInstance().getContractProgrammingInfo(
				new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
		if (!info.isIsFinal()) {
			MsgBox.showError("不能反审批非最新版本合约规划！");
			abort();
		}
		super.actionUnAudit_actionPerformed(e);
	}
}