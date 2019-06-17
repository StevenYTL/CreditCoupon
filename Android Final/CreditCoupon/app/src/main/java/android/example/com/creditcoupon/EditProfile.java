package android.example.com.creditcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {

    private EditText medit_name;
    private EditText medit_bank;
    private EditText medit_favor;
    private Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        medit_bank = findViewById(R.id.text_bank_editable);
        medit_favor = findViewById(R.id.text_favor_editable);
        medit_name = findViewById(R.id.text_name_editable);
        btn_save = findViewById(R.id.button_save);
        Intent intent = getIntent();
        medit_bank.setText(intent.getCharSequenceExtra("bank"));
        medit_name.setText(intent.getCharSequenceExtra("name"));
        medit_favor.setText(intent.getCharSequenceExtra("favor"));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra("bank", medit_bank.getText());
                replyIntent.putExtra("name", medit_name.getText());
                replyIntent.putExtra("favor", medit_favor.getText());
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

}
