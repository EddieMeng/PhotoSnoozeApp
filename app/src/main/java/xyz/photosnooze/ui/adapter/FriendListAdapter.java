package xyz.photosnooze.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import xyz.photosnooze.R;
import xyz.photosnooze.entity.Contacts;
import xyz.photosnooze.messenger.ContactsController;

/**
 * Created by shine on 16/6/10.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.Holder>{
    private Context mContext;
    private int dialogsType;
    private ArrayList<Contacts> checkedContactsList = new ArrayList<>();


    public class Holder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView name, time;
        public CheckBox checkBox;

        public Holder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.contactsAvatar);
            name = (TextView) itemView.findViewById(R.id.contactsName);
            if (dialogsType == 0) {
                checkBox = (CheckBox) itemView.findViewById(R.id.contactsCheckBox);
                checkBox.setVisibility(View.VISIBLE);
            } else if (dialogsType == 1) {
                time = (TextView) itemView.findViewById(R.id.chatTime);
            }
        }
    }


    public FriendListAdapter(Context context, int dataType) {
        this.mContext = context;
        this.dialogsType = dataType;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.contacts_row, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final Contacts contacts = ContactsController.getInstance().contactsList.get(position);
        holder.name.setText(contacts.getName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (checkedContactsList.size() > 0) {
                        buttonView.setChecked(false);
                    } else {
                        checkedContactsList.add(contacts);
                    }
                    Toast.makeText(mContext, checkedContactsList.size() + "", Toast.LENGTH_SHORT).show();
                } else {
                    checkedContactsList.remove(contacts);
                    Toast.makeText(mContext, checkedContactsList.size() + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getContactsArray().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private ArrayList<Contacts> getContactsArray() {
        return ContactsController.getInstance().contactsList;
    }

    public ArrayList<Contacts> getSelectedContacts() {
        return checkedContactsList;
    }


}
