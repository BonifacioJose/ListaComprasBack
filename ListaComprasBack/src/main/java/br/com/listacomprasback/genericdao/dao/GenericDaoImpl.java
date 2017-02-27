package br.com.listacomprasback.genericdao.dao;

import br.com.listacomprasback.genericdao.interfaces.DatabaseEntity;
import br.com.listacomprasback.genericdao.interfaces.GenericDao;
import br.com.listacomprasback.genericdao.util.ArrayUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José
 */
public abstract class GenericDaoImpl<T extends DatabaseEntity> implements GenericDao<T> {

    private final Connection conexao;
    private PreparedStatement ps;
    private ResultSet rs;

    private final String table;
    private final String[] pkColumns;
    private final String[] columns;
    private final String[] columnsUpdate;

    private String sqlSelect;
    private String sqlInsert;
    private String sqlUpdate;
    private String sqlWherePk;

    public GenericDaoImpl(Connection conexao, String table, String[] pkColumns, String[] columns, String[] columnsUpdate) {
        this.conexao = conexao;
        this.table = table;
        this.pkColumns = pkColumns;
        this.columns = columns;
        this.columnsUpdate = columnsUpdate;
        setSqlSelect();
        setWherePk();
        setInsertSql();
        setUpdateSql();
    }

    @Override
    public boolean save(T entity) throws SQLException {
        if (entity.isNew()) {
            ps = conexao.prepareStatement(getSqlInsert());
            prepareInsert(entity);
        } else {
            ps = conexao.prepareStatement(getSqlUpdate());
            prepareUpdate(entity);
        }
        return ps.execute();
    }

    @Override
    public T saveWithReturn(T entity) throws SQLException {
        if (entity.isNew()) {
            ps = conexao.prepareStatement(getSqlInsert());
            prepareInsert(entity);
        } else {
            ps = conexao.prepareStatement(getSqlUpdate());
            prepareUpdate(entity);
        }
        ps.execute();
        ps.getResultSet();
        rs = ps.getResultSet();
        if (rs.next()) {
            System.out.println("teste1: " + rs.getLong(1));
            return findById(rs.getLong("id"));
        }
        return null;
    }

    @Override
    public T findById(Object... id) throws SQLException {
        ps = conexao.prepareStatement(getSqlSelect() + getSqlWherePk());
        int index = 1;
        for (Object object : id) {
            ps.setObject(index++, object);
        }
        System.out.println("FindById: " + getSqlSelect() + getSqlWherePk());
        rs = ps.executeQuery();
        rs.next();
        return extract(rs);
    }

    @Override
    public List<T> findAll() throws SQLException {
        ps = conexao.prepareStatement(getSqlSelect());
        List<T> results = new ArrayList<>();
        rs = ps.executeQuery();
        while (rs.next()) {
            results.add(extract(rs));
        }
        return results;
    }

    @Override
    public ResultSet findByQuery(String sql) throws SQLException {
        ps = conexao.prepareStatement(sql);
        rs = ps.executeQuery();
        return rs;
    }

    @Override
    public <T extends DatabaseEntity> boolean delete(T entity) throws SQLException {
        String sql = "delete from " + table + " where id = " + entity.pk()[0];
        ps = conexao.prepareStatement(sql);
        return ps.executeUpdate() == 1;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return conexao.prepareStatement(sql);
    }

    @Override
    public String getSqlSelect() {
        return sqlSelect;
    }

    @Override
    public String getSqlInsert() {
        return sqlInsert;
    }

    @Override
    public String getSqlUpdate() {
        return sqlUpdate;
    }

    @Override
    public String getSqlWherePk() {
        return sqlWherePk;
    }

    @Override
    public void closeConnections() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void closePointers() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Connection getConnection() {
        return this.conexao;
    }

    private void setSqlSelect() {
        String columnsString = "";
        for (String column : this.pkColumns) {
            if (!columnsString.isEmpty()) {
                columnsString += ",";
            }
            columnsString += table + "." + column + " as " + table + "_" + column;
        }
        for (String column : this.columns) {
            if (!columnsString.isEmpty()) {
                columnsString += ",";
            }
            columnsString += table + "." + column + " as " + table + "_" + column;
        }
        String sql = "select "
                + columnsString
                + " from " + table + " as " + table;
        System.out.println("Select: " + sql);
        sqlSelect = sql;
    }

    private void setWherePk() {
        String where = "";
        if (pkColumns.length > 1) {
            for (int i = 0; i < pkColumns.length; i++) {
                if (!where.isEmpty()) {
                    where += ",";
                } else {
                    where += " where ";
                }
                where += table + "." + pkColumns[i] + " = ?";
            }
        } else {
            where = " where " + table + "." + pkColumns[0] + " = ?";
        }
        sqlWherePk = where;
    }

    private void setInsertSql() {
        String sql = "insert into " + table + "("
                + ArrayUtil.arrayToString(columns) + ") values ("
                + ArrayUtil.arrayToString(ArrayUtil.replaceArrayValues(columns, "?")) + ") "
                + " returning " + ArrayUtil.arrayToString(pkColumns);
        System.out.println("Insert: " + sql);
        sqlInsert = sql;
    }

    private void setUpdateSql() {
        String sql = "update " + table + " set ";
        String[] colunasUpdate = new String[columnsUpdate.length];
        for (int i = 0; i < pkColumns.length; i++) {
            colunasUpdate[i] = columns[i] + " = ?";
        }
        for (int i = 0; i < columnsUpdate.length; i++) {
            colunasUpdate[i] = columnsUpdate[i] + " = ?";
        }
        sql += ArrayUtil.arrayToString(colunasUpdate);
        sql += sqlWherePk;
        System.out.println("Update: " + sql);
        sqlUpdate = sql;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

}
