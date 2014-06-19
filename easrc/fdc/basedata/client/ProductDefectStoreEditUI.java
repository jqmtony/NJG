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
import java.util.Set;

import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.AreaFactory;
import com.kingdee.eas.basedata.assistant.AreaInfo;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.AreaEntryCollection;
import com.kingdee.eas.fdc.basedata.AreaEntryInfo;
import com.kingdee.eas.fdc.basedata.CityEntryCollection;
import com.kingdee.eas.fdc.basedata.CityEntryInfo;
import com.kingdee.eas.fdc.basedata.DefectTypeFactory;
import com.kingdee.eas.fdc.basedata.DefectTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.PositionSeatEntryCollection;
import com.kingdee.eas.fdc.basedata.PositionSeatEntryInfo;
import com.kingdee.eas.fdc.basedata.PositionSeatFactory;
import com.kingdee.eas.fdc.basedata.PositionSeatInfo;
import com.kingdee.eas.fdc.basedata.ProductDefectStoreEntryCollection;
import com.kingdee.eas.fdc.basedata.ProductDefectStoreEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductDefectStoreFactory;
import com.kingdee.eas.fdc.basedata.ProductDefectStoreInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeEntryCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectBaseEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.fdc.basedata.SubsectionProjectFactory;
import com.kingdee.eas.fdc.basedata.SubsectionProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * output class name
 */
public class ProductDefectStoreEditUI extends AbstractProductDefectStoreEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProductDefectStoreEditUI.class);
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
    public ProductDefectStoreEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		this.kdtEntry.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
   
	protected void prmtDefectType_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtDefectType_dataChanged(e);
		if(prmtDefectType.getValue()!=null){
			DefectTypeInfo defectTypeInfo = (DefectTypeInfo) prmtDefectType.getValue();
			 defectTypeInfo = DefectTypeFactory.getRemoteInstance().getDefectTypeInfo(new ObjectUuidPK(defectTypeInfo.getId().toString()));
				SpecialtyTypeInfo specialtyTypeInfo = new SpecialtyTypeInfo();
				specialtyTypeInfo = SpecialtyTypeFactory.getRemoteInstance().getSpecialtyTypeInfo(new ObjectUuidPK(defectTypeInfo.getSpecialty().getId().toString()));
				this.prmtProfession.setValue(specialtyTypeInfo);
		}
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
		if(this.prmtCity.getValue()==null){
			 MsgBox.showInfo("城市不能为空！"); 
	    	 SysUtil.abort();
		}
		if(this.prmtArea.getValue()==null){
			 MsgBox.showInfo("区域不能为空！"); 
	    	 SysUtil.abort();
		}
//		if(this.comboScope.getSelectedItem()==null){
//			 MsgBox.showInfo("适应范围不能为空！"); 
//	    	 SysUtil.abort();
//		}
	}
	/**
     * 保存五个集合F7
     */
    public void storeFields()
    {
    	//区域
    	AreaEntryCollection areaColl = this.editData.getArea();
    	areaColl.clear();
    	Object[] areaArray = (Object[])this.prmtArea.getValue();
    	if(areaArray==null){
    		return;
    	}
    	AreaEntryInfo areaentryInfo = null;
    	StringBuffer areaCount = new StringBuffer();
    	for(int i = 0 ; i < areaArray.length ; i++)
    	{
    		areaentryInfo = new AreaEntryInfo();
    		areaentryInfo.setArea((AreaInfo)areaArray[i]);
    		areaentryInfo.setHead(editData);
    		areaColl.add(areaentryInfo);
    		try {
				AreaInfo areaIndfo =AreaFactory.getRemoteInstance().getAreaInfo(new ObjectUuidPK(areaentryInfo.getArea().getId()));
				 if(i==0)
	             {
	            	 areaCount.append(areaIndfo.getName());
	    		 }
	             else
	             {
	            	 areaCount.append(","+areaIndfo.getName());
	            	
	             }
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	 }
    	 this.txtAreaCount.setText(areaCount.toString());
    	//城市
    	CityEntryCollection cityColl = this.editData.getCity();
    	cityColl.clear();
    	Object[] cityArray = (Object[])this.prmtCity.getValue();
    	if(cityArray==null){
    		return;
    	}
    	CityEntryInfo cityentryInfo =null;
    	StringBuffer cityCount = new StringBuffer();
    	for(int i = 0 ; i < cityArray.length ; i++){
    		cityentryInfo =new CityEntryInfo();
    		cityentryInfo.setCitys((CityInfo)cityArray[i]);
    		cityentryInfo.setHead(editData);
    		cityColl.add(cityentryInfo);
    		try {
				CityInfo cityInfo =CityFactory.getRemoteInstance().getCityInfo(new ObjectUuidPK(cityentryInfo.getCitys().getId()));
				 if(i==0)
	             {
					 cityCount.append(cityInfo.getName());
	    		 }
	             else
	             {
	            	 cityCount.append(","+cityInfo.getName());
	             }
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} 
    	}
    	this.txtCityCount.setText(cityCount.toString());
    	//产品类型
    	ProductTypeEntryCollection productColl = this.editData.getProductType();
    	productColl.clear();
    	Object[] productArray = (Object[]) this.prmtProductType.getValue();
    	if(productArray==null){
    		return;
    	}
    	ProductTypeEntryInfo productentryInfo=null;
    	StringBuffer productCount = new StringBuffer();
    	for(int i=0;i<productArray.length;i++){
    		productentryInfo =new ProductTypeEntryInfo();
    		productentryInfo.setProductType((ProductTypeInfo) productArray[i]);
    		productColl.add(productentryInfo);
    		try {
				ProductTypeInfo productInfo = ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(productentryInfo.getProductType().getId()));
				 if(i==0)
	             {
					 productCount.append(productInfo.getName());
	    		 }
	             else
	             {
	            	 productCount.append(","+productInfo.getName());
	             }
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	this.txtProductCount.setText(productCount.toString());
    	//工程项目
    	ProjectBaseEntryCollection projectColl = this.editData.getProjectItem();
    	projectColl.clear();
    	Object[] projectArray = (Object[]) this.prmtProjectItem.getValue();
    	if(projectArray==null){
    		return;
    	}
    	ProjectBaseEntryInfo projectInfo=null;
    	StringBuffer projectCount = new StringBuffer();
    	for(int i=0;i<projectArray.length;i++){
    		projectInfo =new ProjectBaseEntryInfo();
    		projectInfo.setProjectItem((ProjectBaseInfo) projectArray[i]);
    		projectInfo.setHead(editData);
    		projectColl.add(projectInfo);
    		try {
				ProjectBaseInfo projectEnInfo = ProjectBaseFactory.getRemoteInstance().getProjectBaseInfo(new ObjectUuidPK(projectInfo.getProjectItem().getId()));
				 if(i==0)
	             {
					 projectCount.append(projectEnInfo.getName());
	    		 }
	             else
	             {
	            	 projectCount.append(","+projectEnInfo.getName());
	             }
    		} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	this.txtProjectCount.setText(projectCount.toString());
    	//位置
    	PositionSeatEntryCollection positionColl =this.editData.getPosition();
    	positionColl.clear();
    	Object[] positionArray = (Object[]) this.prmtPosition.getValue();
    	if(positionArray==null){
    		return;
    	}
    	PositionSeatEntryInfo positionInfo=null;
    	StringBuffer positionCount = new StringBuffer();
    	for(int i=0;i<positionArray.length;i++){
    		positionInfo =new PositionSeatEntryInfo();
    		positionInfo.setPositionSea((PositionSeatInfo) positionArray[i]);
    		positionInfo.setHead(editData);
    		positionColl.add(positionInfo);
    		try {
				PositionSeatInfo positionSeatInfo = PositionSeatFactory.getRemoteInstance().getPositionSeatInfo(new ObjectUuidPK(positionInfo.getPositionSea().getId()));
				 if(i==0)
	             {
					 positionCount.append(positionSeatInfo.getName());
	    		 }
	             else
	             {
	            	 positionCount.append(","+positionSeatInfo.getName());
	             }
    		} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	this.txtPositionCount.setText(positionCount.toString());
        super.storeFields();
    }
    /**
     * output actionSave_actionPerformed
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
    	Set projectDefectSet1 =   (Set) this.getUIContext().get("projectID");
    	Set productDefectSet1 =   (Set) this.getUIContext().get("productID");
        super.actionSave_actionPerformed(e);
        ProductDefectStoreFactory.getRemoteInstance().DefectSave(null, projectDefectSet1, productDefectSet1);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
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
			ProductDefectStoreInfo info = ProductDefectStoreFactory.getRemoteInstance().getProductDefectStoreInfo(new ObjectUuidPK(this.editData.getId()));
			ProductDefectStoreEntryCollection imageColl = info.getEntry();
			if (imageColl != null && imageColl.get(0)!=null && imageColl.get(0).getImageFile()!=null) {
				addImage(imageColl.get(0).getImageFile());
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} 
			//区域
			AreaEntryCollection areaColl = this.editData.getArea();
			AreaInfo[] area = new  AreaInfo[areaColl.size()];
			for(int i = 0 ; i< areaColl.size() ; i ++){
				AreaEntryInfo info = areaColl.get(i);
				area[i] = info.getArea();
			}
			this.prmtArea.setValue((area.length >0 &&area[0]!=null)?area:null);
			
			//城市
			CityEntryCollection cityColl = this.editData.getCity();
			CityInfo[] citys = new  CityInfo[cityColl.size()];
			for(int i = 0 ; i< cityColl.size() ; i ++){
				CityEntryInfo info = cityColl.get(i);
				citys[i] = info.getCitys();
			}
			this.prmtCity.setValue((citys.length>0&&citys[0]!=null)?citys:null);
			
			//产品类型
			ProductTypeEntryCollection productColl = this.editData.getProductType();
			ProductTypeInfo[] product = new  ProductTypeInfo[productColl.size()];
			for(int i = 0 ; i< productColl.size() ; i ++){
				ProductTypeEntryInfo info = productColl.get(i);
				product[i] = info.getProductType();
			}
			this.prmtProductType.setValue((product.length>0&&product[0]!=null)?product:null);
			
			//工程项目
			ProjectBaseEntryCollection projectColl = this.editData.getProjectItem();
			ProjectBaseInfo[] projectbase = new  ProjectBaseInfo[projectColl.size()];
			for(int i = 0 ; i< projectColl.size() ; i ++){
				ProjectBaseEntryInfo info = projectColl.get(i);
				projectbase[i] = info.getProjectItem();
			}
			this.prmtProjectItem.setValue((projectbase.length>0&&projectbase[0]!=null)?projectbase:null);
			
			//位置
			PositionSeatEntryCollection positionColl = this.editData.getPosition();
			PositionSeatInfo[] position = new  PositionSeatInfo[positionColl.size()];
			for(int i = 0 ; i< positionColl.size() ; i ++){
				PositionSeatEntryInfo info = positionColl.get(i);
				position[i] = info.getPositionSea();
			}
			this.prmtPosition.setValue(position.length>0&&position[0]!=null?position:null);
	}
	

    protected void kdtEntry_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtEntry_tableClicked(e);
		int rowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if(kdtEntry.getRow(rowIndex)!=null)
		{
			addImage((byte[])kdtEntry.getRow(rowIndex).getCell("imageFile").getValue());
		}
	}
    /**
     * output actionUpPhoto_actionPerformed
     */
    public void actionUpPhoto_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUpPhoto_actionPerformed(e);
        IRow row = null;
		if(this.kdtEntry != null)
		{
			IObjectValue detailData = new ProductDefectStoreEntryInfo();
			row = addLine(kdtEntry,detailData);
		}
		chooser.resetChoosableFileFilters();
		chooser.removeChoosableFileFilter(chooser.getFileFilter());
    	chooser.addChoosableFileFilter(new MyFileFilter(".jpg","JPG格式图片"));
    	int i=chooser.showOpenDialog(null);
    	if(i==KDFileChooser.APPROVE_OPTION){//当选择了图片后才进行下面操作
	    	File file = chooser.getSelectedFile();
//	        if(file!=null)
//	        {
//	        	imgPath=file;
//		        showImage(file);
//		        startX=0;startY=0;endX=0;endY=0;//重新导入图片时去掉 已经画的热点区域
//		        pnlRoomPic.repaint();
//		        
//		        if(imgPath!=null)//保存图片到分录中
//		        {
//					long len = imgPath.length();
//					byte[] bytes = new byte[(int)len];
//					BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(imgPath));
//					int r = bufferedInputStream.read(bytes);
//					if (r != len) {
//						throw new IOException("文件读取异常！");
//					}
//				    bufferedInputStream.close();
//				    row.getCell("imageName").setValue(file.getName());
//				    row.getCell("imagePath").setValue(file.getPath());
//				    row.getCell("imageFile").setValue(bytes);
//				}
//	        }
    	}
    }

    /**
     * output actionDelPhoto_actionPerformed
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
//				showImage(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置显示图片
	 * @param file
	 */
//	private void showImage(File file){
//		
//		pnlRoomPic.setImageFile(file);
//		this.kDPhotoPane.setViewportView(pnlRoomPic);
//		this.isShowedImage=true;
//	}
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

	
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProductDefectStoreFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	protected IObjectValue createNewData() {
		if(this.getUIContext().get("info") != null){
			return (ProductDefectStoreInfo)this.getUIContext().get("info");
		}
		return new ProductDefectStoreInfo();
	
	}
	
	 public SelectorItemCollection getSelectors() {
		 SelectorItemCollection sic = super.getSelectors();
		 sic.add("area.area.*");
		 sic.add("area.*");
		 sic.add("city.citys.*");
		 sic.add("citys.*");
		 sic.add("position.positionSea.*");
		 sic.add("positionSea.*");
		 sic.add("projectItem.projectItem.*");
		 sic.add("projectItem.*");
		 sic.add("productType.productType.*");
		 sic.add("productType.*");
	     return sic;
	    }   
}