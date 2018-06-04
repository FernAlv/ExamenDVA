package dennisveintimilla.examenimovilesveintimilladennis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dennisveintimilla.examenimovilesveintimilladennis.CreateActivity
import com.example.dennisveintimilla.examenimovilesveintimilladennis.ListActivity
import com.example.dennisveintimilla.examenimovilesveintimilladennis.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_crear.setOnClickListener{
            irACreateView()
        }

        btn_listar.setOnClickListener{
            irAListView()
        }
    }

    fun irAListView() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    fun irACreateView() {
        val intent = Intent(this, CreateActivity::class.java)
        intent.putExtra("tipo", "Create")
        startActivity(intent)
    }
}
