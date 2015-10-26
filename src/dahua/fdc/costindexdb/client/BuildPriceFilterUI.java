/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class BuildPriceFilterUI extends AbstractBuildPriceFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildPriceFilterUI.class);
    
    /**
     * output class constructor
     */
    public BuildPriceFilterUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		// 当前用户具有权限的所有成本组织
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select a.forgid from T_PM_OrgRangeIncludeSubOrg a left join T_ORG_BaseUnit c on c.fid=a.forgid where c.fisCostOrgUnit=1 and a.fuserid='"+user.getId().toString()+"'");
		IRowSet rs = builder.executeQuery();
		Set orgs = new HashSet();
		// 4252
		while(rs.next()){
			orgs.add(rs.getString(1));
		}
		filterInfo.getFilterItems().add(new FilterItemInfo("costCenter.id", orgs, CompareType.INCLUDE));
		filterInfo.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		viewInfo.setFilter(filterInfo);
		prmtProject.setEntityViewInfo(viewInfo);
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    @Override
    public FilterInfo getFilterInfo() {
    	if(prmtProject.getValue() != null){
    		CurProjectInfo projectInfo = (CurProjectInfo)prmtProject.getValue();
    		filterInfo.getFilterItems().add(new FilterItemInfo("projectId",projectInfo.getId().toString()));
    	}
    	return filterInfo;
    }

}