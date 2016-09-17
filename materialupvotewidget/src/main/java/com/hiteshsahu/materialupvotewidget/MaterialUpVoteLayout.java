package com.hiteshsahu.materialupvotewidget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by 663918 on 9/16/2016.
 */
public class MaterialUpVoteLayout extends CoordinatorLayout {

    private boolean isUpVoted;
    private int voteCount;
    private VoteChangeListener voteChangeListener;
    private TextSwitcher voteSwitcher;
    private Animation slideInUp;
    private Animation slideOutUp;
    private Animation slidInDown;
    private Animation fadeOut;
    private View upVoteTriangle;
    private View upVote;
    private View downVote;

    public MaterialUpVoteLayout(Context context) {
        super(context);
        init(context);
    }

    public MaterialUpVoteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MaterialUpVoteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * @param voteCount
     */
    public void setVoteCount(int voteCount, boolean isUpVoted) {

        //AVOID http://stackoverflow.com/questions/26819429/cannot-start-this-animator-on-a-detached-view-reveal-effect
        if (isAttachedToWindow()) {
            this.voteCount = voteCount;
            this.isUpVoted = isUpVoted;

            if (isUpVoted) {
                handleUpVote();
            } else {
                handleDownVote();
            }
        }
    }

    /**
     * @param voteChangeListener
     */
    public void setVoteChangeListener(VoteChangeListener voteChangeListener) {
        this.voteChangeListener = voteChangeListener;
    }

    /**
     * @param context
     */
    protected void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.material_upvote_layout, this, true);
        upVote = (View) rootView.findViewById(R.id.upvote_button);
        downVote = (View) rootView.findViewById(R.id.downvote_button);
        upVoteTriangle = (View) rootView.findViewById(R.id.triangle);
        voteSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        voteSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // create new textView and set the properties like clolr, size etc
                TextView voteCount = new TextView(getContext());
                voteCount.setGravity(Gravity.CENTER);
                voteCount.setTextSize(36);
                voteCount.setTextColor(Color.WHITE);
                return voteCount;
            }
        });

        // Declare the in and fadeOut animations and initialize them
        slideInUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_up);
        slideOutUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_up);
        slidInDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_down);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

        upVote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleUpVote();
            }
        });

        downVote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDownVote();

            }
        });

    }

    private void handleDownVote() {
        //Start exit animation
        exitReveal(downVote);
        isUpVoted = false;

        //update vote count
        voteSwitcher.setInAnimation(slidInDown);
        voteSwitcher.setOutAnimation(fadeOut);

        voteSwitcher.setText(String.valueOf(--voteCount));
        if (null != voteChangeListener) {

            //######## Handle Negative scenario ######################
            voteChangeListener.onDownVote(voteCount < 0 ? 0 : voteCount);
        }

        //Animate vote icon
        upVoteTriangle.startAnimation(slidInDown);
    }

    private void handleUpVote() {
        //Start Entry animation
        enterReveal(downVote);
        isUpVoted = true;

        //update vote count
        voteSwitcher.setInAnimation(slideInUp);
        voteSwitcher.setOutAnimation(slideOutUp);
        voteSwitcher.setText(String.valueOf(++voteCount));
        if (null != voteChangeListener) {
            voteChangeListener.onUpVote(voteCount);
        }

        //Animate vote icon
        upVoteTriangle.startAnimation(slideOutUp);
    }

    private void enterReveal(View myView) {
        // previously invisible view

        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    private void exitReveal(final View myView) {
        // previously visible view
        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth() / 2;

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }


}
