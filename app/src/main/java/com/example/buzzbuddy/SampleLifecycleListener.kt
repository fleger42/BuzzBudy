import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class SampleLifecycleListener : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        Log.d("SampleLifecycle", "Returning to foreground…")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.d("SampleLifecycle", "Moving to background…")
    }
}