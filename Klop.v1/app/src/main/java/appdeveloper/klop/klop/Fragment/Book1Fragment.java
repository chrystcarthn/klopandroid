package appdeveloper.klop.klop.Fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.HashMap;

import appdeveloper.klop.klop.Activity.CreateMyBooking;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Book1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Book1Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Book1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Book1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Book1Fragment newInstance(String param1, String param2) {
        Book1Fragment fragment = new Book1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private View view;
    private EditText edPeople;
    private EditText edNamaOrgLain;
    private EditText edTelpOrgLain;

    private RadioGroup rGroup;
    private RadioButton rButton;
    private RadioButton rbSendiri;
    private RadioGroup rbOrgLain;
    int button = 1;
    private Button buttonNext;
    ElegantNumberButton elegantNumberButton;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser, strPeople, strNmUser, strPhone, strAddress;
    String strIdStore, strNmStore;

    TextInputLayout nameWrapper, phoneWrapper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book1, container, false);

        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();

        Bundle b = getActivity().getIntent().getExtras();
        strIdStore = b.getString("idStore");
        strNmStore = b.getString("nmStore");
        strAddress = b.getString("address");

        loadComponent();

        rGroup = (RadioGroup) view.findViewById(R.id.rbGroup);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbMandiri:
                        edNamaOrgLain.setText("");
                        edTelpOrgLain.setText("");
                        edNamaOrgLain.setEnabled(false);
                        edTelpOrgLain.setEnabled(false);
                        button = 1;
                        //Toast.makeText(getContext(), "id "+checkedId, Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rbOrgLain:
                         edNamaOrgLain.setEnabled(true);
                         edTelpOrgLain.setEnabled(true);
                         button = 2;
                      //  Toast.makeText(getContext(), "id "+checkedId, Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }


    private void loadComponent() {
        nameWrapper = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        phoneWrapper = (TextInputLayout) view.findViewById(R.id.input_layout_telepon);
        edNamaOrgLain = (EditText) view.findViewById(R.id.edName);
        edTelpOrgLain = (EditText) view.findViewById(R.id.edTelepon);
        elegantNumberButton = (ElegantNumberButton) view.findViewById(R.id.number_button);
        elegantNumberButton.setNumber("1");
       // edPeople = (EditText) view.findViewById(R.id.edPeople);

        buttonNext = (Button) view.findViewById(R.id.button_next_fragment_step_1);
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonNext) {
            strIdUser = userdata.get(SessionPreference.KEY_IDUSER);
            strPeople = elegantNumberButton.getNumber();

            if(button == 1){
                strNmUser = userdata.get(SessionPreference.KEY_FULLNAME);
                strPhone = userdata.get(SessionPreference.KEY_PHONE);

                CreateMyBooking.goToStepOrangTua();
                Book2Fragment book2Fragment = new Book2Fragment();

                Bundle bundle = new Bundle();
                bundle.putString("idStore", strIdStore);
                bundle.putString("nmStore", strNmStore);
                bundle.putString("address", strAddress);
                bundle.putString("people", strPeople);
                bundle.putString("idUser", strIdUser);
                bundle.putString("nama", strNmUser);
                bundle.putString("telp", strPhone);
                book2Fragment.setArguments(bundle);
             //   Toast.makeText(getContext(), "1 "+strIdStore, Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                        .replace(R.id.frame_layout, book2Fragment)
                        .addToBackStack(null)
                        .commit();


            }else if(button == 2){
                strNmUser = edNamaOrgLain.getText().toString();
                strPhone = edTelpOrgLain.getText().toString();

                if(isValidCust(strNmUser) == false){
                    showInvalidName();
                }else if(isValidPhoneLength(strPhone) == false){
                    showInvalidPhoneLength();
                }else{
                    CreateMyBooking.goToStepOrangTua();
                    Book2Fragment book2Fragment = new Book2Fragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("idStore", strIdStore);
                    bundle.putString("people", strPeople);
                    bundle.putString("nmStore", strNmStore);
                    bundle.putString("address", strAddress);
                    bundle.putString("idUser", strIdUser);
                    bundle.putString("nama", strNmUser);
                    bundle.putString("telp", strPhone);
                    book2Fragment.setArguments(bundle);

                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                            .replace(R.id.frame_layout, book2Fragment)
                            .addToBackStack(null)
                            .commit();


                }
            }




        }
    }

    public boolean isValidCust(String strNmUser){
        if(strNmUser.length() <= 2){
            return false;
        } return true;
    }

    public boolean isValidPhoneLength(String strPhone){
        if(strPhone.length() <= 7){
            return false;
        } return true;
    }

    public void showInvalidName() {
        phoneWrapper.setErrorEnabled(false);
        nameWrapper.setErrorEnabled(true);

        nameWrapper.setError("Nama harus diisi");
        requestFocus(edNamaOrgLain);
    }

    public void showInvalidPhoneLength() {
        phoneWrapper.setErrorEnabled(true);
        nameWrapper.setErrorEnabled(false);

        phoneWrapper.setError("Nomor telepon minimal 8 digit");
        requestFocus(edTelpOrgLain);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
