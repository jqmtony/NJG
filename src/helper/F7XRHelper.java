package com.kingdee.eas.xr.helper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.PositionPromptBox2;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.basedata.org.client.f7.OrgUnitFilterInfoProducer;
import com.kingdee.eas.basedata.person.client.PersonF7Filter;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
public class F7XRHelper {

	public F7XRHelper() {
		super();
	}
	/**
	 * 获取F7属性值
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
	/**
	 * 获取F7ID
	 * @param template
	 * @param string
	 * @return
	 */
	public static String getF7Id(KDBizPromptBox box) {

		ObjectBaseInfo data = (ObjectBaseInfo) box.getData();
		if (data == null) {
			return null;
		}
		String id = data.getId().toString();
		return id;

	}
	/**
	 * 获取F7 ID数组
	 * @param template
	 * @param string
	 * @return
	 */
	public static String[] getF7Ids(KDBizPromptBox box) {

		Object value = box.getData();
		return getIds(value);

	}
	/**
	 * 获取F7 ID集合
	 * @param template
	 * @param string
	 * @return
	 */
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
	/**
	 * 获取F7 对象数组
	 * @param template
	 * @param string
	 * @return
	 */
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
	 * 按组织类型过滤orgF7
	 */
    public static void setBizOrgF7ByType(KDBizPromptBox f7, OrgType orgType)
    {
        OrgUnitFilterInfoProducer oufip = (OrgUnitFilterInfoProducer)FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
        oufip.getModel().setIsCUFilter(true);
        f7.setFilterInfoProducer(oufip);
    }
	/**
	 * 行政组织机构F7框工具类
	 * 注意：设置F7选择控件为 queryInfo: com.kingdee.eas.sem.mp.app.AdminOrgUnitF7
	 * 
	 * @param uio 页面的UI实体
	 * @param bizPrompt F7选择框控件
	 */
	public static void adminOrgUnitF7(IUIObject uio, KDBizPromptBox bizPrompt) {
		
        AdminF7 adminF7 = new AdminF7(uio);
		adminF7.disablePerm();
		adminF7.showCheckBoxOfShowingAllOUs();
		bizPrompt.setSelector(adminF7);
		bizPrompt.setFilterInfoProducer(FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(adminF7));
		
        bizPrompt.setQueryInfo("com.kingdee.eas.sem.mp.app.AdminOrgUnitF7");
        bizPrompt.setCommitFormat("$number$");
        bizPrompt.setEditFormat("$number$");
        bizPrompt.setDisplayFormat("$name$");
	}
	
	/**
	 * 给table框的某个列加入行政组织 F7框工具类
	 * 
	 * @param uio 页面的UI实体
	 * @param table 表名
	 * @param colName 字段名
	 */
	public static void tableAdminOrgUnitF7(IUIObject uio, KDTable table, String colName) {
		KDBizPromptBox kDBizPromptBoxUser = new KDBizPromptBox();
		kDBizPromptBoxUser.setEditable(true);
		kDBizPromptBoxUser.setQueryInfo("com.kingdee.eas.sem.mp.app.AdminOrgUnitF7");
		kDBizPromptBoxUser.setDisplayFormat("$number$");
		kDBizPromptBoxUser.setEditFormat("$number$");
		kDBizPromptBoxUser.setCommitFormat("$number$");
		AdminF7 adminF7 = new AdminF7(uio);
		adminF7.disablePerm();
		adminF7.showCheckBoxOfShowingAllOUs();
		kDBizPromptBoxUser.setSelector(adminF7);
		FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(adminF7);
		table.getColumn(colName).setEditor(new KDTDefaultCellEditor(kDBizPromptBoxUser));
		//	kdtable 中的f7的显示格式 
		ObjectValueRender nameRender = new ObjectValueRender();
		nameRender.setFormat(new BizDataFormat("$name$"));
		table.getColumn(colName).setRenderer(nameRender);
	}
	
	
	/**
	 * 人员F7框工具类
	 * 
	 * @param uio 页面的UI实体
	 * @param bizPrompt
	 */
	public static void personF7(IUIObject uio, KDBizPromptBox bizPrompt) {
		// 人员
		HashMap map = new HashMap();
		map.put(PersonF7UI.DEFAULT_SHOW_ALL, "YES");
		bizPrompt.setSelector(new PersonPromptBox(uio, map));
		bizPrompt.setFilterInfoProducer(new PersonF7Filter(map));
		bizPrompt.setHasCUDefaultFilter(false);
		
        bizPrompt.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        bizPrompt.setCommitFormat("$number$");
        bizPrompt.setEditFormat("$number$");
        bizPrompt.setDisplayFormat("$name$");
	}
	
	
	/**
	 * 给table框的某个列加入人员F7框工具类
	 * 
	 * @param uio 页面的UI实体
	 * @param table 表名
	 * @param colName 字段名
	 */
	public static void tablePersonF7(IUIObject uio, KDTable table, String colName) {
		
		KDBizPromptBox kDBizPromptBoxUser = new KDBizPromptBox();
		kDBizPromptBoxUser.setEditable(true);
		kDBizPromptBoxUser.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		kDBizPromptBoxUser.setDisplayFormat("$number$");
		kDBizPromptBoxUser.setEditFormat("$number$");
		kDBizPromptBoxUser.setCommitFormat("$number$");
		HashMap map = new HashMap();
		map.put(PersonF7UI.DEFAULT_SHOW_ALL, "YES");
		kDBizPromptBoxUser.setSelector(new PersonPromptBox(uio, map));
		kDBizPromptBoxUser.setFilterInfoProducer(new PersonF7Filter(map));
		kDBizPromptBoxUser.setHasCUDefaultFilter(false);
		table.getColumn(colName).setEditor(new KDTDefaultCellEditor(kDBizPromptBoxUser));
		//kdtable 中的f7的显示格式 
		ObjectValueRender nameRender = new ObjectValueRender();
		nameRender.setFormat(new BizDataFormat("$name$"));
		table.getColumn(colName).setRenderer(nameRender);
	}
	
	/**
	 * 职位F7框工具类
	 * 
	 * @param uio 页面的UI实体
	 * @param bizPrompt
	 * 
	 * 参考：职员_任职情况中的【职位】
	 * @see com.kingdee.eas.hr.emp.client.EmployeePMEditUI#initWorkButton()
	 */
	public static void positionF7(IUIObject uio, KDBizPromptBox bizPrompt) {
		
		HashMap map = new HashMap();
        map.put("ALL_ADMIN", "TRUE");
        PositionPromptBox2 pmtOrg = new PositionPromptBox2(uio, map);
		
        bizPrompt.setSelector(pmtOrg);
        bizPrompt.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");
        bizPrompt.setCommitFormat("$number$");
        bizPrompt.setEditFormat("$number$");
        bizPrompt.setDisplayFormat("$name$");
	}
	
	/**
	 * 给table框的某个列加入职位F7框工具类
	 * 
	 * @param uio 页面的UI实体
	 * @param table 表名
	 * @param colName 字段名
	 */
	public static void tablePositionF7(IUIObject uio, KDTable table, String colName) {
		
		KDBizPromptBox kDBizPromptBoxUser = new KDBizPromptBox();
		kDBizPromptBoxUser.setEditable(true);
		kDBizPromptBoxUser.setQueryInfo("com.kingdee.eas.basedata.org.app.PositionQuery");
		kDBizPromptBoxUser.setDisplayFormat("$number$");
		kDBizPromptBoxUser.setEditFormat("$number$");
		kDBizPromptBoxUser.setCommitFormat("$number$");
		HashMap map = new HashMap();
		map.put("ALL_ADMIN", "TRUE");
        PositionPromptBox2 pmtOrg = new PositionPromptBox2(uio, map);
		kDBizPromptBoxUser.setSelector(pmtOrg);
//		kDBizPromptBoxUser.setFilterInfoProducer(new PersonF7Filter(map));
//		kDBizPromptBoxUser.setHasCUDefaultFilter(false);
		table.getColumn(colName).setEditor(new KDTDefaultCellEditor(kDBizPromptBoxUser));
		//kdtable 中的f7的显示格式 
		ObjectValueRender nameRender = new ObjectValueRender();
		nameRender.setFormat(new BizDataFormat("$name$"));
		table.getColumn(colName).setRenderer(nameRender);
	}
	
	/**
	 * 设置部门的树形F7
	 * @param coreui所属UI
	 * @param prmtOrgUnit部门f7控件
	 * @param hasCUFilter是否CU隔离
	 */
	public static void deptF7(CoreUIObject coreui,KDBizPromptBox prmtOrgUnit, boolean hasCUFilter) {
		prmtOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");
		prmtOrgUnit.setEditFormat("$number$");
		prmtOrgUnit.setCommitFormat("$number$");
		prmtOrgUnit.setDisplayFormat("$name$");
		AdminF7 boxCostCenter = new AdminF7(coreui);
		HashMap map = new HashMap();
		map.put("All_Admins", "YES");
		map.put("DEFAULT_SHOW_ALL", "AAA");
		boxCostCenter.disablePerm(map, false);
		prmtOrgUnit.setSelector(boxCostCenter);
	}
}
