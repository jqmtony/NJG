/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class UpdatePlanDate extends AbstractUpdatePlanDate
{
    private static final Logger logger = CoreUIObject.getLogger(UpdatePlanDate.class);
    
    /**
     * output class constructor
     */
    public UpdatePlanDate() throws Exception
    {
        super();
    }


    /**
     * output btnYes_actionPerformed method
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnYes_actionPerformed(e);
        
        if(getUIContext().get("kdtable")!=null){
        	KDTable kdtable = (KDTable)getUIContext().get("kdtable");
        	int selectRows[] = KDTableUtil.getSelectedRows(kdtable);
        	if(selectRows.length<1){
        		MsgBox.showInfo("请选中你要设置时间的单元格！");SysUtil.abort();
        	}
        		if(UIRuleUtil.isNull(pkPlanDate.getValue())){
        			MsgBox.showInfo("时间不能为空！");
        			SysUtil.abort();
        		}
        	KDTSelectBlock sb;
        	int size = kdtable.getSelectManager().size(); 
        	for (int i = 0; i < size; i++){
        		sb = kdtable.getSelectManager().get(i);
        		int top = sb.getTop(); // 选择块最上边行索引
        		int bottom = sb.getBottom(); // 选择块最下边行索引
        		int left = sb.getLeft(); // 选择块最左边列索引
        		int right = sb.getRight(); // 选择块最右边列索引
        		for (int j = top; j <= bottom; j++) {
					for (int j2 = left; j2 <=right; j2++) {
						String cellName = kdtable.getColumnKey(j2);
						kdtable.getCell(j, cellName).setValue(pkPlanDate.getValue());
					}
				}
        	}
        }
        getUIWindow().close();
    }

    /**
     * output btnNo_actionPerformed method
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnNo_actionPerformed(e);
        this.getUIWindow().close();
    }

}