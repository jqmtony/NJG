/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subill.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.port.markesupplier.subase.MarketLevelSetUpInfo;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SupplierLevelUI extends AbstractSupplierLevelUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierLevelUI.class);
    
    /**
     * output class constructor
     */
    public SupplierLevelUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if(!this.cbIsOver.isSelected()&&this.prmtLevel.getValue()==null){
    		MsgBox.showWarning(this,"供应商级别不能为空！");
    		return;
    	}
    	ArrayList id=(ArrayList)this.getUIContext().get("id");
    	if(id!=null){
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("level");
    		for(int i=0;i<id.size();i++){
        		MarketSupplierStockInfo info=MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(id.get(i).toString()));
        		if(this.cbIsOver.isSelected()){
        			info.setLevel(null);
        		}else{
        			info.setLevel((MarketLevelSetUpInfo) this.prmtLevel.getValue());
        		}
        		MarketSupplierStockFactory.getRemoteInstance().updatePartial(info, sel);
    		}
    		MsgBox.showInfo(this,"操作成功！");
    		this.getUIWindow().close();
    	}
    }
    protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.getUIWindow().close();
    }
    
    protected void cbIsOver_actionPerformed(ActionEvent e) throws Exception {if(this.cbIsOver.isSelected()){
		this.prmtLevel.setEnabled(false);
		this.prmtLevel.setValue(null);
	}else{
		this.prmtLevel.setEnabled(true);
	}}

}