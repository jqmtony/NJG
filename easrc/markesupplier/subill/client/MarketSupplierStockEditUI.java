/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subill.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.core.fm.ClientVerifyHelper;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListCollection;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierPersonCollection;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierPersonFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierPersonInfo;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo;
import com.kingdee.eas.port.markesupplier.subase.SupplierState;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherCollection;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherFactory;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttCollection;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockEntryAttInfo;
import com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsFactory;
import com.kingdee.eas.port.pm.base.EvaluationIndicatorsInfo;
import com.kingdee.eas.port.pm.base.EvaluationTemplateEntryInfo;
import com.kingdee.eas.port.pm.base.EvaluationTemplateInfo;
import com.kingdee.eas.port.pm.base.IEvaluationIndicators;
import com.kingdee.eas.port.pm.invite.WinInviteReportUnitCollection;
import com.kingdee.eas.port.pm.invite.WinInviteReportUnitFactory;
import com.kingdee.eas.port.pm.invite.client.WinInviteReportListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketSupplierStockEditUI extends AbstractMarketSupplierStockEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierStockEditUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierStockEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    
    public void onLoad() throws Exception {
    	setEnable();
    	super.onLoad();
    	this.kDPanel4.setPreferredSize(new Dimension(1013,1010));		
        this.kDPanel4.setMinimumSize(new Dimension(1013,1010));
        this.kDTabbedPane1.remove(kDPanel2);
        this.kDTabbedPane1.remove(kDPanel8);
        this.kdtE4.getColumn("EvaluationIndex").getStyleAttributes().setLocked(true);
        this.kdtE4.getColumn("Description").setRequired(true);
        
    	initButton();
    	
    	setF7Filter();
    	
    	setRequiredState();
    	
    	if(editData.getId()!=null)
    	{
    		Set idSet = new HashSet();
    		String oql = "where unitName.id = '"+editData.getId()+"'";
    		WinInviteReportUnitCollection collection = WinInviteReportUnitFactory.getRemoteInstance().getWinInviteReportUnitCollection(oql);
    		for (int i = 0; i < collection.size(); i++) 
    			idSet.add(collection.get(i).getParent().getId());
//    		
    		KDScrollPane panel=new KDScrollPane();
			panel.setName("中标单位核备表");
			panel.setMinimumSize(new Dimension(1013,600));		
			panel.setPreferredSize(new Dimension(1013,600));
	        this.kDTabbedPane1.add(panel,"中标单位核备表");
	        
	        if(idSet.size()>0)
	        {
	        	UIContext uiContext = new UIContext(this);
	        	uiContext.put("IDSET", idSet);
	        	panel.setViewportView((Component)UIFactoryHelper.initUIObject(WinInviteReportListUI.class.getName(), uiContext, null,OprtState.VIEW));
	        	panel.setKeyBoardControl(true);
	        	panel.setEnabled(false);
	        
	        }
    	}
    	
    	if(editData.getId()!=null)
    	{
    		Set idSet = new HashSet();
    		String oql = "where Supplier.id = '"+editData.getId()+"'";
    		MarketSupplierReviewGatherCollection collection = MarketSupplierReviewGatherFactory.getRemoteInstance().getMarketSupplierReviewGatherCollection(oql);
    		for (int i = 0; i < collection.size(); i++) 
    			idSet.add(collection.get(i).getId());
//    		
    		KDScrollPane panel=new KDScrollPane();
			panel.setName("供应商评审");
			panel.setMinimumSize(new Dimension(1013,600));		
			panel.setPreferredSize(new Dimension(1013,600));
	        this.kDTabbedPane1.add(panel,"供应商评审");
	        
	        if(idSet.size()>0)
	        {
	        	UIContext uiContext = new UIContext(this);
	        	uiContext.put("IDSET", idSet);
	        	panel.setViewportView((Component)UIFactoryHelper.initUIObject(MarketSupplierReviewGatherListUI.class.getName(), uiContext, null,OprtState.VIEW));
	        	panel.setKeyBoardControl(true);
	        	panel.setEnabled(false);
	        
	        }
    	}
    	
    }
    
    private void setF7Filter()
    {
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtInviteType.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtQuaLevel.setEntityViewInfo(view);
		this.prmtVisibility.setEntityViewInfo(view);
		this.prmtServiceType.setEntityViewInfo(view);
		this.prmtSupplierSplAreaEntry.setEntityViewInfo(view);
		this.prmtSupplierFileType.setEntityViewInfo(view);
		this.prmtSupplierBusinessMode.setEntityViewInfo(view);
    }
    
    private void setEnable()
    {
    	this.pkauditDate.setEnabled(false);
    	this.State.setEnabled(false);
    	this.contLastUpdateUser.setVisible(true);
    	this.contLastUpdateTime.setVisible(true);
    	this.contLevel.setVisible(false);
    }
    
    private void setRequiredState()
    {
    	this.txtNumber.setRequired(true);
    	this.txtsupplierName.setRequired(true);
    	this.prmtInviteType.setRequired(true);
    	this.prmtPurchaseOrgUnit.setRequired(true);
    	this.txtLinkPhone.setRequired(true);
    	this.prmtEvatemp.setRequired(true);
//    	this.prmtQuaLevel.setRequired(true);
//    	this.prmtVisibility.setRequired(true);
    	this.txtEnterpriseMaster.setRequired(true);
//    	this.txtboEnterpriseKind.setRequired(true);
//    	this.prmtServiceType.setRequired(true);
//    	this.prmtSupplierSplAreaEntry.setRequired(true);
    	this.prmtSupplierFileType.setRequired(true);
    	
    	this.kdtEntrys.getColumn("peopleSum").setRequired(true);
    	this.kdtEntryPerson.getColumn("personName").setRequired(true);
    	this.kdtEntryPerson.getColumn("isDefault").setRequired(true);
    	this.kdtEntryPerson.getColumn("phone").setRequired(true);
    	this.kdtEntryAtt.getColumn("attNumber").setRequired(true);
    	this.kdtEntryAtt.getColumn("attName").setRequired(true);
    }
    
    private void initButton()
    {
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		
    	this.actionSubmit.putValue("SmallIcon", EASResource.getIcon("imgTbtn_submit"));
    	this.actionSave.putValue("SmallIcon", EASResource.getIcon("imgTbtn_save"));
    	
    	this.actionCopy.setVisible(true);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	
    	this.menuView.setVisible(false);
    	
    	this.menuItemSave.setVisible(true);
    	this.menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
    	this.menuItemSubmit.setVisible(true);
    	this.menuItemSubmit.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
    	this.menuItemAddNew.setAccelerator(null);
    	this.actionCreateTo.setVisible(false);//推式生成
		this.actionCreateFrom.setVisible(false);//拉式生成
//		this.actionWorkFlowG.setVisible(false);//流程图
		this.actionTraceUp.setVisible(false);//上查
		this.actionTraceDown.setVisible(false);//下查 
		this.actionFirst.setVisible(false);//第一
		this.actionRemove.setVisible(false);//第一
		this.actionPre.setVisible(false);//前一
		this.actionNext.setVisible(false);//后一
		this.actionLast.setVisible(false);//最后一个
//		this.actionAuditResult.setVisible(false);//审批结果查看
//		this.actionMultiapprove.setVisible(false);//多级审批
//		this.actionNextPerson.setVisible(false);//下一步处理人
    	
		if(!SupplierState.Save.equals(this.editData.getState())&&!SupplierState.submit.equals(this.editData.getState())){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if(SupplierState.submit.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
		this.actionAddNew.setVisible(false);
    	
		this.kDContainer6.getContentPane().add(this.kdtE4,BorderLayout.CENTER);
    	this.kDContainer3.getContentPane().add(this.kdtEntrys,BorderLayout.CENTER);
    	this.kDContainer4.getContentPane().add(this.kdtEntryPerson,BorderLayout.CENTER);
    	this.kDContainer5.getContentPane().add(this.kdtEntryAtt,BorderLayout.CENTER);
    	
    	initTableButton(this.kDContainer6, this.kdtE4_detailPanel);
    	initTableButton(this.kDContainer3, this.kdtEntrys_detailPanel);
    	initTableButton(this.kDContainer4, this.kdtEntryPerson_detailPanel);
    	initTableButton(this.kDContainer5, this.kdtEntryAtt_detailPanel);
    	
    	this.kdtEntryAtt.getColumn("attlist").getStyleAttributes().setLocked(true);
  		this.kdtEntryAtt.getColumn("attlist").getStyleAttributes().setFontColor(Color.BLUE);
  		
  		ActionListener[] actions = kdtEntryAtt_detailPanel.getRemoveLinesButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtEntryAtt_detailPanel.getRemoveLinesButton().removeActionListener(actions[0]);
		actions = kdtEntryAtt_detailPanel.getAddNewLineButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtEntryAtt_detailPanel.getAddNewLineButton().removeActionListener(actions[0]);
		actions = kdtEntryAtt_detailPanel.getInsertLineButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtEntryAtt_detailPanel.getInsertLineButton().removeActionListener(actions[0]);

  		this.kdtEntryAtt_detailPanel.getRemoveLinesButton().addActionListener(new ActionListener()
  		{
			public void actionPerformed(ActionEvent e) 
			{
				kdTableAttDeleteRow(kdtEntryAtt);
			}
  		});
  		
  		this.kdtEntryAtt_detailPanel.getInsertLineButton().addActionListener(new ActionListener()
  		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!getOprtState().equals(OprtState.VIEW))
				{
					IRow row=kdtEntryAtt.addRow();
					MarketSupplierStockEntryAttInfo info=new MarketSupplierStockEntryAttInfo();
					info.setId(BOSUuid.create(info.getBOSType()));
					row.setUserObject(info);
				}
			}
  		});
  		
  		this.kdtEntryAtt_detailPanel.getAddNewLineButton().addActionListener(new ActionListener()
  		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!getOprtState().equals(OprtState.VIEW))
				{
					IRow row=kdtEntryAtt.addRow();
					MarketSupplierStockEntryAttInfo info=new MarketSupplierStockEntryAttInfo();
					info.setId(BOSUuid.create(info.getBOSType()));
					row.setUserObject(info);
				}
			}
  		});
  		
  		this.kdtEntryAtt.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                	kdtEntryAtt_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
  		
  		this.kdtEntryPerson.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                	kdtEntryPerson_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
  		
  		this.prmtSupplierFileType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSupplierFileType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    }
    
    public static void initTableButton(KDContainer kDContainer,DetailPanel kdtEntrys_detailPanel)
    {
    	kDContainer.addButton(kdtEntrys_detailPanel.getAddNewLineButton());
    	kDContainer.addButton(kdtEntrys_detailPanel.getInsertLineButton());
    	kDContainer.addButton(kdtEntrys_detailPanel.getRemoveLinesButton());
    	kdtEntrys_detailPanel.getAddNewLineButton().setText("新增行");
    	kdtEntrys_detailPanel.getInsertLineButton().setText("插入行");
    	kdtEntrys_detailPanel.getRemoveLinesButton().setText("删除行");
    }
    
    protected void kdtEntryPerson_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
		int index = e.getRowIndex();
		int size = this.kdtEntryPerson.getRowCount();
	    IRow row = this.kdtEntryPerson.getRow(index);
	    Boolean b=(Boolean) row.getCell("isDefault").getValue();
	    
	    for(int j = 0 ; j<size ; j++)
	    {
	    	if(null != this.kdtEntryPerson.getRow(j).getCell("contact").getValue())
	    	{
	    		this.kdtEntryPerson.getRow(j).getCell("contact").setValue(null);
	    	}
	    	if(null != this.kdtEntryPerson.getRow(j).getCell("phone").getValue()){
	    		if(null != this.kdtEntryPerson.getRow(j).getCell("workPhone").getValue())
	    		{
	    			this.kdtEntryPerson.getRow(j).getCell("contact").setValue(this.kdtEntryPerson.getRow(j).getCell("phone").getValue()+";"+this.kdtEntryPerson.getRow(j).getCell("workPhone").getValue());
	    		}
	    		else
	    		{
	    			this.kdtEntryPerson.getRow(j).getCell("contact").setValue(this.kdtEntryPerson.getRow(j).getCell("phone").getValue().toString());
	    		}
	    	}
	    	else if(null != this.kdtEntryPerson.getRow(j).getCell("workPhone").getValue())
	    	{
	    		this.kdtEntryPerson.getRow(j).getCell("contact").setValue(this.kdtEntryPerson.getRow(j).getCell("workPhone").getValue().toString());
	    	}
	    	else
	    	{
	    		continue;
	    	}
	    }
	    
	    if(null == b )
	    {
	    	return ;
	    }
	    
	    if(b.booleanValue()){
			for( int i = 0 ; i< size ;i++)
			{
				if(i ==index )
				{
					continue;
				}
				this.kdtEntryPerson.getRow(i).getCell("isDefault").setValue(Boolean.FALSE);
			}
	    }
    }
    
    protected void kdtEntryAtt_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 &&e.getClickCount()==2&&
				this.kdtEntryAtt.getColumnKey(e.getColIndex()).equals("attlist")) 
		{
			if(((MarketSupplierStockEntryAttInfo)this.kdtEntryAtt.getRow(e.getRowIndex()).getUserObject()).getId()==null){return;};
			String id=((MarketSupplierStockEntryAttInfo)this.kdtEntryAtt.getRow(e.getRowIndex()).getUserObject()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			
	        boolean isEdit = false;
	        if(OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
	            isEdit = true;
	        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
	        info.setBoID(id);
	        info.setEdit(isEdit);
	        String multi = (String)getUIContext().get("MultiapproveAttachment");
	        if(multi != null && multi.equals("true"))
	        {
	        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
	        }else
	        {
	        	acm.showAttachmentListUIByBoID(this, info);
	        }
	        this.kdtEntryAtt.getRow(e.getRowIndex()).getCell("attlist").setValue(loadAttachment(id));
		}
	}
    
	protected String loadAttachment(String id) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name="";
		
		for(int i=0;i<col.size();i++){
			name=name+col.get(i).getAttachment().getName()+" ";
		}
		return name;
	}
    
	private void kdTableAttDeleteRow(KDTable table) 
	{
		if(!getOprtState().equals(OprtState.VIEW))
		{
	        if(table.getSelectManager().size() == 0 || isTableColumnSelected(table))
	        {
	            MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(confirmRemove(this))
	        {
	            int top = table.getSelectManager().get().getBeginRow();
	            int bottom = table.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++)
	            {
	                if(table.getRow(top) == null)
	                {
	                    MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                try 
	                {
                		deleteAttachment(((MarketSupplierStockEntryAttInfo)table.getRow(top).getUserObject()).getId().toString());
                	} 
	                catch (BOSException e) {
						e.printStackTrace();
					} 
	                catch (EASBizException e) {
						e.printStackTrace();
					}
	                table.removeRow(top);
	            }
	        }
		}
	}
    
	protected void deleteAttachment(String id) throws BOSException, EASBizException
	{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		
		if(col.size()>0)
		{
			for(int i=0;i<col.size();i++)
			{
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
				if(attcol.size()==1)
				{
					BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
				}else if(attcol.size()>1)
				
				{
					BoAttchAssoFactory.getRemoteInstance().delete(filter);
				}
			}
		}
	}
    
	private boolean confirmRemove(Component comp)
	{
		return MsgBox.isYes(MsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
    
	   protected void prmtSupplierFileType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
	    {
	    	 boolean isChanged = true;
			 isChanged = BizCollUtil.isF7ValueChanged(e);
	         if(!isChanged)
	         {
	        	 return;
	         }
	         MarketSupplierFileTypInfo fileType=(MarketSupplierFileTypInfo)this.prmtSupplierFileType.getValue();
	         boolean isShowWarn=false;
	         boolean isUpdate=false;
	         if(this.kdtEntrys.getRowCount()>0||this.kdtEntryAtt.getRowCount()>0)
	         {
	        	 isShowWarn=true;
	         }
	         if(isShowWarn)
	         {
	        	 if(MsgBox.showConfirm2(this, "档案分类改变会覆盖附件清单及人数信息数据，是否继续？")== MsgBox.YES)
	        	 {
	        		 isUpdate=true;
	             }
	         }
	         else
	         {
	        	 isUpdate=true;
	         }
	         
	         if(isUpdate)
	         {
	        	 this.kdtEntrys.removeRows();
	        	 this.kdtEntryAtt.removeRows();
	        	 
	        	 if(fileType!=null)
	        	 {
	        		 SorterItemCollection sort=new SorterItemCollection();
	        		 sort.add(new SorterItemInfo("number"));
	        		 EntityViewInfo view=new EntityViewInfo();
	            	 FilterInfo filter = new FilterInfo();
	                 filter.getFilterItems().add(new FilterItemInfo("datype.id" , fileType.getId().toString()));
	                 filter.getFilterItems().add(new FilterItemInfo("isEnable" , Boolean.TRUE));
	                 view.setFilter(filter);
	                 view.setSorter(sort);
	                 MarketSupplierPersonCollection col=MarketSupplierPersonFactory.getRemoteInstance().getMarketSupplierPersonCollection(view);
	                 
	                 for(int i=0;i<col.size();i++)
	                 {
	                	 MarketSupplierPersonInfo sp=col.get(i);
	                	 IRow row=this.kdtEntrys.addRow();
	                	 row.getCell("typeNumber").setValue(sp.getNumber());
	                	 row.getCell("typeName").setValue(sp.getName());
	                 }
	                 
	                 view=new EntityViewInfo();
	                 filter = new FilterInfo();
	                 filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id" , fileType.getId().toString()));
	                 filter.getFilterItems().add(new FilterItemInfo("isEnable" , Boolean.TRUE));
	                 view.setFilter(filter);
	                 view.setSorter(sort);
	                 MarketSupplierAttachListCollection alCol=MarketSupplierAttachListFactory.getRemoteInstance().getMarketSupplierAttachListCollection(view);
	                 
	                 for(int i=0;i<alCol.size();i++)
	                 {
	                	 MarketSupplierAttachListInfo at=alCol.get(i);
	                	 IRow row=this.kdtEntryAtt.addRow();
	                	 MarketSupplierStockEntryAttInfo info=new MarketSupplierStockEntryAttInfo();
	     				 info.setId(BOSUuid.create(info.getBOSType()));
	     				 row.setUserObject(info);
	                	 row.getCell("attNumber").setValue(at.getNumber());
	                	 row.getCell("attName").setValue(at.getName());
	                 }
	        	 }
	         }
	    }
	
    void verifyInputForSubmit()
    {
    	verifyInputForSave();
    	ClientVerifyHelper.verifyEmpty(this, this.txtLinkPhone);
//		ClientVerifyHelper.verifyEmpty(this, this.prmtQuaLevel);
//		ClientVerifyHelper.verifyEmpty(this, this.prmtVisibility);
		ClientVerifyHelper.verifyEmpty(this, this.txtEnterpriseMaster);
//		ClientVerifyHelper.verifyEmpty(this, this.txtboEnterpriseKind);
//		ClientVerifyHelper.verifyEmpty(this, this.prmtServiceType);
//		ClientVerifyHelper.verifyEmpty(this, this.prmtSupplierSplAreaEntry);
//		ClientVerifyHelper.verifyEmpty(this, this.prmtSupplierFileType);
		
		if(this.kdtE4.getRowCount()<1)
		{
			MsgBox.showWarning("符合性审查信息不能为空！");SysUtil.abort();
		}
//		if(this.kdtEntryPerson.getRowCount()<1)
//		{
//			MsgBox.showWarning("表体职员构成不能为空！");SysUtil.abort();
//		}
//		if(this.kdtEntryAtt.getRowCount()<1)
//		{
//			MsgBox.showWarning("表体附件清单不能为空！");SysUtil.abort();
//		}
		
//		boolean isDefault=true;
//		for(int i=0;i<this.kdtEntryPerson.getRowCount();i++){
//			IRow row=this.kdtEntryPerson.getRow(i);
//			if(((Boolean)row.getCell("isDefault").getValue()).booleanValue()){
//				isDefault=false;
//				if(row.getCell("personName").getValue()==null||row.getCell("personName").getValue().toString().trim().equals("")){
//					MsgBox.showWarning(this,"授权联系人姓名不能为空！");
//					SysUtil.abort();
//				}
//				if(row.getCell("phone").getValue()==null||row.getCell("phone").getValue().toString().trim().equals("")){
//					MsgBox.showWarning(this,"授权联系人手机不能为空！");
//					SysUtil.abort();
//				}
//			}
//		}
//		if(isDefault){
//			MsgBox.showWarning(this,"授权联系人不能为空！");
//			SysUtil.abort();
//		}
		
//		ClientVerifyHelper.verifyInput(this, this.kdtEntrys, "peopleSum");
//		ClientVerifyHelper.verifyInput(this, this.kdtEntryPerson, "personName");
//		ClientVerifyHelper.verifyInput(this, this.kdtEntryPerson, "isDefault");
//		ClientVerifyHelper.verifyInput(this, this.kdtEntryPerson, "phone");
//		ClientVerifyHelper.verifyInput(this, this.kdtEntryAtt, "attNumber");
//		
//		for (int i = 0; i < this.kdtE4.getRowCount(); i++) 
//		{
//			if(UIRuleUtil.isNull(kdtE4.getCell(i, "Description").getValue()))
//			{
//				MsgBox.showWarning("符合性审查情况描述不能为空！");SysUtil.abort();
//			}
//		}
    }
    
    void verifyInputForSave()
    {
    	ClientVerifyHelper.verifyEmpty(this, this.txtNumber);
    	ClientVerifyHelper.verifyEmpty(this, this.txtsupplierName);
    	ClientVerifyHelper.verifyEmpty(this, this.prmtInviteType);
    	ClientVerifyHelper.verifyEmpty(this, this.prmtPurchaseOrgUnit);
    	ClientVerifyHelper.verifyEmpty(this, this.prmtEvatemp);
    }
    
    protected void prmtEvatemp_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtEvatemp_dataChanged(e);
    	 boolean isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged||e.getNewValue()==null)
         	return;
 		
 		EvaluationTemplateInfo Info = (EvaluationTemplateInfo)e.getNewValue();
 		
 		boolean isShowWarn=false;
         boolean isUpdate=false;
         if(this.kdtE4.getRowCount()>0 ){
         	isShowWarn=true;
         }
         if(isShowWarn){
         	if(MsgBox.showConfirm2(this, "评审模板改变会覆盖符合性审查信息，是否继续？")== JOptionPane.YES_OPTION){
         		isUpdate=true;
             }
         }else{
         	isUpdate=true;
         }
         
         if(isUpdate){
         	this.kdtE4.removeRows();
         	IEvaluationIndicators IEvaluationIndicators = EvaluationIndicatorsFactory.getRemoteInstance();
         	for (int i = 0; i < Info.getEntry().size(); i++) 
         	{
         		EvaluationTemplateEntryInfo entryInfo = Info.getEntry().get(i);
         		
         		IRow row = this.kdtE4.addRow();
         		
         		if(entryInfo.getIndicatorType()!=null)
         		{
         			
         			EvaluationIndicatorsInfo EvaluationIndicatorsInfo = IEvaluationIndicators.getEvaluationIndicatorsInfo(new ObjectUuidPK(entryInfo.getIndicatorType().getId()));
         			
         			row.getCell("EvaluationIndex").setValue(EvaluationIndicatorsInfo.getName());
         			row.getCell("isQualified").setValue(Boolean.FALSE);
         		}
 			}
         }
    }
    
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionCopy_actionPerformed(e);
    	this.prmtCreator.setValue(null);
    	this.prmtAuditor.setValue(null);
    	this.pkauditDate.setValue(null);
    	this.kDPanel2.removeAll();
    	this.kDPanel8.removeAll();
    	
    	this.editData.setSysSupplier(null);
    	this.editData.setLevel(null);
    	this.editData.put("sysSupplier", null);
    	
    	for (int i = 0; i < editData.getEntryAtt().size(); i++) {
    		MarketSupplierStockEntryAttInfo  info = editData.getEntryAtt().get(i);
    		info.setId(BOSUuid.create(info.getBOSType()));
		}
    }

    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	verifyInputForSave();
    	super.actionSave_actionPerformed(e);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	verifyInputForSubmit();
    	super.actionSubmit_actionPerformed(e);
    	if(SupplierState.submit.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
    	this.setOprtState("VIEW");
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if(SupplierState.submit.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
	}
    
	protected void loadSupplierAttachList(){
		MarketSupplierStockEntryAttCollection col=editData.getEntryAtt();
		sortCollection(col, "seq", true);
		this.kdtEntryAtt.removeRows();
		for(int i=0;i<col.size();i++){
			MarketSupplierStockEntryAttInfo entry=col.get(i);
			IRow row=this.kdtEntryAtt.addRow();
			row.setUserObject(entry);
			row.getCell("attNumber").setValue(entry.getAttNumber());
			row.getCell("attName").setValue(entry.getAttName());
			try {
				if(entry.getId()!=null){
					row.getCell("attlist").setValue(loadAttachment(entry.getId().toString()));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}	
	
    public static void sortCollection(IObjectCollection cols, final String sortColName, final boolean sortType)
    {
    	Object toSortData[] = cols.toArray();
    	Arrays.sort(toSortData, new Comparator() {
    	public int compare(Object arg0, Object arg1){
    		IObjectValue obj0 = (IObjectValue)arg0;
    		IObjectValue obj1 = (IObjectValue)arg1;
    		if(obj0 == null || obj1 == null)
    			return 0;
    		Comparable tmp0 = (Comparable)getValue(obj0, sortColName);
    		Comparable tmp1 = (Comparable)getValue(obj1, sortColName);
    		if(tmp0 == null || tmp1 == null)
    		{
    			return 0;
    		}
    		else
    		{
    			return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
            }
    	}
    	});
    	cols.clear();
    	for(int j = 0; j < toSortData.length; j++)
    		cols.addObject((IObjectValue)toSortData[j]);
    }
    
    public static Object getValue(IObjectValue value, String key)
    {
    	int in = key.indexOf(".");
    	if(in == -1)
    		return value.get(key);
    	Object tmp = value.get(key.substring(0, in));
    	if(tmp != null && (tmp instanceof IObjectValue))
    		return getValue((IObjectValue)tmp, key.substring(in + 1, key.length()));
    	else
    		return null;
    }
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo objectValue = new com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        MarketSupplierStockInfo info = new MarketSupplierStockInfo();
		try {
			objectValue.setCreateTime(new Timestamp(SysUtil.getAppServerTime(null).getTime()));
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		if(getUIContext().get("type")!=null){
			objectValue.setInviteType((SupplierInvoiceTypeTreeInfo)getUIContext().get("type"));
		}
		if(getUIContext().get("org")!=null){
			try {
				PurchaseOrgUnitInfo orgUnitInfo = PurchaseOrgUnitFactory.getRemoteInstance().getPurchaseOrgUnitInfo(new ObjectUuidPK(((OrgStructureInfo) getUIContext().get("org")).getUnit().getId()));
				objectValue.setPurchaseOrgUnit(orgUnitInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
        return objectValue;
    }

}