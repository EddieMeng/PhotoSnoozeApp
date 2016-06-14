package xyz.photosnooze.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import xyz.photosnooze.R;
import xyz.photosnooze.ui.actionbar.NavigationBackCell;
import xyz.photosnooze.ui.actionbar.PhotoSnoozeActionBar;
import xyz.photosnooze.ui.adapter.FriendListAdapter;
import xyz.photosnooze.ui.components.DividerItemDecoration;

public class FriendListActivity extends AppCompatActivity {
    private RecyclerView friendList;
    private Button confirmBtn, giveUpBtn;
    private PhotoSnoozeActionBar actionBar;
    private NavigationBackCell navigationBackCell;

    private FriendListAdapter friendListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        init();

        navigationBackCell = actionBar.createNavigationCell();
        navigationBackCell.addNavigationImage(this).addNavigatinText(this, "GO BACK").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        friendListAdapter = new FriendListAdapter(this, 0);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        friendList.setLayoutManager(mLayoutManager);
        friendList.setItemAnimator(new DefaultItemAnimator());
        friendList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        friendList.setAdapter(friendListAdapter);
//        friendList.addOnItemTouchListener(new RecyclerViewTouchListener(this, friendList, new recyclerViewOnClickListener() {
//            @Override
//            public void onClick(View view, int Position) {
//                Toast.makeText(FriendListActivity.this, "OnClick", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int Position) {
//                Toast.makeText(FriendListActivity.this, "OnLongClick", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendListAdapter.getSelectedContacts().size() == 0) {
                    Toast.makeText(FriendListActivity.this, "Still Not Choose Friend To Send", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = getIntent();
                intent.putExtra("contactsChoosen", friendListAdapter.getSelectedContacts());
                setResult(RESULT_OK, intent);
                finish();
                friendListAdapter.getSelectedContacts().clear();
            }
        });

        giveUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void init() {
        actionBar = (PhotoSnoozeActionBar) findViewById(R.id.actionbar_friend);
        friendList = (RecyclerView) findViewById(R.id.friendRecyclerlist);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        giveUpBtn = (Button) findViewById(R.id.giveUpBtn);
    }






}
