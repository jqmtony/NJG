/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.port.equipment.base.ISpecialCheckItem;
import com.kingdee.eas.port.equipment.base.SpecialCheckItemCollection;
import com.kingdee.eas.port.equipment.base.SpecialCheckItemFactory;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.port.equipment.record.IEquId;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ImportEquIdUI extends AbstractImportEquIdUI
{
    private static final Logger logger = CoreUIObject.getLogger(ImportEquIdUI.class);
    
	private KDWorkButton selectAll;
	private KDWorkButton unselectAll;
	private KDWorkButton importAll;
	
	private int maxCount = 0;
    /**
     * output class constructor
     */
    public ImportEquIdUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		
		
		super.onLoad();
		
		IColumn Icolumn = this.tblMain.addColumn(0);
		Icolumn.setKey("selectAll");
		this.tblMain.getHeadRow(0).getCell("selectAll").setValue("全选");
		this.tblMain.getColumn("selectAll").getStyleAttributes().setLocked(false);
		
		KDCheckBox tblMain_selectAll_kdcheck = new KDCheckBox();
		tblMain_selectAll_kdcheck.setVisible(true);
		this.tblMain.getColumn("selectAll").setEditor(new KDTDefaultCellEditor(tblMain_selectAll_kdcheck));
		
		InitWorkButton();
		
		this.toolBar.removeAll();
		
		this.toolBar.add(this.btnAddNew);
		this.toolBar.add(this.btnQuery);
		this.toolBar.add(this.btnRefresh);
		this.btnAddNew.setVisible(false);
		
		this.setUITitle("引入设备");
		
		this.tblMain.addKDTDataFillListener(new KDTDataFillListener() {
            public void afterDataFill(KDTDataRequestEvent e)
            {
                try
                {
                    tblMain_afterDataFill(e);
                }
                catch(Exception exc)
                {
                    handUIException(exc);
                }
            }
        });
	}
	
	private void InitWorkButton()
	{
		this.importAll 	 = new KDWorkButton();
		this.importAll.setIcon(EASResource.getIcon("imgTbtn_affirm"));
		this.importAll.setText("引入设备");
		this.importAll.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				actionImportAll();
			}
			
		});
		
		this.selectAll	 = new KDWorkButton();
		this.selectAll.setIcon(EASResource.getIcon("imgTbtn_selectall"));
		this.selectAll.setText("全选");
		this.selectAll.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				actionSelectAll();
			}
			
		});
		
		this.unselectAll= new KDWorkButton();
		this.unselectAll.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		this.unselectAll.setText("全清");
		this.unselectAll.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				actionUnSelectAll();
			}
			
		});
		
		this.kDContainer1.addButton(this.importAll);
		this.kDContainer1.addButton(this.selectAll);
		this.kDContainer1.addButton(this.unselectAll);
	}
	
	private void actionImportAll() 
	{
		
		LongTimeDialog dialog = new LongTimeDialog((Dialog) SwingUtilities.getWindowAncestor(this));    
        dialog.setLongTimeTask(new ILongTimeTask() {    
            public Object exec() throws Exception {    
            	toBotp();
                return "";    
           }    
            
            public void afterExec(Object result) throws Exception {    
           }    
        });    
        Component[] cps=dialog.getContentPane().getComponents();    
        for(Component cp:cps){    
            if(cp instanceof JLabel){    
                ((JLabel) cp).setText("引入设备正在进行、请稍候.......");    
            }    
        }    
        dialog.show();  
	}
	
	private void tblMain_afterDataFill(KDTDataRequestEvent e){
		maxCount = e.getLastRow();
		
		for (int i = e.getFirstRow(); i <= e.getLastRow(); i++)
		{
			this.tblMain.getCell(i, "selectAll").setValue(Boolean.FALSE);
		}
	}
	
	private void toBotp() throws Exception
	{
		int selectIndex = 0;
		KDTable kdtable = null;
		if(getUIContext().get("kdtable")!=null)
		{
			kdtable = (KDTable)getUIContext().get("kdtable");
		}
		else
		{
			MsgBox.showError("出错！！！");SysUtil.abort();
		}
		
		ISpecialCheckItem Ispecial = SpecialCheckItemFactory.getRemoteInstance();
		IEquId IequId = EquIdFactory.getRemoteInstance();
		
		for (int i = 0; i <= maxCount; i++) 
		{
			if(UIRuleUtil.isNull(this.tblMain.getCell(i, "selectAll").getValue())||!UIRuleUtil.getBoolean(this.tblMain.getCell(i, "selectAll").getValue())){continue;}
			String selectId = UIRuleUtil.getString(this.tblMain.getCell(i, "id").getValue());
			IRow row = kdtable.addRow();
			
			EquIdInfo equIdInfo = IequId.getEquIdInfo(new ObjectUuidPK(selectId));
			
			row.getCell("zdaNumber").setValue(equIdInfo);
			
			if(getUIContext().get("yearPlan")!=null)
			{
				row.getCell("equipmentName").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"name")));
				row.getCell("code").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"code")));
				row.getCell("useUnit").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"usingDept.name")));
				row.getCell("state").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"tzsbStatus")));
				row.getCell("address").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"address.name")));
				row.getCell("companyNumber").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"innerNumber")));
				row.getCell("NO").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"model")));
				row.getCell("engineNumber").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"engineNumber")));
				row.getCell("carNumber").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"carNumber")));
				row.getCell("weight").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"ratedWeight")));
				row.getCell("useDate").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"qyDate")));
				row.getCell("createUnit").setValue(UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"mader")));
			}
			else
			{
				String equName = UIRuleUtil.getString(UIRuleUtil.getProperty((IObjectValue)row.getCell("zdaNumber").getValue(),"name"));
				row.getCell("equipmentName").setValue(equName);
				
				if(equIdInfo.getEqmType()!=null)
				{
					String oql = "select id,name,number where type.id='"+equIdInfo.getEqmType().getId()+"'";
					SpecialCheckItemCollection specialCheckItemCollection = Ispecial.getSpecialCheckItemCollection(oql);
					for (int j = 0; j < specialCheckItemCollection.size(); j++) 
					{
						if(j==0)
						{
							row.getCell("noCheckItem").setValue(specialCheckItemCollection.get(j));
						}
						else
						{
							IRow itemrow = kdtable.addRow();
							itemrow.getCell("zdaNumber").setValue(equIdInfo);
							itemrow.getCell("equipmentName").setValue(equName);
							itemrow.getCell("noCheckItem").setValue(specialCheckItemCollection.get(j));
						}
					}
				}
			}
			selectIndex+=1;
		}
		
		if(getUIContext().get("yearPlan")==null)
		{
			ToolHelp.mergeThemeRow(kdtable, "zdaNumber");
		}
		if(selectIndex==0)
		{
			MsgBox.showWarning("没有选中的设备档案，请检查！");SysUtil.abort();
		}
		
		getUIWindow().close();
		
		
		com.kingdee.eas.util.client.MsgBox.showWarning("本次成功加入{"+selectIndex+"}条设备！");
	}
	
	private void actionSelectAll()
	{
		int maxRowindex = 500;
		if(maxCount>maxRowindex)
		{
			for (int i = 0; i <=maxCount; i++)
			{
				if(i>=maxRowindex){break;}
				this.tblMain.getCell(i, "selectAll").setValue(Boolean.TRUE);
			}
			MsgBox.showWarning("数据过大，默认选中{"+maxRowindex+"}行！");SysUtil.abort();
		}
		else
		{
			for (int i = 0; i < this.tblMain.getRowCount(); i++) {
				if(i>=maxRowindex){MsgBox.showWarning("数据过大，默认选中{"+maxRowindex+"}行！");SysUtil.abort(); break;}
				this.tblMain.getCell(i, "selectAll").setValue(Boolean.TRUE);
			}
		}
	}
	
	private void actionUnSelectAll()
	{
		for (int i = 0; i <=maxCount; i++) {
			this.tblMain.getCell(i, "selectAll").setValue(Boolean.FALSE);
		}
	}
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,EntityViewInfo view) {
		EntityViewInfo viewInfo = (EntityViewInfo)view.clone();
		FilterInfo filterInfo = new FilterInfo();
		if(getUIContext().get("equID")!=null&&((Set)getUIContext().get("equID")).size()>0)
			filterInfo.getFilterItems().add(new FilterItemInfo("id",getUIContext().get("equID"),CompareType.NOTINCLUDE));
		if(getUIContext().get("yearPlan")!=null)
			filterInfo.getFilterItems().add(new FilterItemInfo("special","1",CompareType.EQUALS));
		    filterInfo.getFilterItems().add(new FilterItemInfo("sbStatus","3",CompareType.NOTEQUALS));
		
		try {
			if(viewInfo.getFilter()!=null)
				viewInfo.getFilter().mergeFilter(filterInfo, "and");
			else
				viewInfo.setFilter(filterInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		return super.getQueryExecutor(pk, viewInfo);
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.record.EquIdFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.record.EquIdInfo objectValue = new com.kingdee.eas.port.equipment.record.EquIdInfo();
		
        return objectValue;
    }

}