package xyz.godi.budgetmanager.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import xyz.godi.budgetmanager.model.Transaction;

@Dao
public interface TransactionDAO {

    // insert a transaction into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);

    @Query("DELETE FROM transaction_table WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM transaction_table ORDER BY date")
    List<Transaction> getAllTransactions();

    // get transactions between two dates
    @Query("SELECT * FROM transaction_table WHERE date BETWEEN :from AND :to")
    List<Transaction> getTransactionsBetweenDates(Date from, Date to);
}