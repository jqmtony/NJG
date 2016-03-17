/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.render.CellCheckBoxRenderer;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class QkongUI extends AbstractQkongUI
{
    private static final Logger logger = CoreUIObject.getLogger(QkongUI.class);
    private final String LONGNUMBER = "longNumber";// 长编码
	private final String HEADNUMBER = "headNumber";// 长级长编码
	private final String LEVEL = "level";
	private final String NAME = "name";
	private final String ISQK = "isqk";
	private final String OLDQK = "oldqk";
	private final String ID = "id";
	private final String NUMBER = "number";
//	private Map<Integer,Boolean> modifyMap = new HashMap<Integer,Boolean>();
    
    /**
     * output class constructor
     */
    public QkongUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	kDTable1.checkParsed();
    	super.onLoad();
    	treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    	buildProjectTree();
//    	kDTable1.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	kDTable1.addKDTMouseListener(new KDTMouseListener() {
            public void tableClicked(KDTMouseEvent e) {
                try {
                    kdtEntries_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
//    	kDTable1.addKDTEditListener(new KDTEditAdapter() {
//            public void editStopped(KDTEditEvent e) {
//                try {
//                    kdtEntries_editStopped(e);
//                } catch(Exception exc) {
//                    handUIException(exc);
//                }
//            }
//        });
    	KDTDefaultCellEditor boxeditor = new KDTDefaultCellEditor(new KDCheckBox());
	    CellCheckBoxRenderer boxrender = new CellCheckBoxRenderer();
	    kDTable1.getColumn(ISQK).setEditor(boxeditor);
	    kDTable1.getColumn(ISQK).setRenderer(boxrender);
    }
    
    protected void kdtEntries_tableClicked(KDTMouseEvent e) throws Exception {
    	int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (e.getType() != KDTStyleConstants.BODY_ROW) {
			kDTable1.getSelectManager().remove();
			kDTable1.getSelectManager().setActiveRowIndex(-1);
			return;
		}
		if (colIndex == kDTable1.getColumnIndex(NAME) && e.getClickCount() == 1) {
			ICell cell = kDTable1.getCell(rowIndex, colIndex);
			if (cell != null) {
				Object value = cell.getValue();
				if (value != null) {
					if (value instanceof CellTreeNode) {
						CellTreeNode node = (CellTreeNode) value;
						node.doTreeClick(kDTable1, kDTable1.getCell(rowIndex, colIndex));
					}
				}
			}
		}
    }
    
    public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		if (treeMain.getRowCount() > 0) {
			treeMain.setSelectionRow(0);
			treeMain.expandPath(treeMain.getSelectionPath());
			
		}
	}
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	//检查数据是否被修改，并给出提示
    	
    	checkDataModify();
    	DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    	kDTable1.removeRows();
//    	modifyMap.clear();
    	if(isSelectedProjectNode()) {
    		CurProjectInfo pInfo = (CurProjectInfo)projectNode.getUserObject();
    		kDTextField1.setText(pInfo.getName());
//    		ProgrammingInfo programming = getProgrammingInfo(pInfo.getId().toString());
//    		if(programming != null){
//    			loadDatas(programming);
//    			setNameDisplay();
//    		}
    		IRowSet rs = getProgrammingContract(pInfo.getId().toString());
    		if(rs != null){
    			loadDatas(rs);
    			setNameDisplay();
    		}
    	}
    }
    
    protected void checkDataModify(){
    	IRow row = null;
    	boolean flag = false;
    	for(int i = kDTable1.getRowCount()-1; i >= 0; i--) {
    		row = kDTable1.getRow(i);
    		if(!((Boolean)row.getCell(ISQK).getValue()).toString().equals(((Boolean)row.getCell(OLDQK).getValue()).toString())){
    			flag = true;
    			break;
    		}
		}
    	if(flag && MsgBox.isYes(MsgBox.showConfirm2(this, "数据已修改,是否保存？"))){
    		try {
				actionStoreData_actionPerformed(null);
			} catch (Exception e) {
				handUIException(e);
			}
    	}
    }
    
    protected void kdtEntries_editStopped(KDTEditEvent e) throws Exception{
//    	if(e.getColIndex() == kDTable1.getColumnIndex(ISQK)){
//    		IRow row = kDTable1.getRow(e.getRowIndex());
//    		if(((Boolean)row.getCell(ISQK).getValue()).toString().equals(((Boolean)row.getCell(OLDQK).getValue()).toString())){
//    			modifyMap.put(e.getRowIndex(),false);
//    		}else
//    			modifyMap.put(e.getRowIndex(),true);
//    	}
    }
    
    protected void loadDatas(IRowSet rs){
    	try {
    		IRow row = null;
			while(rs.next()){
				row = kDTable1.addRow();
				row.getCell(NUMBER).setValue(rs.getString(1));
	    		row.getCell(NAME).setValue(rs.getString(2));
	    		row.getCell(ISQK).setValue(rs.getBoolean(3));
	    		row.getCell(OLDQK).setValue(rs.getBoolean(3));
	    		row.getCell(ID).setValue(rs.getString(4));
	    		row.getCell(LONGNUMBER).setValue(rs.getString(5));
	    		row.getCell(HEADNUMBER).setValue(rs.getString(6));
	    		row.getCell(LEVEL).setValue(rs.getInt(7));
			}
		} catch (SQLException e) {
			handUIException(e);
		}
    	
    }
    
    protected void loadDatas(ProgrammingInfo programming){
    	ProgrammingContractCollection pccoll = programming.getEntries();
    	ProgrammingContractInfo pcinfo = null;
    	IRow row = null;
    	for(int i = 0; i < pccoll.size(); i++) {
    		pcinfo = pccoll.get(i);
    		row = kDTable1.addRow();
    		row.getCell(NUMBER).setValue(pcinfo.getNumber());
    		row.getCell(NAME).setValue(pcinfo.getName());
    		row.getCell(ISQK).setValue(pcinfo.isIsQk());
    		row.getCell(OLDQK).setValue(pcinfo.isIsQk());
    		row.getCell(ID).setValue(pcinfo.getId().toString());
    		row.getCell(LONGNUMBER).setValue(pcinfo.getLongNumber());
    		if(pcinfo.getParent() != null)
    			row.getCell(HEADNUMBER).setValue(pcinfo.getParent().getLongNumber());
    		row.getCell(LEVEL).setValue(pcinfo.getLevel());
		}
    }
    
    /**
	 * 设置名称列显示缩进效果、在前面加空格
	 */
	private void setNameDisplay() {
		int rowCount = kDTable1.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kDTable1.getRow(i);
			int level = Integer.parseInt(row.getCell(LEVEL).getValue().toString());
			String name = getCellValue(kDTable1, i, NAME);
			if (name != null && name.trim().length() > 0) {
				boolean isLeaf = isLeaf(getCellValue(kDTable1, i, LONGNUMBER), kDTable1, HEADNUMBER);
				row.getCell(NAME).setValue(createCellTreeNode(name, level, isLeaf));
//				if(getOprtState().equals(OprtState.VIEW)) {
//				} else {
//					String blank = setNameIndent(level);
//					row.getCell("name").setValue(blank + name);
//				}
			}
		}
	}
	
	public void actionStoreData_actionPerformed(ActionEvent e) throws Exception {
		Set<String> idForTrues = new HashSet<String>();
		Set<String> idForFalse = new HashSet<String>();
		IRow row = null;
		for(int i = kDTable1.getRowCount()-1; i >= 0; i--) {
			row = kDTable1.getRow(i);
			if((Boolean)row.getCell(ISQK).getValue())
				idForTrues.add((String)row.getCell(ID).getValue());
			else
				idForFalse.add((String)row.getCell(ID).getValue());
			row.getCell(OLDQK).setValue(row.getCell(ISQK).getValue());
		}
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		String newVersion = null;
		String oldVersion = null;
		IRowSet rowSet = null;
		Set<String> idTrues = new HashSet<String>();
		Set<String> idFalse = new HashSet<String>();
		for(Iterator<String> it = idForTrues.iterator(); it.hasNext();) {
			newVersion = it.next();
			oldVersion = newVersion;
			//新老版本跟着变化
			while(newVersion != null){
				sqlBuilder.clear();
				sqlBuilder.appendSql(" select fid from t_con_programmingContract where fSrcId='"+newVersion+"'");
				rowSet = sqlBuilder.executeQuery();
				if(rowSet.next()){
					newVersion = rowSet.getString(1);
					idTrues.add(newVersion);
				}else
					newVersion = null;
			}
			//老版本跟着变化
			while(oldVersion != null){
				sqlBuilder.clear();
				sqlBuilder.appendSql(" select fSrcId from t_con_programmingContract where fid='"+oldVersion+"'");
				rowSet = sqlBuilder.executeQuery();
				if(rowSet.next()){
					oldVersion = rowSet.getString(1);
					idTrues.add(oldVersion);
				}else
					oldVersion = null;
			}
		}
		for(Iterator<String> it = idForFalse.iterator(); it.hasNext();) {
			newVersion = it.next();
			oldVersion = newVersion;
			//新版本跟着变化
			while(newVersion != null){
				sqlBuilder.clear();
				sqlBuilder.appendSql(" select fid from t_con_programmingContract where fSrcId='"+newVersion+"'");
				rowSet = sqlBuilder.executeQuery();
				if(rowSet.next()){
					newVersion = rowSet.getString(1);
					idFalse.add(newVersion);
				}else
					newVersion = null;
			}
			//老版本跟着变化
			while(oldVersion != null){
				sqlBuilder.clear();
				sqlBuilder.appendSql(" select fSrcId from t_con_programmingContract where fid='"+oldVersion+"'");
				rowSet = sqlBuilder.executeQuery();
				if(rowSet.next()){
					oldVersion = rowSet.getString(1);
					idFalse.add(oldVersion);
				}else
					oldVersion = null;
			}
		}
		idTrues.addAll(idForTrues);
		idFalse.addAll(idForFalse);
		if(idTrues.size() > 0){
			sqlBuilder.clear();
			sqlBuilder.appendSql("update T_CON_ProgrammingContract set cfisqk=1 where fid in("+setToString(idTrues)+")");
			sqlBuilder.executeUpdate();
		}
		if(idFalse.size() > 0){
			sqlBuilder.clear();
			sqlBuilder.appendSql("update T_CON_ProgrammingContract set cfisqk=0 where fid in("+setToString(idFalse)+")");
			sqlBuilder.executeUpdate();
		}
		MsgBox.showInfo("保存成功！");
	}
	
	private String setToString(Set<String> sets){
		StringBuffer params = new StringBuffer();
		for(Iterator<String> it=sets.iterator(); it.hasNext();) {
			params.append("'");
			params.append(it.next());
			params.append("',");
		}
		params.setLength(params.length()-1);
		return params.toString();
	}
	
	private boolean isLeaf(String parentLongNumber, KDTable table, String colHeadNumber) {
		boolean result = true;
		if (parentLongNumber == null || parentLongNumber.length() == 0)
			return result;
		int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String number = getCellValue(table, i, colHeadNumber);
			if (number == null)
				continue;
			if (number.startsWith(parentLongNumber)) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("    ");
		}
		return blank.toString();
	}
	
	private CellTreeNode createCellTreeNode(String name, int level, boolean isLeaf) {
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(name);
		treeNode.setTreeLevel(level);
		treeNode.setCollapse(false);
		treeNode.setHasChildren(!isLeaf);
		return treeNode;
	}
	
	private String getCellValue(KDTable table, int rowIndex, String colName) {
		if (table == null || colName == null)
			return null;
		ICell cell = table.getCell(rowIndex, colName);
		if (cell == null)
			return null;
		Object value = cell.getValue();
		String result = null;
		if (value instanceof CellTreeNode)
			result = (String) ((CellTreeNode) value).getValue();
		else
			result = value == null ? "" : value.toString();
		return result == null ? null : result.trim();
	}
    
    private boolean isSelectedProjectNode() {
		boolean result = false;
		DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
		if (treeNode != null && treeNode.isLeaf()) {
			if (treeNode.getUserObject() instanceof CurProjectInfo) {
				result = true;
			}
		}
		return result;
	}
    
    public DefaultKingdeeTreeNode getTreeNode(TreePath path) {
		return path == null ? null : (DefaultKingdeeTreeNode) path.getLastPathComponent();
	}
    
    public ProgrammingInfo getProgrammingInfo(String projectId) throws Exception {
		ProgrammingInfo result = null;
		EntityViewInfo mainQuery = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		mainQuery.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("project.id", projectId));
		filter.getFilterItems().add(new FilterItemInfo("ISLATEST", 1));
		filter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
//		SorterItemCollection sorter = mainQuery.getSorter();
//		sorter.clear();
//		SorterItemInfo sorterVsersion = new SorterItemInfo("version");
//		sorterVsersion.setSortType(SortType.DESCEND);
//		sorter.add(sorterVsersion);
		mainQuery.setSelector(getSelectors());
		ProgrammingCollection coll = ProgrammingFactory.getRemoteInstance().getProgrammingCollection(mainQuery);
		if (coll.size() > 0) {
			result = coll.get(0);
		}
		
		return result;
	}
    
    public IRowSet getProgrammingContract(String projectId) throws Exception {
    	IRowSet rs = null;
    	FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select pc.fnumber,pc.fname_l2,pc.cfisqk,pc.fid,pc.flongnumber,pcpa.flongnumber,pc.flevel from T_CON_ProgrammingContract pc ");
		builder.appendSql("left join T_CON_Programming program on pc.FPROGRAMMINGID=program.fid ");
		builder.appendSql("left join T_CON_ProgrammingContract pcpa on pcpa.fid=pc.fparentid ");
		builder.appendSql("where program.fprojectid='"+projectId+"' and program.FSTATE='4AUDITTED' and program.FISLATEST=1 order by pc.FSORTNUMBER");
		rs = builder.executeQuery();
    	return rs;
    }
    

    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isLatest"));
		sic.add(new SelectorItemInfo("entries.longNumber"));
		sic.add(new SelectorItemInfo("entries.name"));
		sic.add(new SelectorItemInfo("entries.parent.longNumber"));
		sic.add(new SelectorItemInfo("entries.parent.id"));
		sic.add(new SelectorItemInfo("entries.parent.number"));
		sic.add(new SelectorItemInfo("entries.id"));
		sic.add(new SelectorItemInfo("entries.number"));
		sic.add(new SelectorItemInfo("entries.level"));
		sic.add(new SelectorItemInfo("entries.isQk"));
    	return sic;
    }

}