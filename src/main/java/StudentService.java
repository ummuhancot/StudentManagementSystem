import java.util.Scanner;
//6
//en yoğun en kalabalık methodlar service kılaslarında olur
//service:bussiness:mantıksal işlemler
//service classları repositoriy classları ile görüşür
public class StudentService {

    Scanner input = Runner.inp; //runner de oluşturdumuz static scanner cardık burda

    //repositorynin methodlarını kullanabilmek icin obje oluşturalım
    private Repository repo= new StudentRepository(); //polimorfizm i uyguluyoruz burada
    //repository nin methodlarını kullancaz kendi kılasından almak yerine perıntından aldık

    //7-a-student tablosunu oluşturma methodu
    public void createStudentTable(){
        repo.createTable();
    }

    //8-a-verilen bilgiler ile öğrenciyi kaydetme
    public Student getStudentInfo(){

        System.out.println("AD :");
        String name=input.nextLine();
        System.out.println("SOYAD :");
        String lastname=input.nextLine();
        System.out.println("ŞEHİR :");
        String city=input.next();
        System.out.println("YAŞ :");
        int age= input.nextInt();
        input.nextLine();
        return new Student(name,lastname,city,age); //id:null

    }

    //ögrenciyi kayıt etme icin method
    public void saveStudent(Student newStudent){
        repo.save(newStudent);
    }

}
