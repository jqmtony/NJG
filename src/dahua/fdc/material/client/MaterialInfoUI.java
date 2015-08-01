/*
 * @(#)MaterialInfoUI.java
 * 
 * �����������������޹�˾��Ȩ���� 
 */

package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialGroupFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.material.MaterialIndexCollection;
import com.kingdee.eas.fdc.material.MaterialIndexFactory;
import com.kingdee.eas.fdc.material.MaterialIndexInfo;
import com.kingdee.eas.fdc.material.MaterialIndexValueCollection;
import com.kingdee.eas.fdc.material.MaterialIndexValueFactory;
import com.kingdee.eas.fdc.material.MaterialIndexValueInfo;
import com.kingdee.eas.fdc.material.MaterialInfoCollection;
import com.kingdee.eas.fdc.material.MaterialInfoFactory;
import com.kingdee.eas.fdc.material.MaterialInfoInfo;
import com.kingdee.eas.fi.rpt.IReportProcess;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * ������������Ϣ��<p>
 * @author ������
 * @version EAS 4.0
 * @see IReportProcess
 */

public class MaterialInfoUI extends AbstractMaterialInfoUI {
	
	/**ȱʡ�汾��ʶ*/
	private static final long serialVersionUID = 1L;
	
	/**��־*/
	private static final Logger logger = CoreUIObject.getLogger(MaterialInfoUI.class);
	
	/**������Դ�ļ�λ��*/
    public static final String MATERIALIMPORT_RESOURCEPATH = "com.kingdee.eas.fdc.material.client.MaterialImportResource";
	private static final String MATERIALINFO_RESOURCEPATH = "com.kingdee.eas.fdc.material.MaterialInfoResource";
   
	/** �����������table */
	public static KDTable tblMaterial = null;
	public static String rightUpTableSelectedRowId = null;
	
	private boolean bl = false;
	
	/**��ñ�T_MTR_MaterialInfo���е���Ϣ*/
	private List getMaterialInfos = null;

	/**��¼��Ӧ���Ƿ�Ϊ��*/
	private List supplierIsNullList = null; 
	
	public IUIWindow uiWindow = null ;
	
	/**����������еġ��������*/
	private String material_Type = null;
	
	/**����������еġ����ϱ��롱*/
	private String material_Number = null;
	
	/**����������еġ��������ơ�*/
	private String material_Name = null;
	
	/**����������еġ�����ͺš�*/
	private String material_Model = null;
	
	/**����������еġ���λ��*/
	private String material_Unit = null;
	
	/**����������еġ����ۡ�*/
	private String material_Price = null;
	
	/**����������еġ�����ʱ�䡱*/
	private String material_QuoteTime = null;
	
	/**����������еġ���Ӧ�̡�*/
	private String material_Supplier = null;
	
	/**����������еġ���Ŀ���ơ�*/
	private String material_ProjectName = null;
	
	/**����������еġ���ͬ���ơ�*/
	private String material_ContractName = null;
	
	/**����������еġ���Ч�ڡ�*/
	private String material_Validate = null;
	
	private ArrayList material_dataRepeat = null;
	
	 /**
	* ������<���캯��>
	* @author <luoxiaolong>
	* @param  <e>
	* @return  <null>
	* ����ʱ��  <2010/11/18> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
	public MaterialInfoUI() throws Exception {
		super();
		material_Type = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialType");
		material_Number = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialNumber");
		material_Name = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialName");
		material_Model = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Model");
		material_Unit = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Unit");
		material_Price = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Price");
		material_QuoteTime = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "QuoteTime");
		material_Supplier = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Supplier");
		material_ProjectName = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ProjectName");
		material_ContractName = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ContractName");
		material_Validate = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Validate");
		
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		if (getTypeSelectedTreeNode() != null)
			// �������Ϳ��԰����ϵĽڵ���󴫵�EditUI����ȥ�ˡ�
			this.getUIContext().put("materialNodeInfo",
					getTypeSelectedTreeNode().getUserObject());
	}

	/**
	 * ������������Ľڵ� ����ʱ�䣺2010-11-16
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.kDTree1
				.getLastSelectedPathComponent();
	}

	/**
	 * ��������ɾ�Ľ���򿪷�ʽ
	 * ����ʱ�䣺2010-11-16
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	private void openModel(String state) throws UIException {
		//��ò�����ϸ��Ϣ������ѡ�е�һ��
		IRow row= null;
		if(state.equals(OprtState.ADDNEW)){
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		}else {
			row = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex());
		}
		//���������ѡ���е�ID
		String materialInfoId = null;
		if (row != null && row.getCell("id") != null) {
			materialInfoId = String.valueOf(row.getCell("id").getValue() == null ? "" : row.getCell("id").getValue().toString());
		}
//		//����Ƿ�ѡ��
//		this.checkSelected();
	
		//�õ����Ķ���
		MaterialGroupInfo info = this.getMaterialGroupInfo();
		/*
		 * �õ�id��Ϊ��
		 */
		if (materialInfoId != null) {
			//�����µ�UIcontext����,
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put(UIContext.ID, materialInfoId);
			uiContext.put("materialNodeInfo", info);
			uiContext.put("materialInfoTable", this.MaterialInfo);
			uiContext.put("isViewEnbled", (Boolean)getUIContext().get("isViewEnbled"));
			
			
			
			/*
			 * �����õĵ�Ԫ��Ϊ����ô��Ϊ����ֵ
			 * ��������ת��Ҫ��ֵ���뵽UIContext������
			 */
			if(this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
					"number").getValue()!=null && !"".equals(this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
					"number").getValue())){
				
				uiContext.put("tblNumberValue", this.tblMain.getCell(
						this.tblMain.getSelectManager().getActiveRowIndex(),
				"number").getValue().toString().trim());
			}else{
				uiContext.put("tblNumberValue", "");
			}
			/*
			 * �����õĵ�Ԫ��Ϊ����ô��Ϊ����ֵ
			 */
			if (this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
			"name").getValue()!=null && !"".equals(this.tblMain.getCell(
			this.tblMain.getSelectManager().getActiveRowIndex(),
			"name").getValue())) {
				
				uiContext.put("tblNameValue",this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "name")
						.getValue().toString().trim());
			}else{
				uiContext.put("tblNameValue","");
			}
			/*
			 * �����õĵ�Ԫ��Ϊ����ô��Ϊ����ֵ
			 */
			if(this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
			"model").getValue()!=null && !"".equals(this.tblMain.getCell(
			this.tblMain.getSelectManager().getActiveRowIndex(),
			"model").getValue())){
				
				uiContext.put("tblModelValue", this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"model")
						.getValue().toString().trim());
				
			}else{
				uiContext.put("tblModelValue", "");
			}
			
			/*
			 * �����õĵ�Ԫ��Ϊ����ô��Ϊ����ֵ
			 */
			if (this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
			"baseUnit.name").getValue()!=null && !"".equals(this.tblMain.getCell(
			this.tblMain.getSelectManager().getActiveRowIndex(),
			"baseUnit.name").getValue())) {
				uiContext.put("tblUnitValue", this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"baseUnit.name")
						.getValue().toString().trim());
				
			} else {
				uiContext.put("tblUnitValue", "");
			}
			/*
			 * �����õĵ�Ԫ��Ϊ����ô��Ϊ����ֵ
			 */
			if (this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
			"id").getValue()!=null && !"".equals(this.tblMain.getCell(
			this.tblMain.getSelectManager().getActiveRowIndex(),
			"id").getValue())) {
				uiContext.put("tblMaterialId", this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "id")
						.getValue().toString().trim());
				
			} else {
				uiContext.put("tblMaterialId", "");
			}
			
			/*
			 * �����õĵ�Ԫ��Ϊ����ô��Ϊ����ֵ
			 */
			if(MaterialInfo.getRowCount() != 0 && MaterialInfo.getSelectManager().size() != 0){
				uiContext.put("materialInfoId", String.valueOf(this.MaterialInfo.getCell(
						this.MaterialInfo.getSelectManager().getActiveRowIndex(),"id") == null ? "" : this.MaterialInfo.getCell(
								this.MaterialInfo.getSelectManager().getActiveRowIndex(),"id").getValue().toString().trim()));
	        }
			
		

			//����UI�Ĵ򿪷�ʽ
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			//��ʾUI
			IUIWindow uiWindow = uiFactory.create(MaterialInfoEditUI.class
					.getName(), uiContext, null, state);

			uiWindow.show();
		}
	}

	/**
	 * �����������ʼ������ ����ʱ�䣺2010-11-8
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	public void onLoad() throws Exception {
		
		// ���ø��ڵ�+��
		this.kDTree1.setShowsRootHandles(true);
		super.onLoad();
		
		/*��������ѯ��ť  */
		this.actionQuery.setVisible(true);
		this.actionQuery.setEnabled(true);
		/*������*/
		BuilderTreeUtil.getTreeUtilInstance().buiderTree(this.kDTree1,
				MaterialGroupFactory.getRemoteInstance(), EASResource.getString(MATERIALINFO_RESOURCEPATH,"treeTittle"));
		/* ���������¼�*/
		this.kDTree1.addTreeSelectionListener(new TreeSelectionListener() {
			/* ֵ�ı��¼�*/
			public void valueChanged(TreeSelectionEvent e) {
				try {
					treeMain_valueChanged(e);
				} catch (Exception e1) {
					handUIException(e1);
				} finally {
				}
			}

		});
		/*�����ϱ����ӵ���¼�*/
		this.tblMain.addKDTMouseListener(new KDTMouseListener() {

			public void tableClicked(KDTMouseEvent e) {
				if (e.getClickCount() == 1) {
					try {
						tblMain_tableClicked(e);
					} catch (Exception e1) {
						handUIException(e1);
					}
				} else if (e.getClickCount() == 2) {
					SysUtil.abort();
				}
			}

		});
		/*�����ϱ�����ѡ���¼�*/

		this.tblMain.addKDTSelectListener(new KDTSelectListener() {

			public void tableSelectChanged(KDTSelectEvent e) {

				try {
					tblMain_tableSelectChanged(e);
					tblMain_tableSelectChanged2(e);
				} catch (Exception e1) {
					handUIException(e1);  
				}
			}

		});
		/*��������Ϣ������������¼�*/
		this.MaterialInfo.addKDTMouseListener(new KDTMouseListener() {

			public void tableClicked(KDTMouseEvent e) {
				if (e.getClickCount() == 1) {
					SysUtil.abort();
				} else if (e.getClickCount() == 2) {
					try {
						checkSelected();
						openModel(OprtState.VIEW);
					} catch (UIException e1) {
						handUIException(e1);
					}
				}
			}

		});
		//���ð�ť״̬
		setBtnState();
		setSortAuto(tblMain);
	}

	
	/** 
	 * ���ñ���Զ�������
	 * @Author��owen_wen
	 * @CreateTime��2012-11-19
	 */
	private void setSortAuto(KDTable table) {
		for (int i = 0, size = table.getColumnCount(); i < size; i++) {
			table.getColumn(i).setSortable(true);
		}
		KDTSortManager sm = new KDTSortManager(table);
		sm.setSortAuto(true);
	}

	/**
	 * ���������ð�ť״̬
	 * ����ʱ�䣺2010-11-26<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	private void setBtnState(){
		/*���ú�׼��ťͼ��*/
		this.audit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		/*���÷���׼��ťͼ��*/
		this.unAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
		/*���ú�׼��ť���˵���ͼ��*/
		this.auditItem.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		/*���÷���׼��ť���˵���ͼ��*/
		this.unAuditItem.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
		/*���÷ֲ�ͼ��ťͼ��*/
		this.showDotsMapItem.setIcon(EASResource.getIcon("imgTbtn_chart"));
		/*��������ͼ��ťͼ��*/
		this.showLineMapItem.setIcon(EASResource.getIcon("imgTbtn_chart"));
		/*���ô�Excel���밴ťͼ��*/
		this.ExcelImportItem.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		/*���õ���Excelģ�水ťͼ��*/
		this.ExportExcelItem.setIcon(EASResource.getIcon("imgTbtn_dcdwj"));
		/*���ôӲ�����Ϣ���밴ťͼ��*/
		this.MaterialImportItem.setIcon(EASResource.getIcon("imgTbtn_importfromzz"));
		/*���õ��밴ť״̬*/
		this.menuItemImportData.setVisible(false);
		/*���õ�����ť״̬*/
		this.menuItemExportData.setVisible(false);
		/*���÷ֲ�ͼ��ť״̬*/
		this.showDotsMapItem.setEnabled(true);
		/*��������ͼ��ť״̬*/
		this.showLineMapItem.setEnabled(true);
		/*���õ���Excelģ�水ť״̬*/
		this.ExportExcelItem.setEnabled(true);
		/*���ô�Excel���밴ť״̬*/
		this.actionImportData.setEnabled(true);
		/*���ôӲ�����Ϣ���밴ť״̬*/
		this.actionImportDataFormMaterial.setEnabled(true);
		/*���õ�����ť״̬*/
		this.actionExportExcel.setEnabled(true);
	}
	
	/**
	 * ��������굥�����ϱ��¼� ����ʱ�䣺2010-11-8
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	public  void reflushTable() throws Exception{
		tblMain_tableSelectChanged2(selectEvent); 
		
	}
	
	/**
	 * ������������Ϣ����ϸ���¼����� ����ʱ�䣺2010-11-12
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	int count = 0;
	private static String materialInfoId = null ;
	static  KDTSelectEvent selectEvent = null ;
	private void tblMain_tableSelectChanged2(KDTSelectEvent e) throws Exception {
		
			super.tblMain_tableSelectChanged(e);
			
			// �Ƿ�ѡ��
			if (e.getSelectBlock() == null){
				return;
			}
		
		/*
		 * ��õ�ǰѡ�е��У��ó�ѡ���е�ID
		 */
		selectEvent = e ;
		KDTSelectBlock curSelect = e.getSelectBlock();
		int top = curSelect.getTop();
		if(null != this.getMainTable().getCell(top,this.getKeyFieldName())){
			if (top > -1 && null != this.getMainTable().getCell(top,this.getKeyFieldName()).getValue()) {
				materialInfoId = this.getMainTable().getCell(top,this.getKeyFieldName()).getValue().toString();
			}
		}
		
		if(materialInfoId != null){
			// ���ò�ѯ��ϸ��Ϣ����
			this.builderMaterialInfoTable();
		}
		count++;
	}

	/**
	 * ���������ѡ���¼� ����ʱ�䣺2010-11-9
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		/**�õ�ѡ���е��к�*/
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		
		if (rowIndex == -1)
			return;
		
		String treeNode = this.getMaterialTittle();
		/** �õ�ѡ����model������ͺţ��е�ֵ*/
		Object materialModel = this.tblMain.getRow(rowIndex).getCell("model").getValue();
		Object materialNumber = this.tblMain.getRow(rowIndex).getCell("number").getValue();
		/**��MaterInfo�����ַ�*/
		StringBuffer sb = new StringBuffer();
		if(treeNode != null){
			sb.append(treeNode);
		}
		/*
		 * ���ѡ���е�number(����)��Ϊ�� ����ֵ
		 */
		if (materialNumber != null) {
			sb.append("-" + materialNumber);
			this.kDContainer2.setTitle(sb.toString());
		}
		/*
		 * ���ѡ���е�model������ͺţ���Ϊ�� ����ֵ
		 */
		if (materialModel != null) {
			sb.append("-" + materialModel);
			this.kDContainer2.setTitle(sb.toString());
		}
		sb.reverse();
	}

	/**
	 * ������������ı�ֵ�����¼� ����ʱ�䣺2010-11-5
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		/**��������������Ϣ��*/
		this.builderMaterialTable();
		/*���û������ϱ���*/
		this.kDContainer1.setTitle(this.getMaterialTittle());
		MaterialInfo.removeRows();
		int rowCount = this.tblMain.getRowCount();
		/*ѭ����������ʱ����ʾ��ʽ*/
		for (int i = 0; i < rowCount; i++) {
			/*
			 * ��������е�ֵ��Ϊ�ս����ĸ�ʽ����Ϊ yyyy-mm-dd
			 */
			if (null != this.tblMain.getRow(i).getCell("quoteTime").getValue()) {
				String quoteTime = this.tblMain.getRow(i).getCell("quoteTime")
						.getValue().toString();
				quoteTime = quoteTime.substring(0, 10);
				this.tblMain.getRow(i).getCell("quoteTime").setValue(quoteTime);
			}
		}
		
		/*Ĭ��ѡ�е�һ��*/
		if(this.tblMain.getRowCount() != 0){
			this.tblMain.getSelectManager().select(0,0);
		}
	}

	/**
	 * ������������Ľڵ�����
	 * ����ʱ�䣺2010-11-20<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	private String getMaterialTittle(){
		TreePath path = this.kDTree1.getSelectionPath();
		// ����path�õ��ڵ�
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
				.getLastPathComponent();
		return treenode.toString();
		
	}

	/**
	 * ��������õ�ǰ��ϸ��Ϣ�����״̬
	 * ����ʱ�䣺2010-11-22<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	private boolean getMaterialInfoState() throws EASBizException, BOSException{
		/*��ò�����Ϣ��ID*/
		String materialInfoId = this.getMaterialInfoId();
		String state = this.getState(materialInfoId);
		if(materialInfoId!=null && !materialInfoId.equals("")){
			if(null == state || "".equals(state)){
				/*����ǿգ������κ�����*/
			}
			/**����Ǻ�׼״̬�����ܽ������²���*/
			else if (state.equals("������")) {
				FDCMsgBox.showWarning(this,EASResource.getString(MATERIALINFO_RESOURCEPATH,"auditState"));
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * ���״̬
	 * @param materialInfoId
	 * @return
	 */
	private String getState(String materialInfoId){
		Map map = new HashMap();
		map.put("FID", materialInfoId);
		List fields = new ArrayList();
		fields.add("FState");
		String state = null;
		try {
			List list = MaterialInfoFactory.getRemoteInstance().getMaterialData(map, "T_MTR_MaterialInfo", fields);
			Map mapField = (Map) list.get(0);
			state = mapField.get("FState").toString();
			if(state.equals("4AUDITTED")){
				state = "������";
				return state;
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return state;
	}
	/**
	 * ��������ñ༭�������� 
	 * ����ʱ�䣺2010-11-8
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	protected String getEditUIName() {
		return MaterialInfoEditUI.class.getName();
	}

	/**
	 * ���������ñ༭����򿪵ķ�ʽ 
	 * ����ʱ�䣺2010-11-8
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * ���������Զ�̵��ýӿ� 
	 * ����ʱ�䣺2010-11-8
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return MaterialInfoFactory.getRemoteInstance();
	}




	boolean checkIsAddNew = false;

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkIsAddNew = true;
		this.checkSelected();
		this.openModel(OprtState.ADDNEW);
		// this.sendValue();
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkIsAddNew = false;
		this.checkSelected();
		this.materialInfoCheckSelected();
		this.openModel(OprtState.VIEW);
	}

	/**
	 * �������޸���ϸ��Ϣ���ж�Ϊ����״̬ʱ�����޸� ����ʱ�䣺2010-11-22
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsAddNew = false;
		this.checkSelected();
		this.materialInfoCheckSelected();
		boolean flag = this.getMaterialInfoState();
		/*
		 * ���Ϊ���״̬���ܽ��б༭����
		 */
		if (!flag) {
			this.openModel(OprtState.EDIT);
		}
	}

	/**
	 * ������ɾ������ ����ʱ�䣺2010-12-2
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkIsAddNew = false;
		this.checkSelected();
		this.materialInfoCheckSelected();
		boolean flag = this.getMaterialInfoState();

		if (!flag && MaterialInfo.getRowCount() != 0 && MaterialInfo.getSelectManager().size() != 0) {
			// ��ò�����ϸ��Ϣ������ѡ�е�һ��
			IRow row = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex());
			// ���������ѡ���е�ID
			String materialInfoId = null;
			if (row.getCell("id").getValue() != null) {
				materialInfoId = row.getCell("id").getValue().toString();
			}

			if (MsgBox.showConfirm2(EASResource.getString(MATERIALINFO_RESOURCEPATH, "sureDelete")) == 0) {
				MaterialInfoFactory.getRemoteInstance().deleteMaterialInfoRecord(new ObjectUuidPK(BOSUuid.read(materialInfoId)));
				MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "delDone"));
			} else {
				SysUtil.abort();
			}
		}
		this.refreahAfterAuditAndUnAudit(e);
	}

	/**
	 * ������ ˢ�·��� ����ʱ�䣺2010-12-2
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		/** ������ */
		BuilderTreeUtil.getTreeUtilInstance().buiderTree(this.kDTree1, MaterialGroupFactory.getRemoteInstance(),
				EASResource.getString(MATERIALINFO_RESOURCEPATH, "treeTittle"));
		this.MaterialInfo.removeRows();
		this.tblMain.removeRows();
		/*
		 * 
		 * �˴�Ϊ������,������ָ��Ϊ��ʱ,�򲻻���� ��������ָ���ͷ�д���0,���ɾ��
		 */
		if (headLength > 0) {
			for (int i = 0; i < headLength; i++) {
				this.MaterialInfo.removeColumn(0);
			}
		}
		headLength = -1;

	}

	public void refreahAfterAuditAndUnAudit(ActionEvent e) throws EASBizException, BOSException {
		int tblIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		/*
		 * ���Ϊѡ�б�����Ϣ���е�һ�У��Ͳ����κζ���
		 */
		if (MaterialInfo.getRowCount() == 0 && !checkIsAddNew) {
			this.tblMain.getSelectManager().select(tblIndex, tblIndex);
			// MsgBox.showWarning("��ѡ�в��ϱ�����Ϣ����һ�У�");
			return;
		}
		builderMaterialTable();

		/*
		 * ���ID��Ϊ�գ������¹���������Ϣ��
		 */
		if (materialInfoId != null) {
			this.builderMaterialInfoTable();
		}

		this.tblMain.getSelectManager().select(tblIndex, tblIndex);
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		this.showWindow(bl);
	}

	   /**
	 * 
	 * @description �����Լ��Ľ��� �����ȼ��ع��˽���
	 * @author ���˳�
	 * @createDate 2010-11-17
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	private void showWindow(boolean blo) throws UIException {
		if (blo) {
			UIContext uiContext = new UIContext(this);
			IUIFactory uiFactory;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(MaterialInfoQueryUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
	}

	/**
	 * ������<�Ӳ�����ϸ������>
	 * 
	 * @author <luoxiaolong>
	 * @param <e>
	 * @return <null> ����ʱ�� <2010/11/18>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	public void actionImportDataFormMaterial_actionPerformed(ActionEvent e)
			throws Exception {
		
		/** �ж��Ƿ�ѡ���� */
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceAnyRow"));
			return;
		}

		tblMaterial = this.MaterialInfo;
		/** ����ѡ����id */
		rightUpTableSelectedRowId = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();

		/* ����������ϸ������ */
		MaterialInfoUI materialUI = (MaterialInfoUI) this;
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("materialUI", materialUI);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(PartAMaterialImportorListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();

		/* ˢ�±�ҳ�� */
		this.refreahAfterAuditAndUnAudit(e);

	}

    /**
	 * ������<����Excel�ļ�>
	 * 
	 * @author <luoxiaolong>
	 * @param <e>
	 * @return <null> ����ʱ�� <2010/11/18>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {

		/* �õ�ѡ�е�path */
		TreePath path = this.kDTree1.getSelectionPath();
		if (path == null) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceNode"));
			return ;
		}
		/* ����path�õ��ڵ� */
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();

		// �õ����Ķ���
		MaterialGroupInfo materialGroupInfo = null;
		if (treenode != null && treenode.getUserObject() instanceof MaterialGroupInfo) {
			// �õ����Ķ���
			materialGroupInfo = (MaterialGroupInfo) treenode.getUserObject();
		}

		if (null == materialGroupInfo) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceNode"));
			return ;
		}

		/* ����Ĭ��·�� */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));

		/* ���ù����� */
		chooser.setFileFilter(new DataImportFilterType());

		/* ���ñ��� */
		chooser.setDialogTitle(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ExportExcelModel"));

		/* �����Ի��� */
		chooser.showSaveDialog(null);

		if (null == chooser.getSelectedFile()) {
			return;
		}

		/** ���ѡȡ�ļ�·�� */
		String filePath = chooser.getSelectedFile().getPath();

		/** ����һ�������� */
		HSSFWorkbook wb = new HSSFWorkbook();

		/** ����һ�ű� */
		HSSFSheet sheet = wb.createSheet();

		/** ʵ����ģ���� */
		CreateExcelModel model = new CreateExcelModel();

		/* ���ָ�����Ƽ��� */
		List fNames = getMaterialIndexName(materialGroupInfo.getId().toString());

		/* ���÷���,����Excelģ�� */
		model.createExcelMode(fNames, wb, sheet);

		try {
			/* ���÷���,���Excelģ�� */
			model.outputExcel(wb, filePath);
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ExportModelSuccess"));
		} catch (Exception ex) {
			/* �����ļ����ڱ�ʹ�õ���� */
			logger.error(ex.getMessage());
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportModeFailBecauseFileUsed"));
		}
	}

	/**
	 * ������<�������ָ������>
	 * 
	 * @author <luoxiaolong>
	 * @param <materialGruopId>
	 * @return <null> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	private List getMaterialIndexName(String materialGruopId) {

		/** ����һ������ֵ���� */
		List fNames = new ArrayList();
		Map filterMap = new HashMap();
		filterMap.put("FMaterialGroupID", materialGruopId);
		filterMap.put("FIsEnabled", new Integer(1));
		List list = new ArrayList();
		list.add("FName_l2");
		try {
			fNames = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, "T_MTR_MaterialIndex", list);
		} catch (Exception e) {
			handUIException(e);
		}
		return fNames;
	}

	/**
	 * ������<��Excel����>
	 * 
	 * @author <luoxiaolong>
	 * @param <e>
	 * @return <null> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p> 
	 * 
	 * @see <��ص���> 
	 */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * �������̣� 1������Excel��һ��һ�еĽ���Excel��������һ�о�����Ӧ����֤����֤��ͨ���͸�����ʾ������ֹ����
		 * ��֤�����У������ͨ���ˣ��ͷ���һ��List��
		 * 2������List����ÿһ�����ݸ������������ж��Ƿ���ڸ�����ָ�꣬�����ھ���ʾ������ָ�겻���ڣ�����ֹ���� 3�������ݿ������������
		 */

		//		/** �ж��Ƿ�ѡ���� */
		//		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
		//			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceAnyRow"));
		//			return ;
		//		}
		
		getMaterialInfos = getMaterialAllInfos();

		/* ������½Ǳ��е����� */
		// importDataFormExcel();
		/* �¹��ܣ��µ��� */
		ImportDataFormExcel idfe = new ImportDataFormExcel();
		idfe.importData(this.MaterialInfo, this.tblMain, getMaterialInfos);

		/* ˢ��һ��ҳ�� */
		this.refreahAfterAuditAndUnAudit(e);
	}

    /**
	 * ������<���Excel�ļ�����������>
	 * 
	 * @author <luoxiaolong>
	 * @param <null>
	 * @return <null> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
    private void importDataFormExcel() {
		material_dataRepeat = new ArrayList();
		/** ��ռ��� */
		supplierIsNullList = new ArrayList();
		/* ��һ�ַ�ʽ����׼ģ�嵼�� */
		// DatataskCaller task = new DatataskCaller();
		// task.setParentComponent(this);
		// if (getImportParam() != null){
		// task.invoke(getImportParam(), DatataskMode.ImpMode,true,true);
		// }
		// actionRefresh_actionPerformed(e);

		/* �ڶ��ַ�ʽ��POI��ʽ���� */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));
		/* ���ù����� */
		chooser.setFileFilter(new DataImportFilterType());
		/* �����Ի��� */
		chooser.showDialog(null, EASResource.getString(MATERIALINFO_RESOURCEPATH, "importForExcel"));
		/* �ж��Ƿ�ѡ���ļ� */
		if (chooser.getSelectedFile() == null) {
			return;
		}
		/* ���ѡȡ�ļ�·�� */
		String filePath = chooser.getSelectedFile().getPath();
		/* ������Excel�������ļ������� */
		HSSFWorkbook excelWork = null;
		HSSFSheet sheet = null;
		try {
			excelWork = new HSSFWorkbook(new FileInputStream(filePath));
			/** �����Թ����������,����������,��Excel�ĵ��У���һ�Ź������ȱʡ������0 */
			sheet = excelWork.getSheetAt(0);
		} catch (Exception e) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseConfirmFileIsRight"));
			return;
		}
		/** ����һ�������� */
		Iterator it = sheet.rowIterator();

		/** ����һ�����飬������������� */
		String[] baseColumnName = { material_Type, material_Number, material_Name, material_Model, material_Unit, material_Price, material_QuoteTime,
				material_Supplier, material_ProjectName, material_ContractName, material_Validate };

		/** ����һ���յļ��ϣ���������ָ������ */
		List otherColumnList = new ArrayList();

		/** ����һ���յ�Map����������ָ�� */
		Map otherColumnMap = new HashMap();

		/** ����һ���ռ��ϣ�������������������Լ�ֵ�Ե���ʽ���棬��Ϊ������ֵΪ�к� */
		List baseColumnList = new ArrayList();

		/** ����һ���յ�Map������������� */
		Map baseColumnMap = new HashMap();

		/** ����һ�����ϴ���ÿһ�е����� */
		List oneRowDataList = new ArrayList();

		/** ����һ�����ϣ����������е����� */
		List getAllRowDataFormExcel = new ArrayList();

		/* �ж��Ƿ����ģ�� */
		if (!judgeIsModel(sheet.rowIterator(), baseColumnName)) {
			return;
		}

		if (sheet.getLastRowNum() < 1) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ModelError"));
			return;
		} else if (sheet.getLastRowNum() < 2) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "QuoteInformationNull"));
			return;
		}

		/* ѭ����ȡÿһ�� */
		while (it.hasNext()) {
			/** ����ж��� */
			HSSFRow row = (HSSFRow) it.next();
			/* ��������һ�� */
			if (row.getRowNum() < 1) {
				continue;
			}
			/** ����һ���ж��� */
			HSSFCell cell = null;
			Map rowMap = new HashMap();
			for (int i = 0; i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				String cellValue = null;

				if (null != cell) {
					/* �����Ԫ���ݣ�cell.getStringCellValue()����ȡ���ڵ�Ԫ��ֵ */
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						cellValue = cell.getStringCellValue();
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							double d = cell.getNumericCellValue();
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							cellValue = format.format(HSSFDateUtil.getJavaDate(d));
						} else {
							// cellValue =
							// String.valueOf(cell.getNumericCellValue());
							DecimalFormat df = new DecimalFormat("###000.0000000000000000");
							cellValue = df.format(cell.getNumericCellValue());
							if (cellValue.indexOf(".") != -1) {
								String m = cellValue.substring(cellValue.indexOf("."));
								if (Double.parseDouble(m) == 0) {
									cellValue = cellValue.substring(0, m.indexOf("0") + cellValue.indexOf(".") - 1);
								} else {
									cellValue = cellValue.substring(0, cellValue.indexOf(".") + 7);
								}
							}
						}
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						cellValue = String.valueOf(cell.getBooleanCellValue());
					} else {
						cellValue = "";
					}
				} else {
					cellValue = "";
				}
				if (cellValue.indexOf(",") != -1) {
					FDCMsgBox.showInfo("��������ʧ�ܣ��ڵ���������в��ܺ���Ӣ�Ķ��ţ�");
					return;
				}
				int m = -1;
				/* �����ڶ��е��� */
				if (row.getRowNum() == 1) {
					for (int j = 0; j < baseColumnName.length; j++) {
						/* �ж��Ƿ�Ϊ�����У�����¼����һ�� */
						if (cellValue.equals(baseColumnName[j])) {
							baseColumnMap.put("base_" + i, baseColumnName[j]);
							baseColumnList.add(baseColumnMap);
							m = 1;
							break;
						}
					}
					if (cellValue != null && !"".equals(cellValue) && m == -1) {
						otherColumnMap.put("other_" + i, cellValue);
						otherColumnList.add(otherColumnMap);
					}
				}

				/* ����>2�е��� */
				if (row.getRowNum() > 1) {
					Map thisRow = null;
					String key = null;
					int keyIndex = -1;

					int n = -1;
					/* ѭ�������� */
					for (int j = 0; j < baseColumnList.size(); j++) {
						thisRow = (Map) baseColumnList.get(j);
						key = thisRow.keySet().toArray()[j].toString();
						keyIndex = Integer.parseInt(key.substring(key.indexOf("_") + 1));
						if (keyIndex == i) {
							/* �Լ�ֵ�Ե���ʽ����ֵ */
							rowMap.put(thisRow.get(key), cellValue);
							n = 1;
							break;
						}
					}

					if (n == -1) {
						/* ѭ������ָ���� */
						for (int j = 0; j < otherColumnList.size(); j++) {
							thisRow = (Map) otherColumnList.get(j);
							key = thisRow.keySet().toArray()[j].toString();
							keyIndex = Integer.parseInt(key.substring(key.indexOf("_") + 1));
							if (keyIndex == i && cellValue != null) {
								String tempCellValue = cellValue;
								try {
									cellValue = String.valueOf(Double.parseDouble(cellValue));
									cellValue = cellValue.substring(0, cellValue.indexOf("."));
								} catch (Exception ex) {
									cellValue = tempCellValue;
								}
								/* �Լ�ֵ�Ե���ʽ����ֵ */
								rowMap.put(thisRow.get(key), cellValue);
								break;
							}
						}
					}
				}

			}

					if (row.getRowNum() > 1) {
				/* ����һ�н��������ݴ��뼯���� */
				oneRowDataList.add(rowMap);
			}

			/* �û����ݴ��� */
			if (row.getRowNum() > 1) {

				int isExist = -1;
				List newList = oneRowDataList;
				for (int n = 0; n < newList.size(); n++) {
					Map map = (Map) newList.get(n);
					Object[] obj = map.values().toArray();
					for (int f = 0; f < obj.length; f++) {
						if (!"".equals(obj[f].toString().trim())) {
							isExist = 1;
							break;
						}
					}
				}

				if (isExist == 1) {

					/* ��֤���������Ƿ������б��������ظ� */
					if (!validateDataIsRepeat(oneRowDataList, row.getRowNum(), baseColumnName)) {
						return;
					}

					/* ��֤ͨ������һ�����ݴ��뼯���� */
					getAllRowDataFormExcel.add(oneRowDataList);
					/* ��ռ��� */
					oneRowDataList = new ArrayList();
				}// else{
				// MsgBox.showInfo("����ʧ�ܣ���Excel��ĵ�" + (row.getRowNum() + 1) +
				// "�У�Ϊ���У�");
				// return ;
				// }
			}
		}

		/* ��Ӧ��Ϊ�ջ򲻴��� */
		if (supplierIsNullList.size() > 0) {
			StringBuffer isNull = new StringBuffer();
			isNull.append(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel"));
			for (int k = 0; k < supplierIsNullList.size(); k++) {
				if (k == (supplierIsNullList.size() - 1)) {
					isNull.append(supplierIsNullList.get(k).toString());
				} else {
					isNull.append(supplierIsNullList.get(k).toString() + "��");
				}
			}
			MsgBox.showInfo("��������ʧ�ܣ�" + isNull + "�У���Ӧ��Ϊ�ջ򲻴��ڣ�");
			return ;
		}
		
		/* �����ظ� */
		if (material_dataRepeat.size() > 0) {
			StringBuffer isNull = new StringBuffer();
			isNull.append(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel"));
			for (int k = 0; k < material_dataRepeat.size(); k++) {
				if (k == (material_dataRepeat.size() - 1)) {
					isNull.append(material_dataRepeat.get(k).toString());
				} else {
					isNull.append(material_dataRepeat.get(k).toString() + "��");
				}
			}
			if (this.MaterialInfo.getRowCount() == 0) {
				MsgBox.showInfo("��������ʧ�ܣ�" + isNull + "������������Excel��ǰ���������ظ���");
			} else {
				MsgBox.showInfo("��������ʧ�ܣ�" + isNull + "�����������ظ���");
			}
			return;
		}
		
		/* �����ݿ��в������� */
		if (addImportData(getAllRowDataFormExcel, baseColumnName) >= 1) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataSuccess"));
			return;
		} else {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "OperateFail"));
			return ;
		}
		
    }

	/**
	 * ������<�ж�ģ���Ƿ���ȷ>
	 * 
	 * @author <luoxiaolong>
	 * @param <it,baseColumnName>
	 * @return <boolean> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	public boolean judgeIsModel(Iterator it, String[] baseColumnName) {

		/** ����һ������������Excel�����ж��������� */
		int rowCount = -1;

		while (it.hasNext()) {
			/** ����ж��� */
			HSSFRow row = (HSSFRow) it.next();

			/* ���� �������� �� */
			if (row.getRowNum() == 1) {
				++rowCount;
				/* ѭ���ж��ڡ������������Ƿ�����ϲ��Ļ����У�����������ģ�壬��֮�򲻷��� */
				for (int i = 0; i < baseColumnName.length; i++) {
					int m = -1;
					for (int j = 0; j < row.getLastCellNum(); j++) {
						HSSFCell cell = row.getCell(j);
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING && baseColumnName[i].equals(cell.getStringCellValue())) {
							m = 1;
							break;
						}
					}
					/* �����ڻ�����ʱ */
					if (m == -1) {
						MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MustHave") + baseColumnName[i]
								+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Column"));
						return false;
					}
				}
				break;
			}
		}

		/* �ж������Excel����û������ */
		if (rowCount == -1) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ModelError"));
			return false;
		}

		return true;
	}

	/**
	 * ������<�����ݿ��ﵼ������>
	 * 
	 * @author <luoxiaolong>
	 * @param <allDataRowExcel,baseColumnName>
	 * @return <int> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
    public int addImportData(List allDataRowExcel, String[] baseColumnName) {
    	
    	
    	/** ����һ������ֵ���� */
		int result = 0;

		/* �����Excel���������������� */
		List allDataRow = allDataRowExcel;
    	
    	/* ���ݹ�Ӧ�̲�ѯ������Ӧ������Ӧ�����±���ʱ�� */
		List getLastestQuoteTime = getLastestQuoteTime();
    	
    	/* ѭ���������������������ݿ��� */
		for (int i = 0; i < allDataRow.size(); i++) {
			if (null == allDataRow.get(i)) {
				continue;
			}
			List row = (List) allDataRow.get(i);

			/* ����ÿһ������ */
			for (int j = 0; j < row.size(); j++) {

				Map rowMap = (Map) row.get(j);

				/** �����н�����Ϊһ������ʱ�� */
				Timestamp terminalDate = Timestamp.valueOf("2050-12-31 00:00:00");

				/** ������Ч��Ч 0��Ч 1��Ч */
				int isValidDate = 0;
				if (terminalDate.getTime() > new java.util.Date().getTime()) {
					isValidDate = 1;
				}

				/* ����һ�����ϣ�����ÿһ�е����� */
				List params = new ArrayList();

				/** ȡ������ID */
				String bosId = getBOSID("9D390CBB");
				params.add(bosId);

				/* ����materialId */
				// String fMaterialID =
				// this.tblMain.getRow(this.tblMain.getSelectManager
				// ().getActiveRowIndex()).getCell("id").getValue().toString();
				Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
				/* �����������ƺͱ���ȷ��materialId */
				String materialName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
				String materialNumber = String.valueOf(rowMap.get(material_Number) == null ? "" : rowMap.get(material_Number).toString());
				if (tDouble.matcher(rowMap.get(material_Number).toString().trim()).matches()) {
					materialNumber = materialNumber.substring(0, materialNumber.indexOf("."));
				}
				if (tDouble.matcher(rowMap.get(material_Name).toString().trim()).matches()) {
					materialName = materialName.substring(0, materialName.indexOf("."));
				}
				Map materialMap = new HashMap();
				materialMap.put("FName_l2", materialName);
				materialMap.put("FNumber", materialNumber);
				String fMaterialID = getMaterialIDByFNameAndFNumber("T_BD_Material", "FID", materialMap);
				params.add(fMaterialID);

				/* ��������Ŀ���ơ�һ�� */
				String projName = String.valueOf(rowMap.get(material_ProjectName) == null ? "" : rowMap.get(material_ProjectName).toString());
				Map projMap = new HashMap();
				projMap.put("FName_l2", projName);
				/* ������Ŀ���Ʋ�ѯ��Ŀid */
				String projId = this.getIdByName("T_FDC_CurProject", "FID", projMap);
				if (projId != null) {
					params.add(projId);
				} else {
					params.add("");
				}

				/* ��������ͬ���ơ�һ�� */
				String conName = String.valueOf(rowMap.get(material_ContractName) == null ? "" : rowMap.get(material_ContractName).toString());
				Map conMap = new HashMap();
				conMap.put("FName", conName);
				/* ���ݺ�ͬ���Ʋ�ѯ��Ŀid */
				String conId = this.getIdByName("T_CON_ContractBill", "FID", conMap);
				if (conId != null) {
					params.add(conId);
				} else {
					params.add("");
				}

				BigDecimal price = new BigDecimal(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString());
				params.add(price);
				String quoteTime = String.valueOf(rowMap.get(material_QuoteTime) == null ? "2010-11-11" : rowMap.get(material_QuoteTime).toString());
				params.add(quoteTime);

				/* ��������Ӧ�̡�һ�� */
				String supplier = String.valueOf(rowMap.get(material_Supplier) == null ? "" : rowMap.get(material_Supplier).toString());
				Map supMap = new HashMap();
				supMap.put("FName_l2", supplier);
				/* ���ݹ�Ӧ�����Ʋ�ѯ��Ӧ��id */
				String supId = this.getIdByName("T_BD_Supplier", "FID", supMap);
				if (supId != null) {
					params.add(supId);
				} else {
					params.add("");
				}

				/* ���봴����ID */
				UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
				params.add(user.getId().toString());

				/* ���봴��ʱ�� */
				Date createTime = null;
				try {
					createTime = FDCDateHelper.getServerTimeStamp();
				} catch (BOSException e1) {
					createTime = new Date();
				}
				params.add(new SimpleDateFormat("yyyy-MM-dd").format(createTime));

				/* ������Ч�� */
				String validate = String
						.valueOf((rowMap.get(material_Validate) == null || "".equals(rowMap.get(material_Validate).toString().trim())) ? "2050-11-11"
								: rowMap.get(material_Validate).toString().trim());
				params.add(validate);

				/* �����Ƿ������� 1���� 0�������� */
				int isLastestQutote = 1;
				for (int k = 0; k < getLastestQuoteTime.size(); k++) {
					Map map = (Map) getLastestQuoteTime.get(k);
					String fMaxDate = String.valueOf(map.get("FMaxDate") == null ? "1990-1-1" : map.get("FMaxDate").toString());
					String fSupplierID = String.valueOf(map.get("FSupplierID") == null ? "" : map.get("FSupplierID").toString());
					if (supId.equals(fSupplierID)) {
						if (fMaxDate.indexOf(" ") != -1) {
							fMaxDate = fMaxDate.substring(0, fMaxDate.indexOf(" "));
						}
						if (quoteTime.indexOf(" ") != -1) {
							quoteTime = quoteTime.substring(0, quoteTime.indexOf(" "));
						}
						if (Timestamp.valueOf(fMaxDate + " 00:00:00").getTime() > Timestamp.valueOf(quoteTime + " 00:00:00").getTime()) {
							isLastestQutote = 0;
						}
					}
				}
				params.add(new Integer(isLastestQutote));

				/* �����Ƿ����� 1���� 0���� */
				params.add(new Integer(1));
				String fName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
				params.add(fName);
				/* ����״̬ */
				params.add("1SAVED");
				/* ������Ч��Ч */
				params.add(String.valueOf(isValidDate == 1 ? "VALID" : "INVALID"));

				/* ������Ƶ�Ԫ */
				String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().trim();
				params.add(cuId);

				/* ������֯��Ԫ */
				String orgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString().trim();
				params.add(orgUnitId);

				/* ���� (��Excelȡ����)����ָ���� */
				List fNamesExcel = new ArrayList();

				/* �ж��ǲ��ǻ����У�������ǻ����У��Ǿ�������ָ���� */
				Set set = rowMap.keySet();
				Iterator iColumn = set.iterator();
				while (iColumn.hasNext()) {
					int k = -1;
					String columnName = iColumn.next().toString().trim();
					for (int m = 0; m < baseColumnName.length; m++) {
						if (columnName.equals(baseColumnName[m])) {
							k = 1;
						}
					}
					if (k == -1) {
						fNamesExcel.add(columnName);
					}
				}

				try {
					/* ���÷�������Daoִ��SQL */
					MaterialInfoFactory.getRemoteInstance().addMaterialData(params, "importMaterialInfoSql");

					/* ������������ѯ�������������ĸ����� */
					String excelMaterialType = String.valueOf(rowMap.get(material_Type) == null ? "" : rowMap.get(material_Type).toString());
					Map materialGroupMap = new HashMap();
					materialGroupMap.put("FName_l2", excelMaterialType);
					String materialGroupId = getIdByName("T_BD_MaterialGroup", "FID", materialGroupMap);

					List materialIndexs = this.getMaterialIndexIdByMaterialId(fMaterialID);

					/* ѭ������ָ��ֵ */
					for (int k = 0; k < materialIndexs.size(); k++) {
						Map allMaterialIndexMap = (Map) materialIndexs.get(k);
						String allMaterialIndex = allMaterialIndexMap.get("FID").toString();
						List newParams = new ArrayList();
						newParams.add(getBOSID("6E5BD60C"));
						newParams.add(bosId);

						/* materialIndexId */
						String materialIndexId = "";

						/* ָ��ֵ */
						String indexValue = "";

						for (int m = 0; m < fNamesExcel.size(); m++) {
							String fNameExcel = fNamesExcel.get(m).toString().trim();
							Map filterMap = new HashMap();
							filterMap.put("FName_l2", fNameExcel);
							filterMap.put("FMaterialGroupID", materialGroupId);

							/* ����materialGroupId��fName���ƣ���ѯmaterialIndexId */
							Map materialIndexMap = new HashMap();
							materialIndexMap.put("FMaterialGroupID", materialGroupId);
							materialIndexMap.put("FName_l2", fNameExcel);

							materialIndexId = getMaterialIndexIdByMaterialGroupIdAndIndexName("T_MTR_MaterialIndex", "FID", materialIndexMap);

							if (materialGroupId == null) {
								MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "IndexName") + fNameExcel
										+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "NotExistInExcel") + (j + 1)
										+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Row"));
								return -1;
							}

							if (materialIndexId.equals(allMaterialIndex)) {
								indexValue = String.valueOf(rowMap.get(fNamesExcel.get(m).toString().trim()) == null ? "" : rowMap.get(
										fNamesExcel.get(m).toString().trim()).toString());
								break;
							}
						}

						/* materialIndexId */
						newParams.add(allMaterialIndex);

						/* ����ָ��ֵ */
						newParams.add(indexValue);

						// /*ѭ��ɾ��ָ��*/
						// int n = MaterialInfoFactory.getRemoteInstance().
						// deleteMaterialIndexValue(fMaterialID,
						// materialIndexId);

						/* ѭ������ָ�� */
						MaterialInfoFactory.getRemoteInstance().addMaterialData(newParams, "importMaterialIndexInfoSql");
					}

					++result;
				} catch (Exception e) {
					handUIException(e);
					logger.error(e.getMessage());
					/* ��ʱ����ʧ��,������handUIException(e)�����쳣,�ں��������ʾ������ʧ�ܡ� */
					--result;
				}
			}
		}
		return result;
    }

	/**
	 * ������<��������id���������ָ��id>
	 * 
	 * @author <luoxiaolong>
	 * @param <materialGruopId>
	 * @return <null> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	private List getMaterialIndexIdByMaterialId(String materialId) {
		Map filterMap = new HashMap();
		filterMap.put("FID", materialId);
		String materialGroupId = getMaterialGroupIdByMaterialId(filterMap, "T_BD_Material", "FMaterialGroupID");
		return getMaterialIndexId(materialGroupId);
	}

	/**
	 * ������<�������ָ������>
	 * 
	 * @author <luoxiaolong>
	 * @param <materialGruopId>
	 * @return <null> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	private List getMaterialIndexId(String materialGruopId) {

		/** ����һ������ֵ���� */
		List fNames = new ArrayList();
		Map filterMap = new HashMap();
		filterMap.put("FMaterialGroupID", materialGruopId);
		List list = new ArrayList();
		list.add("FID");
		try {
			fNames = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, "T_MTR_MaterialIndex", list);
		} catch (Exception e) {
			handUIException(e);
		}
		return fNames;
	}

	/**
	 * ������<��������id�������������>
	 * 
	 * @param <materialId>
	 * @return <String>
	 * @throws <>
	 */
	public String getMaterialGroupIdByMaterialId(Map filterMap, String tableName, String fieldName) {
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		String materialGroupId = null;
		try {
			List materialGroupList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
			if (null != materialGroupList && materialGroupList.size() > 0) {
				Map materialGroupMap = (Map) materialGroupList.get(0);
				materialGroupId = materialGroupMap.get(fieldName).toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
		return materialGroupId;
	}

	/**
	 * ������</*����materialGroupId��fName���ƣ���ѯmaterialIndexId*>
	 * 
	 * @author <luoxiaolong>
	 * @param <tableName,fieldName,filters>
	 * @return <String> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
    public String getMaterialIndexIdByMaterialGroupIdAndIndexName(String tableName, String fieldName, Map filters) {
		String result = null;
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		try {
			List list = MaterialInfoFactory.getRemoteInstance().getMaterialData(filters, tableName, fieldList);
			if (null != list && list.size() > 0) {
				Map map = (Map) list.get(0);
				if (null != map.get(fieldName)) {
					result = map.get(fieldName).toString();
				}
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return result;
	}

	/**
	 * ������<��ѯ���±���ʱ��>
	 * 
	 * @author <luoxiaolong>
	 * @param <tableName,fieldName,filters>
	 * @return <String> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	private List getLastestQuoteTime() {
		List lastestQuoteDate = new ArrayList();
		try {
			lastestQuoteDate = MaterialInfoFactory.getRemoteInstance().getLastestQuoteTime();
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
		return lastestQuoteDate;
	}

	/**
	 * ������<�������ƣ����id>
	 * 
	 * @author <luoxiaolong>
	 * @param <tableName,fieldName,filters>
	 * @return <String> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	public String getIdByName(String tableName, String fieldName, Map filters) {
		String result = null;
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		try {
			List list = MaterialInfoFactory.getRemoteInstance().getMaterialData(filters, tableName, fieldList);
			if (null != list && list.size() > 0) {
				Map map = (Map) list.get(0);
				if (null != map.get(fieldName)) {
					result = map.get(fieldName).toString();
				}
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return result;
	}

	/**
	 * ������<����ָ�����ƣ����id>
	 * 
	 * @author <luoxiaolong>
	 * @param <filters,tableName,fieldName>
	 * @return <String> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	private String getMaterialIndexId(Map filters, String tableName, String fieldName) {
		String materialIndexId = null;
		List list = new ArrayList();
		list.add(fieldName);
		List materialIndexList;
		try {
			materialIndexList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filters, tableName, list);
			if (null != materialIndexList && materialIndexList.size() > 0) {
				Map materialMap = (Map) materialIndexList.get(0);
				materialIndexId = materialMap.get(fieldName).toString();
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return materialIndexId;
	}

	/**
	 * ������<����bosType�����BOSID>
	 * 
	 * @author <luoxiaolong>
	 * @param <bosType>
	 * @return <String> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	public String getBOSID(String bosType) {

		/** ����һ������ֵ���� */
		String bosID = null;

		try {
			bosID = MaterialInfoFactory.getRemoteInstance().getBOSID(bosType);
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}

		return bosID;

	}

	/**
	 * ������<��֤�����Ƿ��ظ�>
	 * 
	 * @author <luoxiaolong>
	 * @param <oneRowDataList ��Excel����������һ�����ݣ�rowNum ����Excel�������ˣ�baseColumnName
	 *        ������е�����>
	 * @return <true ��֤ͨ����false ��֤ʧ��> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
	private boolean validateDataIsRepeat(List oneRowDataList, int rowNum, String[] baseColumnName) {

		Map rowMap = (Map) oneRowDataList.get(0);

		/** ����һ�����飬�����¼�е����� */
		String[] notNullColumnNames = { material_Type, material_Number, material_Name, material_Model, material_Unit, material_Price,
				material_QuoteTime, material_Supplier };

		/* �ж��Ǳ�¼���Ƿ��Ѿ�¼�룬 */
		for (int i = 0; i < notNullColumnNames.length; i++) {
			if (null == rowMap.get(notNullColumnNames[i]) || "".equals(rowMap.get(notNullColumnNames[i]).toString())) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Row2") + notNullColumnNames[i]
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ColumnIsNotNull"));
				return false;
			}
		}

		/** ����һ�����飬�����ַ���������Ҫ���Ƶ��� */
		String[] limitCharacterLengths = { material_Number, material_Name, material_Model, material_Unit };
		for (int i = 0; i < limitCharacterLengths.length; i++) {
			if (null == rowMap.get(limitCharacterLengths[i]) && rowMap.get(limitCharacterLengths[i]).toString().trim().length() > 80) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + limitCharacterLengths[i]
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "TooLength"));
				return false;
			}
		}

		/**
		 * ������Ҫ��������֤�� ��һ���ж������������Ƿ���ͬ�����ơ����������Ƿ������ݿ��д��� �ڶ��������ۡ�����ʱ�䡢��Ӧ�̣����ֶ�ֵ�Ƿ��ظ�
		 * ������������������ж��Ƿ���ڸ�ָ��
		 */
		Pattern pDate = Pattern.compile("[1-9][0-9]{3}\\-((0[0-9]|1[0-2])|[1-9])\\-((0[0-9]|1[0-9]|2[0-9]|3[0-1])|[1-9])");
		Pattern pDouble = Pattern.compile("([0-9]{1,21})|([0-9]{1,21}\\.[0-9]{0,100})");
		Pattern pIsNumber = Pattern.compile("([0-9]{1,1000})|([0-9]{1,1000}\\.[0-9]{0,1000})");

		double excelPrice = Double.parseDouble(String.valueOf(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString()));
		String excelPriceString = String.valueOf(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString());

		if (!(pIsNumber.matcher(excelPriceString).matches())) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaceInputNumberType"));
			return false;
		}// else{
		// /*�������ϣ���õ��ۡ�����ʱ�䡢��Ӧ�̡����Ϸ��ࡢ�Լ�����ָ���ֵ������֤��ʽ*/
		// if(rowMap.get(material_Price).toString().length() > 13){
		// MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH,
		// "ImportDataFail") +
		// EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") +
		// (rowNum + 1) + "�У��������ֵ����");
		// return false;
		// }
		// }

		/* �������ϣ���õ��ۡ�����ʱ�䡢��Ӧ�̡����Ϸ��ࡢ�Լ�����ָ���ֵ������֤��ʽ */
		if (excelPriceString.indexOf(".") != -1) {
			if (excelPriceString.substring(0, excelPriceString.indexOf(".")).toString().length() > 12) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PriceTooLength"));
				return false;
			}
		} else {
			if (excelPriceString.length() > 12) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PriceTooLength"));
				return false;
			}
		}

		if (!(pDouble.matcher(excelPriceString).matches())) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaceInputNumberType"));
			return false;
		}

		String excelQuoteTime = String.valueOf(rowMap.get(material_QuoteTime) == null ? (new java.util.Date()).toLocaleString() : rowMap.get(
				material_QuoteTime).toString());

		/* �ж������Ƿ�����ϸ�ʽ��׼ */
		if (!pDate.matcher(excelQuoteTime).matches()) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseInputDateType"));
			return false;
		}

		/* �жϱ��������Ƿ�Ϸ� */
		if (!validate(excelQuoteTime)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "QuoteTimeError"));
			return false;
		}

		if (excelQuoteTime.indexOf(" ") != -1) {
			excelQuoteTime = excelQuoteTime.substring(0, excelQuoteTime.indexOf(" "));
		}

		String excelSupplier = String.valueOf(rowMap.get(material_Supplier) == null ? "" : rowMap.get(material_Supplier).toString());
		Map supMap = new HashMap();
		supMap.put("FName_l2", excelSupplier);
		/* ���ݹ�Ӧ���Ʋ�ѯ��Ŀid */
		String supId = this.getIdByName("T_BD_Supplier", "FID", supMap);
		supId = String.valueOf(supId == null ? "" : supId.toString().trim());

		String validate = String.valueOf((rowMap.get(material_Validate) == null || "2050-10-1"
				.equals(rowMap.get(material_Validate).toString().trim())) ? "2050-11-11" : rowMap.get(material_Validate).toString().trim());

		validate = String.valueOf("".equals(validate) ? "2050-10-1" : validate);

		/* �ж������Ƿ�����ϸ�ʽ��׼ */
		if (!pDate.matcher(validate).matches()) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseInputDateType2"));
			return false;
		}

		/* �ж���Ч�����Ƿ�Ϸ� */
		if (null != validate && !"".equals(validate) && !validate(validate)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ValidateError"));
			return false;
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long excelTimeL = format.parse(excelQuoteTime).getTime();
			long validateL = format.parse(validate).getTime();

			/* ��Ч���ڲ���С�ڱ���ʱ�� */
			if (excelTimeL > validateL) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "TimeError"));
				return false;
			}
		} catch (Exception ex) {
			handUIException(ex);
		}

		/* ������������ѯ�������������ĸ����� */
		String excelMaterialType = String.valueOf(rowMap.get(material_Type) == null ? "" : rowMap.get(material_Type).toString());
		Map materialGroupMap = new HashMap();
		materialGroupMap.put("FName_l2", excelMaterialType);
		String excelMaterialGroupId = getIdByName("T_BD_MaterialGroup", "FID", materialGroupMap);
		excelMaterialGroupId = String.valueOf(excelMaterialGroupId == null ? "" : excelMaterialGroupId.trim());

		Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
		/* �����������ƺͱ���ȷ��materialId */
		String materialName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
		String materialNumber = String.valueOf(rowMap.get(material_Number) == null ? "" : rowMap.get(material_Number).toString());
		if (tDouble.matcher(rowMap.get(material_Number).toString().trim()).matches()) {
			materialNumber = materialNumber.substring(0, materialNumber.indexOf("."));
		}
		if (tDouble.matcher(rowMap.get(material_Name).toString().trim()).matches()) {
			materialName = materialName.substring(0, materialName.indexOf("."));
		}
		
		Map materialMap = new HashMap();
		materialMap.put("FName_l2", materialName.trim());
		materialMap.put("FNumber", materialNumber.trim());
		materialMap.put("FMaterialGroupID", excelMaterialGroupId);
		String materialId = getMaterialIDByFNameAndFNumber("T_BD_Material", "FID", materialMap);

		/* ���� (��Excelȡ����)����ָ���� */
		List fNamesExcel = new ArrayList();

		/* �ж��ǲ��ǻ����У�������ǻ����У��Ǿ�������ָ���� */
		Set set = rowMap.keySet();
		Iterator iColumn = set.iterator();
		while (iColumn.hasNext()) {
			String columnName = iColumn.next().toString().trim();
			int m = -1;
			for (int i = 0; i < baseColumnName.length; i++) {
				if (columnName.equals(baseColumnName[i])) {
					m = 1;
					break;
				}
			}
			if (m == -1) {
				fNamesExcel.add(columnName);
			}
		}

		/* ��һ�����ж������������Ƿ���ͬ */
		if (null == excelMaterialGroupId || "".equals(excelMaterialGroupId)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum - 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialTypeNotExist"));
			return false;
		}
		if (null == materialId) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "NameAndNumberIsNotExist"));
			return false;
		}
    	
    	String parentMaterialID = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();
		Map mapMaterial = new HashMap();
		mapMaterial.put("FID", parentMaterialID);
		String parentMaterialGroupID = this.getIdByName("T_BD_Material", "FMaterialGroupID", mapMaterial);
		if (!excelMaterialGroupId.equals(parentMaterialGroupID)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum - 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialTypeDiferent"));
			return false;
		}

		/* ��֤�����ظ���һ��������½Ǳ��е����� */
		for (int i = 0; i < this.MaterialInfo.getRowCount(); i++) {
			IRow row = this.MaterialInfo.getRow(i);
			double parentPrice = Double.parseDouble(row.getCell("price").getValue().toString());
			String parentQuoteTime = row.getCell("quoteTime").getValue().toString();
			if (parentQuoteTime.indexOf(" ") != -1) {
				parentQuoteTime = parentQuoteTime.substring(0, parentQuoteTime.indexOf(" "));
			}
			String parentSupplier = String.valueOf(row.getCell("supplier").getValue() == null ? "" : row.getCell("supplier").getValue().toString());

			/* �ڶ�������֤�����Ƿ��ظ� */
			if (excelPrice == parentPrice && excelQuoteTime.equals(parentQuoteTime) && excelSupplier.equals(parentSupplier)) {
				int a = -1;
				for (int k = 0; k < material_dataRepeat.size(); k++) {
					if (null == material_dataRepeat.get(k))
						continue;
					int sub = Integer.parseInt(material_dataRepeat.get(k).toString());
					if (rowNum + 1 == sub) {
						a = 1;
					}
				}
				if (a == -1) {
					material_dataRepeat.add(new Integer(rowNum + 1));
				}
			}
		}

		/* ��֤�����ظ������� */
		/* ѭ���ж�(��) */
		for (int j = 0; j < getMaterialInfos.size(); j++) {
			Map materialInfo = (Map) getMaterialInfos.get(j);
			double pPrice = Double.parseDouble(String
					.valueOf(materialInfo.get("FPrice") == null ? "0" : materialInfo.get("FPrice").toString().trim()));
			String pQuoteTime = String.valueOf(materialInfo.get("FQuoteTime") == null ? new java.util.Date().toLocaleString() : materialInfo.get(
					"FQuoteTime").toString().trim());
			if (pQuoteTime.indexOf(" ") != -1) {
				pQuoteTime = pQuoteTime.substring(0, pQuoteTime.indexOf(" "));
			}
			String fMaterialID = String.valueOf(materialInfo.get("FMaterialID") == null ? "" : materialInfo.get("FMaterialID").toString().trim());
			String fName = String.valueOf(materialInfo.get("FSupplierID") == null ? "" : materialInfo.get("FSupplierID").toString().trim());

			/* �жϵ��ۡ�����ʱ�䡢��Ӧ���Ƿ���ͬ */
			if (materialId.equals(fMaterialID) && pPrice == excelPrice && pQuoteTime.equals(excelQuoteTime) && fName.equals(supId)) {
				if ("".equals(excelSupplier) || "".equals(supId)) {
					int a = -1;
					for (int k = 0; k < supplierIsNullList.size(); k++) {
						if (null == supplierIsNullList.get(k))
							continue;
						int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
						if (rowNum + 1 == sub) {
							a = 1;
						}
					}
					if (a == -1) {
						supplierIsNullList.add(new Integer(rowNum + 1));
					}
				} else {
					int a = -1;
					for (int k = 0; k < material_dataRepeat.size(); k++) {
						if (null == material_dataRepeat.get(k))
							continue;
						int sub = Integer.parseInt(material_dataRepeat.get(k).toString());
						if (rowNum + 1 == sub) {
							a = 1;
						}
					}
					if (a == -1) {
						material_dataRepeat.add(new Integer(rowNum + 1));
					}
				}
			} else if ("".equals(excelSupplier) || "".equals(supId)) {
				int a = -1;
				for (int k = 0; k < supplierIsNullList.size(); k++) {
					if (null == supplierIsNullList.get(k))
						continue;
					int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
					if (rowNum + 1 == sub) {
						a = 1;
					}
				}
				if (a == -1) {
					supplierIsNullList.add(new Integer(rowNum + 1));
				}
			}
		}
		
		/* ��֤�����ظ������� */
		if (this.MaterialInfo.getRowCount() == 0) {
			/* ��Ӧ��Ϊ�� */
			if (null != supId && "".equals(supId)) {
				int a = -1;
				for (int k = 0; k < supplierIsNullList.size(); k++) {
					if (null == supplierIsNullList.get(k))
						continue;
					int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
					if (rowNum + 1 == sub) {
						a = 1;
					}
				}
				if (a == -1) {
					supplierIsNullList.add(new Integer(rowNum + 1));
				}
			} else {// �����ظ�

			}
		}
		
		Map newMaterialInfoMap = new HashMap();
		newMaterialInfoMap.put("FPrice", String.valueOf(excelPrice));
		newMaterialInfoMap.put("FQuoteTime", excelQuoteTime);
		newMaterialInfoMap.put("FMaterialID", materialId);
		newMaterialInfoMap.put("FSupplierID", supId);
		getMaterialInfos.add(newMaterialInfoMap);

		/* ����������֤Excel���ݱ��е�����ָ���Ƿ���� */

		/* ��������Ϸ���������Щָ�� */
		Map map = new HashMap();
		map.put("FMaterialGroupID", excelMaterialGroupId);
		List fParentNames = getMaterialIndexFNameByMaterialGroup("T_MTR_MaterialIndex", "FName_l2", map);
		for (int i = 0; i < fNamesExcel.size(); i++) {
			String fName = fNamesExcel.get(0).toString();
			int m = -1;
			for (int j = 0; j < fParentNames.size(); j++) {
				Map fNameMap = (Map) fParentNames.get(j);
				if (fName.equals(fNameMap.get("FName_l2").toString()))
					m = 1;
			}
			if (m == -1) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialIndexNotExist"));
				return false;
			}
		}
		return true;
    }

	/**
	 * ������<�ж�����>
	 * 
	 * @param <>
	 * @return <String>
	 * @throws <>
	 */
	public static boolean validate(String dateString) {

		Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
		Matcher m = p.matcher(dateString);
		if (!m.matches()) {
			return false;
		} // �õ�������
		String[] array = dateString.split("-");
		int year = Integer.parseInt(array[0]);
		int month = Integer.parseInt(array[1]);
		int day = Integer.parseInt(array[2]);
		if (month < 1 || month > 12) {
			return false;
		}
		int[] monthLengths = new int[] { 0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (isLeapYear(year)) {
			monthLengths[2] = 29;
		} else {
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if (day < 1 || day > monthLength) {
			return false;
		}
		return true;
	}

	/**
	 * �Ƿ�������
	 */
	private static boolean isLeapYear(int year) {
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
	}

	/**
	 * ������<��������ID��ѯ���ۡ�����ʱ�䡢��Ӧ��>
	 * 
	 * @param <>
	 * @return <String>
	 * @throws <>
	 */
	public List getMaterialAllInfos() {
		List getMaterialInfos = new ArrayList();
		try {
			List list = new ArrayList();
			list.add("FPrice");
			list.add("FQuoteTime");
			list.add("FSupplierID");
			list.add("FMaterialID");
			getMaterialInfos = MaterialInfoFactory.getRemoteInstance().getMaterialData(new HashMap(), "T_MTR_MaterialInfo", list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
		return getMaterialInfos;
	}

	/**
	 * ������<�����������ƺͱ����ѯmaterialId>
	 * 
	 * @author <luoxiaolong>
	 * @param <tableName,fieldName,filters>
	 * @return <int> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
    public String getMaterialIDByFNameAndFNumber(String tableName, String fieldName, Map filterMap) {
		List fieldList = new ArrayList();
		fieldList.add(fieldName);

		/** ����materialId */
		String materialId = null;

		try {
			List materialList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
			if (null != materialList && materialList.size() > 0) {
				Map materialMap = (Map) materialList.get(0);
				materialId = materialMap.get(fieldName).toString();
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return materialId;
	}

	/**
	 * ������<����������𣬲�ѯ�����������Щָ��>
	 * 
	 * @author <luoxiaolong>
	 * @param <tableName,fieldName,filters>
	 * @return <int> ����ʱ�� <2010/11/16>
	 *         <p>
	 * 
	 *         �޸��ˣ�<�޸���>
	 *         <p>
	 *         �޸�ʱ�䣺<yyyy/mm/dd>
	 *         <p>
	 *         �޸�������<�޸�����>
	 *         <p>
	 * 
	 * @see <��ص���>
	 */
    public List getMaterialIndexFNameByMaterialGroup(String tableName, String fieldName, Map filters) {
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
    	
    	/** ����ָ�� */
		List indexName = new ArrayList();
    	
    	try {
			indexName = MaterialInfoFactory.getRemoteInstance().getMaterialData(filters, tableName, fieldList);
		} catch (Exception e) {
			handUIException(e);
		}
		return indexName;

	}

	/**
	 * ��������ò�����Ϣ���ID ����ʱ�䣺2010-11-22
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */

	private String getMaterialInfoId() {
		IRow row = null;
		String materialInfoId = "";
		if (MaterialInfo.getRowCount() != 0 && MaterialInfo.getSelectManager().size() != 0) {
			row = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex());
			if (row.getCell("id") != null) {
				materialInfoId = row.getCell("id").getValue().toString();
			}
		}
    	
    	return materialInfoId;
	}

	/**
	 * ��������ò���ID ����ʱ�䣺2010-11-22
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	String materialId = null;

	private String getMaterialId() {
    	
    	IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row != null) {
			materialId = row.getCell("id").getValue().toString();
		}
		return materialId;
	}

	/**
	 * �����������¼� ����ʱ�䣺2010-11-22
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	
    	materialInfoCheckSelected();
    	
    	/* �ж��Ƿ���״̬ */
		if (null == this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue()
				|| "".equals(this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue()
						.toString().trim())) {
			MsgBox.showInfo("ѡ����û��״̬��");
			return;
    	}
    	
    	
    	/* �ж�ѡ���������ǵ��ж��Ƿ�Ϊ��Ч״̬ */
		String state = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue().toString();
		if (EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Nullity").equals(state)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Auditing"));
			return;
    	}
    	
    	String id = this.getMaterialInfoId();
		MaterialInfoInfo info = null;
		if (id != null && !id.equals("")) {
			info = (MaterialInfoInfo) MaterialInfoFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(id)));
			if (null == info.getState() || "".equals(info.getState().getValue())) {
				abort();
			}
		}
		if (id == null) {
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "importForExcel"));
		}
		if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showWarning(this, EASResource.getString(MATERIALINFO_RESOURCEPATH, "auditState"));
		} else {
			MaterialInfoFactory.getRemoteInstance().audit(BOSUuid.read(id));
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "auditSuccessfully"));
			this.refreahAfterAuditAndUnAudit(e);
		}
	}

	/**
	 * �������������¼� ����ʱ�䣺2010-11-22
	 * <p>
	 * 
	 * �޸��ˣ�
	 * <p>
	 * �޸�ʱ�䣺
	 * <p>
	 * �޸�������
	 * <p>
	 * 
	 * @see
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	materialInfoCheckSelected();

		/* �ж�ѡ���������ǵ��ж��Ƿ�Ϊ��Ч״̬ */
		/* �ж��Ƿ���״̬ */
		if (null == this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue()
				|| "".equals(this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue()
						.toString().trim())) {
			MsgBox.showInfo("ѡ����û��״̬��");
			return;
    	}
    	
    	String state = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue().toString();
		if (EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Nullity").equals(state)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "UnAuditing"));
			return;
    	}
    	
    	String id = this.getMaterialInfoId();
		MaterialInfoInfo info = null;
		if (id != null && !id.equals("")) {
			info = (MaterialInfoInfo) MaterialInfoFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(id)));
			if (null == info.getState() || "".equals(info.getState().getValue())) {
				abort();
			}
		}
		if (id == null) {
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "failedGetData"));
		}
		if (!info.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showWarning(this, EASResource.getString(MATERIALINFO_RESOURCEPATH, "unAudit"));
		} else {
			MaterialInfoFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "unAuditSuc"));
			this.refreahAfterAuditAndUnAudit(e);
			
		}
    }   
    
	/**
	 * 
	 * @description �ֲ�ͼ
	 * @author ��һ��
	 * @createDate 2010-11-5
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */

	public void actionShowDotsMap_actionPerformed(ActionEvent e)
	throws Exception {
		this.checkSelected();
		//�õ�ѡ���е��к�
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		//�õ�ѡ����name�����ƣ��е�ֵ
		String materialName = (String)this.tblMain.getRow(rowIndex).getCell("name").getValue();
		
		XYDataset dataSet = MaterialInfoFactory.getRemoteInstance().getDotDataSet(materialName, materialInfoId);
		
		//�õ�ѡ����model������ͺţ��е�ֵ
		String materialModel = "���ۣ���Ԫ/" + this.tblMain.getRow(rowIndex).getCell("baseUnit.name").getValue().toString() + "��";
		
		try {
			MaterialScatterChartUI.showChart(this, dataSet,materialModel);
		} catch (UIException ue) {
			logger.error(ue.getMessage());
			handUIException(ue);
		}


	}

	/**
	 * ����ͼ
	 * 
	 * @description
	 * @author ��һ��
	 * @createDate 2010-11-5
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	public void actionShowLineMap_actionPerformed(ActionEvent e)
	throws Exception {
		this.checkSelected();
		
		//�õ�ѡ���е��к�
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		//�õ�ѡ����name�����ƣ��е�ֵ
		String materialName = (String)this.tblMain.getRow(rowIndex).getCell("name").getValue();
		//�õ�ѡ����model������ͺţ��е�ֵ
		String materialModel = "���ۣ���Ԫ/" + this.tblMain.getRow(rowIndex).getCell("baseUnit.name").getValue().toString() + "��";
		XYDataset dataSet = MaterialInfoFactory.getRemoteInstance().getLineDataSet(materialName, materialInfoId);
		try {
			MaterialLineChartUI.showChart(this, dataSet,materialModel);
		} catch (UIException ue) {
			logger.error(ue.getMessage());
			handUIException(ue);
		}
	}
	/**
	 * 
	 * @description		��ʾ���а�ť
	 * @author			���˳�
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0	
	 * @see
	 */
	private void initEnabledTrue() {
		this.actionAddNew.setEnabled(true);
		this.actionView.setEnabled(true);
		this.actionEdit.setEnabled(true);
		this.actionRemove.setEnabled(true);
		this.actionRefresh.setEnabled(true);
		this.actionQuery.setEnabled(true);
		/*ͼ��ͼ��*/
		this.actionShowDotsMap.setEnabled(true);
		this.actionShowLineMap.setEnabled(true);
		/*���뵼��ͼ��     ������*/
		this.KDBtnExcelImport.setEnabled(true);
		this.KDExportExcel.setEnabled(true);
		this.KDBtnMaterialImport.setEnabled(true);
		/*���뵼��ͼ��     �˵���*/
		this.MaterialImportItem.setEnabled(true);
		this.ExcelImportItem.setEnabled(true);
		this.ExportExcelItem.setEnabled(true);
		/*��׼*/
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
	}
	/**
	 * 
	 * @description		�ҵ����а�ť
	 * @author			���˳�
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0	
	 * @see
	 */
	private void initEnabledFalse() {
		  
		/*�ر�Ұ�ť*/
		this.btnView.setEnabled(false);
		this.menuItemView.setEnabled(false);
		this.showLineMap.setEnabled(false);		
		this.showDotsMap.setEnabled(false);
		this.showDotsMapItem.setEnabled(false);
		this.showLineMapItem.setEnabled(false);
		/*�ҵ�ԭ�еİ�ť*/
		this.actionAddNew.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionRefresh.setEnabled(false);
		this.actionQuery.setEnabled(false);
		/*ͼ��ͼ��*/
		this.actionShowDotsMap.setEnabled(false);
		this.actionShowLineMap.setEnabled(false);
		/*���뵼��ͼ��     ������*/
		this.KDBtnExcelImport.setEnabled(false);
		this.KDExportExcel.setEnabled(false);
		this.KDBtnMaterialImport.setEnabled(false);
		/*���뵼��ͼ��     �˵���*/
		this.MaterialImportItem.setEnabled(false);
		this.ExcelImportItem.setEnabled(false);
		this.ExportExcelItem.setEnabled(false); 
		/*��׼*/
		this.actionAudit.setEnabled(false);
		this.actionUnAudit.setEnabled(false);

	}

	/**
	 * 
	 * @description		�ж��Ƿ��Ǽ��� CU
	 * @author			���˳�
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0	
	 * @see
	 */
	private boolean isIsCU() {
		
		if(SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
			this.initEnabledTrue();
			return true;
		}else{
			this.initEnabledFalse();
			return false;
		}
			
	 
	}
	/**
	 * 
	 * @description		�ж��Ƿ�Ϊ�ɹ���֯  �� �Ƿ�Ϊʵ��/����
	 * @author			���˳�
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0	
	 * @see
	 */
	private boolean isIsPurchaseOrgUnit() {
		 /**�ж��Ƿ�Ϊ�ɹ���֯*/
		if(SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			/**�Ƿ�Ϊʵ��/����*/
			if(SysContext.getSysContext().getCurrentPurchaseUnit().isIsBizUnit()){
				this.initEnabledTrue();
				return true;
			}else{
				this.initEnabledFalse();
				return false;
			}
		}
		this.initEnabledFalse();
		return false;
	}
	public void onShow() {

		 
		this.showLineMap.setEnabled(true);
		this.showDotsMap.setEnabled(true);

		/* ���ô�Excel���밴Ť���Ӳ�����ϸ���밴Ť������ */
		this.KDBtnExcelImport.setVisible(true);
		this.KDBtnMaterialImport.setVisible(true);
		this.KDExportExcel.setVisible(true);
		this.KDBtnExcelImport.setToolTipText("��Excel����");
		
		/*��������ѯ��ť*/  
		this.actionQuery.setVisible(true);
		this.actionQuery.setEnabled(true);
		bl = true;
		
		this.btnAttachment.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.btnAuditResult.setVisible(false);
		
		this.tblMain.getColumn("quoteTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd"); 
		
		/**�ж�CU����*/ 
		if(this.isIsCU()){
			this.initEnabledTrue();
		}else{
		/**�ж� �ɹ���֯ ʵ��  ��  ����*/
			this.isIsPurchaseOrgUnit();
			
			if(this.isIsPurchaseOrgUnit()){
				this.getUIContext().put("isViewEnbled", Boolean.TRUE);
			}else{
				this.getUIContext().put("isViewEnbled", Boolean.FALSE);
			}
		}
		
	}


	/**
	 * ������Ϊ�´�ҳ�洫ֵ
	 * ����ʱ�䣺2010-12-2<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 *
	 * @see  
	*/
	private void sendValue() {
		
//		if(this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"number").getValue()!=null && !this.tblMain.getCell(
//						this.tblMain.getSelectManager().getActiveRowIndex(),
//						"number").getValue().equals("")){
//			this.getUIContext().put(
//					"tblNumberValue",
//					this.tblMain.getCell(
//							this.tblMain.getSelectManager().getActiveRowIndex(),
//					"number").getValue().toString().trim());
//			
//		}else{
//			this.getUIContext().put(
//					"tblNumberValue",
//					"");
//		}
//		
//		if(this.tblMain.getCell(
//				this.tblMain.getSelectManager().getActiveRowIndex(),
//				"name").getValue()!=null && !this.tblMain.getCell(
//						this.tblMain.getSelectManager().getActiveRowIndex(),
//						"name").getValue().equals("")){
//			this.getUIContext().put(
//					"tblNameValue",
//					this.tblMain.getCell(
//							this.tblMain.getSelectManager().getActiveRowIndex(),
//					"name").getValue().toString().trim());
//			
//		}else{
//			this.getUIContext().put(
//					"tblNameValue",
//					"");
//		}
//		
//		if(this.tblMain.getCell(
//				this.tblMain.getSelectManager().getActiveRowIndex(),
//				"model").getValue()!=null && !this.tblMain.getCell(
//						this.tblMain.getSelectManager().getActiveRowIndex(),
//						"model").getValue().equals("")){
//			this.getUIContext().put(
//					"tblModelValue",
//					this.tblMain.getCell(
//							this.tblMain.getSelectManager().getActiveRowIndex(),
//					"model").getValue().toString().trim());
//			
//		}else{
//			this.getUIContext().put(
//					"tblModelValue",
//					"");
//		}
//		
//		if(this.tblMain.getCell(
//				this.tblMain.getSelectManager().getActiveRowIndex(),
//				"baseUnit.name").getValue()!=null && !this.tblMain.getCell(
//						this.tblMain.getSelectManager().getActiveRowIndex(),
//						"baseUnit.name").getValue().equals("")){
//			this.getUIContext().put(
//					"tblUnitValue",
//					this.tblMain.getCell(
//							this.tblMain.getSelectManager().getActiveRowIndex(),
//					"baseUnit.name").getValue().toString().trim());
//			
//		}else{
//			this.getUIContext().put(
//					"tblUnitValue",
//					"");
//		}

	}
	

    
    /**
     * ������������ݲ����ݹ���������ֵ����table
     * ����ʱ�䣺2010-11-17<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     *
     * @param treeId ���ڵ����
     * @see  
    */
    private void insertDataToTblMain(String treeId ) throws BOSException, EASBizException{
    	/*ɾ������������*/
    	this.tblMain.removeRows();
    	IRow row = null;
    	Map map = null ;
    	
    	/*����Զ�̷�������װ�ж����MAP*/
    	Map resultMap  = MaterialInfoFactory.getRemoteInstance().getMaterialInfoMsg(new ObjectUuidPK(treeId)) ;
    	MaterialInfoInfo  pInfo = null;
    	MaterialCollection mcol = (MaterialCollection) resultMap.get("materialInfo");
    	map = (Map) resultMap.get("priceInfo");
    	for (int i = 0; i < mcol.size(); i++) {
    		MaterialInfo mInfo = mcol.get(i);
    		pInfo = (MaterialInfoInfo) map.get(mInfo.getId().toString());
    		row = this.tblMain.addRow();
			/*����   �����Ϊ����ֵ*/
			row.getCell("name").setValue(mInfo.getName());
			/*����   �����Ϊ����ֵ*/
			row.getCell("number").setValue(mInfo.getNumber());
			/*����ͺ�   �����Ϊ����ֵ*/
			row.getCell("model").setValue(mInfo.getModel());
			/*��λ   �����Ϊ����ֵ*/
			row.getCell("baseUnit.name").setValue(mInfo.getBaseUnit().getName());
			/*ID   �����Ϊ����ֵ*/
			row.getCell("id").setValue(mInfo.getId());
			/*���±���   �����Ϊ����ֵ*/
			if (pInfo == null) {
				continue;
			}
			row.getCell("winPrice").setValue(pInfo.getPrice());
			/*����ʱ��   �����Ϊ����ֵ*/
			row.getCell("quoteTime").setValue(pInfo.getQuoteTime());
			/*��Ӧ��   �����Ϊ����ֵ*/
			if (pInfo.getSupplier() != null) {
				row.getCell("supplier").setValue(pInfo.getSupplier().getName());
			}
		}
    	
    	this.setTableAutoSort();
	}

	/**
	 * ���������ñ���п��Զ�����
	 * @Author��jian_cao
	 * @CreateTime��2012-9-3
	 */
	private void setTableAutoSort() {
		// ���ñ���Զ�������
		// �����Զ�������в����Զ�����
		for (int i = 0, size = this.tblMain.getColumnCount(); i < size; i++) {
			this.tblMain.getColumn(i).setSortable(true);
		}

		new KDTSortManager(this.tblMain).setSortAuto(true);
	}
    
    /**
     * ���������������
     * ����ʱ�䣺2010-11-17<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     *
     * @see  
    */
    private MaterialGroupInfo getMaterialGroupInfo(){
    	/*�õ�ѡ�е�path*/
		TreePath path = this.kDTree1.getSelectionPath();
		if (path == null) {
			SysUtil.abort();
		}
		/* ����path�õ��ڵ�*/
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
				.getLastPathComponent();
		if(treenode != null && treenode.getUserObject() instanceof MaterialGroupInfo){
			/*�õ����Ķ���*/
			MaterialGroupInfo info = (MaterialGroupInfo) treenode.getUserObject();
			return info;
			
		}else
			return null;
    }
    
    /**
     * ������������
     * ����ʱ�䣺2010-11-17<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     *
     * @see  
    */
    private int headLength = -1;
    private void builderMaterialTable() throws BOSException, EASBizException{
    	this.setTableStyle();
    	/*���������*/
    	MaterialGroupInfo groupInfo = this.getMaterialGroupInfo();
    	String materialGroupId = null;
    	/*
    	 * ���������Ϊ�գ����ò������ݷ��������ݷ������
    	 */
    	if(groupInfo != null){
    		materialGroupId  = groupInfo.getId().toString();
    		insertDataToTblMain(materialGroupId);//
    		/*��÷��ص�ָ����󼯺�*/
			IRowSet rowSet = MaterialInfoFactory.getRemoteInstance().selectTraitAndSuperIndex(groupInfo.getLongNumber());
    		/*ָ�꼯��*/
    		List indexList = new ArrayList();
    		/*ָ��ID����*/
    		List idList= new ArrayList();
    		
    		try {
				while (rowSet.next()) {
					/* ��T_MTR_MaterialIndex�еõ�������ָ��ID */
					idList.add(rowSet.getString("FID"));
					/* �õ�����ָ������� */
					indexList.add(rowSet.getString("FName_l2"));
				}
			} catch (SQLException e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
    		
    		/*���������Ϣ���ͷС��2��������һ��*/
    		if(this.MaterialInfo.getHeadRowCount() < 2){
    			this.MaterialInfo.addHeadRow();
    		}
    		/*
    		 * 
    		 * �˴�Ϊ������,������ָ��Ϊ��ʱ,�򲻻����
    		 * ��������ָ���ͷ�д���0,���ɾ��
    		 */
    		if(headLength > 0){
    			for (int i = 0; i < headLength ; i++) {
    				this.MaterialInfo.removeColumn(0);
    			}
    		}
    		/**���������ĳ�������Ϊ����ָ�곤��*/
    		headLength = indexList.size();
    		/*
    		 * �����Ǹ��ݼ������ж��Ƿ��һ�ν���˷���,
    		 * �������̬��������ָ�����
    		 */
    		if (headLength > 0) {
    			/*ѭ����������ָ���С��������ָ���ͷ*/
				for (int i = 0; i < indexList.size(); i++) {
					IColumn col = this.MaterialInfo.addColumn(i);
					this.MaterialInfo.getHeadRow(1).getCell(i).setValue(
							indexList.get(i).toString());
					this.MaterialInfo.getColumn(i).setKey(
							idList.get(i).toString());

				}
				/*�������ָ���ͷ�ĵ�0��,��ֵ���ó�����ָ��*/
				this.MaterialInfo.getHeadRow(0).getCell(0).setValue(EASResource.getString(MATERIALINFO_RESOURCEPATH,"index"));
			}
    		/*��ñ���ںϹ�����*/
			KDTMergeManager mm = this.MaterialInfo.getHeadMergeManager();
			/*�ں�����ָ���ͷ��0��*/
			mm.mergeBlock(0, 0, 0, indexList.size() - 1);
			/*ѭ���ںϳ�ȥ��̬���ɵ�����ָ����*/
			for (int i = 0; i < 10; i++) {
				mm.mergeBlock(0, indexList.size() + i, 1, indexList.size() + i);
			}

    		
    	}else{
    		this.tblMain.removeRows(false);
    		MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH,"childNote"));
    	}
    		
    }
    /**
     * �������Ƿ�ѡ����ϸ��Ϣ��
     * ����ʱ�䣺2010-11-20<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     *
     * @see  
    */
    private void materialInfoCheckSelected(){
    	if(MaterialInfo.getRowCount() == 0 || MaterialInfo.getSelectManager().size() == 0)
        {
            MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH,"selectQuote"));
            this.abort();
        }
    }
    
    /**
     * ����������materialinfo���״̬
     * ����ʱ�䣺2010-11-18<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     *
     * @see  
    */
    private void setTableStyle(){
    	/* ���ü۸����ʾλ�� */
    	this.MaterialInfo.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("winPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		/*����ʱ��ĸ�ʽ */
		this.tblMain.getColumn("quoteTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.MaterialInfo.getColumn("quoteTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		/* ����ѡ��ʽ*/
		this.MaterialInfo.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		/*����ֻ��ѡ��һ��*/
		this.MaterialInfo.getSelectManager().setSelectMode(2);
		/*����ֻ��ѡ��һ��*/
		this.tblMain.getSelectManager().setSelectMode(2);
		/*���ñ������*/
		this.MaterialInfo.getStyleAttributes().setLocked(true);
    }
    
    
    /**
     * ����������������Ϣ��
     * ����ʱ�䣺2010-11-24<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     *
     * @see  
    */
    private void builderMaterialInfoTable() throws BOSException{
    	/*���������*/
    	MaterialGroupInfo groupInfo = this.getMaterialGroupInfo();
    	String materialGroupId = null;
    	/* ���������Ϊ�գ����ò������ݷ��������ݷ������ */
    	if(groupInfo != null){
    		materialGroupId  = groupInfo.getId().toString();
    		this.createMaterialInfoTable();
    	}else{
    		this.tblMain.removeRows(false);
    		MsgBox.showInfo("��ѡ���ӽڵ�!");
    	}
    }
    
    
    /**
     * ����������������Ϣ���������
     * ����ʱ�䣺2010-11-23<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     *
     * @see  
    */
    private void createMaterialInfoTable() throws BOSException{
    	getMaterialIndexAndValue(this.getMaterialId());
    }
 
    /**
     * �������������ָ��������ָ��ֵ
     * ����ʱ�䣺2010-11-23<p>
     * 
     * �޸��ˣ�<p>
     * �޸�ʱ�䣺 <p>
     * �޸������� <p>
     * @throws UuidException 
     * @throws BOSException 
     * @param materialId 
     * @see  
    */
    private void getMaterialIndexAndValue(String materialId) throws BOSException, UuidException{
    	/*����Ƿ�ѡ�б�����Ϣ��*/
    	this.MaterialInfo.checkParsed();
    	/*��÷��صĲ�����Ϣ���󼯺�*/
    	MaterialInfoCollection mic =  getMaterInfoCollection(materialId);
    	IRow row = null;
    	/*ɾ������������*/
    	MaterialInfo.removeRows();
    	Iterator  it = mic.iterator();
    	/*ѭ��������ò�����Ϣinfo����*/
    	while(it.hasNext()){
    		
    		MaterialInfoInfo info =(MaterialInfoInfo) it.next();
    		row = MaterialInfo.addRow();
    		/*����info�����е�ID �������ָ��ֵ*/
    		MaterialIndexValueCollection indexv =getMaterialIndexValue(info.getId().toString());
    		
    		Iterator  its = indexv.iterator();
    		/*ѭ�������������ָ��ֵ����*/
    		while(its.hasNext()){
    			MaterialIndexValueInfo iinfo =(MaterialIndexValueInfo) its.next();
    			MaterialIndexInfo mminfo = iinfo.getMaterialIndex();
    			if(mminfo != null && iinfo.getValue() != null){
    				if(row.getCell(mminfo.getId().toString()) != null){
    					row.getCell(mminfo.getId().toString()).setValue(iinfo.getValue());
    				}
    			}
    		}
    		/*���� ������ֵ*/
    		row.getCell("price").setValue(info.getPrice());
    		/*���� ���±�����ֵ */
    		row.getCell("quoteTime").setValue(info.getQuoteTime());
    		/*���� ��Ӧ����ֵ*/
    		row.getCell("supplier").setValue(info.getSupplier());
    		/*���� ������Ŀ��ֵ*/
    		row.getCell("project").setValue(info.getProject());
    		/*���� ��ͬ��ֵ*/
    		if(null != info.getContractBill()){
    			row.getCell("contractBill").setValue(info.getContractBill().getName());
    		}
    		/*���� ��ͬ״̬��ֵ*/
    		row.getCell("state").setValue(info.getMState());
    		/*���� ID��ֵ*/
    		row.getCell("id").setValue(info.getId());
    		/*���� ��Ч������ֵ*/
    		row.getCell("validDate").setValue(info.getValidDate());
    		/*���� ������ֵ*/
    		row.getCell("creator").setValue(info.getCreator().getName());
    		/*���� ���״̬��ֵ*/
    		if( info.getState() != null
    				&&  !info.getState().equals("")
    				&& FDCBillStateEnum.AUDITTED.equals(info.getState())) {
    			row.getCell("sstate").setValue("��׼");
    		}else{
    			row.getCell("sstate").setValue("δ��׼");
    		}
    	}
    }
	/**
	 * ���������ָ�귽��
	 * ����ʱ�䣺2010-11-26<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	private MaterialIndexValueCollection getMaterialIndexValue(String id) {
		
		EntityViewInfo view = new EntityViewInfo();
		/*������Ҫ��ѯ���ֶ�*/
		view.getSelector().add(new SelectorItemInfo("*"));	 
		view.getSelector().add(new SelectorItemInfo("materialIndex.*"));	 
		/*������Ҫ���˵�����*/
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("material.id", id));
	    view.setFilter(filter);
        try {
        	/*����Զ�̷�����ö��󲢽��䷵��*/
			return MaterialIndexValueFactory.getRemoteInstance().getMaterialIndexValueCollection(view);
			
		} catch (BOSException e) { 
			handUIException(e);
		}

		return null;

	}
	/**
	 * ��������ñ�����Ϣ
	 * ����ʱ�䣺2010-11-26<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	private MaterialInfoCollection getMaterInfoCollection(String id) {
		
		EntityViewInfo view = new EntityViewInfo();
		/*������Ҫ��ѯ���ֶ�*/
		view.getSelector().add(new SelectorItemInfo("*"));	 
		view.getSelector().add(new SelectorItemInfo("supplier.*"));	 
		view.getSelector().add(new SelectorItemInfo("indexValue.id"));	
		view.getSelector().add(new SelectorItemInfo("indexValue.value"));	
		view.getSelector().add(new SelectorItemInfo("indexValue.materialIndex.id"));	 
		view.getSelector().add(new SelectorItemInfo("project.name"));	 
		view.getSelector().add(new SelectorItemInfo("contractBill.name"));	 
		view.getSelector().add(new SelectorItemInfo("material.*"));	
		view.getSelector().add(new SelectorItemInfo("creator.*"));	
		/*���ù�������*/
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("material.id", id));
	    view.setFilter(filter);
	    try {
        	/*����Զ�̷�����ö��󲢽��䷵��*/
			return MaterialInfoFactory.getRemoteInstance().getMaterialInfoCollection(view);
			
		} catch (BOSException e) { 
			handUIException(e);
		}

		return null;

	}
 
	/**
	 * ���������ָ��ʵ�弯��
	 * ����ʱ�䣺2010-11-26<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	private MaterialIndexCollection getMaterialIndexCollection(String materialGroupId){
		/*������Ҫ��ѯ���ֶ�*/
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("name"));
		/*���ù�������*/
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("materialGroup", materialGroupId));
		view.setFilter(filter);
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
		try {
			view.getFilter().mergeFilter(filter2, "and");
			/*����Զ�̷�����ö��󲢽��䷵��*/
			return MaterialIndexFactory.getRemoteInstance().getMaterialIndexCollection(view);
		} catch (BOSException e) {
			handUIException(e);
		}
		return null;
	}
	
	/**
	 * �������ж��Ƿ�ѡ�л������ϱ��е�һ��
	 * ����ʱ�䣺2010-11-26<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	public void checkSelected(){
		/*
		 * �������û��ѡ���У���ʾ�û�ѡ����
		 */
        if(tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0)
        {
            MsgBox.showInfo(this, EASResource.getString(MATERIALINFO_RESOURCEPATH,"baseMaterial"));
            this.abort();
        }
    }
	
	/**
	 * �������ж��Ƿ�ѡ���������ӽڵ�
	 * ����ʱ�䣺2010-12-2<p>
	 * 
	 * �޸��ˣ�<p>
	 * �޸�ʱ�䣺 <p>
	 * �޸������� <p>
	 *
	 * @see  
	*/
	private boolean checkChildNode() {
		TreePath tpath = kDTree1.getSelectionPath();
		/*
		 * ����õ���Path���գ���ô�û���û��ѡ�������͸�����ʾ
		 */
		if(tpath == null){
			MsgBox.showInfo("��ѡ��ڵ㣡");
			return false;
		}
		DefaultKingdeeTreeNode kdtn = (DefaultKingdeeTreeNode)tpath.getLastPathComponent();
 		/*
 		 * ����ӽڵ��ֵΪ�գ���ʾδѡ���ӽڵ� 
 		 */
		if (!kdtn.isLeaf()) {
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH,"childNote"));
			abort();
			return false;
		}
		return true;
	}
	
	
}