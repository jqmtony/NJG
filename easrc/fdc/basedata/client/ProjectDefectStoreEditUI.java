/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.DefectTypeFactory;
import com.kingdee.eas.fdc.basedata.DefectTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectDefectStoreEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectDefectStoreEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectDefectStoreFactory;
import com.kingdee.eas.fdc.basedata.ProjectDefectStoreInfo;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.fdc.basedata.StateEnum;
import com.kingdee.eas.fdc.basedata.SubsectionProjectFactory;
import com.kingdee.eas.fdc.basedata.SubsectionProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * output class name
 */
public class ProjectDefectStoreEditUI extends AbstractProjectDefectStoreEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectDefectStoreEditUI.class);
    private KDFileChooser chooser=new KDFileChooser();
    private File imgPath=null;//图片路径
    private boolean isShowedImage=false;//是否有显示图片  这个属性 决定是否可以设置热点
	private boolean isShowHotspot=false;//是否加载已保存热点区域
	private int startX, startY, endX, endY;//确定热点区域的4个坐标值
	private Graphics og;
	MouseListener moListener;
	MouseMotionListener mmListener;
    
    /**
     * output class constructor
     */
    public ProjectDefectStoreEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		this.kdtEntry.checkParsed();
		this.actionAudit.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionAddLine.setVisible(false);
		 if (STATUS_ADDNEW.equals(this.oprtState)) {
				this.comboStatus.setSelectedItem(StateEnum.No);
				if (( this.getUIContext().get("ID") != null) && ( this.getUIContext().get("ID") != "")) {
//					SellInfo =(SellProjectInfo) this.getUIContext().get("ID");
					ProjectBaseInfo projectInfo = ProjectBaseFactory.getRemoteInstance().getProjectBaseInfo(new ObjectUuidPK(this.getUIContext().get("ID").toString()));
					this.prmtProjectItem.setValue(projectInfo.getName());
				}
		 }
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    /*选择缺陷类型，带出专业*/
    protected void prmtDefectType_dataChanged(DataChangeEvent e)throws Exception
    {
         super.prmtDefectType_dataChanged(e);
         if(prmtDefectType.getValue()!=null)
         {
	         DefectTypeInfo defectTypeInfo = (DefectTypeInfo) prmtDefectType.getValue();
	         defectTypeInfo = DefectTypeFactory.getRemoteInstance().getDefectTypeInfo(new ObjectUuidPK(defectTypeInfo.getId().toString()));
		     SpecialtyTypeInfo specialtyTypeInfo = new SpecialtyTypeInfo();
		     specialtyTypeInfo = SpecialtyTypeFactory.getRemoteInstance().getSpecialtyTypeInfo(new ObjectUuidPK(defectTypeInfo.getSpecialty().getId().toString()));
		     this.prmtProfession.setValue(specialtyTypeInfo);
          }
     }
    /**
     * 保存时将缺陷类型、专业、分部工程的Number存入txtCountNumber；
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<kdtEntry.getRowCount();i++){
    		IRow row   = kdtEntry.getRow(i);
			ICell cell  =row.getCell("imageName");
    		if(i==0)
    			sb.append(cell.getValue());
    		
    		else sb.append(","+cell.getValue().toString());
    	}
    	this.txtPhotoName.setText(sb.toString());
    	StringBuffer count2 = new StringBuffer();
    	if(prmtDefectType.getValue()!=null&&this.prmtSubsectionType.getValue()!=null)
        {
    		 DefectTypeInfo defectTypeInfo = (DefectTypeInfo) prmtDefectType.getValue();
	         defectTypeInfo = DefectTypeFactory.getRemoteInstance().getDefectTypeInfo(new ObjectUuidPK(defectTypeInfo.getId().toString()));
		     SpecialtyTypeInfo specialtyTypeInfo = new SpecialtyTypeInfo();
		     specialtyTypeInfo = SpecialtyTypeFactory.getRemoteInstance().getSpecialtyTypeInfo(new ObjectUuidPK(defectTypeInfo.getSpecialty().getId().toString()));
		     this.prmtProfession.setValue(specialtyTypeInfo);
		     SubsectionProjectInfo subsectionProjectInfo =(SubsectionProjectInfo) this.prmtSubsectionType.getValue();
		     subsectionProjectInfo=SubsectionProjectFactory.getRemoteInstance().getSubsectionProjectInfo(new ObjectUuidPK(subsectionProjectInfo.getId().toString()));
	         String  N= defectTypeInfo.getNumber();
	         String  B= specialtyTypeInfo.getNumber(); 
	         String  A=subsectionProjectInfo.getNumber();
	         count2.append(N+B+A);
	         this.txtCountNumber.setText(count2.toString());
        }
    	super.actionSave_actionPerformed(e);
    	
    }
    protected void verifyInput(ActionEvent e) throws Exception {
		/*编码是否为空*/ 
		KDTextField txtNumber = this.getNumberCtrl();
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		/*缺陷*/
		if(this.prmtDefectType.getValue()==null){
			 MsgBox.showInfo("缺陷类型不能为空！"); 
	    	 SysUtil.abort();
		}
		if(this.prmtSubsectionType.getValue()==null){
			 MsgBox.showInfo("分部类型不能为空！"); 
	    	 SysUtil.abort();
		}
		if(this.prmtProductType.getValue()==null){
			 MsgBox.showInfo("产品类型不能为空！"); 
	    	 SysUtil.abort();
		}
		if(this.prmtProjectItem.getValue()==null){
			 MsgBox.showInfo("工程项目不能为空！"); 
	    	 SysUtil.abort();
		}
		if(this.prmtPosition.getValue()==null){
			 MsgBox.showInfo("位置不能为空！"); 
	    	 SysUtil.abort();
		}
		if(this.prmtProfession.getValue()==null){
			 MsgBox.showInfo("专业不能为空！"); 
	    	 SysUtil.abort();
		}
	}
    /**
     * 提交时将缺陷类型、专业、分部工程的Number存入txtCountNumber；
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<kdtEntry.getRowCount();i++){
    		IRow row   = kdtEntry.getRow(i);
			ICell cell  =row.getCell("imageName");
    		if(i==0)
    			sb.append(cell.getValue());
    		
    		else sb.append(","+cell.getValue().toString());
    	}
    	this.txtPhotoName.setText(sb.toString());
    	StringBuffer count2 = new StringBuffer();
    	if(prmtDefectType.getValue()!=null&&this.prmtSubsectionType.getValue()!=null)
        {
    		 DefectTypeInfo defectTypeInfo = (DefectTypeInfo) prmtDefectType.getValue();
	         defectTypeInfo = DefectTypeFactory.getRemoteInstance().getDefectTypeInfo(new ObjectUuidPK(defectTypeInfo.getId().toString()));
		     SpecialtyTypeInfo specialtyTypeInfo = new SpecialtyTypeInfo();
		     specialtyTypeInfo = SpecialtyTypeFactory.getRemoteInstance().getSpecialtyTypeInfo(new ObjectUuidPK(defectTypeInfo.getSpecialty().getId().toString()));
		     this.prmtProfession.setValue(specialtyTypeInfo);
		     SubsectionProjectInfo subsectionProjectInfo =(SubsectionProjectInfo) this.prmtSubsectionType.getValue();
		     subsectionProjectInfo=SubsectionProjectFactory.getRemoteInstance().getSubsectionProjectInfo(new ObjectUuidPK(subsectionProjectInfo.getId().toString()));
	         String  N= defectTypeInfo.getNumber();
	         String  B= specialtyTypeInfo.getNumber(); 
	         String  A=subsectionProjectInfo.getNumber();
	         count2.append(N+B+A);
	         this.txtCountNumber.setText(count2.toString());
        }
    	super.actionSubmit_actionPerformed(e);
    }

    
    /*初始化加载图片*/
	public void loadFields() {
		this.kdtEntry.checkParsed();
		super.loadFields();
		
		try { 
			ProjectDefectStoreInfo info = ProjectDefectStoreFactory.getRemoteInstance().getProjectDefectStoreInfo(new ObjectUuidPK(this.editData.getId()));
			ProjectDefectStoreEntryCollection imageColl = info.getEntry();
			if (imageColl != null && imageColl.get(0)!=null && imageColl.get(0).getImageFile()!=null) {
				addImage(imageColl.get(0).getImageFile());
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 点击图片分录,切换图片
	 * */
	 protected void kdtEntry_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtEntry_tableClicked(e);
		int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if(kdtEntry.getRow(rowIndex)!=null)
		{
			addImage((byte[])kdtEntry.getRow(rowIndex).getCell("imageFile").getValue());
		}
	}

	/**
     * 新增上传图片
     */
    public void actionUpPhoto_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUpPhoto_actionPerformed(e);
    	IRow row = null;
		if(this.kdtEntry != null)
		{
			IObjectValue detailData = new ProjectDefectStoreEntryInfo();
			row = addLine(kdtEntry,detailData);
		}
		chooser.resetChoosableFileFilters();
		chooser.removeChoosableFileFilter(chooser.getFileFilter());
    	chooser.addChoosableFileFilter(new MyFileFilter(".jpg","JPG格式图片"));
    	int i=chooser.showOpenDialog(null);
    	if(i==KDFileChooser.APPROVE_OPTION){//当选择了图片后才进行下面操作
	    	File file = chooser.getSelectedFile();
	        if(file!=null)
	        {
	        	imgPath=file;
		        showImage(file);
		        startX=0;startY=0;endX=0;endY=0;//重新导入图片时去掉 已经画的热点区域
//		        pnlRoomPic.repaint();
		        
		        if(imgPath!=null)//保存图片到分录中
		        {
					long len = imgPath.length();
					byte[] bytes = new byte[(int)len];
					BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(imgPath));
					int r = bufferedInputStream.read(bytes);
					if (r != len) {
						throw new IOException("文件读取异常！");
					}
				    bufferedInputStream.close();
				    row.getCell("imageName").setValue(file.getName());
				    row.getCell("imagePath").setValue(file.getPath());
				    row.getCell("imageFile").setValue(bytes);
				}
	        }
    	}
    }

    /**
     * 删除图片
     */
    public void actionDelPhoto_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelPhoto_actionPerformed(e);
        removeTable(kdtEntry);
      
    }
    /**
	 * 通用方法-删除行
	 * */
	public void removeTable(KDTable table){
		if(table != null)
        {
            removeLine(table);
            appendFootRow(table);
            if(table.getRowCount() == 0)
            {
                FocusTraversalPolicy policy = null;
                java.awt.Container container = null;
                Component initComponent = null;
                if(getFocusTraversalPolicy() != null && (getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy))
                {
                    policy = getFocusTraversalPolicy();
                    container = this;
                    Component traverComponent[] = ((UIFocusTraversalPolicy)policy).getComponents();
                    int i = 0;
                    do
                    {
                        if(i >= traverComponent.length)
                            break;
                        if(traverComponent[i] == table)
                        {
                            initComponent = traverComponent[i];
                            break;
                        }
                        i++;
                    } while(true);
                    if(initComponent == null)
                    {
                        initComponent = policy.getLastComponent(container);
                        initComponent.requestFocusInWindow();
                    } else
                    if(initComponent != null)
                    {
                        Component component;
                        for(component = policy.getComponentBefore(container, initComponent); !(component instanceof IKDTextComponent) || !component.isEnabled(); component = policy.getComponentBefore(container, component));
                        component.requestFocusInWindow();
                    }
                } else
                if(policy == null)
                {
                    if(getUIWindow() instanceof Dialog)
                    {
                        policy = ((Dialog)uiWindow).getFocusTraversalPolicy();
                        container = (Dialog)uiWindow;
                    } else
                    if(getUIWindow() instanceof Window)
                    {
                        policy = ((Window)uiWindow).getFocusTraversalPolicy();
                        container = (Window)uiWindow;
                    }
                    if(policy != null)
                        try
                        {
                            Component component;
                            for(component = policy.getComponentBefore(container, table); !(component instanceof IKDTextComponent) || !component.isEnabled(); component = policy.getComponentBefore(container, component));
                            component.requestFocusInWindow();
                        }
                        catch(Exception ex) { }
                }
            }
        }
	}

	/**
	 * 通用方法-新增行
	 * */
	public IRow addLine(KDTable table,IObjectValue detailData){
		IRow row = null;
		if(detailData != null)
        {
			row = table.addRow();
            getUILifeCycleHandler().fireOnAddNewLine(table, detailData);
            loadLineFields(table, row, detailData);
            afterAddLine(table, detailData);
        }
	    appendFootRow(kdtEntry);
	    return row;
	}
	/**
	 * 加载图片
	 * */
	private void addImage(byte[] imageFile)
	{
		File file;
		try {
			if(imageFile!=null)
			{
				file = File.createTempFile("tmp1111", "a.jpg");
				ByteInputStream stream = new ByteInputStream(imageFile, imageFile.length);
				FileOutputStream out = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int size = -1;
				while((size = stream.read(buffer)) != -1){
					out.write(buffer, 0, size);
				}
				out.flush();
				stream.close();
				out.close();
				imgPath=file;
				showImage(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置显示图片
	 * @param file
	 */
	private void showImage(File file){
		
//		pnlRoomPic.setImageFile(file);
//		this.kDPhotoPane.setViewportView(pnlRoomPic);
		this.isShowedImage=true;
	}
	/**
	 * 文件内部类
	 * */
	class MyFileFilter extends FileFilter{
		private String ext;
		private String des;
	    public MyFileFilter(String ext,String des){
	    	this.ext=ext;
	    	this.des=des;
	    }
	    public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        }
	        if(f.getName().toLowerCase().endsWith(ext)){
	        	return true;
	        }
	        return false;
	    }

	    public String getDescription() {
	        return des;
	    }
	}
	/**
	 * 显示图片Panel
	 * */
//	SHEImagePanel pnlRoomPic = new SHEImagePanel(){
//		public void paint(Graphics g) {
//			super.paint(g);
//			og=g.create();
//			if(isShowHotspot && kdtEntry.getRowCount()>0){
//				for(int i=0;i<kdtEntry.getRowCount();i++){
//					//当前选中行的热点区域  在下面以红色绘制
//					if(i==kdtEntry.getSelectManager().getActiveRowIndex()){
//						continue;
//					}
//					IRow row=kdtEntry.getRow(i);
//					if(row.getCell("hotspot1").getValue()!=null && row.getCell("hotspot2").getValue()!=null){
//						String hotspot1=row.getCell("hotspot1").getValue().toString();
//						int hotspot1_x=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
//						int hotspot1_y=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
//						String hotspot2=row.getCell("hotspot2").getValue().toString();
//						int hotspot2_x=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
//						int hotspot2_y=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
//						if(hotspot1_x<hotspot2_x){
//							if(hotspot1_y<hotspot2_y){
//								g.drawRect(hotspot1_x, hotspot1_y, hotspot2_x-hotspot1_x, hotspot2_y-hotspot1_y);
//							}
//							else{
//								g.drawRect(hotspot1_x, hotspot2_y, hotspot2_x-hotspot1_x, hotspot1_y-hotspot2_y);
//							}
//						}
//						else{
//							if(hotspot2_y<hotspot1_y){
//								g.drawRect(hotspot2_x, hotspot2_y, hotspot1_x-hotspot2_x, hotspot1_y-hotspot2_y);
//							}
//							else{
//								g.drawRect(hotspot2_x, hotspot1_y, hotspot1_x-hotspot2_x, hotspot2_y-hotspot1_y);
//							}
//						}
//					}
//				}
//			}
//			g.setColor(Color.RED);//这里更改了颜色 然后画当前的热点区域
//			if(startX<endX){
//				if(startY<endY){
//					g.drawRect(startX, startY, endX-startX, endY-startY);
//				}
//				else{
//					g.drawRect(startX, endY, endX-startX, startY-endY);
//				}
//			}
//			else{
//				if(endY<startY){
//					g.drawRect(endX, endY, startX-endX, startY-endY);
//				}
//				else{
//					g.drawRect(endX, startY, startX-endX, endY-startY);
//				}
//			}
//		}
//	};

    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    	this.comboStatus.setSelectedItem(StateEnum.No);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }


	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectDefectStoreFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	protected IObjectValue createNewData() {
		return new ProjectDefectStoreInfo();
	
	}

}