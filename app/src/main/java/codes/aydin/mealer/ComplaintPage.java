package codes.aydin.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ComplaintPage extends AppCompatActivity {

    //creating page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_page);

        LinearLayout complaintLayout = findViewById(R.id.complaintLayout);

        //for accessing database
        DBHelper DBHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        //for database query
        String[] projection = {"cook_email", "client_email", "description", "complaint_id"};
        String selection = "is_resolved = ?";
        String[] selectionArgs = {"0"};

        //database query
        Cursor cursor = db.query(codes.aydin.mealer.DBHelper.COMPLAINT_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        //make arraylist of complaints
        List<String[]> rows = new ArrayList<>();
        while(cursor.moveToNext()) {
            String[] row = { cursor.getString(cursor.getColumnIndexOrThrow("cook_email")), cursor.getString(cursor.getColumnIndexOrThrow("client_email")), cursor.getString(cursor.getColumnIndexOrThrow("description")), cursor.getString(cursor.getColumnIndexOrThrow("complaint_id"))};
            rows.add(row);
        }
        cursor.close();

        //output all complaints as buttons
        for (String[] row : rows) {
            Button complaintBtn = new Button(this);
            complaintBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            complaintBtn.setText("Complaint ID"+row[3]+ ":" + row[0]);
            complaintBtn.setId(View.generateViewId());


            //activity when button is pressed
            complaintBtn.setOnClickListener(l -> {
                Intent launchActivity = new Intent(getApplicationContext(), IndividualComplaint.class).putExtra("complaintInfo", row);
                startActivity(launchActivity);
                finish();
            });

            complaintLayout.addView(complaintBtn);
        }

        //logout buttobn
        Button logout = findViewById(R.id.btnComplaintLogOut);

        logout.setOnClickListener(view -> {
            finish();
        });


    }
}