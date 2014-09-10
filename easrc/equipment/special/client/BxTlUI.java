/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.client.AttachmentEditUI;
import com.kingdee.eas.base.attachment.client.AttachmentSTUI;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.equipment.special.RegulationsEntryFactory;
import com.kingdee.eas.port.equipment.special.RegulationsEntryInfo;
import com.kingdee.eas.port.equipment.special.RegulationsInfo;
import com.kingdee.eas.port.equipment.uitl.KDDetailedAreaDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.XRSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class BxTlUI extends AbstractBxTlUI
{
    private static final Logger logger = CoreUIObject.getLogger(BxTlUI.class);
    
    /**
     * output class constructor
     */
    public BxTlUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	this.kDContainer1.addButton(this.btnAddAtttachment);
    	this.kDContainer1.addButton(this.btnViewAtttachment);
    	this.kDContainer1.addButton(this.btnAttRemove);
    	this.kDContainer1.addButton(this.btnDow);
    	this.kDContainer1.addButton(this.btnOpen);
    	
    	this.btnAddAtttachment.setEnabled(true);
    	this.btnViewAtttachment.setEnabled(true);
    	this.btnAttRemove.setEnabled(true);
    	this.btnDow.setEnabled(true);
    	this.btnOpen.setEnabled(true);
    	
    	
    	this.actionAddNew.setVisible(false);
    	this.actionView.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionRemove.setVisible(false);
    	
    	this.setUITitle("保险条例");
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	if(e.getClickCount()==2)
    		viewAtt();
    	if ("attachment.description".equals(this.tblMain.getColumnKey(e.getColIndex()))) {
    		showDialog(this,"VIEW", this.tblMain, 400, 200, 200);
    	}
    }
    
    private void viewAtt(KDTMouseEvent e)
    {
    	int rowIndex = 0;
    	if(e!=null)
    	{
    		if(e.getRowIndex()==-1||e.getClickCount()!=2)
    			return;
    		rowIndex = e.getRowIndex();
    	}
    	else
    	{
    		rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    	}
    	if(rowIndex==-1)
    		return;
    	AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
        info.setBoID(UIRuleUtil.getString(this.tblMain.getCell(rowIndex, "RegulationsEntry.id").getValue()));
        info.setEdit(false);
        String multi = (String)getUIContext().get("MultiapproveAttachment");
        if(multi != null && multi.equals("true")){
        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
        }else{
        	acm.showAttachmentListUIByBoID(this, info);
        }
    }
    
    
    //去除CU隔离
	protected boolean isIgnoreCUFilter() {
		return true;
	}

    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
    	EntityViewInfo newInfo = (EntityViewInfo)viewInfo.clone();
    	FilterInfo filInfo = new FilterInfo();
    	CtrlUnitInfo CTRLiNFO  = SysContext.getSysContext().getCurrentCtrlUnit();
    	filInfo.getFilterItems().add(new FilterItemInfo("RegulationsEntry.beizhu","保险条例",CompareType.EQUALS));
    	filInfo.getFilterItems().add(new FilterItemInfo("cu.longNumber",CTRLiNFO.getLongNumber()+"%",CompareType.LIKE));
    	filInfo.getFilterItems().add(new FilterItemInfo("cu.id",OrgConstants.DEF_CU_ID,CompareType.EQUALS));
    	filInfo.setMaskString("#0 and (#1 or #2)");
    	if(newInfo.getFilter()!=null)
    	{
    		try {
				newInfo.getFilter().mergeFilter(filInfo, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	else
    	{
    		newInfo.setFilter(filInfo);
    	}
    	return super.getQueryExecutor(queryPK, newInfo);
    }
    
    
	public static void showDialog(JComponent owner,String UIState, KDTable table, int X, int Y, int len)
    {
		table.checkParsed();
        int rowIndex = table.getSelectManager().getActiveRowIndex();
        int colIndex = table.getSelectManager().getActiveColumnIndex();
        final ICell cell = table.getCell(rowIndex, colIndex);
        if(cell==null||cell.getValue() == null)
            return;
        BasicView view = table.getViewManager().getView(5);
        Point p = view.getLocationOnScreen();
        Rectangle rect = view.getCellRectangle(rowIndex, colIndex);
        java.awt.Window parent = SwingUtilities.getWindowAncestor(owner);
        final KDDetailedAreaDialog dialog ;
        if(parent instanceof Dialog)
        {
            dialog = new KDDetailedAreaDialog((Dialog)parent, X, Y, true){
            	protected void btnOK_actionPerformed(ActionEvent e) {
            		super.btnOK_actionPerformed(e);
            		cell.setValue(getData());
            		dispose();
            	}
            };
        }
        else
        if(parent instanceof Frame){
            dialog = new KDDetailedAreaDialog((Frame)parent, X, Y, true){
            	protected void btnOK_actionPerformed(ActionEvent e) {
            		super.btnOK_actionPerformed(e);
            		cell.setValue(getData());
            		dispose();
            	}
            };
        }
        else{
            dialog = new KDDetailedAreaDialog(true);
        }
        String vals = cell.getValue()!=null?cell.getValue().toString():"";
        dialog.setData(vals);
        dialog.setPRENTE_X(p.x + rect.x + rect.width);
        dialog.setPRENTE_Y(p.y + rect.y);
        dialog.setMaxLength(len);
        if(UIState.equals("EDIT")||UIState.equals("ADDNEW"))
        	dialog.setEditable(true);
        else
        	dialog.setEditable(false);
        dialog.show();
    }
    
    public void actionViewAtttachment_actionPerformed(ActionEvent e)throws Exception {
    	super.actionViewAtttachment_actionPerformed(e);
    	viewAtt();
    }
    
    private void viewAtt() throws Exception
    {
    	checkSelected();
    	IUIWindow uiWindow = null;
		UIContext context = new UIContext(this);
		context.put("ID", UIRuleUtil.getString(this.tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),"attachment.id").getValue()));
		context.put("boID",  UIRuleUtil.getString(this.tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),"RegulationsEntry.id").getValue()));
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(AttachmentEditUI.class.getName(), context, null, OprtState.EDIT);
		uiWindow.show(); 
		refresh(null);
    }
    
    public void actionDow_actionPerformed(ActionEvent e) throws Exception {
    	super.actionDow_actionPerformed(e);
    	checkSelected();
    	String path=getPath();  
        if(null==path){  
            return;
        }  
        String id = UIRuleUtil.getString(this.tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),"RegulationsEntry.id").getValue());
        exportAttachment(id, path);  
    }
    
    public void actionOpen_actionPerformed(ActionEvent e) throws Exception {
    	super.actionOpen_actionPerformed(e);
    	checkSelected();
    	String id = UIRuleUtil.getString(this.tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(),"attachment.id").getValue());
        AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
    	attachmentClientManager.viewAttachment(id);    	
    }
    
	private FileGetter getFileGetter() throws Exception {
		FileGetter fileGetter = null;

		if (getUIContext().get("fileGetter") == null) {
			fileGetter = new FileGetter((IAttachment) AttachmentFactory
					.getRemoteInstance(), AttachmentFtpFacadeFactory
					.getRemoteInstance());
			getUIContext().put("fileGetter", fileGetter);
		}else{
			fileGetter = (FileGetter) getUIContext().get("fileGetter");
		}
		return fileGetter;
	}
	
    
    public void actionAddAtttachment_actionPerformed(ActionEvent e)throws Exception {
    	super.actionAddAtttachment_actionPerformed(e);
    	RegulationsEntryInfo entryInfo = new RegulationsEntryInfo();
    	RegulationsInfo info = new RegulationsInfo();
    	info.setId(BOSUuid.create(info.getBOSType()));
    	
    	entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
    	entryInfo.setParent(info);
    	entryInfo.setBeizhu("保险条例");
    	
    	AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
    	attachmentClientManager.showUploadFilesUI(this, entryInfo.getId().toString());
    	
    	if (attachmentClientManager.attachmentID != null
				&& attachmentClientManager.attachmentID.length > 0) {
    		java.util.List list = new ArrayList();
    		for (int i = 0; i < attachmentClientManager.attachmentID.length; i++)
    		{
    			if (!StringUtils
						.isEmpty(attachmentClientManager.attachmentID[i]))
    				list
					.add(attachmentClientManager.attachmentID[i]);
    			if (list.size() > 0) {
    				String atID[] = new String[list.size()];
    				for (int i1 = 0; i1 < list.size(); i1++)
    					atID[i1] = list.get(i1).toString();
    				setAttachmentST(atID);
    			}
    		}
    		RegulationsEntryFactory.getRemoteInstance().addnew(entryInfo);
    		refresh(null);
    	}
    }
    
    private String getPath() throws Exception{  
        String dir = ""; // 缺省路径  
        Component parent = null; // Dialog上级组件  
        JFileChooser chooser = new JFileChooser(dir);  
        javax.swing.filechooser.FileFilter dirFilter = new javax.swing.filechooser.FileFilter() {  
            public boolean accept(File f) {  
                return f.isDirectory();  
            }  
      
            public String getDescription() {  
                return "";  
            }  
        };  
        chooser.setFileFilter(dirFilter);  
        chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);  
        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {  
            dir = chooser.getSelectedFile().getAbsolutePath();  
            return dir;  
        }  
        return null;  
    }  
      
    
    private void setAttachmentST(String id[]) {
		HashMap map = new HashMap();
		if (id != null && id.length == 1
				&& id[0] == null)
			return;
		map.put("ATTACHMENT_ID", id);
		try {
			AttachmentSTUI aui = new AttachmentSTUI(
					this, map);
			aui.show();
		}
		catch (Exception e) {
			handleException(e);
		}
	}
    
    public void actionAttRemove_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAttRemove_actionPerformed(e);
    	checkSelected();
		if (confirmRemove()) {
			deleteAttachment(UIRuleUtil.getString(this.tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "RegulationsEntry.id").getValue()));
			refresh(e);
		}
    }
    
	protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getRemoteInstance().delete(filter);
				}
			}
		}
	}
	


	protected ICoreBase getBizInterface() throws Exception {
		return AttachmentFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}
	
	/** 
	 * 一键导出该单据的相关附件 
	 * @param id 单据对应的id 
	 * @param path 需要导出保存文件的路径 
	 * @throws Exception 
	 */  
	public static void exportAttachment(String id,String path)throws Exception{  
	    StringBuffer sb=new StringBuffer();  
	    sb.append("/*dialect*/ ");  
	    sb.append(" select attachment.fname_l2 name, ");  
	    sb.append("       attachment.fsimplename type, ");  
	    sb.append("       attachment.ffile  contens  ");  
	    sb.append("from T_BAS_Attachment attachment,");  
	    sb.append("     T_BAS_BoAttchAsso temp ");  
	    sb.append("where attachment.fid=temp.fattachmentid ");  
	    sb.append("      and attachment.ffile is not null ");  
	    sb.append("      and temp.fboid='").append(id).append("'");  
	    IRowSet rs=new XRSQLBuilder().appendSql(sb.toString()).executeQuery();  
	    while(rs.next()){  
	        String fileName=rs.getString("name");  
	        String types=rs.getString("type");  
	        String fullName=path+"/"+fileName+"."+types;  
	        File f=new File(fullName);  
	        int i=0;  
	        while(f.exists()){  
	            fullName=path+"/"+fileName+"("+(++i)+")."+types;  
	            f=new File(fullName);  
	        }  
	        Blob contends=rs.getBlob("contens");  
	        InputStream in=contends.getBinaryStream();  
	        FileOutputStream fos=new FileOutputStream(fullName);  
	        storeAttch(fos,in);  
	    }  
	}  
	
	public static void storeAttch(OutputStream ops,InputStream ips){  
	      
	    BufferedInputStream bis=new BufferedInputStream(ips);  
	    BufferedOutputStream bos=new BufferedOutputStream(ops);  
	    byte[] buf=new byte[1024*600];  
	    int len=0;  
	    try {  
	        while((len=bis.read(buf))!=-1){  
	            bos.write(buf, 0, len);  
	        }  
	        MsgBox.showInfo("下载成功！");SysUtil.abort();
	    } catch (IOException e) {  
	        System.out.println("流文件写入、输出异常" +ips+ops);  
	        e.printStackTrace();  
	    }finally{  
	        if(bis!=null){  
	            try {  
	                bis.close();  
	            } catch (IOException e) {  
	                System.out.println("输入流文件关闭异常" +ips);  
	                e.printStackTrace();  
	            }  
	        }  
	        if(bos!=null){  
	            try {  
	                bos.close();  
	            } catch (IOException e) {  
	                System.out.println("输出流文件关闭异常"+ops);  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	      
	}  
	

}