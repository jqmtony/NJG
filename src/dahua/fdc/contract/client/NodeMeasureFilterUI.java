/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class NodeMeasureFilterUI extends AbstractNodeMeasureFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(NodeMeasureFilterUI.class);
    //当前登陆组织
    private FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
    
    private ListUI listUI ;
    
    private ItemAction actionListOnLoad;
    
    private boolean isLoaded;
    
    /**
     * output class constructor
     */
    public NodeMeasureFilterUI() throws Exception
    {
        super();
    }
    
    public NodeMeasureFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
	public boolean verify() {
		if(this.prmtContract.getValue() == null){
			this.prmtContract.requestFocus(true);
			MsgBox.showWarning(this,"必须选择合同！");
			return false;
		}
		if(this.pkDateTo.getValue()!=null&&this.pkDateFrom
				.getValue() != null) {
			if (((Date) this.pkDateTo.getValue()).before((Date) this.pkDateFrom
					.getValue())) {
				this.pkDateFrom.requestFocus(true);
				MsgBox.showWarning(this,"结束日期不能晚于开始日期！");
				return false;
			}
		}
		return true;
	}

	private static final String KEY_CONTRACTID = "contractBill.id";
	private static final String KEY_BIZDATE = "bizDate";
	private static final String KEY_CREATETIME = "createTime";
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams  param = new FDCCustomerParams ();
//		Object[] conBill = (Object[])this.prmtContract.getValue();
		Object[] data = FDCHelper.getF7Data(prmtContract);
		if(data != null && !FDCHelper.isEmpty(data)){
			String contractId[] = new String[data.length];
			for(int i=0;i<data.length;i++){
				if(data[i] instanceof ContractBillInfo){
					contractId[i] = ((ContractBillInfo)data[i]).getId().toString();
					
				}
			}	
//			String id = ((ContractBillInfo)data).getId().toString();

			param.add(KEY_CONTRACTID, contractId[0]);
			
		}
//		param.add(KEY_CONTRACTID,((ContractBillInfo)this.prmtContract.getValue()).getId().toString());
		param.add(KEY_BIZDATE,(Date)this.pkDateFrom.getValue());
		param.add(KEY_CREATETIME,(Date)this.pkDateTo.getValue());
		
		return param.getCustomerParams();
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams param = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();
		String contractId = param.get(KEY_CONTRACTID);
		Date dateFrom = (Date)param.getDate(KEY_BIZDATE);
		Date dateTo = (Date)param.getDate(KEY_CREATETIME);
		filter.getFilterItems().add(new FilterItemInfo("bizDate",dateFrom,CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("createTime",dateTo,CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
		return filter;
	}

	public void setCustomerParams(CustomerParams cp) {
		if(cp == null )
		{
			return ;
		}
		FDCCustomerParams param = new FDCCustomerParams(cp);
		String contractId = param.get(KEY_CONTRACTID);
		if(contractId != null && !contractId.equals("")){
			try {
				ContractBillInfo contractBillInfo = ContractBillFactory
					.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				this.prmtContract.setValue(contractBillInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
				SysUtil.abort();
			} catch (BOSException e) {
				e.printStackTrace();
				SysUtil.abort();
			} catch (UuidException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		Date dateFrom = (Date)param.getDate(KEY_BIZDATE);
		Date dateTo = (Date)param.getDate(KEY_CREATETIME);
		this.pkDateFrom.setValue(dateFrom);
		this.pkDateTo.setValue(dateTo);
		
		super.setCustomerParams(param.getCustomerParams());
	}

	public void setFilterInfo(FilterInfo filterInfo) {
		super.setFilterInfo(filterInfo);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.prmtContract.setRequired(true);
		EntityViewInfo  view = this.prmtContract.getEntityViewInfo();
		if(view == null){
			view = new EntityViewInfo();
		}
		prmtContract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7Query");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("isMeasureContract",Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.longNumber",orgUnit.getLongNumber()+"%",CompareType.LIKE));
		view.setFilter(filter);
		prmtContract.setEntityViewInfo(view);
		prmtContract.setEnabledMultiSelection(false);
		if (!isLoaded) {
			this.clear();
		}
//		prmtContract.addSelectorListener(new SelectorListener() {
//			public void willShow(SelectorEvent e) {
//				KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
//				f7.getQueryAgent().setDefaultFilterInfo(null);
//				f7.getQueryAgent().setHasCUDefaultFilter(false);
//				f7.getQueryAgent().resetRuntimeEntityView();
//				EntityViewInfo view = new EntityViewInfo();
////				FilterInfo filter = new FilterInfo();
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(
//						new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//				filter.getFilterItems().add(
//						new FilterItemInfo("isMeasureContract",Boolean.TRUE));
//				filter.getFilterItems().add(
//						new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
//				filter.getFilterItems().add(
//						new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
//				view.setFilter(filter);
//				f7.setEntityViewInfo(view);
//			}
//		});
//		prmtContract.addCommitListener(new CommitListener(){
//			public void willCommit(CommitEvent e) {
//				KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
//				f7.getQueryAgent().setDefaultFilterInfo(null);
//				f7.getQueryAgent().setHasCUDefaultFilter(false);
//				f7.getQueryAgent().resetRuntimeEntityView();
//				EntityViewInfo view = new EntityViewInfo();
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(
//						new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//				filter.getFilterItems().add(
//						new FilterItemInfo("isMeasureContract",Boolean.TRUE));
//				filter.getFilterItems().add(
//						new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
//				filter.getFilterItems().add(
//						new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
//				view.setFilter(filter);
//				f7.setEntityViewInfo(view);
//			}
//		});
		isLoaded = true;
	}
	
	public void clear() {
		this.prmtContract.setValue(null);
		this.pkDateFrom.setValue(new Date());
		this.pkDateTo.setValue(new Date());
	}

}