package com.example.moneytracker.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moneytracker.models.debts.DebtDao
import com.example.moneytracker.models.debts.Debts
import com.example.moneytracker.models.expense.Expense
import com.example.moneytracker.models.expense.ExpenseDao
import com.example.moneytracker.models.lend.Lend
import com.example.moneytracker.models.lend.LendDao
import com.example.moneytracker.models.income.Income
import com.example.moneytracker.models.income.IncomeDao

@Database(
    entities = [
        Income::class,
        Expense::class,
        Lend::class,
        Debts::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun lendDao(): LendDao
    abstract fun debtDao(): DebtDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my-database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
