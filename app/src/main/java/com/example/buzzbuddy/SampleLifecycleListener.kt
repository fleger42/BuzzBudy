import android.content.Context
import android.icu.util.GregorianCalendar
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.buzzbuddy.db.BuzzBudyDatabase


class SampleLifecycleListener(private val context: Context) : DefaultLifecycleObserver {

    private lateinit var db: BuzzBudyDatabase

    override fun onStart(owner: LifecycleOwner) {
        db = BuzzBudyDatabase(context)
        val lastLog = db.getLastLog()
        val calendar = GregorianCalendar().apply {
            timeInMillis = lastLog
        }

        val year = calendar.time
        Toast.makeText(context, "Last log: $year", Toast.LENGTH_SHORT).show()
    }

    override fun onStop(owner: LifecycleOwner) {
        db = BuzzBudyDatabase(context)
        val calendar = GregorianCalendar()
        val time = calendar.timeInMillis

        db.editLastLog(time)
    }
}