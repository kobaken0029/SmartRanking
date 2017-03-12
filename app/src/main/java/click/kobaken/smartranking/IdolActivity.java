package click.kobaken.smartranking;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import click.kobaken.smartranking.databinding.ActivityIdolBinding;
import click.kobaken.smartranking.entity.Idol;

public class IdolActivity extends AppCompatActivity {
    public static final String TAG = IdolActivity.class.getSimpleName();
    
    private static final String ARG_IDOL_ID = "iodl_id";

    private ActivityIdolBinding binding;
    
    public static Intent getCallingIntent(Context context, String id) {
        Intent intent = new Intent(context, IdolActivity.class);
        intent.putExtra(ARG_IDOL_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_idol);
        
        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra(ARG_IDOL_ID);
            binding.setIdol(Idol.createMock(id));
        }
    }
    
    public void vote(View v) {
        Toast.makeText(this, "Voted!", Toast.LENGTH_SHORT).show();
    }
}
