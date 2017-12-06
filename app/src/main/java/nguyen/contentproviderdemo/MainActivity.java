package nguyen.contentproviderdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addBirthday(View view) {

        //add a new birthday record
        ContentValues values = new ContentValues();

        values.put(BirthProvider.NAME, ((EditText)findViewById(R.id.nameET)).getText().toString());
        values.put(BirthProvider.BIRTHDAY, ((EditText)findViewById(R.id.birthdayET)).getText().toString());

        Uri uri = getContentResolver().insert(BirthProvider.CONTENT_URI, values);

        Toast.makeText(getApplicationContext(),
                "Nguyen: " + uri.toString() + " inserted!", Toast.LENGTH_LONG).show();
    }

    public void showAllBirthdays(View view) {
        //show all the birthdays sorted by friend's name
        String URL = "content://nguyen.contentproviderdemo.BirthdayProv/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "name");
        String result = "Nguyen Results: ";

        if(!c.moveToFirst()) {
            Toast.makeText(this, result + "no content yet!", Toast.LENGTH_LONG).show();
        } else {
            do {
                result = result + "\n" + c.getString(c.getColumnIndex(BirthProvider.NAME)) +
                        " with id " + c.getString(c.getColumnIndex(BirthProvider.ID)) +
                        " has birthday " + c.getString(c.getColumnIndex(BirthProvider.BIRTHDAY));
            } while(c.moveToNext());

            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }

    public void deleteAllBirthdays(View view) {
        //delete all the records and its table of the database provider
        String URL = "content://nguyen.contentproviderdemo.BirthdayProv/friends";
        Uri friends = Uri.parse(URL);
        int count = getContentResolver().delete(friends, null, null);

        String countNum = "Nguyen: " + count + " records are deleted.";
        Toast.makeText(getBaseContext(), countNum, Toast.LENGTH_LONG).show();
    }
}
