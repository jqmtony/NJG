package com.kingdee.eas.port.equipment.uitl;


import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
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
import com.kingdee.eas.port.equipment.record.client.EquIdEditUI;
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
            iBTPManager.saveRelations(destBillInfo, botRelationCols);   
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
		int testResults = table.getColumnIndex("testResults");
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
					mm.mergeBlock(rowIndx, testResults, endIndx - 1, testResults); // 将最后相同的主题融合
					rowIndx = endIndx;
				}
			}
		}
		mm.mergeBlock(rowIndx, colIndex, endIndx, colIndex); // 将最后相同的主题融合
		mm.mergeBlock(rowIndx, nameIndex, endIndx, nameIndex); // 将最后相同的主题融合
		mm.mergeBlock(rowIndx, testResults, endIndx, testResults); // 将最后相同的主题融合
	}

    
	public static KDPromptSelector initPrmtEquIdByF7Color(EntityViewInfo view,boolean isMuiltiSelection)
    {
    	MyKDCommonPromptDialog dlg = new MyKDCommonPromptDialog() {
			protected void handleDisplayCol(KDTDataRequestEvent e, KDTable table) {
				super.handleDisplayCol(e, table);
				
				int begin = e.getFirstRow();
				int last = e.getLastRow();
				
				for (int i = begin; i < last+1 ; i++) {
					ICell cellP = table.getRow(i).getCell("sbStatus");
					
					if(cellP != null  &&  cellP.getValue() != null){
						String status = UIRuleUtil.getString(cellP.getValue());
		        		 
		        		 if(status.equals("停用"))
		        			 table.getRow(i).getStyleAttributes().setBackground(Color.RED);
					}
				}
			}
		};
		dlg.setEnabledMultiSelection(isMuiltiSelection);
		
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.port.equipment.record.app.EquIdQuery")));
		dlg.setEntityViewInfo(view);
		return dlg;
    }
	
	public static SelectorItemCollection getEquIdSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = "false";
		if (StringUtils.isEmpty(selectorAll)) {
			selectorAll = "true";
		}
		if (selectorAll.equalsIgnoreCase("true")) {
		} else {
			sic.add(new SelectorItemInfo("creator.id"));
			sic.add(new SelectorItemInfo("creator.number"));
			sic.add(new SelectorItemInfo("creator.name"));
		}
		sic.add(new SelectorItemInfo("createTime"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		} else {
			sic.add(new SelectorItemInfo("lastUpdateUser.id"));
			sic.add(new SelectorItemInfo("lastUpdateUser.number"));
			sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("CU.*"));
		} else {
			sic.add(new SelectorItemInfo("CU.id"));
			sic.add(new SelectorItemInfo("CU.number"));
			sic.add(new SelectorItemInfo("CU.name"));
		}
		sic.add(new SelectorItemInfo("bizDate"));
		sic.add(new SelectorItemInfo("description"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("auditor.*"));
		} else {
			sic.add(new SelectorItemInfo("auditor.id"));
			sic.add(new SelectorItemInfo("auditor.number"));
			sic.add(new SelectorItemInfo("auditor.name"));
		}
		sic.add(new SelectorItemInfo("status"));
		sic.add(new SelectorItemInfo("bizStatus"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("special"));
		sic.add(new SelectorItemInfo("isMainEqm"));
		sic.add(new SelectorItemInfo("isbaoxian"));
		sic.add(new SelectorItemInfo("parent"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("nowAmount"));
		sic.add(new SelectorItemInfo("oldYear"));
		sic.add(new SelectorItemInfo("deadline"));
		sic.add(new SelectorItemInfo("debuger"));
		sic.add(new SelectorItemInfo("cpgjzs"));
		sic.add(new SelectorItemInfo("cpsyqh"));
		sic.add(new SelectorItemInfo("cpsbh"));
		sic.add(new SelectorItemInfo("chuanCheck"));
		sic.add(new SelectorItemInfo("name"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("ssOrgUnit.*"));
		} else {
			sic.add(new SelectorItemInfo("ssOrgUnit.id"));
			sic.add(new SelectorItemInfo("ssOrgUnit.number"));
			sic.add(new SelectorItemInfo("ssOrgUnit.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("equTypeone.*"));
		} else {
			sic.add(new SelectorItemInfo("equTypeone.id"));
			sic.add(new SelectorItemInfo("equTypeone.number"));
			sic.add(new SelectorItemInfo("equTypeone.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("textType.*"));
		} else {
			sic.add(new SelectorItemInfo("textType.id"));
			sic.add(new SelectorItemInfo("textType.number"));
			sic.add(new SelectorItemInfo("textType.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("jhOrgUnit.*"));
		} else {
			sic.add(new SelectorItemInfo("jhOrgUnit.id"));
			sic.add(new SelectorItemInfo("jhOrgUnit.number"));
			sic.add(new SelectorItemInfo("jhOrgUnit.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("wxOrgUnit.*"));
		} else {
			sic.add(new SelectorItemInfo("wxOrgUnit.id"));
			sic.add(new SelectorItemInfo("wxOrgUnit.number"));
			sic.add(new SelectorItemInfo("wxOrgUnit.name"));
		}
		sic.add(new SelectorItemInfo("model"));
		sic.add(new SelectorItemInfo("size"));
		sic.add(new SelectorItemInfo("weight"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("wxDept.*"));
		} else {
			sic.add(new SelectorItemInfo("wxDept.id"));
			sic.add(new SelectorItemInfo("wxDept.number"));
			sic.add(new SelectorItemInfo("wxDept.name"));
		}
		sic.add(new SelectorItemInfo("qyDate"));
		sic.add(new SelectorItemInfo("serialNumber"));
		sic.add(new SelectorItemInfo("sbStatus"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("unit.*"));
		} else {
			sic.add(new SelectorItemInfo("unit.id"));
			sic.add(new SelectorItemInfo("unit.number"));
			sic.add(new SelectorItemInfo("unit.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("eqmType.*"));
		} else {
			sic.add(new SelectorItemInfo("eqmType.id"));
			sic.add(new SelectorItemInfo("eqmType.number"));
			sic.add(new SelectorItemInfo("eqmType.name"));
		}
		sic.add(new SelectorItemInfo("eqmCategory"));
		sic.add(new SelectorItemInfo("innerNumber"));
		sic.add(new SelectorItemInfo("nowStatus"));
		sic.add(new SelectorItemInfo("zzsShortName"));
		sic.add(new SelectorItemInfo("dependable"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("parent.*"));
		} else {
			sic.add(new SelectorItemInfo("parent.id"));
			sic.add(new SelectorItemInfo("parent.number"));
			sic.add(new SelectorItemInfo("parent.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("address.*"));
		} else {
			sic.add(new SelectorItemInfo("address.id"));
			sic.add(new SelectorItemInfo("address.number"));
			sic.add(new SelectorItemInfo("address.name"));
			sic.add(new SelectorItemInfo("address.detailAddress"));
		}
		sic.add(new SelectorItemInfo("location"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("usingDept.*"));
		} else {
			sic.add(new SelectorItemInfo("usingDept.id"));
			sic.add(new SelectorItemInfo("usingDept.number"));
			sic.add(new SelectorItemInfo("usingDept.name"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("resPerson.*"));
		} else {
			sic.add(new SelectorItemInfo("resPerson.id"));
			sic.add(new SelectorItemInfo("resPerson.number"));
			sic.add(new SelectorItemInfo("resPerson.name"));
		}
		sic.add(new SelectorItemInfo("mader"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("madedCountry.*"));
		} else {
			sic.add(new SelectorItemInfo("madedCountry.id"));
			sic.add(new SelectorItemInfo("madedCountry.number"));
			sic.add(new SelectorItemInfo("madedCountry.name"));
		}
		sic.add(new SelectorItemInfo("madeDate"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("supplier.*"));
		} else {
			sic.add(new SelectorItemInfo("supplier.id"));
			sic.add(new SelectorItemInfo("supplier.number"));
			sic.add(new SelectorItemInfo("supplier.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("useUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("useUnit.id"));
        	sic.add(new SelectorItemInfo("useUnit.number"));
        	sic.add(new SelectorItemInfo("useUnit.name"));
		}
		sic.add(new SelectorItemInfo("reachedDate"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("installer.*"));
		} else {
			sic.add(new SelectorItemInfo("installer.id"));
			sic.add(new SelectorItemInfo("installer.number"));
			sic.add(new SelectorItemInfo("installer.name"));
		}
		sic.add(new SelectorItemInfo("checkDate"));
		sic.add(new SelectorItemInfo("sourceUnit"));
		sic.add(new SelectorItemInfo("portTest"));
		sic.add(new SelectorItemInfo("cityTest"));
		sic.add(new SelectorItemInfo("testDay"));
		sic.add(new SelectorItemInfo("isccCheck"));
		sic.add(new SelectorItemInfo("tzdaNumber"));
		sic.add(new SelectorItemInfo("tzsbStatus"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("asset.*"));
		} else {
			sic.add(new SelectorItemInfo("asset.id"));
			sic.add(new SelectorItemInfo("asset.number"));
			sic.add(new SelectorItemInfo("asset.assetName"));
		}
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("assetStatus.*"));
		} else {
			sic.add(new SelectorItemInfo("assetStatus.id"));
			sic.add(new SelectorItemInfo("assetStatus.number"));
			sic.add(new SelectorItemInfo("assetStatus.name"));
			sic.add(new SelectorItemInfo("assetStatus.isDefault"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("specialType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("specialType.id"));
        	sic.add(new SelectorItemInfo("specialType.number"));
        	sic.add(new SelectorItemInfo("specialType.name"));
		}
		sic.add(new SelectorItemInfo("assetValue"));
		sic.add(new SelectorItemInfo("installCost"));
		sic.add(new SelectorItemInfo("TechnologyPar.seq"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("TechnologyPar.*"));
		} else {
		}
		sic.add(new SelectorItemInfo("TechnologyPar.parName"));
		sic.add(new SelectorItemInfo("TechnologyPar.parValue"));
		sic.add(new SelectorItemInfo("TechnologyPar.parInfo"));
		sic.add(new SelectorItemInfo("SpareInfo.seq"));
		if (selectorAll.equalsIgnoreCase("true")) {
			sic.add(new SelectorItemInfo("SpareInfo.*"));
		} else {
		}
		sic.add(new SelectorItemInfo("SpareInfo.materialName"));
		sic.add(new SelectorItemInfo("SpareInfo.speModel"));
    	sic.add(new SelectorItemInfo("SpareInfo.shuliangone"));
    	sic.add(new SelectorItemInfo("SpareInfo.useyong"));
    	sic.add(new SelectorItemInfo("SpareInfo.fachangjia"));
    	sic.add(new SelectorItemInfo("SpareInfo.noteone"));
    	sic.add(new SelectorItemInfo("SpareInfo.attachone"));
    	sic.add(new SelectorItemInfo("E3.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E3.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E3.csmingcheng"));
    	sic.add(new SelectorItemInfo("E3.csmodel"));
    	sic.add(new SelectorItemInfo("E3.shuliang"));
    	sic.add(new SelectorItemInfo("E3.power"));
    	sic.add(new SelectorItemInfo("E3.speed"));
    	sic.add(new SelectorItemInfo("E3.chuandong"));
    	sic.add(new SelectorItemInfo("E3.zidong"));
    	sic.add(new SelectorItemInfo("E3.madeFac"));
    	sic.add(new SelectorItemInfo("E3.noteoo"));
		sic.add(new SelectorItemInfo("ccNumber"));
		sic.add(new SelectorItemInfo("tzdaNumber"));
		sic.add(new SelectorItemInfo("cityPeriod"));
		sic.add(new SelectorItemInfo("portPeriod"));
		sic.add(new SelectorItemInfo("code"));
		sic.add(new SelectorItemInfo("engineNumber"));
		sic.add(new SelectorItemInfo("carNumber"));
		sic.add(new SelectorItemInfo("parent"));
		sic.add(new SelectorItemInfo("ratedWeight"));
		sic.add(new SelectorItemInfo("assetValue"));
		sic.add(new SelectorItemInfo("sbdescription"));
		sic.add(new SelectorItemInfo("textDate1"));
		sic.add(new SelectorItemInfo("daytow"));
		sic.add(new SelectorItemInfo("dayone"));
		sic.add(new SelectorItemInfo("telePhoneNumber"));
		sic.add(new SelectorItemInfo("actrueTime"));
		sic.add(new SelectorItemInfo("responsible"));
		sic.add(new SelectorItemInfo("inStreet"));
		sic.add(new SelectorItemInfo("inspectorOne"));
		return sic;
	}
}
