package com.depandre.expenseTrackerAPI.repository;

import com.depandre.expenseTrackerAPI.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /* SELECT * FROM tbl_expenses where category = ?; */
    Page<Expense> findByCategory(String category, Pageable page);

    /* SELECT * from tbl_expenses WHERE name LIKE %keyword% */
    Page<Expense> findByNameContaining(String keyword, Pageable page);

    /* you can choose either return type as List<T> or Page<T> both are fine */

    /* SELECT * FROM tbl_expense WHERE date BETWEEN 'startDate' AND 'endDate'; */
    List<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

}
