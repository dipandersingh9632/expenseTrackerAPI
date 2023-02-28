package com.depandre.expenseTrackerAPI.service;

import com.depandre.expenseTrackerAPI.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {
    public Page<Expense> getAllExpenses(Pageable page);
    public Expense getExpenseById(Long id);
    public void deleteExpenseById(Long id);
    public Expense saveExpenseDetails(Expense expense);

    public Expense updateExpenseDetails(Long id, Expense expense);

    public List<Expense> readByCategory(String category, Pageable page);
}
