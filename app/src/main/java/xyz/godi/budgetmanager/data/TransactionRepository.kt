package xyz.godi.budgetmanager.data

import java.util.*

class TransactionRepository private constructor(private val transactionDao: TransactionDao) {

    fun getAllTransactions() = transactionDao.getTransactions()

    fun getTransactionsBtwnDates(from: Date, to: Date) = transactionDao.getTransactionsBtwnDates(from, to)

    fun deleteTransaction(transaction: Transaction) = transactionDao.deleteTransaction(transaction)

    companion object {
        // for singleton instanciation
        @Volatile private var instance: TransactionRepository? = null

        fun getInstance(transactionDao: TransactionDao) =
                instance?: synchronized(this) {
                    instance?: TransactionRepository(transactionDao).also { instance = it }
                }
    }
}