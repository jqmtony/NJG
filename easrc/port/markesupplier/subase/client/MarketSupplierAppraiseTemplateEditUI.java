/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subase.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.port.markesupplier.subase.AppraiseTypeEnum;
import com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexTreeFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexTreeInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class MarketSupplierAppraiseTemplateEditUI extends AbstractMarketSupplierAppraiseTemplateEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierAppraiseTemplateEditUI.class);
	 /*
     * ����ά��
     */
    private final String TYPE_COL = "Accreditationwd";
    /*
     * ����ָ��
     */
    private final String NAME_COL = "IndexName";
    /*
     * Ȩ��
     */
    private final String WEIGHT_COL = "qz";
    /*
     * ���ֱ�׼
     */
    private final String DESC_COL = "threeStandard";
    /*
     * ״̬���к�
     */
    private int states = 0;
    /**
     * output class constructor
     */
    public MarketSupplierAppraiseTemplateEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        mergeThemeRow(kdtEntry, "Accreditationwd", 1);
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
    	this.prmtAccreditationType.setEnabled(false);
    	super.onLoad();
    	chkMenuItemSubmitAndAddNew.setSelected(true);
		chkMenuItemSubmitAndAddNew.setVisible(true);
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
		}
		this.kDLabelContainer4.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionFirst.setVisible(false);//��һ
		this.actionPre.setVisible(false);//ǰһ
		this.actionNext.setVisible(false);//��һ
		this.actionLast.setVisible(false);//���һ��
		
		this.kdtEntry.getColumn("seq").getStyleAttributes().setHided(true);
		this.kdtEntry.getColumn("Accreditationwd").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("threeStandard").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("IndexDesc").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("threeStandard").getStyleAttributes().setWrapText(true);
		
		this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                	kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
		kDContainer1.getContentPane().add(kdtEntry, BorderLayout.CENTER);
		kDContainer2.getContentPane().add(scrollPaneremake, BorderLayout.CENTER);
		KDWorkButton addNewLine = kdtEntry_detailPanel.getAddNewLineButton();
		addNewLine.setText("������");
		KDWorkButton insertLine = kdtEntry_detailPanel.getInsertLineButton();
		insertLine.setText("������");
		KDWorkButton removeLine = kdtEntry_detailPanel.getRemoveLinesButton();
		removeLine.setText("ɾ����");
		this.kDContainer1.addButton(addNewLine);
		this.kDContainer1.addButton(insertLine);
		this.kDContainer1.addButton(removeLine);
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
		int count = kdtEntry.getRowCount();
		boolean flse = false;
		if(count <0){
			return;
		}else{
			for(int i =0;i<count;i++){
				if(UIRuleUtil.isNull(kdtEntry.getRow(i).getCell(NAME_COL).getValue())){
					MsgBox.showWarning(this, "����ָ�겻��Ϊ�գ�");
					SysUtil.abort();
				}
				if(UIRuleUtil.isNull(kdtEntry.getRow(i).getCell("ScoreType").getValue())){
					MsgBox.showWarning(this, "���������Ϊ�գ�");
					SysUtil.abort();
				}
				if(AppraiseTypeEnum.WEIGHT.equals(kdtEntry.getRow(i).getCell("ScoreType").getValue())){
					flse = true;
				}
			}
		}
		//�ж��Ƿ���
		if(flse){
			BigDecimal big = getNumWeight();
			if(big.compareTo(new BigDecimal("100"))!=0){
				MsgBox.showWarning(this, "Ȩ��֮�ͱ������100%");
				SysUtil.abort();
			}
		}
		super.verifyInput(e);
	}
    
    /**
	 * @description �������Ȩ�صĺ�
	 * @param
	 * @return BigDecimal
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private BigDecimal getNumWeight() {
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < kdtEntry.getRowCount(); ++i) {
			IRow tmpRow = kdtEntry.getRow(i);
			if (AppraiseTypeEnum.PASS.equals(tmpRow.getCell("ScoreType")
					.getValue())) {
				continue;
			}
			if (tmpRow.getCell(WEIGHT_COL).getValue() != null) {
				BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL)
						.getValue().toString());
				sum = sum.add(tmp);
			} else {
				tmpRow.getCell(WEIGHT_COL).setValue(new BigDecimal("1.00"));
				BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL)
						.getValue().toString());
				sum = sum.add(tmp);
			}
		}
		return sum;
	}
    

	/**
	 * @description �ж�Ȩ���Ƿ�ϸ�
	 * @author �㿭
	 * @createDate 2010-11-26
	 * @param String
	 *            int
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void isWeightPoint(String colName, int rowIndex) {
		if (colName.equals(WEIGHT_COL)) {
			if (kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).getValue() != null) {
				BigDecimal tmp = new BigDecimal(kdtEntry.getRow(rowIndex).getCell(
						WEIGHT_COL).getValue().toString());
				if (tmp.compareTo(BigDecimal.ZERO) < 0) {
					MsgBox.showWarning(this, "Ȩ�ز���С��0��");
					kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue(
							new BigDecimal(1));
					abort();
				}
				if (tmp.compareTo(new BigDecimal("100")) > 0) {
					MsgBox.showWarning(this, "Ȩ�ز��ܴ���100��");
					kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue(
							new BigDecimal(1));
					abort();
				}
				int i = setFootRowSum();
				if (i == 1) {
					kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue(
							new BigDecimal(0));
					setFootRowSum();
				}
			}
		}
	}

	/**
	 * @description F7���ݸı�ʱ������ά����ͬ�ϲ���Ԫ��
	 * @author
	 * @createDate 2010-11-26
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	protected void setColumMerger() {
		kdtEntry.checkParsed();
		KDTMergeManager mm = kdtEntry.getMergeManager();
		int longth = kdtEntry.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for (int i = 0; i < longth; i++) {
			/*
			 * ȡ�����е�γ��
			 */
			row = kdtEntry.getRow(i);
			String type = (String) row.getCell(TYPE_COL).getValue();
			if (map.get(type) == null) {
				map.put(type, Boolean.TRUE);
			}
		}
		Set key = map.keySet();
		Iterator it = key.iterator();
		while (it.hasNext()) {
			String type = (String) it.next();
			int startEnd[] = getStartEnd(-1, -1, type, longth);
			if (startEnd[0] < startEnd[1]) {
				mm.mergeBlock(startEnd[0], 0, startEnd[1], 0,
						KDTMergeManager.SPECIFY_MERGE);
			}
		}
	}

	/**
	 * @description
	 * @author
	 * @createDate 2010-11-26
	 * @param int int String int
	 * @return int []
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private int[] getStartEnd(int ben, int end, String type, int longth) {
		IRow row = null;
		for (int i = 0; i < longth; i++) {
			// ȡ�����е�γ��
			row = kdtEntry.getRow(i);
			String str = (String) row.getCell(TYPE_COL).getValue();
			if (type == null || str == null) {
				continue;
			}
			if (str.equals(type)) {
				if (ben < 0) {
					ben = i;
				} else {
					end = i;
				}
			}
		}
		int obj[] = new int[2];
		obj[0] = ben;
		obj[1] = end;
		return obj;
	}

	private int setFootRowSum() {
		int i = 0;
		IRow footRow = null;
		KDTFootManager footRowManager = kdtEntry.getFootManager();
		if (footRowManager == null) {
			String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
			footRowManager = new KDTFootManager(kdtEntry);
			footRowManager.addFootView();
			kdtEntry.setFootManager(footRowManager);
			footRow = footRowManager.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
			kdtEntry.getIndexColumn().setWidthAdjustMode((short) 1);
			kdtEntry.getIndexColumn().setWidth(30);
			footRowManager.addIndexText(0, total);
		} else {
			footRow = footRowManager.getFootRow(0);
		}
		footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
		BigDecimal sum = getNumWeight();
		if (sum.compareTo(new BigDecimal("100")) > 0) {
			MsgBox.showWarning(this, "Ȩ��֮�Ͳ��ܴ���100%");
			i = 1;
		}
		footRow.getCell(WEIGHT_COL).setValue(sum);
		return i;
	}

	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if(e.getValue() instanceof MarketSplAuditIndexInfo){
			MarketSplAuditIndexInfo info = (MarketSplAuditIndexInfo)e.getValue();
			this.kdtEntry.getRow(rowIndex).getCell("threeStandard").setValue(info.getThreeStandard());
			this.kdtEntry.getRow(rowIndex).getCell("IndexDesc").setValue(info.getRemake());
			if(UIRuleUtil.isNotNull(info.getAccreditationType().getId())){
				MarketAccreditationTypeInfo treeinfo = MarketAccreditationTypeFactory.getRemoteInstance()
				.getMarketAccreditationTypeInfo(new ObjectUuidPK(info.getAccreditationType().getId()));
				this.kdtEntry.getRow(rowIndex).getCell("Accreditationwd").setValue(treeinfo.getName());
			}
		}
		if(AppraiseTypeEnum.PASS.equals(e.getValue())){
			this.kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
			this.kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
		}else{
			this.kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(false);
	    	if(kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).getValue() == null){
	    		kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.00");
	    	}
		}
		if(UIRuleUtil.isNull(kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).getValue())){
			kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.00");
		}
		
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			if(i==rowIndex){continue;};
			if(UIRuleUtil.isNotNull(kdtEntry.getRow(rowIndex).getCell("IndexName").getValue())){
				String indexid = ((MarketSplAuditIndexInfo)kdtEntry.getRow(rowIndex).getCell("IndexName").getValue()).getId().toString();
				if(UIRuleUtil.isNotNull(kdtEntry.getRow(i).getCell("IndexName").getValue())){
					String lastid = ((MarketSplAuditIndexInfo)kdtEntry.getRow(i).getCell("IndexName").getValue()).getId().toString();
					if(indexid.contains(lastid)){
						MsgBox.showWarning("��["+ UIRuleUtil.getIntValue(e.getRowIndex()+1) +"]�����["+ ++i+"]��ָ��������ͬ��������ѡ��ָ�����ƣ�");
						kdtEntry.getRow(rowIndex).getCell("IndexName").setValue(null);
						kdtEntry.getRow(rowIndex).getCell("Accreditationwd").setValue(null);
						kdtEntry.getRow(rowIndex).getCell("threeStandard").setValue(null);
						kdtEntry.getRow(rowIndex).getCell("IndexDesc").setValue(null);
						kdtEntry.getRow(rowIndex).getCell("qz").setValue(new BigDecimal(0));
						SysUtil.abort();
					}
				}
			}
		}
//		setColumMerger();
		mergeThemeRow(kdtEntry, "Accreditationwd", 1);
    	String colName  = kdtEntry.getColumnKey(colIndex);
    	isWeightPoint(colName,rowIndex);
	}
	
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getId()==null){return;}
    	ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
    	MarketSupplierAppraiseTemplateInfo Info = (MarketSupplierAppraiseTemplateInfo)getBizInterface().getValue(pk);
    	if(Info.isIsEnable()){
    		MsgBox.showWarning("�����ã�����ִ�д˲�����");SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getId()==null){return;}
    	ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
    	MarketSupplierAppraiseTemplateInfo Info = (MarketSupplierAppraiseTemplateInfo)getBizInterface().getValue(pk);
    	if(Info.isIsEnable()){
    		MsgBox.showWarning("�����ã�����ִ�д˲�����");SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateFactory.getRemoteInstance();
    }

    /**
	 * �����ں�
	 * */
	public static void mergeThemeRow(KDTable table, String columnName,int colIndex) {
		String theme = "";
		String lastTheme = "";
		KDTMergeManager mm = table.getMergeManager();
		int rowIndx = 0;
		int endIndx = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			endIndx = i;
			theme = UIRuleUtil.getString(table.getRow(i).getCell(columnName).getValue()) ; // ��ǰ����
			if (i > 0) {
				lastTheme = UIRuleUtil.getString(table.getRow(i - 1).getCell(columnName).getValue()); // ��һ����
				if (!theme.equals(lastTheme)) { // ��ȡ��ǰ���� �� ��һ���� ����ͬ�����ڵ��к�
					mm.mergeBlock(rowIndx, colIndex, endIndx - 1, colIndex); // �������ͬ�������ں�
					rowIndx = endIndx;
				}
			}
		}
		mm.mergeBlock(rowIndx, colIndex, endIndx, colIndex); // �������ͬ�������ں�
	}

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject) 
    {
        super.setDataObject(dataObject);
        if(STATUS_ADDNEW.equals(getOprtState())) {
            editData.put("treeid",(com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateTreeInfo)getUIContext().get(UIContext.PARENTNODE));
        }
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo objectValue = new com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        if(getUIContext().get("supplierFileType")!=null){
			objectValue.setAccreditationType((MarketAccreditationTypeInfo) getUIContext().get("supplierFileType"));
		}
        return objectValue;
    }

}