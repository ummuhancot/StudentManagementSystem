import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//4
//repository:veritabanı ile ilgili işlemler
//ögrenciyi kaydetme silme getirme burada olacak
//create table,save,update, delete findAll
//student,instructor,course de olabilirdi
//interfaceler tanımlarız bu kadar büyürse
//bu class icin 5 i oluşturduk ve bu class tan cağırdık
public class StudentRepository  implements Repository <Student,Integer>{

    //7-t_student tablosunu oluşturma
    @Override
    public void createTable() {
        JdbcUtils.setConnection();//baglantı oluşturuldu
        JdbcUtils.setStatement();//statement başlattık
        //IF NOT eger tabyo yoksa oluştur dedik
        //oluşturma silme update etme icin kullandık

        try {
            JdbcUtils.st.execute("CREATE TABLE IF NOT EXISTS t_student(\n" +
                    "id SERIAL UNIQUE,\n" +
                    "name VARCHAR(50) NOT NULL CHECK(LENGTH(name)>0),\n" +
                    "lastname VARCHAR(50) NOT NULL CHECK(LENGTH(name)>0),\n" +
                    "city VARCHAR(50) NOT NULL, \n" +
                    "age INTEGER NOT NULL CHECK(age>0)  )"  );

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                JdbcUtils.st.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }

    //8-öğrenciyi kaydetme
    @Override
    public void save(Student student) {
        JdbcUtils.setConnection();
        JdbcUtils.SetPreparedStatement("INSERT INTO t_student(name,lastname,city,age) VALUES(?,?,?,?)");

        try {
            JdbcUtils.prst.setString(1,student.getName());
            JdbcUtils.prst.setString(2,student.getLastname());
            JdbcUtils.prst.setString(3,student.getCity());
            JdbcUtils.prst.setInt(4,student.getAge());
            JdbcUtils.prst.executeUpdate();
            System.out.println("Başarılı bir şekilde kaydedildi.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                JdbcUtils.prst.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //tüm ögrencileri db (databaseden) cekme işlemi
    @Override
    public List<Student> findAll() {
        JdbcUtils.setConnection();
        JdbcUtils.setStatement();
        List<Student> allStudent=new ArrayList<>();
        try {
            ResultSet rs =JdbcUtils.st.executeQuery("SELECT * FROM t_student");
            while (rs.next()){

                Student student=new Student(rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("city"),
                        rs.getInt("age"));
                student.setId(rs.getInt("id"));
                allStudent.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            try {
                JdbcUtils.st.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allStudent;
    }

    //11-verilen yeni öğrenci bilgisi ile var olan öğrenciyi güncelleme
    @Override
    public void update(Student student) {//student:ögrencinin yeni bilgileri

         JdbcUtils.setConnection();
         JdbcUtils.SetPreparedStatement("UPDATE t_student SET name=?, lastname=?,city=?,age=? WHERE id=?");
        try {
            JdbcUtils.prst.setString(1,student.getName());//buradake 1.2.3 sıraları soru işaretlerinin sırası
            JdbcUtils.prst.setString(2,student.getLastname());
            JdbcUtils.prst.setString(3,student.getCity());
            JdbcUtils.prst.setInt(4,student.getAge());
            JdbcUtils.prst.setInt(5,student.getId());
            int update = JdbcUtils.prst.executeUpdate();//güncellenen kayıt sayısını döndürür
            if (update>0){
                System.out.println("Güncelleme başarılı...");//1
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                JdbcUtils.st.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    //12-id si verilen öğrenciyi silme
    @Override
    public void deleteById(Integer id) {
        JdbcUtils.setConnection();
        JdbcUtils.setStatement();

        try {
            int delete = JdbcUtils.st.executeUpdate("DELETE FROM t_student WHERE id="+id);
            //kayıt bulunursa 1 aksi halde 0 kayıt silinir
            if (delete>0){
                System.out.println("Ögrenci silindi, ID : " + id);
            } else {
                System.out.println("Ögrenci bulunamadı...");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }  finally {
        try {
            JdbcUtils.st.close();
            JdbcUtils.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    }

    //10-idsi verilen öğrenciyi bulma
    @Override
    public Student findById(Integer id) {
        Student student=null;
        JdbcUtils.setConnection();
        JdbcUtils.SetPreparedStatement("SELECT * FROM t_student WHERE id=?");
        try {
            JdbcUtils.prst.setInt(1,id);
            ResultSet rs=JdbcUtils.prst.executeQuery();
            if (rs.next()){
                student=new Student(rs.getString("name"),rs.getString("lastname"),
                        rs.getString("city"),rs.getInt("age"));
                student.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                JdbcUtils.prst.close();
                JdbcUtils.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return student;
    }
}
