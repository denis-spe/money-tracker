import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.moneytracker.models.EarnedViewModel

@Composable
fun YearExpander(idx: Int, earnedViewModel: EarnedViewModel){
    // Get the total earned a year
    earnedViewModel.getTotalEarnedAYear()

    // load the live updated data
    val totalEarnedAYear: List<Double> by earnedViewModel.liveDoubleData.observeAsState(emptyList())
    val totalEarned: Double? = totalEarnedAYear.getOrNull(idx)

    Text(text = totalEarned.toString())
}

@Composable
fun MonthExpander(){

}