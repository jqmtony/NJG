/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.math.BigDecimal;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.forewarn.CompareType;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.contract.ContractBillInfo;

/**
 * output class name
 */
public class PartAMaterialDefaultFilterUI extends AbstractPartAMaterialDefaultFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(PartAMaterialDefaultFilterUI.class);
    
    /**
     * output class constructor
     */
    private Set curPrjSet;
   
    
    public Set getCurPrjSet() {
		return curPrjSet;
	}

	public void setCurPrjSet(Set curPrjSet) {
		this.curPrjSet = curPrjSet;
	}

	public void onLoad() throws Exception {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		
		
		prmtContract.setSelectorCollection(sic);
		prmtContract.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				EntityViewInfo view = null;
				FilterInfo filter = null;
				prmtContract.getQueryAgent().resetRuntimeEntityView();
				if(prmtContract.getEntityViewInfo() != null){
					view = prmtContract.getEntityViewInfo();
					filter = new FilterInfo();
					if(getCurPrjSet()!= null && getCurPrjSet().size()>0){
						filter.getFilterItems().add(new FilterItemInfo("curProject.id",getCurPrjSet(),com.kingdee.bos.metadata.query.util.CompareType.INCLUDE));
					}
//					filter.getFilterItems().add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("isPartAMaterialCon",Boolean.FALSE));
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
					view.setFilter(filter);
				}else{
					view = new EntityViewInfo();
					filter = new FilterInfo();
					if(getCurPrjSet()!= null && getCurPrjSet().size()>0){
						filter.getFilterItems().add(new FilterItemInfo("curProject.id",getCurPrjSet(),com.kingdee.bos.metadata.query.util.CompareType.INCLUDE));
					}
//					filter.getFilterItems().add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("isPartAMaterialCon",Boolean.FALSE));
					filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
					view.setFilter(filter);
					prmtContract.setEntityViewInfo(view);
					prmtContract.getQueryAgent().resetRuntimeEntityView();
				}
				if(view != null){
				prmtContract.setEntityViewInfo(view);
				prmtContract.getQueryAgent().resetRuntimeEntityView();
				}
			}});
		
    }
    
    public PartAMaterialDefaultFilterUI() throws Exception
    {
        super();
    }

    public CustomerParams getCustomerParams() {
		// TODO Auto-generated method stub
    	FDCCustomerParams param = new FDCCustomerParams();
    	if(prmtContract.getData() != null){
    		param.add("mainContractName", ((ContractBillInfo)prmtContract.getData()).getName());
    		param.add("mainContractId", ((ContractBillInfo)prmtContract.getData()).getId().toString());
    		param.add("mainContractNumber", ((ContractBillInfo)prmtContract.getData()).getNumber());
    	}
        if(txtMaterialName.getText().trim().length()>0){
        	param.add("materialName", txtMaterialName.getText().trim());
        }
        if(txtMaterialNumber.getText().trim().length()>0){
        	param.add("materialNumber", txtMaterialNumber.getText().trim());
        }
        if(txtModel.getText().trim().length()>0){
        	param.add("model", txtModel.getText().trim());
        }
        if(txtUnit.getText().trim().length()>0){
        	param.add("unitName", txtUnit.getText().trim());
        }
    	
        if(combEquals.getSelectedItem() != null){
        	param.add("PlanNumberCondition", ((CompareType)combEquals.getSelectedItem()).getValue());
        }
        if(combE1.getSelectedItem() != null){
        	param.add("enterNumberPlanCondition", ((CompareType)combE1.getSelectedItem()).getValue());
        }
        if(comE2.getSelectedItem() != null){
        	param.add("notEnterNumberCondition", ((CompareType)comE2.getSelectedItem()).getValue());
        }
        
        if(txtPlanNumber.getText().length()>0){
        	param.add("planNumber", txtPlanNumber.getText().trim());
        }
        
        if(txtEnterNumber.getText().trim().length()>0){
        	param.add("enterNumber",txtEnterNumber.getText().trim());
        }
        
        if(txtNotEnterNumber.getText().trim().length()>0){
        	param.add("notEnterNumber",txtNotEnterNumber.getText().trim());
        }
        
    	param.add("isLike", isIncludeLike.isSelected());
		return param.getCustomerParams();
	}

    
    
    
	public FilterInfo getFilterInfo() {
		
		FilterInfo filter = new FilterInfo();
		if(prmtContract.getData() != null){
    		filter.getFilterItems().add(new FilterItemInfo("mainContractId",((ContractBillInfo)prmtContract.getData()).getId().toString()));
    	}
        if(txtMaterialName.getText().trim().length()>0){
        	filter.getFilterItems().add(new FilterItemInfo("materialName",txtMaterialName.getText().trim()));
        }
        if(txtMaterialNumber.getText().trim().length()>0){
        	filter.getFilterItems().add(new FilterItemInfo("materialNumber",txtMaterialNumber.getText().trim()));
        }
        if(txtModel.getText().trim().length()>0){
        	filter.getFilterItems().add(new FilterItemInfo("model",txtModel.getText().trim()));
        }
        if(txtUnit.getText().trim().length()>0){
        	filter.getFilterItems().add(new FilterItemInfo("unitName",txtUnit.getText().trim()));
        }
    	
        if(combEquals.getSelectedItem() != null){
        	filter.getFilterItems().add(new FilterItemInfo("planNumberCondition",((CompareType)combEquals.getSelectedItem()).getValue()));
        }
        if(combE1.getSelectedItem() != null){
        	filter.getFilterItems().add(new FilterItemInfo("enterNumberCondition",((CompareType)combE1.getSelectedItem()).getValue()));
        }
        if(comE2.getSelectedItem() != null){
        	filter.getFilterItems().add(new FilterItemInfo("notEnterNumberCondition",((CompareType)comE2.getSelectedItem()).getValue()));
        }
        
        if(txtPlanNumber.getText().trim().length()>0){
        	filter.getFilterItems().add(new FilterItemInfo("planNumber",txtPlanNumber.getText().trim()));
        }
        
        if(txtEnterNumber.getText().trim().length()>0){
        	filter.getFilterItems().add(new FilterItemInfo("enterNumber",txtEnterNumber.getText().trim()));
        }
//        
        if(txtNotEnterNumber.getText().trim().length()>0){
        	filter.getFilterItems().add(new FilterItemInfo("notEnterNumber",txtNotEnterNumber.getText().trim()));
        }
        
        filter.getFilterItems().add(new FilterItemInfo("isLike",Boolean.valueOf(isIncludeLike.isSelected())));
		return filter;
	}

	
	
	public void clear() {
		this.prmtContract.setData(null);
		this.txtEnterNumber.setText(null);
		this.txtPlanNumber.setText(null);
		this.txtNotEnterNumber.setText(null);
		this.txtMaterialName.setText(null);
		this.txtMaterialNumber.setText(null);
		this.txtModel.setText(null);
		this.txtUnit.setText(null);
		this.isIncludeLike.setSelected(false);
//		super.clear();
	}

	public void setCustomerParams(CustomerParams cp) {
		// TODO Auto-generated method stub
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		isIncludeLike.setSelected(para.getBoolean("isLike"));
		if(para.get("mainContractNumber") != null && para.get("mainContractName")!=null &&para.get("mainContractId")!= null){
		    ContractBillInfo contract = new ContractBillInfo();
		    contract.setId(BOSUuid.read(para.get("mainContractId")));
		    contract.setName(para.get("mainContractName"));
		    contract.setNumber(para.get("mainContractNumber"));
			this.prmtContract.setData(contract);
		}
		if(para.get("enterNumber") != null)
		this.txtEnterNumber.setText(para.get("enterNumber"));
		if(para.get("planNumber") != null)
		this.txtPlanNumber.setText(para.get("planNumber"));
		if(para.get("notEnterNumber") != null)
		this.txtNotEnterNumber.setText(para.get("notEnterNumber"));
		if(para.get("materialName") != null)
		this.txtMaterialName.setText(para.get("materialName"));
		if(para.get("materialNumber") != null)
		this.txtMaterialNumber.setText(para.get("materialNumber"));
		if(para.get("model") != null)
		this.txtModel.setText(para.get("model"));
		if(para.get("unitName") != null)
		this.txtUnit.setText(para.get("unitName"));
		super.setCustomerParams(para.getCustomerParams());
//		super.setCustomerParams(cp);
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

}