package dadm.quixada.ufc.screennavigation.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dadm.quixada.ufc.screennavigation.R
import dadm.quixada.ufc.screennavigation.models.Player

class PlayersAdapter(private val context: Activity, private val playerList: ArrayList<Player>)
    :ArrayAdapter<Player>(context, R.layout.player_list_item, playerList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.player_list_item, null)

        val player = playerList[position]
        val playerIdTextView: TextView = view.findViewById(R.id.player_id)
        val playerNameTextView: TextView = view.findViewById(R.id.player_name)
        val playerPositionTextView: TextView = view.findViewById(R.id.player_position)
        val playerClubTextView: TextView = view.findViewById(R.id.player_club)
        val playerValueTextView: TextView = view.findViewById(R.id.player_value)

        playerIdTextView.text = "ID: " + player.id.toString()
        playerNameTextView.text = player.name
        playerPositionTextView.text = player.position
        playerClubTextView.text = player.club
        playerValueTextView.text = player.value.toString()

        return view
    }
}