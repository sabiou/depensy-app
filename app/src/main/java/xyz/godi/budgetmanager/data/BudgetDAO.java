package xyz.godi.budgetmanager.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import xyz.godi.budgetmanager.model.Budget;

@Dao
public interface BudgetDAO {

    // insert a budget into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Budget budget);

    @Query("DELETE FROM budget_table WHERE name = :name")
    void delete(String name);

    // Query all budgets
    @Query("SELECT * FROM budget_table ORDER BY name ASC")
    List<Budget> getAllBudgets();
}
