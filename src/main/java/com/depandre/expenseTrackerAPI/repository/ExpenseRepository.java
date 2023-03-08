package com.depandre.expenseTrackerAPI.repository;

import com.depandre.expenseTrackerAPI.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /* SELECT * FROM tbl_expenses where user_id =  AND category = ?; */
    Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable page);

    /* SELECT * from tbl_expenses WHERE user_id = ? AND name LIKE %keyword% */
    Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

    /* you can choose either return type as List<T> or Page<T> both are fine */

    /* SELECT * FROM tbl_expense WHERE user_id = ? AND date BETWEEN 'startDate' AND 'endDate'; */
    List<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);

    /* SELECT * FROM tbl_expense WHERE user_id = ?; */
    Page<Expense> findByUserId(Long userId, Pageable page);

    /* SELECT * FROM tbl_expenses WHERE user_id =  AND id = ? */
    Optional<Expense> findByUserIdAndId(Long userId, Long expenseId);

}
