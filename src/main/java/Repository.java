import java.util.List;

//5
//course,student, instructor için
//data tipinden BAĞIMSIZ CRUD işlemleri listeleyen interface
public interface Repository <S, U>{

    void createTable();

    void save(S entity); //varlık demek entity

    List<S> findAll();

    void update(S entity);

    void deleteById(U id); //id verilen ögrenciyi silme

    S findById(U id);


}
