package com.easyapps.CricketTeam;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyapps.CricketTeam.data.PlayersContract;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PlayerViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public RecyclerViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.player_list_item, parent, false);
        return new PlayerViewHolder(view);
    }
//-------------------------------------------------------------------------
    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        String name = mCursor.getString(mCursor.getColumnIndex(PlayersContract.Team.COLUMN_PLAYER_NAME));
        int average = mCursor.getInt(mCursor.getColumnIndex(PlayersContract.Team.COLUMN_PLAYER_AVERAGE));

        long id = mCursor.getLong(mCursor.getColumnIndex(PlayersContract.Team._ID));

        holder.nameTextView.setText(name);

        holder.partySizeTextView.setText(String.valueOf(average));

        holder.itemView.setTag(id);
    }
//-----------------------------------------------------------------

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
//----------------------------------------------------------------
    class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView partySizeTextView;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            partySizeTextView = itemView.findViewById(R.id.party_size_text_view);
        }

    }
//---------------------------------------------------------
}