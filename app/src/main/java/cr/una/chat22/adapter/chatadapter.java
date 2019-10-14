package cr.una.chat22.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import cr.una.chat22.R;
import cr.una.chat22.model.Chat;
import cr.una.chat22.model.User;

public class chatadapter extends RecyclerView.Adapter<chatadapter.ViewHolder> {

    public  static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context mContext;
    private List<Chat> mChat;
    private String ImageURL;

    FirebaseUser fuser;

    public chatadapter(Context mContext, List<Chat> mChat, String imageURL) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.ImageURL = imageURL;
    }

    @NonNull
    @Override
    public chatadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return  new chatadapter.ViewHolder(view);
        }else
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_items_left, parent, false);
            return  new chatadapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull chatadapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.mostrarmensaje.setText(chat.getMensaje());

        if(ImageURL.equals("default")){
        holder.profile_image.setImageResource(R.mipmap.ic_launcher);

        }else{
          Glide.with(mContext).load(ImageURL).into(holder.profile_image);
        }
    }



    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mostrarmensaje;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mostrarmensaje = itemView.findViewById(R.id.Mostrarmensaje);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getEmisor().equals((fuser.getUid()))){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
