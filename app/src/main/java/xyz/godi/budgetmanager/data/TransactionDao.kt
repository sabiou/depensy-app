package xyz.godi.budgetmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import java.util.*

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction` ORDER BY name")
    fun getTransactions(): LiveData<List<Transaction>>

    // get transactions between dates
    @Query("SELECT * FROM `transaction` WHERE transactionDate BETWEEN :from AND :to")
    fun getTransactionsBtwnDates(from: Date, to: Date): LiveData<List<Transaction>>

    @Delete
    fun deleteTransaction(transaction: Transaction)
}