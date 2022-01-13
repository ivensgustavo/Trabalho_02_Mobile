package dadm.quixada.ufc.screennavigation.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dadm.quixada.ufc.screennavigation.PlayerRegistrationActivity
import dadm.quixada.ufc.screennavigation.R
import dadm.quixada.ufc.screennavigation.models.Player

class PlayersAdapter(private val context: Activity, private val playerList: ArrayList<Player>) :
    ArrayAdapter<Player>(context, R.layout.player_list_item, playerList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.player_list_item, null)

        val player = playerList[position]
        val playerNameTextView: TextView = view.findViewById(R.id.player_name)
        val playerPositionTextView: TextView = view.findViewById(R.id.player_position)
        val playerClubTextView: TextView = view.findViewById(R.id.player_club)
        val playerValueTextView: TextView = view.findViewById(R.id.player_value)
        val editPlayerLink: TextView = view.findViewById(R.id.player_edit_link)
        val btnRemovePlayer: TextView = view.findViewById(R.id.player_remove_button)

        playerNameTextView.text = player.name
        playerPositionTextView.text = player.position
        playerClubTextView.text = player.club
        playerValueTextView.text = player.value.toString()

        editPlayerLink.setOnClickListener {
            val intent = Intent(context, PlayerRegistrationActivity::class.java)

            intent.putExtra("request_code", R.integer.REQUEST_EDIT)
            intent.putExtra("id", player.id)
            intent.putExtra("name", player.name)
            intent.putExtra("position", player.position)
            intent.putExtra("club", player.club)
            intent.putExtra("value", player.value)

            context.startActivityForResult(intent, R.integer.REQUEST_EDIT)
        }

        btnRemovePlayer.setOnClickListener {
            removePlayer(player)
        }

        return view
    }

    private fun removePlayer(player: Player) {
        val db = Firebase.firestore

        db.collection("players").document(player.id)
            .delete()
            .addOnSuccessListener {
                this.playerList.remove(player)
                Toast.makeText(
                    context,
                    "Jogador foi excluído com sucesso",
                    Toast.LENGTH_SHORT
                ).show()
                this.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Ocorreu um erro ao excluir o jogador",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}