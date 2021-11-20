package dadm.quixada.ufc.screennavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import dadm.quixada.ufc.screennavigation.adapters.PlayersAdapter
import dadm.quixada.ufc.screennavigation.models.Player

class MainActivity : AppCompatActivity() {

    private  lateinit var playerList: ArrayList<Player>
    private lateinit var adapter: PlayersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playersListView: ListView = findViewById(R.id.players_list_view)
        playerList = this.populatePlayerList()
        adapter = PlayersAdapter(this, playerList)

        playersListView.adapter = adapter

        this. handleAddPlayerButton()
    }

    private fun populatePlayerList(): ArrayList<Player> {
        val playerList: ArrayList<Player> = ArrayList()

        playerList.add(Player(1, "Gabigol", "ATA", "Flamengo", 25.0F))
        playerList.add(Player(2, "Vina", "ATA", "Ceará", 19.5F))

        return playerList
    }

    private fun handleAddPlayerButton() {
        val btnAddPlayer: Button = findViewById(R.id.add_player_button)

        btnAddPlayer.setOnClickListener {
            val intent = Intent(this, PlayerRegistrationActivity::class.java)
            val newPlayerID: Int = playerList.size + 1
            intent.putExtra("request_code", R.integer.REQUEST_ADD)
            intent.putExtra("id", newPlayerID)
            startActivityForResult(intent, R.integer.REQUEST_ADD)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
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
            dataIntent.extras!!.get("id") as Int,
            dataIntent.extras!!.get("name") as String,
            dataIntent.extras!!.get("position") as String,
            dataIntent.extras!!.get("club") as String,
            dataIntent.extras!!.get("value") as Float
        )
    }

    private fun addNewPlayer(player: Player) {
        playerList.add(player)
    }

    private fun editPlayer(player: Player) {
        playerList[player.id - 1] = player
        adapter.notifyDataSetChanged()
    }

}