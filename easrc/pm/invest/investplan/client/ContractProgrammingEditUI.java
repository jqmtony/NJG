/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.investplan.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractWithProgramFactory;
import com.kingdee.eas.fdc.contract.client.CostAccountPromptBox;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryCollection;
import com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryFactory;
import com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 合约规划 序时薄
 */
public class ContractProgrammingEditUI extends AbstractContractProgrammingEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractProgrammingEditUI.class);
    //因为修订合约规划然后保存后 会再调用一次loadfields() 这时会造成 分录又被加载一次 所以搞个变量控制下
    private boolean isLoadEmend=true;
    private ProgrammingEntryInfo  programmingEntryInfo;
    public ContractProgrammingEditUI() throws Exception
    {
        super();
    }
    
    public void loadFields() {    	
    	super.loadFields();
    	//因为需求改成 只要还没审批 每次都需要取最新的目标成本 所以只能用代码搞定了。。
    	if(editData.getState()==null ||!editData.getState().equals(FDCBillStateEnum.AUDITTED)){
	    	if(kdtEntry.getRowCount()>0){
	    		for(int i=0;i<kdtEntry.getRowCount();i++){
	    			IRow row=kdtEntry.getRow(i);
	    			CostAccountInfo newInfo =(CostAccountInfo)row.getCell("costAccount").getValue();
	    			if(newInfo != null){
	    				initEntryData(newInfo,row);
	    			}
	    		}
	    	}
    	}
    	//修订状态时    
    	//修订时也要带出最新目标成本。。。。2010.1.22
    	if(isLoadEmend && getUIContext().get("isEmend")!=null && getUIContext().get("isEmend").toString().equals("yes")){
    		
    		EntityViewInfo view=new EntityViewInfo();
    		SelectorItemCollection sic = new SelectorItemCollection();
    		sic.add("parent.curProject.id");
    		sic.add("parent.curProject.*");
    		sic.add("costAccount.id");
    		sic.add("costAccount.*");
    		sic.add("programmingMoney");
    		sic.add("aimCostMoney");
    		sic.add("costAccountName");
    		sic.add("prjLongNumber");
    		sic.add("prjDisplayName");
    		view.setSelector(sic);
    		FilterInfo filter=new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("parent.isFinal",Boolean.TRUE));
    		filter.getFilterItems().add(new FilterItemInfo("parent.number",editData.getNumber()));
    		view.setFilter(filter);
    		try {
				ContractProgrammingEntryCollection coll=ContractProgrammingEntryFactory.getRemoteInstance().getContractProgrammingEntryCollection(view);
				for(Iterator it=coll.iterator();it.hasNext();){
					ContractProgrammingEntryInfo info=(ContractProgrammingEntryInfo)it.next();
					this.prmtCurProject.setValue(info.getParent().getCurProject());
					IRow row=kdtEntry.addRow();
					row.getCell("costAccount").setValue(info.getCostAccount());
					row.getCell("programmingMoney").setValue(info.getProgrammingMoney());
					row.getCell("id").setValue(info.getId().toString());
					initEntryData(info.getCostAccount(),row);
				}
    		} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    }

    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
    	this.kdtEntry.addRow();
    }
    
    public void actionInsertLine_actionPerformed(ActionEvent e)
    		throws Exception {
    	int index = kdtEntry.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			kdtEntry.addRow();
		} else {
			kdtEntry.addRow(index);
		}
    }
    
    public void actionRemoveLine_actionPerformed(ActionEvent e)
    		throws Exception {
    	int index = kdtEntry.getSelectManager().getActiveRowIndex();
		if (index >= 0) {
			kdtEntry.removeRow(index);
			setProgrammingMoney();
		}
		else{
			MsgBox.showWarning("请选择要删除的行！");
		}
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
//    	//提交时 更新其他版本为 非最大版本
//    	if(getUIContext().get("isEmend")!=null && getUIContext().get("isEmend").toString().equals("yes")){
//    		FDCSQLBuilder builder=new FDCSQLBuilder();
//    		builder.appendSql("update t_con_contractprogramming set fislastversion=0 where fnumber=? ");
//    		builder.addParam(editData.getNumber());
//    		builder.execute();
//    		builder.clear();
//    	}
    	
    	// 保存时需要重新计算
    	setProgrammingMoney();
    	
    	isLoadEmend=false;//在修订提交后 调用loadfields时 不在加载分录
        super.actionSubmit_actionPerformed(e);
        if(STATUS_ADDNEW.equals(getOprtState())){
	        this.txtName.setEnabled(true);
			this.txtNumber.setEnabled(true);
        }
    }

    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
//    	//保存时 更新其他版本为 非最大版本
//    	if(getUIContext().get("isEmend")!=null && getUIContext().get("isEmend").toString().equals("yes")){
//    		FDCSQLBuilder builder=new FDCSQLBuilder();
//    		builder.appendSql("update t_con_contractprogramming set fislastversion=0 where fnumber=? ");
//    		builder.addParam(editData.getNumber());
//    		builder.execute();
//    		builder.clear();
//    	}
    	// 保存时重新计算
    	setProgrammingMoney();
    	
    	isLoadEmend=false;//在修订保存后 调用loadfields时 不在加载分录
    	super.actionSave_actionPerformed(e);
    	
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
        this.txtName.setEnabled(true);
		this.txtNumber.setEnabled(true);
		kdtEntry.setEnabled(true);
    }
    
	/**
	 *  这里检查 合约规划是否关联了 合同 任务等 
	 *  如果关联了则给出提示然后中断修改、删除操作。
	 */
	private void checkEditOrRemove() throws Exception{
		String id=this.editData.getId().toString();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programming",id));
		if(ContractWithProgramFactory.getRemoteInstance().exists(filter)){
			MsgBox.showError("合约规划已被合同引用！");
			abort();
		}
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic=super.getSelectors();
		sic.add("state");
		sic.add("edition");
		sic.add("isFinal");
		sic.add("isLastVersion");
		return sic;
	}
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
//    	checkEditOrRemove();
//    	if(!this.editData.isIsFinal()){
//    		MsgBox.showError("只能修改最新版本合约规划！");
//    		abort();
//    	}
        super.actionEdit_actionPerformed(e);
        if(editData.getEdition().compareTo(new BigDecimal("1.0"))>0){
    		this.txtName.setEnabled(false);
    		this.txtNumber.setEnabled(false);
    	}
        kdtEntry.setEnabled(true);
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
//    	checkEditOrRemove();
//    	if(!this.editData.isIsFinal()){
//    		MsgBox.showError("只能删除最新版本合约规划！");
//    		abort();
//    	}
        super.actionRemove_actionPerformed(e);
    }

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {		
		return ContractProgrammingFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {		
		return this.kdtEntry;
	}

	protected KDTextField getNumberCtrl() {		
		return txtNumber;
	}
	
	protected IObjectValue createNewData() {		
		ContractProgrammingInfo info = new ContractProgrammingInfo();
		if(getUIContext().get("projectId")!=null)
		{
			BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
			try {
				ProjectInfo prjInfo = ProjectFactory.getRemoteInstance().getProjectInfo(new ObjectUuidPK(projId));
				info.setCurProject(prjInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(getUIContext().get("project")!=null)
		{
			info.setCurProject((ProjectInfo)getUIContext().get("project"));
		}
		if(getUIContext().get("programmingContract")!=null)
		{
			ProgrammingEntryInfo rowObject = (ProgrammingEntryInfo)getUIContext().get("programmingContract");
			info.setSourceBillId(rowObject.getId().toString());
			info.setNumber(rowObject.getNumber());
			info.setName(rowObject.getName()!=null?rowObject.getName().trim():"");
			info.setDescription(rowObject.getDescription());
		}
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setCreateTime(new Timestamp(new Date().getTime()));
		if(getUIContext().get("isEmend")==null){
			info.setEdition(new BigDecimal("1.00"));			
		}
		info.setIsFinal(false);
		info.setIsLastVersion(true);
		return info;
	}
	//添加判断  当修订时 不调用编码规则
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {
		if(getUIContext().get("isEmend")!=null && getUIContext().get("isEmend").toString().equals("yes")){
			return;
		}
		super.handleCodingRule();
	}
	
	public void onLoad() throws Exception {
		kdtEntry.checkParsed();
		super.onLoad();
		initButton();
		//分录成本科目F7
		CostAccountPromptBox selector=new CostAccountPromptBox(this);
		KDBizPromptBox prmtCostAccount=new KDBizPromptBox(){
			protected String valueToString(Object o) {
				String str=null;
				if (o != null && o instanceof CostAccountInfo) {
					str=((CostAccountInfo)o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		
		
//		prmtCostAccount.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CostAccountQuery");
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");
		
		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		//分录单元格状态 设置
		kdtEntry.getColumn("costAccount").setEditor(caEditor);
		kdtEntry.getColumn("costAccount").setRequired(true);
		kdtEntry.getColumn("costAccountName").getStyleAttributes().setLocked(true);
		kdtEntry.getColumn("aimcost").getStyleAttributes().setLocked(true);
		kdtEntry.getColumn("prjLongNumber").getStyleAttributes().setLocked(true);
		kdtEntry.getColumn("prjDisplayName").getStyleAttributes().setLocked(true);
		//分录规划金额
		kdtEntry.getColumn("programmingMoney").setRequired(true);		
		KDFormattedTextField valueText=new KDFormattedTextField();
		valueText.setHorizontalAlignment(FDCClientHelper.NUMBERTEXTFIELD_ALIGNMENT);
		valueText.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		valueText.setMinimumValue(FDCHelper.ZERO);
		valueText.setSupportedEmpty(true);
		valueText.setPrecision(2);
		KDTDefaultCellEditor valueTextEditor = new KDTDefaultCellEditor(valueText);
		kdtEntry.getColumn("programmingMoney").setEditor(valueTextEditor);
		//设置F7显示、编辑时的格式
		class CellTextRenderImpl extends ObjectValueRender {
			public String getText(Object obj) {
				if(obj == null)
					return null;
				if(obj instanceof CostAccountInfo)
				{
					if(((CostAccountInfo)obj).getLongNumber()==null){
						return null;
					}
					return ((CostAccountInfo)obj).getLongNumber().replace('!', '.');
				}
				return defaultObjectName;
			}
		}
		CellTextRenderImpl render = new CellTextRenderImpl();
		render.setFormat(new BizDataFormat("$longNumber$"));
		kdtEntry.getColumn("costAccount").setRenderer(render);
		if(isLoadEmend && getUIContext().get("isEmend")!=null && getUIContext().get("isEmend").toString().equals("yes")){
			chkMenuItemSubmitAndAddNew.setSelected(false);
		}
		
		//设置分录的 delete键事件
		kdtEntry.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					KDTEditEvent event = new KDTEditEvent(
							kdtEntry, null, null,kdtEntry.getSelectManager().getActiveRowIndex(),
							kdtEntry.getSelectManager().getActiveColumnIndex(), true, 1);
					try {
						kdtEntry_editStopped(event);
					} catch (Exception e1) {
						
					}
					
				}
			}
		});
	}
	//选择成本科目带出 科目名称和 目标成本
	private void initEntryData(CostAccountInfo newInfo,IRow row){		
		row.getCell("costAccountName").setValue(newInfo.getName());
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql(" select sum(entry.FCostAmount) costAmount from T_AIM_CostEntry as entry ");
		builder.appendSql(" inner join T_AIM_AimCost a on entry.fheadid=a.fid ");
		builder.appendSql(" where entry.FCostAccountID=? and a.FIsLastVersion=1 ");
		builder.addParam(newInfo.getId().toString());
		try {
			IRowSet rs=builder.executeQuery();
			if(rs!=null && rs.next()){
				if(rs.getBigDecimal("costAmount")==null){
					MsgBox.showError("该成本科目还没有目标成本！");
					row.getCell("costAccount").setValue(null);
					row.getCell("costAccountName").setValue(null);
					row.getCell("programmingMoney").setValue(null);
					row.getCell("aimcost").setValue(null);
					row.getCell("prjLongNumber").setValue(null);
					row.getCell("prjDisplayName").setValue(null);
					return;
				}
				row.getCell("aimcost").setValue(rs.getBigDecimal("costAmount"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		builder.clear();
		builder.appendSql(" select prj.flongnumber longNumber,prj.fdisplayname_l2 displayName from T_FDC_CurProject prj ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FCurProject=prj.fid ");
		builder.appendSql(" where cost.fid=? ");
		builder.addParam(newInfo.getId().toString());
		try {
			IRowSet rs=builder.executeQuery();
			if(rs!=null && rs.next()){
				row.getCell("prjLongNumber").setValue(rs.getString("longNumber").replace('!', '.'));
				row.getCell("prjDisplayName").setValue(rs.getString("displayName"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void onShow() throws Exception {
		super.onShow();
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		//修订状态时
    	if(getUIContext().get("isEmend")!=null && getUIContext().get("isEmend").toString().equals("yes")){
    		this.txtName.setEnabled(false);
    		this.txtNumber.setEnabled(false);
    	}
    	if(getOprtState().equals(STATUS_EDIT)){
    		if(editData.getEdition().compareTo(new BigDecimal("1.0"))>0){
        		this.txtName.setEnabled(false);
        		this.txtNumber.setEnabled(false);
        	}
    	}
    	if(getOprtState().equals(STATUS_VIEW)){
    		kdtEntry.setEnabled(false);
    	}
	}
	
	private void initButton(){		
		this.actionSubmit.setVisible(true);
		this.actionAuditResult.setVisible(false);
		this.menuTable1.removeAll();
		this.menuTable1.add(this.menuItemAddLine);		
		this.menuTable1.add(this.menuItemInsertLine);
		this.menuTable1.add(this.menuItemRemoveLine);
		
		if(FDCBillStateEnum.SUBMITTED.equals(editData.getState()))
			actionSave.setEnabled(false);
	}
	
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if(e.getColIndex()==this.kdtEntry.getColumnIndex("costAccount")){
			//选择没有改变直接返回
			if(e.getOldValue()!=null && e.getValue()!=null){
				CostAccountInfo oldInfo=(CostAccountInfo)e.getOldValue();
				CostAccountInfo newInfo=(CostAccountInfo)e.getValue();
				if(oldInfo.getId().toString().equals(newInfo.getId().toString())){
					return;
				}
			}
			if(e.getValue()!=null){
				CostAccountInfo newInfo=(CostAccountInfo)e.getValue();
				int index=kdtEntry.getSelectManager().getActiveRowIndex();
				IRow row=kdtEntry.getRow(index);
				initEntryData(newInfo,row);
			}
			if(e.getValue()==null){
				int index=kdtEntry.getSelectManager().getActiveRowIndex();
				IRow row=kdtEntry.getRow(index);
				row.getCell("costAccount").setValue(null);
				row.getCell("costAccountName").setValue(null);
				row.getCell("programmingMoney").setValue(null);
				row.getCell("aimcost").setValue(null);
				row.getCell("prjLongNumber").setValue(null);
				row.getCell("prjDisplayName").setValue(null);
			}
		}
		else if(e.getColIndex()==this.kdtEntry.getColumnIndex("programmingMoney")){
			setProgrammingMoney();
		}
	}
	
	private void setProgrammingMoney(){
		BigDecimal programmingMoney=FDCHelper.ZERO;
		for(int i=0;i<kdtEntry.getRowCount();i++){
			if(kdtEntry.getCell(i, "programmingMoney").getValue()==null){
				continue;
			}
			BigDecimal money=FDCHelper.toBigDecimal(kdtEntry.getCell(i, "programmingMoney").getValue());
			programmingMoney=programmingMoney.add(money);
		}
		if(programmingMoney.compareTo(FDCHelper.ZERO)!=0){
			this.txtProgrammingMoney.setValue(programmingMoney);
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
//    	if(this.txtName.getText()==null || this.txtName.getText().trim().length()==0){
//    		MsgBox.showWarning("合约规划名称不能为空!");
//    		abort();
//    	}
//		if(kdtEntry.getRowCount()>0){
//			Set set=new HashSet();
//			for(int i=0;i<kdtEntry.getRowCount();i++){
//				if(kdtEntry.getCell(i, "costAccount").getValue()==null){
//					MsgBox.showError("第"+(i+1)+"行没有选择成本科目！");
//					abort();
//				}
//				else{
//					CostAccountInfo info = (CostAccountInfo)kdtEntry.getCell(i, "costAccount").getValue();
//					if(!set.add(info.getId().toString())){
//						MsgBox.showError("同一成本科目不能重复录入规划金额！");
//						abort();
//					}
//				}
//				if(kdtEntry.getCell(i, "aimcost").getValue()==null){
//					MsgBox.showError("第"+(i+1)+"行的成本科目没有对应目标成本！");
//					abort();
//				}				
//				else{
//					if(kdtEntry.getCell(i, "programmingMoney").getValue()!=null){
//						BigDecimal programmingMoney=FDCHelper.toBigDecimal(kdtEntry.getCell(i, "programmingMoney").getValue());
//						BigDecimal aimCost=FDCHelper.toBigDecimal(kdtEntry.getCell(i, "aimcost").getValue());
//						if(programmingMoney.compareTo(FDCHelper.ZERO)<=0){
//							MsgBox.showError("第"+(i+1)+"行录入的规划金额小于或等于零！");
//							abort();
//						}
//						if(aimCost.compareTo(programmingMoney)<0){
//							MsgBox.showError("第"+(i+1)+"行录入的规划金额大于目标成本！");
//							abort();
//						}
//					}
//					else{
//						MsgBox.showError("第"+(i+1)+"行没有录入规划金额！");
//						abort();
//					}
//				}
//				// 需求变更为可对同一科目进行多次规划 所以 新加 验证 对同一科目的规划金额累加 不能超过目标成本
//				BigDecimal money=FDCHelper.ZERO;
//				IRow row=kdtEntry.getRow(i);
//    			CostAccountInfo newInfo =(CostAccountInfo)row.getCell("costAccount").getValue();
//    			//这里为了区分 新增、修订和修改  
//    			if(row.getCell("id").getValue()==null){
//					FDCSQLBuilder builder=new FDCSQLBuilder();
//					builder.appendSql(" select sum(entry.FProgrammingMoney) money from T_CON_ContractProgrammingEntry entry ");
//					builder.appendSql(" inner join T_CON_ContractProgramming parent on entry.FParentID=parent.fid  ");
//					builder.appendSql(" where entry.fprjLongNumber=? and entry.FCostAccountID=? and parent.FIsLastVersion=1 ");				
//					builder.addParam(row.getCell("prjLongNumber").getValue().toString());
//					builder.addParam(newInfo.getId().toString());
//					IRowSet rs=builder.executeQuery();
//					if(rs.next()){
//						if(rs.getBigDecimal("money")!=null){
//							money=money.add(rs.getBigDecimal("money"));
//						}
//					}
//				}
//    			else{
//    				FDCSQLBuilder builder=new FDCSQLBuilder();
//					builder.appendSql(" select sum(entry.FProgrammingMoney) money from T_CON_ContractProgrammingEntry entry ");
//					builder.appendSql(" inner join T_CON_ContractProgramming parent on entry.FParentID=parent.fid  ");
//					builder.appendSql(" where entry.fprjLongNumber=? and entry.FCostAccountID=? and parent.FIsLastVersion=1 and entry.fid<>? ");				
//					builder.addParam(row.getCell("prjLongNumber").getValue().toString());
//					builder.addParam(newInfo.getId().toString());
//					builder.addParam(row.getCell("id").getValue().toString());
//					IRowSet rs=builder.executeQuery();
//					if(rs.next()){
//						if(rs.getBigDecimal("money")!=null){
//							money=money.add(rs.getBigDecimal("money"));
//						}
//					}					
//    			}
//				BigDecimal aimCost=FDCHelper.toBigDecimal(row.getCell("aimcost").getValue());
//				BigDecimal programmingMoney=FDCHelper.toBigDecimal(row.getCell("programmingMoney").getValue());
//				money=money.add(programmingMoney);
//				if(money.compareTo(aimCost)>0){
//					MsgBox.showError("在同一工程项目下科目"+newInfo.getName()+"的累计规划金额不能超过目标成本！");
//					abort();
//				}
//				//如果是修订状态  验证完了 应该把分录的ID 替换成新的
//				if(getUIContext().get("isEmend")!=null && getUIContext().get("isEmend").toString().equals("yes")){
//					row.getCell("id").setValue(BOSUuid.create("21118986"));
//				}
//			}
//		}
//		else{
//			MsgBox.showError("请添加成本科目分录！");
//			abort();
//		}
	}

}