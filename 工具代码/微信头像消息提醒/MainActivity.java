package com.qfxu.myview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private NumView numView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		numView = (NumView) findViewById(R.id.numView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button1:
			numView.setShowNumMode((numView.getShowNumMode() + 1) % 3);
			break;
		case R.id.button2:
			numView.setNum(numView.getNum() + 1);
			break;
		case R.id.button3:
			numView.setNum(0);
			break;
		default:
			break;
		}
	}

}
