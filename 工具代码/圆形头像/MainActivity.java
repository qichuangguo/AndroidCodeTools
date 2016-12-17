package com.qfxu.iconview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private IconView iconView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iconView = (IconView) findViewById(R.id.iconView_main);
		iconView.setOnMyClickListener(new OnMyClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "µ„¡À",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
