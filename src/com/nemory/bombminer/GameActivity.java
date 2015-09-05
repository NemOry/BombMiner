package com.nemory.bombminer;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class GameActivity extends Activity {
	
	private TableLayout table;
	private Bomb bomb;
	
	private final int limit = 30;
	private int guesses = 0;
	
	private class Bomb{
		
		public int x;
		public int y;
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.initialize();
        this.setUp();
        this.showInstructions();
        
        Random randomGenerator = new Random();
        
        bomb = new Bomb();
        bomb.x = randomGenerator.nextInt(11);
        bomb.y = randomGenerator.nextInt(16);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }
    
    private void setUp()
    {
        int rows = 12;
        int columns = 10;
        
        for(int x = 0; x < rows; x++)
        {
        	TableRow tr = new TableRow(this);
        	tr.setGravity(Gravity.CENTER);
        	
        	for(int y = 0; y < columns; y++)
            {
        		final Button btn = new Button(this);
        		btn.setText("o");
        		
        		Bomb position = new Bomb();
        		position.x = x;
        		position.y = y;
        		
        		btn.setTag(position);
        		
        		btn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						btn.setEnabled(false);
						
						Bomb btnPosition = new Bomb();
						btnPosition = (Bomb)btn.getTag();

						if(btnPosition.x == bomb.x && btnPosition.y == bomb.y)
						{
							AlertDialog d = new AlertDialog.Builder(GameActivity.this).create();
							d.setCancelable(false);
							d.setTitle("Congratulations!");
							d.setMessage("You've found the bomb!");

							d.setButton(d.BUTTON_POSITIVE, "Play again",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											restart();
										}
									});

							d.setButton(d.BUTTON_NEGATIVE, "Back to menu",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											finish();
										}
									});

							d.show();
						}
						else
						{
							guesses++;
							
							if(guesses == 30)
							{
								AlertDialog d = new AlertDialog.Builder(GameActivity.this).create();
								d.setCancelable(false);
								d.setTitle("Sorry!");
								d.setMessage("You're not lucky enough to find it.");

								d.setButton(d.BUTTON_POSITIVE, "Play again",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												restart();
											}
										});

								d.setButton(d.BUTTON_NEGATIVE, "Back to menu",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												finish();
											}
										});

								d.show();
							}
						}
					}
				});
        		
            	tr.addView(btn);
            }
        	
        	table.addView(tr);
        }
    }
    
    private void restart()
    {
    	for(int x = 0; x < table.getChildCount(); x++)
    	{
    		TableRow tr = (TableRow) table.getChildAt(x);
    		
    		for(int y = 0; y < tr.getChildCount(); y++)
    		{
    			Button btn = (Button) tr.getChildAt(y);
    			btn.setEnabled(true);
    		}
    	}
    	
    	Random randomGenerator = new Random();
        
        bomb = new Bomb();
        bomb.x = randomGenerator.nextInt(11);
        bomb.y = randomGenerator.nextInt(16);
    }
    
    private void showInstructions()
    {
    	AlertDialog d = new AlertDialog.Builder(this).create();
		d.setCancelable(false);
		d.setTitle("Instructions");
		d.setMessage("Just click on the mines to guess where's the bomb. You have 30 clicks to find it. If not, you're dead. :)");

		d.setButton(d.BUTTON_POSITIVE, "Got It! Let's start!",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});

		d.setButton(d.BUTTON_NEGATIVE, "Back to menu",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});

		d.show();
    }
    
    private void initialize()
    {
    	table = (TableLayout) findViewById(R.id.table);
    }
}
