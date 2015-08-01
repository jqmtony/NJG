package com.kingdee.eas.fdc.basedata;

import java.awt.Color;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.logging.Logger;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.BasicNumberTextField;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.NewOrgViewHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.ScriptUtil;
import com.kingdee.util.enums.IntEnum;

public class FDCHelper extends FDCNumberHelper{
	public final static Color KDTABLE_TOTAL_BG_COLOR = new Color(0xF6F6B6);

	public final static Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(0xF5F5E6);

	// 格式化精度设置
	public final static String KDTABLE_NUMBER_FTM = "#,##0.00";

	// public final static String kdTablePercentFtm = "%{0.00%}f";
	public final static String KDTABLE_PERCENT_FTM = "%r{0.00}p";

	public final static String KDTABLE_DATE_FMT = "yyyy-MM-dd";
	public final static String KDTABLE_DATETIME_FMT = "yyyy-MM-dd HH:mm:ss";

	public final static String ACTUAL_DIGIT_FMT = "#,##0.###########";

	public static final String strDataFormat = "#,##0.00;-#,##0.00";
	public static final String strDataFormat4 = "#,##0.0000;-#,##0.0000";
	//带%百分比
	public static final String strPropFormat2="#,###.##;-#,###.##%";
	// static DateFormat is not safe in multithread, see jdk
	public static final DateFormat FORMAT_DAY = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final DateFormat FORMAT_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final DateFormat FORMAT_MONTH = new SimpleDateFormat(
			"yyyy-MM");

	/**
	 * 根据数组在指定字段中过滤
	 *
	 * @param key
	 * @param array
	 * @return
	 */
	public static EntityViewInfo getIncludeEntityView(String key, String[] array) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Set bankSet = getSetByArray(array);
		if (bankSet != null && bankSet.size() > 0) {
			filter.getFilterItems().add(
					new FilterItemInfo(key, bankSet, CompareType.INCLUDE));
		}
		return view;
	}

	/**
	 * 数组转换set
	 *
	 * @param array
	 * @return
	 */
	public static Set getSetByArray(String[] array) {
		Set set = new HashSet();
		if (array == null) {
			return set;
		}
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		return set;
	}

	/**
	 * 得到action的PK
	 *
	 * @param action
	 * @return
	 */
	public static IMetaDataPK getActionPK(ItemAction action) {
		if (action == null) {
			return null;
		}
		String actoinName = action.getClass().getName();
		if (actoinName.indexOf("$") >= 0) {
			actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
		}

		return new MetaDataPK(actoinName);
	}

	/**
	 * 判断数组是否为空
	 *
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(Object[] param) {
		return (param == null || param.length == 0 || param[0] == null);
	}

	/**
	 * 得到精度格式
	 *
	 * @param currencyId
	 * @return
	 */
	public static String getNumberFtm(int pre) {
		StringBuffer fmt = new StringBuffer("#,##0");
		if(pre>0){
			fmt.append(".");
			for(int i=1;i<=pre;i++){
				fmt.append("0");
			}
		}
		return fmt.toString();
//		return "%r-[ ]0." + pre + "n";
	}

	/**
	 * 当前一天的下一天
	 *
	 * @return
	 */
	public static java.util.Date getNextDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
		return cal.getTime();
	}

	/**
	 * 对2个对象作equals比较,主要是对Timestamp.equals(Date)的处理,通过时间getTime比较
	 * */
	public static boolean isEqual(Object object1, Object object2) {
		if (object1 instanceof BigDecimal) {
			if (object1 == null) {
				object1 = FDCHelper.ZERO;
			}
			if (object2 == null) {
				object2 = FDCHelper.ZERO;
			}
			if (((BigDecimal) object1).compareTo((BigDecimal) object2) == 0) {
				return true;
			} else {
				return false;
			}
		} else if ((object1 instanceof Timestamp)) {
			if (object2 instanceof Date) {
				long time1 = ((Timestamp) object1).getTime();
				long time2 = ((Date) object2).getTime();
				return time1 == time2;
			} else {
				return object1.equals(object2);
			}
		} else if ((object1 instanceof Date)) {
			if (object2 instanceof Timestamp) {
				long time1 = ((Date) object1).getTime();
				long time2 = ((Timestamp) object2).getTime();
				return time1 == time2;
			} else {
				return object1.equals(object2);
			}
		} else {
			if (object1 == null && object2 != null) {
				return false;
			} else if (object1 != null && object2 == null) {
				return false;
			} else if (object1 != null && object2 != null) {
				return object1.equals(object2);
			}
			return true;
		}
	}

	/**
	 * 金额转化大小写
	 *
	 * @param currency
	 * @param amount
	 * @return
	 */
	public static String transCap(CurrencyInfo currency, BigDecimal amount) {
		if (currency == null || currency.getIsoCode() == null || amount == null)
			return "";
		boolean isNegative = false;
		if (amount.doubleValue() < 0) {
			amount = amount.abs();
			isNegative = true;
		}
		String iso = currency.getIsoCode();
		Class c = null;
		Format f = null;
		try {
			c = Class.forName("com.kingdee.eas.fm.nt.NTNumberFormat");
			f = (Format) c.getMethod("getInstance",
					new Class[] { String.class }).invoke(null,
					new Object[] { iso });
		} catch (Exception e) {
			// @AbortException
			e.printStackTrace();
		}
		String amountString = f.format(amount);
		if (isNegative)
			amountString = "负" + amountString;
		return amountString;
	}
	
	/*
	 * 金额到角的话根据参数hasFull,设置“整”
	 */
    public static String getChineseFormat(BigDecimal number, boolean hasFull) {
        if (number == null) {
            return "";
        }

        String nagative = "";
        String nagVirText = "";
        boolean isNagativeB = false;
        if (number.toString().indexOf("-") >= 0) {
            nagative = getChineseName("nagative");
            isNagativeB = true;
        }

        Properties decimalPro = getDecimalUnitMapping();
        Properties integerPro = getIntegerUnitMapping(true);
        Properties numberPro = getNumberMapping();

        String valTemp = number.toString();
        
        /** 若是有负数,则去掉 "- "号 * */
        if (isNagativeB) {
            valTemp = valTemp.replace('-', ' ').trim();
        }

        int dotIndex = valTemp.indexOf(".");
        
        /** 整数部分 * */
        String integerStr = "";
        String integerTempStr = null;
        
        /** 小数部分 * */
        String decimalStr = "";
        String decimalTempStr = null;
        
        /** 1: **/
        if (dotIndex == -1) {
            integerTempStr = valTemp;
        } else {
            integerTempStr = valTemp.substring(0, dotIndex);
            decimalTempStr = valTemp.substring(dotIndex + 1);
        }

        /** 2: 整数部分的处理 * */

        if (integerTempStr != null) {
            int integerLen = integerTempStr.length() - 1;
            boolean zeroAttached = false; // Jacket:

            boolean hasTem = true;

            for (int i = integerLen; i >= 0; i--) {
                String index = String.valueOf(i);
                String str = String.valueOf(integerTempStr.charAt(integerLen - i));
                String numStr = numberPro.getProperty(str);
                String uombStr = integerPro.getProperty(index);

                boolean needed = false;
                if (uombStr.startsWith("*")) {
                    needed = true;
                    uombStr = uombStr.substring(1);
                    if (hasTem && i == 4 && integerLen >= 8) {
                        //uombStr = "";
                        if((('0' == integerTempStr.charAt(0) && '0' == integerTempStr.charAt(1)
                        		&& '0' == integerTempStr.charAt(2) && '0' == integerTempStr.charAt(3)
                        		&& '0' == integerTempStr.charAt(integerLen-8) &&'0' == integerTempStr.charAt(integerLen-7)
                        		&& '0' == integerTempStr.charAt(integerLen-6) && '0' == integerTempStr.charAt(integerLen-5))
                        		) && (('0' != integerTempStr.charAt(integerLen-4) || '0' != integerTempStr.charAt(integerLen-3)
                        		||  '0' != integerTempStr.charAt(integerLen-2)|| '0' != integerTempStr.charAt(integerLen-1)) )){
                        	uombStr = getChineseName("zero");
                        }else if(('0' != integerTempStr.charAt(integerLen-4))){
                        	//uombStr = getChineseName("zero");
                        }else{
                        	uombStr = "";
                        }
                        hasTem = false;
                    }                   
                }
                if ("0".equals(str)) {
                    if (zeroAttached) {
                        numStr = "";
                        if (needed) {
                            zeroAttached = false;
                            integerStr = integerStr.substring(0, integerStr.length() - 1);

                        } else {
                            uombStr = "";
                        }
                    } else {
                        if (!needed) {
                            zeroAttached = true;
                            uombStr = "";
                        } else {
                            zeroAttached = false;
                            numStr = "";
                        }
                    }
                } else {
                    if (i <= 7 && i >= 4) {
                        hasTem = false;
                    }
                    zeroAttached = false;
                }

                integerStr += numStr + uombStr;
            }
        }

        boolean integerEmpty = integerStr.length() < 2 ? true : false;
        if (integerEmpty) {
            integerStr = "";
        }

        boolean decimalEmpty = true;
        boolean hasJiao = false;
        
        /** 3: 小数部分的处理 * */
        if (decimalTempStr != null) {
            int decimalLen = decimalTempStr.length();
            int n = decimalPro.size();
            n = decimalLen > n ? n : decimalLen;
            boolean zeroAttached = false; // Jacket:
            boolean ignoreLeadingZero = true;
            for (int j = 0; j < n; j++) {
                String index = String.valueOf(j);
                String str = String.valueOf(decimalTempStr.charAt(j));
                String numStr = numberPro.getProperty(str);
                String uombStr = decimalPro.getProperty(index);

                if ("0".equals(str)) {
                    if (zeroAttached) {
                        numStr = "";
                        uombStr = "";
                        if (j == n - 1) {
                            decimalStr = decimalStr.substring(0, decimalStr.length() - 1);
                        }
                    } else {
                        if (j < n - 1 && !ignoreLeadingZero) {
                            zeroAttached = true;
                        } else {
                            numStr = "";
                        }
                        uombStr = "";
                    }
                } else {
                    if (j == 0) {
                        hasJiao = true;
                    } else {
                        hasJiao = false;
                    }
                    zeroAttached = false;
                    ignoreLeadingZero = false;
                }
                String temp = numStr + uombStr ;
                decimalStr = decimalStr + temp;
            }

            decimalEmpty = decimalStr.length() < 2 ? true : false;
            if (decimalEmpty) {
                decimalStr = "";
            }

            String unDescriptable = ""; // 无法用币别单位表达的小数位
            int index = -1; // 最后一个非零小数位
            for (int i = n; i < decimalLen; i++) {
                char c = decimalTempStr.charAt(i);
                String numStr = numberPro.getProperty(String.valueOf(c));
                unDescriptable += numStr;
                if ('0' != c) {
                    index = i;
                }
            }
            if (index >= 0) {
                decimalEmpty = false;
                if ('0' == decimalTempStr.charAt(n - 1)) {
                    decimalStr += getChineseName("zero") + decimalPro.getProperty(String.valueOf(n - 1));
                }

                decimalStr += unDescriptable.substring(0, index - n + 1);
            }else{
            	//by tim_gao 当decimalTempStr length只有1的时候decimalTempStr会报错的,不知道应该怎么改合适所以加判断
            	//2012-03-02
            	//提问 类似这种转换的东西为什么不做成资源文件呢
            	if(decimalTempStr.length()>1){
            		 if ('0' == decimalTempStr.charAt(0) && '0' != decimalTempStr.charAt(1)) {
                         decimalStr = getChineseName("zero") + decimalStr;
                     } 
            	}else{
            		if ('0' == decimalTempStr.charAt(0) ) {
                        decimalStr = getChineseName("zero") + decimalStr;
                    }
            	}
               
                
            }
        }

        if (integerEmpty && decimalEmpty) {
            integerStr = getChineseName("zero") + getChineseName("money"); // "零元";
        }

        // 金额到分的话不应该有“整”
        // 金额到角的话根据参数设置“整”
        String value = nagative + nagVirText + integerStr + decimalStr;
        
//        if( decimalStr.length() == 1 && ! ( hasJiao)){
//            value = value +getChineseName("zero") ;
//        }
        
        value = nagative + nagVirText + integerStr + decimalStr;
        
        if ((decimalStr == null || decimalStr.length() == 0 || decimalStr.length() == 1) || ( hasJiao)) {
            value = value + getChineseName("full");
        }
        /** 4: 整合负数标识,整数和小数.* */
        return value;
    }    
    private static String getChineseName(String key) {
        return EASResource.getString("com.kingdee.eas.fi.gl.GLResource", key);
    }
    private static Properties getDecimalUnitMapping() {
        Properties decimalCurrencyUomb = new Properties();
        decimalCurrencyUomb.setProperty("0", getChineseName("jiao"));
        decimalCurrencyUomb.setProperty("1", getChineseName("fen"));
        decimalCurrencyUomb.setProperty("2", getChineseName("li"));

        return decimalCurrencyUomb;
    }

    private static Properties getIntegerUnitMapping(boolean flag) {
        Properties integerCurrencyUomb = new Properties();
        if (flag)
            integerCurrencyUomb.setProperty("0", "*" + getChineseName("money")); // needed
        // unit
        else
            integerCurrencyUomb.setProperty("0", "*"); // needed
        // unit
        integerCurrencyUomb.setProperty("1", getChineseName("ten"));
        integerCurrencyUomb.setProperty("2", getChineseName("handred"));
        integerCurrencyUomb.setProperty("3", getChineseName("thrasand"));
        integerCurrencyUomb.setProperty("4", "*" + getChineseName("tenshand")); // needed
        // unit
        integerCurrencyUomb.setProperty("5", getChineseName("ten"));
        integerCurrencyUomb.setProperty("6", getChineseName("handred"));
        integerCurrencyUomb.setProperty("7", getChineseName("thrasand"));
        integerCurrencyUomb.setProperty("8", "*" + getChineseName("handredmill")); // needed
        // unit
        integerCurrencyUomb.setProperty("9", getChineseName("ten"));
        integerCurrencyUomb.setProperty("10", getChineseName("handred"));
        integerCurrencyUomb.setProperty("11", getChineseName("thrasand"));
        integerCurrencyUomb.setProperty("12", getChineseName("tenshand"));
        integerCurrencyUomb.setProperty("13", getChineseName("handredmill"));
        integerCurrencyUomb.setProperty("14", getChineseName("ten"));

        return integerCurrencyUomb;
    }

    private static Properties getNumberMapping() {
        Properties numberMapping = new Properties();
        numberMapping.setProperty("0", getChineseName("zero"));
        numberMapping.setProperty("1", getChineseName("one"));
        numberMapping.setProperty("2", getChineseName("two"));
        numberMapping.setProperty("3", getChineseName("three"));
        numberMapping.setProperty("4", getChineseName("four"));
        numberMapping.setProperty("5", getChineseName("five"));
        numberMapping.setProperty("6", getChineseName("six"));
        numberMapping.setProperty("7", getChineseName("seven"));
        numberMapping.setProperty("8", getChineseName("eight"));
        numberMapping.setProperty("9", getChineseName("nine"));

        return numberMapping;
    }
	

	/**
	 * 得到表达式结果
	 *
	 * @param express
	 * @return
	 */
	public static Object getScriptResult(String express) {
		return ScriptUtil.evalExpr(express, null);
	}

	/**
	 * 构造树形结构完整名称的Map<String(ID), String(LongKey)>
	 * @param iTree 业务接口
	 * @param keyName 用以标识的字段名称
	 * @param sep 长编码的连接符
	 * @throws BOSException
	 */
	public static Map createTreeDataMap(ITreeBase iTree, String keyName,
			String sep) throws BOSException {
		TreeBaseCollection collection = iTree.getTreeBaseCollection();
		Map idInfoMap = new HashMap();
		for (int i = 0; i < collection.size(); i++) {
			CoreBaseInfo coreBaseInfo = collection.get(i);
			idInfoMap.put(coreBaseInfo.get("id").toString(), coreBaseInfo);
		}
		Map idLongNameMap = new HashMap();
		for (int i = 0; i < collection.size(); i++) {
			TreeBaseInfo treeBaseInfo = collection.get(i);
			String id = treeBaseInfo.getId().toString();
			String longKey = (String) treeBaseInfo.get(keyName);
			while (treeBaseInfo.innerGetParent() != null) {
				TreeBaseInfo parent = (TreeBaseInfo) idInfoMap.get(treeBaseInfo
						.innerGetParent().getId().toString());
				longKey = parent.getString(keyName) + sep + longKey;
				treeBaseInfo = parent;
			}
			idLongNameMap.put(id, longKey);
		}

		return idLongNameMap;
	}

	public static void setTreeForbidNode(DefaultKingdeeTreeNode treeNode,
			String comparePath, Object selectObject) {
		if (treeNode == null || selectObject == null)
			return;
		Object object = treeNode.getUserObject();
		String[] pros = comparePath.split(",");
		for (int i = 0; i < pros.length; i++) {
			Class c = object.getClass();
			Method[] methods = c.getMethods();
			Method method = null;
			for (int j = 0; j < methods.length; j++) {
				Method met = methods[j];
				if (met.getName().equals(pros[i].trim())) {
					method = met;
				}
			}
			if (method == null) {
				object = null;
				break;
			}
			try {
				object = method.invoke(object, new Object[] {});
			} catch (Exception e) {
				// @AbortException
				e.printStackTrace();
			}
		}
		if (object != null && object.equals(selectObject)) {
			treeNode.setCheckBoxEnabled(false);
//			treeNode.setTextColor(Color.gray);
		}
		for (int i = 0; i < treeNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) treeNode
					.getChildAt(i);
			setTreeForbidNode(child, comparePath, selectObject);
		}
	}

	public static CostAccountCollection getDistributAccounts(
			CostAccountInfo acct) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems()
				.add(
						new FilterItemInfo("srcCostAccountId", acct.getId()
								.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("isSource", Boolean.FALSE));
		return CostAccountFactory.getRemoteInstance().getCostAccountCollection(
				view);
	}

	public static String getFullAcctSql() {
		return "(select case when acct.fcurproject is not null then unit2.flongnumber||'!'||project.flongnumber else  unit.flongnumber end"
				+ " fullnumber,acct.*, project.fisLeaf isleafProject	from t_fdc_costaccount acct left join t_org_baseunit unit on acct.ffullorgunit=unit.fid left join"
				+ " t_fdc_curproject project on acct.fcurproject =project.fid and project.FIsEnabled=1 left join t_org_baseunit unit2 "
				+ "on project.ffullorgunit=unit2.fid) costAcct";
	}

	public static String[] getProjectLeafIds(CurProjectInfo project)
			throws BOSException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select fid from  t_fdc_curProject where ");
		String longNumber = project.getLongNumber();
		sql.append(" (FLongNumber = '").append(longNumber).append("'").append(
				" or FLongNumber like '").append(longNumber).append("!%' ")
				.append(" or FLongNumber like '%!").append(longNumber).append(
						"!%') and fisleaf=1 ");
		sql.append(" and FFullOrgUnit ='"
				+ project.getFullOrgUnit().getId().toString() + "'");
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql.toString())
				.executeSQL();
		String[] ids = new String[rs.size()];
		int i = 0;
		while (rs.next()) {
			ids[i++] = rs.getString("fid");
		}
		return ids;
	}

	/**
	 * 得到所有下有工程项目
	 * @param ctx
	 * @param prjId 工程项目Id,可以为非明细/明细
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Set getProjectLeafIdSet(Context ctx,String prjId) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select b.fid as fid from T_FDC_CurProject a ,T_FDC_CurProject b");
		builder.appendSql(" where a.ffullorgunit=b.ffullorgunit and a.fid=? and b.fisleaf=1 and b.fisenabled=1 and (charindex(a.flongnumber||'!',b.flongnumber)=1 or b.fid=a.fid) ");
		builder.addParam(prjId);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.size() == 0) {
			return new HashSet();
		}
		Set prjSet = new HashSet(rowSet.size());
		while (rowSet.next()) {
			prjSet.add(rowSet.getString("fid"));
		}
		return prjSet;
	}
	public static String[] getFullOrgUnitLeafIds(FullOrgUnitInfo org)
			throws BOSException, SQLException {
		StringBuffer sql = new StringBuffer();
		String longNumber = org.getLongNumber();
		sql.append("select fid from  t_org_company where ");
		sql.append(" (FLongNumber = '").append(longNumber.trim()).append("'")
				.append(" or FLongNumber like '").append(longNumber).append(
						"!%' ").append(" or FLongNumber like '%!").append(
						longNumber).append("!%')").append(
						" and FisLeaf =1 and fisonlyunion =0");
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql.toString())
				.executeSQL();
		String[] companyIds = new String[rs.size()];
		int i = 0;
		while (rs.next()) {
			companyIds[i++] = rs.getString("fid");
		}
		return companyIds;
	}
	public static Set getUpCompanyOrgIds(Context ctx,String companyOrgId) throws BOSException, EASBizException{
		if(companyOrgId==null)
			return new HashSet();
		CompanyOrgUnitInfo currentOrg = null;
		if(ctx!=null)
			currentOrg = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(companyOrgId));
		else
			currentOrg = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(companyOrgId));
		if(currentOrg==null)
			return new HashSet();
		String[] ancestor = NewOrgViewHelper.getAncestor(currentOrg.getLongNumber());
		Set lNumSet = new HashSet();
		for (int i = 0; i < ancestor.length; i++) {
			lNumSet.add(ancestor[i]);
		}
		EntityViewInfo orgView = new EntityViewInfo();
		FilterInfo orgFilter = new FilterInfo();
		orgFilter.getFilterItems().add(
				new FilterItemInfo("longNumber", lNumSet, CompareType.INCLUDE));
		orgFilter.getFilterItems().add(
				new FilterItemInfo("id", currentOrg.getId(), CompareType.NOTEQUALS));
		orgView.setFilter(orgFilter);
		ICompanyOrgUnit iComp = null;
		if(ctx!=null)
			iComp = CompanyOrgUnitFactory.getLocalInstance(ctx);
		else
			iComp = CompanyOrgUnitFactory.getRemoteInstance();
		CompanyOrgUnitCollection ancestorComps = iComp
				.getCompanyOrgUnitCollection(orgView);
		Set orgIdSet = new HashSet(ancestorComps.size());
		for (int i = 0, n = ancestorComps.size(); i < n; i++) {
			orgIdSet.add(ancestorComps.get(i).getId().toString());
		}
		return orgIdSet;
		
	}
	
	//获得工程项目对应的成本中心组织
	public static CostCenterOrgUnitInfo getCostCenter(CurProjectInfo project,Context ctx) {
		
		CostCenterOrgUnitInfo orgInfo = null;
		if(project.getCostCenter()!=null && project.getCostCenter().getDisplayName()!=null){
			orgInfo = project.getCostCenter();
		}else{
			String projId = project.getId().toString();
			
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("costCenter.number");
			sic.add("costCenter.name");
			sic.add("costCenter.isLeaf");
			sic.add("costCenter.displayName");
			sic.add("costCenter.longNumber");//displayName
			try {
				project = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projId),sic);
			} catch (Exception e) {
				// @AbortException
				e.printStackTrace();
				Logger.error(e, e.getMessage());
			}
			
			orgInfo = project.getCostCenter();
		}
    	
		return orgInfo;
	}
	
	/**
	 * 从成本中心与工程项目的对应关系中获得成本中心对应的工程项目ID
	 * @param costCenterId <b>成本中心ID</b>
	 * @return <b>null</b>或者 <b>工程项目ID</b>
	 * @author by sxhong 2008-09-26 15:09:02
	 */
	public static String getCurProjectId(Context ctx,String costCenterId) throws BOSException{
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcurprojectid from t_fdc_projectwithcostcenterou where fcostcenterouid=?");
		builder.addParam(costCenterId);
		IRowSet rowSet=builder.executeQuery();
		try{
			if(rowSet.next()){
				return rowSet.getString("fcurprojectid");
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		return null;
	}
	
	/**
	 * 描述：取工程项目财务组织ID（取财务级参数用）
	 * @param ctx
	 * @param projectId
	 * @return
	 * @throws BOSException
	 */
	public static String getCurCompanyId(Context ctx, String projectId) throws BOSException{
		String companyId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select ffullorgunit from t_fdc_curproject where fid=? ");
		builder.addParam(projectId);
		IRowSet rowSet=builder.executeQuery();
		try{
			if(rowSet.next()){
				companyId = rowSet.getString("ffullorgunit");
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		if(companyId==null){
			if(ctx!=null){
				companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			}else{
				companyId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
			}
		}
		return companyId;
	}
	
	/**
	 * 描述：通过成本中心取工程项目信息
	 * 
	 * @param ctx
	 * @param costCenterId
	 * @return
	 * @throws BOSException
	 */
	public static Map getPrjInfosByCostCenter(Context ctx,String costCenterId) throws BOSException{
		Map retMap = new HashMap();
		Set prjIdSet = new HashSet();
		CurProjectCollection projects = new CurProjectCollection();
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select prj.fid,prj.fdisplayname_l2,prj.ffullorgunit from t_fdc_curproject prj \n");
		builder.appendSql("inner join t_org_baseunit unit on unit.fid=prj.ffullorgunit \n");
		builder.appendSql("where (prj.fcostcenterid =? or prj.ffullorgunit=?) and prj.fisleaf =1 order by prj.flongnumber \n");
		builder.addParam(costCenterId);
		//成本中心下也可以有工程项目?
		builder.addParam(costCenterId);
		IRowSet rowSet=builder.executeQuery();
		CurProjectInfo info = null;
		FullOrgUnitInfo fullOrgUnit = null;
		try{
			while(rowSet.next()){
				info = new CurProjectInfo();
				fullOrgUnit = new FullOrgUnitInfo();
				String id = rowSet.getString("fid");
				String name=rowSet.getString("fdisplayname_l2");
				String orgId = rowSet.getString("ffullorgunit");
				info.setId(BOSUuid.read(id));
				info.setDisplayName(name);
				fullOrgUnit.setId(BOSUuid.read(orgId));
				info.setFullOrgUnit(fullOrgUnit);
				
				prjIdSet.add(id);
				projects.add(info);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		retMap.put("prjIdSet", prjIdSet);
		retMap.put("projects", projects);
		return retMap;
	}
	public static BigDecimal getApportionValue(String objectId, String appId, ProjectStageEnum projectStage)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		SelectorItemCollection selector = view.getSelector();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("entries.*"));
		filter.getFilterItems()
				.add(new FilterItemInfo("projOrOrgId", objectId));
		filter.getFilterItems().add(new FilterItemInfo("productType", null));
		filter.getFilterItems().add(new FilterItemInfo("projectStage", projectStage.getValue()));
		filter.getFilterItems().add(
				new FilterItemInfo("isLatestVer", Boolean.TRUE));
		ProjectIndexDataCollection projectIndexDataCollection = ProjectIndexDataFactory
				.getRemoteInstance().getProjectIndexDataCollection(view);
		ProjectIndexDataInfo indexData = projectIndexDataCollection.get(0);
		if(indexData==null){
			return FDCHelper.ZERO;
		}
		ProjectIndexDataEntryCollection entries = indexData.getEntries();
		BigDecimal appData = FDCHelper.ZERO;
		for (int i = 0; i < entries.size(); i++) {
			ProjectIndexDataEntryInfo entry = entries.get(i);
			if (entry.getApportionType().getId().toString().equals(appId)) {
				if (entry.getIndexValue() != null) {
					appData = entry.getIndexValue();
					break;
				}
			}

		}
		return appData.setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	// public static BigDecimal getProjectApportionValue(String[] projectIds,
	// String apportionId) throws BOSException, SQLException,
	// EASBizException {
	// if (projectIds == null || projectIds.length == 0) {
	// return FDCHelper.ZERO;
	// }
	// ApportionTypeInfo typeInfo = ApportionTypeFactory.getRemoteInstance()
	// .getApportionTypeInfo(
	// new ObjectUuidPK(BOSUuid.read(apportionId)));
	// if (!typeInfo.isIsEnabled()) {
	// return FDCHelper.ZERO;
	// }
	// StringBuffer sql = new StringBuffer();
	// sql
	// .append("select FValue from T_FDC_ProjectIndexDataCurProjCostEntries
	// where FCurProject in ('");
	// sql.append(projectIds[0] + "'");
	// for (int i = 1; i < projectIds.length; i++) {
	// sql.append(",'" + projectIds[i] + "'");
	// }
	// sql.append(") and FApportionType='" + apportionId + "'");
	// IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql.toString())
	// .executeSQL();
	// BigDecimal result = FDCHelper.ZERO;
	// while (rs.next()) {
	// if (rs.getBigDecimal("FValue") != null) {
	// result = result.add(rs.getBigDecimal("FValue"));
	// }
	// }
	// return result.setScale(2, BigDecimal.ROUND_HALF_UP);
	// }

	/**
	 *
	 * 描述：获得当前日期（已截断时间）
	 *
	 * @return
	 * @author:liupd 创建时间：2006-9-25
	 *               <p>
	 */
	public static Date getCurrentDate() {
		return DateTimeUtils.truncateDate(new Date());
	}

	/**
	 * 给指定table设定金额格式，并且右对齐
	 * 
	 * @param table
	 *            表
	 * @param columnName
	 *            列名
	 */
	public static void formatTableNumber(KDTable table, String columnName) {
		IColumn column = table.getColumn(columnName);
		if (column != null) {
			column.getStyleAttributes().setNumberFormat(strDataFormat);
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
		}
	}

	public static void formatTableNumber(KDTable table, int from,int to) {
		for(int i=from;i<=to;i++){
			if(i>=table.getColumnCount()){
				break;
			}
			table.getColumn(i).getStyleAttributes().setNumberFormat(strDataFormat);
			table.getColumn(i).getStyleAttributes().setHorizontalAlign(
			HorizontalAlignment.RIGHT); // 右对齐
		}
	}
	
	/**
	 * 给指定table设定金额格式，并且右对齐
	 *
	 * @param table
	 * @param columnName
	 *            列索引
	 */
	public static void formatTableNumber(KDTable table, int columnIndex,
			String format) {
		table.getColumn(columnIndex).getStyleAttributes().setNumberFormat(
				format);
		table.getColumn(columnIndex).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT); // 右对齐
	}

	/**
	 * 给指定table设定金额格式，并且右对齐
	 * @param table
	 * @param columnIndex
	 */
	public static void formatTableNumber(KDTable table, int[] columnIndex) {
		for (int i = 0; i < columnIndex.length; i++) {
			int j = columnIndex[i];
			table.getColumn(j).getStyleAttributes().setNumberFormat(strDataFormat);
			table.getColumn(j).getStyleAttributes().setHorizontalAlign(
			HorizontalAlignment.RIGHT); // 右对齐
		}
	}

	/**
	 * 给指定table设定金额格式，并且右对齐
	 *
	 * @param table
	 * @param columnNames
	 *            列名数组
	 */
	public static void formatTableNumber(KDTable table, String[] columnNames) {
		for (int i = 0; i < columnNames.length; i++)
			formatTableNumber(table, columnNames[i]);
	}

	/**
	 * 给指定table设定金额格式，并且右对齐
	 *
	 * @param table
	 * @param columnNames
	 *            列名数组
	 */
	public static void formatTableNumber(KDTable table, String[] columnNames,
			String format) {
		for (int i = 0; i < columnNames.length; i++)
			formatTableNumber(table, columnNames[i], format);
	}

	/**
	 * 给指定table设定金额格式，并且右对齐
	 *
	 * @param table
	 * @param columnNames
	 *            列名数组
	 */
	public static void formatTableNumber(KDTable table, String columnName,
			String format) {
		table.getColumn(columnName).getStyleAttributes()
				.setNumberFormat(format);
		table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT); // 右对齐
	}

	public static void formatTable(KDTable table, String columnName,
			String format) {
		table.getColumn(columnName).getStyleAttributes()
				.setNumberFormat(format);
	}

	public static void formatTableDate(KDTable table, String columnName) {
		formatTable(table,columnName,KDTABLE_DATE_FMT);
	}

	public static void formatTableDateTime(KDTable table, String columnName) {
		formatTable(table,columnName,KDTABLE_DATETIME_FMT);
	}

//	/**
//	 * 描述：对控件设置精度
//	 *
//	 * @throws Exception
//	 *
//	 */
//	public static void setPrecision(KDFormattedTextField formattedTextField, int scale){
//		formattedTextField.setPrecision(scale);
//	}
	
	/**
	 * 描述：对控件设置精度,最大长度等
	 *
	 * @throws Exception
	 *
	 */
	public static void setComponentPrecision(Object[] c, int scale)
			throws Exception {
		Object kdc;
		for (int i = 0, size = c.length; i < size; i++) {
			kdc = c[i];
			JComponent comp = null;
			if (kdc instanceof KDLabelContainer) {
				comp = ((KDLabelContainer) kdc).getBoundEditor();
			} else {
				comp = (JComponent) kdc;
			}

			if (comp instanceof KDPanel || comp instanceof KDTabbedPane) {
				setComponentPrecision(((JComponent) kdc).getComponents(), scale);
			}

			if (comp instanceof KDTextField) {
				((KDTextField) comp).setMaxLength(MAX_LENGTH_TXT);
			}

			if (comp instanceof BasicNumberTextField) {
				((BasicNumberTextField) comp)
						.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
				((BasicNumberTextField) comp).setPrecision(scale);
				((BasicNumberTextField) comp)
						.setHorizontalAlignment(BasicNumberTextField.RIGHT);
				((BasicNumberTextField) comp).setRemoveingZeroInDispaly(false);
				((BasicNumberTextField) comp).setMaximumNumber(MAX_VALUE);
				((BasicNumberTextField) comp).setMinimumNumber(MIN_VALUE);
			}
			if (comp instanceof KDFormattedTextField) {
				((KDFormattedTextField) comp)
						.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
				((KDFormattedTextField) comp).setPrecision(scale);
				((KDFormattedTextField) comp)
						.setHorizontalAlignment(KDFormattedTextField.RIGHT);
				((KDFormattedTextField) comp).setRemoveingZeroInDispaly(false);
				((KDFormattedTextField) comp).setMaximumValue(MAX_VALUE);
				((KDFormattedTextField) comp).setMinimumValue(MIN_VALUE);
				((KDFormattedTextField) comp)
						.setHorizontalAlignment(JTextField.RIGHT);
			}
			if (comp instanceof IColumn) {
				KDFormattedTextField formattedTextField = new KDFormattedTextField(
						KDFormattedTextField.BIGDECIMAL_TYPE);
				formattedTextField.setPrecision(scale);
				formattedTextField.setRemoveingZeroInDispaly(false);
				formattedTextField.setRemoveingZeroInEdit(false);
				formattedTextField.setNegatived(false);
				formattedTextField.setMaximumValue(MAX_VALUE);
				formattedTextField.setMinimumValue(MIN_VALUE);
				ICellEditor numberEditor = new KDTDefaultCellEditor(
						formattedTextField);
				((IColumn) comp).getStyleAttributes().setNumberFormat(
						getDecimalFormat(scale));
				((IColumn) comp).getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT); // 右对齐
				((IColumn) comp).setEditor(numberEditor);
			}
			if (comp instanceof ICell) {
				KDFormattedTextField formattedTextField = new KDFormattedTextField(
						KDFormattedTextField.BIGDECIMAL_TYPE);
				formattedTextField.setPrecision(scale);
				formattedTextField.setRemoveingZeroInDispaly(false);
				formattedTextField.setRemoveingZeroInEdit(false);
				formattedTextField.setNegatived(false);
				formattedTextField.setMaximumValue(MAX_VALUE);
				formattedTextField.setMinimumValue(MIN_VALUE);
				ICellEditor numberEditor = new KDTDefaultCellEditor(
						formattedTextField);
				((ICell) comp).getStyleAttributes().setNumberFormat(
						getDecimalFormat(scale));
				((ICell) comp).getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT); // 右对齐
				((ICell) comp).setEditor(numberEditor);
			}
		}
	}

	/**
	 * 描述：取得小数位的格式
	 *
	 * @return
	 * @author
	 */
	public static String getDecimalFormat(int quantityScale) {
		StringBuffer sbff = new StringBuffer();
		StringBuffer sbffN = new StringBuffer();
		if (quantityScale == 0) {
			sbff.append("#,##0");
			sbffN.append("-#,##0");

		} else {
			sbff.append("#,##0.");
			sbffN.append("-#,##0.");
		}
		for (int i = 0; i < quantityScale; i++) {
			sbff.append("0");
			sbffN.append("0");
		}
		return sbff.append(";").append(sbffN).toString();
	}

	/** String 分割符 */
	public final static String SEPARATOR = ",";

	/**
	 *
	 * 描述：将String数组转换为String，格式为"e1,e2,e3"
	 *
	 * @param strArray
	 * @return
	 * @author:liupd 创建时间：2006-10-18
	 *               <p>
	 */
	public static String strArrayToString(String[] strArray) {
		if (isEmpty(strArray))
			return null;
		StringBuffer rtnValue = new StringBuffer();

		for (int i = 0; i < strArray.length - 1; i++) {
			rtnValue.append(strArray[i]);
			rtnValue.append(SEPARATOR);
		}

		rtnValue.append(strArray[strArray.length - 1]);

		return rtnValue.toString();
	}

	/**
	 *
	 * 描述：将String转换为String数组，String的格式要求是"e1,e2,e3"
	 *
	 * @param str
	 * @return
	 * @author:liupd 创建时间：2006-10-18
	 *               <p>
	 */
	public static String[] stringToStrArray(String str) {
		if (str == null || str.length() == 0)
			return null;
		return str.split(SEPARATOR);
	}

	/**
	 *
	 * 描述：日期转换为String，忽略时间，格式为: yyyy,mm,dd
	 * 注意：转换后，你看到的月比实际的月小1，如实际日期是10月，则转换为是9，这是由于Calendar类1月是从0开始的
	 *
	 * @param date
	 * @return
	 * @author:liupd 创建时间：2006-10-18
	 *               <p>
	 */
	public static String dateToString(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);

		return year + SEPARATOR + month + SEPARATOR + day;

	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	public static boolean isEmpty(Object obj) {
		if(obj instanceof String){
			return isEmpty((String)obj);
		}else if(obj instanceof Map){
			Map map=(Map)obj;
			return map.size()<=0;
		}else if(obj instanceof Collection){
			Collection c=(Collection)obj;
			return c.size()<=0;
		}else{
			return obj==null?true:isEmpty(obj.toString());
		}
	}

	/**
	 * 描述：是否为空
	 * 
	 * @param obj
	 *            对象
	 * @param isAllowZero
	 *            (数值类型)是否允许为0
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-28
	 */
	public static boolean isEmpty(Object obj, boolean isAllowZero) {
		if (obj instanceof String) {
			return isEmpty((String) obj);
		} else if (obj instanceof Number) {
			if (isAllowZero)
				return obj == null;
			Number num = (Number) obj;
			BigDecimal tempNum = new BigDecimal(num.toString());

			// 数字0值作empty处理
			return (0 == FDCConstants.ZERO.compareTo(tempNum));
		} else if (obj instanceof Map) {
			Map map = (Map) obj;
			return map.size() <= 0;
		} else if (obj instanceof Collection) {
			Collection c = (Collection) obj;
			return c.size() <= 0;
		} else {
			return obj == null ? true : isEmpty(obj.toString());
		}
	}
	
	/**
	 *
	 * 描述：String转换为日期，String格式要求为：yyyy,mm,dd，时间清0
	 *
	 * @param str
	 * @return
	 * @author:liupd 创建时间：2006-10-18
	 *               <p>
	 */
	public static Date strToDate(String str) {
		if (isEmpty(str))
			return null;

		String[] strArray = str.split(SEPARATOR);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(strArray[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(strArray[1]));
		cal.set(Calendar.DATE, Integer.parseInt(strArray[2]));

		return DateTimeUtils.truncateDate(cal.getTime());

	}

	/**
	 * @param cboCurrency
	 * @param string
	 * @return
	 */
	public static Object getCboAttribute(KDComboBox cbo, String string) {
		ObjectBaseInfo info = (ObjectBaseInfo) cbo.getSelectedItem();
		if (info == null) {
			return null;
		}
		return info.get(string);
	}

	/**
	 * @param cbo
	 * @return
	 */
	public static String getCboId(KDComboBox cbo) {
		ObjectBaseInfo info = (ObjectBaseInfo) cbo.getSelectedItem();
		if (info == null) {
			return null;
		}
		return info.getId().toString();

	}

	/**
	 * @param cbo
	 * @return
	 */
	public static int getCboInt(KDComboBox cbo) {
		Object obj = cbo.getSelectedItem();
		if (obj != null && obj instanceof IntEnum) {
			IntEnum new_name = (IntEnum) obj;

			IntEnum enumObj = (IntEnum) new_name;
			return enumObj.getValue();
		}
		return 0;
	}

	/**
	 * @param template
	 * @param string
	 * @return
	 */
	public static Object getF7Attribute(KDBizPromptBox box, String string) {
		ObjectBaseInfo data = (ObjectBaseInfo) box.getData();
		if (data == null) {
			return null;
		}
		Object id = data.get(string);
		return id;
	}

	public static String getF7Id(KDBizPromptBox box) {

		ObjectBaseInfo data = (ObjectBaseInfo) box.getData();
		if (data == null) {
			return null;
		}
		String id = data.getId().toString();
		return id;

	}

	public static BigDecimal getValue(BigDecimal big) {
		return null;
	}

	public static String[] getF7Ids(KDBizPromptBox box) {

		Object value = box.getData();
		return getIds(value);

	}

	public static Set getF7IdSet(KDBizPromptBox box) {
		Object value = box.getData();

		Object[] data;
		if (value instanceof Object[]) {
			data = (Object[]) value;

		} else {
			data = new Object[] { value };
		}
		if (FMHelper.isEmpty(data)) {
			return null;
		}
		Set result = new HashSet();
		for (int i = 0; i < data.length; i++) {
			CoreBaseInfo info = (CoreBaseInfo) data[i];
			result.add(info.getId().toString());
		}
		return result;
	}

	public static String[] getIds(Object value) {
		Object[] data;
		if (value instanceof Object[]) {
			data = (Object[]) value;

		} else {
			data = new Object[] { value };
		}
		if (FMHelper.isEmpty(data)) {
			return null;
		}
		String[] result = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			CoreBaseInfo info = (CoreBaseInfo) data[i];
			result[i] = info.getId().toString();
		}
		return result;
	}

	public static Object[] getF7Data(KDBizPromptBox box) {

		Object value = box.getData();
		Object[] data;
		if (value instanceof Object[]) {
			data = (Object[]) value;

		} else {
			data = new Object[] { value };
		}
		if (FMHelper.isEmpty(data)) {
			return null;
		}
		return data;

	}

	/**
	 * 根据propertyName找到item
	 *
	 * @param filters
	 * @param filterName
	 * @return
	 */
	public static FilterItemInfo getFilterItem(FilterItemCollection filters,
			String filterName) {
		for (int i = 0; i < filters.size(); i++) {
			if (filters.get(i).getPropertyName().equals(filterName)) {
				return filters.get(i);
			}
			;
		}

		return null;
	}

	/**
	 * 返回filterItem的值
	 *
	 * @param filters
	 * @param filterName
	 * @return
	 */
	public static Object getFilterValue(FilterItemCollection filters,
			String filterName) {
		for (int i = 0; i < filters.size(); i++) {
			if (filters.get(i).getPropertyName().equals(filterName)) {
				return filters.get(i).getCompareValue();
			}
			;
		}

		return null;
	}

	/**
	 * 获取对象的Id, 如果是AbstractObjectValue 实例,返回id的字符串值; 否则返回对象的字符串值
	 *
	 * @param info
	 * @return
	 */
	static public String getId(Object info) {
		if (info == null) {
			return null;
		}

		if (AbstractObjectValue.class.isInstance(info)) {
			AbstractObjectValue objInfo = (AbstractObjectValue) info;

			return objInfo.getBOSUuid("id").toString();
		}

		return info.toString();
	}

	static public Set getIds(Object[] info) {
		if (isEmpty(info)) {
			return new HashSet();
		}
		Set set = new HashSet(info.length);
		for (int i = 0; i < info.length; i++) {
			set.add(getId(info[i]));

		}
		return set;
	}

	/**
	 * 根据propertyName找到item
	 *
	 * @param sorters
	 * @param sorterName
	 * @return
	 */
	public static SorterItemInfo getSorterItem(SorterItemCollection sorters,
			String sorterName) {
		for (int i = 0; i < sorters.size(); i++) {
			if (sorters.get(i).getPropertyName().equals(sorterName)) {
				return sorters.get(i);
			}
			;
		}

		return null;
	}

	public static Integer getSpiIntValue(KDSpinner spi) {
		Integer value = (Integer) spi.getValue();

		return value;
	}

	/**
	 * @param spiBeginMonth
	 * @return
	 */
	public static int getSpiValue(KDSpinner spi) {
		Integer value = (Integer) spi.getValue();

		return value.intValue();
	}

	/**
	 * 返回Uuid的值
	 *
	 * @param uuid
	 * @return
	 */
	static public String getString(Object uuid) {
		if (uuid != null) {
			return uuid.toString();
		} else {
			return null;
		}
	}

	/**
	 * 返回枚举对象的值
	 *
	 * @param enumObj
	 * @return
	 */
	static public int getValue(IntEnum enumObj) {
		if (enumObj != null) {
			return enumObj.getValue();
		} else {
			return 0;
		}
	}

	/**
	 * 根据idCollection拼出in子句
	 *
	 * @author yangzk
	 */
	public static String idListToInClause(Collection idCollection) {
		StringBuffer sb = new StringBuffer();
		if (idCollection.size() == 0) {
			throw new IllegalArgumentException("idCollection.size cannot be 0!");
		} else if (idCollection.size() == 1) {
			sb.append("'").append(idCollection.toArray()[0]).append("'");
			return sb.toString();
		} else {
			Iterator it = idCollection.iterator();
			for (int i = 0, n = idCollection.size(); i < n - 1; i++) {
				sb.append("'").append(it.next()).append("'").append(", ");
			}
			sb.append("'").append(it.next()).append("'");
			return sb.toString();
		}
	}

	/**
	 *
	 * 描述：从编码规则获取编码,如果没有编码规则,则返回null
	 *
	 * @param info
	 *            当前单据的Info
	 * @param orgUnitId
	 *            当前组织id
	 * @return
	 * @author:liupd 创建时间：2006-9-29
	 *               <p>
	 * @throws Exception
	 */
	public static String getNumberByCodingRule(Context ctx, IObjectValue info,
			String orgUnitId) throws Exception {

		ICodingRuleManager iCodingRuleManager = null;
		if (ctx == null) {
			iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		} else {
			iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		}

		if (iCodingRuleManager.isExist(info, orgUnitId)) {
			return iCodingRuleManager.getNumber(info, orgUnitId);
		}

		return null;
	}

	public static PeriodInfo getGLCurCompanyCurPeriod(Context ctx){
		CompanyOrgUnitInfo curC = ContextUtil.getCurrentFIUnit(ctx);
		PeriodInfo curP = null;
		try {
			curP = SystemStatusCtrolUtils.getCurrentPeriod(ctx,	SystemEnum.GENERALLEDGER, curC);
		} catch (Exception e) {
			// @AbortException
			ExceptionHandler.handle(e);
		}

		return curP;
	}

	/**
	 * 获取当前财务组织的本位币
	 *
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	public static CurrencyInfo getBaseCurrency(Context ctx) throws BOSException, EASBizException {

		CompanyOrgUnitInfo currentFIUnit = null;

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("baseCurrency.id");

		ICompanyOrgUnit iCompanyOrgUnit = null;
		if (ctx == null) {
			iCompanyOrgUnit = CompanyOrgUnitFactory.getRemoteInstance();
			currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		} else {
			iCompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
			currentFIUnit = ContextUtil.getCurrentFIUnit(ctx);
		}

		BOSUuid id = currentFIUnit.getId();
		currentFIUnit = iCompanyOrgUnit.getCompanyOrgUnitInfo(new ObjectUuidPK(
				id), selector);

		return currentFIUnit.getBaseCurrency();
	}

 	/**
	 * 生成当前日期的字符串,格式为:年月日时分秒,如:20061229111010
	 * @return
	 */
	public static String getTimestampString() {
		String curTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

		curTime = curTime.replaceAll("-", "");
		curTime = curTime.replaceAll(":", "");
		curTime = curTime.replaceAll(" ", "");

		return curTime;
	}

	public static Set list2Set(List list) {

		if (list == null || list.size() < 1) {
			return new HashSet();
		}
		return new HashSet(list);
	}

	public static IObjectPK[] idListToPKArray(List ids) {
		IObjectPK[] pkArray = new ObjectStringPK[ids.size()];

    	for (int i = 0; i < ids.size(); i++) {
    		String element = (String) ids.get(i);
    		pkArray[i] = new ObjectStringPK(element);
		}
		return pkArray;
	}

	public static java.sql.Date getSqlDate() {
		return new java.sql.Date(getCurrentDate().getTime());
	}


	/**得到当前组织的实体财务组织
	 * @param ctx
	 * @param org
	 * @return
	 * @throws Exception
	 * by sxhong
	 */
	public static CompanyOrgUnitInfo getFIOrgUnit(Context ctx,FullOrgUnitInfo org) throws Exception{
		if(org==null||org.getId()==null){
			return null;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select top 1 fid,fname_l2,fnumber,flongNumber,flevel,fiscompanyorgunit  from T_ORG_BaseUnit t1,"	);
		builder.appendSql("(select flongnumber as longnumber from T_ORG_BaseUnit where fid=? )t2 ");
		builder.appendSql("where charindex(flongNumber||'!',t2.longNumber||'!')>0 and fiscompanyorgunit=1 order by flevel desc;");
		builder.addParam(org.getId().toString());
		IRowSet rowSet=null;
		if(ctx==null){
			rowSet=builder.executeQuery();
		}else{
			rowSet=builder.executeQuery(ctx);
		}
		if(rowSet.size()==1){
			rowSet.next();
			CompanyOrgUnitInfo info=new CompanyOrgUnitInfo();
			info.setId(BOSUuid.read(rowSet.getString("fid")));
			info.setName(rowSet.getString("fname_l2"));
			info.setNumber(rowSet.getString("fnumber"));
			info.setLongNumber(rowSet.getString("flongNumber"));
			info.setLevel(rowSet.getInt("flevel"));
			info.setIsCompanyOrgUnit(rowSet.getBoolean("fiscompanyorgunit"));
			return info;
		}else{
			return null;
		}
	}

	public static void sortObjectCollection(AbstractObjectCollection c,Comparator comparator){
		if(c==null||c.size()<=1)
			return;
		Object[] objs=c.toArray();
		Arrays.sort(objs, comparator);
		c.clear();
		for(int i=0;i<objs.length;i++){
			c.addObject((IObjectValue)objs[i]);
		}
	}

	public static void sortObjectCollection(AbstractObjectCollection c,String key){
		final String k=key;
		Comparator comparator=new Comparator(){
			public int compare(Object o1, Object o2) {
				if(o1==null&&o2==null){
					return 0;
				}
				if(o1!=null&&o2==null){
					return 1;
				}
				if(o1==null&&o2!=null){
					return -1;
				}

				IObjectValue info1=(IObjectValue)o1;
				IObjectValue info2=(IObjectValue)o2;

				o1 = info1.getString(k);
				o2 = info2.getString(k);

				if(o1==null&&o2==null){
					return 0;
				}
				if(o1!=null&&o2==null){
					return 1;
				}
				if(o1==null&&o2!=null){
					return -1;
				}
				return ((String)o1).compareTo((String)o2);
			}
		};
		sortObjectCollection(c,comparator);
	}

    //存在botp关系
    public static boolean _existBotRelation(Context ctx,String srcId,BOSObjectType type) throws BOSException{

    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("srcObjectID",srcId));
		filter.getFilterItems().add(new FilterItemInfo("srcEntityID",type.toString()));
		filter.getFilterItems().add(new FilterItemInfo("destEntityID","40284E81"));

		if(ctx!=null){
			return BOTRelationFactory.getLocalInstance(ctx).getCollection(view).size()>0;
		}else{
			return BOTRelationFactory.getRemoteInstance().getCollection(view).size()>0;
		}

		//return false;
    }
    /**
	 * 自动做数据正确性的校验,及垃圾数据的定期后台清除
	 *  by sxhong 2008-06-24 14:24:21
	 * @param ctx
	 */
    public static synchronized void autoClear(Context ctx) throws BOSException {
    	synchronized(clearList){
    		for(int i=0;i<clearList.size();i++){
    			try {
    				((IFDCAction)clearList.get(i)).actionPerformed(ctx);
    			} catch (Exception e) {
    				throw new BOSException(e);
    			}
    		}
    	}
    }
    private static List clearList=new ArrayList();
    public static void addClearAction(IFDCAction action){
    	synchronized (clearList) {
    		clearList.add(action);
		}
    }
    /*****
     * 集合转IObjectPK[]
     * @param c 集合中的对象，可以Stirng，BillInfo
     * @return
     */
    public static IObjectPK[] CollectionToArrayPK(Collection c){
    	if(c==null || c.size()==0)
    		return new IObjectPK[0];
    	IObjectPK[] pks = new IObjectPK[c.size()];
    	int i=0;
    	for(Iterator it=c.iterator();it.hasNext();){
    		Object obj = it.next();
    		if(obj instanceof String)
    			pks[i++] = new ObjectUuidPK((String)obj);
    		else if(obj instanceof CoreBillBaseInfo)
    			pks[i++] = new ObjectUuidPK(((CoreBillBaseInfo)obj).getId());
    	}
    	return pks;
    }
    

	/**
	 * List转换Set,如果list为null,返回new HashSet()
	 * */
	public static Set getSetByList(List list){
		Set set = new HashSet();
		if (list == null) {
			return set;
		}
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i));
		}
		return set;
	}

    
	/**
	 * 返回相同主键的map的差值,map里面的值为数值
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static Map getDifMap(Map map1,Map map2){
		Map difMap=new HashMap();
		Set keySet=new HashSet();
		keySet.addAll(map1.keySet());
		keySet.addAll(map2.keySet());
		for(Iterator iter=keySet.iterator();iter.hasNext();){
			String key=(String)iter.next();
			difMap.put(key, FDCNumberHelper.subtract(map1.get(key), map2.get(key)));
		}
		return difMap;
	}
	
    /**   
	 * 得到二个日期间的间隔天数   
	 */
	public static String getTwoDay(String sj1, String sj2)
	{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try
		{
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e)
		{
			// @AbortException
			return "";
		}
		return day + "";
	}
	  /**   
	 * 得到二个日期间的间隔天数   
	 */
	public static int getTwoDay(Date beginDate, Date endDate)
	{
		Date bd = DateTimeUtils.truncateDate(beginDate);
		Date ed = DateTimeUtils.truncateDate(endDate);
		int day = (int) ((ed.getTime() - bd.getTime()) / (24 * 60 * 60 * 1000));
		return day;
	}
	
	/**
	 * 房地产模块调试开关项
	 * @return
	 */
	public static boolean isFDCDebug(){
		String p = System.getProperty("DevFDC");
		if(p!=null&&p.equals("fdc")){
			return true;
		}
		return false;
	}
	
	/**
     * 获得房地产系统参数值, 适用于 <b>布尔</b> 类型的参数 
     * @param ctx
     * @param companyID 传入组织ID，如果是集团参数，传入null即可。非集团参数用SysContext.getSysContext().getCurrentOrgUnit().getId().toString()
     * @param paramNumber 参数编码
     * @return 参数值
     * @throws EASBizException
     * @throws BOSException
     * @author owen_wen 2010-11-30
     */
    public static boolean getBooleanValue4FDCParamByKey(Context ctx, String companyID,String paramNumber) throws EASBizException, BOSException{
		IObjectPK comPK = null;
		if (companyID != null) {
			comPK = new ObjectUuidPK(BOSUuid.read(companyID));
		}
		
		IParamControl pc;
		 if(ctx!=null)
	        pc = ParamControlFactory.getLocalInstance(ctx);
	     else
	        pc= ParamControlFactory.getRemoteInstance();
		String fdcParamValue = pc.getParamValue(comPK, paramNumber);
		
		if (fdcParamValue != null && !"true".equalsIgnoreCase(fdcParamValue) && !"false".equalsIgnoreCase(fdcParamValue)) {
			throw new IllegalArgumentException("不是布尔类型的参数，请调用getStringValue4FDCParamByKey()取参数！");
		}
		return Boolean.valueOf(fdcParamValue).booleanValue();
    }

	/**
	 * 获得房地产系统参数值, 适用于 <b>String</b> 类型的参数 
	 * @param ctx
	 * @param companyID 传入组织ID，如果是集团参数，传入null即可。非集团参数用SysContext.getSysContext().getCurrentOrgUnit().getId().toString()
	 * @param paramNumber 参数编码
	 * @return 参数值
	 * @author owen_wen 2013-4-15
	 * @see com.kingdee.eas.fdc.basedata.FDCHelper.getBooleanValue4FDCParamByKey(Context ctx, String companyID,String paramNumber)
	 */
	public static String getStringValue4FDCParamByKey(Context ctx, String companyID, String paramNumber) throws EASBizException,
			BOSException {
		IObjectPK comPK = null;
		if (companyID != null) {
			comPK = new ObjectUuidPK(BOSUuid.read(companyID));
		}

		IParamControl pc;
		if (ctx != null)
			pc = ParamControlFactory.getLocalInstance(ctx);
		else
			pc = ParamControlFactory.getRemoteInstance();

		return pc.getParamValue(comPK, paramNumber);
	}

	/**
	 * 取任意组织的所有上级组织id集合
	 * 
	 * @param ctx
	 * @param curLongNum
	 * @return
	 * @throws BOSException
	 */
	public static Set getUpOrgIDs(Context ctx, String curLongNum)
			throws BOSException {
		if (!isEmpty(curLongNum)) {
			Set longNums = new HashSet();
			longNums.add(curLongNum);
			while (curLongNum.indexOf("!") > -1) {
				curLongNum = curLongNum.substring(0, curLongNum.lastIndexOf("!"));
				longNums.add(curLongNum);
			}
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("id");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("longNumber", longNums, CompareType.INCLUDE));
			view.setFilter(filter);
			FullOrgUnitCollection col = null;
			if (ctx == null) {
				col = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
			} else {
				col = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(view);
			}
			if (col != null && col.size() > 0) {
				Set ids = new HashSet(col.size());
				for (int i = 0; i < col.size(); i++) {
					ids.add(col.get(i).getId().toString());
				}
				return ids;
			}
		}
		return null;
	}
}
