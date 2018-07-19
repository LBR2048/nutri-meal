package ardjomand.leonardo.nutrimeal.widget;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class WidgetJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        OrdersWidget.updateAllWidgets(getApplicationContext());
        Log.d("Job", "onStartJob");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d("Job", "onStopJob");
        return false;
    }
}
