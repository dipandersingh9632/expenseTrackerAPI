package com.depandre.expenseTrackerAPI.service;

import com.depandre.expenseTrackerAPI.entity.Expense;
import com.depandre.expenseTrackerAPI.excpetions.ResourceNotFoundException;
import com.depandre.expenseTrackerAPI.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
        /* We need to make sure that id is Valid so for this
        we can use the getExpenseById()  */
        Expense deleteExpense = getExpenseById(id);
        expenseRepository.delete(deleteExpense);
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
        2. Pageable attribute to apply pagination and sorting */
        return expenseRepository.findByCategory(category, page).toList();
    }

    @Override
    public List<Expense> readByName(String keyword, Pageable page) {
        return expenseRepository.findByNameContaining(keyword, page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
        /* If user has not pass the startDate then we will take the startDate as initial Date */
        if(startDate == null) startDate = new Date(0);
        /* If user has not pass the endDate then we will take the endDate as currDate */
        if(endDate == null) endDate = new Date(System.currentTimeMillis());
        return expenseRepository.findByDateBetween(startDate, endDate, page);
    }
}
