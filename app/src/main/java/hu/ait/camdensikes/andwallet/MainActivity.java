package hu.ait.camdensikes.andwallet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static hu.ait.camdensikes.andwallet.R.drawable.income;
import static hu.ait.camdensikes.andwallet.R.string.no_amount;
import static hu.ait.camdensikes.andwallet.R.string.no_name;

public class MainActivity extends AppCompatActivity {

    LinearLayout layoutList;

    EditText etName;
    EditText etAmount;

    TextView tvBalance;

    ToggleButton btnInOut;
    Button btnSave;


    boolean inOutChecked = false;

    int balance = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutList = (LinearLayout) findViewById(R.id.layoutList);

        etName = (EditText) findViewById(R.id.etName);
        etAmount = (EditText) findViewById(R.id.etAmount);

        tvBalance = (TextView) findViewById(R.id.balance);

        btnInOut = (ToggleButton) findViewById(R.id.btnInOut);

        btnInOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) inOutChecked = true;
                else inOutChecked = false;
                return;
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newItem(v);
            }
        });
    }

    private void newItem(View v) {
        boolean error = false;
        String name = etName.getText().toString();
        if(name.matches("")){
            etName.setError(getString(no_name));
            error = true;
        }
        String amount = etAmount.getText().toString();
        if(amount.matches("")){
            etAmount.setError(getString(no_amount));
            error = true;
        }

        if(error){
            return;
        }


        int value = Integer.parseInt(amount);

        View listItem = getLayoutInflater().inflate(R.layout.list_item, null, false);

        TextView tvInfo = (TextView) listItem.findViewById(R.id.tvInfo);

        tvInfo.setText(name + "\n$" + amount);

        if(inOutChecked){
            ImageView ivType = (ImageView) listItem.findViewById(R.id.ivType);
            ivType.setImageResource(income);
            value = -value;
        }

        setBalance(balance-value);

        layoutList.addView(listItem,0);

        etName.setText("");
        etAmount.setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clearList) {
            layoutList.removeAllViews();
            setBalance(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setBalance(int newBalance) {
        balance = newBalance;
        String balanceText = getString(R.string.balance);
        balanceText = balanceText + balance;
        tvBalance.setText(balanceText);
    }
}
