package com.example.taskmysqlite;

import static com.example.taskmysqlite.DatabaseHelper.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private Context context;
    int singledata;
    private final ArrayList<Game> gameArrayList;
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper mDatabaseHelper;

    public GameAdapter(Context context, int singledata, ArrayList<Game> gameArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.gameArrayList = gameArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_games, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Game game = gameArrayList.get(position);
        byte[] imageGame = game.getGamePicture();
        Bitmap bitmap= BitmapFactory.decodeByteArray(imageGame,0, imageGame.length);
        holder.imgGame.setImageBitmap(bitmap);
        holder.txtNameGame.setText(game.getNamaGame());
        holder.txtPrice.setText(game.getPriceGame());
        holder.txtType.setText(game.getTypeGame());

        // Flow Menu
        holder.flowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.flowMenu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_menu:
                                // Operasi Edit
                                Bundle bundle = new Bundle();
                                bundle.putInt(DatabaseHelper.GAMES_ID, game.getId());
                                bundle.putByteArray(DatabaseHelper.GAMES_IMAGE, game.getGamePicture());
                                bundle.putString(DatabaseHelper.GAMES_NAME, game.getNamaGame());
                                bundle.putString(DatabaseHelper.GAMES_PRICE, game.getPriceGame());
                                bundle.putString(DatabaseHelper.GAMES_TYPE, game.getTypeGame());
                                Intent intent = new Intent(context,MainActivity.class);
                                intent.putExtra("userdata", bundle);
                                context.startActivity(intent);
                                break;

                            case R.id.delete_menu:
                                // Operasi Delete
                                mDatabaseHelper = new DatabaseHelper(context);
                                sqLiteDatabase = mDatabaseHelper.getReadableDatabase();
                                long recdelete = sqLiteDatabase.delete(TABLE_NAME,"id=" + game.getId(),null);
                                if (recdelete != -1){
                                    Toast.makeText(context, "Data Terhapus", Toast.LENGTH_SHORT).show();
                                    // Hilangkan posisi di RV setelah dihapus
                                    gameArrayList.remove(position);
                                    // Update Data Setelah dihapus
                                    notifyDataSetChanged();
                                }
                                break;
                            default:
                                return false;
                        }

                        return false;
                    }
                });

                // Display Menu
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGame;
        TextView txtNameGame, txtPrice, txtType;
        ImageButton flowMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGame = itemView.findViewById(R.id.gambar_game);
            txtNameGame = itemView.findViewById(R.id.name_game);
            txtPrice = itemView.findViewById(R.id.price);
            txtType = itemView.findViewById(R.id.type_game);
            flowMenu = itemView.findViewById(R.id.flowmenu);

        }
    }
}
