package vas.tsv.contactsmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import vas.tsv.contactsmanagerapp.databinding.ActivityAddNewCntactBinding;

public class AddNewCntactActivity extends AppCompatActivity {
    private ActivityAddNewCntactBinding binding;
    private AddNewContactClickHandler handler;
    private Contacts contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cntact);
        contacts = new Contacts();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_cntact);

        MyViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        handler = new AddNewContactClickHandler(contacts, this, myViewModel);
        binding.setContact(contacts);
        binding.setClickHandler(handler);
    }
}