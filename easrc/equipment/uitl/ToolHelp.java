package com.kingdee.eas.port.equipment.uitl;


import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.fa.manage.client.FaBillTraceUI;
import com.kingdee.eas.fm.common.FMException;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.util.UuidException;

public class ToolHelp {
	public static void setViewCompState(CoreUIObject ui) {
		Component[] components = ui.getComponents();
		Component component = null;
		for (int i = 0, size = components.length; i < size; i++) {
			component = components[i];
			if (component instanceof DetailPanel) {
				DetailPanel dp = (DetailPanel) component;
				Component[] dpcomponents = null;
				Component dpcomponent = null;
				dpcomponents = dp.getComponents();
				for (int j = 0, dpsize = dpcomponents.length; j < dpsize; j++) {
					dpcomponent = dpcomponents[j];
					KDPanel panel = (KDPanel) dpcomponent;
					Component[] pcomponents = null;
					Component pcomponent = null;
					pcomponents = panel.getComponents();
					for (int k = 0, psize = pcomponents.length; k < psize; k++) {
						pcomponent = pcomponents[k];
						if ("btnAddEntryLine".equals(pcomponent.getName())
								|| "btnInsertEntryLine".equals(pcomponent
										.getName())
								|| "btnRemoveEntryLine".equals(pcomponent
										.getName())
								|| "btnAddnewLine".equals(pcomponent.getName())
								|| "btnInsertLine".equals(pcomponent.getName())
								|| "btnRemoveLines"
										.equals(pcomponent.getName())
								|| "btnAddSwapLine"
										.equals(pcomponent.getName())
								|| "btnInsertSwapLine".equals(pcomponent
										.getName())
								|| "btnRemoveSwapLine".equals(pcomponent
										.getName())) {
							pcomponent.setEnabled(false);
						}
					}
				}
			} else {
				component.setEnabled(false);
			}
		}
	}
	/**
	 * 参数说明：
	 1、ctx ：服务端上下文
	 2、srcBosType：源单据的BosType
	 3、destBosType：目标单据的BosType
	 4、srcBillCollection：源单据集合，可以批量转换
	 5、botpPK：要使用的BOTP的PK。9AF44A6D
	 可以通过表T_BOT_Mapping查找到id,将id转换成PK。
	 select t.fid from T_BOT_Mapping t where t.fname='***'，***是botp的名称。
	 */
	public static void generateDestBill(String srcBosType,   
            String destBosType, CoreBillBaseCollection srcBillCollection,   
            IObjectPK botpPK) throws Exception {   
        IBOTMapping botMapping = BOTMappingFactory.getRemoteInstance();
        CoreBillBaseInfo billInfo = srcBillCollection.get(0);   
        BOTMappingInfo botMappingInfo = botMapping.getMapping(billInfo, destBosType, DefineSysEnum.BTP);   
        if (botMappingInfo == null) {   
            throw new FMException(FMException.NODESTBILL);   
        }   
        IBTPManager iBTPManager = BTPManagerFactory.getRemoteInstance();
        BTPTransformResult btpResult = null;   
        btpResult = iBTPManager.transformForBotp(srcBillCollection,destBosType, botpPK);   
        IObjectCollection destBillCols = btpResult.getBills();   
        BOTRelationCollection botRelationCols = btpResult.getBOTRelationCollection();   
        for (int i = 0; i < destBillCols.size(); i++) {   
            CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destBillCols.getObject(i);   
            iBTPManager.submitRelations(destBillInfo, botRelationCols);   
        }   
	}  
	
	/** 获取编码规则生成的编码 
     * @throws UuidException 
     * @throws BOSException 
     * @throws EASBizException */
	public static String getAutoCode(Context ctx ,IObjectValue objValue) throws EASBizException, BOSException, UuidException {
		    String NumberCode = "";
		    String companyId;
			com.kingdee.eas.base.codingrule.ICodingRuleManager codeRuleMgr = null;
			if(ctx==null){
				companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
				codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
			}else{
				companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
				codeRuleMgr = CodingRuleManagerFactory.getLocalInstance(ctx);
			}

			if (codeRuleMgr.isExist(objValue, companyId)) {
				if (codeRuleMgr.isUseIntermitNumber(objValue, companyId)) {
					NumberCode = codeRuleMgr.readNumber(objValue, companyId);
				} else {
					NumberCode = codeRuleMgr.getNumber(objValue, companyId);
				}
			}
		return NumberCode;
	}
	/**
     * 描述：显示上下查界面
     * 
     * @param idList
     * @param traceType
     * @throws Exception
     * @author add by shieli
     */
    public static void showTraceUI(CoreUI aCoreUI, HashMap idList, int traceType) throws Exception {
        UIContext uiContext = new UIContext(aCoreUI);
        uiContext.put("IDList", idList);
        uiContext.put(UIContext.OWNER, aCoreUI);
        String typeName = UIFactoryName.MODEL;
        IUIFactory uiFactory = UIFactory.createUIFactory(typeName);
        IUIWindow newWindow = uiFactory.create(FaBillTraceUI.class.getName(), uiContext, null);
        newWindow.show();
    }
    public static void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null){
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else{
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++){
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++){
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName)){
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
    /**
     * 给指定table数字列求和
     * 
     * @param table 表格
     * @param columnName 表格列名
     */
    public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
        		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
        	else if(table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof String){
        		String value = (String)table.getRow(i).getCell(columnName).getValue();
        		value = value.replaceAll(",", "");
        		sum = sum.add(new BigDecimal(value));
        	}
        }
        return sum;
    }
    /**
     * 动态报表获取选中的行
     * @param tblMain
     * @return
     */
    public static int[] getRowList(KDTable tblMain){
    	int selectRows[] = KDTableUtil.getSelectedRows(tblMain);
		return selectRows;
    }
    
    /**
     * 不合格整改通知单融合
     * @param table
     * @param columnName
     * @param colIndex
     */
    public static void mergeThemeRow(KDTable table, String columnName) {
		String theme = "";
		String lastTheme = "";
		int colIndex = table.getColumnIndex(columnName);
		int nameIndex = table.getColumnIndex("equipmentName");
		KDTMergeManager mm = table.getMergeManager();
		int rowIndx = 0;
		int endIndx = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			endIndx = i;
			theme = UIRuleUtil.isNotNull(table.getRow(i).getCell(columnName).getValue())?
					((EquIdInfo)table.getRow(i).getCell(columnName).getValue()).getId().toString():""; // 当前主题
			if (i > 0) {
				lastTheme = UIRuleUtil.isNotNull(table.getRow(i - 1).getCell(columnName).getValue())?
						((EquIdInfo)table.getRow(i - 1).getCell(columnName).getValue()).getId().toString():""; // 上一主题
				if (!theme.equals(lastTheme)) { // 获取当前主题 与 上一主题 不相同，所在的行号
					mm.mergeBlock(rowIndx, colIndex, endIndx - 1, colIndex); // 将最后相同的主题融合
					mm.mergeBlock(rowIndx, nameIndex, endIndx - 1, nameIndex); // 将最后相同的主题融合
					rowIndx = endIndx;
				}
			}
		}
		mm.mergeBlock(rowIndx, colIndex, endIndx, colIndex); // 将最后相同的主题融合
		mm.mergeBlock(rowIndx, nameIndex, endIndx, nameIndex); // 将最后相同的主题融合
	}

}
