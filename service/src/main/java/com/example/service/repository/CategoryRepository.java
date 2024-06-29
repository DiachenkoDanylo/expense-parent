package com.example.service.repository;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;
import com.example.service.entity.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findAllByClientUser(ClientUser clientUser);
    List<Category> findByClientUser_Username(String username);
//    @Query(
//            value = " INSERT INTO t_custom_category(c_client_user_id,  c_description, c_category_id, c_amount) VALUES (?,?,?,?)",
//            nativeQuery = true)
//    void saveCategoryByClient(Integer a, String b, Integer c, BigDecimal d);

//    List<Category> findAllByClientUser_Username
}
