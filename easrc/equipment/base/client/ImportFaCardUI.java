package com.kingdee.eas.port.equipment.base.client;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.fi.fa.manage.FaCurCardFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

public class ImportFaCardUI extends AbstractImportFaCardUI{

	private KDWorkButton selectAll;
	private KDWorkButton unselectAll;
	private KDWorkButton importAll;
	
	private int maxCount = 0;
	
	public ImportFaCardUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		
		
		super.onLoad();
		
		IColumn Icolumn = this.tblMain.addColumn(1);
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
		
		this.kDContainer1.setTitle("固定资产信息");
		this.setUITitle("固定资产引入设备档案");
		
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
		this.importAll.setText("导入设备档案");
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
                ((JLabel) cp).setText("设备档案引入正在进行、请稍候.......");    
            }    
        }    
        dialog.show();  
        com.kingdee.eas.util.client.MsgBox.showInfo("引入成功！");SysUtil.abort();
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
		BOTMappingInfo botInfo = (BOTMappingInfo)BOTMappingFactory.getRemoteInstance().getValue("where name='NJ0001'");
		if(botInfo== null)
		{
			MsgBox.showWarning("规则错误，请检查BOTP！");SysUtil.abort();
		}
		int selectIndex = 0;
		for (int i = 0; i <= maxCount; i++) {
			if(UIRuleUtil.isNull(this.tblMain.getCell(i, "selectAll").getValue())||!UIRuleUtil.getBoolean(this.tblMain.getCell(i, "selectAll").getValue())){continue;}
			String selectId = UIRuleUtil.getString(this.tblMain.getCell(i, "id").getValue());
			CoreBillBaseCollection cordCollect = FaCurCardFactory.getRemoteInstance().getCoreBillBaseCollection("select id where id='"+selectId+"'");
			
			ToolHelp.generateDestBill("42AC39EC", "0ED4BEC2",cordCollect , new ObjectUuidPK(botInfo.getId()));
			selectIndex+=1;
		}
		
		if(selectIndex==0)
		{
			MsgBox.showWarning("没有选中的资产，请检查！");SysUtil.abort();
		}
		
		
		refresh(null);
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
		for (int i = 0; i <= maxCount; i++) {
			this.tblMain.getCell(i, "selectAll").setValue(Boolean.FALSE);
		}
	}
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,EntityViewInfo view) {
		EntityViewInfo viewInfo = (EntityViewInfo)view.clone();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id",getExcludeSourceBillID(),CompareType.NOTINNER));
		
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
	
	private String getExcludeSourceBillID()
	{
		return "select fsourceBillID from CT_REC_EquId where fsourceBillID is not null";
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}

}
