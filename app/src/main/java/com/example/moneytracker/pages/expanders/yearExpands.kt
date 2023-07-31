import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.moneytracker.models.income.IncomeViewModel

@Composable
fun YearExpander(idx: Int, incomeViewModel: IncomeViewModel) {
    // Get the total earned a year
    incomeViewModel.getTotalEarnedAYear("2021")

    // load the live updated data
    val totalEarnedAYear: Double by incomeViewModel.liveEarnedAYear.observeAsState(0.0)
    val totalEarned: Double? = totalEarnedAYear

    Text(text = totalEarned.toString())
}

@Composable
fun MonthExpander(){

}