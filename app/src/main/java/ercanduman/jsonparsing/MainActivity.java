package ercanduman.jsonparsing;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private static String dataURL;
    private static ProgressDialog dialog;

    private static TextView tvTitle, tvDate, tvDetails;
    private static String title, date, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        initializeAllVariables();
    }

    private void initializeAllVariables() {
        dataURL = "http://horoscope-api.herokuapp.com/horoscope/month/libra";
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDetails = (TextView) findViewById(R.id.tvDetails);

        dialog = new ProgressDialog(context);
        dialog.setMessage(getResources().getString(R.string.connecting));
        dialog.setCancelable(false);
    }

    public void getData(View view) {
        //check netrwork conneciton
        if (CheckNetwork.isNetworkAvailable(context)) {
            MyAsyncTask task = new MyAsyncTask();
            task.execute(dataURL);
        } else {
            Toast.makeText(context, "No Connection Available!", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = HttpManager.getDataFromURL(strings[0]);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(result);
                title = jsonObject.getString("sunsign");
                date = jsonObject.getString("month");
                details = jsonObject.getString("horoscope");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (dialog.isShowing()) dialog.dismiss();
            tvTitle.setText("Title: " + title);
            tvDate.setText("Date: " + date);
            tvDetails.setText("Details here: " + details);
            tvDetails.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}
