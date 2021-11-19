package dadm.quixada.ufc.screennavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import dadm.quixada.ufc.screennavigation.adapters.PlayersAdapter
import dadm.quixada.ufc.screennavigation.models.Player

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playersListView: ListView = findViewById(R.id.players_list_view)
        val playerList: ArrayList<Player> = this.populatePlayerList()
        val adapter: PlayersAdapter = PlayersAdapter(this, playerList)

        playersListView.adapter = adapter

        val btnAddPlayer: Button = findViewById(R.id.add_player_button)
        btnAddPlayer.setOnClickListener {
            val intent = Intent(this, PlayerRegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun populatePlayerList(): ArrayList<Player> {
        val playerList: ArrayList<Player> = ArrayList()

        playerList.add(Player(1, "Gabigol", "ATA", "Flamengo", 25.0F))
        playerList.add(Player(2, "Vina", "ATA", "Ceará", 19.5F))

        return playerList
    }
}