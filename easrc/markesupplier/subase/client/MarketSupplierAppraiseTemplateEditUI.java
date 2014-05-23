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
     * 评审维度
     */
    private final String TYPE_COL = "Accreditationwd";
    /*
     * 评审指标
     */
    private final String NAME_COL = "IndexName";
    /*
     * 权重
     */
    private final String WEIGHT_COL = "qz";
    /*
     * 满分标准
     */
    private final String DESC_COL = "threeStandard";
    /*
     * 状态序列号
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
		
		this.actionFirst.setVisible(false);//第一
		this.actionPre.setVisible(false);//前一
		this.actionNext.setVisible(false);//后一
		this.actionLast.setVisible(false);//最后一个
		
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
		addNewLine.setText("新增行");
		KDWorkButton insertLine = kdtEntry_detailPanel.getInsertLineButton();
		insertLine.setText("插入行");
		KDWorkButton removeLine = kdtEntry_detailPanel.getRemoveLinesButton();
		removeLine.setText("删除行");
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
					MsgBox.showWarning(this, "评审指标不能为空！");
					SysUtil.abort();
				}
				if(UIRuleUtil.isNull(kdtEntry.getRow(i).getCell("ScoreType").getValue())){
					MsgBox.showWarning(this, "评分类别不能为空！");
					SysUtil.abort();
				}
				if(AppraiseTypeEnum.WEIGHT.equals(kdtEntry.getRow(i).getCell("ScoreType").getValue())){
					flse = true;
				}
			}
		}
		//判断是否有
		if(flse){
			BigDecimal big = getNumWeight();
			if(big.compareTo(new BigDecimal("100"))!=0){
				MsgBox.showWarning(this, "权重之和必须等于100%");
				SysUtil.abort();
			}
		}
		super.verifyInput(e);
	}
    
    /**
	 * @description 获得所有权重的和
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
	 * @description 判断权重是否合格
	 * @author 胥凯
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
					MsgBox.showWarning(this, "权重不能小于0！");
					kdtEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue(
							new BigDecimal(1));
					abort();
				}
				if (tmp.compareTo(new BigDecimal("100")) > 0) {
					MsgBox.showWarning(this, "权重不能大于100！");
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
	 * @description F7数据改变时候当评审维度相同合并单元格
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
			 * 取得所有的纬度
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
			// 取得所有的纬度
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
			MsgBox.showWarning(this, "权重之和不能大于100%");
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
						MsgBox.showWarning("第["+ UIRuleUtil.getIntValue(e.getRowIndex()+1) +"]行与第["+ ++i+"]行指标名称相同，请重新选择指标名称！");
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
    		MsgBox.showWarning("已启用，不能执行此操作！");SysUtil.abort();
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
    		MsgBox.showWarning("已启用，不能执行此操作！");SysUtil.abort();
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
	 * 设置融合
	 * */
	public static void mergeThemeRow(KDTable table, String columnName,int colIndex) {
		String theme = "";
		String lastTheme = "";
		KDTMergeManager mm = table.getMergeManager();
		int rowIndx = 0;
		int endIndx = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			endIndx = i;
			theme = UIRuleUtil.getString(table.getRow(i).getCell(columnName).getValue()) ; // 当前主题
			if (i > 0) {
				lastTheme = UIRuleUtil.getString(table.getRow(i - 1).getCell(columnName).getValue()); // 上一主题
				if (!theme.equals(lastTheme)) { // 获取当前主题 与 上一主题 不相同，所在的行号
					mm.mergeBlock(rowIndx, colIndex, endIndx - 1, colIndex); // 将最后相同的主题融合
					rowIndx = endIndx;
				}
			}
		}
		mm.mergeBlock(rowIndx, colIndex, endIndx, colIndex); // 将最后相同的主题融合
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