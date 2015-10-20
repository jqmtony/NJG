/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;

/**
 * output class name
 */
public class ContractCostInfoUI extends AbstractContractCostInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractCostInfoUI.class);
    
    /**
     * output class constructor
     */
    public ContractCostInfoUI() throws Exception
    {
        super();
    }

    protected void tblCost_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if(helper==null) return ;
    	helper.tblCost_tableClicked(this,e);
    }

	/**
	 * 根据id显示窗体
	 */
	public static void showEditUI(IUIObject ui, UIContext uiContext, String oprt)
			throws UIException {

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractCostInfoUI.class.getName(), uiContext, null,
				oprt);
		uiWindow.show();
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		initControl();		
		loadData() ;
		tblCost.getStyleAttributes().setLocked(true);
	}
	
	private void initControl() {		
		tblCost.checkParsed();
		FDCHelper.formatTableNumber(tblCost, "aimCost");
		FDCHelper.formatTableNumber(tblCost, "hasHappen");
		FDCHelper.formatTableNumber(tblCost, "intendingHappen");
		FDCHelper.formatTableNumber(tblCost, "dynamicCost");
		FDCHelper.formatTableNumber(tblCost, "chayi");
		FDCHelper.formatTableNumber(tblCost, "conSplit");
	}
	
	ContractCostHelper helper = null;
	
	private void loadData() throws Exception {
		Map acctMap = new HashMap();
		String contractId  = (String)getUIContext().get(UIContext.ID);
		//获取当前合同的拆分科目			
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id",contractId));
		filter.getFilterItems().add(new FilterItemInfo("parent.isInvalid",new Integer(0)));
		view.setFilter(filter);
		ContractCostSplitEntryCollection col = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			ContractCostSplitEntryInfo element = (ContractCostSplitEntryInfo) iter.next();
			
			String costAcctId = element.getCostAccount().getId().toString();
			if(acctMap.containsKey(costAcctId)){
				continue ;
			}	
			acctMap.put(costAcctId,element);
		}		
			
		helper = new ContractCostHelper(tblCost,contractId);
		helper.fillCostTable() ;
	}
}