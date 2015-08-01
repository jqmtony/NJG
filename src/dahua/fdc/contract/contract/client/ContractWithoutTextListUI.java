/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * 描述:无文本合同列表界面
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.1.3
 */
public class ContractWithoutTextListUI extends
		AbstractContractWithoutTextListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractWithoutTextListUI.class);

	/**
	 * output class constructor
	 */
	public ContractWithoutTextListUI() throws Exception {
		super();
	}

	 protected String[] getBillStateForEditOrRemove() {
		return new String[] { FDCBillStateEnum.BACK_VALUE, FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE,
				FDCBillStateEnum.INVALID_VALUE };
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		//	    	checkIsInWorkflow();
		
		boolean pass = false;

		String selectedKeyValue = FDCClientHelper.getSelectedKeyValue(tblMain, "id");
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(getBillStatePropertyName());
		selector.add("splitState");
		ContractWithoutTextInfo contractBillInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(
				new ObjectStringPK(selectedKeyValue), selector);

		String billState = contractBillInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		for (int i = 0; i < states.length; i++) {
			if (billState.equals(states[i])) {
				pass = true;
			}
		}
		if (!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
		
		super.actionEdit_actionPerformed(e);
	}
		
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractWithoutTextFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractWithoutTextInfo objectValue = new com.kingdee.eas.fdc.contract.ContractWithoutTextInfo();
		return objectValue;
	}
	/**
	 * 
	 * 描述：冻结合同表列
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void freezeTableColumn() {
		//FDCHelper.formatTableNumber(getMainTable(), "amount");
	
		// 合同名称
		int name_col_index = getMainTable().getColumn("billName")
				.getColumnIndex();
		getMainTable().getViewManager().setFreezeView(-1, name_col_index+1);
	}
	
	
	
	 /**
	 * 
	 * 描述：返回远程接口（子类必须实现）
	 * @return
	 * @throws BOSException
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected ICoreBase getRemoteInterface() throws BOSException {
		return ContractWithoutTextFactory.getRemoteInstance();
	}
	
	/**
	 * 
	 * 描述：审核通过（子类必须实现，调用服务器端打审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected void audit(List ids) throws Exception {
		ContractWithoutTextFactory.getRemoteInstance().audit(ids);
	}

	/**
	 * 
	 * 描述：反审核（子类必须实现，调用服务器端打反审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected void unAudit(List ids) throws Exception {
		ContractWithoutTextFactory.getRemoteInstance().unAudit(ids);
	}
	
	/**
     * 
     * 描述：检查是否有关联对象
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected void checkRef(String id) {
    	ContractWithoutTextClientUtils.checkRef(this, id);
    }
    
    protected void updateButtonStatus() {
    
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 描述：（批量）删除前检查 - R130812-0007(打回状态的单据不让删除)
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#checkBeforeRemove()
	 * @author zhaoqin
	 * @CreateTime：2013-8-22
	 */
	protected void checkBeforeRemove() throws Exception {
		checkSelected();

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		String[] states = getBillStateForRemove();

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if (billState.equals(states[i])) {
					pass = true;
				}
			}
			if (!pass) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
				SysUtil.abort();
			}
		}
	}

	/**
	 * 描述：可删除的单据状态 - R130812-0007
	 * @return
	 * @Author：zhaoqin
	 * @CreateTime：2013-8-22
	 */
	protected String[] getBillStateForRemove() {
		return new String[] { FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE, FDCBillStateEnum.INVALID_VALUE };
	}
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
    	FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
    	super.actionAddNew_actionPerformed(e);
    }
    
//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		获取用户选择的块
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");

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
					abort();
				}
			}	
		}
	}
	
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        //super.actionQuery_actionPerformed(e);
    }
    
    /**
     * 描述：按钮"查看付款单"的Action方法。
     * @author zhiyuan_tang 2010/08/04
     */
    public void actionViewPayment_actionPerformed(ActionEvent e)
    		throws Exception {
    	
    	//检查是否选中行
    	super.checkSelected();
    	
    	//判断是否有关联的付款单
    	boolean hasPaymentBill = false;
    	String paymentID = null;
    	//取得选中的合同ID
    	String contractBillID = getSelectedKeyValue();
    	
    	//判断有没有该合同对应的付款单
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("ContractBillId", contractBillID));
		view.setFilter(filter);
		view.getSelector().add("id");
		
		PaymentBillCollection col = com.kingdee.eas.fi.cas.PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
		if (col != null && col.size() > 0) {
			hasPaymentBill = true;
			
			//获取付款单ID
			for (int i = 0; i < col.size(); i++) {
				paymentID = col.get(i).getId().toString();
			}
		} else {
			hasPaymentBill = false;
		}
    	
    	
    	if (hasPaymentBill) {
    		//当无文本合同下有对应生成的付款单时则以查看状态打开付款单
    		showPayment(paymentID);
    		
    	} else {
    		//当无文本合同下没有对应生成的付款单时则提示用户：该无文本合同没有对应的付款单
    		FDCMsgBox.showWarning(EASResource.getString(
    						"com.kingdee.eas.fdc.contract.client.ContractResource",
    						"contractWithoutTextHasNOPayment"));
    		abort();
    	}
    }
    
    /**
     * 描述：打开指定付款单的编辑界面
     * @param billID:付款单ID
     * @throws Exception
     * @author zhiyuan_tang 2010/08/03
     */
    private void showPayment(String billID) throws Exception {
		Map uiContext = new HashMap();
		uiContext.put(UIContext.OWNER, this);
		uiContext.put(UIContext.ID, billID);
		FilterInfo myFilter = new FilterInfo();
		myFilter.getFilterItems().add(new FilterItemInfo("id", billID));
		uiContext.put("MyFilter", myFilter); // 注意，这里的billID是44位长的BOSUuid，类型是String
		// IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.MODEL) ;
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow window = uiFactory.create(
				"com.kingdee.eas.fdc.finance.client.PaymentBillEditUI",
				uiContext, null, OprtState.VIEW);
		window.show();
	}
    
	public void onLoad() throws Exception {
		
		super.onLoad();
		//Add by zhiyuan_tang 2010/08/03 
		//添加查看付款单按钮,ToolBar和MenuBar上都要添加,图标为下查图标
		btnViewPayment.setVisible(true);
		btnViewPayment.setEnabled(true);
		btnViewPayment.setIcon(EASResource.getIcon("imgTbtn_downview"));
		menuItemViewPayment.setVisible(true);
		menuItemViewPayment.setEnabled(true);
		menuItemViewPayment.setIcon(EASResource.getIcon("imgTbtn_downview"));
	}
	
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
    	return true;
    }
	
	public boolean isPrepareActionSave() {
    	return true;
    }
		
	/** 添加快速定位功能，定位字段有：单据编号，单据名称、合同类型、收款单位、签约日期；
	 * @author owen_wen 2010-09-06
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "billName","contractType", "receiveUnit.name", "signDate", };
	}
	
	protected boolean isFootVisible() {
		return true;
	}

	//执行查询
	protected void execQuery() {
		super.execQuery();
		//格式化表格的脚
		FDCClientUtils.fmtFootNumber(tblMain, new String[] { "originalAmount", "amount" });
	}
}