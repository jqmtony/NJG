/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.finance.client.PaymentBillListUI;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)			ContractFullListUI			
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		合同查询界面
 *		
 * @author		蒲磊		<p>
 * @createDate	2011-8-22	<p>	 
 * @version		EAS7.0		
 * @see					
 */
public class ContractFullListUI extends AbstractContractFullListUI {
	/** */
	private static final long serialVersionUID = 1L;

	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	private static final String CONTRACT_PROPERT = "补充合同";
	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	/** 正文 */
	private Map contentMap = new HashMap();
	
	/** 附件*/
	private Map attachMap = new HashMap();
	
	//最新审批人 审批时间
	private Map auditMap = new HashMap();
	
	//校验权限用
	private String currentUserInfoId="";//用户Id
	private String currentOrgUnitId="";//组织Id

	/**
	 * 默认构造器
	 */
	public ContractFullListUI() throws Exception {
		super();
	}

	protected String getEditUIName() {
		return ContractFullInfoUI.class.getName();
	}
	
	/**
	 * 关闭editui后， 不自动刷新序时簿 modify by zhiqiao_yang at 2010-09-28
	 */
	protected void refresh(ActionEvent e) throws Exception {		
		if (e != null && e.getSource() != null && !e.getSource().equals(this.btnRefresh) ) {			
			return;
		}
//		super.refresh(e);
		refreshTblMain(this.getMainQuery());
	}

	/**
	 * description		执行查询
	 * @author			蒲磊 <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.framework.client.ListUI#execQuery()
	 */
	protected void execQuery() {
		super.execQuery();
		auditMap.clear();
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"amount","originalAmount"});
//		FDCClientHelper.initTable(tblMain);
		/**
		 * 性能优化，将三个map的取值在Map com.kingdee.eas.fdc.contract.app.ContractBillControllerBean._getOtherInfo中一次性取出,减少RPC
		 * by Cassiel_peng 2009-11-13
		 */
		/*Set contractIds = new HashSet() ;
		for(int i = 0; i < this.tblMain.getRowCount(); ++i)
		{
			IRow row = this.tblMain.getRow(i);
			String contractId = row.getCell("id").getValue().toString();
			contractIds.add(contractId);
		}
		
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
		try {
			ContractContentCollection colContent = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewContent);
			
			for(int j = 0; j < colContent.size(); ++j){
				contentMap.put(colContent.get(j).getContract().getId().toString(),Boolean.TRUE);
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		*//**
		 * 由于肖飙彪在保存正文的时候想要整个房地产系统各的业务单据都可用便没有直接使用之前系统已有的contract这个字段为正文的业务单据父体而是新加了
		 * 字段parent，所以保存的时候依据不同，之后查看修改的时候可能就会产生很多问题。对于ID不同步的问题我是在启用或者禁用参数"房地产业务系统正文管理启用审批笔迹留痕功能"
		 * 解决的，也就是说如果没有手动启用或者是禁用参数的话我们的ID还是存在不同步的问题，而最直接的影响就是在合同录入序时簿和合同查询序时簿上的"正文"一列的信息显示不准确
		 * 由于目前咱们这个房地产系统就只有合同模块用了该功能，所以FParent其实就是合同的ID(FContractId)，那解决该问题就只以FParent为依据再找一遍咯！烦。。 by Cassiel_peng 2009-9-6
		 *//*
		EntityViewInfo _viewContent1 = new EntityViewInfo();
		FilterInfo _filterContent1 = new FilterInfo();
		_viewContent1.getSelector().clear();
		_viewContent1.getSelector().add("parent");
		_filterContent1.getFilterItems().add(new FilterItemInfo("parent", contractIds, CompareType.INCLUDE));
		_viewContent1.setFilter(_filterContent1);
		try {
			ContractContentCollection colContent = ContractContentFactory.getRemoteInstance().getContractContentCollection(_viewContent1);
			
			for(int j = 0; j < colContent.size(); ++j){
				contentMap.put(colContent.get(j).getParent(),Boolean.TRUE);
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		//获取最新审批人 审批时间信息
		try {
			auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(contractIds);
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
		}
		*//**
		 * 填充附件列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有业务相关附件就进行标记，否则不标记
		 *//*
		EntityViewInfo viewAttachment = new EntityViewInfo();
		FilterInfo filterAttachment = new FilterInfo();
		_viewContent1.getSelector().clear();
		viewAttachment.getSelector().add("boID");
		filterAttachment.getFilterItems().add(new FilterItemInfo("boID", contractIds, CompareType.INCLUDE));
		viewAttachment.setFilter(filterAttachment);
		try {
			BoAttchAssoCollection colAttachment = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(viewAttachment);
			
			for(int j = 0; j < colAttachment.size(); ++j)
				attachMap.put(colAttachment.get(j).getBoID().toString(),Boolean.TRUE);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		for(int k = 0; k < this.tblMain.getRowCount(); k++)
		{
			IRow row = this.tblMain.getRow(k);
			//获取每一个row的key值
			String key = row.getCell("id").getValue().toString();
			//填充最新审批人和审批时间
			if(auditMap.containsKey(key)){
			MultiApproveInfo info = (MultiApproveInfo)auditMap.get(key);
			row.getCell("wfAuditName").setValue(info.getCreator().getName());
			row.getCell("wfAuditTime").setValue(info.getCreateTime());
			}
			if(attachMap.containsKey(row.getCell("id").getValue()))
				row.getCell("attachment").setValue(Boolean.TRUE);
			else
				row.getCell("attachment").setValue(Boolean.FALSE);
			
			if(contentMap.containsKey(row.getCell("id").getValue()))
				row.getCell("content").setValue(Boolean.TRUE);
			else
				row.getCell("content").setValue(Boolean.FALSE);
		}*/
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractBillFactory.getRemoteInstance();
	}


	/**
	 * description		初始化查询界面
	 * @author			蒲磊 <p>
	 * @createDate		2011-8-23<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.framework.client.ListUI#initCommonQueryDialog()
	 */
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());		
		return commonQueryDialog;
	}
	
	protected boolean isShowAttachmentAction() {
		
		return true;
	}
	
	/**
	 * description		返回查询面板
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-23<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		return this.filterUI;
	}

	boolean hasCurrency = false;
	
	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return !FDCMsgBox.isHidedBox();
	}
	
	public void onLoad() throws Exception {
		currentUserInfoId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
		currentOrgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				boolean isMainOrder = false;
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
//						if(row.getCell("wfAuditName").getValue()==null){
							row.getCell("wfAuditName").setValue(info.getCreator().getName());
						//这句代码不知道是多久之前哪位神仙写的了，把制单时间赋给了制单人那一列，导致制单事件没值，制单人却是日期，悲剧呀！by cassiel_peng
//							row.getCell("wfAuditName").setValue(info.getCreateTime());
							row.getCell("wfAuditTime").setValue(info.getCreateTime());
//						}
					}
					if(isMainOrder){
//							 node = new CellTreeNode();
						String number = "";
						if(row.getCell("mainContractNumber")!=null){
							if (row.getCell("mainContractNumber").getValue() != null) {
								number = row.getCell("mainContractNumber").getValue().toString();
							}
							if (!number.equals(preNumber)) {
								row.setTreeLevel(0);
							} else  if (!"".equals(number)){//
								row.setTreeLevel(1);
								tblMain.getTreeColumn().setDepth(2);
								row.getCell("number").setValue("   " + row.getCell("number").getValue());
							}
							preNumber = number;
							
							//tblMain.getColumn("mainContractNumber").getStyleAttributes().setHided(true);
						}
					}
				}
			}
		});
		
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();
		FDCClientHelper.initTableListener(tblMain,this);
		
		FDCHelper.formatTableNumber(tblMain, new String[]{"amount", "originalAmount", "exRate","conPaid"});
		FDCHelper.formatTableDateTime(tblMain,"wfAuditTime");
		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		super.onLoad();
		afterOnLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//只允许选择单行
		//显示图标
		initWorkButton();
		initTableStyle();
		
		refreshTblMain(this.getMainQuery());
		
	}

	private void afterOnLoad() {
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionAudit.setEnabled(false);
		this.actionAntiAudit.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionAntiAudit.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		FDCClientHelper.initTable(tblMain);
		
		//显示附件管理按钮和附件管理菜单
//		this.actionAttachment.setVisible(true);
//		this.actionAttachment.setEnabled(true);
		
		this.actionViewContent.setVisible(true);
		this.actionViewContent.setEnabled(true);
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
    protected boolean isIgnoreCUFilter() {
        return true;
    }
    
	public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception {
//		super.actionAntiAudit_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		checkSelected();
//
//		checkBillState(getStateForAudit(), "selectRightRowForAudit");
//
//		audit(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
//
//		super.actionAudit_actionPerformed(e);
//		
//		showOprtOKMsgAndRefresh();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	return;
    }
	
	protected OrgType getMainBizOrgType() {
		return OrgType.CostCenter;
	}
	
	/**
     * 设置是否显示合计行
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }
    
    /**
	 * 查看正文   by Cassiel_peng 
	 */
	public void actionViewContent_actionPerformed(ActionEvent arg0) throws Exception {
		this.checkSelected();
		//根据ID获得实体的BOSType
        BOSObjectType bosType = BOSUuid.read(getSelectedKeyValue()).getType(); 
        //根据BOSType获得实体调用接口
        ICoreBase coreBase = (ICoreBase) BOSObjectFactory.createRemoteBOSObject(bosType, ICoreBase.class);
        //获得该对象
        CoreBaseInfo objectInfo = coreBase.getValue(new ObjectUuidPK(getSelectedKeyValue()));  
       
    	//房地产业务系统正文管理是否启用审批笔迹留痕功能
		boolean isUseWriteMark = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_WRITEMARK);

		//参数值为是，并且基类要是FDCBill.
        if(isUseWriteMark&&objectInfo!=null&&objectInfo instanceof FDCBillInfo){
        	ForWriteMarkHelper.isUseWriteMarkForListUI((FDCBillInfo)objectInfo,OprtState.VIEW,this);
        }else{
        	ContractClientUtils.viewContent(this, this.getSelectedKeyValue());
        }
	}
	
	/**
	 * 合同拆分:需要保留合同拆分序时簿界面点击拆分按钮所有的逻辑，"量价拆分"作为特殊情况极少数客户使用故不在考虑之列
	 *  by Cassiel_peng  2009-11-17
	 */
	public void actionContractSplit_actionPerformed(ActionEvent e)
			throws Exception {
		
		ContractFullListUtils.verifyAudited(tblMain);//必须的校验
		
		Object contractId=this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		String UI="com.kingdee.eas.fdc.contract.client.ContractCostSplitListUI";
		String edit_action_permission="ActionCostSplit";
		String view_action_permission="ActionView";
		
		boolean hasEditPermission=ContractFullListUtils.verifyPermission(UI,edit_action_permission,currentUserInfoId,currentOrgUnitId);//校验是否有修改权限
		boolean hasViewPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);//校验是否有查看权限
		
		if(ContractFullListUtils.isCostSplit(this.tblMain)){//成本类合同
			EntityViewInfo view=new EntityViewInfo();
			view.getSelector().add("id");
			view.getSelector().add("state");
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill",contractId==null?"":contractId.toString()));
			filter.getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));//作废的过滤出去
			view.setFilter(filter);
			ContractCostSplitCollection conCostSplitColl=ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection(view);
			if(conCostSplitColl!=null&&conCostSplitColl.size()==0){//未拆分
				if(hasEditPermission){//有维护权限，打开让用户维护
//					boolean canSplit=ContractFullListUtils.verifyCanSplit(this.tblMain,true);//校验是否能够拆分(由于合同尚未拆分，所以不存在拆分是否被引用之说，真是个猪)
//					if(canSplit){//拆分已经被引用
						UIContext costSplitUIContext = new UIContext(this);
						costSplitUIContext.put("costBillID",contractId==null?"":contractId.toString());
						IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"ADDNEW");
						costSplitUIWindow.show();
//					}else{//拆分没有被引用
//						
//					}
				}else{
					FDCMsgBox.showWarning(this,"该合同尚未拆分，但由于你没有维护权限，操作终止！");
					SysUtil.abort();
				}
			}else if(conCostSplitColl!=null&&conCostSplitColl.size()!=0){//已拆分
				ContractCostSplitInfo conCostSplitInfo=(ContractCostSplitInfo)conCostSplitColl.get(0);
				if(conCostSplitInfo.getId()!=null&&conCostSplitInfo.getState()!=null){
					if(conCostSplitInfo.getState().equals(FDCBillStateEnum.AUDITTED)){//已审批
						if(hasViewPermission){						//以查看方式打开
							UIContext costSplitUIContext = new UIContext(this);
							costSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
							IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"VIEW");
							costSplitUIWindow.show();
						}else{
							FDCMsgBox.showWarning(this,"该合同对应的合同拆分已经审批，由于你没有查看权限，操作终止！");
							SysUtil.abort();
						}
					}else{//未审批
						if(hasEditPermission){//有维护权限，打开让用户维护
							boolean canSplit=	ContractFullListUtils.verifyCanSplit(this.tblMain,true);//校验是否能够拆分
							if(canSplit){//拆分未被引用
								UIContext costSplitUIContext = new UIContext(this);
								costSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
								IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"EDIT");
								costSplitUIWindow.show();
							}else{//拆分已被引用，需检验是否有查看权限
								if(hasViewPermission){
									UIContext costSplitUIContext = new UIContext(this);
									costSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
									IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"VIEW");
									costSplitUIWindow.show();
								}else{
									FDCMsgBox.showWarning("该合同对应的合同拆分已经被引用，不能再修改拆分单据，仅供查看。但是由于你没有查看权限，操作终止！");
									SysUtil.abort();
								}
							}
						}else{//无维护权限，再校验是否有查看权限
							if(!hasViewPermission){
								FDCMsgBox.showWarning(this,"该合同对应的合同拆分尚未审批，但由于你没有维护或者是查看合同拆分的权限，操作终止！");
								SysUtil.abort();
							}else{//有查看权限，打开让用户查看
								UIContext costSplitUIContext = new UIContext(this);
								costSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
								IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null,"VIEW");
								costSplitUIWindow.show();
							}
						}
					}
				}
			}
		}else{//非成本类合同
			EntityViewInfo view=new EntityViewInfo();
			view.getSelector().add("id");
			view.getSelector().add("state");
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill",contractId==null?"":contractId.toString()));
			filter.getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));//作废的过滤出去
			view.setFilter(filter);
			ConNoCostSplitCollection conNoCostSplitColl=ConNoCostSplitFactory.getRemoteInstance().getConNoCostSplitCollection(view);
			if(conNoCostSplitColl!=null&&conNoCostSplitColl.size()==0){//未拆分
				if(hasEditPermission){//有维护权限，打开让用户维护
					UIContext nonCostSplitUIContext = new UIContext(this);
					nonCostSplitUIContext.put("costBillID",contractId==null?"":contractId.toString());
					IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"ADDNEW");
					nonCostSplitUIWindow.show();
				}else{
					FDCMsgBox.showWarning(this,"该合同尚未拆分，但由于你没有维护权限，操作终止！");
					SysUtil.abort();
				}
			}else if(conNoCostSplitColl!=null&&conNoCostSplitColl.size()!=0){//已拆分
				ConNoCostSplitInfo conCostSplitInfo=(ConNoCostSplitInfo)conNoCostSplitColl.get(0);
				if(conCostSplitInfo.getId()!=null&&conCostSplitInfo.getState()!=null){
					if(conCostSplitInfo.getState().equals(FDCBillStateEnum.AUDITTED)){//已审批
						if(hasViewPermission){						//以查看方式打开
							UIContext nonCostSplitUIContext = new UIContext(this);
							nonCostSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
							IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"VIEW");
							nonCostSplitUIWindow.show();
						}else{
							FDCMsgBox.showWarning(this,"该合同对应的合同拆分已经审批，由于你没有查看权限，操作终止！");
							SysUtil.abort();
						}
					}else{//未审批
						if(hasEditPermission){//有维护权限，打开让用户维护
							
							boolean canSplit=ContractFullListUtils.verifyCanSplit(this.tblMain,false);//校验是否能够拆分
							if(canSplit){//拆分未被引用
								UIContext nonCostSplitUIContext = new UIContext(this);
								nonCostSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
								IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"EDIT");
								nonCostSplitUIWindow.show();
							}else{//拆分已被引用，再校验是否有查看权限
								if(hasViewPermission){
									UIContext nonCostSplitUIContext = new UIContext(this);
									nonCostSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
									IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"VIEW");
									nonCostSplitUIWindow.show();
								}else{
									FDCMsgBox.showWarning("该合同对应的合同拆分已经被引用，不能再修改拆分单据，仅供查看。但是由于你没有查看权限，操作终止！");
									SysUtil.abort();
								}
							}
						}else{//无维护权限，再校验是否有查看权限
							if(!hasViewPermission){
								FDCMsgBox.showWarning(this,"该合同对应的合同拆分尚未审批，但由于你没有维护或者是查看合同拆分的权限，操作终止！");
								SysUtil.abort();
							}else{//有查看权限，打开让用户查看
								UIContext nonCostSplitUIContext = new UIContext(this);
								nonCostSplitUIContext.put(UIContext.ID,conCostSplitInfo.getId().toString());
								IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null,"VIEW");
								nonCostSplitUIWindow.show();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 新增变更签证申请     by Cassiel_peng  2009-11-17
	 */
	public void actionAddChangeAudit_actionPerformed(ActionEvent e)
			throws Exception {
		ContractFullListUtils.verifyHasSettled(tblMain);
		Object contractId=this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		String UI="com.kingdee.eas.fdc.contract.client.ChangeAuditListUI";
		String view_action_permission="ActionAddNew";
		boolean hasAddPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);
		if(hasAddPermission){
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",contractId));
			SelectorItemCollection sic = new SelectorItemCollection();
			view.setSelector(sic);
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("name"));
	        sic.add(new SelectorItemInfo("partB.number"));
	        sic.add(new SelectorItemInfo("partB.name"));
	        sic.add(new SelectorItemInfo("curProject.id"));
			sic.add(new SelectorItemInfo("curProject.name"));
			sic.add(new SelectorItemInfo("curProject.number"));
			sic.add(new SelectorItemInfo("curProject.cu"));
			
			sic.add(new SelectorItemInfo("currency.number"));
			sic.add(new SelectorItemInfo("currency.name"));		
			sic.add(new SelectorItemInfo("currency.precision"));	
			sic.add(new SelectorItemInfo("isCoseSplit"));	
			sic.add(new SelectorItemInfo("hasSettled"));	
			sic.add(new SelectorItemInfo("currency.precision"));
			ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId.toString())), sic);
			UIContext changeAuditUIContext = new UIContext(this);
			changeAuditUIContext.put("contract", info);
			changeAuditUIContext.put("contractBillId", contractId==null?"":contractId.toString());
			changeAuditUIContext.put("projectId", info.getCurProject().getId());
			IUIWindow  changeAuditUIWindow= UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ChangeAuditEditUI.class.getName(), changeAuditUIContext, null,"ADDNEW");
			changeAuditUIWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"你没有新增权限，操作终止！");
			SysUtil.abort();
		}
	}
	/**
	 * 新增结算单       by Cassiel_peng  2009-11-17
	 */
	public void actionAddContractSettlement_actionPerformed(ActionEvent e)
	throws Exception {
		ContractFullListUtils.verifyAudited(tblMain);
		ContractFullListUtils.verifyHasSettled(tblMain);
		
		Object contractId=this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		String UI="com.kingdee.eas.fdc.contract.client.ContractSettlementBillListUI";
		String view_action_permission="ActionAddNew";
		boolean hasAddPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);
		if(hasAddPermission){
			UIContext conSettleUIContext = new UIContext(this);
			conSettleUIContext.put("contractBillId", contractId==null?"":contractId.toString());
			IUIWindow  conSettleUIWindow= UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractSettlementBillEditUI.class.getName(), conSettleUIContext, null,"ADDNEW");
			conSettleUIWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"你没有新增权限，操作终止！");
			SysUtil.abort();
		}
	}

	/**
	 * 新增付款申请单     by Cassiel_peng  2009-11-17
	 */
	public void actionAddPayRequest_actionPerformed(ActionEvent e)
			throws Exception {
		ContractFullListUtils.verifyAudited(tblMain);
		
		Object contractId=this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		String UI="com.kingdee.eas.fdc.contract.client.PayRequestBillListUI";
		String view_action_permission="ActionAddNew";
		boolean hasAddPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);
		if(hasAddPermission){
			UIContext changeAuditUIContext = new UIContext(this);
			changeAuditUIContext.put("contractBillId", contractId==null?"":contractId.toString());
			IUIWindow  changeAuditUIWindow= UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PayRequestBillEditUI.class.getName(), changeAuditUIContext, null,"ADDNEW");
			changeAuditUIWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"你没有新增权限，操作终止！");
			SysUtil.abort();
		}
	}
	/**
	 * 付款单序时簿   by Cassiel_peng  2009-11-17
	 */
	public void actionPaymentListUI_actionPerformed(ActionEvent e)
			throws Exception {
		if(tblMain.getRowCount()==0){
			FDCMsgBox.showWarning("请先选择一行单据！");
			SysUtil.abort();
		};
		String UI="com.kingdee.eas.fdc.finance.client.PaymentBillListUI";
		String view_action_permission="ActionView";
		boolean hasViewPermission=ContractFullListUtils.verifyPermission(UI,view_action_permission,currentUserInfoId,currentOrgUnitId);
		if(hasViewPermission){
			UIContext paymentListUIContext = new UIContext(this);
			IUIWindow paymentListUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PaymentBillListUI.class.getName(), paymentListUIContext, null,"EDIT");
			paymentListUIWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"你没有查看权限，操作终止！");
			SysUtil.abort();
		}
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.btnContractSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnAddChangeAudit.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.btnAddContractSettlement.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.btnAddPayRequest.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.btnPaymentListUI.setIcon(EASResource.getIcon("imgTbtn_listfile"));
		this.menuItemView.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemViewContent.setAction(btnViewContent.getAction());
		this.menuItemContractSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.menuItemAddChangeAudit.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.menuItemAddConSettlement.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.menuItemAddPayRequest.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.menuItemPaymentListUI.setIcon(EASResource.getIcon("imgTbtn_listfile"));
		this.btnAuditResult.setEnabled(true);
        this.btnAuditResult.setVisible(true);
	}
	protected void updateButtonStatus() {
//		super.updateButtonStatus();
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
        refreshTblMain(mainQuery);
        
        /**
         * 给tblMain的最近审批人和最新审批时间赋值－by neo
         */
        auditMap.clear();
        Set contractIds = new HashSet() ;
		for(int i = 0; i < this.tblMain.getRowCount(); ++i)
		{
			IRow row = this.tblMain.getRow(i);
			String contractId = row.getCell("id").getValue().toString();
			contractIds.add(contractId);
		}
		try {
			auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(contractIds);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
		}
        for(int k = 0; k < this.tblMain.getRowCount(); k++)
		{
			IRow row = this.tblMain.getRow(k);
			//获取每一个row的key值
			String key = row.getCell("id").getValue().toString();
			//填充最新审批人和审批时间
			if(auditMap.containsKey(key)){
				MultiApproveInfo info = (MultiApproveInfo)auditMap.get(key);
				row.getCell("wfAuditName").setValue(info.getCreator().getName());
				row.getCell("wfAuditTime").setValue(info.getCreateTime());
			}
		}
    }

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	boolean isEdit=false;
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
       
        acm.showAttachmentListUIByBoID(boID, this,isEdit); // boID 是 与附件关联的 业务对象的 ID
    }
	
	/**
	 * 设置查询器
	 * @author owen_wen 2010-8-25
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
		
		queryExecutor.option().isAutoIgnoreZero = false;
		
		return queryExecutor;
	}
	
	/**
	 * 初始化表格样式
	 * @author owen_wen 2010-8-25
	 */
	private void initTableStyle(){
		FDCHelper.formatTableNumber(getMainTable(), "amount");
		FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		FDCHelper.formatTableDate(getMainTable(), "wfAuditTime");
	}
	
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }

	/**
	 * description		返回合同的分录集合
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-17<p>
	 * @param			billInfo 合同单据
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private ContractBillEntryCollection getContractBillEntryCols(ContractBillInfo billInfo){
		ContractBillEntryCollection cols = null;
		if (null != billInfo && null != billInfo.getEntrys()) {
			cols = billInfo.getEntrys();
		}
		
		return cols;
	}
	
	/**
	 * description		判断补充合同是否 独立计算
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-17<p>
	 * @param			cols 分录集合
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private boolean isLonelyCal(ContractBillEntryCollection cols){
		boolean isLonelyCal = false;
		if (null != cols) {
			ContractBillEntryInfo entryInfo = null;
			for (int i = 0; i < cols.size(); i++) {
				entryInfo = cols.get(i);
				if (null != entryInfo.getDetail() && entryInfo.getDetail().toString().equals("是否单独计算")) {
					String content = entryInfo.getContent();
					if (content.equals("是")) {
						isLonelyCal = true;
					}else {
						isLonelyCal = false;
					}
				}
			}
		}
		
		return isLonelyCal;
	}
	
	/**
	 * description		返回主合同包含的补充合同ID集合
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-17<p>
	 * @param			contractBillId 主合同ID
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @throws BOSException 
	 * @throws SQLException 
	 * @see							
	 */
	private HashSet getSupplyContract(String contractBillId) throws BOSException, SQLException{
		HashSet suppplySet = new HashSet();
		
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select bill.fid id,bill.fcontractpropert ,entry.* from t_con_contractbill bill left outer join t_con_contractbillentry entry on bill.fid=entry.fparentid ");
		buildSQL.appendSql("where bill.fcontractpropert = 'SUPPLY' and entry.fcontent = '"+ contractBillId + "'");
		IRowSet rowSet = buildSQL.executeQuery();
		while (rowSet.next()) {
			String id = rowSet.getString("id");
			suppplySet.add(id);
		}
		return suppplySet;
	}
	
	/**
	 * modify haiou_li,2013.12.10
	 * @description:加载合同列表，此处严重影响性能，在第一步中合同进行查询出来满足条件的记录，
	 *              但是后面又去循环查补充合同，完全没有必要
	 * @param view
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException
	 */
	private void refreshTblMain(EntityViewInfo view) throws BOSException, SQLException, EASBizException{
		tblMain.removeRows(false);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE); //实模式
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);//只允许选择单行

		EntityViewInfo viewInfo = view;
		viewInfo.getSelector().clear();
		viewInfo.setSelector(getSelectors());
		viewInfo.getSelector();
		ContractBillCollection billColls = ContractBillFactory.getRemoteInstance().getContractBillCollection(viewInfo);
		if (null == billColls || billColls.size() < 0 ) {
			return;
		}
		
		Map addConMap = new HashMap();
		Set contractIds = new HashSet();
		for (int i = 0; i < billColls.size(); i++) {
			ContractBillInfo billInfo = billColls.get(i);
			String contractBillId = billInfo.getId().toString();
			//主合同编码
			String mainConNumber = billInfo.getMainContractNumber();
			List addConList = null;
			if(StringUtils.isNotBlank(mainConNumber) ){
				addConList = (List)addConMap.get(mainConNumber);
				if(addConList == null){
					addConList = new ArrayList();
					addConMap.put(mainConNumber, addConList);
				}
				addConList.add(billInfo);
			}
			
			
			contractIds.add(contractBillId);
		}
		
		//用来记录已经插入行的合同ID，防止重复插入补充合同
		Set hasInsertCon = new HashSet();
		/****构造补充合同****/
		for (int i = 0; i < billColls.size(); i++) { //重新加载合同列表，显示主合同与补充合同的关系
			ContractBillInfo billInfo = billColls.get(i);
			String contractBillId = billInfo.getId().toString();
			hasInsertCon.add(contractBillId);
			if (null != billInfo.getContractPropert() && ! billInfo.getContractPropert().toString().equals("补充合同")) {
				//
				String mainConNumber = billInfo.getNumber();
				List addConList = (List)addConMap.get(mainConNumber);
				IRow row = tblMain.addRow();
				int level = row.getTreeLevel();
				int dep = tblMain.getTreeColumn().getDepth();
				if (dep < level + 1) {
					tblMain.getTreeColumn().setDepth(level + 1);
				}
				row.setUserObject(billInfo);
				fillSupplyRow(row,billInfo);
				//遍历补充合同
				if(addConList != null && addConList.size() > 0){
					for(int j = 0; j < addConList.size(); j ++){
						ContractBillInfo supplyBillInfo = (ContractBillInfo)addConList.get(j);
						IRow newRow = tblMain.addRow();
						newRow.setUserObject(supplyBillInfo);
						newRow.setTreeLevel(level + 1);
						fillSupplyRow(newRow, supplyBillInfo);
						if (null != newRow.getCell("isLonelyCal")) {
							newRow.getCell("isLonelyCal").setValue(Boolean.valueOf(isLonelyCal(getContractBillEntryCols(supplyBillInfo))));
						}
						hasInsertCon.add(supplyBillInfo.getId().toString());
					}
				}
				/*if (null != supplySet && supplySet.size() > 0) { //如果有补充合同
					EntityViewInfo viewInfo2 = new EntityViewInfo();
					viewInfo2.getSelector().clear();
					viewInfo2.setSelector(getSelectors());
					FilterInfo filter = new FilterInfo(); 
					filter.getFilterItems().add(new FilterItemInfo("id",supplySet,CompareType.INCLUDE));
					
					if (isHasFilter("respDept.id", view.getFilter())) {
						//添加了过滤责任部门条件 add by jian_cao
						filter.getFilterItems().add(
								new FilterItemInfo("respDept.id", billInfo.getRespDept().getId().toString(), CompareType.EQUALS));
					}
					if (isHasFilter("contractType.id", view.getFilter())) {
						//添加了合同类型过滤 add by jian_cao BT718525
						filter.getFilterItems().add(
								new FilterItemInfo("contractType.id", billInfo.getContractType().getId().toString(), CompareType.EQUALS));
					}
					if (isHasFilter("partB.id", view.getFilter())) {
						//供应商（乙方）过滤 add by jian_cao BT718524
						filter.getFilterItems().add(new FilterItemInfo("partB.id", billInfo.getPartB().getId(), CompareType.EQUALS));
					}
					
					
					viewInfo2.setFilter(filter);
					ContractBillCollection supCols = ContractBillFactory.getRemoteInstance().getContractBillCollection(viewInfo2);
					IRow newRow = null; //补充合同行
					ContractBillInfo supplyBillInfo = null;
					for (int j = 0; j < supCols.size(); j++) {
						supplyBillInfo = supCols.get(j);
						newRow = tblMain.addRow();
						newRow.setUserObject(supplyBillInfo);
						newRow.setTreeLevel(level + 1);
						fillSupplyRow(newRow, supplyBillInfo);
						if (null != newRow.getCell("isLonelyCal")) {
							newRow.getCell("isLonelyCal").setValue(Boolean.valueOf(isLonelyCal(getContractBillEntryCols(supplyBillInfo))));
						}
					}
				}*/
			}else { //如果是补充合同
				if (isAddSupplyRow(contractIds,billInfo)) {
					IRow newRow = tblMain.addRow();
					newRow.setUserObject(billInfo);
					fillSupplyRow(newRow, billInfo);
					if (null != newRow.getCell("isLonelyCal")) {
						newRow.getCell("isLonelyCal").setValue(Boolean.valueOf(isLonelyCal(getContractBillEntryCols(billInfo))));
					}
				}
			}
			
		}
		
		Map retValue = ContractBillFactory.getRemoteInstance().getOtherInfo(contractIds);
    	contentMap = (Map)retValue.get("contentMap");
    	attachMap = (Map) retValue.get("attachMap");
    	auditMap = (Map) retValue.get("auditMap");

		addTableListener();
	}


	/**
	 * 描述：检测合同查询Filter是否包含指定的Filter
	 * @param name
	 * @param filterInfo
	 * @return
	 * @Author：jian_cao
	 * @CreateTime：2012-10-26
	 */
	private boolean isHasFilter(String name, FilterInfo filterInfo) {
		if (filterInfo == null)
			return false;
		FilterItemCollection filterItems = filterInfo.getFilterItems();
		if (filterItems != null && filterItems.size() > 0) {
			for (int i = 0, size = filterItems.size(); i < size; i++) {
				if (name.equals(filterItems.get(i).getPropertyName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * description		返回是否单独增加补充合同行，true就要增加
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-25<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private boolean isAddSupplyRow(Set idsSet,ContractBillInfo billInfo){
		boolean isAddSupplyRow = false;
		ContractBillEntryCollection cols = getContractBillEntryCols(billInfo);
		if (null != cols && cols.size() > 0) {
			ContractBillEntryInfo entryInfo = null;
			for (int i = 0; i < cols.size(); i++) {
				entryInfo = cols.get(i);
				if (null != entryInfo.getDetail() && entryInfo.getDetail().toString().equals("对应主合同编码")) {
					String content = entryInfo.getContent();
					if (! idsSet.contains(content)) {
						isAddSupplyRow = true;
					}
				}
			}
		}
		return isAddSupplyRow;
	}

	/**
	 * description		填充补充合同行数据
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-18<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private void fillSupplyRow(IRow row ,ContractBillInfo info){
		//合同ID
		if (null != row.getCell("id") && null != info.getId()) {
			row.getCell("id").setValue(info.getId().toString());
		}
		
		//订立时间
		if (null != row.getCell("bookedDate") && null != info.getBookedDate()) {
			row.getCell("bookedDate").setValue(info.getBookedDate());
		}
		//期间
		if (null != row.getCell("period") && null != info.getPeriod()) {
			row.getCell("period").setValue(BigDecimal.valueOf(info.getPeriod().getNumber()));
		}

		//当前项目
		if (null != row.getCell("project") && null != info.getCurProject() && null != info.getCurProject().getName()) {
			row.getCell("project").setValue(info.getCurProject().getName());
		}

		//合同类型
		if (null != row.getCell("contractType.name") && null != info.getContractType()) {
			row.getCell("contractType.name").setValue(info.getContractType().getName());
		}

		//合同编码
		if (null != row.getCell("number") && null != info.getNumber()) {
			row.getCell("number").setValue(info.getNumber());
		}

		//合同名称
		if (null != row.getCell("contractName") && null != info.getName()) {
			row.getCell("contractName").setValue(info.getName());
		}
		
		//合同累计付款
		if (null != row.getCell("conPaid") && null != info.getPrjPriceInConPaid()) {
			row.getCell("conPaid").setValue(info.getPrjPriceInConPaid());
		}

		//状态
		if (null != row.getCell("state") && null != info.getState()) {
			row.getCell("state").setValue(info.getState());
		}
		
		//已结算
		if (null != row.getCell("hasSettle")) {
			row.getCell("hasSettle").setValue(Boolean.valueOf(info.isHasSettled()));
		}

		//合同性质
		if (null != row.getCell("contractPropert") && null != info.getContractPropert()) {
			row.getCell("contractPropert").setValue(info.getContractPropert());
		}

		//主合同编码
		if (null != row.getCell("mainContractNumber")) {
			if (null != info.getMainContractNumber()) {
				row.getCell("mainContractNumber").setValue(info.getMainContractNumber());
			}else {
				row.getCell("mainContractNumber").setValue(info.getNumber());
			}
			
		}
		
		//币别
		if (null != row.getCell("currency") && null != info.getCurrency() && null != info.getCurrency().getName()) {
			row.getCell("currency").setValue(info.getCurrency().getName());
		}

		//原币金额
		if (null != row.getCell("originalAmount") && null != info.getOriginalAmount()) {
			row.getCell("originalAmount").setValue(info.getOriginalAmount());
		}

		//税率
		if (null != row.getCell("exRate") && null != info.getExRate()) {
			row.getCell("exRate").setValue(info.getExRate());
		}

		//本币金额
		if (null != row.getCell("amount") && null != info.getAmount()) {
			row.getCell("amount").setValue(info.getAmount());
		}

		//乙方
		if (null != row.getCell("partB.name") && null != info.getPartB() && null != info.getPartB().getName()) {
			row.getCell("partB.name").setValue(info.getPartB().getName());
		}

		//形成方式
		if (null != row.getCell("contractSource") && null != info.getContractSourceId()) {
			row.getCell("contractSource").setValue(info.getContractSourceId().getName());
		}

		//签约日期
		if (null != row.getCell("signDate") && null != info.getSignDate()) {
			row.getCell("signDate").setValue(info.getSignDate());
		}

		//责任部门
		if (null != row.getCell("respDept") && null != info.getRespDept() && null != info.getRespDept().getName()) {
			row.getCell("respDept").setValue(info.getRespDept().getName());
		}

		//签约甲方
		if (null != row.getCell("landDeveloper.name") && null != info.getLandDeveloper() && null != info.getLandDeveloper().getName()) {
			row.getCell("landDeveloper.name").setValue(info.getLandDeveloper().getName());
		}

		//丙方
		if (null != row.getCell("partC.name") && null != info.getPartC() && null != info.getPartC().getName()) {
			row.getCell("partC.name").setValue(info.getPartC().getName());
		}

		//造价性质
		if (null != row.getCell("costProperty") && null != info.getCostProperty()) {
			row.getCell("costProperty").setValue(info.getCostProperty());
		}

		//归档
		if (null != row.getCell("isArchived")) {
			row.getCell("isArchived").setValue(Boolean.valueOf(info.isIsArchived()));
		}

		if (null != row.getCell("currency.id") && null != info.getCurrency() && null != info.getCurrency().getId()) {
			row.getCell("currency.id").setValue(info.getCurrency().getId());
		}

		if (null != row.getCell("currency.precision") && null != info.getCurrency()) {
			row.getCell("currency.precision").setValue(BigDecimal.valueOf(info.getCurrency().getPrecision()));
		}
		
		if (info.getAuditor() != null) {
			row.getCell("wfAuditName").setValue(info.getAuditor().getName());
			row.getCell("wfAuditTime").setValue(info.getAuditTime());
		}

		if (info.getCreator()  != null ) {
			row.getCell("createTime").setValue(info.getCreateTime());
			row.getCell("creator.name").setValue(info.getCreator().getName());
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();		
        sic.add(new SelectorItemInfo("entrys.detail"));
		sic.add(new SelectorItemInfo("entrys.content"));
		return sic;
	}
	
	private void addTableListener(){
		boolean isMainOrder = false;
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

		for (int i = 0; i < this.tblMain.getRowCount(); i++) { //填充附件，正文，审批信息
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
					row.getCell("wfAuditName").setValue(info.getCreator().getName());
				//这句代码不知道是多久之前哪位神仙写的了，把制单时间赋给了制单人那一列，导致制单事件没值，制单人却是日期，悲剧呀！by cassiel_peng
					row.getCell("wfAuditTime").setValue(info.getCreateTime());
			}
		}

	}

	/**
	 * @see com.kingdee.eas.framework.client.BillListUI#tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent)
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		
		boolean isEnabled = true;
		
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index != -1) {
			if (CONTRACT_PROPERT.equals(this.tblMain.getCell(index, "contractPropert").getValue().toString().trim())) {
				Object isLonelyCal = this.tblMain.getCell(index, "isLonelyCal").getValue();
				//如果不是独立计算
				if (isLonelyCal == Boolean.FALSE) {
					isEnabled = false;
				}
			}
		}
		this.actionAddPayRequest.setEnabled(isEnabled);
		this.actionAddChangeAudit.setEnabled(isEnabled);
		this.actionAddContractSettlement.setEnabled(isEnabled);
		
		super.tblMain_tableSelectChanged(e);
	}
	
	
}