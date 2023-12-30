package vas.tsv.contactsmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MainActivityClickHandlers {

    Context context;

    public MainActivityClickHandlers(Context context) {
        this.context = context;
    }

    public void onFABClicked(View view){
        Intent i = new Intent(view.getContext(), AddNewCntactActivity.class);
        context.startActivity(i);
    }
}
