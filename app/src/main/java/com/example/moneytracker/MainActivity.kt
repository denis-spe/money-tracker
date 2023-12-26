package com.example.moneytracker

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.moneytracker.models.AppDatabase
import com.example.moneytracker.models.debts.DebtDao
import com.example.moneytracker.models.debts.DebtRepository
import com.example.moneytracker.models.debts.DebtViewModel
import com.example.moneytracker.models.expense.ExpenseDao
import com.example.moneytracker.models.expense.ExpenseRepository
import com.example.moneytracker.models.expense.ExpenseViewModel
import com.example.moneytracker.models.lend.LendDao
import com.example.moneytracker.models.lend.LendRepository
import com.example.moneytracker.models.lend.LendViewModel
import com.example.moneytracker.models.income.IncomeDao
import com.example.moneytracker.models.income.IncomeRepository
import com.example.moneytracker.models.income.IncomeViewModel
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    // Instantiate view models
    private lateinit var incomeViewModel: IncomeViewModel
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var lendViewModel: LendViewModel
    private lateinit var debtViewModel: DebtViewModel

    // Declare an instance of FirebaseAuth
    private lateinit var auth: FirebaseAuth

    // Get the current user
    private var currentUser: FirebaseUser? = null

    // App context
    private val context: Context = this

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializer the Firebase app
        FirebaseApp.initializeApp(this)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize the dao
        val incomeDao: IncomeDao = AppDatabase.getInstance(this).incomeDao()
        val expenseDao: ExpenseDao = AppDatabase.getInstance(this).expenseDao()
        val lendDao: LendDao = AppDatabase.getInstance(this).lendDao()
        val debtDao: DebtDao = AppDatabase.getInstance(this).debtDao()

        // Initialize the repositories
        val incomeRepository: IncomeRepository = IncomeRepository(incomeDao)
        val expenseRepository: ExpenseRepository = ExpenseRepository(expenseDao)
        val lendRepository: LendRepository = LendRepository(lendDao)
        val debtRepository: DebtRepository = DebtRepository(debtDao)

        // Assign view models
        incomeViewModel = IncomeViewModel(incomeRepository, this)
        expenseViewModel = ExpenseViewModel(expenseRepository, this)
        lendViewModel = LendViewModel(lendRepository, this)
        debtViewModel = DebtViewModel(debtRepository, this)

        setContent {
            MoneyTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(
                        incomeViewModel,
                        expenseViewModel=expenseViewModel,
                        lendViewModel=lendViewModel,
                        debtViewModel=debtViewModel,
                        auth = auth,
                        currentUser = currentUser
                    )
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (auth.currentUser != null) {
            this.currentUser = auth.currentUser
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoneyTrackerTheme {
        // Instantiate view models
        lateinit var incomeViewModel: IncomeViewModel
        lateinit var expenseViewModel: ExpenseViewModel
        lateinit var lendViewModel: LendViewModel
        lateinit var debtViewModel: DebtViewModel

        val context = LocalContext.current

        // Initialize the dao
        val incomeDao: IncomeDao = AppDatabase.getInstance(context).incomeDao()
        val expenseDao: ExpenseDao = AppDatabase.getInstance(context).expenseDao()
        val lendDao: LendDao = AppDatabase.getInstance(context).lendDao()
        val debtDao: DebtDao = AppDatabase.getInstance(context).debtDao()

        // Initialize the repositories
        val incomeRepository: IncomeRepository = IncomeRepository(incomeDao)
        val expenseRepository: ExpenseRepository = ExpenseRepository(expenseDao)
        val lendRepository: LendRepository = LendRepository(lendDao)
        val debtRepository: DebtRepository = DebtRepository(debtDao)

        // Assign view models
        incomeViewModel = IncomeViewModel(incomeRepository, context)
        expenseViewModel = ExpenseViewModel(expenseRepository, context)
        lendViewModel = LendViewModel(lendRepository, context)
        debtViewModel = DebtViewModel(debtRepository, context)

        val auth: FirebaseAuth = Firebase.auth
        val currentUser = auth.currentUser

        App(
            incomeViewModel,
            expenseViewModel=expenseViewModel,
            lendViewModel=lendViewModel,
            debtViewModel=debtViewModel,
            auth,
            currentUser
        )
    }
}