package click.kobaken.smartranking;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import click.kobaken.smartranking.databinding.ActivityElectionBinding;
import click.kobaken.smartranking.entity.Idol;

public class ElectionResultActivity extends AppCompatActivity {
    public static final String TAG = ElectionResultActivity.class.getSimpleName();

    private static final String ARG_ELECTION_ID = "election_id";

    private ActivityElectionBinding binding;

    public static Intent getCallingIntent(Context context, String electionId) {
        Intent intent = new Intent(context, ElectionResultActivity.class);
        intent.putExtra(ARG_ELECTION_ID, electionId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_election);

        Intent intent = getIntent();
        if (intent != null) {
            String electionId = intent.getStringExtra(ARG_ELECTION_ID);
            Toast.makeText(this, electionId, Toast.LENGTH_SHORT).show();
        }

        binding.myPoint.setText("99IRH");

        IdolListAdapter adapter = new IdolListAdapter(this, Idol.createMocks(5));
        binding.idolList.setAdapter(adapter);
        binding.idolList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent1 = IdolActivity.getCallingIntent(this, adapter.getItem(i).id);
            startActivity(intent1);
        });
    }
    
//    public void vote(View v) {
//        Toast.makeText(this, "Voted!!", Toast.LENGTH_SHORT).show();
//    }

    private class IdolListAdapter extends ArrayAdapter<Idol> {
        private Context context;
        private List<Idol> idols;

        public IdolListAdapter(Context context, List<Idol> idols) {
            super(context, 0);
            this.context = context;
            this.idols = idols;
        }

        @Nullable
        @Override
        public Idol getItem(int position) {
            return idols.get(position);
        }

        @Override
        public int getCount() {
            return idols.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_layout_result, parent, false);
            }

            Idol idol = getItem(position);

//            TextView numVote = (TextView) convertView.findViewById(R.id.num_vote);
//            numVote.setText(idol.numVote);

            TextView name = (TextView) convertView.findViewById(R.id.num_vote);
            name.setText(idol.name);

            ImageView image = (ImageView) convertView.findViewById(R.id.idol_image);
            switch (idol.imageUrl) {
                case "0":
                    image.setImageDrawable(getDrawable(R.drawable.mmrn));
                    break;
                case "1":
                    image.setImageDrawable(getDrawable(R.drawable.mmrn2));
                    break;
                case "2":
                    image.setImageDrawable(getDrawable(R.drawable.mmrn5));
                    break;
                default:
                    image.setImageDrawable(getDrawable(R.drawable.mmrn));
                    break;
            }

            ImageView ranking = (ImageView) convertView.findViewById(R.id.ranking);
            if (ranking != null) {
                Log.d(TAG, "getView: " + position);
                switch (position) {
                    case 0:
                        ranking.setImageDrawable(getDrawable(R.drawable.ic_looks_one_black_24dp));
                        break;
                    case 1:
                        ranking.setImageDrawable(getDrawable(R.drawable.ic_looks_two_black_24dp));
                        break;
                    case 2:
                        ranking.setImageDrawable(getDrawable(R.drawable.ic_looks_3_black_24dp));
                        break;
                    case 3:
                        ranking.setImageDrawable(getDrawable(R.drawable.ic_looks_4_black_24dp));
                        break;
                    case 4:
                        ranking.setImageDrawable(getDrawable(R.drawable.ic_looks_5_black_24dp));
                    default:
                        break;
                }
            }

            TextView description = (TextView) convertView.findViewById(R.id.description);
            description.setText(idol.description);

            return convertView;
        }
    }
}
