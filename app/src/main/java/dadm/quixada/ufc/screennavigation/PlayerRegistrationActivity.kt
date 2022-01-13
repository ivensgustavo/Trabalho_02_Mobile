package dadm.quixada.ufc.screennavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.screennavigation.models.Player

class PlayerRegistrationActivity : AppCompatActivity() {

    var id: String = ""
    private lateinit var titleTextView: TextView
    private lateinit var actionButton: Button
    private lateinit var cancelButton: Button
    private lateinit var idTextView: TextView
    private lateinit var nameEditText: TextInputEditText
    private lateinit var positionEditText: TextInputEditText
    private lateinit var clubEditText: TextInputEditText
    private lateinit var valueEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_registration)

        this.initializeViews()
        this.handleRequestCode()
        this.configureActionButton()
        this.configureCancelButton()
    }

    private fun initializeViews() {
        titleTextView = findViewById(R.id.title_registration_screen)
        actionButton = findViewById(R.id.add_player_button_registration)
        cancelButton = findViewById(R.id.cancel_button)
        idTextView = findViewById(R.id.player_id_registration)
        nameEditText = findViewById(R.id.player_name_input_text)
        positionEditText = findViewById(R.id.player_position_input_text)
        clubEditText = findViewById(R.id.player_club_input_text)
        valueEditText = findViewById(R.id.player_value_input_text)
    }

    private fun getRequestCode(): Int {
        var requestCode = 0
         if(intent.extras != null){
             requestCode = intent.extras!!.get("request_code") as Int
         }

        return requestCode
    }

    private fun getResultCode(): Int {

        var resultCode = 0

        resultCode = when(getRequestCode()){
            R.integer.REQUEST_ADD -> R.integer.RESULT_ADD
            R.integer.REQUEST_EDIT -> R.integer.RESULT_EDIT
            else -> 0
        }

        return resultCode
    }

    private fun handleRequestCode() {

        val requestCode = this.getRequestCode()

        if(requestCode == R.integer.REQUEST_EDIT){
            fillFields()
            modifyTitleAndButton(getString(R.string.edit_screen_label), getString(R.string.edit_label))
        }else {
            fillID()
        }
    }

    private fun modifyTitleAndButton(screenTitle: String, buttonText: String) {
        titleTextView.text = screenTitle
        actionButton.text = buttonText
    }

    private fun fillID(){
        id = intent.extras?.get("id").toString()
        idTextView.text = "ID: " + id
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
            saveOrUpdatePlayer()
        }
    }

    private fun configureCancelButton() {
        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun saveOrUpdatePlayer(){
        val player = Player(
            id,
            nameEditText.text.toString(),
            positionEditText.text.toString(),
            clubEditText.text.toString(),
            valueEditText.text.toString().toFloat()
        )

        val db = Firebase.firestore

        db.collection("players").document(id.toString())
            .set(player)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val intent = Intent()
                    intent.putExtra("id", player.id )
                    intent.putExtra("name", player.name)
                    intent.putExtra("position", player.position)
                    intent.putExtra("club", player.club)
                    intent.putExtra("value", player.value)

                    val resultCode = this.getResultCode()
                    setResult(resultCode, intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Ocorreu um erro ao salvar o jogador. Tente novamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }
}