/**
 * 
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.popup.MenuSection;
import com.kingdee.eas.framework.client.popup.PopupMenuManager;
import com.kingdee.eas.framework.config.IObjectMultiPKBuilder;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.config.UserCustomConfigItemData;
import com.kingdee.eas.framework.config.UserPreferenceData;

/**
 * @author ken_liu
 *ԭhelper �ܶ�Ϊprotected, private. �̳��Ը�дһЩ����
 */
public class MyTablePreferencesHelper extends TablePreferencesHelper {
	public HashMap menuItemMap = new HashMap();
	public List tablesToAppled = new ArrayList();
//	public CoreUI ui = null;
	/**
	 * @param ui
	 */
	public MyTablePreferencesHelper(CoreUI ui) {
		super(ui);
	}

	/**
	 * @param ui
	 * @param pkBuilder
	 */
	public MyTablePreferencesHelper(CoreUI ui, IObjectMultiPKBuilder pkBuilder) {
		super(ui, pkBuilder);
	}

	/* Ŀǰ����Ŀ��ɱ��������裬��һЩ������ȥ���ˡ�
	 * (non-Javadoc)
	 * @see com.kingdee.eas.framework.config.AbstractTablePreferencesHelper#addMenuToTable(com.kingdee.bos.ctrl.kdf.table.KDTable)
	 */
	public void addMenuToTable( KDTable table ) 
	{
		PopupMenuManager mgr = ui.getPopupMenuManager(table);
        if( mgr == null ) 
        {
        	ui.addCommonMenusToTable(table);        	
        }
        
        mgr = ui.getPopupMenuManager(table);
        if (mgr==null)
        {
        	return;
        }
        
    	MenuSection section = mgr.findMenuSection("table");
    	if(section==null) 
    	{
    		mgr.addMenuSection(new MenuSection("table"));
    	}
    	
    	section = mgr.findMenuSection("table");
    	if(!section.containsMenuItemName(MENUITEM_TABLESETTING)) 
    	{
    		section.insertAfter(getSettingMenuItem(table));
    	}
    	
    	if(!section.containsMenuItemName("saveSetting")) {
    		section.insertAfter(getSaveMenuItem(table));
    	}
    }
	
	 private KDMenuItem getSettingMenuItem( KDTable table ) 
	 {
        KDMenuItem item = new KDMenuItem();
        item.setName(MENUITEM_TABLESETTING);
        item.setAction( getActionShowPreference(table) );
        item.setText(resHelper.getString("UITitle"));
        item.setToolTipText(resHelper.getString("UITitle"));
        menuItemMap.put(MENUITEM_TABLESETTING, item);
        return item;
	 }
	 
	 private KDMenuItem getSaveMenuItem( KDTable table ) 
	 {
        KDMenuItem item = new KDMenuItem();
        item.setName("saveSetting");
        item.setAction( getActionSave(table) );
        item.setText(resHelper.getString("SaveCurrentSetting"));
        item.setToolTipText(resHelper.getString("SaveCurrentSetting"));
        menuItemMap.put("saveSetting", item);
        return item;
	  }
	 
	 
	 
	public List getTablesToAppled() {
		return tablesToAppled;
	}

	public void setTablesToAppled(List tablesToAppled) {
		this.tablesToAppled = tablesToAppled;
	}

	/*TablePreferences�İ�ť��Ϊprotected.�޷���ȡ������¼���Ϊ�������ý���㡮ȷ������
		Ӧ�õ����б��������д; table�����ڴ�������
	 * (non-Javadoc)
	 * @see com.kingdee.eas.framework.config.AbstractTablePreferencesHelper#applyConfigFromData(com.kingdee.bos.ctrl.kdf.table.KDTable, com.kingdee.eas.framework.config.UserCustomConfigItemData, boolean)
	 */
	 public void applyConfigFromData( KDTable table , UserCustomConfigItemData userData , boolean hasData ) 
	 {
	 	for(int i=0; i<tablesToAppled.size(); i++) {
	 		super.applyConfigFromData((KDTable) tablesToAppled.get(i), userData, hasData);
	 	}
	 }

	 /**
	  * Ӧ��userData��tablesToAppled�������talble��ȥ
	  * @param userData
	  */
	 public void applyConfigFromData( UserCustomConfigItemData userData  ) 
	 {
	 	for(int i=0; i<tablesToAppled.size(); i++) {
	 		super.applyConfigFromData((KDTable) tablesToAppled.get(i), userData, true);
	 	}
	 }
	 
	 /**
	  * ��ȡtablesToAppled�е�����
	  * @param allSame��tablesToAppled�����б���һ������
	  * @return
	  */
	 public UserPreferenceData getUserDataFromUI(boolean allSame)
    {
        UserPreferenceData userData = new UserPreferenceData() ;
        userData.setVersion(UserPreferenceData.VER_2_0_0); //Added by Charse Wong at 2006-8-2
        if(allSame) {
            KDTable tb = (KDTable)tablesToAppled.get(0);;
            String key = ui.getMetaDataPK().getFullName()+"." + tb.getName();
            UserCustomConfigItemData itemData = null ;
            itemData = getColumnDataFromTable( tb , itemData ) ;
            itemData = getGenricDataFromTable( tb , itemData ) ;
            userData.getTables().put( key , itemData ) ;
        }
        return userData ;
    }
}
