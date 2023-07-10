import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.moneytracker.models.EarnedViewModel

@Composable
fun YearExpander(idx: Int, earnedViewModel: EarnedViewModel){
    // Get the total earned a year
    earnedViewModel.getTotalEarnedAYear("2021")

    // load the live updated data
    val totalEarnedAYear: Double by earnedViewModel.liveEarnedAYear.observeAsState(0.0)
    val totalEarned: Double? = totalEarnedAYear

    Text(text = totalEarned.toString())
}

@Composable
fun MonthExpander(){

}