/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractWithoutTextFullListUI extends AbstractContractWithoutTextFullListUI
{
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	private Map proLongNameMap=new HashMap();
	/**
	 * output class constructor
	 */
	public ContractWithoutTextFullListUI() throws Exception {
		super();
	}
	
	protected boolean isShowAttachmentAction() {
		
		return false;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return ContractWithoutTextEditUI.class.getName();
	}

	protected void execQuery() {
		super.execQuery();
		
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"originalAmount"});
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"amount"});
		//FDCClientHelper.initTable(tblMain);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractWithoutTextFactory.getRemoteInstance();
	}

	

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractWithoutTextFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	public void onLoad() throws Exception {
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();		
		FDCClientHelper.initTableListener(tblMain,this);
		
		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		
		this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		this.actionAuditResult.setEnabled(true);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionAuditResult.setVisible(true);
		
		//Add by zhiyuan_tang 2010/08/03 
		//添加查看付款单按钮,ToolBar和MenuBar上都要添加,图标为下查图标
		btnViewPayment.setVisible(true);
		btnViewPayment.setEnabled(true);
		btnViewPayment.setIcon(EASResource.getIcon("imgTbtn_downview"));
		menuItemViewPayment.setVisible(true);
		menuItemViewPayment.setEnabled(true);
		menuItemViewPayment.setIcon(EASResource.getIcon("imgTbtn_downview"));
		
		FDCClientHelper.initTable(tblMain);
		
	}
	public void onGetRowSet(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String displayName="";
				String id=rowSet.getString("curProject.id");
				String orgName=rowSet.getString("orgUnit.name");
				if(orgName!=null){
					displayName+=orgName;
				}
				String proName = (String) this.proLongNameMap.get(id);
				if(proName!=null){
					displayName+="\\"+proName;
				}
				rowSet.updateString("curProject.name",displayName);
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		super.onGetRowSet(rowSet);
	}
	protected String getEditUIModal() {
		// TODO 自动生成方法存根
		return UIFactoryName.NEWTAB;
	}
    protected boolean isIgnoreCUFilter() {
        return true;
    }
    
    int sortType = KDTSortManager.SORT_ASCEND;
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	super.tblMain_tableClicked(e);
    	
//    	 if (isOrderForClickTableHead() && e.getType() == KDTStyleConstants.HEAD_ROW
//                 && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1)
//         {
//    		 if(tblMain.getColumnKey(e.getColIndex()).equals("curProject.name")) {
//    			 
//	             KDTSortManager mgr = new KDTSortManager(tblMain);
//	             
//	             if (sortType == KDTSortManager.SORT_ASCEND)
//	             {
//	            	 sortType = KDTSortManager.SORT_DESCEND;
//	             }
//	             else
//	             {
//	            	 sortType = KDTSortManager.SORT_ASCEND;
//	             }
//	             
//	             mgr.setSortAuto(true);
//	             
//	             tblMain.getColumn(e.getColIndex()).setSortable(true);
//	             
//	    		 mgr.sort(e.getColIndex(), this.sortType);
//    		 }
//             return;
//         }
//    	
    }
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	return;
    }
    protected String[] getNotOrderColumns() {
    
    	return new String[]{"curProject.name"};
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
		uiContext.put("MyFilter", myFilter);
		// IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.MODEL) ;
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow window = uiFactory.create(
				"com.kingdee.eas.fdc.finance.client.PaymentBillEditUI",
				uiContext, null, OprtState.VIEW);
		window.show();
	}
}