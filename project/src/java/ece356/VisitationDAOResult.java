/*
 * To change this license header, choose License Headevisitation in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FireChemist
 */
public class VisitationDAOResult {
    private List<Visitation> visitationRecords;
    private List<String> columnNames;
    public int count;
    
    public VisitationDAOResult () {
        
    }
    
    public VisitationDAOResult(List<Visitation> visitationRecords, List<String> columnNames, int count) {
        this.visitationRecords = visitationRecords;
        this.columnNames = columnNames;
        this.count = count;
    }
    
    public void setVisitationRecord(List<Visitation> visitationRecords) {
        this.visitationRecords = visitationRecords;
    }
    
    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
    
    public void setCount(int count){
        this.count = count;
    }
    
    public List<Visitation> getVisitationRecords() {
        return visitationRecords;
    }
    
    public List<String> getColumnNames() {
        return columnNames;
    }
    
    public int getCount() {
        return count;
    }
}
