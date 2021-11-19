package dadm.quixada.ufc.screennavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import dadm.quixada.ufc.screennavigation.models.Player

class PlayerRegistrationActivity : AppCompatActivity() {

    var id: Int = 0
    private lateinit var titleTextView: TextView
    private lateinit var actionButton: Button
    private lateinit var idTextView: TextView
    private lateinit var nameEditText: TextInputEditText
    private lateinit var positionEditText: TextInputEditText
    private lateinit var clubEditText: TextInputEditText
    private lateinit var valueEditText: TextInputEditText
    val RESULT_ADD: Int = 1
    val RESULT_EDIT: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_registration)

        titleTextView = findViewById(R.id.title_registration_screen)
        actionButton = findViewById(R.id.add_player_button_registration)
        idTextView = findViewById(R.id.player_id_registration)
        nameEditText = findViewById(R.id.player_name_input_text)
        positionEditText = findViewById(R.id.player_position_input_text)
        clubEditText = findViewById(R.id.player_club_input_text)
        valueEditText = findViewById(R.id.player_value_input_text)

        this.handleRequestCode()
        this.configureActionButton()
    }

    private fun getRequestCode(): Int {
        var requestCode = 0
         if(intent.extras != null){
             requestCode = intent.extras!!.get("request_code") as Int
         }

        return requestCode
    }

    private fun getResultCode(): Int {

        var resultCode: Int = 0

        when(getRequestCode()){
            1 -> resultCode = RESULT_ADD
            2 -> resultCode = RESULT_EDIT
            else -> resultCode = 0
        }

        return resultCode
    }

    private fun handleRequestCode() {

        val requestCode = this.getRequestCode()

        if(requestCode == 2){
            fillFields()
            modifyTitleAndButton("Editar jogador", "Editar")
        }else {
            fillID()
        }
    }

    private fun modifyTitleAndButton(screenTitle: String, buttonText: String) {
        titleTextView.text = screenTitle
        actionButton.text = buttonText
    }

    private fun fillID(){
        id = intent.extras?.get("id") as Int
        idTextView.text = "ID: " + id.toString()
    }

    private fun fillFields() {
        this.fillID()
        nameEditText.setText(intent.extras!!.get("name") as String)
        positionEditText.setText(intent.extras!!.get("position") as String)
        clubEditText.setText(intent.extras!!.get("club") as String)
        valueEditText.setText((intent.extras!!.get("value") as Float).toString())

    }

    private fun configureActionButton(){
        actionButton.setOnClickListener {

            val intent = Intent()
            intent.putExtra("id", id )
            intent.putExtra("name", nameEditText.text.toString())
            intent.putExtra("position", positionEditText.text.toString())
            intent.putExtra("club", clubEditText.text.toString())
            intent.putExtra("value", valueEditText.text.toString().toFloat())

            val resultCode = this.getResultCode()
            setResult(resultCode, intent)
            finish()
        }
    }
}