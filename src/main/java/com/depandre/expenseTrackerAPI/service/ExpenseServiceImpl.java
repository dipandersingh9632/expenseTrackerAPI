package com.depandre.expenseTrackerAPI.service;

import com.depandre.expenseTrackerAPI.entity.Expense;
import com.depandre.expenseTrackerAPI.excpetions.ResourceNotFoundException;
import com.depandre.expenseTrackerAPI.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private ExpenseRepository expenseRepository;
    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepository.findAll(page);
    }

    @Override
    public Expense getExpenseById(Long id) throws ResourceNotFoundException {
        Optional<Expense> expense = expenseRepository.findById(id);
        if(expense.isPresent()) return expense.get();
        else throw new ResourceNotFoundException("Expense is not found for id " + id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);

        if(expense.getName() != null) existingExpense.setName(expense.getName());
        if(expense.getDescription() != null) existingExpense.setDescription(expense.getDescription());
        if(expense.getAmount() != null) existingExpense.setAmount(expense.getAmount());
        if(expense.getCategory() != null) existingExpense.setCategory(expense.getCategory());
        if(expense.getDate() != null) existingExpense.setDate(expense.getDate());
        return expenseRepository.save(existingExpense);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
        /* this function findByCategory that JPA will implements need to require 2 parameter
        1. your filtering entity
        2. Pageable attribute */
        return expenseRepository.findByCategory(category, page).toList();
    }
}
