import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun CurrencySymbolByLocation() {
    val context = LocalContext.current

    // Get the user's default locale
    val userLocale = context.resources.configuration.locales[0]

    // Retrieve the currency object based on the user's locale
    val currency = Currency.getInstance(userLocale)

    // Get the currency symbol
    val currencySymbol = currency.symbol

    // Now you can use the currency symbol in your UI
    Text(text = currencySymbol,
        fontSize = 25.sp, fontWeight = FontWeight.Bold,
        color = if (isSystemInDarkTheme()) Color.White
                else Color(71, 69, 69, 255)
    )
}

fun String.formatMoney(): String {

    // Reverse the input string to insert commas from right to left
    val reversedInput = this.reversed()

    // Use a StringBuilder to build the formatted string
    val formattedBuilder = StringBuilder()

    for ((index, char) in reversedInput.withIndex()) {
        // Add a comma after every three digits
        if (index > 0 && index % 3 == 0) {
            formattedBuilder.append(',')
        }
        formattedBuilder.append(char)
    }

    // Reverse the formatted string to get the correct order
    return formattedBuilder.toString().reversed()
}

fun String.limitDigit(limit: Int): String{

    // Get the last character from the string
    val lastCharacter = this[length - 1]

    return if (length > limit)
        substring(0, limit).formatMoney() + "..." + lastCharacter;
    else
        this;
}

fun shortenNumber(number: BigDecimal): String {
    val suffixes = arrayOf("", "K", "M", "B", "T") // Add more suffixes as needed
    var index = 0
    var num = number.toDouble()

    while (num >= 1000 && index < suffixes.size - 1) {
        num /= 1000
        index++
    }

    return "%.2f%s".format(num, suffixes[index])
}