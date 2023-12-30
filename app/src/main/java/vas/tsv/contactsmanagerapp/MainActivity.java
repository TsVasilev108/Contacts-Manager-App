package vas.tsv.contactsmanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import vas.tsv.contactsmanagerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //Data Sources
    private ContactDatabase contactDatabase;
    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();
    //Adapter
    private MyAdapter myAdapter;
    //Binding
    private ActivityMainBinding mainBinding;
    private MainActivityClickHandlers handlers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandlers(this);
        mainBinding.setClickHandler(handlers);

        //RecyclerView
        RecyclerView recyclerView = mainBinding.recyclerview;
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //Database
        contactDatabase = ContactDatabase.getInstance(this);

        //ViewModel
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        //Insert a new Contact (Just For Testing)
//        Contacts c1 = new Contacts("Jack", "jack@gmail.com");
//        viewModel.addNewContact(c1);

        //Loading the Data form ROOM DB
        viewModel.getAllContacts().observe(this, new Observer<List<Contacts>>() {
            @Override
            public void onChanged(List<Contacts> contacts) {
                contactsArrayList.clear();
                for(Contacts c: contacts){
                    Log.v("TAGY", String.valueOf(c.getId()));
                    Log.v("TAGY", c.getName());
                    contactsArrayList.add(c);
                }


//                ListIterator<Contacts> listIterator = contacts.listIterator();
//                while(listIterator.hasNext()){
//
//                    Contacts c1 = listIterator.next();
//                    Log.v("TAGY", String.valueOf(c1.getId()));
//                    Log.v("TAGY", c1.getName());
//                    listIterator.add(c1);
//                }

                myAdapter.notifyDataSetChanged();
            }
        });


        //Adapter
        myAdapter = new MyAdapter(contactsArrayList);

        //Linking RecyclerView with the Adapter
        recyclerView.setAdapter(myAdapter);

        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //If you swipe the item to the left
                Contacts c = contactsArrayList.get(viewHolder.getAdapterPosition());

                viewModel.deleteContact(c);
            }
        }).attachToRecyclerView(recyclerView);



    }
}