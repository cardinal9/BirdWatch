package Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jpitkonen.tsirbulawatch.R;

import java.util.List;

import Model.Bird;

public class BirdListAdapter extends RecyclerView.Adapter<BirdListAdapter.BirdViewHolder> {

    class BirdViewHolder extends RecyclerView.ViewHolder {
        private final TextView birdItemView;
        private final TextView birdRareView;
        private final TextView birdNotesView;
        private final TextView birdTimeStampView;
        private final ImageView birdImageView;

        private BirdViewHolder(View view) {
            super(view);

            birdItemView = view.findViewById(R.id.birdNameId);
            birdRareView = view.findViewById(R.id.rarityId);
            birdNotesView = view.findViewById(R.id.notesId);
            birdTimeStampView = view.findViewById(R.id.timestamptextId);
            birdImageView = view.findViewById(R.id.birdPicture);

        }
    }

    private final LayoutInflater inflater;
    private List<Bird> birds;

    public BirdListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BirdListAdapter.BirdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new BirdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirdListAdapter.BirdViewHolder holder, int position) {
        if (birds != null) {
            Bird currentBird = birds.get(position);
            holder.birdItemView.setText(currentBird.getbName());
            holder.birdRareView.setText(currentBird.getbRarity());
            holder.birdNotesView.setText(currentBird.getbNotes());
            holder.birdTimeStampView.setText(currentBird.getbTimeStamp());
            holder.birdImageView.setImageURI(Uri.parse(currentBird.getBirdImage()));
        } else {
            holder.birdItemView.setText("No bird found!");
        }
    }

    public void setBirds(List<Bird> mBirds) {
        birds = mBirds;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (birds != null)
            return birds.size();
        else return 0;
    }
}
