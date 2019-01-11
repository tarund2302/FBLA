package tarundFBLA;

import tarundFBLA.FormViews.StudentFormView;
import tarundFBLA.Models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL {
    private Connection connection = null;
    private Statement stmt = null;

    private java.util.Date utilDate = new java.util.Date();

    public SQL(){

    }
    public enum Database{
        EBOOK("eBookIssuance");
        public final String database;
        Database (String database){this.database = database;}
    }
    public enum Table{
        EBOOK("EbookCode"),
        STUDENT("StudentInfo");

        public final String table;
        Table (String table){this.table = table;}
    }

    public enum Grade {
        GRADE9(9),
        GRADE10(10),
        GRADE11(11),
        GRADE12(12);
        public final int grade;
        Grade (int grade){this.grade = grade;}
    }

    public void connect (Database d){
        try {Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");}
        catch (ClassNotFoundException e){
            e.printStackTrace();
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost;database=eBookIssuance" + d.database, "tarundFBLA", "t03212003");
        }
        catch (SQLException e){
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }

    public void addEBook(int redemptionCode, int id, String bookName,String author, Table t){
        if(connection != null){
            try{
                PreparedStatement ps = connection.prepareStatement("Insert into dbo." + t.table + "values (?,?,?,?)");
                ps.setInt(1,redemptionCode);
                ps.setInt(2,id);
                ps.setString(3,bookName);
                ps.setString(4,author);
                ps.executeUpdate();
            }
            catch (SQLException e){
                System.out.println("Prepared Statement Failed!Check output console" );
                e.printStackTrace();
                return;
            }
        }
        else{
            System.out.println("Failed to make connection!");
        }
    }
    public void addStudent(String firstName, String lastName, String grade, String classRoom, int id, Table t){
        if (connection != null){
            try {
                PreparedStatement ps = connection.prepareStatement("insert into dbo.StudentInfo." + t.table + "values (?,?,?,?,?)");
                ps.setString(1,firstName);
                ps.setString(2,lastName);
                ps.setString(3,grade);
                ps.setString(4,classRoom);
                ps.setInt(4,id);
                ps.executeUpdate();
            }
            catch (SQLException e){
                System.out.println("Prepared Statement Failed! Check output console");
                e.printStackTrace();
            }
        }
        commit();
    }
    public void addStudent(String firstName, String lastName, String grade, String classRoom, int id, int redemptionCode, Table t){
        if(connection != null){
            try{
                PreparedStatement ps = connection.prepareStatement("insert into dbo.StudentInfo." + t.table + "values (?,?,?,?,?,?)");
                ps.setString(1,firstName);
                ps.setString(2,lastName);
                ps.setString(3,grade);
                ps.setString(4,classRoom);
                ps.setInt(5,id);
                ps.setInt(6,redemptionCode);
                ps.executeUpdate();
            }
            catch(SQLException e){
                System.out.println("Prepared Statement Failed! Check output console");
                e.printStackTrace();
            }
        }
        commit();
    }

    public void addStudent(Student student){
        addStudent(student.getFirstName(),student.getLastName(),student.getGrade(),student.getClassRoom(),Integer.valueOf(student.getID()),Integer.valueOf(student.getRedemptioncode()),Table.STUDENT);
    }

    public void delete(String key, Table t){
        runStatement("delete from " + t.table + " where id = " + key);
    }

    public void delete(String key, String column, Table t){
        runStatement("delete from " + t.table + " where " + column + " = " + key);
    }

    public void editStudent(Table t, String col, String edit, int id){
        String sql = "UPDATE " + t.table+ " SET " + col + " = " + "'" + edit + "'" + "WHERE id=" + String.valueOf(id);
        runStatement(sql);
        commit();
    }

    public void edit(Table t, String col, Object edit, int id) {
        String sql = "UPDATE " + t.table + " SET "+  col + " = "  + edit.toString()  + " WHERE id="+ String.valueOf(id) + ";";
        runStatement(sql);
        commit();
    }

    public void editCode(Table t, String col, Object edit, int id){
        String sql = "UPDATE " + t.table + " SET "+  col + " = "  + edit.toString()  + " WHERE bookId="+ String.valueOf(id) + ";";
        runStatement(sql);
        commit();
    }

    public void print(Table t){
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement("Select * from dbo." + t.table);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        ResultSet rs = null;
        try{
            rs = ps.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        if(rs != null){
            try{
                while(rs.next()){
                    int i = rs.getInt(1);
                    String user = rs.getString(2);
                    String password = rs.getString(3);
                    System.out.println("RECORD : " + i + " - " + user + " - " + password);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    private void runStatement(String sql){
        if (connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet getResultSet(String sql) {
        ResultSet rs = null;
        if (connection != null){
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(sql);
            } catch (SQLException l) {
                l.printStackTrace();
            }
        }
        return rs;
    }

    public void refresh (StudentFormView s){
        int id = Integer.parseInt(s.studentId.getValue().replaceAll("'",""));
        editStudent(Table.STUDENT,"id",s.studentId.getValue(),id);
        commit();

    }

    public int genID(Table table){
        ResultSet rs = getResultSet("SELECT id from dbo." + table.table + " ORDER BY id DESC LIMIT 1;");
        int id = 0;
        try{
            while(rs.next()){
                id = rs.getInt("id");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public void commit() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("COMMIT;");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Object> getList(SQL.Table table, String column) {
        List<Object> list = new ArrayList<>();
        ResultSet resultSet = getResultSet("SELECT * FROM " + table.table);
        String elements;
        try {
            while (resultSet.next()) {
                elements = resultSet.getString(column);
                list.add(elements);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Object> getList(ResultSet rs, int column) {
        List<Object> strings = new ArrayList<>();
        resetResultSet(rs);
        try {
            while (rs.next()) {
                strings.add(String.valueOf(rs.getObject(column)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public void resetResultSet(ResultSet rs) {
        try {
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Integer> getIntegerList(SQL.Table table, String coloum) {
        List<Integer> list = new ArrayList<>();
        ResultSet resultSet = getResultSet("SELECT * FROM " + table.table);
        String elements;
        try {
            while (resultSet.next()) {
                elements = resultSet.getString(coloum);
                list.add(Integer.valueOf(elements));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }






}
