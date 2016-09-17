package com.hiteshsahu.materialupvote;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.hiteshsahu.materialupvotewidget.MaterialUpVoteLayout;
import com.hiteshsahu.materialupvotewidget.VoteChangeListener;

import java.util.Random;

public class DemoActivity extends AppCompatActivity implements VoteChangeListener {

    private CoordinatorLayout coordinatorLayout;
    private MaterialUpVoteLayout upvoteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root);
        upvoteLayout = (MaterialUpVoteLayout) findViewById(R.id.upvote_layout);

        //set Votes
        upvoteLayout.setVoteCount(22, false);

        //Listen for Vote Change in Class
        upvoteLayout.setVoteChangeListener(DemoActivity.this);

        //----------OR------

        // Listen for Vote change anonymously
      /*  upvoteLayout.setVoteChangeListener(new VoteChangeListener() {
            @Override
            public void onUpVote(int voteCount) {
                Snackbar.make(coordinatorLayout, "Up Voted " + voteCount, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onDownVote(int voteCount) {
                Snackbar.make(coordinatorLayout, "Down Voted " + voteCount, Snackbar.LENGTH_SHORT).show();
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Generate Random Test Data
                int vote = new Random().nextInt(100);
                boolean voteType = new Random().nextBoolean();
                //set Votes
                upvoteLayout.setVoteCount(vote, voteType);
                Toast.makeText(getApplicationContext(), "Updated voted " + vote + (voteType ? "Upvote" : "Downvote"),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpVote(int voteCount) {
        Snackbar.make(coordinatorLayout, "Up Voted " + voteCount, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDownVote(int voteCount) {
        Snackbar.make(coordinatorLayout, "Down Voted " + voteCount, Snackbar.LENGTH_SHORT).show();

    }
}
