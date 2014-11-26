/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.client.f7.CompanyF7;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.client.ExpenseTypePromptBox;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.ma.budget.BgControlSchemeCollection;
import com.kingdee.eas.ma.budget.BgControlSchemeFactory;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgItemEntryCollection;
import com.kingdee.eas.ma.budget.BgItemEntryFactory;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;

/**
 * output class name
 */
public class AccActOnLoadBgFilterUI extends AbstractAccActOnLoadBgFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccActOnLoadBgFilterUI.class);
    public AccActOnLoadBgFilterUI() throws Exception
    {
        super();
    }
	public boolean verify()
    {
        if(this.prmtCostedCompany.getValue()==null)
        {
            FDCMsgBox.showWarning("预算承担公司不能为空！");
            return false;
        }
        if(this.prmtCostedDept.getValue()==null)
        {
            FDCMsgBox.showWarning("预算承担部门不能为空！");
            return false;
        }
        if(this.prmtBgItem.getValue()==null)
        {
            FDCMsgBox.showWarning("预算项目不能为空！");
            return false;
        }
        return true;
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
		 if(this.prmtCostedCompany.getValue()!=null){
        	 pp.setObject("costedCompany", this.prmtCostedCompany.getValue());
         }else{
        	 pp.setObject("costedCompany", null);
         }
         if(this.prmtCostedDept.getValue()!=null){
        	 pp.setObject("costedDept", this.prmtCostedDept.getValue());
         }else{
        	 pp.setObject("costedDept", null);
         }
         Object[] bgItem = (Object[])this.prmtBgItem.getValue();
         if(bgItem!=null ){
			 pp.setObject("bgItem",bgItem);
		 }else{
			 pp.setObject("bgItem",null);
		 }
		 return pp;
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.prmtBgItem.setValue(null);
		this.prmtCostedCompany.setValue(null);
		this.prmtCostedDept.setValue(null);
	}
	public void onInit(RptParams params) throws Exception {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		CompanyOrgUnitInfo com=(CompanyOrgUnitInfo)this.prmtCostedCompany.getValue();
		if(com!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",getCostedDeptIdSet(com),CompareType.INCLUDE));
		}
		view.setFilter(filter);
        this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
        this.prmtCostedDept.setEntityViewInfo(view);
        this.prmtCostedDept.setEditFormat("$number$");
        this.prmtCostedDept.setDisplayFormat("$name$");
        this.prmtCostedDept.setCommitFormat("$number$");
        
        view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		view.setFilter(filter);
        this.prmtCostedCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
        CompanyF7 comp = new CompanyF7(this);
        this.prmtCostedCompany.setEntityViewInfo(view);
        this.prmtCostedCompany.setSelector(comp);
        this.prmtCostedCompany.setDisplayFormat("$name$");
        this.prmtCostedCompany.setEditFormat("$number$");
        this.prmtCostedCompany.setCommitFormat("$number$");
        
		this.prmtBgItem.setDisplayFormat("$name$");
		this.prmtBgItem.setEditFormat("$number$");
		this.prmtBgItem.setCommitFormat("$number$");
		NewBgItemDialog bgItemDialog=new NewBgItemDialog(this);
		bgItemDialog.setMulSelect(true);
		bgItemDialog.setSelectCombinItem(false);
		this.prmtBgItem.setSelector(bgItemDialog);
		this.prmtBgItem.setEnabledMultiSelection(true);
		this.prmtBgItem.setQueryInfo("com.kingdee.eas.ma.budget.BgItemQuery");
		
        if(this.prmtCostedCompany.getValue()!=null){
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.prmtCostedDept.setEnabled(false);
		}
        if(this.prmtCostedDept.getValue()!=null){
			this.prmtBgItem.setEnabled(true);
		}else{
			this.prmtBgItem.setEnabled(false);
		}
	}
	public void setCustomCondition(RptParams params) {
		if(params.getObject("costedCompany")!=null){
			this.prmtCostedCompany.setValue(params.getObject("costedCompany"));
		}else{
			this.prmtCostedCompany.setValue(null);
		}
		if(params.getObject("costedDept")!=null){
			this.prmtCostedDept.setValue(params.getObject("costedDept"));
		}else{
			this.prmtCostedDept.setValue(null);
		}
		if(params.getObject("bgItem")!=null){
			this.prmtBgItem.setValue(params.getObject("bgItem"));
		}else{
			this.prmtBgItem.setValue(null);
		}
	}
    protected void prmtCostedCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		CompanyOrgUnitInfo com=(CompanyOrgUnitInfo)this.prmtCostedCompany.getValue();
		if(com!=null){
			if(!com.isIsLeaf()){
				FDCMsgBox.showWarning(this,"请选择明细预算承担公司！");
				this.prmtCostedCompany.setValue(null);
				return;
			}
			Set id=getCostedDeptIdSet(com);
			if(this.prmtCostedDept.getValue()!=null){
				if(id.contains(((CostCenterOrgUnitInfo)prmtCostedDept.getValue()).getId().toString())){
					return;
				}else{
					this.prmtCostedDept.setValue(null);
				}
			}
			filter.getFilterItems().add(new FilterItemInfo("id",getCostedDeptIdSet(com),CompareType.INCLUDE));
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.prmtCostedDept.setValue(null);
			this.prmtCostedDept.setEnabled(false);
		}
		view.setFilter(filter);
        this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
        this.prmtCostedDept.setEntityViewInfo(view);
        this.prmtCostedDept.setEditFormat("$number$");
        this.prmtCostedDept.setDisplayFormat("$name$");
        this.prmtCostedDept.setCommitFormat("$number$");
    }
    protected Set getCostedDeptIdSet(CompanyOrgUnitInfo com) throws EASBizException, BOSException{
		if(com==null) return null;
		Set id=new HashSet();
		String longNumber=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(com.getId())).getLongNumber();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber",longNumber+"!%",CompareType.LIKE));
		view.setFilter(filter);
		FullOrgUnitCollection col=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		for(int i=0;i<col.size();i++){
			if(col.get(i).getPartCostCenter()!=null){
				id.add(col.get(i).getId().toString());
			}
		}
		return id;
	}
	protected void prmtCostedDept_dataChanged(DataChangeEvent e) throws Exception {
		if(this.prmtCostedDept.getValue()!=null){
			this.prmtBgItem.setEnabled(true);
		}else{
			this.prmtBgItem.setValue(null);
			this.prmtBgItem.setEnabled(false);
		}
	}
	protected void prmtBgItem_dataChanged(DataChangeEvent e) throws Exception {
		if(this.prmtBgItem.getValue()!=null&&this.prmtBgItem.getValue() instanceof BgItemObject){
			BgItemObject bgItemObject=(BgItemObject) prmtBgItem.getValue();
			Set bgItem=new HashSet();
			Set bgItemId=new HashSet();
			for(int i=0;i<bgItemObject.getResult().size();i++){
				if(bgItemObject.getResult().get(i).isIsLeaf()){
					if(!bgItemId.contains(bgItemObject.getResult().get(i).getId())){
						bgItem.add(bgItemObject.getResult().get(i));
						bgItemId.add(bgItemObject.getResult().get(i).getId());
					}
				}else{
					BgItemCollection col=BgItemFactory.getRemoteInstance().getBgItemCollection("select *,bgItem.* from where isLeaf=1 and longNumber like '"+bgItemObject.getResult().get(i).getLongNumber()+"%'");
					for(int k=0;k<col.size();k++){
						if(!bgItemId.contains(bgItemObject.getResult().get(i).getId())){
							bgItem.add(col.get(k));
							bgItemId.add(col.get(k).getId());
						}
					}
				}
			}
			this.prmtBgItem.setValue(bgItem.toArray());
		}
	}
}