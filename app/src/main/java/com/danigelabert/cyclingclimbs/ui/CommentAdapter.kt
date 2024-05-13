import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danigelabert.cyclingclimbs.R
import com.danigelabert.cyclingclimbs.ui.Comentario

class CommentAdapter(var list: List<Comentario>) : RecyclerView.Adapter<CommentAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        val tvComment: TextView = itemView.findViewById(R.id.tvComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comentarios_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val comment = list[position]
        holder.tvUserName.text = comment.usuario
        holder.tvComment.text = comment.comentario
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
