/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDButtonGroup;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.param.IOtherParam;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.base.param.client.ICustomParamUI;
import com.kingdee.eas.basedata.assistant.IVoucherType;
import com.kingdee.eas.basedata.assistant.VoucherTypeFactory;
import com.kingdee.eas.basedata.assistant.VoucherTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.IAccountView;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgTreeInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCParamInfo;
import com.kingdee.eas.fi.gl.client.InitClientHelp;

/**
 * output class name
 */
public class FDCParamEditUI extends AbstractFDCParamEditUI  implements IOtherParam, ICustomParamUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCParamEditUI.class);
    
	private CompanyOrgUnitInfo curCompany;
	//private IFDCParamFacade facade = null;
	FDCParamInfo paramInfo = null; 
    
    /**
     * output class constructor
     */
    public FDCParamEditUI() throws Exception
    {
        super();
    }
    
	public void onLoad() throws Exception {
		super.onLoad();

		initData();
		initMyIcon();		
	}
	
	private void initMyIcon() throws EASBizException, BOSException {
		String currentFIid = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		
		bptAccountview.setEditable(true);
		bptAccountview.setRefresh(true);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("isLeaf", new Integer(1)));
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", currentFIid));
		if(curCompany.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", curCompany.getAccountTable().getId().toString()));
		}
		filter.setMaskString("#0 and #1 and #2");
		view.setFilter(filter);
		bptAccountview.setEntityViewInfo(view);
		
		bptAccountview.setSelector(new AccountPromptBox(this, curCompany, curCompany.getAccountTable(), filter, false, true));

		
		bptVoucherType.setEditable(true);
		bptVoucherType.setRefresh(true);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		//filter.getFilterItems().add(new FilterItemInfo("isLeaf", new Integer(1)));
		view.setFilter(filter);
		
        //凭证条件是分配类的
        String cuid = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
        ObjectUuidPK pk = new ObjectUuidPK(cuid);
        FilterInfo dfilter = VoucherTypeFactory.getRemoteInstance().getDatabaseDFilter(pk, "id", "adminCU.id");

		if (dfilter != null) {
			filter.mergeFilter(dfilter, "and");
		}
		//传入的过滤条件
		if (filter != null) {
			filter.mergeFilter(filter, "and");
        }
      
        view.setFilter(filter);
        bptVoucherType.setEntityViewInfo(view);
	}
	
	//设置其他界面的参数
	private void initElement() throws BOSException, EASBizException {		

		if (paramInfo == null) {
			return;
		}
		
		//中间转换科目
		String s = paramInfo.getBase_AccountId();
		if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
			IAccountView ctrl = AccountViewFactory.getRemoteInstance();
			AccountViewInfo account = ctrl.getAccountViewInfo(new ObjectUuidPK(s));
			bptAccountview.setData(account);
		}
		
		//凭证类型
		s = paramInfo.getBase_VoucherTypeId();
		if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
			IVoucherType ctrl = VoucherTypeFactory.getRemoteInstance();
			VoucherTypeInfo account = ctrl.getVoucherTypeInfo(new ObjectUuidPK(s));
			bptVoucherType.setData(account);
		}
				
		//set budget ui element
		this.radioHit.setSelected(paramInfo.isBudgetHint());
		this.radioStrictCtrl.setSelected(!this.radioHit.isSelected());
		KDButtonGroup btnGroup=new KDButtonGroup();
		btnGroup.add(this.radioHit);
		btnGroup.add(this.radioStrictCtrl);		
	}

	private void initData() throws Exception {
		if (curCompany == null) {
			curCompany = InitClientHelp.getCurrentCompany();
		}
	}
	
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }


	public void otherParamSave() throws BOSException, EASBizException {

		save();
	}

	private boolean save() throws BOSException, EASBizException {
		
		HashMap prop = paramInfo.getProperties();
		storeField(prop);
		
		return true;
	}
	
	private void storeField(HashMap prop) throws EASBizException, BOSException {
		
		String str = null;
		// 1.本年利润科目
		AccountViewInfo account = (AccountViewInfo) bptAccountview.getData();
		if (account == null) {
			str = "none";
		} else {
			str = account.getId().toString();
		}
		
		ParamItemInfo itemInfo = ((ParamItemInfo) prop.get(FDCConstants.FDC_PARAM_ACCOUNTVIEW));
		if(itemInfo!=null){
			itemInfo.setValue(str);
			itemInfo.setIsControlSub(false);
			itemInfo.setIsModify(false);
			itemInfo.setOrgUnitID(curCompany);
	
			ParamItemFactory.getRemoteInstance().submit(itemInfo);
		}
		
		
		VoucherTypeInfo voucherType = (VoucherTypeInfo) bptVoucherType.getData();
		if (voucherType == null) {
			str = "none";
		} else {
			str = voucherType.getId().toString();
		}
		itemInfo = ((ParamItemInfo) prop.get(FDCConstants.FDC_PARAM_VOUCHERTYPE));
		if(itemInfo!=null){
			itemInfo.setValue(str);
			itemInfo.setIsControlSub(false);
			itemInfo.setIsModify(false);
			itemInfo.setOrgUnitID(curCompany);
			
			ParamItemFactory.getRemoteInstance().submit(itemInfo);
		}
		
		
		//save budget param
		itemInfo = ((ParamItemInfo) prop.get(FDCConstants.FDC_PARAM_BUDGET_STRICTCTRL));
		if(itemInfo!=null){
			itemInfo.setValue(Boolean.toString(this.radioStrictCtrl.isSelected()));
			itemInfo.setIsControlSub(false);
			itemInfo.setIsModify(false);
			itemInfo.setOrgUnitID(curCompany);
	
			ParamItemFactory.getRemoteInstance().submit(itemInfo);
		}
	}
	
	public void sendOrgInfo(OrgType orgType, OrgUnitInfo orgUnit, OrgTreeInfo orgTree) {

		try {
			if (curCompany == null) {
				curCompany = InitClientHelp.getCurrentCompany();
			}			
			setParams();			
			initElement();			
			
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}
	

	/**
	 * 描述：给定参数
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setParams() throws BOSException, EASBizException {
	
	
		HashMap paramItem = ParamControlFactory.getRemoteInstance().getParamHashMap(curCompany.getId().toString(),"com.kingdee.eas.fdc.finance.finance");

		paramInfo = new FDCParamInfo(paramItem);
	}

}