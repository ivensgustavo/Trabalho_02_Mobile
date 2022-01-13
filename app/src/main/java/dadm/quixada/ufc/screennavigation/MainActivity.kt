package dadm.quixada.ufc.screennavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.screennavigation.adapters.PlayersAdapter
import dadm.quixada.ufc.screennavigation.models.Player
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var playerList: ArrayList<Player> = ArrayList()
    private lateinit var adapter: PlayersAdapter
    private lateinit var playerListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerListView = findViewById(R.id.players_list_view)
        this.populatePlayerList()
        adapter = PlayersAdapter(this, playerList)
        playerListView.adapter = adapter

        this.handleAddPlayerButton()
    }

    private fun populatePlayerList(): ArrayList<Player> {
        val db = Firebase.firestore

        db.collection("players")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val player = Player(
                        document["id"].toString(),
                        document["name"].toString(),
                        document["position"].toString(),
                        document["club"].toString(),
                        document["value"].toString().toFloat()
                    )

                    this.playerList.add(player)
                    Log.d("Players", this.playerList.toString())
                }

                adapter.notifyDataSetChanged()

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Ocorreu um erro ao buscar os dados.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        return playerList
    }

    private fun handleAddPlayerButton() {
        val btnAddPlayer: Button = findViewById(R.id.add_player_button)

        btnAddPlayer.setOnClickListener {
            val intent = Intent(this, PlayerRegistrationActivity::class.java)
            val newPlayerID: String = UUID.randomUUID().toString()
            intent.putExtra("request_code", R.integer.REQUEST_ADD)
            intent.putExtra("id", newPlayerID)
            startActivityForResult(intent, R.integer.REQUEST_ADD)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            R.integer.RESULT_ADD -> {
                if (data != null) {
                    addNewPlayer(getReturnedPlayer(data))
                }
            }
            R.integer.RESULT_EDIT -> {
                if (data != null) {
                    editPlayer(getReturnedPlayer(data))
                }
            }
        }
    }


    private fun getReturnedPlayer(dataIntent: Intent): Player {

        return Player(
            dataIntent.extras!!.get("id") as String,
            dataIntent.extras!!.get("name") as String,
            dataIntent.extras!!.get("position") as String,
            dataIntent.extras!!.get("club") as String,
            dataIntent.extras!!.get("value") as Float
        )
    }

    private fun addNewPlayer(player: Player) {
        playerList.add(player)
        adapter.notifyDataSetChanged()
    }

    private fun editPlayer(player: Player) {
        val playerRef = playerList.find { it.id == player.id }
        val index = playerList.indexOf(playerRef)
        playerList[index] = player
        adapter.notifyDataSetChanged()
    }
}