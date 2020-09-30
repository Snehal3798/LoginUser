package snehal.adhav.loginuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
private EditText name,email,password,c_password;
private Button btn_reg;
private ProgressBar loading;
private static String URL_REG="https://referal123.000webhostapp.com/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //URL_REG.replaceAll("","%20");
        loading=findViewById(R.id.loading);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        c_password=findViewById(R.id.c_password);
        btn_reg=findViewById(R.id.reg);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }
    private void Register(){
        loading.setVisibility(View.VISIBLE);
        btn_reg.setVisibility(View.GONE);
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();



        StringRequest stringRequest =new StringRequest(Request.Method.POST, URL_REG,
                   new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            try {

                                JSONObject jsonObject = new  JSONObject(response);
                                String success = jsonObject.getString("success");


                                if (success.equals("1")){
                                    Toast.makeText(RegisterActivity.this, "Registration Sucessful", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                                Toast.makeText(RegisterActivity.this, "Registration Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_reg.setVisibility(View.VISIBLE);
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RegisterActivity.this, "Registration Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_reg.setVisibility(View.VISIBLE);
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params =new HashMap<>();
              //  params.put("Content-Type", "application/json; charset=utf-8");
               params.put("name",name);
               params.put("email",email);
               params.put("password",password);
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}