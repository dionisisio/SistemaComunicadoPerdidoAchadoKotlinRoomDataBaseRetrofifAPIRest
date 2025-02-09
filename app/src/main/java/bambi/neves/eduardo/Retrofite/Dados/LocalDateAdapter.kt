package bambi.neves.eduardo.Retrofite.Dados
import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate

class LocalDateAdapter {
    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    fun fromJson(date: String): LocalDate {
        return LocalDate.parse(date) // Converte JSON para LocalDate
    }

    @ToJson
    fun toJson(date: LocalDate): String {
        return date.toString() // Converte LocalDate para JSON
    }
}
